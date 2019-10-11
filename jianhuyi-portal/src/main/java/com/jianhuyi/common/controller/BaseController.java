package com.jianhuyi.common.controller;

import org.springframework.stereotype.Controller;

import com.jianhuyi.common.utils.ShiroUtils;
import com.jianhuyi.owneruser.domain.OwnerUserDO;
import com.jianhuyi.system.domain.UserToken;

@Controller
public class BaseController {
	public OwnerUserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
	public Long getforIds() {
		return getUser().getUserId();
	}
}