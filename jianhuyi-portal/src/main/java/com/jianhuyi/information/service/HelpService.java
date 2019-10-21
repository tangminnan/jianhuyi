package com.jianhuyi.information.service;

import java.util.List;
import java.util.Map;

import com.jianhuyi.information.domain.HelpDO;

/**
 * 帮助表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 17:00:55
 */
public interface HelpService {
	
	HelpDO get(Integer id);
	
	List<HelpDO> list(Map<String, Object> map);
	
}
