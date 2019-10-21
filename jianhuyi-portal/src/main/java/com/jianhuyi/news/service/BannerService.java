package com.jianhuyi.news.service;

import java.util.List;
import java.util.Map;

import com.jianhuyi.news.domain.BannerDO;

/**
 * 文件上传
 * 
 * @author wjl
 */
public interface BannerService {
	
	BannerDO get(Long id);
	
	List<BannerDO> list(Map<String, Object> map);
	
}
