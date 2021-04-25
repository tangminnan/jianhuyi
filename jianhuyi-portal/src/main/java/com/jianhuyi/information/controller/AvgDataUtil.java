package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.information.domain.*;
import com.jianhuyi.information.service.*;
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
import java.util.concurrent.atomic.AtomicReferenceArray;

/** 每日平均值 */
@Component
@Controller
public class AvgDataUtil {
  static DecimalFormat df = new DecimalFormat("#.##");
  @Autowired private static UseTimeService useTimeService;
  @Autowired private static UseJianhuyiLogService useJianhuyiLogService;
  @Autowired private static UseRemindsService useRemindsService;
  @Autowired private static DataEverydayService everydayService;
  @Autowired private static DataService tdataService;
  @Autowired private static DataInitService tdataInitService;
  static JSONObject jsonObject = null;

  @Autowired private UseJianhuyiLogService jianhuyiLogService;
  @Autowired private UseTimeService timeService;
  @Autowired private UseRemindsService remindsService;
  @Autowired private DataEverydayService dataEverydayService;
  @Autowired private DataService dataService;
  @Autowired private DataInitService dataInitService;

  @PostConstruct
  public void init() {
    useJianhuyiLogService = jianhuyiLogService;
    useTimeService = timeService;
    useRemindsService = remindsService;
    everydayService = dataEverydayService;
    tdataService = dataService;
    tdataInitService = dataInitService;
  }

