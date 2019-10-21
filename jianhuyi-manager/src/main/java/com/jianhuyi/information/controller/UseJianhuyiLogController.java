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
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;


/**
 * 检测记录表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */
 
@Controller
@RequestMapping("/information/useJianhuyiLog")
public class UseJianhuyiLogController {
	@Autowired
	private UseJianhuyiLogService useJianhuyiLogService;
	
	@GetMapping()
	@RequiresPermissions("information:useJianhuyiLog:useJianhuyiLog")
	String UseJianhuyiLog(){
	    return "information/useJianhuyiLog/useJianhuyiLog";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:useJianhuyiLog:useJianhuyiLog")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UseJianhuyiLogDO> useJianhuyiLogList = useJianhuyiLogService.list(query);
		int total = useJianhuyiLogService.count(query);
		PageUtils pageUtils = new PageUtils(useJianhuyiLogList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:useJianhuyiLog:add")
	String add(){
	    return "information/useJianhuyiLog/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:useJianhuyiLog:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		UseJianhuyiLogDO useJianhuyiLog = useJianhuyiLogService.get(id);
		model.addAttribute("useJianhuyiLog", useJianhuyiLog);
	    return "information/useJianhuyiLog/edit";
	}
	
	@GetMapping("/detail/{id}")
	String detail(@PathVariable("id") Integer id,Model model){
		model.addAttribute("id", id);
	    return "users/useJianhuyiLog";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:useJianhuyiLog:add")
	public R save( UseJianhuyiLogDO useJianhuyiLog){
		if(useJianhuyiLogService.save(useJianhuyiLog)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:useJianhuyiLog:edit")
	public R update( UseJianhuyiLogDO useJianhuyiLog){
		useJianhuyiLogService.update(useJianhuyiLog);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:useJianhuyiLog:remove")
	public R remove( Integer id){
		if(useJianhuyiLogService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:useJianhuyiLog:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		useJianhuyiLogService.batchRemove(ids);
		return R.ok();
	}
	
}
