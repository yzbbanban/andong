package com.yzb.andong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/2/4
 */
@ApiModel("产品添加 dto")
public class ProductAddDTO {
    @ApiModelProperty("唯一标示")
    private String ssid;
    @ApiModelProperty("详情")
    private String detail;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("产品类型")
    private Integer typeId;

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
        return "ProductAddDTO{" +
                "ssid='" + ssid + '\'' +
                ", detail='" + detail + '\'' +
                ", remark='" + remark + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
