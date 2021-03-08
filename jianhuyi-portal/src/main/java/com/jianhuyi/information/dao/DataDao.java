package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.DataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-25 09:40:41
 */
@Mapper
public interface DataDao {

  DataDO get(Long id);

  List<DataDO> list(Map<String, Object> map);

  int count(Map<String, Object> map);

  int save(DataDO data);

  int update(DataDO data);

  int remove(Long id);

  int batchRemove(Long[] ids);

  int saveList(@Param("dataDOList") List<DataDO> dataDOList);

  List<DataDO> getList(@Param("userId") Long userId, @Param("startTime") String time);
}
