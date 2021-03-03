package com.jianhuyi.information.service;

import com.jianhuyi.information.domain.UseTimeDO;

import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-07-22 09:43:53
 */
public interface UseTimeService {

  UseTimeDO get(Long id);

  List<UseTimeDO> list(Map<String, Object> map);

  int count(Map<String, Object> map);

  int save(UseTimeDO useTime);

  int update(UseTimeDO useTime);

  int remove(Long id);

  int batchRemove(Long[] ids);

  Map getSNCount(Integer userId, String saveTime);

  List<UseTimeDO> getTodayData(Integer userId, String saveTime);
}
