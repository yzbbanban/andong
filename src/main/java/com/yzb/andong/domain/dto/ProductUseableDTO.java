package com.yzb.andong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/2/4
 */
@ApiModel("产品可用性 dto")
public class ProductUseableDTO {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("是否可用")
    private Boolean useable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getUseable() {
        return useable;
    }

    public void setUseable(Boolean useable) {
        this.useable = useable;
    }

    @Override
    public String toString() {
        return "ProductUseableDTO{" +
                "id=" + id +
                ", useable=" + useable +
                '}';
    }
}
