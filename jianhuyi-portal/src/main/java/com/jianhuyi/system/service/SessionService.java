package com.jianhuyi.system.service;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.jianhuyi.owneruser.domain.OwnerUserDO;
import com.jianhuyi.system.domain.UserOnline;

@Service
public interface SessionService {
	List<UserOnline> list();

	List<OwnerUserDO> listOnlineUser();

	Collection<Session> sessionList();
	
	boolean forceLogout(String sessionId);
}
