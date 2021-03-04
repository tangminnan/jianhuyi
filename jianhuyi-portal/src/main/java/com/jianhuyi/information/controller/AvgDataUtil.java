package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.information.domain.DataEverydayDO;
import com.jianhuyi.information.domain.SaveParamsDO;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.domain.UseTimeDO;
import com.jianhuyi.information.service.DataEverydayService;
import com.jianhuyi.information.service.UseJianhuyiLogService;
import com.jianhuyi.information.service.UseRemindsService;
import com.jianhuyi.information.service.UseTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** 每日平均值 */
@Component
@Controller
public class AvgDataUtil {
  static DecimalFormat df = new DecimalFormat("#.##");
  @Autowired private static UseTimeService useTimeService;
  @Autowired private static UseJianhuyiLogService useJianhuyiLogService;
  @Autowired private static UseRemindsService useRemindsService;
  @Autowired private static DataEverydayService everydayService;
  static JSONObject jsonObject = null;

  @Autowired private UseJianhuyiLogService jianhuyiLogService;
  @Autowired private UseTimeService timeService;
  @Autowired private UseRemindsService remindsService;
  @Autowired private DataEverydayService dataEverydayService;

  @PostConstruct
  public void init() {
    useJianhuyiLogService = jianhuyiLogService;
    useTimeService = timeService;
    useRemindsService = remindsService;
    everydayService = dataEverydayService;
  }

