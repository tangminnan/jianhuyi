package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.*;
import com.jianhuyi.information.service.DataImgService;
import com.jianhuyi.information.service.DataInitService;
import com.jianhuyi.information.service.DataService;
import com.jianhuyi.information.service.UseRemindsService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


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
    @Autowired
    private UseRemindsService useRemindsService;
    @Autowired
    private DataInitService dataInitService;


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
//        List<DataDO> dataList = dataService.list(query);
//        List<RemaindDO> data = null;
//
//        for (DataDO dataDO : dataList) {
//            String baseData = dataDO.getRemaind();
//            data = JSON.parseArray(baseData, RemaindDO.class);
//        }

        List<UseRemindsDO> data = useRemindsService.list(query);

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

    /**
     *  16分区数据导出 比对
     */
    @GetMapping("/exportDistanceData")
    public void exportDistanceData(HttpServletResponse response,@RequestParam Map<String,Object> parmasMap) throws IOException {
        List<DataDO> dataList = dataService.listDataDO(parmasMap);
        List<DistanceDO> distanceDOS = dataInitService.listDistanceDO(parmasMap);
        Map<String,DistanceDO> distanceMap = distanceDOS.stream().collect(Collectors.toMap(DistanceDO::getStartTime,item->item,
                (value1, value2 )->{
                    return value1;
                }));

        List<BaseDataDO> data = new ArrayList<>();

        for (DataDO dataDO : dataList) {
            String baseData = dataDO.getBaseData();
            data.addAll(JSON.parseArray(baseData, BaseDataDO.class));
        }

        Map<String, BaseDataDO> dataMap = data.stream().collect(Collectors.toMap(BaseDataDO::getTime, item -> item,
                (value1, value2 )->{
                    return value1;
                }));
        Iterator<String> iterator = dataMap.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            DistanceDO distanceDO = distanceMap.get(key);
            if(distanceDO!=null){
                BaseDataDO baseDataDO = dataMap.get(key);
                baseDataDO.setDistanceData(distanceDO.getDistanceData());
                baseDataDO.setType(distanceDO.getType());
                baseDataDO.setDistance(distanceDO.getDistance());
                baseDataDO.setUserId(distanceDO.getUserId());
                baseDataDO.setUploadId(distanceDO.getUploadId());
                baseDataDO.setEquipmentId(distanceDO.getEquipmentId());
            }
        }
        List<BaseDataDO> list = new ArrayList<>(dataMap.values());
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("16分区测距报告.xls");
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell=null;
        for(int i=0;i<list.size(); i++){
            BaseDataDO baseDataDO = list.get(i);
            Integer userId =     baseDataDO.getUserId()==null?0:baseDataDO.getUserId();
            Integer uploadId=    baseDataDO.getUploadId()==null?0: baseDataDO.getUploadId();
            String equipmentId = baseDataDO.getEquipmentId()==null?"":baseDataDO.getEquipmentId();
            String distanceData= baseDataDO.getDistanceData()==null?"":baseDataDO.getDistanceData();
            String date = baseDataDO.getTime();
            Integer type=baseDataDO.getType()==null?2:baseDataDO.getType();
            Double distance  =baseDataDO.getDistance()==null?0.0:baseDataDO.getDistance();
            Double distances  =baseDataDO.getDistances()==null?0.0:baseDataDO.getDistances();
            row = sheet.createRow(i+1);
            cell = row.createCell(0);
            cell.setCellValue(userId);
            cell = row.createCell(1);
            cell.setCellValue(uploadId);
            cell = row.createCell(2);
            cell.setCellValue(equipmentId);
            cell = row.createCell(3);
            cell.setCellValue(distanceData);
            cell = row.createCell(4);
            cell.setCellValue(date);
            cell = row.createCell(5);
            cell.setCellValue(type);
            cell = row.createCell(6);
            cell.setCellValue(distance);
            cell = row.createCell(7);
            cell.setCellValue(distances);
            cell = row.createCell(8);

        }
        String d = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName=d+"报告.xls";
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader(
                "Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));

        workbook.write(response.getOutputStream());
    }

}
