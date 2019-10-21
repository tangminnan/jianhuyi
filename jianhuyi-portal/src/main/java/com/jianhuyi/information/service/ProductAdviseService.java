package com.jianhuyi.information.service;

import java.util.List;
import java.util.Map;

import com.jianhuyi.information.domain.ProductAdviseDO;

/**
 * 产品建议
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 17:40:54
 */
public interface ProductAdviseService {
	
	ProductAdviseDO get(Integer id);
	
	List<ProductAdviseDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProductAdviseDO productAdvise);
	
	int update(ProductAdviseDO productAdvise);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
