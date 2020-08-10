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

import static com.jianhuyi.common.utils.DateUtils.format;


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
    public Map<String, Collection<Double>> getlineData(Date start, Date end, Long userId) {
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
            Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
            Integer lookPhoneCount = 0;//看手机次数
            Integer lookTvComputerCount = 0;//看电脑电视次数
            Double sitTiltT = 0d;//坐姿倾斜度
            Double useJianhuyiDurationT = 0d;//使用监护仪时长(小时）
            Double sportDurationT = 0d;//运动时长(小时)

            List<UseJianhuyiLogDO> weekRecord = useJianhuyiLogDao.queryUserWeekRecord(string, userId);

            Integer readSize = 0;
            Integer other = 0;
            if (weekRecord.size() > 0) {
                for (UseJianhuyiLogDO useJianhuyiLogDO : weekRecord) {
                    if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                        lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
                    }
                    if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                        lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
                    }
                    useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
                    sportDurationT += useJianhuyiLogDO.getSportDuration();

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
                if (lookPhoneCount > 0) {
                    avgLookPhoneDuration.put(string, Double.parseDouble(df.format(lookPhoneDurationT / lookPhoneCount)));
                } else {
                    avgLookPhoneDuration.put(string, 0.0);
                }

                if (lookTvComputerCount > 0) {
                    avgLookTvComputerDuration.put(string, Double.parseDouble(df.format(lookTvComputerDurationT / lookTvComputerCount)));
                } else {
                    avgLookTvComputerDuration.put(string, 0.0);
                }

                if (readSize > 0) {
                    avgReadDuration.put(string, Double.parseDouble(df.format(readDurationT / readSize)));
                    avgSitTilt.put(string, Double.parseDouble(df.format(sitTiltT / readSize)));
                    avgReadLight.put(string, Double.parseDouble(df.format(readLightT / readSize)));
                    avgReadDistance.put(string, Double.parseDouble(df.format(readDistanceT / readSize)));
                } else {
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

    @Override
    public Map<String, Collection<Double>> seasonData(Date start, Date end, Long userId) throws Exception {
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
            Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
            Integer lookPhoneCount = 0;//看手机次数
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
                        if (useJianhuyiLogDO != null) {

                            if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                                lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
                            }
                            if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                                lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
                            }

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
                    avgReadDuration.put(i + 1, Double.parseDouble(df.format(readDurationT / between.size())));
                    outdoorsDuration.put(i + 1, Double.parseDouble(df.format(outdoorsDurationT)));
                    avgReadDistance.put(i + 1, Double.parseDouble(df.format(readDistanceT / between.size())));
                    avgReadLight.put(i + 1, Double.parseDouble(df.format(readLightT / between.size())));
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
                        if (useJianhuyiLogDO != null) {
                            if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                                lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
                            }
                            if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                                lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
                            }

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

                    avgReadDuration.put(i + 1, Double.parseDouble(df.format(readDurationT / between.size())));
                    outdoorsDuration.put(i + 1, Double.parseDouble(df.format(outdoorsDurationT)));
                    avgReadDistance.put(i + 1, Double.parseDouble(df.format(readDistanceT / between.size())));
                    avgReadLight.put(i + 1, Double.parseDouble(df.format(readLightT / between.size())));
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

    @Override
    public Map<String, Collection<Double>> yearData(Date start, Date end, Long userId) throws
            ParseException {
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
            Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
            Integer lookPhoneCount = 0;//看手机次数
            Integer lookTvComputerCount = 0;//看电脑电视次数
            Double sitTiltT = 0d;//坐姿倾斜度
            Double useJianhuyiDurationT = 0d;//使用监护仪时长(小时）
            Double sportDurationT = 0d;//运动时长(小时)
            if (i == 11) {
			     		   /*System.out.println(list.get(i));
			     		   System.out.println(format.format(sdf.parse(list.get(i+1))));*/
                List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
                if (between.size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
                        if (useJianhuyiLogDO != null) {
                            if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                                lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
                            }
                            if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                                lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
                            }

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
                    avgLookPhoneDuration.put(i + 1, lookPhoneDurationT / between.size());
                    avgLookTvComputerDuration.put(i + 1, lookTvComputerDurationT / between.size());
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
                        if (useJianhuyiLogDO != null) {
                            if (useJianhuyiLogDO.getLookPhoneCount() != null) {
                                lookPhoneCount += useJianhuyiLogDO.getLookPhoneCount();
                            }
                            if (useJianhuyiLogDO.getLookTvComputerCount() != null) {
                                lookTvComputerCount += useJianhuyiLogDO.getLookTvComputerCount();
                            }

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

                    avgReadDuration.put(i + 1, Double.parseDouble(df.format(readDurationT / between.size())));
                    outdoorsDuration.put(i + 1, Double.parseDouble(df.format(outdoorsDurationT)));
                    avgReadDistance.put(i + 1, Double.parseDouble(df.format(readDistanceT / between.size())));
                    avgReadLight.put(i + 1, Double.parseDouble(df.format(readLightT / between.size())));
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

    @Override
    public List<UseJianhuyiLogDO> selectData(Date start, Date end, Map<String, Object> params) {
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        List<UseJianhuyiLogDO> useJianhuyiLogDOList = new ArrayList<>();
        //查询列表数据
        Long uploadId = null;
        Long userId = null;
        if (params.get("uploadId") != null && !params.get("uploadId").equals("")) {
            uploadId = Long.parseLong(params.get("uploadId").toString());
        }
        if (params.get("userId") != null && !params.get("userId").equals("")) {
            userId = Long.parseLong(params.get("userId").toString());
        }


        if (start != null && end != null) {
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
                useJianhuyiLogDOList.addAll(useJianhuyiLogDao.queryData(string, uploadId, userId));
            }
        } else {
            useJianhuyiLogDOList.addAll(useJianhuyiLogDao.queryData(null, uploadId, userId));
        }

        return useJianhuyiLogDOList;
    }

    @Override
    public List<Map<String, Object>> exeList(Map<String, Object> params) throws ParseException {
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        List<Map<String, Object>> exelist = new ArrayList<Map<String, Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date start = null;
        Date end = null;
        Long uploadId = null;
        Long userId = null;
        if (params.get("uploadId") != null && !params.get("uploadId").equals("")) {
            uploadId = Long.parseLong(params.get("uploadId").toString());
        }
        if (params.get("userId") != null && !params.get("userId").equals("")) {
            userId = Long.parseLong(params.get("userId").toString());
        }

        if (params.get("startTime") != null && !params.get("startTime").equals("")) {
            start = sdf.parse((String) params.get("startTime"));
        }
        if (params.get("endTime") != null && !params.get("endTime").equals("")) {
            end = sdf.parse((String) params.get("endTime"));
        }

        if (start != null && end != null) {
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
                System.out.println("=============useJianhuyiLogDao.exeList(string, uploadId, userId)============================" + useJianhuyiLogDao.exeList(string, uploadId, userId));
                exelist.addAll(useJianhuyiLogDao.exeList(string, uploadId, userId));
            }
        } else {
            exelist.addAll(useJianhuyiLogDao.exeList(null, uploadId, userId));
        }
        return exelist;
    }

    @Override
    public List<UseJianhuyiLogDO> listDetail(Map<String, Object> params) {
        return useJianhuyiLogDao.listDetail(params);
    }

    @Override
    public int counts(Map<String, Object> params) {
        return useJianhuyiLogDao.counts(params);
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
    public int remove(Integer id) {
        return useJianhuyiLogDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return useJianhuyiLogDao.batchRemove(ids);
    }


}
