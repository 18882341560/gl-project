package com.greelee.tool.util.io;

import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: 输入输出工具类
 */
@Slf4j
public class IOUtil {

    private IOUtil() {
    }

    /**
     * 输入流转化为字节数组字符串，并对其进行Base64编码处理
     */
    public static String getBase64FromInputStream(InputStream inputStream) throws IOException {
        Assert.notNull(inputStream, "输入流不能为 null");
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            //代表一次最多读取1KB的内容
            byte[] buffer = new byte[1024];
            //代表实际读取的字节数
            int length = 0;
            while ((length = bufferedInputStream.read(buffer)) != -1) {
                //length 代表实际读取的字节数
                byteArrayOutputStream.write(buffer, 0, length);
            }
            //缓冲区的内容写入到文件
            byteArrayOutputStream.flush();
            data = byteArrayOutputStream.toByteArray();
        }
        byte[] base64 = Base64.encodeBase64(data);
        if (Objects.nonNull(base64)) {
            return new String(base64);
        }
        return null;
    }

    /**
     * 获取网络文件流
     */
    public static InputStream getInputStream(String destUrl) throws IOException {
        URL url = new URL(destUrl);
        URLConnection urlConnection = url.openConnection();
        return urlConnection.getInputStream();
    }

    /**
     * 输入流转字节数组
     */
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toByteArray();
        }
    }

    /**
     * 将字符串写入到文件中
     *
     * @param source   要写入的字符串
     * @param fileName 文件名,相对路径,绝对路径均可
     * @throws IOException 异常
     */
    public static void writeStringToFile(String source, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.write(source);
            if (log.isDebugEnabled()) {
                log.debug("将字符串写入到文件成功:" + fileName);
            }
        }
    }

}
