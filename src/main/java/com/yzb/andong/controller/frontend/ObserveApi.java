package com.yzb.andong.controller.frontend;

import com.yzb.andong.config.ResultJson;
import com.yzb.andong.controller.BaseApi;
import com.yzb.andong.domain.dto.SmsMessageDTO;
import com.yzb.andong.domain.orm.GroupUrl;
import com.yzb.andong.domain.orm.ObservePath;
import com.yzb.andong.service.ifac.GroupUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangban
 * @data 2019/9/10 9:15
 */
@RestController
@RequestMapping("v1/display")
@Api(tags = {"app展屏相关"})
public class ObserveApi extends BaseApi {


    @Autowired
    private GroupUrlService groupUrlService;

    @ApiOperation(value = "获取展屏地址url")
    @GetMapping(value = "url")
    @RequiresAuthentication
    public ResultJson<List<GroupUrl>> getUrl() {
        Integer sysUserId = getCurrentManageUserId();

        List<GroupUrl> groupUrls = groupUrlService.getGroupUrlInfo(sysUserId);

        return ResultJson.createBySuccess(groupUrls);
    }

}
