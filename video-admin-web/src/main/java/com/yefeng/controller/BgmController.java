package com.yefeng.controller;

import com.yefeng.annotation.LoginRequired;
import com.yefeng.dto.BgmPageInputDTO;
import com.yefeng.dto.LoginUser;
import com.yefeng.pojo.Bgm;
import com.yefeng.pojo.User;
import com.yefeng.service.BgmService;
import com.yefeng.service.UserService;
import com.yefeng.util.JsonResult;
import com.yefeng.util.StringUtil;
import com.yefeng.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@CrossOrigin
@RequestMapping("/api/bgm")
@ResponseBody
public class BgmController {
    @Autowired
    private BgmService bgmService;

    @Value("${fileUpload.filePath}")
    private String filePath;

    @GetMapping("/queryBgmList")
    @ResponseBody
    public JsonResult queryBgmList(@Validated BgmPageInputDTO input) {
        return bgmService.queryBgmList(input);
    }

    @PostMapping("/updateBgmStatus")
    @ResponseBody
    public JsonResult updateBgmStatus(@RequestBody Bgm bgm) {
        if (bgm != null) {
            return bgmService.updateBgmStatus(bgm);
        } else {
            return JsonResult.ok("bgm参数不能为空");
        }
    }

    @PostMapping("/deleteBgm")
    @ResponseBody
    public JsonResult deleteBgm(@RequestBody Bgm bgm) {
        if (bgm != null && StringUtil.isEmpty(bgm.getId())) {
            return bgmService.deleteBgm(bgm);
        } else {
            return JsonResult.ok("bgm参数不能为空");
        }
    }

    @PostMapping("/addBgm")
    @ResponseBody
    public JsonResult addBgm(@RequestParam("file") MultipartFile file,
                             @RequestParam("name") String name,
                             @RequestParam("author") String author) {
        String actualPath = "";
        try {
            actualPath = UploadUtil.uploadBgm(file, filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.errorMessage(e.getMessage());
        }
        if (!StringUtil.isEmpty(actualPath)) {
            return JsonResult.ok(bgmService.addBgm(actualPath, name, author));
        }
        return JsonResult.ok("文件上传失败");
    }
}
