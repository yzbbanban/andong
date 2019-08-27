package com.yzb.andong.controller.backend;

import com.yzb.andong.config.MessageKey;
import com.yzb.andong.config.ResultJson;
import com.yzb.andong.config.ResultList;
import com.yzb.andong.domain.dto.UserLockDTO;
import com.yzb.andong.domain.dto.UserSearchDTO;
import com.yzb.andong.domain.dto.UserUpdateDTO;
import com.yzb.andong.domain.vo.UserVO;
import com.yzb.andong.service.ifac.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by brander on 2019/2/5
 */
@RestController
@RequestMapping("/v1/manage/user")
@Api(tags = {"manage用户管理 api"})
public class UserManageApi {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "王斑：锁定用户", notes = "")
    @RequestMapping(value = "/lock", method = RequestMethod.POST)
    public ResultJson lockUser(UserLockDTO dto) {
        if (dto == null
                || dto.getUseable() == null
                || dto.getId() == null
                || dto.getId() <= 0) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        UserUpdateDTO updateDTO = new UserUpdateDTO();

        updateDTO.setId(dto.getId());
        updateDTO.setStatus(dto.getUseable());

        if (userService.updateUser(updateDTO)) {
            return ResultJson.createBySuccessMsg(MessageKey.USER_LOCKED);
        }

        return ResultJson.createByError();
    }


    @ApiOperation(value = "王斑：获取用户列表", notes = "")
    @GetMapping(value = "/list")
    public ResultJson listUser(UserSearchDTO dto) {

        if (dto == null
                || dto.getPageNo() == null
                || dto.getPageSize() == null) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }

        int count = userService.getUserCount(dto);

        if (count <= 0) {
            return ResultJson.createBySuccess();
        }

        List<UserVO> userList = userService.listUserInfo(dto);

        if (CollectionUtils.isEmpty(userList)) {
            return ResultJson.createBySuccess();
        }
        ResultList<UserVO> userVOResultList = new ResultList<>();
        userVOResultList.setCount(count);
        userVOResultList.setDataList(userList);

        return ResultJson.createBySuccess(userVOResultList);
    }


}
