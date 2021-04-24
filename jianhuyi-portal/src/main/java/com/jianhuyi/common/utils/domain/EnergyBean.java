package com.jianhuyi.common.utils.domain;

import java.util.Date;

/** * 电量信息 */
public class EnergyBean {
  private Integer power; // 电量
  private Integer usbStatus; // usb状态
  private String time; // 时间
  private Integer effectiveTime; // 有效时长
  private Integer readTime; // 阅读时长
  private Integer unreadTime; // 非阅读时长
  private Integer coverTime; // 遮挡时长
  private Integer runningTime; // 开机时长

  private Integer noneffectiveTime; // 无效时长

  private Integer serialNumber;

  private Integer useId;

  private Date updateTime;

  public Integer getUseId() {
    return useId;
  }

  public void setUseId(Integer useId) {
    this.useId = useId;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(Integer serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Integer getNoneffectiveTime() {
    return noneffectiveTime;
  }

  public void setNoneffectiveTime(Integer noneffectiveTime) {
    this.noneffectiveTime = noneffectiveTime;
  }

  public Integer getCoverTime() {
    return coverTime;
  }

  public void setCoverTime(Integer coverTime) {
    this.coverTime = coverTime;
  }

  public Integer getRunningTime() {
    return runningTime;
  }

  public void setRunningTime(Integer runningTime) {
    this.runningTime = runningTime;
  }

  public Integer getPower() {
    return power;
  }

  public void setPower(Integer power) {
    this.power = power;
  }

  public Integer getUsbStatus() {
    return usbStatus;
  }

  public void setUsbStatus(Integer usbStatus) {
    this.usbStatus = usbStatus;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Integer getEffectiveTime() {
    return effectiveTime;
  }

  public void setEffectiveTime(Integer effectiveTime) {
    this.effectiveTime = effectiveTime;
  }

  public Integer getReadTime() {
    return readTime;
  }

  public void setReadTime(Integer readTime) {
    this.readTime = readTime;
  }

  public Integer getUnreadTime() {
    return unreadTime;
  }

  public void setUnreadTime(Integer unreadTime) {
    this.unreadTime = unreadTime;
  }

  @Override
  public String toString() {
    return "EnergyBean{"
        + "power="
        + power
        + ", usbStatus="
        + usbStatus
        + ", time='"
        + time
        + '\''
        + ", effectiveTime="
        + effectiveTime
        + ", readTime="
        + readTime
        + ", unreadTime="
        + unreadTime
        + ", coverTime="
        + coverTime
        + ", runningTime="
        + runningTime
        + ", noneffectiveTime="
        + noneffectiveTime
        + ", serialNumber="
        + serialNumber
        + '}';
  }
}
