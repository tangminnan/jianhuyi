package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-13 10:52:52
 */
public class UseRemindsDO implements Serializable {
  private static final long serialVersionUID = 1L;

  // ID
  private Long id;
  // 震动时间
  private String remindsTime;
  // 保存时间
  private Date saveTime;
  // 用户id
  private int userId;

  // 用户id
  private int uploadId;

  // 设备id
  private String equipmentId;
  // 震动次数
  private Integer remindsNum;

  private Integer type;

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public int getUploadId() {
    return uploadId;
  }

  public void setUploadId(int uploadId) {
    this.uploadId = uploadId;
  }

  public Integer getRemindsNum() {
    return remindsNum;
  }

  public void setRemindsNum(Integer remindsNum) {
    this.remindsNum = remindsNum;
  }

  public String getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(String equipmentId) {
    this.equipmentId = equipmentId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
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

  /** 设置：震动时间 */
  public void setRemindsTime(String remindsTime) {
    this.remindsTime = remindsTime;
  }

  /** 获取：震动时间 */
  public String getRemindsTime() {
    return remindsTime;
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
    return "UseRemindsDO{"
        + "id="
        + id
        + ", remindsTime="
        + remindsTime
        + ", saveTime="
        + saveTime
        + ", userId="
        + userId
        + ", equipmentId="
        + equipmentId
        + '}';
  }
}
