package com.jianhuyi.information.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.jianhuyi.information.domain.VersionUpdateDO;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-02-11 17:10:35
 */
@Mapper
public interface VersionUpdateDao {

	VersionUpdateDO get(Long id);

	List<VersionUpdateDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(VersionUpdateDO versionUpdate);

	int update(VersionUpdateDO versionUpdate);

	int remove(Long id);

	int batchRemove(Long[] ids);

	VersionUpdateDO versionCheck(Integer type);
}
