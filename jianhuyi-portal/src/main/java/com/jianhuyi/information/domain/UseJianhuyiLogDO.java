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
// @Data
public class UseJianhuyiLogDO implements Serializable {
  private static final long serialVersionUID = 1L;

  // id
  private Integer id;
  // 用户Id
  private Integer userId;
  // 用户Id
  private Integer uploadId;
  // 数据测试时间
  private String saveTime;
  // 设备号
  private String equipmentId;
  // 添加时间
  private Date addTime;
  // 阅读时长(分钟）
  private Double readDuration;
  // 户外时长(小时）
  private Double outdoorsDuration;
  // 阅读距离(厘米)
  private Double readDistance;
  // 阅读光照(lux)
  private Double readLight;
  // 看手机时长(分钟)
  private Double lookPhoneDuration;
  // 看手机次数
  private Integer lookPhoneCount;
  // 看电脑次数
  private Integer lookTvComputerCount;
  // 看电脑电视时长(分钟）
  private Double lookTvComputerDuration;
  // 坐姿倾斜度
  private Double sitTilt;
  // 使用监护仪时长(小时）
  private Double useJianhuyiDuration;
  // 运动时长(小时)
  private Double sportDuration;
  // 删除标志(1:删除 0：未删除)
  private Integer delFlag;
  // 阅读状态
  private Integer status;

  private Integer num;

  private Double allreadDuration;
  // 治疗提醒
  private Integer remind;

  private int type;

  // 使用监护仪时长
  private Double userDurtion;

  /** 创建时间 */
  private String createTime;

  private Double runningTime;

  private Double effectiveTime;

  private Double coverTime;

  private Double noneffectiveTime;

  private int serialNumber;

  public int getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(int serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Double getRunningTime() {
    return runningTime;
  }

  public void setRunningTime(Double runningTime) {
    this.runningTime = runningTime;
  }

  public Double getEffectiveTime() {
    return effectiveTime;
  }

  public void setEffectiveTime(Double effectiveTime) {
    this.effectiveTime = effectiveTime;
  }

  public Double getCoverTime() {
    return coverTime;
  }

  public void setCoverTime(Double coverTime) {
    this.coverTime = coverTime;
  }

  public Double getNoneffectiveTime() {
    return noneffectiveTime;
  }

  public void setNoneffectiveTime(Double noneffectiveTime) {
    this.noneffectiveTime = noneffectiveTime;
  }

  public Double getUserDurtion() {
    return userDurtion;
  }

  public void setUserDurtion(Double userDurtion) {
    this.userDurtion = userDurtion;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getUploadId() {
    return uploadId;
  }

  public void setUploadId(Integer uploadId) {
    this.uploadId = uploadId;
  }

  public String getSaveTime() {
    return saveTime;
  }

  public void setSaveTime(String saveTime) {
    this.saveTime = saveTime;
  }

  public String getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(String equipmentId) {
    this.equipmentId = equipmentId;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  public Double getReadDuration() {
    return readDuration;
  }

  public void setReadDuration(Double readDuration) {
    this.readDuration = readDuration;
  }

  public Double getOutdoorsDuration() {
    return outdoorsDuration;
  }

  public void setOutdoorsDuration(Double outdoorsDuration) {
    this.outdoorsDuration = outdoorsDuration;
  }

  public Double getReadDistance() {
    return readDistance;
  }

  public void setReadDistance(Double readDistance) {
    this.readDistance = readDistance;
  }

  public Double getReadLight() {
    return readLight;
  }

  public void setReadLight(Double readLight) {
    this.readLight = readLight;
  }

  public Double getLookPhoneDuration() {
    return lookPhoneDuration;
  }

  public void setLookPhoneDuration(Double lookPhoneDuration) {
    this.lookPhoneDuration = lookPhoneDuration;
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

  public Double getLookTvComputerDuration() {
    return lookTvComputerDuration;
  }

  public void setLookTvComputerDuration(Double lookTvComputerDuration) {
    this.lookTvComputerDuration = lookTvComputerDuration;
  }

  public Double getSitTilt() {
    return sitTilt;
  }

  public void setSitTilt(Double sitTilt) {
    this.sitTilt = sitTilt;
  }

  public Double getUseJianhuyiDuration() {
    return useJianhuyiDuration;
  }

  public void setUseJianhuyiDuration(Double useJianhuyiDuration) {
    this.useJianhuyiDuration = useJianhuyiDuration;
  }

  public Double getSportDuration() {
    return sportDuration;
  }

  public void setSportDuration(Double sportDuration) {
    this.sportDuration = sportDuration;
  }

  public Integer getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(Integer delFlag) {
    this.delFlag = delFlag;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Double getAllreadDuration() {
    return allreadDuration;
  }

  public void setAllreadDuration(Double allreadDuration) {
    this.allreadDuration = allreadDuration;
  }

  public Integer getRemind() {
    return remind;
  }

  public void setRemind(Integer remind) {
    this.remind = remind;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
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
        + ", saveTime='"
        + saveTime
        + '\''
        + ", equipmentId='"
        + equipmentId
        + '\''
        + ", addTime="
        + addTime
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
        + ", lookTvComputerCount="
        + lookTvComputerCount
        + ", lookTvComputerDuration="
        + lookTvComputerDuration
        + ", sitTilt="
        + sitTilt
        + ", useJianhuyiDuration="
        + useJianhuyiDuration
        + ", sportDuration="
        + sportDuration
        + ", delFlag="
        + delFlag
        + ", status="
        + status
        + ", num="
        + num
        + ", allreadDuration="
        + allreadDuration
        + ", remind="
        + remind
        + ", type="
        + type
        + ", userDurtion="
        + userDurtion
        + ", createTime='"
        + createTime
        + '\''
        + ", runningTime="
        + runningTime
        + ", effectiveTime="
        + effectiveTime
        + ", coverTime="
        + coverTime
        + ", noneffectiveTime="
        + noneffectiveTime
        + ", serialNumber="
        + serialNumber
        + '}';
  }
}
