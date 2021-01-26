package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.BaseDataDO;
import com.jianhuyi.information.domain.DataDO;
import com.jianhuyi.information.domain.DataImgDO;
import com.jianhuyi.information.domain.RemaindDO;
import com.jianhuyi.information.service.DataImgService;
import com.jianhuyi.information.service.DataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-03-25 14:23:41
 */

@Controller
@RequestMapping("/information/data")
public class DataController {
    @Autowired
    private DataService dataService;
    @Autowired
    private DataImgService dataImgService;

    @GetMapping("/listdata")
    @RequiresPermissions("information:data:data")
    ModelAndView Data(Integer userId, String startTime, ModelAndView model) {
        model.addObject("userId", userId);
        model.addObject("startTime", startTime.substring(0, 10));

        model.setViewName("information/data/data");
        return model;
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:data:data")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        Integer num = 0;
        List<DataImgDO> imgDOList = null;
        List<DataDO> dataList = dataService.list(query);
        for (DataDO dataDO : dataList) {

            if (dataDO.getImgs() != null && !dataDO.getImgs().equals("")) {
                String imgs = dataDO.getImgs();
                imgDOList = JSON.parseArray(imgs, DataImgDO.class);
            }
            for (DataImgDO dataImgDO : imgDOList) {
                if (dataImgDO.getTime() != null && dataImgDO.getFilename() == null) {
                    num++;
                }
            }
            dataDO.setPicNum(imgDOList.size());
            dataDO.setPicStatus(num.toString());
        }

        int total = dataService.count(query);
        PageUtils pageUtils = new PageUtils(dataList, total);
        return pageUtils;
    }

    @GetMapping("/jiaodu")
    String jiaodu(Long id, String startTime, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("startTime", startTime);
        return "information/data/jiaodu";
    }

    @ResponseBody
    @GetMapping("/jiaodudata")
    public PageUtils jiaodudata(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<DataDO> dataList = dataService.list(query);
        List<BaseDataDO> data = null;

        for (DataDO dataDO : dataList) {
            String baseData = dataDO.getBaseData();
            data = JSON.parseArray(baseData, BaseDataDO.class);
        }

        PageUtils pageUtils = new PageUtils(data, data.size());
        return pageUtils;
    }

    @ResponseBody
    @GetMapping("/jiaodudetaildata")
    public List<BaseDataDO> jiaodudetaildata(Integer dataid) {
        //查询列表数据
        Map<String, Object> params = new HashMap<>();
        params.put("id", dataid.toString());
        List<DataDO> dataList = dataService.list(params);
        List<BaseDataDO> data = null;

        for (DataDO dataDO : dataList) {
            String baseData = dataDO.getBaseData();
            data = JSON.parseArray(baseData, BaseDataDO.class);
        }

        return data;
    }


    @GetMapping("/zhendong/{id}")
    String zhendong(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);

        return "information/data/zhendong";
    }

    @ResponseBody
    @GetMapping("/zhendongdata")
    public PageUtils zhendongdata(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<DataDO> dataList = dataService.list(query);
        List<RemaindDO> data = null;

        for (DataDO dataDO : dataList) {
            String baseData = dataDO.getRemaind();
            data = JSON.parseArray(baseData, RemaindDO.class);
        }

        PageUtils pageUtils = new PageUtils(data, data.size());
        return pageUtils;
    }


    @GetMapping("/add")
    @RequiresPermissions("information:data:add")
    String add() {
        return "information/data/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:data:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        DataDO data = dataService.get(id);
        model.addAttribute("data", data);
        return "information/data/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:data:add")
    public R save(DataDO data) {
        if (dataService.save(data) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("information:data:edit")
    public R update(DataDO data) {
        dataService.update(data);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:data:remove")
    public R remove(Long id) {
        if (dataService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("information:data:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        dataService.batchRemove(ids);
        return R.ok();
    }

}
