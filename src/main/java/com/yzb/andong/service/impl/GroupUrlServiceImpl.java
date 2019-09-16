package com.yzb.andong.service.impl;

import com.yzb.andong.config.util.PageParamUtil;
import com.yzb.andong.dao.GroupUrlDao;
import com.yzb.andong.domain.dto.GroupUrlDTO;
import com.yzb.andong.domain.dto.GroupUrlSearchDTO;
import com.yzb.andong.domain.orm.GroupUrl;
import com.yzb.andong.domain.orm.PageParamDTO;
import com.yzb.andong.service.ifac.GroupUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by brander on 2019/9/16
 */
@Service
public class GroupUrlServiceImpl implements GroupUrlService {

    @Autowired
    private GroupUrlDao groupUrlDao;

    /**
     * @param sysUserId 用户信息
     * @return
     */
    @Override
    public List<GroupUrl> getGroupUrlInfo(Integer sysUserId) {
        return groupUrlDao.getBaseUrl(sysUserId);
    }


    /**
     * 获取url count
     *
     * @param dto dto
     * @return count
     */
    @Override
    public int getGroupUrlCount(PageParamDTO dto) {
        return groupUrlDao.getGroupUrlCount(dto);
    }

    /**
     * 获取url list
     *
     * @param dto dto
     * @return list
     */
    @Override
    public List<GroupUrl> getGroupUrlList(PageParamDTO dto) {
        return groupUrlDao.getGroupUrlList(PageParamUtil.setPageParam(dto));
    }

    /**
     * 更新
     *
     * @param groupUrlDTO groupUrlDTO
     * @return 成功
     */
    @Override
    public boolean updateGroupUrl(GroupUrlDTO groupUrlDTO) {
        return groupUrlDao.updateGroupUrl(groupUrlDTO) > 0;
    }

    /**
     * add
     *
     * @param groupUrlDTO groupUrlDTO
     * @return 成功
     */
    @Override
    public boolean addGroupUrl(GroupUrlDTO groupUrlDTO) {
        return groupUrlDao.addGroupUrl(groupUrlDTO) > 0;
    }
}
