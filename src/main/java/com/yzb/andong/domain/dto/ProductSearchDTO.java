package com.yzb.andong.domain.dto;

import com.yzb.andong.domain.orm.PageParamDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/2/7
 */
@ApiModel("产品列表条件")
public class ProductSearchDTO extends PageParamDTO {
    @ApiModelProperty("用户 id")
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProductSearchDTO{" +
                "userId=" + userId +
                '}';
    }
}
