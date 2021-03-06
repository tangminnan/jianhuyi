package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 检测记录表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */
public class UseJianhuyiLogDO implements Serializable {
  private static final long serialVersionUID = 1L;

  // id
  private Integer id;
  // 用户Id
  private Integer userId;

  private Integer uploadId;
  // 添加时间
  private Date addTime;
  // 开始时间
  private String saveTime;
  // 设备号
  private String equipmentId;

  private Date startTime;
  private Date endTime;
  // 阅读时长(分钟）
  private Double readDuration;

  // 阅读时长(分钟）
  private Double allReadDuration;
  // 户外时长(小时）
  private Double outdoorsDuration;
  // 阅读距离(厘米)
  private Double readDistance;
  // 阅读光照(lux)
  private Double readLight;
  // 看手机时长(分钟)
  private Double lookPhoneDuration;

  private Double allLookPhoneDuration;

  private Double allLookTvComputerDuration;
  // 看手机次数
  private Integer lookPhoneCount;
  // 看电脑电视时长(分钟）
  private Double lookTvComputerDuration;
  // 看电脑电视次数
  private Integer lookTvComputerCount;
  // 坐姿倾斜度
  private Double sitTilt;
  // 使用监护仪时长(小时）
  private Double useJianhuyiDuration;
  // 运动时长(小时)
  private Double sportDuration;
  // 删除标志(1:删除 0：未删除)
  private Integer delFlag;

  private String week;

  private String month;

  private String quarter;

  private String year;

  private Integer num;

  private String day;

  private Integer status;

  private Integer remindsNum;

  private Double userDurtion;

  private int type;

  private String saveTime1;

  private int lightNum;

  private int sitNum;

  private int distanceNum;
  //平均阅读光照
  private Double avgLight;
  //平均坐姿
  private Double avgSit;

  public int getLightNum() {
    return lightNum;
  }

  public void setLightNum(int lightNum) {
    this.lightNum = lightNum;
  }

  public int getSitNum() {
    return sitNum;
  }

  public void setSitNum(int sitNum) {
    this.sitNum = sitNum;
  }

  public Double getAllLookPhoneDuration() {
    return allLookPhoneDuration;
  }

  public void setAllLookPhoneDuration(Double allLookPhoneDuration) {
    this.allLookPhoneDuration = allLookPhoneDuration;
  }

  public Double getAllLookTvComputerDuration() {
    return allLookTvComputerDuration;
  }

  public void setAllLookTvComputerDuration(Double allLookTvComputerDuration) {
    this.allLookTvComputerDuration = allLookTvComputerDuration;
  }

  public Double getAllReadDuration() {
    return allReadDuration;
  }

  public void setAllReadDuration(Double allReadDuration) {
    this.allReadDuration = allReadDuration;
  }

  public String getSaveTime1() {
    return saveTime1;
  }

