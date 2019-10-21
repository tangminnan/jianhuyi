package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.MsgDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 消息表
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-19 11:33:19
 */
@Mapper
public interface MsgDao {

	MsgDO get(Integer id);
	
	List<MsgDO> queryMsgList(Long userId);
	
	int updateUserMsg(Integer id);

}
