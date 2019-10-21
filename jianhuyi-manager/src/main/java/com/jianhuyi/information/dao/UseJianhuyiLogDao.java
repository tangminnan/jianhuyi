package com.jianhuyi.information.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.jianhuyi.information.domain.UseJianhuyiLogDO;

/**
 * 检测记录表
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */
@Mapper
public interface UseJianhuyiLogDao {

	UseJianhuyiLogDO get(Integer id);
	
	List<UseJianhuyiLogDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UseJianhuyiLogDO useJianhuyiLog);
	
	int update(UseJianhuyiLogDO useJianhuyiLog);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
