package com.yzb.andong.service.impl;


import com.yzb.andong.dao.SysManageResourceDao;
import com.yzb.andong.dao.SysResourceRoleRelationDao;
import com.yzb.andong.dao.SysUserRoleRelationDao;
import com.yzb.andong.domain.dto.SysManageResourceAddChildrenDTO;
import com.yzb.andong.domain.dto.SysManageResourceAddParentDTO;
import com.yzb.andong.domain.dto.SysManageResourceUpdateDTO;
import com.yzb.andong.domain.dto.SysManageResourceUpdateStateDTO;
import com.yzb.andong.domain.orm.SysManageResource;
import com.yzb.andong.domain.orm.SysResourceRoleRelation;
import com.yzb.andong.service.ifac.SysManageResourceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统管理资源业务
 *
 * @author wangban
 * @date 15:17 2018/7/30
 */
@Service
public class SysManageResourceServiceImpl implements SysManageResourceService {

    @Autowired
    private SysManageResourceDao sysManageResourceDao;

    @Autowired
    private SysResourceRoleRelationDao resourceRoleRelationDao;

    @Autowired
    private SysUserRoleRelationDao sysUserRoleRelationDao;

    /**
     * 添加父节点系统管理资源
     *
     * @param resourceAddParentDTO 系统管理资源信息
     * @return 添加结果>0保存成功
     */
    @Override
    public boolean addSysManageParentResource(SysManageResourceAddParentDTO resourceAddParentDTO) {
        return sysManageResourceDao.saveSysManageParentResource(resourceAddParentDTO) > 0;
    }

    /**
     * 添加子节点系统管理资源
     *
     * @param resourceAddChildrenDTO 系统管理资源信息
     * @return 添加结果>0保存成功
     */
    @Override
    public boolean addSysManageChildrenResource(SysManageResourceAddChildrenDTO resourceAddChildrenDTO) {
        return sysManageResourceDao.saveSysManageChildrenResource(resourceAddChildrenDTO) > 0;
    }

    /**
     * 根据角色信息获取资源
     *
     * @param userId 用户id
     * @return 资源树
     */
    @Override
    public JSONObject listRoleTreeByRole(Integer userId) {
        JSONObject jsonObject = new JSONObject();
        Integer roleId = sysUserRoleRelationDao.getRoleIdByUserId(userId);
        if (roleId == null) {
            return null;
        }
        getMenuNodeForAuthTreeByRole(roleId, 0, jsonObject);
        return jsonObject;
    }

    /**
     * 根据角色信息获取全部资源并选中哪些是角色有的资源
     *
     * @param roleId 角色id
     * @return 资源树
     */
    @Override
    public JSONObject listRoleTreeByRoleWithChecked(Integer roleId) {
        JSONObject jsonObject = new JSONObject();
        getAllMenuNodeForCheckedAuthTree(roleId, 0, jsonObject);
        return jsonObject;
    }

    /**
     * 更新系统管理资源
     *
     * @param resourceUpdateDTO 系统管理资源
     * @return 更新结果
     */
    @Override
    public boolean updateSysManageResource(SysManageResourceUpdateDTO resourceUpdateDTO) {
        return sysManageResourceDao.updateSysManageResource(resourceUpdateDTO) > 0;
    }

    /**
     * 根据id删除系统资源
     *
     * @param id 资源id
     * @return 删除结果
     */
    @Override
    public boolean deleteSysManageSysResource(Integer id) {
        if (sysManageResourceDao.getSysManageResourceById(id) == null) {
            return false;
        }
        return sysManageResourceDao.deleteSysManageResource(id) > 0;
    }

    /**
     * 更新系统资源状态
     *
     * @param updateStateDTO 系统资源状态
     * @return 更新状态结果
     */
    @Override
    public boolean updateSysManageResourceState(SysManageResourceUpdateStateDTO updateStateDTO) {
        return sysManageResourceDao.updateSysManageResourceState(updateStateDTO) > 0;
    }

    /**
     * 获取资源树
     *
     * @return 资源树
     */
    @Override
    public JSONObject listRoleTree() {
        JSONObject jsonObject = new JSONObject();
        getMenuNodeForAuthTree(0, jsonObject);
        return jsonObject;
    }

