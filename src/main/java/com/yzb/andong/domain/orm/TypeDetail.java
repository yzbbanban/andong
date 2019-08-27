package com.yzb.andong.domain.orm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("产品类型详情")
public class TypeDetail {
    @ApiModelProperty("产品 id")
    private long id;
    @ApiModelProperty("产品名称")
    private String name;
    @ApiModelProperty("产品介绍")
    private String explain;
    @ApiModelProperty("创建时间")
    private long createTime;
    @ApiModelProperty("更新时间")
    private long updateTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TypeDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", explain='" + explain + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
