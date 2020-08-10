package com.jianhuyi.device.controller;


import com.jianhuyi.common.utils.R;
import com.jianhuyi.common.utils.ShiroUtils;
import com.jianhuyi.device.domain.DeviceDO;
import com.jianhuyi.device.service.DeviceService;
import com.jianhuyi.owneruser.domain.OwnerUserDO;
import com.jianhuyi.owneruser.service.OwnerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户设备表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-18 16:22:42
 */

@RestController
@RequestMapping("/jianhuyi/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    OwnerUserService userService;

    /**
     * 设备列表接口
     */
    @GetMapping("/list")
    R list(String identity) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("identity", identity);
        List<DeviceDO> list = deviceService.list(params);
        Map<String, Object> map = new HashMap<String, Object>();

        if (list.size() > 0 && list.get(0).getUserId() != null) {
            OwnerUserDO userDO = userService.get(list.get(0).getUserId());
            map.put("data", userDO);
            return R.ok(map);
        } else {
            return R.error("设备号不存在或设备号未绑定");
        }

    }

    @GetMapping("/devicelist")
    R devicelist(Long userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        List<DeviceDO> list = deviceService.list(params);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("data", list);
        return R.ok(map);

    }

    /**
     * 设备添加接口
     */
    @PostMapping("/addBymac")
    Map<String, Object> addBymac(String mac) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mac", mac);
        List<DeviceDO> list = deviceService.list(params);
        if (list.size() == 0) {
            map.put("msg", "设备不存在，添加失败");
            map.put("code", -1);
            map.put("data", "error");
        } else {
            DeviceDO deviceDO = new DeviceDO();
            deviceDO.setAccount(ShiroUtils.getUser().getPhone());
            deviceDO.setMac(mac);
            if (deviceService.update(deviceDO) > 0) {
                map.put("msg", "设备添加成功");
                map.put("code", 0);
                map.put("data", "success");
            }
        }
        return map;
    }

    @PostMapping("/addByidentity")
    Map<String, Object> addByidentity(String identity, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("identity", identity);
        List<DeviceDO> list = deviceService.list(params);
        System.out.println("===========list==============================" + list.size());
        if (list.size() == 0) {
            map.put("msg", "设备不存在，添加失败");
            map.put("code", -1);
            map.put("data", "");
        } else if (list.size() == 1) {
            if (list.get(0).getUserId() != null && !list.get(0).getUserId().equals("")) {
                map.put("msg", "设备已绑定");
                map.put("code", -1);
            } else {
                DeviceDO deviceDO = new DeviceDO();
                String phone = userService.get(userId).getPhone();
                if (phone != null) {
                    deviceDO.setAccount(phone);
                }
                deviceDO.setUserId(userId);
                deviceDO.setIdentity(identity);
                if (deviceService.updateByidentity(deviceDO) > 0) {
                    map.put("msg", "设备添加成功");
                    map.put("code", 0);
                    map.put("data", "success");
                } else {
                    map.put("msg", "设备绑定失败");
                    map.put("code", -1);
                }
            }
        }
        return map;
    }


    @PostMapping("/delete")
    Map<String, Object> delete(String identity) {
        Map<String, Object> map = new HashMap<String, Object>();

        DeviceDO deviceDO = new DeviceDO();
        deviceDO.setAccount("");
        deviceDO.setUserId(null);
        deviceDO.setIdentity(identity);
        if (deviceService.updateByidentity(deviceDO) > 0)
            map.put("msg", "操作成功");
        map.put("code", 0);
        map.put("data", "success");

        return map;
    }

    @GetMapping("isExist")
    R isExist(String identity) {
        Map<String, Object> params = new HashMap<String, Object>();

        if (identity != null && identity != "") {
            params.put("identity", identity);
            if (deviceService.list(params).size() > 0) {
                return R.error("已存在，请修改");
            } else {
                return R.ok("暂无,可添加");
            }
        } else {
            return R.error("请输入设备号");
        }

    }

    @PostMapping("/addDevice")
    R addDevice(DeviceDO deviceDO) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (deviceDO != null) {
            deviceDO.setDeleted(0);
            deviceDO.setDefaultDevice(1);
            deviceDO.setDeviceType(0);
            if (deviceService.save(deviceDO) > 0) {
                return R.ok();
            } else {
                return R.error("添加失败");
            }
        } else {
            return R.error("请输入设备号");
        }
    }

}
