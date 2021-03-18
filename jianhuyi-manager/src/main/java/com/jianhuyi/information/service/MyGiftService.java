package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.MyGiftDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-18 14:27:52
 */
public interface MyGiftService {
	
	MyGiftDO get(Long id);
	
	List<MyGiftDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);


	
	int update(MyGiftDO myGift);

}
