package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.UserTaskDao;
import com.jianhuyi.information.domain.UserTaskDO;
import com.jianhuyi.information.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserTaskServiceImpl implements UserTaskService {
    @Autowired
    private UserTaskDao userTaskDao;

    @Override
    public UserTaskDO get(Long id) {
        return userTaskDao.get(id);
    }

    @Override
    public List<UserTaskDO> list(Map<String, Object> map) {
        return userTaskDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userTaskDao.count(map);
    }

    @Override
    public int save(UserTaskDO userTask) {
        return userTaskDao.save(userTask);
    }

    @Override
    public int update(UserTaskDO userTask) {
        return userTaskDao.update(userTask);
    }

    @Override
    public int remove(Long id) {
        return userTaskDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return userTaskDao.batchRemove(ids);
    }

    @Override
    public UserTaskDO getRecentlyTask(Long userId) {
       return userTaskDao.getRecentlyTask(userId);
    }

    @Override
    public List<UserTaskDO> getAllReadyFinishedTask(Long userId, Integer flag) {
        return userTaskDao.getAllReadyFinishedTask(userId,flag);
    }

}
