package com.jianhuyi.information.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.users.domain.UserDO;
import com.jianhuyi.users.service.UserService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
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

import com.jianhuyi.information.domain.MyGiftDO;
import com.jianhuyi.information.service.MyGiftService;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-18 14:27:52
 */
 
@Controller
@RequestMapping("/information/myGift")
public class MyGiftController {
	@Autowired
	private MyGiftService myGiftService;
	
	@GetMapping()
	@RequiresPermissions("information:myGift:myGift")
	String MyGift(){
	    return "information/myGift/myGift";
	}


	@GetMapping("/duihuanjilu/{id}")
	@RequiresPermissions("information:myGift:myGift")
	String duihuanjilu(@PathVariable("id") Integer userId,Model model){
		model.addAttribute("userId",userId);
		return "information/myGift/myGift";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:myGift:myGift")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        List<MyGiftDO> myGiftList = myGiftService.list(query);
		int total = myGiftService.count(query);
		PageUtils pageUtils = new PageUtils(myGiftList, total);
		return pageUtils;
	}
	


	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Long id,Model model){
		MyGiftDO myGift = myGiftService.get(id);
		model.addAttribute("myGift", myGift);
	    return "information/myGift/edit";
	}
	

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( MyGiftDO myGift){
		myGiftService.update(myGift);
		return R.ok();
	}
	


}
