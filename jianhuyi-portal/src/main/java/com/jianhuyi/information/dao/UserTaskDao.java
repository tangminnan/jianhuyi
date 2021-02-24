package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.UserTaskDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-08-21 17:00:32
 */
@Mapper
public interface UserTaskDao {

    UserTaskDO get(Long id);

    List<UserTaskDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserTaskDO userTask);

    int update(UserTaskDO userTask);

    int remove(Long id);

    int batchRemove(Long[] ids);

    UserTaskDO getRecentlyTask(Long userId);

    List<UserTaskDO> getAllReadyFinishedTask(Long userId);

    UserTaskDO getCurrentTask(Long userId);

    UserTaskDO getCurrentTaskN(@Param("userId") Long userId,@Param("type") Integer type);
}
