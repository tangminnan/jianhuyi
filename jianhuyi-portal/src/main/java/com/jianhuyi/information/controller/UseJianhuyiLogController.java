package com.jianhuyi.information.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
 
@RestController
@RequestMapping("/jianhuyi/useJianhuyiLog")
public class UseJianhuyiLogController {
	@Autowired
	private UseJianhuyiLogService useJianhuyiLogService;
	
	
	
	/**
	 * 保存检测
	 */
	@ResponseBody
	@PostMapping("/save")
	Map<String,Object> save( UseJianhuyiLogDO useJianhuyiLog){
		Map<String,Object> map = new HashMap<String,Object>();
		useJianhuyiLog.setAddTime(new Date());
		useJianhuyiLog.setDelFlag(0);
		int save = useJianhuyiLogService.save(useJianhuyiLog);
		if(save>0){
			map.put("msg", "操作成功");
			map.put("code", "0");
			map.put("data", "");
		}else{
			map.put("msg", "操作失败");
			map.put("code", "-1");
			map.put("data", "");
		}
		return map;	
	}
	
	/**
	 * 获取今天数据
	 * @param userId
	 * @param addTime
	 * @return
	 */
	@GetMapping("/todayData")
	Map<String,Object> todayData(Long userId){
		Map<String, Object> byTime = useJianhuyiLogService.getByDayTime(userId);
		return byTime;
		
	}
	
	/**
	 * 周记录
	 * @param start
	 * @param end
	 * @param userId
	 * @return
	 */
	@GetMapping("/weekData")
	Map<String,Object> weekData(Date start,Date end, Long userId){
		return useJianhuyiLogService.queryUserWeekHistory(start, end, userId);
		
	}
	
	/**
	 * 月记录
	 * @param start
	 * @param end
	 * @param userId
	 * @return
	 */
	@GetMapping("/monthData")
	Map<String,Object> monthData(Date start,Date end, Long userId){
		return useJianhuyiLogService.queryUserMonthHistory(start, end, userId);
		
	}
	
	/**
	 * 季记录
	 * @param start
	 * @param end
	 * @param userId
	 * @return
	 */
	@GetMapping("/seasonData")
	Map<String,Object> seasonData(Date start,Date end, Long userId){
		return useJianhuyiLogService.queryUserSeasonHistory(start, end, userId);
		
	}
	
	/**
	 * 年记录
	 * @param start
	 * @param end
	 * @param userId
	 * @return
	 */
	@GetMapping("/yearData")
	Map<String,Object> yearData(Date start,Date end, Long userId){
		return useJianhuyiLogService.queryUserYearHistory(start, end, userId);
		
	}
	
	
	
}
