package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.ModelartApiDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-04-07 11:03:48
 */
@Mapper
public interface ModelartApiDao {

	ModelartApiDO get(Long id);
	
	List<ModelartApiDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ModelartApiDO modelartApi);
	
	int update(ModelartApiDO modelartApi);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
