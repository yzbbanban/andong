package com.yzb.andong.controller.backend;

import com.yzb.andong.config.MessageKey;
import com.yzb.andong.config.ResultJson;
import com.yzb.andong.config.ResultList;
import com.yzb.andong.config.util.OkHttpUtils;
import com.yzb.andong.controller.BaseApi;
import com.yzb.andong.domain.dto.GroupUrlDTO;
import com.yzb.andong.domain.orm.GroupUrl;
import com.yzb.andong.domain.orm.PageParamDTO;
import com.yzb.andong.domain.orm.SysManageUser;
import com.yzb.andong.service.ifac.GroupUrlService;
import com.yzb.andong.service.ifac.SysManageUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brander on 2019/9/15
 */
@RestController
@RequestMapping("v1/manage/display")
@RequiresAuthentication
@Api(tags = {"获取对应的数据信息"})
public class GroupUrlApi extends BaseApi {

    @Autowired
    private GroupUrlService groupUrlService;

    @Autowired
    private SysManageUserService userService;

    @ApiOperation(value = "分页获取所有的url数据")
    @GetMapping(value = "list")
    @RequiresAuthentication
    public ResultJson<ResultList<GroupUrl>> getList(PageParamDTO dto) {

        int count = groupUrlService.getGroupUrlCount(dto);
        List<GroupUrl> list = new ArrayList<>();
        if (count > 0) {
            list = groupUrlService.getGroupUrlList(dto);
        }
        return ResultJson.createList(count, list);
    }

    @ApiOperation(value = "编辑url数据")
    @PostMapping(value = "update")
    @RequiresAuthentication
    public ResultJson<String> update(GroupUrlDTO groupUrlDTO) {
        if (groupUrlService.updateGroupUrl(groupUrlDTO)) {
            return ResultJson.createBySuccess();
        }
        return ResultJson.createByError();
    }

    @ApiOperation(value = "增加url数据")
    @PostMapping(value = "add")
    @RequiresAuthentication
    public ResultJson<String> add(GroupUrlDTO groupUrlDTO) {
        int usrId = getCurrentManageUserId();
        if (usrId == -1) {
            return ResultJson.createByNoAuth();
        }
        SysManageUser user = userService.getSysMangeUserMessageById(usrId);
        if (user == null) {
            return ResultJson.createByErrorMsg(MessageKey.USER_NOT_EXISTS_ERROR);
        }
        groupUrlDTO.setSysUserId("" + usrId);
        if (groupUrlService.addGroupUrl(groupUrlDTO)) {
            return ResultJson.createBySuccess();
        }
        return ResultJson.createByError();

    }

    @ApiOperation(value = "删除url数据")
    @PostMapping(value = "del")
    @RequiresAuthentication
    public ResultJson<String> del(Integer id) {
        if (groupUrlService.delGroupUrl(id)) {
            return ResultJson.createBySuccess();
        }
        return ResultJson.createByError();

    }

    @ApiOperation(value = "获取产线信息")
    @GetMapping(value = "getLine")
    @RequiresAuthentication
    public ResultJson<String> getLine() {
        String res = OkHttpUtils.getRequest("http://47.106.181.1:7777/public/api/index/line");
        return ResultJson.createBySuccess(res);

    }
}
