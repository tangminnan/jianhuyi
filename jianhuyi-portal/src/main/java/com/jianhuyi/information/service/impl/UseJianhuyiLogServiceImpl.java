package com.jianhuyi.information.service.impl;

import com.jianhuyi.information.dao.UseJianhuyiLogDao;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.domain.UseRemindsDO;
import com.jianhuyi.information.domain.UseTimeDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;
import com.jianhuyi.information.service.UseRemindsService;
import com.jianhuyi.information.service.UseTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jianhuyi.common.utils.DateUtils.format;


@Service
public class UseJianhuyiLogServiceImpl implements UseJianhuyiLogService {
    @Autowired
    private UseJianhuyiLogDao useJianhuyiLogDao;
    @Autowired
    private UseRemindsService useRemindsService;
    @Autowired
    private UseTimeService useTimeService;

    @Override
    public UseJianhuyiLogDO get(Integer id) {
        return useJianhuyiLogDao.get(id);
    }

    @Override
    public List<UseJianhuyiLogDO> list(Map<String, Object> map) {
        return useJianhuyiLogDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return useJianhuyiLogDao.count(map);
    }

    @Override
    public int save(UseJianhuyiLogDO useJianhuyiLog) {
        return useJianhuyiLogDao.save(useJianhuyiLog);
    }

    @Override
    public int update(UseJianhuyiLogDO useJianhuyiLog) {
        return useJianhuyiLogDao.update(useJianhuyiLog);
    }

    //今日的数据
    @Override
    public Map<String, Object> getByDayTime(Long userId) {
        Map<String, Object> mapP = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        UseJianhuyiLogDO useJianhuyiLogDO = new UseJianhuyiLogDO();
        //非阅读状态 户外
        UseJianhuyiLogDO outduration = useJianhuyiLogDao.getOutduration(userId);
        //阅读状态
        UseJianhuyiLogDO useJianhuyiLogDO1 = useJianhuyiLogDao.getByTime(userId);

        if (useJianhuyiLogDO1 != null) {
            if (outduration != null) {
                useJianhuyiLogDO1.setOutdoorsDuration(outduration.getOutdoorsDuration());
            } else {
                useJianhuyiLogDO1.setOutdoorsDuration(0.0);
            }
            params.put("userId", userId);
            if (useRemindsService.list(params).size() > 0) {
                useJianhuyiLogDO1.setRemind(useRemindsService.list(params).get(0).getRemindsNum());
            } else {
                useJianhuyiLogDO1.setRemind(0);
            }

            if (useTimeService.list(params).size() > 0) {
                useJianhuyiLogDO1.setUseJianhuyiDuration(useTimeService.list(params).get(0).getUserDurtion() * 180);
            } else {
                useJianhuyiLogDO1.setUseJianhuyiDuration(0);
            }
            mapP.put("data", useJianhuyiLogDO1);
        } else {
            params.put("userId", userId);
            if (useRemindsService.list(params).size() > 0) {
                useJianhuyiLogDO.setRemind(useRemindsService.list(params).get(0).getRemindsNum());
            } else {
                useJianhuyiLogDO.setRemind(0);
            }

            if (useTimeService.list(params).size() > 0) {
                useJianhuyiLogDO.setUseJianhuyiDuration(useTimeService.list(params).get(0).getUserDurtion() * 180);
            } else {
                useJianhuyiLogDO.setUseJianhuyiDuration(0);
            }

            useJianhuyiLogDO.setOutdoorsDuration(0.0);
            useJianhuyiLogDO.setReadDistance(0.0);
            useJianhuyiLogDO.setReadLight(0.0);
            useJianhuyiLogDO.setLookPhoneDuration(0.0);
            useJianhuyiLogDO.setLookTvComputerDuration(0.0);
            useJianhuyiLogDO.setSitTilt(0.0);
            useJianhuyiLogDO.setSportDuration(0.0);
            useJianhuyiLogDO.setAllreadDuration(0.0);

            useJianhuyiLogDO.setReadDuration(0.0);
            if (outduration != null) {
                useJianhuyiLogDO.setOutdoorsDuration(outduration.getOutdoorsDuration());
            } else {
                useJianhuyiLogDO.setOutdoorsDuration(0.0);
            }


            mapP.put("data", useJianhuyiLogDO);
        }


        mapP.put("msg", "操作成功");
        mapP.put("code", 0);
        return mapP;
    }

