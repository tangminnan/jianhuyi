package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.UseRemindsDO;
import com.jianhuyi.information.service.UseRemindsService;
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
@RequestMapping("/information/useReminds")
public class UseRemindsController {
    @Autowired
    private UseRemindsService useRemindsService;

    @GetMapping()
    @RequiresPermissions("information:useReminds:useReminds")
    String UseReminds() {
        return "information/useReminds/useReminds";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:useReminds:useReminds")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UseRemindsDO> useRemindsList = useRemindsService.list(query);
        int total = useRemindsService.count(query);
        PageUtils pageUtils = new PageUtils(useRemindsList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("information:useReminds:add")
    String add() {
        return "information/useReminds/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:useReminds:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        UseRemindsDO useReminds = useRemindsService.get(id);
        model.addAttribute("useReminds", useReminds);
        return "information/useReminds/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:useReminds:add")
    public R save(UseRemindsDO useReminds) {
        if (useRemindsService.save(useReminds) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("information:useReminds:edit")
    public R update(UseRemindsDO useReminds) {
        useRemindsService.update(useReminds);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:useReminds:remove")
    public R remove(Long id) {
        if (useRemindsService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("information:useReminds:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        useRemindsService.batchRemove(ids);
        return R.ok();
    }

}
