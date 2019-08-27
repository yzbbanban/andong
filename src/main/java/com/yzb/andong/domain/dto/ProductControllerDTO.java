package com.yzb.andong.domain.dto;

import java.io.Serializable;

/**
 * Created by brander on 2019/8/15
 */
public class ProductControllerDTO implements Serializable {

    private String ssid;

    private String order;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "ProductControllerDTO{" +
                "ssid='" + ssid + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
