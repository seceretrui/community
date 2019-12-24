package com.ruihe.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * Created by seceretrui 2019/12/21/15:03
 */
@Service
@Slf4j
public class AliyunProvider {
    @Value("${Aliyun-oss-accessKeyId}")
    private String accessKeyId;

    @Value("${Aliyun-oss-accessKeySecret}")
    private String accessKeySecret;

    @Value("${Aliyun-oss-bucketName}")
    private String bucketName;

    @Value("${Aliyun-oss-endpoint}")
    private String endpoint;

    private OSS ossClient;

    public String upload(InputStream fileStream, String fileName) {
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String generatedName;
        generatedName = setFileName(fileName);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, generatedName, fileStream);
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            if (result != null) {
                OSS response = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
                Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
                URL url = response.generatePresignedUrl(bucketName, generatedName, expiration);
                return url.toString();
            }
        } catch (Exception e) {
            log.error("upload error,{}", fileName, e);
        } finally {
            ossClient.shutdown();
        }
        return null;
    }

    public String setFileName(String fileName) {
        String filePath = fileName.substring(0, fileName.lastIndexOf("."));
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            fileName = filePath + UUID.randomUUID() + "." + filePaths[filePaths.length - 1];
        } else {
            return null;
        }
        return fileName;
    }
}
