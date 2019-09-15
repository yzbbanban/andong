package com.yzb.andong.service.ifac;

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
}
