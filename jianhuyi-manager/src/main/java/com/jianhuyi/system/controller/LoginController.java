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
import com.jianhuyi.information.dao.UseJianhuyiLogDao;
import com.jianhuyi.information.domain.EchartsDO;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;
    @Autowired
    private UserService userService;

    @Autowired
    private UseJianhuyiLogService useJianhuyiLogService;
    @Autowired
    private UseJianhuyiLogDao useJianhuyiLogDao;

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
    ModelAndView main() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ModelAndView mav = new ModelAndView();
        // 首页总人数
        Integer num = userService.list(null).size();
        // 首页昨日使用人数
        Integer yesterdayNum = userService.selectNum();

        //查询用户最后一次使用的日期和用户id
        List<UseJianhuyiLogDO> useJianhuyiLogDOList = userService.selectLastUse();

        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        //查询所有数据的人和日期
        LinkedList<UseJianhuyiLogDO> useJianhuyiLogDOLinkedList = useJianhuyiLogDao.selectPersonAndDate(params);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(sdf.parse(useJianhuyiLogDOLinkedList.get(0).getSaveTime()));
        calendar.add(calendar.DATE, +1);

        params.put("startTime", useJianhuyiLogDOLinkedList.get(0).getSaveTime());
        params.put("endTime", sdf.format(calendar.getTime()));

        List<UseJianhuyiLogDO> useJianhuyiLogDOS = useJianhuyiLogService.getData(params);

        Double avgReadDuration = 0.00;
        Double avgOutdoorsDuration = 0.00;
        Double avgReadDistance = 0.00;
        Double avgReadLight = 0.00;
        Double avgLookPhoneDuration = 0.00;
        Double avgLookTvComputerDuration = 0.00;
        Double avgSitTilt = 0.00;
        Double avgUseJianhuyiDuration = 0.00;

        for (UseJianhuyiLogDO useJianhuyiLogDO : useJianhuyiLogDOS) {
            if (useJianhuyiLogDO.getReadDuration() != null)
                avgReadDuration += useJianhuyiLogDO.getReadDuration();
            if (useJianhuyiLogDO.getOutdoorsDuration() != null)
                avgOutdoorsDuration += useJianhuyiLogDO.getOutdoorsDuration();
            if (useJianhuyiLogDO.getReadDistance() != null)
                avgReadDistance += useJianhuyiLogDO.getReadDistance();
            if (useJianhuyiLogDO.getLookPhoneDuration() != null)
                avgLookPhoneDuration += useJianhuyiLogDO.getLookPhoneDuration();
            if (useJianhuyiLogDO.getLookTvComputerDuration() != null)
                avgLookTvComputerDuration += useJianhuyiLogDO.getLookTvComputerDuration();
            if (useJianhuyiLogDO.getReadLight() != null)
                avgReadLight += useJianhuyiLogDO.getReadLight();
            if (useJianhuyiLogDO.getSitTilt() != null)
                avgSitTilt += useJianhuyiLogDO.getSitTilt();
            if (useJianhuyiLogDO.getUserDurtion() != null)
                avgUseJianhuyiDuration += useJianhuyiLogDO.getUserDurtion();
        }

        // 首页各等级数据统计
        mav.addObject("avgReadDuration", avgReadDuration / useJianhuyiLogDOS.size());
        mav.addObject("avgOutdoorsDuration", avgOutdoorsDuration / useJianhuyiLogDOS.size() / 60);
        mav.addObject("avgReadDistance", avgReadDistance / useJianhuyiLogDOS.size());
        mav.addObject("avgReadLight", avgReadLight / useJianhuyiLogDOS.size());
        mav.addObject("avgLookPhoneDuration", avgLookPhoneDuration / useJianhuyiLogDOS.size());
        mav.addObject("avgLookTvComputerDuration", avgLookTvComputerDuration / useJianhuyiLogDOS.size());
        mav.addObject("avgSitTilt", avgSitTilt / useJianhuyiLogDOS.size());
        mav.addObject("avgUseJianhuyiDuration", avgUseJianhuyiDuration / useJianhuyiLogDOS.size());


        mav.addObject("num", num);
        mav.addObject("yesterdayNum", yesterdayNum);
        mav.addObject("useJianhuyiLogDOList", JSON.toJSONString(useJianhuyiLogDOS));

        mav.setViewName("main");
        return mav;
    }

    @RequestMapping(path = "/getReadDuration")
    @ResponseBody
    List<EchartsDO> getReadDuration(@RequestBody String useJianhuyiLogDOList) throws IOException {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList, UseJianhuyiLogDO.class);

        // 首页查询平均单次阅读时长各等级人数
        return userService.selectGrade(useJianhuyiLogDOList1);
    }

    @PostMapping("/getOutdoorsDuration")
    @ResponseBody
    List<EchartsDO> getOutdoorsDuration(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList, UseJianhuyiLogDO.class);
        // 首页户外总时长各等级人数
        return userService.getOutdoorsDuration(useJianhuyiLogDOList1);
    }

    @PostMapping("/getReadDistance")
    @ResponseBody
    List<EchartsDO> getReadDistance(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList, UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getReadDistance(useJianhuyiLogDOList1);
    }

    @PostMapping("/getReadLight")
    @ResponseBody
    List<EchartsDO> getReadLight(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList, UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getReadLight(useJianhuyiLogDOList1);
    }

    @PostMapping("/getLookPhoneDuration")
    @ResponseBody
    List<EchartsDO> getLookPhoneDuration(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList, UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getLookPhoneDuration(useJianhuyiLogDOList1);
    }

    @PostMapping("/getLookTvComputerDuration")
    @ResponseBody
    List<EchartsDO> getLookTvComputerDuration(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList, UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getLookTvComputerDuration(useJianhuyiLogDOList1);
    }

    @PostMapping("/getSitTilt")
    @ResponseBody
    List<EchartsDO> getSitTilt(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList, UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getSitTilt(useJianhuyiLogDOList1);
    }

    @PostMapping("/getUseJianhuyiDuration")
    @ResponseBody
    List<EchartsDO> getUseJianhuyiDuration(@RequestBody String useJianhuyiLogDOList) {
        List<UseJianhuyiLogDO> useJianhuyiLogDOList1 = JSON.parseArray(useJianhuyiLogDOList, UseJianhuyiLogDO.class);
        // 首页平均阅读距离各等级人数
        return userService.getUseJianhuyiDuration(useJianhuyiLogDOList1);
    }


}
