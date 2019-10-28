package com.jianhuyi.information.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jianhuyi.information.domain.FunctionSetDO;
import com.jianhuyi.information.service.FunctionSetService;


/**
 * 功能设置
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-14 15:23:48
 */
 
@RestController
@RequestMapping("/jianhuyi/functionSet")
public class FunctionSetController {
	@Autowired
	private FunctionSetService functionSetService;
	
	/**
	 * 用户设置
	 * @param userId
	 * @return
	 */
	@GetMapping("/userSet")
	Map<String,Object> userSet(Long userId){
		Map<String,Object> map = new HashMap<String,Object>();
		FunctionSetDO setDO = functionSetService.get(userId);
		map.put("data", setDO);
		map.put("msg", "");
		map.put("code", "0");
		return map;
	}
	
	/**
	 * 更新用户设置
	 * @param functionSet
	 * @return
	 */
	@PostMapping("/updateUserSet")
	Map<String,Object> updateUserSet(FunctionSetDO functionSet){
		Map<String,Object> map = new HashMap<String,Object>();
		int update = functionSetService.update(functionSet);
		if(update>0){
			map.put("msg", "操作成功");
			map.put("code", "0");
			map.put("data", "");
		}else{
			map.put("msg", "操作失败");
			map.put("code", "-1");
			map.put("data", "");
		}
		return map;
		
	}
	
	/**
	 * 恢复默认
	 * @return
	 */
	@PostMapping("/restoreDefault")
	Map<String,Object> restoreDefault(Long userId){
		Map<String,Object> map = new HashMap<String,Object>();
		FunctionSetDO functionSet = new FunctionSetDO();
		functionSet.setUserId(userId);
		functionSet.setFunctionSet(1);
		functionSet.setOutdoorsSet(1);
		functionSet.setLookPhoneSet(1);
		functionSet.setLookTvComputer(1);
		functionSet.setShockTips(1);
		functionSet.setSitCorrect("80");
		int update = functionSetService.update(functionSet);
		if(update>0){
			map.put("msg", "操作成功");
			map.put("code", "0");
			map.put("data", "");
		}else{
			map.put("msg", "操作失败");
			map.put("code", "-1");
			map.put("data", "");
		}
		return map;
		
	}

}
