package com.jianhuyi.users.service.impl;

import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.EchartsDO;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.system.config.ExcelUtils;
import com.jianhuyi.users.dao.UserDao;
import com.jianhuyi.users.domain.UserDO;
import com.jianhuyi.users.service.UserService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public R importMember(MultipartFile file) {
		int num = 0;
		InputStream in=null;
		Workbook book=null;
		List<Integer> errnum = new ArrayList<>();
		try {
			if(file != null){
				in = file.getInputStream();
				book = ExcelUtils.getBook(in);
				Sheet sheet = book.getSheetAt(0);
				Row row=null;
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					try {
						row = sheet.getRow(rowNum);
						String phone = ExcelUtils.getCellFormatValue(row.getCell((short)0));			//手机号
						String name = ExcelUtils.getCellFormatValue(row.getCell((short)1));		//姓名
						String identityCard = ExcelUtils.getCellFormatValue(row.getCell((short)2));		//身份证号
						String sex = ExcelUtils.getCellFormatValue(row.getCell((short)3));			//性别
						String school = ExcelUtils.getCellFormatValue(row.getCell((short)4));			//学校
						String grade = ExcelUtils.getCellFormatValue(row.getCell((short)5));		//年级
						String lVision = ExcelUtils.getCellFormatValue(row.getCell((short)6));		//左眼视力
						String rVision = ExcelUtils.getCellFormatValue(row.getCell((short)7));		//右眼视力
						String lEyeBallDiameter = ExcelUtils.getCellFormatValue(row.getCell((short)8));		//左眼球径
						String rEyeBallDiameter = ExcelUtils.getCellFormatValue(row.getCell((short)9));		//右眼球径
						String lColumnDiameter = ExcelUtils.getCellFormatValue(row.getCell((short)10));		//左眼柱径
						String rColumnDiameter = ExcelUtils.getCellFormatValue(row.getCell((short)11));		//右眼柱径
						String lEyeAxis = ExcelUtils.getCellFormatValue(row.getCell((short)12));		//左眼轴
						String rEyeAxis = ExcelUtils.getCellFormatValue(row.getCell((short)13));		//右眼轴
						UserDO user = new UserDO();

						if(phone != null && phone != ""){
							user.setPhone(phone);
						}else{
							errnum.add(rowNum);
							continue;
						}
						if(name != null && name !=""){
							user.setName(name);
						}else{
							errnum.add(rowNum);
							continue;
						}
						if(identityCard != null && identityCard != ""){
							user.setIdentityCard(identityCard);
						}
						if(sex != null && !"".equals(sex)){
							if(sex=="男"){
								user.setSex(1);
							}
							if(sex=="女"){
								user.setSex(2);
							}
						}
						if(school != null && school != ""){
							user.setSchool(school);
						}
						if(grade != null && grade != ""){
							user.setGrade(grade);
						}
						if(lVision != null && lVision != ""){
							user.setlVision(Double.parseDouble(lVision));
						}
						if(rVision != null && rVision != ""){
							user.setrVision(Double.parseDouble(rVision));
						}
						if(lEyeBallDiameter != null && lEyeBallDiameter != ""){
							user.setlEyeBallDiameter(Double.parseDouble(lEyeBallDiameter));
						}
						if(rEyeBallDiameter != null && rEyeBallDiameter != ""){
							user.setrEyeBallDiameter(Double.parseDouble(rEyeBallDiameter));
						}
						if(lColumnDiameter != null && lColumnDiameter != ""){
							user.setlColumnDiameter(Double.parseDouble(lColumnDiameter));
						}
						if(rColumnDiameter != null && rColumnDiameter != ""){
							user.setrColumnDiameter(Double.parseDouble(rColumnDiameter));
						}
						if(lEyeAxis != null && lEyeAxis != ""){
							user.setlEyeAxis(Double.parseDouble(lEyeAxis));
						}
						if(rEyeAxis != null && rEyeAxis != ""){
							user.setrEyeAxis(Double.parseDouble(rEyeAxis));
						}
						user.setDeleteFlag(1);
						user.setRegisterTime(new Date());

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						// 15位需要补年份
						if (user.getIdentityCard().length() == 15) {
							user.setBirthday(substringBir("19" + user.getIdentityCard().substring(6, 12)));
							user.setAge(Integer.parseInt(sdf.format(new Date()))
									- (substringAge("19" + user.getIdentityCard().substring(6, 12))));
							// 18位直接截取7到14位
						} else if (user.getIdentityCard().length() == 18) {
							user.setBirthday(substringBir(user.getIdentityCard().substring(6, 14)));
							user.setAge(
									Integer.parseInt(sdf.format(new Date())) - (substringAge(user.getIdentityCard().substring(6, 14))));
						}

						userDao.save(user);
						num++;
					} catch (Exception e) {
						e.printStackTrace();
						return R.error("导入失败！第"+rowNum+"条");
					}
				}
				if(errnum.size()>0){
					return R.ok("上传成功,共增加["+num+"]条,第"+errnum+"条上传失败，姓名或者手机号为空或手机号已存在");
				}else{
					return R.ok("上传成功,共增加["+num+"]条");
				}

			}else{
				return R.error("请选择导入的文件!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(book!=null)
					book.close();
				if(book!=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return R.error();
    }

	public Date substringBir(String day) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		String yyyy = day.substring(0, 4);
		String mm = day.substring(4, 6);
		String dd = day.substring(6);
		String date1 = yyyy+"-"+mm+"-"+dd;
		//不抛出异常会报错
		date = format.parse(date1);
		return date;
	}

	public Integer substringAge(String day) {
		Integer yyyy = Integer.parseInt(day.substring(0, 4));
		return yyyy;
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
