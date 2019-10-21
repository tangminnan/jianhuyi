package com.jianhuyi.news.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.news.dao.BannerDao;
import com.jianhuyi.news.domain.BannerDO;
import com.jianhuyi.news.service.BannerService;


@Service
public class BannerServiceImpl implements BannerService {
	@Autowired
	private BannerDao sysFileMapper;

	@Autowired
	private BootdoConfig bootdoConfig;
	@Override
	public BannerDO get(Long id){
		return sysFileMapper.get(id);
	}
	
	@Override
	public List<BannerDO> list(Map<String, Object> map){
		return sysFileMapper.list(map);
	}

}
