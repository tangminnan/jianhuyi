package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.GiftPcDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-01-11 15:27:22
 */
public interface GiftPcService {
	
	GiftPcDO get(Integer id);
	
	List<GiftPcDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GiftPcDO giftPc);
	
	int update(GiftPcDO giftPc);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
