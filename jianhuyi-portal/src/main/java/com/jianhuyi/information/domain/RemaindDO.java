package com.jianhuyi.information.domain;

import java.io.Serializable;


/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-25 09:40:42
 */
public class RemaindDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	private Long userId;
	//dataid
	private Long dataid;
	//时间
	private String time;
	//震动类型 1 距离过近，2 光线较暗，3 坐姿不正
	private int type;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
