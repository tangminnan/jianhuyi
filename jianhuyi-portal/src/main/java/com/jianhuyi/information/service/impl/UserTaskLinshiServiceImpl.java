package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.UserTaskLinshiDao;
import com.jianhuyi.information.domain.UserTaskLinshiDO;
import com.jianhuyi.information.service.UserTaskLinshiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class UserTaskLinshiServiceImpl implements UserTaskLinshiService {
    @Autowired
    private UserTaskLinshiDao userTaskLinshiDao;

    @Override
    public UserTaskLinshiDO get(Long id) {
        return userTaskLinshiDao.get(id);
    }

    @Override
    public List<UserTaskLinshiDO> list(Map<String, Object> map) {
        return userTaskLinshiDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userTaskLinshiDao.count(map);
    }

    @Override
    public int save(UserTaskLinshiDO userTaskLinshi) {
        return userTaskLinshiDao.save(userTaskLinshi);
    }

    @Override
    public int update(UserTaskLinshiDO userTaskLinshi) {
        return userTaskLinshiDao.update(userTaskLinshi);
    }

    @Override
    public int remove(Long id) {
        return userTaskLinshiDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return userTaskLinshiDao.batchRemove(ids);
    }

    @Override
    public Integer getTotalScore(Long id) {
        return userTaskLinshiDao.getTotalScore(id);
    }

    @Override
    public UserTaskLinshiDO getRecentlyDate(Long userId,Long taskId) {
        return userTaskLinshiDao.getRecentlyDate(userId,taskId);
    }

    @Override
    public void updateCurrentDay(UserTaskLinshiDO userTaskLinshiDO) {
        userTaskLinshiDao.updateCurrentDay(userTaskLinshiDO);
    }

    @Override
    public void updateScore(UserTaskLinshiDO userTaskLinshiDO) {
        userTaskLinshiDao.updateScore(userTaskLinshiDO);
    }

}
