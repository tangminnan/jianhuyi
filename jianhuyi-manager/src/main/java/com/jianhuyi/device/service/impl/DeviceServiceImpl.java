package com.jianhuyi.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianhuyi.device.dao.DeviceDao;
import com.jianhuyi.device.domain.DeviceDO;
import com.jianhuyi.device.service.DeviceService;

import java.util.List;
import java.util.Map;





@Service
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	private DeviceDao deviceDao;
	
	@Override
	public DeviceDO get(Integer id){
		return deviceDao.get(id);
	}
	
	@Override
	public List<DeviceDO> list(Map<String, Object> map){
		return deviceDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return deviceDao.count(map);
	}
	
	@Override
	public int save(DeviceDO device){
		return deviceDao.save(device);
	}
	
	@Override
	public int update(DeviceDO device){
		return deviceDao.update(device);
	}
	
	@Override
	public int remove(Integer id){
		return deviceDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return deviceDao.batchRemove(ids);
	}
	
}
