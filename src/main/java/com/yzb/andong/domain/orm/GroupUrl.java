package com.yzb.andong.domain.orm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/9/15
 */
@ApiModel("url")
public class GroupUrl {
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("登录用户")
    private String sysUserId;
    @ApiModelProperty("组别")
    private String urlGroup;
    @ApiModelProperty("员工看板")
    private String staffUrl;
    @ApiModelProperty("客户看板")
    private String customUrl;
    @ApiModelProperty("线别 id")
    private String xid;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("是否删除了")
    private Boolean delTag;
    @ApiModelProperty("创建时间")
    private Long createTime;
    @ApiModelProperty("更新时间")
    private Long updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getUrlGroup() {
        return urlGroup;
    }

    public void setUrlGroup(String urlGroup) {
        this.urlGroup = urlGroup;
    }

    public String getStaffUrl() {
        return staffUrl;
    }

    public void setStaffUrl(String staffUrl) {
        this.staffUrl = staffUrl;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getDelTag() {
        return delTag;
    }

    public void setDelTag(Boolean delTag) {
        this.delTag = delTag;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "GroupUrl{" +
                "id=" + id +
                ", sysUserId='" + sysUserId + '\'' +
                ", urlGroup='" + urlGroup + '\'' +
                ", staffUrl='" + staffUrl + '\'' +
                ", customUrl='" + customUrl + '\'' +
                ", xid='" + xid + '\'' +
                ", remark='" + remark + '\'' +
                ", delTag=" + delTag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
