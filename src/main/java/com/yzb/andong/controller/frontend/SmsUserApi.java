package com.yzb.andong.controller.frontend;

import com.yzb.andong.config.ResultJson;
import com.yzb.andong.controller.BaseApi;
import com.yzb.andong.domain.dto.SmsMessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yzb.andong.domain.orm.MessageConstant.SYSTEM_SMS_MODIFY_CODE_PHONE;
import static com.yzb.andong.domain.orm.MessageConstant.SYSTEM_SMS_REGISTER_CODE_PHONE;

/**
 * Created by brander on 2019/2/3
 */
@RestController
@RequestMapping("v1/app/sms")
@Api(tags = {"app短信api"})
public class SmsUserApi extends BaseApi {


    @ApiOperation(value = "发送获取登录短信")
    @PostMapping(value = "register")
    public ResultJson<String> registerSms(SmsMessageDTO messageDTO) {

        return getStringResultJson(messageDTO, SYSTEM_SMS_REGISTER_CODE_PHONE + messageDTO.getCountryCode() + messageDTO.getPhoneNumber());
    }


    @ApiOperation(value = "发送修改密码短信")
    @PostMapping(value = "modify")
    public ResultJson<String> modifySms(SmsMessageDTO messageDTO) {

        return getStringResultJson(messageDTO, SYSTEM_SMS_MODIFY_CODE_PHONE + messageDTO.getCountryCode() + messageDTO.getPhoneNumber());
    }

}
