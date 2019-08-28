package com.yzb.andong.controller.backend;

import com.yzb.andong.config.MessageKey;
import com.yzb.andong.config.ResultJson;
import com.yzb.andong.config.ResultList;
import com.yzb.andong.config.ResultStatus;
import com.yzb.andong.config.security.JwtHelper;
import com.yzb.andong.config.util.MD5Utils;
import com.yzb.andong.controller.BaseApi;
import com.yzb.andong.domain.dto.*;
import com.yzb.andong.domain.orm.PageParamDTO;
import com.yzb.andong.domain.orm.SysManageUser;
import com.yzb.andong.domain.vo.SysManageUserVO;
import com.yzb.andong.service.ServiceException;
import com.yzb.andong.service.ifac.SysManageUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yzb.andong.config.security.JwtConstant.MANAGE_JWT_SECRET;
import static com.yzb.andong.domain.orm.MessageConstant.SYSTEM_SMS_MANAGE_LOGIN_CODE_PHONE;
import static com.yzb.andong.domain.orm.MessageConstant.SYSTEM_SMS_MANAGE_MODIFY_CODE_PHONE;

/**
 * 系统管理用户api
 *
 * @author wangban
 * @date 16:10 2018/7/24
 */
@RestController
@RequestMapping("/v1/manage/sysUser")
@Api(tags = {"manage系统管理用户api"})
public class SysManageUserApi extends BaseApi {
    /**
     * 系统管理用户查询、处理业务
     */
    @Autowired
    private SysManageUserService sysManageUserService;


