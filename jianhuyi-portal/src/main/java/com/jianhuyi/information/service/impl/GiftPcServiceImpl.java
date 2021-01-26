package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.GiftPcDao;
import com.jianhuyi.information.domain.GiftPcDO;
import com.jianhuyi.information.service.GiftPcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class GiftPcServiceImpl implements GiftPcService {
	@Autowired
	private GiftPcDao giftPcDao;
	
	@Override
	public GiftPcDO get(Integer id){
		return giftPcDao.get(id);
	}
	
	@Override
	public List<GiftPcDO> list(Map<String, Object> map){
		return giftPcDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return giftPcDao.count(map);
	}
	
	@Override
	public int save(GiftPcDO giftPc){
		return giftPcDao.save(giftPc);
	}
	
	@Override
	public int update(GiftPcDO giftPc){
		return giftPcDao.update(giftPc);
	}
	
	@Override
	public int remove(Integer id){
		return giftPcDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return giftPcDao.batchRemove(ids);
	}
	
}
