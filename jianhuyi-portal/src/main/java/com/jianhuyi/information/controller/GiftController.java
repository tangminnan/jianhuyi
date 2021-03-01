package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.common.utils.StringUtils;
import com.jianhuyi.information.domain.*;
import com.jianhuyi.information.service.*;
import com.jianhuyi.information.service.impl.UseJianhuyiLogServiceImpl;
import com.jianhuyi.owneruser.domain.OwnerUserDO;
import com.jianhuyi.owneruser.service.OwnerUserService;
import org.activiti.bpmn.model.UserTask;
import org.apache.ibatis.mapping.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Autowired
    private UseJianhuyiLogServiceImpl useJianhuyiLogServiceimpl;

    @Autowired
    private OwnerUserService userService;
    @Autowired
    private GiftPcService giftPcService;
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");

    /**
     * 礼物列表
     */
    @ResponseBody
    @GetMapping("/list")
    public Map<String,Object>  list() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        List<GiftDO> giftDOList = giftService.list(params);
        if(giftDOList.size()>0) {
            resultMap.put("data", giftDOList);
            resultMap.put("msg","获取数据成功");
            resultMap.put("code", 0);
        }else{
            resultMap.put("data", giftDOList);
            resultMap.put("code", -1);
            resultMap.put("msg","礼物列表是空的");
        }
      return resultMap;
    }

    /**
     * 兑换记录
     */
    @ResponseBody
    @GetMapping("/getMyAllGift")
    public Map<String,Object>  getMyAllGift(Long userId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<MyGiftDO> giftDOList = giftService.getMyAllGift(userId);
        if(giftDOList.size()>0) {
            resultMap.put("data", giftDOList);
            resultMap.put("msg","获取数据成功");
            resultMap.put("code", 0);
        }
        else{
            resultMap.put("data", giftDOList);
            resultMap.put("code", -1);
            resultMap.put("msg","您还未兑换过礼品呢");
        }
        return resultMap;
    }

    /**
     * 查询我的积分
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyScore")
    public Map<String,Object> getMyScore(Long userId){
        Integer score = userService.getMyScore(userId);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",0);
        resultMap.put("data",score);
        resultMap.put("msg","获取积分成功");
        return resultMap;
    }

    /**
     * 兑换礼物
     */
    @ResponseBody
    @PostMapping("/duihuan")
    public Map<String,Object> duihuan(Long userId,Long id){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        UserDO userDO = userService.getById(userId);
        if(userDO!=null){
            GiftDO giftDO = giftService.get(id);
            if(giftDO!=null){
                Integer scores = userDO.getScores();
                Integer score = giftDO.getScore();
                if(scores<score){
                    resultMap.put("code",-1);
                    resultMap.put("msg","积分不足，请继续努力...");
                }else{
                    userDO.setScores(scores-score);
                    userService.updateScores(userDO);
                    MyGiftDO myGiftDO = new MyGiftDO();
                    myGiftDO.setGiftId(giftDO.getId());
                    myGiftDO.setCreateTime(new Date());
                    myGiftDO.setUserId(userId);
                    giftService.saveMyGiftDO(myGiftDO);
                    resultMap.put("code",0);
                    resultMap.put("msg","兑换成功...");
                }
            }
        }
        return resultMap;
    }

    /**
     * 任务提交
     * @return
     */
    @ResponseBody
    @PostMapping("/submitTask")
    public Map<String,Object> submitTask(UserTaskDO userTaskDO) throws ParseException {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Long userId = userTaskDO.getUserId();
        Integer type = userTaskDO.getType();
        UserTaskDO userTaskDO1=userTaskService.getCurrentTaskN(userId,type);
        if(userTaskDO1==null){//新增任务
            userTaskDO.setCreateTime(new Date());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(userTaskDO.getCreateTime());
            calendar.add(Calendar.DAY_OF_YEAR,1);
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            userTaskDO.setStartTime(calendar.getTime());
            userTaskDO.setPcorapp("APP");
            Map<String,Object> map =  getLastTaskResult(userId);
            UserTaskLinshiDO userTaskLinshiDO = (UserTaskLinshiDO)map.get("data");
            if(userTaskLinshiDO!=null) {
                userTaskDO.setLastavgLight(userTaskLinshiDO.getAvgLight());
                userTaskDO.setLastavgLookPhone(userTaskLinshiDO.getAvgLookPhone());
                userTaskDO.setLastavgLookTv(userTaskLinshiDO.getAvgLookTv());
                userTaskDO.setLastavgOut(userTaskLinshiDO.getAvgOut());
                userTaskDO.setLastavgRead(userTaskLinshiDO.getAvgRead());
                userTaskDO.setLastavgReadDistance(userTaskLinshiDO.getAvgReadDistance());
                userTaskDO.setLastavgSitTilt(userTaskLinshiDO.getAvgSitTilt());
                userTaskDO.setLasteffectiveUseTime(userTaskLinshiDO.getEffectiveUseTime());
            }
            userTaskService.save(userTaskDO);
            UserDO userDO = userService.getById(userId);
            if(type==1) userDO.setTaskIds(userTaskDO.getId());
            if(type==0) userDO.setTaskId(userTaskDO.getId());
            userService.updateTaskIdInUser(userDO);
            resultMap.put("code",0);
            resultMap.put("msg","操作成功");
            resultMap.put("data",null);
        }else{//不做任何处理
                resultMap.put("code",-1);
                resultMap.put("msg","上次的任务还在进行中...");
                resultMap.put("data",null);
            }

        return resultMap;
    }



    /**
     * 获取上次的评级
     */
    @ResponseBody
    @GetMapping("/getLastTaskResult")
    public Map<String,Object> getLastTaskResult(Long userId) throws ParseException {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Date date  = useJianhuyiLogService.getMaxDate(userId);
        if(date==null) {
            resultMap.put("data", null);
            resultMap.put("code", -1);
            resultMap.put("msg", "没有获取不到数据，请先上传一次呗");
        }else{
            List<UseJianhuyiLogDO> useJianhuyiLogDOS = useJianhuyiLogService.getNearData(userId,date);
            List<UseJianhuyiLogDO> sublist = useJianhuyiLogDOS.stream().filter(a->a.getStatus()!=null && a.getStatus()==1).collect(Collectors.toList());
            Double outdoorsDuration = 0.0;//户外时间累计版本
            Map<String,Double> map = ResultUtils.countData(sublist);
            outdoorsDuration=useJianhuyiLogDOS.stream()
                    .filter(a->a.getStatus()!=null && a.getStatus()==2)
                    .collect(Collectors.summingDouble(UseJianhuyiLogDO::getOutdoorsDuration));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            UseJianhuyiLogDO userJianHuYiYouXiao = useJianhuyiLogService.getUserJianHuYiYouXiao(userId,sdf.format(date));
            Double useJianhuyiDuration = userJianHuYiYouXiao.getUseJianhuyiDuration();
            UserTaskLinshiDO userTaskLinshiDO = new UserTaskLinshiDO();
            userTaskLinshiDO.setAvgRead(ResultUtils.resultAvgReadDuration(map.get("avgReadDuration")));
            userTaskLinshiDO.setAvgLookPhone(ResultUtils.resultAvgLookPhoneDuration(map.get("avgLookPhoneDuration")));
            userTaskLinshiDO.setAvgLookTv(ResultUtils.resultAvgLookTvComputerDuration(map.get("avgLookTvComputerDuration")));
            userTaskLinshiDO.setAvgReadDistance(ResultUtils.resultAvgReadDistance(map.get("avgReadDistance")));
            userTaskLinshiDO.setAvgLight(ResultUtils.resultAvgReadLight(map.get("avgReadLight")));
            userTaskLinshiDO.setAvgSitTilt(ResultUtils.resultAvgSitTilt(map.get("avgSitTilt")));
            userTaskLinshiDO.setAvgOut(ResultUtils.resultOutdoorsDuration(outdoorsDuration));
            userTaskLinshiDO.setEffectiveUseTime(ResultUtils.resultUseJianhuyiDuration(useJianhuyiDuration));
            resultMap.put("data", userTaskLinshiDO);
            resultMap.put("code", 0);
            resultMap.put("msg", "获取成功");

            System.out.println(date);
            System.out.println("平均每次阅读时长 "+map.get("avgReadDuration"));
            System.out.println("使用时长 "+useJianhuyiDuration);
            System.out.println("户外时间累计 "+outdoorsDuration);
            System.out.println("平均阅读距离 "+map.get("avgReadDistance"));
            System.out.println("平均阅读光照 "+map.get("avgReadLight"));
            System.out.println("平均单次看手机时长 "+map.get("avgLookPhoneDuration"));
            System.out.println("平均单次看电脑及电视时长 "+map.get("avgLookTvComputerDuration"));
            System.out.println("平均旋转角度 "+map.get("avgSitTilt"));
        }

        return resultMap;
    }

    /**
     * 根据姓名搜索孩子
     */
    @ResponseBody
    @GetMapping("/getChild")
    public Map<String,Object> getChild(String name){
        List<UserDO> list=userService.getStudent(name);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        if(list.size()>0) {
            resultMap.put("code", 0);
            resultMap.put("data", list);
            resultMap.put("msg","获取数据成功");
        }else{
            resultMap.put("code", -1);
            resultMap.put("data", list);
            resultMap.put("msg","没有搜索到");
        }
        return resultMap;
    }

    /**
     * 根据登陆手机号搜索孩子
     */
    @ResponseBody
    @GetMapping("/getChildByPhone")
    public Map<String,Object> getChildByPhone(String phone){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        UserDO userDO = userService.getByPhone(phone);
        if(userDO!=null){
            resultMap.put("code",0);
            resultMap.put("data",userDO);
            resultMap.put("msg","获取数据成功");
        }else{
            resultMap.put("code",-1);
            resultMap.put("data",null);
            resultMap.put("msg","没有找到孩子的信息，可能孩子信息后台没有添加或输入的手机号有误...");
        }

        return resultMap;
    }

    /**
     *  根据任务id查询任务记录
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyTaskProcess")
    public Map<String,Object> getMyTaskProcess(Long TaskId){
        UserTaskDO userTaskDO = userTaskService.get(TaskId);
        Map<String,Object> resultMap = new HashMap<>();
        if(userTaskDO!=null){
            long d   =  (new Date().getTime()-userTaskDO.getStartTime().getTime())/1000/60/60/24;
            userTaskDO.setFinishDay(d);//已完成天数
            userTaskDO.setUnfinishedDay(userTaskDO.getTaskTime()-d);//未完成天数
            resultMap.put("code",0);
            resultMap.put("data",userTaskDO);
            resultMap.put("msg","获取数据成功");
        }else{
            resultMap.put("code",-1);
            resultMap.put("data",null);
            resultMap.put("msg","数据获取失败");
        }
        return resultMap;
    }

    /**
     * 获取任务记录
     */
    @ResponseBody
    @GetMapping("/getTask")
    public Map<String,Object> getTask(Long userId,Integer  type){
        List<UserTaskDO> list = userTaskService.getAllReadyFinishedTask(userId,type);//查询已经完成的任务
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if(list.size()>0) {
            resultMap.put("code", 0);
            resultMap.put("msg","获取数据成功");
            resultMap.put("data", list);
        }else{
            resultMap.put("code", -1);
            resultMap.put("msg","无数据");
            resultMap.put("data", list);
        }
        return resultMap;
    }

    /**
     * 获取当前正在进行的任务接口
     */
    @ResponseBody
    @GetMapping("/getCurrentTask")
    public Map<String,Object> getCurrentTask(Long userId,Integer type){
        UserTaskDO userTaskDO = userTaskService.getCurrentTask(userId,type);
        Map<String,Object> resultMap = new HashMap<>();
        if(userTaskDO!=null){
            long d   =  (new Date().getTime()-userTaskDO.getStartTime().getTime())/1000/60/60/24;
            userTaskDO.setFinishDay(d);//已完成天数
            userTaskDO.setUnfinishedDay(userTaskDO.getTaskTime()-d);//未完成天数
            resultMap.put("code",0);
            resultMap.put("data",userTaskDO);
            resultMap.put("msg","获取数据成功");
        }else{
            resultMap.put("code",-1);
            resultMap.put("data",null);
            resultMap.put("msg","当前没有正在进行的任务");
        }
        return resultMap;
    }

    /**
     * 查看任务进度
     * @return
     */
    @ResponseBody
    @GetMapping("/getTaskDetail")
    public Map<String,Object> getTaskDetail(Long taskId){
        UserTaskDO userTask=userTaskService.get(taskId);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        if(userTask==null){
            resultMap.put("code",-1);
            resultMap.put("data",null);
            resultMap.put("msg","没有找到任务，输入的参数可能有误...");
            return resultMap;
        }
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("taskId",taskId);
        List<UserTaskLinshiDO> userTaskLinshiDOList = userTaskLinshiService.list(paramsMap);
        Map<String,UserTaskLinshiDO> utmap = new HashMap<String,UserTaskLinshiDO>();
        userTaskLinshiDOList.forEach(a->{
            a.setDay(simpleDateFormat.format(a.getCreateTime()));
            utmap.put(simpleDateFormat.format(a.getCreateTime()),a);
        });

        Map<String,Object> map = new HashMap<String,Object>();
        fillMapDays(map,userTask);
        for(Map.Entry<String,UserTaskLinshiDO> entry: utmap.entrySet()){
            map.put(entry.getKey(),entry.getValue());
        }
       /* resultMap.put("renwu",map.size());//已完成任务天数
        resultMap.put("countGrade",userTask.getCountGrade());//平均等级
        resultMap.put("totaluser",userTask.getTotaluser());//有效使用时长*/
        resultMap.put("data", new ArrayList(map.values()));
        resultMap.put("code",0);
        resultMap.put("msg","获取数据成功");
        return  resultMap;
    }

    /**
     * 查看当日任务详情
     */
    @ResponseBody
    @GetMapping("/getDayDetail")
    public Map<String,Object> getDayDetail(Long id){
        UserTaskLinshiDO userTaskLinshiDO = userTaskLinshiService.get(id);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if(userTaskLinshiDO==null){
            resultMap.put("code",-1);
            resultMap.put("msg","当天没有数据");
            resultMap.put("data",null);
        }else{
            resultMap.put("code",0);
            resultMap.put("data",userTaskLinshiDO);
            resultMap.put("msg","获取数据成功");
        }
        return resultMap;
    }

    private void fillMapDays(Map<String, Object> map, UserTaskDO userTask) {
        Date startDate = userTask.getStartTime();
        Calendar calendar  = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR,userTask.getTaskTime());
        Date endDate = calendar.getTime();
        if(endDate.compareTo(new Date())>0)
            endDate=new Date();
        while(startDate.compareTo(endDate)<=0){
            UserTaskLinshiDO userTaskLinshiDO = new UserTaskLinshiDO();
            userTaskLinshiDO.setDay(simpleDateFormat.format(startDate));
            map.put(simpleDateFormat.format(startDate),userTaskLinshiDO);
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            startDate=calendar.getTime();
        }
    }


    /**
     * pc端礼物列表（老师端，礼物任务自定义）
     * */
