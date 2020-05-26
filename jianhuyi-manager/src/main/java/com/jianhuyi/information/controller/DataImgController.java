package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.DataImgDO;
import com.jianhuyi.information.service.DataImgService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-03-25 14:23:41
 */
 
@Controller
@RequestMapping("/information/dataImg")
public class DataImgController {
	@Autowired
	private DataImgService dataImgService;
	
	@GetMapping("/pic/{id}")
	String DataImg(@PathVariable("id") Long id,Model model){
	    model.addAttribute("id",id);
		return "information/dataImg/dataImg";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DataImgDO> dataImgList = dataImgService.list(query);
		int total = dataImgService.count(query);
		PageUtils pageUtils = new PageUtils(dataImgList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:dataImg:add")
	String add(){
	    return "information/dataImg/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:dataImg:edit")
	String edit(@PathVariable("id") Long id,Model model){
		DataImgDO dataImg = dataImgService.get(id);
		model.addAttribute("dataImg", dataImg);
	    return "information/dataImg/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:dataImg:add")
	public R save(DataImgDO dataImg){
		if(dataImgService.save(dataImg)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:dataImg:edit")
	public R update( DataImgDO dataImg){
		dataImgService.update(dataImg);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:dataImg:remove")
	public R remove( Long id){
		if(dataImgService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:dataImg:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		dataImgService.batchRemove(ids);
		return R.ok();
	}
	
}
