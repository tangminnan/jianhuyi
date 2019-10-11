package com.jianhuyi.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianhuyi.news.dao.NewsDao;
import com.jianhuyi.news.domain.NewsDO;
import com.jianhuyi.news.service.NewsService;

import java.util.List;
import java.util.Map;




@Service
public class NewsServiceImpl implements NewsService {
	@Autowired
	private NewsDao newsDao;
	
	@Override
	public NewsDO get(Long id){
		return newsDao.get(id);
	}
	
	@Override
	public List<NewsDO> list(Map<String, Object> map){
		return newsDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return newsDao.count(map);
	}
	
	@Override
	public int save(NewsDO news){
		return newsDao.save(news);
	}
	
	@Override
	public int update(NewsDO news){
		return newsDao.update(news);
	}
	
	@Override
	public int remove(Integer id){
		return newsDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return newsDao.batchRemove(ids);
	}
	
}
