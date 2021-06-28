package com.jianhuyi.information.service.impl;

import com.jianhuyi.common.utils.domain.HistoryDataBean;
import com.jianhuyi.information.dao.DataInitDao;
import com.jianhuyi.information.domain.DistanceDO;
import com.jianhuyi.information.service.DataInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataInitServiceImpl implements DataInitService {
  @Autowired private DataInitDao dataInitDao;

  @Override
  public HistoryDataBean get(Integer id) {
    return dataInitDao.get(id);
  }

  @Override
  public List<HistoryDataBean> list(Map<String, Object> map) {
    return dataInitDao.list(map);
  }

  @Override
  public int count(Map<String, Object> map) {
    return dataInitDao.count(map);
  }

  @Override
  public int save(HistoryDataBean dataInit) {
    return dataInitDao.save(dataInit);
  }

  @Override
  public int update(HistoryDataBean dataInit) {
    return dataInitDao.update(dataInit);
  }

  @Override
  public int remove(Integer id) {
    return dataInitDao.remove(id);
  }

  @Override
  public int batchRemove(Integer[] ids) {
    return dataInitDao.batchRemove(ids);
  }

  @Override
  public List<HistoryDataBean> getList(Long userId, String time) {
    return dataInitDao.getList(userId, time);
  }

  @Override
  public int saveDistanceDO(DistanceDO distanceDO) {
    return dataInitDao.saveDistanceDO(distanceDO);
  }
}
