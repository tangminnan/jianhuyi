package com.jianhuyi.information.service;

import com.jianhuyi.information.domain.UserTaskDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-19 14:22:00
 */
public interface UserTaskService {
	
	UserTaskDO get(Long id);
	
	List<UserTaskDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserTaskDO userTask);
	
	int update(UserTaskDO userTask);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
