package com.yzb.andong.service.ifac;

import com.yzb.andong.domain.dto.*;
import com.yzb.andong.domain.orm.PageParamDTO;
import com.yzb.andong.domain.vo.ProductVO;
import com.yzb.andong.domain.vo.TypeDetailVO;

import java.util.List;

/**
 * Created by brander on 2019/2/5
 */
public interface ProductService {

    /**
     * 添加产品
     *
     * @param addDTO 产品信息
     * @return true 成功
     */
    boolean addProduct(ProductAddDTO addDTO);

    /**
     * 更新产品
     *
     * @param dto 产品信息
     * @return true 成功
     */
    boolean updateProduct(ProductUpdateDTO dto);

    /**
     * 添加产品类型
     *
     * @param dto 产品类型信息
     * @return true 成功
     */
    boolean addProductType(TypeDetailAddDTO dto);

    /**
     * 更新产品类型
     *
     * @param dto 产品信息
     * @return true 成功
     */
    boolean updateProductType(TypeDetailUpdateDTO dto);


    /**
     * 更新产品可用性
     *
     * @param dto 产品是否可用
     * @return true 更新成功
     */
    boolean updateProductUseable(ProductUseableDTO dto);

    /**
     * 获取产品类型信息
     *
     * @param dto 分页信息
     * @return 产品类型列表
     */
    List<TypeDetailVO> listProductType(PageParamDTO dto);

    /**
     * 获取产品信息
     *
     * @param dto 分页信息
     * @return 产品信息列表
     */
    List<ProductVO> listProduct(ProductSearchDTO dto);

    /**
     * 获取产品数量
     *
     * @return 数量
     */
    int getProductCount();

    /**
     * 获取产品类型数量
     *
     * @return 数量
     */
    int getProductTypeCount();

    /**
     * 获取产品唯一编码
     *
     * @param ssid
     * @return
     */
    int getProUidCount(String ssid);

    void saveImg(String imgStr, String channelId);

}
