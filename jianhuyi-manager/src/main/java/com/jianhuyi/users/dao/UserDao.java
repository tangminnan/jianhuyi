package com.jianhuyi.users.dao;

import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.users.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户信息表
 * @author wjl
 * @email bushuo@163.com
 * @date 2018-09-27 10:18:38
 */
@Mapper
@Repository("UserDao")
public interface UserDao {

	UserDO get(Integer id);
	
	UserDO getidbyphone(String phone);
	
	List<UserDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	Integer selectNum();

	List<UseJianhuyiLogDO> queryUserRecordBetweenSum();

    List<UseJianhuyiLogDO> selectLastUse();

	UseJianhuyiLogDO getUseDay(@Param("saveTime") String saveTime, @Param("userId") Integer userId);
}
