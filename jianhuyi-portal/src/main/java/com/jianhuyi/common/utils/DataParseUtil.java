package com.jianhuyi.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.jianhuyi.common.utils.domain.*;
import com.jianhuyi.information.domain.DistanceDO;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/** 数据解析工具 */
public class DataParseUtil {
  // 包头
  public static byte[] BT = new byte[] {(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A};
  // 包尾
  public static byte[] BW = new byte[] {0x0d, 0x0a, 0x0a, 0x0d};

  // 阅读状态包头
  public static byte[] ZT = new byte[] {(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x50};
  // 基本信息包头 距离 光照 角度
  public static byte[] BASE_DATA = new byte[] {(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x51};
  // 摄像头包头
  public static byte[] SXT = new byte[] {(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x54};
  // 电源信息包头
  public static byte[] DY = new byte[] {(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x55};
  // 震动提醒包头
  public static byte[] ZDTX = new byte[] {(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x58};
  // 报错提醒包头
  public static byte[] ERROER = new byte[] {(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x59};

  public static byte[] PIC_BT = new byte[] {(byte) 0xff, (byte) 0xd8};
  public static byte[] PIC_BW = new byte[] {(byte) 0xff, (byte) 0xd9};
  //16分位阅读距离包头
  public static byte[] DATA_BT=new byte[]{(byte) 0xAA,(byte)0x55,(byte)0xA5,(byte)0x5A,(byte)0x53};
  //16分位阅读距离包尾
  public static byte[] DATA_BW=new byte[]{(byte)0x0d,(byte)0x0a,(byte)0x0a,(byte)0x0d};

  /**
   * 读取文件
   *
   * @param file
   */
  public static HistoryDataBean readFileTo(File file) {
    Integer uploadId = 0;
    String hexString = readFile(file.getAbsolutePath());
    if (hexString.contains("sourceData")) {
      JSONObject jsonObject = JSONObject.parseObject(hexString);
      uploadId = (Integer) jsonObject.get("uploadId");
      hexString = jsonObject.get("sourceData").toString();
      hexString = hexString.replace(" ", "");
    } else {
      hexString = hexString.replace(" ", "");
    }
    return parseData(hexStringToByteArray(hexString), uploadId);
  }

  private static HistoryDataBean parseData(byte[] bytes, Integer uploadId) {

    HistoryDataBean historyDataBean = new HistoryDataBean();
    if (uploadId > 0) {
      historyDataBean.setUploadId(uploadId);
    }
    try {

      List<SerialDataBean> datas = new ArrayList();

      List<EnergyBean> energys = new ArrayList();

      List<ErrorBean> errors = new ArrayList();

      List<RemindBean> reminds = new ArrayList();
      List<DistanceDO> distanceDOS =  new ArrayList<>();

      SerialDataBean serialDataBean = null;
      for (int i = 0; i < bytes.length; i++) {

        // 剩余长度大于5
        if (i < bytes.length - 5) {
          // 验证包头
          byte[] ztbt = subBytes(bytes, i, 5);
          if (Arrays.equals(ztbt, ZT)) {
            // 验证包尾
            if (i < bytes.length - 16 && Arrays.equals(subBytes(bytes, i + 12, 4), BW)) {
              if (serialDataBean != null) { // 把上一个阅读状态添加到集合
                datas.add(serialDataBean);
              }
              // 新的阅读状态
              serialDataBean = new SerialDataBean();
              serialDataBean.setStartTime(getTime(bytes, i + 6, true));
              int status = bytes[i + 5] & 0xff;
              serialDataBean.setStatus(status);
              List<BaseDataBean> baseDatas = new ArrayList<>();
              serialDataBean.setBaseDatas(baseDatas);
              List<PictureBean> pictureBeans = new ArrayList<>();
              serialDataBean.setPictures(pictureBeans);
            }
          }
          // 验证包头
          else if (Arrays.equals(subBytes(bytes, i, 5), BASE_DATA)) { // 基础数据  倾斜角 距离 光感
            // 验证包尾
            if (i < bytes.length - 21 && Arrays.equals(subBytes(bytes, i + 17, 4), BW)) {
              if (serialDataBean != null) {
                serialDataBean.getBaseDatas().add(getBaseData(bytes, i));
              }
            }
          }
          // 验证包头
          else if (Arrays.equals(subBytes(bytes, i, 5), SXT)) { // 摄像头图片
            if (serialDataBean != null) {
              serialDataBean.getPictures().add(getPic(bytes, i));
            }
          }
          // 验证包头
          else if (Arrays.equals(subBytes(bytes, i, 5), ZDTX)) { // //震动信息
            // 验证包尾
            if (i < bytes.length - 16 && Arrays.equals(subBytes(bytes, i + 12, 4), BW)) {
              reminds.add(getRemind(bytes, i));
            }
          }
          // 验证包头
          else if (Arrays.equals(subBytes(bytes, i, 5), DY)) { // //电源信息
            // 验证包尾
            if (i < bytes.length - 29 && Arrays.equals(subBytes(bytes, i + 25, 4), BW)) {
              energys.add(getEnergy(bytes, i, false));
            } else if (i < bytes.length - 33 && Arrays.equals(subBytes(bytes, i + 29, 4), BW)) {
              energys.add(getEnergy(bytes, i, true));
            }
          }
          // 验证包头
          else if (Arrays.equals(subBytes(bytes, i, 5), ERROER)) { // //报错信息
            // 验证包尾
            if (i < bytes.length - 20 && Arrays.equals(subBytes(bytes, i + 16, 4), BW)) {
              errors.add(getError(bytes, i));
            }
          }


          // 16分位阅读距离验证包头
          else if (Arrays.equals(subBytes(bytes, i, 5), DATA_BT)) { //16分位阅读距离
            // 16分位阅读距离验证包尾
            if (i < bytes.length - 41 && Arrays.equals(subBytes(bytes, i + 37, 4), DATA_BW)) {
              /**
               *  解析16分位的阅读距离数据
               */
              distanceDOS.add(getDistanceDO(bytes,i));

            }
          }

        }
      }

      if (serialDataBean != null) { // 把最后一个阅读状态添加进去
        datas.add(serialDataBean);
      }

      historyDataBean.setDataDOList(datas);

      historyDataBean.setEnergysDataDOList(energys);

      historyDataBean.setErrorDataDOList(errors);

      historyDataBean.setRemaind(reminds);
      historyDataBean.setDistanceDOS(distanceDOS);

      return historyDataBean;

      // 解析完成
    } catch (Exception e) {
      // 解析异常
      e.printStackTrace();
      return null;
    }
  }

  private static DistanceDO getDistanceDO(byte[] bytes, int i) {
    Double[] doubles = new Double[16];
    byte[] bytes1 = Arrays.copyOfRange(bytes, i + 5, i + 37);
    int[] byteH=new int[16],byteL=new int[16];
    for(int j=0,size=bytes1.length;j<size;j++){
      if(j%2==0){//高字节
        byteH[j/2]=bytes1[j] & 0xff;
      }else{//低字节
        byteL[(j-1)/2]=bytes1[j] & 0xff;
      }
    }
    for(int j=0;j<16;j++){
      if(byteH[j]>0)
        doubles[j]=Double.valueOf( byteH[j]<< 8 | byteL[j] );
      else
        doubles[j]=Double.valueOf(byteL[j] );
    }
    /**
     *  根据不同的阅读状态 统计实际阅读距离 但并没有四舍五入 可以考虑四舍五入
     */
    int   m=0;//数组中小于120mm的数据
    List<Double> doublesY= new ArrayList<>();
    List<Double> doubleW = new ArrayList<>();
    for(int k=0,length=doubles.length;k<length;k++){
      double d = doubles[k];
      if(d!=0) doublesY.add(d);
      if(d<120 && d>0) m++;
      if(d>=120) doubleW.add(d);
    }
    int type=0,size = doublesY.size();
    double distance=0.0;//实际阅读距离
    if(m>=0.8*size){//全遮挡
      type=0;
      if(size>3)
        distance = doublesY.stream().sorted().limit(size-3).mapToDouble(Double::doubleValue).average().orElse(0);
      else
        distance=doublesY.stream().sorted().mapToDouble(Double::doubleValue).average().orElse(0);

    }else if(m<0.8*size && m>0) {//半遮挡
      type = 1;
      if (doubleW.size() > 3)
        distance = doubleW.stream().sorted(Comparator.comparingDouble(Double::doubleValue)).limit(size - 3).mapToDouble(Double::doubleValue).average().orElse(0);
      else
        distance = doubleW.stream().sorted(Comparator.comparingDouble(Double::doubleValue)).mapToDouble(Double::doubleValue).average().orElse(0);
    }else if(m==0){
      type=2;
      if(size>3)
        distance = doublesY.stream().sorted().limit(size-3).mapToDouble(Double::doubleValue).average().orElse(0);
      else
        distance=doublesY.stream().sorted().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    DistanceDO distanceDO = new DistanceDO();
    distanceDO.setType(type);
    distanceDO.setDistance(distance);
    distanceDO.setDistanceData(Arrays.toString(doubles));
    return distanceDO;
  }

  /** 解析并返回基础数据 距离 光照 倾斜角 */
  private static BaseDataBean getBaseData(byte[] bytes, int index) {
    BaseDataBean baseDataBean = new BaseDataBean();
    // 时间
    baseDataBean.setTime(getTime(bytes, index + 5, true));
    // 倾斜方向
    baseDataBean.setDeflection(bytes[index + 11] & 0xff);
    // 倾斜角度
    baseDataBean.setAngles(Double.parseDouble((bytes[index + 12] & 0xff) + ""));
    // 光强
    int light_h = bytes[index + 13] & 0xFF;
    int light_l = bytes[index + 14] & 0xFF;
    Double lights;
    if (light_h > 0) {
      lights = Double.parseDouble(((light_h << 8) + light_l) + "");
    } else {
      lights = Double.parseDouble(light_l + "");
    }
    baseDataBean.setLights(lights);
    // 测距
    int distance_h = bytes[index + 15] & 0xFF;
    int distance_l = bytes[index + 16] & 0xFF;
    Double distances;
    if (distance_h > 0) {
      distances = Double.parseDouble((distance_h << 8) + distance_l + "");
    } else {
      distances = Double.parseDouble(distance_l + "");
    }
    baseDataBean.setDistances(distances);

    return baseDataBean;
  }

  /** 解析并返回图片 */
  private static PictureBean getPic(byte[] bytes, int index) {
    PictureBean serialPic = new PictureBean();

    serialPic.setTime(getTime(bytes, index + 5, true));

    int startIndex = -1;
    int endIndex = -1;
    for (int i = index; i < bytes.length; i++) {
      if (i < bytes.length - 1) {
        if (Arrays.equals(subBytes(bytes, i, 2), PIC_BT)) { // 包头
          startIndex = i;
        }
        if (Arrays.equals(subBytes(bytes, i, 2), PIC_BW)) { // 包尾
          endIndex = i + 2;
          break;
        }
      }
    }

    if (startIndex != -1 && endIndex != -1) {
      if ((endIndex - startIndex) > 100) {
        byte[] picBytes = subBytes(bytes, startIndex, endIndex - startIndex);
        if (picBytes.length < (60 * 1024)) {
          String time = getTime(bytes, index + 5, false);
          serialPic.setFilename(
              new HexImageUtil().saveToImage(formatHexString(picBytes, false), time));
        } else {
          serialPic.setFilename("");
        }

      } else {
        serialPic.setFilename("");
      }
    }

    return serialPic;
  }

  /** 电源信息 */
  private static EnergyBean getEnergy(byte[] bytes, int index, boolean length) {
    EnergyBean energy = new EnergyBean();
    int serialNumberH = (bytes[index + 5] & 0xff);
    int serialNumberL = (bytes[index + 6] & 0xff);
    int serialNumber = (serialNumberH << 8) + serialNumberL;

    energy.setSerialNumber(serialNumber);
    energy.setUsbStatus(bytes[index + 8] & 0xff);
    //        Log.e(TAG, "电源信息时间")
    energy.setTime(getTime(bytes, index + 9, true));

    int runningTimeH = bytes[index + 15] & 0xFF;
    int runningTimeL = bytes[index + 16] & 0xFF;
    int runningTime;
    if (runningTimeH > 0) {
      runningTime = ((runningTimeH << 8) + runningTimeL);
    } else {
      runningTime = runningTimeL;
    }
    energy.setRunningTime(runningTime);

    int coverTimeH = bytes[index + 17] & 0xFF;
    int coverTimeL = bytes[index + 18] & 0xFF;
    int coverTime;
    if (coverTimeH > 0) {
      coverTime = ((coverTimeH << 8) + coverTimeL);
    } else {
      coverTime = coverTimeL;
    }
    energy.setCoverTime(coverTime);

    int effectiveTimeH = bytes[index + 19] & 0xFF;
    ;
    int effectiveTimeL = bytes[index + 20] & 0xFF;
    int effectiveTime;
    if (effectiveTimeH > 0) {
      effectiveTime = ((effectiveTimeH << 8) + effectiveTimeL);
    } else {
      effectiveTime = effectiveTimeL;
    }
    energy.setEffectiveTime(effectiveTime); // 有效时长

    int readTimeH = bytes[index + 21] & 0xFF;
    int readTimeL = bytes[index + 22] & 0xFF;
    int readTime;
    if (readTimeH > 0) {
      readTime = (readTimeH << 8) + readTimeL;
    } else {
      readTime = readTimeL;
    }
    energy.setReadTime(readTime); // 阅读时长

    int unReadTimeH = bytes[index + 23] & 0xFF;
    int unReadTimeL = bytes[index + 24] & 0xFF;
    int unReadTime;
    if (unReadTimeH > 0) {
      unReadTime = (unReadTimeH << 8) + unReadTimeL;
    } else {
      unReadTime = unReadTimeL;
    }
    energy.setUnreadTime(unReadTime); // 非阅读时长

    // 包长是否大于33
    int noneEffective = 0;
    if (length) {
      int noneEffectiveH = bytes[index + 25] & 0xFF;
      int noneEffectiveL = bytes[index + 26] & 0xFF;
      noneEffective = (noneEffectiveH << 8) + noneEffectiveL;
    }
    energy.setNoneffectiveTime(noneEffective);

    return energy;
  }

  /** 解析并返回震动提醒 */
  private static RemindBean getRemind(byte[] bytes, int index) {
    RemindBean remind = new RemindBean();
    remind.setType(bytes[index + 5] & 0xff);
    remind.setTime(getTime(bytes, index + 6, true));
    return remind;
  }

  /** 解析并返回错误信息 */
  private static ErrorBean getError(byte[] bytes, int index) {
    ErrorBean error = new ErrorBean();
    error.setTime(getTime(bytes, index + 5, true));
    error.setStoreError(bytes[index + 11] & 0xff);
    error.setMemsError(bytes[index + 12] & 0xff);
    error.setSxterror(bytes[index + 13] & 0xff);
    error.setDisError(bytes[index + 14] & 0xff);
    error.setLightError(bytes[index + 15] & 0xff);
    return error;
  }

  /**
   * 解析时间
   *
   * @return
   */
  private static String getTime(byte[] bytes, int index, boolean divide) {
    if (bytes.length < 10) return "";
    //        年
    String time = "";
    int year = bytes[index] & 0xff;
    if (year > 99) {
      time = "2099";
    } else {
      time = "20" + year;
    }
    //        月
    int month = bytes[index + 1] & 0xff;
    if (divide) {
      time += "-";
    }
    if (month < 10) {
      time += "0" + month;
    } else {
      time += "" + month;
    }
    //        日
    int day = bytes[index + 2] & 0xff;
    if (divide) {
      time += "-";
    }
    if (day < 10) {
      time += "0" + day;
    } else {
      time += "" + day;
    }
    //        时
    int hour = bytes[index + 3];
    if (divide) {
      time += " ";
    } else {
      time += "_";
    }
    if (hour < 10) {
      time += "0" + hour;
    } else {
      time += "" + hour;
    }
    //        分
    int minute = bytes[index + 4];
    if (divide) {
      time += ":";
    }
    if (minute < 10) {
      time += "0" + minute;
    } else {
      time += "" + minute;
    }
    //        喵
    int second = bytes[index + 5];
    if (divide) {
      time += ":";
    }
    if (second < 10) {
      time += "0" + second;
    } else {
      time += "" + second;
    }

    return time;
  }

  /**
   * 字节数组 转 hexString
   *
   * @param data
   * @param addSpace
   * @return
   */
  public static String formatHexString(byte[] data, boolean addSpace) {
    if (data == null || data.length < 1) return null;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      String hex = Integer.toHexString(data[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex);
      if (addSpace) sb.append(" ");
    }
    return sb.toString().trim();
  }

  /**
   * hexString 转 字节数组
   *
   * @param hexStr
   * @return
   */
  public static byte[] hexStringToByteArray(String hexStr) {
    int len = (int) Math.ceil(hexStr.length() / 2.0);
    byte[] data = new byte[len];

    int j = 0;
    int i = 0;
    while (i < hexStr.length()) {
      data[j] =
          (byte)
              ((Character.digit(hexStr.charAt(i), 16) << 4)
                  + Character.digit(hexStr.charAt(i + 1), 16));
      j++;
      i += 2;
    }

    return data;
  }

  /**
   * 截取byte[]
   *
   * @param src
   * @param begin 开始的标
   * @param count 长度
   * @return
   */
  public static byte[] subBytes(byte[] src, int begin, int count) {
    byte[] bs = new byte[count];
    System.arraycopy(src, begin, bs, 0, count);
    return bs;
  }

  /**
   * 将文件读取为String
   *
   * @return
   * @throws IOException
   */
  public static String readFile(String url) {
    File file = new File(url);
    FileInputStream is = null;
    StringBuilder stringBuilder = null;
    try {
      if (file.length() != 0) {
        /** 文件有内容才去读文件 */
        is = new FileInputStream(file);
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
          stringBuilder.append(line);
        }
        reader.close();
        is.close();
      } else {

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return String.valueOf(stringBuilder);
  }
}
