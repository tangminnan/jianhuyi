package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.UseJianhuyiLogDao;
import com.jianhuyi.information.domain.UploadRecordDO;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;
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

  @Override
  public UseJianhuyiLogDO get(Integer id) {
    return useJianhuyiLogDao.get(id);
  }

  @Override
  public Map<String, Object> list(Map<String, Object> map) {
    List<UseJianhuyiLogDO> useJianhuyiLogDOList = getData(map);
    Map<String, Object> result = new HashMap<>();
    if (Integer.parseInt(map.get("offset").toString())
            + Integer.parseInt(map.get("limit").toString())
        > useJianhuyiLogDOList.size()) {
      result.put(
          "useJianhuyiLogDOList",
          useJianhuyiLogDOList.subList(
              Integer.parseInt(map.get("offset").toString()), useJianhuyiLogDOList.size()));
    } else {
      result.put(
          "useJianhuyiLogDOList",
          useJianhuyiLogDOList.subList(
              Integer.parseInt(map.get("offset").toString()),
              Integer.parseInt(map.get("offset").toString())
                  + Integer.parseInt(map.get("limit").toString())));
    }

    result.put("total", useJianhuyiLogDOList.size());
    return result;
  }

  // 获取时间段内统计数据
  @Override
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
      String date = ""; // 上一次的时间
      Double allDurtion = 0.0; // 总阅读时长
      Double readDuration = 0.0; // 单次阅读时长
      int readCount = 0; // 总阅读次数
      Double readDistance = 0.0; // 阅读距离
      int readDistanceCount = 0; // 符合阅读距离条件的条数
      Double sitTilt = 0.0; // 坐姿角度
      Double lookPhoneDuration = 0.0; // 看手机时长
      int lookPhoneCount = 0; // 看手机次数
      Double lookTvComputerDuration = 0.0; // 看屏幕时长
      int lookScreenCount = 0; // 看屏幕次数
      Double readLight = 0.0; // 阅读光照
      int readLightCount = 0;
      boolean flag = true; // 第一次进入时，次数加2
      Double outdoorsDuration = 0.0; // 户外时长

      for (UseJianhuyiLogDO jianhuyiLogDO : useJianhuyiLogDOS) {
        if ((useJianhuyiLogDO.getUserId().equals(jianhuyiLogDO.getUserId()))
            && (jianhuyiLogDO.getSaveTime().contains(useJianhuyiLogDO.getSaveTime()))
            && (jianhuyiLogDO.getEquipmentId().equals(useJianhuyiLogDO.getEquipmentId()))) {
          // 平均阅读距离（cm）：阅读1状态下 15<=阅读距离<=60和 / 该条件下数据条数
          if (jianhuyiLogDO.getReadDistance() >= 15 && jianhuyiLogDO.getReadDistance() <= 60) {
            readDistance += jianhuyiLogDO.getReadDistance();
            readDistanceCount++;
          }
          // (1)筛选：0＜L≤412  and  15＜D≤60                 (2)调整：L = L * LOG(L , 4.3)
          if ((jianhuyiLogDO.getReadLight() > 0 && jianhuyiLogDO.getReadLight() <= 412)
              && (jianhuyiLogDO.getReadDistance() > 15 && jianhuyiLogDO.getReadDistance() <= 60)) {
            Double linshilight = jianhuyiLogDO.getReadLight();
            // 4.3为底数原始值的对数
            readLight += 100 * (Math.log(linshilight) / Math.log(4.3));
            sitTilt += jianhuyiLogDO.getSitTilt();

            readLightCount++;
          }

          // 判断上一条时间
          if (!date.equals("")) {
            try {
              // 用本次和上一次的时间对比
              long getReadDuration = (long) (jianhuyiLogDO.getReadDuration() * 60 * 1000);
              long difference =
                  (sdf.parse(date).getTime()
                      - (sdf.parse(jianhuyiLogDO.getSaveTime()).getTime() + getReadDuration));
              long minute = difference / (1000 * 60);
              // 间隔大于3分钟，视为2次
              if (minute >= 3) {
                // 第一次进来加2
                if (flag) {
                  readCount += 2;
                } else {
                  readCount++;
                }
                flag = false;
              }
              // 间隔小于3分钟，视为1次
            } catch (ParseException e) {
              e.printStackTrace();
            }
          } else {
            if (flag) {
              readCount++;
            }
            flag = false;
          }
          date = jianhuyiLogDO.getSaveTime();
          allDurtion += jianhuyiLogDO.getReadDuration();
          lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
          lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
        } else {
          // 2状态户外累加
          if (jianhuyiLogDO.getOutdoorsDuration() != null) {
            outdoorsDuration += jianhuyiLogDO.getOutdoorsDuration();
          }
        }
      }

      if (readCount > 0) {
        useJianhuyiLogDO.setReadDuration(Double.parseDouble(df.format(allDurtion / readCount)));

        useJianhuyiLogDO.setLookPhoneDuration(
            Double.parseDouble(df.format(lookPhoneDuration / readCount)));
        useJianhuyiLogDO.setLookTvComputerDuration(
            Double.parseDouble(df.format(lookPhoneDuration / readCount)));
      } else {
        useJianhuyiLogDO.setReadDuration(0.0);

        useJianhuyiLogDO.setLookPhoneDuration(0.0);
        useJianhuyiLogDO.setLookTvComputerDuration(0.0);
      }
      if (readDistanceCount > 0) {
        useJianhuyiLogDO.setReadDistance(
            Double.parseDouble(df.format(readDistance / readDistanceCount)));
      } else {
        useJianhuyiLogDO.setReadDistance(0.0);
      }
      if (readLightCount > 0) {
        useJianhuyiLogDO.setReadLight(Double.parseDouble(df.format(readLight / readLightCount)));
        useJianhuyiLogDO.setSitTilt(Double.parseDouble(df.format(sitTilt / readLightCount)));
      } else {
        useJianhuyiLogDO.setReadLight(0.0);
        useJianhuyiLogDO.setSitTilt(0.0);
      }
      if (outdoorsDuration > 0) {
        useJianhuyiLogDO.setOutdoorsDuration(outdoorsDuration);
      } else {
        useJianhuyiLogDO.setOutdoorsDuration(0.0);
      }
    }
    return useJianhuyiLogDOList;
  }

  @Override
  public List<UploadRecordDO> uploadRecordList(Map<String, Object> params) {
    return useJianhuyiLogDao.uploadRecordList(params);
  }

  @Override
  public int uploadRecordCount(Map<String, Object> params) {
    return useJianhuyiLogDao.uploadRecordCount(params);
  }

  @Override
  public Map<String, Collection<Double>> getlineData(Date start, Date end, Long userId) {
    Map<String, Object> params = new HashMap<>();
    params.put("startTime", start);
    params.put("endTime", end);
    params.put("userId", userId);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Map<String, Double> avgReadDuration = new LinkedHashMap<String, Double>();
    Map<String, Double> outdoorsDuration = new LinkedHashMap<String, Double>();
    Map<String, Double> avgReadDistance = new LinkedHashMap<String, Double>();
    Map<String, Double> avgReadLight = new LinkedHashMap<String, Double>();
    Map<String, Double> avgLookPhoneDuration = new LinkedHashMap<String, Double>();
    Map<String, Double> avgLookTvComputerDuration = new LinkedHashMap<String, Double>();
    Map<String, Double> avgSitTilt = new LinkedHashMap<String, Double>();
    Map<String, Double> avgUseJianhuyiDuration = new LinkedHashMap<String, Double>();
    Map<String, Double> sportDuration = new LinkedHashMap<String, Double>();
    while (start.compareTo(end) <= 0) {
      avgReadDuration.put(format(start), 0.0);
      outdoorsDuration.put(format(start), 0.0);
      avgReadDistance.put(format(start), 0.0);
      avgReadLight.put(format(start), 0.0);
      avgLookPhoneDuration.put(format(start), 0.0);
      avgLookTvComputerDuration.put(format(start), 0.0);
      avgSitTilt.put(format(start), 0.0);
      avgUseJianhuyiDuration.put(format(start), 0.0);
      sportDuration.put(format(start), 0.0);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(start);
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      start = calendar.getTime();
    }
    List<String> list = new ArrayList<String>();

    Set<String> keySet = avgReadDuration.keySet();
    Iterator<String> iterator = keySet.iterator();
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }
    for (String string : list) {
      //	System.out.println(string);
      Double readDurationT = 0d; // 阅读时长(分钟）
      Double outdoorsDurationT = 0d; // 户外时长(小时）
      Double readDistanceT = 0d; // 阅读距离(厘米)
      Double readLightT = 0d; // 阅读光照(lux)
      Double lookPhoneDurationT = 0d; // 看手机时长(分钟)
      Double lookTvComputerDurationT = 0d; // 看电脑电视时长(分钟）
      Integer lookPhoneCount = 0; // 看手机次数
      Integer lookTvComputerCount = 0; // 看电脑电视次数
      Double sitTiltT = 0d; // 坐姿倾斜度
      Double useJianhuyiDurationT = 0d; // 使用监护仪时长(小时）
      Double sportDurationT = 0d; // 运动时长(小时)

      List<UseJianhuyiLogDO> weekRecord = useJianhuyiLogDao.queryUserWeekRecord(string, userId);

      Integer readSize = 0;
      Integer other = 0;
      if (weekRecord.size() > 0) {
        for (UseJianhuyiLogDO useJianhuyiLogDO : weekRecord) {
          if (useJianhuyiLogDO.getLookPhoneCount() != null) {
            lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
          }
          if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
            lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
          }
          if (useJianhuyiLogDO.getUseJianhuyiDuration() != null)
            useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();

          if (useJianhuyiLogDO.getStatus() != null) {
            if (useJianhuyiLogDO.getStatus() == 1) {
              readDurationT += useJianhuyiLogDO.getReadDuration();
              readDistanceT += useJianhuyiLogDO.getReadDistance();
              readLightT += useJianhuyiLogDO.getReadLight();
              lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
              lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
              sitTiltT += useJianhuyiLogDO.getSitTilt();

              readSize++;

            } else if (useJianhuyiLogDO.getStatus() == 2) {
              outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
            } else {
              other++;
            }
          } else {
            readDurationT += useJianhuyiLogDO.getReadDuration();
            readDistanceT += useJianhuyiLogDO.getReadDistance();
            readLightT += useJianhuyiLogDO.getReadLight();
            lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
            lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
            sitTiltT += useJianhuyiLogDO.getSitTilt();
          }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        if (lookPhoneCount > 0) {
          avgLookPhoneDuration.put(
              string, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
        } else {
          avgLookPhoneDuration.put(string, 0.0);
        }

        if (lookTvComputerCount > 0) {
          avgLookTvComputerDuration.put(
              string, Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCount)));
        } else {
          avgLookTvComputerDuration.put(string, 0.0);
        }

        if (readSize > 0) {
          avgReadDuration.put(string, Double.parseDouble(df.format(readDurationT / readSize)));
          avgSitTilt.put(string, Double.parseDouble(df.format(sitTiltT / readSize)));
          avgReadLight.put(string, Double.parseDouble(df.format(readLightT / readSize)));
          avgReadDistance.put(string, Double.parseDouble(df.format(readDistanceT / readSize)));
        } else {
          avgReadDuration.put(
              string, Double.parseDouble(df.format(readDurationT / weekRecord.size())));
          avgSitTilt.put(string, Double.parseDouble(df.format(sitTiltT / weekRecord.size())));
          avgReadLight.put(string, Double.parseDouble(df.format(readLightT / weekRecord.size())));
          avgReadDistance.put(
              string, Double.parseDouble(df.format(readDistanceT / weekRecord.size())));
        }
        avgUseJianhuyiDuration.put(
            string, Double.parseDouble(df.format(useJianhuyiDurationT / weekRecord.size())));
        outdoorsDuration.put(string, Double.parseDouble(df.format(outdoorsDurationT)));
        sportDuration.put(string, Double.parseDouble(df.format(sportDurationT)));
      }
    }
    Map<String, Collection<Double>> map = new HashMap<String, Collection<Double>>();
    map.put("avgReadDuration", avgReadDuration.values());
    map.put("outdoorsDuration", outdoorsDuration.values());
    map.put("avgReadDistance", avgReadDistance.values());
    map.put("avgReadLight", avgReadLight.values());
    map.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
    map.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
    map.put("avgSitTilt", avgSitTilt.values());
    map.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
    map.put("sportDuration", sportDuration.values());

    return map;
  }

  @Override
  public Map<String, Collection<Double>> seasonData(Date start, Date end, Long userId)
      throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Map<String, Double> map = new LinkedHashMap<String, Double>();
    Map<Integer, Double> avgReadDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> outdoorsDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgReadDistance = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgReadLight = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgLookPhoneDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgLookTvComputerDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgSitTilt = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgUseJianhuyiDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> sportDuration = new LinkedHashMap<Integer, Double>();
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
      avgReadDuration.put(i + 1, 0.0);
      outdoorsDuration.put(i + 1, 0.0);
      avgReadDistance.put(i + 1, 0.0);
      avgReadLight.put(i + 1, 0.0);
      avgLookPhoneDuration.put(i + 1, 0.0);
      avgLookTvComputerDuration.put(i + 1, 0.0);
      avgSitTilt.put(i + 1, 0.0);
      avgUseJianhuyiDuration.put(i + 1, 0.0);
      sportDuration.put(i + 1, 0.0);
      Double readDurationT = 0d; // 阅读时长(分钟）
      Double outdoorsDurationT = 0d; // 户外时长(小时）
      Double readDistanceT = 0d; // 阅读距离(厘米)
      Double readLightT = 0d; // 阅读光照(lux)
      Double lookPhoneDurationT = 0d; // 看手机时长(分钟)
      Double lookTvComputerDurationT = 0d; // 看电脑电视时长(分钟）
      Integer lookPhoneCount = 0; // 看手机次数
      Integer lookTvComputerCount = 0; // 看电脑电视次数
      Double sitTiltT = 0d; // 坐姿倾斜度
      Double useJianhuyiDurationT = 0d; // 使用监护仪时长(小时）
      Double sportDurationT = 0d; // 运动时长(小时)

      Integer readSize = 0;
      Integer other = 0;
      if (i == 11) {
        /*System.out.println(list.get(i));
        System.out.println(format.format(sdf.parse(list.get(i+1))));*/
        List<UseJianhuyiLogDO> between =
            useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
        if (between.size() > 0) {
          for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
            if (useJianhuyiLogDO != null) {

              if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
              }
              if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
              }

              if (useJianhuyiLogDO.getReadDuration() != null) {
                readDurationT += useJianhuyiLogDO.getReadDuration();
              }
              if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
              }
              if (useJianhuyiLogDO.getReadDistance() != null) {
                readDistanceT += useJianhuyiLogDO.getReadDistance();
              }
              if (useJianhuyiLogDO.getReadLight() != null) {
                readLightT += useJianhuyiLogDO.getReadLight();
              }
              if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
              }
              if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
              }
              if (useJianhuyiLogDO.getSitTilt() != null) {
                sitTiltT += useJianhuyiLogDO.getSitTilt();
              }
              if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
              }
              if (useJianhuyiLogDO.getSportDuration() != null) {
                sportDurationT += useJianhuyiLogDO.getSportDuration();
              }
            }
          }
          DecimalFormat df = new DecimalFormat("0.00");
          if (lookPhoneCount > 0) {
            avgLookPhoneDuration.put(
                i + 1, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
          } else {
            avgLookPhoneDuration.put(i + 1, 0.0);
          }

          if (lookTvComputerCount > 0) {
            avgLookTvComputerDuration.put(
                i + 1,
                Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCount)));
          } else {
            avgLookTvComputerDuration.put(i + 1, 0.0);
          }
          avgReadDuration.put(i + 1, Double.parseDouble(df.format(readDurationT / between.size())));
          outdoorsDuration.put(i + 1, Double.parseDouble(df.format(outdoorsDurationT)));
          avgReadDistance.put(i + 1, Double.parseDouble(df.format(readDistanceT / between.size())));
          avgReadLight.put(i + 1, Double.parseDouble(df.format(readLightT / between.size())));
          avgSitTilt.put(i + 1, Double.parseDouble(df.format(sitTiltT / between.size())));
          avgUseJianhuyiDuration.put(
              i + 1, Double.parseDouble(df.format(useJianhuyiDurationT / between.size())));
          sportDuration.put(i + 1, Double.parseDouble(df.format(sportDurationT)));
        }

      } else {
        /*System.out.println(list.get(i));
        System.out.println(list.get(i+1));
        System.out.println("============"+i);*/
        List<UseJianhuyiLogDO> between =
            useJianhuyiLogDao.queryUserWeekRecordBetween(
                sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
        if (between.size() > 0) {
          for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
            if (useJianhuyiLogDO != null) {
              if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
              }
              if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
              }

              if (useJianhuyiLogDO.getReadDuration() != null) {
                readDurationT += useJianhuyiLogDO.getReadDuration();
              }
              if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
              }
              if (useJianhuyiLogDO.getReadDistance() != null) {
                readDistanceT += useJianhuyiLogDO.getReadDistance();
              }
              if (useJianhuyiLogDO.getReadLight() != null) {
                readLightT += useJianhuyiLogDO.getReadLight();
              }
              if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
              }
              if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
              }
              if (useJianhuyiLogDO.getSitTilt() != null) {
                sitTiltT += useJianhuyiLogDO.getSitTilt();
              }
              if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
              }
              if (useJianhuyiLogDO.getSportDuration() != null) {
                sportDurationT += useJianhuyiLogDO.getSportDuration();
              }
            }
          }
          DecimalFormat df = new DecimalFormat("0.00");

          if (lookPhoneCount > 0) {
            avgLookPhoneDuration.put(
                i + 1, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
          } else {
            avgLookPhoneDuration.put(i + 1, 0.0);
          }

          if (lookTvComputerCount > 0) {
            avgLookTvComputerDuration.put(
                i + 1,
                Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCount)));
          } else {
            avgLookTvComputerDuration.put(i + 1, 0.0);
          }

          avgReadDuration.put(i + 1, Double.parseDouble(df.format(readDurationT / between.size())));
          outdoorsDuration.put(i + 1, Double.parseDouble(df.format(outdoorsDurationT)));
          avgReadDistance.put(i + 1, Double.parseDouble(df.format(readDistanceT / between.size())));
          avgReadLight.put(i + 1, Double.parseDouble(df.format(readLightT / between.size())));
          avgSitTilt.put(i + 1, Double.parseDouble(df.format(sitTiltT / between.size())));
          avgUseJianhuyiDuration.put(
              i + 1, Double.parseDouble(df.format(useJianhuyiDurationT / between.size())));
          sportDuration.put(i + 1, Double.parseDouble(df.format(sportDurationT)));
        }
      }
    }

    Map<String, Collection<Double>> mapP = new LinkedHashMap<String, Collection<Double>>();
    mapP.put("avgReadDuration", avgReadDuration.values());
    mapP.put("outdoorsDuration", outdoorsDuration.values());
    mapP.put("avgReadDistance", avgReadDistance.values());
    mapP.put("avgReadLight", avgReadLight.values());
    mapP.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
    mapP.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
    mapP.put("avgSitTilt", avgSitTilt.values());
    mapP.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
    mapP.put("sportDuration", sportDuration.values());

    return mapP;
  }

  @Override
  public Map<String, Collection<Double>> yearData(Date start, Date end, Long userId)
      throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Map<String, Double> map = new LinkedHashMap<String, Double>();
    Map<Integer, Double> avgReadDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> outdoorsDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgReadDistance = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgReadLight = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgLookPhoneDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgLookTvComputerDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgSitTilt = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> avgUseJianhuyiDuration = new LinkedHashMap<Integer, Double>();
    Map<Integer, Double> sportDuration = new LinkedHashMap<Integer, Double>();
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
      avgReadDuration.put(i + 1, 0.0);
      outdoorsDuration.put(i + 1, 0.0);
      avgReadDistance.put(i + 1, 0.0);
      avgReadLight.put(i + 1, 0.0);
      avgLookPhoneDuration.put(i + 1, 0.0);
      avgLookTvComputerDuration.put(i + 1, 0.0);
      avgSitTilt.put(i + 1, 0.0);
      avgUseJianhuyiDuration.put(i + 1, 0.0);
      sportDuration.put(i + 1, 0.0);
      Double readDurationT = 0d; // 阅读时长(分钟）
      Double outdoorsDurationT = 0d; // 户外时长(小时）
      Double readDistanceT = 0d; // 阅读距离(厘米)
      Double readLightT = 0d; // 阅读光照(lux)
      Double lookPhoneDurationT = 0d; // 看手机时长(分钟)
      Double lookTvComputerDurationT = 0d; // 看电脑电视时长(分钟）
      Integer lookPhoneCount = 0; // 看手机次数
      Integer lookTvComputerCount = 0; // 看电脑电视次数
      Double sitTiltT = 0d; // 坐姿倾斜度
      Double useJianhuyiDurationT = 0d; // 使用监护仪时长(小时）
      Double sportDurationT = 0d; // 运动时长(小时)
      if (i == 11) {
        /*System.out.println(list.get(i));
        System.out.println(format.format(sdf.parse(list.get(i+1))));*/
        List<UseJianhuyiLogDO> between =
            useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
        if (between.size() > 0) {
          for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
            if (useJianhuyiLogDO != null) {
              if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
              }
              if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
              }

              if (useJianhuyiLogDO.getReadDuration() != null) {
                readDurationT += useJianhuyiLogDO.getReadDuration();
              }
              if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
              }
              if (useJianhuyiLogDO.getReadDistance() != null) {
                readDistanceT += useJianhuyiLogDO.getReadDistance();
              }
              if (useJianhuyiLogDO.getReadLight() != null) {
                readLightT += useJianhuyiLogDO.getReadLight();
              }
              if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
              }
              if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
              }
              if (useJianhuyiLogDO.getSitTilt() != null) {
                sitTiltT += useJianhuyiLogDO.getSitTilt();
              }
              if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
              }
              if (useJianhuyiLogDO.getSportDuration() != null) {
                sportDurationT += useJianhuyiLogDO.getSportDuration();
              }
            }
          }
          avgReadDuration.put(i + 1, readDurationT / between.size());
          outdoorsDuration.put(i + 1, outdoorsDurationT);
          avgReadDistance.put(i + 1, readDistanceT / between.size());
          avgReadLight.put(i + 1, readLightT / between.size());
          avgLookPhoneDuration.put(i + 1, lookPhoneDurationT / between.size());
          avgLookTvComputerDuration.put(i + 1, lookTvComputerDurationT / between.size());
          avgSitTilt.put(i + 1, sitTiltT / between.size());
          avgUseJianhuyiDuration.put(i + 1, useJianhuyiDurationT / between.size());
          sportDuration.put(i + 1, sportDurationT);
        }

      } else {
        /*System.out.println(list.get(i));
        System.out.println(list.get(i+1));
        System.out.println("============"+i);*/
        List<UseJianhuyiLogDO> between =
            useJianhuyiLogDao.queryUserWeekRecordBetween(
                sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
        if (between.size() > 0) {
          for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
            if (useJianhuyiLogDO != null) {
              if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
              }
              if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
              }

              if (useJianhuyiLogDO.getReadDuration() != null) {
                readDurationT += useJianhuyiLogDO.getReadDuration();
              }
              if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
              }
              if (useJianhuyiLogDO.getReadDistance() != null) {
                readDistanceT += useJianhuyiLogDO.getReadDistance();
              }
              if (useJianhuyiLogDO.getReadLight() != null) {
                readLightT += useJianhuyiLogDO.getReadLight();
              }
              if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
              }
              if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
              }
              if (useJianhuyiLogDO.getSitTilt() != null) {
                sitTiltT += useJianhuyiLogDO.getSitTilt();
              }
              if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
              }
              if (useJianhuyiLogDO.getSportDuration() != null) {
                sportDurationT += useJianhuyiLogDO.getSportDuration();
              }
            }
          }

          DecimalFormat df = new DecimalFormat("0.00");

          if (lookPhoneCount > 0) {
            avgLookPhoneDuration.put(
                i + 1, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
          } else {
            avgLookPhoneDuration.put(i + 1, 0.0);
          }

          if (lookTvComputerCount > 0) {
            avgLookTvComputerDuration.put(
                i + 1,
                Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCount)));
          } else {
            avgLookTvComputerDuration.put(i + 1, 0.0);
          }

          avgReadDuration.put(i + 1, Double.parseDouble(df.format(readDurationT / between.size())));
          outdoorsDuration.put(i + 1, Double.parseDouble(df.format(outdoorsDurationT)));
          avgReadDistance.put(i + 1, Double.parseDouble(df.format(readDistanceT / between.size())));
          avgReadLight.put(i + 1, Double.parseDouble(df.format(readLightT / between.size())));
          avgSitTilt.put(i + 1, Double.parseDouble(df.format(sitTiltT / between.size())));
          avgUseJianhuyiDuration.put(
              i + 1, Double.parseDouble(df.format(useJianhuyiDurationT / between.size())));
          sportDuration.put(i + 1, Double.parseDouble(df.format(sportDurationT)));
        }
      }
    }

    Map<String, Collection<Double>> mapP = new LinkedHashMap<String, Collection<Double>>();
    mapP.put("avgReadDuration", avgReadDuration.values());
    mapP.put("outdoorsDuration", outdoorsDuration.values());
    mapP.put("avgReadDistance", avgReadDistance.values());
    mapP.put("avgReadLight", avgReadLight.values());
    mapP.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
    mapP.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
    mapP.put("avgSitTilt", avgSitTilt.values());
    mapP.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
    mapP.put("sportDuration", sportDuration.values());

    return mapP;
  }

  @Override
  public List<UseJianhuyiLogDO> selectData(Date start, Date end, Map<String, Object> params) {
    Map<String, Double> map = new LinkedHashMap<String, Double>();
    List<UseJianhuyiLogDO> useJianhuyiLogDOList = new ArrayList<>();
    // 查询列表数据
    Long uploadId = null;
    Long userId = null;
    if (params.get("uploadId") != null && !params.get("uploadId").equals("")) {
      uploadId = Long.parseLong(params.get("uploadId").toString());
    }
    if (params.get("userId") != null && !params.get("userId").equals("")) {
      userId = Long.parseLong(params.get("userId").toString());
    }

    if (start != null && end != null) {
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
      for (String string : list) {
        useJianhuyiLogDOList.addAll(useJianhuyiLogDao.queryData(string, uploadId, userId));
      }
    } else if (start != null && end == null) {
      end = new Date();
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
      for (String string : list) {
        useJianhuyiLogDOList.addAll(useJianhuyiLogDao.queryData(string, uploadId, userId));
      }
    } else if (start == null && end == null) {
      Calendar calendar11 = Calendar.getInstance();
      calendar11.setTime(new Date());
      calendar11.set(Calendar.HOUR_OF_DAY, 0);
      calendar11.set(Calendar.MINUTE, 0);
      calendar11.set(Calendar.SECOND, 0);

      start = calendar11.getTime();
      end = new Date();
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
      for (String string : list) {
        useJianhuyiLogDOList.addAll(useJianhuyiLogDao.queryData(string, uploadId, userId));
      }
    } else {
      useJianhuyiLogDOList.addAll(useJianhuyiLogDao.queryData(null, uploadId, userId));
    }

    return useJianhuyiLogDOList;
  }

  @Override
  public List<Map<String, Object>> exeList(Map<String, Object> params) throws ParseException {
    Map<String, Double> map = new LinkedHashMap<String, Double>();
    List<Map<String, Object>> exelist = new ArrayList<Map<String, Object>>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date start = null;
    Date end = null;
    Long uploadId = null;
    Long userId = null;
    if (params.get("uploadId") != null && !params.get("uploadId").equals("")) {
      uploadId = Long.parseLong(params.get("uploadId").toString());
    }
    if (params.get("userId") != null && !params.get("userId").equals("")) {
      userId = Long.parseLong(params.get("userId").toString());
    }

    if (params.get("startTime") != null && !params.get("startTime").equals("")) {
      start = sdf.parse((String) params.get("startTime"));
    }
    if (params.get("endTime") != null && !params.get("endTime").equals("")) {
      end = sdf.parse((String) params.get("endTime"));
    }

    if (start != null && end != null) {
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
      for (String string : list) {
        System.out.println(
            "=============useJianhuyiLogDao.exeList(string, uploadId, userId)============================"
                + useJianhuyiLogDao.exeList(string, uploadId, userId));
        exelist.addAll(useJianhuyiLogDao.exeList(string, uploadId, userId));
      }
    } else if (start != null && end == null) {
      end = new Date();
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
      for (String string : list) {
        exelist.addAll(useJianhuyiLogDao.exeList(string, uploadId, userId));
      }
    } else {
      exelist.addAll(useJianhuyiLogDao.exeList(null, uploadId, userId));
    }
    return exelist;
  }

  @Override
  public List<UseJianhuyiLogDO> listDetail(Map<String, Object> params) {
    return useJianhuyiLogDao.listDetail(params);
  }

  @Override
  public int counts(Map<String, Object> params) {
    return useJianhuyiLogDao.counts(params);
  }

  @Override
  public int count(Map<String, Object> map) {
    return useJianhuyiLogDao.count(map);
  }

  @Override
  public int countLog(Map<String, Object> map) {
    return useJianhuyiLogDao.countLog(map);
  }

  @Override
  public int save(UseJianhuyiLogDO useJianhuyiLog) {
    return useJianhuyiLogDao.save(useJianhuyiLog);
  }

  @Override
  public int update(UseJianhuyiLogDO useJianhuyiLog) {
    return useJianhuyiLogDao.update(useJianhuyiLog);
  }

  @Override
  public int remove(Integer id) {
    return useJianhuyiLogDao.remove(id);
  }

  @Override
  public int batchRemove(Integer[] ids) {
    return useJianhuyiLogDao.batchRemove(ids);
  }
}
