package com.jianhuyi.information.controller;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.utils.ExcelExportUtil4DIY;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.UserTaskLinshiDO;
import com.jianhuyi.information.service.UserTaskLinshiService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianhuyi.information.domain.UserTaskDO;
import com.jianhuyi.information.service.UserTaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-19 14:22:00
 */
 
@Controller
@RequestMapping("/information/userTask")
public class UserTaskController {
	@Autowired
	private UserTaskService userTaskService;
	@Autowired
	private UserTaskLinshiService userTaskLinshiService;
	@Autowired
	private BootdoConfig bootdoConfig;

	@GetMapping()
	@RequiresPermissions("information:userTask:userTask")
	String UserTask(){
	    return "information/userTask/userTask";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:userTask:userTask")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserTaskDO> userTaskList = userTaskService.list(query);
		int total = userTaskService.count(query);
		PageUtils pageUtils = new PageUtils(userTaskList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("information:userTask:add")
	String add(){
	    return "information/userTask/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:userTask:edit")
	String edit(@PathVariable("id") Long id,Model model){
		UserTaskDO userTask = userTaskService.get(id);
		model.addAttribute("userTask", userTask);
	    return "information/userTask/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:userTask:add")
	public R save(UserTaskDO userTask){
		if(userTaskService.save(userTask)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:userTask:edit")
	public R update( UserTaskDO userTask){
		userTaskService.update(userTask);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:userTask:remove")
	public R remove( Long id){
		if(userTaskService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:userTask:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userTaskService.batchRemove(ids);
		return R.ok();
	}

	@ResponseBody
	@PostMapping("/checkIfExist")
	public Map<String,Object> checkIfExist(@RequestParam("ids[]") Long[] ids){
		List<Long> list = new ArrayList<>();
		for(int i=0;i<ids.length;i++) {
			Long id = ids[i];
			List<UserTaskLinshiDO> userTaskLinshiDOs = userTaskLinshiService.getById(id);
			if(userTaskLinshiDOs.size()==0)
				list.add(id);
		}
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("data",list);
		return resultMap;
	}
	/**
	 * 批量导出积分
	 */
	@GetMapping( "/scoreExport/{ids}")
	public void scoreExport(@PathVariable("ids") Long[] ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<ids.length;i++) {
			List<Map<String, Object>> bb = new ArrayList<Map<String, Object>>();
			Long id = ids[i];
			List<UserTaskLinshiDO> userTaskLinshiDOs = userTaskLinshiService.getById(id);
			String name = "";
			for (UserTaskLinshiDO userTaskLinshiDO : userTaskLinshiDOs) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("日期", simpleDateFormat.format(userTaskLinshiDO.getCreateTime()));
				name = userTaskLinshiDO.getName() == null ? userTaskLinshiDO.getUserId().toString() : userTaskLinshiDO.getName();
				map.put("姓名",name);
				map.put("学校", userTaskLinshiDO.getSchool()==null?"":userTaskLinshiDO.getSchool());
				map.put("年级", userTaskLinshiDO.getGrade()==null?"":userTaskLinshiDO.getGrade());
				map.put("单次阅读时长", getResultPP(userTaskLinshiDO.getAvgRead()));
				map.put("阅读距离", getResultPP(userTaskLinshiDO.getAvgReadDistance()));
				map.put("看手机时长", getResultPP(userTaskLinshiDO.getAvgLookPhone()));
				map.put("看电子屏", getResultPP(userTaskLinshiDO.getAvgLookTv()));
				map.put("坐姿倾斜度", getResultPP(userTaskLinshiDO.getAvgSitTilt()));
				map.put("阅读光照", getResultPP(userTaskLinshiDO.getAvgLight()));
				map.put("户外时长", getResultPP(userTaskLinshiDO.getAvgOut()));
				map.put("有效时长", getResultPP(userTaskLinshiDO.getEffectiveUseTime()));
				map.put("当日所获积分", userTaskLinshiDO.getScore());
				bb.add(map);
			}
			if (bb.size() > 0) {
				OutputStream out = new FileOutputStream(bootdoConfig.getPoiword()+new String(name.getBytes(),"UTF-8")+".xls");
				try {
					ExcelExportUtil4DIY.exportToFile(bb, out);
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		}
			craeteZipPath(bootdoConfig.getPoiword(),response);
			File file=new File(bootdoConfig.getPoiword());
			if(file.exists()) {
				File[] files = file.listFiles();
				for(File f :files)
					f.delete();
			}



	}


	public static void craeteZipPath(String path,HttpServletResponse response) throws IOException {

		ZipOutputStream zipOutputStream = null;
		OutputStream output = response.getOutputStream();
        response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip");
		response.setContentType("application/zip");
		zipOutputStream = new ZipOutputStream(output, Charset.forName("GBK"));
		File[] files = new File(path).listFiles();
		FileInputStream fileInputStream = null;
		byte[] buf = new byte[1024];
		int len = 0;
		if (files != null && files.length > 0) {
			for (File wordFile : files) {
				String fileName = wordFile.getName();
				fileInputStream = new FileInputStream(wordFile);
				//放入压缩zip包中;
				zipOutputStream.putNextEntry(new ZipEntry(fileName));

				//读取文件;
				while ((len = fileInputStream.read(buf)) > 0) {
					zipOutputStream.write(buf, 0, len);
				}
				//关闭;
				zipOutputStream.flush();
				zipOutputStream.closeEntry();
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			}
			zipOutputStream.close();
		}
	}

	public String getResultPP(String value){
		if("5".equals(value)) return "优秀";
		if("4".equals(value)) return "一般";
		if("2".equals(value)) return "差";
		if("1".equals(value)) return "极差";
		return "";
	}
}
