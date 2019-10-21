package com.jianhuyi.information.controller;

import java.util.Date;
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
import com.jianhuyi.information.domain.ProductAdviseDO;
import com.jianhuyi.information.service.ProductAdviseService;


/**
 * 产品建议
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 17:40:54
 */
 
@RestController
@RequestMapping("/jianhuyi/productAdvise")
public class ProductAdviseController {
	@Autowired
	private ProductAdviseService productAdviseService;
	
	/**
	 * 产品建议保存
	 */
	@ResponseBody
	@PostMapping("/save")
	Map<String,Object> save( ProductAdviseDO productAdvise){
		Map<String,Object> map = new HashMap<String,Object>();
		productAdvise.setAddTime(new Date());
		int save = productAdviseService.save(productAdvise);
		if(save>0){
			map.put("msg", "操作成功");
			map.put("code", 0);
		}else{
			map.put("msg", "操作失败");
			map.put("code", 1);
		}
		return map;
	}
	
}
