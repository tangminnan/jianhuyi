package com.jianhuyi.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianhuyi.information.dao.FunctionSetDao;
import com.jianhuyi.information.domain.FunctionSetDO;
import com.jianhuyi.information.service.FunctionSetService;

import java.util.List;
import java.util.Map;




@Service
public class FunctionSetServiceImpl implements FunctionSetService {
	@Autowired
	private FunctionSetDao functionSetDao;

	@Override
	public FunctionSetDO get(Long userId) {
		return functionSetDao.get(userId);
	}
	
	@Override
	public int update(FunctionSetDO functionSet){
		return functionSetDao.update(functionSet);
	}
	
	@Override
	public int save(FunctionSetDO functionSet){
		return functionSetDao.save(functionSet);
	}
	
}
