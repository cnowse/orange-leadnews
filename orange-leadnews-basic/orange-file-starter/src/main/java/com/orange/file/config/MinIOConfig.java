package com.orange.file.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.orange.file.service.FileStorageService;

import io.minio.MinioClient;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 当引入 FileStorageService 接口时配置生效
 */
@Data
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({MinIOConfigProperties.class})
@ConditionalOnClass(FileStorageService.class)
public class MinIOConfig {

    private final MinIOConfigProperties minIOConfigProperties;

    @Bean
    public MinioClient buildMinioClient() {
        return MinioClient
                .builder()
                .credentials(minIOConfigProperties.getAccessKey(), minIOConfigProperties.getSecretKey())
                .endpoint(minIOConfigProperties.getEndpoint())
                .build();
    }

}