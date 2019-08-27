package com.yzb.andong.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/12
 */
@ApiModel("平台用户")
public class SysManageUserVO {
    @ApiModelProperty(
            value = "字典ID"
    )
    private Integer id;
    @ApiModelProperty("登录账号")
    private String account;
    @ApiModelProperty("姓名")
    private String realName;
    @ApiModelProperty("账号锁定(0锁定；1未锁定)")
    private Boolean locked;
    @ApiModelProperty("创建时间")
    private Long createTime;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty("修改时间")
    private Long updateTime;
    @ApiModelProperty("手机号码")
    private String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "SysManageUserVO{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", realName='" + realName + '\'' +
                ", locked=" + locked +
                ", createTime=" + createTime +
                ", roleName='" + roleName + '\'' +
                ", updateTime=" + updateTime +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}