package com.jianhuyi.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jianhuyi.information.dao.MsgUserDao;
import com.jianhuyi.information.domain.MsgUserDO;
import com.jianhuyi.information.service.MsgUserService;



@Service
public class MsgUserServiceImpl implements MsgUserService {
	@Autowired
	private MsgUserDao msgUserDao;
	
	@Override
	public MsgUserDO get(Long id){
		return msgUserDao.get(id);
	}
	
	@Override
	public List<MsgUserDO> list(Map<String, Object> map){
		return msgUserDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return msgUserDao.count(map);
	}
	
	@Override
	public int save(MsgUserDO msgUser){
		return msgUserDao.save(msgUser);
	}
	
	@Override
	public int update(MsgUserDO msgUser){
		return msgUserDao.update(msgUser);
	}
	
	@Override
	public int remove(Long id){
		return msgUserDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return msgUserDao.batchRemove(ids);
	}
	
}