//    @ResponseBody
//    @GetMapping("/listPc")
//    public String listPc(@RequestParam("callback") String callback) {
//        Map<String, Object> data = new HashMap<>();
//        Map<String, Object> params = new HashMap<>();
//        params.put("type", 2);
//        List<GiftDO> giftDOList = giftService.list(params);
//        data.put("data", giftDOList);
//        return callback+"("+JSONObject.toJSONString(R.ok(data))+")";
//    }

    /**
     * pc端礼物列表（家长端）
     * */
//    @ResponseBody
//    @GetMapping("/listPcJiazhang")
//    public String listPcJiazhang(@RequestParam("callback") String callback) {
//        Map<String, Object> data = new HashMap<>();
//        Map<String, Object> params = new HashMap<>();
//        params.put("type", 2);
//        List<GiftDO> giftDOList = giftService.list(params);
//        if(giftDOList.size()>0){
//            for (GiftDO giftDO : giftDOList) {
//                if(giftDO!=null){
//                    Map<String,Object> params11 = new HashMap<>();
//                    params11.put("giftId",giftDO.getId());
//                    giftDO.setGiftPc(giftPcService.list(params11).get(0));
//
//
//                }
//            }
//        }
//        data.put("data", giftDOList);
//        return callback+"("+JSONObject.toJSONString(R.ok(data))+")";
//    }
    /**
     * 礼物详情
     */
   /* @ResponseBody
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
    }*/


    /**
     * 老师任务下发给班级
     */
    @ResponseBody
    @GetMapping("/customTaskPc")
    public String customTask(@RequestParam("callback") String callback,UserTaskDO userTaskDO) {
        List<String> idstrinss = JSONArray.parseArray(userTaskDO.getIdCards(), String.class)
                .stream().distinct().collect(Collectors.toList());
        for (String idCard : idstrinss) {
            OwnerUserDO userDO=null;
            UserTaskDO userTaskDO1=null;
            List<OwnerUserDO> list = userService.getUserByIdCard(idCard.trim());
            if(list.size()==0){
                userDO = new OwnerUserDO();
                userDO.setIdentityCard(idCard.trim());
                userService.save(userDO);
            }else{
                userDO=list.get(0);
                userTaskDO1=userTaskService.getCurrentTaskN(userDO.getId(),1);//1= PC端老师 或App端医生下达的任务
            }
            if(userTaskDO1==null){
                userTaskDO.setUserId(userDO.getId());
                userTaskDO.setType(1);
                userTaskDO.setCreateTime(new Date());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(userTaskDO.getCreateTime());
                calendar.add(Calendar.DAY_OF_YEAR,1);
                calendar.set(Calendar.HOUR_OF_DAY,0);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                userTaskDO.setStartTime(calendar.getTime());//任务的开始时间 为下达 任务的第二天0点
                userTaskDO.setPcorapp("PC");
                userTaskDO.setAvgOutScore(countScore(userTaskDO.getAvgOut()));
                userTaskDO.setAvgLightScore(countScore(userTaskDO.getAvgLight()));
                userTaskDO.setAvgLookPhoneScore(countScore(userTaskDO.getAvgLookPhone()));
                userTaskDO.setAvgLookScore(countScore(userTaskDO.getAvgLookTv()) );
                userTaskDO.setAvgSitTiltScore(countScore(userTaskDO.getAvgSitTilt()));
                userTaskDO.setAvgReadDistanceScore(countScore(userTaskDO.getAvgReadDistance()));
                userTaskDO.setAvgReadScore(countScore(userTaskDO.getAvgRead()));
                userTaskDO.setEffectiveUseTimeScore(countScore(userTaskDO.getEffectiveUseTime()));
                int result = userTaskService.save(userTaskDO);
                if(result>0) {
                    userDO.setTaskIds(userTaskDO.getId());
                    userService.update(userDO);
                }
            }
        }

            return callback+"("+JSONObject.toJSONString(R.ok())+")";

    }

    /**
     * PC
     * @param
     */
    private int countScore(String pcX) {
        int score=0;

        if(StringUtils.isNotBlank(pcX)) {
            switch (pcX) {
                case "5":
                    score = 2;
                    break;
                case "4":
                    score = 1;
                    break;
                case "2":
                    score = 0;
                    break;
                case "1":
                    score = 0;
                    break;
            }
        }
        return score;
    }

    /**
     * 家长下发给学生
     */
    @ResponseBody
    @GetMapping("/addTaskPcByJz")
    public String addTaskPcByJz(@RequestParam("callback") String callback,String idCard,UserTaskDO userTaskDO) {
        OwnerUserDO userDO = null;
        UserTaskDO userTaskDO1 = null;
        List<OwnerUserDO> list = userService.getUserByIdCard(idCard.trim());
        if (list.size() == 0) {
            userDO = new OwnerUserDO();
            userDO.setIdentityCard(idCard.trim());
            userService.save(userDO);
        } else {
            userDO = list.get(0);
            userTaskDO1 = userTaskService.getCurrentTaskN(userDO.getId(), 0);//1= PC端老师 或App端医生下达的任务
        }

        if (userTaskDO1 == null) {
            userTaskDO.setUserId(userDO.getId());
            userTaskDO.setType(0);
            userTaskDO.setCreateTime(new Date());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(userTaskDO.getCreateTime());
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            userTaskDO.setStartTime(calendar.getTime());//任务的开始时间 为下达 任务的第二天0点
            userTaskDO.setPcorapp("PC");
            userTaskDO.setAvgOutScore(countScore(userTaskDO.getAvgOut()));
            userTaskDO.setAvgLightScore(countScore(userTaskDO.getAvgLight()));
            userTaskDO.setAvgLookPhoneScore(countScore(userTaskDO.getAvgLookPhone()));
            userTaskDO.setAvgLookScore(countScore(userTaskDO.getAvgLookTv()));
            userTaskDO.setAvgSitTiltScore(countScore(userTaskDO.getAvgSitTilt()));
            userTaskDO.setAvgReadDistanceScore(countScore(userTaskDO.getAvgReadDistance()));
            userTaskDO.setAvgReadScore(countScore(userTaskDO.getAvgRead()));
            userTaskDO.setEffectiveUseTimeScore(countScore(userTaskDO.getEffectiveUseTime()));
            int result = userTaskService.save(userTaskDO);
            if (result > 0) {
                userDO.setTaskId(userTaskDO.getId());
                userService.update(userDO);
            }
        }
        return callback + "(" + JSONObject.toJSONString(R.ok()) + ")";

    }

    /**
     *   查看PC端任务详情
     * @return
     */
    @ResponseBody
    @GetMapping("/getTaskFromPC")
    public String getTaskFromPC(String idCard,@RequestParam("callback") String callback,Integer type){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<OwnerUserDO> list = userService.getUserByIdCard(idCard);
        if(list.size()==0) {
            resultMap.put("code","-1");
            resultMap.put("msg","没有找到孩子的信息");
        }else {
            OwnerUserDO ownerUserDO = list.get(0);
            UserTaskDO userTaskDO = userTaskService.getCurrentTask(ownerUserDO.getId(), type);
            if (userTaskDO == null) {
                resultMap.put("code", -1);
                resultMap.put("msg", type == 1 ? "当前没有老师或医生下发的任务" : "当前没有家长下发的任务");
            } else {
                long d = (new Date().getTime() - userTaskDO.getStartTime().getTime()) / 1000 / 60 / 60 / 24;
                userTaskDO.setFinishDay(d);//已完成天数
                userTaskDO.setUnfinishedDay(userTaskDO.getTaskTime() - d);//未完成天数
                UseJianhuyiLogDO userJianHuYiYouXiao =
                        useJianhuyiLogService.getUserJianHuYiYouXiaoAll(ownerUserDO.getId(), userTaskDO.getStartTime(), new Date());
                if (userJianHuYiYouXiao != null) {
                    userTaskDO.setYouxiaotime(userJianHuYiYouXiao.getUseJianhuyiDuration());
                }
                resultMap.put("code", 0);
                resultMap.put("msg","获取数据成功");
                resultMap.put("data", userTaskDO);
            }
        }
        return callback + "(" + JSONObject.toJSONString(R.ok(resultMap)) + ")";
    }

    /**
     * pc端任务详情
     * @return
     */
    @ResponseBody
    @GetMapping("/getTaskDetailPC")
    public String getTaskDetailPC(Long taskId,@RequestParam("callback") String callback){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        UserTaskDO userTask=userTaskService.get(taskId);
        if(userTask==null){
            resultMap.put("code",-1);
            resultMap.put("data",null);
            resultMap.put("msg","没有找到任务，输入的参数可能有误...");
        }else {
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("taskId", taskId);
            List<UserTaskLinshiDO> userTaskLinshiDOList = userTaskLinshiService.list(paramsMap);
            Map<String, UserTaskLinshiDO> utmap = new HashMap<String, UserTaskLinshiDO>();
            userTaskLinshiDOList.forEach(a -> {
                a.setDay(simpleDateFormat.format(a.getCreateTime()));
                utmap.put(simpleDateFormat.format(a.getCreateTime()), a);
            });
            Map<String, Object> map = new HashMap<String, Object>();
            fillMapDays(map, userTask);
            for (Map.Entry<String, UserTaskLinshiDO> entry : utmap.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            resultMap.put("data", new ArrayList(map.values()));
            resultMap.put("code", 0);
            resultMap.put("msg", "获取数据成功");
        }
        return callback + "(" + JSONObject.toJSONString(R.ok(resultMap)) + ")";
    }


    /**
     * 查看当日任务详情
     */
    @ResponseBody
    @GetMapping("/getDayDetailPC")
    public String getDayDetailPC(Long id,Long taskId,  @RequestParam("callback") String callback){
        UserTaskDO userTaskDO = userTaskService.get(taskId);
        UserTaskLinshiDO userTaskLinshiDO = userTaskLinshiService.get(id);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if(userTaskLinshiDO==null){
            resultMap.put("code",-1);
            resultMap.put("msg","当天没有数据");
            resultMap.put("data",null);
        }else{
            resultMap.put("code",0);
            resultMap.put("data",userTaskLinshiDO);
            resultMap.put("msg","获取数据成功");
        }
        resultMap.put("data2",userTaskDO);
        return callback + "(" + JSONObject.toJSONString(R.ok(resultMap)) + ")";
    }


    /**
     * 家长自定义下发给学生
     */
    /*@ResponseBody
    @GetMapping("/customTaskPcByJz")
    public String customTaskPcByJz(@RequestParam("callback") String callback,UserTaskDO userTaskDO) {
        Map<String,Object> map = new HashMap<>();
        OwnerUserDO userDO = userService.getUserByIdCard(userTaskDO.getIdCard());
        if(userDO!=null){
            Map<String, Object> params = new HashMap<>();
            //查询用户 未完成的任务
            params.put("userId", userDO.getId());
            params.put("finishStatus", "2");
            //个人任务
            params.put("taskType",2);
            List<UserTaskDO> userTaskDOList = userTaskService.list(params);
            if(userTaskDOList.size()>0){
                return callback+"("+JSONObject.toJSONString(R.error("有一个未结束任务，同时只能存在一个任务哦！"))+")";
            }else{
                userTaskDO.setUserId(userDO.getId());
                userTaskDO.setTaskType(2);
                userTaskDO.setFinishStatus("2");
                userTaskDO.setCreateTime(new Date());
                userTaskDO.setFinishDay(0);
                userTaskDO.setUnfinishedDay(0);
                userTaskDO.setType(1);

                int result = userTaskService.save(userTaskDO);
                if(result>0)
                    return callback+"("+JSONObject.toJSONString(R.ok("保存成功！"))+")";
                else
                    return callback+"("+JSONObject.toJSONString(R.error("保存失败，请重试"))+")";
            }
        }else{
            OwnerUserDO userDO1 = new OwnerUserDO();
            userDO1.setIdentityCard(userTaskDO.getIdCard());
            if(userService.save(userDO1)>0){
                userTaskDO.setUserId(userDO1.getId());
                userTaskDO.setTaskType(2);
                userTaskDO.setFinishStatus("2");
                userTaskDO.setCreateTime(new Date());
                userTaskDO.setFinishDay(0);
                userTaskDO.setUnfinishedDay(0);
                userTaskDO.setType(1);
                int result = userTaskService.save(userTaskDO);
                if(result>0)
                    return callback+"("+JSONObject.toJSONString(R.ok("保存成功！"))+")";
                else
                    return callback+"("+JSONObject.toJSONString(R.error("保存失败，请重试"))+")";
            }else{
                return callback+"("+JSONObject.toJSONString(R.error("用户不存在且保存失败，请重试"))+")";
            }
        }

    }
*/

    /**
     * pc端查询我的任务
     */
//    @ResponseBody
//    @GetMapping("/myGiftPc")
//    public String myGiftPc(@RequestParam("callback") String callback,String idCard,int taskType) {
//        Map<String, Object> data = new HashMap<>();
//        Map<String, Object> result = new HashMap<>();
//        Map<String, Object> params = new HashMap<>();
//        Map<String, Object> params11 = new HashMap<>();
//        Map<String, Object> params2 = new HashMap<>();
//        List<RecordDO> recordDOList = new LinkedList<RecordDO>();
//        if(idCard!=null){
//            List<OwnerUserDO> list = userService.getUserByIdCard(idCard);
//            if(list.size()>0)
//            params.put("userId",list.get(0).getId());
//            params.put("taskType",taskType);
//            List<UserTaskDO> taskDOList =  userTaskService.list(params);
//            if(taskDOList!=null && taskDOList.size()==1){
//                if(taskDOList.get(0).getGiftId()!=null){
//                    params11.put("giftId",taskDOList.get(0).getGiftId());
//                    GiftDO giftDO = giftService.get(taskDOList.get(0).getGiftId());
//
//                    taskDOList.get(0).setGiftDO(giftDO);
//                }
//                result.put("taskIng",taskDOList.get(0));
//
//                recordDOList = (List<RecordDO>)getRecordList(taskDOList.get(0).getId(),userDO.getId()).get("recordDOList");
//
//                //返回平均等级
//                UseJianhuyiLogDO useJianhuyiLogDO = (UseJianhuyiLogDO) getRecordList(taskDOList.get(0).getId(),userDO.getId()).get("allUseJianhuyiLogDO");
//                result.put("overallMerit", getGrade(useJianhuyiLogDO));
//                result.put("allUseJianhuyi", getRecordList(taskDOList.get(0).getId(),userDO.getId()).get("allUseJianhuyi"));
//                result.put("recordDOList",recordDOList);
//            }
//        }else{
//            return callback+"("+JSONObject.toJSONString(R.error("学生身份证号为空"))+")";
//        }
//
//        data.put("data",result);
//
//        return callback+"("+JSONObject.toJSONString(R.ok(data))+")";
//    }

    /**
     * 自定义任务(保存和修改)
     */
    /*@ResponseBody
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
*/
    /**
     * 提交任务（我要挑战）
     */
    /*@ResponseBody
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
    }*/


    /**
     * 我的礼品
     */
   /* @ResponseBody
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

            if (userTaskDO.getFinishDay() >= userTaskDO.getTaskTime()) {
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
    }*/

    //获取每项结果数组
//    String[] getArray(Map<String, Object> resultData, String fieldName) {
//        String avgLookTvComputerDuration = resultData.get(fieldName).toString();
//        avgLookTvComputerDuration = avgLookTvComputerDuration.replace("[", "");
//        avgLookTvComputerDuration = avgLookTvComputerDuration.replace("]", "");
//        avgLookTvComputerDuration = avgLookTvComputerDuration.replace(" ", "");
//
//        String[] attr = avgLookTvComputerDuration.split(",");
//        return attr;
//    }


    /**
     * 任务进度
     */
    /*@GetMapping("/taskProgress")
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
    }*/

    //获取进度集合
    /*Map<String, Object> getRecordList(Long taskId, Long userId) {
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
        Double allUseJianhuyi = 0.0;

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
                        useJianhuyiLogDO.setUseJianhuyiDuration(Double.parseDouble(getArray(resultData, "avgUseJianhuyiDuration")[j]));
                        allUseJianhuyi += Double.parseDouble(getArray(resultData, "avgUseJianhuyiDuration")[j]);
                        recordDO.setUseJianhuyiTime(Double.parseDouble(getArray(resultData, "avgUseJianhuyiDuration")[i]));
                    } else {
                        useJianhuyiLogDO.setUseJianhuyiDuration(0.0);
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


            //计算每天的评价 5=优，4=良，3=不太好，2=差，1=极差
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
*/
    //已完成天数计算 type=1 自定义
//    Integer finishDay(Long taskId, List<UseJianhuyiLogDO> useJianhuyiLogDOList) {
//        Integer day = 0;
//        UserTaskDO userTaskDO = userTaskService.get(taskId);
//        for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOList) {
//
//
//        }
//
//        return day;
//    }

//    boolean getMinTask(Long taskId, UseJianhuyiLogDO useJianhuyiLogDO) {
//        UserTaskDO userTaskDO = userTaskService.get(taskId);
//        boolean result = false;
//        boolean getAvgRead = false;
//        boolean getAvgOut = false;
//        boolean getAvgReadDistance = false;
//        boolean getAvgLookPhone = false;
//        boolean getAvgLookTv = false;
//
//        /**
//         * 判断阅读时长是否达标
//         * */
//        //极差
//        if (userTaskDO.getAvgRead().equals("1")) {
//            if (useJianhuyiLogDO.getReadDuration() > 90) {
//                getAvgRead = true;
//            }
//        } //差
//        else if (userTaskDO.getAvgRead().equals("2")) {
//            if (useJianhuyiLogDO.getReadDuration() > 40 && useJianhuyiLogDO.getReadDuration() <= 90) {
//                getAvgRead = true;
//            }
//        }//良好
//        else if (userTaskDO.getAvgRead().equals("4")) {
//            if (useJianhuyiLogDO.getReadDuration() > 20 && useJianhuyiLogDO.getReadDuration() <= 40) {
//                getAvgRead = true;
//            }
//        }//优
//        else if (userTaskDO.getAvgRead().equals("5")) {
//            if (useJianhuyiLogDO.getReadDuration() <= 20) {
//                getAvgRead = true;
//            }
//        }
//
//
//        /**
//         * 判断户外时长是否达标
//         * */
//        //极差
//        if (userTaskDO.getAvgOut().equals("1")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
//                getAvgOut = true;
//            }
//        } //差
//        else if (userTaskDO.getAvgOut().equals("2")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1) {
//                getAvgOut = true;
//            }
//        }//良好
//        else if (userTaskDO.getAvgOut().equals("4")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) {
//                getAvgOut = true;
//            }
//        }//优
//        else if (userTaskDO.getAvgOut().equals("5")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 2) {
//                getAvgOut = true;
//            }
//        }
//
//
//        /**
//         * 判断阅读距离是否达标
//         * */
//        //极差
//        if (userTaskDO.getAvgReadDistance().equals("1")) {
//            if (useJianhuyiLogDO.getReadDistance() < 20) {
//                getAvgReadDistance = true;
//            }
//        } //差
//        else if (userTaskDO.getAvgReadDistance().equals("2")) {
//            if (useJianhuyiLogDO.getReadDistance() > 20 && useJianhuyiLogDO.getReadDistance() <= 30) {
//                getAvgReadDistance = true;
//            }
//        }//良好
//        else if (userTaskDO.getAvgReadDistance().equals("4")) {
//            if (useJianhuyiLogDO.getReadDistance() >= 30 && useJianhuyiLogDO.getReadDistance() < 33) {
//                getAvgReadDistance = true;
//            }
//        }//优
//        else if (userTaskDO.getAvgReadDistance().equals("5")) {
//            if (useJianhuyiLogDO.getReadDistance() >= 33) {
//                getAvgReadDistance = true;
//            }
//        }
//
//
//        /**
//         * 判断看手机是否达标
//         * */
//        //极差
//        if (userTaskDO.getAvgLookPhone().equals("1")) {
//            if (useJianhuyiLogDO.getLookPhoneDuration() > 40) {
//                getAvgLookPhone = true;
//            }
//        } //差
//        else if (userTaskDO.getAvgLookPhone().equals("2")) {
//            if (useJianhuyiLogDO.getLookPhoneDuration() > 20 && useJianhuyiLogDO.getLookPhoneDuration() <= 40) {
//                getAvgLookPhone = true;
//            }
//        }//良好
//        else if (userTaskDO.getAvgLookPhone().equals("4")) {
//            if (useJianhuyiLogDO.getLookPhoneDuration() > 10 && useJianhuyiLogDO.getLookPhoneDuration() <= 20) {
//                getAvgLookPhone = true;
//            }
//        }//优
//        else if (userTaskDO.getAvgLookPhone().equals("5")) {
//            if (useJianhuyiLogDO.getLookPhoneDuration() <= 10) {
//                getAvgLookPhone = true;
//            }
//        }
//
//
//        /**
//         * 判断户外时长是否达标
//         * */
//        //极差
//        if (userTaskDO.getAvgLookTv().equals("1")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
//                getAvgOut = true;
//            }
//        } //差
//        else if (userTaskDO.getAvgOut().equals("2")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1) {
//                getAvgOut = true;
//            }
//        }//良好
//        else if (userTaskDO.getAvgOut().equals("4")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) {
//                getAvgOut = true;
//            }
//        }//优
//        else if (userTaskDO.getAvgOut().equals("5")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 2) {
//                getAvgOut = true;
//            }
//        }
//
//
//        /**
//         * 判断户外时长是否达标
//         * */
//        //极差
//        if (userTaskDO.getAvgOut().equals("1")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
//                getAvgOut = true;
//            }
//        } //差
//        else if (userTaskDO.getAvgOut().equals("2")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1) {
//                getAvgOut = true;
//            }
//        }//良好
//        else if (userTaskDO.getAvgOut().equals("4")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) {
//                getAvgOut = true;
//            }
//        }//优
//        else if (userTaskDO.getAvgOut().equals("5")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 2) {
//                getAvgOut = true;
//            }
//        }
//
//
//        /**
//         * 判断户外时长是否达标
//         * */
//        //极差
//        if (userTaskDO.getAvgOut().equals("1")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
//                getAvgOut = true;
//            }
//        } //差
//        else if (userTaskDO.getAvgOut().equals("2")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1) {
//                getAvgOut = true;
//            }
//        }//良好
//        else if (userTaskDO.getAvgOut().equals("4")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) {
//                getAvgOut = true;
//            }
//        }//优
//        else if (userTaskDO.getAvgOut().equals("5")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 2) {
//                getAvgOut = true;
//            }
//        }
//
//
//        /**
//         * 判断户外时长是否达标
//         * */
//        //极差
//        if (userTaskDO.getAvgOut().equals("1")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() < 0.5) {
//                getAvgOut = true;
//            }
//        } //差
//        else if (userTaskDO.getAvgOut().equals("2")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 0.5 && useJianhuyiLogDO.getOutdoorsDuration() < 1) {
//                getAvgOut = true;
//            }
//        }//良好
//        else if (userTaskDO.getAvgOut().equals("4")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 1 && useJianhuyiLogDO.getOutdoorsDuration() < 2) {
//                getAvgOut = true;
//            }
//        }//优
//        else if (userTaskDO.getAvgOut().equals("5")) {
//            if (useJianhuyiLogDO.getOutdoorsDuration() >= 2) {
//                getAvgOut = true;
//            }
//        }
//        return result;
 //  }


    //获取任务记录的日期和完成度

}
