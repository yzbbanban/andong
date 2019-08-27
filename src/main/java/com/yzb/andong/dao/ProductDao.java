package com.yzb.andong.dao;

import com.yzb.andong.domain.dto.*;
import com.yzb.andong.domain.vo.ProductVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by brander on 2019/2/4
 */
@Transactional
public interface ProductDao {
    /**
     * 保存产品信息
     *
     * @param dto 产品信息
     * @return >0 保存成功
     */
    int saveProduct(ProductAddDTO dto);

    /**
     * 更新产品信息
     *
     * @param dto 更新信息
     * @return >0成功
     */
    int updateProduct(ProductUpdateDTO dto);

    /**
     * 更新产品状态
     *
     * @param dto 产品状态信息
     * @return >0成功
     */
    int updateProductUseable(ProductUseableDTO dto);

    /**
     * 产品信息列表
     *
     * @return 返回产品信息
     */
    List<ProductVO> listProduct(ProductSearchDTO dto);

    /**
     * 获取产品数量
     *
     * @return 产品数量
     */
    int getProductCount();

    /**
     * 获取产品唯一编码
     * @param ssid
     * @return
     */
    int getProUidCount(String ssid);

    /**
     * 获取产品id
     * @param ssid
     * @return
     */
    String queryProIdBySid(String ssid);

    void saveImgUrl(ProductImgAddDTO dto);

    void saveProMessage(ProductImgAddDTO dto);
    /**
     * delete image
     *
     * @param time time
     * @return effect row
     */
    int deleteImg(Date time);
}
