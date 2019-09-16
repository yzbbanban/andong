package com.yzb.andong.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("GroupUrlVO")
public class GroupUrlVO {
    @ApiModelProperty("员工看板")
    private String staffUrl;
    @ApiModelProperty("客户看板")
    private String customUrl;
    @ApiModelProperty("线别 id")
    private String xid;

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

    @Override
    public String toString() {
        return "GroupUrlVO{" +
                "staffUrl='" + staffUrl + '\'' +
                ", customUrl='" + customUrl + '\'' +
                ", xid='" + xid + '\'' +
                '}';
    }
}
