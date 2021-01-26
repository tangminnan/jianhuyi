package com.jianhuyi.information.service;



import com.jianhuyi.common.utils.domain.HistoryDataBean;

import java.util.List;
import java.util.Map;

/**
 * 原始文件解析表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-01-21 09:41:13
 */
public interface DataInitService {

	HistoryDataBean get(Integer id);
	
	List<HistoryDataBean> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(HistoryDataBean dataInit);
	
	int update(HistoryDataBean dataInit);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
