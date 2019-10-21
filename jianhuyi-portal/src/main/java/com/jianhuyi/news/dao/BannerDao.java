package com.jianhuyi.news.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.jianhuyi.news.domain.BannerDO;

/**
 * 轮播图表
 * @author wjl
 */
@Mapper
public interface BannerDao {

	BannerDO get(Long id);
	
	List<BannerDO> list(Map<String,Object> map);
	
}
