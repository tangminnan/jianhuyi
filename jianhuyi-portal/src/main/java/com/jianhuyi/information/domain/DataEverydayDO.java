package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-03-02 17:18:05
 */
public class DataEverydayDO implements Serializable {
  private static final long serialVersionUID = 1L;

  // id
  private Integer id;
  // 上传者id
  private Integer uploadId;
  // 用户Id
  private Integer userId;
  // 设备号
  private String equipmentId;
  // 数据时间
  private String useTime;
  // 添加时间
  private Date addTime;
  // 总阅读时长
  private Double allReadDuration;
  // 平均阅读时长(分钟）
  private Double readDuration;
  // 总户外时长(小时）
  private Double outdoorsDuration;
  // 平均阅读距离(厘米)
  private Double readDistance;
  // 平均阅读光照(lux)
  private Double readLight;
  // 看手机时长(分钟)
  private Double lookPhoneDuration;
  // 看电脑电视时长(分钟）
  private Double lookTvComputerDuration;
  // 坐姿倾斜度
  private Double sitTilt;
  // 提醒次数
  private Integer remind;
  // 开机时长（s）,计算时取最大
  private Double runningTime;
  // 有效时长（s）,累计，计算时取最大
  private Double effectiveTime;
  // 遮挡
  private Double coverTime;
  // 无效
  private Double noneffectiveTime;

  /** 设置：id */
  public void setId(Integer id) {
    this.id = id;
  }
  /** 获取：id */
  public Integer getId() {
    return id;
  }
  /** 设置：上传者id */
  public void setUploadId(Integer uploadId) {
    this.uploadId = uploadId;
  }
  /** 获取：上传者id */
  public Integer getUploadId() {
    return uploadId;
  }
  /** 设置：用户Id */
  public void setUserId(Integer userId) {
    this.userId = userId;
  }
  /** 获取：用户Id */
  public Integer getUserId() {
    return userId;
  }
  /** 设置：设备号 */
  public void setEquipmentId(String equipmentId) {
    this.equipmentId = equipmentId;
  }
  /** 获取：设备号 */
  public String getEquipmentId() {
    return equipmentId;
  }
  /** 设置：数据时间 */
  public void setUseTime(String useTime) {
    this.useTime = useTime;
  }
  /** 获取：数据时间 */
  public String getUseTime() {
    return useTime;
  }
  /** 设置：添加时间 */
  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
  /** 获取：添加时间 */
  public Date getAddTime() {
    return addTime;
  }
  /** 设置：总阅读时长 */
  public void setAllReadDuration(Double allReadDuration) {
    this.allReadDuration = allReadDuration;
  }
  /** 获取：总阅读时长 */
  public Double getAllReadDuration() {
    return allReadDuration;
  }
  /** 设置：平均阅读时长(分钟） */
  public void setReadDuration(Double readDuration) {
    this.readDuration = readDuration;
  }
  /** 获取：平均阅读时长(分钟） */
  public Double getReadDuration() {
    return readDuration;
  }
  /** 设置：总户外时长(小时） */
  public void setOutdoorsDuration(Double outdoorsDuration) {
    this.outdoorsDuration = outdoorsDuration;
  }
  /** 获取：总户外时长(小时） */
  public Double getOutdoorsDuration() {
    return outdoorsDuration;
  }
  /** 设置：平均阅读距离(厘米) */
  public void setReadDistance(Double readDistance) {
    this.readDistance = readDistance;
  }
  /** 获取：平均阅读距离(厘米) */
  public Double getReadDistance() {
    return readDistance;
  }
  /** 设置：平均阅读光照(lux) */
  public void setReadLight(Double readLight) {
    this.readLight = readLight;
  }
  /** 获取：平均阅读光照(lux) */
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
  /** 设置：提醒次数 */
  public void setRemind(Integer remind) {
    this.remind = remind;
  }
  /** 获取：提醒次数 */
  public Integer getRemind() {
    return remind;
  }
  /** 设置：开机时长（s）,计算时取最大 */
  public void setRunningTime(Double runningTime) {
    this.runningTime = runningTime;
  }
  /** 获取：开机时长（s）,计算时取最大 */
  public Double getRunningTime() {
    return runningTime;
  }
  /** 设置：有效时长（s）,累计，计算时取最大 */
  public void setEffectiveTime(Double effectiveTime) {
    this.effectiveTime = effectiveTime;
  }
  /** 获取：有效时长（s）,累计，计算时取最大 */
  public Double getEffectiveTime() {
    return effectiveTime;
  }
  /** 设置：遮挡 */
  public void setCoverTime(Double coverTime) {
    this.coverTime = coverTime;
  }
  /** 获取：遮挡 */
  public Double getCoverTime() {
    return coverTime;
  }
  /** 设置：无效 */
  public void setNoneffectiveTime(Double noneffectiveTime) {
    this.noneffectiveTime = noneffectiveTime;
  }
  /** 获取：无效 */
  public Double getNoneffectiveTime() {
    return noneffectiveTime;
  }

  @Override
  public String toString() {
    return "DataEverydayDO{"
        + "id="
        + id
        + ", uploadId="
        + uploadId
        + ", userId="
        + userId
        + ", equipmentId='"
        + equipmentId
        + '\''
        + ", useTime='"
        + useTime
        + '\''
        + ", addTime="
        + addTime
        + ", allReadDuration="
        + allReadDuration
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
        + ", lookTvComputerDuration="
        + lookTvComputerDuration
        + ", sitTilt="
        + sitTilt
        + ", remind="
        + remind
        + ", runningTime="
        + runningTime
        + ", effectiveTime="
        + effectiveTime
        + ", coverTime="
        + coverTime
        + ", noneffectiveTime="
        + noneffectiveTime
        + '}';
  }
}
