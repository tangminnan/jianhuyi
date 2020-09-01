package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.GiftDao;
import com.jianhuyi.information.domain.GiftDO;
import com.jianhuyi.information.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class GiftServiceImpl implements GiftService {
    @Autowired
    private GiftDao giftDao;

    @Override
    public GiftDO get(Long id) {
        return giftDao.get(id);
    }

    @Override
    public List<GiftDO> list(Map<String, Object> map) {
        return giftDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return giftDao.count(map);
    }

    @Override
    public int save(GiftDO gift) {
        return giftDao.save(gift);
    }

    @Override
    public int update(GiftDO gift) {
        return giftDao.update(gift);
    }

    @Override
    public int remove(Long id) {
        return giftDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return giftDao.batchRemove(ids);
    }

}
