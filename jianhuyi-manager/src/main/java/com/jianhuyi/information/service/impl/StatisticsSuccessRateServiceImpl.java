package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.StatisticsSuccessRateDao;
import com.jianhuyi.information.domain.StatisticsSuccessRateDO;
import com.jianhuyi.information.service.StatisticsSuccessRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class StatisticsSuccessRateServiceImpl implements StatisticsSuccessRateService {
    @Autowired
    private StatisticsSuccessRateDao statisticsSuccessRateDao;

    @Override
    public StatisticsSuccessRateDO get(Integer id) {
        return statisticsSuccessRateDao.get(id);
    }

    @Override
    public List<StatisticsSuccessRateDO> list(Map<String, Object> map) {
        return statisticsSuccessRateDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return statisticsSuccessRateDao.count(map);
    }

    @Override
    public List<StatisticsSuccessRateDO> detaillist(Map<String, Object> map) {
        return statisticsSuccessRateDao.detaillist(map);
    }

    @Override
    public int detailcount(Map<String, Object> map) {
        return statisticsSuccessRateDao.detailcount(map);
    }

    @Override
    public int save(StatisticsSuccessRateDO statisticsSuccessRate) {
        return statisticsSuccessRateDao.save(statisticsSuccessRate);
    }

    @Override
    public int update(StatisticsSuccessRateDO statisticsSuccessRate) {
        return statisticsSuccessRateDao.update(statisticsSuccessRate);
    }

    @Override
    public int remove(Integer id) {
        return statisticsSuccessRateDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return statisticsSuccessRateDao.batchRemove(ids);
    }

}
