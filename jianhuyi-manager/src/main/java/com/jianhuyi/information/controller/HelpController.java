package com.jianhuyi.information.controller;

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
 
@Controller
@RequestMapping("/information/help")
public class HelpController {
	@Autowired
	private HelpService helpService;
	
	@GetMapping()
	@RequiresPermissions("information:help:help")
	String Help(){
	    return "information/help/help";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:help:help")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<HelpDO> helpList = helpService.list(query);
		int total = helpService.count(query);
		PageUtils pageUtils = new PageUtils(helpList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:help:add")
	String add(){
	    return "information/help/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:help:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		HelpDO help = helpService.get(id);
		model.addAttribute("help", help);
	    return "information/help/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:help:add")
	public R save( HelpDO help){
		if(helpService.save(help)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:help:edit")
	public R update( HelpDO help){
		helpService.update(help);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:help:remove")
	public R remove( Integer id){
		if(helpService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:help:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		helpService.batchRemove(ids);
		return R.ok();
	}
	
}
