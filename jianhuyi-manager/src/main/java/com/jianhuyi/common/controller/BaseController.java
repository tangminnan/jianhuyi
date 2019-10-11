package com.jianhuyi.common.controller;

import org.springframework.stereotype.Controller;

import com.jianhuyi.common.utils.ShiroUtils;
import com.jianhuyi.system.domain.UserDO;
import com.jianhuyi.system.domain.UserToken;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}