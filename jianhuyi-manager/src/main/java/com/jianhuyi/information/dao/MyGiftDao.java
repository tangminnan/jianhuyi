package com.jianhuyi.information.dao;


import java.util.List;
import java.util.Map;

import com.jianhuyi.information.domain.MyGiftDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-18 14:27:52
 */
@Mapper
public interface MyGiftDao {

	MyGiftDO get(Long id);
	
	List<MyGiftDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	

	
	int update(MyGiftDO myGift);
	

	

}
