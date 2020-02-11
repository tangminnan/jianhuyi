package com.jianhuyi.information.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
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
	
	@GetMapping()
	@RequiresPermissions("information:versionUpdate:versionUpdate")
	String VersionUpdate(){
	    return "information/versionUpdate/versionUpdate";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:versionUpdate:versionUpdate")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<VersionUpdateDO> versionUpdateList = versionUpdateService.list(query);
		int total = versionUpdateService.count(query);
		PageUtils pageUtils = new PageUtils(versionUpdateList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:versionUpdate:add")
	String add(){
	    return "information/versionUpdate/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:versionUpdate:edit")
	String edit(@PathVariable("id") Long id,Model model){
		VersionUpdateDO versionUpdate = versionUpdateService.get(id);
		model.addAttribute("versionUpdate", versionUpdate);
	    return "information/versionUpdate/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:versionUpdate:add")
	public R save( VersionUpdateDO versionUpdate){
		if(versionUpdateService.save(versionUpdate)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:versionUpdate:edit")
	public R update( VersionUpdateDO versionUpdate){
		versionUpdateService.update(versionUpdate);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:versionUpdate:remove")
	public R remove( Long id){
		if(versionUpdateService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:versionUpdate:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		versionUpdateService.batchRemove(ids);
		return R.ok();
	}
	
}
