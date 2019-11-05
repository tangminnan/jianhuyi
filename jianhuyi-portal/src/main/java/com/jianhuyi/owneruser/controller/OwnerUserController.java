package com.jianhuyi.owneruser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jianhuyi.common.annotation.Log;
import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.controller.BaseController;
import com.jianhuyi.common.utils.*;
import com.jianhuyi.owneruser.domain.OwnerUserDO;
import com.jianhuyi.owneruser.service.OwnerUserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class OwnerUserController extends BaseController {
    @Autowired
    OwnerUserService userService;
    @Autowired
    private BootdoConfig bootdoConfig;

    /**
     * 个人中心
     *
     * @return
     */
    @Log("获取用户信息")
    @GetMapping("/userInfo")
    Map<String, Object> user() {
        Map<String, Object> map = new HashMap<>();
        OwnerUserDO udo = userService.get(getUserId());
        map.put("data", udo);
        map.put("msg", "success");
        map.put("code", 0);
        return map;
    }


    /**
     * 编辑用户信息
     *
     * @return
     */
    @Log("编辑用户信息")
    @PostMapping("/editInfo")
    Map<String, Object> editInfo(OwnerUserDO user) {
        Map<String, Object> map = new HashMap<>();
        
        	 OwnerUserDO userd = userService.get(getUserId());
             if (user.getHeardUrl() != null) {
                 userd.setHeardUrl(user.getHeardUrl());
             }
             if (user.getNickname() != null) {
                 userd.setNickname(user.getNickname());
             }
             if (user.getUserId() != null) {
                 userd.setUserId(user.getUserId());
             }
             if (user.getSex() != null) {
                 userd.setSex(user.getSex());
             }
             if (user.getBirthday() != null) {
                 userd.setBirthday(user.getBirthday());
             }
             if (user.getPhone() != null) {
                 userd.setPhone(user.getPhone());
             }
             if(user.getFileImg() != null && user.getFileImg().getSize() > 0){
     			MultipartFile sysFile = user.getFileImg();
     			String fileName = sysFile.getOriginalFilename();
     			fileName = FileUtil.renameToUUID(fileName);
     			try {
     				FileUtil.uploadFile(sysFile.getBytes(), bootdoConfig.getUploadPath(), fileName);
     			} catch (IOException e) {
     				e.printStackTrace();
     			} catch (Exception e) {
     				e.printStackTrace();
     			}
     			userd.setHeardUrl("/files/" + fileName);
             }
             
             if (userService.update(userd) > 0) {
                 map.put("msg", "保存成功");
                 map.put("data", "");
                 map.put("code", 0);
             } else {
                 map.put("msg", "保存失败");
                 map.put("data", "");
                 map.put("code", -1);
             }
       
        return map;
    }



}
