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
	public NewsDO get(Integer id){
		return newsDao.get(id);
	}
	
	@Override
	public List<NewsDO> list(Map<String, Object> map){
		return newsDao.list(map);
	}
	
}
