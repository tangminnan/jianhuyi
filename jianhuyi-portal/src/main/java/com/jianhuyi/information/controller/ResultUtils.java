package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.common.utils.StringUtils;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.domain.UseTimeDO;
import com.jianhuyi.information.domain.UserTaskDO;
import com.jianhuyi.information.domain.UserTaskLinshiDO;
import com.jianhuyi.information.service.UseTimeService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  监护仪等级判断标准
 */
@Component
public class ResultUtils {

    private static UseTimeService useTimeService;
    static JSONObject jsonObject = null;

    @Autowired
    public void setUseTimeService(UseTimeService useTimeService) {
        ResultUtils.useTimeService = useTimeService;
    }

    /**
     *  平均单次阅读时长判断
     */

    public static String resultAvgReadDuration(Double avgReadDuration){
        String result="1";//默认极差
        if (avgReadDuration <= 20)
            result= "5";//优
        else if (avgReadDuration > 20 && avgReadDuration <= 40)
            result= "4";//良好
        else if (avgReadDuration > 40 && avgReadDuration <= 90)
            result= "2";//差
        else if (avgReadDuration > 90)
            result= "1";//极差
        return result;
    }

    /**
     *  户外累计时间
     */

    public static String resultOutdoorsDuration(Double outdoorsDuration){
        String result="1";//默认极差
        if (outdoorsDuration >=2 )
            result= "5";//优
        else if (outdoorsDuration >= 1 && outdoorsDuration <2)
            result= "4";//良好
        else if (outdoorsDuration >= 0.5 && outdoorsDuration <= 1)
            result= "2";//差
        else if (outdoorsDuration <0.5)
            result= "1";//极差
        return result;
    }

    /**
     *  平均阅读距离
     */

    public static String resultAvgReadDistance(Double avgReadDistance){
        String result="1";//默认极差
        if (avgReadDistance >=33 )
            result= "5";//优
        else if (avgReadDistance >= 30 && avgReadDistance <33)
            result= "4";//良好
        else if (avgReadDistance >= 20 && avgReadDistance <30)
            result= "2";//差
        else if (avgReadDistance <20)
            result= "1";//极差
        return result;
    }

    /**
     *  平均阅读光照
     */

    public static String resultAvgReadLight(Double avgReadLight){
        String result="1";//默认极差
        if (avgReadLight >=300 )
            result= "5";//优
        else if (avgReadLight >= 250 && avgReadLight <300)
            result= "4";//良好
        else if (avgReadLight >= 200 && avgReadLight <250)
            result= "2";//差
        else if (avgReadLight <200)
            result= "1";//极差
        return result;
    }

    /**
     *  平均单次看手机时长
     */

    public static String resultAvgLookPhoneDuration(Double avgLookPhoneDuration){
        String result="1";//默认极差
        if (avgLookPhoneDuration <=10 )
            result= "5";//优
        else if (avgLookPhoneDuration > 10 && avgLookPhoneDuration <=20)
            result= "4";//良好
        else if (avgLookPhoneDuration > 20 && avgLookPhoneDuration <=40)
            result= "2";//差
        else if (avgLookPhoneDuration >40)
            result= "1";//极差
        return result;
    }

    /**
     *  看电脑与电视时长
     */

    public static String resultAvgLookTvComputerDuration(Double avgLookTvComputerDuration){
        String result="1";//默认极差
        if (avgLookTvComputerDuration <=20 )
            result= "5";//优
        else if (avgLookTvComputerDuration > 20 && avgLookTvComputerDuration <=40)
            result= "4";//良好
        else if (avgLookTvComputerDuration > 40 && avgLookTvComputerDuration <=60)
            result= "2";//差
        else if (avgLookTvComputerDuration >60)
            result= "1";//极差
        return result;
    }

    /**
     *  平均阅读旋转角度
     */

