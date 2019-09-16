package com.yzb.andong.service.ifac;

import com.yzb.andong.domain.dto.GroupUrlDTO;
import com.yzb.andong.domain.dto.GroupUrlSearchDTO;
import com.yzb.andong.domain.orm.GroupUrl;

import java.util.List;

/**
 * Created by brander on 2019/2/6
 */
public interface GroupUrlService {


    /**
     * 根据手机号获取用户信息
     *
     * @param sysUserId 用户信息
     * @return url信息
     */
    List<GroupUrl> getGroupUrlInfo(Integer sysUserId);

    /**
     * Count
     *
     * @param dto dto
     * @return Count
     */
    int getGroupUrlCount(GroupUrlSearchDTO dto);

    /**
     * List
     *
     * @param dto dto
     * @return List
     */
    List<GroupUrl> getGroupUrlList(GroupUrlSearchDTO dto);

    /**
     * 更新
     *
     * @param groupUrlDTO groupUrlDTO
     * @return 成功
     */
    boolean updateGroupUrl(GroupUrlDTO groupUrlDTO);

    /**
     * add
     *
     * @param groupUrlDTO groupUrlDTO
     * @return 成功
     */
    boolean addGroupUrl(GroupUrlDTO groupUrlDTO);
}
