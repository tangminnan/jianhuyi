package com.jianhuyi.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianhuyi.common.utils.DateUtils;
import com.jianhuyi.information.dao.UseJianhuyiLogDao;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;




@Service
public class UseJianhuyiLogServiceImpl implements UseJianhuyiLogService {
	@Autowired
	private UseJianhuyiLogDao useJianhuyiLogDao;
	
	@Override
	public UseJianhuyiLogDO get(Integer id){
		return useJianhuyiLogDao.get(id);
	}
	
	@Override
	public List<UseJianhuyiLogDO> list(Map<String, Object> map){
		return useJianhuyiLogDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return useJianhuyiLogDao.count(map);
	}
	
	@Override
	public int save(UseJianhuyiLogDO useJianhuyiLog){
		return useJianhuyiLogDao.save(useJianhuyiLog);
	}
	
	@Override
	public int update(UseJianhuyiLogDO useJianhuyiLog){
		return useJianhuyiLogDao.update(useJianhuyiLog);
	}

	@Override
	public Map<String, Object> getByDayTime(Long userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapP = new HashMap<String,Object>();
		List<UseJianhuyiLogDO> byTime = useJianhuyiLogDao.getByTime(userId);
		//阅读时长(分钟）
		Double readDuration = 0d;
		//户外时长(小时）
		Double outdoorsDuration = 0d;
		//阅读距离(厘米)
		Double readDistance = 0d;
		//阅读光照(lux)
		Double readLight = 0d;
		//看手机时长(分钟)
		Double lookPhoneDuration = 0d;
		//看电脑电视时长(分钟）
		Double lookTvComputerDuration = 0d;
		//坐姿倾斜度
		Double sitTilt = 0d;
		//使用监护仪时长(小时）
		Double useJianhuyiDuration = 0d;
		//运动时长(小时)
		Double sportDuration = 0d;
		if(byTime.size()>0){
			for (UseJianhuyiLogDO useJianhuyiLogDO : byTime) {
				if(useJianhuyiLogDO.getReadDuration() != null){
					readDuration += useJianhuyiLogDO.getReadDuration();
				}
				if(useJianhuyiLogDO.getOutdoorsDuration() != null){
					outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
				}
				if(useJianhuyiLogDO.getReadDistance() != null){
					readDistance += useJianhuyiLogDO.getReadDistance();
				}
				if(useJianhuyiLogDO.getReadLight() != null){
					readLight += useJianhuyiLogDO.getReadLight();
				}
				if(useJianhuyiLogDO.getLookPhoneDuration() != null){
					lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
				}
				if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
					lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
				}
				if(useJianhuyiLogDO.getSitTilt() != null){
					sitTilt += useJianhuyiLogDO.getSitTilt();
				}
				if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
					useJianhuyiDuration += useJianhuyiLogDO.getUseJianhuyiDuration();
				}
				if(useJianhuyiLogDO.getSportDuration() != null){
					sportDuration += useJianhuyiLogDO.getSportDuration();
				}
				
			}
		}
		DecimalFormat df = new DecimalFormat("0.00");
		map.put("useJianhuyiDuration", useJianhuyiDuration);
		map.put("readDuration", readDuration);
		map.put("num", byTime.size());
		map.put("avgReadDuration", df.format(readDuration/byTime.size()));
		map.put("outdoorsDuration", outdoorsDuration);
		map.put("avgReadDistance", df.format(readDistance/byTime.size()));
		map.put("avgReadLight", df.format(readLight/byTime.size()));
		map.put("avgLookPhoneDuration", df.format(lookPhoneDuration/byTime.size()));
		map.put("avgLookTvComputerDuration", df.format(lookTvComputerDuration/byTime.size()));
		map.put("avgSitTilt", df.format(sitTilt/byTime.size()));
		map.put("sportDuration", sportDuration);
		mapP.put("data", map);
		mapP.put("msg", "操作成功");
		mapP.put("code", 0);
		return mapP;
	}
	//周记录
	@Override
	public Map<String, Object> queryUserWeekHistory(Date start,Date end, Long userId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Map<String, Collection<Double>> weekData = weekData(start,end,userId);
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
		if(recordBetween.size()>0){
			for (UseJianhuyiLogDO useJianhuyiLogDO : recordBetween) {
				if(useJianhuyiLogDO.getReadDuration() != null){
					readDuration += useJianhuyiLogDO.getReadDuration();
				}
				if(useJianhuyiLogDO.getOutdoorsDuration() != null){
					outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
				}
				if(useJianhuyiLogDO.getReadDistance() != null){
					readDistance += useJianhuyiLogDO.getReadDistance();
				}
				if(useJianhuyiLogDO.getReadLight() != null){
					readLight += useJianhuyiLogDO.getReadLight();
				}
				if(useJianhuyiLogDO.getLookPhoneDuration() != null){
					lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
				}
				if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
					lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
				}
				if(useJianhuyiLogDO.getSitTilt() != null){
					sitTilt += useJianhuyiLogDO.getSitTilt();
				}
				if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
					useJianhuyiDuration += useJianhuyiLogDO.getUseJianhuyiDuration();
				}
				if(useJianhuyiLogDO.getSportDuration() != null){
					sportDuration += useJianhuyiLogDO.getSportDuration();
				}
				
			}
		}
		DecimalFormat df = new DecimalFormat("0.00");
		map.put("totalAvgUseJianhuyiDuration", df.format(useJianhuyiDuration/7));
		map.put("totalAvgReadDuration", df.format(readDuration/7));
		map.put("totalAvgOutdoorsDuration", df.format(outdoorsDuration/7));
		map.put("totalAvgReadDistance", df.format(readDistance/7));
		map.put("totalAvgReadLight", df.format(readLight/7));
		map.put("totalAvgLookPhoneDuration", df.format(lookPhoneDuration/7));
		map.put("totalAvgLookTvComputerDuration", df.format(lookTvComputerDuration/7));
		map.put("totalAvgSitTilt", df.format(sitTilt/7));
		map.put("totalAvgSportDuration", df.format(sportDuration/7));
		map.put("msg", "");
		map.put("code", 0);
		
		return map;
	}
		
	
	private Map<String, Collection<Double>> weekData(Date start,Date end, Long userId) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Double> avgReadDuration = new LinkedHashMap<String,Double>();
		Map<String,Double> outdoorsDuration = new LinkedHashMap<String,Double>();
		Map<String,Double> avgReadDistance = new LinkedHashMap<String,Double>();
		Map<String,Double> avgReadLight = new LinkedHashMap<String,Double>();
		Map<String,Double> avgLookPhoneDuration = new LinkedHashMap<String,Double>();
		Map<String,Double> avgLookTvComputerDuration = new LinkedHashMap<String,Double>();
		Map<String,Double> avgSitTilt = new LinkedHashMap<String,Double>();
		Map<String,Double> avgUseJianhuyiDuration = new LinkedHashMap<String,Double>();
		Map<String,Double> sportDuration = new LinkedHashMap<String,Double>();
		while(start.compareTo(end)<=0){
			avgReadDuration.put(DateUtils.format(start),0.0);
			outdoorsDuration.put(DateUtils.format(start),0.0);
			avgReadDistance.put(DateUtils.format(start),0.0);
			avgReadLight.put(DateUtils.format(start),0.0);
			avgLookPhoneDuration.put(DateUtils.format(start),0.0);
			avgLookTvComputerDuration.put(DateUtils.format(start),0.0);
			avgSitTilt.put(DateUtils.format(start),0.0);
			avgUseJianhuyiDuration.put(DateUtils.format(start),0.0);
			sportDuration.put(DateUtils.format(start),0.0);
			Calendar calendar  = Calendar.getInstance();
			calendar.setTime(start);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			start=calendar.getTime();
		}
		List<String> list = new ArrayList<String>();
		
		Set<String> keySet = avgReadDuration.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
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
    		Double sitTiltT = 0d;//坐姿倾斜度
    		Double useJianhuyiDurationT = 0d;//使用监护仪时长(小时）
    		Double sportDurationT = 0d;//运动时长(小时)
            List<UseJianhuyiLogDO> weekRecord = useJianhuyiLogDao.queryUserWeekRecord(sdf.parse(string), userId);
            if(weekRecord.size()>0){
            	for (UseJianhuyiLogDO useJianhuyiLogDO : weekRecord) {
            		if(useJianhuyiLogDO.getReadDuration() != null){
            			readDurationT += useJianhuyiLogDO.getReadDuration();
    				}
    				if(useJianhuyiLogDO.getOutdoorsDuration() != null){
    					outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
    				}
    				if(useJianhuyiLogDO.getReadDistance() != null){
    					readDistanceT += useJianhuyiLogDO.getReadDistance();
    				}
    				if(useJianhuyiLogDO.getReadLight() != null){
    					readLightT += useJianhuyiLogDO.getReadLight();
    				}
    				if(useJianhuyiLogDO.getLookPhoneDuration() != null){
    					lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
    				}
    				if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
    					lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
    				}
    				if(useJianhuyiLogDO.getSitTilt() != null){
    					sitTiltT += useJianhuyiLogDO.getSitTilt();
    				}
    				if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
    					useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
    				}
    				if(useJianhuyiLogDO.getSportDuration() != null){
    					sportDurationT += useJianhuyiLogDO.getSportDuration();
    				}
    			}
            	avgReadDuration.put(string, readDurationT/weekRecord.size());
            	outdoorsDuration.put(string, outdoorsDurationT);
            	avgReadDistance.put(string, readDistanceT/weekRecord.size());
            	avgReadLight.put(string, readLightT/weekRecord.size());
            	avgLookPhoneDuration.put(string, lookPhoneDurationT/weekRecord.size());
            	avgLookTvComputerDuration.put(string, lookTvComputerDurationT/weekRecord.size());
            	avgSitTilt.put(string, sitTiltT/weekRecord.size());
            	avgUseJianhuyiDuration.put(string, useJianhuyiDurationT/weekRecord.size());
            	sportDuration.put(string, sportDurationT);
            }
		}
		Map<String,Collection<Double>> map = new HashMap<String,Collection<Double>>();
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
	
	//月记录
		@Override
		public Map<String, Object> queryUserMonthHistory(Date start,Date end, Long userId){
			Map<String,Object> map = new HashMap<String,Object>();
			try {
				Map<String, Collection<Double>> monthData = monthData(start,end,userId);
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
			if(recordBetween.size()>0){
				for (UseJianhuyiLogDO useJianhuyiLogDO : recordBetween) {
					if(useJianhuyiLogDO.getReadDuration() != null){
						readDuration += useJianhuyiLogDO.getReadDuration();
					}
					if(useJianhuyiLogDO.getOutdoorsDuration() != null){
						outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
					}
					if(useJianhuyiLogDO.getReadDistance() != null){
						readDistance += useJianhuyiLogDO.getReadDistance();
					}
					if(useJianhuyiLogDO.getReadLight() != null){
						readLight += useJianhuyiLogDO.getReadLight();
					}
					if(useJianhuyiLogDO.getLookPhoneDuration() != null){
						lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
					}
					if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
						lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
					}
					if(useJianhuyiLogDO.getSitTilt() != null){
						sitTilt += useJianhuyiLogDO.getSitTilt();
					}
					if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
						useJianhuyiDuration += useJianhuyiLogDO.getUseJianhuyiDuration();
					}
					if(useJianhuyiLogDO.getSportDuration() != null){
						sportDuration += useJianhuyiLogDO.getSportDuration();
					}
					
				}
			}
			DecimalFormat df = new DecimalFormat("0.00");
			map.put("totalAvgUseJianhuyiDuration", df.format(useJianhuyiDuration/30));
			map.put("totalAvgReadDuration", df.format(readDuration/30));
			map.put("totalAvgOutdoorsDuration", df.format(outdoorsDuration/30));
			map.put("totalAvgReadDistance", df.format(readDistance/30));
			map.put("totalAvgReadLight", df.format(readLight/30));
			map.put("totalAvgLookPhoneDuration", df.format(lookPhoneDuration/30));
			map.put("totalAvgLookTvComputerDuration", df.format(lookTvComputerDuration/30));
			map.put("totalAvgSitTilt", df.format(sitTilt/30));
			map.put("totalAvgSportDuration", df.format(sportDuration/30));
			map.put("msg", "");
			map.put("code", 0);
			
			return map;
		}
			
		
		private Map<String, Collection<Double>> monthData(Date start,Date end, Long userId) throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Map<String,Double> avgReadDuration = new LinkedHashMap<String,Double>();
			Map<String,Double> outdoorsDuration = new LinkedHashMap<String,Double>();
			Map<String,Double> avgReadDistance = new LinkedHashMap<String,Double>();
			Map<String,Double> avgReadLight = new LinkedHashMap<String,Double>();
			Map<String,Double> avgLookPhoneDuration = new LinkedHashMap<String,Double>();
			Map<String,Double> avgLookTvComputerDuration = new LinkedHashMap<String,Double>();
			Map<String,Double> avgSitTilt = new LinkedHashMap<String,Double>();
			Map<String,Double> avgUseJianhuyiDuration = new LinkedHashMap<String,Double>();
			Map<String,Double> sportDuration = new LinkedHashMap<String,Double>();
			while(start.compareTo(end)<=0){
				avgReadDuration.put(DateUtils.format(start),0.0);
				outdoorsDuration.put(DateUtils.format(start),0.0);
				avgReadDistance.put(DateUtils.format(start),0.0);
				avgReadLight.put(DateUtils.format(start),0.0);
				avgLookPhoneDuration.put(DateUtils.format(start),0.0);
				avgLookTvComputerDuration.put(DateUtils.format(start),0.0);
				avgSitTilt.put(DateUtils.format(start),0.0);
				avgUseJianhuyiDuration.put(DateUtils.format(start),0.0);
				sportDuration.put(DateUtils.format(start),0.0);
				Calendar calendar  = Calendar.getInstance();
				calendar.setTime(start);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				start=calendar.getTime();
			}
			List<String> list = new ArrayList<String>();
			
			Set<String> keySet = avgReadDuration.keySet();
			Iterator<String> iterator = keySet.iterator();
			while(iterator.hasNext()){
				list.add(iterator.next());
	        }
			for (String string : list) {
				System.out.println(string);
				Double readDurationT = 0d;//阅读时长(分钟）
	    		Double outdoorsDurationT = 0d;//户外时长(小时）
	    		Double readDistanceT = 0d;//阅读距离(厘米)
	    		Double readLightT = 0d;//阅读光照(lux)
	    		Double lookPhoneDurationT = 0d;//看手机时长(分钟)
	    		Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
	    		Double sitTiltT = 0d;//坐姿倾斜度
	    		Double useJianhuyiDurationT = 0d;//使用监护仪时长(小时）
	    		Double sportDurationT = 0d;//运动时长(小时)
	            List<UseJianhuyiLogDO> weekRecord = useJianhuyiLogDao.queryUserWeekRecord(sdf.parse(string), userId);
	            if(weekRecord.size()>0){
	            	for (UseJianhuyiLogDO useJianhuyiLogDO : weekRecord) {
	            		if(useJianhuyiLogDO.getReadDuration() != null){
	            			readDurationT += useJianhuyiLogDO.getReadDuration();
	    				}
	    				if(useJianhuyiLogDO.getOutdoorsDuration() != null){
	    					outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
	    				}
	    				if(useJianhuyiLogDO.getReadDistance() != null){
	    					readDistanceT += useJianhuyiLogDO.getReadDistance();
	    				}
	    				if(useJianhuyiLogDO.getReadLight() != null){
	    					readLightT += useJianhuyiLogDO.getReadLight();
	    				}
	    				if(useJianhuyiLogDO.getLookPhoneDuration() != null){
	    					lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
	    				}
	    				if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
	    					lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
	    				}
	    				if(useJianhuyiLogDO.getSitTilt() != null){
	    					sitTiltT += useJianhuyiLogDO.getSitTilt();
	    				}
	    				if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
	    					useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
	    				}
	    				if(useJianhuyiLogDO.getSportDuration() != null){
	    					sportDurationT += useJianhuyiLogDO.getSportDuration();
	    				}
	    			}
	            	avgReadDuration.put(string, readDurationT/weekRecord.size());
	            	outdoorsDuration.put(string, outdoorsDurationT);
	            	avgReadDistance.put(string, readDistanceT/weekRecord.size());
	            	avgReadLight.put(string, readLightT/weekRecord.size());
	            	avgLookPhoneDuration.put(string, lookPhoneDurationT/weekRecord.size());
	            	avgLookTvComputerDuration.put(string, lookTvComputerDurationT/weekRecord.size());
	            	avgSitTilt.put(string, sitTiltT/weekRecord.size());
	            	avgUseJianhuyiDuration.put(string, useJianhuyiDurationT/weekRecord.size());
	            	sportDuration.put(string, sportDurationT);
	            }
			}
			Map<String,Collection<Double>> map = new HashMap<String,Collection<Double>>();
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
			Map<String,Object> map = new HashMap<String,Object>();
			try {
				Map<String, Collection<Double>> seasonData = seasonData(start,end, userId);
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
			if(betweenSum.size()>0){
				for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
					if(useJianhuyiLogDO.getReadDuration() != null){
						readDuration += useJianhuyiLogDO.getReadDuration();
					}
					if(useJianhuyiLogDO.getOutdoorsDuration() != null){
						outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
					}
					if(useJianhuyiLogDO.getReadDistance() != null){
						readDistance += useJianhuyiLogDO.getReadDistance();
					}
					if(useJianhuyiLogDO.getReadLight() != null){
						readLight += useJianhuyiLogDO.getReadLight();
					}
					if(useJianhuyiLogDO.getLookPhoneDuration() != null){
						lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
					}
					if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
						lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
					}
					if(useJianhuyiLogDO.getSitTilt() != null){
						sitTilt += useJianhuyiLogDO.getSitTilt();
					}
					if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
						useJianhuyiDuration += useJianhuyiLogDO.getUseJianhuyiDuration();
					}
					if(useJianhuyiLogDO.getSportDuration() != null){
						sportDuration += useJianhuyiLogDO.getSportDuration();
					}
				}
			}
			
			DecimalFormat df = new DecimalFormat("0.00");
			map.put("totalAvgUseJianhuyiDuration", df.format(useJianhuyiDuration/12));
			map.put("totalAvgReadDuration", df.format(readDuration/12));
			map.put("totalAvgOutdoorsDuration", df.format(outdoorsDuration/12));
			map.put("totalAvgReadDistance", df.format(readDistance/12));
			map.put("totalAvgReadLight", df.format(readLight/12));
			map.put("totalAvgLookPhoneDuration", df.format(lookPhoneDuration/12));
			map.put("totalAvgLookTvComputerDuration", df.format(lookTvComputerDuration/12));
			map.put("totalAvgSitTilt", df.format(sitTilt/12));
			map.put("totalAvgSportDuration", df.format(sportDuration/12));
			map.put("dayAvgUseJianhuyiDuration", df.format(useJianhuyiDuration/90));
			map.put("dayAvgReadDuration", df.format(readDuration/90));
			map.put("msg", "");
			map.put("code", 0);
			return map;
		}
		
		private Map<String, Collection<Double>> seasonData(Date start,Date end, Long userId) throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Map<String,Double> map = new LinkedHashMap<String,Double>();
			Map<Integer,Double> avgReadDuration = new LinkedHashMap<Integer,Double>();
			Map<Integer,Double> outdoorsDuration = new LinkedHashMap<Integer,Double>();
			Map<Integer,Double> avgReadDistance = new LinkedHashMap<Integer,Double>();
			Map<Integer,Double> avgReadLight = new LinkedHashMap<Integer,Double>();
			Map<Integer,Double> avgLookPhoneDuration = new LinkedHashMap<Integer,Double>();
			Map<Integer,Double> avgLookTvComputerDuration = new LinkedHashMap<Integer,Double>();
			Map<Integer,Double> avgSitTilt = new LinkedHashMap<Integer,Double>();
			Map<Integer,Double> avgUseJianhuyiDuration = new LinkedHashMap<Integer,Double>();
			Map<Integer,Double> sportDuration = new LinkedHashMap<Integer,Double>();
			while(start.compareTo(end)<0){
				map.put(DateUtils.format(start),0.0);
				Calendar calendar  = Calendar.getInstance();
				calendar.setTime(start);
				calendar.add(Calendar.DAY_OF_MONTH, 7);
				start=calendar.getTime();
			}
			List<String> list = new ArrayList<String>();
			Set<String> set1 = map.keySet();
	        Iterator<String> it1 =  set1.iterator();
	        while(it1.hasNext()){
	         //  System.out.println(it1.next());
	            list.add(it1.next());
	        }
	        for(int i = 0;i<12;i++){
	        	avgReadDuration.put(i+1, 0.0);
            	outdoorsDuration.put(i+1, 0.0);
            	avgReadDistance.put(i+1, 0.0);
            	avgReadLight.put(i+1, 0.0);
            	avgLookPhoneDuration.put(i+1, 0.0);
            	avgLookTvComputerDuration.put(i+1, 0.0);
            	avgSitTilt.put(i+1, 0.0);
            	avgUseJianhuyiDuration.put(i+1, 0.0);
            	sportDuration.put(i+1, 0.0);
	        	Double readDurationT = 0d;//阅读时长(分钟）
	    		Double outdoorsDurationT = 0d;//户外时长(小时）
	    		Double readDistanceT = 0d;//阅读距离(厘米)
	    		Double readLightT = 0d;//阅读光照(lux)
	    		Double lookPhoneDurationT = 0d;//看手机时长(分钟)
	    		Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
	    		Double sitTiltT = 0d;//坐姿倾斜度
	    		Double useJianhuyiDurationT = 0d;//使用监护仪时长(小时）
	    		Double sportDurationT = 0d;//运动时长(小时)
	     	   if(i==11){
	     		   /*System.out.println(list.get(i));
	     		   System.out.println(format.format(sdf.parse(list.get(i+1))));*/ 
		         List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
		         if(between.size()>0){
		        	 for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
			        	 if(useJianhuyiLogDO.getReadDuration() != null){
		            			readDurationT += useJianhuyiLogDO.getReadDuration();
		    				}
		    				if(useJianhuyiLogDO.getOutdoorsDuration() != null){
		    					outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
		    				}
		    				if(useJianhuyiLogDO.getReadDistance() != null){
		    					readDistanceT += useJianhuyiLogDO.getReadDistance();
		    				}
		    				if(useJianhuyiLogDO.getReadLight() != null){
		    					readLightT += useJianhuyiLogDO.getReadLight();
		    				}
		    				if(useJianhuyiLogDO.getLookPhoneDuration() != null){
		    					lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
		    				}
		    				if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
		    					lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
		    				}
		    				if(useJianhuyiLogDO.getSitTilt() != null){
		    					sitTiltT += useJianhuyiLogDO.getSitTilt();
		    				}
		    				if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
		    					useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
		    				}
		    				if(useJianhuyiLogDO.getSportDuration() != null){
		    					sportDurationT += useJianhuyiLogDO.getSportDuration();
		    				}
					}
			         	avgReadDuration.put(i+1, readDurationT/between.size());
		            	outdoorsDuration.put(i+1, outdoorsDurationT);
		            	avgReadDistance.put(i+1, readDistanceT/between.size());
		            	avgReadLight.put(i+1, readLightT/between.size());
		            	avgLookPhoneDuration.put(i+1, lookPhoneDurationT/between.size());
		            	avgLookTvComputerDuration.put(i+1, lookTvComputerDurationT/between.size());
		            	avgSitTilt.put(i+1, sitTiltT/between.size());
		            	avgUseJianhuyiDuration.put(i+1, useJianhuyiDurationT/between.size());
		            	sportDuration.put(i+1, sportDurationT);
		         }
		         
	     	   }else{
	     		   /*System.out.println(list.get(i));
	         	   System.out.println(list.get(i+1));
	         	   System.out.println("============"+i);*/
	         	  List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), sdf.parse(list.get(i+1)), userId);
	         	  if(between.size()>0){
	         		 for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
		         		 if(useJianhuyiLogDO.getReadDuration() != null){
		            			readDurationT += useJianhuyiLogDO.getReadDuration();
		    				}
		    				if(useJianhuyiLogDO.getOutdoorsDuration() != null){
		    					outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
		    				}
		    				if(useJianhuyiLogDO.getReadDistance() != null){
		    					readDistanceT += useJianhuyiLogDO.getReadDistance();
		    				}
		    				if(useJianhuyiLogDO.getReadLight() != null){
		    					readLightT += useJianhuyiLogDO.getReadLight();
		    				}
		    				if(useJianhuyiLogDO.getLookPhoneDuration() != null){
		    					lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
		    				}
		    				if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
		    					lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
		    				}
		    				if(useJianhuyiLogDO.getSitTilt() != null){
		    					sitTiltT += useJianhuyiLogDO.getSitTilt();
		    				}
		    				if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
		    					useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
		    				}
		    				if(useJianhuyiLogDO.getSportDuration() != null){
		    					sportDurationT += useJianhuyiLogDO.getSportDuration();
		    				}
						}
	         		avgReadDuration.put(i+1, readDurationT/between.size());
	            	outdoorsDuration.put(i+1, outdoorsDurationT);
	            	avgReadDistance.put(i+1, readDistanceT/between.size());
	            	avgReadLight.put(i+1, readLightT/between.size());
	            	avgLookPhoneDuration.put(i+1, lookPhoneDurationT/between.size());
	            	avgLookTvComputerDuration.put(i+1, lookTvComputerDurationT/between.size());
	            	avgSitTilt.put(i+1, sitTiltT/between.size());
	            	avgUseJianhuyiDuration.put(i+1, useJianhuyiDurationT/between.size());
	            	sportDuration.put(i+1, sportDurationT);
	         	  }
	         	  
	         	  
	     	   }
	     	   
	        }
	        
	        Map<String,Collection<Double>> mapP = new LinkedHashMap<String,Collection<Double>>();
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
					Map<String,Object> map = new HashMap<String,Object>();
					try {
						Map<String, Collection<Double>> yearData = yearData(start,end, userId);
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
					if(betweenSum.size()>0){
						for (UseJianhuyiLogDO useJianhuyiLogDO : betweenSum) {
							if(useJianhuyiLogDO.getReadDuration() != null){
								readDuration += useJianhuyiLogDO.getReadDuration();
							}
							if(useJianhuyiLogDO.getOutdoorsDuration() != null){
								outdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
							}
							if(useJianhuyiLogDO.getReadDistance() != null){
								readDistance += useJianhuyiLogDO.getReadDistance();
							}
							if(useJianhuyiLogDO.getReadLight() != null){
								readLight += useJianhuyiLogDO.getReadLight();
							}
							if(useJianhuyiLogDO.getLookPhoneDuration() != null){
								lookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
							}
							if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
								lookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
							}
							if(useJianhuyiLogDO.getSitTilt() != null){
								sitTilt += useJianhuyiLogDO.getSitTilt();
							}
							if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
								useJianhuyiDuration += useJianhuyiLogDO.getUseJianhuyiDuration();
							}
							if(useJianhuyiLogDO.getSportDuration() != null){
								sportDuration += useJianhuyiLogDO.getSportDuration();
							}
						}
					}
					
					DecimalFormat df = new DecimalFormat("0.00");
					map.put("totalAvgUseJianhuyiDuration", df.format(useJianhuyiDuration/12));
					map.put("totalAvgReadDuration", df.format(readDuration/12));
					map.put("totalAvgOutdoorsDuration", df.format(outdoorsDuration/12));
					map.put("totalAvgReadDistance", df.format(readDistance/12));
					map.put("totalAvgReadLight", df.format(readLight/12));
					map.put("totalAvgLookPhoneDuration", df.format(lookPhoneDuration/12));
					map.put("totalAvgLookTvComputerDuration", df.format(lookTvComputerDuration/12));
					map.put("totalAvgSitTilt", df.format(sitTilt/12));
					map.put("totalAvgSportDuration", df.format(sportDuration/12));
					map.put("dayAvgUseJianhuyiDuration", df.format(useJianhuyiDuration/365));
					map.put("dayAvgReadDuration", df.format(readDuration/365));
					map.put("msg", "");
					map.put("code", 0);
					return map;
				}
				
				private Map<String, Collection<Double>> yearData(Date start,Date end, Long userId) throws ParseException{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Map<String,Double> map = new LinkedHashMap<String,Double>();
					Map<Integer,Double> avgReadDuration = new LinkedHashMap<Integer,Double>();
					Map<Integer,Double> outdoorsDuration = new LinkedHashMap<Integer,Double>();
					Map<Integer,Double> avgReadDistance = new LinkedHashMap<Integer,Double>();
					Map<Integer,Double> avgReadLight = new LinkedHashMap<Integer,Double>();
					Map<Integer,Double> avgLookPhoneDuration = new LinkedHashMap<Integer,Double>();
					Map<Integer,Double> avgLookTvComputerDuration = new LinkedHashMap<Integer,Double>();
					Map<Integer,Double> avgSitTilt = new LinkedHashMap<Integer,Double>();
					Map<Integer,Double> avgUseJianhuyiDuration = new LinkedHashMap<Integer,Double>();
					Map<Integer,Double> sportDuration = new LinkedHashMap<Integer,Double>();
					while(start.compareTo(end)<0){
						map.put(DateUtils.format(start),0.0);
						Calendar calendar  = Calendar.getInstance();
						calendar.setTime(start);
						calendar.add(Calendar.MONTH, 1);
						start=calendar.getTime();
					}
					List<String> list = new ArrayList<String>();
					Set<String> set1 = map.keySet();
			        Iterator<String> it1 =  set1.iterator();
			        while(it1.hasNext()){
			         //  System.out.println(it1.next());
			            list.add(it1.next());
			        }
			        for(int i = 0;i<12;i++){
			        	avgReadDuration.put(i+1, 0.0);
		            	outdoorsDuration.put(i+1, 0.0);
		            	avgReadDistance.put(i+1, 0.0);
		            	avgReadLight.put(i+1, 0.0);
		            	avgLookPhoneDuration.put(i+1, 0.0);
		            	avgLookTvComputerDuration.put(i+1, 0.0);
		            	avgSitTilt.put(i+1, 0.0);
		            	avgUseJianhuyiDuration.put(i+1, 0.0);
		            	sportDuration.put(i+1, 0.0);
			        	Double readDurationT = 0d;//阅读时长(分钟）
			    		Double outdoorsDurationT = 0d;//户外时长(小时）
			    		Double readDistanceT = 0d;//阅读距离(厘米)
			    		Double readLightT = 0d;//阅读光照(lux)
			    		Double lookPhoneDurationT = 0d;//看手机时长(分钟)
			    		Double lookTvComputerDurationT = 0d;//看电脑电视时长(分钟）
			    		Double sitTiltT = 0d;//坐姿倾斜度
			    		Double useJianhuyiDurationT = 0d;//使用监护仪时长(小时）
			    		Double sportDurationT = 0d;//运动时长(小时)
			     	   if(i==11){
			     		   /*System.out.println(list.get(i));
			     		   System.out.println(format.format(sdf.parse(list.get(i+1))));*/ 
				         List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), end, userId);
				         if(between.size()>0){
				        	 for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
					        	 if(useJianhuyiLogDO.getReadDuration() != null){
				            			readDurationT += useJianhuyiLogDO.getReadDuration();
				    				}
				    				if(useJianhuyiLogDO.getOutdoorsDuration() != null){
				    					outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
				    				}
				    				if(useJianhuyiLogDO.getReadDistance() != null){
				    					readDistanceT += useJianhuyiLogDO.getReadDistance();
				    				}
				    				if(useJianhuyiLogDO.getReadLight() != null){
				    					readLightT += useJianhuyiLogDO.getReadLight();
				    				}
				    				if(useJianhuyiLogDO.getLookPhoneDuration() != null){
				    					lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
				    				}
				    				if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
				    					lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
				    				}
				    				if(useJianhuyiLogDO.getSitTilt() != null){
				    					sitTiltT += useJianhuyiLogDO.getSitTilt();
				    				}
				    				if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
				    					useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
				    				}
				    				if(useJianhuyiLogDO.getSportDuration() != null){
				    					sportDurationT += useJianhuyiLogDO.getSportDuration();
				    				}
							}
					         	avgReadDuration.put(i+1, readDurationT/between.size());
				            	outdoorsDuration.put(i+1, outdoorsDurationT);
				            	avgReadDistance.put(i+1, readDistanceT/between.size());
				            	avgReadLight.put(i+1, readLightT/between.size());
				            	avgLookPhoneDuration.put(i+1, lookPhoneDurationT/between.size());
				            	avgLookTvComputerDuration.put(i+1, lookTvComputerDurationT/between.size());
				            	avgSitTilt.put(i+1, sitTiltT/between.size());
				            	avgUseJianhuyiDuration.put(i+1, useJianhuyiDurationT/between.size());
				            	sportDuration.put(i+1, sportDurationT);
				         }
				         
			     	   }else{
			     		   /*System.out.println(list.get(i));
			         	   System.out.println(list.get(i+1));
			         	   System.out.println("============"+i);*/
			         	  List<UseJianhuyiLogDO> between = useJianhuyiLogDao.queryUserWeekRecordBetween(sdf.parse(list.get(i)), sdf.parse(list.get(i+1)), userId);
			         	  if(between.size()>0){
			         		 for (UseJianhuyiLogDO useJianhuyiLogDO : between) {
				         		 if(useJianhuyiLogDO.getReadDuration() != null){
				            			readDurationT += useJianhuyiLogDO.getReadDuration();
				    				}
				    				if(useJianhuyiLogDO.getOutdoorsDuration() != null){
				    					outdoorsDurationT += useJianhuyiLogDO.getOutdoorsDuration();
				    				}
				    				if(useJianhuyiLogDO.getReadDistance() != null){
				    					readDistanceT += useJianhuyiLogDO.getReadDistance();
				    				}
				    				if(useJianhuyiLogDO.getReadLight() != null){
				    					readLightT += useJianhuyiLogDO.getReadLight();
				    				}
				    				if(useJianhuyiLogDO.getLookPhoneDuration() != null){
				    					lookPhoneDurationT += useJianhuyiLogDO.getLookPhoneDuration();
				    				}
				    				if(useJianhuyiLogDO.getLookTvComputerDuration() != null){
				    					lookTvComputerDurationT += useJianhuyiLogDO.getLookTvComputerDuration();
				    				}
				    				if(useJianhuyiLogDO.getSitTilt() != null){
				    					sitTiltT += useJianhuyiLogDO.getSitTilt();
				    				}
				    				if(useJianhuyiLogDO.getUseJianhuyiDuration() != null){
				    					useJianhuyiDurationT += useJianhuyiLogDO.getUseJianhuyiDuration();
				    				}
				    				if(useJianhuyiLogDO.getSportDuration() != null){
				    					sportDurationT += useJianhuyiLogDO.getSportDuration();
				    				}
								}
			         		avgReadDuration.put(i+1, readDurationT/between.size());
			            	outdoorsDuration.put(i+1, outdoorsDurationT);
			            	avgReadDistance.put(i+1, readDistanceT/between.size());
			            	avgReadLight.put(i+1, readLightT/between.size());
			            	avgLookPhoneDuration.put(i+1, lookPhoneDurationT/between.size());
			            	avgLookTvComputerDuration.put(i+1, lookTvComputerDurationT/between.size());
			            	avgSitTilt.put(i+1, sitTiltT/between.size());
			            	avgUseJianhuyiDuration.put(i+1, useJianhuyiDurationT/between.size());
			            	sportDuration.put(i+1, sportDurationT);
			         	  }
			         	  
			         	  
			     	   }
			     	   
			        }
			        
			        Map<String,Collection<Double>> mapP = new LinkedHashMap<String,Collection<Double>>();
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
