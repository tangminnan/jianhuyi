package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.ErrorDataDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-03-27 14:52:34
 */
public interface ErrorDataService {
	
	ErrorDataDO get(Long id);
	
	List<ErrorDataDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ErrorDataDO errorData);
	
	int update(ErrorDataDO errorData);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
