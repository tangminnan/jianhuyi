package com.jianhuyi.information.domain;

import java.io.Serializable;

/**
 * 咨询表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2018-04-13 14:42:52
 */
public class EchartsDO implements Serializable {
	private static final long serialVersionUID = 1L;

	String name;
	Integer value;

	public EchartsDO(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "EchartsDO [name=" + name + ", value=" + value + "]";
	}

}
