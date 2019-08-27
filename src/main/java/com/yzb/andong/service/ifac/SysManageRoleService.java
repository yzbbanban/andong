package com.yzb.andong.service.ifac;

import com.yzb.andong.domain.dto.SysManageRoleDTO;
import com.yzb.andong.domain.dto.SysManageRoleStateDTO;
import com.yzb.andong.domain.dto.SysManageRoleUpdateDTO;
import com.yzb.andong.domain.orm.PageParamDTO;
import com.yzb.andong.domain.orm.SysManageRole;

import java.util.List;

/**
 * 系统管理角色业务
 *
 * @author wangban
 * @date 11:12 2018/7/30
 */
public interface SysManageRoleService {
    /**
     * 保存系统管理角色
     *
     * @param sysManageRoleDTO 系统管理角色
     * @return true保存成功
     */
    boolean saveSysManageRole(SysManageRoleDTO sysManageRoleDTO);

    /**
     * 更新系统管理角色状态
     *
     * @param sysManageRoleStateDTO 系统角色状态
     * @return true更新成功
     */
    boolean updateSysManageRoleState(SysManageRoleStateDTO sysManageRoleStateDTO);

    /**
     * 更新系统管理角色信息
     *
     * @param sysManageRoleUpdateDTO 系统管理角色信息
     * @return true更新成功
     */
    boolean updateSysManageRole(SysManageRoleUpdateDTO sysManageRoleUpdateDTO);

    /**
     * 获取所有系统管理角色信息
     *
     * @param pageParamsDTO 分页信息
     * @return 系统管理角色列表
     */
    List<SysManageRole> listSysManageRole(PageParamDTO pageParamsDTO);

    /**
     * 获取系统管理角色数量
     *
     * @return 角色数量
     */
    int getSysMangeRoleCount();
}
