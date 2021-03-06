package com.jianhuyi.owneruser.dao;

import java.util.List;
import java.util.Map;

import com.jianhuyi.information.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import com.jianhuyi.owneruser.domain.OwnerUserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

/**
 * 
 * @author tmn
 */
@Mapper
public interface OwnerUserDao {

	OwnerUserDO get(Long userId);
	
	OwnerUserDO getbyname(String username);
	
	List<OwnerUserDO> list(Map<String,Object> map);
	
	int save(OwnerUserDO user);
	
	int count(Map<String,Object> map);
	
	int update(OwnerUserDO user);
	
	int remove(Long userId);
	
	int updateFlag(OwnerUserDO user);

	OwnerUserDO getbyunionid(String unionid);

	OwnerUserDO getbyopenid(String openId);

	List<OwnerUserDO> getopenId(String phone);

	List<OwnerUserDO> getUnionid(String phone);

	OwnerUserDO getUserByphone(String phone);

	int updateUser(OwnerUserDO ownerUserDO);


    List<OwnerUserDO> getUserByIdCard(String idCard);

    List<UserDO> getStudent(String name);

	UserDO getByPhone(String phone);

	UserDO getById(Long userId);

	void updateScores(UserDO userDO);

    void updateTaskIdInUser(UserDO userDO);

	Integer getMyScore(Long userId);

    List<OwnerUserDO> getUserByIdCardAndSchoolAndGrade(@Param("identityCard") String identityCard,@Param("name") String name,@Param("grade") String grade);
}