  public void setSaveTime1(String saveTime1) {
    this.saveTime1 = saveTime1;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public Integer getUploadId() {
    return uploadId;
  }

  public void setUploadId(Integer uploadId) {
    this.uploadId = uploadId;
  }

  public Integer getRemindsNum() {
    return remindsNum;
  }

  public void setRemindsNum(Integer remindsNum) {
    this.remindsNum = remindsNum;
  }

  public Double getUserDurtion() {
    return userDurtion;
  }

  public void setUserDurtion(Double userDurtion) {
    this.userDurtion = userDurtion;
  }

  public Integer getLookPhoneCount() {
    return lookPhoneCount;
  }

  public void setLookPhoneCount(Integer lookPhoneCount) {
    this.lookPhoneCount = lookPhoneCount;
  }

  public Integer getLookTvComputerCount() {
    return lookTvComputerCount;
  }

  public void setLookTvComputerCount(Integer lookTvComputerCount) {
    this.lookTvComputerCount = lookTvComputerCount;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(String equipmentId) {
    this.equipmentId = equipmentId;
  }

  public String getSaveTime() {
    return saveTime;
  }

  public void setSaveTime(String saveTime) {
    this.saveTime = saveTime;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public String getWeek() {
    return week;
  }

  public void setWeek(String week) {
    this.week = week;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  /** 设置：id */
  public void setId(Integer id) {
    this.id = id;
  }

  /** 获取：id */
  public Integer getId() {
    return id;
  }

  /** 设置：用户Id */
  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  /** 获取：用户Id */
  public Integer getUserId() {
    return userId;
  }

  /** 设置：添加时间 */
  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  /** 获取：添加时间 */
  public Date getAddTime() {
    return addTime;
  }

  /** 设置：阅读时长(分钟） */
  public void setReadDuration(Double readDuration) {
    this.readDuration = readDuration;
  }

  /** 获取：阅读时长(分钟） */
  public Double getReadDuration() {
    return readDuration;
  }

  /** 设置：户外时长(小时） */
  public void setOutdoorsDuration(Double outdoorsDuration) {
    this.outdoorsDuration = outdoorsDuration;
  }

  /** 获取：户外时长(小时） */
  public Double getOutdoorsDuration() {
    return outdoorsDuration;
  }

  /** 设置：阅读距离(厘米) */
  public void setReadDistance(Double readDistance) {
    this.readDistance = readDistance;
  }

  /** 获取：阅读距离(厘米) */
  public Double getReadDistance() {
    return readDistance;
  }

  /** 设置：阅读光照(lux) */
  public void setReadLight(Double readLight) {
    this.readLight = readLight;
  }

  /** 获取：阅读光照(lux) */
  public Double getReadLight() {
    return readLight;
  }

  /** 设置：看手机时长(分钟) */
  public void setLookPhoneDuration(Double lookPhoneDuration) {
    this.lookPhoneDuration = lookPhoneDuration;
  }

  /** 获取：看手机时长(分钟) */
  public Double getLookPhoneDuration() {
    return lookPhoneDuration;
  }

  /** 设置：看电脑电视时长(分钟） */
  public void setLookTvComputerDuration(Double lookTvComputerDuration) {
    this.lookTvComputerDuration = lookTvComputerDuration;
  }

  /** 获取：看电脑电视时长(分钟） */
  public Double getLookTvComputerDuration() {
    return lookTvComputerDuration;
  }

  /** 设置：坐姿倾斜度 */
  public void setSitTilt(Double sitTilt) {
    this.sitTilt = sitTilt;
  }

  /** 获取：坐姿倾斜度 */
  public Double getSitTilt() {
    return sitTilt;
  }

  /** 设置：使用监护仪时长(小时） */
  public void setUseJianhuyiDuration(Double useJianhuyiDuration) {
    this.useJianhuyiDuration = useJianhuyiDuration;
  }

  /** 获取：使用监护仪时长(小时） */
  public Double getUseJianhuyiDuration() {
    return useJianhuyiDuration;
  }

  /** 设置：运动时长(小时) */
  public void setSportDuration(Double sportDuration) {
    this.sportDuration = sportDuration;
  }

  /** 获取：运动时长(小时) */
  public Double getSportDuration() {
    return sportDuration;
  }

  /** 设置：删除标志(1:删除 0：未删除) */
  public void setDelFlag(Integer delFlag) {
    this.delFlag = delFlag;
  }

  /** 获取：删除标志(1:删除 0：未删除) */
  public Integer getDelFlag() {
    return delFlag;
  }

  public int getDistanceNum() {
    return distanceNum;
  }

  public void setDistanceNum(int distanceNum) {
    this.distanceNum = distanceNum;
  }

  public Double getAvgLight() {
    return avgLight;
  }

  public void setAvgLight(Double avgLight) {
    this.avgLight = avgLight;
  }

  public Double getAvgSit() {
    return avgSit;
  }

  public void setAvgSit(Double avgSit) {
    this.avgSit = avgSit;
  }

  @Override
  public String toString() {
    return "UseJianhuyiLogDO{"
        + "id="
        + id
        + ", userId="
        + userId
        + ", uploadId="
        + uploadId
        + ", addTime="
        + addTime
        + ", saveTime='"
        + saveTime
        + '\''
        + ", equipmentId='"
        + equipmentId
        + '\''
        + ", startTime="
        + startTime
        + ", endTime="
        + endTime
        + ", readDuration="
        + readDuration
        + ", outdoorsDuration="
        + outdoorsDuration
        + ", readDistance="
        + readDistance
        + ", readLight="
        + readLight
        + ", lookPhoneDuration="
        + lookPhoneDuration
        + ", lookPhoneCount="
        + lookPhoneCount
        + ", lookTvComputerDuration="
        + lookTvComputerDuration
        + ", lookTvComputerCount="
        + lookTvComputerCount
        + ", sitTilt="
        + sitTilt
        + ", useJianhuyiDuration="
        + useJianhuyiDuration
        + ", sportDuration="
        + sportDuration
        + ", delFlag="
        + delFlag
        + ", week='"
        + week
        + '\''
        + ", month='"
        + month
        + '\''
        + ", quarter='"
        + quarter
        + '\''
        + ", year='"
        + year
        + '\''
        + ", num="
        + num
        + ", day='"
        + day
        + '\''
        + ", status="
        + status
        + ", remindsNum="
        + remindsNum
        + ", userDurtion="
        + userDurtion
        + '}';
  }
}
