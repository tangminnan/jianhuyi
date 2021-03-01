package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-13 10:52:52
 */
public class UseTimeDO implements Serializable {
  private static final long serialVersionUID = 1L;

  // ID
  private Long id;
  // 使用时间点（每节点乘以5s等于使用时长）
  private Date useTime;
  // 保存时间
  private Date saveTime;
  // 用户id
  private Long userId;
  // 用户id
  private Long uploadId;
  // 设备id
  private String equipmentId;

  private Integer userDurtion;

  private Integer runningTime;

  private Integer effectiveTime;

  private Integer coverTime;

  private Integer noneffectiveTime;

  private Integer serialNumber;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getUseTime() {
    return useTime;
  }

  public void setUseTime(Date useTime) {
    this.useTime = useTime;
  }

  public Date getSaveTime() {
    return saveTime;
  }

  public void setSaveTime(Date saveTime) {
    this.saveTime = saveTime;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getUploadId() {
    return uploadId;
  }

  public void setUploadId(Long uploadId) {
    this.uploadId = uploadId;
  }

  public String getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(String equipmentId) {
    this.equipmentId = equipmentId;
  }

  public Integer getUserDurtion() {
    return userDurtion;
  }

  public void setUserDurtion(Integer userDurtion) {
    this.userDurtion = userDurtion;
  }

  public Integer getRunningTime() {
    return runningTime;
  }

  public void setRunningTime(Integer runningTime) {
    this.runningTime = runningTime;
  }

  public Integer getEffectiveTime() {
    return effectiveTime;
  }

  public void setEffectiveTime(Integer effectiveTime) {
    this.effectiveTime = effectiveTime;
  }

  public Integer getCoverTime() {
    return coverTime;
  }

  public void setCoverTime(Integer coverTime) {
    this.coverTime = coverTime;
  }

  public Integer getNoneffectiveTime() {
    return noneffectiveTime;
  }

  public void setNoneffectiveTime(Integer noneffectiveTime) {
    this.noneffectiveTime = noneffectiveTime;
  }

  public Integer getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(Integer serialNumber) {
    this.serialNumber = serialNumber;
  }

  @Override
  public String toString() {
    return "UseTimeDO{"
        + "id="
        + id
        + ", useTime="
        + useTime
        + ", saveTime="
        + saveTime
        + ", userId="
        + userId
        + ", uploadId="
        + uploadId
        + ", equipmentId='"
        + equipmentId
        + '\''
        + ", userDurtion="
        + userDurtion
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
