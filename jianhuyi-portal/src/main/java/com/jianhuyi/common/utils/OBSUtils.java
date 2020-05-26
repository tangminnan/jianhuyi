package com.jianhuyi.common.utils;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


public class OBSUtils {
	
	
	public static String uploadFile(MultipartFile multipartFile,String urlName) {
		String url = "";
		try {	
			
			String endPoint = "https://obs.cn-north-4.myhuaweicloud.com";
			String ak = "AHTXMQV0QBGQZH6SPK7H";
			String sk = "mwF0OH51ojCEPuthmGz3B15sSeLj7eoAhliPbboS";
			String bucketname = "dm-em03";
			
			// 创建ObsClient实例
			ObsClient obsClient = new ObsClient(ak, sk, endPoint);
			String fileName = multipartFile.getOriginalFilename();
			fileName = FileUtil.renameToUUID(fileName);
            InputStream inputStream = multipartFile.getInputStream();
			PutObjectResult putObject = obsClient.putObject(bucketname, urlName+fileName,  inputStream);
			url = putObject.getObjectUrl();
			System.out.println(url);
			// 使用访问OBS
			        
			// 关闭obsClient
			inputStream.close();
			obsClient.close();
						
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return url;
		
		}
	

}
