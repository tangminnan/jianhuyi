package com.jianhuyi.owneruser.service.impl;

import java.util.List;
import java.util.Map;

import com.jianhuyi.information.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.service.FileService;
import com.jianhuyi.owneruser.dao.OwnerUserDao;
import com.jianhuyi.owneruser.domain.OwnerUserDO;
import com.jianhuyi.owneruser.service.OwnerUserService;



@Transactional
@Service
public class OwnerUserServiceImpl implements OwnerUserService {
	@Autowired
	OwnerUserDao ownerUserMapper;
	@Autowired
	private FileService sysFileService;
	@Autowired
	private BootdoConfig bootdoConfig;
/*	private static final Logger logger = LoggerFactory.getLogger(UserService.class);*/

	@Override
	public OwnerUserDO get(Long id) {
		OwnerUserDO user = ownerUserMapper.get(id);
		return user;
	}
	
	@Override
	public OwnerUserDO getbyname(String username){
		
		OwnerUserDO user = ownerUserMapper.getbyname(username);
		return user;
	}

	@Override
	public List<OwnerUserDO> list(Map<String, Object> map) {
		return ownerUserMapper.list(map);
	}

	@Override
	public int save(OwnerUserDO user){
		return ownerUserMapper.save(user);
	}
	
	@Override
	public int count(Map<String, Object> map) {
		return ownerUserMapper.count(map);
	}


	@Override
	public int update(OwnerUserDO user) {
		int r = ownerUserMapper.update(user);
		
		return r;
	}

	@Override
	public int remove(Long userId) {
		/*userRoleMapper.removeByUserId(userId);*/
		return ownerUserMapper.remove(userId);
	}

	@Override
	public boolean exit(Map<String, Object> params) {
		boolean exit;
		exit = ownerUserMapper.list(params).size() > 0;
		return exit;
	}

	@Override
	public OwnerUserDO getbyunionid(String unionid) {
		return ownerUserMapper.getbyunionid(unionid);
	}

	@Override
	public OwnerUserDO getbyopenid(String openId) {
		return ownerUserMapper.getbyopenid(openId);
	}

	@Override
	public boolean getUnionid(Map<String, Object> params) {
		boolean exit;
		if(ownerUserMapper.list(params).size() > 0){
			exit =  ownerUserMapper.list(params).get(0).getUnionid() != null && !ownerUserMapper.list(params).get(0).getUnionid().isEmpty();
		}else{
			exit = false;
		}
		return exit;
	}

	@Override
	public boolean getopenId(Map<String, Object> params) {
		boolean exit;
		if(ownerUserMapper.list(params).size() > 0){
			exit =  ownerUserMapper.list(params).get(0).getOpenId() != null && !ownerUserMapper.list(params).get(0).getOpenId().isEmpty();
		}else{
			exit = false;
		}
		return exit;
	}

	@Override
	public OwnerUserDO getUserByphone(String phone) {
		return ownerUserMapper.getUserByphone(phone);
	}

	@Override
	public int updateUser(OwnerUserDO ownerUserDO) {
		return ownerUserMapper.updateUser(ownerUserDO);
	}

	@Override
	public List<OwnerUserDO> getUserByIdCard(String idCard) {
		return ownerUserMapper.getUserByIdCard(idCard);
	}

	@Override
	public List<UserDO> getStudent(String name) {
		return ownerUserMapper.getStudent(name);
	}

	@Override
	public UserDO getByPhone(String phone) {
		return ownerUserMapper.getByPhone(phone);
	}

	@Override
	public UserDO getById(Long userId) {
		return ownerUserMapper.getById(userId);
	}

	@Override
	public void updateScores(UserDO userDO) {
		 ownerUserMapper.updateScores(userDO);
	}

	@Override
	public void updateTaskIdInUser(UserDO userDO) {
		ownerUserMapper.updateTaskIdInUser(userDO);
	}

	@Override
	public Integer getMyScore(Long userId) {
		return ownerUserMapper.getMyScore(userId);
	}

	@Override
	public List<OwnerUserDO> getUserByIdCardAndSchoolAndGrade(String identityCard, String name, String grade) {
		return ownerUserMapper.getUserByIdCardAndSchoolAndGrade(identityCard,name,grade);
	}

	@Override
	public void updateTaskIdNullInUser(Long userId) {
		ownerUserMapper.updateTaskIdNullInUser(userId);
	}

}
