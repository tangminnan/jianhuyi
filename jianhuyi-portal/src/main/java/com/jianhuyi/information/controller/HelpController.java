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

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.HelpDO;
import com.jianhuyi.information.service.HelpService;


/**
 * 帮助表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 17:00:55
 */
 
@RestController
@RequestMapping("/jianhuyi/help")
public class HelpController {
	@Autowired
	private HelpService helpService;
	
	/**
	 * 帮助列表
	 * @return
	 */
	@GetMapping("/helpList")
	Map<String,Object> helpList(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<HelpDO> list = helpService.list(map);
		map.put("data", list);
		map.put("msg", "");
		map.put("code", "0");
		return map;
	}
	
	/**
	 * 帮助详情
	 * @param id
	 * @return
	 */
	@GetMapping("/helpList/details")
	Map<String,Object> details(Integer id){
		Map<String,Object> map = new HashMap<String,Object>();
		HelpDO helpDO = helpService.get(id);
		map.put("data", helpDO);
		map.put("msg", "");
		map.put("code", "0");
		return map;
	}
	
}
