package com.jianhuyi.information.controller;

import com.jianhuyi.common.utils.OBSUtils;
import com.jianhuyi.common.utils.PageUtils;
import com.jianhuyi.common.utils.Query;
import com.jianhuyi.common.utils.R;
import com.jianhuyi.information.domain.GiftDO;
import com.jianhuyi.information.service.GiftService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:12
 */

@Controller
@RequestMapping("/information/gift")
public class GiftController {
    @Autowired
    private GiftService giftService;

    @GetMapping()
    @RequiresPermissions("information:gift:gift")
    String Gift() {
        return "information/gift/gift";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:gift:gift")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<GiftDO> giftList = giftService.list(query);
        int total = giftService.count(query);
        PageUtils pageUtils = new PageUtils(giftList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("information:gift:add")
    String add() {
        return "information/gift/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:gift:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        GiftDO gift = giftService.get(id);
        model.addAttribute("gift", gift);
        return "information/gift/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:gift:add")
    public R save(GiftDO gift) {
        if (gift.getImgFile() != null && gift.getImgFile().getSize() > 0) {
            String fileName = OBSUtils.uploadFile(gift.getImgFile(), "jianhuyi/giftCover/");
            gift.setCoverImg(fileName);
        }

        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        gift.setGiftId(sd.format(new Date()));
        gift.setCreateTime(new Date());
        if (giftService.save(gift) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("information:gift:edit")
    public R update(GiftDO gift) {
        if (gift.getImgFile() != null && gift.getImgFile().getSize() > 0) {
            String fileName = OBSUtils.uploadFile(gift.getImgFile(), "jianhuyi/giftCover/");
            gift.setCoverImg(fileName);
        }
        giftService.update(gift);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:gift:remove")
    public R remove(Long id) {
        if (giftService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("information:gift:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        giftService.batchRemove(ids);
        return R.ok();
    }

    @PostMapping("/saveImg")
    @ResponseBody
    public R remove(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        String fileName = OBSUtils.uploadFile(file, "jianhuyi/giftDetail/");
        result.put("fileName", fileName);
        return R.ok(result);
    }

}
