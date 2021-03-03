package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.ExcelExportUtil4DIY;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.DataEverydayDO;
import com.jianhuyi.information.service.DataEverydayService;
import com.jianhuyi.users.domain.UserDO;
import com.jianhuyi.users.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-03-02 17:18:05
 */
@Controller
@RequestMapping("/information/dataEveryday")
public class DataEverydayController {
  @Autowired private DataEverydayService dataEverydayService;
  @Autowired private UserService userService;

  @GetMapping()
  @RequiresPermissions("information:dataEveryday:dataEveryday")
  String DataEveryday() {
    return "information/dataEveryday/dataEveryday";
  }

  @ResponseBody
  @GetMapping("/list")
  @RequiresPermissions("information:dataEveryday:dataEveryday")
  public PageUtils list(@RequestParam Map<String, Object> params) {
    // 查询列表数据
    Query query = new Query(params);
    List<DataEverydayDO> dataEverydayList = dataEverydayService.list(query);
    int total = dataEverydayService.count(query);
    PageUtils pageUtils = new PageUtils(dataEverydayList, total);
    return pageUtils;
  }

  @GetMapping("/daochu")
  void daochu(
      @RequestParam Map<String, Object> params,
      HttpServletRequest request,
      HttpServletResponse response)
      throws IOException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<Map<String, Object>> resultmap = new ArrayList<Map<String, Object>>();
    List<DataEverydayDO> dataEverydayList = dataEverydayService.list(params);
    DecimalFormat df = new DecimalFormat("#.##");
    if (dataEverydayList.size() > 0) {
      for (DataEverydayDO ujl : dataEverydayList) {

        Map<String, Object> mapp = new LinkedHashMap<>();
        mapp.put("数据时间", ujl.getUseTime());
        mapp.put("用户id", ujl.getUserId());
        if (ujl.getUserId() != null) {
          UserDO userDO = userService.get(ujl.getUserId());
          mapp.put("姓名", userDO.getName());
          mapp.put("学校", userDO.getSchool());
          mapp.put("年级", userDO.getGrade());
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
        mapp.put("有效使用监护仪时长(小时)", ujl.getEffectiveTime());
        mapp.put("无效使用监护仪时长(分钟)", ujl.getNoneffectiveTime());
        mapp.put("遮挡时长(分钟)", ujl.getCoverTime());
        mapp.put("开机时长(分钟)", ujl.getRunningTime());
        mapp.put("提醒次数", ujl.getRemind());
        resultmap.add(mapp);
      }
    } else {
      Map<String, Object> mapP = new HashMap<String, Object>();
      mapP.put("信息", "暂无数据！！！");
      resultmap.add(mapP);
    }
    OutputStream out = response.getOutputStream();
    try {
      String filename = "导出数据" + sdf.format(new Date().getTime()) + ".xls";
      response.setContentType("application/ms-excel;charset=UTF-8");
      response.setHeader(
          "Content-Disposition",
          "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));

      Cookie status = new Cookie("status", "success");
      status.setMaxAge(600);
      response.addCookie(status);

      ExcelExportUtil4DIY.exportToFile(resultmap, out);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.close();
    }
  }

  @GetMapping("/add")
  @RequiresPermissions("information:dataEveryday:add")
  String add() {
    return "information/dataEveryday/add";
  }

  @GetMapping("/edit/{id}")
  @RequiresPermissions("information:dataEveryday:edit")
  String edit(@PathVariable("id") Integer id, Model model) {
    DataEverydayDO dataEveryday = dataEverydayService.get(id);
    model.addAttribute("dataEveryday", dataEveryday);
    return "information/dataEveryday/edit";
  }

  /** 保存 */
  @ResponseBody
  @PostMapping("/save")
  @RequiresPermissions("information:dataEveryday:add")
  public R save(DataEverydayDO dataEveryday) {
    if (dataEverydayService.save(dataEveryday) > 0) {
      return R.ok();
    }
    return R.error();
  }
  /** 修改 */
  @ResponseBody
  @RequestMapping("/update")
  @RequiresPermissions("information:dataEveryday:edit")
  public R update(DataEverydayDO dataEveryday) {
    dataEverydayService.update(dataEveryday);
    return R.ok();
  }

  /** 删除 */
  @PostMapping("/remove")
  @ResponseBody
  @RequiresPermissions("information:dataEveryday:remove")
  public R remove(Integer id) {
    if (dataEverydayService.remove(id) > 0) {
      return R.ok();
    }
    return R.error();
  }

  /** 删除 */
  @PostMapping("/batchRemove")
  @ResponseBody
  @RequiresPermissions("information:dataEveryday:batchRemove")
  public R remove(@RequestParam("ids[]") Integer[] ids) {
    dataEverydayService.batchRemove(ids);
    return R.ok();
  }
}
