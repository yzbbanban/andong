package com.yzb.andong.controller.backend;

import com.yzb.andong.config.MessageKey;
import com.yzb.andong.config.ResultJson;
import com.yzb.andong.controller.BaseApi;
import com.yzb.andong.domain.dto.SmsMessageDTO;
import com.yzb.andong.domain.orm.SysManageUser;
import com.yzb.andong.service.ifac.SysManageUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yzb.andong.domain.orm.MessageConstant.*;

@RestController
@RequestMapping("v1/manage/sms")
@Api(tags = {"manage短信api"})
public class SmsManageApi extends BaseApi {

    @Autowired
    private SysManageUserService manageUserService;


    @ApiOperation(value = "发送获取登录短信")
    @PostMapping(value = "login")
    public ResultJson<String> loginSms(String account) {
        SysManageUser user = manageUserService.getSysMangeUserMessageByAccount(account);
        if (user == null) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }
        SmsMessageDTO messageDTO = new SmsMessageDTO();
        messageDTO.setCountryCode("86");
        messageDTO.setPhoneNumber(user.getMobile());
        return getStringResultJson(messageDTO, SYSTEM_SMS_MANAGE_LOGIN_CODE_PHONE + messageDTO.getCountryCode() + messageDTO.getPhoneNumber());
    }

    @ApiOperation(value = "发送更改密码短信")
    @PostMapping(value = "modify")
    @RequiresAuthentication
    public ResultJson<String> getModifySms() {
        Integer userId = getCurrentManageUserId();
        if (userId == -1) {
            return ResultJson.createByNoAuth();
        }
        SysManageUser user = manageUserService.getSysMangeUserMessageById(userId);
        SmsMessageDTO messageDTO = new SmsMessageDTO();
        messageDTO.setCountryCode("86");
        messageDTO.setPhoneNumber(user.getMobile());
        return getStringResultJson(messageDTO, SYSTEM_SMS_MANAGE_MODIFY_CODE_PHONE + messageDTO.getCountryCode() + messageDTO.getPhoneNumber());
    }


}
