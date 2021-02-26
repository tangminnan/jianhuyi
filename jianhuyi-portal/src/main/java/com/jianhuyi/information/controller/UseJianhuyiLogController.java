package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.utils.FileUtil;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.common.utils.SpringContextUtil;
import com.jianhuyi.common.utils.StringUtils;
import com.jianhuyi.information.domain.*;
import com.jianhuyi.information.service.*;
import com.jianhuyi.owneruser.service.OwnerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 检测记录表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */
@RestController
@RequestMapping("/jianhuyi/useJianhuyiLog")
public class UseJianhuyiLogController {
  @Autowired private UseJianhuyiLogService useJianhuyiLogService;
  @Autowired private UseRemindsService useRemindsService;
  @Autowired private UseTimeService useTimeService;
  @Autowired private BootdoConfig bootdoConfig;
  @Autowired private OwnerUserService userService;
  @Autowired private UserTaskService userTaskService;
  @Autowired private UserTaskLinshiService userTaskLinshiService;

  @PostMapping("/saveFile")
  @ResponseBody
  R saveFile(@RequestBody MultipartFile temporaryFile) {
    try {
      String filename = temporaryFile.getOriginalFilename();
      FileUtil.uploadFile(
          temporaryFile.getBytes(), bootdoConfig.getUploadPath() + "/temporaryFiles/", filename);

      File file = new File(bootdoConfig.getUploadPath() + "/temporaryFiles/" + filename);
      FileReader in = new FileReader(file);
      BufferedReader br = new BufferedReader(in);
      String str = br.readLine();

      if (str != null) {
        JSONObject userlog = JSONObject.parseObject(new String(str));
        SaveParamsDO saveParamsDO = JSON.toJavaObject(userlog, SaveParamsDO.class);

        R result = save(saveParamsDO);
        if (result.get("code").equals(0)) {
          FileUtil.deleteFile(bootdoConfig.getUploadPath() + "/temporaryFiles/" + filename);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return R.error(500, e.toString());
    }
    return R.ok();
  }

  R save(@RequestBody SaveParamsDO saveParamsDOList) {
    this.useRemindsService = SpringContextUtil.getBean(UseRemindsService.class);
    this.useTimeService = SpringContextUtil.getBean(UseTimeService.class);
    this.useJianhuyiLogService = SpringContextUtil.getBean(UseJianhuyiLogService.class);

    Map<String, Object> map = new HashMap<String, Object>();
    boolean flag = true;
    try {
      if (saveParamsDOList != null) {
        if (saveParamsDOList.getUseRemindsDOList() != null
                && saveParamsDOList.getUseRemindsDOList().size() > 0) {
          for (UseRemindsDO useRemindsDO : saveParamsDOList.getUseRemindsDOList()) {
            if (useRemindsDO != null) {
              useRemindsDO.setSaveTime(new Date());

              if (saveParamsDOList.getEquipmentId() != null) {
                useRemindsDO.setEquipmentId(saveParamsDOList.getEquipmentId());
              }
            }
          }

          useRemindsService.saveList(saveParamsDOList.getUseRemindsDOList());
        }
        if (saveParamsDOList.getUseTimeDOList() != null
                && saveParamsDOList.getUseTimeDOList().size() > 0) {
          for (UseTimeDO useTimeDO : saveParamsDOList.getUseTimeDOList()) {
            if (useTimeDO != null) {
              useTimeDO.setSaveTime(new Date());

              if (saveParamsDOList.getEquipmentId() != null) {
                useTimeDO.setEquipmentId(saveParamsDOList.getEquipmentId());
              }
            }
          }

          useTimeService.saveList(saveParamsDOList.getUseTimeDOList());
        }

        if (saveParamsDOList.getUseJianhuyiLogDOList() != null
                && saveParamsDOList.getUseJianhuyiLogDOList().size() > 0) {
          for (UseJianhuyiLogDO useJianhuyiLogDO : saveParamsDOList.getUseJianhuyiLogDOList()) {
            useJianhuyiLogDO.setAddTime(new Date());
            useJianhuyiLogDO.setDelFlag(0);

            if (saveParamsDOList.getEquipmentId() != null) {
              useJianhuyiLogDO.setEquipmentId(saveParamsDOList.getEquipmentId());
            }
            if (saveParamsDOList.getUploadId() != null) {
              useJianhuyiLogDO.setUploadId(
                      Integer.parseInt(saveParamsDOList.getUploadId().toString()));
            }
            if (saveParamsDOList.getUserId() != null) {
              useJianhuyiLogDO.setUserId(Integer.parseInt(saveParamsDOList.getUserId().toString()));
            }
          }

          useJianhuyiLogService.saveList(saveParamsDOList.getUseJianhuyiLogDOList());
        }
      }

      /** 统计分析数据 */
      if (saveParamsDOList.getUseJianhuyiLogDOList() != null
              && saveParamsDOList.getUseJianhuyiLogDOList().size() > 0) {

        Long userId = saveParamsDOList.getUserId();
        UserDO userDO = userService.getById(userId);
        if (userDO != null) {
          List<Long> list = Arrays.asList(userDO.getTaskId(),userDO.getTaskIds());
          for (Long taskId : list) {
            UserTaskDO userTaskDO = userTaskService.get(taskId);
            if (userTaskDO != null) {
              System.out.println("统计分析数据=========" + userId + "================= " + userTaskDO.getId());
              Date createTime = userTaskDO.getStartTime(); // 最近一次任务的开始时间
              Integer taskTime = userTaskDO.getTaskTime(); // 最近一次任务的天数
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(createTime);
              calendar.add(Calendar.DAY_OF_YEAR, taskTime);
              calendar.add(Calendar.HOUR, 20); // 任务的最大持续时间
              Date taskDate = calendar.getTime();
              if (taskDate.compareTo(new Date()) >= 0) { // 最近一次任务的数据统计
                Map<String, Object> params = new HashMap<>();
                params.put("userId", userId);
                params.put("startTime", createTime);
                params.put("endTime", new Date());
                List<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogService.getMyData(params);
                System.out.println("有数据否======================" + useJianhuyiLogDOList.size());
                if (useJianhuyiLogDOList.size() > 0) { // 创建线程进行统计分析
                  new Thread(
                          () -> {
                            System.out.println("创建线程统计分析数据");
                            try {
                              countPerDay(userTaskDO, useJianhuyiLogDOList); // 统计任务每天评级
                              countTotal(userTaskDO, useJianhuyiLogDOList); // /统计最终评级
                            } catch (ParseException e) {
                              e.printStackTrace();
                            }
                          })
                          .start();
                }
              } else {
                System.out.println("上传的数据时间 已经超过最近任务的截止时间，数据不会被统计进入最近一次任务中了");
              }
            }
          }
        }
      }
      } catch(Exception e){
        e.printStackTrace();
      }

    return R.ok(map);
  }

  /**
   * 统计最终的数据情况
   *
   * @param useJianhuyiLogDOList
   */
  private void countTotal(UserTaskDO userTaskDO, List<UseJianhuyiLogDO> useJianhuyiLogDOList)
      throws ParseException {
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Double outdoorsDuration = 0.0; // 户外时间累计版本
    Double useJianhuyiDuration=0.0;
    List<UseJianhuyiLogDO> sublist =
            useJianhuyiLogDOList.stream()
                    .filter(a -> a.getStatus() != null && a.getStatus() == 1)
                    .collect(Collectors.toList());
    Map<String,Double> resultMap = ResultUtils.countData(sublist);
    outdoorsDuration =
            useJianhuyiLogDOList.stream()
                    .filter(a -> a.getStatus() != null && a.getStatus() == 2)
                    .collect(Collectors.summingDouble(UseJianhuyiLogDO::getOutdoorsDuration));
    UseJianhuyiLogDO userJianHuYiYouXiao =
            useJianhuyiLogService.getUserJianHuYiYouXiaoAll(
                    userTaskDO.getUserId(), userTaskDO.getCreateTime(), new Date());

    useJianhuyiDuration = userJianHuYiYouXiao.getUseJianhuyiDuration(); // 监护仪总的使用时长
    userTaskDO.setTotaluser(useJianhuyiDuration);
    long days =
            (new Date().getTime() - userTaskDO.getCreateTime().getTime()) / 1000 / 60 / 60 / 24;
    resultScore(
            1,
            userTaskDO,
            null,
            resultMap.get("avgReadDuration"),
            resultMap.get("avgLookPhoneDuration"),
            resultMap.get("avgLookTvComputerDuration"),
            resultMap.get("avgSitTilt"),
            resultMap.get("avgReadLight") ,
            resultMap.get("avgReadDistance"),
            outdoorsDuration,
            useJianhuyiDuration / days);

  }

  /**
   * 统计每天的数据情况
   *
   * @param useJianhuyiLogDOList
   */
  private void countPerDay(UserTaskDO userTaskDO, List<UseJianhuyiLogDO> useJianhuyiLogDOList)
      throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    useJianhuyiLogDOList.forEach(
        a -> {
          a.setCreateTime(a.getSaveTime().substring(0,10));
        });
    Double outdoorsDuration = 0.0; // 户外时间累计版本

    Map<String, List<UseJianhuyiLogDO>> map1 =
            useJianhuyiLogDOList.stream()
                    .collect(Collectors.groupingBy(UseJianhuyiLogDO::getCreateTime));
    int scores = 0;
    for (Map.Entry<String, List<UseJianhuyiLogDO>> entry : map1.entrySet()) {
      List<UseJianhuyiLogDO> sublist =
          entry.getValue().stream()
              .filter(a -> a.getStatus() != null && a.getStatus() == 1)
              .collect(Collectors.toList());

      Map<String,Double> resultMap = ResultUtils.countData(sublist);
      outdoorsDuration =
          entry.getValue().stream()
              .filter(a -> a.getStatus() != null && a.getStatus() == 2)
              .collect(Collectors.summingDouble(UseJianhuyiLogDO::getOutdoorsDuration));
      String createTime = entry.getKey();
      UseJianhuyiLogDO userJianHuYiYouXiao =
          useJianhuyiLogService.getUserJianHuYiYouXiao(userTaskDO.getUserId(), createTime);
      Double useJianhuyiDuration = userJianHuYiYouXiao.getUseJianhuyiDuration();
      scores +=
          resultScore(
              0,
              userTaskDO,
              createTime,
                  resultMap.get("avgReadDuration"),
              resultMap.get("avgLookPhoneDuration"),
              resultMap.get("avgLookTvComputerDuration"),
              resultMap.get("avgSitTilt"),
                  resultMap.get("avgReadLight") ,
                  resultMap.get("avgReadDistance"),
                  outdoorsDuration,
              useJianhuyiDuration);
    }
    UserDO userDO = userService.getById(userTaskDO.getUserId());
    userDO.setScores(userDO.getScores() + scores);
    userService.updateScores(userDO);
  }

  /**
   * 统计分析最终评级
   *
   * @param userTaskDO
   * @param createTime
   * @param avgReadDuration
   * @param avgLookPhoneDuration
   * @param avgLookTvComputerDuration
   * @param avgSitTilt
   * @param avgReadLight
   * @param avgReadDistance
   * @param outdoorsDuration
   * @return
   */
  private synchronized Integer resultScore(
      Integer flag,
      UserTaskDO userTaskDO,
      String createTime,
      Double avgReadDuration,
      Double avgLookPhoneDuration,
      Double avgLookTvComputerDuration,
      Double avgSitTilt,
      Double avgReadLight,
      Double avgReadDistance,
      Double outdoorsDuration,
      Double useJianhuyiDuration) {
    if (flag == 0) {
      UserTaskLinshiDO userTaskLinshiDO = new UserTaskLinshiDO();
      userTaskLinshiDO.setUserId(userTaskDO.getUserId());
      userTaskLinshiDO.setTaskId(userTaskDO.getId());
      userTaskLinshiDO.setAvgRead(ResultUtils.resultAvgReadDuration(avgReadDuration));
      userTaskLinshiDO.setAvgLookPhone(
          ResultUtils.resultAvgLookPhoneDuration(avgLookPhoneDuration));
      userTaskLinshiDO.setAvgLookTv(
          ResultUtils.resultAvgLookTvComputerDuration(avgLookTvComputerDuration));
      userTaskLinshiDO.setAvgReadDistance(ResultUtils.resultAvgReadDistance(avgReadDistance));
      userTaskLinshiDO.setAvgLight(ResultUtils.resultAvgReadLight(avgReadLight));
      userTaskLinshiDO.setAvgSitTilt(ResultUtils.resultAvgSitTilt(avgSitTilt));
      userTaskLinshiDO.setAvgOut(ResultUtils.resultOutdoorsDuration(outdoorsDuration));
      userTaskLinshiDO.setEffectiveUseTime(
          ResultUtils.resultUseJianhuyiDuration(useJianhuyiDuration));
      userTaskLinshiDO.setHourtime(Double.toString(useJianhuyiDuration));
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      try {
        Date d = simpleDateFormat.parse(createTime);
        userTaskLinshiDO.setCreateTime(d);
      } catch (ParseException e) {
      }
      Integer score = ResultUtils.countScore(userTaskDO, userTaskLinshiDO);
      userTaskLinshiDO.setScore(score);
      userTaskLinshiDO.setEyeRate(ResultUtils.totalDegree(null, userTaskLinshiDO)); // 当日等级
      /** t_user_task_linshi表中已经存在的数据来说，更新表最后的一条数据，对于之前的数据不做任何处理，对于新数据会新增 */
      UserTaskLinshiDO userTaskLinshiDO11 = userTaskLinshiService.getRecentlyDate(userTaskDO.getUserId(),userTaskDO.getId());
      if(userTaskLinshiDO11==null){
          userTaskLinshiService.save(userTaskLinshiDO);
          return score;
      }else{
        Date allreadyDate = userTaskLinshiDO11.getCreateTime();
        if (userTaskLinshiDO.getCreateTime().compareTo(allreadyDate) == 0) {
          userTaskLinshiService.updateCurrentDay(userTaskLinshiDO);
          Integer score1 = userTaskLinshiDO11.getScore()==null?0:userTaskLinshiDO11.getScore();
          return score-score1;
        }
        else if (userTaskLinshiDO.getCreateTime().compareTo(allreadyDate) > 0) {
          userTaskLinshiService.save(userTaskLinshiDO);
          return score;
        }
        else
          return 0;
      }

    } else if (flag == 1) {
      Integer totalScore = userTaskLinshiService.getTotalScore(userTaskDO.getId());
      System.out.println(userTaskDO.getId()+"============="+totalScore);
      System.out.println(userTaskDO.getId()+"============="+totalScore);
      System.out.println(userTaskDO.getId()+"============="+totalScore);
      userTaskDO.setTotalScore(totalScore); // 总积分
      userTaskDO.setAvgReadResult(ResultUtils.resultAvgReadDuration(avgReadDuration));
      userTaskDO.setAvgLookPhoneResult(
          ResultUtils.resultAvgLookPhoneDuration(avgLookPhoneDuration));
      userTaskDO.setAvgLookTvResult(
          ResultUtils.resultAvgLookTvComputerDuration(avgLookTvComputerDuration));
      userTaskDO.setAvgReadDistanceResult(ResultUtils.resultAvgReadDistance(avgReadDistance));
      userTaskDO.setAvgLightResult(ResultUtils.resultAvgReadLight(avgReadLight));
      userTaskDO.setAvgSitTiltResult(ResultUtils.resultAvgSitTilt(avgSitTilt));
      userTaskDO.setAvgOutResult(ResultUtils.resultOutdoorsDuration(outdoorsDuration));
      userTaskDO.setEffectiveUseTimeResult(
          ResultUtils.resultUseJianhuyiDuration(useJianhuyiDuration));

      userTaskDO.setCountGrade(ResultUtils.totalDegree(userTaskDO, null)); // 平均等级
      System.out.println(ResultUtils.totalDegree(userTaskDO, null));

      userTaskService.update(userTaskDO);
    }
    return null;
  }

  /**
   * 获取今天数据
   *
   * @return
   */
  @GetMapping("/todayData")
  Map<String, Object> todayData(Long userId) {
    Map<String, Object> useLog = useJianhuyiLogService.getByDayTime(userId);

    return useLog;
  }

  /**
   * 周记录
   *
   * @param start
   * @param end
   * @param userId
   * @return
   */
  @GetMapping("/weekData")
  Map<String, Object> weekData(Date start, Date end, Long userId) {
    return useJianhuyiLogService.queryUserWeekHistory(start, end, userId);
  }

  /**
   * 月记录
   *
   * @param start
   * @param end
   * @param userId
   * @return
   */
  @GetMapping("/monthData")
  Map<String, Object> monthData(Date start, Date end, Long userId) {
    return useJianhuyiLogService.queryUserMonthHistory(start, end, userId);
  }

  /**
   * 季记录
   *
   * @param start
   * @param end
   * @param userId
   * @return
   */
  @GetMapping("/seasonData")
  Map<String, Object> seasonData(Date start, Date end, Long userId) {
    return useJianhuyiLogService.queryUserSeasonHistory(start, end, userId);
  }

  /**
   * 年记录
   *
   * @param start
   * @param end
   * @param userId
   * @return
   */
  @GetMapping("/yearData")
  Map<String, Object> yearData(Date start, Date end, Long userId) {
    return useJianhuyiLogService.queryUserYearHistory(start, end, userId);
  }
}
