package com.jianhuyi.device.service.impl;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jianhuyi.common.utils.R;
import com.jianhuyi.device.dao.DeviceDao;
import com.jianhuyi.device.domain.DeviceDO;
import com.jianhuyi.device.service.DeviceService;
import com.jianhuyi.system.config.ExcelUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





@Service
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	private DeviceDao deviceDao;
	
	@Override
	public DeviceDO get(Integer id){
		return deviceDao.get(id);
	}
	
	@Override
	public List<DeviceDO> list(Map<String, Object> map){
		return deviceDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return deviceDao.count(map);
	}
	
	@Override
	public int save(DeviceDO device){
		return deviceDao.save(device);
	}
	
	@Override
	public int update(DeviceDO device){
		return deviceDao.update(device);
	}
	
	@Override
	public int remove(Integer id){
		return deviceDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return deviceDao.batchRemove(ids);
	}
	
	@ResponseBody
	@Transactional(propagation=Propagation.REQUIRED)
	public R importMember(MultipartFile file){
		int num = 0;
		InputStream in=null;
		Workbook book=null;
		List<Integer> errnum = new ArrayList<>();
		try {
			if(file != null){
				in = file.getInputStream();
				book =ExcelUtils.getBook(in);
				Sheet sheet = book.getSheetAt(0);
				Row row=null;
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					try {
						row = sheet.getRow(rowNum);
						String identity = ExcelUtils.getCellFormatValue(row.getCell((short)0));	//设备号
						String name = ExcelUtils.getCellFormatValue(row.getCell((short)1));		//设备名称
						String mac = ExcelUtils.getCellFormatValue(row.getCell((short)2));		//mac地址
						String deviceType = ExcelUtils.getCellFormatValue(row.getCell((short)3));	//设备类型（0个人设备 1机构设备）

						DeviceDO device = new DeviceDO();
				    	if(identity != null && identity !=""){
				    		device.setIdentity(identity);
						}else{
							errnum.add(rowNum);
							continue;
						}
						if(mac != null && mac != ""){
							device.setMac(mac);
						}else{
							errnum.add(rowNum);
							continue;
						}
						
						if(name != null && !"".equals(name)){
							device.setName(name);
							
						}
						
						if(deviceType != null && !"".equals(deviceType)){
							if(deviceType.equals(0)){
								device.setDeviceType(0);
							}
							if(deviceType.equals(1)){
								device.setDeviceType(1);
							}
						}
						
						device.setDefaultDevice(1);
				    	device.setCreateTime(new Date());
				    	device.setDeleted(0);
				    	Map<String,Object> paramMap = new HashMap<String,Object>();
				    	paramMap.put("identity", device.getIdentity());
						List<DeviceDO> list = deviceDao.list(paramMap);
						if(list.size()>0){
							errnum.add(rowNum);
							continue;
						}
						deviceDao.save(device);
				    	num++;
					} catch (Exception e) {
						e.printStackTrace();
						return R.error("导入失败！第"+rowNum+"条");
					}
				}
				if(errnum.size()>0){
					return R.ok("上传成功,共增加["+num+"]条,第"+errnum+"条上传失败，设备号或者mac地址为空或已存在");
				}else{
					return R.ok("上传成功,共增加["+num+"]条");
				}
				
			}else{
				return R.error("请选择导入的文件!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(book!=null)
					book.close();
				if(book!=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return R.error();
	}
	
}
