package com.jianhuyi.information.service;

import com.jianhuyi.information.domain.DataEverydayDO;

import java.util.List;
import java.util.Map;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-03-02 17:18:05
 */
public interface DataEverydayService {

  DataEverydayDO get(Integer id);

  List<DataEverydayDO> list(Map<String, Object> map);

  int count(Map<String, Object> map);

  int save(DataEverydayDO dataEveryday);

  int update(DataEverydayDO dataEveryday);

  int remove(Integer id);

  int batchRemove(Integer[] ids);

  int saveList(List<DataEverydayDO> everydayDOList);

  DataEverydayDO getByUserIdAndTime(Long userId, String substring);
}
