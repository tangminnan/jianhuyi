package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.UseTimeDO;
import com.jianhuyi.information.service.UseTimeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-07-22 09:43:53
 */

@Controller
@RequestMapping("/information/useTime")
public class UseTimeController {
    @Autowired
    private UseTimeService useTimeService;

    @GetMapping()
    @RequiresPermissions("information:useTime:useTime")
    String UseTime() {
        return "information/useTime/useTime";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:useTime:useTime")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UseTimeDO> useTimeList = useTimeService.list(query);
        int total = useTimeService.count(query);
        PageUtils pageUtils = new PageUtils(useTimeList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("information:useTime:add")
    String add() {
        return "information/useTime/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:useTime:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        UseTimeDO useTime = useTimeService.get(id);
        model.addAttribute("useTime", useTime);
        return "information/useTime/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:useTime:add")
    public R save(UseTimeDO useTime) {
        if (useTimeService.save(useTime) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("information:useTime:edit")
    public R update(UseTimeDO useTime) {
        useTimeService.update(useTime);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:useTime:remove")
    public R remove(Long id) {
        if (useTimeService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("information:useTime:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        useTimeService.batchRemove(ids);
        return R.ok();
    }

}
