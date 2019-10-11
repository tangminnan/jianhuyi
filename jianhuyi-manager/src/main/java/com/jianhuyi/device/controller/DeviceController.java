package com.jianhuyi.device.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.utils.FileUtil;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.device.domain.DeviceDO;
import com.jianhuyi.device.service.DeviceService;


/**
 * 用户设备表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-18 16:22:42
 */
 
@Controller
@RequestMapping("/information/device")
public class DeviceController {
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private BootdoConfig bootdoConfig;
	
	@GetMapping()
	@RequiresPermissions("information:device:device")
	String Device(){
	    return "information/device/device";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:device:device")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        query.put("deviceType",params.get("deviceType"));
		List<DeviceDO> deviceList = deviceService.list(query);
		int total = deviceService.count(query);
		PageUtils pageUtils = new PageUtils(deviceList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:device:add")
	String add(){
	    return "information/device/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:device:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		DeviceDO device = deviceService.get(id);
		model.addAttribute("device", device);
	    return "information/device/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:device:add")
	public R save( DeviceDO device){
		try {
			XSSFWorkbook wb=null;
			XSSFSheet sheet=null;
			
			if(device.getExcelDevice()!=null &&device.getExcelDevice().getSize()>0){
				wb= new XSSFWorkbook(device.getExcelDevice().getInputStream());
			    sheet=wb.getSheetAt(0);
			    for(int rowNum=1;rowNum<=sheet.getLastRowNum();rowNum++){
			    	XSSFRow row=sheet.getRow(rowNum);
			    	if(row==null) continue;
			    	if(row.getCell(0)!=null)
			    		device.setIdentity(row.getCell(0).getStringCellValue());//设备号
			    	if(row.getCell(1)!=null)
			    		device.setName(row.getCell(1).getStringCellValue());//设备名称
			    	if(row.getCell(2)!=null)
			    		device.setMac(row.getCell(2).getStringCellValue());//设备mac地址
			    	if(row.getCell(3)!=null)
			    		device.setDeviceType((int) row.getCell(3).getNumericCellValue());//设备类型
			    	device.setDefaultDevice(1);
			    	device.setCreateTime(new Date());
			    	device.setDeleted(0);
			    	Map<String,Object> params = new HashMap<String,Object>();
			    	params.put("identity", device.getIdentity());
			    	List<DeviceDO> list = deviceService.list(params);
			    	if(list.size()>0) continue;
			    	deviceService.save(device);
			    }
			}
			return R.ok();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:device:edit")
	public R update( DeviceDO device){
		if(device.getIconitems()!= null && device.getIconitems().getSize() > 0){
			String fileName = device.getIconitems().getOriginalFilename(); 
			fileName = FileUtil.renameToUUID(fileName);
			try {
				FileUtil.uploadFile(device.getIconitems().getBytes(), bootdoConfig.getUploadPath()+"device/", fileName);
				device.setIcon("/files/device/" + fileName);
				
			} catch (Exception e) {
				return R.error();
			}
			
		}
		deviceService.update(device);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:device:remove")
	public R remove( Integer id){
		DeviceDO dvo = deviceService.get(id);
		if(dvo!=null && dvo.getDefaultDevice()==0)
			return R.error("不可以删除默认设备！");
		DeviceDO deviceDO = new DeviceDO();
		deviceDO.setId(id);
		deviceDO.setDeleted(1);
		if(deviceService.update(deviceDO)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:device:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		deviceService.batchRemove(ids);
		return R.ok();
	}
	
}
