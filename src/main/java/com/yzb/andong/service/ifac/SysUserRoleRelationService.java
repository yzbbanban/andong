package com.yzb.andong.service.ifac;


import com.yzb.andong.domain.orm.SysUserRoleRelation;

/**
 * 用户与角色关系
 *
 * @author wangban
 * @date 20:09 2018/7/31
 */
public interface SysUserRoleRelationService {
    /**
     * 保存用户与角色
     *
     * @param sysUserRoleRelation 用户与角色信息
     * @return 保存结果 true成功
     */
    boolean saveUserRoleRelation(SysUserRoleRelation sysUserRoleRelation);

    /**
     * 更新用户与角色
     *
     * @param sysUserRoleRelation 用户与角色信息
     * @return 保存结果 true成功
     */
    boolean updateUserRoleRelation(SysUserRoleRelation sysUserRoleRelation);

    /**
     * 根据系统用户id获取角色id
     *
     * @param sysUserId 系统用户id
     * @return 保存结果 true成功
     */
    Integer getRoleIdByUserId(Integer sysUserId);
}
