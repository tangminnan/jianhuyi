package com.jianhuyi.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jianhuyi.information.dao.MsgDao;
import com.jianhuyi.information.domain.MsgDO;
import com.jianhuyi.information.service.MsgService;



@Service
public class MsgServiceImpl implements MsgService {
	@Autowired
	private MsgDao msgDao;
	
	@Override
	public MsgDO get(Long id){
		return msgDao.get(id);
	}
	
	@Override
	public List<MsgDO> list(Map<String, Object> map){
		return msgDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return msgDao.count(map);
	}
	
	@Override
	public int save(MsgDO msg){
		return msgDao.save(msg);
	}
	
	@Override
	public int update(MsgDO msg){
		return msgDao.update(msg);
	}
	
	@Override
	public int remove(Integer id){
		return msgDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return msgDao.batchRemove(ids);
	}

	@Override
	public List<MsgDO> queryUserMsgList(Long userId) {
		List<MsgDO> queryUserMsgList = msgDao.queryUserMsgList(userId);
		return queryUserMsgList;
	}

	@Override
	public List<MsgDO> queryUserMsgListNull(Map<String, Object> map) {
		List<MsgDO> userMsgListNull = msgDao.queryUserMsgListNull(map);
		return userMsgListNull;
	}

	@Override
	public MsgDO queryUserMsgId(Long id) {
		return msgDao.queryUserMsgId(id);
	}


	
}
