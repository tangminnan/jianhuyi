package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.EnergysDataDO;
import com.jianhuyi.information.service.EnergysDataService;
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
@RequestMapping("/information/energysData")
public class EnergysDataController {
	@Autowired
	private EnergysDataService energysDataService;

	@GetMapping("/listdata")
	@RequiresPermissions("information:data:data")
	ModelAndView Data(Integer userId, String time, ModelAndView model){
		model.addObject("userId",userId);
		model.addObject("time",time.substring(0,10));

		model.setViewName("information/energysData/energysData");
		return model;
	}

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EnergysDataDO> energysDataList = energysDataService.list(query);
		int total = energysDataService.count(query);
		PageUtils pageUtils = new PageUtils(energysDataList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:energysData:add")
	String add(){
	    return "information/energysData/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:energysData:edit")
	String edit(@PathVariable("id") Long id,Model model){
		EnergysDataDO energysData = energysDataService.get(id);
		model.addAttribute("energysData", energysData);
	    return "information/energysData/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:energysData:add")
	public R save(EnergysDataDO energysData){
		if(energysDataService.save(energysData)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:energysData:edit")
	public R update( EnergysDataDO energysData){
		energysDataService.update(energysData);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:energysData:remove")
	public R remove( Long id){
		if(energysDataService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:energysData:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		energysDataService.batchRemove(ids);
		return R.ok();
	}
	
}
