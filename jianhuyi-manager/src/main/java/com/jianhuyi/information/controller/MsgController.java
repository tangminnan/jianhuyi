package com.jianhuyi.information.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.jianhuyi.information.domain.MsgDO;
import com.jianhuyi.information.domain.MsgUserDO;
import com.jianhuyi.information.service.MsgService;
import com.jianhuyi.information.service.MsgUserService;
import com.jianhuyi.users.domain.UserDO;
import com.jianhuyi.users.service.UserService;

/**
 * 消息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2018-04-12 16:00:47
 */
 
@Controller
@RequestMapping("/information/msg")
public class MsgController {
	@Autowired
	private MsgService msgService;
	@Autowired
	private UserService userService;
	@Autowired
	private MsgUserService msgUserService;
	
	@GetMapping()
	@RequiresPermissions("information:msg:msg")
	String Msg(){
		return "information/msg/msg";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:msg:msg")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<MsgDO> msgList = msgService.list(query);
		int total = msgService.count(query);
		PageUtils pageUtils = new PageUtils(msgList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:msg:add")
	String add(Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserDO> list = userService.list(map);
		model.addAttribute("list", list);
		return "information/msg/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:msg:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		MsgDO msg = msgService.get(id);
		model.addAttribute("msg", msg);
	    return "information/msg/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:msg:add")
	public R save( MsgDO msg){
		msg.setAddTime(new Date());
		msg.setUpdateTime(new Date());
		msg.setType(0);
		if(msgService.save(msg)>0){
			String[] split = msg.getForIds().split(",");
			for (String string : split) {
				MsgUserDO msgUser = new MsgUserDO();
				msgUser.setUserId(Long.valueOf(string));
				msgUser.setMsgId(msg.getId());
				msgUser.setAddTime(new Date());
				msgUser.setStatue(1);
				msgUserService.save(msgUser);
			}
			
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:msg:edit")
	public R update( MsgDO msg){
		msgService.update(msg);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:msg:remove")
	public R remove( Integer id){
		if(msgService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:msg:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		msgService.batchRemove(ids);
		return R.ok();
	}
	
}