    @ApiOperation(value = "王斑：系统管理用户登录", notes = "返回结果为json数据，带提示")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultJson<String> login(SysManageUserLoginDTO loginDTO) {
        ResultJson<String> resultJson = new ResultJson<>();

        //对象为空
        if (isInfoNull(resultJson, loginDTO)) {
            return resultJson;
        }

        String account = loginDTO.getAccount();
        //用户为空
        if (isInfoEmpty(resultJson, account, MessageKey.ACCOUNT_NULL)) {
            return resultJson;
        }
        String pass = loginDTO.getPass();
        //密码为空
        if (isInfoEmpty(resultJson, pass, MessageKey.PASS_NULL)) {
            return resultJson;
        }
        String code = loginDTO.getCode();
        ///验证码为空
        if (isInfoEmpty(resultJson, code, MessageKey.CODE_NULL)) {
            return resultJson;
        }
        //查询用户信息
        SysManageUser manageUser = sysManageUserService.getSysMangeUserMessageByAccount(loginDTO.getAccount());
        //根据要登录名获取用户信息
        //用户不存在
        if (manageUser == null) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.ACCOUNT_ERROR);
            return resultJson;
        }
        //验证验证码
        if (getCode(resultJson, SYSTEM_SMS_MANAGE_LOGIN_CODE_PHONE, code, manageUser.getMobile())) {
            return resultJson;
        }
        //用户被锁定
        if (manageUser.getLocked()) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.USER_LOCKED);
            return resultJson;
        }

        String password = loginDTO.getPass();
        password = MD5Utils.md5Salt(password, manageUser.getSalt());
        if (password.equals(manageUser.getPass())) {
            //登录成功
            resultJson.setCode(ResultStatus.OK.getCode());
            resultJson.setMessage(MessageKey.LOGIN_SUCCESS);
            //生成jwt
            String token = JwtHelper.createManageJWT(loginDTO.getAccount(), MANAGE_JWT_SECRET,
                    String.valueOf(manageUser.getId()), 7200L);

            resultJson.setData(token);
            //登录成功后删除验证码
            localCache.removeCache(SYSTEM_SMS_MANAGE_LOGIN_CODE_PHONE + "86" + manageUser.getMobile());
            return resultJson;
        } else {
            //密码错误
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.PASS_ERROR);
            return resultJson;
        }

    }


    @ApiOperation(value = "王斑：系统管理用户注册", notes = "注册成功与失败，带账号重复提示")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultJson<String> add(SysManageUserAddDTO addDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<String> resultJson = new ResultJson<>();
        //对象为空
        if (isInfoNull(resultJson, addDTO)) {
            return resultJson;
        }
        String account = addDTO.getAccount();
        //账号为空
        if (isInfoEmpty(resultJson, account, MessageKey.ACCOUNT_NULL)) {
            return resultJson;
        }
        String pass = addDTO.getPass();
        //密码为空
        if (isInfoEmpty(resultJson, pass, MessageKey.PASS_NULL)) {
            return resultJson;
        }
        String mobile = addDTO.getMobile();
        //手机号为空
        if (isInfoEmpty(resultJson, mobile, MessageKey.PHONE_NULL)) {
            return resultJson;
        }
        String realName = addDTO.getRealName();
        //姓名为空
        if (isInfoEmpty(resultJson, realName, MessageKey.REALNAME_NULL)) {
            return resultJson;
        }

        SysManageUser sysManageUser = sysManageUserService.getSysMangeUserMessageByAccount(account);

        //验证account是否重复
        if (sysManageUser != null) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.USER_EXISTS_ERROR);
            return resultJson;
        }
        //验证手机号是否重复
        if (sysManageUserService.getMobileInfo(mobile)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.PHONE_EXIST);
            return resultJson;
        }

        SysManageUser manageUser = new SysManageUser();
        manageUser.setAccount(addDTO.getAccount());
        manageUser.setRealName(addDTO.getRealName());
        manageUser.setMobile(addDTO.getMobile());
        manageUser.setLocked(false);
        //加密处理
        String password = addDTO.getPass();
        String salt = MD5Utils.getSalt();
        manageUser.setPass(MD5Utils.md5Salt(password, salt));
        manageUser.setSalt(salt);

        //保存系统管理用户信息
        try {
            if (sysManageUserService.saveSysManageUser(manageUser)) {
                //保存成功
                resultJson.setCode(ResultStatus.OK.getCode());
                resultJson.setMessage(MessageKey.ACCOUNT_ADD_SUCCESS);
                return resultJson;
            } else {
                //保存失败
                resultJson.setCode(ResultStatus.ERROR.getCode());
                resultJson.setMessage(MessageKey.ACCOUNT_ADD_FAILURE);
                return resultJson;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            resultJson.setStatus(ResultStatus.ERROR);
            resultJson.setMessage(MessageKey.ACCOUNT_ADD_FAILURE);
            return resultJson;
        }
    }

    @ApiOperation(value = "王斑：获取系统管理用户信息",
            notes = "{\n" +
                    "  \"code\": 200,\n" +
                    "  \"message\": \"OK\",\n" +
                    "  \"data\": {\n" +
                    "    \"id\": ID,\n" +
                    "    \"account\": \"登录名\",\n" +
                    "    \"realName\": \"真实名\",\n" +
                    "    \"locked\": 是否锁定,\n" +
                    "    \"createTime\": 创建时间,\n" +
                    "    \"roleName\": 角色名,\n" +
                    "    \"updateTime\": 更新时间,\n" +
                    "    \"mobile\": \"手机号\"\n" +
                    "  }\n" +
                    "}")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResultJson<SysManageUserVO> getSysManageUser(Integer id) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<SysManageUserVO> resultJson = new ResultJson<>();
        //id参数有误
        if (isIdEmpty(resultJson, id, MessageKey.ID_ERROR)) {
            return resultJson;
        }
        //根据id获取系统用户视图对象
        SysManageUserVO manageUserVO = sysManageUserService.getSysMangeUserVOMessageById(id);
        //用户不存在
        if (manageUserVO == null) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.ACCOUNT_ERROR);
            return resultJson;
        }
        resultJson.setStatus(ResultStatus.OK);
        resultJson.setData(manageUserVO);
        return resultJson;

    }

    @ApiOperation(value = "王斑：获取系统管理用户列表",
            notes = "{\n" +
                    "  \"code\": 200,\n" +
                    "  \"message\": \"OK\",\n" +
                    "  \"data\": {\n" +
                    "    \"count\": 总数量,\n" +
                    "    \"dataList\": [\n" +
                    "      {\n" +
                    "        \"id\": 1,\n" +
                    "        \"account\": \"登录名\",\n" +
                    "        \"realName\": \"真实名\",\n" +
                    "        \"locked\": 是否被锁定,\n" +
                    "        \"createTime\": 创建时间,\n" +
                    "        \"roleName\": \"角色名\",\n" +
                    "        \"updateTime\": 更新时间,\n" +
                    "        \"mobile\": \"手机号\"\n" +
                    "      }" +
                    "    ]\n" +
                    "  }\n" +
                    "}")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultJson<ResultList<SysManageUserVO>> listSysManageUser(PageParamDTO pageParamsDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<ResultList<SysManageUserVO>> resultJson = new ResultJson<>();
        //对象为空
        if (isInfoNull(resultJson, pageParamsDTO)) {
            return resultJson;
        }

        int count = sysManageUserService.getSysMangeUserCount();
        //获取用户总数量
        if (count <= 0) {
            resultJson.setCode(ResultStatus.OK.getCode());
            resultJson.setMessage(MessageKey.ACCOUNT_ERROR);
            return resultJson;
        }
        List<SysManageUserVO> sysManageUserList = sysManageUserService.listSysMangeUserMessage(pageParamsDTO);
        //用户列表为空
        if (CollectionUtils.isEmpty(sysManageUserList)) {
            resultJson.setCode(ResultStatus.OK.getCode());
            resultJson.setMessage(MessageKey.ACCOUNT_ERROR);
            return resultJson;
        }
        //设置分页参数
        ResultList<SysManageUserVO> resultList = new ResultList<>();
        resultList.setCount(count);
        resultList.setDataList(sysManageUserList);
        //设置回传值
        resultJson.setStatus(ResultStatus.OK);
        resultJson.setData(resultList);
        return resultJson;
    }

    @ApiOperation(value = "王斑：更新用户信息", notes = "更新成功与失败信息提示")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson<String> updateSysManageUser(SysManageUserUpdateDTO userUpdateDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<String> resultJson = new ResultJson<>();
        //对象为空
        if (isInfoNull(resultJson, userUpdateDTO)) {
            return resultJson;
        }
        Integer id = userUpdateDTO.getId();
        if (isIdEmpty(resultJson, id, MessageKey.ID_ERROR)) {
            return resultJson;
        }
        //更新系统管理用户信息
        if (sysManageUserService.updateManageUser(userUpdateDTO)) {
            //更新成功
            resultJson.setCode(ResultStatus.OK.getCode());
            resultJson.setMessage(MessageKey.USER_UPDATE_SUCCESS);
            return resultJson;
        } else {
            //更新失败
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.USER_UPDATE_FAILURE);
            return resultJson;
        }

    }

    @ApiOperation(value = "王斑：锁定用户", notes = "锁定用户结果")
    @RequestMapping(value = "/lock", method = RequestMethod.POST)
    public ResultJson<String> lockSysManageUser(SysManageUserLockDTO userLockDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<String> resultJson = new ResultJson<>();
        //对象为空
        if (isInfoNull(resultJson, userLockDTO)) {
            return resultJson;
        }
        Integer id = userLockDTO.getId();
        if (isIdEmpty(resultJson, id, MessageKey.ID_ERROR)) {
            return resultJson;
        }
        //判断是否为空，并给出默认值
        boolean lock = userLockDTO.getLocked() == null ? false : userLockDTO.getLocked();
        userLockDTO.setLocked(lock);
        //更新系统管理用户锁定信息
        if (sysManageUserService.lockManageUser(userLockDTO)) {
            //用户已解锁或已锁定
            resultJson.setCode(ResultStatus.OK.getCode());
            //传来的值为true则锁定成功，为false锁定失败
            resultJson.setMessage(lock ? MessageKey.USER_LOCKED : MessageKey.USER_UNLOCK);
            return resultJson;
        } else {
            //锁定失败
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.USER_LOCK_ERROR);
            return resultJson;
        }
    }

    @ApiOperation(value = "王斑：更改用户密码", notes = "更改密码成功与失败")
    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
    public ResultJson<String> updateSysManageUserPassword(SysManageUserChangePwdConvertDTO pwdDTO) {
        Integer userId = getCurrentManageUserId();
        if (userId == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<String> resultJson = new ResultJson<>();
        //对象为空
        if (isInfoNull(resultJson, pwdDTO)) {
            return resultJson;
        }
        Integer id = userId;
        if (isIdEmpty(resultJson, id, MessageKey.ID_ERROR)) {
            return resultJson;
        }

        //验证码是否为空
        String code = pwdDTO.getCode();
        if (isInfoEmpty(resultJson, code, MessageKey.CODE_NULL)) {
            return resultJson;
        }

        //验证密码
        SysManageUser sysManageUser = sysManageUserService.getSysMangeUserMessageById(id);
        //用户不存在
        if (sysManageUser == null) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.ACCOUNT_ERROR);
            return resultJson;
        }

        //验证验证码
        if (getCode(resultJson, SYSTEM_SMS_MANAGE_MODIFY_CODE_PHONE, code, sysManageUser.getMobile())) {
            return resultJson;
        }
        //加密处理
        SysManageUserChangePwdConvertDTO convertDTO = new SysManageUserChangePwdConvertDTO();
        String password = pwdDTO.getPass();
        String salt = MD5Utils.getSalt();
        convertDTO.setPass(MD5Utils.md5Salt(password, salt));
        convertDTO.setSalt(salt);
        convertDTO.setId(id);
        if (sysManageUserService.changeManageUserPwd(convertDTO)) {
            //更新密码成功
            resultJson.setCode(ResultStatus.OK.getCode());
            resultJson.setMessage(MessageKey.PASS_UPDATE_SUCCESS);
            //登录成功后删除验证码
            deleteCode(SYSTEM_SMS_MANAGE_LOGIN_CODE_PHONE, "86", sysManageUser.getMobile());
            return resultJson;
        } else {
            //更新密码失败
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.PASS_UPDATE_ERROR);
            return resultJson;
        }
    }

    @ApiOperation(value = "王斑：查询手机号是否存在", notes = "查询手机号是否存在，存在返回200")
    @RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
    public ResultJson<String> checkMobile(SysManageSmsMessageDTO smsMessageDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<String> resultJson = new ResultJson<>();
        //对象为空
        if (isInfoNull(resultJson, smsMessageDTO)) {
            return resultJson;
        }

        String mobile = smsMessageDTO.getMobile();
        //手机号为空
        if (isInfoEmpty(resultJson, mobile, MessageKey.PHONE_NULL)) {
            return resultJson;
        }
        //验证手机号是否存在
        if (sysManageUserService.getMobileInfo(mobile)) {
            //存在,手机号已经注册，不可注册
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.PHONE_EXIST);
            return resultJson;
        } else {
            //不存在
            resultJson.setCode(ResultStatus.OK.getCode());
            resultJson.setMessage(MessageKey.PHONE_ERROR);
            return resultJson;
        }

    }

    @ApiOperation(value = "王斑：查询用户是否存在", notes = "查询用户是否存在，存在返回200")
    @RequestMapping(value = "/checkAccount", method = RequestMethod.POST)
    public ResultJson<String> checkAccount(String account) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<String> resultJson = new ResultJson<>();
        //手机号为空
        if (isInfoEmpty(resultJson, account, MessageKey.ACCOUNT_NULL)) {
            return resultJson;
        }
        //验证手机号是否存在
        if (sysManageUserService.getSysMangeUserMessageByAccount(account) == null) {
            //不存在此用户
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.USER_NOT_EXISTS_ERROR);
            return resultJson;
        } else {
            //用户已存在
            resultJson.setCode(ResultStatus.OK.getCode());
            resultJson.setMessage(MessageKey.USER_EXISTS_ERROR);
            return resultJson;
        }

    }

    @ApiOperation(value = "王斑：根据用户查询有效的手机号码",
            notes = "{\n" +
                    "  \"code\": 200,\n" +
                    "  \"message\": \"OK\",\n" +
                    "  \"data\": \"手机号\"\n" +
                    "}")
    @RequestMapping(value = "/getMobile", method = RequestMethod.GET)
    public ResultJson<String> getMobile(String account) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<String> resultJson = new ResultJson<>();
        //手机号为空
        if (isInfoEmpty(resultJson, account, MessageKey.ACCOUNT_NULL)) {
            return resultJson;
        }
        //验证手机号是否存在
        SysManageUser sysManageUser = sysManageUserService.getSysMangeUserMessageByAccount(account);
        if (sysManageUser == null) {
            //不存在此用户
            resultJson.setMessage(MessageKey.USER_NOT_EXISTS_ERROR);
            resultJson.setCode(ResultStatus.ERROR.getCode());
            return resultJson;
        } else {
            resultJson.setStatus(ResultStatus.OK);
            resultJson.setData(sysManageUser.getMobile());
            return resultJson;
        }

    }

    @ApiOperation(value = "王斑：根据用户id查询有效的手机号码",
            notes = "{\n" +
                    "  \"code\": 200,\n" +
                    "  \"message\": \"OK\",\n" +
                    "  \"data\": \"手机号\"\n" +
                    "}")
    @RequestMapping(value = "/getMobileById", method = RequestMethod.GET)
    public ResultJson<String> getMobileById(Integer id) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson<String> resultJson = new ResultJson<>();
        //手机号为空
        if (isIdEmpty(resultJson, id, MessageKey.ID_ERROR)) {
            return resultJson;
        }
        //验证手机号是否存在
        SysManageUser sysManageUser = sysManageUserService.getSysMangeUserMessageById(id);
        if (sysManageUser == null) {
            //不存在此用户
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.USER_NOT_EXISTS_ERROR);
            return resultJson;
        } else {
            resultJson.setData(sysManageUser.getMobile());
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }

    }

}
