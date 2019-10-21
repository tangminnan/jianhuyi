package com.jianhuyi.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianhuyi.information.dao.ProductAdviseDao;
import com.jianhuyi.information.domain.ProductAdviseDO;
import com.jianhuyi.information.service.ProductAdviseService;

import java.util.List;
import java.util.Map;




@Service
public class ProductAdviseServiceImpl implements ProductAdviseService {
	@Autowired
	private ProductAdviseDao productAdviseDao;
	
	@Override
	public ProductAdviseDO get(Integer id){
		return productAdviseDao.get(id);
	}
	
	@Override
	public List<ProductAdviseDO> list(Map<String, Object> map){
		return productAdviseDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return productAdviseDao.count(map);
	}
	
	@Override
	public int save(ProductAdviseDO productAdvise){
		return productAdviseDao.save(productAdvise);
	}
	
	@Override
	public int update(ProductAdviseDO productAdvise){
		return productAdviseDao.update(productAdvise);
	}
	
	@Override
	public int remove(Integer id){
		return productAdviseDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return productAdviseDao.batchRemove(ids);
	}
	
}
