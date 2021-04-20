package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.DataInitDO;
import com.jianhuyi.information.service.DataInitService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 原始文件解析表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-04-19 17:27:20
 */
@Controller
@RequestMapping("/information/dataInit")
public class DataInitController {
  @Autowired private DataInitService dataInitService;

  @GetMapping()
  @RequiresPermissions("information:dataInit:dataInit")
  String DataInit() {
    return "information/dataInit/dataInit";
  }

  @ResponseBody
  @GetMapping("/list")
  @RequiresPermissions("information:dataInit:dataInit")
  public PageUtils list(@RequestParam Map<String, Object> params) {
    // 查询列表数据
    Query query = new Query(params);
    List<DataInitDO> dataInitList = dataInitService.list(query);
    int total = dataInitService.count(query);
    PageUtils pageUtils = new PageUtils(dataInitList, total);
    return pageUtils;
  }

  @GetMapping("/add")
  @RequiresPermissions("information:dataInit:add")
  String add() {
    return "information/dataInit/add";
  }

  @GetMapping("/edit/{id}")
  @RequiresPermissions("information:dataInit:edit")
  String edit(@PathVariable("id") Integer id, Model model) {
    DataInitDO dataInit = dataInitService.get(id);
    model.addAttribute("dataInit", dataInit);
    return "information/dataInit/edit";
  }

  /** 保存 */
  @ResponseBody
  @PostMapping("/save")
  @RequiresPermissions("information:dataInit:add")
  public R save(DataInitDO dataInit) {
    if (dataInitService.save(dataInit) > 0) {
      return R.ok();
    }
    return R.error();
  }
  /** 修改 */
  @ResponseBody
  @RequestMapping("/update")
  @RequiresPermissions("information:dataInit:edit")
  public R update(DataInitDO dataInit) {
    dataInitService.update(dataInit);
    return R.ok();
  }

  /** 删除 */
  @PostMapping("/remove")
  @ResponseBody
  @RequiresPermissions("information:dataInit:remove")
  public R remove(Integer id) {
    if (dataInitService.remove(id) > 0) {
      return R.ok();
    }
    return R.error();
  }

  /** 删除 */
  @PostMapping("/batchRemove")
  @ResponseBody
  @RequiresPermissions("information:dataInit:batchRemove")
  public R remove(@RequestParam("ids[]") Integer[] ids) {
    dataInitService.batchRemove(ids);
    return R.ok();
  }
}
