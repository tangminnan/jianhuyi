package com.jianhuyi.information.controller;

import com.alibaba.fastjson.JSONArray;
import com.jianhuyi.common.annotation.Log;
import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.utils.ExcelExportUtil4DIY;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.Data;
import com.jianhuyi.information.domain.DataEverydayDO;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import com.jianhuyi.information.service.DataEverydayService;
import com.jianhuyi.information.service.UseJianhuyiLogService;
import com.jianhuyi.users.domain.UserDO;
import com.jianhuyi.users.service.UserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-03-02 17:18:05
 */
@Controller
@RequestMapping("/information/dataEveryday")
public class DataEverydayController {
  @Autowired private DataEverydayService dataEverydayService;
  @Autowired private UserService userService;
  @Autowired private UseJianhuyiLogService useJianhuyiLogService;
  @Autowired private BootdoConfig bootdoConfig;
  private static final int YOU=4;
  private static final int LIANG=3;
  private static final int CHA=2;
  private static final int JICHA=1;


  @GetMapping()
  @RequiresPermissions("information:dataEveryday:dataEveryday")
  String DataEveryday() {
    return "information/dataEveryday/dataEveryday";
  }

  @ResponseBody
  @GetMapping("/list")
  @RequiresPermissions("information:dataEveryday:dataEveryday")
  public PageUtils list(@RequestParam Map<String, Object> params) {
    // 查询列表数据
    Query query = new Query(params);
    List<DataEverydayDO> dataEverydayList = dataEverydayService.list(query);
    int total = dataEverydayService.count(query);
    PageUtils pageUtils = new PageUtils(dataEverydayList, total);
    return pageUtils;
  }

  @GetMapping("/detail/{id}")
  @RequiresPermissions("information:dataEveryday:dataEveryday")
  String detail(@PathVariable("id") Long userId, Model model) {
    model.addAttribute("userId", userId);
    return "information/dataEveryday/dataEverydayByUser";
  }

  @GetMapping("/daochu")
  void daochu(
      @RequestParam Map<String, Object> params,
      HttpServletRequest request,
      HttpServletResponse response)
      throws IOException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<Map<String, Object>> resultmap = new ArrayList<Map<String, Object>>();
    List<DataEverydayDO> dataEverydayList = dataEverydayService.list(params);
    DecimalFormat df = new DecimalFormat("#.##");
    if (dataEverydayList.size() > 0) {
      for (DataEverydayDO ujl : dataEverydayList) {

        Map<String, Object> mapp = new LinkedHashMap<>();
        mapp.put("数据时间", ujl.getUseTime());
        mapp.put("用户id", ujl.getUserId());
        if (ujl.getUserId() != null) {
          UserDO userDO = userService.get(ujl.getUserId());
          if (userDO != null) {
            mapp.put("姓名", userDO.getName());
            mapp.put("学校", userDO.getSchool());
            mapp.put("年级", userDO.getGrade());
            mapp.put("身份证号", userDO.getIdentityCard());
            if (userDO.getSex() != null && userDO.getSex() == 1) {
              mapp.put("性别", "男");
            } else if (userDO.getSex() != null && userDO.getSex() == 2) {
              mapp.put("性别", "女");
            } else {
              mapp.put("性别", "未知");
            }

            if (userDO.getIsWearGlasses() != null) {
              mapp.put("戴镜", userDO.getIsWearGlasses());
            } else {
              mapp.put("戴镜", "未填");
            }
          } else {
            mapp.put("姓名", "");
            mapp.put("学校", "");
            mapp.put("年级", "");
            mapp.put("性别", "未知");
            mapp.put("戴镜", "未填");
          }
        }
        mapp.put("上传人id", ujl.getUploadId());
        mapp.put("设备号", ujl.getEquipmentId());

        if (ujl.getAllReadDuration() != null) {
          mapp.put("总阅读时长(小时)", df.format(ujl.getAllReadDuration() / 60));
        }
        mapp.put("阅读时长(分钟)", ujl.getReadDuration());
        mapp.put("阅读距离(厘米)", ujl.getReadDistance());
        mapp.put("阅读光照(lux)", ujl.getReadLight());
        mapp.put("看手机时长(分钟)", ujl.getLookPhoneDuration());
        mapp.put("看电脑电视时长(分钟)", ujl.getLookTvComputerDuration());
        mapp.put("坐姿倾斜度(+ 左偏 - 右偏)", ujl.getSitTilt());
        mapp.put("户外时长(分钟)", ujl.getOutdoorsDuration());
        mapp.put("有效使用监护仪时长(小时)", ujl.getEffectiveTime());
        mapp.put("无效使用监护仪时长(分钟)", ujl.getNoneffectiveTime());
        mapp.put("遮挡时长(分钟)", ujl.getCoverTime());
        mapp.put("开机时长(小时)", ujl.getRunningTime());
        mapp.put("提醒次数", ujl.getRemind());
        resultmap.add(mapp);
      }
    } else {
      Map<String, Object> mapP = new HashMap<String, Object>();
      mapP.put("信息", "暂无数据！！！");
      resultmap.add(mapP);
    }
    OutputStream out = response.getOutputStream();
    try {
      String filename = "导出数据" + sdf.format(new Date().getTime()) + ".xls";
      response.setContentType("application/ms-excel;charset=UTF-8");
      response.setHeader(
          "Content-Disposition",
          "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));

      Cookie status = new Cookie("status", "success");
      status.setMaxAge(600);
      response.addCookie(status);

      ExcelExportUtil4DIY.exportToFile(resultmap, out);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.close();
    }
  }

