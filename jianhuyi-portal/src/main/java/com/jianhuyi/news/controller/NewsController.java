package com.jianhuyi.news.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jianhuyi.news.domain.BannerDO;
import com.jianhuyi.news.domain.NewsDO;
import com.jianhuyi.news.service.BannerService;
import com.jianhuyi.news.service.NewsService;



/**
 * 咨讯表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-15 16:39:38
 */
 
@RestController
@RequestMapping("/jianhuyi/news")
public class NewsController {
	@Autowired
	private NewsService newsService;
	@Autowired
	private BannerService bannerService;
	
	/**
	 * 咨询列表
	 * @return
	 */
	@GetMapping("/newList")
	Map<String,Object> newList(){
		Map<String, Object> map = new HashMap<String,Object>();
		List<NewsDO> list = newsService.list(map);
		map.put("data", list);
		map.put("msg", "");
		map.put("code", 0);
		return map;
	}
	
	/**
	 * 咨询详情
	 * @param id
	 * @return
	 */
	@GetMapping("/newDetails")
	Map<String,Object> newDetails(Integer id){
		Map<String, Object> map = new HashMap<String,Object>();
		NewsDO newsDO = newsService.get(id);
		map.put("data", newsDO);
		map.put("msg", "");
		map.put("code", 0);
		return map;
	}
	
	/**
	 * 轮播图
	 * @return
	 */
	@GetMapping("/bannerList")
	Map<String,Object> bannerList(){
		Map<String, Object> map = new HashMap<String,Object>();
		List<BannerDO> list = bannerService.list(map);
		map.put("data", list);
		map.put("msg", "");
		map.put("code", 0);
		return map;
	}
	
}