    public static String resultAvgSitTilt(Double avgSitTilt){
        String result="1";//默认极差
        if (avgSitTilt <=5 )
            result= "5";//优
        else if (avgSitTilt > 5 && avgSitTilt <=10)
            result= "4";//良好
        else if (avgSitTilt > 10 && avgSitTilt <=15)
            result= "2";//差
        else if (avgSitTilt >15)
            result= "1";//极差
        return result;
    }

    /**
     *  有效监护仪使用时长
     */

    public static String resultUseJianhuyiDuration(Double useJianhuyiDuration){
        String result="1";//默认极差
        if (useJianhuyiDuration >=10 )
            result= "5";//优
        else if (useJianhuyiDuration >= 8 && useJianhuyiDuration <10)
            result= "4";//良好
        else if (useJianhuyiDuration >= 5 && useJianhuyiDuration <8)
            result= "2";//差
        else if (useJianhuyiDuration <5)
            result= "1";//极差
        return result;
    }

    /**
     * 计算平均等级
     */
    public static String totalDegree(UserTaskDO userTaskDO,UserTaskLinshiDO userTaskLinshiDO){
        if(ifY(userTaskDO,userTaskLinshiDO))
            return "5";
        if(ifL(userTaskDO,userTaskLinshiDO))
            return "4";
        if(ifC(userTaskDO,userTaskLinshiDO))
            return "3";//不太好
        if(ifJC(userTaskDO,userTaskLinshiDO))
            return "2";//差
        return "1";

    }

