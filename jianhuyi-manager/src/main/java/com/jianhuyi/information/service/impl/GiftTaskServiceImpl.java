package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.GiftTaskDao;
import com.jianhuyi.information.domain.GiftTaskDO;
import com.jianhuyi.information.service.GiftTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class GiftTaskServiceImpl implements GiftTaskService {
    @Autowired
    private GiftTaskDao giftTaskDao;

    @Override
    public GiftTaskDO get(Long id) {
        return giftTaskDao.get(id);
    }

    @Override
    public List<GiftTaskDO> list(Map<String, Object> map) {
        return giftTaskDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return giftTaskDao.count(map);
    }

    @Override
    public int save(GiftTaskDO giftTask) {
        return giftTaskDao.save(giftTask);
    }

    @Override
    public int update(GiftTaskDO giftTask) {
        return giftTaskDao.update(giftTask);
    }

    @Override
    public int remove(Long id) {
        return giftTaskDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return giftTaskDao.batchRemove(ids);
    }

}
