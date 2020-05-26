package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.ProductAdviseDO;
import com.jianhuyi.information.service.ProductAdviseService;
import com.jianhuyi.users.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 产品建议
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 17:40:54
 */
 
@Controller
@RequestMapping("/information/productAdvise")
public class ProductAdviseController {
	@Autowired
	private ProductAdviseService productAdviseService;
	@Autowired
	private UserService userService;
	
	@GetMapping()
	@RequiresPermissions("information:productAdvise:productAdvise")
	String ProductAdvise(){
	    return "information/productAdvise/productAdvise";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:productAdvise:productAdvise")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ProductAdviseDO> productAdviseList = productAdviseService.list(query);

		for (ProductAdviseDO productAdviseDO : productAdviseList) {
			if(productAdviseDO.getUserId() != null ){
				productAdviseDO.setUsersname(userService.get(productAdviseDO.getUserId()).getName());
			}
		}
		System.out.println("==========productAdviseList======================="+productAdviseList);
		int total = productAdviseService.count(query);
		PageUtils pageUtils = new PageUtils(productAdviseList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:productAdvise:add")
	String add(){
	    return "information/productAdvise/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:productAdvise:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ProductAdviseDO productAdvise = productAdviseService.get(id);
		model.addAttribute("productAdvise", productAdvise);
	    return "information/productAdvise/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:productAdvise:add")
	public R save( ProductAdviseDO productAdvise){
		if(productAdviseService.save(productAdvise)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:productAdvise:edit")
	public R update( ProductAdviseDO productAdvise){
		productAdviseService.update(productAdvise);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:productAdvise:remove")
	public R remove( Integer id){
		if(productAdviseService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:productAdvise:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		productAdviseService.batchRemove(ids);
		return R.ok();
	}
	
}
