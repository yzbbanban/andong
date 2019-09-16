package com.yzb.andong.domain.dto;

import com.yzb.andong.domain.orm.PageParamDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/9/15
 */
@ApiModel("url")
public class GroupUrlSearchDTO extends PageParamDTO {
    @ApiModelProperty("登录用户")
    private String sysUserId;
    @ApiModelProperty("线别 id")
    private String xid;

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    @Override
    public String toString() {
        return "GroupUrlSearchDTO{" +
                super.toString() +
                "sysUserId='" + sysUserId + '\'' +
                ", xid='" + xid + '\'' +
                '}';
    }
}
