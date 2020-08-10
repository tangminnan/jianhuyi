package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.EnergysDataDao;
import com.jianhuyi.information.domain.EnergysDataDO;
import com.jianhuyi.information.service.EnergysDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class EnergysDataServiceImpl implements EnergysDataService {
    @Autowired
    private EnergysDataDao energysDataDao;

    @Override
    public EnergysDataDO get(Long id) {
        return energysDataDao.get(id);
    }

    @Override
    public List<EnergysDataDO> list(Map<String, Object> map) {
        return energysDataDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return energysDataDao.count(map);
    }

    @Override
    public int save(EnergysDataDO energysData) {
        return energysDataDao.save(energysData);
    }

    @Override
    public int update(EnergysDataDO energysData) {
        return energysDataDao.update(energysData);
    }

    @Override
    public int remove(Long id) {
        return energysDataDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return energysDataDao.batchRemove(ids);
    }

    @Override
    public int saveList(List<EnergysDataDO> energysDataDOList) {
        return energysDataDao.saveList(energysDataDOList);
    }

}
