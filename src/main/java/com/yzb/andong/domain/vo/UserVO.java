package com.yzb.andong.domain.vo;

import com.yzb.andong.domain.orm.User;
import io.swagger.annotations.ApiModel;

@ApiModel("用户信息")
public class UserVO extends User {

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                '}';
    }
}
