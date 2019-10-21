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
	public MsgDO get(Integer id){
		return msgDao.get(id);
	}

	@Override
	public List<MsgDO> queryMsgList(Long userId) {
		return msgDao.queryMsgList(userId);
	}

	@Override
	public MsgDO queryMsgDetails(Integer id, Integer muId) {
		msgDao.updateUserMsg(muId);
		return msgDao.get(id);
	}
	
	

	
}
