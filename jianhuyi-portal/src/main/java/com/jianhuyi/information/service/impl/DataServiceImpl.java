package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.DataDao;
import com.jianhuyi.information.domain.DataDO;
import com.jianhuyi.information.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DataServiceImpl implements DataService {
  @Autowired private DataDao dataDao;

  @Override
  public DataDO get(Long id) {
    return dataDao.get(id);
  }

  @Override
  public List<DataDO> list(Map<String, Object> map) {
    return dataDao.list(map);
  }

  @Override
  public int count(Map<String, Object> map) {
    return dataDao.count(map);
  }

  @Override
  public int save(DataDO data) {
    return dataDao.save(data);
  }

  @Override
  public int update(DataDO data) {
    return dataDao.update(data);
  }

  @Override
  public int remove(Long id) {
    return dataDao.remove(id);
  }

  @Override
  public int batchRemove(Long[] ids) {
    return dataDao.batchRemove(ids);
  }

  @Override
  public int saveList(List<DataDO> dataDOList) {
    return dataDao.saveList(dataDOList);
  }

  @Override
  public List<DataDO> getList(Long userId, String time) {
    return dataDao.getList(userId, time);
  }

  @Override
  public List<DataDO> getAllList(Long userId, Date startDate, Date endDate) {
    return dataDao.getAllList(userId,startDate,endDate);
  }
}
