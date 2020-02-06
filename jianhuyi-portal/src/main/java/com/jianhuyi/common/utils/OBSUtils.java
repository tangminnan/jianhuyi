package com.jianhuyi.common.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;


public class OBSUtils {
	
	public static String uploadFile(MultipartFile multipartFile) {
		String url = "";
		try {	
			
			String endPoint = "https://obs.cn-north-4.myhuaweicloud.com";
			String ak = "INH4CIS1OCNGSVVPF1F8";
			String sk = "7Cqofwd0jrGyLI5GHmKHBtaIrUtERILD0bxPpcJ1";
			String bucketname = "jianhuyi";
			
			// 创建ObsClient实例
			ObsClient obsClient = new ObsClient(ak, sk, endPoint);
			/*HeaderResponse createBucket = obsClient.createBucket(bucketname);
			System.out.println(createBucket.getRequestId());*/
			String fileName = multipartFile.getOriginalFilename();
			fileName = FileUtil.renameToUUID(fileName);
            InputStream inputStream = multipartFile.getInputStream();
			PutObjectResult putObject = obsClient.putObject(bucketname, fileName,  inputStream);
			url = putObject.getObjectUrl();
	        /*JSONObject jsonObject = new JSONObject();
	        jsonObject.put("fileName", fileName);
	        jsonObject.put("url", url);*/
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
