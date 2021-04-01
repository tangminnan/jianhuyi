package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.domain.UseRemindsDO;
import com.jianhuyi.information.domain.UseTimeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 检测记录表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */
@Mapper
public interface UseJianhuyiLogDao {

  UseJianhuyiLogDO get(Integer id);

  List<UseJianhuyiLogDO> list(Map<String, Object> map);

  int count(Map<String, Object> map);

  int save(UseJianhuyiLogDO useJianhuyiLog);

  int update(UseJianhuyiLogDO useJianhuyiLog);

  UseJianhuyiLogDO getByTime(@Param("userId") Long userId);

  UseJianhuyiLogDO getDayRemind(@Param("userId") Long userId);

  List<UseJianhuyiLogDO> getMonthAndWeekByTime(
      @Param("start") Date start, @Param("end") Date end, @Param("userId") Long userId);

  List<UseJianhuyiLogDO> getMonthAndWeekOutduration(
      @Param("start") Date start, @Param("end") Date end, @Param("userId") Long userId);

  List<UseJianhuyiLogDO> getMonthAndWeekRemind(
      @Param("start") Date start, @Param("end") Date end, @Param("userId") Long userId);

  UseJianhuyiLogDO getOutduration(@Param("userId") Long userId);

  UseJianhuyiLogDO queryUserWeekRecord(
      @Param("saveTime") String saveTime, @Param("userId") Long userId);

  List<UseJianhuyiLogDO> queryUserWeekRecordBetween(
      @Param("start") Date startTime, @Param("end") Date endTime, @Param("userId") Long userId);

  List<UseJianhuyiLogDO> queryUserRecordBetweenSum(
      @Param("start") Date startTime, @Param("end") Date endTime, @Param("userId") Long userId);

  UseJianhuyiLogDO queryOutdoorsDuration(
      @Param("saveTime") String saveTime, @Param("userId") Long userId);

  UseRemindsDO queryRemind(@Param("remindsTime") String string, @Param("userId") Long userId);

  UseTimeDO queryUseTime(@Param("use_time") String string, @Param("userId") Long userId);

  List<UseJianhuyiLogDO> getOutdurationYear(
      @Param("start") Date startTime, @Param("end") Date endTime, @Param("userId") Long userId);

  List<UseTimeDO> getUseJianhuyiTimeYear(
      @Param("start") Date startTime, @Param("end") Date endTime, @Param("userId") Long userId);

  List<UseRemindsDO> getRemindYear(
      @Param("start") Date startTime, @Param("end") Date endTime, @Param("userId") Long userId);

  void saveList(@Param("useJianhuyiLogDOList") List<UseJianhuyiLogDO> useJianhuyiLogDOList);

  LinkedList<UseJianhuyiLogDO> selectAllData(Map<String, Object> map);

  List<UseJianhuyiLogDO> getByDay(@Param("date") String string, @Param("userId") Long userId);

  UseJianhuyiLogDO getRemindByUseidByday(
      @Param("date") String string, @Param("userId") Long userId);

  UseJianhuyiLogDO getUseByUseidByday(@Param("date") String string, @Param("userId") Long userId);

  List<UseJianhuyiLogDO> getAllDate(
      @Param("start") Date parse, @Param("end") Date end, @Param("userId") Long userId);

  LinkedList<UseJianhuyiLogDO> selectPersonAndDate(Map<String, Object> map);

  LinkedList<UseJianhuyiLogDO> selectDataEvery(Map<String, Object> map);

  List<UseJianhuyiLogDO> getMyData(Map<String, Object> map);

  UseJianhuyiLogDO getUserJianHuYiYouXiao(
      @Param("userId") Long userId, @Param("createTime") String createTime);

  UseJianhuyiLogDO getUserJianHuYiYouXiaoAll(
      @Param("userId") Long userId,
      @Param("createTime") Date createTime,
      @Param("endTime") Date endTime);

  Date getMaxDate(Long userId);

  List<UseJianhuyiLogDO> getNearData(@Param("userId") Long userId, @Param("date") Date date);

  List<UseJianhuyiLogDO> countByUserIdAndDate(
      @Param("userId") Long userId, @Param("date") String time);
}
