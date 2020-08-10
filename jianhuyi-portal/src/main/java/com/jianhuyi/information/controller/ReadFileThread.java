package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.common.utils.FileUtil;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.common.utils.SpringContextUtil;
import com.jianhuyi.information.domain.SaveParamsDO;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.domain.UseRemindsDO;
import com.jianhuyi.information.domain.UseTimeDO;
import com.jianhuyi.information.service.UseJianhuyiLogService;
import com.jianhuyi.information.service.UseRemindsService;
import com.jianhuyi.information.service.UseTimeService;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReadFileThread implements Runnable {

    private UseRemindsService useRemindsService;
    private UseTimeService useTimeService;
    private UseJianhuyiLogService useJianhuyiLogService;
    private String fileName;

    public ReadFileThread(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);

            File file = new File(fileName);
            FileReader in = new FileReader(file);
            BufferedReader br = new BufferedReader(in);
            String str = br.readLine();

            if (str != null) {
                JSONObject userlog = JSONObject.parseObject(new String(str));
                SaveParamsDO saveParamsDO = JSON.toJavaObject(userlog, SaveParamsDO.class);

                R result = save(saveParamsDO);
                if (result.get("code").equals(0)) {
                    FileUtil.deleteFile(fileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    R save(@RequestBody SaveParamsDO saveParamsDOList) {
        this.useRemindsService = SpringContextUtil.getBean(UseRemindsService.class);
        this.useTimeService = SpringContextUtil.getBean(UseTimeService.class);
        this.useJianhuyiLogService = SpringContextUtil.getBean(UseJianhuyiLogService.class);

        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = true;
        try {
            if (saveParamsDOList != null) {
                if (saveParamsDOList.getUseRemindsDOList() != null) {
                    for (UseRemindsDO useRemindsDO : saveParamsDOList.getUseRemindsDOList()) {
                        if (useRemindsDO != null) {
                            useRemindsDO.setSaveTime(new Date());
                        }
                    }

                    useRemindsService.saveList(saveParamsDOList.getUseRemindsDOList());
                }
                if (saveParamsDOList.getUseTimeDOList() != null) {
                    for (UseTimeDO useTimeDO : saveParamsDOList.getUseTimeDOList()) {
                        if (useTimeDO != null) {
                            useTimeDO.setSaveTime(new Date());
                        }
                    }

                    useTimeService.saveList(saveParamsDOList.getUseTimeDOList());
                }

                if (saveParamsDOList.getUseJianhuyiLogDOList() != null) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : saveParamsDOList.getUseJianhuyiLogDOList()) {
                        useJianhuyiLogDO.setAddTime(new Date());
                        useJianhuyiLogDO.setDelFlag(0);
                    }

                    useJianhuyiLogService.saveList(saveParamsDOList.getUseJianhuyiLogDOList());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(map);
    }
}