package com.jianhuyi.information.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianhuyi.information.domain.VersionUpdateDO;
import com.jianhuyi.information.service.VersionUpdateService;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-02-11 17:10:35
 */
 
@Controller
@RequestMapping("/information/versionUpdate")
public class VersionUpdateController {
	@Autowired
	private VersionUpdateService versionUpdateService;
	
	
	@ResponseBody
	@GetMapping("/list")
	public Map<String,Object> list(Integer code){
		Map<String,Object> list = new HashMap<String,Object>();
		VersionUpdateDO version = versionUpdateService.versionCheck();
		
		if(code == version.getVersionNum()){
			list.put("code", -1);
			list.put("msg","已是最新版本");
			list.put("data", "");
		}else{
			list.put("code", 0);
			list.put("msg","success");
			list.put("data", version);
		}
		
		return list;
		
	}
	
}
