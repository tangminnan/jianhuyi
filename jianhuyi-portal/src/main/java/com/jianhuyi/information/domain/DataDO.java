package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-25 09:40:41
 */
public class DataDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//开始时间
	private String startTime;

	private Date updateTime;
	//1 阅读，2 非阅读，3 遮挡状态，4 无效
	private Integer status;
	private Long userId;
	//设备号
	private String equipmentId;
	//角度信息（以json数据保存）
	private List<BaseDataDO> baseDatas;
	//震动提醒（json存储）
	private List<RemaindDO> remaind;

	private String baseDatasDB;
	//电量信息（json存储）
	private String energysDB;
	//震动提醒（json存储）
	private String remaindDB;
	//错误信息（保存json数据）
	private String errorsDB;

	private List<DataImgDO> pictures;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getBaseDatasDB() {
		return baseDatasDB;
	}

	public void setBaseDatasDB(String baseDatasDB) {
		this.baseDatasDB = baseDatasDB;
	}

	public String getEnergysDB() {
		return energysDB;
	}

	public void setEnergysDB(String energysDB) {
		this.energysDB = energysDB;
	}

	public String getRemaindDB() {
		return remaindDB;
	}

	public void setRemaindDB(String remaindDB) {
		this.remaindDB = remaindDB;
	}

	public String getErrorsDB() {
		return errorsDB;
	}

	public void setErrorsDB(String errorsDB) {
		this.errorsDB = errorsDB;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<BaseDataDO> getBaseDatas() {
		return baseDatas;
	}

	public void setBaseDatas(List<BaseDataDO> baseDatas) {
		this.baseDatas = baseDatas;
	}


	public List<RemaindDO> getRemaind() {
		return remaind;
	}

	public void setRemaind(List<RemaindDO> remaind) {
		this.remaind = remaind;
	}


	public List<DataImgDO> getPictures() {
		return pictures;
	}

	public void setPictures(List<DataImgDO> pictures) {
		this.pictures = pictures;
	}

	@Override
	public String toString() {
		return "DataDO{" +
				"id=" + id +
				", startTime='" + startTime + '\'' +
				", updateTime=" + updateTime +
				", status=" + status +
				", equipmentId='" + equipmentId + '\'' +
				", baseDatas=" + baseDatas +
				", remaind=" + remaind +
				", baseDatasDB='" + baseDatasDB + '\'' +
				", energysDB='" + energysDB + '\'' +
				", remaindDB='" + remaindDB + '\'' +
				", errorsDB='" + errorsDB + '\'' +
				", pictures=" + pictures +
				'}';
	}
}
