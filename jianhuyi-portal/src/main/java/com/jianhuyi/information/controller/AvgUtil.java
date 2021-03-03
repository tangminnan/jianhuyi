package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.domain.UseTimeDO;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/** 每日平均值 */
public class AvgUtil {
  public static AvgUtil INSTANCE;
  public static List<UseJianhuyiLogDO> useJianhuyiLogDOList;
  static DecimalFormat df = new DecimalFormat("#.##");

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

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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
    if (allDurtion > 0) {
      useJianhuyiLogDO.setAllreadDuration(allDurtion);
    } else {
      useJianhuyiLogDO.setAllreadDuration(0.0);
    }

    if (outdoorsDuration > 0) {
      useJianhuyiLogDO.setOutdoorsDuration(outdoorsDuration);
    } else {
      useJianhuyiLogDO.setOutdoorsDuration(0.0);
    }

    return useJianhuyiLogDO;
  }

  // 有效数据使用（t_use_time） 参数num=总条数 sum=0的个数
  public static UseJianhuyiLogDO getSNCount(
      Integer num, Integer sum, List<UseTimeDO> useTimeDOList) {
    UseJianhuyiLogDO useJianhuyiLogDO = new UseJianhuyiLogDO();

    // 有数据
    if (num > 0) {
      Double runningTime = 0.0;
      Double effectiveTime = 0.0;
      Double coverTime = 0.0;
      Double noneffectiveTime = 0.0;
      // 有为0的序号，判断0的位置
      if (sum > 0) {
        for (int i = 0; i < useTimeDOList.size(); i++) {
          UseTimeDO useTimeDO =
              (UseTimeDO)
                  JSONObject.parseObject(JSON.toJSONString(useTimeDOList.get(i)), UseTimeDO.class);

          // 序号为0
          if (useTimeDO.getSerialNumber().equals(0)) {
            // 判断位置，如果0在最后一个，取最后一个值
            if (i == useTimeDOList.size() - 1) {
              runningTime += useTimeDO.getRunningTime();
              effectiveTime += useTimeDO.getEffectiveTime();
              noneffectiveTime += useTimeDO.getNoneffectiveTime();
              coverTime += useTimeDO.getCoverTime();

            }
            // 如果不在最后一位，就取0前边的值相加
            else {
              if (i > 0) {
                UseTimeDO lastuseTimeDO =
                    (UseTimeDO)
                        JSONObject.parseObject(
                            JSON.toJSONString(useTimeDOList.get(i - 1)), UseTimeDO.class);
                runningTime += lastuseTimeDO.getRunningTime();
                effectiveTime += lastuseTimeDO.getEffectiveTime();
                noneffectiveTime += lastuseTimeDO.getNoneffectiveTime();
                coverTime += lastuseTimeDO.getCoverTime();
              } else {
                UseTimeDO zuihouUseTime =
                    (UseTimeDO)
                        JSONObject.parseObject(
                            JSON.toJSONString(useTimeDOList.get(useTimeDOList.size() - 1)),
                            UseTimeDO.class);
                runningTime += zuihouUseTime.getRunningTime();
                effectiveTime += zuihouUseTime.getEffectiveTime();
                noneffectiveTime += zuihouUseTime.getNoneffectiveTime();
                coverTime += zuihouUseTime.getCoverTime();
              }
            }
          }
        }

        useJianhuyiLogDO.setEffectiveTime(
            Double.parseDouble(df.format(effectiveTime * 5 / 60 / 60)));
        useJianhuyiLogDO.setNoneffectiveTime(
            Double.parseDouble(df.format(noneffectiveTime * 5 / 60)));
        useJianhuyiLogDO.setRunningTime(Double.parseDouble(df.format(runningTime * 5 / 60)));
        useJianhuyiLogDO.setCoverTime(Double.parseDouble(df.format(coverTime * 5 / 60)));
      }
      // 没有为0的序号，取最大值
      else {
        UseTimeDO useTimeDO =
            (UseTimeDO)
                JSONObject.parseObject(
                    JSON.toJSONString(useTimeDOList.get(useTimeDOList.size() - 1)),
                    UseTimeDO.class);
        useJianhuyiLogDO.setEffectiveTime(
            Double.parseDouble(df.format(useTimeDO.getEffectiveTime() * 5 / 60 / 60)));
        useJianhuyiLogDO.setNoneffectiveTime(
            Double.parseDouble(df.format(useTimeDO.getNoneffectiveTime() * 5 / 60)));
        useJianhuyiLogDO.setRunningTime(
            Double.parseDouble(df.format(useTimeDO.getRunningTime() * 5 / 60)));
        useJianhuyiLogDO.setCoverTime(
            Double.parseDouble(df.format(useTimeDO.getCoverTime() * 5 / 60)));
      }
    }
    return useJianhuyiLogDO;
  }
}
