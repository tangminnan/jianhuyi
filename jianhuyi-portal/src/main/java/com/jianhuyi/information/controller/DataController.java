package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.utils.FileUtil;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.DataDO;
import com.jianhuyi.information.domain.EnergysDataDO;
import com.jianhuyi.information.domain.ErrorDataDO;
import com.jianhuyi.information.domain.QueryDOS;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    @Autowired
    private BootdoConfig bootdoConfig;

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(@RequestBody MultipartFile originalFile) {
        String baseDatasDB;
        String remaindDB;
        String imgs;
        Integer result = 0;
        String filename = originalFile.getOriginalFilename();
        try {
            FileUtil.uploadFile(originalFile.getBytes(), bootdoConfig.getUploadPath() + "/originalFile/", filename);
            File file = new File(bootdoConfig.getUploadPath() + "/originalFile/" + filename);
            FileReader in = new FileReader(file);
            BufferedReader br = new BufferedReader(in);
            String str = br.readLine();

            System.out.println("========str===============" + str);
            if (str != null) {
                JSONObject queryJson = JSONObject.parseObject(new String(str));
                QueryDOS query = JSON.toJavaObject(queryJson, QueryDOS.class);

                if (query.getEnergysDataDOList().size() > 0) {
                    for (EnergysDataDO energysDataDO : query.getEnergysDataDOList()) {
                        energysDataDO.setUpdateTime(new Date());
                        if (query.getUserId() != null) {
                            energysDataDO.setUserId(query.getUserId());
                        }
                    }
                    result += energysDataService.saveList(query.getEnergysDataDOList());
                }
                if (query.getErrorDataDOList().size() > 0) {
                    for (ErrorDataDO errorDataDO : query.getErrorDataDOList()) {
                        errorDataDO.setUpdateTime(new Date());
                        if (query.getUserId() != null) {
                            errorDataDO.setUserId(query.getUserId());
                        }
                    }
                    result += errorDataService.saveList(query.getErrorDataDOList());
                }

                if (query.getDataDOList().size() > 0) {
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
                            if (datum.getPictures() != null) {
                                imgs = JSON.toJSON(datum.getPictures()).toString();
                                datum.setImgs(imgs);
                            }
                            if (query.getUserId() != null) {
                                datum.setUserId(query.getUserId());
                            }
                        }
                    }
                    result += dataService.saveList(query.getDataDOList());
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(500, e.toString());
        }
        if (result > 0) {
            FileUtil.deleteFile(bootdoConfig.getUploadPath() + "/originalFile/" + filename);
            return R.ok();
        } else {
            return R.error(-1, "上传失败");
        }
    }
}