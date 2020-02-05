package com.jianhuyi.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianhuyi.information.dao.UseJianhuyiLogDao;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;

import java.util.List;
import java.util.Map;




@Service
public class UseJianhuyiLogServiceImpl implements UseJianhuyiLogService {
	@Autowired
	private UseJianhuyiLogDao useJianhuyiLogDao;
	
	@Override
	public UseJianhuyiLogDO get(Integer id){
		return useJianhuyiLogDao.get(id);
	}
	
	@Override
	public List<UseJianhuyiLogDO> list(Map<String, Object> map){
		return useJianhuyiLogDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return useJianhuyiLogDao.count(map);
	}
	
	@Override
	public int save(UseJianhuyiLogDO useJianhuyiLog){
		return useJianhuyiLogDao.save(useJianhuyiLog);
	}
	
	@Override
	public int update(UseJianhuyiLogDO useJianhuyiLog){
		return useJianhuyiLogDao.update(useJianhuyiLog);
	}
	
	@Override
	public int remove(Integer id){
		return useJianhuyiLogDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return useJianhuyiLogDao.batchRemove(ids);
	}
	
}