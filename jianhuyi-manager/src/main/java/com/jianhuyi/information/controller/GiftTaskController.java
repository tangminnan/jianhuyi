package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.GiftTaskDO;
import com.jianhuyi.information.service.GiftTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:13
 */

@Controller
@RequestMapping("/information/giftTask")
public class GiftTaskController {
    @Autowired
    private GiftTaskService giftTaskService;

    @GetMapping("/index/{id}")
    @RequiresPermissions("information:giftTask:giftTask")
    String GiftTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("giftId", id);
        return "information/giftTask/giftTask";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:giftTask:giftTask")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<GiftTaskDO> giftTaskList = giftTaskService.list(query);
        int total = giftTaskService.count(query);
        PageUtils pageUtils = new PageUtils(giftTaskList, total);
        return pageUtils;
    }

    @GetMapping("/add/{giftId}")
    @RequiresPermissions("information:giftTask:add")
    String add(@PathVariable("giftId") Long giftId, Model model) {
        model.addAttribute("giftId", giftId);
        return "information/giftTask/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:giftTask:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        GiftTaskDO giftTask = giftTaskService.get(id);
        model.addAttribute("giftTask", giftTask);
        return "information/giftTask/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:giftTask:add")
    public R save(GiftTaskDO giftTask) {
        giftTask.setCreateTime(new Date());
        if (giftTaskService.save(giftTask) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("information:giftTask:edit")
    public R update(GiftTaskDO giftTask) {
        giftTaskService.update(giftTask);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:giftTask:remove")
    public R remove(Long id) {
        if (giftTaskService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("information:giftTask:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        giftTaskService.batchRemove(ids);
        return R.ok();
    }

}
