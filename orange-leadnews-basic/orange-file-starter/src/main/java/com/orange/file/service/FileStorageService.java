package com.orange.file.service;

import java.io.InputStream;

public interface FileStorageService {

    /**
     * 上传图片文件
     * 
     * @param prefix 文件前缀
     * @param filename 文件名
     * @param inputStream 文件流
     * @return 文件全路径
     */
    String uploadImgFile(String prefix, String filename, InputStream inputStream);

    /**
     * 上传html文件
     * 
     * @param prefix 文件前缀
     * @param filename 文件名
     * @param inputStream 文件流
     * @return 文件全路径
     */
    String uploadHtmlFile(String prefix, String filename, InputStream inputStream);

    /**
     * 删除文件
     * 
     * @param pathUrl 文件全路径
     */
    void delete(String pathUrl);

    /**
     * 下载文件
     * 
     * @param pathUrl 文件全路径
     * @return 文件字节数组
     *
     */
    byte[] downLoadFile(String pathUrl);

}
