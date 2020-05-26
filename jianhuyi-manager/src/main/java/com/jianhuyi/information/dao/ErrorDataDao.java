package com.jianhuyi.information.dao;


import com.jianhuyi.information.domain.ErrorDataDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-03-27 14:52:34
 */
@Mapper
public interface ErrorDataDao {

	ErrorDataDO get(Long id);
	
	List<ErrorDataDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ErrorDataDO errorData);
	
	int update(ErrorDataDO errorData);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
