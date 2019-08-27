package com.yzb.andong.service.impl;

import com.yzb.andong.config.util.PageParamUtil;
import com.yzb.andong.dao.SysManageRoleDao;
import com.yzb.andong.domain.dto.SysManageRoleDTO;
import com.yzb.andong.domain.dto.SysManageRoleStateDTO;
import com.yzb.andong.domain.dto.SysManageRoleUpdateDTO;
import com.yzb.andong.domain.orm.PageParamDTO;
import com.yzb.andong.domain.orm.SysManageRole;
import com.yzb.andong.service.ifac.SysManageRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统管理角色业务
 *
 * @author wangban
 * @date 14:48 2018/7/30
 */
@Service
public class SysManageRoleServiceImpl implements SysManageRoleService {

    @Autowired
    private SysManageRoleDao sysManageRoleDao;

    /**
     * 保存系统管理角色
     *
     * @param sysManageRoleDTO 系统管理角色
     * @return true保存成功
     */
    @Override
    public boolean saveSysManageRole(SysManageRoleDTO sysManageRoleDTO) {
        return sysManageRoleDao.saveSysManageRole(sysManageRoleDTO) > 0;
    }

    /**
     * 更新系统管理角色状态
     *
     * @param sysManageRoleStateDTO 系统角色状态
     * @return true更新成功
     */
    @Override
    public boolean updateSysManageRoleState(SysManageRoleStateDTO sysManageRoleStateDTO) {
        return sysManageRoleDao.updateSysManageRoleState(sysManageRoleStateDTO) > 0;
    }

    /**
     * 更新系统管理角色信息
     *
     * @param sysManageRoleUpdateDTO 系统管理角色信息
     * @return true更新成功
     */
    @Override
    public boolean updateSysManageRole(SysManageRoleUpdateDTO sysManageRoleUpdateDTO) {
        return sysManageRoleDao.updateSysManageRole(sysManageRoleUpdateDTO) > 0;
    }

    /**
     * 获取所有系统管理角色信息
     *
     * @param pageParamsDTO 分页信息
     * @return 系统管理角色列表
     */
    @Override
    public List<SysManageRole> listSysManageRole(PageParamDTO pageParamsDTO) {

        return sysManageRoleDao.listSysManageRole(PageParamUtil.setPageParam(pageParamsDTO));
    }

    /**
     * 获取系统角色数量
     *
     * @return 角色数量
     */
    @Override
    public int getSysMangeRoleCount() {
        return sysManageRoleDao.getSysManageRoleCount();
    }

}
