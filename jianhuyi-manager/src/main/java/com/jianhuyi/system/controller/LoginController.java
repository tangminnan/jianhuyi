package com.jianhuyi.system.controller;

import com.jianhuyi.common.annotation.Log;
import com.jianhuyi.common.controller.BaseController;
import com.jianhuyi.common.domain.FileDO;
import com.jianhuyi.common.domain.Tree;
import com.jianhuyi.common.service.FileService;
import com.jianhuyi.common.utils.MD5Utils;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.common.utils.ShiroUtils;
import com.jianhuyi.information.domain.EchartsDO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
        //平均阅读时长
        Double avgReadDuration = userService.avgReadDuration();
        Double avgOutdoorsDuration = userService.avgOutdoorsDuration();
        Double avgReadDistance = userService.avgReadDistance();
        Double avgReadLight = userService.avgReadLight();
        Double avgLookPhoneDuration = userService.avgLookPhoneDuration();
        Double avgLookTvComputerDuration = userService.avgLookTvComputerDuration();
        Double avgSitTilt = userService.avgSitTilt();
        Double avgUseJianhuyiDuration = userService.avgUseJianhuyiDuration();
        Double avgSportDuration = userService.avgSportDuration();

        // 首页各等级数据统计
        mav.addObject("avgReadDuration", avgReadDuration);
        mav.addObject("avgOutdoorsDuration", avgOutdoorsDuration);
        mav.addObject("avgReadDistance", avgReadDistance);
        mav.addObject("avgReadLight", avgReadLight);
        mav.addObject("avgLookPhoneDuration", avgLookPhoneDuration);
        mav.addObject("avgLookTvComputerDuration", avgLookTvComputerDuration);
        mav.addObject("avgSitTilt", avgSitTilt);
        mav.addObject("avgUseJianhuyiDuration", avgUseJianhuyiDuration);
        mav.addObject("avgSportDuration", avgSportDuration);

        mav.addObject("num", num);
        mav.addObject("yesterdayNum", yesterdayNum);

        mav.setViewName("main");
        return mav;
    }

    @GetMapping("/getReadDuration")
    @ResponseBody
    List<EchartsDO> getReadDuration() {
        // 首页查询平均单次阅读时长各等级人数
        return userService.selectGrade();
    }

    @GetMapping("/getOutdoorsDuration")
    @ResponseBody
    List<EchartsDO> getOutdoorsDuration() {
        // 首页户外总时长各等级人数
        List<EchartsDO> list = userService.getOutdoorsDuration();
        System.out.println("===========getOutdoorsDurationlist=========================" + list);

        return userService.getOutdoorsDuration();
    }

    @GetMapping("/getReadDistance")
    @ResponseBody
    List<EchartsDO> getReadDistance() {
        // 首页平均阅读距离各等级人数
        List<EchartsDO> list = userService.getReadDistance();
        System.out.println("===========getReadDistancelist=========================" + list);

        return userService.getReadDistance();
    }

    @GetMapping("/getReadLight")
    @ResponseBody
    List<EchartsDO> getReadLight() {
        // 首页平均阅读距离各等级人数
        List<EchartsDO> list = userService.getReadLight();
        System.out.println("===========getReadLightlist=========================" + list);

        return userService.getReadLight();
    }

    @GetMapping("/getLookPhoneDuration")
    @ResponseBody
    List<EchartsDO> getLookPhoneDuration() {
        // 首页平均阅读距离各等级人数
        List<EchartsDO> list = userService.getLookPhoneDuration();
        System.out.println("===========getLookPhoneDuration=========================" + list);

        return userService.getLookPhoneDuration();
    }

    @GetMapping("/getLookTvComputerDuration")
    @ResponseBody
    List<EchartsDO> getLookTvComputerDuration() {
        // 首页平均阅读距离各等级人数
        List<EchartsDO> list = userService.getLookTvComputerDuration();
        System.out.println("===========getLookTvComputerDuration=========================" + list);

        return userService.getLookTvComputerDuration();
    }

    @GetMapping("/getSitTilt")
    @ResponseBody
    List<EchartsDO> getSitTilt() {
        // 首页平均阅读距离各等级人数
        List<EchartsDO> list = userService.getSitTilt();
        System.out.println("===========getSitTilt=========================" + list);

        return userService.getSitTilt();
    }

    @GetMapping("/getUseJianhuyiDuration")
    @ResponseBody
    List<EchartsDO> getUseJianhuyiDuration() {
        // 首页平均阅读距离各等级人数
        List<EchartsDO> list = userService.getUseJianhuyiDuration();
        System.out.println("===========getUseJianhuyiDuration=========================" + list);

        return userService.getUseJianhuyiDuration();
    }

    @GetMapping("/getSportDuration")
    @ResponseBody
    List<EchartsDO> getSportDuration() {
        // 首页平均阅读距离各等级人数
        List<EchartsDO> list = userService.getSportDuration();
        System.out.println("===========getSportDuration=========================" + list);

        return userService.getSportDuration();
    }

}
