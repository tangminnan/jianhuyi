package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.DataInitDao;
import com.jianhuyi.information.domain.DataInitDO;
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
  public DataInitDO get(Integer id) {
    return dataInitDao.get(id);
  }

  @Override
  public List<DataInitDO> list(Map<String, Object> map) {
    return dataInitDao.list(map);
  }

  @Override
  public int count(Map<String, Object> map) {
    return dataInitDao.count(map);
  }

  @Override
  public int save(DataInitDO dataInit) {
    return dataInitDao.save(dataInit);
  }

  @Override
  public int update(DataInitDO dataInit) {
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
  public List<DistanceDO> listDistanceDO(Map<String, Object> parmasMap) {
    return dataInitDao.listDistanceDO(parmasMap);
  }
}
