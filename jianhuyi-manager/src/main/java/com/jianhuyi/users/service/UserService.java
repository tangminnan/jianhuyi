package com.jianhuyi.users.service;

import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.EchartsDO;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.users.domain.UserDO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

	List<EchartsDO> selectGrade(List<UseJianhuyiLogDO> avgReadDuration);

	List<EchartsDO> getOutdoorsDuration(List<UseJianhuyiLogDO> avgOutdoorsDuration);

	List<EchartsDO> getReadDistance(List<UseJianhuyiLogDO> avgReadDistance);

	List<EchartsDO> getReadLight(List<UseJianhuyiLogDO> avgReadLight);

	List<EchartsDO> getLookPhoneDuration(List<UseJianhuyiLogDO> avgLookPhoneDuration);

	List<EchartsDO> getLookTvComputerDuration(List<UseJianhuyiLogDO> avgLookTvComputerDuration);

	List<EchartsDO> getSitTilt(List<UseJianhuyiLogDO> avgSitTilt);

	List<EchartsDO> getUseJianhuyiDuration(List<UseJianhuyiLogDO> avgUseJianhuyiDuration);

	List<EchartsDO> getSportDuration(List<UseJianhuyiLogDO> avgSportDuration);

    R importMember(MultipartFile file);

	boolean exit(Map<String, Object> params);

    List<UseJianhuyiLogDO> selectLastUse();

	UseJianhuyiLogDO getUseDay(String saveTime, Integer userId);
}
