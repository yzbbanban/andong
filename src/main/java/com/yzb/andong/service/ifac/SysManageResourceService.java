package com.yzb.andong.service.ifac;


import com.yzb.andong.domain.dto.SysManageResourceAddChildrenDTO;
import com.yzb.andong.domain.dto.SysManageResourceAddParentDTO;
import com.yzb.andong.domain.dto.SysManageResourceUpdateDTO;
import com.yzb.andong.domain.dto.SysManageResourceUpdateStateDTO;
import net.sf.json.JSONObject;

/**
 * 系统管理资源业务
 *
 * @author wangban
 * @date 15:17 2018/7/30
 */
public interface SysManageResourceService {
    /**
     * 添加父节点系统管理资源
     *
     * @param resourceAddParentDTO 系统管理资源信息
     * @return 添加结果>0保存成功
     */
    boolean addSysManageParentResource(SysManageResourceAddParentDTO resourceAddParentDTO);

    /**
     * 添加子节点系统管理资源
     *
     * @param resourceAddChildrenDTO 系统管理资源信息
     * @return 添加结果>0保存成功
     */
    boolean addSysManageChildrenResource(SysManageResourceAddChildrenDTO resourceAddChildrenDTO);

    /**
     * 根据角色信息获取资源
     *
     * @param userId 用户id
     * @return 资源树
     */
    JSONObject listRoleTreeByRole(Integer userId);

    /**
     * 根据角色信息获取全部资源并选中哪些是角色有的资源
     *
     * @param roleId 角色id
     * @return 资源树
     */
    JSONObject listRoleTreeByRoleWithChecked(Integer roleId);

    /**
     * 更新系统管理资源
     *
     * @param resourceUpdateDTO 系统管理资源
     * @return 更新结果
     */
    boolean updateSysManageResource(SysManageResourceUpdateDTO resourceUpdateDTO);

    /**
     * 根据id删除系统管理资源
     *
     * @param id 资源id
     * @return 删除结果
     */
    boolean deleteSysManageSysResource(Integer id);

    /**
     * 根据id更新系统资源的可用状态
     *
     * @param updateStateDTO 系统资源状态
     * @return 更新成功或失败
     */
    boolean updateSysManageResourceState(SysManageResourceUpdateStateDTO updateStateDTO);

    /**
     * 获取资源树
     *
     * @return 资源树
     */
    JSONObject listRoleTree();


}
