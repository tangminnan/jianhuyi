package com.jianhuyi.users.service.impl;

import com.jianhuyi.common.utils.R;
import com.jianhuyi.device.dao.DeviceDao;
import com.jianhuyi.device.domain.DeviceDO;
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
import java.util.*;

@Service("UserService")
public class UserServiceImpl implements UserService {
  @Autowired private UserDao userDao;
  @Autowired private DeviceDao deviceDao;

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

  @Override
  public boolean exit(Map<String, Object> params) {
    boolean exit;
    exit = userDao.list(params).size() > 0;
    return exit;
  }

  @Override
  public List<UseJianhuyiLogDO> selectLastUse() {
    return userDao.selectLastUse();
  }

  @Override
  public UseJianhuyiLogDO getUseDay(String saveTime, Integer userId) {
    return userDao.getUseDay(saveTime, userId);
  }
  // 单次平均阅读时长

  @Override
  public List<EchartsDO> selectGrade(List<UseJianhuyiLogDO> avgReadDuration) {
    List<EchartsDO> list = new ArrayList<EchartsDO>();

    // a 优 ，b 良，c 中，d 差，e 极差
    Integer a = 0;
    Integer b = 0;
    Integer c = 0;
    Integer d = 0;
    Integer e = 0;

    for (UseJianhuyiLogDO useJianhuyiLogDO : avgReadDuration) {
      if (useJianhuyiLogDO != null) {
        if (useJianhuyiLogDO.getReadDuration() != null) {
          if (useJianhuyiLogDO.getReadDuration() < 20) {
            a++;
          } else if (useJianhuyiLogDO.getReadDuration() > 20
              && useJianhuyiLogDO.getReadDuration() <= 40) {
            b++;
          } else if (useJianhuyiLogDO.getReadDuration() == 20) {
            c++;
          } else if (useJianhuyiLogDO.getReadDuration() > 40
              && useJianhuyiLogDO.getReadDuration() <= 90) {
            d++;
          } else if (useJianhuyiLogDO.getReadDuration() > 90) {
            e++;
          }
        } else {
          continue;
        }
      }
    }

    list.add(new EchartsDO("优", a));
    list.add(new EchartsDO("良", b));
    list.add(new EchartsDO("中", c));
    list.add(new EchartsDO("差", d));
    list.add(new EchartsDO("极差", e));

    return list;
  }

  // 户外阅读时长
  @Override
  public List<EchartsDO> getOutdoorsDuration(List<UseJianhuyiLogDO> avgOutdoorsDuration) {
    List<EchartsDO> list = new ArrayList<EchartsDO>();

    // a 优 ，b 良，c 中，d 差，e 极差
    Integer a = 0;
    Integer b = 0;
    Integer c = 0;
    Integer d = 0;
    Integer e = 0;

    for (UseJianhuyiLogDO useJianhuyiLogDO : avgOutdoorsDuration) {
      if (useJianhuyiLogDO != null) {
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
        } else {
          continue;
        }
      }
    }

    list.add(new EchartsDO("优", a));
    list.add(new EchartsDO("良", b));
    list.add(new EchartsDO("中", c));
    list.add(new EchartsDO("差", d));
    list.add(new EchartsDO("极差", e));

