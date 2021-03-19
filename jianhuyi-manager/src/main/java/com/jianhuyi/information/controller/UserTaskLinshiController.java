package com.jianhuyi.information.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.dao.UserTaskLinshiDao;
import com.jianhuyi.information.domain.UserTaskDO;
import com.jianhuyi.information.service.UserTaskService;
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

import com.jianhuyi.information.domain.UserTaskLinshiDO;
import com.jianhuyi.information.service.UserTaskLinshiService;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-19 15:07:20
 */
 
@Controller
@RequestMapping("/information/userTaskLinshi")
public class UserTaskLinshiController {
	@Autowired
	private UserTaskLinshiService userTaskLinshiService;
	@Autowired
	private UserTaskService userTaskService;

	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");


	@GetMapping("/{id}")
	String UserTaskLinshi(@PathVariable("id") Integer taskId,Model model){
		model.addAttribute("taskId",taskId);
	    return "information/userTaskLinshi/userTaskLinshi";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public List<UserTaskLinshiDao> list(@RequestParam Map<String, Object> params){

        Query query = new Query(params);
		List<UserTaskLinshiDO> userTaskLinshiList = userTaskLinshiService.list(query);
		Map<String, UserTaskLinshiDO> utmap = new HashMap<>();
		      userTaskLinshiList.forEach(a -> {
				a.setDay(simpleDateFormat.format(a.getCreateTime()));
				utmap.put(simpleDateFormat.format(a.getCreateTime()), a);
		      });
			Map<String, Object> map = new LinkedHashMap<>();
			fillMapDays(map, userTaskService.get(Long.parseLong(params.get("taskId").toString())));
			for (Map.Entry<String, UserTaskLinshiDO> entry : utmap.entrySet()) {
				map.put(entry.getKey(), entry.getValue());
			}

			List<UserTaskLinshiDao> list = new ArrayList(map.values());
			return list;

	}


	private void fillMapDays(Map<String, Object> map, UserTaskDO userTask) {
		Date startDate = userTask.getStartTime();
		Calendar calendar  = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DAY_OF_YEAR,userTask.getTaskTime());
		Date endDate = calendar.getTime();
		if(endDate.compareTo(new Date())>0)
			endDate=new Date();
		while(startDate.compareTo(endDate)<0){
			UserTaskLinshiDO userTaskLinshiDO = new UserTaskLinshiDO();
			userTaskLinshiDO.setDay(simpleDateFormat.format(startDate));
			map.put(simpleDateFormat.format(startDate),userTaskLinshiDO);
			calendar.setTime(startDate);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			startDate=calendar.getTime();
		}
	}
	

}
