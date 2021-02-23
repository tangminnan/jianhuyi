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

  private int runningTime;

  private int effectiveTime;

  private int coverTime;

  private int noneffectiveTime;

  private int serialNumber;

  public int getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(int serialNumber) {
    this.serialNumber = serialNumber;
  }

  public int getRunningTime() {
    return runningTime;
  }

  public void setRunningTime(int runningTime) {
    this.runningTime = runningTime;
  }

  public int getEffectiveTime() {
    return effectiveTime;
  }

  public void setEffectiveTime(int effectiveTime) {
    this.effectiveTime = effectiveTime;
  }

  public int getCoverTime() {
    return coverTime;
  }

  public void setCoverTime(int coverTime) {
    this.coverTime = coverTime;
  }

  public int getNoneffectiveTime() {
    return noneffectiveTime;
  }

  public void setNoneffectiveTime(int noneffectiveTime) {
    this.noneffectiveTime = noneffectiveTime;
  }

  public Long getUploadId() {
    return uploadId;
  }

  public void setUploadId(Long uploadId) {
    this.uploadId = uploadId;
  }

  public Integer getUserDurtion() {
    return userDurtion;
  }

  public void setUserDurtion(Integer userDurtion) {
    this.userDurtion = userDurtion;
  }

  public String getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(String equipmentId) {
    this.equipmentId = equipmentId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /** 设置：ID */
  public void setId(Long id) {
    this.id = id;
  }

  /** 获取：ID */
  public Long getId() {
    return id;
  }

  /** 设置：使用时间点（每节点乘以5s等于使用时长） */
  public void setUseTime(Date useTime) {
    this.useTime = useTime;
  }

  /** 获取：使用时间点（每节点乘以5s等于使用时长） */
  public Date getUseTime() {
    return useTime;
  }

  /** 设置：保存时间 */
  public void setSaveTime(Date saveTime) {
    this.saveTime = saveTime;
  }

  /** 获取：保存时间 */
  public Date getSaveTime() {
    return saveTime;
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