    return list;
  }

  // 平均阅读距离
  @Override
  public List<EchartsDO> getReadDistance(List<UseJianhuyiLogDO> avgReadDistance) {
    List<EchartsDO> list = new ArrayList<EchartsDO>();

    // a 优 ，b 良，c 中，d 差，e 极差
    Integer a = 0;
    Integer b = 0;
    Integer c = 0;
    Integer d = 0;
    Integer e = 0;

    for (UseJianhuyiLogDO useJianhuyiLogDO : avgReadDistance) {
      if (useJianhuyiLogDO != null) {
        if (useJianhuyiLogDO.getReadDistance() != null) {
          if (useJianhuyiLogDO.getReadDistance() > 33) {
            a++;
          } else if (useJianhuyiLogDO.getReadDistance() >= 30
              && useJianhuyiLogDO.getReadDistance() < 33) {
            b++;
          } else if (useJianhuyiLogDO.getReadDistance() == 33) {
            c++;
          } else if (useJianhuyiLogDO.getReadDistance() >= 20
              && useJianhuyiLogDO.getReadDistance() < 30) {
            d++;
          } else if (useJianhuyiLogDO.getReadDistance() < 20) {
            e++;
          }
        } else {
          continue;
        }
      }
    }

    list.add(new EchartsDO("优", a));
    list.add(new EchartsDO("良", b));
    list.add(new EchartsDO("中", c));
    list.add(new EchartsDO("差", d));
    list.add(new EchartsDO("极差", e));

    return list;
  }

  // 平均阅读光照
  @Override
  public List<EchartsDO> getReadLight(List<UseJianhuyiLogDO> avgReadLight) {
    List<EchartsDO> list = new ArrayList<EchartsDO>();

    // a 优 ，b 良，c 中，d 差，e 极差
    Integer a = 0;
    Integer b = 0;
    Integer c = 0;
    Integer d = 0;
    Integer e = 0;
    for (UseJianhuyiLogDO useJianhuyiLogDO : avgReadLight) {
      if (useJianhuyiLogDO != null) {
        if (useJianhuyiLogDO.getReadLight() != null) {
          if (useJianhuyiLogDO.getReadLight() > 300) {
            a++;
          } else if (useJianhuyiLogDO.getReadLight() >= 250
              && useJianhuyiLogDO.getReadLight() < 300) {
            b++;
          } else if (useJianhuyiLogDO.getReadLight() == 300) {
            c++;
          } else if (useJianhuyiLogDO.getReadLight() >= 200
              && useJianhuyiLogDO.getReadLight() < 250) {
            d++;
          } else if (useJianhuyiLogDO.getReadLight() < 200) {
            e++;
          }
        } else {
          continue;
        }
      }
    }
    list.add(new EchartsDO("优", a));
    list.add(new EchartsDO("良", b));
    list.add(new EchartsDO("中", c));
    list.add(new EchartsDO("差", d));
    list.add(new EchartsDO("极差", e));

    return list;
  }

  // 平均单次看手机时长
  @Override
  public List<EchartsDO> getLookPhoneDuration(List<UseJianhuyiLogDO> avgLookPhoneDuration) {
    List<EchartsDO> list = new ArrayList<EchartsDO>();

    // a 优 ，b 良，c 中，d 差，e 极差
    Integer a = 0;
    Integer b = 0;
    Integer c = 0;
    Integer d = 0;
    Integer e = 0;

    for (UseJianhuyiLogDO useJianhuyiLogDO : avgLookPhoneDuration) {

      if (useJianhuyiLogDO != null) {
        if (useJianhuyiLogDO.getLookPhoneDuration() != null) {

          if (useJianhuyiLogDO.getLookPhoneDuration() < 10) {
            a++;
          } else if (useJianhuyiLogDO.getLookPhoneDuration() > 10
              && useJianhuyiLogDO.getLookPhoneDuration() <= 20) {
            b++;
          } else if (useJianhuyiLogDO.getLookPhoneDuration() == 10) {
            c++;
          } else if (useJianhuyiLogDO.getLookPhoneDuration() > 20
              && useJianhuyiLogDO.getLookPhoneDuration() <= 40) {
            d++;
          } else if (useJianhuyiLogDO.getLookPhoneDuration() > 40) {
            e++;
          }
        } else {
          continue;
        }
      }
    }

    list.add(new EchartsDO("优", a));
    list.add(new EchartsDO("良", b));
    list.add(new EchartsDO("中", c));
    list.add(new EchartsDO("差", d));
    list.add(new EchartsDO("极差", e));

    return list;
  }

  // 平均单次看电脑电视时长(分钟)
  @Override
  public List<EchartsDO> getLookTvComputerDuration(
      List<UseJianhuyiLogDO> avgLookTvComputerDuration) {
    List<EchartsDO> list = new ArrayList<EchartsDO>();

    // a 优 ，b 良，c 中，d 差，e 极差
    Integer a = 0;
    Integer b = 0;
    Integer c = 0;
    Integer d = 0;
    Integer e = 0;

    for (UseJianhuyiLogDO useJianhuyiLogDO : avgLookTvComputerDuration) {
      if (useJianhuyiLogDO != null) {
        if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
          if (useJianhuyiLogDO.getLookTvComputerDuration() < 20) {
            a++;
          } else if (useJianhuyiLogDO.getLookTvComputerDuration() > 20
              && useJianhuyiLogDO.getLookTvComputerDuration() <= 40) {
            b++;
          } else if (useJianhuyiLogDO.getLookTvComputerDuration() == 20) {
            c++;
          } else if (useJianhuyiLogDO.getLookTvComputerDuration() > 40
              && useJianhuyiLogDO.getLookTvComputerDuration() <= 60) {
            d++;
          } else if (useJianhuyiLogDO.getLookTvComputerDuration() > 60) {
            e++;
          }
        } else {
          continue;
        }
      }
    }
    list.add(new EchartsDO("优", a));
    list.add(new EchartsDO("良", b));
    list.add(new EchartsDO("中", c));
    list.add(new EchartsDO("差", d));
    list.add(new EchartsDO("极差", e));

    return list;
  }

  // 平均坐姿倾斜度
  @Override
  public List<EchartsDO> getSitTilt(List<UseJianhuyiLogDO> avgSitTilt) {
    List<EchartsDO> list = new ArrayList<EchartsDO>();

    // a 优 ，b 良，c 中，d 差，e 极差
    Integer a = 0;
    Integer b = 0;
    Integer c = 0;
    Integer d = 0;
    Integer e = 0;

    for (UseJianhuyiLogDO useJianhuyiLogDO : avgSitTilt) {
      if (useJianhuyiLogDO != null) {
        if (useJianhuyiLogDO.getSitTilt() != null) {
          if (useJianhuyiLogDO.getSitTilt() < 5) {
            a++;
          } else if (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10) {
            b++;
          } else if (useJianhuyiLogDO.getSitTilt() == 5) {
            c++;
          } else if (useJianhuyiLogDO.getSitTilt() > 10 && useJianhuyiLogDO.getSitTilt() <= 15) {
            d++;
          } else if (useJianhuyiLogDO.getSitTilt() > 15) {
            e++;
          }
        } else {
          continue;
        }
      }
    }
    list.add(new EchartsDO("优", a));
    list.add(new EchartsDO("良", b));
    list.add(new EchartsDO("中", c));
    list.add(new EchartsDO("差", d));
    list.add(new EchartsDO("极差", e));

    return list;
  }

  // 有效使用监护仪总时长
  @Override
  public List<EchartsDO> getUseJianhuyiDuration(List<UseJianhuyiLogDO> avgUseJianhuyiDuration) {
    List<EchartsDO> list = new ArrayList<EchartsDO>();

    // a 优 ，b 良，c 中，d 差，e 极差
    Integer a = 0;
    Integer b = 0;
    Integer c = 0;
    Integer d = 0;
    Integer e = 0;

    for (UseJianhuyiLogDO useJianhuyiLogDO : avgUseJianhuyiDuration) {
      if (useJianhuyiLogDO != null) {
        if (useJianhuyiLogDO.getUserDurtion() != null) {
          if (useJianhuyiLogDO.getUserDurtion() > 10) {
            a++;
          } else if (useJianhuyiLogDO.getUserDurtion() >= 8
              && useJianhuyiLogDO.getUserDurtion() < 10) {
            b++;
          } else if (useJianhuyiLogDO.getUserDurtion() == 10) {
            c++;
          } else if (useJianhuyiLogDO.getUserDurtion() >= 5
              && useJianhuyiLogDO.getUserDurtion() < 8) {
            d++;
          } else if (useJianhuyiLogDO.getUserDurtion() < 5) {
            e++;
          }
        } else {
          continue;
        }
      }
    }

    list.add(new EchartsDO("优", a));
    list.add(new EchartsDO("良", b));
    list.add(new EchartsDO("中", c));
    list.add(new EchartsDO("差", d));
    list.add(new EchartsDO("极差", e));

    return list;
  }

  // 运动总时长
  @Override
  public List<EchartsDO> getSportDuration(List<UseJianhuyiLogDO> avgSportDuration) {
    List<EchartsDO> list = new ArrayList<EchartsDO>();

    // a 优 ，b 良，c 中，d 差，e 极差
    Integer a = 0;
    Integer b = 0;
    Integer c = 0;
    Integer d = 0;
    Integer e = 0;

    for (UseJianhuyiLogDO useJianhuyiLogDO : avgSportDuration) {
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
        } else {
          continue;
        }
      }
    }

    list.add(new EchartsDO("优", a));
    list.add(new EchartsDO("良", b));
    list.add(new EchartsDO("中", c));
    list.add(new EchartsDO("差", d));
    list.add(new EchartsDO("极差", e));

    return list;
  }

  @Override
  public R importMember(MultipartFile file) {
    Map<String, Object> params = new HashMap<>();
    Map<String, Object> params1 = new HashMap<>();
    int num = 0;
    InputStream in = null;
    Workbook book = null;
    List<Integer> errnum = new ArrayList<>();
    try {
      if (file != null) {
        in = file.getInputStream();
        book = ExcelUtils.getBook(in);
        Sheet sheet = book.getSheetAt(0);
        Row row = null;
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
          try {
            row = sheet.getRow(rowNum);
            String phone = ExcelUtils.getCellFormatValue(row.getCell((short) 1)); // 手机号
            String name = ExcelUtils.getCellFormatValue(row.getCell((short) 2)); // 姓名
            String identityCard = ExcelUtils.getCellFormatValue(row.getCell((short) 3)); // 身份证号
            String sex = ExcelUtils.getCellFormatValue(row.getCell((short) 4)); // 性别
            String school = ExcelUtils.getCellFormatValue(row.getCell((short) 5)); // 学校
            String grade = ExcelUtils.getCellFormatValue(row.getCell((short) 6)); // 年级
            String studentNum = ExcelUtils.getCellFormatValue(row.getCell((short) 7)); // 学号
            String lVision = ExcelUtils.getCellFormatValue(row.getCell((short) 8)); // 左眼视力
            String rVision = ExcelUtils.getCellFormatValue(row.getCell((short) 9)); // 右眼视力
            String lEyeBallDiameter =
                ExcelUtils.getCellFormatValue(row.getCell((short) 10)); // 左眼球径
            String rEyeBallDiameter =
                ExcelUtils.getCellFormatValue(row.getCell((short) 11)); // 右眼球径
            String lColumnDiameter = ExcelUtils.getCellFormatValue(row.getCell((short) 12)); // 左眼柱径
            String rColumnDiameter = ExcelUtils.getCellFormatValue(row.getCell((short) 13)); // 右眼柱径
            String lEyeAxis = ExcelUtils.getCellFormatValue(row.getCell((short) 14)); // 左眼轴
            String rEyeAxis = ExcelUtils.getCellFormatValue(row.getCell((short) 15)); // 右眼轴
            String identity = ExcelUtils.getCellFormatValue(row.getCell((short) 16)); // 设备号
            String managerId = ExcelUtils.getCellFormatValue(row.getCell((short) 17)); // 设备号
            String isWearGlasses = ExcelUtils.getCellFormatValue(row.getCell((short) 18)); // 设备号

            UserDO user = new UserDO();
            DeviceDO deviceDO = new DeviceDO();

            if (phone != null && phone != "") {
              user.setPhone(phone);
            }

            if (name != null && name != "") {
              user.setName(name);
            } else {
              errnum.add(rowNum);
              continue;
            }
            if (identityCard != null && identityCard != "") {
              user.setIdentityCard(identityCard);
            }
            if (sex != null && !"".equals(sex)) {
              System.out.println("======sex===================" + sex instanceof String);
              if (sex.equals("男")) {
                user.setSex(1);
              } else if (sex.equals("女")) {
                user.setSex(2);
              } else {
                user.setSex(0);
              }
            } else {
              errnum.add(rowNum);
              continue;
            }

            if (school != null && school != "") {
              user.setSchool(school);
            } else {
              errnum.add(rowNum);
              continue;
            }
            if (grade != null && grade != "") {
              user.setGrade(grade);
            } else {
              errnum.add(rowNum);
              continue;
            }

            if (studentNum != null && studentNum != "") {
              user.setStudentNum(studentNum);
            } else {
              errnum.add(rowNum);
              continue;
            }
            if (lVision != null && lVision != "") {
              user.setlVision(Double.parseDouble(lVision));
            }

            if (rVision != null && rVision != "") {
              user.setrVision(Double.parseDouble(rVision));
            }
            if (lEyeBallDiameter != null && lEyeBallDiameter != "") {
              user.setlEyeBallDiameter(Double.parseDouble(lEyeBallDiameter));
            }
            if (rEyeBallDiameter != null && rEyeBallDiameter != "") {
              user.setrEyeBallDiameter(Double.parseDouble(rEyeBallDiameter));
            }
            if (lColumnDiameter != null && lColumnDiameter != "") {
              user.setlColumnDiameter(Double.parseDouble(lColumnDiameter));
            }
            if (rColumnDiameter != null && rColumnDiameter != "") {
              user.setrColumnDiameter(Double.parseDouble(rColumnDiameter));
            }
            if (lEyeAxis != null && lEyeAxis != "") {
              user.setlEyeAxis(Double.parseDouble(lEyeAxis));
            }
            if (rEyeAxis != null && rEyeAxis != "") {
              user.setrEyeAxis(Double.parseDouble(rEyeAxis));
            }
            if (identity != null && identity != "") {
              deviceDO.setIdentity(identity);
            }
            if (managerId != null && managerId != "") {
              user.setManagerId(Integer.parseInt(managerId));
            } else {
              user.setManagerId(0);
            }
            user.setDeleteFlag(1);
            user.setRegisterTime(new Date());
            user.setIsWearGlasses(isWearGlasses);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

            if (user.getIdentityCard() != null) {
              // 15位需要补年份
              if (user.getIdentityCard().length() == 15) {
                user.setBirthday(substringBir("19" + user.getIdentityCard().substring(6, 12)));
                user.setAge(
                    Integer.parseInt(sdf.format(new Date()))
                        - (substringAge("19" + user.getIdentityCard().substring(6, 12))));
                // 18位直接截取7到14位
              } else if (user.getIdentityCard().length() == 18) {
                user.setBirthday(substringBir(user.getIdentityCard().substring(6, 14)));
                user.setAge(
                    Integer.parseInt(sdf.format(new Date()))
                        - (substringAge(user.getIdentityCard().substring(6, 14))));
              }

              // 身份证号查重
              params.put("identityCard", identityCard);
              if (!exit(params)) {
                // 不存在的话查手机
                params.remove("identityCard");
                if (phone != null && phone != "") {
                  params.put("phone", phone);
                  // 如果不存在，保存用户
                  if (!exit(params)) {
                    save(user);
                    // 查询设备号是否存在
                    params1.put("identity", identity);
                    // 如果设备号已存在，查是否绑定用户
                    if (deviceDao.list(params1).size() > 0) {
                      // 如果设备已经绑定，则提示设备已绑定
                      if (deviceDao.list(params1).get(0).getUserId() != null) {
   //                     errnum.add(rowNum);
                      } else {
                        deviceDO.setUserId(list(params).get(0).getId());
                        deviceDO.setId(deviceDao.list(params1).get(0).getId());
                        deviceDO.setAccount(name);

                        deviceDao.update(deviceDO);
                      }
                    } else {
                      deviceDO.setUserId(list(params).get(0).getId());
                      deviceDO.setDeleted(0);
                      deviceDO.setDeviceType(0);
                      deviceDO.setDefaultDevice(1);
                      deviceDO.setAccount(name);
                      deviceDao.save(deviceDO);
                    }
                    num++;
                  } else {
                    continue;
                  }
                } else {
                  save(user);

                  // 查询设备号是否存在
                  params1.put("identity", identity);
                  // 如果设备号已存在，查是否绑定用户
                  if (deviceDao.list(params1).size() > 0) {
                    // 如果设备已经绑定，则提示设备已绑定
                    if (deviceDao.list(params1).get(0).getUserId() != null) {
 //                     errnum.add(rowNum);
                    } else {
                      deviceDO.setUserId(list(params).get(0).getId());
                      deviceDO.setId(deviceDao.list(params1).get(0).getId());
                      deviceDO.setAccount(name);
                      deviceDao.update(deviceDO);
                    }
                  } else {
 //                   errnum.add(rowNum);
                  }
                  num++;
                }
              } else {
                params1.put("userId", list(params).get(0).getId());
                // 如果用户已绑定设备号，就继续走
                if (deviceDao.list(params1).size() > 0) {
 //                   errnum.add(rowNum);
                    continue;
                } else {
                  params1.remove("userId");
                  params1.put("identity", identity);
                  // 如果设备号存在
                  if (deviceDao.list(params1).size() > 0) {
                    // 如果设备已绑定用户
                    if (deviceDao.list(params1).get(0).getUserId() != null
                        && !deviceDao.list(params1).get(0).getUserId().equals("")) {
  //                    errnum.add(rowNum);
                      continue;
                    } else {
                      deviceDO.setUserId(list(params).get(0).getId());
                      deviceDO.setId(deviceDao.list(params1).get(0).getId());
                      deviceDO.setAccount(name);
                      deviceDao.update(deviceDO);

                      num++;
                      continue;
                    }
                  } else {
                    errnum.add(rowNum);
                  }
                }
              }
            }

          } catch (Exception e) {
            e.printStackTrace();
            return R.error("导入失败！第" + rowNum + "条");
          }
        }
        if (errnum.size() > 0) {
          return R.ok("上传成功,共增加[" + num + "]条,第" + errnum + "条上传失败，用户已存在或设备已绑定");
        } else {
          return R.ok("上传成功,共增加[" + num + "]条");
        }

      } else {
        return R.error("请选择导入的文件!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (book != null) book.close();
        if (book != null) in.close();
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
    String date1 = yyyy + "-" + mm + "-" + dd;
    // 不抛出异常会报错
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
