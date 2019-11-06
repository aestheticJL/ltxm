package com.mmt.ltxm.provider;

import com.mmt.ltxm.exception.CustomizeErrorCode;
import com.mmt.ltxm.exception.CustomizeException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class TencentCloudProvider {
    @Value("${TencentCloud.SECRETID}")
    private String secretId;
    @Value("${TencentCloud.SECRETKEY}")
    private String secretKey;
    @Value("${TencentCloud.bucketName}")
    private String bucketName;


    public String upload(MultipartFile file, String fileName) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定要上传到 COS 上对象键
        String generatedFileName;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        try {
            // 指定要上传到的存储桶
            String bucketName = this.bucketName;
            File localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, generatedFileName, localFile);
            cosClient.putObject(putObjectRequest);
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, generatedFileName, HttpMethodName.GET);
            URL downloadUrl = cosClient.generatePresignedUrl(req);
            cosClient.shutdown();
            return downloadUrl.toString();
        } catch (CosServiceException serverException) {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (IOException e) {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }
}
