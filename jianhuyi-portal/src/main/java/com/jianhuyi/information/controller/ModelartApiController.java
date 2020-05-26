package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.ModelartApiDO;
import com.jianhuyi.information.domain.QueryDO;
import com.jianhuyi.information.service.ModelartApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-04-07 11:03:48
 */
 
@Controller
@RequestMapping("/information/modelartApi")
public class ModelartApiController {
	@Autowired
	private ModelartApiService modelartApiService;

	@ResponseBody
	@GetMapping("/list")
	public R list(){
		//查询列表数据
		List<ModelartApiDO> modelartApiDOList = modelartApiService.list(null);
		QueryDO queryDO = new QueryDO();

		for (ModelartApiDO modelartApiDO : modelartApiDOList) {
			if(modelartApiDO.getType() == 1){
				queryDO.setOutdoorApi(modelartApiDO.getModelApi());
			}else if(modelartApiDO.getType() == 2){
				queryDO.setScreenApi(modelartApiDO.getModelApi());
			}
		}
		return R.data(queryDO);
	}
}
