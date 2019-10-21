package com.jianhuyi.news.service;


import java.util.List;
import java.util.Map;

import com.jianhuyi.news.domain.NewsDO;

/**
 * 咨讯表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-15 16:39:38
 */
public interface NewsService {
	
	NewsDO get(Integer id);
	
	List<NewsDO> list(Map<String, Object> map);

}
