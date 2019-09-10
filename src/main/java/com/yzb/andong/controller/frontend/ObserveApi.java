package com.yzb.andong.controller.frontend;

import com.yzb.andong.config.ResultJson;
import com.yzb.andong.domain.dto.SmsMessageDTO;
import com.yzb.andong.domain.orm.ObservePath;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangban
 * @data 2019/9/10 9:15
 */
@RestController
@RequestMapping("v1/display")
@Api(tags = {"app展屏相关"})
public class ObserveApi {

    @ApiOperation(value = "获取展屏地址url")
    @GetMapping(value = "url")
    public ResultJson<ObservePath> getUrl(SmsMessageDTO messageDTO) {
        ObservePath path = new ObservePath();
        path.setCustomUrl("http://www.yzbbanban.com:8087/#/index");
        path.setStaffUrl("http://www.yzbbanban.com:8087/#/emIndex");
        return ResultJson.createBySuccess(path);
    }

}
