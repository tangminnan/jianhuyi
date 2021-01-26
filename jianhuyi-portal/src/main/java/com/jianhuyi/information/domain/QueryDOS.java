package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.List;


/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-25 09:40:42
 */
public class QueryDOS implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private Long uploadId;
    private String equipmentId;
    //数据信息（角度、震动、图片信息）
    private List<DataDO> dataDOList;
    //电量信息
    private List<EnergysDataDO> energysDataDOList;
    //错误信息
    private List<ErrorDataDO> errorDataDOList;


    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public List<DataDO> getDataDOList() {
        return dataDOList;
    }

    public void setDataDOList(List<DataDO> dataDOList) {
        this.dataDOList = dataDOList;
    }

    public List<EnergysDataDO> getEnergysDataDOList() {
        return energysDataDOList;
    }

    public void setEnergysDataDOList(List<EnergysDataDO> energysDataDOList) {
        this.energysDataDOList = energysDataDOList;
    }

    public List<ErrorDataDO> getErrorDataDOList() {
        return errorDataDOList;
    }

    public void setErrorDataDOList(List<ErrorDataDO> errorDataDOList) {
        this.errorDataDOList = errorDataDOList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "QueryDOS{" +
                "dataDOList=" + dataDOList +
                ", energysDataDOList=" + energysDataDOList +
                ", errorDataDOList=" + errorDataDOList +
                '}';
    }
}
