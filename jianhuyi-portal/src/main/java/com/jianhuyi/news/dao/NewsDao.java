package com.jianhuyi.news.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.jianhuyi.news.domain.NewsDO;

/**
 * 咨讯表
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-15 16:39:38
 */
@Mapper
public interface NewsDao {

	NewsDO get(Long id);
	
	List<NewsDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(NewsDO news);
	
	int update(NewsDO news);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
