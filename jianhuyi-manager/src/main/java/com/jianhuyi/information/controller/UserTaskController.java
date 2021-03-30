package com.jianhuyi.information.controller;

import java.util.List;
import java.util.Map;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianhuyi.information.domain.UserTaskDO;
import com.jianhuyi.information.service.UserTaskService;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-19 14:22:00
 */
 
@Controller
@RequestMapping("/information/userTask")
public class UserTaskController {
	@Autowired
	private UserTaskService userTaskService;
	
	@GetMapping()
	@RequiresPermissions("information:userTask:userTask")
	String UserTask(){
	    return "information/userTask/userTask";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:userTask:userTask")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserTaskDO> userTaskList = userTaskService.list(query);
		int total = userTaskService.count(query);
		PageUtils pageUtils = new PageUtils(userTaskList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:userTask:add")
	String add(){
	    return "information/userTask/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:userTask:edit")
	String edit(@PathVariable("id") Long id,Model model){
		UserTaskDO userTask = userTaskService.get(id);
		model.addAttribute("userTask", userTask);
	    return "information/userTask/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:userTask:add")
	public R save(UserTaskDO userTask){
		if(userTaskService.save(userTask)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:userTask:edit")
	public R update( UserTaskDO userTask){
		userTaskService.update(userTask);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:userTask:remove")
	public R remove( Long id){
		if(userTaskService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:userTask:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userTaskService.batchRemove(ids);
		return R.ok();
	}
	
}
