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
  private static Double sitTilt = 0.0; // 坐姿角度
  private static Double lookPhoneDuration = 0.0; // 看手机时长
  private static int lookPhoneCount = 0; // 看手机次数
  private static Double lookTvComputerDuration = 0.0; // 看屏幕时长
  private static int lookScreenCount = 0; // 看屏幕次数
  private static Double readLight = 0.0; // 阅读光照
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

  // 某日统计数据
  public static UseJianhuyiLogDO getAvgReadTime() {
    UseJianhuyiLogDO useJianhuyiLogDO = new UseJianhuyiLogDO();
    for (UseJianhuyiLogDO jianhuyiLogDO : useJianhuyiLogDOList) {
      if (jianhuyiLogDO.getStatus() != null && jianhuyiLogDO.getStatus() == 1) {
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
              allDurtion += jianhuyiLogDO.getReadDuration();
              flag = false;
            }
            // 间隔小于3分钟，视为1次
            allDurtion += jianhuyiLogDO.getReadDuration();
          } catch (ParseException e) {
            e.printStackTrace();
          }
        } else {
          if (flag) {
            readCount++;
            lookPhoneCount++;
            lookScreenCount++;
          }
          allDurtion = jianhuyiLogDO.getReadDuration();
          flag = false;
        }
        date = jianhuyiLogDO.getSaveTime();

      } else {
        // 2状态户外累加
        outdoorsDuration += jianhuyiLogDO.getOutdoorsDuration();
      }
    }
    if (readCount > 0) {
      useJianhuyiLogDO.setReadDuration(Double.parseDouble(df.format(allDurtion / readCount)));
    }
    return useJianhuyiLogDO;
  }
}
