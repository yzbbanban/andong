package com.yzb.andong.service.impl;

import com.yzb.andong.dao.SysResourceRoleRelationDao;
import com.yzb.andong.domain.dto.SysManageResourceRoleDTO;
import com.yzb.andong.service.ServiceException;
import com.yzb.andong.service.ifac.SysResourceRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统角色权限业务
 *
 * @author wangban
 * @date 19:25 2018/8/1
 */
@Service
public class SysResourceRoleServiceImpl implements SysResourceRoleService {
    /**
     * 系统资源与角色dao
     */
    @Autowired
    private SysResourceRoleRelationDao sysResourceRoleRelationDao;

    /**
     * 保存资源与角色的关系
     *
     * @param sysManageResourceRoleDTO 资源列表
     * @return 保存结果
     */
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public boolean saveRoles(SysManageResourceRoleDTO sysManageResourceRoleDTO) {
        Integer sysRoleId = sysManageResourceRoleDTO.getSysRoleId();

        //查询是否存在此角色的资源
        int r = sysResourceRoleRelationDao.getResourceRoleByRoleId(sysRoleId);
        if (r > 0) {
            //存在角色资源
            //删除对应信息
            int result = sysResourceRoleRelationDao.deleteRoleByRoleId(sysRoleId);

            //插入信息
            if (result > 0) {
                int row = sysResourceRoleRelationDao.saveResRoles(sysManageResourceRoleDTO);
                if (row > 0) {
                    return true;
                } else {
                    throw new ServiceException("失败");
                }
            } else {
                throw new ServiceException("失败");
            }
        } else {
            //不存在角色资源，插入资源
            int row = sysResourceRoleRelationDao.saveResRoles(sysManageResourceRoleDTO);
            if (row > 0) {
                return true;
            } else {
                throw new ServiceException("失败");
            }
        }

    }
}
