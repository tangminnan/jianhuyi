package com.jianhuyi.information.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.jianhuyi.information.domain.ProductAdviseDO;

/**
 * 产品建议
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 17:40:54
 */
@Mapper
public interface ProductAdviseDao {

	ProductAdviseDO get(Integer id);
	
	List<ProductAdviseDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ProductAdviseDO productAdvise);
	
	int update(ProductAdviseDO productAdvise);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
