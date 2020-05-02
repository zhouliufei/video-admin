package com.yefeng.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class UploadUtil {

    public static String uploadBgm(MultipartFile file, String path) throws Exception {
        String fileName = file.getOriginalFilename();
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) {
            return "";
        }
        String fileType = fileName.substring(lastIndex + 1);
        if (!"mp3".equals(fileType)) {
            throw new Exception("文件不合法");
        }
        File uploadFilePath = new File(path + "/bgm");
        if(uploadFilePath.exists() == false) {
            uploadFilePath.mkdirs();
        }
        String uid = UUID.randomUUID().toString().substring(0,10).replaceAll("-","");
        String localUrl = path + "/bgm/" + uid + fileName;
        File actualFile = new File(localUrl);
        file.transferTo(actualFile);
        return "/bgm/" + uid + fileName;
    }

}
