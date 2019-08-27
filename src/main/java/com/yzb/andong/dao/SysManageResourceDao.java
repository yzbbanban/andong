package com.yzb.andong.dao;

import com.yzb.andong.domain.dto.SysManageResourceAddChildrenDTO;
import com.yzb.andong.domain.dto.SysManageResourceAddParentDTO;
import com.yzb.andong.domain.dto.SysManageResourceUpdateDTO;
import com.yzb.andong.domain.dto.SysManageResourceUpdateStateDTO;
import com.yzb.andong.domain.orm.SysManageResource;

import java.util.List;

/**
 * 系统管理资源
 *
 * @author wangban
 */
public interface SysManageResourceDao {
    /**
     * 保存系统资源父节点信息
     *
     * @param resourceAddParentDTO 系统管理资源信息
     * @return 保存结果>0保存成功
     */
    int saveSysManageParentResource(SysManageResourceAddParentDTO resourceAddParentDTO);

    /**
     * 保存系统资源子节点信息
     *
     * @param resourceAddChildrenDTO 系统管理资源信息
     * @return 保存结果>0保存成功
     */
    int saveSysManageChildrenResource(SysManageResourceAddChildrenDTO resourceAddChildrenDTO);

    /**
     * 更新系统管理资源信息
     *
     * @param resourceUpdateDTO 系统管理资源信息
     * @return 更新结果>0更新成功
     */
    int updateSysManageResource(SysManageResourceUpdateDTO resourceUpdateDTO);

    /**
     * 更新系统管理资源信息状态
     *
     * @param updateStateDTO 系统资源状态
     * @return
     */
    int updateSysManageResourceState(SysManageResourceUpdateStateDTO updateStateDTO);

    /**
     * 根据id删除系统管理资源信息
     *
     * @param id 资源id
     * @return 删除结果>0删除成功
     */
    int deleteSysManageResource(Integer id);

    /**
     * 获取系统管理资源信息
     *
     * @param id 资源id
     * @return 系统管理资源信息
     */
    SysManageResource getSysManageResourceById(Integer id);

    /**
     * 获取所有系统管理资源信息
     *
     * @return 系统管理资源信息列表
     */
    List<SysManageResource> listSysManageResource();

    /**
     * 根据父节点获取系统管理资源信息
     *
     * @param parentId 父节点
     * @return 子节点
     */
    List<SysManageResource> listSysManageResourceById(Integer parentId);


}
