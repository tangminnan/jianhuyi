package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.UseRemindsDao;
import com.jianhuyi.information.domain.UseRemindsDO;
import com.jianhuyi.information.service.UseRemindsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UseRemindsServiceImpl implements UseRemindsService {
    @Autowired
    private UseRemindsDao useRemindsDao;

    @Override
    public UseRemindsDO get(Long id) {
        return useRemindsDao.get(id);
    }

    @Override
    public List<UseRemindsDO> list(Map<String, Object> map) {
        return useRemindsDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return useRemindsDao.count(map);
    }

    @Override
    public int save(UseRemindsDO useReminds) {
        return useRemindsDao.save(useReminds);
    }

    @Override
    public int update(UseRemindsDO useReminds) {
        return useRemindsDao.update(useReminds);
    }

    @Override
    public int remove(Long id) {
        return useRemindsDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return useRemindsDao.batchRemove(ids);
    }

}
