package com.yzb.andong.controller.backend;


import com.yzb.andong.config.MessageKey;
import com.yzb.andong.config.ResultJson;
import com.yzb.andong.config.ResultStatus;
import com.yzb.andong.controller.BaseApi;
import com.yzb.andong.domain.dto.SysManageResourceAddChildrenDTO;
import com.yzb.andong.domain.dto.SysManageResourceAddParentDTO;
import com.yzb.andong.domain.dto.SysManageResourceUpdateDTO;
import com.yzb.andong.domain.dto.SysManageResourceUpdateStateDTO;
import com.yzb.andong.service.ifac.SysManageResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理资源api
 *
 * @author wangban
 * @date 14:51 2018/7/30
 */
@RestController
@RequestMapping("/v1/manage/sysRes")
@Api(tags = {"manage系统管理资源api"})
@RequiresAuthentication
public class SysManageResouceApi extends BaseApi {
    /**
     * 系统管理资源服务
     */
    @Autowired
    private SysManageResourceService resourceService;

    @ApiOperation(value = "王斑：添加父节点系统管理资源", notes = "添加父节点信息")
    @RequestMapping(value = "/addParent", method = RequestMethod.POST)
    public ResultJson addSySManageParentResource(SysManageResourceAddParentDTO addParentDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();
        if (addParentDTO != null) {
            if (addParentDTO.getSort() == null) {
                addParentDTO.setSort(0);
            }
        }
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 有信息未填入 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(addParentDTO)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 保存父节点 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (resourceService.addSysManageParentResource(addParentDTO)) {
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 保存失败 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.ADD_PARENT_SYSTEM_RESOURCE_NODE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }


    @ApiOperation(value = "王斑：添加子节点系统管理资源", notes = "添加子节点信息")
    @RequestMapping(value = "/addChildren", method = RequestMethod.POST)
    public ResultJson addSySManageChildrenResource(SysManageResourceAddChildrenDTO resourceAddChildrenDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();
        if (resourceAddChildrenDTO != null) {
            if (resourceAddChildrenDTO.getSort() == null) {
                resourceAddChildrenDTO.setSort(0);
            }
        }
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 有信息未填入 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(resourceAddChildrenDTO)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 保存子节点 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (resourceService.addSysManageChildrenResource(resourceAddChildrenDTO)) {
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 保存失败 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.ADD_CHILDREN_SYSTEM_RESOURCE_NODE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }

    @ApiOperation(value = "王斑：更新节点信息", notes = "更新节点信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson updateSysManageRes(SysManageResourceUpdateDTO resourceUpdateDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 有信息未填入 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(resourceUpdateDTO)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新节点 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (resourceService.updateSysManageResource(resourceUpdateDTO)) {
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新失败 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.UPDATE_SYSTEM_RESOURCE_NODE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }

    @ApiOperation(value = "王斑：删除节点信息", notes = "删除结果")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultJson deleteSysManageRes(Integer id) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 有信息未填入 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (id == null) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.ID_ERROR);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新节点 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (resourceService.deleteSysManageSysResource(id)) {
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新失败 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.DELETE_SYSTEM_RESOURCE_NODE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }

    @ApiOperation(value = "王斑：更新节点信息是否可用", notes = "更新节点信息是否可用结果")
    @RequestMapping(value = "/useable", method = RequestMethod.POST)
    public ResultJson useableSysManageRes(SysManageResourceUpdateStateDTO updateStateDTO) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 有信息未填入 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (ObjectUtils.isEmpty(updateStateDTO)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新节点 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (resourceService.updateSysManageResourceState(updateStateDTO)) {
            resultJson.setStatus(ResultStatus.OK);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 更新失败 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.UPDATE_SYSTEM_RESOURCE_NODE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }

    @ApiOperation(value = "王斑：获取资源树",
            notes = "{\n" +
                    "    \"code\": 200,\n" +
                    "    \"message\": \"OK\",\n" +
                    "    \"data\": {\n" +
                    "        \"children\": [\n" +
                    "            {\n" +
                    "                \"id\": 1,\n" +
                    "                \"resName\": \"资源名称\",\n" +
                    "                \"parentId\":父节点id,\n" +
                    "                \"resKey\": \"资源key\",\n" +
                    "                \"resType\": 子节点为2，父节点为1,\n" +
                    "                \"resUrl\": \"图标\",\n" +
                    "                \"sort\": \"排序\",\n" +
                    "                \"children\": [\n" +
                    "                    {\n" +
                    "                        \"id\": 4,\n" +
                    "                        \"resName\": \"系统配置\",\n" +
                    "                        \"parentId\": 1,\n" +
                    "                        \"resKey\": \"系统配置\",\n" +
                    "                        \"resType\": 2,\n" +
                    "                        \"resUrl\": \"wrench\",\n" +
                    "                        \"sort\": \"排序\",\n" +
                    "                        \"leaf\": true\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultJson listRoleTree() {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 获取资源树 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        JSONObject jsonObject = resourceService.listRoleTree();
        if (jsonObject != null) {
            resultJson.setStatus(ResultStatus.OK);
            resultJson.setData(jsonObject);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 获取失败 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.GET_SYSTEM_RESOURCE_NODE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }

    @ApiOperation(value = "王斑：根据角色信息获取资源树",
            notes = "{\n" +
                    "    \"code\": 200,\n" +
                    "    \"message\": \"OK\",\n" +
                    "    \"data\": {\n" +
                    "        \"children\": [\n" +
                    "            {\n" +
                    "                \"id\": 1,\n" +
                    "                \"resName\": \"资源名称\",\n" +
                    "                \"parentId\": 父节点id,\n" +
                    "                \"resKey\": \"资源key\",\n" +
                    "                \"resType\": 子节点为2，父节点为1,\n" +
                    "                \"resUrl\": \"图标\",\n" +
                    "                \"children\": [\n" +
                    "                    {\n" +
                    "                        \"id\": 4,\n" +
                    "                        \"resName\": \"系统配置\",\n" +
                    "                        \"parentId\": 1,\n" +
                    "                        \"resKey\": \"系统配置\",\n" +
                    "                        \"resType\": 2,\n" +
                    "                        \"resUrl\": \"cog\",\n" +
                    "                        \"leaf\": true\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}")
    @RequestMapping(value = "/listByRole", method = RequestMethod.GET)
    public ResultJson listRoleTreeByRole() {
        Integer userId = getCurrentManageUserId();
        if (userId == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 根据角色获取资源树 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        JSONObject jsonObject = resourceService.listRoleTreeByRole(userId);
        if (jsonObject != null) {
            resultJson.setStatus(ResultStatus.OK);
            resultJson.setData(jsonObject);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 获取失败 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.GET_SYSTEM_RESOURCE_NODE_FAILURE_BY_ROLE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        return resultJson;
    }

    @ApiOperation(value = "王斑：获取所有资源树，若角色有此权限则checked为true",
            notes = "用于修改角色对应的资源，在所有的资源中，角色包含的资源的会置为true" +
                    "{\n" +
                    "    \"code\": 200,\n" +
                    "    \"message\": \"OK\",\n" +
                    "    \"data\": {\n" +
                    "        \"children\": [\n" +
                    "            {\n" +
                    "                \"id\": 1,\n" +
                    "                \"resName\": \"资源名称\",\n" +
                    "                \"parentId\": 父节点id,\n" +
                    "                \"resKey\": \"资源key\",\n" +
                    "                \"resType\":子节点为2，父节点为1,\n" +
                    "                \"resUrl\": \"图标\",\n" +
                    "                \"checked\": 该角色是否有此权限,\n" +
                    "                \"children\": [\n" +
                    "                    {\n" +
                    "                        \"id\": 4,\n" +
                    "                        \"resName\": \"系统配置\",\n" +
                    "                        \"parentId\": 1,\n" +
                    "                        \"resKey\": \"系统配置\",\n" +
                    "                        \"resType\": 2,\n" +
                    "                        \"resUrl\": \"wrench\",\n" +
                    "                        \"checked\": true,\n" +
                    "                        \"leaf\": true\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}")
    @RequestMapping(value = "/listRoleTree", method = RequestMethod.GET)
    public ResultJson listRoleWithAllTree(Integer roleId) {
        if (getCurrentManageUserId() == -1) {
            return ResultJson.createByNoAuth();
        }
        ResultJson resultJson = new ResultJson();
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ roleId是否为空 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        if (roleId == null) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 获取所有资源树，若角色有此权限则checked为true ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        JSONObject jsonObject = resourceService.listRoleTreeByRoleWithChecked(roleId);
        if (jsonObject != null) {
            resultJson.setStatus(ResultStatus.OK);
            resultJson.setData(jsonObject);
            return resultJson;
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        //获取失败
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.GET_SYSTEM_RESOURCE_NODE_FAILURE);
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
        return resultJson;
    }


}
