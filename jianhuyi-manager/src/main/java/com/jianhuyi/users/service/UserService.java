package com.jianhuyi.users.service;

import java.util.List;
import java.util.Map;

import com.jianhuyi.information.domain.EchartsDO;
import com.jianhuyi.users.domain.UserDO;

/**
 * 用户信息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2018-09-27 10:18:38
 */

public interface UserService {

	UserDO get(Integer id);

	UserDO getidbyphone(String phone);

	List<UserDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(UserDO user);

	int update(UserDO user);

	int remove(Long id);

	int batchRemove(Long[] ids);

	Integer selectNum();

	List<EchartsDO> selectGrade();

	List<EchartsDO> getOutdoorsDuration();

	List<EchartsDO> getReadDistance();

	List<EchartsDO> getReadLight();

	List<EchartsDO> getLookPhoneDuration();

	List<EchartsDO> getLookTvComputerDuration();

	List<EchartsDO> getSitTilt();

	List<EchartsDO> getUseJianhuyiDuration();

	List<EchartsDO> getSportDuration();

}
