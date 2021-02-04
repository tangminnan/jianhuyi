package com.jianhuyi.common.utils;

import com.jianhuyi.common.utils.domain.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据解析工具
 */
public class DataParseUtil {
    //包头
    public static byte[] BT = new byte[]{(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A};
    //包尾
    public static byte[] BW = new byte[]{0x0d, 0x0a, 0x0a, 0x0d};


    // 阅读状态包头
    public static byte[] ZT = new byte[]{(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x50};
    //基本信息包头 距离 光照 角度
    public static byte[] BASE_DATA = new byte[]{(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x51};
    //摄像头包头
    public static byte[] SXT = new byte[]{(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x54};
    //电源信息包头
    public static byte[] DY = new byte[]{(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x55};
    //震动提醒包头
    public static byte[] ZDTX = new byte[]{(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x58};
    //报错提醒包头
    public static byte[] ERROER = new byte[]{(byte) 0xAA, 0x55, (byte) 0xA5, 0x5A, 0x59};


    public static byte[] PIC_BT = new byte[]{(byte) 0xff, (byte) 0xd8};
    public static byte[] PIC_BW = new byte[]{(byte) 0xff, (byte) 0xd9};

    /**
     * 读取文件
     *
     * @param file
     */
    public static HistoryDataBean readFileTo(File file) {

        String hexString = readFile(file.getAbsolutePath());
        hexString = hexString.replace(" ", "");
        return parseData(hexStringToByteArray(hexString));
    }


    private static HistoryDataBean parseData(byte[] bytes) {

        HistoryDataBean historyDataBean = new HistoryDataBean();
        try {

            List<SerialDataBean> datas = new ArrayList();

            List<EnergyBean> energys = new ArrayList();

            List<ErrorBean> errors = new ArrayList();

            List<RemindBean> reminds = new ArrayList();

            SerialDataBean serialDataBean = null;
            for (int i = 0; i < bytes.length; i++) {

                //剩余长度大于5
                if (i < bytes.length - 5) {
                    //验证包头
                    byte[] ztbt =subBytes(bytes, i,  5);
                    if (Arrays.equals(ztbt,ZT)) {
                        //验证包尾
                        if (i < bytes.length - 16 && Arrays.equals(subBytes(bytes, i + 12,  4),BW)) {
                            if (serialDataBean != null) {//把上一个阅读状态添加到集合
                                datas.add(serialDataBean);
                            }
                            //新的阅读状态
                            serialDataBean = new SerialDataBean();
                            serialDataBean.setStartTime(getTime(bytes, i + 6, true));
                            int status =bytes[i+5] & 0xff;
                            serialDataBean.setStatus(status);
                            List<BaseDataBean> baseDatas =new ArrayList<>();
                            serialDataBean.setBaseDatas(baseDatas);
                            List<PictureBean> pictureBeans =new ArrayList<>();
                            serialDataBean.setPictures(pictureBeans);
                        }
                    }
                    //验证包头
                    else if (Arrays.equals(subBytes(bytes, i, 5),BASE_DATA)) {//基础数据  倾斜角 距离 光感
                        //验证包尾
                        if (i < bytes.length - 21 && Arrays.equals(subBytes(bytes, i + 17, 4),BW)) {
                            serialDataBean.getBaseDatas().add(getBaseData(bytes, i));
                        }
                    }
                    //验证包头
                    else if (Arrays.equals(subBytes(bytes, i, 5),SXT)) {//摄像头图片
                        serialDataBean.getPictures().add(getPic(bytes, i));
                    }
                    //验证包头
                    else if (Arrays.equals(subBytes(bytes, i, 5),ZDTX)) {////震动信息
                        //验证包尾
                        if (i < bytes.length - 16 && Arrays.equals(subBytes(bytes, i + 12, 4),BW)) {
                            reminds.add(getRemind(bytes, i));
                        }
                    }
                    //验证包头
                    else if (Arrays.equals(subBytes(bytes, i, 5),DY)) {////电源信息
                        //验证包尾
                        if (i < bytes.length - 29 && Arrays.equals(subBytes(bytes, i + 25, 4),BW)) {
                            energys.add(getEnergy(bytes, i));
                        }
                    }
                    //验证包头
                    else if (Arrays.equals(subBytes(bytes, i, 5),ERROER)) {////报错信息
                        //验证包尾
                        if (i < bytes.length - 20 && Arrays.equals(subBytes(bytes, i + 16, 4),BW)) {
                            errors.add(getError(bytes, i));
                        }
                    }
                }
            }

            if (serialDataBean != null) {//把最后一个阅读状态添加进去
                datas.add(serialDataBean);
            }


            historyDataBean.setDataDOList(datas);

            historyDataBean.setEnergysDataDOList(energys);

            historyDataBean.setErrorDataDOList(errors);

            historyDataBean.setRemaind(reminds);

            return historyDataBean;

            //解析完成
        } catch (Exception e) {
            //解析异常
            e.printStackTrace();
            return null;
        }


    }


    /**
     * 解析并返回基础数据 距离 光照 倾斜角
     */
    private static BaseDataBean getBaseData(byte[] bytes, int index) {
        BaseDataBean baseDataBean = new BaseDataBean();
        //时间
        baseDataBean.setTime(getTime(bytes, index + 5, true));
        //倾斜方向
        baseDataBean.setDeflection(bytes[index + 11] & 0xff);
        //倾斜角度
        baseDataBean.setAngles(Double.parseDouble((bytes[index + 12] & 0xff) + ""));
        //光强
        int light_h = bytes[index + 13] & 0xFF;
        int light_l = bytes[index + 14] & 0xFF;
        Double lights;
        if (light_h > 0) {
            lights = Double.parseDouble(((light_h >> 8) + light_l) + "");
        } else {
            lights = Double.parseDouble(light_l + "");
        }
        baseDataBean.setLights(lights);
        //测距
        int distance_h = bytes[index + 15] & 0xFF;
        int distance_l = bytes[index + 16] & 0xFF;
        Double distances;
        if (distance_h > 0) {
            distances = Double.parseDouble((distance_h >> 8) + distance_l + "");
        } else {
            distances = Double.parseDouble(distance_l + "");
        }
        baseDataBean.setDistances(distances);

        return baseDataBean;
    }

    /**
     * 解析并返回图片
     */
    private static PictureBean getPic(byte[] bytes, int index) {
        PictureBean serialPic = new PictureBean();

        serialPic.setTime(getTime(bytes, index + 5, true));

        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < bytes.length; i++) {
            if (i < bytes.length - 1) {
                if (Arrays.equals(subBytes(bytes, i,  2),PIC_BT)) {//包头
                    startIndex = i;
                }
                if (Arrays.equals(subBytes(bytes, i, 2),PIC_BW)) {//包尾
                    endIndex = i + 2;
                    break;
                }
            }
        }

        if (startIndex != -1 && endIndex != -1) {
            if ((endIndex - startIndex) > 100) {
                byte[] picBytes = subBytes(bytes, startIndex, endIndex-startIndex);
                if (picBytes.length < (60 * 1024)) {
                   String time =  getTime(bytes, index + 5, false);
                    serialPic.setFilename(new HexImageUtil().saveToImage(formatHexString(picBytes, false), time));
                } else {
                    serialPic.setFilename("");
                }

            } else {
                serialPic.setFilename("");
            }
        }

        return serialPic;
    }

    /**
     * 电源信息
     */
    private static EnergyBean getEnergy(byte[] bytes, int index) {
        EnergyBean energy = new EnergyBean();
        energy.setPower(bytes[index + 7] & 0xff);
        energy.setUsbStatus(bytes[index + 8] & 0xff);
//        Log.e(TAG, "电源信息时间")
        energy.setTime(getTime(bytes, index + 9, true));

        int effectiveTimeH = bytes[index + 19] & 0xFF;
        ;
        int effectiveTimeL = bytes[index + 20] & 0xFF;
        int effectiveTime;
        if (effectiveTimeH > 0) {
            effectiveTime = ((effectiveTimeH >> 8) + effectiveTimeL);
        } else {
            effectiveTime = effectiveTimeL;
        }
        energy.setEffectiveTime(effectiveTime); //有效时长

        int readTimeH = bytes[index + 21] & 0xFF;
        int readTimeL = bytes[index + 22] & 0xFF;
        int readTime;
        if (readTimeH > 0) {
            readTime = (readTimeH >> 8) + readTimeL;
        } else {
            readTime = readTimeL;
        }
        energy.setReadTime(readTime);  //阅读时长

        int unReadTimeH = bytes[index + 23] & 0xFF;
        int unReadTimeL = bytes[index + 24] & 0xFF;
        int unReadTime;
        if (unReadTimeH > 0) {
            unReadTime = (unReadTimeH >> 8) + unReadTimeL;
        } else {
            unReadTime = unReadTimeL;
        }
        energy.setUnreadTime(unReadTime); //非阅读时长

        return energy;

    }

    /**
     * 解析并返回震动提醒
     */
    private static RemindBean getRemind(byte[] bytes, int index) {
        RemindBean remind = new RemindBean();
        remind.setType(bytes[index + 5] & 0xff);
        remind.setTime(getTime(bytes, index + 6, true));
        return remind;
    }

    /**
     * 解析并返回错误信息
     */
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
        if (bytes.length < 10)
            return "";
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
        if (data == null || data.length < 1)
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(data[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
            if (addSpace)
                sb.append(" ");
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
        int len = hexStr.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexStr.charAt(i), 16) << 4)
                    + Character.digit(hexStr.charAt(i + 1), 16));
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
                /**
                 * 文件有内容才去读文件
                 */
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