package com.orange;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.orange.file.service.FileStorageService;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.runner.RunWith;

@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
public class MinIOTest {

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void testUpdateImgFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\list2.html");
            String filePath = fileStorageService.uploadHtmlFile("", "list2.html", fileInputStream);
            System.out.println(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void name() {
        FileInputStream fileInputStream;
        try {

            fileInputStream = new FileInputStream("D:\\plugins\\js\\index.js");;

            // 1.创建minio链接客户端
            MinioClient minioClient = MinioClient.builder().credentials("minio", "minio123")
                    .endpoint("http://192.168.0.100:9000").build();
            // 2.上传
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object("plugins/js/index.js")// 文件名
                    .contentType("text/js")// 文件类型
                    .bucket("leadnews")// 桶名词 与minio创建的名词一致
                    .stream(fileInputStream, fileInputStream.available(), -1) // 文件流
                    .build();
            minioClient.putObject(putObjectArgs);

            // System.out.println("http://192.168.0.100:9000/leadnews/list2.html");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
