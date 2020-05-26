package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.DataImgDao;
import com.jianhuyi.information.domain.DataImgDO;
import com.jianhuyi.information.service.DataImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




@Service
public class DataImgServiceImpl implements DataImgService {
	@Autowired
	private DataImgDao dataImgDao;
	
	@Override
	public DataImgDO get(Long id){
		return dataImgDao.get(id);
	}
	
	@Override
	public List<DataImgDO> list(Map<String, Object> map){
		return dataImgDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dataImgDao.count(map);
	}
	
	@Override
	public int save(DataImgDO dataImg){
		return dataImgDao.save(dataImg);
	}
	
	@Override
	public int update(DataImgDO dataImg){
		return dataImgDao.update(dataImg);
	}
	
	@Override
	public int remove(Long id){
		return dataImgDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return dataImgDao.batchRemove(ids);
	}
	
}
