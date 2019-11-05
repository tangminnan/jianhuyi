package com.jianhuyi.device.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jianhuyi.common.utils.ShiroUtils;
import com.jianhuyi.device.domain.DeviceDO;
import com.jianhuyi.device.service.DeviceService;
import com.jianhuyi.owneruser.service.OwnerUserService;


/**
 * 用户设备表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-18 16:22:42
 */
 
@RestController
@RequestMapping("/jianhuyi/device")
public class DeviceController {
	@Autowired
	private DeviceService deviceService;
	@Autowired
    OwnerUserService userService;
	
	/**
	 * 设备列表接口
	 */
	@GetMapping("/list")
	Map<String,Object> list(Long userId){
	   Map<String,Object> params = new HashMap<String,Object>();
	   params.put("userId",userId);
	   List<DeviceDO> list = deviceService.list(params);
	   Map<String,Object> map =  new HashMap<String,Object>();
	   map.put("data", list);
	   map.put("msg", "success");
	   map.put("code", 0);
	   return map;
	}
	
	/**
	 * 设备添加接口
	 */
	@PostMapping("/addBymac")
	Map<String,Object> addBymac(String mac){
		 Map<String,Object> map = new HashMap<String,Object>();
		 Map<String,Object> params = new HashMap<String,Object>();
		 params.put("mac",mac);
		 List<DeviceDO> list = deviceService.list(params);
		 if(list.size()==0){
			 map.put("msg","设备不存在，添加失败");
			 map.put("code", -1);
			 map.put("data", "error");
		 }else{
			 DeviceDO deviceDO = new DeviceDO();
			 deviceDO.setAccount(ShiroUtils.getUser().getPhone());
			 deviceDO.setMac(mac);
			 if(deviceService.update(deviceDO)>0){
				 map.put("msg","设备添加成功");
				 map.put("code", 0);
				 map.put("data", "success");
			 }
		 }
		 return map;
	}
	
	@PostMapping("/addByidentity")
	Map<String,Object> addByidentity(String identity,Long userId){
		 Map<String,Object> map = new HashMap<String,Object>();
		 Map<String,Object> params = new HashMap<String,Object>();
		 params.put("identity",identity);
		 List<DeviceDO> list = deviceService.list(params);
		 if(list.size()==0){
			 map.put("msg","设备不存在，添加失败");
			 map.put("code", -1);
			 map.put("data", "");
		 }else{
			 DeviceDO deviceDO = new DeviceDO();
			 String phone = userService.get(userId).getPhone();
			 if(phone != null){
				 deviceDO.setAccount(phone);
			 }
			 deviceDO.setUserId(userId);
			 deviceDO.setIdentity(identity);
			 if(deviceService.updateByidentity(deviceDO)>0){
				 map.put("msg","设备添加成功");
				 map.put("code", 0);
				 map.put("data", "success");
			 }
		 }
		 return map;
	}
	
	
	
	
	
	@PostMapping("/delete")
	Map<String,Object> delete(String identity){
		Map<String,Object> map = new HashMap<String,Object>();
		
			DeviceDO deviceDO= new DeviceDO();
			deviceDO.setAccount("");
			deviceDO.setUserId(0l);
			deviceDO.setIdentity(identity);
			if(deviceService.updateByidentity(deviceDO)>0)
			map.put("msg","操作成功");
			map.put("code", 0);
			map.put("data", "success");
		
		return map;
	}
	
}
