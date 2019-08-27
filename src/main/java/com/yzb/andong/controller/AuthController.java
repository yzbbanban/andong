package com.yzb.andong.controller;

import com.yzb.andong.config.ResultJson;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by brander on 2019/1/15
 */
@RestController
@Api(tags = {"token"})
public class AuthController {

    @RequestMapping(value = "401", method = RequestMethod.GET)
    public ResultJson<String> throw401() {
        return ResultJson.createByNoAuth();
    }

}
