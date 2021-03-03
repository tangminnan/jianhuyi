package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.domain.UseTimeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-13 10:52:52
 */
@Mapper
public interface UseTimeDao {

  UseTimeDO get(Long id);

  List<UseTimeDO> list(Map<String, Object> map);

  int count(Map<String, Object> map);

  int save(UseTimeDO useTime);

  int update(UseTimeDO useTime);

  int remove(Long id);

  int batchRemove(Long[] ids);

  void saveList(@Param("useTimeDOList") List<UseTimeDO> useTimeDOList);

  UseJianhuyiLogDO getTodayUse(@Param("userId") Long userId);

  Map getSNCount(@Param("userId") Long userId, @Param("time") String time);

  List<UseTimeDO> getTodayData(@Param("userId") Long userId, @Param("time") String time);
}
