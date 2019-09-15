package com.yzb.andong.controller;

import com.yzb.andong.config.ResultJson;
import com.yzb.andong.domain.temp.Line;
import com.yzb.andong.domain.temp.ResultJ;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Created by brander on 2019/9/14
 */
@RestController
@RequestMapping("public/api/index/")
@Api(tags = {"testapi"})
public class Test {


    @ApiOperation(value = "dad")
    @GetMapping(value = "au")
    public ResultJson<String> loginSms() {

        String mess = "sss";
        System.out.println("======>" + mess);

        return ResultJson.createBySuccess(mess);
    }


    @ApiOperation(value = "12")
    @GetMapping(value = "line")
    public ResultJ<List<Line>> line() throws InvocationTargetException, IllegalAccessException {

        ResultJ resultJ = new ResultJ();
        List<Line> lines=new ArrayList<>();
        Line line=new Line();
        line.setBanzhang("admin");
        line.setId(1);
        line.setName("线别 1");
        line.setNum(21312);
        line.setShuizhizhu(121);
        line.setUnit("OXO");

        lines.add(line);

        Line line2=new Line();

        BeanUtils.copyProperties(line2,line);
        line2.setId(2);
        line2.setBanzhang("adwad");
        line.setName("线别 2");

        lines.add(line2);

        resultJ.setCode(1);
        resultJ.setData(lines);
        return resultJ;
    }

}
