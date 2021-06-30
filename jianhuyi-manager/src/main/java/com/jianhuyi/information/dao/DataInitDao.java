package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.DataInitDO;
import com.jianhuyi.information.domain.DistanceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 原始文件解析表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-04-19 17:27:20
 */
@Mapper
public interface DataInitDao {

  DataInitDO get(Integer id);

  List<DataInitDO> list(Map<String, Object> map);

  int count(Map<String, Object> map);

  int save(DataInitDO dataInit);

  int update(DataInitDO dataInit);

  int remove(Integer id);

  int batchRemove(Integer[] ids);

    List<DistanceDO> listDistanceDO(Map<String, Object> parmasMap);
}
