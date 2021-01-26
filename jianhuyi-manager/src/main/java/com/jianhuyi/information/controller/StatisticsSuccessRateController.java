package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.StatisticsSuccessRateDO;
import com.jianhuyi.information.service.StatisticsSuccessRateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-09-23 09:20:06
 */

@Controller
@RequestMapping("/information/statisticsSuccessRate")
public class StatisticsSuccessRateController {
    @Autowired
    private StatisticsSuccessRateService statisticsSuccessRateService;

    @GetMapping()
    @RequiresPermissions("information:statisticsSuccessRate:statisticsSuccessRate")
    String StatisticsSuccessRate() {
        return "information/statisticsSuccessRate/statisticsSuccessRate";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:statisticsSuccessRate:statisticsSuccessRate")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        System.out.println("========sortr=============" + params.get("sort"));
        if (params.get("sort") != null && params.get("sort").equals("equipmentId")) {
            params.remove("sort");
            params.put("sort", "equipment_id");
        }
        //查询列表数据
        Query query = new Query(params);
        List<StatisticsSuccessRateDO> statisticsSuccessRateList = statisticsSuccessRateService.detaillist(query);
        int total = statisticsSuccessRateService.detailcount(query);
        PageUtils pageUtils = new PageUtils(statisticsSuccessRateList, total);
        return pageUtils;
    }

    @ResponseBody
    @PostMapping("/getError")
    public R getError(String equipmentId) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        params.put("equipmentId", equipmentId);
        List<StatisticsSuccessRateDO> statisticsSuccessRateDOS = statisticsSuccessRateService.list(params);

        result.put("data", statisticsSuccessRateDOS);
        return R.ok(result);
    }

    @GetMapping("/add")
    @RequiresPermissions("information:statisticsSuccessRate:add")
    String add() {
        return "information/statisticsSuccessRate/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:statisticsSuccessRate:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        StatisticsSuccessRateDO statisticsSuccessRate = statisticsSuccessRateService.get(id);
        model.addAttribute("statisticsSuccessRate", statisticsSuccessRate);
        return "information/statisticsSuccessRate/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:statisticsSuccessRate:add")
    public R save(StatisticsSuccessRateDO statisticsSuccessRate) {
        if (statisticsSuccessRateService.save(statisticsSuccessRate) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("information:statisticsSuccessRate:edit")
    public R update(StatisticsSuccessRateDO statisticsSuccessRate) {
        statisticsSuccessRateService.update(statisticsSuccessRate);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:statisticsSuccessRate:remove")
    public R remove(Integer id) {
        if (statisticsSuccessRateService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("information:statisticsSuccessRate:batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        statisticsSuccessRateService.batchRemove(ids);
        return R.ok();
    }

}
