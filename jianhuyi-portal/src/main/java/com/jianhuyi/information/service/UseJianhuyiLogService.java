package com.jianhuyi.information.service;

import com.jianhuyi.information.domain.UseJianhuyiLogDO;

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

  Map<String, Object> getByDayTime(Long userId);

  Map<String, Object> queryUserWeekHistory(Date start, Date end, Long userId);

  Map<String, Object> queryUserMonthHistory(Date start, Date end, Long userId);

  Map<String, Object> queryUserSeasonHistory(Date start, Date end, Long userId);

  Map<String, Object> queryUserYearHistory(Date start, Date end, Long userId);

  void saveList(List<UseJianhuyiLogDO> useJianhuyiLogDOList);

  List<UseJianhuyiLogDO> selectPersonAndDate(Map<String, Object> map);

  List<UseJianhuyiLogDO> getMyData(Map<String, Object> map);

  UseJianhuyiLogDO getUserJianHuYiYouXiao(Long userId, String createTime);

  UseJianhuyiLogDO getUserJianHuYiYouXiaoAll(Long userId, Date createTime, Date endTime);

  Date getMaxDate(Long userId);

  List<UseJianhuyiLogDO> getNearData(Long userId, Date date);

  List<UseJianhuyiLogDO> selectAllData(Map<String, Object> params);

  List<UseJianhuyiLogDO> countByUserIdAndDate();
}
