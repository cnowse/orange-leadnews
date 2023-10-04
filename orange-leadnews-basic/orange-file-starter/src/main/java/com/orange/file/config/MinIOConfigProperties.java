package com.orange.file.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 文件上传，配置前缀file.oss
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinIOConfigProperties implements Serializable {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endpoint;
    private String readPath;

}
