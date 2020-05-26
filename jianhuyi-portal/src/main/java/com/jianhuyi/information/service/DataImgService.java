package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.DataImgDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-25 09:40:42
 */
public interface DataImgService {
	
	DataImgDO get(Long id);
	
	List<DataImgDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DataImgDO dataImg);
	
	int update(DataImgDO dataImg);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
