package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-25 09:40:42
 */
public class BaseDataDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//dataid
	private Long dataid;
	//时间
	private String time;
	//角度信息
	private Double angles;
	//光照信息
	private Double lights;
	//距离信息
	private Double distances;
	private String distanceData;//16分位  阅读距离信息
	private Integer type;       //16分区  遮挡状态 0 遮挡   1 半遮挡   2无遮挡
	private Double distance;    //16分区  实际阅读距离
	private Integer userId;//用户ID
	private Integer uploadId;//上传人ID
	private String equipmentId;//设备号


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDataid() {
		return dataid;
	}

	public void setDataid(Long dataid) {
		this.dataid = dataid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getAngles() {
		return angles;
	}

	public void setAngles(Double angles) {
		this.angles = angles;
	}

	public Double getLights() {
		return lights;
	}

	public void setLights(Double lights) {
		this.lights = lights;
	}

	public Double getDistances() {
		return distances;
	}

	public void setDistances(Double distances) {
		this.distances = distances;
	}

	public String getDistanceData() {
		return distanceData;
	}

	public void setDistanceData(String distanceData) {
		this.distanceData = distanceData;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
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

	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
}
