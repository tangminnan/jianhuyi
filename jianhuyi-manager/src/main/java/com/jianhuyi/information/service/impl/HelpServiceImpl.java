package com.jianhuyi.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianhuyi.information.dao.HelpDao;
import com.jianhuyi.information.domain.HelpDO;
import com.jianhuyi.information.service.HelpService;

import java.util.List;
import java.util.Map;




@Service
public class HelpServiceImpl implements HelpService {
	@Autowired
	private HelpDao helpDao;
	
	@Override
	public HelpDO get(Integer id){
		return helpDao.get(id);
	}
	
	@Override
	public List<HelpDO> list(Map<String, Object> map){
		return helpDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return helpDao.count(map);
	}
	
	@Override
	public int save(HelpDO help){
		return helpDao.save(help);
	}
	
	@Override
	public int update(HelpDO help){
		return helpDao.update(help);
	}
	
	@Override
	public int remove(Integer id){
		return helpDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return helpDao.batchRemove(ids);
	}
	
}
