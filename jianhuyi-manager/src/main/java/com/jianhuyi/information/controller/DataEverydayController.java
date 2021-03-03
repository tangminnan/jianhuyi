package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.DataEverydayDO;
import com.jianhuyi.information.service.DataEverydayService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-03-02 17:18:05
 */
@Controller
@RequestMapping("/information/dataEveryday")
public class DataEverydayController {
  @Autowired private DataEverydayService dataEverydayService;

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
