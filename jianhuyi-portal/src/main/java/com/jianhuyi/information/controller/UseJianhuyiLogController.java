package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.common.config.BootdoConfig;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 检测记录表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */

@RestController
@RequestMapping("/jianhuyi/useJianhuyiLog")
public class UseJianhuyiLogController {
    @Autowired
    private UseJianhuyiLogService useJianhuyiLogService;
    @Autowired
    private UseRemindsService useRemindsService;
    @Autowired
    private UseTimeService useTimeService;
    @Autowired
    private BootdoConfig bootdoConfig;

    @ResponseBody
    @PostMapping("/saveFile")
    R saveFile(@RequestBody MultipartFile temporaryFile) {
        try {
            String filename = temporaryFile.getOriginalFilename();
            FileUtil.uploadFile(temporaryFile.getBytes(), bootdoConfig.getUploadPath() + "/temporaryFiles/", filename);

            File file = new File(bootdoConfig.getUploadPath() + "/temporaryFiles/" + filename);
            FileReader in = new FileReader(file);
            BufferedReader br = new BufferedReader(in);
            String str = br.readLine();

            System.out.println("========str===============" + str);
            if (str != null) {
                JSONObject userlog = JSONObject.parseObject(new String(str));
                SaveParamsDO saveParamsDO = JSON.toJavaObject(userlog, SaveParamsDO.class);

                R result = save(saveParamsDO);
                if (result.get("code").equals(0)) {
                    FileUtil.deleteFile(bootdoConfig.getUploadPath() + "/temporaryFiles/" + filename);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(500, e.toString());
        }
        return R.ok();
    }

    R save(@RequestBody SaveParamsDO saveParamsDOList) {
        this.useRemindsService = SpringContextUtil.getBean(UseRemindsService.class);
        this.useTimeService = SpringContextUtil.getBean(UseTimeService.class);
        this.useJianhuyiLogService = SpringContextUtil.getBean(UseJianhuyiLogService.class);

        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = true;
        try {
            if (saveParamsDOList != null) {
                if (saveParamsDOList.getUseRemindsDOList() != null && saveParamsDOList.getUseRemindsDOList().size() > 0) {
                    for (UseRemindsDO useRemindsDO : saveParamsDOList.getUseRemindsDOList()) {
                        if (useRemindsDO != null) {
                            useRemindsDO.setSaveTime(new Date());

                            if (saveParamsDOList.getEquipmentId() != null) {
                                useRemindsDO.setEquipmentId(saveParamsDOList.getEquipmentId());
                            }
                        }
                    }

                    useRemindsService.saveList(saveParamsDOList.getUseRemindsDOList());
                }
                if (saveParamsDOList.getUseTimeDOList() != null && saveParamsDOList.getUseTimeDOList().size() > 0) {
                    for (UseTimeDO useTimeDO : saveParamsDOList.getUseTimeDOList()) {
                        if (useTimeDO != null) {
                            useTimeDO.setSaveTime(new Date());

                            if (saveParamsDOList.getEquipmentId() != null) {
                                useTimeDO.setEquipmentId(saveParamsDOList.getEquipmentId());
                            }
                        }
                    }

                    useTimeService.saveList(saveParamsDOList.getUseTimeDOList());
                }

                if (saveParamsDOList.getUseJianhuyiLogDOList() != null && saveParamsDOList.getUseJianhuyiLogDOList().size() > 0) {
                    for (UseJianhuyiLogDO useJianhuyiLogDO : saveParamsDOList.getUseJianhuyiLogDOList()) {
                        useJianhuyiLogDO.setAddTime(new Date());
                        useJianhuyiLogDO.setDelFlag(0);

                        if (saveParamsDOList.getEquipmentId() != null) {
                            useJianhuyiLogDO.setEquipmentId(saveParamsDOList.getEquipmentId());
                        }
                        if (saveParamsDOList.getUploadId() != null) {
                            useJianhuyiLogDO.setUploadId(Integer.parseInt(saveParamsDOList.getUploadId().toString()));
                        }
                        if (saveParamsDOList.getUserId() != null) {
                            useJianhuyiLogDO.setUserId(Integer.parseInt(saveParamsDOList.getUserId().toString()));
                        }
                    }

                    useJianhuyiLogService.saveList(saveParamsDOList.getUseJianhuyiLogDOList());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(map);
    }


    /**
     * 获取今天数据
     *
     * @return
     */
    @GetMapping("/todayData")
    Map<String, Object> todayData(Long userId) {
        Map<String, Object> useLog = useJianhuyiLogService.getByDayTime(userId);

        return useLog;

    }

    /**
     * 周记录
     *
     * @param start
     * @param end
     * @param userId
     * @return
     */
    @GetMapping("/weekData")
    Map<String, Object> weekData(Date start, Date end, Long userId) {
        return useJianhuyiLogService.queryUserWeekHistory(start, end, userId);

    }

    /**
     * 月记录
     *
     * @param start
     * @param end
     * @param userId
     * @return
     */
    @GetMapping("/monthData")
    Map<String, Object> monthData(Date start, Date end, Long userId) {
        return useJianhuyiLogService.queryUserMonthHistory(start, end, userId);

    }

    /**
     * 季记录
     *
     * @param start
     * @param end
     * @param userId
     * @return
     */
    @GetMapping("/seasonData")
    Map<String, Object> seasonData(Date start, Date end, Long userId) {
        return useJianhuyiLogService.queryUserSeasonHistory(start, end, userId);

    }

    /**
     * 年记录
     *
     * @param start
     * @param end
     * @param userId
     * @return
     */
    @GetMapping("/yearData")
    Map<String, Object> yearData(Date start, Date end, Long userId) {
        return useJianhuyiLogService.queryUserYearHistory(start, end, userId);

    }

}
