package com.jianhuyi.information.service.impl;

import com.jianhuyi.common.utils.DateUtils;
import com.jianhuyi.information.dao.UseJianhuyiLogDao;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class UseJianhuyiLogServiceImpl implements UseJianhuyiLogService {
    @Autowired
    private UseJianhuyiLogDao useJianhuyiLogDao;

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

    @Override
    public Map<String, Object> getByDayTime(Long userId) {
        Map<String, Object> mapP = new HashMap<String, Object>();
        UseJianhuyiLogDO useJianhuyiLogDO = new UseJianhuyiLogDO();
        Double avgreadDuration = 0.0;
        Double readDuration = 0.0;

        UseJianhuyiLogDO useJianhuyiLogDOs = useJianhuyiLogDao.getReadDuration(userId);
        UseJianhuyiLogDO outduration = useJianhuyiLogDao.getOutduration(userId);
        System.out.println("============useJianhuyiLogDOs=================" + useJianhuyiLogDOs);
        if (useJianhuyiLogDOs != null) {
            if (useJianhuyiLogDOs.getAllreadDuration() != null && useJianhuyiLogDOs.getNum() != null && !useJianhuyiLogDOs.getNum().equals(0)) {
                avgreadDuration = useJianhuyiLogDOs.getAllreadDuration() / useJianhuyiLogDOs.getNum();
            } else {
                avgreadDuration = 0.0;
            }
            if (useJianhuyiLogDOs.getAllreadDuration() != null) {
                readDuration = useJianhuyiLogDOs.getAllreadDuration();
            }
        }

        UseJianhuyiLogDO useJianhuyiLogDO1 = useJianhuyiLogDao.getByTime(userId);


        if (useJianhuyiLogDO1 != null) {
            useJianhuyiLogDO1.setAllreadDuration(readDuration);
            useJianhuyiLogDO1.setReadDuration(avgreadDuration);
            if (outduration != null) {
                useJianhuyiLogDO1.setOutdoorsDuration(outduration.getOutdoorsDuration());
            } else {
                useJianhuyiLogDO1.setOutdoorsDuration(0.0);
            }
            mapP.put("data", useJianhuyiLogDO1);
        } else {
            useJianhuyiLogDO.setOutdoorsDuration(0.0);
            useJianhuyiLogDO.setReadDistance(0.0);
            useJianhuyiLogDO.setReadLight(0.0);
            useJianhuyiLogDO.setLookPhoneDuration(0.0);
            useJianhuyiLogDO.setLookTvComputerDuration(0.0);
            useJianhuyiLogDO.setSitTilt(0.0);
            useJianhuyiLogDO.setUseJianhuyiDuration(0.0);
            useJianhuyiLogDO.setSportDuration(0.0);
            useJianhuyiLogDO.setAllreadDuration(0.0);
            useJianhuyiLogDO.setAllreadDuration(0.0);

            useJianhuyiLogDO.setReadDuration(avgreadDuration);
            useJianhuyiLogDO.setAllreadDuration(readDuration);
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
            Map<String, Collection<Double>> weekData = getlineData(start, end, userId);
            map.put("lineChart", weekData);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<UseJianhuyiLogDO> recordBetween = useJianhuyiLogDao.queryUserWeekRecordBetween(start, end, userId);
        Double readDuration = 0d;//阅读时长(分钟）
        Double outdoorsDuration = 0d;//户外时长(小时）
        Double readDistance = 0d;//阅读距离(厘米)
        Double readLight = 0d;//阅读光照(lux)
        Double lookPhoneDuration = 0d;//看手机时长(分钟)
        Double lookTvComputerDuration = 0d;//看电脑电视时长(分钟）
        Double sitTilt = 0d;//坐姿倾斜度
        Double useJianhuyiDuration = 0d;//使用监护仪时长(小时）
        Double sportDuration = 0d;//运动时长(小时)

        if (recordBetween.size() > 0) {
            for (UseJianhuyiLogDO useJianhuyiLogDO : recordBetween) {
                if (useJianhuyiLogDO != null) {
                    if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                        useJianhuyiDuration += useJianhuyiLogDO.getUseJianhuyiDuration();
                    }
                    if (useJianhuyiLogDO.getSportDuration() != null) {
                        sportDuration += useJianhuyiLogDO.getSportDuration();
                    }

                    if (useJianhuyiLogDO.getStatus() != null) {
                        if (useJianhuyiLogDO.getStatus() == 1) {
                            if (useJianhuyiLogDO.getReadDuration() != null) {
                                readDuration += useJianhuyiLogDO.getReadDuration();
                            }
                            if (useJianhuyiLogDO.getReadDistance() != null) {
                                readDistance += useJianhuyiLogDO.getReadDistance();
                            }
                            if (useJianhuyiLogDO.getReadLight() != null) {
                                readLight += useJianhuyiLogDO.getReadLight();
                            }
                            if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                                lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
                            }
                            if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                                lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
                            }
                            if (useJianhuyiLogDO.getSitTilt() != null) {
                                sitTilt += useJianhuyiLogDO.getSitTilt();
                            }

                        } else {
                            if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                                outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
                            }
                        }
                    } else {
                        if (useJianhuyiLogDO.getReadDuration() != null) {
                            readDuration += useJianhuyiLogDO.getReadDuration();
                        }
                        if (useJianhuyiLogDO.getReadDistance() != null) {
                            readDistance += useJianhuyiLogDO.getReadDistance();
                        }
                        if (useJianhuyiLogDO.getReadLight() != null) {
                            readLight += useJianhuyiLogDO.getReadLight();
                        }
                        if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                            lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
                        }
                        if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                            lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
                        }
                        if (useJianhuyiLogDO.getSitTilt() != null) {
                            sitTilt += useJianhuyiLogDO.getSitTilt();
                        }
                        if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                            outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
                        }
                    }
                }
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        map.put("totalAvgUseJianhuyiDuration", df.format(useJianhuyiDuration / 7));
        map.put("totalAvgReadDuration", df.format(readDuration / 7));
        map.put("totalAvgOutdoorsDuration", df.format(outdoorsDuration / 7));
        map.put("totalAvgReadDistance", df.format(readDistance / 7));
        map.put("totalAvgReadLight", df.format(readLight / 7));
        map.put("totalAvgLookPhoneDuration", df.format(lookPhoneDuration / 7));
        map.put("totalAvgLookTvComputerDuration", df.format(lookTvComputerDuration / 7));
        map.put("totalAvgSitTilt", df.format(sitTilt / 7));
        map.put("totalAvgSportDuration", df.format(sportDuration / 7));
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
        try {
            Map<String, Collection<Double>> monthData = getlineData(start, end, userId);
            map.put("lineChart", monthData);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<UseJianhuyiLogDO> recordBetween = useJianhuyiLogDao.queryUserWeekRecordBetween(start, end, userId);
        Double readDuration = 0d;//阅读时长(分钟）
        Double outdoorsDuration = 0d;//户外时长(小时）
        Double readDistance = 0d;//阅读距离(厘米)
        Double readLight = 0d;//阅读光照(lux)
        Double lookPhoneDuration = 0d;//看手机时长(分钟)
        Double lookTvComputerDuration = 0d;//看电脑电视时长(分钟）
        Double sitTilt = 0d;//坐姿倾斜度
        Double useJianhuyiDuration = 0d;//使用监护仪时长(小时）
        Double sportDuration = 0d;//运动时长(小时)
        if (recordBetween.size() > 0) {
            for (UseJianhuyiLogDO useJianhuyiLogDO : recordBetween) {
                if (useJianhuyiLogDO != null) {


                    if (useJianhuyiLogDO.getReadDuration() != null) {
                        readDuration += useJianhuyiLogDO.getReadDuration();
                    }
                    if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                        outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
                    }
                    if (useJianhuyiLogDO.getReadDistance() != null) {
                        readDistance += useJianhuyiLogDO.getReadDistance();
                    }
                    if (useJianhuyiLogDO.getReadLight() != null) {
                        readLight += useJianhuyiLogDO.getReadLight();
                    }
                    if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                        lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
                    }
                    if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                        lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
                    }
                    if (useJianhuyiLogDO.getSitTilt() != null) {
                        sitTilt += useJianhuyiLogDO.getSitTilt();
                    }
                    if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                        useJianhuyiDuration += useJianhuyiLogDO.getUseJianhuyiDuration();
                    }
                    if (useJianhuyiLogDO.getSportDuration() != null) {
                        sportDuration += useJianhuyiLogDO.getSportDuration();
                    }

                }


            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        map.put("totalAvgUseJianhuyiDuration", df.format(useJianhuyiDuration / 30));
        map.put("totalAvgReadDuration", df.format(readDuration / 30));
        map.put("totalAvgOutdoorsDuration", df.format(outdoorsDuration / 30));
        map.put("totalAvgReadDistance", df.format(readDistance / 30));
        map.put("totalAvgReadLight", df.format(readLight / 30));
        map.put("totalAvgLookPhoneDuration", df.format(lookPhoneDuration / 30));
        map.put("totalAvgLookTvComputerDuration", df.format(lookTvComputerDuration / 30));
        map.put("totalAvgSitTilt", df.format(sitTilt / 30));
        map.put("totalAvgSportDuration", df.format(sportDuration / 30));
        mapP.put("data", map);
        mapP.put("msg", "操作成功");
        mapP.put("code", 0);

        return mapP;
    }


    private Map<String, Collection<Double>> getlineData(Date start, Date end, Long userId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Double> avgReadDuration = new LinkedHashMap<String, Double>();
        Map<String, Double> outdoorsDuration = new LinkedHashMap<String, Double>();
        Map<String, Double> avgReadDistance = new LinkedHashMap<String, Double>();
        Map<String, Double> avgReadLight = new LinkedHashMap<String, Double>();
        Map<String, Double> avgLookPhoneDuration = new LinkedHashMap<String, Double>();
        Map<String, Double> avgLookTvComputerDuration = new LinkedHashMap<String, Double>();
        Map<String, Double> avgSitTilt = new LinkedHashMap<String, Double>();
        Map<String, Double> avgUseJianhuyiDuration = new LinkedHashMap<String, Double>();
        Map<String, Double> sportDuration = new LinkedHashMap<String, Double>();
        while (start.compareTo(end) <= 0) {
            avgReadDuration.put(DateUtils.format(start), 0.0);
            outdoorsDuration.put(DateUtils.format(start), 0.0);
            avgReadDistance.put(DateUtils.format(start), 0.0);
            avgReadLight.put(DateUtils.format(start), 0.0);
            avgLookPhoneDuration.put(DateUtils.format(start), 0.0);
            avgLookTvComputerDuration.put(DateUtils.format(start), 0.0);
            avgSitTilt.put(DateUtils.format(start), 0.0);
            avgUseJianhuyiDuration.put(DateUtils.format(start), 0.0);
            sportDuration.put(DateUtils.format(start), 0.0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            start = calendar.getTime();
        }
        List<String> list = new ArrayList<String>();

        Set<String> keySet = avgReadDuration.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        for (String string : list) {
            //	System.out.println(string);
            Double readDurationT = 0d;//阅读时长(分钟）
            Double outdoorsDurationT = 0d;//户外时长(小时）
            Double readDistanceT = 0d;//阅读距离(厘米)
            Double readLightT = 0d;//阅读光照(lux)
            Double lookPhoneDurationT = 0d;//看手机时长(分钟)
            Integer lookPhoneCount = 0;//看手机次数
            Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
            Integer lookTvComputerCuont = 0;//看电脑电视次数
            Double sitTiltT = 0d;//坐姿倾斜度
            Double useJianhuyiDurationT = 0d;//使用监护仪时长(小时）
            Double sportDurationT = 0d;//运动时长(小时)

            List<UseJianhuyiLogDO> weekRecord = useJianhuyiLogDao.queryUserWeekRecord(string, userId);

            Integer readSize = 0;
            Integer other = 0;
            if (weekRecord.size() > 0) {
                for (UseJianhuyiLogDO useJianhuyiLogDO : weekRecord) {
                    useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
                    sportDurationT += useJianhuyiLogDO.getSportDuration();
                    if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                        lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
                    }
                    if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                        lookTvComputerCuont += useJianhuyiLogDO.getLookTvComputerCount();
                    }
                    if (useJianhuyiLogDO.getStatus() != null) {
                        if (useJianhuyiLogDO.getStatus() == 1) {
                            readDurationT += useJianhuyiLogDO.getReadDuration();
                            readDistanceT += useJianhuyiLogDO.getReadDistance();
                            readLightT += useJianhuyiLogDO.getReadLight();
                            lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
                            lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
                            sitTiltT += useJianhuyiLogDO.getSitTilt();

                            readSize++;

                        } else if (useJianhuyiLogDO.getStatus() == 2) {
                            outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
                        } else {
                            other++;
                        }
                    } else {
                        readDurationT += useJianhuyiLogDO.getReadDuration();
                        readDistanceT += useJianhuyiLogDO.getReadDistance();
                        readLightT += useJianhuyiLogDO.getReadLight();
                        lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
                        lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
                        sitTiltT += useJianhuyiLogDO.getSitTilt();
                    }

                }

                DecimalFormat df = new DecimalFormat("0.00");
                if (readSize > 0) {
                    if (lookPhoneCount > 0) {
                        avgLookPhoneDuration.put(string, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
                    } else {
                        avgLookPhoneDuration.put(string, 0.0);
                    }

                    if (lookTvComputerCuont > 0) {
                        avgLookTvComputerDuration.put(string, Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCuont)));
                    } else {
                        avgLookTvComputerDuration.put(string, 0.0);
                    }
                    avgReadDuration.put(string, Double.parseDouble(df.format(readDurationT / readSize)));
                    avgSitTilt.put(string, Double.parseDouble(df.format(sitTiltT / readSize)));
                    avgReadLight.put(string, Double.parseDouble(df.format(readLightT / readSize)));
                    avgReadDistance.put(string, Double.parseDouble(df.format(readDistanceT / readSize)));
                } else {
                    if (lookPhoneCount > 0) {
                        avgLookPhoneDuration.put(string, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
                    } else {
                        avgLookPhoneDuration.put(string, 0.0);
                    }

                    if (lookTvComputerCuont > 0) {
                        avgLookTvComputerDuration.put(string, Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCuont)));
                    } else {
                        avgLookTvComputerDuration.put(string, 0.0);
                    }
                    avgReadDuration.put(string, Double.parseDouble(df.format(readDurationT / weekRecord.size())));
                    avgSitTilt.put(string, Double.parseDouble(df.format(sitTiltT / weekRecord.size())));
                    avgReadLight.put(string, Double.parseDouble(df.format(readLightT / weekRecord.size())));
                    avgReadDistance.put(string, Double.parseDouble(df.format(readDistanceT / weekRecord.size())));
                }
                avgUseJianhuyiDuration.put(string, Double.parseDouble(df.format(useJianhuyiDurationT / weekRecord.size())));
                outdoorsDuration.put(string, Double.parseDouble(df.format(outdoorsDurationT)));
                sportDuration.put(string, Double.parseDouble(df.format(sportDurationT)));
            }
        }
        Map<String, Collection<Double>> map = new HashMap<String, Collection<Double>>();
        map.put("avgReadDuration", avgReadDuration.values());
        map.put("outdoorsDuration", outdoorsDuration.values());
        map.put("avgReadDistance", avgReadDistance.values());
        map.put("avgReadLight", avgReadLight.values());
        map.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
        map.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
        map.put("avgSitTilt", avgSitTilt.values());
        map.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
        map.put("sportDuration", sportDuration.values());

        return map;

    }


    //季记录
    @Override
    public Map<String, Object> queryUserSeasonHistory(Date start, Date end, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapP = new HashMap<String, Object>();
        try {
            Map<String, Collection<Double>> seasonData = seasonData(start, end, userId);
            map.put("lineChart", seasonData);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<UseJianhuyiLogDO> betweenSum = useJianhuyiLogDao.queryUserRecordBetweenSum(start, end, userId);
        Double readDuration = 0d;//阅读时长(分钟）
        Double outdoorsDuration = 0d;//户外时长(小时）
        Double readDistance = 0d;//阅读距离(厘米)
        Double readLight = 0d;//阅读光照(lux)
        Double lookPhoneDuration = 0d;//看手机时长(分钟)
        Double lookTvComputerDuration = 0d;//看电脑电视时长(分钟）
        Double sitTilt = 0d;//坐姿倾斜度
        Double useJianhuyiDuration = 0d;//使用监护仪时长(小时）
        Double sportDuration = 0d;//运动时长(小时)
        if (betweenSum.size() > 0) {
            for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
                if (useJianhuyiLogDO != null) {
                    if (useJianhuyiLogDO.getReadDuration() != null) {
                        readDuration += useJianhuyiLogDO.getReadDuration();
                    }
                    if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                        outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
                    }
                    if (useJianhuyiLogDO.getReadDistance() != null) {
                        readDistance += useJianhuyiLogDO.getReadDistance();
                    }
                    if (useJianhuyiLogDO.getReadLight() != null) {
                        readLight += useJianhuyiLogDO.getReadLight();
                    }
                    if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                        lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
                    }
                    if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                        lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
                    }
                    if (useJianhuyiLogDO.getSitTilt() != null) {
                        sitTilt += useJianhuyiLogDO.getSitTilt();
                    }
                    if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                        useJianhuyiDuration += useJianhuyiLogDO.getUseJianhuyiDuration();
                    }
                    if (useJianhuyiLogDO.getSportDuration() != null) {
                        sportDuration += useJianhuyiLogDO.getSportDuration();
                    }
                }


            }
        }

        DecimalFormat df = new DecimalFormat("0.00");
        map.put("totalAvgUseJianhuyiDuration", df.format(useJianhuyiDuration / 12));
        map.put("totalAvgReadDuration", df.format(readDuration / 12));
        map.put("totalAvgOutdoorsDuration", df.format(outdoorsDuration / 12));
        map.put("totalAvgReadDistance", df.format(readDistance / 12));
        map.put("totalAvgReadLight", df.format(readLight / 12));
        map.put("totalAvgLookPhoneDuration", df.format(lookPhoneDuration / 12));
        map.put("totalAvgLookTvComputerDuration", df.format(lookTvComputerDuration / 12));
        map.put("totalAvgSitTilt", df.format(sitTilt / 12));
        map.put("totalAvgSportDuration", df.format(sportDuration / 12));
        map.put("dayAvgUseJianhuyiDuration", df.format(useJianhuyiDuration / 90 / 60));
        map.put("dayAvgReadDuration", df.format(readDuration / 90 / 60));
        mapP.put("data", map);
        mapP.put("msg", "操作成功");
        mapP.put("code", 0);

        return mapP;
    }

    private Map<String, Collection<Double>> seasonData(Date start, Date end, Long userId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        Map<Integer, Double> avgReadDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> outdoorsDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgReadDistance = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgReadLight = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgLookPhoneDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgLookTvComputerDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgSitTilt = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> avgUseJianhuyiDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> sportDuration = new LinkedHashMap<Integer, Double>();
        while (start.compareTo(end) < 0) {
            map.put(DateUtils.format(start), 0.0);
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
            avgUseJianhuyiDuration.put(i + 1, 0.0);
            sportDuration.put(i + 1, 0.0);
            Double readDurationT = 0d;//阅读时长(分钟）
            Double outdoorsDurationT = 0d;//户外时长(小时）
            Double readDistanceT = 0d;//阅读距离(厘米)
            Double readLightT = 0d;//阅读光照(lux)
            Double lookPhoneDurationT = 0d;//看手机时长(分钟)
            Integer lookPhoneCount = 0;//看手机次数
            Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
            Integer lookTvComputerCount = 0;//看电脑电视次数
            Double sitTiltT = 0d;//坐姿倾斜度
            Double useJianhuyiDurationT = 0d;//使用监护仪时长(小时）
            Double sportDurationT = 0d;//运动时长(小时)

            Integer readSize = 0;
            Integer other = 0;
            if (i == 11) {
	     		   /*System.out.println(list.get(i));
	     		   System.out.println(format.format(sdf.parse(list.get(i+1))));*/
                List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
                if (between.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
                        lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
                        lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();

                        if (useJianhuyiLogDO != null) {
                            if (useJianhuyiLogDO.getReadDuration() != null) {
                                readDurationT += useJianhuyiLogDO.getReadDuration();
                            }
                            if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                                outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
                            }
                            if (useJianhuyiLogDO.getReadDistance() != null) {
                                readDistanceT += useJianhuyiLogDO.getReadDistance();
                            }
                            if (useJianhuyiLogDO.getReadLight() != null) {
                                readLightT += useJianhuyiLogDO.getReadLight();
                            }
                            if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                                lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
                            }
                            if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                                lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
                            }
                            if (useJianhuyiLogDO.getSitTilt() != null) {
                                sitTiltT += useJianhuyiLogDO.getSitTilt();
                            }
                            if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                                useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
                            }
                            if (useJianhuyiLogDO.getSportDuration() != null) {
                                sportDurationT += useJianhuyiLogDO.getSportDuration();
                            }
                        }

                    }
                    DecimalFormat df = new DecimalFormat("0.00");

                    avgReadDuration.put(i + 1, Double.parseDouble(df.format(readDurationT / between.size())));
                    outdoorsDuration.put(i + 1, Double.parseDouble(df.format(outdoorsDurationT)));
                    avgReadDistance.put(i + 1, Double.parseDouble(df.format(readDistanceT / between.size())));
                    avgReadLight.put(i + 1, Double.parseDouble(df.format(readLightT / between.size())));

                    if (lookPhoneCount > 0) {
                        avgLookPhoneDuration.put(i + 1, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
                    } else {
                        avgLookPhoneDuration.put(i + 1, 0.0);
                    }

                    if (lookTvComputerCount > 0) {
                        avgLookTvComputerDuration.put(i + 1, Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCount)));
                    } else {
                        avgLookTvComputerDuration.put(i + 1, 0.0);
                    }
                    avgSitTilt.put(i + 1, Double.parseDouble(df.format(sitTiltT / between.size())));
                    avgUseJianhuyiDuration.put(i + 1, Double.parseDouble(df.format(useJianhuyiDurationT / between.size())));
                    sportDuration.put(i + 1, Double.parseDouble(df.format(sportDurationT)));

                }

            } else {
	     		   /*System.out.println(list.get(i));
	         	   System.out.println(list.get(i+1));
	         	   System.out.println("============"+i);*/
                List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
                if (between.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
                        if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                            lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
                        }
                        if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                            lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
                        }
                        if (useJianhuyiLogDO != null) {
                            if (useJianhuyiLogDO.getReadDuration() != null) {
                                readDurationT += useJianhuyiLogDO.getReadDuration();
                            }
                            if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                                outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
                            }
                            if (useJianhuyiLogDO.getReadDistance() != null) {
                                readDistanceT += useJianhuyiLogDO.getReadDistance();
                            }
                            if (useJianhuyiLogDO.getReadLight() != null) {
                                readLightT += useJianhuyiLogDO.getReadLight();
                            }
                            if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                                lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
                            }
                            if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                                lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
                            }
                            if (useJianhuyiLogDO.getSitTilt() != null) {
                                sitTiltT += useJianhuyiLogDO.getSitTilt();
                            }
                            if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                                useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
                            }
                            if (useJianhuyiLogDO.getSportDuration() != null) {
                                sportDurationT += useJianhuyiLogDO.getSportDuration();
                            }
                        }


                    }
                    DecimalFormat df = new DecimalFormat("0.00");

                    avgReadDuration.put(i + 1, Double.parseDouble(df.format(readDurationT / between.size())));
                    outdoorsDuration.put(i + 1, Double.parseDouble(df.format(outdoorsDurationT)));
                    avgReadDistance.put(i + 1, Double.parseDouble(df.format(readDistanceT / between.size())));
                    avgReadLight.put(i + 1, Double.parseDouble(df.format(readLightT / between.size())));
                    if (lookPhoneCount > 0) {
                        avgLookPhoneDuration.put(i + 1, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
                    } else {
                        avgLookPhoneDuration.put(i + 1, 0.0);
                    }

                    if (lookTvComputerCount > 0) {
                        avgLookTvComputerDuration.put(i + 1, Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCount)));
                    } else {
                        avgLookTvComputerDuration.put(i + 1, 0.0);
                    }
                    avgSitTilt.put(i + 1, Double.parseDouble(df.format(sitTiltT / between.size())));
                    avgUseJianhuyiDuration.put(i + 1, Double.parseDouble(df.format(useJianhuyiDurationT / between.size())));
                    sportDuration.put(i + 1, Double.parseDouble(df.format(sportDurationT)));
                }
            }
        }

        Map<String, Collection<Double>> mapP = new LinkedHashMap<String, Collection<Double>>();
        mapP.put("avgReadDuration", avgReadDuration.values());
        mapP.put("outdoorsDuration", outdoorsDuration.values());
        mapP.put("avgReadDistance", avgReadDistance.values());
        mapP.put("avgReadLight", avgReadLight.values());
        mapP.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
        mapP.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
        mapP.put("avgSitTilt", avgSitTilt.values());
        mapP.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
        mapP.put("sportDuration", sportDuration.values());

        return mapP;
    }


    //年记录
    @Override
    public Map<String, Object> queryUserYearHistory(Date start, Date end, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapP = new HashMap<String, Object>();
        try {
            Map<String, Collection<Double>> yearData = yearData(start, end, userId);
            map.put("lineChart", yearData);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<UseJianhuyiLogDO> betweenSum = useJianhuyiLogDao.queryUserRecordBetweenSum(start, end, userId);
        Double readDuration = 0d;//阅读时长(分钟）
        Double outdoorsDuration = 0d;//户外时长(小时）
        Double readDistance = 0d;//阅读距离(厘米)
        Double readLight = 0d;//阅读光照(lux)
        Double lookPhoneDuration = 0d;//看手机时长(分钟)
        Double lookTvComputerDuration = 0d;//看电脑电视时长(分钟）
        Double sitTilt = 0d;//坐姿倾斜度
        Double useJianhuyiDuration = 0d;//使用监护仪时长(小时）
        Double sportDuration = 0d;//运动时长(小时)
        if (betweenSum.size() > 0) {
            for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
                if (useJianhuyiLogDO != null) {
                    if (useJianhuyiLogDO.getReadDuration() != null) {
                        readDuration += useJianhuyiLogDO.getReadDuration();
                    }
                    if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                        outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
                    }
                    if (useJianhuyiLogDO.getReadDistance() != null) {
                        readDistance += useJianhuyiLogDO.getReadDistance();
                    }
                    if (useJianhuyiLogDO.getReadLight() != null) {
                        readLight += useJianhuyiLogDO.getReadLight();
                    }
                    if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                        lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
                    }
                    if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                        lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
                    }
                    if (useJianhuyiLogDO.getSitTilt() != null) {
                        sitTilt += useJianhuyiLogDO.getSitTilt();
                    }
                    if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                        useJianhuyiDuration += useJianhuyiLogDO.getUseJianhuyiDuration();
                    }
                    if (useJianhuyiLogDO.getSportDuration() != null) {
                        sportDuration += useJianhuyiLogDO.getSportDuration();
                    }

                }

            }
        }

        DecimalFormat df = new DecimalFormat("0.00");
        map.put("totalAvgUseJianhuyiDuration", df.format(useJianhuyiDuration / 365));
        map.put("totalAvgReadDuration", df.format(readDuration / 365));
        map.put("totalAvgOutdoorsDuration", df.format(outdoorsDuration / 365));
        map.put("totalAvgReadDistance", df.format(readDistance / 365));
        map.put("totalAvgReadLight", df.format(readLight / 365));
        map.put("totalAvgLookPhoneDuration", df.format(lookPhoneDuration / 365));
        map.put("totalAvgLookTvComputerDuration", df.format(lookTvComputerDuration / 365));
        map.put("totalAvgSitTilt", df.format(sitTilt / 365));
        map.put("totalAvgSportDuration", df.format(sportDuration / 365));
        map.put("dayAvgUseJianhuyiDuration", df.format(useJianhuyiDuration / 365 / 60));
        map.put("dayAvgReadDuration", df.format(readDuration / 365 / 60));
        mapP.put("data", map);
        mapP.put("msg", "操作成功");
        mapP.put("code", 0);

        return mapP;
    }

    private Map<String, Collection<Double>> yearData(Date start, Date end, Long userId) throws
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
        Map<Integer, Double> avgUseJianhuyiDuration = new LinkedHashMap<Integer, Double>();
        Map<Integer, Double> sportDuration = new LinkedHashMap<Integer, Double>();
        while (start.compareTo(end) < 0) {
            map.put(DateUtils.format(start), 0.0);
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
            avgUseJianhuyiDuration.put(i + 1, 0.0);
            sportDuration.put(i + 1, 0.0);
            Double readDurationT = 0d;//阅读时长(分钟）
            Double outdoorsDurationT = 0d;//户外时长(小时）
            Double readDistanceT = 0d;//阅读距离(厘米)
            Double readLightT = 0d;//阅读光照(lux)
            Double lookPhoneDurationT = 0d;//看手机时长(分钟)
            Integer lookPhoneCount = 0;//看手机次数
            Integer lookTvComputerCount = 0;//看电脑电视次数
            Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
            Double sitTiltT = 0d;//坐姿倾斜度
            Double useJianhuyiDurationT = 0d;//使用监护仪时长(小时）
            Double sportDurationT = 0d;//运动时长(小时)
            if (i == 11) {
			     		   /*System.out.println(list.get(i));
			     		   System.out.println(format.format(sdf.parse(list.get(i+1))));*/
                List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
                if (between.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
                        if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                            lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
                        }
                        if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                            lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
                        }

                        if (useJianhuyiLogDO != null) {
                            if (useJianhuyiLogDO.getReadDuration() != null) {
                                readDurationT += useJianhuyiLogDO.getReadDuration();
                            }
                            if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                                outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
                            }
                            if (useJianhuyiLogDO.getReadDistance() != null) {
                                readDistanceT += useJianhuyiLogDO.getReadDistance();
                            }
                            if (useJianhuyiLogDO.getReadLight() != null) {
                                readLightT += useJianhuyiLogDO.getReadLight();
                            }
                            if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                                lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
                            }
                            if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                                lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
                            }
                            if (useJianhuyiLogDO.getSitTilt() != null) {
                                sitTiltT += useJianhuyiLogDO.getSitTilt();
                            }
                            if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                                useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
                            }
                            if (useJianhuyiLogDO.getSportDuration() != null) {
                                sportDurationT += useJianhuyiLogDO.getSportDuration();
                            }
                        }

                    }
                    avgReadDuration.put(i + 1, readDurationT / between.size());
                    outdoorsDuration.put(i + 1, outdoorsDurationT);
                    avgReadDistance.put(i + 1, readDistanceT / between.size());
                    avgReadLight.put(i + 1, readLightT / between.size());
                    if (lookPhoneCount > 0) {
                        avgLookPhoneDuration.put(i + 1, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
                    } else {
                        avgLookPhoneDuration.put(i + 1, 0.0);
                    }

                    if (lookTvComputerCount > 0) {
                        avgLookTvComputerDuration.put(i + 1, Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCount)));
                    } else {
                        avgLookTvComputerDuration.put(i + 1, 0.0);
                    }
                    avgSitTilt.put(i + 1, sitTiltT / between.size());
                    avgUseJianhuyiDuration.put(i + 1, useJianhuyiDurationT / between.size());
                    sportDuration.put(i + 1, sportDurationT);
                }

            } else {
			     		   /*System.out.println(list.get(i));
			         	   System.out.println(list.get(i+1));
			         	   System.out.println("============"+i);*/
                List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), sdf.parse(list.get(i + 1)), userId);
                if (between.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : between) {

                        if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                            lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
                        }
                        if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                            lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
                        }

                        if (useJianhuyiLogDO != null) {
                            if (useJianhuyiLogDO.getReadDuration() != null) {
                                readDurationT += useJianhuyiLogDO.getReadDuration();
                            }
                            if (useJianhuyiLogDO.getOutdoorsDuration() != null) {
                                outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
                            }
                            if (useJianhuyiLogDO.getReadDistance() != null) {
                                readDistanceT += useJianhuyiLogDO.getReadDistance();
                            }
                            if (useJianhuyiLogDO.getReadLight() != null) {
                                readLightT += useJianhuyiLogDO.getReadLight();
                            }
                            if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                                lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
                            }
                            if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                                lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
                            }
                            if (useJianhuyiLogDO.getSitTilt() != null) {
                                sitTiltT += useJianhuyiLogDO.getSitTilt();
                            }
                            if (useJianhuyiLogDO.getUseJianhuyiDuration() != null) {
                                useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
                            }
                            if (useJianhuyiLogDO.getSportDuration() != null) {
                                sportDurationT += useJianhuyiLogDO.getSportDuration();
                            }
                        }


                    }


                    avgReadDuration.put(i + 1, Double.parseDouble(df.format(readDurationT / between.size())));
                    outdoorsDuration.put(i + 1, Double.parseDouble(df.format(outdoorsDurationT)));
                    avgReadDistance.put(i + 1, Double.parseDouble(df.format(readDistanceT / between.size())));
                    avgReadLight.put(i + 1, Double.parseDouble(df.format(readLightT / between.size())));
                    if (lookPhoneCount > 0) {
                        avgLookPhoneDuration.put(i + 1, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
                    } else {
                        avgLookPhoneDuration.put(i + 1, 0.0);
                    }

                    if (lookTvComputerCount > 0) {
                        avgLookTvComputerDuration.put(i + 1, Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCount)));
                    } else {
                        avgLookTvComputerDuration.put(i + 1, 0.0);
                    }
                    avgSitTilt.put(i + 1, Double.parseDouble(df.format(sitTiltT / between.size())));
                    avgUseJianhuyiDuration.put(i + 1, Double.parseDouble(df.format(useJianhuyiDurationT / between.size())));
                    sportDuration.put(i + 1, Double.parseDouble(df.format(sportDurationT)));
                }


            }

        }

        Map<String, Collection<Double>> mapP = new LinkedHashMap<String, Collection<Double>>();
        mapP.put("avgReadDuration", avgReadDuration.values());
        mapP.put("outdoorsDuration", outdoorsDuration.values());
        mapP.put("avgReadDistance", avgReadDistance.values());
        mapP.put("avgReadLight", avgReadLight.values());
        mapP.put("avgLookPhoneDuration", avgLookPhoneDuration.values());
        mapP.put("avgLookTvComputerDuration", avgLookTvComputerDuration.values());
        mapP.put("avgSitTilt", avgSitTilt.values());
        mapP.put("avgUseJianhuyiDuration", avgUseJianhuyiDuration.values());
        mapP.put("sportDuration", sportDuration.values());


        return mapP;

    }

}