    /**
     * 获取资源树
     *
     * @param parentId 父节点
     * @param root     资源树
     */
    private void getMenuNodeForAuthTree(Integer parentId, JSONObject root) {
        List<SysManageResource> menus = sysManageResourceDao.listSysManageResourceById(parentId);
        if (menus.size() > 0) {
            JSONArray children = new JSONArray();
            for (SysManageResource menu : menus) {
                JSONObject node = new JSONObject();
                // 根据角色是否具备此权限
                node.put("id", menu.getId());
                node.put("resName", menu.getResName());
                node.put("parentId", menu.getParentId());
                node.put("resKey", menu.getResKey());
                node.put("resType", menu.getResType());
                node.put("resUrl", menu.getResUrl());
                node.put("useable", menu.getUseable());
                node.put("sort", menu.getSort());
                node.put("createTime", menu.getCreateTime());
                node.put("updateTime", menu.getUpdateTime());
                getMenuNodeForAuthTree(menu.getId(), node);
                children.add(node);
            }
            root.put("children", children);
        } else {
            root.put("leaf", true);
        }
        return;
    }


    /**
     * 遍历去处所有菜单节点
     *
     * @param roleId   角色id
     * @param parentId 父节点
     * @param root     资源树
     */
    private void getMenuNodeForAuthTreeByRole(Integer roleId, Integer parentId, JSONObject root) {
        List<SysManageResource> menus = sysManageResourceDao.listSysManageResourceById(parentId);
        if (menus.size() > 0) {
            JSONArray children = new JSONArray();
            for (SysManageResource menu : menus) {
                JSONObject node = new JSONObject();
                SysResourceRoleRelation resourceRoleRelation = new SysResourceRoleRelation();
                // 根据角色是否具备此权限
                resourceRoleRelation.setSysRoleId(roleId);
                resourceRoleRelation.setSysResourceId(menu.getId());
                if (resourceRoleRelationDao.getResourceRole(resourceRoleRelation) > 0) {
                    node.put("id", menu.getId());
                    node.put("resName", menu.getResName());
                    node.put("parentId", menu.getParentId());
                    node.put("resKey", menu.getResKey());
                    node.put("resType", menu.getResType());
                    node.put("resUrl", menu.getResUrl());
                    node.put("useable", menu.getUseable());
                    node.put("sort", menu.getSort());
                    node.put("createTime", menu.getCreateTime());
                    node.put("updateTime", menu.getUpdateTime());
                    getMenuNodeForAuthTreeByRole(roleId, menu.getId(), node);
                    children.add(node);
                }
            }
            root.put("children", children);
        } else {
            root.put("leaf", true);
        }
        return;
    }

    /**
     * 遍历所有节点并选中已有的的节点
     *
     * @param roleId
     * @param parentId
     * @param root
     */
    private void getAllMenuNodeForCheckedAuthTree(Integer roleId, Integer parentId, JSONObject root) {
        List<SysManageResource> menus = sysManageResourceDao.listSysManageResourceById(parentId);
        if (menus.size() > 0) {
            JSONArray children = new JSONArray();
            for (SysManageResource menu : menus) {
                JSONObject node = new JSONObject();
                SysResourceRoleRelation resourceRoleRelation = new SysResourceRoleRelation();
                // 根据角色是否具备此权限
                resourceRoleRelation.setSysRoleId(roleId);
                resourceRoleRelation.setSysResourceId(menu.getId());
                // 根据角色是否具备此权限
                node.put("id", menu.getId());
                node.put("resName", menu.getResName());
                node.put("parentId", menu.getParentId());
                node.put("resKey", menu.getResKey());
                node.put("resType", menu.getResType());
                node.put("resUrl", menu.getResUrl());
                node.put("useable", menu.getUseable());
                node.put("sort", menu.getSort());
                node.put("createTime", menu.getCreateTime());
                node.put("updateTime", menu.getUpdateTime());
                if (resourceRoleRelationDao.getResourceRole(resourceRoleRelation) > 0) {
                    node.put("checked", true);
                } else {
                    node.put("checked", false);

                }
                getAllMenuNodeForCheckedAuthTree(roleId, menu.getId(), node);
                children.add(node);
            }
            root.put("children", children);
        } else {
            root.put("leaf", true);
        }
        return;
    }

}
