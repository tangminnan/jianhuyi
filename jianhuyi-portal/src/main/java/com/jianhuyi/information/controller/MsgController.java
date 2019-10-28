package com.jianhuyi.information.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jianhuyi.common.annotation.Log;
import com.jianhuyi.common.controller.BaseController;
import com.jianhuyi.information.domain.MsgDO;
import com.jianhuyi.information.service.MsgService;


/**
 * 消息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-19 11:33:19
 */
 
@RestController
@RequestMapping("/jianhuyi/msg")
public class MsgController extends BaseController{
	@Autowired
	private MsgService msgService;
	
	
	@Log("获取信息列表")
	@ResponseBody
	@GetMapping("/list")
	 Map<String, Object> list(Long userId){
		Map<String, Object> map = new HashMap<>();
		List<MsgDO> msgList = msgService.queryMsgList(userId);
		map.put("data", msgList);
		map.put("msg", "");
		map.put("code", "0");
		return map;

	}
	
	
	@Log("信息详情")
	@ResponseBody
	@GetMapping("/list/info")
	Map<String, Object> info(Integer id, Integer muId){
		Map<String, Object> map = new HashMap<>();
		MsgDO userMsg = msgService.queryMsgDetails(id,muId);
		map.put("data", userMsg);
		map.put("msg", "");
		map.put("code", "0");
		return map;
	}
	
}
