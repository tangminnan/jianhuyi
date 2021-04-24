package com.jianhuyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties(prefix="jianhuyi")
public class BootdoConfig {
	//上传路径
	private String uploadPath;
	private String poiword;

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getPoiword() {
		File file =new File(poiword);
		if(!file.exists())
			file.mkdirs();
		return poiword;
	}

	public void setPoiword(String poiword) {
		this.poiword = poiword;
	}
}
