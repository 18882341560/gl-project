package com.greelee.tool.util.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 文件工具类
 */
public class FileUtil {


    private static final String HTTP_AGREEMENT_PREFIX = "http://";
    private static final String HTTPS_AGREEMENT_PREFIX = "https://";

    private FileUtil() {
    }

    /**
     * 使用NIO下载文件
     *
     * @param url      网络资源的 url
     * @param saveDir  保存的文件夹
     * @param fileName 指定的文件名
     * @return 下载的文件字节数
     */
    public static long downloadByNio(String url, String saveDir, String fileName) throws IOException {
        url = formatUrlString(url);
        try (InputStream inputStream = new URL(url).openStream()) {
            Path target = Paths.get(saveDir, fileName);
            Files.createDirectories(target.getParent());
            return Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Nio保存文件
     *
     * @param inputStream 文件输入流
     * @param saveDir     保存的文件夹
     * @param fileName    指定的文件名
     * @return 下载的文件字节数
     */
    public static long saveFileByNio(InputStream inputStream, String saveDir, String fileName) throws IOException {
        try {
            Path target = Paths.get(saveDir, fileName);
            Files.createDirectories(target.getParent());
            return Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件扩展名,若没有.(dot)分隔符则获取到的扩展名为空
     */
    public static String getSuffix(File file) {
        String fileName = file.getName();
        return getSuffix(fileName);
    }

    /**
     * 获取文件扩展名
     */
    public static String getSuffix(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > -1) {
            return fileName.toLowerCase().substring(dotIndex + 1, fileName.length());
        } else {
            return "";
        }
    }

    /**
     * 获取文件名称(不包括扩展名)
     */
    public static String getFileName(File file) {
        String fileName = file.getName();
        return getFileName(fileName);
    }

    /**
     * 获取文件名称(不包括扩展名)
     */
    public static String getFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > -1) {
            return fileName.toLowerCase().substring(0, dotIndex);
        } else {
            return fileName;
        }
    }

    /**
     * 判断 urlString 是否是 http:// 或 https://开头的,如果不是则加上前缀
     *
     * @param urlString urlString 字符串
     * @return urlString 字符串
     */
    private static String formatUrlString(String urlString) {
        if (null != urlString) {
            if (!urlString.toLowerCase().startsWith(HTTP_AGREEMENT_PREFIX) && !urlString.toLowerCase().startsWith(HTTPS_AGREEMENT_PREFIX)) {
                urlString = HTTP_AGREEMENT_PREFIX + urlString;
            }
        }
        return urlString;
    }
}
