package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.utils.*;
import com.jianhuyi.common.utils.domain.*;
import com.jianhuyi.information.domain.*;
import com.jianhuyi.information.service.*;
import com.jianhuyi.owneruser.comment.DateUtil;
import com.jianhuyi.owneruser.service.OwnerUserService;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Collectors;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-25 09:40:41
 */
@Controller
@RequestMapping("/original/data")
public class DataController {
  @Autowired private DataService dataService;
  @Autowired private DataImgService dataImgService;
  @Autowired private EnergysDataService energysDataService;
  @Autowired private ErrorDataService errorDataService;
  @Autowired private BootdoConfig bootdoConfig;
  @Autowired private DataInitService dataInitService;
  @Autowired private UseJianhuyiLogService useJianhuyiLogService;
  @Autowired private UseRemindsService useRemindsService;
  @Autowired private UseTimeService useTimeService;
  @Autowired private OwnerUserService userService;
  @Autowired private UserTaskService userTaskService;
  @Autowired private UserTaskLinshiService userTaskLinshiService;

  @Autowired HttpSession session;

  @ResponseBody
  @PostMapping("/saveInit")
  R saveInit(@RequestBody MultipartFile initFile, int type) {
    // 刷新token，24小时过期
    String token = getToken();
    System.out.println("=========刷新token===========");
    String filename = initFile.getOriginalFilename();
    String[] str = filename.split("_");
    try {
      FileUtil.uploadFile(
          initFile.getBytes(), bootdoConfig.getUploadPath() + "/initFile/", filename);

      HistoryDataBean historyDataBean = new HistoryDataBean();
      File file = new File(bootdoConfig.getUploadPath() + "/initFile/" + filename);
      if (file.exists()) {
        historyDataBean = DataParseUtil.readFileTo(file);
        historyDataBean.setEquipmentId(str[0]);
        historyDataBean.setUserId(Integer.parseInt(str[1]));
        if (historyDataBean.getUploadId() == null) {
          historyDataBean.setUploadId(Integer.parseInt(str[1]));
        }
      }
      for (SerialDataBean serialDataBean : historyDataBean.getDataDOList()) {
        serialDataBean.setEquipmentId(str[0]);
      }
      historyDataBean.setReadData(JSON.toJSON(historyDataBean.getDataDOList()).toString());
      historyDataBean.setEnergysData(
          JSON.toJSON(historyDataBean.getEnergysDataDOList()).toString());
      historyDataBean.setErrorData(JSON.toJSON(historyDataBean.getErrorDataDOList()).toString());
      historyDataBean.setRemaindData(JSON.toJSON(historyDataBean.getRemaind()).toString());

      historyDataBean.setAddTime(new Date());
      if (historyDataBean.getDataDOList().size() > 0) {
        historyDataBean.setStartTime(historyDataBean.getDataDOList().get(0).getStartTime());
      }

      historyDataBean.setType(type);
      historyDataBean.setFileUrl("http://eyemonitor.dddmaker.com/files//initFile/" + filename);

      if (dataInitService.save(historyDataBean) > 0) {
        HistoryDataBean finalHistoryDataBean = historyDataBean;
        AtomicReferenceArray<DataEverydayDO> atomicReferenceArray = new AtomicReferenceArray<DataEverydayDO>(1000);
        AtomicLong atomicLong  =new AtomicLong();
        if (type == 1) {
         Thread thread= new Thread(
                  () -> {
                    try {
                      long startTime = System.nanoTime();

                      System.out.println("============计时开始=============");
                      List<Map<String, List>> allList =
                          yuceAnddetail(finalHistoryDataBean, type, token);
                      // 预测结果保存
                      List<DataDO> dataDOList = allList.get(0).get("dataDOList");
                      if (dataDOList.size() > 0) {
                        dataService.saveList(dataDOList);
                      }
                      System.out.println("================预测结果已保存===============================");
                      // 预测统计保存
                      List<UseJianhuyiLogDO> useJianhuyiLogDOList =
                          allList.get(0).get("useJianhuyiLogDOList");
                      if (useJianhuyiLogDOList.size() > 0) {
                        useJianhuyiLogService.saveList(useJianhuyiLogDOList);
                      }
                      System.out.println("=============统计数据保存完成=======================");

                      List<UseRemindsDO> useRemindsDOList = getRemindsList(finalHistoryDataBean);
                      if (useRemindsDOList.size() > 0) {
                        useRemindsService.saveList(useRemindsDOList);
                      }
                      System.out.println("=============震动数据保存完成=======================");

                      List<UseTimeDO> useTimeDOList = getUseTime(finalHistoryDataBean);
                      if (useTimeDOList.size() > 0) {
                        useTimeService.saveList(useTimeDOList);
                      }

                      System.out.println("=============使用时长数据保存完成=======================");

                      List<EnergysDataDO> energysDataDOS = getEnergy(finalHistoryDataBean);
                      if (energysDataDOS.size() > 0) {
                        energysDataService.saveList(energysDataDOS);
                      }
                      System.out.println("=============电量包保存完成=======================");

                      List<ErrorDataDO> errorDataDOS = getRrrors(finalHistoryDataBean);
                      if (errorDataDOS.size() > 0) {
                        errorDataService.saveList(errorDataDOS);
                      }
                      System.out.println("=================错误包保存完成========================");
                      SaveParamsDO saveParamsDOList = new SaveParamsDO();
                      saveParamsDOList.setUseJianhuyiLogDOList(useJianhuyiLogDOList);
                      saveParamsDOList.setUseRemindsDOList(useRemindsDOList);
                      saveParamsDOList.setUseTimeDOList(useTimeDOList);
                      saveParamsDOList.setEquipmentId(finalHistoryDataBean.getEquipmentId());
                      saveParamsDOList.setUploadId(
                          Long.parseLong(finalHistoryDataBean.getUploadId().toString()));
                      saveParamsDOList.setUserId(
                          Long.parseLong(finalHistoryDataBean.getUserId() + ""));
                      atomicLong.set(saveParamsDOList.getUserId());
                      AvgDataUtil.addOrUpdateData(saveParamsDOList,atomicReferenceArray);
                      System.out.println("=============今日数据已更新=======================");
                      long endTime = System.nanoTime();
                      long duration = (endTime - startTime);
                      System.out.println(
                          "============计时结束=============共"
                              + duration / (1000 * 60 * 60 * 24 * 60 * 60)
                              + "s");

                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  });
              thread.start();
              thread.join();
          /**
           *  此处不放在一个线程里执行，是为了防止新添加的代码报错，而影响原有的正常的执行
           */
          new Thread(()->{
              System.out.println("开启线程统计分析任务等级 评分");
              final Long userId = atomicLong.get();
              UserDO userDO = userService.getById(userId);
              if(userDO!=null) {
                  List<Long> list = Arrays.asList(userDO.getTaskId(), userDO.getTaskIds());
                  for (Long taskId : list) {
                      if (taskId == null) continue;
                      UserTaskDO userTaskDO = userTaskService.get(taskId);
                      if (userTaskDO != null) {
                          System.out.println(userId+"===================="+taskId);
                          Date startTime = userTaskDO.getStartTime(); // 最近一次任务的开始时间
                          Integer taskTime = userTaskDO.getTaskTime(); // 最近一次任务的天数
                          Calendar calendar = Calendar.getInstance();
                          calendar.setTime(startTime);
                          calendar.add(Calendar.DAY_OF_YEAR, taskTime - 1);
                          calendar.add(Calendar.HOUR, 20); // 任务的最大持续时间
                          Date taskDate = calendar.getTime();
                          if (taskDate.compareTo(new Date()) >= 0) { // 最近一次任务的数据统计
                             System.out.println("任务有效，开始统计分析");  Integer allScore=0;Double useTimes=0.0;
                             for(int index=0;index<atomicReferenceArray.length();index++){
                               DataEverydayDO dataEverydayDO = atomicReferenceArray.get(index);
                               if(dataEverydayDO!=null){
                                 String useTime = dataEverydayDO.getUseTime();
                                 Date date = null;
                                 if(useTime!=null && !"".equals(useTime)){
                                   try {
                                     date = new SimpleDateFormat("yyyy-MM-dd").parse(useTime);
                                   } catch (ParseException e) {
                                     e.printStackTrace();
                                   }
                                 }

                                 UserTaskLinshiDO userTaskLinshiDO = new UserTaskLinshiDO();
                                 userTaskLinshiDO.setUserId(userId);
                                 userTaskLinshiDO.setTaskId(taskId);
                                 userTaskLinshiDO.setCreateTime(date);
                                 userTaskLinshiDO.setAvgRead(ResultUtils.resultAvgReadDuration(dataEverydayDO.getReadDuration()));
                                 userTaskLinshiDO.setAvgLookPhone(
                                         ResultUtils.resultAvgLookPhoneDuration(dataEverydayDO.getLookPhoneDuration()));
                                 userTaskLinshiDO.setAvgLookTv(
                                         ResultUtils.resultAvgLookTvComputerDuration(dataEverydayDO.getLookTvComputerDuration()));
                                 userTaskLinshiDO.setAvgReadDistance(ResultUtils.resultAvgReadDistance(dataEverydayDO.getReadDistance()));
                                 userTaskLinshiDO.setAvgLight(ResultUtils.resultAvgReadLight(dataEverydayDO.getReadLight()));
                                 userTaskLinshiDO.setAvgSitTilt(ResultUtils.resultAvgSitTilt(dataEverydayDO.getSitTilt()));
                                 userTaskLinshiDO.setAvgOut(ResultUtils.resultOutdoorsDuration(dataEverydayDO.getOutdoorsDuration()));
                                 userTaskLinshiDO.setEffectiveUseTime(
                                         ResultUtils.resultUseJianhuyiDuration(dataEverydayDO.getEffectiveTime()));
                                 userTaskLinshiDO.setHourtime(Double.toString(dataEverydayDO.getEffectiveTime()));
                                 useTimes+=dataEverydayDO.getEffectiveTime();
                                 Integer score = ResultUtils.countScore(userTaskDO, userTaskLinshiDO);
                                 userTaskLinshiDO.setScore(score);//积分
                                 allScore+=score;
                                 UserTaskLinshiDO userLinShiTaskDO = userTaskLinshiService.getUserLinShiTaskDO(userId, taskId, date);
                                 if(userLinShiTaskDO!=null){//任务详情表中 已经存在这天的统计数据 ，只进行更新操作
                                   allScore-=userLinShiTaskDO.getScore();
                                   userTaskLinshiService.updateCurrentDay(userTaskLinshiDO);
                                 }else{//新增统计数据
                                   userTaskLinshiService.save(userTaskLinshiDO);
                                 }
                               }else{
                                 break;
                               }
                             }
                            /**
                             *  分析 统计总的任务等级
                             */
                            DataEverydayDO addAllData = AvgDataUtil.addAllData(userId,startTime,new Date());
                            userTaskDO.setTotalScore(allScore); // 总积分
                            userTaskDO.setTotaluser(useTimes); // 监护仪使用时长
                            userTaskDO.setAvgReadResult(ResultUtils.resultAvgReadDuration(addAllData.getReadDuration()));
                            userTaskDO.setAvgLookPhoneResult(
                                    ResultUtils.resultAvgLookPhoneDuration(addAllData.getLookPhoneDuration()));
                            userTaskDO.setAvgLookTvResult(
                                    ResultUtils.resultAvgLookTvComputerDuration(addAllData.getLookTvComputerDuration()));
                            userTaskDO.setAvgReadDistanceResult(ResultUtils.resultAvgReadDistance(addAllData.getReadDistance()));
                            userTaskDO.setAvgLightResult(ResultUtils.resultAvgReadLight(addAllData.getReadLight()));
                            userTaskDO.setAvgSitTiltResult(ResultUtils.resultAvgSitTilt(addAllData.getSitTilt()));
                            userTaskDO.setAvgOutResult(ResultUtils.resultOutdoorsDuration(addAllData.getOutdoorsDuration()));
                            userTaskDO.setEffectiveUseTimeResult(
                                    ResultUtils.resultUseJianhuyiDuration(useTimes));

                            userTaskDO.setCountGrade(ResultUtils.totalDegree(userTaskDO, null)); // 平均等级
                            userTaskService.update(userTaskDO);
                            userDO.setScores(userDO.getScores() + allScore);
                            userService.updateScores(userDO);
                          }else{
                            System.out.println("上传的数据时间 已经超过最近任务的截止时间，数据不会被统计进入最近一次任务中了");
                            if (taskId == userDO.getTaskId())
                                userService.updateTaskIdNullInUser(userId);
                            else if (taskId == userDO.getTaskIds())
                                userService.updateTaskIdsNullInUser(userId);
                          }
                      }
                  }
              }
          }).start();




        }
        return R.ok();
      } else {
        return R.error("解析失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return R.error(500, e.toString());
    }
  }


  // 1和2状态的数据预测及统计
  public List<Map<String, List>> yuceAnddetail(
      HistoryDataBean finalHistoryDataBean, int type, String token) {
    List list = new ArrayList();
    Map<String, Object> map = new HashMap<>();
    List<UseJianhuyiLogDO> useJianhuyiLogDOList = new LinkedList<>();
    List<DataDO> dataDOList = new LinkedList<>();
    DecimalFormat df = new DecimalFormat("#.##");

    for (SerialDataBean serialDataBean : finalHistoryDataBean.getDataDOList()) {
      UseJianhuyiLogDO useJianhuyiLogDO = new UseJianhuyiLogDO();

      DataDO dataDO = new DataDO();

      dataDO.setBaseDatasDB(JSON.toJSON(serialDataBean.getBaseDatas()).toString());
      dataDO.setImgs(JSON.toJSON(serialDataBean.getPictures()).toString());
      dataDO.setEquipmentId(finalHistoryDataBean.getEquipmentId());
      dataDO.setUserId(Long.parseLong(finalHistoryDataBean.getUserId() + ""));
      dataDO.setStatus(serialDataBean.getStatus());
      dataDO.setStartTime(serialDataBean.getStartTime());
      dataDO.setUpdateTime(new Date());
      if (finalHistoryDataBean.getUploadId() != null) {
        dataDO.setUploadId(Long.parseLong(finalHistoryDataBean.getUploadId() + ""));
      }
      dataDOList.add(dataDO);
      if (serialDataBean.getStatus() == 1
          || serialDataBean.getStatus() == 2
          || serialDataBean.getStatus() == 6) {
        // 看手机标识，看手机次数，用来统计状态平均
        boolean isPhone = true;
        int lookPhoneCount = 0;
        Double lookphonetime = 0.0;
        // 看电脑电视标识，看电视次数，用来统计状态平均
        boolean isTV = true;
        int lookTvCount = 0;
        Double lookTvTime = 0.0;
        boolean isOutdoor = true;
        Double outdoorTime = 0.0;

        Double sitTilt = 0.0;
        // Double readDistance = 0.0;
        Double readLight = 0.0;
        Double readDuration = 0.0;

        Double lookPhoneDuration = 0.0;
        Double lookTvComputerDuration = 0.0;
        int sitSize = 0;
        Integer size = 0;
        for (PictureBean pictureBean : serialDataBean.getPictures()) {
          try {
            if (pictureBean != null && pictureBean.getFilename() != null) {
              System.out.println("===========图片地址==============" + pictureBean.getFilename());
              File localFile = new File(pictureBean.getFilename());
              FileInputStream fileInputStream = new FileInputStream(localFile);
              MultipartFile multipartFile =
                  new MockMultipartFile(
                      localFile.getName(),
                      localFile.getName(),
                      ContentType.APPLICATION_OCTET_STREAM.toString(),
                      fileInputStream);

              if (localFile.exists()) {
                // 图片预测
                String result = "";
                if (serialDataBean.getStatus() == 1
                    || serialDataBean.getStatus() == 2
                    || serialDataBean.getStatus() == 6) {
                  result = getYuceResult(serialDataBean.getStatus(), multipartFile, token);
                }
                if (serialDataBean.getStatus() == 1 || serialDataBean.getStatus() == 2) {

                  if (result.equals("phone")) { // 看手机
                    if (isPhone) {
                      lookPhoneCount++;
                    }
                    lookphonetime += 1.5;

                    isPhone = false;
                  } else if (result.equals("monitor")) { // 看电视
                    if (isTV) {
                      lookTvCount++;
                    }
                    lookTvTime += 1.5;

                    isTV = false;
                  } else if (result.equals("other")) { // 其他
                    result += "_screen";
                  } else {
                    result = "error_screen";
                  }
                  pictureBean.setType("阅读" + result);
                } else if (serialDataBean.getStatus() == 6) { // 户外
                  if (result.equals("outdoor")) { // 户外
                    outdoorTime += 1.5;
                  } else if (result.equals("other")) { // 其他
                    result += "_outdoor";
                  }
                  pictureBean.setType("户外" + result);
                }
                if (!result.equals("")) {
                  String url =
                      getUrl(finalHistoryDataBean.getUserId(), pictureBean.getTime(), result);
                  String obsurl = OBSUtils.uploadFile(multipartFile, url);
                  pictureBean.setFilename(obsurl);
                }
              }
            }

          } catch (IOException e) {
            e.printStackTrace();
          }
        }

        if (lookPhoneCount > 0) {
          lookPhoneDuration = lookphonetime / lookPhoneCount;
        }
        if (lookTvCount > 0) {
          lookTvComputerDuration = lookTvTime / lookTvCount;
        }

        if (serialDataBean.getStatus() == 1) { // 阅读
          for (BaseDataBean baseData : serialDataBean.getBaseDatas()) {
            double sit = baseData.getAngles() - 10;

            sitTilt += sit;
            sitSize++;

            readDuration += 5;
            if (baseData.getLights() != null && baseData.getDistances() != null) {
              if ((baseData.getLights() > 0 && baseData.getLights() <= 10000)
                  && (baseData.getDistances() > 150 && baseData.getDistances() <= 600)) {
                Double linshilight = baseData.getLights();
                // 4.3为底数原始值的对数
                readLight += 100 * (Math.log(linshilight / 10) / Math.log(4.3));
                size++;
              }
            }
          }
        }

        useJianhuyiLogDO.setReadDuration(Double.parseDouble(df.format(readDuration / 60)));
        // useJianhuyiLogDO.setReadDistance(Double.parseDouble(df.format(readDistance)));

        useJianhuyiLogDO.setSitTilt(Double.parseDouble(df.format(sitTilt)));
        useJianhuyiLogDO.setSitNum(sitSize);

        useJianhuyiLogDO.setLookPhoneDuration(Double.parseDouble(df.format(lookPhoneDuration)));
        useJianhuyiLogDO.setLookTvComputerDuration(
            Double.parseDouble(df.format(lookTvComputerDuration)));
        useJianhuyiLogDO.setReadLight(Double.parseDouble(df.format(readLight)));
        useJianhuyiLogDO.setLightNum(size);

        useJianhuyiLogDO.setOutdoorsDuration(Double.parseDouble(df.format(outdoorTime)));

        useJianhuyiLogDO.setUserId(finalHistoryDataBean.getUserId());
        useJianhuyiLogDO.setUploadId(finalHistoryDataBean.getUploadId());
        useJianhuyiLogDO.setSaveTime(serialDataBean.getStartTime());
        useJianhuyiLogDO.setEquipmentId(serialDataBean.getEquipmentId());
        useJianhuyiLogDO.setAddTime(DateUtil.nowDate());
        if (serialDataBean.getStatus() == 6) {
          useJianhuyiLogDO.setStatus(2);
        } else {
          useJianhuyiLogDO.setStatus(serialDataBean.getStatus());
        }
        useJianhuyiLogDO.setType(type);

        useJianhuyiLogDOList.add(useJianhuyiLogDO);
      }
    }
    map.put("useJianhuyiLogDOList", useJianhuyiLogDOList);
    map.put("dataDOList", dataDOList);

    list.add(map);
    return list;
  }

  /** 保存震动信息 */
  public List<UseRemindsDO> getRemindsList(HistoryDataBean finalHistoryDataBean) {
    List<UseRemindsDO> useRemindsDOList = new LinkedList<>();
    for (RemindBean remindBean : finalHistoryDataBean.getRemaind()) {
      UseRemindsDO useRemindsDO = new UseRemindsDO();
      useRemindsDO.setRemindsTime(remindBean.getTime());
      useRemindsDO.setType(remindBean.getType());
      useRemindsDO.setSaveTime(new Date());
      useRemindsDO.setEquipmentId(finalHistoryDataBean.getEquipmentId());
      useRemindsDO.setUserId(finalHistoryDataBean.getUserId());
      useRemindsDO.setUploadId(finalHistoryDataBean.getUploadId());

      useRemindsDOList.add(useRemindsDO);
    }
    return useRemindsDOList;
  }

  /** 使用时长 */
  public List<UseTimeDO> getUseTime(HistoryDataBean finalHistoryDataBean) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    List<UseTimeDO> useTimeDOList = new LinkedList<>();
    for (EnergyBean energyBean : finalHistoryDataBean.getEnergysDataDOList()) {
      UseTimeDO useTimeDO = new UseTimeDO();

      useTimeDO.setCoverTime(energyBean.getCoverTime());
      useTimeDO.setEffectiveTime(energyBean.getEffectiveTime());
      useTimeDO.setRunningTime(energyBean.getRunningTime());
      useTimeDO.setNoneffectiveTime(energyBean.getNoneffectiveTime());
      useTimeDO.setSerialNumber(energyBean.getSerialNumber());
      try {
        useTimeDO.setUseTime(sdf.parse(energyBean.getTime()));
      } catch (ParseException e) {
        e.printStackTrace();
      }
      useTimeDO.setSaveTime(new Date());
      useTimeDO.setUserId(finalHistoryDataBean.getUserId());
      useTimeDO.setUploadId(finalHistoryDataBean.getUploadId());
      useTimeDO.setEquipmentId(finalHistoryDataBean.getEquipmentId());
      useTimeDOList.add(useTimeDO);
    }

    return useTimeDOList;
  }

  /*电量包保存*/
  public List<EnergysDataDO> getEnergy(HistoryDataBean finalHistoryDataBean) {
    List<EnergysDataDO> energysDataDOList = new LinkedList<>();
    if (finalHistoryDataBean.getEnergysDataDOList().size() > 0) {
      for (EnergyBean energyBean : finalHistoryDataBean.getEnergysDataDOList()) {
        EnergysDataDO energysDataDO = new EnergysDataDO();
        energysDataDO.setUserId(Long.parseLong(finalHistoryDataBean.getUserId().toString()));
        energysDataDO.setTime(energyBean.getTime());
        energysDataDO.setUpdateTime(new Date());
        energysDataDO.setPower(energyBean.getPower());
        energysDataDO.setUsbStatus(energyBean.getUsbStatus());
        energysDataDO.setEffectiveTime(energyBean.getEffectiveTime().toString());
        energysDataDO.setRunningTime(energyBean.getRunningTime().toString());
        energysDataDO.setCoverTime(energyBean.getCoverTime().toString());
        energysDataDO.setReadTime(energyBean.getReadTime().toString());
        energysDataDO.setUnreadTime(energyBean.getUnreadTime().toString());

        energysDataDOList.add(energysDataDO);
      }
    }
    return energysDataDOList;
  }

  /** 错误包保存 */
  public List<ErrorDataDO> getRrrors(HistoryDataBean finalHistoryDataBean) {
    List<ErrorDataDO> errorDataDOList = new LinkedList<>();
    if (finalHistoryDataBean.getErrorDataDOList().size() > 0) {
      for (ErrorBean errorBean : finalHistoryDataBean.getErrorDataDOList()) {
        ErrorDataDO errorDataDO = new ErrorDataDO();
        errorDataDO.setUserId(Long.parseLong(finalHistoryDataBean.getUserId().toString()));
        errorDataDO.setDisError(errorBean.getDisError());
        errorDataDO.setLightError(errorBean.getLightError());
        errorDataDO.setMemsError(errorBean.getMemsError());
        errorDataDO.setStoreError(errorBean.getStoreError());
        errorDataDO.setSxterror(errorBean.getSxterror());
        errorDataDO.setTime(errorBean.getTime());
        errorDataDO.setUpdateTime(new Date());

        errorDataDOList.add(errorDataDO);
      }
    }
    return errorDataDOList;
  }
  /** 获取token */
  public String getToken() {
    String url = "https://iam.myhuaweicloud.com/v3/auth/tokens";

    String token = "";
    try {
      String request =
          "{\n"
              + "\t\"auth\": {\n"
              + "\t\t\"identity\": {\n"
              + "\t\t\t\"methods\": [\n"
              + "\t\t\t\t\"password\"\n"
              + "\t\t\t],\n"
              + "\t\t\t\"password\": {\n"
              + "\t\t\t\t\"user\": {\n"
              + "\t\t\t\t\t\"domain\": {\n"
              + "\t\t\t\t\t\t\"name\": \"dddmaker\"\n"
              + "\t\t\t\t\t},\n"
              + "\t\t\t\t\t\"name\": \"jiasj\",\n"
              + "\t\t\t\t\t\"password\": \"Dm2020jsj\"\n"
              + "\t\t\t\t}\n"
              + "\t\t\t}\n"
              + "\t\t},\n"
              + "\t\t\"scope\": {\n"
              + "\t\t\t\"project\": {\n"
              + "\t\t\t\t\"name\": \"cn-north-4\"\n"
              + "\t\t\t}\n"
              + "\t\t}\n"
              + "\t}\n"
              + "}";
      token = HttpRequestUtils.getData(url, request, "POST").get("token").toString();
      session.setMaxInactiveInterval(60 * 60 * 24);
      session.setAttribute("token", token);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return session.getAttribute("token").toString();
  }

  /** 获取预测结果 */
  public String getYuceResult(int status, MultipartFile fileUrl, String token) {
    String result = "";
    String url = "";
    if (status == 1 || status == 2) {
      url =
          "https://fef550100d1a49c2b165253366d28887.apig.cn-north-4.huaweicloudapis.com/v1/infers/30bb23bb-1292-4827-aee9-8dab90c93a39";
    } else if (status == 6) {
      url =
          "https://a47c3c80e1e541b4804f42c0ada01103.apig.cn-north-4.huaweicloudapis.com/v1/infers/fd2a6094-d1e5-4554-9f7c-fcce11397cd6";
    }
    String respJson = HttpRequestUtils.upload(url, fileUrl, token);
    if (respJson == "预测失败") {
      respJson = HttpRequestUtils.upload(url, fileUrl, token);
    }
    if (respJson != null && !respJson.equals("")) {
      JSONObject jsonObject = JSONObject.parseObject(respJson);
      result = jsonObject.get("predicted_label") + "";
      return result;
    } else {
      return "失败";
    }
  }
  /** 上传路径 */
  public String getUrl(Integer userId, String time, String result) {

    String url =
        "app-img/"
            + userId
            + "/"
            + time.split(" ")[0].replace("-", "")
            + "/"
            + result
            + "/"
            + time.split(" ")[1].replace(":", "");
    return url;
  }

  /** 保存 */
  @ResponseBody
  @PostMapping("/save")
  public R save(@RequestBody MultipartFile originalFile) {
    String baseDatasDB;
    String remaindDB;
    String imgs;
    Integer result = 0;
    String filename = originalFile.getOriginalFilename();
    try {
      FileUtil.uploadFile(
          originalFile.getBytes(), bootdoConfig.getUploadPath() + "/originalFile/", filename);
      File file = new File(bootdoConfig.getUploadPath() + "/originalFile/" + filename);
      FileReader in = new FileReader(file);
      BufferedReader br = new BufferedReader(in);
      String str = br.readLine();

      if (str != null) {
        JSONObject queryJson = JSONObject.parseObject(new String(str));
        QueryDOS query = JSON.toJavaObject(queryJson, QueryDOS.class);

        if (query.getEnergysDataDOList().size() > 0) {
          for (EnergysDataDO energysDataDO : query.getEnergysDataDOList()) {
            energysDataDO.setUpdateTime(new Date());
            if (query.getUserId() != null) {
              energysDataDO.setUserId(query.getUserId());
            }
          }
          result += energysDataService.saveList(query.getEnergysDataDOList());
        }
        if (query.getErrorDataDOList().size() > 0) {
          for (ErrorDataDO errorDataDO : query.getErrorDataDOList()) {
            errorDataDO.setUpdateTime(new Date());
            if (query.getUserId() != null) {
              errorDataDO.setUserId(query.getUserId());
            }
          }
          result += errorDataService.saveList(query.getErrorDataDOList());
        }

        if (query.getDataDOList().size() > 0) {
          for (DataDO datum : query.getDataDOList()) {
            if (datum != null) {
              datum.setUpdateTime(new Date());
              if (datum.getBaseDatas() != null) {
                baseDatasDB = JSON.toJSON(datum.getBaseDatas()).toString();
                datum.setBaseDatasDB(baseDatasDB);
              }
              if (datum.getRemaind() != null) {
                remaindDB = JSON.toJSON(datum.getRemaind()).toString();
                datum.setRemaindDB(remaindDB);
              }
              if (datum.getPictures() != null) {
                imgs = JSON.toJSON(datum.getPictures()).toString();
                datum.setImgs(imgs);
              }
              if (query.getUserId() != null) {
                datum.setUserId(query.getUserId());
              }
              if (query.getUploadId() != null) {
                datum.setUploadId(query.getUploadId());
              }
              if (query.getEquipmentId() != null) {
                datum.setEquipmentId(query.getEquipmentId());
              }
            }
          }
          result += dataService.saveList(query.getDataDOList());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return R.error(500, e.toString());
    }
    if (result > 0) {
      FileUtil.deleteFile(bootdoConfig.getUploadPath() + "/originalFile/" + filename);
      return R.ok();
    } else {
      return R.error(-1, "上传失败");
    }
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
    Double useJianhuyiDuration = 0.0;
    List<UseJianhuyiLogDO> sublist =
        useJianhuyiLogDOList.stream()
            .filter(a -> a.getStatus() != null && a.getStatus() == 1)
            .collect(Collectors.toList());
    Map<String, Double> resultMap = ResultUtils.countData(null, sublist, null);
    outdoorsDuration =
        useJianhuyiLogDOList.stream()
            .filter(a -> a.getStatus() != null && a.getStatus() == 2)
            .collect(Collectors.summingDouble(UseJianhuyiLogDO::getOutdoorsDuration));
    resultScore(
        1,
        userTaskDO,
        null,
        resultMap.get("avgReadDuration"),
        resultMap.get("avgLookPhoneDuration"),
        resultMap.get("avgLookTvComputerDuration"),
        resultMap.get("avgSitTilt"),
        resultMap.get("avgReadLight"),
        resultMap.get("avgReadDistance"),
        outdoorsDuration,
        0.0);
  }

  /**
   * 统计每天的数据情况
   *
   * @param useJianhuyiLogDOList
   */
  private void countPerDay(UserTaskDO userTaskDO, List<UseJianhuyiLogDO> useJianhuyiLogDOList)
      throws ParseException {
    useJianhuyiLogDOList.forEach(
        a -> {
          a.setCreateTime(a.getSaveTime().substring(0, 10));
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

      Map<String, Double> resultMap =
          ResultUtils.countData(userTaskDO.getUserId(), sublist, entry.getKey());
      outdoorsDuration =
          entry.getValue().stream()
              .filter(a -> a.getStatus() != null && a.getStatus() == 2)
              .collect(Collectors.summingDouble(UseJianhuyiLogDO::getOutdoorsDuration));
      String createTime = entry.getKey();
      scores +=
          resultScore(
              0,
              userTaskDO,
              createTime,
              resultMap.get("avgReadDuration"),
              resultMap.get("avgLookPhoneDuration"),
              resultMap.get("avgLookTvComputerDuration"),
              resultMap.get("avgSitTilt"),
              resultMap.get("avgReadLight"),
              resultMap.get("avgReadDistance"),
              outdoorsDuration,
              resultMap.get("effectiveTime"));
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
      UserTaskLinshiDO userTaskLinshiDO11= userTaskLinshiService.getUserLinShiTaskDO(userTaskDO.getUserId(),userTaskDO.getId(),userTaskDO.getCreateTime());
      if (userTaskLinshiDO11 == null) {
        userTaskLinshiService.save(userTaskLinshiDO);
        return score;
      } else {//数据记录已经存在  做更新操作
        userTaskLinshiService.updateCurrentDay(userTaskLinshiDO);
        Integer score1 =
                userTaskLinshiDO11.getScore() == null ? 0 : userTaskLinshiDO11.getScore();
        return score - score1;
      }
    } else if (flag == 1) {
      List<UserTaskLinshiDO> l = userTaskLinshiService.getTotalScore(userTaskDO.getId());
      Integer totalScore = 0;

      for (UserTaskLinshiDO userTaskLinshiDO : l) {
        totalScore += userTaskLinshiDO.getScore();
        useJianhuyiDuration += Double.parseDouble(userTaskLinshiDO.getHourtime());
      }
      userTaskDO.setTotalScore(totalScore); // 总积分
      userTaskDO.setTotaluser(useJianhuyiDuration); // 监护仪使用时长
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
      // System.out.println(ResultUtils.totalDegree(userTaskDO, null));

      userTaskService.update(userTaskDO);
    }
    return null;
  }
}