    /**
     * 判断是否为优
     * @return
     */
    public static boolean ifY(UserTaskDO userTaskDO,UserTaskLinshiDO userTaskLinshiDO){
        if(userTaskLinshiDO!=null) {
            if ("5".equals(userTaskLinshiDO.getAvgRead())
                    && "5".equals(userTaskLinshiDO.getAvgOut())
                    && "5".equals(userTaskLinshiDO.getAvgReadDistance())) {
                List<String> l = new ArrayList<String>();
                if ("5".equals(userTaskLinshiDO.getAvgLight()) || "4".equals(userTaskLinshiDO.getAvgLight()))
                    l.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgLookPhone()) || "4".equals(userTaskLinshiDO.getAvgLookPhone()))
                    l.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgLookTv()) || "4".equals(userTaskLinshiDO.getAvgLookTv()))
                    l.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgSitTilt()) || "4".equals(userTaskLinshiDO.getAvgSitTilt()))
                    l.add("c");
                if (l.size() >= 3) {
                    if ("5".equals(userTaskLinshiDO.getEffectiveUseTime()) || "4".equals(userTaskLinshiDO.getEffectiveUseTime()))
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
        else if(userTaskDO!=null) {
            if ("5".equals(userTaskDO.getAvgRead())
                    && "5".equals(userTaskDO.getAvgOut())
                    && "5".equals(userTaskDO.getAvgReadDistance())) {
                List<String> l = new ArrayList<String>();
                if ("5".equals(userTaskDO.getAvgLight()) || "4".equals(userTaskDO.getAvgLight()))
                    l.add("c");
                if ("5".equals(userTaskDO.getAvgLookPhone()) || "4".equals(userTaskDO.getAvgLookPhone()))
                    l.add("c");
                if ("5".equals(userTaskDO.getAvgLookTv()) || "4".equals(userTaskDO.getAvgLookTv()))
                    l.add("c");
                if ("5".equals(userTaskDO.getAvgSitTilt()) || "4".equals(userTaskDO.getAvgSitTilt()))
                    l.add("c");
                if (l.size() >= 3) {
                    if ("5".equals(userTaskDO.getEffectiveUseTime()) || "4".equals(userTaskDO.getEffectiveUseTime()))
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
        return false;
    }
    /**
     * 判断是否为良
     * @return
     */
    public static boolean ifL(UserTaskDO userTaskDO,UserTaskLinshiDO userTaskLinshiDO){
        if(userTaskLinshiDO!=null) {
            if (("5".equals(userTaskLinshiDO.getAvgRead())
                    || "4".equals(userTaskLinshiDO.getAvgRead()))
                    && ("5".equals(userTaskLinshiDO.getAvgOut())
                    || "4".equals(userTaskLinshiDO.getAvgOut()))
                    && ("5".equals(userTaskLinshiDO.getAvgReadDistance())
                    || "4".equals(userTaskLinshiDO.getAvgReadDistance()))) {

                List<String> l = new ArrayList<String>();
                if ("5".equals(userTaskLinshiDO.getAvgLight()) || "4".equals(userTaskLinshiDO.getAvgLight())
                        || "2".equals(userTaskLinshiDO.getAvgLight()))
                    l.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgLookPhone()) || "4".equals(userTaskLinshiDO.getAvgLookPhone())
                        || "2".equals(userTaskLinshiDO.getAvgLookPhone()))
                    l.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgLookTv()) || "4".equals(userTaskLinshiDO.getAvgLookTv())
                        || "2".equals(userTaskLinshiDO.getAvgLookTv()))
                    l.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgSitTilt()) || "4".equals(userTaskLinshiDO.getAvgSitTilt())
                        || "2".equals(userTaskLinshiDO.getAvgSitTilt()))
                    l.add("c");
                if (l.size() <= 1) {
                    if ("5".equals(userTaskLinshiDO.getEffectiveUseTime()) || "4".equals(userTaskLinshiDO.getEffectiveUseTime()))
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }else if(userTaskDO!=null){
            if (("5".equals(userTaskDO.getAvgRead())
                    || "4".equals(userTaskDO.getAvgRead()))
                    && ("5".equals(userTaskDO.getAvgOut())
                    || "4".equals(userTaskDO.getAvgOut()))
                    && ("5".equals(userTaskDO.getAvgReadDistance())
                    || "4".equals(userTaskDO.getAvgReadDistance()))) {

                List<String> l = new ArrayList<String>();
                if ("5".equals(userTaskDO.getAvgLight()) || "4".equals(userTaskDO.getAvgLight())
                        || "2".equals(userTaskDO.getAvgLight()))
                    l.add("c");
                if ("5".equals(userTaskDO.getAvgLookPhone()) || "4".equals(userTaskDO.getAvgLookPhone())
                        || "2".equals(userTaskDO.getAvgLookPhone()))
                    l.add("c");
                if ("5".equals(userTaskDO.getAvgLookTv()) || "4".equals(userTaskDO.getAvgLookTv())
                        || "2".equals(userTaskDO.getAvgLookTv()))
                    l.add("c");
                if ("5".equals(userTaskDO.getAvgSitTilt()) || "4".equals(userTaskDO.getAvgSitTilt())
                        || "2".equals(userTaskDO.getAvgSitTilt()))
                    l.add("c");
                if (l.size() <= 1) {
                    if ("5".equals(userTaskDO.getEffectiveUseTime()) || "4".equals(userTaskDO.getEffectiveUseTime()))
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
        return false;
    }
    /**
     * 判断是否为不太好
     * @return
     */
    public static boolean ifC(UserTaskDO userTaskDO,UserTaskLinshiDO userTaskLinshiDO){
        if(userTaskLinshiDO!=null) {
            List<String> l = new ArrayList<String>();
            if (("5".equals(userTaskLinshiDO.getAvgRead()))
                    || "4".equals(userTaskLinshiDO.getAvgRead())
                    || "2".equals(userTaskLinshiDO.getAvgRead()))
                l.add("c");
            if (("5".equals(userTaskLinshiDO.getAvgOut()))
                    || "4".equals(userTaskLinshiDO.getAvgOut())
                    || "2".equals(userTaskLinshiDO.getAvgOut()))
                l.add("c");
            if (("5".equals(userTaskLinshiDO.getAvgReadDistance()))
                    || "4".equals(userTaskLinshiDO.getAvgReadDistance())
                    || "2".equals(userTaskLinshiDO.getAvgReadDistance()))
                l.add("c");
            if (l.size() <= 1) {
                List<String> ll = new ArrayList<String>();
                if ("5".equals(userTaskLinshiDO.getAvgLight()) || "4".equals(userTaskLinshiDO.getAvgLight())
                        || "2".equals(userTaskLinshiDO.getAvgLight()))
                    ll.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgLookPhone()) || "4".equals(userTaskLinshiDO.getAvgLookPhone())
                        || "2".equals(userTaskLinshiDO.getAvgLookPhone()))
                    ll.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgLookTv()) || "4".equals(userTaskLinshiDO.getAvgLookTv())
                        || "2".equals(userTaskLinshiDO.getAvgLookTv()))
                    ll.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgSitTilt()) || "4".equals(userTaskLinshiDO.getAvgSitTilt())
                        || "2".equals(userTaskLinshiDO.getAvgSitTilt()))
                    ll.add("c");
                if (ll.size() <= 1) {
                    if ("5".equals(userTaskLinshiDO.getEffectiveUseTime())
                            || "4".equals(userTaskLinshiDO.getEffectiveUseTime())
                            || "2".equals(userTaskLinshiDO.getEffectiveUseTime()))
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }else if(userTaskDO!=null){
            List<String> l = new ArrayList<String>();
            if (("5".equals(userTaskDO.getAvgRead()))
                    || "4".equals(userTaskDO.getAvgRead())
                    || "2".equals(userTaskDO.getAvgRead()))
                l.add("c");
            if (("5".equals(userTaskDO.getAvgOut()))
                    || "4".equals(userTaskDO.getAvgOut())
                    || "2".equals(userTaskDO.getAvgOut()))
                l.add("c");
            if (("5".equals(userTaskDO.getAvgReadDistance()))
                    || "4".equals(userTaskDO.getAvgReadDistance())
                    || "2".equals(userTaskDO.getAvgReadDistance()))
                l.add("c");
            if (l.size() <= 1) {
                List<String> ll = new ArrayList<String>();
                if ("5".equals(userTaskDO.getAvgLight()) || "4".equals(userTaskDO.getAvgLight())
                        || "2".equals(userTaskDO.getAvgLight()))
                    ll.add("c");
                if ("5".equals(userTaskDO.getAvgLookPhone()) || "4".equals(userTaskDO.getAvgLookPhone())
                        || "2".equals(userTaskDO.getAvgLookPhone()))
                    ll.add("c");
                if ("5".equals(userTaskDO.getAvgLookTv()) || "4".equals(userTaskDO.getAvgLookTv())
                        || "2".equals(userTaskDO.getAvgLookTv()))
                    ll.add("c");
                if ("5".equals(userTaskDO.getAvgSitTilt()) || "4".equals(userTaskDO.getAvgSitTilt())
                        || "2".equals(userTaskDO.getAvgSitTilt()))
                    ll.add("c");
                if (ll.size() <= 1) {
                    if ("5".equals(userTaskDO.getEffectiveUseTime())
                            || "4".equals(userTaskDO.getEffectiveUseTime())
                            || "2".equals(userTaskDO.getEffectiveUseTime()))
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }
    /**
     * 判断是否为差
     * @return
     */
    public static boolean ifJC(UserTaskDO userTaskDO,UserTaskLinshiDO userTaskLinshiDO){
        if(userTaskLinshiDO!=null) {
            List<String> l = new ArrayList<String>();
            if (("5".equals(userTaskLinshiDO.getAvgRead()))
                    || "4".equals(userTaskLinshiDO.getAvgRead())
                    || "2".equals(userTaskLinshiDO.getAvgRead()))
                l.add("c");
            if (("5".equals(userTaskLinshiDO.getAvgOut()))
                    || "4".equals(userTaskLinshiDO.getAvgOut())
                    || "2".equals(userTaskLinshiDO.getAvgOut()))
                l.add("c");
            if (("5".equals(userTaskLinshiDO.getAvgReadDistance()))
                    || "4".equals(userTaskLinshiDO.getAvgReadDistance())
                    || "2".equals(userTaskLinshiDO.getAvgReadDistance()))
                l.add("c");
            if (l.size() <= 2) {
                List<String> ll = new ArrayList<String>();
                if ("5".equals(userTaskLinshiDO.getAvgLight()) || "4".equals(userTaskLinshiDO.getAvgLight())
                        || "2".equals(userTaskLinshiDO.getAvgLight()))
                    ll.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgLookPhone()) || "4".equals(userTaskLinshiDO.getAvgLookPhone())
                        || "2".equals(userTaskLinshiDO.getAvgLookPhone()))
                    ll.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgLookTv()) || "4".equals(userTaskLinshiDO.getAvgLookTv())
                        || "2".equals(userTaskLinshiDO.getAvgLookTv()))
                    ll.add("c");
                if ("5".equals(userTaskLinshiDO.getAvgSitTilt()) || "4".equals(userTaskLinshiDO.getAvgSitTilt())
                        || "2".equals(userTaskLinshiDO.getAvgSitTilt()))
                    ll.add("c");
                if (ll.size() <= 3) {
                    if ("5".equals(userTaskLinshiDO.getEffectiveUseTime())
                            || "4".equals(userTaskLinshiDO.getEffectiveUseTime())
                            || "2".equals(userTaskLinshiDO.getEffectiveUseTime())
                            || "1".equals(userTaskLinshiDO.getEffectiveUseTime()))
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }else if(userTaskDO!=null){
            List<String> l = new ArrayList<String>();
            if(("5".equals(userTaskDO.getAvgRead()))
                    || "4".equals(userTaskDO.getAvgRead())
                    || "2".equals(userTaskDO.getAvgRead()))
                l.add("c");
            if(("5".equals(userTaskDO.getAvgOut()))
                    || "4".equals(userTaskDO.getAvgOut())
                    || "2".equals(userTaskDO.getAvgOut()))
                l.add("c");
            if(("5".equals(userTaskDO.getAvgReadDistance()))
                    || "4".equals(userTaskDO.getAvgReadDistance())
                    || "2".equals(userTaskDO.getAvgReadDistance()))
                l.add("c");
            if(l.size()<=2){
                List<String> ll = new ArrayList<String>();
                if("5".equals(userTaskDO.getAvgLight()) || "4".equals(userTaskDO.getAvgLight())
                        || "2".equals(userTaskDO.getAvgLight()))
                    ll.add("c");
                if("5".equals(userTaskDO.getAvgLookPhone()) || "4".equals(userTaskDO.getAvgLookPhone())
                        || "2".equals(userTaskDO.getAvgLookPhone()))
                    ll.add("c");
                if("5".equals(userTaskDO.getAvgLookTv()) || "4".equals(userTaskDO.getAvgLookTv())
                        || "2".equals(userTaskDO.getAvgLookTv()))
                    ll.add("c");
                if("5".equals(userTaskDO.getAvgSitTilt()) || "4".equals(userTaskDO.getAvgSitTilt())
                        || "2".equals(userTaskDO.getAvgSitTilt()))
                    ll.add("c");
                if(ll.size()<=3){
                    if("5".equals(userTaskDO.getEffectiveUseTime())
                            || "4".equals(userTaskDO.getEffectiveUseTime())
                            || "2".equals(userTaskDO.getEffectiveUseTime())
                            || "1".equals(userTaskDO.getEffectiveUseTime()))
                        return true;
                    else
                        return false;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * 判断当天的任务是否已经完成
     * @param userTaskDO
     * @param userTaskLinshiDO
     * @return 0 已完成  1 = 未完成
     */
    public static Integer checkTaskIfFinished(UserTaskDO userTaskDO, UserTaskLinshiDO userTaskLinshiDO){
        System.out.println("判断当天的任务是否已经完成");
        if(     StrC(userTaskDO.getAvgRead(), userTaskLinshiDO.getAvgRead()) == 1 &&
                StrC(userTaskDO.getAvgLookPhone(), userTaskLinshiDO.getAvgLookPhone()) == 1 &&
                StrC(userTaskDO.getAvgLookTv(), userTaskLinshiDO.getAvgLookTv()) == 1 &&
                StrC(userTaskDO.getAvgLight(), userTaskLinshiDO.getAvgLight()) == 1 &&
                StrC(userTaskDO.getAvgOut(), userTaskLinshiDO.getAvgOut()) == 1 &&
                StrC(userTaskDO.getAvgReadDistance(), userTaskLinshiDO.getAvgReadDistance()) == 1 &&
                StrC(userTaskDO.getAvgSitTilt(), userTaskLinshiDO.getAvgSitTilt()) == 1 &&
                StrC(userTaskDO.getEffectiveUseTime(), userTaskLinshiDO.getEffectiveUseTime()) == 1
                )
            return 0;
        else
            return 1;
    }

    /**
     * 计算每日的积分
     * @param userTaskDO
     * @param userTaskLinshiDO
     * @return
     */
    public static int countScore(UserTaskDO userTaskDO, UserTaskLinshiDO userTaskLinshiDO) {
        userTaskLinshiDO.setIffinish(checkTaskIfFinished(userTaskDO,userTaskLinshiDO));
        if(userTaskDO.getTaskType()==null || userTaskDO.getTaskType()==2) {
            System.out.println("统计个人任务的分值");
            Integer score1 = StrC(userTaskDO.getAvgRead(), userTaskLinshiDO.getAvgRead()) == 1 ?
                    (userTaskDO.getAvgReadScore() == null ? 0 : userTaskDO.getAvgReadScore()) : 0;
            Integer score2 = StrC(userTaskDO.getAvgLookPhone(), userTaskLinshiDO.getAvgLookPhone()) == 1 ?
                    (userTaskDO.getAvgLookPhoneScore() == null ? 0 : userTaskDO.getAvgLookPhoneScore()) : 0;
            Integer score3 = StrC(userTaskDO.getAvgLookTv(), userTaskLinshiDO.getAvgLookTv()) == 1 ?
                    (userTaskDO.getAvgLookScore() == null ? 0 : userTaskDO.getAvgLookScore()) : 0;
            Integer score4 = StrC(userTaskDO.getAvgLight(), userTaskLinshiDO.getAvgLight()) == 1 ?
                    (userTaskDO.getAvgLightScore() == null ? 0 : userTaskDO.getAvgLightScore()) : 0;
            Integer score5 = StrC(userTaskDO.getAvgOut(), userTaskLinshiDO.getAvgOut()) == 1 ?
                    (userTaskDO.getAvgOutScore() == null ? 0 : userTaskDO.getAvgOutScore()) : 0;
            Integer score6 = StrC(userTaskDO.getAvgReadDistance(), userTaskLinshiDO.getAvgReadDistance()) == 1 ?
                    (userTaskDO.getAvgReadDistanceScore() == null ? 0 : userTaskDO.getAvgReadDistanceScore()) : 0;
            Integer score7 = StrC(userTaskDO.getAvgSitTilt(), userTaskLinshiDO.getAvgSitTilt()) == 1 ?
                    (userTaskDO.getAvgSitTiltScore() == null ? 0 : userTaskDO.getAvgSitTiltScore()) : 0;
            Integer score8 = StrC(userTaskDO.getEffectiveUseTime(), userTaskLinshiDO.getEffectiveUseTime()) == 1 ?
                    (userTaskDO.getEffectiveUseTimeScore() == null ? 0 : userTaskDO.getEffectiveUseTimeScore()) : 0;

            return score1 + score2 + score3 + score4 + score5 + score6 + score7 + score8;
        }
        if(userTaskDO.getTaskType()==1){
            System.out.println("统计批量任务的分值");
            if(userTaskLinshiDO.getIffinish()==0) {
                return (userTaskDO.getAvgReadScore() == null ? 0 : userTaskDO.getAvgReadScore()) +
                        (userTaskDO.getAvgLookPhoneScore() == null ? 0 : userTaskDO.getAvgLookPhoneScore()) +
                        (userTaskDO.getAvgLookScore() == null ? 0 : userTaskDO.getAvgLookScore()) +
                        (userTaskDO.getAvgLightScore() == null ? 0 : userTaskDO.getAvgLightScore()) +
                        (userTaskDO.getAvgOutScore() == null ? 0 : userTaskDO.getAvgOutScore()) +
                        (userTaskDO.getAvgReadDistanceScore() == null ? 0 : userTaskDO.getAvgReadDistanceScore()) +
                        (userTaskDO.getAvgSitTiltScore() == null ? 0 : userTaskDO.getAvgSitTiltScore()) +
                        (userTaskDO.getEffectiveUseTimeScore() == null ? 0 : userTaskDO.getEffectiveUseTimeScore());
            }
            else
                return 0;


        }
        return 0;
    }

    public static int StrC(String a,String b){
        if(a!=null && b!=null && a.compareTo(b)<=0)
            return 1;
        else return 0;
    }

    /**
     * 取对数运算
     * @param value
     * @param base
     * @return
     */
     public static double log(double value, double base) {
         return Math.log(value) / Math.log(base);
    }


    /**
     *  数据统计
     * @param useJianhuyiLogDOList
     * @return
     * @throws ParseException
     */
    public static Map<String,Double> countData(Long userId, List<UseJianhuyiLogDO> useJianhuyiLogDOList,String time)
            throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Double avgReadDuration = 0.0; // 平均每次阅读时长
        Double avgReadDistance = 0.0; // 平均阅读距离
        Double avgReadLight = 0.0; // 平均阅读光照
        Double avgLookPhoneDuration = 0.0; // 平均单次看手机时长
        Double avgLookTvComputerDuration = 0.0; // 平均单次看电脑及电视时长
        Double avgSitTilt = 0.0; // 平均旋转角度
        int lookPhoneCount = 0; // 看手机次数
        int lookScreenCount = 0; // 看电脑屏幕的次数
        int count = 0; // 阅读次数
        int avgReadLightCount=0;//光照强度次数
        int avgSitTiltCount=0;//角度次数
        int  avgReadDistanceCount=0;
        for (int i = 0; i < useJianhuyiLogDOList.size(); i++) {
            if(i==0){
                lookPhoneCount++;
                lookScreenCount++;
                count++;
            }
            UseJianhuyiLogDO useJianhuyiLogDO = useJianhuyiLogDOList.get(i);
            /**
             *  光照强度
             */
            if(useJianhuyiLogDO.getReadLight()!=null && useJianhuyiLogDO.getReadLight()>0 && useJianhuyiLogDO.getReadLight()<=412
                    &&  useJianhuyiLogDO.getReadDistance()!=null && useJianhuyiLogDO.getReadDistance()>15
                    && useJianhuyiLogDO.getReadDistance()<=60){
                avgReadLight += 100*ResultUtils.log(useJianhuyiLogDO.getReadLight(),4.3) ;
                avgReadLightCount++;
            }
            /**
             *  坐姿旋转角度
             */
            if(useJianhuyiLogDO.getReadLight()!=null && useJianhuyiLogDO.getReadLight()>0 && useJianhuyiLogDO.getReadLight()<=412
                    &&  useJianhuyiLogDO.getReadDistance()!=null && useJianhuyiLogDO.getReadDistance()>15
                    && useJianhuyiLogDO.getReadDistance()<=60){
                avgSitTilt += useJianhuyiLogDO.getSitTilt();
                avgSitTiltCount++;
            }
            /**
             *  阅读距离
             */
            if(useJianhuyiLogDO.getReadDistance()!=null && useJianhuyiLogDO.getReadDistance()>15
                    && useJianhuyiLogDO.getReadDistance()<=60){
                avgReadDistance += useJianhuyiLogDO.getReadDistance();
                avgReadDistanceCount++;
            }
            /**
             *  看手机时长
             */
            if (useJianhuyiLogDO.getLookPhoneDuration() != null) {
                avgLookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
            }
            /**
             *  看电脑电视时长
             */
            if (useJianhuyiLogDO.getLookTvComputerDuration() != null) {
                avgLookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
            }
            /**
             *  单次阅读
             */
            avgReadDuration += useJianhuyiLogDO.getReadDuration();
            /**
             *  看电脑、手机次数统计
             */
            if (i + 1 < useJianhuyiLogDOList.size()) {
                UseJianhuyiLogDO useJianhuyiLogDO1 = useJianhuyiLogDOList.get(i + 1);
                long minute =
                        (sdf1.parse(useJianhuyiLogDO1.getSaveTime()).getTime()
                                - sdf1.parse(useJianhuyiLogDO.getSaveTime()).getTime()
                                - (long) (useJianhuyiLogDO.getReadDuration() * 60 * 1000))
                                / 1000
                                / 60;
                if (minute >= 3) {
                    if (useJianhuyiLogDO.getLookPhoneDuration() != null
                            ) lookPhoneCount++; // 看手机次数
                    if (useJianhuyiLogDO.getLookTvComputerDuration() != null
                            ) lookScreenCount++; // 看电脑屏幕的次数
                    count++; // 阅读次数

                }
            }
        }
        DecimalFormat df = new DecimalFormat("#.##");
        if (avgReadDuration != null && count > 0) {
            avgReadDuration = Double.parseDouble(df.format(avgReadDuration / count));
        }
        if (lookPhoneCount > 0) {
            avgLookPhoneDuration = Double.parseDouble(df.format(avgLookPhoneDuration / lookPhoneCount));
        }
        if (lookScreenCount > 0) {
            avgLookTvComputerDuration = Double.parseDouble(df.format(avgLookTvComputerDuration / lookScreenCount));
        }
        if(avgReadLightCount>0){
            avgReadLight = Double.parseDouble(df.format(avgReadLight / avgReadLightCount));
        }

        if(avgSitTiltCount>0){
            avgSitTilt = Double.parseDouble(df.format(avgSitTilt / avgSitTiltCount));
        }
        if(avgReadDistanceCount>0){
            avgReadDistance = Double.parseDouble(df.format(avgReadDistance / avgReadDistanceCount));
        }

        /**
         *  获取有效使用时长
         */
        Double effectiveTime=null;
        if(userId!=null && time!=null) {
            System.out.println(useTimeService);
            System.out.println(useTimeService);

            System.out.println(useTimeService);

            Map mappp = useTimeService.getSNCount(userId, time);
            List<UseTimeDO> useTimeDOList = useTimeService.getTodayData(userId, time);


            Optional.ofNullable(mappp)
                    .ifPresent(
                            m -> {
                                jsonObject = (JSONObject) JSONObject.toJSON(m);
                            });
            String num = jsonObject.get("num").toString();
            String sum = jsonObject.get("sum").toString();
            UseJianhuyiLogDO usetime =
                    AvgDataUtil.getSNCount(Integer.parseInt(num), Integer.parseInt(sum), useTimeDOList);

            effectiveTime = usetime.getEffectiveTime();
        }
        Map<String,Double> resultMap = new HashMap<>();
        resultMap.put("avgReadDuration",avgReadDuration);
        resultMap.put("avgLookPhoneDuration",avgLookPhoneDuration);
        resultMap.put("avgLookTvComputerDuration",avgLookTvComputerDuration);
        resultMap.put("avgReadLight",avgReadLight);
        resultMap.put("avgSitTilt",avgSitTilt);
        resultMap.put("avgReadDistance",avgReadDistance);
        resultMap.put("effectiveTime",effectiveTime);
        System.out.println("=================================");
        System.out.println(lookPhoneCount);
        System.out.println(lookScreenCount);
        System.out.println(count);
        System.out.println("=================================");

        return  resultMap;
    }
}
