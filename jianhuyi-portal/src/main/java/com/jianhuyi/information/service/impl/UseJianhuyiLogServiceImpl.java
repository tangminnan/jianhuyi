package com.jianhuyi.information.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.information.controller.AvgUtil;
import com.jianhuyi.information.dao.UseJianhuyiLogDao;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.domain.UseTimeDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;
import com.jianhuyi.information.service.UseRemindsService;
import com.jianhuyi.information.service.UseTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jianhuyi.common.utils.DateUtils.format;

@Service
public class UseJianhuyiLogServiceImpl implements UseJianhuyiLogService {
  @Autowired private UseJianhuyiLogDao useJianhuyiLogDao;
  @Autowired private UseRemindsService useRemindsService;
  @Autowired private UseTimeService useTimeService;

  @Override
  public UseJianhuyiLogDO get(Integer id) {
    return useJianhuyiLogDao.get(id);
  }

  @Override
  public List<UseJianhuyiLogDO> list(Map<String, Object> map) {
    return useJianhuyiLogDao.list(map);
  }

  @Override
  public int count(Map<String, Object> map) {
    return useJianhuyiLogDao.count(map);
  }

  @Override
  public int save(UseJianhuyiLogDO useJianhuyiLog) {
    return useJianhuyiLogDao.save(useJianhuyiLog);
  }

  @Override
  public int update(UseJianhuyiLogDO useJianhuyiLog) {
    return useJianhuyiLogDao.update(useJianhuyiLog);
  }

  JSONObject jsonObject = null;

  // 今日的数据
  @Override
  public Map<String, Object> getByDayTime(Long userId) {
    Map<String, Object> mapP = new HashMap<String, Object>();
    Map<String, Object> params = new HashMap<String, Object>();
    UseJianhuyiLogDO useJianhuyiLogDO = new UseJianhuyiLogDO();
    params.put("userId", userId);
    List<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogDao.selectAllData(params);
    UseJianhuyiLogDO newUseJianhuyiLogDO = new UseJianhuyiLogDO();
    newUseJianhuyiLogDO.setUserId(Integer.parseInt(userId.toString()));

    AvgUtil.getINSTANCE().putUserJianhuyi(useJianhuyiLogDOList);
    UseJianhuyiLogDO useJianhuyiLogDO1 = AvgUtil.getINSTANCE().getAvgReadTime();

    newUseJianhuyiLogDO.setReadDuration(useJianhuyiLogDO1.getReadDuration());
    newUseJianhuyiLogDO.setLookPhoneDuration(useJianhuyiLogDO1.getLookPhoneDuration());
    newUseJianhuyiLogDO.setLookTvComputerDuration(useJianhuyiLogDO1.getLookTvComputerDuration());
    newUseJianhuyiLogDO.setReadDistance(useJianhuyiLogDO1.getReadDistance());
    newUseJianhuyiLogDO.setReadLight(useJianhuyiLogDO1.getReadLight());
    newUseJianhuyiLogDO.setSitTilt(useJianhuyiLogDO1.getSitTilt());
    newUseJianhuyiLogDO.setAllreadDuration(useJianhuyiLogDO1.getAllreadDuration());
    newUseJianhuyiLogDO.setOutdoorsDuration(useJianhuyiLogDO1.getOutdoorsDuration());

    // 序号为0的个数 0的个数代表重新计算和开机
    Map mappp = useTimeService.getSNCount(userId);
    List<UseTimeDO> useTimeDOList = useTimeService.getTodayData(userId);
    Optional.ofNullable(mappp)
        .ifPresent(
            m -> {
              jsonObject = (JSONObject) JSONObject.toJSON(m);
            });
    String num = jsonObject.get("num").toString();
    String sum = jsonObject.get("sum").toString();
    UseJianhuyiLogDO usetime =
        AvgUtil.getINSTANCE()
            .getSNCount(Integer.parseInt(num), Integer.parseInt(sum), useTimeDOList);

    newUseJianhuyiLogDO.setEffectiveTime(usetime.getEffectiveTime());
    newUseJianhuyiLogDO.setNoneffectiveTime(usetime.getNoneffectiveTime());
    newUseJianhuyiLogDO.setCoverTime(usetime.getCoverTime());
    newUseJianhuyiLogDO.setRunningTime(usetime.getRunningTime());

    UseJianhuyiLogDO useJianhuyiLogDO2 = useRemindsService.getTodayReminds(userId);

    if (useJianhuyiLogDO2 != null && useJianhuyiLogDO2.getRemind() != null) {
      newUseJianhuyiLogDO.setRemind(useJianhuyiLogDO2.getRemind());
    } else {
      newUseJianhuyiLogDO.setRemind(0);
    }

    mapP.put("data", newUseJianhuyiLogDO);
    mapP.put("msg", "操作成功");
    mapP.put("code", 0);
    return mapP;
  }

