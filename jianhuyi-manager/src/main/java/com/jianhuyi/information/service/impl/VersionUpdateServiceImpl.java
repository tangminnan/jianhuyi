package com.jianhuyi.information.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianhuyi.information.dao.VersionUpdateDao;
import com.jianhuyi.information.domain.VersionUpdateDO;
import com.jianhuyi.information.service.VersionUpdateService;



@Service
public class VersionUpdateServiceImpl implements VersionUpdateService {
	@Autowired
	private VersionUpdateDao versionUpdateDao;
	
	@Override
	public VersionUpdateDO get(Long id){
		return versionUpdateDao.get(id);
	}
	
	@Override
	public List<VersionUpdateDO> list(Map<String, Object> map){
		return versionUpdateDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return versionUpdateDao.count(map);
	}
	
	@Override
	public int save(VersionUpdateDO versionUpdate){
		return versionUpdateDao.save(versionUpdate);
	}
	
	@Override
	public int update(VersionUpdateDO versionUpdate){
		return versionUpdateDao.update(versionUpdate);
	}
	
	@Override
	public int remove(Long id){
		return versionUpdateDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return versionUpdateDao.batchRemove(ids);
	}
	
}
