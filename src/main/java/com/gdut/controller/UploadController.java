package com.gdut.controller;

import com.gdut.annotation.RequireRole;
import com.gdut.pojo.Result;
import com.gdut.util.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    /**
     * 文件上传
     */
    @RequireRole("student")
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        log.info("上传文件{}",fileName);
        try {
            String url = aliyunOSSOperator.upload(file, fileName);
            return Result.success((Object) url);
        } catch (Exception e) {
            log.error("上传文件失败：{}", e.getMessage());
            return Result.fail("上传失败");
        }
    }

    /**
     * 删除oss里面的文件
     */
    @RequireRole
    @DeleteMapping("/deleteImage")
    public Result deleteImage(@RequestParam String image)  {
        log.info("删除图片：{}", image);
        try {
            String objectKey = aliyunOSSOperator.extractObjectKeyFromUrl(image);
            aliyunOSSOperator.deleteFile(objectKey);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("删除 OSS 文件失败：{}", e.getMessage());
            return Result.fail("删除失败：" + e.getMessage());
        }
    }

}