  // 周记录
  @Override
  public Map<String, Object> queryUserWeekHistory(Date start, Date end, Long userId) {
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> mapP = new HashMap<String, Object>();
    try {
      Object weekData = getlineData(start, end, userId);
      map.put("lineChart", weekData);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    mapP.put("data", map);
    mapP.put("msg", "操作成功");
    mapP.put("code", 0);

    return mapP;
  }

  // 月记录
  @Override
  public Map<String, Object> queryUserMonthHistory(Date start, Date end, Long userId) {
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> mapP = new HashMap<String, Object>();
    // 提醒
    Integer avgRemind = 0;
    Integer remindCount = 0;
    // 户外
    Double avgOutduration = 0.0;
    Integer outdurationCount = 0;
    // 平均日阅读时长
    Double avgReadDuration = 0.0;
    Integer readDurationCount = 0;

    Map<String, Object> mapData = null;
    try {
      mapData = getlineData(start, end, userId);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    mapP.put("data", mapData);
    mapP.put("msg", "操作成功");
    mapP.put("code", 0);

    return mapP;
  }

  private Map<String, Object> getlineData(Date start, Date end, Long userId) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Map<String, Double> avgReadDuration = new LinkedHashMap<String, Double>();
    Map<String, Double> outdoorsDuration = new LinkedHashMap<String, Double>();
    Map<String, Double> avgReadDistance = new LinkedHashMap<String, Double>();
    Map<String, Double> avgReadLight = new LinkedHashMap<String, Double>();
    Map<String, Double> avgLookPhoneDuration = new LinkedHashMap<String, Double>();
    Map<String, Double> avgLookTvComputerDuration = new LinkedHashMap<String, Double>();
    Map<String, Double> avgSitTilt = new LinkedHashMap<String, Double>();
    Map<String, Double> avgUseJianhuyiDuration = new LinkedHashMap<String, Double>();
    Map<String, Integer> remind = new LinkedHashMap<String, Integer>();
    Map<String, Double> map = new LinkedHashMap<String, Double>();

    // 用来保留两位小数
    DecimalFormat df = new DecimalFormat("#.##");
    // 保留整数
    DecimalFormat df1 = new DecimalFormat("#");

    // 有数据的天数
    Integer readDurationCount = 0;
    Integer outdoorsDurationCount = 0;
    Integer readDistanceCount = 0;
    Integer readLightCount = 0;
    Integer lookPhoneDurationCount = 0;
    Integer lookTvComputerDurationCount = 0;
    Integer sitTiltCount = 0;
    Integer useJianhuyiDurationCount = 0;
    Integer remindCount = 0;

    // 总平均
    Double AllreadDuration = 0.0;
    Double AlloutdoorsDuration = 0.0;
    Double AllreadDistance = 0.0;
    Double AllreadLight = 0.0;
    Double AlllookPhoneDuration = 0.0;
    Double AlllookTvComputerDuration = 0.0;
    Double AllsitTilt = 0.0;
    Double AlluseJianhuyiDuration = 0.0;
    Integer Allremind = 0;

    while (start.compareTo(end) <= 0) {
      map.put(format(start), 0.0);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(start);
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      start = calendar.getTime();
    }
    List<String> list = new ArrayList<String>();

    Set<String> keySet = map.keySet();
    Iterator<String> iterator = keySet.iterator();
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }
    // 循环日期
    for (String string : list) {
      Map<String, Object> params = new HashMap<>();
      params.put("userId", userId);
      params.put("startTime", string);
      params.put("endTime", string);

      List<UseJianhuyiLogDO> useJianhuyiLogDOList = getDataByDay(params);
      UseJianhuyiLogDO useJianhuyiLogDO1 = new UseJianhuyiLogDO();
      if (useJianhuyiLogDOList != null && useJianhuyiLogDOList.size() > 0) {
        useJianhuyiLogDO1 = useJianhuyiLogDOList.get(0);
      }

      if (useJianhuyiLogDO1.getRemind() != null && useJianhuyiLogDO1.getRemind() > 0) {
        remind.put(string, useJianhuyiLogDO1.getRemind());
        Allremind += useJianhuyiLogDO1.getRemind();
        remindCount++;
      } else {
        remind.put(string, 0);
      }

      if (useJianhuyiLogDO1.getUserDurtion() != null && useJianhuyiLogDO1.getUserDurtion() > 0) {
        avgUseJianhuyiDuration.put(string, useJianhuyiLogDO1.getUserDurtion());
        AlluseJianhuyiDuration += useJianhuyiLogDO1.getUserDurtion();
        useJianhuyiDurationCount++;
      } else {
        avgUseJianhuyiDuration.put(string, 0.0);
      }
      if (useJianhuyiLogDO1.getOutdoorsDuration() != null) {
        outdoorsDuration.put(string, useJianhuyiLogDO1.getOutdoorsDuration());
        AlloutdoorsDuration += useJianhuyiLogDO1.getOutdoorsDuration();
        if (useJianhuyiLogDO1.getOutdoorsDuration() > 0) outdoorsDurationCount++;
      } else {
        outdoorsDuration.put(string, 0.0);
      }

      if (useJianhuyiLogDO1.getReadDuration() != null) {
        avgReadDuration.put(string, useJianhuyiLogDO1.getReadDuration());
        AllreadDuration += useJianhuyiLogDO1.getReadDuration();
        if (useJianhuyiLogDO1.getReadDuration() > 0) {
          readDurationCount++;
        }
      } else {
        avgReadDuration.put(string, 0.0);
      }

      if (useJianhuyiLogDO1.getLookPhoneDuration() != null) {
        avgLookPhoneDuration.put(string, useJianhuyiLogDO1.getLookPhoneDuration());
        AlllookPhoneDuration += useJianhuyiLogDO1.getLookPhoneDuration();
        if (useJianhuyiLogDO1.getLookPhoneDuration() > 0) lookPhoneDurationCount++;
      } else {
        avgLookPhoneDuration.put(string, 0.0);
      }
      if (useJianhuyiLogDO1.getLookTvComputerDuration() != null) {
        avgLookTvComputerDuration.put(string, useJianhuyiLogDO1.getLookTvComputerDuration());
        AlllookTvComputerDuration += useJianhuyiLogDO1.getLookTvComputerDuration();
        if (useJianhuyiLogDO1.getLookTvComputerDuration() > 0) lookTvComputerDurationCount++;
      } else {
        avgLookTvComputerDuration.put(string, 0.0);
      }

      if (useJianhuyiLogDO1.getSitTilt() != null) {
        avgSitTilt.put(string, useJianhuyiLogDO1.getSitTilt());
        if (useJianhuyiLogDO1.getSitTilt() > 0) {
          AllsitTilt += useJianhuyiLogDO1.getSitTilt();
          sitTiltCount++;
        }
      } else {
        avgSitTilt.put(string, 0.0);
      }
      if (useJianhuyiLogDO1.getReadLight() != null) {
        avgReadLight.put(string, useJianhuyiLogDO1.getReadLight());
        if (useJianhuyiLogDO1.getReadLight() > 0) {
          AllreadLight += useJianhuyiLogDO1.getReadLight();
          readLightCount++;
        }
      } else {
        avgReadLight.put(string, 0.0);
      }
      if (useJianhuyiLogDO1.getReadDistance() != null) {
        avgReadDistance.put(string, useJianhuyiLogDO1.getReadDistance());
        if (useJianhuyiLogDO1.getReadDistance() > 0) {
          AllreadDistance += useJianhuyiLogDO1.getReadDistance();
          readDistanceCount++;
        }
      } else {
        avgReadDistance.put(string, 0.0);
      }
    }
    Map<String, Object> all = new HashMap<String, Object>();
    if (outdoorsDurationCount > 0)
      all.put("AlloutdoorsDuration", df.format(AlloutdoorsDuration / outdoorsDurationCount));
    else all.put("AlloutdoorsDuration", 0.0);
    if (readDistanceCount > 0)
      all.put("AllreadDistance", df.format(AllreadDistance / readDistanceCount));
    else all.put("AllreadDistance", 0.0);
    if (lookPhoneDurationCount > 0)
      all.put("AlllookPhoneDuration", df.format(AlllookPhoneDuration / lookPhoneDurationCount));
    else all.put("AlllookPhoneDuration", 0.0);
    if (lookTvComputerDurationCount > 0)
      all.put(
          "AlllookTvComputerDuration",
          df.format(AlllookTvComputerDuration / lookTvComputerDurationCount));
    else all.put("AlllookTvComputerDuration", 0.0);
    if (readDurationCount > 0)
      all.put("AllreadDuration", df.format(AllreadDuration / readDurationCount));
    else all.put("AllreadDuration", 0.0);
    if (readLightCount > 0) all.put("AllreadLight", df.format(AllreadLight / readLightCount));
    else all.put("AllreadLight", 0.0);
    if (sitTiltCount > 0) all.put("AllsitTilt", df.format(AllsitTilt / sitTiltCount));
    else all.put("AllsitTilt", 0.0);

    if (remindCount > 0) all.put("Allremind", df1.format(Allremind / remindCount));
    else all.put("Allremind", 0);
    if (useJianhuyiDurationCount > 0)
      all.put("AlluseJianhuyiDuration", AlluseJianhuyiDuration / useJianhuyiDurationCount);
    else all.put("AlluseJianhuyiDuration", 0.0);

    Map<String, Object> avg = new HashMap<String, Object>();
    avg.put("avgReadDuration", avgReadDuration.values());
    avg.put("outdoorsDuration", outdoorsDuration.values());
    avg.put("avgReadDistance", avgReadDistance.values());
    avg.put("avgReadLight", avgReadLight.values());
    avg.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
    avg.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
    avg.put("avgSitTilt", avgSitTilt.values());
    avg.put("remind", remind.values());
    avg.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());

