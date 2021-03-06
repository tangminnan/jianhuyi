package com.jianhuyi.information.service;

import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.domain.UseTimeDO;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-13 10:52:52
 */
public interface UseTimeService {

  UseTimeDO get(Long id);

  List<UseTimeDO> list(Map<String, Object> map);

  int count(Map<String, Object> map);

  int save(UseTimeDO useTime);

  int update(UseTimeDO useTime);

  int remove(Long id);

  int batchRemove(Long[] ids);

  void saveList(List<UseTimeDO> useTimeDOList);

  UseJianhuyiLogDO getTodayUse(Long userId);

  LinkedList<UseTimeDO> getTodayData(Long userId, String time);

  Map getSNCount(Long userId, String time);
}
