package cn.distantstar.srb.oss.service;

import java.io.InputStream;

/**
 * @author distantstar
 */
public interface FileService {

    /**
     * 将文件上传至阿里云
     * @param inputStream 输入流
     * @param module 模块
     * @param fileName 文件名
     * @return 返回上传结果和文件地址
     */
    String upload(InputStream inputStream, String module, String fileName);

    /**
     * 根据文件地址删除文件
     * @param url 文件地址
     */
    void removeFile(String url);
}