  @Log("监护仪报告")
  @PostMapping("/jianhuyiReport")
  public void  jianhuyiReport(@RequestParam Map<String, Object> params,HttpServletRequest request,HttpServletResponse response){
    UserDO userDO = userService.get(Integer.parseInt((String)params.get("userId")));
    if(userDO==null)
      userDO=new UserDO();
    List<DataEverydayDO> dataEverydayDOS = dataEverydayService.list(params);
    DataEverydayDO dataEverydayDO = new DataEverydayDO();
    if(dataEverydayDOS.size()>0){
      dataEverydayDO=dataEverydayDOS.get(0);
    }
    Map<String,Object> paramsMap = new HashMap<String,Object>();
    paramsMap.put("school",userDO.getSchool()==null?"":userDO.getSchool());
    paramsMap.put("class",userDO.getGrade()==null?"":userDO.getGrade());
    Integer sex = userDO.getSex()==null?0:userDO.getSex();
    if(sex==1)
      paramsMap.put("sex","男");
    else if(sex==2)
      paramsMap.put("sex","女");
    paramsMap.put("name",userDO.getName()==null?"":userDO.getName());
    /**
     *  开始佩戴时间
     */
    UseJianhuyiLogDO useJianhuyiLogDO = useJianhuyiLogService.getStart(userDO.getId(),(String)params.get("useTime"));
    paramsMap.put("date",useJianhuyiLogDO.getSaveTime());
    paramsMap.put("loghtF",params.get("loghtF"));
    paramsMap.put("distanceF",params.get("distanceF"));
    paramsMap.put("sitF",params.get("sitF"));
    paramsMap.put("time","11：30~12.00 ；  13.30~14.30");
    paramsMap.put("effectivetime",dataEverydayDO.getEffectiveTime()==null?0.0:dataEverydayDO.getEffectiveTime());
    paramsMap.put("allread",dataEverydayDO.getAllReadDuration()==null?0.0:dataEverydayDO.getAllReadDuration());
    paramsMap.put("avgread",dataEverydayDO.getReadDuration()==null?0.0:dataEverydayDO.getReadDuration());
    paramsMap.put("outdoor",dataEverydayDO.getOutdoorsDuration()==null?0.0:dataEverydayDO.getOutdoorsDuration());
    String ln = (String)params.get("ln");
      ln=ln.replaceAll(" ","+");
    paramsMap.put("readlightimg",ln);
    String dn = (String)params.get("dn");
      dn=dn.replaceAll(" ","+");
    paramsMap.put("readdistanceimg",dn);
    String sn = (String)params.get("sn");
    sn=sn.replaceAll(" ","+");
    paramsMap.put("sitimg",sn);
    paramsMap.put("avglight",dataEverydayDO.getReadLight()==null?0.0:dataEverydayDO.getReadLight() );
    paramsMap.put("avgdistance",dataEverydayDO.getReadDistance()==null?0.0:dataEverydayDO.getReadDistance());
    paramsMap.put("avgsit",dataEverydayDO.getSitTilt()==null?0.0:dataEverydayDO.getSitTilt());
   /* String s = (String)params.get("useTime");
    String s1 = s.replaceFirst("-","年");
    String s2 = s1.replaceFirst("-","月");*/
    paramsMap.put("fileName",paramsMap.get("name")+"-个人用眼行为报告-"+((String)paramsMap.get("date")).substring(0,10));
    download(response, "个人监护仪报告-II.ftl", paramsMap);
  }

