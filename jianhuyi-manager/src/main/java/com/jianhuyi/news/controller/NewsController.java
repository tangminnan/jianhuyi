package com.jianhuyi.news.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.utils.FileUtil;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.news.domain.NewsDO;
import com.jianhuyi.news.service.NewsService;



/**
 * 咨讯表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-15 16:39:38
 */
 
@Controller
@RequestMapping("/information/news")
public class NewsController {
	@Autowired
	private NewsService newsService;
	@Autowired
	private BootdoConfig bootdoConfig;
	
	@GetMapping()
	@RequiresPermissions("information:news:news")
	String News(Model model){
		List<NewsDO> list = newsService.list(new HashMap<String,Object>());
		model.addAttribute("list",list);
	    return "information/news/news";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:news:news")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        query.put("uId",params.get("uId"));
        query.put("deleted","0");
		List<NewsDO> newsList = newsService.list(query);
		int total = newsService.count(query);
		PageUtils pageUtils = new PageUtils(newsList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:news:add")
	String add(Model model){
	    return "information/news/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:news:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		NewsDO news = newsService.get(id);
		model.addAttribute("news", news);
		return "information/news/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:news:add")
	public R save( NewsDO news){
		
		try {
			if(news.getPicItems()!= null && news.getPicItems().getSize() > 0){
				String fileName = news.getPicItems().getOriginalFilename();
				fileName = FileUtil.renameToUUID(fileName);
				FileUtil.uploadFile(news.getPicItems().getBytes(), bootdoConfig.getUploadPath()+"news/", fileName);
				news.setTupianurl("/files/news/" + fileName);
			}
			news.setDeleted(0);
			news.setCheckStatus(0);
			
			news.setCreateTime(new Date());
			news.setUpdateTime(new Date());
		} catch (Exception e) {
			return R.error();
		}
		if(newsService.save(news)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:news:edit")
	public R update( NewsDO news){
		if(news.getPicItems()!= null && news.getPicItems().getSize() > 0){
			String fileName = news.getPicItems().getOriginalFilename(); 
			fileName = FileUtil.renameToUUID(fileName);
			try {
				FileUtil.uploadFile(news.getPicItems().getBytes(), bootdoConfig.getUploadPath()+"news/", fileName);
				news.setTupianurl("/files/news/" + fileName);
				news.setUpdateTime(new Date());
			} catch (Exception e) {
				return R.error();
			}
			
		}
		newsService.update(news);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:news:remove")
	public R remove( Integer id){
		NewsDO newsDO = new NewsDO();
		newsDO.setId(id);
		newsDO.setDeleted(1);
		if(newsService.update(newsDO)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:news:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		newsService.batchRemove(ids);
		return R.ok();
	}
	
	
}
