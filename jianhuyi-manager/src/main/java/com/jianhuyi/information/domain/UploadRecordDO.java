package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-03-25 14:23:41
 */
public class UploadRecordDO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int uploadId;

    private int userId;

    private Date saveTime;

    private String equipmentId;

    private Date addTime;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getUploadId() {
        return uploadId;
    }

    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Override
    public String toString() {
        return "UploadRecordDO{" +
                "uploadId=" + uploadId +
                ", userId=" + userId +
                ", saveTime='" + saveTime + '\'' +
                ", equipmentId='" + equipmentId + '\'' +
                '}';
    }
}
