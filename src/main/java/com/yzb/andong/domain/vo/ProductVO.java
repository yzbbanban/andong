package com.yzb.andong.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("产品")
public class ProductVO {
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
    @ApiModelProperty("产品名称")
    private String name;
    @ApiModelProperty("产品介绍")
    private String explain;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Override
    public String toString() {
        return "ProductVO{" +
                "id=" + id +
                ", ssid='" + ssid + '\'' +
                ", userId=" + userId +
                ", detail='" + detail + '\'' +
                ", remark='" + remark + '\'' +
                ", useable=" + useable +
                ", typeId=" + typeId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", name='" + name + '\'' +
                ", explain='" + explain + '\'' +
                '}';
    }
}
