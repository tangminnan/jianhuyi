package com.jianhuyi.information.service;

import java.util.List;
import java.util.Map;

import com.jianhuyi.information.domain.FunctionSetDO;

/**
 * 功能设置
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-14 15:23:48
 */
public interface FunctionSetService {
	
	FunctionSetDO get(Long userId);
	
	int update(FunctionSetDO functionSet);
	
	int save(FunctionSetDO functionSet);


}
