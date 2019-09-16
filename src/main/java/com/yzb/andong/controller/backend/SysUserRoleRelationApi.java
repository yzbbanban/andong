package com.yzb.andong.controller.backend;

import com.yzb.andong.config.MessageKey;
import com.yzb.andong.config.ResultJson;
import com.yzb.andong.config.ResultStatus;
import com.yzb.andong.controller.BaseApi;
import com.yzb.andong.domain.orm.SysUserRoleRelation;
import com.yzb.andong.service.ifac.SysUserRoleRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理用户角色api
 *
 * @author wangban
 * @date 20:27 2018/7/31
 */
@RestController
@RequestMapping("/v1/manage/sysUserRole")
@Api(tags = {"manage系统管理用户与角色关系api"})
public class SysUserRoleRelationApi extends BaseApi {
    /**
     * 用户角色关系业务
     */
    @Autowired
    private SysUserRoleRelationService sysUserRoleRelationService;

    @ApiOperation(value = "王斑：保存用户与角色关系", notes = "添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresAuthentication
    public ResultJson addSysUserRole(SysUserRoleRelation sysUserRoleRelation) {
        ResultJson resultJson = new ResultJson();
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 检查是否有为传入的参数 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(sysUserRoleRelation)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 保存用户与角色关系 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (sysUserRoleRelationService.saveUserRoleRelation(sysUserRoleRelation)) {
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 保存用户与角色关系失败 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.ADD_MANAGE_USER_WITH_ROLE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }

    @ApiOperation(value = "王斑：更新用户与角色关系", notes = "更新结果信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresAuthentication
    public ResultJson updateSysUserRole(SysUserRoleRelation sysUserRoleRelation) {
        ResultJson resultJson = new ResultJson();
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 检查是否有为传入的参数 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(sysUserRoleRelation)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新用户与角色关系 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (sysUserRoleRelationService.updateUserRoleRelation(sysUserRoleRelation)) {
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新用户与角色关系失败 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.MODIFY_MANAGE_USER_WITH_ROLE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }

}
