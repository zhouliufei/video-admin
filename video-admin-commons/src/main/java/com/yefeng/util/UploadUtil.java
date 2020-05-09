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
        if (uploadFilePath.exists() == false) {
            uploadFilePath.mkdirs();
        }
        String uid = UUID.randomUUID().toString().substring(0, 10).replaceAll("-", "");
        String localUrl = path + "/bgm/" + uid + fileName;
        File actualFile = new File(localUrl);
        file.transferTo(actualFile);
        return "/bgm/" + uid + fileName;
    }

    public static String updateFile(MultipartFile file, String rootPath, String path) throws Exception {
        deleteFile(rootPath, path);
        return uploadBgm(file, rootPath);
    }

    public static void deleteFile(String rootPath, String path) {
        File customFile = new File(rootPath + path);
        customFile.deleteOnExit();
    }

}
