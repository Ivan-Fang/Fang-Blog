package com.ivanfang.fangblog.controller;

import com.ivanfang.fangblog.pojo.Result;
import com.ivanfang.fangblog.utils.AwsS3Util;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/uploadToLocal")
    public Result uploadToLocal(MultipartFile file) throws IOException {
        // get file extension and use UUID as saved filename
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;

        String filepath = "C:\\Users\\zenbook-14x\\Desktop\\Backend\\project-fang-times\\fang-times\\upload_files\\";
        file.transferTo(new File(filepath + newFilename));

        return Result.success("simulated url...");
    }

    @PostMapping("/upload")
    public Result uploadToAws(MultipartFile file) throws IOException {
        // get file extension and use UUID as saved filename
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;

        // upload to aws
        AwsS3Util.upload(newFilename, file.getInputStream());

        // get upload file url
        String url = "https://fang-times-bucket.s3.ap-northeast-1.amazonaws.com/" + newFilename;

        return Result.success(url);
    }

}
