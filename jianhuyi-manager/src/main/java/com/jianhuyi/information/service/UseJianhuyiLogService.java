package com.jianhuyi.information.service;

import com.jianhuyi.information.domain.UseJianhuyiLogDO;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 检测记录表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */
public interface UseJianhuyiLogService {

    UseJianhuyiLogDO get(Integer id);

    List<UseJianhuyiLogDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UseJianhuyiLogDO useJianhuyiLog);

    int update(UseJianhuyiLogDO useJianhuyiLog);

    int remove(Integer id);

    int batchRemove(Integer[] ids);

    Map<String, Collection<Double>> getlineData(Date start, Date end, Long userId);

    List<UseJianhuyiLogDO> listDetail(Map<String, Object> params);

    int counts(Map<String, Object> map);

    Map<String, Collection<Double>> seasonData(Date start, Date end, Long userId) throws Exception;

    Map<String, Collection<Double>> yearData(Date start, Date end, Long userId) throws Exception;

    List<UseJianhuyiLogDO> selectData(Date start, Date end, Map<String, Object> params);

    List<Map<String, Object>> exeList(Map<String, Object> map) throws ParseException;
}
