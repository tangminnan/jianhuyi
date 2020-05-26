package com.jianhuyi.information.controller;

import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	Map<String, Object> save(@RequestBody List<UseJianhuyiLogDO> useJianhuyiLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLog) {
			useJianhuyiLogDO.setAddTime(new Date());
			useJianhuyiLogDO.setDelFlag(0);
			int save = useJianhuyiLogService.save(useJianhuyiLogDO);
			if (save > 0) {
				continue;
			}else{
				flag = false;
			}
		}
		if(flag){
			map.put("msg", "操作成功");
			map.put("code", 0);
			map.put("data", "");
		} else {
			map.put("msg", "操作失败");
			map.put("code", -1);
			map.put("data", "");
		}
		return map;
	}

	/**
	 * 获取今天数据
	 * 
	 * @param userId
	 * @param addTime
	 * @return
	 */
	@GetMapping("/todayData")
	Map<String, Object> todayData(Long userId) {
		Map<String, Object> useLog = useJianhuyiLogService.getByDayTime(userId);

		return useLog;

	}

	/**
	 * 周记录
	 * 
	 * @param start
	 * @param end
	 * @param userId
	 * @return
	 */
	@GetMapping("/weekData")
	Map<String, Object> weekData(Date start, Date end, Long userId) {
		return useJianhuyiLogService.queryUserWeekHistory(start, end, userId);

	}

	/**
	 * 月记录
	 * 
	 * @param start
	 * @param end
	 * @param userId
	 * @return
	 */
	@GetMapping("/monthData")
	Map<String, Object> monthData(Date start, Date end, Long userId) {
		return useJianhuyiLogService.queryUserMonthHistory(start, end, userId);

	}

	/**
	 * 季记录
	 * 
	 * @param start
	 * @param end
	 * @param userId
	 * @return
	 */
	@GetMapping("/seasonData")
	Map<String, Object> seasonData(Date start, Date end, Long userId) {
		return useJianhuyiLogService.queryUserSeasonHistory(start, end, userId);

	}

	/**
	 * 年记录
	 * 
	 * @param start
	 * @param end
	 * @param userId
	 * @return
	 */
	@GetMapping("/yearData")
	Map<String, Object> yearData(Date start, Date end, Long userId) {
		return useJianhuyiLogService.queryUserYearHistory(start, end, userId);

	}

}
