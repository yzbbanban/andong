package com.yzb.andong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/9/15
 */
@ApiModel("url")
public class GroupUrlDTO {
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("登录用户")
    private String sysUserId;
    @ApiModelProperty("组别")
    private String group;
    @ApiModelProperty("员工看板")
    private String staffUrl;
    @ApiModelProperty("客户看板")
    private String customUrl;
    @ApiModelProperty("线别 id")
    private String xid;
    @ApiModelProperty("备注")
    private String remark;

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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    @Override
    public String toString() {
        return "GroupUrl{" +
                "id=" + id +
                ", sysUserId='" + sysUserId + '\'' +
                ", group='" + group + '\'' +
                ", staffUrl='" + staffUrl + '\'' +
                ", customUrl='" + customUrl + '\'' +
                ", xid='" + xid + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
