package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 检测记录表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */
public class SaveParamsDO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private Long uploadId;
    private String equipmentId;
    private List<UseJianhuyiLogDO> useJianhuyiLogDOList;
    private List<UseRemindsDO> useRemindsDOList;
    private List<UseTimeDO> useTimeDOList;

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

    public List<UseJianhuyiLogDO> getUseJianhuyiLogDOList() {
        return useJianhuyiLogDOList;
    }

    public void setUseJianhuyiLogDOList(List<UseJianhuyiLogDO> useJianhuyiLogDOList) {
        this.useJianhuyiLogDOList = useJianhuyiLogDOList;
    }

    public List<UseRemindsDO> getUseRemindsDOList() {
        return useRemindsDOList;
    }

    public void setUseRemindsDOList(List<UseRemindsDO> useRemindsDOList) {
        this.useRemindsDOList = useRemindsDOList;
    }

    public List<UseTimeDO> getUseTimeDOList() {
        return useTimeDOList;
    }

    public void setUseTimeDOList(List<UseTimeDO> useTimeDOList) {
        this.useTimeDOList = useTimeDOList;
    }

    @Override
    public String toString() {
        return "SaveParamsDO{" +
                "useJianhuyiLogDOList=" + useJianhuyiLogDOList +
                ", useRemindsDOList=" + useRemindsDOList +
                ", useTimeDOList=" + useTimeDOList +
                '}';
    }
}