    //周记录
    @Override
    public Map<String, Object> queryUserWeekHistory(Date start, Date end, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapP = new HashMap<String, Object>();
        try {
            Object weekData = getlineData(start, end, userId);
            map.put("lineChart", weekData);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mapP.put("data", map);
        mapP.put("msg", "操作成功");
        mapP.put("code", 0);

        return mapP;
    }

    //月记录
    @Override
    public Map<String, Object> queryUserMonthHistory(Date start, Date end, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapP = new HashMap<String, Object>();
        //提醒
        Integer avgRemind = 0;
        Integer remindCount = 0;
        //户外
        Double avgOutduration = 0.0;
        Integer outdurationCount = 0;
        //平均日阅读时长
        Double avgReadDuration = 0.0;
        Integer readDurationCount = 0;

        Map<String, Object> mapData = null;
        try {
            mapData = getlineData(start, end, userId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("============mapData==========================" + mapData);

        mapP.put("data", mapData);
        mapP.put("msg", "操作成功");
        mapP.put("code", 0);

        return mapP;
    }


    private Map<String, Object> getlineData(Date start, Date end, Long userId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Double> avgReadDuration = new LinkedHashMap<String, Double>();
        Map<String, Double> outdoorsDuration = new LinkedHashMap<String, Double>();
        Map<String, Double> avgReadDistance = new LinkedHashMap<String, Double>();
        Map<String, Double> avgReadLight = new LinkedHashMap<String, Double>();
        Map<String, Double> avgLookPhoneDuration = new LinkedHashMap<String, Double>();
        Map<String, Double> avgLookTvComputerDuration = new LinkedHashMap<String, Double>();
        Map<String, Double> avgSitTilt = new LinkedHashMap<String, Double>();
        Map<String, Integer> avgUseJianhuyiDuration = new LinkedHashMap<String, Integer>();
        Map<String, Double> sportDuration = new LinkedHashMap<String, Double>();
        Map<String, Integer> remind = new LinkedHashMap<String, Integer>();
        Map<String, Double> map = new LinkedHashMap<String, Double>();

        //有数据的天数
        Integer readDurationCount = 0;
        Integer outdoorsDurationCount = 0;
        Integer readDistanceCount = 0;
        Integer readLightCount = 0;
        Integer lookPhoneDurationCount = 0;
        Integer lookTvComputerDurationCount = 0;
        Integer sitTiltCount = 0;
        Integer useJianhuyiDurationCount = 0;
        Integer remindCount = 0;

        //总平均
        Double AllreadDuration = 0.0;
        Double AlloutdoorsDuration = 0.0;
        Double AllreadDistance = 0.0;
        Double AllreadLight = 0.0;
        Double AlllookPhoneDuration = 0.0;
        Double AlllookTvComputerDuration = 0.0;
        Double AllsitTilt = 0.0;
        Integer AlluseJianhuyiDuration = 0;
        Integer Allremind = 0;

        while (start.compareTo(end) <= 0) {
            map.put(format(start), 0.0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            start = calendar.getTime();
        }
        List<String> list = new ArrayList<String>();

        Set<String> keySet = map.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        for (String string : list) {

            //阅读状态
            UseJianhuyiLogDO useJianhuyiLogDO = useJianhuyiLogDao.queryUserWeekRecord(string, userId);
            if (useJianhuyiLogDO != null) {
                if (useJianhuyiLogDO.getReadDuration() != null && useJianhuyiLogDO.getReadDuration() > 0) {
                    readDurationCount++;
                    avgReadDuration.put(string, useJianhuyiLogDO.getReadDuration());
                    AllreadDuration += useJianhuyiLogDO.getReadDuration();
                } else {
                    avgReadDuration.put(string, 0.0);
                }
                if (useJianhuyiLogDO.getReadDistance() != null && useJianhuyiLogDO.getReadDistance() > 0) {
                    readDistanceCount++;
                    avgReadDistance.put(string, useJianhuyiLogDO.getReadDistance());
                    AllreadDistance += useJianhuyiLogDO.getReadDistance();
                } else {
                    avgReadDistance.put(string, 0.0);
                }
                if (useJianhuyiLogDO.getReadLight() != null && useJianhuyiLogDO.getReadLight() > 0) {
                    readLightCount++;
                    avgReadLight.put(string, useJianhuyiLogDO.getReadLight());
                    AllreadLight += useJianhuyiLogDO.getReadLight();
                } else {
                    avgReadLight.put(string, 0.0);
                }
                if (useJianhuyiLogDO.getLookPhoneDuration() != null && useJianhuyiLogDO.getLookPhoneDuration() > 0) {
                    lookPhoneDurationCount++;
                    avgLookPhoneDuration.put(string, useJianhuyiLogDO.getLookPhoneDuration());
                    AlllookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
                } else {
                    avgLookPhoneDuration.put(string, 0.0);
                }
                if (useJianhuyiLogDO.getLookTvComputerDuration() != null && useJianhuyiLogDO.getLookTvComputerDuration() > 0) {
                    lookTvComputerDurationCount++;
                    avgLookTvComputerDuration.put(string, useJianhuyiLogDO.getLookTvComputerDuration());
                    AlllookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
                } else {
                    avgLookTvComputerDuration.put(string, 0.0);
                }
                if (useJianhuyiLogDO.getSitTilt() != null && useJianhuyiLogDO.getSitTilt() > 0) {
                    sitTiltCount++;
                    avgSitTilt.put(string, useJianhuyiLogDO.getSitTilt());
                    AllsitTilt += useJianhuyiLogDO.getSitTilt();
                } else {
                    avgSitTilt.put(string, 0.0);
                }
            } else {
                avgReadDuration.put(string, 0.0);
                avgReadDistance.put(string, 0.0);
                avgReadLight.put(string, 0.0);
                avgLookPhoneDuration.put(string, 0.0);
                avgLookTvComputerDuration.put(string, 0.0);
                avgSitTilt.put(string, 0.0);
            }

            //户外非阅读
            UseJianhuyiLogDO useJianhuyiLogDO1 = useJianhuyiLogDao.queryOutdoorsDuration(string, userId);
            if (useJianhuyiLogDO1 != null) {
                if (useJianhuyiLogDO1.getOutdoorsDuration() != null && useJianhuyiLogDO1.getOutdoorsDuration() > 0) {
                    outdoorsDurationCount++;
                    outdoorsDuration.put(string, useJianhuyiLogDO1.getOutdoorsDuration());
                    AlloutdoorsDuration += useJianhuyiLogDO1.getOutdoorsDuration();
                } else {
                    outdoorsDuration.put(string, 0.0);
                }
            } else {
                outdoorsDuration.put(string, 0.0);
            }

            //震动提醒
            UseRemindsDO useRemindsDO = useJianhuyiLogDao.queryRemind(string, userId);
            if (useRemindsDO != null && useRemindsDO.getRemindsNum() > 0) {
                remind.put(string, useRemindsDO.getRemindsNum());
                remindCount++;
                Allremind += useRemindsDO.getRemindsNum();
            } else {
                remind.put(string, 0);
            }

            //使用时长
            UseTimeDO useTimeDO = useJianhuyiLogDao.queryUseTime(string, userId);
            if (useTimeDO != null && useTimeDO.getUserDurtion() > 0) {
                avgUseJianhuyiDuration.put(string, useTimeDO.getUserDurtion() * 180);
                useJianhuyiDurationCount++;
                AlluseJianhuyiDuration += (useTimeDO.getUserDurtion() * 180);
            } else {
                avgUseJianhuyiDuration.put(string, 0);
            }
        }
        Map<String, Object> mapP = new HashMap<String, Object>();
        mapP.put("avgReadDuration", avgReadDuration.values());
        mapP.put("outdoorsDuration", outdoorsDuration.values());
        mapP.put("avgReadDistance", avgReadDistance.values());
        mapP.put("avgReadLight", avgReadLight.values());
        mapP.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
        mapP.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
        mapP.put("avgSitTilt", avgSitTilt.values());
        mapP.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
        mapP.put("remind", remind.values());

        Map<String, Object> map2 = new HashMap<>();
        if (readDurationCount > 0) {
            map2.put("AllreadDuration", AllreadDuration / readDurationCount);
        } else {
            map2.put("AllreadDuration", 0.0);
        }
        if (outdoorsDurationCount > 0) {
            map2.put("AlloutdoorsDuration", AlloutdoorsDuration / outdoorsDurationCount);
        } else {
            map2.put("AlloutdoorsDuration", 0.0);
        }
        if (readDistanceCount > 0) {
            map2.put("AllreadDistance", AllreadDistance / readDistanceCount);
        } else {
            map2.put("AllreadDistance", 0.0);
        }

        if (readLightCount > 0) {
            map2.put("AllreadLight", AllreadLight / readLightCount);
        } else {
            map2.put("AllreadLight", 0.0);
        }
        if (lookPhoneDurationCount > 0) {
            map2.put("AlllookPhoneDuration", AlllookPhoneDuration / lookPhoneDurationCount);
        } else {
            map2.put("AlllookPhoneDuration", 0.0);
        }

        if (lookTvComputerDurationCount > 0) {
            map2.put("AlllookTvComputerDuration", AlllookTvComputerDuration / lookTvComputerDurationCount);
        } else {
            map2.put("AlllookTvComputerDuration", 0.0);
        }
        if (sitTiltCount > 0) {
            map2.put("AllsitTilt", AllsitTilt / sitTiltCount);
        } else {
            map2.put("AllsitTilt", 0.0);
        }

        if (useJianhuyiDurationCount > 0) {
            map2.put("AlluseJianhuyiDuration", AlluseJianhuyiDuration / useJianhuyiDurationCount);
        } else {
            map2.put("AlluseJianhuyiDuration", 0.0);
        }
        if (remindCount > 0) {
            map2.put("Allremind", Allremind / remindCount);
        } else {
            map2.put("Allremind", 0.0);
        }


        Map<String, Object> result = new HashMap<>();
        //数据集合
        result.put("mapP", mapP);
        result.put("map2", map2);
        return result;

    }


    //季记录
    @Override
    public Map<String, Object> queryUserSeasonHistory(Date start, Date end, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapP = new HashMap<String, Object>();
        try {
            Map<String, Object> seasonData = seasonData(start, end, userId);
            map.put("lineChart", seasonData);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mapP.put("data", map);
        mapP.put("msg", "操作成功");
        mapP.put("code", 0);

        return mapP;
    }

    private Map<String, Object> seasonData(Date start, Date end, Long userId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Double> map = new LinkedHashMap<String, Double>();
        Map<Integer, Double> avgReadDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> outdoorsDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgReadDistance = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgReadLight = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgLookPhoneDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgLookTvComputerDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgSitTilt = new LinkedHashMap<Integer, Double>();
        Map<Integer, Integer> avgUseJianhuyiDuration = new LinkedHashMap<Integer, Integer>();
        Map<Integer, Double> sportDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Integer> remindCount = new LinkedHashMap<>();

        //有数据的天数
        Integer readDurationCount = 0;
        Integer outdoorsDurationCount = 0;
        Integer readDistanceCount = 0;
        Integer readLightCount = 0;
        Integer lookPhoneDurationCount = 0;
        Integer lookTvComputerDurationCount = 0;
        Integer sitTiltCount = 0;
        Integer useJianhuyiDurationCount = 0;
        Integer remindCounts = 0;

        //总平均
        Double AllreadDuration = 0.0;
        Double AlloutdoorsDuration = 0.0;
        Double AllreadDistance = 0.0;
        Double AllreadLight = 0.0;
        Double AlllookPhoneDuration = 0.0;
        Double AlllookTvComputerDuration = 0.0;
        Double AllsitTilt = 0.0;
        Integer AlluseJianhuyiDuration = 0;
        Integer Allremind = 0;

        while (start.compareTo(end) < 0) {
            map.put(format(start), 0.0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            start = calendar.getTime();
        }
        List<String> list = new ArrayList<String>();
        Set<String> set1 = map.keySet();
        Iterator<String> it1 = set1.iterator();
        while (it1.hasNext()) {
            //  System.out.println(it1.next());
            list.add(it1.next());
        }
        for (int i = 0; i < 12; i++) {
            avgReadDuration.put(i + 1, 0.0);
            outdoorsDuration.put(i + 1, 0.0);
            avgReadDistance.put(i + 1, 0.0);
            avgReadLight.put(i + 1, 0.0);
            avgLookPhoneDuration.put(i + 1, 0.0);
            avgLookTvComputerDuration.put(i + 1, 0.0);
            avgSitTilt.put(i + 1, 0.0);
            avgUseJianhuyiDuration.put(i + 1, 0);
            sportDuration.put(i + 1, 0.0);
            Double readDurationT = 0d;//阅读时长(分钟）
            Double outdoorsDurationT = 0d;//户外时长(小时）
            Double readDistanceT = 0d;//阅读距离(厘米)
            Double readLightT = 0d;//阅读光照(lux)
            Double lookPhoneDurationT = 0d;//看手机时长(分钟)
            Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
            Double sitTiltT = 0d;//坐姿倾斜度
            Integer useJianhuyiDurationT = 0;//使用监护仪时长(小时）
            Integer remindCountT = 0; //震动次数

            Integer readSize = 0;
            Integer other = 0;
            if (i == 11) {

                /*每月阅读状态统计*/
                List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
                if (between.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
                        readDurationT += useJianhuyiLogDO.getReadDuration();
                        readDistanceT += useJianhuyiLogDO.getReadDistance();
                        readLightT += useJianhuyiLogDO.getReadLight();
                        if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                            lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
                        }
                        if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                            lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
                        }
                        sitTiltT += useJianhuyiLogDO.getSitTilt();

                        avgReadDuration.put(i + 1, readDurationT / between.size());
                        avgReadDistance.put(i + 1, readDistanceT / between.size());
                        avgReadLight.put(i + 1, readLightT / between.size());
                        avgLookPhoneDuration.put(i + 1, lookPhoneDurationT / between.size());
                        avgLookTvComputerDuration.put(i + 1, lookTvComputerDurationT / between.size());
                        avgSitTilt.put(i + 1, sitTiltT / between.size());
                    }

                } else {
                    avgReadDuration.put(i + 1, 0.0);
                    avgReadDistance.put(i + 1, 0.0);
                    avgReadLight.put(i + 1, 0.0);
                    avgLookPhoneDuration.put(i + 1, 0.0);
                    avgLookTvComputerDuration.put(i + 1, 0.0);
                    avgSitTilt.put(i + 1, 0.0);
                }

                /*每月户外非阅读状态数据统计*/
                List<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogDao.getOutdurationYear(sdf.parse(list.get(i)), end, userId);
                if (useJianhuyiLogDOList.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOList) {
                        outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
                    }
                    outdoorsDuration.put(i + 1, outdoorsDurationT / useJianhuyiLogDOList.size());
                } else {
                    outdoorsDuration.put(i + 1, 0.0);
                }
                /*每月使用监护仪时长数据统计*/
                List<UseTimeDO> useTimeDOList = useJianhuyiLogDao.getUseJianhuyiTimeYear(sdf.parse(list.get(i)), end, userId);
                if (useTimeDOList.size() > 0) {
                    for (UseTimeDO useTimeDO : useTimeDOList) {
                        System.out.println("===============getUserDurtion==============" + useTimeDO.getUserDurtion());
                        useJianhuyiDurationT += (useTimeDO.getUserDurtion() * 180);
                    }
                    avgUseJianhuyiDuration.put(i + 1, useJianhuyiDurationT / useTimeDOList.size());
                } else {
                    avgUseJianhuyiDuration.put(i + 1, 0);
                }
                /*每月震动次数数据统计*/
                List<UseRemindsDO> useRemindsDOList = useJianhuyiLogDao.getRemindYear(sdf.parse(list.get(i)), end, userId);
                if (useRemindsDOList.size() > 0) {
                    for (UseRemindsDO useRemindsDO : useRemindsDOList) {
                        remindCountT += useRemindsDO.getRemindsNum();
                    }
                    remindCount.put(i + 1, remindCountT / useRemindsDOList.size());
                } else {
                    remindCount.put(i + 1, 0);
                }
            } else {
                /*每月阅读状态统计*/
                List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
                if (between.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
                        readDurationT += useJianhuyiLogDO.getReadDuration();
                        readDistanceT += useJianhuyiLogDO.getReadDistance();
                        readLightT += useJianhuyiLogDO.getReadLight();
                        if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                            lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
                        }
                        if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                            lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
                        }

                        sitTiltT += useJianhuyiLogDO.getSitTilt();

                        avgReadDuration.put(i + 1, readDurationT / between.size());
                        avgReadDistance.put(i + 1, readDistanceT / between.size());
                        avgReadLight.put(i + 1, readLightT / between.size());
                        avgLookPhoneDuration.put(i + 1, lookPhoneDurationT / between.size());
                        avgLookTvComputerDuration.put(i + 1, lookTvComputerDurationT / between.size());
                        avgSitTilt.put(i + 1, sitTiltT / between.size());
                    }

                } else {
                    avgReadDuration.put(i + 1, 0.0);
                    avgReadDistance.put(i + 1, 0.0);
                    avgReadLight.put(i + 1, 0.0);
                    avgLookPhoneDuration.put(i + 1, 0.0);
                    avgLookTvComputerDuration.put(i + 1, 0.0);
                    avgSitTilt.put(i + 1, 0.0);
                }

                /*每月户外非阅读状态数据统计*/
                List<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogDao.getOutdurationYear(sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
                if (useJianhuyiLogDOList.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOList) {
                        outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();

                        outdoorsDuration.put(i + 1, outdoorsDurationT / useJianhuyiLogDOList.size());
                    }
                } else {
                    outdoorsDuration.put(i + 1, 0.0);
                }
                /*每月使用监护仪时长数据统计*/
                List<UseTimeDO> useTimeDOList = useJianhuyiLogDao.getUseJianhuyiTimeYear(sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
                if (useTimeDOList.size() > 0) {
                    for (UseTimeDO useTimeDO : useTimeDOList) {
                        useJianhuyiDurationT += useTimeDO.getUserDurtion() * 180;

                        avgUseJianhuyiDuration.put(i + 1, useJianhuyiDurationT / useTimeDOList.size());
                    }
                } else {
                    avgUseJianhuyiDuration.put(i + 1, 0);
                }
                /*每月震动次数数据统计*/
                List<UseRemindsDO> useRemindsDOList = useJianhuyiLogDao.getRemindYear(sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
                if (useRemindsDOList.size() > 0) {
                    for (UseRemindsDO useRemindsDO : useRemindsDOList) {
                        remindCountT += useRemindsDO.getRemindsNum();

                        remindCount.put(i + 1, remindCountT / useRemindsDOList.size());
                    }
                } else {
                    remindCount.put(i + 1, 0);
                }
            }
        }

        Map<String, Object> mapP = new LinkedHashMap<String, Object>();
        /*每月平均使用*/
        mapP.put("avgReadDuration", avgReadDuration.values());
        mapP.put("outdoorsDuration", outdoorsDuration.values());
        mapP.put("avgReadDistance", avgReadDistance.values());
        mapP.put("avgReadLight", avgReadLight.values());
        mapP.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
        mapP.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
        mapP.put("avgSitTilt", avgSitTilt.values());
        mapP.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
        mapP.put("remind", remindCount.values());

        for (Double read : avgReadDuration.values()) {
            if (read > 0) {
                readDurationCount++;
            }
            AllreadDuration += read;
        }

        for (Double value : outdoorsDuration.values()) {
            if (value > 0) {
                outdoorsDurationCount++;
            }
            AlloutdoorsDuration += value;
        }
        for (Double value : avgReadDistance.values()) {
            if (value > 0) {
                readDistanceCount++;
            }
            AllreadDistance += value;
        }

        for (Double value : avgReadLight.values()) {
            if (value > 0) {
                readLightCount++;
            }
            AllreadLight += value;
        }
        for (Double value : avgLookPhoneDuration.values()) {
            if (value > 0) {
                lookPhoneDurationCount++;
            }
            AlllookPhoneDuration += value;
        }
        for (Double value : avgLookTvComputerDuration.values()) {
            if (value > 0) {
                lookTvComputerDurationCount++;
            }
            AlllookTvComputerDuration += value;
        }
        for (Double value : avgSitTilt.values()) {
            if (value > 0) {
                sitTiltCount++;
            }
            AllsitTilt += value;
        }

        for (Integer value : avgUseJianhuyiDuration.values()) {
            if (value > 0) {
                useJianhuyiDurationCount++;
            }
            AlluseJianhuyiDuration += value;
        }

        for (Integer value : remindCount.values()) {
            if (value > 0) {
                remindCounts++;
            }
            Allremind += value;
        }
        Map<String, Object> map2 = new LinkedHashMap<String, Object>();
        if (readDurationCount > 0) {
            map2.put("AllreadDuration", AllreadDuration / readDurationCount);
        } else {
            map2.put("AllreadDuration", 0.0);
        }
        if (outdoorsDurationCount > 0) {
            map2.put("AlloutdoorsDuration", AlloutdoorsDuration / outdoorsDurationCount);
        } else {
            map2.put("AlloutdoorsDuration", 0.0);
        }
        if (readDistanceCount > 0) {
            map2.put("AllreadDistance", AllreadDistance / readDistanceCount);
        } else {
            map2.put("AllreadDistance", 0.0);
        }

        if (readLightCount > 0) {
            map2.put("AllreadLight", AllreadLight / readLightCount);
        } else {
            map2.put("AllreadLight", 0.0);
        }
        if (lookPhoneDurationCount > 0) {
            map2.put("AlllookPhoneDuration", AlllookPhoneDuration / lookPhoneDurationCount);
        } else {
            map2.put("AlllookPhoneDuration", 0.0);
        }

        if (lookTvComputerDurationCount > 0) {
            map2.put("AlllookTvComputerDuration", AlllookTvComputerDuration / lookTvComputerDurationCount);
        } else {
            map2.put("AlllookTvComputerDuration", 0.0);
        }
        if (sitTiltCount > 0) {
            map2.put("AllsitTilt", AllsitTilt / sitTiltCount);
        } else {
            map2.put("AllsitTilt", 0.0);
        }

        if (useJianhuyiDurationCount > 0) {
            map2.put("AlluseJianhuyiDuration", AlluseJianhuyiDuration / useJianhuyiDurationCount);
        } else {
            map2.put("AlluseJianhuyiDuration", 0.0);
        }
        if (remindCounts > 0) {
            map2.put("Allremind", Allremind / remindCounts);
        } else {
            map2.put("Allremind", 0.0);
        }

        Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("mapP", mapP);
        result.put("map2", map2);
        return result;
    }

    //年记录
    @Override
    public Map<String, Object> queryUserYearHistory(Date start, Date end, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapP = new HashMap<String, Object>();
        try {
            Map<String, Object> yearData = yearData(start, end, userId);
            map.put("lineChart", yearData);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mapP.put("data", map);
        mapP.put("msg", "操作成功");
        mapP.put("code", 0);

        return mapP;
    }

    @Override
    public void saveList(List<UseJianhuyiLogDO> useJianhuyiLogDOList) {
        useJianhuyiLogDao.saveList(useJianhuyiLogDOList);
    }

    private Map<String, Object> yearData(Date start, Date end, Long userId) throws
            ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("0.00");

        Map<String, Double> map = new LinkedHashMap<String, Double>();
        Map<Integer, Double> avgReadDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> outdoorsDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgReadDistance = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgReadLight = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgLookPhoneDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgLookTvComputerDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgSitTilt = new LinkedHashMap<Integer, Double>();
        Map<Integer, Integer> avgUseJianhuyiDuration = new LinkedHashMap<Integer, Integer>();
        Map<Integer, Integer> remindCount = new LinkedHashMap<>();

        //有数据的天数
        Integer readDurationCount = 0;
        Integer outdoorsDurationCount = 0;
        Integer readDistanceCount = 0;
        Integer readLightCount = 0;
        Integer lookPhoneDurationCount = 0;
        Integer lookTvComputerDurationCount = 0;
        Integer sitTiltCount = 0;
        Integer useJianhuyiDurationCount = 0;
        Integer remindCounts = 0;

        //总平均
        Double AllreadDuration = 0.0;
        Double AlloutdoorsDuration = 0.0;
        Double AllreadDistance = 0.0;
        Double AllreadLight = 0.0;
        Double AlllookPhoneDuration = 0.0;
        Double AlllookTvComputerDuration = 0.0;
        Double AllsitTilt = 0.0;
        Integer AlluseJianhuyiDuration = 0;
        Integer Allremind = 0;
        while (start.compareTo(end) < 0) {
            map.put(format(start), 0.0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            calendar.add(Calendar.MONTH, 1);
            start = calendar.getTime();
        }
        List<String> list = new ArrayList<String>();
        Set<String> set1 = map.keySet();
        Iterator<String> it1 = set1.iterator();
        while (it1.hasNext()) {
            //  System.out.println(it1.next());
            list.add(it1.next());
        }
        for (int i = 0; i < 12; i++) {
            avgReadDuration.put(i + 1, 0.0);
            outdoorsDuration.put(i + 1, 0.0);
            avgReadDistance.put(i + 1, 0.0);
            avgReadLight.put(i + 1, 0.0);
            avgLookPhoneDuration.put(i + 1, 0.0);
            avgLookTvComputerDuration.put(i + 1, 0.0);
            avgSitTilt.put(i + 1, 0.0);
            avgUseJianhuyiDuration.put(i + 1, 0);

            Double readDurationT = 0d;//阅读时长(分钟）
            Double outdoorsDurationT = 0d;//户外时长(小时）
            Double readDistanceT = 0d;//阅读距离(厘米)
            Double readLightT = 0d;//阅读光照(lux)
            Double lookPhoneDurationT = 0d;//看手机时长(分钟)
            Integer lookPhoneCount = 0;//看手机次数
            Integer lookTvComputerCount = 0;//看电脑电视次数
            Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
            Double sitTiltT = 0d;//坐姿倾斜度
            Integer useJianhuyiDurationT = 0;//使用监护仪时长(小时）
            Integer remindCountT = 0; //震动次数
            if (i == 11) {

                /*每月阅读状态统计*/
                List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
                if (between.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
                        readDurationT += useJianhuyiLogDO.getReadDuration();
                        readDistanceT += useJianhuyiLogDO.getReadDistance();
                        readLightT += useJianhuyiLogDO.getReadLight();
                        if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                            lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
                        }
                        if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                            lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
                        }
                        sitTiltT += useJianhuyiLogDO.getSitTilt();

                        avgReadDuration.put(i + 1, readDurationT / between.size());
                        avgReadDistance.put(i + 1, readDistanceT / between.size());
                        avgReadLight.put(i + 1, readLightT / between.size());
                        avgLookPhoneDuration.put(i + 1, lookPhoneDurationT / between.size());
                        avgLookTvComputerDuration.put(i + 1, lookTvComputerDurationT / between.size());
                        avgSitTilt.put(i + 1, sitTiltT / between.size());
                    }

                } else {
                    avgReadDuration.put(i + 1, 0.0);
                    avgReadDistance.put(i + 1, 0.0);
                    avgReadLight.put(i + 1, 0.0);
                    avgLookPhoneDuration.put(i + 1, 0.0);
                    avgLookTvComputerDuration.put(i + 1, 0.0);
                    avgSitTilt.put(i + 1, 0.0);
                }

                /*每月户外非阅读状态数据统计*/
                List<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogDao.getOutdurationYear(sdf.parse(list.get(i)), end, userId);
                if (useJianhuyiLogDOList.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOList) {
                        outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();

                        outdoorsDuration.put(i + 1, outdoorsDurationT / useJianhuyiLogDOList.size());
                    }
                } else {
                    outdoorsDuration.put(i + 1, 0.0);
                }
                /*每月使用监护仪时长数据统计*/
                List<UseTimeDO> useTimeDOList = useJianhuyiLogDao.getUseJianhuyiTimeYear(sdf.parse(list.get(i)), end, userId);
                if (useTimeDOList.size() > 0) {
                    for (UseTimeDO useTimeDO : useTimeDOList) {
                        useJianhuyiDurationT += useTimeDO.getUserDurtion() * 180;

                        avgUseJianhuyiDuration.put(i + 1, useJianhuyiDurationT / useTimeDOList.size());
                    }
                } else {
                    avgUseJianhuyiDuration.put(i + 1, 0);
                }
                /*每月震动次数数据统计*/
                List<UseRemindsDO> useRemindsDOList = useJianhuyiLogDao.getRemindYear(sdf.parse(list.get(i)), end, userId);
                if (useRemindsDOList.size() > 0) {
                    for (UseRemindsDO useRemindsDO : useRemindsDOList) {
                        remindCountT += useRemindsDO.getRemindsNum();

                        remindCount.put(i + 1, remindCountT / useRemindsDOList.size());
                    }
                } else {
                    remindCount.put(i + 1, 0);
                }
            } else {
                /*每月阅读状态统计*/
                List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
                if (between.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
                        readDurationT += useJianhuyiLogDO.getReadDuration();
                        readDistanceT += useJianhuyiLogDO.getReadDistance();
                        readLightT += useJianhuyiLogDO.getReadLight();
                        if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                            lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
                        }
                        if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                            lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
                        }

