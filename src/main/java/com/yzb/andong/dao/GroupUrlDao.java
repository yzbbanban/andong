package com.yzb.andong.dao;

import com.yzb.andong.domain.dto.GroupUrlDTO;
import com.yzb.andong.domain.dto.GroupUrlSearchDTO;
import com.yzb.andong.domain.orm.GroupUrl;
import com.yzb.andong.domain.orm.PageParamDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by brander on 2019/2/4
 */
@Transactional
public interface GroupUrlDao {

    /**
     * 获取 url 信息
     *
     * @param sysUserId sysUserId
     * @return url 信息
     */
    List<GroupUrl> getBaseUrl(Integer sysUserId);

    /**
     * xx
     *
     * @param dto dto
     * @return count
     */
    int getGroupUrlCount(PageParamDTO dto);

    /**
     * xx
     *
     * @param pageParamDTO dto
     * @return list
     */
    List<GroupUrl> getGroupUrlList(PageParamDTO pageParamDTO);

    /**
     * updateGroupUrl
     *
     * @param groupUrlDTO groupUrlDTO
     * @return row
     */
    int updateGroupUrl(GroupUrlDTO groupUrlDTO);

    /**
     * addGroupUrl
     *
     * @param groupUrlDTO groupUrlDTO
     * @return row
     */
    int addGroupUrl(GroupUrlDTO groupUrlDTO);

    /**
     * groupUrlDTO
     *
     * @param groupUrlDTO groupUrlDTO
     * @return groupUrlDTO
     */
    int updateDelGroupUrl(GroupUrlDTO groupUrlDTO);
}
