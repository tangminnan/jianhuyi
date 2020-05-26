package com.jianhuyi.information.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jianhuyi/wangyi")
public class OtherInterfaceController {
	
	/*static String accessKeyId = "LTAI4FwreKXfeqwD5rxv4dX5";
	static String accessKeySecret = "ki6wo7qTsQVrBtARp2wUWzdl9Kob05";

	static String projectId = "db6d679ba50b4a898631b2a081ad694f";
	static String iterationId = "82ad1af84dec4237b4e1d8d160daaa5a";

//	static String dataUrls = "https://pic20191011.oss-cn-beijing.aliyuncs.com/cg.jpg";	
	
	// 初始化一个client对象
	private static IAcsClient getAscClient() {
		String regionId = "cn-beijing";
		// 创建DefaultAcsClient实例并初始化
		DefaultProfile profile = DefaultProfile.getProfile(regionId, // 地域ID
				accessKeyId, // RAM账号的AccessKey ID
				accessKeySecret); // RAM账号Access Key Secret
		IAcsClient client = new DefaultAcsClient(profile);
		return client;
	}
	
	// 将oss文件添加到训练集
	@PostMapping("/createTrainDataFromUrls")
	Map<String, Object> CreateTrainDataFromUrls(MultipartFile dataUrls){
		String dataUrls2 = OBSUtils.uploadFile(dataUrls,"");
		IAcsClient client = getAscClient();
		Map<String, Object> map = new HashMap<>();
		List<Object> list = new ArrayList<Object>();
		CreateTrainDatasFromUrlsRequest request = new CreateTrainDatasFromUrlsRequest();
		request.setProjectId(projectId);
		request.setUrls(dataUrls2);
		try {
			CreateTrainDatasFromUrlsResponse response = client.getAcsResponse(request);
			for (CreateTrainDatasFromUrlsResponse.TrainData trainData : response.getTrainDatas()) {
				System.out.println("TrainData" + JSON.toJSONString(trainData));
				list.add(trainData);
			}
			map.put("data", list);
			map.put("msg", "");
			map.put("code", 0);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}	
	
	
	// 图片预测
	@PostMapping("/predictImage")
	Map<String, Object> PredictImage(MultipartFile dataUrls) {
		String dataUrls2 = OBSUtils.uploadFile(dataUrls);
		Map<String, Object> map = new HashMap<>();
		List<Object> list = new ArrayList<Object>();
		IAcsClient client = getAscClient();
		PredictImageRequest request = new PredictImageRequest();
		request.setProjectId(projectId);
		request.setIterationId(iterationId);
		request.setDataUrls(dataUrls2);
		request.setAcceptFormat(FormatType.JSON);

		try {
			PredictImageResponse response = client.getAcsResponse(request);
			for (PredictImageResponse.PredictData predictData : response.getPredictDatas()) {
				System.out.println("PredictData: " + JSON.toJSONString(predictData));
				list.add(predictData);
			}
			map.put("data", list);
			map.put("msg", "");
			map.put("code", 0);
		}catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static void main(String[] args) {
		*//*IAcsClient client = getAscClient();
		DescribeProjects(client);
		CreateTrainDataFromUrls(client);
		DescribeTrainDatas(client);
		DescribeIterations(client);
		PredictImage(client, dataUrls);*//*
	}*/
	

}
