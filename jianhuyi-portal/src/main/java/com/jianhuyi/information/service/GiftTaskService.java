package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.GiftTaskDO;

import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:13
 */
public interface GiftTaskService {

    GiftTaskDO get(Long id);

    List<GiftTaskDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(GiftTaskDO giftTask);

    int update(GiftTaskDO giftTask);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
