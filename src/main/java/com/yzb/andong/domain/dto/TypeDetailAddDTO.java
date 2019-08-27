package com.yzb.andong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("产品类型详情")
public class TypeDetailAddDTO {
    @ApiModelProperty("产品名称")
    private String name;
    @ApiModelProperty("产品介绍")
    private String explain;


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
        return "TypeDetailAddDTO{" +
                ", name='" + name + '\'' +
                ", explain='" + explain + '\'' +
                '}';
    }
}
