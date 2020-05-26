package com.jianhuyi.information.domain;

import java.io.Serializable;


/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-25 09:40:42
 */
public class QueryDO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String outdoorApi;

	private String screenApi;

	public String getOutdoorApi() {
		return outdoorApi;
	}

	public void setOutdoorApi(String outdoorApi) {
		this.outdoorApi = outdoorApi;
	}

	public String getScreenApi() {
		return screenApi;
	}

	public void setScreenApi(String screenApi) {
		this.screenApi = screenApi;
	}
}
