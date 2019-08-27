package com.yzb.andong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/2/4
 */
@ApiModel("产品更新 dto")
public class ProductUpdateDTO {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("用户 id")
    private Integer userId;
    @ApiModelProperty("唯一标示")
    private String ssid;
    @ApiModelProperty("详情")
    private String detail;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("产品类型")
    private Integer typeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "ProductAppUpdateDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", ssid='" + ssid + '\'' +
                ", detail='" + detail + '\'' +
                ", remark='" + remark + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
