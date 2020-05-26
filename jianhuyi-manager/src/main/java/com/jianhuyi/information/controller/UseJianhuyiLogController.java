package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;
import com.jianhuyi.users.domain.UserDO;
import com.jianhuyi.users.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 检测记录表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */

@Controller
@RequestMapping("/information/useJianhuyiLog")
public class UseJianhuyiLogController {
    @Autowired
    private UseJianhuyiLogService useJianhuyiLogService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("information:useJianhuyiLog:useJianhuyiLog")
    ModelAndView UseJianhuyiLog(@RequestParam Map<String, Object> params) {
        List<UserDO> userList = userService.list(null);
        ModelAndView mav = new ModelAndView();
        mav.addObject("userList", userList);
        mav.setViewName("information/useJianhuyiLog/useJianhuyiLog");

        return mav;
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:useJianhuyiLog:useJianhuyiLog")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UseJianhuyiLogDO> useJianhuyiLogList = useJianhuyiLogService.list(query);
        int total = useJianhuyiLogService.count(query);
        PageUtils pageUtils = new PageUtils(useJianhuyiLogList, total);
        return pageUtils;
    }


    @ResponseBody
    @GetMapping("useJianhuyiLogDetail")
    public ModelAndView listDetail(Integer userId, String saveTime, ModelAndView model) {

        model.addObject("userId", userId);
        model.addObject("saveTime", saveTime.substring(0, 10));

        model.setViewName("users/useJianhuyiLogDetail");
        return model;
    }

    @ResponseBody
    @GetMapping("/listDetail")
    public PageUtils listDetail(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UseJianhuyiLogDO> useJianhuyiLogList = useJianhuyiLogService.listDetail(query);

        System.out.println("============useJianhuyiLogList=====================" + useJianhuyiLogList);
        int total = useJianhuyiLogService.counts(query);
        PageUtils pageUtils = new PageUtils(useJianhuyiLogList, total);
        return pageUtils;
    }

    @ResponseBody
    @GetMapping("/listData")
    public Map<String, Collection<Double>> listData(Date start, Date end, Long userId) throws Throwable {
        //查询列表数据
        Map<String, Collection<Double>> useJianhuyiLogList = useJianhuyiLogService.getlineData(start, end, userId);

        return useJianhuyiLogList;
    }

    @ResponseBody
    @GetMapping("/seasonData")
    public Map<String, Collection<Double>> seasonData(Date start, Date end, Long userId) throws Throwable {
        //查询列表数据
        Map<String, Collection<Double>> useJianhuyiLogList = useJianhuyiLogService.seasonData(start, end, userId);

        return useJianhuyiLogList;
    }

    @ResponseBody
    @GetMapping("/yearData")
    public Map<String, Collection<Double>> yearData(Date start, Date end, Long userId) throws Throwable {
        //查询列表数据
        Map<String, Collection<Double>> useJianhuyiLogList = useJianhuyiLogService.yearData(start, end, userId);

        return useJianhuyiLogList;
    }


    public static String dayForWeek(String pTime) throws Throwable {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = format.parse(pTime);
        Calendar cal = Calendar.getInstance();

        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

        try {
            cal.setTime(tmpDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    @GetMapping("/add")
    @RequiresPermissions("information:useJianhuyiLog:add")
    String add() {
        return "information/useJianhuyiLog/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:useJianhuyiLog:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        UseJianhuyiLogDO useJianhuyiLog = useJianhuyiLogService.get(id);
        model.addAttribute("useJianhuyiLog", useJianhuyiLog);
        return "information/useJianhuyiLog/edit";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "users/useJianhuyiLog";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:useJianhuyiLog:add")
    public R save(UseJianhuyiLogDO useJianhuyiLog) {
        if (useJianhuyiLogService.save(useJianhuyiLog) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("information:useJianhuyiLog:edit")
    public R update(UseJianhuyiLogDO useJianhuyiLog) {
        useJianhuyiLogService.update(useJianhuyiLog);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:useJianhuyiLog:remove")
    public R remove(Integer id) {
        if (useJianhuyiLogService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("information:useJianhuyiLog:batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        useJianhuyiLogService.batchRemove(ids);
        return R.ok();
    }

}
