package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.DataEverydayDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-03-02 17:18:05
 */
@Mapper
public interface DataEverydayDao {

  DataEverydayDO get(Integer id);

  List<DataEverydayDO> list(Map<String, Object> map);

  int count(Map<String, Object> map);

  int save(DataEverydayDO dataEveryday);

  int update(DataEverydayDO dataEveryday);

  int remove(Integer id);

  int batchRemove(Integer[] ids);

  int saveList(@Param("everydayDOList") List<DataEverydayDO> everydayDOList);

    DataEverydayDO getByUserIdAndTime(@Param("userId") Long userId,@Param("date") String date);
}
