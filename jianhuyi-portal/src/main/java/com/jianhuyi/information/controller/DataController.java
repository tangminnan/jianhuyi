package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.*;
import com.jianhuyi.information.service.DataImgService;
import com.jianhuyi.information.service.DataService;
import com.jianhuyi.information.service.EnergysDataService;
import com.jianhuyi.information.service.ErrorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-25 09:40:41
 */

@Controller
@RequestMapping("/original/data")
public class DataController {
    @Autowired
    private DataService dataService;
    @Autowired
    private DataImgService dataImgService;
    @Autowired
    private EnergysDataService energysDataService;
    @Autowired
    private ErrorDataService errorDataService;

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(@RequestBody QueryDOS query) {
        boolean flag = true;
        String baseDatasDB;
        String remaindDB;
        if(query.getEnergysDataDOList().size()>0){
            for (EnergysDataDO energysDataDO : query.getEnergysDataDOList()) {
                energysDataDO.setUpdateTime(new Date());
                if(query.getUserId() !=null){
                    energysDataDO.setUserId(query.getUserId());
                }
                if(energysDataService.save(energysDataDO)>0)
                    continue;
                else
                    break;
            }
        }
        if(query.getErrorDataDOList().size()>0){
            for (ErrorDataDO errorDataDO : query.getErrorDataDOList()) {
                errorDataDO.setUpdateTime(new Date());
                if(query.getUserId() !=null) {
                    errorDataDO.setUserId(query.getUserId());
                }
               if(errorDataService.save(errorDataDO)>0)
                   continue;
               else
                   break;
            }
        }

        if(query.getDataDOList().size()>0){
            for (DataDO datum : query.getDataDOList()) {
                if (datum != null) {
                    datum.setUpdateTime(new Date());
                    if (datum.getBaseDatas() != null) {
                        baseDatasDB = JSON.toJSON(datum.getBaseDatas()).toString();
                        datum.setBaseDatasDB(baseDatasDB);
                    }

                    if (datum.getRemaind() != null) {
                        remaindDB = JSON.toJSON(datum.getRemaind()).toString();
                        datum.setRemaindDB(remaindDB);
                    }
                    if(query.getUserId() !=null){
                    datum.setUserId(query.getUserId());
                    }
                    if (dataService.save(datum) > 0) {
                        if (datum.getPictures() != null) {
                            for (DataImgDO picture : datum.getPictures()) {
                                picture.setDataid(datum.getId());
                                if (dataImgService.save(picture) > 0) {
                                    continue;
                                } else {
                                    flag = false;
                                }
                            }
                        }
                    }
                } else {
                    return R.error("数据为空");
                }

            }

        }

        if (flag) {
            return R.ok();
        } else {
            return R.error("保存失败");
        }
    }
}