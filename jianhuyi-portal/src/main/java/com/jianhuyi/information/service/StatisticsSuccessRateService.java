package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.StatisticsSuccessRateDO;

import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-09-23 09:20:06
 */
public interface StatisticsSuccessRateService {

    StatisticsSuccessRateDO get(Integer id);

    List<StatisticsSuccessRateDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(StatisticsSuccessRateDO statisticsSuccessRate);

    int update(StatisticsSuccessRateDO statisticsSuccessRate);

    int remove(Integer id);

    int batchRemove(Integer[] ids);

    int saveList(List<StatisticsSuccessRateDO> statisticsSuccessRateDOList);
}
