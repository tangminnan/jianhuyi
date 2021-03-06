package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.UserTaskDO;
import org.activiti.bpmn.model.UserTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;

import java.util.Date;
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

    List<UserTaskDO> getAllReadyFinishedTask(@Param("userId") Long userId,@Param("type") Integer type);

    UserTaskDO getCurrentTask(@Param("userId") Long userId,@Param("type") Integer type);

    UserTaskDO getCurrentTaskN(@Param("userId") Long userId,@Param("taskType") Integer taskType);

    UserTaskDO getCurrentTaskNT(@Param("pcorapp") String pcorapp,@Param("taskType") Integer taskType);

    List<UserTaskDO> getBatchRenwu(String pcorapp);

    List<UserTaskDO> getRenwuDetail(@Param("pcorapp") String pcorapp,@Param("startTime") Date startTime,@Param("taskTime") Integer taskTime);
}
