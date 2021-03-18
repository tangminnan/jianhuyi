package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.ExcelExportUtil4DIY;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.UploadRecordDO;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
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
  @Autowired private UseJianhuyiLogService useJianhuyiLogService;
  @Autowired private UserService userService;

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
  public PageUtils list(@RequestParam Map<String, Object> params) {

    Query query = new Query(params);
    Map<String, Object> useJianhuyiLogList = useJianhuyiLogService.list(query);
    PageUtils pageUtils =
        new PageUtils(
            (List<UseJianhuyiLogDO>) useJianhuyiLogList.get("useJianhuyiLogDOList"),
            (int) useJianhuyiLogList.get("total"));
    return pageUtils;
  }

  @GetMapping("/uploadRecord")
  String uploadRecord() {

    return "information/useJianhuyiLog/uploadRecord";
  }

  @ResponseBody
  @GetMapping("/uploadRecordList")
  public PageUtils uploadRecordList(@RequestParam Map<String, Object> params) {

    Query query = new Query(params);
    List<UploadRecordDO> uploadRecordDOS = useJianhuyiLogService.uploadRecordList(query);

    int total = useJianhuyiLogService.uploadRecordCount(query);
    System.out.println("======total===========" + total);
    PageUtils pageUtils = new PageUtils(uploadRecordDOS, total);
    return pageUtils;
  }

  @RequestMapping(value = "/exportExcel")
  public void exportExcel(
      @RequestParam Map<String, Object> params,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String filename = "监护仪数据列表" + format.format(new Date().getTime()) + ".xls";
    response.setContentType("application/ms-excel;charset=UTF-8");
    response.setHeader(
        "Content-Disposition",
        "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
    OutputStream out = response.getOutputStream();
    try {
      Query query = new Query(params);
      String type = request.getParameter("type");
      // 导出全部数据
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
    // 查询列表数据
    Query query = new Query(params);
    List<UseJianhuyiLogDO> useJianhuyiLogList = useJianhuyiLogService.listDetail(query);

    System.out.println("============useJianhuyiLogList=====================" + useJianhuyiLogList);
    int total = useJianhuyiLogService.counts(query);
    PageUtils pageUtils = new PageUtils(useJianhuyiLogList, total);
    return pageUtils;
  }

  @ResponseBody
  @GetMapping("/listData")
  public Map<String, Collection<Double>> listData(Date start, Date end, Long userId)
      throws Throwable {
    // 查询列表数据
    Map<String, Collection<Double>> useJianhuyiLogList =
        useJianhuyiLogService.getlineData(start, end, userId);

    return useJianhuyiLogList;
  }

  @ResponseBody
  @GetMapping("/seasonData")
  public Map<String, Collection<Double>> seasonData(Date start, Date end, Long userId)
      throws Throwable {
    // 查询列表数据
    Map<String, Collection<Double>> useJianhuyiLogList =
        useJianhuyiLogService.seasonData(start, end, userId);

    return useJianhuyiLogList;
  }

  @ResponseBody
  @GetMapping("/yearData")
  public Map<String, Collection<Double>> yearData(Date start, Date end, Long userId)
      throws Throwable {
    // 查询列表数据
    Map<String, Collection<Double>> useJianhuyiLogList =
        useJianhuyiLogService.yearData(start, end, userId);

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
    if (w < 0) w = 0;
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

  /** 保存 */
  @ResponseBody
  @PostMapping("/save")
  @RequiresPermissions("information:useJianhuyiLog:add")
  public R save(UseJianhuyiLogDO useJianhuyiLog) {
    if (useJianhuyiLogService.save(useJianhuyiLog) > 0) {
      return R.ok();
    }
    return R.error();
  }

  /** 修改 */
  @ResponseBody
  @RequestMapping("/update")
  @RequiresPermissions("information:useJianhuyiLog:edit")
  public R update(UseJianhuyiLogDO useJianhuyiLog) {
    useJianhuyiLogService.update(useJianhuyiLog);
    return R.ok();
  }

  /** 删除 */
  @PostMapping("/remove")
  @ResponseBody
  @RequiresPermissions("information:useJianhuyiLog:remove")
  public R remove(Integer id) {
    if (useJianhuyiLogService.remove(id) > 0) {
      return R.ok();
    }
    return R.error();
  }

  /** 删除 */
  @PostMapping("/batchRemove")
  @ResponseBody
  @RequiresPermissions("information:useJianhuyiLog:batchRemove")
  public R remove(@RequestParam("ids[]") Integer[] ids) {
    useJianhuyiLogService.batchRemove(ids);
    return R.ok();
  }

  @GetMapping("/shujudaochu")
  String shujudaochu() {
    return "information/useJianhuyiLog/shujudaochu";
  }

  @GetMapping("/kaishidaochu")
  void kaishidaochu(
      @RequestParam Map<String, Object> params,
      HttpServletRequest request,
      HttpServletResponse response)
      throws IOException {
    List<Map<String, Object>> bb = new ArrayList<Map<String, Object>>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("#.##");

    List<UseJianhuyiLogDO> list = useJianhuyiLogService.listDetail(params);

    if (list.size() > 0) {
      for (UseJianhuyiLogDO ujl : list) {

        Map<String, Object> mapp = new LinkedHashMap<>();
        mapp.put("数据时间", ujl.getSaveTime());
        mapp.put("用户id", ujl.getUserId());
        if (ujl.getUserId() != null) {
          UserDO userDO = userService.get(ujl.getUserId());
          mapp.put("姓名", userDO.getName());
          mapp.put("学校", userDO.getSchool());
          mapp.put("年级", userDO.getGrade());

          if (userDO.getSex() == 1) {
            mapp.put("性别", "男");
          } else if (userDO.getSex() == 2) {
            mapp.put("性别", "女");
          } else {
            mapp.put("性别", "未知");
          }

          if (userDO.getIsWearGlasses() != null) {
            mapp.put("戴镜", userDO.getIsWearGlasses());
          } else {
            mapp.put("戴镜", "未填");
          }
        }
        mapp.put("上传人id", ujl.getUploadId());
        mapp.put("设备号", ujl.getEquipmentId());

        if (ujl.getAllReadDuration() != null) {
          mapp.put("总阅读时长(小时)", df.format(ujl.getAllReadDuration() / 60));
        }
        mapp.put("阅读时长(分钟)", ujl.getReadDuration());
        mapp.put("阅读距离(厘米)", ujl.getReadDistance());
        mapp.put("阅读光照(lux)", ujl.getReadLight());
        mapp.put("看手机时长(分钟)", ujl.getLookPhoneDuration());
        mapp.put("看电脑电视时长(分钟)", ujl.getLookTvComputerDuration());
        mapp.put("坐姿倾斜度", ujl.getSitTilt());
        mapp.put("户外时长(分钟)", ujl.getOutdoorsDuration());
        bb.add(mapp);
      }
    } else {
      Map<String, Object> mapP = new HashMap<String, Object>();
      mapP.put("信息", "暂无数据！！！");
      bb.add(mapP);
    }

    String filename = "导出数据" + sdf.format(new Date().getTime()) + ".xls";
    response.setContentType("application/ms-excel;charset=UTF-8");
    response.setHeader(
        "Content-Disposition",
        "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));

    Cookie status = new Cookie("status", "success");
    status.setMaxAge(600);
    response.addCookie(status);

    OutputStream out = response.getOutputStream();

    try {
      ExcelExportUtil4DIY.exportToFile(bb, out);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.close();
    }
  }
}
