package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.DataDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-03-25 14:23:41
 */
public interface DataService {
	
	DataDO get(Long id);
	
	List<DataDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DataDO data);
	
	int update(DataDO data);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    List<DataDO> listDataDO(Map<String, Object> parmasMap);
}
