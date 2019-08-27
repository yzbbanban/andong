package com.yzb.andong.service.ifac;


import com.yzb.andong.domain.dto.SysManageResourceRoleDTO;

/**
 * 系统资源与角色关系业务
 *
 * @author wangban
 * @date 15:44 2018/8/1
 */
public interface SysResourceRoleService {
    /**
     * 保存资源与角色的关系
     *
     * @param sysManageResourceRoleDTO 资源与角色的关系
     * @return 保存结果
     */
    boolean saveRoles(SysManageResourceRoleDTO sysManageResourceRoleDTO);

}
