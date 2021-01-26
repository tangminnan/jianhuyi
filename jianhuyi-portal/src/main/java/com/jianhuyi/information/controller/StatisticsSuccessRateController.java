package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.StatisticsSuccessRateDO;
import com.jianhuyi.information.service.StatisticsSuccessRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-09-23 09:20:06
 */

@Controller
@RequestMapping("/api")
public class StatisticsSuccessRateController {
    @Autowired
    private StatisticsSuccessRateService statisticsSuccessRateService;


    @PostMapping("/saveStatisticsSuccessRate")
    @ResponseBody
    R saveStatisticsSuccessRate(@RequestBody List<StatisticsSuccessRateDO> statisticsSuccessRateDOList) {
        for (StatisticsSuccessRateDO statisticsSuccessRateDO : statisticsSuccessRateDOList) {
            statisticsSuccessRateDO.setSaveTime(new Date());
        }
        int result = statisticsSuccessRateService.saveList(statisticsSuccessRateDOList);
        return R.ok();
    }

}
