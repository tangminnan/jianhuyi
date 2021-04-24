package com.jianhuyi.common.utils;

import java.io.File;
import java.io.FileOutputStream;

/** hex保存为图片 */
public class HexImageUtil {
  private static final String TAG = "HexImageUtil";
  public String IMAGE_PATH = "/www/jianhuyi/init/images/";

  public String saveToImage(String hex, String time) {
    //        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date()) +
    // "monitor.jpg";

    String fileName = "TeasMonitor_" + time + ".jpeg";
    File file = new File(IMAGE_PATH);
    if (!file.exists()) {
      if (!file.exists()) {
        file.mkdirs();
      }
      // 两种方式判断文件夹是否创建成功
      // Folder.isDirectory()返回True表示文件路径是对的，即文件创建成功，false则相反
      boolean isFilemaked1 = file.isDirectory();
      // Folder.mkdirs()返回true即文件创建成功，false则相反
      boolean isFilemaked2 = file.mkdirs();
      /*if (isFilemaked1 || isFilemaked2) {
      } else {
      }*/
    }
    hexToImage(hex, IMAGE_PATH + fileName);
    return IMAGE_PATH + fileName;
  }

  /**
   * hex保存为图片
   *
   * @param src 16进制字符串
   * @param output
   */
  public static void hexToImage(String src, String output) {
    if (src == null || src.length() == 0) {
      return;
    }
    byte[] bytes = stringToByte(src);
    try {
      File file = new File(output);
      FileOutputStream out = new FileOutputStream(file);
      out.write(bytes, 0, bytes.length);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * string转为字节
   *
   * @param s
   * @return
   */
  public static byte[] stringToByte(String s) {
    int length = s.length() / 2;
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; i++) {
      bytes[i] =
          (byte)
              ((Character.digit(s.charAt(i * 2), 16) << 4)
                  | Character.digit(s.charAt((i * 2) + 1), 16));
    }
    return bytes;
  }
}
