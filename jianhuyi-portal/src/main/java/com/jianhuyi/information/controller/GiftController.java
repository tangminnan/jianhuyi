package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.*;
import com.jianhuyi.information.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static com.jianhuyi.common.utils.DateUtils.format;


/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:12
 */

@Controller
@RequestMapping("/api/gift")
public class GiftController {
    @Autowired
    private GiftService giftService;
    @Autowired
    private GiftTaskService giftTaskService;
    @Autowired
    private UserTaskLinshiService userTaskLinshiService;
    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private UseJianhuyiLogService useJianhuyiLogService;

    /**
     * 礼物列表
     */
    @ResponseBody
    @GetMapping("/list")
    public R list(Integer page) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("limit", 12);
        params.put("offset", (page - 1) * 12);
        List<GiftDO> giftDOList = giftService.list(params);
        data.put("data", giftDOList);
        return R.ok(data);
    }

    /**
     * 礼物详情
     */
    @ResponseBody
    @PostMapping("/giftDetails")
    public R giftDetails(Long id) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        GiftTaskDO giftTaskDO = new GiftTaskDO();
        GiftDO giftDO = giftService.get(id);

        params.put("giftId", id);
        if (giftTaskService.list(params).size() > 0) {
            giftTaskDO = giftTaskService.list(params).get(0);
        }
        //礼物介绍
        data.put("gift", giftDO);
        //任务详情
        data.put("task", giftTaskDO);
        result.put("data", data);
        return R.ok(result);
    }


    /**
     * 自定义任务(保存和修改)
     */
    @ResponseBody
    @PostMapping("/customTask")
    public R customTask(UserTaskLinshiDO userTaskLinshiDO) {

        if (userTaskLinshiDO.getUserId() != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userTaskLinshiDO.getUserId());
            params.put("finishStatus", "2");
            List<UserTaskDO> userTaskDOList = userTaskService.list(params);

            if (userTaskDOList.size() > 0) {
                return R.error("您还有一个任务没有完成哦！抱歉同时只可以申请一个");
            } else {
                if (userTaskLinshiDO.getId() == 0) {
                    userTaskLinshiDO.setCreateTime(new Date());
                    if (userTaskLinshiService.save(userTaskLinshiDO) > 0) {
                        Map<String, Object> result = new HashMap<>();
                        result.put("data", userTaskLinshiDO.getId());
                        return R.ok(result);
                    } else {
                        return R.error("保存失败");
                    }
                } else {
                    if (userTaskLinshiService.update(userTaskLinshiDO) > 0) {
                        Map<String, Object> result = new HashMap<>();
                        result.put("data", userTaskLinshiDO.getId());
                        return R.ok(result);
                    } else {
                        return R.error("保存失败");
                    }
                }
            }

        } else {
            return R.error("请选择用户！！！");
        }
    }


    /**
     * 提交任务（我要挑战）
     */
    @ResponseBody
    @PostMapping("/saveTask")
    public R saveTask(Long userId, Long giftId, Long taskId) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> params1 = new HashMap<>();
        //查询用户 未完成的任务
        params.put("userId", userId);
        params.put("finishStatus", "2");
        List<UserTaskDO> userTaskDOList = userTaskService.list(params);
        UserTaskDO userTaskDO = new UserTaskDO();
        if (userTaskDOList.size() > 0) {
            return R.error("您还有一个任务没有完成哦！抱歉同时只可以申请一个");
        } else {
            //没有自定义(type == 2)
            if (taskId == null || taskId.equals("") || taskId == 0) {
                userTaskDO.setType(2);
                userTaskDO.setUserId(userId);
                userTaskDO.setGiftId(giftId);

                params1.put("giftId", giftId);

                GiftTaskDO giftDO = giftTaskService.list(params1).get(0);

                userTaskDO.setTaskTime(giftDO.getTaskTime());
                userTaskDO.setEyeRate(giftDO.getAvgRate());
                userTaskDO.setFinishStatus("2");
                userTaskDO.setFinishDay(0);
                userTaskDO.setUnfinishedDay(0);
                userTaskDO.setCreateTime(new Date());

                if (userTaskService.save(userTaskDO) > 0) {
                    return R.ok();
                } else {
                    return R.error("保存失败");
                }
            } else {
                //自定义就查询临时表 type =1
                userTaskDO.setUserId(userId);
                userTaskDO.setGiftId(giftId);
                userTaskDO.setType(1);

                UserTaskLinshiDO userTaskLinshiDO = userTaskLinshiService.get(taskId);
                userTaskDO.setTaskTime(userTaskLinshiDO.getTaskTime());
                userTaskDO.setEyeRate(userTaskLinshiDO.getEyeRate());
                userTaskDO.setAvgRead(userTaskLinshiDO.getAvgRead());
                userTaskDO.setAvgOut(userTaskLinshiDO.getAvgOut());
                userTaskDO.setAvgReadDistance(userTaskLinshiDO.getAvgReadDistance());
                userTaskDO.setAvgLookPhone(userTaskLinshiDO.getAvgLookPhone());
                userTaskDO.setAvgLookTv(userTaskLinshiDO.getAvgLookTv());
                userTaskDO.setAvgSitTilt(userTaskLinshiDO.getAvgSitTilt());
                userTaskDO.setEffectiveUseTime(userTaskLinshiDO.getEffectiveUseTime());
                userTaskDO.setAvgLight(userTaskLinshiDO.getAvgLight());
                userTaskDO.setFinishStatus("2");
                userTaskDO.setFinishDay(0);
                userTaskDO.setUnfinishedDay(0);

                userTaskDO.setCreateTime(new Date());

                if (userTaskService.save(userTaskDO) > 0) {
                    userTaskLinshiService.remove(taskId);
                    return R.ok();
                } else {
                    return R.error("保存失败");
                }
            }
        }
    }


    /**
     * 我的礼品
     */
    @ResponseBody
    @GetMapping("/myGift")
    public R myGift(Long userId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        List<RecordDO> recordDOList = new LinkedList<RecordDO>();
        Integer finishDay = 0;
        //查询用户 已完成的任务
        params.put("userId", userId);
        params.put("finishStatus", "1");
        List<UserTaskDO> userTaskDOList = userTaskService.list(params);
        for (UserTaskDO userTaskDO : userTaskDOList) {
            GiftDO giftDO = giftService.get(userTaskDO.getGiftId());
            userTaskDO.setGiftName(giftDO.getGiftName());
            userTaskDO.setCoverImg(giftDO.getCoverImg());
        }
        //已完成记录
        data.put("finishTask", userTaskDOList);

        //查询未完成的任务
        params.remove("finishStatus");
        params.put("finishStatus", "2");
        if (userTaskService.list(params).size() > 0) {
            UserTaskDO userTaskDO = userTaskService.list(params).get(0);
            //默认任务 判断每日总评价是否达标
            if (userTaskDO.getType() == 2) {
                for (RecordDO recordDO : recordDOList) {
                    if (Integer.parseInt(recordDO.getGrade()) >= Integer.parseInt(userTaskDO.getEyeRate())) {
                        finishDay++;
                        recordDO.setIsfinish(true);
                    } else {
                        recordDO.setIsfinish(false);
                    }
                }
            }
            //自定义任务 判断每天每项是否达标
            else if (userTaskDO.getType() == 1) {
                // finishDay = finishDay(taskId, useJianhuyiLogDOList);
            }
            //返回已完成天数/未完成天数/总天数
            userTaskDO.setFinishDay(finishDay);
            userTaskDO.setUnfinishedDay(userTaskDO.getTaskTime() - finishDay);

            //返回平均等级
            UseJianhuyiLogDO useJianhuyiLogDO = (UseJianhuyiLogDO) getRecordList(userTaskDO.getId(), userId).get("allUseJianhuyiLogDO");
            userTaskDO.setCountGrade(getGrade(useJianhuyiLogDO));

            if (userTaskDO.getFinishDay() == userTaskDO.getTaskTime()) {
                userTaskDO.setFinishStatus("1");
                data.put("taskIng", new UserTaskDO());
            } else {
                data.put("taskIng", userTaskDO);
            }
            userTaskService.update(userTaskDO);
        } else {
            data.put("taskIng", new UserTaskDO());
        }

        result.put("data", data);
        return R.ok(result);
    }

    //获取每项结果数组
    String[] getArray(Map<String, Object> resultData, String fieldName) {
        String avgLookTvComputerDuration = resultData.get(fieldName).toString();
        avgLookTvComputerDuration = avgLookTvComputerDuration.replace("[", "");
        avgLookTvComputerDuration = avgLookTvComputerDuration.replace("]", "");
        avgLookTvComputerDuration = avgLookTvComputerDuration.replace(" ", "");

        String[] attr = avgLookTvComputerDuration.split(",");
        return attr;
    }


    /**
     * 任务进度
     */
    @GetMapping("/taskProgress")
    @ResponseBody
    public R taskProgress(Long taskId, Long userId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        List<RecordDO> recordDOList = new LinkedList<RecordDO>();
        List<UseJianhuyiLogDO> useJianhuyiLogDOList = new LinkedList<UseJianhuyiLogDO>();

        Integer finishDay = 0;

        UserTaskDO userTaskDO = userTaskService.get(taskId);
        if (userTaskDO != null) {
            //查询每日记录
            recordDOList = (List<RecordDO>) getRecordList(taskId, userId).get("recordDOList");
            //返回每天的每项数据总和
            useJianhuyiLogDOList = (List<UseJianhuyiLogDO>) getRecordList(taskId, userId).get("useJianhuyiLogDOList");
            //返回平均等级
            UseJianhuyiLogDO useJianhuyiLogDO = (UseJianhuyiLogDO) getRecordList(taskId, userId).get("allUseJianhuyiLogDO");
            data.put("overallMerit", getGrade(useJianhuyiLogDO));
            //返回平均使用监护仪时长
            data.put("allUseJianhuyi", getRecordList(taskId, userId).get("allUseJianhuyi"));

            //默认任务 判断每日总评价是否达标
            if (userTaskDO.getType() == 2) {
                for (RecordDO recordDO : recordDOList) {
                    if (Integer.parseInt(recordDO.getGrade()) >= Integer.parseInt(userTaskDO.getEyeRate())) {
                        finishDay++;
                        recordDO.setIsfinish(true);
                    } else {
                        recordDO.setIsfinish(false);
                    }
                }
            }
            //自定义任务 判断每天每项是否达标
            else if (userTaskDO.getType() == 1) {
                finishDay = finishDay(taskId, useJianhuyiLogDOList);
            }
            //返回已完成天数/未完成天数/总天数
            userTaskDO.setFinishDay(finishDay);
            userTaskDO.setUnfinishedDay(userTaskDO.getTaskTime() - finishDay);
            //记录每天的数据（日期，有效使用时长，评级，完成度）
            data.put("recordDOList", recordDOList);


            //礼物封面，礼物名称
            GiftDO giftDO = giftService.get(userTaskDO.getGiftId());
            userTaskDO.setCoverImg(giftDO.getCoverImg());
            userTaskDO.setGiftName(giftDO.getGiftName());

            //任务详情
            params.put("giftId", userTaskDO.getGiftId());
            List<GiftTaskDO> giftTaskDOList = giftTaskService.list(params);
            data.put("taskDetails", giftTaskDOList.get(0).getTaskDetails());

            //如果已完成更新状态
            if (finishDay == userTaskDO.getTaskTime()) {
                userTaskDO.setFinishStatus("1");
            }
            //更新任务表
            userTaskService.update(userTaskDO);
            data.put("userTaskDO", userTaskDO);
        }

        result.put("data", data);
        return R.ok(result);
    }

    //获取进度集合
    Map<String, Object> getRecordList(Long taskId, Long userId) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        List<RecordDO> recordDOList = new LinkedList<RecordDO>();
        List<UseJianhuyiLogDO> useJianhuyiLogDOList = new LinkedList<UseJianhuyiLogDO>();

        Double avgLookTvComputerDuration = 0.0;
        Double avgReadLight = 0.0;
        Double avgLookPhoneDuration = 0.0;
        Double avgReadDuration = 0.0;
        Double avgSitTilt = 0.0;
        Double outdoorsDuration = 0.0;
        Double avgReadDistance = 0.0;
        Integer allUseJianhuyi = 0;

        UseJianhuyiLogDO allUseJianhuyiLogDO = new UseJianhuyiLogDO();

        UserTaskDO userTaskDO = userTaskService.get(taskId);
        Date start = null;
        if (userTaskDO != null) {
            start = userTaskDO.getCreateTime();
        } else {
            start = new Date();
        }

        Date end = new Date();

        Map<String, Object> useJianhuyiData = useJianhuyiLogService.queryUserWeekHistory(start, end, userId);

        Map<String, Object> useData = (Map<String, Object>) useJianhuyiData.get("data");
        Map<String, Object> mapData = (Map<String, Object>) useData.get("lineChart");
        Map<String, Object> resultData = (Map<String, Object>) mapData.get("mapP");

        //开始到结束的日期

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


        for (int j = 0; j < list.size(); j++) {
            UseJianhuyiLogDO useJianhuyiLogDO = new UseJianhuyiLogDO();
            RecordDO recordDO = new RecordDO();
            recordDO.setDate(list.get(j));


            for (int i = 0; i < getArray(resultData, "avgLookTvComputerDuration").length; i++) {
                if (i == j) {
                    if (Double.parseDouble(getArray(resultData, "avgLookTvComputerDuration")[j]) > 0) {
                        useJianhuyiLogDO.setLookTvComputerDuration(Double.parseDouble(getArray(resultData, "avgLookTvComputerDuration")[j]));
                        avgLookTvComputerDuration += Double.parseDouble(getArray(resultData, "avgLookTvComputerDuration")[j]);
                    } else {
                        useJianhuyiLogDO.setLookTvComputerDuration(0.0);
                    }

                }
            }

            for (int i = 0; i < getArray(resultData, "avgUseJianhuyiDuration").length; i++) {
                if (i == j) {


                    if (Double.parseDouble(getArray(resultData, "avgUseJianhuyiDuration")[i]) > 0) {
                        useJianhuyiLogDO.setUseJianhuyiDuration(Integer.parseInt(getArray(resultData, "avgUseJianhuyiDuration")[j]));
                        allUseJianhuyi += Integer.parseInt(getArray(resultData, "avgUseJianhuyiDuration")[j]);
                        recordDO.setUseJianhuyiTime(Double.parseDouble(getArray(resultData, "avgUseJianhuyiDuration")[i]));
                    } else {
                        useJianhuyiLogDO.setUseJianhuyiDuration(0);
                        recordDO.setUseJianhuyiTime(0.0);
                    }

                }
            }

            for (int i = 0; i < getArray(resultData, "avgReadLight").length; i++) {
                if (i == j) {
                    if (Double.parseDouble(getArray(resultData, "avgReadLight")[j]) > 0) {
                        useJianhuyiLogDO.setReadLight(Double.parseDouble(getArray(resultData, "avgReadLight")[j]));
                        avgReadLight += Double.parseDouble(getArray(resultData, "avgReadLight")[j]);
                    } else {
                        useJianhuyiLogDO.setReadLight(0.0);
                    }


                }
            }


            for (int i = 0; i < getArray(resultData, "avgLookPhoneDuration").length; i++) {
                if (i == j) {
                    if (Double.parseDouble(getArray(resultData, "avgLookPhoneDuration")[j]) > 0) {
                        useJianhuyiLogDO.setLookPhoneDuration(Double.parseDouble(getArray(resultData, "avgLookPhoneDuration")[j]));
                        avgLookPhoneDuration += Double.parseDouble(getArray(resultData, "avgLookPhoneDuration")[j]);
                    } else {
                        useJianhuyiLogDO.setLookPhoneDuration(0.0);
                    }


                }
            }

            for (int i = 0; i < getArray(resultData, "avgReadDuration").length; i++) {
                if (i == j) {
                    if (Double.parseDouble(getArray(resultData, "avgReadDuration")[j]) > 0) {
                        useJianhuyiLogDO.setReadDuration(Double.parseDouble(getArray(resultData, "avgReadDuration")[j]));
                        avgReadDuration += Double.parseDouble(getArray(resultData, "avgReadDuration")[j]);
                    } else {
                        useJianhuyiLogDO.setReadDuration(0.0);
                    }


                }
            }

            for (int i = 0; i < getArray(resultData, "avgSitTilt").length; i++) {
                if (i == j) {
                    if (Double.parseDouble(getArray(resultData, "avgSitTilt")[j]) > 0) {
                        useJianhuyiLogDO.setSitTilt(Double.parseDouble(getArray(resultData, "avgSitTilt")[j]));
                        avgSitTilt += Double.parseDouble(getArray(resultData, "avgSitTilt")[j]);
                    } else {
                        useJianhuyiLogDO.setSitTilt(0.0);
                    }


                }
            }

            for (int i = 0; i < getArray(resultData, "outdoorsDuration").length; i++) {
                if (i == j) {
                    if (Double.parseDouble(getArray(resultData, "outdoorsDuration")[j]) > 0) {
                        useJianhuyiLogDO.setOutdoorsDuration(Double.parseDouble(getArray(resultData, "outdoorsDuration")[j]));
                        outdoorsDuration += Double.parseDouble(getArray(resultData, "outdoorsDuration")[j]);
                    } else {
                        useJianhuyiLogDO.setOutdoorsDuration(0.0);
                    }

                }
            }

            for (int i = 0; i < getArray(resultData, "avgReadDistance").length; i++) {
                if (i == j) {
                    if (Double.parseDouble(getArray(resultData, "avgReadDistance")[j]) > 0) {
                        useJianhuyiLogDO.setReadDistance(Double.parseDouble(getArray(resultData, "avgReadDistance")[j]));
                        avgReadDistance += Double.parseDouble(getArray(resultData, "avgReadDistance")[j]);
                    } else {
                        useJianhuyiLogDO.setReadDistance(0.0);
                    }


                }
            }


            //计算每天的评价
            recordDO.setGrade(getGrade(useJianhuyiLogDO));
            //每天的评价，添加到全部记录里
            recordDOList.add(recordDO);
            //返回每天的每项数据总和
            useJianhuyiLogDOList.add(useJianhuyiLogDO);
        }
        allUseJianhuyiLogDO.setLookTvComputerDuration(avgLookTvComputerDuration / getArray(resultData, "avgLookTvComputerDuration").length);
        allUseJianhuyiLogDO.setUseJianhuyiDuration(allUseJianhuyi / getArray(resultData, "avgUseJianhuyiDuration").length);
        allUseJianhuyiLogDO.setReadLight(avgReadLight / getArray(resultData, "avgReadLight").length);
        allUseJianhuyiLogDO.setLookPhoneDuration(avgLookPhoneDuration / getArray(resultData, "avgLookPhoneDuration").length);
        allUseJianhuyiLogDO.setReadDuration(avgReadDuration / getArray(resultData, "avgReadDuration").length);
        allUseJianhuyiLogDO.setSitTilt(avgSitTilt / getArray(resultData, "avgSitTilt").length);
        allUseJianhuyiLogDO.setOutdoorsDuration(outdoorsDuration / getArray(resultData, "outdoorsDuration").length);
        allUseJianhuyiLogDO.setReadDistance(avgReadDistance / getArray(resultData, "avgReadDistance").length);


        System.out.println("===================allUseJianhuyiLogDO=====================" + allUseJianhuyiLogDO);
        //计算平均使用监护仪时长
        allUseJianhuyi = allUseJianhuyi / getArray(resultData, "avgUseJianhuyiDuration").length;
        //计算所有天数每项总和
        result.put("allUseJianhuyiLogDO", allUseJianhuyiLogDO);

        result.put("recordDOList", recordDOList);
        result.put("useJianhuyiLogDOList", useJianhuyiLogDOList);
        result.put("allUseJianhuyi", allUseJianhuyi);
        return result;
    }

    //获取总评价
    String getGrade(UseJianhuyiLogDO useJianhuyiLogDO) {
        //System.out.println("========useJianhuyiLogDO222====================" + useJianhuyiLogDO);
        if ((useJianhuyiLogDO.getReadDuration() <= 20 && useJianhuyiLogDO.getOutdoorsDuration() >= 2 && useJianhuyiLogDO.getReadDistance() >= 33) &&
                ((useJianhuyiLogDO.getReadLight() >= 300 && useJianhuyiLogDO.getLookPhoneDuration() <= 10 && useJianhuyiLogDO.getLookTvComputerDuration() <= 20 && useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getReadLight() >= 300 && useJianhuyiLogDO.getLookPhoneDuration() <= 10 && useJianhuyiLogDO.getLookTvComputerDuration() <= 20) || (useJianhuyiLogDO.getReadLight() >= 300 && useJianhuyiLogDO.getLookPhoneDuration() <= 10 && useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getLookTvComputerDuration() <= 20 && useJianhuyiLogDO.getLookPhoneDuration() <= 10 && useJianhuyiLogDO.getSitTilt() <= 5))
                && (useJianhuyiLogDO.getUseJianhuyiDuration() >= 10 || useJianhuyiLogDO.getUseJianhuyiDuration() <= 8 && useJianhuyiLogDO.getUseJianhuyiDuration() < 10)) {
            return "5";
        } else if (
                ((useJianhuyiLogDO.getReadDuration() <= 20) || (useJianhuyiLogDO.getReadDuration() > 20 && useJianhuyiLogDO.getReadDuration() <= 40)) && ((useJianhuyiLogDO.getOutdoorsDuration() >= 2 || (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2))) && ((useJianhuyiLogDO.getReadDistance() >= 33) || (useJianhuyiLogDO.getReadDistance() >= 30 && useJianhuyiLogDO.getReadDistance() < 33))
                        //d优良差
                        && (
                        (
                                ((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300) || ((useJianhuyiLogDO.getReadLight() >= 200) && (useJianhuyiLogDO.getReadLight() < 250)))
                                        && ((useJianhuyiLogDO.getLookPhoneDuration() <= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20))
                                        && ((useJianhuyiLogDO.getLookTvComputerDuration() <= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40))
                                        && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10))
                        ) || (
                                ((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300))
                                        && ((useJianhuyiLogDO.getLookPhoneDuration() <= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20) || (useJianhuyiLogDO.getLookPhoneDuration() > 20 && useJianhuyiLogDO.getLookPhoneDuration() <= 40))
                                        && ((useJianhuyiLogDO.getLookTvComputerDuration() <= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40))
                                        && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10))
                        ) || (
                                ((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300))
                                        && ((useJianhuyiLogDO.getLookPhoneDuration() <= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20)
                                        && ((useJianhuyiLogDO.getLookTvComputerDuration() <= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40) || (useJianhuyiLogDO.getLookTvComputerDuration() > 40 && useJianhuyiLogDO.getLookTvComputerDuration() <= 60))
                                        && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10))
                                )
                        ) || (
                                ((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300))
                                        && ((useJianhuyiLogDO.getLookPhoneDuration() <= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20)
                                        && ((useJianhuyiLogDO.getLookTvComputerDuration() <= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40))
                                        && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10) || (useJianhuyiLogDO.getSitTilt() > 10 && useJianhuyiLogDO.getSitTilt() <= 15))
                                )
                        )
                ) && (useJianhuyiLogDO.getUseJianhuyiDuration() >= 10 || (useJianhuyiLogDO.getUseJianhuyiDuration() >= 8 && useJianhuyiLogDO.getUseJianhuyiDuration() < 10))
        ) {
            return "4";
        } else if (
                (
                        ((useJianhuyiLogDO.getReadDuration() <= 20) || ((useJianhuyiLogDO.getReadDuration() > 20 && useJianhuyiLogDO.getReadDuration() <= 40)) || ((useJianhuyiLogDO.getReadDuration() > 40 && useJianhuyiLogDO.getReadDuration() <= 90))) && ((useJianhuyiLogDO.getOutdoorsDuration() >= 2) || ((useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2))) && ((useJianhuyiLogDO.getReadDistance() >= 33) || (useJianhuyiLogDO.getReadDistance() >= 30 && useJianhuyiLogDO.getReadDistance() < 33))
                                ||
                                ((useJianhuyiLogDO.getReadDuration() <= 20) || ((useJianhuyiLogDO.getReadDuration() > 20 && useJianhuyiLogDO.getReadDuration() <= 40))) && ((useJianhuyiLogDO.getOutdoorsDuration() >= 2) || ((useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2)) || (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1)) && ((useJianhuyiLogDO.getReadDistance() >= 33) || (useJianhuyiLogDO.getReadDistance() >= 30 && useJianhuyiLogDO.getReadDistance() < 33))
                                ||
                                ((useJianhuyiLogDO.getReadDuration() <= 20) || (useJianhuyiLogDO.getReadDuration() > 20 && useJianhuyiLogDO.getReadDuration() <= 40)) || ((useJianhuyiLogDO.getOutdoorsDuration() >= 2) || ((useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2))) && ((useJianhuyiLogDO.getReadDistance() >= 33) || (useJianhuyiLogDO.getReadDistance() >= 30 && useJianhuyiLogDO.getReadDistance() < 33) || (useJianhuyiLogDO.getReadDistance() > 20 && useJianhuyiLogDO.getReadDistance() <= 30))
                ) && (
                        (((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300) || (useJianhuyiLogDO.getReadLight() >= 200 && useJianhuyiLogDO.getReadLight() < 250)) && ((useJianhuyiLogDO.getLookPhoneDuration() <= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20)) && ((useJianhuyiLogDO.getLookTvComputerDuration() <= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40)) && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10)))
                                ||
                                (((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300)) && (((useJianhuyiLogDO.getLookPhoneDuration() <= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20)) || (useJianhuyiLogDO.getLookPhoneDuration() > 20 && useJianhuyiLogDO.getLookPhoneDuration() <= 40)) && ((useJianhuyiLogDO.getLookTvComputerDuration() <= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40)) && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10)))
                                ||
                                (((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300)) && (((useJianhuyiLogDO.getLookPhoneDuration() <= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20))) && ((useJianhuyiLogDO.getLookTvComputerDuration() <= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40) || (useJianhuyiLogDO.getLookTvComputerDuration() > 40 && useJianhuyiLogDO.getLookTvComputerDuration() <= 60)) && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10)))
                                ||
                                (((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300)) && (((useJianhuyiLogDO.getLookPhoneDuration() <= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20))) && ((useJianhuyiLogDO.getLookTvComputerDuration() <= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40)) && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10)) || (useJianhuyiLogDO.getSitTilt() > 10 && useJianhuyiLogDO.getSitTilt() <= 15))
                )
                        && (
                        ((useJianhuyiLogDO.getUseJianhuyiDuration() >= 10) || (useJianhuyiLogDO.getUseJianhuyiDuration() >= 8 && useJianhuyiLogDO.getUseJianhuyiDuration() < 10) || (useJianhuyiLogDO.getUseJianhuyiDuration() >= 5 && useJianhuyiLogDO.getUseJianhuyiDuration() < 8))
                )
        ) {
            return "3";
        } else if (
                (
                        //前三项最多2项差
                        (
                                ((useJianhuyiLogDO.getReadDuration() <= 20) || (useJianhuyiLogDO.getReadDuration() > 20 && useJianhuyiLogDO.getReadDuration() <= 40) || (useJianhuyiLogDO.getReadDuration() > 40 && useJianhuyiLogDO.getReadDuration() <= 90)) && ((useJianhuyiLogDO.getOutdoorsDuration() >= 2) || (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) || (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1)) && ((useJianhuyiLogDO.getReadDistance() >= 33) || (useJianhuyiLogDO.getReadDistance() >= 30 && useJianhuyiLogDO.getReadDistance() < 33))
                        )
                                ||
                                (
                                        ((useJianhuyiLogDO.getReadDuration() <= 20) || (useJianhuyiLogDO.getReadDuration() > 20 && useJianhuyiLogDO.getReadDuration() <= 40)) && ((useJianhuyiLogDO.getOutdoorsDuration() >= 2) || (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) || (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1)) && ((useJianhuyiLogDO.getReadDistance() >= 33) || (useJianhuyiLogDO.getReadDistance() >= 30 && useJianhuyiLogDO.getReadDistance() < 33) || (useJianhuyiLogDO.getReadDistance() > 20 && useJianhuyiLogDO.getReadDistance() <= 30))
                                )
                                ||
                                (
                                        ((useJianhuyiLogDO.getReadDuration() <= 20) || (useJianhuyiLogDO.getReadDuration() > 20 && useJianhuyiLogDO.getReadDuration() <= 40) || (useJianhuyiLogDO.getReadDuration() > 40 && useJianhuyiLogDO.getReadDuration() <= 90)) && ((useJianhuyiLogDO.getOutdoorsDuration() >= 2) || (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2)) && ((useJianhuyiLogDO.getReadDistance() >= 33) || (useJianhuyiLogDO.getReadDistance() >= 30 && useJianhuyiLogDO.getReadDistance() < 33) || (useJianhuyiLogDO.getReadDistance() > 20 && useJianhuyiLogDO.getReadDistance() <= 30))
                                )
                ) &&
                        (
                                (
                                        (((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300) || (useJianhuyiLogDO.getReadLight() >= 200 && useJianhuyiLogDO.getReadLight() < 250)) && ((useJianhuyiLogDO.getLookPhoneDuration() >= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20) || (useJianhuyiLogDO.getLookPhoneDuration() > 20 && useJianhuyiLogDO.getLookPhoneDuration() <= 40)) && ((useJianhuyiLogDO.getLookTvComputerDuration() >= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40) || (useJianhuyiLogDO.getLookTvComputerDuration() > 40 && useJianhuyiLogDO.getLookTvComputerDuration() <= 60)) && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10)))//123
                                                &&
                                                (((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300) || (useJianhuyiLogDO.getReadLight() >= 200 && useJianhuyiLogDO.getReadLight() < 250)) && ((useJianhuyiLogDO.getLookPhoneDuration() >= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20)) && ((useJianhuyiLogDO.getLookTvComputerDuration() >= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40) || (useJianhuyiLogDO.getLookTvComputerDuration() > 40 && useJianhuyiLogDO.getLookTvComputerDuration() <= 60)) && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10) || (useJianhuyiLogDO.getSitTilt() > 10 && useJianhuyiLogDO.getSitTilt() <= 15)))//134
                                                &&
                                                (((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300)) && ((useJianhuyiLogDO.getLookPhoneDuration() >= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20) || (useJianhuyiLogDO.getLookPhoneDuration() > 20 && useJianhuyiLogDO.getLookPhoneDuration() <= 40)) && ((useJianhuyiLogDO.getLookTvComputerDuration() >= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40) || (useJianhuyiLogDO.getLookTvComputerDuration() > 40 && useJianhuyiLogDO.getLookTvComputerDuration() <= 60)) && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10) || (useJianhuyiLogDO.getSitTilt() > 10 && useJianhuyiLogDO.getSitTilt() <= 15)))//234
                                                &&
                                                (((useJianhuyiLogDO.getReadLight() >= 300) || (useJianhuyiLogDO.getReadLight() >= 250 && useJianhuyiLogDO.getReadLight() < 300) || (useJianhuyiLogDO.getReadLight() >= 200 && useJianhuyiLogDO.getReadLight() < 250)) && ((useJianhuyiLogDO.getLookPhoneDuration() >= 10) || (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20) || (useJianhuyiLogDO.getLookPhoneDuration() > 20 && useJianhuyiLogDO.getLookPhoneDuration() <= 40)) && ((useJianhuyiLogDO.getLookTvComputerDuration() >= 20) || (useJianhuyiLogDO.getLookTvComputerDuration() > 20 && useJianhuyiLogDO.getLookTvComputerDuration() <= 40)) && ((useJianhuyiLogDO.getSitTilt() <= 5) || (useJianhuyiLogDO.getSitTilt() > 5 && useJianhuyiLogDO.getSitTilt() <= 10) || (useJianhuyiLogDO.getSitTilt() > 10 && useJianhuyiLogDO.getSitTilt() <= 15)))//234
                                )
                        ) &&
                        (
                                (useJianhuyiLogDO.getUseJianhuyiDuration() >= 10) || (useJianhuyiLogDO.getUseJianhuyiDuration() >= 8 && useJianhuyiLogDO.getUseJianhuyiDuration() < 10) || (useJianhuyiLogDO.getUseJianhuyiDuration() >= 5 && useJianhuyiLogDO.getUseJianhuyiDuration() < 8) || (useJianhuyiLogDO.getUseJianhuyiDuration() < 5)
                        )
        ) {
            return "2";
        } else {
            return "1";
        }
    }

    //已完成天数计算 type=1 自定义
    Integer finishDay(Long taskId, List<UseJianhuyiLogDO> useJianhuyiLogDOList) {
        Integer day = 0;
        UserTaskDO userTaskDO = userTaskService.get(taskId);
        for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOList) {


        }

        return day;
    }

    boolean getMinTask(Long taskId, UseJianhuyiLogDO useJianhuyiLogDO) {
        UserTaskDO userTaskDO = userTaskService.get(taskId);
        boolean result = false;
        boolean getAvgRead = false;
        boolean getAvgOut = false;
        boolean getAvgReadDistance = false;
        boolean getAvgLookPhone = false;
        boolean getAvgLookTv = false;

        /**
         * 判断阅读时长是否达标
         * */
        //极差
        if (userTaskDO.getAvgRead().equals("1")) {
            if (useJianhuyiLogDO.getReadDuration() > 90) {
                getAvgRead = true;
            }
        } //差
        else if (userTaskDO.getAvgRead().equals("2")) {
            if (useJianhuyiLogDO.getReadDuration() > 40 && useJianhuyiLogDO.getReadDuration() <= 90) {
                getAvgRead = true;
            }
        }//良好
        else if (userTaskDO.getAvgRead().equals("4")) {
            if (useJianhuyiLogDO.getReadDuration() > 20 && useJianhuyiLogDO.getReadDuration() <= 40) {
                getAvgRead = true;
            }
        }//优
        else if (userTaskDO.getAvgRead().equals("5")) {
            if (useJianhuyiLogDO.getReadDuration() <= 20) {
                getAvgRead = true;
            }
        }


        /**
         * 判断户外时长是否达标
         * */
        //极差
        if (userTaskDO.getAvgOut().equals("1")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
                getAvgOut = true;
            }
        } //差
        else if (userTaskDO.getAvgOut().equals("2")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1) {
                getAvgOut = true;
            }
        }//良好
        else if (userTaskDO.getAvgOut().equals("4")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) {
                getAvgOut = true;
            }
        }//优
        else if (userTaskDO.getAvgOut().equals("5")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 2) {
                getAvgOut = true;
            }
        }


        /**
         * 判断阅读距离是否达标
         * */
        //极差
        if (userTaskDO.getAvgReadDistance().equals("1")) {
            if (useJianhuyiLogDO.getReadDistance() < 20) {
                getAvgReadDistance = true;
            }
        } //差
        else if (userTaskDO.getAvgReadDistance().equals("2")) {
            if (useJianhuyiLogDO.getReadDistance() > 20 && useJianhuyiLogDO.getReadDistance() <= 30) {
                getAvgReadDistance = true;
            }
        }//良好
        else if (userTaskDO.getAvgReadDistance().equals("4")) {
            if (useJianhuyiLogDO.getReadDistance() >= 30 && useJianhuyiLogDO.getReadDistance() < 33) {
                getAvgReadDistance = true;
            }
        }//优
        else if (userTaskDO.getAvgReadDistance().equals("5")) {
            if (useJianhuyiLogDO.getReadDistance() >= 33) {
                getAvgReadDistance = true;
            }
        }


        /**
         * 判断看手机是否达标
         * */
        //极差
        if (userTaskDO.getAvgLookPhone().equals("1")) {
            if (useJianhuyiLogDO.getLookPhoneDuration() > 40) {
                getAvgLookPhone = true;
            }
        } //差
        else if (userTaskDO.getAvgLookPhone().equals("2")) {
            if (useJianhuyiLogDO.getLookPhoneDuration() > 20 && useJianhuyiLogDO.getLookPhoneDuration() <= 40) {
                getAvgLookPhone = true;
            }
        }//良好
        else if (userTaskDO.getAvgLookPhone().equals("4")) {
            if (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20) {
                getAvgLookPhone = true;
            }
        }//优
        else if (userTaskDO.getAvgLookPhone().equals("5")) {
            if (useJianhuyiLogDO.getLookPhoneDuration() <= 10) {
                getAvgLookPhone = true;
            }
        }


        /**
         * 判断户外时长是否达标
         * */
        //极差
        if (userTaskDO.getAvgLookTv().equals("1")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
                getAvgOut = true;
            }
        } //差
        else if (userTaskDO.getAvgOut().equals("2")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1) {
                getAvgOut = true;
            }
        }//良好
        else if (userTaskDO.getAvgOut().equals("4")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) {
                getAvgOut = true;
            }
        }//优
        else if (userTaskDO.getAvgOut().equals("5")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 2) {
                getAvgOut = true;
            }
        }


        /**
         * 判断户外时长是否达标
         * */
        //极差
        if (userTaskDO.getAvgOut().equals("1")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
                getAvgOut = true;
            }
        } //差
        else if (userTaskDO.getAvgOut().equals("2")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1) {
                getAvgOut = true;
            }
        }//良好
        else if (userTaskDO.getAvgOut().equals("4")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) {
                getAvgOut = true;
            }
        }//优
        else if (userTaskDO.getAvgOut().equals("5")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 2) {
                getAvgOut = true;
            }
        }


        /**
         * 判断户外时长是否达标
         * */
        //极差
        if (userTaskDO.getAvgOut().equals("1")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
                getAvgOut = true;
            }
        } //差
        else if (userTaskDO.getAvgOut().equals("2")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1) {
                getAvgOut = true;
            }
        }//良好
        else if (userTaskDO.getAvgOut().equals("4")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) {
                getAvgOut = true;
            }
        }//优
        else if (userTaskDO.getAvgOut().equals("5")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 2) {
                getAvgOut = true;
            }
        }


        /**
         * 判断户外时长是否达标
         * */
        //极差
        if (userTaskDO.getAvgOut().equals("1")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
                getAvgOut = true;
            }
        } //差
        else if (userTaskDO.getAvgOut().equals("2")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1) {
                getAvgOut = true;
            }
        }//良好
        else if (userTaskDO.getAvgOut().equals("4")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) {
                getAvgOut = true;
            }
        }//优
        else if (userTaskDO.getAvgOut().equals("5")) {
            if (useJianhuyiLogDO.getOutdoorsDuration() >= 2) {
                getAvgOut = true;
            }
        }
        return result;
    }
}
