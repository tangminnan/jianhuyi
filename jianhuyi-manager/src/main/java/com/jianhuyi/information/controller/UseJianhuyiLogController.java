package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.ExcelExportUtil4DIY;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;
import com.jianhuyi.users.domain.UserDO;
import com.jianhuyi.users.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
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

    private static Logger logger = LoggerFactory.getLogger(UseJianhuyiLogController.class);

    @GetMapping()
    @RequiresPermissions("information:useJianhuyiLog:useJianhuyiLog")
    ModelAndView UseJianhuyiLog(@RequestParam Map<String, Object> params) {
        List<UserDO> userList = userService.list(null);
        ModelAndView mav = new ModelAndView();

        mav.addObject("userList", userList);
        mav.setViewName("information/useJianhuyiLog/useJianhuyiLog1");

        return mav;
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:useJianhuyiLog:useJianhuyiLog")
    public PageUtils list(@RequestParam Map<String, Object> params) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date start = null;
        Date end = null;
        Long uploadId = null;
        Long userId = null;
        //查询列表数据
        if (params.get("startTime") != null && !params.get("startTime").equals("")) {
            start = sdf.parse((String) params.get("startTime"));
        }
        if (params.get("endTime") != null && !params.get("endTime").equals("")) {
            end = sdf.parse((String) params.get("endTime"));
        }
        if (params.get("uploadId") != null && !params.get("uploadId").equals("")) {
            uploadId = Long.parseLong(params.get("uploadId").toString());
        }
        if (params.get("userId") != null && !params.get("userId").equals("") && !params.get("userId").equals("0")) {
            userId = Long.parseLong(params.get("userId").toString());
        }

        if (params.get("startTime").equals("") && params.get("endTime").equals("") &&
                params.get("uploadId").equals("") && (params.get("userId").equals("") || params.get("userId").equals("0"))) {

            Query query = new Query(params);
            List<UseJianhuyiLogDO> useJianhuyiLogList = useJianhuyiLogService.list(query);


            List<UseJianhuyiLogDO> useJianhuyiLogDOS = useJianhuyiLogService.list(null);
            PageUtils pageUtils = new PageUtils(useJianhuyiLogList, useJianhuyiLogDOS.size());

            return pageUtils;

        } else {
            List<UseJianhuyiLogDO> useJianhuyiLogDOList = useJianhuyiLogService.selectData(start, end, params);
            List<UseJianhuyiLogDO> useJianhuyiLogDOList11 = new ArrayList<>();
            //分页

            if ((Integer.parseInt(params.get("offset").toString())) + Integer.parseInt(params.get("limit").toString()) > useJianhuyiLogDOList.size()) {
                useJianhuyiLogDOList11 = useJianhuyiLogDOList.subList(
                        Integer.parseInt(params.get("offset").toString()),
                        useJianhuyiLogDOList.size());
            } else {
                useJianhuyiLogDOList11 = useJianhuyiLogDOList.subList(
                        Integer.parseInt(params.get("offset").toString()),
                        (Integer.parseInt(params.get("offset").toString())) + Integer.parseInt(params.get("limit").toString()));
            }

            PageUtils pageUtils = new PageUtils(useJianhuyiLogDOList11, useJianhuyiLogDOList.size());

            return pageUtils;
        }


    }


    @RequestMapping(value = "/exportExcel")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String filename = "监护仪数据列表" + format.format(new Date().getTime()) + ".xls";
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
        OutputStream out = response.getOutputStream();
        try {
            Query query = new Query(params);
            String type = request.getParameter("type");
            //导出全部数据
            if (type.equals("2")) {
                List<Map<String, Object>> XxxDOs = useJianhuyiLogService.exeList(params);
                ExcelExportUtil4DIY.exportToFile(XxxDOs, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("exportExcel出错" + e.getMessage());
        } finally {
            out.close();
        }
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
