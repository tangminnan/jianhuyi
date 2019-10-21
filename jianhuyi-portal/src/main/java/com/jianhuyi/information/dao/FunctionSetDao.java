package com.jianhuyi.information.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.jianhuyi.information.domain.FunctionSetDO;

/**
 * 功能设置
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-14 15:23:48
 */
@Mapper
public interface FunctionSetDao {

	FunctionSetDO get(Long userId);
	
	int update(FunctionSetDO functionSet);
	
	int save(FunctionSetDO functionSet);

}