                        sitTiltT += useJianhuyiLogDO.getSitTilt();

                        avgReadDuration.put(i + 1, readDurationT / between.size());
                        avgReadDistance.put(i + 1, readDistanceT / between.size());
                        avgReadLight.put(i + 1, readLightT / between.size());
                        avgLookPhoneDuration.put(i + 1, lookPhoneDurationT / between.size());
                        avgLookTvComputerDuration.put(i + 1, lookTvComputerDurationT / between.size());
                        avgSitTilt.put(i + 1, sitTiltT / between.size());
                    }

                } else {
                    avgReadDuration.put(i + 1, 0.0);
                    avgReadDistance.put(i + 1, 0.0);
                    avgReadLight.put(i + 1, 0.0);
                    avgLookPhoneDuration.put(i + 1, 0.0);
                    avgLookTvComputerDuration.put(i + 1, 0.0);
                    avgSitTilt.put(i + 1, 0.0);
                }

                /*每月户外非阅读状态数据统计*/
                List<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogDao.getOutdurationYear(sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
                if (useJianhuyiLogDOList.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOList) {
                        outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();

                        outdoorsDuration.put(i + 1, outdoorsDurationT / useJianhuyiLogDOList.size());
                    }
                } else {
                    outdoorsDuration.put(i + 1, 0.0);
                }
                /*每月使用监护仪时长数据统计*/
                List<UseTimeDO> useTimeDOList = useJianhuyiLogDao.getUseJianhuyiTimeYear(sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
                if (useTimeDOList.size() > 0) {
                    for (UseTimeDO useTimeDO : useTimeDOList) {
                        useJianhuyiDurationT += useTimeDO.getUserDurtion() * 180;

                        avgUseJianhuyiDuration.put(i + 1, useJianhuyiDurationT / useTimeDOList.size());
                    }
                } else {
                    avgUseJianhuyiDuration.put(i + 1, 0);
                }
                /*每月震动次数数据统计*/
                List<UseRemindsDO> useRemindsDOList = useJianhuyiLogDao.getRemindYear(sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
                if (useRemindsDOList.size() > 0) {
                    for (UseRemindsDO useRemindsDO : useRemindsDOList) {
                        remindCountT += useRemindsDO.getRemindsNum();

                        remindCount.put(i + 1, remindCountT / useRemindsDOList.size());
                    }
                } else {
                    remindCount.put(i + 1, 0);
                }
            }

        }
        /*}*/
        Map<String, Object> mapP = new LinkedHashMap<String, Object>();
        /*每月平均使用*/
        mapP.put("avgReadDuration", avgReadDuration.values());
        mapP.put("outdoorsDuration", outdoorsDuration.values());
        mapP.put("avgReadDistance", avgReadDistance.values());
        mapP.put("avgReadLight", avgReadLight.values());
        mapP.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
        mapP.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
        mapP.put("avgSitTilt", avgSitTilt.values());
        mapP.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
        mapP.put("remind", remindCount.values());

        for (Double read : avgReadDuration.values()) {
            if (read > 0) {
                readDurationCount++;
            }
            AllreadDuration += read;
        }

        for (Double value : outdoorsDuration.values()) {
            if (value > 0) {
                outdoorsDurationCount++;
            }
            AlloutdoorsDuration += value;
        }
        for (Double value : avgReadDistance.values()) {
            if (value > 0) {
                readDistanceCount++;
            }
            AllreadDistance += value;
        }

        for (Double value : avgReadLight.values()) {
            if (value > 0) {
                readLightCount++;
            }
            AllreadLight += value;
        }
        for (Double value : avgLookPhoneDuration.values()) {
            if (value > 0) {
                lookPhoneDurationCount++;
            }
            AlllookPhoneDuration += value;
        }
        for (Double value : avgLookTvComputerDuration.values()) {
            if (value > 0) {
                lookTvComputerDurationCount++;
            }
            AlllookTvComputerDuration += value;
        }
        for (Double value : avgSitTilt.values()) {
            if (value > 0) {
                sitTiltCount++;
            }
            AllsitTilt += value;
        }

        for (Integer value : avgUseJianhuyiDuration.values()) {
            if (value > 0) {
                useJianhuyiDurationCount++;
            }
            AlluseJianhuyiDuration += value;
        }

        for (Integer value : remindCount.values()) {
            if (value > 0) {
                remindCounts++;
            }
            Allremind += value;
        }
        Map<String, Object> map2 = new LinkedHashMap<String, Object>();
        if (readDurationCount > 0) {
            map2.put("AllreadDuration", AllreadDuration / readDurationCount);
        } else {
            map2.put("AllreadDuration", 0.0);
        }
        if (outdoorsDurationCount > 0) {
            map2.put("AlloutdoorsDuration", AlloutdoorsDuration / outdoorsDurationCount);
        } else {
            map2.put("AlloutdoorsDuration", 0.0);
        }
        if (readDistanceCount > 0) {
            map2.put("AllreadDistance", AllreadDistance / readDistanceCount);
        } else {
            map2.put("AllreadDistance", 0.0);
        }

        if (readLightCount > 0) {
            map2.put("AllreadLight", AllreadLight / readLightCount);
        } else {
            map2.put("AllreadLight", 0.0);
        }
        if (lookPhoneDurationCount > 0) {
            map2.put("AlllookPhoneDuration", AlllookPhoneDuration / lookPhoneDurationCount);
        } else {
            map2.put("AlllookPhoneDuration", 0.0);
        }

        if (lookTvComputerDurationCount > 0) {
            map2.put("AlllookTvComputerDuration", AlllookTvComputerDuration / lookTvComputerDurationCount);
        } else {
            map2.put("AlllookTvComputerDuration", 0.0);
        }
        if (sitTiltCount > 0) {
            map2.put("AllsitTilt", AllsitTilt / sitTiltCount);
        } else {
            map2.put("AllsitTilt", 0.0);
        }

        if (useJianhuyiDurationCount > 0) {
            map2.put("AlluseJianhuyiDuration", AlluseJianhuyiDuration / useJianhuyiDurationCount);
        } else {
            map2.put("AlluseJianhuyiDuration", 0.0);
        }
        if (remindCounts > 0) {
            map2.put("Allremind", Allremind / remindCounts);
        } else {
            map2.put("Allremind", 0.0);
        }

        Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("mapP", mapP);
        result.put("map2", map2);
        return result;
    }

}


