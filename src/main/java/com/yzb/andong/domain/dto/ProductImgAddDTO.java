package com.yzb.andong.domain.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api("产品图片信息dto")
public class ProductImgAddDTO {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("imageUrl")
    private String imageUrl;

    @ApiModelProperty("productId")
    private String productId;

    @ApiModelProperty("timestamp")
    private String timestamp;
    
    @ApiModelProperty("hasImage")
    private Integer hasImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getHasImage() {
        return hasImage;
    }

    public void setHasImage(Integer hasImage) {
        this.hasImage = hasImage;
    }

    
    public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
    public String toString() {
        return "ProductImgAddDTO{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", productId='" + productId + '\'' +
                ", hasImage=" + hasImage +
                '}';
    }
}