    Map<String, Object> result = new HashMap<>();
    // 数据集合
    result.put("mapP", avg);
    result.put("map2", all);
    // result.put("map2", map2);
    return result;
  }

  // 季记录
  @Override
  public Map<String, Object> queryUserSeasonHistory(Date start, Date end, Long userId) {
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> mapP = new HashMap<String, Object>();
    try {
      Map<String, Object> seasonData = seasonData(start, end, userId);
      map.put("lineChart", seasonData);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    mapP.put("data", map);
    mapP.put("msg", "操作成功");
    mapP.put("code", 0);

    return mapP;
  }

  private Map<String, Object> seasonData(Date start, Date end, Long userId) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // 用来保留两位小数
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat df1 = new DecimalFormat("#");

    Map<String, Double> map = new LinkedHashMap<String, Double>();
    Map<Integer, Double> avgReadDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> outdoorsDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgReadDistance = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgReadLight = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgLookPhoneDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgLookTvComputerDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgSitTilt = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgUseJianhuyiDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Integer> remindCount = new LinkedHashMap<>();

    // 有数据的天数
    Integer readDurationCount = 0;
    Integer outdoorsDurationCount = 0;
    Integer readDistanceCount = 0;
    Integer readLightCount = 0;
    Integer lookPhoneDurationCount = 0;
    Integer lookTvComputerDurationCount = 0;
    Integer sitTiltCount = 0;
    Integer useJianhuyiDurationCount = 0;
    Integer remindCounts = 0;

    // 总平均
    Double AllreadDuration = 0.0;
    Double AlloutdoorsDuration = 0.0;
    Double AllreadDistance = 0.0;
    Double AllreadLight = 0.0;
    Double AlllookPhoneDuration = 0.0;
    Double AlllookTvComputerDuration = 0.0;
    Double AllsitTilt = 0.0;
    Double AlluseJianhuyiDuration = 0.0;
    Integer Allremind = 0;

    while (start.compareTo(end) < 0) {
      map.put(format(start), 0.0);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(start);
      calendar.add(Calendar.DAY_OF_MONTH, 7);
      start = calendar.getTime();
    }
    List<String> list = new ArrayList<String>();
    Set<String> set1 = map.keySet();
    Iterator<String> it1 = set1.iterator();
    while (it1.hasNext()) {
      //  System.out.println(it1.next());
      list.add(it1.next());
    }
    for (int i = 0; i < 12; i++) {

      readDurationCount = 0;
      outdoorsDurationCount = 0;
      readDistanceCount = 0;
      readLightCount = 0;
      lookPhoneDurationCount = 0;
      lookTvComputerDurationCount = 0;
      sitTiltCount = 0;
      useJianhuyiDurationCount = 0;
      remindCounts = 0;

      // 总平均
      AllreadDuration = 0.0;
      AlloutdoorsDuration = 0.0;
      AllreadDistance = 0.0;
      AllreadLight = 0.0;
      AlllookPhoneDuration = 0.0;
      AlllookTvComputerDuration = 0.0;
      AllsitTilt = 0.0;
      AlluseJianhuyiDuration = 0.0;
      Allremind = 0;

      List<UseJianhuyiLogDO> between = new ArrayList<UseJianhuyiLogDO>();
      List<UseJianhuyiLogDO> dates = new ArrayList<UseJianhuyiLogDO>();
      if (i == 13) {
        /*每月阅读状态统计*/
        between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
        dates = useJianhuyiLogDao.getAllDate(sdf.parse(list.get(i)), end, userId);
      } else {
        between =
            useJianhuyiLogDao.queryUserWeekRecordBetween(
                sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
        dates =
            useJianhuyiLogDao.getAllDate(
                sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
      }

      SimpleDateFormat sdf11 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

      // 遍历用户id和日期
      for (UseJianhuyiLogDO useJianhuyiLogDO : dates) {

        UseJianhuyiLogDO remindUser =
            useJianhuyiLogDao.getRemindByUseidByday(useJianhuyiLogDO.getSaveTime(), userId);
        UseJianhuyiLogDO userTime =
            useJianhuyiLogDao.getUseByUseidByday(useJianhuyiLogDO.getSaveTime(), userId);

        if (remindUser != null) {
          if (remindUser.getRemind() != null && remindUser.getRemind() > 0) {
            Allremind += remindUser.getRemind();
            if (remindUser.getRemind() > 0) {
              remindCounts++;
            }
          }
        }

        if (userTime != null) {
          if (userTime.getUseJianhuyiDuration() != null && userTime.getUseJianhuyiDuration() > 0) {
            AlluseJianhuyiDuration += userTime.getUseJianhuyiDuration();
            if (userTime.getUseJianhuyiDuration() > 0) useJianhuyiDurationCount++;
          }
        }

        // UseJianhuyiLogDO useJianhuyiLogDO = useJianhuyiLogDOList.get(1);
        String date = "";
        Double readDurtion = null;
        Double allDurtion = null;
        int count = 0;
        Double readDistance = 0.0;
        Double readDuration = 0.0;
        Double sitTilt = 0.0;
        Double lookPhoneDuration = 0.0;
        int lookPhoneCount = 0;
        Double lookTvComputerDuration = 0.0;
        int lookScreenCount = 0;
        Double readLight = 0.0;
        Double outdoorsDuration11 = 0.0;

        Double readDurtionNum = 0.0;
        Double outdoorsDurationday = 0.0;

        boolean flag = true;
        for (UseJianhuyiLogDO jianhuyiLogDO : between) {
          Double oneReadDurtion = jianhuyiLogDO.getReadDuration();
          if (jianhuyiLogDO.getSaveTime().contains(useJianhuyiLogDO.getSaveTime())) {
            if (jianhuyiLogDO.getStatus() != null && jianhuyiLogDO.getStatus() == 1) {
              readDistance += jianhuyiLogDO.getReadDistance() * jianhuyiLogDO.getReadDuration();
              sitTilt += jianhuyiLogDO.getSitTilt() * jianhuyiLogDO.getReadDuration();
              readLight += jianhuyiLogDO.getReadLight() * jianhuyiLogDO.getReadDuration();

              readDurtionNum += jianhuyiLogDO.getReadDuration();

              readDuration += jianhuyiLogDO.getReadDuration();
              lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
              lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
              if (!date.equals("")) {
                try {
                  long getReadDuration = (long) (jianhuyiLogDO.getReadDuration() * 60 * 1000);
                  long difference =
                      (sdf11.parse(date).getTime()
                          - (sdf11.parse(jianhuyiLogDO.getSaveTime()).getTime() + getReadDuration));
                  long minute = difference / (1000 * 60);
                  // 间隔大于3分钟，视为2次
                  if (minute > 3) {
                    if (flag) {
                      count += 2;
                      if (jianhuyiLogDO.getLookPhoneDuration() != null
                          && jianhuyiLogDO.getLookPhoneDuration() > 0) {
                        lookPhoneCount += 2;
                        lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
                      }
                      if (jianhuyiLogDO.getLookTvComputerDuration() != null
                          && jianhuyiLogDO.getLookTvComputerDuration() > 0) {
                        lookScreenCount += 2;
                        lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
                      }

                      if (allDurtion > 3) {
                        allDurtion += jianhuyiLogDO.getReadDuration();
                      }
                    } else {
                      count++;
                      if (jianhuyiLogDO.getLookPhoneDuration() != null
                          && jianhuyiLogDO.getLookPhoneDuration() > 0) {
                        lookPhoneCount++;
                        lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
                      }
                      if (jianhuyiLogDO.getLookTvComputerDuration() != null
                          && jianhuyiLogDO.getLookTvComputerDuration() > 0) {
                        lookScreenCount++;
                        lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
                      }
                      if (allDurtion > 3) {
                        allDurtion += jianhuyiLogDO.getReadDuration();
                      }
                    }
                  }
                  // 间隔小于3分钟，视为1次
                  else {
                    allDurtion += jianhuyiLogDO.getReadDuration();
                    lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
                    lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
                  }
                } catch (ParseException e) {
                  e.printStackTrace();
                }
              } else {
                allDurtion = jianhuyiLogDO.getReadDuration();
                lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
                lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
              }
              date = jianhuyiLogDO.getSaveTime();

            } else {
              outdoorsDurationday += jianhuyiLogDO.getOutdoorsDuration();
            }
            flag = false;
          }
        }
        if (outdoorsDurationday > 0) {
          AlloutdoorsDuration += Double.parseDouble(df.format(outdoorsDurationday));
          if (Double.parseDouble(df.format(outdoorsDurationday)) > 0) outdoorsDurationCount++;
        }

        if (allDurtion != null && count > 0) {
          AllreadDuration += Double.parseDouble(df.format(allDurtion / count));
          if (Double.parseDouble(df.format(allDurtion / count)) > 0) {
            readDurationCount++;
          }
        }

        if (lookPhoneCount > 0) {
          AlllookPhoneDuration += Double.parseDouble(df.format(lookPhoneDuration / lookPhoneCount));
          if (Double.parseDouble(df.format(lookPhoneDuration / lookPhoneCount)) > 0)
            lookPhoneDurationCount++;
        }
        if (lookScreenCount > 0) {
          AlllookTvComputerDuration +=
              Double.parseDouble(df.format(lookTvComputerDuration / lookScreenCount));
          if (Double.parseDouble(df.format(lookTvComputerDuration / lookScreenCount)) > 0)
            lookTvComputerDurationCount++;
        }

        if (readDurtionNum > 0) {
          if (Double.parseDouble(df.format(sitTilt / readDurtionNum)) > 0) {
            AllsitTilt += Double.parseDouble(df.format(sitTilt / readDurtionNum));
            sitTiltCount++;
          }
          if (Double.parseDouble(df.format(readLight / readDurtionNum)) > 0) {
            AllreadLight += Double.parseDouble(df.format(readLight / readDurtionNum));
            readLightCount++;
          }
          if (Double.parseDouble(df.format(readDistance / readDurtionNum)) > 0) {
            AllreadDistance += Double.parseDouble(df.format(readDistance / readDurtionNum));
            readDistanceCount++;
          }
        }
      }

      if (remindCounts > 0) remindCount.put(i, Allremind / remindCounts);
      else remindCount.put(i, 0);

      if (useJianhuyiDurationCount > 0)
        avgUseJianhuyiDuration.put(
            i, Double.parseDouble(df.format(AlluseJianhuyiDuration / useJianhuyiDurationCount)));
      else avgUseJianhuyiDuration.put(i, 0.0);

      if (readDurationCount > 0)
        avgReadDuration.put(i, Double.parseDouble(df.format(AllreadDuration / readDurationCount)));
      else avgReadDuration.put(i, 0.0);

      if (outdoorsDurationCount > 0)
        outdoorsDuration.put(
            i, Double.parseDouble(df.format(AlloutdoorsDuration / outdoorsDurationCount)));
      else outdoorsDuration.put(i, 0.0);

      if (lookPhoneDurationCount > 0)
        avgLookPhoneDuration.put(
            i, Double.parseDouble(df.format(AlllookPhoneDuration / lookPhoneDurationCount)));
      else avgLookPhoneDuration.put(i, 0.0);

      if (lookTvComputerDurationCount > 0)
        avgLookTvComputerDuration.put(
            i,
            Double.parseDouble(df.format(AlllookTvComputerDuration / lookTvComputerDurationCount)));
      else avgLookTvComputerDuration.put(i, 0.0);

      if (sitTiltCount > 0)
        avgSitTilt.put(i, Double.parseDouble(df.format(AllsitTilt / sitTiltCount)));
      else avgSitTilt.put(i, 0.0);

      if (readLightCount > 0)
        avgReadLight.put(i, Double.parseDouble(df.format(AllreadLight / readLightCount)));
      else avgReadLight.put(i, 0.0);

      if (readDistanceCount > 0)
        avgReadDistance.put(i, Double.parseDouble(df.format(AllreadDistance / readDistanceCount)));
      else avgReadDistance.put(i, 0.0);
    }

    for (Double read : avgReadDuration.values()) {
      if (read > 0) {
        readDurationCount++;
      }
      AllreadDuration += read;
    }

    for (Double value : outdoorsDuration.values()) {
      if (value > 0) {
        outdoorsDurationCount++;
      }
      AlloutdoorsDuration += value;
    }
    for (Double value : avgReadDistance.values()) {
      if (value > 0) {
        readDistanceCount++;
      }
      AllreadDistance += value;
    }

    for (Double value : avgReadLight.values()) {
      if (value > 0) {
        readLightCount++;
      }
      AllreadLight += value;
    }
    for (Double value : avgLookPhoneDuration.values()) {
      if (value > 0) {
        lookPhoneDurationCount++;
      }
      AlllookPhoneDuration += value;
    }
    for (Double value : avgLookTvComputerDuration.values()) {
      if (value > 0) {
        lookTvComputerDurationCount++;
      }
      AlllookTvComputerDuration += value;
    }
    for (Double value : avgSitTilt.values()) {
      if (value > 0) {
        sitTiltCount++;
      }
      AllsitTilt += value;
    }

    for (Double value : avgUseJianhuyiDuration.values()) {
      if (value > 0) {
        useJianhuyiDurationCount++;
      }
      AlluseJianhuyiDuration += value;
    }

    for (Integer value : remindCount.values()) {
      if (value > 0) {
        remindCounts++;
      }
      Allremind += value;
    }
    Map<String, Object> map2 = new LinkedHashMap<String, Object>();
    if (readDurationCount > 0) {
      map2.put("AllreadDuration", AllreadDuration / readDurationCount);
    } else {
      map2.put("AllreadDuration", 0.0);
    }
    if (outdoorsDurationCount > 0) {
      map2.put("AlloutdoorsDuration", AlloutdoorsDuration / outdoorsDurationCount);
    } else {
      map2.put("AlloutdoorsDuration", 0.0);
    }
    if (readDistanceCount > 0) {
      map2.put("AllreadDistance", AllreadDistance / readDistanceCount);
    } else {
      map2.put("AllreadDistance", 0.0);
    }

    if (readLightCount > 0) {
      map2.put("AllreadLight", AllreadLight / readLightCount);
    } else {
      map2.put("AllreadLight", 0.0);
    }
    if (lookPhoneDurationCount > 0) {
      map2.put("AlllookPhoneDuration", AlllookPhoneDuration / lookPhoneDurationCount);
    } else {
      map2.put("AlllookPhoneDuration", 0.0);
    }

    if (lookTvComputerDurationCount > 0) {
      map2.put(
          "AlllookTvComputerDuration", AlllookTvComputerDuration / lookTvComputerDurationCount);
    } else {
      map2.put("AlllookTvComputerDuration", 0.0);
    }
    if (sitTiltCount > 0) {
      map2.put("AllsitTilt", AllsitTilt / sitTiltCount);
    } else {
      map2.put("AllsitTilt", 0.0);
    }

    if (useJianhuyiDurationCount > 0) {
      map2.put("AlluseJianhuyiDuration", AlluseJianhuyiDuration / useJianhuyiDurationCount);
    } else {
      map2.put("AlluseJianhuyiDuration", 0.0);
    }
    if (remindCounts > 0) {
      map2.put("Allremind", df1.format(Allremind / remindCounts));
    } else {
      map2.put("Allremind", 0.0);
    }
    Map<String, Object> mapP = new LinkedHashMap<String, Object>();

    mapP.put("avgReadDuration", avgReadDuration.values());
    mapP.put("outdoorsDuration", outdoorsDuration.values());
    mapP.put("avgReadDistance", avgReadDistance.values());
    mapP.put("avgReadLight", avgReadLight.values());
    mapP.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
    mapP.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
    mapP.put("avgSitTilt", avgSitTilt.values());
    mapP.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
    mapP.put("remind", remindCount.values());

    Map<String, Object> result = new LinkedHashMap<String, Object>();
    result.put("mapP", mapP);
    result.put("map2", map2);
    return result;
  }

  // 年记录
  @Override
  public Map<String, Object> queryUserYearHistory(Date start, Date end, Long userId) {
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> mapP = new HashMap<String, Object>();
    try {
      Map<String, Object> yearData = yearData(start, end, userId);
      map.put("lineChart", yearData);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    mapP.put("data", map);
    mapP.put("msg", "操作成功");
    mapP.put("code", 0);

    return mapP;
  }

  private Map<String, Object> yearData(Date start, Date end, Long userId) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat df1 = new DecimalFormat("#");

    Map<String, Double> map = new LinkedHashMap<String, Double>();
    Map<Integer, Double> avgReadDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> outdoorsDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgReadDistance = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgReadLight = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgLookPhoneDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgLookTvComputerDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgSitTilt = new LinkedHashMap<Integer, Double>();
    Map<Integer, Integer> avgUseJianhuyiDuration = new LinkedHashMap<Integer, Integer>();
    Map<Integer, Integer> remindCount = new LinkedHashMap<>();

    // 有数据的天数
    Integer readDurationCount = 0;
    Integer outdoorsDurationCount = 0;
    Integer readDistanceCount = 0;
    Integer readLightCount = 0;
    Integer lookPhoneDurationCount = 0;
    Integer lookTvComputerDurationCount = 0;
    Integer sitTiltCount = 0;
    Integer useJianhuyiDurationCount = 0;
    Integer remindCounts = 0;

    // 总平均
    Double AllreadDuration = 0.0;
    Double AlloutdoorsDuration = 0.0;
    Double AllreadDistance = 0.0;
    Double AllreadLight = 0.0;
    Double AlllookPhoneDuration = 0.0;
    Double AlllookTvComputerDuration = 0.0;
    Double AllsitTilt = 0.0;
    Double AlluseJianhuyiDuration = 0.0;
    Integer Allremind = 0;
    while (start.compareTo(end) < 0) {
      map.put(format(start), 0.0);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(start);
      calendar.add(Calendar.MONTH, 1);
      start = calendar.getTime();
    }
    List<String> list = new ArrayList<String>();
    Set<String> set1 = map.keySet();
    Iterator<String> it1 = set1.iterator();
    while (it1.hasNext()) {
      //  System.out.println(it1.next());
      list.add(it1.next());
    }
    for (int i = 0; i < 12; i++) {
      readDurationCount = 0;
      outdoorsDurationCount = 0;
      readDistanceCount = 0;
      readLightCount = 0;
      lookPhoneDurationCount = 0;
      lookTvComputerDurationCount = 0;
      sitTiltCount = 0;
      useJianhuyiDurationCount = 0;
      remindCounts = 0;

      // 总平均
      AllreadDuration = 0.0;
      AlloutdoorsDuration = 0.0;
      AllreadDistance = 0.0;
      AllreadLight = 0.0;
      AlllookPhoneDuration = 0.0;
      AlllookTvComputerDuration = 0.0;
      AllsitTilt = 0.0;
      AlluseJianhuyiDuration = 0.0;
      Allremind = 0;

      List<UseJianhuyiLogDO> between = new ArrayList<UseJianhuyiLogDO>();
      List<UseJianhuyiLogDO> dates = new ArrayList<UseJianhuyiLogDO>();
      if (i == 11) {
        between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
        dates = useJianhuyiLogDao.getAllDate(sdf.parse(list.get(i)), end, userId);
      } else {
        between =
            useJianhuyiLogDao.queryUserWeekRecordBetween(
                sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
        dates =
            useJianhuyiLogDao.getAllDate(
                sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
      }

      SimpleDateFormat sdf11 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

      // 遍历用户id和日期
      for (UseJianhuyiLogDO useJianhuyiLogDO : dates) {

        UseJianhuyiLogDO remindUser =
            useJianhuyiLogDao.getRemindByUseidByday(useJianhuyiLogDO.getSaveTime(), userId);
        UseJianhuyiLogDO userTime =
            useJianhuyiLogDao.getUseByUseidByday(useJianhuyiLogDO.getSaveTime(), userId);

        if (remindUser != null) {
          if (remindUser.getRemind() != null && remindUser.getRemind() > 0) {
            Allremind += remindUser.getRemind();
            if (remindUser.getRemind() > 0) {
              remindCounts++;
            }
          }
        }

        if (userTime != null) {
          if (userTime.getUseJianhuyiDuration() != null && userTime.getUseJianhuyiDuration() > 0) {
            AlluseJianhuyiDuration += userTime.getUseJianhuyiDuration();
            if (userTime.getUseJianhuyiDuration() > 0) useJianhuyiDurationCount++;
          }
        }

        // UseJianhuyiLogDO useJianhuyiLogDO = useJianhuyiLogDOList.get(1);
        String date = "";
        Double readDurtion = null;
        Double allDurtion = null;
        int count = 0;
        Double readDistance = 0.0;
        Double readDuration = 0.0;
        Double sitTilt = 0.0;
        Double lookPhoneDuration = 0.0;
        int lookPhoneCount = 0;
        Double lookTvComputerDuration = 0.0;
        int lookScreenCount = 0;
        Double readLight = 0.0;
        Double outdoorsDuration11 = 0.0;

        Double readDurtionNum = 0.0;
        Double outdoorsDurationday = 0.0;

        for (UseJianhuyiLogDO jianhuyiLogDO : between) {
          Double oneReadDurtion = jianhuyiLogDO.getReadDuration();
          if (jianhuyiLogDO.getSaveTime().contains(useJianhuyiLogDO.getSaveTime())) {
            if (jianhuyiLogDO.getStatus() != null && jianhuyiLogDO.getStatus() == 1) {
              if (jianhuyiLogDO.getReadDistance() != null)
                readDistance += jianhuyiLogDO.getReadDistance() * jianhuyiLogDO.getReadDuration();
              if (jianhuyiLogDO.getSitTilt() != null)
                sitTilt += jianhuyiLogDO.getSitTilt() * jianhuyiLogDO.getReadDuration();
              if (jianhuyiLogDO.getReadLight() != null)
                readLight += jianhuyiLogDO.getReadLight() * jianhuyiLogDO.getReadDuration();
              if (jianhuyiLogDO.getReadDuration() != null)
                readDurtionNum += jianhuyiLogDO.getReadDuration();
              if (jianhuyiLogDO.getReadDuration() != null)
                readDuration += jianhuyiLogDO.getReadDuration();
              if (jianhuyiLogDO.getLookPhoneDuration() != null)
                lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
              if (jianhuyiLogDO.getLookTvComputerDuration() != null)
                lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
              if (jianhuyiLogDO.getLookPhoneCount() != null)
                lookPhoneCount += jianhuyiLogDO.getLookPhoneCount();
              if (jianhuyiLogDO.getLookTvComputerCount() != null)
                lookScreenCount += jianhuyiLogDO.getLookTvComputerCount();
              if (!date.equals("")) {
                try {
                  long getReadDuration = (long) (jianhuyiLogDO.getReadDuration() * 60 * 1000);
                  long difference =
                      (sdf11.parse(date).getTime()
                          - (sdf11.parse(jianhuyiLogDO.getSaveTime()).getTime() + getReadDuration));
                  long minute = difference / (1000 * 60);
                  // 间隔大于3分钟，视为2次
                  if (minute > 3) {
                    count++;
                    if (allDurtion > 3) {
                      allDurtion += jianhuyiLogDO.getReadDuration();
                    }
                  }
                  // 间隔小于3分钟，视为1次
                  else {
                    allDurtion += jianhuyiLogDO.getReadDuration();
                  }
                } catch (ParseException e) {
                  e.printStackTrace();
                }
              } else {
                allDurtion = jianhuyiLogDO.getReadDuration();
              }
              date = jianhuyiLogDO.getSaveTime();

            } else {
              outdoorsDurationday += jianhuyiLogDO.getOutdoorsDuration();
            }
          }
        }
        count++;
        if (outdoorsDurationday > 0) {
          AlloutdoorsDuration += Double.parseDouble(df.format(outdoorsDurationday));
          if (Double.parseDouble(df.format(outdoorsDurationday)) > 0) outdoorsDurationCount++;
        }

        if (allDurtion != null && count > 0) {
          AllreadDuration += Double.parseDouble(df.format(allDurtion / count));
          if (Double.parseDouble(df.format(allDurtion / count)) > 0) {
            readDurationCount++;
          }
        }

        if (lookPhoneCount > 0) {
          AlllookPhoneDuration += Double.parseDouble(df.format(lookPhoneDuration / lookPhoneCount));
          if (Double.parseDouble(df.format(lookPhoneDuration / lookPhoneCount)) > 0)
            lookPhoneDurationCount++;
        }
        if (lookScreenCount > 0) {
          AlllookTvComputerDuration +=
              Double.parseDouble(df.format(lookTvComputerDuration / lookScreenCount));
          if (Double.parseDouble(df.format(lookTvComputerDuration / lookScreenCount)) > 0)
            lookTvComputerDurationCount++;
        }

        if (readDurtionNum > 0) {
          if (Double.parseDouble(df.format(sitTilt / readDurtionNum)) > 0) {
            AllsitTilt += Double.parseDouble(df.format(sitTilt / readDurtionNum));
            sitTiltCount++;
          }
          if (Double.parseDouble(df.format(readLight / readDurtionNum)) > 0) {
            AllreadLight += Double.parseDouble(df.format(readLight / readDurtionNum));
            readLightCount++;
          }
          if (Double.parseDouble(df.format(readDistance / readDurtionNum)) > 0) {
            AllreadDistance += Double.parseDouble(df.format(readDistance / readDurtionNum));
            readDistanceCount++;
          }
        }
      }

      if (remindCounts > 0)
        remindCount.put(i, Integer.parseInt(df.format(Allremind / remindCounts)));
      else remindCount.put(i, 0);

      if (useJianhuyiDurationCount > 0)
        avgUseJianhuyiDuration.put(
            i, Integer.parseInt(df.format(AlluseJianhuyiDuration / useJianhuyiDurationCount)));
      else avgUseJianhuyiDuration.put(i, 0);

      if (readDurationCount > 0)
        avgReadDuration.put(i, Double.parseDouble(df.format(AllreadDuration / readDurationCount)));
      else avgReadDuration.put(i, 0.0);

      if (outdoorsDurationCount > 0)
        outdoorsDuration.put(
            i, Double.parseDouble(df.format(AlloutdoorsDuration / outdoorsDurationCount)));
      else outdoorsDuration.put(i, 0.0);

      if (lookPhoneDurationCount > 0)
        avgLookPhoneDuration.put(
            i, Double.parseDouble(df.format(AlllookPhoneDuration / lookPhoneDurationCount)));
      else avgLookPhoneDuration.put(i, 0.0);

      if (lookTvComputerDurationCount > 0)
        avgLookTvComputerDuration.put(
            i,
            Double.parseDouble(df.format(AlllookTvComputerDuration / lookTvComputerDurationCount)));
      else avgLookTvComputerDuration.put(i, 0.0);

      if (sitTiltCount > 0)
        avgSitTilt.put(i, Double.parseDouble(df.format(AllsitTilt / sitTiltCount)));
      else avgSitTilt.put(i, 0.0);

      if (readLightCount > 0)
        avgReadLight.put(i, Double.parseDouble(df.format(AllreadLight / readLightCount)));
      else avgReadLight.put(i, 0.0);

      if (readDistanceCount > 0)
        avgReadDistance.put(i, Double.parseDouble(df.format(AllreadDistance / readDistanceCount)));
      else avgReadDistance.put(i, 0.0);
    }

    Map<String, Object> mapP = new LinkedHashMap<String, Object>();
    /*每月平均使用*/
    mapP.put("avgReadDuration", avgReadDuration.values());
    mapP.put("outdoorsDuration", outdoorsDuration.values());
    mapP.put("avgReadDistance", avgReadDistance.values());
    mapP.put("avgReadLight", avgReadLight.values());
    mapP.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
    mapP.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
    mapP.put("avgSitTilt", avgSitTilt.values());
    mapP.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
    mapP.put("remind", remindCount.values());

    for (Double read : avgReadDuration.values()) {
      if (read > 0) {
        readDurationCount++;
      }
      AllreadDuration += read;
    }

    for (Double value : outdoorsDuration.values()) {
      if (value > 0) {
        outdoorsDurationCount++;
      }
      AlloutdoorsDuration += value;
    }
    for (Double value : avgReadDistance.values()) {
      if (value > 0) {
        readDistanceCount++;
      }
      AllreadDistance += value;
    }

    for (Double value : avgReadLight.values()) {
      if (value > 0) {
        readLightCount++;
      }
      AllreadLight += value;
    }
    for (Double value : avgLookPhoneDuration.values()) {
      if (value > 0) {
        lookPhoneDurationCount++;
      }
      AlllookPhoneDuration += value;
    }
    for (Double value : avgLookTvComputerDuration.values()) {
      if (value > 0) {
        lookTvComputerDurationCount++;
      }
      AlllookTvComputerDuration += value;
    }
    for (Double value : avgSitTilt.values()) {
      if (value > 0) {
        sitTiltCount++;
      }
      AllsitTilt += value;
    }

    for (Integer value : avgUseJianhuyiDuration.values()) {
      if (value > 0) {
        useJianhuyiDurationCount++;
      }
      AlluseJianhuyiDuration += value;
    }

    for (Integer value : remindCount.values()) {
      if (value > 0) {
        remindCounts++;
      }
      Allremind += value;
    }

    Map<String, Object> map2 = new LinkedHashMap<String, Object>();
    if (readDurationCount > 0) {
      map2.put(
          "AllreadDuration", Double.parseDouble(df.format(AllreadDuration / readDurationCount)));
    } else {
      map2.put("AllreadDuration", 0.0);
    }
    if (outdoorsDurationCount > 0) {
      map2.put(
          "AlloutdoorsDuration",
          Double.parseDouble(df.format(AlloutdoorsDuration / outdoorsDurationCount)));
    } else {
      map2.put("AlloutdoorsDuration", 0.0);
    }
    if (readDistanceCount > 0) {
      map2.put(
          "AllreadDistance", Double.parseDouble(df.format(AllreadDistance / readDistanceCount)));
    } else {
      map2.put("AllreadDistance", 0.0);
    }

    if (readLightCount > 0) {
      map2.put("AllreadLight", Double.parseDouble(df.format(AllreadLight / readLightCount)));
    } else {
      map2.put("AllreadLight", 0.0);
    }
    if (lookPhoneDurationCount > 0) {
      map2.put(
          "AlllookPhoneDuration",
          Double.parseDouble(df.format(AlllookPhoneDuration / lookPhoneDurationCount)));
    } else {
      map2.put("AlllookPhoneDuration", 0.0);
    }

    if (lookTvComputerDurationCount > 0) {
      map2.put(
          "AlllookTvComputerDuration",
          Double.parseDouble(df.format(AlllookTvComputerDuration / lookTvComputerDurationCount)));
    } else {
      map2.put("AlllookTvComputerDuration", 0.0);
    }
    if (sitTiltCount > 0) {
      map2.put("AllsitTilt", Double.parseDouble(df.format(AllsitTilt / sitTiltCount)));
    } else {
      map2.put("AllsitTilt", 0.0);
    }

    if (useJianhuyiDurationCount > 0) {
      map2.put(
          "AlluseJianhuyiDuration",
          Double.parseDouble(df.format(AlluseJianhuyiDuration / useJianhuyiDurationCount)));
    } else {
      map2.put("AlluseJianhuyiDuration", 0.0);
    }
    if (remindCounts > 0) {
      map2.put("Allremind", df1.format(Allremind / remindCounts));
    } else {
      map2.put("Allremind", 0);
    }

    Map<String, Object> result = new LinkedHashMap<String, Object>();
    result.put("mapP", mapP);
    result.put("map2", map2);
    return result;
  }

  @Override
  public void saveList(List<UseJianhuyiLogDO> useJianhuyiLogDOList) {
    useJianhuyiLogDao.saveList(useJianhuyiLogDOList);
  }

  @Override
  public List<UseJianhuyiLogDO> selectPersonAndDate(Map<String, Object> map) {
    return useJianhuyiLogDao.selectPersonAndDate(map);
  }

  @Override
  public List<UseJianhuyiLogDO> getMyData(Map<String, Object> map) {
    return useJianhuyiLogDao.getMyData(map);
  }

  @Override
  public UseJianhuyiLogDO getUserJianHuYiYouXiao(Long userId, String createTime) {
    return useJianhuyiLogDao.getUserJianHuYiYouXiao(userId, createTime);
  }

  @Override
  public UseJianhuyiLogDO getUserJianHuYiYouXiaoAll(Long userId, Date createTime, Date endTime) {
    return useJianhuyiLogDao.getUserJianHuYiYouXiaoAll(userId, createTime, endTime);
  }

  @Override
  public Date getMaxDate(Long userId) {
    return useJianhuyiLogDao.getMaxDate(userId);
  }

  @Override
  public List<UseJianhuyiLogDO> getNearData(Long userId, Date date) {
    return useJianhuyiLogDao.getNearData(userId, date);
  }

  // 获取时间段内统计数据
  public List<UseJianhuyiLogDO> getDataByDay(Map<String, Object> map) {
    // 用来保留两位小数
    DecimalFormat df = new DecimalFormat("#.##");
    // 查询所有数据的人和日期
    LinkedList<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogDao.selectPersonAndDate(map);
    // 查询所有数据
    LinkedList<UseJianhuyiLogDO> useJianhuyiLogDOS = useJianhuyiLogDao.selectDataEvery(map);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    // 遍历用户id和日期
    for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOList) {
      // UseJianhuyiLogDO useJianhuyiLogDO = useJianhuyiLogDOList.get(1);
      String date = "";
      Double readDurtion = null;
      Double allDurtion = null;
      int count = 0;
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

      Double readDistance = 0.0;
      Double readDuration = 0.0;
      Double sitTilt = 0.0;
      Double lookPhoneDuration = 0.0;
      int lookPhoneCount = 0;
      Double lookTvComputerDuration = 0.0;
      int lookScreenCount = 0;
      boolean flag = true;
      Double readLight = 0.0;

      Double outdoorsDurationday = 0.0;
      int countss = 0;
      Double readDurtionNum = 0.0;
      for (UseJianhuyiLogDO jianhuyiLogDO : useJianhuyiLogDOS) {
        Double oneReadDurtion = jianhuyiLogDO.getReadDuration();
        if ((useJianhuyiLogDO.getUserId().equals(jianhuyiLogDO.getUserId()))
            && (jianhuyiLogDO.getSaveTime().contains(useJianhuyiLogDO.getSaveTime()))
            && (jianhuyiLogDO.getEquipmentId().equals(useJianhuyiLogDO.getEquipmentId()))) {
          if (jianhuyiLogDO.getStatus() != null && jianhuyiLogDO.getStatus() == 1) {

            if (jianhuyiLogDO.getReadDistance() != null)
              readDistance += jianhuyiLogDO.getReadDistance() * jianhuyiLogDO.getReadDuration();
            if (jianhuyiLogDO.getSitTilt() != null)
              sitTilt += jianhuyiLogDO.getSitTilt() * jianhuyiLogDO.getReadDuration();
            if (jianhuyiLogDO.getReadLight() != null)
              readLight += jianhuyiLogDO.getReadLight() * jianhuyiLogDO.getReadDuration();
            if (jianhuyiLogDO.getReadDuration() != null)
              readDurtionNum += jianhuyiLogDO.getReadDuration();
            if (jianhuyiLogDO.getReadDuration() != null)
              readDuration += jianhuyiLogDO.getReadDuration();

            if (!date.equals("")) {
              try {
                long getReadDuration = (long) (jianhuyiLogDO.getReadDuration() * 60 * 1000);
                long getDate = sdf1.parse(date).getTime();
                long difference =
                    (getDate
                        - (sdf1.parse(jianhuyiLogDO.getSaveTime()).getTime() + getReadDuration));
                long minute = difference / (1000 * 60);
                // 间隔大于3分钟，视为2次
                if (minute > 3) {
                  if (flag) {
                    countss += 2;
                    if (jianhuyiLogDO.getLookPhoneDuration() != null
                        && jianhuyiLogDO.getLookPhoneDuration() > 0) {
                      lookPhoneCount += 2;
                      lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
                    }
                    if (jianhuyiLogDO.getLookTvComputerDuration() != null
                        && jianhuyiLogDO.getLookTvComputerDuration() > 0) {
                      lookScreenCount += 2;
                      lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
                    }

                    if (jianhuyiLogDO.getReadDuration() > 3) {
                      allDurtion += jianhuyiLogDO.getReadDuration();
                    }
                  } else {
                    countss++;
                    if (jianhuyiLogDO.getLookPhoneDuration() != null
                        && jianhuyiLogDO.getLookPhoneDuration() > 0) {
                      lookPhoneCount++;
                      lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
                    }
                    if (jianhuyiLogDO.getLookTvComputerDuration() != null
                        && jianhuyiLogDO.getLookTvComputerDuration() > 0) {
                      lookScreenCount++;
                      lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
                    }
                    if (jianhuyiLogDO.getReadDuration() > 3) {
                      allDurtion += jianhuyiLogDO.getReadDuration();
                    }
                  }
                  flag = false;

                }
                // 间隔小于3分钟，视为1次
                else {
                  allDurtion += jianhuyiLogDO.getReadDuration();
                  lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
                  lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
                }
              } catch (ParseException e) {
                e.printStackTrace();
              }
            } else {
              if (flag) {
                countss++;
                lookPhoneCount++;
                lookScreenCount++;
              }
              allDurtion = jianhuyiLogDO.getReadDuration();
              lookPhoneDuration = jianhuyiLogDO.getLookPhoneDuration();
              lookTvComputerDuration = jianhuyiLogDO.getLookTvComputerDuration();
              flag = false;
            }
            date = jianhuyiLogDO.getSaveTime();
          } else {
            outdoorsDurationday += jianhuyiLogDO.getOutdoorsDuration();
          }
        }
      }
      if (countss > 0) {
        if (allDurtion != null) {
          useJianhuyiLogDO.setReadDuration(Double.parseDouble(df.format(allDurtion / countss)));
        }
      }

      if (lookPhoneCount > 0) {
        useJianhuyiLogDO.setLookPhoneDuration(
            Double.parseDouble(df.format(lookPhoneDuration / lookPhoneCount)));
      }
      if (lookScreenCount > 0) {
        useJianhuyiLogDO.setLookTvComputerDuration(
            Double.parseDouble(df.format(lookTvComputerDuration / lookScreenCount)));
      }

      useJianhuyiLogDO.setAllreadDuration(allDurtion);
      if (outdoorsDurationday > 0) {
        useJianhuyiLogDO.setOutdoorsDuration(Double.parseDouble(df.format(outdoorsDurationday)));
      }
      if (readDurtionNum > 0) {
        useJianhuyiLogDO.setReadDistance(
            Double.parseDouble(df.format(readDistance / readDurtionNum)));
        useJianhuyiLogDO.setReadLight(Double.parseDouble(df.format(readLight / readDurtionNum)));
        useJianhuyiLogDO.setSitTilt(Double.parseDouble(df.format(sitTilt / readDurtionNum)));
      }
    }
    return useJianhuyiLogDOList;
  }

  // 获取时间段内统计数据
  public List<UseJianhuyiLogDO> getData(Map<String, Object> map) {
    // 用来保留两位小数
    DecimalFormat df = new DecimalFormat("#.##");
    // 查询所有数据的人和日期
    LinkedList<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogDao.selectPersonAndDate(map);
    // 查询所有数据
    LinkedList<UseJianhuyiLogDO> useJianhuyiLogDOS = useJianhuyiLogDao.selectAllData(map);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    // 遍历用户id和日期
    for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOList) {
      // UseJianhuyiLogDO useJianhuyiLogDO = useJianhuyiLogDOList.get(1);
      String date = "";
      Double readDurtion = null;
      Double allDurtion = null;
      int count = 0;
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

      Double readDistance = 0.0;
      Double readDuration = 0.0;
      Double sitTilt = 0.0;
      Double lookPhoneDuration = 0.0;
      int lookPhoneCount = 0;
      Double lookTvComputerDuration = 0.0;
      int lookScreenCount = 0;
      boolean flag = true;
      Double readLight = 0.0;

      Double outdoorsDurationday = 0.0;
      int countss = 0;
      Double readDurtionNum = 0.0;
      for (UseJianhuyiLogDO jianhuyiLogDO : useJianhuyiLogDOS) {
        Double oneReadDurtion = jianhuyiLogDO.getReadDuration();
        if ((useJianhuyiLogDO.getUserId().equals(jianhuyiLogDO.getUserId()))
            && (jianhuyiLogDO.getSaveTime().contains(useJianhuyiLogDO.getSaveTime()))
            && (jianhuyiLogDO.getEquipmentId().equals(useJianhuyiLogDO.getEquipmentId()))) {
          if (jianhuyiLogDO.getStatus() != null && jianhuyiLogDO.getStatus() == 1) {

            if (jianhuyiLogDO.getReadDistance() != null)
              readDistance += jianhuyiLogDO.getReadDistance() * jianhuyiLogDO.getReadDuration();
            if (jianhuyiLogDO.getSitTilt() != null)
              sitTilt += jianhuyiLogDO.getSitTilt() * jianhuyiLogDO.getReadDuration();
            if (jianhuyiLogDO.getReadLight() != null)
              readLight += jianhuyiLogDO.getReadLight() * jianhuyiLogDO.getReadDuration();
            if (jianhuyiLogDO.getReadDuration() != null)
              readDurtionNum += jianhuyiLogDO.getReadDuration();
            if (jianhuyiLogDO.getReadDuration() != null)
              readDuration += jianhuyiLogDO.getReadDuration();

            if (!date.equals("")) {
              try {
                long getReadDuration = (long) (jianhuyiLogDO.getReadDuration() * 60 * 1000);
                long getDate = sdf1.parse(date).getTime();
                long difference =
                    (getDate
                        - (sdf1.parse(jianhuyiLogDO.getSaveTime()).getTime() + getReadDuration));
                long minute = difference / (1000 * 60);
                // 间隔大于3分钟，视为2次
                if (minute > 3) {
                  if (flag) {
                    countss += 2;
                    if (jianhuyiLogDO.getLookPhoneDuration() != null
                        && jianhuyiLogDO.getLookPhoneDuration() > 0) {
                      lookPhoneCount += 2;
                      lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
                    }
                    if (jianhuyiLogDO.getLookTvComputerDuration() != null
                        && jianhuyiLogDO.getLookTvComputerDuration() > 0) {
                      lookScreenCount += 2;
                      lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
                    }

                    if (jianhuyiLogDO.getReadDuration() > 3) {
                      allDurtion += jianhuyiLogDO.getReadDuration();
                    }
                  } else {
                    countss++;
                    if (jianhuyiLogDO.getLookPhoneDuration() != null
                        && jianhuyiLogDO.getLookPhoneDuration() > 0) {
                      lookPhoneCount++;
                      lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
                    }
                    if (jianhuyiLogDO.getLookTvComputerDuration() != null
                        && jianhuyiLogDO.getLookTvComputerDuration() > 0) {
                      lookScreenCount++;
                      lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
                    }
                    if (jianhuyiLogDO.getReadDuration() > 3) {
                      allDurtion += jianhuyiLogDO.getReadDuration();
                    }
                  }
                  flag = false;

                }
                // 间隔小于3分钟，视为1次
                else {
                  allDurtion += jianhuyiLogDO.getReadDuration();
                  lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
                  lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
                }
              } catch (ParseException e) {
                e.printStackTrace();
              }
            } else {
              if (flag) {
                countss++;
                lookPhoneCount++;
                lookScreenCount++;
              }
              allDurtion = jianhuyiLogDO.getReadDuration();
              lookPhoneDuration = jianhuyiLogDO.getLookPhoneDuration();
              lookTvComputerDuration = jianhuyiLogDO.getLookTvComputerDuration();
              flag = false;
            }
            date = jianhuyiLogDO.getSaveTime();
          } else {
            outdoorsDurationday += jianhuyiLogDO.getOutdoorsDuration();
          }
        }
      }
      if (countss > 0) {
        if (allDurtion != null) {
          useJianhuyiLogDO.setReadDuration(Double.parseDouble(df.format(allDurtion / countss)));
        }
      }

      if (lookPhoneCount > 0) {
        useJianhuyiLogDO.setLookPhoneDuration(
            Double.parseDouble(df.format(lookPhoneDuration / lookPhoneCount)));
      }
      if (lookScreenCount > 0) {
        useJianhuyiLogDO.setLookTvComputerDuration(
            Double.parseDouble(df.format(lookTvComputerDuration / lookScreenCount)));
      }
      if (outdoorsDurationday > 0) {
        useJianhuyiLogDO.setOutdoorsDuration(Double.parseDouble(df.format(outdoorsDurationday)));
      }
      if (readDurtionNum > 0) {
        useJianhuyiLogDO.setReadDistance(
            Double.parseDouble(df.format(readDistance / readDurtionNum)));
        useJianhuyiLogDO.setReadLight(Double.parseDouble(df.format(readLight / readDurtionNum)));
        useJianhuyiLogDO.setSitTilt(Double.parseDouble(df.format(sitTilt / readDurtionNum)));
      }
    }
    return useJianhuyiLogDOList;
  }
}
