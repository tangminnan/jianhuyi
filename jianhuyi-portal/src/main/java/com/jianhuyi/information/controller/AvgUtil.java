package com.jianhuyi.information.controller;

import com.jianhuyi.information.domain.UseJianhuyiLogDO;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/** 每日平均值 */
public class AvgUtil {
  public static AvgUtil INSTANCE;
  public static List<UseJianhuyiLogDO> useJianhuyiLogDOList;

  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  private static DecimalFormat df = new DecimalFormat("#.##");
  private static String date = ""; // 上一次的时间
  private static Double allDurtion = 0.0; // 总阅读时长
  private static Double readDuration = 0.0; // 单次阅读时长
  private static int readCount = 0; // 总阅读次数
  private static Double readDistance = 0.0; // 阅读距离
  private static int readDistanceCount = 0; // 符合阅读距离条件的条数
  private static Double sitTilt = 0.0; // 坐姿角度
  private static Double lookPhoneDuration = 0.0; // 看手机时长
  private static int lookPhoneCount = 0; // 看手机次数
  private static Double lookTvComputerDuration = 0.0; // 看屏幕时长
  private static int lookScreenCount = 0; // 看屏幕次数
  private static Double readLight = 0.0; // 阅读光照
  private static int readLightCount = 0;
  private static boolean flag = true; // 第一次进入时，次数加2

  private static Double outdoorsDuration = 0.0; // 户外时长

  // 单例，实现每次new的是同一个对象
  public static AvgUtil getINSTANCE() {
    if (INSTANCE == null) {
      synchronized (AvgUtil.class) {
        if (INSTANCE == null) {
          INSTANCE = new AvgUtil();
        }
      }
    }
    return INSTANCE;
  }

  public void putUserJianhuyi(List<UseJianhuyiLogDO> useJianhuyiLogDOList) {
    this.useJianhuyiLogDOList = useJianhuyiLogDOList;
  }

  // 某日统计数(t_use_jianhuyi_log)
  public static UseJianhuyiLogDO getAvgReadTime() {
    UseJianhuyiLogDO useJianhuyiLogDO = new UseJianhuyiLogDO();
    for (UseJianhuyiLogDO jianhuyiLogDO : useJianhuyiLogDOList) {
      if (jianhuyiLogDO.getStatus() != null && jianhuyiLogDO.getStatus() == 1) {
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
        outdoorsDuration += jianhuyiLogDO.getOutdoorsDuration();
      }
    }
    if (readCount > 0) {
      useJianhuyiLogDO.setReadDuration(Double.parseDouble(df.format(allDurtion / readCount)));

      useJianhuyiLogDO.setLookPhoneDuration(
          Double.parseDouble(df.format(lookPhoneDuration / readCount)));
      useJianhuyiLogDO.setLookTvComputerDuration(
          Double.parseDouble(df.format(lookPhoneDuration / readCount)));
    }
    if (readDistanceCount > 0) {
      useJianhuyiLogDO.setReadDistance(
          Double.parseDouble(df.format(readDistance / readDistanceCount)));
    }
    if (readLightCount > 0) {
      useJianhuyiLogDO.setReadLight(Double.parseDouble(df.format(readLight / readLightCount)));
      useJianhuyiLogDO.setSitTilt(Double.parseDouble(df.format(sitTilt / readLightCount)));
    }
    useJianhuyiLogDO.setAllreadDuration(allDurtion);
    return useJianhuyiLogDO;
  }

  // 有效数据使用（t_use_time）
}