  // 某日统计数(t_use_jianhuyi_log)
  @ResponseBody
  public static UseJianhuyiLogDO getAvgReadTime(Long userId, String time) {
    Map<String, Object> params = new HashMap<String, Object>();

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

    // 计算平均阅读坐姿角度光照户外等数据
    params.put("userId", userId);
    params.put("time", time);
    List<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogService.selectAllData(params);
    if (useJianhuyiLogDOList.size() > 0) {
      useJianhuyiLogDO.setSaveTime(useJianhuyiLogDOList.get(0).getSaveTime().substring(0, 10));
      useJianhuyiLogDO.setUserId(useJianhuyiLogDOList.get(0).getUserId());
      for (UseJianhuyiLogDO jianhuyiLogDO : useJianhuyiLogDOList) {
        if (jianhuyiLogDO.getStatus() != null && jianhuyiLogDO.getStatus() == 1) {
          // 平均阅读距离（cm）：阅读1状态下 15<=阅读距离<=60和 / 该条件下数据条数

          if (jianhuyiLogDO.getReadDistance() != null
              && jianhuyiLogDO.getReadDistance() >= 15
              && jianhuyiLogDO.getReadDistance() <= 60) {
            readDistance += jianhuyiLogDO.getReadDistance();
            readDistanceCount++;
          }
          // (1)筛选：0＜L≤412  and  15＜D≤60                 (2)调整：L = L * LOG(L , 4.3)
          if (jianhuyiLogDO.getReadLight() != null && jianhuyiLogDO.getReadDistance() != null) {
            if ((jianhuyiLogDO.getReadLight() > 0 && jianhuyiLogDO.getReadLight() <= 412)
                && (jianhuyiLogDO.getReadDistance() > 15
                    && jianhuyiLogDO.getReadDistance() <= 60)) {
              Double linshilight = jianhuyiLogDO.getReadLight();
              // 4.3为底数原始值的对数
              readLight += 100 * (Math.log(linshilight) / Math.log(4.3));
              sitTilt += jianhuyiLogDO.getSitTilt();

              readLightCount++;
            }
          }

          // 判断上一条时间
          if (!date.equals("")) {
            try {
              // 用本次和上一次的时间对比
              long getReadDuration = 0;
              long difference = 0;
              if (jianhuyiLogDO.getReadDuration() != null) {
                getReadDuration = (long) (jianhuyiLogDO.getReadDuration() * 60 * 1000);
              }
              if (jianhuyiLogDO.getSaveTime() != null) {
                difference =
                    (sdf.parse(date).getTime()
                        - (sdf.parse(jianhuyiLogDO.getSaveTime()).getTime() + getReadDuration));
              }
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
          if (jianhuyiLogDO.getSaveTime() != null) date = jianhuyiLogDO.getSaveTime();
          if (jianhuyiLogDO.getReadDuration() != null)
            allDurtion += jianhuyiLogDO.getReadDuration();
          if (jianhuyiLogDO.getLookPhoneDuration() != null)
            lookPhoneDuration += jianhuyiLogDO.getLookPhoneDuration();
          if (jianhuyiLogDO.getLookTvComputerDuration() != null)
            lookTvComputerDuration += jianhuyiLogDO.getLookTvComputerDuration();
        } else {
          // 2状态户外累加
          if (jianhuyiLogDO.getOutdoorsDuration() != null)
            outdoorsDuration += jianhuyiLogDO.getOutdoorsDuration();
        }
      }
    }

    if (readCount > 0) {
      useJianhuyiLogDO.setReadDuration(Double.parseDouble(df.format(allDurtion / readCount)));

      useJianhuyiLogDO.setLookPhoneDuration(
          Double.parseDouble(df.format(lookPhoneDuration / readCount)));
      useJianhuyiLogDO.setLookTvComputerDuration(
          Double.parseDouble(df.format(lookTvComputerDuration / readCount)));
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

    // 获取有效无效等数据
    Map mappp = useTimeService.getSNCount(userId, time);
    List<UseTimeDO> useTimeDOList = useTimeService.getTodayData(userId, time);
    Optional.ofNullable(mappp)
        .ifPresent(
            m -> {
              jsonObject = (JSONObject) JSONObject.toJSON(m);
            });
    String num = jsonObject.get("num").toString();
    String sum = jsonObject.get("sum").toString();
    UseJianhuyiLogDO usetime =
        getSNCount(Integer.parseInt(num), Integer.parseInt(sum), useTimeDOList);

    useJianhuyiLogDO.setEffectiveTime(usetime.getEffectiveTime());
    useJianhuyiLogDO.setNoneffectiveTime(usetime.getNoneffectiveTime());
    useJianhuyiLogDO.setRunningTime(usetime.getRunningTime());
    useJianhuyiLogDO.setCoverTime(usetime.getCoverTime());

    UseJianhuyiLogDO useJianhuyiLogDO2 = useRemindsService.getTodayReminds(userId, time);
    if (useJianhuyiLogDO2 != null && useJianhuyiLogDO2.getRemind() != null) {
      useJianhuyiLogDO.setRemind(useJianhuyiLogDO2.getRemind());
    } else {
      useJianhuyiLogDO.setRemind(0);
    }

    return useJianhuyiLogDO;
  }

  // 有效数据使用（t_use_time） 参数num=总条数 sum=0的个数
  public static UseJianhuyiLogDO getSNCount(
      Integer num, Integer sum, List<UseTimeDO> useTimeDOList) {
    UseJianhuyiLogDO useJianhuyiLogDO = new UseJianhuyiLogDO();

    if (useTimeDOList.size() > 0) {
      // 有数据
      if (num > 0) {
        Double runningTime = 0.0;
        Double effectiveTime = 0.0;
        Double coverTime = 0.0;
        Double noneffectiveTime = 0.0;
        // 有为0的序号，判断0的位置
        if (sum > 0) {
          int serialNumber = 0;
          int j = 0;
          for (int i = 0; i < useTimeDOList.size(); i++) {
            // 判断0的位置

            UseTimeDO useTimeDO =
                (UseTimeDO)
                    JSONObject.parseObject(
                        JSON.toJSONString(useTimeDOList.get(i)), UseTimeDO.class);

            // 序号为0
            if (useTimeDO != null && useTimeDO.getSerialNumber() != null) {
              if (useTimeDO.getSerialNumber().equals(0)) {
                // 判断位置，如果0在最后一个，取最后一个值
                if (i == useTimeDOList.size() - 1) {
                  runningTime += useTimeDO.getRunningTime();
                  effectiveTime += useTimeDO.getEffectiveTime();
                  noneffectiveTime += useTimeDO.getNoneffectiveTime();
                  coverTime += useTimeDO.getCoverTime();

                }
                // 如果前边是0就累加，如果是第一个就不加
                else {

                  if (i > 0) {
                    UseTimeDO lastuseTimeDO =
                        (UseTimeDO)
                            JSONObject.parseObject(
                                JSON.toJSONString(useTimeDOList.get(i - 1)), UseTimeDO.class);
                    if (lastuseTimeDO != null && lastuseTimeDO.getSerialNumber() != null) {
                      if (!lastuseTimeDO.getSerialNumber().equals(0)) {
                        runningTime += lastuseTimeDO.getRunningTime();
                        effectiveTime += lastuseTimeDO.getEffectiveTime();
                        noneffectiveTime += lastuseTimeDO.getNoneffectiveTime();
                        coverTime += lastuseTimeDO.getCoverTime();
                      } else {
                        runningTime += useTimeDO.getRunningTime();
                        effectiveTime += useTimeDO.getEffectiveTime();
                        noneffectiveTime += useTimeDO.getNoneffectiveTime();
                        coverTime += useTimeDO.getCoverTime();
                      }
                    }
                  } else {
                    runningTime += useTimeDO.getRunningTime();
                    effectiveTime += useTimeDO.getEffectiveTime();
                    noneffectiveTime += useTimeDO.getNoneffectiveTime();
                    coverTime += useTimeDO.getCoverTime();
                  }
                }
              } else {
                if (useTimeDO.getSerialNumber() > serialNumber) {
                  serialNumber = useTimeDO.getSerialNumber();
                  j = i;
                }
              }
            }
          }
          UseTimeDO useTimeDO =
              (UseTimeDO)
                  JSONObject.parseObject(JSON.toJSONString(useTimeDOList.get(j)), UseTimeDO.class);

          if (useTimeDO != null) {
            if (useTimeDO.getEffectiveTime() != null) {
              useJianhuyiLogDO.setEffectiveTime(
                  Double.parseDouble(
                      df.format((useTimeDO.getEffectiveTime() + effectiveTime) * 5 / 60 / 60)));
            } else {
              useJianhuyiLogDO.setEffectiveTime(0.0);
            }
            if (useTimeDO.getNoneffectiveTime() != null) {
              useJianhuyiLogDO.setNoneffectiveTime(
                  Double.parseDouble(
                      df.format((useTimeDO.getNoneffectiveTime() + noneffectiveTime) * 5 / 60)));
            } else {
              useJianhuyiLogDO.setNoneffectiveTime(0.0);
            }
            if (useTimeDO.getRunningTime() != null) {
              useJianhuyiLogDO.setRunningTime(
                  Double.parseDouble(
                      df.format((useTimeDO.getRunningTime() + runningTime) * 5 / 60)));
            } else {
              useJianhuyiLogDO.setRunningTime(0.0);
            }

            if (useTimeDO.getCoverTime() != null) {
              useJianhuyiLogDO.setCoverTime(
                  Double.parseDouble(df.format((useTimeDO.getCoverTime() + coverTime) * 5 / 60)));
            } else {
              useJianhuyiLogDO.setCoverTime(0.0);
            }

          } else {
            useJianhuyiLogDO.setEffectiveTime(0.0);
            useJianhuyiLogDO.setNoneffectiveTime(0.0);
            useJianhuyiLogDO.setRunningTime(0.0);
            useJianhuyiLogDO.setCoverTime(0.0);
          }

        }
        // 没有为0的序号，取最大值
        else {
          UseTimeDO useTimeDO =
              (UseTimeDO)
                  JSONObject.parseObject(
                      JSON.toJSONString(useTimeDOList.get(useTimeDOList.size() - 1)),
                      UseTimeDO.class);
          if (useTimeDO != null) {
            if (useTimeDO.getEffectiveTime() != null) {
              useJianhuyiLogDO.setEffectiveTime(
                  Double.parseDouble(df.format(useTimeDO.getEffectiveTime() * 5 / 60 / 60)));
            } else {
              useJianhuyiLogDO.setEffectiveTime(0.0);
            }
            if (useTimeDO.getNoneffectiveTime() != null) {
              useJianhuyiLogDO.setNoneffectiveTime(
                  Double.parseDouble(df.format(useTimeDO.getNoneffectiveTime() * 5 / 60)));
            } else {
              useJianhuyiLogDO.setNoneffectiveTime(0.0);
            }
            if (useTimeDO.getRunningTime() != null) {
              useJianhuyiLogDO.setRunningTime(
                  Double.parseDouble(df.format(useTimeDO.getRunningTime() * 5 / 60)));
            } else {
              useJianhuyiLogDO.setRunningTime(0.0);
            }

            if (useTimeDO.getCoverTime() != null) {
              useJianhuyiLogDO.setCoverTime(
                  Double.parseDouble(df.format(useTimeDO.getCoverTime() * 5 / 60)));
            } else {
              useJianhuyiLogDO.setCoverTime(0.0);
            }

          } else {
            useJianhuyiLogDO.setEffectiveTime(0.0);
            useJianhuyiLogDO.setNoneffectiveTime(0.0);
            useJianhuyiLogDO.setRunningTime(0.0);
            useJianhuyiLogDO.setCoverTime(0.0);
          }
        }
      }
    }
    return useJianhuyiLogDO;
  }

  // 添加或更新某日数据（app上传数据后计算数据添加或更新到统计表）    参数：时间，用户，设备号
  public static void addOrUpdateData(SaveParamsDO saveParamsDOList) {
    System.out.println("===========开始添加或更新数据=============");
    Map<String, Object> params = new HashMap<>();
    params.put("userId", saveParamsDOList.getUserId());
    params.put("equipmentId", saveParamsDOList.getEquipmentId());
    params.put(
        "useTime",
        saveParamsDOList
            .getUseJianhuyiLogDOList()
            .get(0)
            .getSaveTime()
            .toString()
            .substring(0, 10));
    List<DataEverydayDO> everydayDOList = everydayService.list(params);
    UseJianhuyiLogDO useJianhuyiLogDO =
        getAvgReadTime(
            saveParamsDOList.getUserId(),
            saveParamsDOList
                .getUseJianhuyiLogDOList()
                .get(0)
                .getSaveTime()
                .toString()
                .substring(0, 10));
    DataEverydayDO dataEverydayDO = new DataEverydayDO();
    dataEverydayDO.setUserId(Integer.parseInt(saveParamsDOList.getUserId().toString()));
    dataEverydayDO.setEquipmentId(saveParamsDOList.getEquipmentId());
    dataEverydayDO.setAddTime(new Date());
    dataEverydayDO.setUseTime(
        saveParamsDOList
            .getUseJianhuyiLogDOList()
            .get(0)
            .getSaveTime()
            .toString()
            .substring(0, 10));
    dataEverydayDO.setAllReadDuration(useJianhuyiLogDO.getAllreadDuration());
    dataEverydayDO.setReadDuration(useJianhuyiLogDO.getReadDuration());
    dataEverydayDO.setLookPhoneDuration(useJianhuyiLogDO.getLookPhoneDuration());
    dataEverydayDO.setLookTvComputerDuration(useJianhuyiLogDO.getLookTvComputerDuration());
    dataEverydayDO.setReadDistance(useJianhuyiLogDO.getReadDistance());
    dataEverydayDO.setReadLight(useJianhuyiLogDO.getReadLight());
    dataEverydayDO.setSitTilt(useJianhuyiLogDO.getSitTilt());
    dataEverydayDO.setEffectiveTime(useJianhuyiLogDO.getEffectiveTime());
    dataEverydayDO.setNoneffectiveTime(useJianhuyiLogDO.getNoneffectiveTime());
    dataEverydayDO.setOutdoorsDuration(useJianhuyiLogDO.getOutdoorsDuration());
    dataEverydayDO.setCoverTime(useJianhuyiLogDO.getCoverTime());
    dataEverydayDO.setRunningTime(useJianhuyiLogDO.getRunningTime());
    dataEverydayDO.setUploadId(Integer.parseInt(saveParamsDOList.getUploadId().toString()));
    dataEverydayDO.setRemind(useJianhuyiLogDO.getRemind());

    if (everydayDOList.size() > 0) {
      dataEverydayDO.setId(everydayDOList.get(0).getId());
      everydayService.update(dataEverydayDO);
    } else {
      everydayService.save(dataEverydayDO);
    }
    System.out.println("==========dataEverydayDO======================" + dataEverydayDO);
    System.out.println("保存完毕");
  }

  // 添加数据到新表 用于添加以前的记录，以后的由app上传后自动更新，不再使用本接口
  @GetMapping("/addData")
  @ResponseBody
  public static void addData() {
    System.out.println("=============开始查询=============");
    List<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogService.countByUserIdAndDate();
    List<DataEverydayDO> everydayDOList = new ArrayList<>();
    for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOList) {
      UseJianhuyiLogDO useJianhuyiLogDO1 =
          getAvgReadTime(
              Long.parseLong(useJianhuyiLogDO.getUserId().toString()),
              useJianhuyiLogDO.getSaveTime());
      DataEverydayDO dataEverydayDO = new DataEverydayDO();
      dataEverydayDO.setUserId(useJianhuyiLogDO.getUserId());
      dataEverydayDO.setEquipmentId(useJianhuyiLogDO.getEquipmentId());
      dataEverydayDO.setAddTime(new Date());
      dataEverydayDO.setUseTime(useJianhuyiLogDO1.getSaveTime());
      dataEverydayDO.setAllReadDuration(useJianhuyiLogDO1.getAllreadDuration());
      dataEverydayDO.setReadDuration(useJianhuyiLogDO1.getReadDuration());
      dataEverydayDO.setLookPhoneDuration(useJianhuyiLogDO1.getLookPhoneDuration());
      dataEverydayDO.setLookTvComputerDuration(useJianhuyiLogDO1.getLookTvComputerDuration());
      dataEverydayDO.setReadDistance(useJianhuyiLogDO1.getReadDistance());
      dataEverydayDO.setReadLight(useJianhuyiLogDO1.getReadLight());
      dataEverydayDO.setSitTilt(useJianhuyiLogDO1.getSitTilt());
      dataEverydayDO.setEffectiveTime(useJianhuyiLogDO1.getEffectiveTime());
      dataEverydayDO.setNoneffectiveTime(useJianhuyiLogDO1.getNoneffectiveTime());
      dataEverydayDO.setOutdoorsDuration(useJianhuyiLogDO1.getOutdoorsDuration());
      dataEverydayDO.setCoverTime(useJianhuyiLogDO1.getCoverTime());
      dataEverydayDO.setRunningTime(useJianhuyiLogDO1.getRunningTime());
      dataEverydayDO.setUploadId(useJianhuyiLogDO.getUploadId());
      dataEverydayDO.setRemind(useJianhuyiLogDO1.getRemind());

      everydayDOList.add(dataEverydayDO);
    }
    System.out.println("=============开始添加=============");
    int result = everydayService.saveList(everydayDOList);

    System.out.println("result============" + result);
  }
}
