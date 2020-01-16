package com.yzb.andong.dao;

import com.yzb.andong.domain.dto.UserAddDTO;
import com.yzb.andong.domain.dto.UserSearchDTO;
import com.yzb.andong.domain.dto.UserUpdateDTO;
import com.yzb.andong.domain.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by brander on 2019/2/6
 */
public interface UserDao {

    /**
     * 保存用户
     *
     * @param dto 用户信息
     * @return >0保存成功
     */
    int saveUser(UserAddDTO dto);

    /**
     * 更新用户
     *
     * @param dto 用户信息
     * @return >0更新成功
     */
    int updateUser(UserUpdateDTO dto);

    /**
     * 获取用户列表
     *
     * @param dto 用户信息
     * @return 用户列表
     */
    List<UserVO> listUserInfo(UserSearchDTO dto);

    /**
     * 用户列表数量
     *
     * @param dto 用户信息
     * @return 数量
     */
    int getUserCount(UserSearchDTO dto);

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    UserVO getUserInfo(@Param("mobile") String mobile, @Param("countryCode") String countryCode);

    /**
     * 获取用户
     *
     * @param id id
     * @return user
     */
    UserVO getUserById(Integer id);
}
