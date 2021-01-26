package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.StatisticsSuccessRateDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-09-23 09:20:06
 */
@Mapper
public interface StatisticsSuccessRateDao {

    StatisticsSuccessRateDO get(Integer id);

    List<StatisticsSuccessRateDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(StatisticsSuccessRateDO statisticsSuccessRate);

    int update(StatisticsSuccessRateDO statisticsSuccessRate);

    int remove(Integer id);

    int batchRemove(Integer[] ids);

    List<StatisticsSuccessRateDO> detaillist(Map<String, Object> map);

    int detailcount(Map<String, Object> map);
}
