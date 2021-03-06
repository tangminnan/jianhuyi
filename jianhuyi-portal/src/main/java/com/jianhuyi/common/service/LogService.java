package com.jianhuyi.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jianhuyi.common.domain.LogDO;
import com.jianhuyi.common.domain.PageDO;
import com.jianhuyi.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
