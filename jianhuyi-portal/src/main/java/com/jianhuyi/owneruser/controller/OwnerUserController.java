package com.jianhuyi.owneruser.controller;

import com.jianhuyi.common.annotation.Log;
import com.jianhuyi.common.config.BootdoConfig;
import com.jianhuyi.common.controller.BaseController;
import com.jianhuyi.common.utils.OBSUtils;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.owneruser.domain.OwnerUserDO;
import com.jianhuyi.owneruser.service.OwnerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class OwnerUserController extends BaseController {
  @Autowired OwnerUserService userService;
  @Autowired private BootdoConfig bootdoConfig;

  /**
   * 个人中心
   *
   * @return
   */
  @Log("获取用户信息")
  @GetMapping("/userInfo")
  R user() {
    Map<String, Object> map = new HashMap<>();
    OwnerUserDO udo = userService.get(getUserId());
    if (udo != null) {
      map.put("data", udo);
      return R.ok(map);
    } else {
      return R.sessionOut();
    }
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
    if (user.getFileImg() != null && user.getFileImg().getSize() > 0) {
      String fileName = OBSUtils.uploadFile(user.getFileImg(), "jianhuyi/headUrl/");
      userd.setHeardUrl(fileName);
    }

    userd.setUpdateTime(new Date());
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

  /**
   * 个人中心
   *
   * @return
   */
  @Log("获取用户列表")
  @GetMapping("/userList")
  R userList() {
    Map<String, Object> map = new HashMap<>();

    List<OwnerUserDO> ownerUserDOList = userService.list(null);
    map.put("data", ownerUserDOList);

    return R.ok(map);
  }

  @Log("获取用户列表")
  @GetMapping("/getUserByName")
  R getUserByName(String name, int managerId) {
    Map<String, Object> map = new HashMap<>();
    Map<String, Object> params = new HashMap<>();
//    params.put("name", name);
//    params.put("managerId", managerId);

    List<OwnerUserDO> ownerUserDOList = userService.list(params);
    map.put("data", ownerUserDOList);

    return R.ok(map);
  }
}
