package com.jianhuyi.information.dao;


import com.jianhuyi.information.domain.GiftTaskDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:13
 */
@Mapper
public interface GiftTaskDao {

    GiftTaskDO get(Long id);

    List<GiftTaskDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(GiftTaskDO giftTask);

    int update(GiftTaskDO giftTask);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