  // 某日统计数(t_use_jianhuyi_log)
  @ResponseBody
  public static UseJianhuyiLogDO getAvgReadTime(Long userId, String time) {
    Map<String, Object> params = new HashMap<String, Object>();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String date = ""; // 上一次的时间
    Double allDurtion = 0.0; // 总阅读时长
    Double readDuration = 0.0; // 单次阅读时长
    int readCount = 0; // 总阅读次数
    Double readDistance = 0.0; // 阅读距离
    int readDistanceCount = 0; // 符合阅读距离条件的条数
    Double sitTilt = 0.0; // 坐姿角度
    int sitNum = 0;
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

          readLight += jianhuyiLogDO.getReadLight();
          readLightCount += jianhuyiLogDO.getLightNum();

          sitTilt += jianhuyiLogDO.getSitTilt();
          sitNum += jianhuyiLogDO.getSitNum();

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

    if (readLightCount > 0) {
      useJianhuyiLogDO.setReadLight(Double.parseDouble(df.format(readLight / readLightCount)));
    }
    if (sitNum > 0) {
      useJianhuyiLogDO.setSitTilt(Double.parseDouble(df.format(sitTilt / sitNum)));
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

    // 计算距离
    readDistance = selectDisList(userId, time);
    System.out.println("=======平均距离222===============" + readDistance);
    useJianhuyiLogDO.setReadDistance(readDistance);

    // 获取有效无效等数据
    Map mappp = useTimeService.getSNCount(userId, time);
    LinkedList<UseTimeDO> useTimeDOList = useTimeService.getTodayData(userId, time);
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

  @GetMapping("/getSHuju")
  @ResponseBody
  public static UseJianhuyiLogDO getSHuju(Long userId, String time) {
    Map mappp = useTimeService.getSNCount(userId, time);
    LinkedList<UseTimeDO> useTimeDOList = useTimeService.getTodayData(userId, time);
    Optional.ofNullable(mappp)
        .ifPresent(
            m -> {
              jsonObject = (JSONObject) JSONObject.toJSON(m);
            });
    String num = jsonObject.get("num").toString();
    String sum = jsonObject.get("sum").toString();
    return getSNCount(Integer.parseInt(num), Integer.parseInt(sum), useTimeDOList);
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
                } else {
                  if (i > 0) { // 判断当前位置都是不是第一个，不是第一个就取上一个对比
                    UseTimeDO lastuseTimeDO =
                        (UseTimeDO)
                            JSONObject.parseObject(
                                JSON.toJSONString(useTimeDOList.get(i - 1)), UseTimeDO.class);
                    if (lastuseTimeDO != null && lastuseTimeDO.getSerialNumber() != null) {
                      if (lastuseTimeDO
                          .getSerialNumber()
                          .equals(0)) { // 判断上一个序号是否为0，如果是0就加上一条的值，否则就不加
                        runningTime += lastuseTimeDO.getRunningTime();
                        effectiveTime += lastuseTimeDO.getEffectiveTime();
                        noneffectiveTime += lastuseTimeDO.getNoneffectiveTime();
                        coverTime += lastuseTimeDO.getCoverTime();
                      }
                    }
                  }
                }
              } else {
                if (useTimeDO.getSerialNumber() > serialNumber) {
                  serialNumber = useTimeDO.getSerialNumber();
                  j = i;
                } else {
                  UseTimeDO useTimeDO11 =
                      (UseTimeDO)
                          JSONObject.parseObject(
                              JSON.toJSONString(useTimeDOList.get(j)), UseTimeDO.class);
                  runningTime += useTimeDO11.getRunningTime();
                  effectiveTime += useTimeDO11.getEffectiveTime();
                  noneffectiveTime += useTimeDO11.getNoneffectiveTime();
                  coverTime += useTimeDO11.getCoverTime();
                  j = 0;
                  serialNumber = 0;
                }
              }
            }
          }

          if (j > 0) {
            UseTimeDO useTimeDO11 =
                (UseTimeDO)
                    JSONObject.parseObject(
                        JSON.toJSONString(useTimeDOList.get(j)), UseTimeDO.class);
            runningTime += useTimeDO11.getRunningTime();
            effectiveTime += useTimeDO11.getEffectiveTime();
            noneffectiveTime += useTimeDO11.getNoneffectiveTime();
            coverTime += useTimeDO11.getCoverTime();
          }
          useJianhuyiLogDO.setEffectiveTime(
              Double.parseDouble(df.format((effectiveTime) * 5 / 60 / 60)));
          useJianhuyiLogDO.setNoneffectiveTime(
              Double.parseDouble(df.format((noneffectiveTime) * 5 / 60)));
          useJianhuyiLogDO.setRunningTime(
              Double.parseDouble(df.format((runningTime) * 5 / 60 / 60)));
          useJianhuyiLogDO.setCoverTime(Double.parseDouble(df.format((coverTime) * 5 / 60)));

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
                  Double.parseDouble(df.format(useTimeDO.getRunningTime() * 5 / 60 / 60)));
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
  public static void addOrUpdateData(SaveParamsDO saveParamsDOList,AtomicReferenceArray<DataEverydayDO> atomicReferenceArray) {
    System.out.println("===========开始添加或更新数据=============");
    List<DataEverydayDO> everydayDOListadd = new ArrayList<>();
    List<String> datess = new ArrayList<>();
    List<UseJianhuyiLogDO> useJianhuyiLogDOList = saveParamsDOList.getUseJianhuyiLogDOList();
    for (int i = 0; i < useJianhuyiLogDOList.size(); i++) {
      if (i == 0) {
        datess.add(useJianhuyiLogDOList.get(i).getSaveTime().toString().substring(0, 10));
      } else {
        if (!(useJianhuyiLogDOList.get(i).getSaveTime().toString().substring(0, 10))
                .equals(useJianhuyiLogDOList.get(i - 1).getSaveTime().toString().substring(0, 10))) {
          datess.add(useJianhuyiLogDOList.get(i).getSaveTime().toString().substring(0, 10));
        }
      }
    }
    int index=0;
    for (String date : datess) {
      Map<String, Object> params = new HashMap<>();
      params.put("userId", saveParamsDOList.getUserId());
      // params.put("equipmentId", saveParamsDOList.getEquipmentId());
      params.put("useTime", date);
      List<DataEverydayDO> everydayDOList = everydayService.list(params);

      UseJianhuyiLogDO useJianhuyiLogDO = getAvgReadTime(saveParamsDOList.getUserId(), date);

      DataEverydayDO dataEverydayDO = new DataEverydayDO();
      dataEverydayDO.setUserId(Integer.parseInt(saveParamsDOList.getUserId().toString()));
      dataEverydayDO.setEquipmentId(saveParamsDOList.getEquipmentId());
      dataEverydayDO.setAddTime(new Date());
      dataEverydayDO.setUseTime(date);
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
      atomicReferenceArray.getAndSet(index++,dataEverydayDO);
      /*************************************************************************/
      if (everydayDOList.size() > 0) {
        dataEverydayDO.setId(everydayDOList.get(0).getId());
        everydayService.update(dataEverydayDO);
      } else {
        everydayDOListadd.add(dataEverydayDO);
      }
    }

    if (everydayDOListadd.size() > 0) {
      int result = everydayService.saveList(everydayDOListadd);
    }
    System.out.println("保存完毕");
  }

  // 添加数据到新表 用于添加以前的记录，以后的由app上传后自动更新，不再使用本接口
  @GetMapping("/addData")
  @ResponseBody
  public static void addData(Long userId, String time) {
    System.out.println("=============开始查询=============");
    List<UseJianhuyiLogDO> useJianhuyiLogDOList =
        useJianhuyiLogService.countByUserIdAndDate(userId, time);
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

      Map<String, Object> params = new HashMap<>();
      params.put("userId", useJianhuyiLogDO.getUserId());
      // params.put("equipmentId", useJianhuyiLogDO.getEquipmentId());
      params.put("useTime", useJianhuyiLogDO1.getSaveTime());
      List<DataEverydayDO> everydayDOList11 = everydayService.list(params);

      if (everydayDOList11.size() > 0) {
        dataEverydayDO.setId(everydayDOList11.get(0).getId());
        everydayService.update(dataEverydayDO);
      } else {
        everydayDOList.add(dataEverydayDO);
      }
    }
    System.out.println("=============开始添加=============");
    if (everydayDOList.size() > 0) {
      int result = everydayService.saveList(everydayDOList);
    }
  }




  // 添加或更新某日数据（app上传数据后计算数据添加或更新到统计表）    参数：时间，用户，设备号
  public static DataEverydayDO addAllData(Long userId,Date startDate,Date endDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String date = ""; // 上一次的时间
    Double allDurtion = 0.0; // 总阅读时长
    Double readDuration = 0.0; // 单次阅读时长
    int readCount = 0; // 总阅读次数
    Double readDistance = 0.0; // 阅读距离
    int readDistanceCount = 0; // 符合阅读距离条件的条数
    Double sitTilt = 0.0; // 坐姿角度
    int sitNum = 0;
    Double lookPhoneDuration = 0.0; // 看手机时长
    int lookPhoneCount = 0; // 看手机次数
    Double lookTvComputerDuration = 0.0; // 看屏幕时长
    int lookScreenCount = 0; // 看屏幕次数
    Double readLight = 0.0; // 阅读光照
    int readLightCount = 0;
    boolean flag = true; // 第一次进入时，次数加2
    Double outdoorsDuration = 0.0; // 户外时长

    DataEverydayDO dataEverydayDO = new DataEverydayDO();

    // 计算平均阅读坐姿角度光照户外等数据
    Map<String, Object> params = new HashMap<>();
    params.put("userId", userId);
    params.put("startTime", startDate);
    params.put("endTime", endDate);
    List<UseJianhuyiLogDO> useJianhuyiLogDOList =
            useJianhuyiLogService.getMyData(params);
    if (useJianhuyiLogDOList.size() > 0) {
      for (UseJianhuyiLogDO jianhuyiLogDO : useJianhuyiLogDOList) {
        if (jianhuyiLogDO.getStatus() != null && jianhuyiLogDO.getStatus() == 1) {
          // 4.3为底数原始值的对数
          readLight += jianhuyiLogDO.getReadLight();
          readLightCount += jianhuyiLogDO.getLightNum();

          sitTilt += jianhuyiLogDO.getSitTilt();
          sitNum += jianhuyiLogDO.getSitNum();

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
      dataEverydayDO.setReadDuration(Double.parseDouble(df.format(allDurtion / readCount)));

      dataEverydayDO.setLookPhoneDuration(
              Double.parseDouble(df.format(lookPhoneDuration / readCount)));
      dataEverydayDO.setLookTvComputerDuration(
              Double.parseDouble(df.format(lookTvComputerDuration / readCount)));
    } else {
      dataEverydayDO.setReadDuration(0.0);

      dataEverydayDO.setLookPhoneDuration(0.0);
      dataEverydayDO.setLookTvComputerDuration(0.0);
    }

    dataEverydayDO.setReadLight(Double.parseDouble(df.format(readLight / readLightCount)));
    dataEverydayDO.setSitTilt(Double.parseDouble(df.format(sitTilt / sitNum)));

    if (outdoorsDuration > 0) {
      dataEverydayDO.setOutdoorsDuration(outdoorsDuration);
    } else {
      dataEverydayDO.setOutdoorsDuration(0.0);
    }
    /**
     *  计算阅读距离
     */
    Double distances = 0.0;
    int distancesCount = 0;
    List<DataDO> dataDOList = tdataService.getAllList(userId, startDate,endDate);
    List<List<BaseDataDO>> data = new ArrayList<>();
    List<BaseDataDO> olddata = new ArrayList<>();
    List<BaseDataDO> allData = new ArrayList<>();
    // 循环原始数据
    for (DataDO dataDO : dataDOList) {
      String baseData = dataDO.getBaseData();
      olddata = JSON.parseArray(baseData, BaseDataDO.class);
      data.add(olddata);
    }
    for (List<BaseDataDO> datum : data) {
      for (BaseDataDO baseDataDO : datum) {
        allData.add(baseDataDO);
      }
    }
    // 取出距离 筛选>=10 并且 <=60 的距离值
    List<BaseDataDO> baseDataDOList = countDistance(allData);
    for (BaseDataDO baseDataDO : baseDataDOList) {
      if (baseDataDO.getDistances() >= 100 && baseDataDO.getDistances() <= 600) {
        distances += baseDataDO.getDistances();
        distancesCount++;
      }
    }
    if (distancesCount > 0) {
      dataEverydayDO.setReadDistance( distances / distancesCount / 10);
    } else {
      dataEverydayDO.setReadDistance(0.0);
    }
    return  dataEverydayDO;
  }




  @GetMapping("/selectDisList")
  @ResponseBody
  public static Double selectDisList(Long userId, String time) {
    Double distances = 0.0;
    int distancesCount = 0;
    // 查询t_data表今天的数据记录 1状态的数据
    List<DataDO> dataDOList = tdataService.getList(userId, time);

    List<List<BaseDataDO>> data = new ArrayList<>();
    List<BaseDataDO> olddata = new ArrayList<>();
    List<BaseDataDO> allData = new ArrayList<>();
    // 循环原始数据
    for (DataDO dataDO : dataDOList) {
      String baseData = dataDO.getBaseData();
      olddata = JSON.parseArray(baseData, BaseDataDO.class);
      data.add(olddata);
    }
    for (List<BaseDataDO> datum : data) {
      for (BaseDataDO baseDataDO : datum) {
        allData.add(baseDataDO);
      }
    }
    // 取出距离 筛选>=10 并且 <=60 的距离值
    List<BaseDataDO> baseDataDOList = countDistance(allData);
    for (BaseDataDO baseDataDO : baseDataDOList) {
      if (baseDataDO.getDistances() >= 100 && baseDataDO.getDistances() <= 600) {
        distances += baseDataDO.getDistances();
        distancesCount++;
      }
    }
    if (distancesCount > 0) {
      return distances / distancesCount / 10;
    } else {
      return 0.0;
    }
  }

  // 调整数据范围
  public static List<BaseDataDO> countDistance(List<BaseDataDO> dataDOList) {
    List<BaseDataDO> newData = new ArrayList<>();
    // 筛选距离范围 光强值L ＜3000； 距离 10≤D≤80；
    for (BaseDataDO datum : dataDOList) {
      if (datum.getLights() < 30000
          && (datum.getDistances() >= 100 && datum.getDistances() <= 800)) {
        newData.add(datum);
      }
    }
    // 计算均值mean，标准差std
    Double mean = 0.0;
    Double dis = 0.0;
    for (BaseDataDO newDatum : newData) {
      dis += newDatum.getDistances();
    }
    if (newData.size() > 0) {
      mean = dis / newData.size();
    }
    Double std = 0.0;
    for (BaseDataDO newDatum : newData) {
      std += (newDatum.getDistances() - mean) * (newDatum.getDistances() - mean);
    }
    std = Math.sqrt(std / newData.size());
    // 计算远近中组
    Double f1 = 1.433 * mean + 1.011 * std - 22.136; // 近组
    Double f2 = 2.076 * mean + 1.54 * std - 46.346; // 中组
    Double f3 = 2.829 * mean + 1.483 * std - 73.809; // 远组

    // 调整距离原值
    DecimalFormat df = new DecimalFormat("#.#");
    if (f1 > f2 && f1 > f3) { // 近组
      // (1)近组调整：  IF(D<=30） D = ROUND(D*LOG(D,6.5),1)    10-30的数据log(6.5) 放大, 1位小数
      for (BaseDataDO newDatum : newData) {
        if (newDatum.getDistances() <= 30) {
          Double dd = newDatum.getDistances() * getLog(newDatum.getDistances(), 6.5);
          newDatum.setDistances(Double.parseDouble(df.format(dd)));
        }
      }

    } else if (f3 > f1 && f3 > f2) { // 远组
      // (2)远组调整：  IF(D>=40） D = ROUND(25+0.875*(D-40),1)    40-80映射到25-60, 1位小数
      for (BaseDataDO newDatum : newData) {
        if (newDatum.getDistances() >= 40) {
          Double dd = 25 + 0.875 * (newDatum.getDistances() - 40);
          newDatum.setDistances(Double.parseDouble(df.format(dd)));
        }
      }
    }

    // 总体调整  IF(10≤D≤60)  D = 20+0.75*(D-20)       20-60映射到20-50，15-20部分相应调整
    for (BaseDataDO newDatum : newData) {
      if (newDatum.getDistances() >= 10 && newDatum.getDistances() <= 60) {
        newDatum.setDistances(20 + 0.75 * (newDatum.getDistances() - 20));
      }
    }
    return newData;
  }

  // 计算对数Log(logarithm,background)   对数，底数
  public static Double getLog(Double logarithm, Double background) {
    if (background != 0) {
      return Math.log(logarithm) / Math.log(background);
    } else {
      return 0.0;
    }
  }
}
