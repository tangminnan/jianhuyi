package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.GiftDO;

import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:12
 */
public interface GiftService {

    GiftDO get(Long id);

    List<GiftDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(GiftDO gift);

    int update(GiftDO gift);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
