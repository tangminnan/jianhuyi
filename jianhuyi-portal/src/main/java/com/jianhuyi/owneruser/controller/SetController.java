package com.jianhuyi.owneruser.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.jianhuyi.common.utils.ShiroUtils;
import com.jianhuyi.owneruser.domain.SetDO;
import com.jianhuyi.owneruser.service.SetService;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-03-18 14:11:10
 */
 
@RestController
@RequestMapping("/jianhuyi/set")
public class SetController {
	@Autowired
	private SetService setService;
	
	
	/**
	 *获取设置
	 */
	@GetMapping("/getSet")
	public Map<String,Object> getUserSet(Integer flag){
		Map<String,Object> map = new HashMap<String,Object>();
		SetDO setDO=null;
		if(flag==1){//家长模式
			setDO = setService.getHouseHolderSet(ShiroUtils.getUser().getUserId());
			map.put("householder", setDO);
		}
		else if(flag==2){//专业模式
			setDO = setService.getProfessionSet(ShiroUtils.getUser().getUserId());
			map.put("profession", setDO);
		}
		return map;
	}
	
	/**
	 * 保存设置
	 */
	@PostMapping("/addSet")
	public Map<String,Object> addsetUser(SetDO setDO){
		setDO.setUserid(ShiroUtils.getUser().getUserId());
		SetDO so = setService.getHouseHolderSet(ShiroUtils.getUser().getUserId());
		int i=0;
		if(so==null)
			i=setService.addsetUser(setDO);
		else
			i=setService.updatesetUser(setDO);
		setService.updateFlag(setDO);
		Map<String,Object> map = new HashMap<String,Object>();
		if(i>0)
			map.put("msg","保存成功");
		else 
			map.put("msg","保存失败");
		return map;
	}
	
	/**
	 * 获取模式
	 */
	
	@GetMapping("/getFlag")
	public Map<String,Object> getFlag(){
		SetDO setDO = setService.getFlag(ShiroUtils.getUser().getUserId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data", setDO);
		return map;
	}
}
