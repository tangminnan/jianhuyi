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

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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

  private String token = null;

  @ResponseBody
  @PostMapping("/saveInit")
  R saveInit(@RequestBody MultipartFile initFile, int type) {
    String filename = initFile.getOriginalFilename();
    String[] str = filename.split("_");
    AtomicReference<String> token = null;
    try {
      FileUtil.uploadFile(
          initFile.getBytes(), bootdoConfig.getUploadPath() + "/initFile/", filename);

      HistoryDataBean historyDataBean = new HistoryDataBean();
      File file = new File(bootdoConfig.getUploadPath() + "/initFile/" + filename);
      if (file.exists()) {
        historyDataBean = DataParseUtil.readFileTo(file);
        historyDataBean.setEquipmentId(str[0]);
        historyDataBean.setUserId(Integer.parseInt(str[1]));
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
      // if (dataInitService.save(historyDataBean) > 0) {
      HistoryDataBean finalHistoryDataBean = historyDataBean;
      new Thread(
              () -> {
                try {
                  // 图片预测及计算
                  List<UseJianhuyiLogDO> useJianhuyiLogDOList =
                      yuceAnddetail(finalHistoryDataBean, type);
                  useJianhuyiLogService.saveList(useJianhuyiLogDOList);
                  System.out.println("==========统计数据保存完成=============");

                  List<UseRemindsDO> useRemindsDOList = getRemindsList(finalHistoryDataBean);
                  useRemindsService.saveList(useRemindsDOList);
                  System.out.println("===============震动数据保存完成==============");

                  List<UseTimeDO> useTimeDOList = getUseTime(finalHistoryDataBean);
                  useTimeService.saveList(useTimeDOList);
                  System.out.println("=============使用时长数据保存完成=======================");

                  SaveParamsDO saveParamsDOList = new SaveParamsDO();
                  saveParamsDOList.setUseJianhuyiLogDOList(useJianhuyiLogDOList);
                  saveParamsDOList.setUseRemindsDOList(useRemindsDOList);
                  saveParamsDOList.setUseTimeDOList(useTimeDOList);

                  AvgDataUtil.addOrUpdateData(saveParamsDOList);
                  /**
                   *  任务数据统计
                   */
                  if(useJianhuyiLogDOList.size()>0){
                    Long userId = useJianhuyiLogDOList.get(0).getUserId().longValue();
                    UserDO userDO = userService.getById(userId);
                    if(userDO!=null){
                      List<Long> list = Arrays.asList(userDO.getTaskId(), userDO.getTaskIds());
                      for (Long taskId : list) {
                        if(taskId==null) continue;
                        UserTaskDO userTaskDO = userTaskService.get(taskId);
                        if (userTaskDO != null) {
                          System.out.println(
                                  "统计分析任务数据=========" + userId + "================= " + userTaskDO.getId());
                          Date createTime = userTaskDO.getStartTime(); // 最近一次任务的开始时间
                          Integer taskTime = userTaskDO.getTaskTime(); // 最近一次任务的天数
                          Calendar calendar = Calendar.getInstance();
                          calendar.setTime(createTime);
                          calendar.add(Calendar.DAY_OF_YEAR, taskTime-1);
                          calendar.add(Calendar.HOUR, 20); // 任务的最大持续时间
                          Date taskDate = calendar.getTime();
                          if (taskDate.compareTo(new Date()) >= 0) { // 最近一次任务的数据统计
                            Map<String, Object> params = new HashMap<>();
                            params.put("userId", userId);
                            params.put("startTime", createTime);
                            params.put("endTime", new Date());
                            List<UseJianhuyiLogDO> useJianhuyiLogDOS =
                                    useJianhuyiLogService.getMyData(params);
                            System.out.println("有数据否======================" + useJianhuyiLogDOList.size());
                            if (useJianhuyiLogDOList.size() > 0) { // 创建线程进行统计分析
                              countPerDay(userTaskDO, useJianhuyiLogDOS); // 统计任务每天评级
                              countTotal(userTaskDO, useJianhuyiLogDOS); // /统计最终评级
                            }

                          } else {
                            System.out.println("上传的数据时间 已经超过最近任务的截止时间，数据不会被统计进入最近一次任务中了");
                            if(taskId==userDO.getTaskId())
                              userService.updateTaskIdNullInUser(userId);
                            else if(taskId==userDO.getTaskIds())
                              userService.updateTaskIdsNullInUser(userId);
                          }
                        }
                      }
                    }
                  }

                } catch (Exception e) {
                  e.printStackTrace();
                }
              })
          .start();

      return R.ok();
      // } else {
      // return R.error("解析失败");
      // }
    } catch (Exception e) {
      e.printStackTrace();
      return R.error(500, e.toString());
    }
  }

  // 1和2状态的数据预测及统计
  public List<UseJianhuyiLogDO> yuceAnddetail(HistoryDataBean finalHistoryDataBean, int type) {
    List<UseJianhuyiLogDO> useJianhuyiLogDOList = new LinkedList<>();
    DecimalFormat df = new DecimalFormat("#.##");

    for (SerialDataBean serialDataBean : finalHistoryDataBean.getDataDOList()) {
      UseJianhuyiLogDO useJianhuyiLogDO = new UseJianhuyiLogDO();
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
        Double readDistance = 0.0;
        Double readLight = 0.0;
        Double readDuration = 0.0;

        Double lookPhoneDuration = 0.0;
        Double lookTvComputerDuration = 0.0;
        Integer size = 0;
        for (PictureBean pictureBean : serialDataBean.getPictures()) {
          try {
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
              String result = getYuceResult(serialDataBean.getStatus(), multipartFile);
              if (serialDataBean.getStatus() == 1) {

                if (result.equals("phone")) { // 看手机
                  if (isPhone) {
                    lookphonetime += 0.33;
                    lookPhoneCount++;
                  } else {
                    lookphonetime += 3;
                  }
                  isPhone = false;
                } else if (result.equals("monitor")) { // 看电视
                  if (isTV) {
                    lookTvComputerDuration += 0.33;
                    lookTvCount++;
                  } else {
                    lookTvTime += 3;
                  }
                  isTV = false;
                } else if (result.equals("other")) { // 其他
                  result += "_screen";
                } else {
                  result = "error_screen";
                }
                pictureBean.setType("阅读" + result);
              } else if (serialDataBean.getStatus() == 2) { // 非阅读
                if (result.equals("outdoor")) { // 户外
                  if (isOutdoor) {
                    outdoorTime += 0.33;
                  } else {
                    outdoorTime += 3;
                  }
                  isOutdoor = false;
                } else if (result.equals("other")) { // 其他
                  result += "_outdoor";
                }
                pictureBean.setType("非阅读" + result);
              } else if (serialDataBean.getStatus() == 6) { // 户外
                result = "isOutDoor";
                pictureBean.setType("户外" + result);
                outdoorTime += 3;
              }
              if (!result.equals("")) {
                String url =
                    getUrl(finalHistoryDataBean.getUserId(), pictureBean.getTime(), result);
                String obsurl = OBSUtils.uploadFile(multipartFile, url);
                pictureBean.setFilename(obsurl);
              }

              if (lookPhoneCount > 0) {
                lookPhoneDuration = lookphonetime / lookPhoneCount;
              }
              if (lookTvCount > 0) {
                lookTvComputerDuration = lookTvTime / lookTvCount;
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

        if (serialDataBean.getStatus() == 1) { // 阅读
          for (BaseDataBean baseData : serialDataBean.getBaseDatas()) {
            if (baseData.getAngles() > 30) {
              baseData.setAngles(Math.abs(baseData.getAngles()) - 30);
            } else if (baseData.getAngles() < -30) {
              baseData.setAngles(Math.abs(baseData.getAngles()) + 30);
            }
            readDuration += 5;
            if (baseData.getDistances() <= 900) {
              sitTilt += baseData.getAngles();
              readDistance += baseData.getDistances();
              readLight += baseData.getLights();
              size++;
            }
          }

          if (size > 0) {
            sitTilt = sitTilt / size;
            readDistance = readDistance / size / 10;
            readLight = readLight / size / 10;
          }
        }

        useJianhuyiLogDO.setReadDuration(Double.parseDouble(df.format(readDuration / 60)));
        useJianhuyiLogDO.setReadDistance(Double.parseDouble(df.format(readDistance)));
        useJianhuyiLogDO.setSitTilt(Double.parseDouble(df.format(sitTilt)));
        useJianhuyiLogDO.setLookPhoneDuration(Double.parseDouble(df.format(lookPhoneDuration)));
        useJianhuyiLogDO.setLookTvComputerDuration(
            Double.parseDouble(df.format(lookTvComputerDuration)));
        useJianhuyiLogDO.setReadLight(Double.parseDouble(df.format(readLight)));
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

    return useJianhuyiLogDOList;
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

  /** 获取token */
  public String getToken() {
    if (token == null) {
      String url = "https://iam.myhuaweicloud.com/v3/auth/tokens";
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
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return token;
  }

  /** 获取预测结果 */
  public String getYuceResult(int status, MultipartFile fileUrl) {
    String result = "";
    String url = "";
    if (status == 1) {
      url =
          "https://fef550100d1a49c2b165253366d28887.apig.cn-north-4.huaweicloudapis.com/v1/infers/30bb23bb-1292-4827-aee9-8dab90c93a39";
    } else if (status == 2) {
      url =
          "https://a47c3c80e1e541b4804f42c0ada01103.apig.cn-north-4.huaweicloudapis.com/v1/infers/fd2a6094-d1e5-4554-9f7c-fcce11397cd6";
    }
    if (token == null) {
      token = getToken();
    }
    String respJson = HttpRequestUtils.upload(url, fileUrl, token);
    if (respJson != null && !respJson.equals("")) {
      JSONObject jsonObject = JSONObject.parseObject(respJson);
      result = jsonObject.get("predicted_label") + "";
    }
    return result;
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
      System.out.println(ResultUtils.totalDegree(userTaskDO, null));

      userTaskService.update(userTaskDO);
    }
    return null;
  }
}
