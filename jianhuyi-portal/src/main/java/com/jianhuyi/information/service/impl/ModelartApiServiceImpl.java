package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.ModelartApiDao;
import com.jianhuyi.information.domain.ModelartApiDO;
import com.jianhuyi.information.service.ModelartApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class ModelartApiServiceImpl implements ModelartApiService {
	@Autowired
	private ModelartApiDao modelartApiDao;
	
	@Override
	public ModelartApiDO get(Long id){
		return modelartApiDao.get(id);
	}
	
	@Override
	public List<ModelartApiDO> list(Map<String, Object> map){
		return modelartApiDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return modelartApiDao.count(map);
	}
	
	@Override
	public int save(ModelartApiDO modelartApi){
		return modelartApiDao.save(modelartApi);
	}
	
	@Override
	public int update(ModelartApiDO modelartApi){
		return modelartApiDao.update(modelartApi);
	}
	
	@Override
	public int remove(Long id){
		return modelartApiDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return modelartApiDao.batchRemove(ids);
	}
	
}
