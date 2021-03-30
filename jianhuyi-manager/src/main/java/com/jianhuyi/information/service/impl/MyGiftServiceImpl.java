package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.MyGiftDao;
import com.jianhuyi.information.domain.MyGiftDO;
import com.jianhuyi.information.service.MyGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




@Service
public class MyGiftServiceImpl implements MyGiftService {
	@Autowired
	private MyGiftDao myGiftDao;
	
	@Override
	public MyGiftDO get(Long id){
		return myGiftDao.get(id);
	}
	
	@Override
	public List<MyGiftDO> list(Map<String, Object> map){
		return myGiftDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return myGiftDao.count(map);
	}



	@Override
	public int update(MyGiftDO myGift){
		return myGiftDao.update(myGift);
	}
	

	
}
