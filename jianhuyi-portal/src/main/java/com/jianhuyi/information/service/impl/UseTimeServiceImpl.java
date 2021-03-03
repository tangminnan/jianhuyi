package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.UseTimeDao;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.domain.UseTimeDO;
import com.jianhuyi.information.service.UseTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UseTimeServiceImpl implements UseTimeService {
  @Autowired private UseTimeDao useTimeDao;

  @Override
  public UseTimeDO get(Long id) {
    return useTimeDao.get(id);
  }

  @Override
  public List<UseTimeDO> list(Map<String, Object> map) {
    return useTimeDao.list(map);
  }

  @Override
  public int count(Map<String, Object> map) {
    return useTimeDao.count(map);
  }

  @Override
  public int save(UseTimeDO useTime) {
    return useTimeDao.save(useTime);
  }

  @Override
  public int update(UseTimeDO useTime) {
    return useTimeDao.update(useTime);
  }

  @Override
  public int remove(Long id) {
    return useTimeDao.remove(id);
  }

  @Override
  public int batchRemove(Long[] ids) {
    return useTimeDao.batchRemove(ids);
  }

  @Override
  public void saveList(List<UseTimeDO> useTimeDOList) {
    useTimeDao.saveList(useTimeDOList);
  }

  @Override
  public UseJianhuyiLogDO getTodayUse(Long userId) {
    return useTimeDao.getTodayUse(userId);
  }

  @Override
  public Map getSNCount(Long userId, String time) {
    return useTimeDao.getSNCount(userId, time);
  }

  @Override
  public List<UseTimeDO> getTodayData(Long userId, String time) {
    return useTimeDao.getTodayData(userId, time);
  }
}
