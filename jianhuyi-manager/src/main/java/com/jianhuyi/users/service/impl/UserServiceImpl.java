package com.jianhuyi.users.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianhuyi.information.domain.EchartsDO;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.users.dao.UserDao;
import com.jianhuyi.users.domain.UserDO;
import com.jianhuyi.users.service.UserService;

@Service("UserService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public UserDO get(Integer id) {
		return userDao.get(id);
	}

	@Override
	public UserDO getidbyphone(String phone) {
		return userDao.getidbyphone(phone);
	}

	@Override
	public List<UserDO> list(Map<String, Object> map) {
		return userDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return userDao.count(map);
	}

	@Override
	public int save(UserDO user) {
		return userDao.save(user);
	}

	@Override
	public int update(UserDO user) {
		return userDao.update(user);
	}

	@Override
	public int remove(Long id) {
		return userDao.remove(id);
	}

	@Override
	public int batchRemove(Long[] ids) {
		return userDao.batchRemove(ids);
	}

	@Override
	public Integer selectNum() {
		return userDao.selectNum();
	}

	// 单次平均阅读时长
	@Override
	public List<EchartsDO> selectGrade() {
		List<EchartsDO> list = new ArrayList<EchartsDO>();

		// 查询各用户使用总数据
		List<UseJianhuyiLogDO> betweenSum = userDao.queryUserRecordBetweenSum();

		// a 优 ，b 良，c 标准，d 差，e 极差
		Integer a = 0;
		Integer b = 0;
		Integer c = 0;
		Integer d = 0;
		Integer e = 0;

		if (betweenSum.size() > 0) {
			for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
				if (useJianhuyiLogDO != null) {
					// 平均单次阅读时长
					if (useJianhuyiLogDO.getReadDuration() != null) {
						if (useJianhuyiLogDO.getReadDuration() / useJianhuyiLogDO.getNum() < 20) {
							a++;
						} else if (useJianhuyiLogDO.getReadDuration() / useJianhuyiLogDO.getNum() > 20
								&& useJianhuyiLogDO.getReadDuration() / useJianhuyiLogDO.getNum() <= 40) {
							b++;
						} else if (useJianhuyiLogDO.getReadDuration() / useJianhuyiLogDO.getNum() == 20) {
							c++;
						} else if (useJianhuyiLogDO.getReadDuration() / useJianhuyiLogDO.getNum() > 40
								&& useJianhuyiLogDO.getReadDuration() / useJianhuyiLogDO.getNum() <= 90) {
							d++;
						} else if (useJianhuyiLogDO.getReadDuration() / useJianhuyiLogDO.getNum() > 90) {
							e++;
						}

					}
				}

			}
		}
		list.add(new EchartsDO("优", a));
		list.add(new EchartsDO("良", b));
		list.add(new EchartsDO("标准", c));
		list.add(new EchartsDO("差", d));
		list.add(new EchartsDO("极差", e));

		return list;

	}

	// 户外阅读时长
	@Override
	public List<EchartsDO> getOutdoorsDuration() {
		List<EchartsDO> list = new ArrayList<EchartsDO>();

		// 查询各用户使用总数据
		List<UseJianhuyiLogDO> betweenSum = userDao.queryUserRecordBetweenSum();

		// a 优 ，b 良，c 标准，d 差，e 极差
		Integer a = 0;
		Integer b = 0;
		Integer c = 0;
		Integer d = 0;
		Integer e = 0;

		if (betweenSum.size() > 0) {
			for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
				if (useJianhuyiLogDO != null) {
					// 平均单次阅读时长
					if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
						if (useJianhuyiLogDO.getOutdoorsDuration() > 2) {
							a++;
						} else if (useJianhuyiLogDO.getOutdoorsDuration() >= 1
								&& useJianhuyiLogDO.getOutdoorsDuration() < 2) {
							b++;
						} else if (useJianhuyiLogDO.getOutdoorsDuration() == 2) {
							c++;
						} else if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5
								&& useJianhuyiLogDO.getOutdoorsDuration() < 1) {
							d++;
						} else if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
							e++;
						}

					}
				}

			}
		}
		list.add(new EchartsDO("优", a));
		list.add(new EchartsDO("良", b));
		list.add(new EchartsDO("标准", c));
		list.add(new EchartsDO("差", d));
		list.add(new EchartsDO("极差", e));

		return list;
	}

	// 平均阅读距离
	@Override
	public List<EchartsDO> getReadDistance() {
		List<EchartsDO> list = new ArrayList<EchartsDO>();

		// 查询各用户使用总数据
		List<UseJianhuyiLogDO> betweenSum = userDao.queryUserRecordBetweenSum();

		// a 优 ，b 良，c 标准，d 差，e 极差
		Integer a = 0;
		Integer b = 0;
		Integer c = 0;
		Integer d = 0;
		Integer e = 0;

		if (betweenSum.size() > 0) {
			for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
				if (useJianhuyiLogDO != null) {
					// 平均单次阅读时长
					if (useJianhuyiLogDO.getReadDistance() != null) {
						if (useJianhuyiLogDO.getReadDistance() / useJianhuyiLogDO.getNum() > 33) {
							a++;
						} else if (useJianhuyiLogDO.getReadDistance() / useJianhuyiLogDO.getNum() >= 30
								&& useJianhuyiLogDO.getReadDistance() / useJianhuyiLogDO.getNum() < 33) {
							b++;
						} else if (useJianhuyiLogDO.getReadDistance() / useJianhuyiLogDO.getNum() == 33) {
							c++;
						} else if (useJianhuyiLogDO.getReadDistance() / useJianhuyiLogDO.getNum() >= 20
								&& useJianhuyiLogDO.getReadDistance() / useJianhuyiLogDO.getNum() < 30) {
							d++;
						} else if (useJianhuyiLogDO.getReadDistance() / useJianhuyiLogDO.getNum() < 20) {
							e++;
						}

					}
				}

			}
		}
		list.add(new EchartsDO("优", a));
		list.add(new EchartsDO("良", b));
		list.add(new EchartsDO("标准", c));
		list.add(new EchartsDO("差", d));
		list.add(new EchartsDO("极差", e));

		return list;
	}

	// 平均阅读光照
	@Override
	public List<EchartsDO> getReadLight() {
		List<EchartsDO> list = new ArrayList<EchartsDO>();

		// 查询各用户使用总数据
		List<UseJianhuyiLogDO> betweenSum = userDao.queryUserRecordBetweenSum();

		// a 优 ，b 良，c 标准，d 差，e 极差
		Integer a = 0;
		Integer b = 0;
		Integer c = 0;
		Integer d = 0;
		Integer e = 0;

		if (betweenSum.size() > 0) {
			for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
				if (useJianhuyiLogDO != null) {
					// 平均单次阅读时长
					if (useJianhuyiLogDO.getReadLight() != null) {
						if (useJianhuyiLogDO.getReadLight() / useJianhuyiLogDO.getNum() > 300) {
							a++;
						} else if (useJianhuyiLogDO.getReadLight() / useJianhuyiLogDO.getNum() >= 250
								&& useJianhuyiLogDO.getReadLight() / useJianhuyiLogDO.getNum() < 300) {
							b++;
						} else if (useJianhuyiLogDO.getReadLight() / useJianhuyiLogDO.getNum() == 300) {
							c++;
						} else if (useJianhuyiLogDO.getReadLight() / useJianhuyiLogDO.getNum() >= 200
								&& useJianhuyiLogDO.getReadLight() / useJianhuyiLogDO.getNum() < 250) {
							d++;
						} else if (useJianhuyiLogDO.getReadLight() / useJianhuyiLogDO.getNum() < 200) {
							e++;
						}

					}
				}

			}
		}
		list.add(new EchartsDO("优", a));
		list.add(new EchartsDO("良", b));
		list.add(new EchartsDO("标准", c));
		list.add(new EchartsDO("差", d));
		list.add(new EchartsDO("极差", e));

		return list;
	}

	// 平均单次看手机时长
	@Override
	public List<EchartsDO> getLookPhoneDuration() {
		List<EchartsDO> list = new ArrayList<EchartsDO>();

		// 查询各用户使用总数据
		List<UseJianhuyiLogDO> betweenSum = userDao.queryUserRecordBetweenSum();

		// a 优 ，b 良，c 标准，d 差，e 极差
		Integer a = 0;
		Integer b = 0;
		Integer c = 0;
		Integer d = 0;
		Integer e = 0;

		if (betweenSum.size() > 0) {
			for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
				if (useJianhuyiLogDO != null) {
					// 平均单次阅读时长
					if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
						if (useJianhuyiLogDO.getLookPhoneDuration() / useJianhuyiLogDO.getNum() < 10) {
							a++;
						} else if (useJianhuyiLogDO.getLookPhoneDuration() / useJianhuyiLogDO.getNum() > 10
								&& useJianhuyiLogDO.getLookPhoneDuration() / useJianhuyiLogDO.getNum() <= 20) {
							b++;
						} else if (useJianhuyiLogDO.getLookPhoneDuration() / useJianhuyiLogDO.getNum() == 10) {
							c++;
						} else if (useJianhuyiLogDO.getLookPhoneDuration() / useJianhuyiLogDO.getNum() > 20
								&& useJianhuyiLogDO.getLookPhoneDuration() / useJianhuyiLogDO.getNum() <= 40) {
							d++;
						} else if (useJianhuyiLogDO.getLookPhoneDuration() / useJianhuyiLogDO.getNum() > 40) {
							e++;
						}

					}
				}

			}
		}
		list.add(new EchartsDO("优", a));
		list.add(new EchartsDO("良", b));
		list.add(new EchartsDO("标准", c));
		list.add(new EchartsDO("差", d));
		list.add(new EchartsDO("极差", e));

		return list;
	}

	// 平均单次看电脑电视时长(分钟)
	@Override
	public List<EchartsDO> getLookTvComputerDuration() {
		List<EchartsDO> list = new ArrayList<EchartsDO>();

		// 查询各用户使用总数据
		List<UseJianhuyiLogDO> betweenSum = userDao.queryUserRecordBetweenSum();

		// a 优 ，b 良，c 标准，d 差，e 极差
		Integer a = 0;
		Integer b = 0;
		Integer c = 0;
		Integer d = 0;
		Integer e = 0;

		if (betweenSum.size() > 0) {
			for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
				if (useJianhuyiLogDO != null) {
					// 平均单次阅读时长
					if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
						if (useJianhuyiLogDO.getLookTvComputerDuration() / useJianhuyiLogDO.getNum() < 20) {
							a++;
						} else if (useJianhuyiLogDO.getLookTvComputerDuration() / useJianhuyiLogDO.getNum() > 20
								&& useJianhuyiLogDO.getLookTvComputerDuration() / useJianhuyiLogDO.getNum() <= 40) {
							b++;
						} else if (useJianhuyiLogDO.getLookTvComputerDuration() / useJianhuyiLogDO.getNum() == 20) {
							c++;
						} else if (useJianhuyiLogDO.getLookTvComputerDuration() / useJianhuyiLogDO.getNum() > 40
								&& useJianhuyiLogDO.getLookTvComputerDuration() / useJianhuyiLogDO.getNum() <= 60) {
							d++;
						} else if (useJianhuyiLogDO.getLookTvComputerDuration() / useJianhuyiLogDO.getNum() > 60) {
							e++;
						}

					}
				}

			}
		}
		list.add(new EchartsDO("优", a));
		list.add(new EchartsDO("良", b));
		list.add(new EchartsDO("标准", c));
		list.add(new EchartsDO("差", d));
		list.add(new EchartsDO("极差", e));

		return list;
	}

	// 平均坐姿倾斜度
	@Override
	public List<EchartsDO> getSitTilt() {
		List<EchartsDO> list = new ArrayList<EchartsDO>();

		// 查询各用户使用总数据
		List<UseJianhuyiLogDO> betweenSum = userDao.queryUserRecordBetweenSum();

		// a 优 ，b 良，c 标准，d 差，e 极差
		Integer a = 0;
		Integer b = 0;
		Integer c = 0;
		Integer d = 0;
		Integer e = 0;

		if (betweenSum.size() > 0) {
			for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
				if (useJianhuyiLogDO != null) {
					// 平均单次阅读时长
					if (useJianhuyiLogDO.getSitTilt() != null) {
						if (useJianhuyiLogDO.getSitTilt() / useJianhuyiLogDO.getNum() < 5) {
							a++;
						} else if (useJianhuyiLogDO.getSitTilt() / useJianhuyiLogDO.getNum() > 5
								&& useJianhuyiLogDO.getSitTilt() / useJianhuyiLogDO.getNum() <= 10) {
							b++;
						} else if (useJianhuyiLogDO.getSitTilt() / useJianhuyiLogDO.getNum() == 5) {
							c++;
						} else if (useJianhuyiLogDO.getSitTilt() / useJianhuyiLogDO.getNum() > 10
								&& useJianhuyiLogDO.getSitTilt() / useJianhuyiLogDO.getNum() <= 15) {
							d++;
						} else if (useJianhuyiLogDO.getSitTilt() / useJianhuyiLogDO.getNum() > 15) {
							e++;
						}

					}
				}

			}
		}
		list.add(new EchartsDO("优", a));
		list.add(new EchartsDO("良", b));
		list.add(new EchartsDO("标准", c));
		list.add(new EchartsDO("差", d));
		list.add(new EchartsDO("极差", e));

		return list;
	}

	// 有效使用监护仪总时长
	@Override
	public List<EchartsDO> getUseJianhuyiDuration() {
		List<EchartsDO> list = new ArrayList<EchartsDO>();

		// 查询各用户使用总数据
		List<UseJianhuyiLogDO> betweenSum = userDao.queryUserRecordBetweenSum();

		// a 优 ，b 良，c 标准，d 差，e 极差
		Integer a = 0;
		Integer b = 0;
		Integer c = 0;
		Integer d = 0;
		Integer e = 0;

		if (betweenSum.size() > 0) {
			for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
				if (useJianhuyiLogDO != null) {
					// 平均单次阅读时长
					if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
						if (useJianhuyiLogDO.getUseJianhuyiDuration() > 10) {
							a++;
						} else if (useJianhuyiLogDO.getUseJianhuyiDuration() >= 8
								&& useJianhuyiLogDO.getUseJianhuyiDuration() < 10) {
							b++;
						} else if (useJianhuyiLogDO.getUseJianhuyiDuration() == 10) {
							c++;
						} else if (useJianhuyiLogDO.getUseJianhuyiDuration() >= 5
								&& useJianhuyiLogDO.getUseJianhuyiDuration() < 8) {
							d++;
						} else if (useJianhuyiLogDO.getUseJianhuyiDuration() < 5) {
							e++;
						}

					}
				}

			}
		}
		list.add(new EchartsDO("优", a));
		list.add(new EchartsDO("良", b));
		list.add(new EchartsDO("标准", c));
		list.add(new EchartsDO("差", d));
		list.add(new EchartsDO("极差", e));

		return list;
	}

	// 运动总时长
	@Override
	public List<EchartsDO> getSportDuration() {
		List<EchartsDO> list = new ArrayList<EchartsDO>();

		// 查询各用户使用总数据
		List<UseJianhuyiLogDO> betweenSum = userDao.queryUserRecordBetweenSum();

		// a 优 ，b 良，c 标准，d 差，e 极差
		Integer a = 0;
		Integer b = 0;
		Integer c = 0;
		Integer d = 0;
		Integer e = 0;

		if (betweenSum.size() > 0) {
			for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
				if (useJianhuyiLogDO != null) {
					if (useJianhuyiLogDO.getSportDuration() != null) {
						if (useJianhuyiLogDO.getSportDuration() > 2) {
							a++;
						} else if (useJianhuyiLogDO.getSportDuration() >= 1
								&& useJianhuyiLogDO.getSportDuration() < 2) {
							b++;
						} else if (useJianhuyiLogDO.getSportDuration() == 2) {
							c++;
						} else if (useJianhuyiLogDO.getSportDuration() >= 0.5
								&& useJianhuyiLogDO.getSportDuration() < 1) {
							d++;
						} else if (useJianhuyiLogDO.getSportDuration() < 0.5) {
							e++;
						}

					}
				}

			}
		}
		list.add(new EchartsDO("优", a));
		list.add(new EchartsDO("良", b));
		list.add(new EchartsDO("标准", c));
		list.add(new EchartsDO("差", d));
		list.add(new EchartsDO("极差", e));

		return list;
	}

	/*
	 * if(useJianhuyiLogDO.getOutdoorsDuration()!=null)
	 * 
	 * { outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration(); }
	 * if(useJianhuyiLogDO.getReadDistance()!=null)
	 * 
	 * { readDistance += useJianhuyiLogDO.getReadDistance(); }
	 * if(useJianhuyiLogDO.getReadLight()!=null)
	 * 
	 * { readLight += useJianhuyiLogDO.getReadLight(); }
	 * if(useJianhuyiLogDO.getLookPhoneDuration()!=null)
	 * 
	 * { lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration(); }
	 * if(useJianhuyiLogDO.getLookTvComputerDuration()!=null)
	 * 
	 * { lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
	 * } if(useJianhuyiLogDO.getSitTilt()!=null)
	 * 
	 * { sitTilt += useJianhuyiLogDO.getSitTilt(); }
	 * if(useJianhuyiLogDO.getUseJianhuyiDuration()!=null)
	 * 
	 * { useJianhuyiDuration += useJianhuyiLogDO.getUseJianhuyiDuration(); }
	 * if(useJianhuyiLogDO.getSportDuration()!=null)
	 * 
	 * { sportDuration += useJianhuyiLogDO.getSportDuration(); }
	 */

}
