package com.jianhuyi.common.utils.domain;

import java.util.Date;
import java.util.List;

/** 历史数据原始数据 */
public class HistoryDataBean {

  private int id;
  private Integer userId; // 数据绑定人ID
  private Integer uploadId; // 上传人ID
  private String equipmentId; // 设备名
  private List<SerialDataBean> dataDOList; // 阅读信息
  private List<EnergyBean> energysDataDOList; // 电源信息
  private List<ErrorBean> errorDataDOList; // 报错信息
  private List<RemindBean> remaind; // 震动提醒

  private String fileUrl;

  private String readData; // 阅读信息
  private String energysData; // 电源信息
  private String errorData; // 报错信息
  private String remaindData; // 震动提醒

  private String startTime;

  private int type;

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  // 添加时间
  private Date addTime;

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  public String getReadData() {
    return readData;
  }

  public void setReadData(String readData) {
    this.readData = readData;
  }

  public String getEnergysData() {
    return energysData;
  }

  public void setEnergysData(String energysData) {
    this.energysData = energysData;
  }

  public String getErrorData() {
    return errorData;
  }

  public void setErrorData(String errorData) {
    this.errorData = errorData;
  }

  public String getRemaindData() {
    return remaindData;
  }

  public void setRemaindData(String remaindData) {
    this.remaindData = remaindData;
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

  public void setUploadId(int uploadId) {
    this.uploadId = uploadId;
  }

  public String getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(String equipmentId) {
    this.equipmentId = equipmentId;
  }

  public List<SerialDataBean> getDataDOList() {
    return dataDOList;
  }

  public void setDataDOList(List<SerialDataBean> dataDOList) {
    this.dataDOList = dataDOList;
  }

  public List<EnergyBean> getEnergysDataDOList() {
    return energysDataDOList;
  }

  public void setEnergysDataDOList(List<EnergyBean> energysDataDOList) {
    this.energysDataDOList = energysDataDOList;
  }

  public List<ErrorBean> getErrorDataDOList() {
    return errorDataDOList;
  }

  public void setErrorDataDOList(List<ErrorBean> errorDataDOList) {
    this.errorDataDOList = errorDataDOList;
  }

  public List<RemindBean> getRemaind() {
    return remaind;
  }

  public void setRemaind(List<RemindBean> remaind) {
    this.remaind = remaind;
  }

  @Override
  public String toString() {
    return "HistoryDataBean{"
        + "userId="
        + userId
        + ", uploadId="
        + uploadId
        + ", equipmentId='"
        + equipmentId
        + '\''
        + ", dataDOList="
        + dataDOList
        + ", energysDataDOList="
        + energysDataDOList
        + ", errorDataDOList="
        + errorDataDOList
        + ", remaind="
        + remaind
        + ", readData='"
        + readData
        + '\''
        + ", energysData='"
        + energysData
        + '\''
        + ", errorData='"
        + errorData
        + '\''
        + ", remaindData='"
        + remaindData
        + '\''
        + '}';
  }
}
