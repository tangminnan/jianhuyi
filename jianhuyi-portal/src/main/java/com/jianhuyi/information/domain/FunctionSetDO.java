package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 功能设置
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-14 15:23:48
 */
public class FunctionSetDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//用户Id
	private Long userId;
	//功能设置(1:开启 2:关闭)
	private Integer functionSet;
	//户外识别(1:开启2:关闭)
	private Integer outdoorsSet;
	//看手机检测(1:开启2:关闭)
	private Integer lookPhoneSet;
	//看电视电脑检测(1:开启2:关闭)
	private Integer lookTvComputer;
	//震动提示(1:开启2;关闭)
	private Integer shockTips;
	//坐姿矫正
	private String sitCorrect;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：用户Id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户Id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：功能设置(1:开启 2:关闭)
	 */
	public void setFunctionSet(Integer functionSet) {
		this.functionSet = functionSet;
	}
	/**
	 * 获取：功能设置(1:开启 2:关闭)
	 */
	public Integer getFunctionSet() {
		return functionSet;
	}
	/**
	 * 设置：户外识别(1:开启2:关闭)
	 */
	public void setOutdoorsSet(Integer outdoorsSet) {
		this.outdoorsSet = outdoorsSet;
	}
	/**
	 * 获取：户外识别(1:开启2:关闭)
	 */
	public Integer getOutdoorsSet() {
		return outdoorsSet;
	}
	/**
	 * 设置：看手机检测(1:开启2:关闭)
	 */
	public void setLookPhoneSet(Integer lookPhoneSet) {
		this.lookPhoneSet = lookPhoneSet;
	}
	/**
	 * 获取：看手机检测(1:开启2:关闭)
	 */
	public Integer getLookPhoneSet() {
		return lookPhoneSet;
	}
	/**
	 * 设置：看电视电脑检测(1:开启2:关闭)
	 */
	public void setLookTvComputer(Integer lookTvComputer) {
		this.lookTvComputer = lookTvComputer;
	}
	/**
	 * 获取：看电视电脑检测(1:开启2:关闭)
	 */
	public Integer getLookTvComputer() {
		return lookTvComputer;
	}
	/**
	 * 设置：震动提示(1:开启2;关闭)
	 */
	public void setShockTips(Integer shockTips) {
		this.shockTips = shockTips;
	}
	/**
	 * 获取：震动提示(1:开启2;关闭)
	 */
	public Integer getShockTips() {
		return shockTips;
	}
	/**
	 * 设置：坐姿矫正
	 */
	public void setSitCorrect(String sitCorrect) {
		this.sitCorrect = sitCorrect;
	}
	/**
	 * 获取：坐姿矫正
	 */
	public String getSitCorrect() {
		return sitCorrect;
	}
}
