package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.ErrorDataDO;
import com.jianhuyi.information.service.ErrorDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-03-27 14:52:34
 */
 
@Controller
@RequestMapping("/information/errorData")
public class ErrorDataController {
	@Autowired
	private ErrorDataService errorDataService;

	@GetMapping("/listdata")
	@RequiresPermissions("information:data:data")
	ModelAndView Data(Integer userId, String time, ModelAndView model){
		model.addObject("userId",userId);
		model.addObject("time",time.substring(0,10));

		model.setViewName("information/errorData/errorData");
		return model;
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ErrorDataDO> errorDataList = errorDataService.list(query);
		int total = errorDataService.count(query);
		PageUtils pageUtils = new PageUtils(errorDataList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:errorData:add")
	String add(){
	    return "information/errorData/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:errorData:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ErrorDataDO errorData = errorDataService.get(id);
		model.addAttribute("errorData", errorData);
	    return "information/errorData/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:errorData:add")
	public R save(ErrorDataDO errorData){
		if(errorDataService.save(errorData)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:errorData:edit")
	public R update( ErrorDataDO errorData){
		errorDataService.update(errorData);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:errorData:remove")
	public R remove( Long id){
		if(errorDataService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:errorData:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		errorDataService.batchRemove(ids);
		return R.ok();
	}
	
}
