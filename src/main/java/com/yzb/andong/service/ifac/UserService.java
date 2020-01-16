package com.yzb.andong.service.ifac;

import com.yzb.andong.domain.dto.UserAddDTO;
import com.yzb.andong.domain.dto.UserSearchDTO;
import com.yzb.andong.domain.dto.UserUpdateDTO;
import com.yzb.andong.domain.vo.UserVO;

import java.util.List;

/**
 * Created by brander on 2019/2/6
 */
public interface UserService {

    /**
     * 保存用户
     *
     * @param dto 用户信息
     * @return >0保存成功
     */
    boolean saveUser(UserAddDTO dto);

    /**
     * 获取用户
     *
     * @param id id
     * @return user
     */
    UserVO getUserInfo(Integer id);

    /**
     * 更新用户
     *
     * @param dto 用户信息
     * @return >0更新成功
     */
    boolean updateUser(UserUpdateDTO dto);

    /**
     * 获取用户列表
     *
     * @param dto 条件
     * @return 用户列表
     */
    List<UserVO> listUserInfo(UserSearchDTO dto);

    /**
     * 获取用户数量
     *
     * @param dto 条件
     * @return 用户数量
     */
    int getUserCount(UserSearchDTO dto);


    /**
     * 根据手机号获取用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    UserVO getUserInfo(String mobile, String countryCode);
}