  @Log("监护仪报告批量导出")
  @PostMapping("/exportReportC")
  public void exportReportC(HttpServletRequest request,HttpServletResponse response, UserDO userDO){
    List<Data> dataList = userDO.getDataList();
    for(int i=0;i<dataList.size(); i++){
      Data data = dataList.get(i);
      Map<String,Object> resultMap = new HashMap<>();
      resultMap.put("school",data.getSchool()==null?"":data.getSchool());
      resultMap.put("class",data.getGrade()==null?"":data.getGrade());
      Integer sex = data.getSex()==null?0:data.getSex();
      if(sex==1)
        resultMap.put("sex","男");
      else if(sex==2)
        resultMap.put("sex","女");
      resultMap.put("name",data.getName()==null?"":data.getName());
      Map<String,Object> paramsMap = new HashMap<>();

      paramsMap = new HashMap<>();
      paramsMap.put("userId",data.getUserId());
      paramsMap.put("useTime",data.getDate());
      List<DataEverydayDO> dataEverydayDOS = dataEverydayService.list(paramsMap);
      DataEverydayDO dataEverydayDO=dataEverydayDOS.size()>0?dataEverydayDOS.get(0):new DataEverydayDO();
      resultMap.put("effectivetime",dataEverydayDO.getEffectiveTime()==null?0.0:dataEverydayDO.getEffectiveTime());
      resultMap.put("allread",dataEverydayDO.getAllReadDuration()==null?0.0:dataEverydayDO.getAllReadDuration());
      resultMap.put("avgread",dataEverydayDO.getReadDuration()==null?0.0:dataEverydayDO.getReadDuration());
      resultMap.put("outdoor",dataEverydayDO.getOutdoorsDuration()==null?0.0:dataEverydayDO.getOutdoorsDuration());
      resultMap.put("avglight",dataEverydayDO.getReadLight()==null?0.0:dataEverydayDO.getReadLight() );
      resultMap.put("avgdistance",dataEverydayDO.getReadDistance()==null?0.0:dataEverydayDO.getReadDistance());
      resultMap.put("avgsit",dataEverydayDO.getSitTilt()==null?0.0:dataEverydayDO.getSitTilt());
      resultMap.put("loghtF",data.getLoghtF());
      resultMap.put("distanceF",data.getDistanceF());
      resultMap.put("sitF",data.getSitF());
      resultMap.put("readlightimg",data.getLn());
      resultMap.put("readdistanceimg",data.getDn());
      resultMap.put("sitimg",data.getSn());
      resultMap.put("fileName",resultMap.get("name")+"-个人用眼行为报告-"+data.getDate().substring(0,10));
      download(null, "个人监护仪报告-II.ftl", resultMap);
    }
    try {
      craeteZipPath(bootdoConfig.getPoiword(),response);
    } catch (IOException e) {
      e.printStackTrace();
    }finally{
      File file=new File(bootdoConfig.getPoiword());
      if(file.exists()) {
        File[] files = file.listFiles();
        for(File f :files)
          f.delete();
      }
    }

  }
  /**
   * freemarker导出工具类
   */

