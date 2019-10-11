package com.jianhuyi.device.service;


import java.util.List;
import java.util.Map;

import com.jianhuyi.device.domain.DeviceDO;

/**
 * 用户设备表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-18 16:22:42
 */
public interface DeviceService {
	
	DeviceDO get(Integer id);
	
	List<DeviceDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DeviceDO device);
	
	int update(DeviceDO device);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	int updateByidentity(DeviceDO deviceDO);
}
