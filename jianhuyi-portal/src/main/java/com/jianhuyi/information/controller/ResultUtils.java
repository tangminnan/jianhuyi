package com.jianhuyi.information.controller;

import com.jianhuyi.information.domain.UserTaskDO;
import com.jianhuyi.information.domain.UserTaskLinshiDO; /**
 *  监护仪等级判断标准
 */
public class ResultUtils {
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
    public static String totalDegree(){

    }

    /**
     * 计算每日的积分
     * @param userTaskDO
     * @param userTaskLinshiDO
     * @return
     */
    public static int countScore(UserTaskDO userTaskDO, UserTaskLinshiDO userTaskLinshiDO) {
       int score1 = StrC(userTaskDO.getAvgRead(),userTaskLinshiDO.getAvgRead())==1? userTaskDO.getAvgReadScore():0;
       int score2 = StrC(userTaskDO.getAvgLookPhone(),userTaskLinshiDO.getAvgLookPhone())==1? userTaskDO.getAvgLookPhoneScore():0;
       int score3 = StrC(userTaskDO.getAvgLookTv(),userTaskLinshiDO.getAvgLookTv())==1?userTaskDO.getAvgLookScore():0;
       int score4 = StrC(userTaskDO.getAvgLight(),userTaskLinshiDO.getAvgLight())==1?userTaskDO.getAvgLightScore():0;
       int score5 = StrC(userTaskDO.getAvgOut(),userTaskLinshiDO.getAvgOut())==1?userTaskDO.getAvgOutScore():0;
       int score6 = StrC(userTaskDO.getAvgReadDistance(),userTaskLinshiDO.getAvgReadDistance())==1? userTaskDO.getAvgReadDistanceScore():0;
       int score7 = StrC(userTaskDO.getAvgSitTilt(),userTaskLinshiDO.getAvgSitTilt())==1?userTaskDO.getAvgSitTiltScore():0;
       int score8 = StrC(userTaskDO.getEffectiveUseTime(),userTaskLinshiDO.getEffectiveUseTime())==1?userTaskDO.getEffectiveUseTimeScore():0;
       return score1+score2+score3+score4+score5+score6+score7+score8;
    }

    public static int StrC(String a,String b){
        if(a.compareTo(b)<=0)
            return 1;
        else return 0;
    }
}
