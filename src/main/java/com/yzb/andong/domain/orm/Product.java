package com.yzb.andong.domain.orm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("产品")
public class Product {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("唯一标示")
    private String ssid;
    @ApiModelProperty("用户 id")
    private Integer userId;
    @ApiModelProperty("详情")
    private String detail;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("是否可用")
    private Boolean useable;
    @ApiModelProperty("产品类型")
    private Integer typeId;
    @ApiModelProperty("创建时间")
    private Integer createTime;
    @ApiModelProperty("更新时间")
    private Integer updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Boolean getUseable() {
        return useable;
    }

    public void setUseable(Boolean useable) {
        this.useable = useable;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", ssid='" + ssid + '\'' +
                ", userId=" + userId +
                ", detail='" + detail + '\'' +
                ", remark='" + remark + '\'' +
                ", useable=" + useable +
                ", typeId=" + typeId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
