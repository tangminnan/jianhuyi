package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.UserTaskLinshiDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-19 15:07:20
 */
@Mapper
public interface UserTaskLinshiDao {

	UserTaskLinshiDO get(Long id);
	
	List<UserTaskLinshiDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserTaskLinshiDO userTaskLinshi);
	
	int update(UserTaskLinshiDO userTaskLinshi);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    List<UserTaskLinshiDO> getById(Long id);
}
