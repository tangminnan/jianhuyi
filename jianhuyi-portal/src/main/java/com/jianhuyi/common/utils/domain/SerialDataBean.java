package com.jianhuyi.common.utils.domain;

import java.util.List;

/** 历史数据 */
public class SerialDataBean {
  private String equipmentId;
  private Integer status; // 设备状态  阅读   0x01 非阅读 0x02    遮挡状态 0x04   无效   0x05
  private String startTime; // 时间 年月日时分秒
  private List<BaseDataBean> baseDatas; // 基础信息
  private List<PictureBean> pictures; // 图片信息

  public String getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(String equipmentId) {
    this.equipmentId = equipmentId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public List<BaseDataBean> getBaseDatas() {
    return baseDatas;
  }

  public void setBaseDatas(List<BaseDataBean> baseDatas) {
    this.baseDatas = baseDatas;
  }

  public List<PictureBean> getPictures() {
    return pictures;
  }

  public void setPictures(List<PictureBean> pictures) {
    this.pictures = pictures;
  }

  @Override
  public String toString() {
    return "SerialDataBean{"
        + "equipmentId='"
        + equipmentId
        + '\''
        + ", status="
        + status
        + ", startTime='"
        + startTime
        + '\''
        + ", baseDatas="
        + baseDatas
        + ", pictures="
        + pictures
        + '}';
  }
}
