package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.UserTaskLinshiDO;

import java.util.List;
import java.util.Map;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-08-21 11:33:27
 */
public interface UserTaskLinshiService {

    UserTaskLinshiDO get(Long id);

    List<UserTaskLinshiDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserTaskLinshiDO userTaskLinshi);

    int update(UserTaskLinshiDO userTaskLinshi);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<UserTaskLinshiDO> getTotalScore(Long id);

    UserTaskLinshiDO getRecentlyDate(Long userId,Long taskId);

    void updateCurrentDay(UserTaskLinshiDO userTaskLinshiDO);

    void updateScore(UserTaskLinshiDO userTaskLinshiDO);


}
