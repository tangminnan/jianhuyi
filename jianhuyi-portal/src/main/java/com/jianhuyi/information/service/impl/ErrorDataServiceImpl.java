package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.ErrorDataDao;
import com.jianhuyi.information.domain.ErrorDataDO;
import com.jianhuyi.information.service.ErrorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ErrorDataServiceImpl implements ErrorDataService {
    @Autowired
    private ErrorDataDao errorDataDao;

    @Override
    public ErrorDataDO get(Long id) {
        return errorDataDao.get(id);
    }

    @Override
    public List<ErrorDataDO> list(Map<String, Object> map) {
        return errorDataDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return errorDataDao.count(map);
    }

    @Override
    public int save(ErrorDataDO errorData) {
        return errorDataDao.save(errorData);
    }

    @Override
    public int update(ErrorDataDO errorData) {
        return errorDataDao.update(errorData);
    }

    @Override
    public int remove(Long id) {
        return errorDataDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return errorDataDao.batchRemove(ids);
    }

    @Override
    public int saveList(List<ErrorDataDO> errorDataDOList) {
        return 0;
    }

}
