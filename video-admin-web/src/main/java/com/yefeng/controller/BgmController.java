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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BgmController.class);

    @Autowired
    private BgmService bgmService;

    @Value("${fileUpload.filePath}")
    private String filePath;

    @GetMapping("/queryBgmList")
    @ResponseBody
    public JsonResult queryBgmList(@Validated BgmPageInputDTO input) {
        return bgmService.queryBgmList(input);
    }

    @PostMapping("/updateBgm")
    @ResponseBody
    public JsonResult updateBgm(@RequestParam(value = "file",required = false) MultipartFile file,
                                @RequestParam("name") String name,
                                @RequestParam("author") String author,
                                @RequestParam("id") String id,
                                @RequestParam("path") String path,
                                @RequestParam("status") Integer status) {

        if (!StringUtil.isEmpty(id) && !StringUtil.isEmpty(name) && !StringUtil.isEmpty(author)) {
            String newFilePath = "";
            if (file != null) {
                try {
                    newFilePath = UploadUtil.updateFile(file, filePath, path);
                } catch (Throwable r) {
                    LOGGER.error("update Upload file error, fileId {} ", id, r);
                }
            }
            Bgm updateBgm = null;
            if (!StringUtil.isEmpty(newFilePath)) {
                updateBgm = genBgm(id, name, author, newFilePath, status);
            } else {
                updateBgm = genBgm(id, name, author, path, status);
            }
            return bgmService.updateBgm(updateBgm);
        } else {
            LOGGER.error("update bgm lack param, id {} name {} author{}", id, name, author);
            return JsonResult.errorMessage("更新Bgm时参数未全部上传");
        }
    }

    @PostMapping("/deleteBgm")
    @ResponseBody
    public JsonResult deleteBgm(@RequestBody Bgm bgm) {
        if (bgm != null && StringUtil.isEmpty(bgm.getId())) {
            UploadUtil.deleteFile(filePath, bgm.getPath());
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

    public static Bgm genBgm(String id, String name, String author, String path, Integer status) {
        Bgm genBgm = new Bgm();
        genBgm.setId(id);
        genBgm.setName(name);
        genBgm.setAuthor(author);
        genBgm.setPath(path);
        genBgm.setStatus(status);
        return genBgm;
    }
}
