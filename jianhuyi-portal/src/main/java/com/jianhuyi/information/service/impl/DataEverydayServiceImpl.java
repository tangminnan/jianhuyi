package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.DataEverydayDao;
import com.jianhuyi.information.domain.DataEverydayDO;
import com.jianhuyi.information.service.DataEverydayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataEverydayServiceImpl implements DataEverydayService {
  @Autowired private DataEverydayDao dataEverydayDao;

  @Override
  public DataEverydayDO get(Integer id) {
    return dataEverydayDao.get(id);
  }

  @Override
  public List<DataEverydayDO> list(Map<String, Object> map) {
    return dataEverydayDao.list(map);
  }

  @Override
  public int count(Map<String, Object> map) {
    return dataEverydayDao.count(map);
  }

  @Override
  public int save(DataEverydayDO dataEveryday) {
    return dataEverydayDao.save(dataEveryday);
  }

  @Override
  public int update(DataEverydayDO dataEveryday) {
    return dataEverydayDao.update(dataEveryday);
  }

  @Override
  public int remove(Integer id) {
    return dataEverydayDao.remove(id);
  }

  @Override
  public int batchRemove(Integer[] ids) {
    return dataEverydayDao.batchRemove(ids);
  }

  @Override
  public int saveList(List<DataEverydayDO> everydayDOList) {
    return dataEverydayDao.saveList(everydayDOList);
  }

  @Override
  public DataEverydayDO getByUserIdAndTime(Long userId, String date) {
    return dataEverydayDao.getByUserIdAndTime(userId,date);
  }
}
