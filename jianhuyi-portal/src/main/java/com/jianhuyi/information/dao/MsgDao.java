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

	MsgDO get(Long id);
	
	List<MsgDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(MsgDO msg);
	
	int update(MsgDO msg);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	List<MsgDO> queryUserMsgList(Long userId);
	List<MsgDO> queryUserMsgListNull(Map<String,Object> map);
	
	MsgDO queryUserMsgId(Long id);

}
