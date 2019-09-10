package com.yzb.andong.domain.orm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("展屏地址信息")
public class ObservePath {

    @ApiModelProperty("客户展示地址")
    private String customUrl;

    @ApiModelProperty("员工展示地址")
    private String staffUrl;

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public String getStaffUrl() {
        return staffUrl;
    }

    public void setStaffUrl(String staffUrl) {
        this.staffUrl = staffUrl;
    }

    @Override
    public String toString() {
        return "ObservePath{" +
                "customUrl='" + customUrl + '\'' +
                ", staffUrl='" + staffUrl + '\'' +
                '}';
    }
}