  public void download(HttpServletResponse response,String template,Map dataMap) {
    Configuration configuration = new Configuration();
    configuration.setDefaultEncoding("utf-8");
    configuration.setClassForTemplateLoading(DataEverydayController.class, "/");
    Template t = null;
    try {
      //word.xml是要生成Word文件的模板文件
      t = configuration.getTemplate(template,"utf-8");
      String filename = dataMap.get("fileName")+".docx";
      Writer out=null;
      if(response!=null) {
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader(
                "Content-Disposition",
                "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
        out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
      }else{
         out=  new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(bootdoConfig.getPoiword()+new File(filename))));
      }
      t.process(dataMap, out);
      out.flush();
      out.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   *  班级监护仪数据excel导出
   */
  @GetMapping("/exportClassData")
  public void exportClassData(HttpServletResponse response,String school,String grade,String date) throws IOException {
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("用眼行为班级报告.xls");
    HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
    HSSFSheet sheet = workbook.getSheetAt(0);
    HSSFRow row = sheet.getRow(0);
    HSSFCell cell = row.createCell(7);
    cell.setCellValue(school);
    cell = row.createCell(10);
    cell.setCellValue(grade);
    cell = row.createCell(13);
    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    Map<String,Object> paramsMap = new HashMap<>();
    paramsMap.put("school",school);
    paramsMap.put("grade",grade);
    List<UserDO> list = userService.list(paramsMap);
    for(int i=0;i<list.size(); i++){
      UserDO userDO = list.get(i);
      Long userId = userDO.getId();
      String name = userDO.getName()==null?"":userDO.getName();
      String studentNum = userDO.getStudentNum()==null?"":userDO.getStudentNum();
      String identityCard = userDO.getIdentityCard()==null?"":userDO.getIdentityCard();
      String gender = userDO.getSex()==null?"":userDO.getSex()==1?"男":"女";
      paramsMap = new HashMap<>();
      paramsMap.put("userId",userId);
      paramsMap.put("useTime",date);
      List<DataEverydayDO> dataEverydayDOS = dataEverydayService.list(paramsMap);
      DataEverydayDO dataEverydayDO = new DataEverydayDO();
      if(dataEverydayDOS.size()>0){
        dataEverydayDO=dataEverydayDOS.get(0);
      }
      Double readLight =  dataEverydayDO.getReadLight()==null?0.0:dataEverydayDO.getReadLight();//平均阅读光照
      Double readDistance= dataEverydayDO.getReadDistance()==null?0.0:dataEverydayDO.getReadDistance();//平均阅读距离
      Double sitTilt = dataEverydayDO.getSitTilt()==null?0.0:dataEverydayDO.getSitTilt();//平均坐姿
      Double effectiveTime =dataEverydayDO.getEffectiveTime()==null?0.0:dataEverydayDO.getEffectiveTime();//有效佩戴时长
      paramsMap.put("userId",userId);paramsMap.remove("useTime");paramsMap.put("saveTime",date);
      List<UseJianhuyiLogDO> useJianhuyiLogList = useJianhuyiLogService.listDetail(paramsMap);
      Map<String, Object> map = createData(useJianhuyiLogList);
      Double loghtF = Double.parseDouble((String)map.get("loghtF"));//阅读光照评分
      Double sitF = Double.parseDouble((String)map.get("sitF"));//坐姿评分
      Double distanceF =Double.parseDouble((String)map.get("distanceF")) ;//阅读距离评分
      Double tottal  = loghtF+sitF+distanceF;
      row = sheet.createRow(i+5);
      cell = row.createCell(0);
      cell.setCellValue(date);
      cell = row.createCell(1);
      cell.setCellValue(userId);
      cell = row.createCell(2);
      cell.setCellValue(name);
      cell = row.createCell(3);
      cell.setCellValue(grade);
      cell = row.createCell(4);
      cell.setCellValue(studentNum);
      cell = row.createCell(5);
      cell.setCellValue(identityCard);
      cell = row.createCell(6);
      cell.setCellValue(gender);
      cell = row.createCell(7);
      cell.setCellValue(tottal);
      cell = row.createCell(8);
      cell.setCellValue(readDistance);
      cell = row.createCell(9);
      cell.setCellValue(distanceF);
      cell = row.createCell(10);
      cell.setCellValue(readLight);
      cell = row.createCell(11);
      cell.setCellValue(loghtF);
      cell = row.createCell(12);
      cell.setCellValue(sitTilt);
      cell = row.createCell(13);
      cell.setCellValue(sitF);
      cell = row.createCell(14);
      cell.setCellValue(effectiveTime);
    }
    String fileName=school+grade+date+"报告.xls";
    response.setContentType("application/ms-excel;charset=UTF-8");
    response.setHeader(
            "Content-Disposition",
            "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));

    workbook.write(response.getOutputStream());
  }



  /**
   *  获取个人echarts图表数据
   * @return
   */

  @ResponseBody
  @GetMapping("/getEchartsData")
  public Map<String,Object> getEchartsData(@RequestParam Map<String,Object> params) {
    List<UseJianhuyiLogDO> useJianhuyiLogList = useJianhuyiLogService.listDetail(params);
    Map<String, Object> resultMap = createData(useJianhuyiLogList);
    return resultMap;
  }

  /**
   *  获取班级echarts图表数据
   * @param school
   * @param grade
   * @param date
   * @return
   */
  @ResponseBody
  @GetMapping("/getEchartsDataC")
  public Map<String,Object> getEchartsDataC(String school,String grade,String date){
    Map<String,Object> resultMap = new HashMap<>();
    Map<String,Object> paramsMap = new HashMap<>();
    paramsMap.put("school",school);
    paramsMap.put("grade",grade);
    List<UserDO> list = userService.list(paramsMap);
    boolean flag=false;
    for(UserDO userDO:list){
      paramsMap = new HashMap<>();
      paramsMap.put("userId",userDO.getId());
      paramsMap.put("saveTime",date);
      List<UseJianhuyiLogDO> useJianhuyiLogList = useJianhuyiLogService.listDetail(paramsMap);
      if(useJianhuyiLogList.size()==0){
        continue;
      }
      flag=true;
      Map<String, Object> map = createData(useJianhuyiLogList);
      map.put("name",userDO.getName());
      map.put("sex",userDO.getSex());
      map.put("userId",userDO.getId());
      resultMap.put(userDO.getId().toString(),map);
      resultMap.put("code",0);
    }
    if(!flag){
      resultMap.put("code",-1);
    }
    return resultMap;
  }

  private Map<String,Object> createData(List<UseJianhuyiLogDO> useJianhuyiLogList) {
  /**
       *  阅读光强 阅读距离  阅读倾斜角度
       */
    EchartsData lightY = new EchartsData("优");
    EchartsData lightL = new EchartsData("良");
    EchartsData lightC = new EchartsData("差");
    EchartsData lightJC = new EchartsData("极差");
    EchartsData sitY = new EchartsData("优");
    EchartsData sitL = new EchartsData("良");
    EchartsData sitC = new EchartsData("差");
    EchartsData sitJC = new EchartsData("极差");
    EchartsData distanceY = new EchartsData("优");
    EchartsData distanceL = new EchartsData("良");
    EchartsData distanceC = new EchartsData("差");
    EchartsData distanceJC = new EchartsData("极差");

    for(UseJianhuyiLogDO useJianhuyiLogDO:useJianhuyiLogList){
      Double readLight=null,sit=null,distance=null;
      if(useJianhuyiLogDO.getStatus()==1) {
        if ((readLight = useJianhuyiLogDO.getAvgLight()) != null) {
          if (readLight > 300) lightY.setNum(lightY.getNum()+1);
          if (readLight > 200 && readLight <= 300) lightL.setNum(lightL.getNum() + 1);
          if (readLight > 100 && readLight <= 200) lightC.setNum(lightC.getNum() + 1);
          if (readLight > 10 && readLight <= 100) lightJC.setNum(lightJC.getNum() + 1);
        }
        if ((sit = useJianhuyiLogDO.getAvgSit()) != null) {
          if (Math.abs(sit) > 15) sitJC.setNum(sitJC.getNum() + 1);
          if (Math.abs(sit) > 10 && Math.abs(sit) <= 15) sitC.setNum(sitC.getNum() + 1);
          if (Math.abs(sit) > 5 && Math.abs(sit) <= 10) sitL.setNum(sitL.getNum() + 1);
          if (Math.abs(sit) > 0 && Math.abs(sit) <= 5) sitY.setNum(sitY.getNum() + 1);
        }
        if ((distance = useJianhuyiLogDO.getReadDistance()) != null) {
          if (distance > 33) distanceY.setNum(distanceY.getNum() + 1);
          if (distance > 28 && distance <= 33) distanceL.setNum(distanceL.getNum() + 1);
          if (distance >= 20 && distance <= 28) distanceC.setNum(distanceC.getNum() + 1);
          if (distance < 20) distanceJC.setNum(distanceJC.getNum() + 1);
        }
      }
    }
    /**
     *  统计点数占比
     */
    int lightNum = lightY.getNum()+lightL.getNum()+lightC.getNum()+lightJC.getNum();
    if(lightNum!=0){
      String lightYPercent = String.format("%.1f", (double) lightY.getNum() / lightNum * 100);
      String lightLPercent = String.format("%.1f", (double) lightL.getNum() / lightNum * 100);
      String lightCPercent = String.format("%.1f", (double) lightC.getNum() / lightNum * 100);
      String lightJCPercent =String.format("%.1f",100-Double.parseDouble(lightYPercent)-Double.parseDouble(lightLPercent)
              -Double.parseDouble(lightCPercent));
      lightY.setValue(lightYPercent+"%");
      lightL.setValue(lightLPercent+"%");
      lightC.setValue(lightCPercent+"%");
      lightJC.setValue(lightJCPercent+"%");
    }

    int sitNum = sitY.getNum()+sitL.getNum()+sitC.getNum()+sitJC.getNum();
    if(sitNum!=0){
      String sitYPercent = String.format("%.1f", (double) sitY.getNum() / sitNum * 100);
      String sitLPercent = String.format("%.1f", (double) sitL.getNum() / sitNum * 100);
      String sitCPercent = String.format("%.1f", (double) sitC.getNum() / sitNum * 100);
      String sitJCPercent =String.format("%.1f",100-Double.parseDouble(sitYPercent)-Double.parseDouble(sitLPercent)
              -Double.parseDouble(sitCPercent));
      sitY.setValue(sitYPercent+"%");
      sitL.setValue(sitLPercent+"%");
      sitC.setValue(sitCPercent+"%");
      sitJC.setValue(sitJCPercent+"%");
    }

    int distanceNum = distanceY.getNum()+distanceL.getNum()+distanceC.getNum()+distanceJC.getNum();
    if(distanceNum!=0){
      String distanceYPercent = String.format("%.1f", (double) distanceY.getNum() / distanceNum * 100);
      String distanceLPercent = String.format("%.1f", (double) distanceL.getNum() / distanceNum * 100);
      String distanceCPercent = String.format("%.1f", (double) distanceC.getNum() / distanceNum * 100);
      String distanceJCPercent =String.format("%.1f",100-Double.parseDouble(distanceYPercent)-Double.parseDouble(distanceLPercent)
              -Double.parseDouble(distanceCPercent));
      distanceY.setValue(distanceYPercent+"%");
      distanceL.setValue(distanceLPercent+"%");
      distanceC.setValue(distanceCPercent+"%");
      distanceJC.setValue(distanceJCPercent+"%");
    }
    /**
     *  统计分数
     */
    Map<String,Object> resulyMap = new HashMap<>();
    if(lightNum==0) resulyMap.put("loghtF","0.0");
    else {
       String loghtF = String.format("%.1f", (double) (lightY.getNum() * YOU +
                                                       lightL.getNum() * LIANG +
                                                        lightC.getNum() * CHA + lightJC.getNum() * JICHA) *100/ lightNum / YOU);
      resulyMap.put("loghtF",loghtF);
    }
    if(sitNum==0) resulyMap.put("sitF","0.0");
    else {
      String sitF = String.format("%.1f", (double) (sitY.getNum() * YOU +
              sitL.getNum() * LIANG +
              sitC.getNum() * CHA +
              sitJC.getNum() * JICHA) *100/ sitNum / YOU);
      resulyMap.put("sitF",sitF);
    }
    if(distanceNum==0) resulyMap.put("distanceF","0.0");
    else {
      String distanceF = String.format("%.1f", (double) (distanceY.getNum() * YOU +
              distanceL.getNum() * LIANG +
              distanceC.getNum() * CHA +
              distanceJC.getNum() * JICHA) *100/ distanceNum/YOU);
      resulyMap.put("distanceF",distanceF);
    }
    List<EchartsData> lights = Arrays.asList(lightY,lightL,lightC,lightJC);
    List<EchartsData> sits = Arrays.asList(sitY,sitL,sitC,sitJC);
    List<EchartsData> distances = Arrays.asList(distanceY,distanceL,distanceC,distanceJC);
    resulyMap.put("lights",lights);
    resulyMap.put("sits",sits);
    resulyMap.put("distances",distances);
    return resulyMap;
  }


   static class EchartsData{
    private String name; //类型名 优 良 中 差 极差
    private Integer num;//点数
    private String value;//点数占比

     public EchartsData(String name) {
        this.name=name;
        this.num=0;
        this.value="0.0%";
     }

     public String getName() {
       return name;
     }
     public void setName(String name) {
       this.name = name;
     }
     public Integer getNum() {
       return num;
     }
     public void setNum(Integer num) {
       this.num = num;
     }
     public String getValue() {
       return value;
     }
     public void setValue(String value) {
       this.value = value;
     }
   }

//   static class Data{
//     private Long userId;
//     private String school;
//     private String grade;
//     private String date;
//     private String sn;
//     private String ln;
//     private String dn;
//     private Double loghtF;
//     private Double distanceF;
//     private Double sitF;
//
//     public Long getUserId() {
//       return userId;
//     }
//
//     public void setUserId(Long userId) {
//       this.userId = userId;
//     }
//
//     public String getSchool() {
//       return school;
//     }
//
//     public void setSchool(String school) {
//       this.school = school;
//     }
//
//     public String getGrade() {
//       return grade;
//     }
//
//     public void setGrade(String grade) {
//       this.grade = grade;
//     }
//
//     public String getDate() {
//       return date;
//     }
//
//     public void setDate(String date) {
//       this.date = date;
//     }
//
//     public String getSn() {
//       return sn;
//     }
//
//     public void setSn(String sn) {
//       this.sn = sn;
//     }
//
//     public String getLn() {
//       return ln;
//     }
//
//     public void setLn(String ln) {
//       this.ln = ln;
//     }
//
//     public String getDn() {
//       return dn;
//     }
//
//     public void setDn(String dn) {
//       this.dn = dn;
//     }
//
//     public Double getLoghtF() {
//       return loghtF;
//     }
//
//     public void setLoghtF(Double loghtF) {
//       this.loghtF = loghtF;
//     }
//
//     public Double getDistanceF() {
//       return distanceF;
//     }
//
//     public void setDistanceF(Double distanceF) {
//       this.distanceF = distanceF;
//     }
//
//     public Double getSitF() {
//       return sitF;
//     }
//
//     public void setSitF(Double sitF) {
//       this.sitF = sitF;
//     }
//   }

  /**
   * zip文件下载
   */
  public static void craeteZipPath(String path,HttpServletResponse response) throws IOException{

    ZipOutputStream zipOutputStream = null;
    OutputStream output=response.getOutputStream();
//        response.reset();
    response.setHeader("Content-disposition", "attachment; filename="+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".zip");
    response.setContentType("application/zip");
    zipOutputStream = new ZipOutputStream(output, Charset.forName("GBK"));
    File[] files = new File(path).listFiles();
    FileInputStream fileInputStream = null;
    byte[] buf = new byte[1024];
    int len = 0;
    if(files!=null && files.length > 0){
      for(File wordFile:files){
        String fileName = wordFile.getName();
        fileInputStream = new FileInputStream(wordFile);
        //放入压缩zip包中;
        zipOutputStream.putNextEntry(new ZipEntry(fileName));

        //读取文件;
        while((len=fileInputStream.read(buf)) >0){
          zipOutputStream.write(buf, 0, len);
        }
        //关闭;
        zipOutputStream.closeEntry();
        if(fileInputStream != null){
          fileInputStream.close();
        }
      }
    }

    if(zipOutputStream !=null){
      zipOutputStream.close();
    }
  }

  @GetMapping("/add")
  @RequiresPermissions("information:dataEveryday:add")
  String add() {
    return "information/dataEveryday/add";
  }

  @GetMapping("/edit/{id}")
  @RequiresPermissions("information:dataEveryday:edit")
  String edit(@PathVariable("id") Integer id, Model model) {
    DataEverydayDO dataEveryday = dataEverydayService.get(id);
    model.addAttribute("dataEveryday", dataEveryday);
    return "information/dataEveryday/edit";
  }

  /** 保存 */
  @ResponseBody
  @PostMapping("/save")
  @RequiresPermissions("information:dataEveryday:add")
  public R save(DataEverydayDO dataEveryday) {
    if (dataEverydayService.save(dataEveryday) > 0) {
      return R.ok();
    }
    return R.error();
  }
  /** 修改 */
  @ResponseBody
  @RequestMapping("/update")
  @RequiresPermissions("information:dataEveryday:edit")
  public R update(DataEverydayDO dataEveryday) {
    dataEverydayService.update(dataEveryday);
    return R.ok();
  }

  /** 删除 */
  @PostMapping("/remove")
  @ResponseBody
  @RequiresPermissions("information:dataEveryday:remove")
  public R remove(Integer id) {
    if (dataEverydayService.remove(id) > 0) {
      return R.ok();
    }
    return R.error();
  }

  /** 删除 */
  @PostMapping("/batchRemove")
  @ResponseBody
  @RequiresPermissions("information:dataEveryday:batchRemove")
  public R remove(@RequestParam("ids[]") Integer[] ids) {
    dataEverydayService.batchRemove(ids);
    return R.ok();
  }
}
