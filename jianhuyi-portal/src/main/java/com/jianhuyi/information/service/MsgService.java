package com.jianhuyi.information.service;

import com.jianhuyi.information.domain.MsgDO;

import java.util.List;
import java.util.Map;

/**
 * 消息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-19 11:33:19
 */
public interface MsgService {
	
	MsgDO get(Integer id);

	List<MsgDO> queryMsgList(Long userId);
	
	MsgDO queryMsgDetails(Integer id,Integer muId);
	
}
