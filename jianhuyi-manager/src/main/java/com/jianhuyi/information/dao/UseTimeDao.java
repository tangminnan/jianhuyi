package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.UseTimeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-07-22 09:43:53
 */
@Mapper
public interface UseTimeDao {

    UseTimeDO get(Long id);

    List<UseTimeDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UseTimeDO useTime);

    int update(UseTimeDO useTime);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
