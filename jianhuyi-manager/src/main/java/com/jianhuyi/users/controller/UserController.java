package com.jianhuyi.users.controller;


import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.users.domain.UserDO;
import com.jianhuyi.users.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * 用户信息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2018-09-27 10:18:38
 */
 

@Controller("UserController")
@RequestMapping("/information/users")
public class UserController{
	@Autowired
	private UserService userService;
	
	@GetMapping()
	@RequiresPermissions("information:user:user")
	String User(){
	    return "users/user";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:user:user")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserDO> userList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtils = new PageUtils(userList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:user:add")
	String add(){
	    return "users/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:user:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		UserDO user = userService.get(id);
		model.addAttribute("user", user);
	    return "users/edit";
	}
	@GetMapping("/importtemplate")
	String importtemplate(){
		return "users/importtemplate";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:user:add")
	public R save( UserDO user) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		// 15位需要补年份
		if (user.getIdentityCard().length() == 15) {
			user.setBirthday(substringBir("19" + user.getIdentityCard().substring(6, 12)));
			user.setAge(Integer.parseInt(sdf.format(new Date()))
					- (substringAge("19" + user.getIdentityCard().substring(6, 12))));
			// 18位直接截取7到14位
		} else if (user.getIdentityCard().length() == 18) {
			user.setBirthday(substringBir(user.getIdentityCard().substring(6, 14)));
			user.setAge(
					Integer.parseInt(sdf.format(new Date())) - (substringAge(user.getIdentityCard().substring(6, 14))));
		}
		user.setRegisterTime(new Date());
		user.setDeleteFlag(1);
		if(userService.save(user)>0){
			return R.ok();
		}
		return R.error();
	}

	public Date substringBir(String day) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		String yyyy = day.substring(0, 4);
		String mm = day.substring(4, 6);
		String dd = day.substring(6);
		String date1 = yyyy+"-"+mm+"-"+dd;
		//不抛出异常会报错
		date = format.parse(date1);
		return date;
	}

	public Integer substringAge(String day) {
		Integer yyyy = Integer.parseInt(day.substring(0, 4));
		return yyyy;
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:user:edit")
	public R update( UserDO user){
		userService.update(user);
		return R.ok();
	}
		

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:user:remove")
	public R remove( Long id){
		
		UserDO user = new UserDO();
        user.setId(id);
        user.setDeleteFlag(0);
		userService.update(user);
		
//		if(userService.remove(id)>0){
//			return R.ok();
//		}	        	
//		return R.error();
		return R.ok();
		
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:user:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userService.batchRemove(ids);
		return R.ok();
	}

	/**
	 * 批量导入会员
	 * */
	@ResponseBody
	@PostMapping("/importMember")
	@RequiresPermissions("information:member:member")
	public R importMember(MultipartFile file){
		return userService.importMember(file);
	}
}
