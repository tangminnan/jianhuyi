package com.jianhuyi.information.dao;


import java.util.List;
import java.util.Map;

import com.jianhuyi.information.domain.GiftPcDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-01-11 15:27:22
 */
@Mapper
public interface GiftPcDao {

	GiftPcDO get(Integer id);
	
	List<GiftPcDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GiftPcDO giftPc);
	
	int update(GiftPcDO giftPc);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
