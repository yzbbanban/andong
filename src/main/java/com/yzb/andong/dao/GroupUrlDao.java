package com.yzb.andong.dao;

import com.yzb.andong.domain.orm.GroupUrl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by brander on 2019/2/4
 */
@Transactional
public interface GroupUrlDao {

    List<GroupUrl> getBaseUrl(Integer sysUserId);

}
