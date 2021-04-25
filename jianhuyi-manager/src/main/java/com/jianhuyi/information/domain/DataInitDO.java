package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 原始文件解析表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-04-19 17:27:20
 */
public class DataInitDO implements Serializable {
  private static final long serialVersionUID = 1L;

  // id
  private Integer id;
  // 用户id
  private Integer userId;
  // 上传人id
  private Integer uploadId;
  // 设备id
  private String equipmentId;
  // 上传文件路径
  private String fileUrl;
  // 阅读信息
  private String readData;
  // 电源信息
  private String energysData;
  // 错误信息
  private String errorData;
  // 震动信息
  private String remaindData;
  //
  private String startTime;
  // 添加时间
  private Date addTime;
  // 类型，1=app上传解析，2=文件导入解析，3=工装解析
  private Integer type;

  /** 设置：id */
  public void setId(Integer id) {
    this.id = id;
  }
  /** 获取：id */
  public Integer getId() {
    return id;
  }
  /** 设置：用户id */
  public void setUserId(Integer userId) {
    this.userId = userId;
  }
  /** 获取：用户id */
  public Integer getUserId() {
    return userId;
  }
  /** 设置：上传人id */
  public void setUploadId(Integer uploadId) {
    this.uploadId = uploadId;
  }
  /** 获取：上传人id */
  public Integer getUploadId() {
    return uploadId;
  }
  /** 设置：设备id */
  public void setEquipmentId(String equipmentId) {
    this.equipmentId = equipmentId;
  }
  /** 获取：设备id */
  public String getEquipmentId() {
    return equipmentId;
  }
  /** 设置：上传文件路径 */
  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }
  /** 获取：上传文件路径 */
  public String getFileUrl() {
    return fileUrl;
  }
  /** 设置：阅读信息 */
  public void setReadData(String readData) {
    this.readData = readData;
  }
  /** 获取：阅读信息 */
  public String getReadData() {
    return readData;
  }
  /** 设置：电源信息 */
  public void setEnergysData(String energysData) {
    this.energysData = energysData;
  }
  /** 获取：电源信息 */
  public String getEnergysData() {
    return energysData;
  }
  /** 设置：错误信息 */
  public void setErrorData(String errorData) {
    this.errorData = errorData;
  }
  /** 获取：错误信息 */
  public String getErrorData() {
    return errorData;
  }
  /** 设置：震动信息 */
  public void setRemaindData(String remaindData) {
    this.remaindData = remaindData;
  }
  /** 获取：震动信息 */
  public String getRemaindData() {
    return remaindData;
  }
  /** 设置： */
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  /** 获取： */
  public String getStartTime() {
    return startTime;
  }
  /** 设置：添加时间 */
  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
  /** 获取：添加时间 */
  public Date getAddTime() {
    return addTime;
  }
  /** 设置：类型，1=app上传解析，2=文件导入解析，3=工装解析 */
  public void setType(Integer type) {
    this.type = type;
  }
  /** 获取：类型，1=app上传解析，2=文件导入解析，3=工装解析 */
  public Integer getType() {
    return type;
  }
}
