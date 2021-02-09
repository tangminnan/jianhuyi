package com.jianhuyi.owneruser.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jianhuyi.information.domain.UserDO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jianhuyi.common.domain.Tree;
import com.jianhuyi.owneruser.domain.OwnerUserDO;


public interface OwnerUserService {
	OwnerUserDO get(Long id);
	
	OwnerUserDO getbyname(String username);

	List<OwnerUserDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(OwnerUserDO user);

	int update(OwnerUserDO user);

	int remove(Long userId);

	boolean exit(Map<String, Object> params);

	OwnerUserDO getbyunionid(String unionid);

	OwnerUserDO getbyopenid(String openId);

	boolean getUnionid(Map<String, Object> params) ;

	boolean getopenId(Map<String, Object> params);

	OwnerUserDO getUserByphone(String phone);

	int updateUser(OwnerUserDO ownerUserDO);

    OwnerUserDO getUserByIdCard(String idCard);

    List<UserDO> getStudent(String name);

	UserDO getByPhone(String phone);

	UserDO getById(Long userId);

	void updateScores(UserDO userDO);

	void updateTaskIdInUser(Long userId, Long id);

    Integer getMyScore(Long userId);
}
