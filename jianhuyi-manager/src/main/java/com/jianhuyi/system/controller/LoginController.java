package com.jianhuyi.system.controller;

import com.alibaba.fastjson.JSON;
import com.jianhuyi.common.annotation.Log;
import com.jianhuyi.common.controller.BaseController;
import com.jianhuyi.common.domain.FileDO;
import com.jianhuyi.common.domain.Tree;
import com.jianhuyi.common.service.FileService;
import com.jianhuyi.common.utils.MD5Utils;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.common.utils.ShiroUtils;
import com.jianhuyi.information.domain.EchartsDO;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.system.domain.MenuDO;
import com.jianhuyi.system.service.MenuService;
import com.jianhuyi.users.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;
    @Autowired
    private UserService userService;

    @GetMapping({"/", ""})
    String welcome(Model model) {

        return "redirect:/login";
    }

    @Log("请求访问主页")
    @GetMapping({"/index"})
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        FileDO fileDO = fileService.get(getUser().getPicId());
        if (fileDO != null && fileDO.getUrl() != null) {
            if (fileService.isExist(fileDO.getUrl())) {
                model.addAttribute("picUrl", fileDO.getUrl());
            } else {
                model.addAttribute("picUrl", "/img/photo_s.jpg");
            }
        } else {
            model.addAttribute("picUrl", "/img/photo_s.jpg");
        }
        model.addAttribute("username", getUser().getUsername());
        return "index_v1";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(String username, String password) {

        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return R.ok();
        } catch (AuthenticationException e) {
            return R.error("用户或密码错误");
        }
    }

    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    @GetMapping("/main")
    ModelAndView main() {
        ModelAndView mav = new ModelAndView();
        // 首页总人数
        Integer num = userService.list(null).size();
        // 首页昨日使用人数
        Integer yesterdayNum = userService.selectNum();

        //查询用户最后一次使用的日期和用户id
        List<UseJianhuyiLogDO> useJianhuyiLogDOList = userService.selectLastUse();

        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = new ArrayList<>();
        System.out.println("==========useJianhuyiLogDOList=================="+useJianhuyiLogDOList.get(0).getSaveTime());
        System.out.println("==========useJianhuyiLogDOList=================="+useJianhuyiLogDOList.get(0).getUserId());

        Double avgReadDuration=0.00;
        Double avgOutdoorsDuration =0.00;
        Double avgReadDistance =0.00;
        Double avgReadLight =0.00;
        Double avgLookPhoneDuration =0.00;
        Double avgLookTvComputerDuration =0.00;
        Double avgSitTilt =0.00;
        Double avgUseJianhuyiDuration =0.00;
        Double avgSportDuration =0.00;

        for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOList) {
            UseJianhuyiLogDO useJianhuyiLogDO1 = userService.getUseDay(useJianhuyiLogDO.getSaveTime(),useJianhuyiLogDO.getUserId());

            useJianhuyiLogDOList1.add(useJianhuyiLogDO1);

            if(useJianhuyiLogDO1!=null){
                if(useJianhuyiLogDO1.getReadDuration()!=null){
                    avgReadDuration+= useJianhuyiLogDO1.getReadDuration();
                }
                if(useJianhuyiLogDO1.getOutdoorsDuration()!=null){
                    avgOutdoorsDuration+=useJianhuyiLogDO1.getOutdoorsDuration();
                }
                if(useJianhuyiLogDO1.getReadDistance()!=null){
                    avgReadDistance+=useJianhuyiLogDO1.getReadDistance();
                }
                if(useJianhuyiLogDO1.getReadLight()!=null){
                    avgReadLight+=useJianhuyiLogDO1.getReadLight();
                }

                if(useJianhuyiLogDO1.getReadLight()!=null){
                    avgReadLight+=useJianhuyiLogDO1.getReadLight();
                }
                if(useJianhuyiLogDO1.getLookPhoneDuration()!=null){
                    avgLookPhoneDuration+=useJianhuyiLogDO1.getLookPhoneDuration();
                }
                if(useJianhuyiLogDO1.getLookTvComputerDuration()!=null){
                    avgLookTvComputerDuration+=useJianhuyiLogDO1.getLookTvComputerDuration();
                }
                if(useJianhuyiLogDO1.getSitTilt()!=null){
                    avgSitTilt+=useJianhuyiLogDO1.getSitTilt();
                }
                if(useJianhuyiLogDO1.getUseJianhuyiDuration()!=null){
                    avgUseJianhuyiDuration+=useJianhuyiLogDO1.getUseJianhuyiDuration();
                }
                if(useJianhuyiLogDO1.getSportDuration()!=null){
                    avgSportDuration+=useJianhuyiLogDO1.getSportDuration();
                }
            }



        }

        // 首页各等级数据统计
        mav.addObject("avgReadDuration", avgReadDuration/useJianhuyiLogDOList.size());
        mav.addObject("avgOutdoorsDuration", avgOutdoorsDuration/useJianhuyiLogDOList.size());
        mav.addObject("avgReadDistance", avgReadDistance/useJianhuyiLogDOList.size());
        mav.addObject("avgReadLight", avgReadLight/useJianhuyiLogDOList.size());
        mav.addObject("avgLookPhoneDuration", avgLookPhoneDuration/useJianhuyiLogDOList.size());
        mav.addObject("avgLookTvComputerDuration", avgLookTvComputerDuration/useJianhuyiLogDOList.size());
        mav.addObject("avgSitTilt", avgSitTilt/useJianhuyiLogDOList.size());
        mav.addObject("avgUseJianhuyiDuration", avgUseJianhuyiDuration/useJianhuyiLogDOList.size());
        mav.addObject("avgSportDuration", avgSportDuration/useJianhuyiLogDOList.size());

        mav.addObject("num", num);
        mav.addObject("yesterdayNum", yesterdayNum);
        mav.addObject("useJianhuyiLogDOList", JSON.toJSONString(useJianhuyiLogDOList1));

        mav.setViewName("main");
        return mav;
    }

    @RequestMapping(path = "/getReadDuration")
    @ResponseBody
    List<EchartsDO> getReadDuration(@RequestBody String useJianhuyiLogDOList) throws IOException {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList,UseJianhuyiLogDO.class);

        System.out.println("===========useJianhuyiLogDOList1=================="+useJianhuyiLogDOList1);

        // 首页查询平均单次阅读时长各等级人数
        return userService.selectGrade(useJianhuyiLogDOList1);
    }

    @PostMapping("/getOutdoorsDuration")
    @ResponseBody
    List<EchartsDO> getOutdoorsDuration(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList,UseJianhuyiLogDO.class);
        // 首页户外总时长各等级人数
        return userService.getOutdoorsDuration(useJianhuyiLogDOList1);
    }

    @PostMapping("/getReadDistance")
    @ResponseBody
    List<EchartsDO> getReadDistance(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList,UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getReadDistance(useJianhuyiLogDOList1);
    }

    @PostMapping("/getReadLight")
    @ResponseBody
    List<EchartsDO> getReadLight(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList,UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getReadLight(useJianhuyiLogDOList1);
    }

    @PostMapping("/getLookPhoneDuration")
    @ResponseBody
    List<EchartsDO> getLookPhoneDuration(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList,UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getLookPhoneDuration(useJianhuyiLogDOList1);
    }

    @PostMapping("/getLookTvComputerDuration")
    @ResponseBody
    List<EchartsDO> getLookTvComputerDuration(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList,UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getLookTvComputerDuration(useJianhuyiLogDOList1);
    }

    @PostMapping("/getSitTilt")
    @ResponseBody
    List<EchartsDO> getSitTilt(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList,UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getSitTilt(useJianhuyiLogDOList1);
    }

    @PostMapping("/getUseJianhuyiDuration")
    @ResponseBody
    List<EchartsDO> getUseJianhuyiDuration(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList,UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getUseJianhuyiDuration(useJianhuyiLogDOList1);
    }

    @PostMapping("/getSportDuration")
    @ResponseBody
    List<EchartsDO> getSportDuration(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList,UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getSportDuration(useJianhuyiLogDOList1);
    }

}
