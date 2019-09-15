package com.yzb.andong.service.impl;

import com.yzb.andong.dao.GroupUrlDao;
import com.yzb.andong.domain.orm.GroupUrl;
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

    @Override
    public List<GroupUrl> getGroupUrlInfo(Integer sysUserId) {
        return groupUrlDao.getBaseUrl(sysUserId);
    }
}
