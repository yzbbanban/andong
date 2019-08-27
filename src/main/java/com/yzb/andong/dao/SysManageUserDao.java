package com.yzb.andong.dao;


import com.yzb.andong.domain.dto.SysManageUserChangePwdConvertDTO;
import com.yzb.andong.domain.dto.SysManageUserLockDTO;
import com.yzb.andong.domain.dto.SysManageUserUpdateDTO;
import com.yzb.andong.domain.orm.PageParamDTO;
import com.yzb.andong.domain.orm.SysManageUser;
import com.yzb.andong.domain.vo.SysManageUserVO;

import java.util.List;

/**
 * 系统管理用户操作
 *
 * @author wangban
 */
public interface SysManageUserDao {

    /**
     * 保存系统管理用户信息
     *
     * @param sysManageUser 系统管理用户信息
     * @return 保存结果>0保存成功
     */
    int saveSysManageUser(SysManageUser sysManageUser);

    /**
     * 更新系统管理用户信息
     *
     * @param sysManageUserUpdateDTO 系统管理用户信息
     * @return 更新结果>0更新成功
     */
    int updateSysManageUser(SysManageUserUpdateDTO sysManageUserUpdateDTO);

    /**
     * 更新系统管理用户锁定信息
     *
     * @param sysManageUserLockDTO 系统管理用户锁定信息
     * @return 更新结果>0更新成功
     */
    int updateSysManageUserLockInfo(SysManageUserLockDTO sysManageUserLockDTO);

    /**
     * 删除系统管理用户信息
     *
     * @param id 系统管理用户id
     * @return 删除结果>0删除成功
     */
    int removeSysManageUser(Integer id);

    /**
     * 根据系统管理用户id获取系统管理用户信息
     *
     * @param id 系统管理用户id
     * @return 系统管理用户
     */
    SysManageUser getSysManageUserById(Integer id);

    /**
     * 根据系统管理用户id获取系统管理用户视图信息
     *
     * @param id 系统管理用户id
     * @return 系统管理用户
     */
    SysManageUserVO getSysManageUserVOById(Integer id);


    /**
     * 根据登录名获取系统用户信息
     *
     * @param account 登录名
     * @return 系统用户信息
     */
    SysManageUser getSysManageUserByAccount(String account);

    /**
     * 获取系统管理用户数量
     *
     * @return 用户数量
     */
    Integer getSysManageUserCount();

    /**
     * 验证用户是否存在
     *
     * @param mobile 手机号
     * @return 结果信息>0则存在
     */
    int getSysManageUserByPhone(String mobile);


    /**
     * 获取用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    SysManageUser getSysManageUser(String mobile);

    /**
     * 分页获取所有系统管理用户信息
     *
     * @param pageParamsDTO 页码信息
     * @return 系统管理用户集合
     */
    List<SysManageUserVO> listSysManageUser(PageParamDTO pageParamsDTO);

    /**
     * 更新用户名密码
     *
     * @param userChangePwdConvertDTO 用户名密码信息
     * @return 更新结果>0更新成功
     */
    int updateSysManageUserPwd(SysManageUserChangePwdConvertDTO userChangePwdConvertDTO);

}
