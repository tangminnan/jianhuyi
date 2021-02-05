package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.utils.FileUtil;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.common.utils.SpringContextUtil;
import com.jianhuyi.information.domain.*;
import com.jianhuyi.information.service.*;
import com.jianhuyi.owneruser.service.OwnerUserService;
import com.sun.istack.internal.Nullable;
import org.apache.catalina.User;
import org.jsoup.select.Collector;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
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
    @Autowired
    private UseJianhuyiLogService useJianhuyiLogService;
    @Autowired
    private UseRemindsService useRemindsService;
    @Autowired
    private UseTimeService useTimeService;
    @Autowired
    private BootdoConfig bootdoConfig;
    @Autowired
    private OwnerUserService userService;
    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private UserTaskLinshiService userTaskLinshiService;
    @PostMapping("/saveFile")

    @ResponseBody
    R saveFile(@RequestBody MultipartFile temporaryFile) {
        try {
            String filename = temporaryFile.getOriginalFilename();
            FileUtil.uploadFile(temporaryFile.getBytes(), bootdoConfig.getUploadPath() + "/temporaryFiles/", filename);

            File file = new File(bootdoConfig.getUploadPath() + "/temporaryFiles/" + filename);
            FileReader in = new FileReader(file);
            BufferedReader br = new BufferedReader(in);
            String str = br.readLine();

            System.out.println("========str===============" + str);
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
                if (saveParamsDOList.getUseRemindsDOList() != null && saveParamsDOList.getUseRemindsDOList().size() > 0) {
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
                if (saveParamsDOList.getUseTimeDOList() != null && saveParamsDOList.getUseTimeDOList().size() > 0) {
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

                if (saveParamsDOList.getUseJianhuyiLogDOList() != null && saveParamsDOList.getUseJianhuyiLogDOList().size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : saveParamsDOList.getUseJianhuyiLogDOList()) {
                        useJianhuyiLogDO.setAddTime(new Date());
                        useJianhuyiLogDO.setDelFlag(0);

                        if (saveParamsDOList.getEquipmentId() != null) {
                            useJianhuyiLogDO.setEquipmentId(saveParamsDOList.getEquipmentId());
                        }
                        if (saveParamsDOList.getUploadId() != null) {
                            useJianhuyiLogDO.setUploadId(Integer.parseInt(saveParamsDOList.getUploadId().toString()));
                        }
                        if (saveParamsDOList.getUserId() != null) {
                            useJianhuyiLogDO.setUserId(Integer.parseInt(saveParamsDOList.getUserId().toString()));
                        }
                    }

                    useJianhuyiLogService.saveList(saveParamsDOList.getUseJianhuyiLogDOList());
                }
            }

            /**
             *  统计分析数据
             */
            if (saveParamsDOList.getUseJianhuyiLogDOList() != null && saveParamsDOList.getUseJianhuyiLogDOList().size() > 0) {
                Long userId = saveParamsDOList.getUserId();
                UserDO userDO = userService.getById(userId);
                if(userDO!=null && userDO.getTaskId()!=null){
                    UserTaskDO userTaskDO = userTaskService.get(userDO.getTaskId());
                    if(userTaskDO!=null){
                        Date createTime = userTaskDO.getCreateTime();//最近一次任务的开始时间
                        Integer taskTime = userTaskDO.getTaskTime();//最近一次任务的天数
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(createTime);
                        calendar.add(Calendar.DAY_OF_YEAR,taskTime);
                        calendar.add(Calendar.HOUR,20);//任务的最大持续时间
                        Date taskDate = calendar.getTime();
                        if(taskDate.compareTo(new Date())>=0){//最近一次任务的数据统计
                            Map<String, Object> params = new HashMap<>();
                            params.put("userId", userId);
                            params.put("startTime", createTime);
                            params.put("endTime", new Date());
                            List<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogService.getMyData(map);
                            if(useJianhuyiLogDOList.size()>0){//创建线程池进行统计分析

                               new Thread(()->{
                                    try {
                                        countPerDay(userTaskDO,useJianhuyiLogDOList);//统计任务每天评级
                                        countTotal(userTaskDO,useJianhuyiLogDOList);///统计最终评级
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }).start();
                            }
                        }else{
                            System.out.println("上传的数据时间 已经超过最近任务的截止时间，数据不会被统计进入最近一次任务中了");
                        }
                    }
                }
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(map);
    }

    /**
     *  统计最终的数据情况
     * @param useJianhuyiLogDOList
     */
    private void countTotal(UserTaskDO userTaskDO,   List<UseJianhuyiLogDO> useJianhuyiLogDOList) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Double avgReadDuration = 0.0;//平均每次阅读时长
        Double outdoorsDuration = 0.0;//户外时间累计版本
        Double avgReadDistance = 0.0;//平均阅读距离
        Double avgReadLight = 0.0;//平均阅读光照
        Double avgLookPhoneDuration = 0.0;//平均单次看手机时长
        Double avgLookTvComputerDuration = 0.0;//平均单次看电脑及电视时长
        Double avgSitTilt = 0.0;//平均旋转角度
        Double allUseJianhuyi = 0.0;//有效监护仪使用时长
        int lookPhoneCount = 0;//看手机次数
        int lookScreenCount = 0;//看电脑屏幕的次数
        int count = 0;//阅读次数

        Double readDurtionNum = 0.0;
        for(int i=0;i<useJianhuyiLogDOList.size();i++){
            UseJianhuyiLogDO useJianhuyiLogDO=useJianhuyiLogDOList.get(i);
            avgReadDistance += useJianhuyiLogDO.getReadDistance() * useJianhuyiLogDO.getReadDuration();
            avgSitTilt += useJianhuyiLogDO.getSitTilt() * useJianhuyiLogDO.getReadDuration();
            avgReadLight += useJianhuyiLogDO.getReadLight() * useJianhuyiLogDO.getReadDuration();
            readDurtionNum += useJianhuyiLogDO.getReadDuration();
            if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                avgLookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
            }
            if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                avgLookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
            }
            avgReadDuration += useJianhuyiLogDO.getReadDuration();
            if(i+1<useJianhuyiLogDOList.size()) {
                UseJianhuyiLogDO useJianhuyiLogDO1=useJianhuyiLogDOList.get(i+1);
                long minute = (sdf1.parse(useJianhuyiLogDO1.getSaveTime()).getTime() -
                        sdf1.parse(useJianhuyiLogDO.getSaveTime()).getTime()
                        - (long) (useJianhuyiLogDO.getReadDuration() * 60 * 1000)) / 1000 / 60;
                if (minute >= 5 || i == 0) {
                    if (useJianhuyiLogDO.getLookPhoneDuration() != null && useJianhuyiLogDO.getLookPhoneDuration() > 0)
                        lookPhoneCount++;//看手机次数
                    if (useJianhuyiLogDO.getLookTvComputerDuration() != null && useJianhuyiLogDO.getLookTvComputerDuration() > 0)
                        lookScreenCount++;//看电脑屏幕的次数
                    if (useJianhuyiLogDO.getReadDuration() >= 5) {
                        count++;//阅读次数
                    }
                }
            }
            DecimalFormat df = new DecimalFormat("#.##");
            if (avgReadDuration != null && count > 0) {
                avgReadDuration=Double.parseDouble(df.format(avgReadDuration / count));
            }
            if (avgLookPhoneDuration > 0) {
                avgLookPhoneDuration=Double.parseDouble(df.format(avgLookPhoneDuration / lookPhoneCount));
            }
            if (lookScreenCount > 0) {
                avgLookTvComputerDuration=Double.parseDouble(df.format(avgLookTvComputerDuration / lookScreenCount));
            }
            if (readDurtionNum > 0) {
                avgSitTilt=Double.parseDouble(df.format(avgSitTilt / readDurtionNum));
                avgReadLight=Double.parseDouble(df.format(avgReadLight / readDurtionNum));
                avgReadDistance=Double.parseDouble(df.format(avgReadDistance / readDurtionNum));
            } else {
                avgSitTilt=0.0;
                avgReadLight=0.0;
                avgReadDistance=0.0;
            }
                outdoorsDuration=useJianhuyiLogDOList.stream()
                        .filter(a->a.getStatus()!=null && a.getStatus()==2)
                        .collect(Collectors.summingDouble(UseJianhuyiLogDO::getOutdoorsDuration));
                UseJianhuyiLogDO userJianHuYiYouXiao = useJianhuyiLogService.getUserJianHuYiYouXiaoAll(userTaskDO.getUserId(),userTaskDO.getCreateTime(),new Date());


            Double useJianhuyiDuration = userJianHuYiYouXiao.getUseJianhuyiDuration();//监护仪总的使用时长
            userTaskDO.setTotaluser(useJianhuyiDuration);
            long days = (new Date().getTime()-userTaskDO.getCreateTime().getTime())/1000/60/60/24;
            resultScore(1,userTaskDO,null,avgReadDuration,avgLookPhoneDuration,avgLookTvComputerDuration,avgSitTilt,avgReadLight,avgReadDistance,outdoorsDuration,useJianhuyiDuration/days);
            }
        }


    /**
     *  统计每天的数据情况
     * @param useJianhuyiLogDOList
     */
    private void countPerDay(UserTaskDO userTaskDO,List<UseJianhuyiLogDO> useJianhuyiLogDOList) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        useJianhuyiLogDOList.forEach(a->{
           a.setCreateTime(sdf.format(a.getAddTime()));
        });
        Double avgReadDuration = 0.0;//平均每次阅读时长
        Double outdoorsDuration = 0.0;//户外时间累计版本
        Double avgReadDistance = 0.0;//平均阅读距离
        Double avgReadLight = 0.0;//平均阅读光照
        Double avgLookPhoneDuration = 0.0;//平均单次看手机时长
        Double avgLookTvComputerDuration = 0.0;//平均单次看电脑及电视时长
        Double avgSitTilt = 0.0;//平均旋转角度
        Double allUseJianhuyi = 0.0;//有效监护仪使用时长
        int lookPhoneCount = 0;//看手机次数
        int lookScreenCount = 0;//看电脑屏幕的次数
        int count = 0;//阅读次数

        Double readDurtionNum = 0.0;
        Map<String, List<UseJianhuyiLogDO>> map1 = useJianhuyiLogDOList.stream()
                .collect(Collectors.groupingBy(UseJianhuyiLogDO::getCreateTime));
       for(Map.Entry<String,List<UseJianhuyiLogDO>> entry :map1.entrySet()){
           List<UseJianhuyiLogDO> sublist = entry.getValue().stream().filter(a->a.getStatus()!=null && a.getStatus()==1).collect(Collectors.toList());
           for(int i=0;i<sublist.size();i++){
               UseJianhuyiLogDO useJianhuyiLogDO=sublist.get(i);
               avgReadDistance += useJianhuyiLogDO.getReadDistance() * useJianhuyiLogDO.getReadDuration();
               avgSitTilt += useJianhuyiLogDO.getSitTilt() * useJianhuyiLogDO.getReadDuration();
               avgReadLight += useJianhuyiLogDO.getReadLight() * useJianhuyiLogDO.getReadDuration();
               readDurtionNum += useJianhuyiLogDO.getReadDuration();
               if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                   avgLookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
               }
               if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                   avgLookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
               }
               avgReadDuration += useJianhuyiLogDO.getReadDuration();
               if(i+1<sublist.size()) {
                   UseJianhuyiLogDO useJianhuyiLogDO1=sublist.get(i+1);
                   long minute = (sdf1.parse(useJianhuyiLogDO1.getSaveTime()).getTime() -
                           sdf1.parse(useJianhuyiLogDO.getSaveTime()).getTime()
                           - (long) (useJianhuyiLogDO.getReadDuration() * 60 * 1000)) / 1000 / 60;
                   if (minute >= 5 || i == 0) {
                       if (useJianhuyiLogDO.getLookPhoneDuration() != null && useJianhuyiLogDO.getLookPhoneDuration() > 0)
                               lookPhoneCount++;//看手机次数
                       if (useJianhuyiLogDO.getLookTvComputerDuration() != null && useJianhuyiLogDO.getLookTvComputerDuration() > 0)
                                lookScreenCount++;//看电脑屏幕的次数
                       if (useJianhuyiLogDO.getReadDuration() >= 5) {
                           count++;//阅读次数
                       }
                   }
               }
               DecimalFormat df = new DecimalFormat("#.##");
               if (avgReadDuration != null && count > 0) {
                   avgReadDuration=Double.parseDouble(df.format(avgReadDuration / count));
               }
               if (avgLookPhoneDuration > 0) {
                   avgLookPhoneDuration=Double.parseDouble(df.format(avgLookPhoneDuration / lookPhoneCount));
               }
               if (lookScreenCount > 0) {
                   avgLookTvComputerDuration=Double.parseDouble(df.format(avgLookTvComputerDuration / lookScreenCount));
               }
               if (readDurtionNum > 0) {
                   avgSitTilt=Double.parseDouble(df.format(avgSitTilt / readDurtionNum));
                   avgReadLight=Double.parseDouble(df.format(avgReadLight / readDurtionNum));
                   avgReadDistance=Double.parseDouble(df.format(avgReadDistance / readDurtionNum));
               } else {
                   avgSitTilt=0.0;
                   avgReadLight=0.0;
                   avgReadDistance=0.0;
               }
               outdoorsDuration=entry.getValue().stream()
                       .filter(a->a.getStatus()!=null && a.getStatus()==2)
                       .collect(Collectors.summingDouble(UseJianhuyiLogDO::getOutdoorsDuration));
               String createTime = entry.getKey();
               UseJianhuyiLogDO userJianHuYiYouXiao = useJianhuyiLogService.getUserJianHuYiYouXiao(userTaskDO.getUserId(),createTime);
               Double useJianhuyiDuration = userJianHuYiYouXiao.getUseJianhuyiDuration();
               UserTaskLinshiDO userTaskLinshiDO =  resultScore(0,userTaskDO,createTime,avgReadDuration,avgLookPhoneDuration,avgLookTvComputerDuration,avgSitTilt,avgReadLight,avgReadDistance,outdoorsDuration,useJianhuyiDuration);
           }
       }
    }

    /**
     * 统计分析最终评级
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
    private synchronized UserTaskLinshiDO resultScore(Integer flag, UserTaskDO userTaskDO,
                                         String createTime,
                                         Double avgReadDuration,
                                         Double avgLookPhoneDuration,
                                         Double avgLookTvComputerDuration,
                                         Double avgSitTilt,
                                         Double avgReadLight,
                                         Double avgReadDistance,
                                                      Double outdoorsDuration,
                                                      Double useJianhuyiDuration) {
        if(flag==0) {
            UserTaskLinshiDO userTaskLinshiDO = new UserTaskLinshiDO();
            userTaskLinshiDO.setUserId(userTaskDO.getUserId());
            userTaskLinshiDO.setTaskId(userTaskDO.getId());
            userTaskLinshiDO.setAvgRead(ResultUtils.resultAvgReadDuration(avgReadDuration));
            userTaskLinshiDO.setAvgLookPhone(ResultUtils.resultAvgLookPhoneDuration(avgLookPhoneDuration));
            userTaskLinshiDO.setAvgLookTv(ResultUtils.resultAvgLookTvComputerDuration(avgLookTvComputerDuration));
            userTaskLinshiDO.setAvgReadDistance(ResultUtils.resultAvgReadDistance(avgReadDistance));
            userTaskLinshiDO.setAvgLight(ResultUtils.resultAvgReadLight(avgReadLight));
            userTaskLinshiDO.setAvgSitTilt(ResultUtils.resultAvgSitTilt(avgSitTilt));
            userTaskLinshiDO.setAvgOut(ResultUtils.resultOutdoorsDuration(outdoorsDuration));
            userTaskLinshiDO.setEffectiveUseTime(ResultUtils.resultUseJianhuyiDuration(useJianhuyiDuration));
            userTaskLinshiDO.setHourtime(Double.toString(useJianhuyiDuration));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d=  simpleDateFormat.parse(createTime);
                userTaskLinshiDO.setCreateTime(d);
            } catch (ParseException e) {
            }
            Integer score = ResultUtils.countScore(userTaskDO,userTaskLinshiDO);
            userTaskLinshiDO.setScore(score);
            userTaskLinshiDO.setEyeRate(ResultUtils.totalDegree(null,userTaskLinshiDO));//当日等级
            userTaskLinshiService.save(userTaskLinshiDO);
        }else if(flag==1){
           int totalScore = userTaskLinshiService.getTotalScore(userTaskDO.getId());
            userTaskDO.setTotalScore(totalScore);//总积分
            userTaskDO.setAvgReadResult(ResultUtils.resultAvgReadDuration(avgReadDuration));
            userTaskDO.setAvgLookPhoneResult(ResultUtils.resultAvgLookPhoneDuration(avgLookPhoneDuration));
            userTaskDO.setAvgLookTvResult(ResultUtils.resultAvgLookTvComputerDuration(avgLookTvComputerDuration));
            userTaskDO.setAvgReadDistanceResult(ResultUtils.resultAvgReadDistance(avgReadDistance));
            userTaskDO.setAvgLightResult(ResultUtils.resultAvgReadLight(avgReadLight));
            userTaskDO.setAvgSitTiltResult(ResultUtils.resultAvgSitTilt(avgSitTilt));
            userTaskDO.setAvgOutResult(ResultUtils.resultOutdoorsDuration(outdoorsDuration));
            userTaskDO.setEffectiveUseTimeResult(ResultUtils.resultUseJianhuyiDuration(useJianhuyiDuration));
            userTaskDO.setCountGrade(ResultUtils.totalDegree(userTaskDO,null));//平均等级
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
