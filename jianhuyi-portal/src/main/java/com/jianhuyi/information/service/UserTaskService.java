package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.UserTaskDO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:13
 */
public interface UserTaskService {

    UserTaskDO get(Long id);

    List<UserTaskDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserTaskDO userTask);

    int update(UserTaskDO userTask);

    int remove(Long id);

    int batchRemove(Long[] ids);

    UserTaskDO getRecentlyTask(Long userId);

    List<UserTaskDO> getAllReadyFinishedTask(Long userId,Integer type);

    UserTaskDO getCurrentTask(Long userId,Integer type);

    UserTaskDO getCurrentTaskN(Long userId,Integer taskType);

    UserTaskDO getCurrentTaskNT(String pcorapp,Integer  taskType);

    List<UserTaskDO> getBatchRenwu(String pcorapp);

    List<UserTaskDO> getRenwuDetail(String pcorapp, Date startTime, Integer taskTime);
}
