package com.jianhuyi.information.domain;

import java.io.Serializable;


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
}
