package com.yzb.andong.dao;

import com.yzb.andong.domain.dto.TypeDetailAddDTO;
import com.yzb.andong.domain.dto.TypeDetailUpdateDTO;
import com.yzb.andong.domain.orm.PageParamDTO;
import com.yzb.andong.domain.vo.TypeDetailVO;

import java.util.List;

/**
 * Created by brander on 2019/2/5
 */
public interface TypeDetailDao {
    /**
     * 保存产品信息
     *
     * @param dto 产品信息
     * @return >0 保存成功
     */
    int saveTypeDetail(TypeDetailAddDTO dto);

    /**
     * 更新产品信息
     *
     * @param dto 更新信息
     * @return >0成功
     */
    int updateTypeDetail(TypeDetailUpdateDTO dto);

    /**
     * 产品信息列表
     *
     * @return 返回产品信息
     */
    List<TypeDetailVO> listTypeDetail(PageParamDTO dto);

    /**
     * 获取产品数量
     *
     * @return 产品数量
     */
    int getTypeDetailCount();
}
