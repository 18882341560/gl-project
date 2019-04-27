package com.greelee.tool.util.pdf;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: pdf工具类
 */
public class PdfUtil {
    /**
     * 设置 pdf 文件流响应属性
     *
     * @param response HTTP响应对象
     * @param fileName Excel 文件名
     */
    public static void setResponse(HttpServletResponse response, String fileName, boolean download) throws Exception {
        if (StringUtils.isNotBlank(fileName)) {
            // 中文解析
            fileName = new String(fileName.getBytes(), Charsets.ISO_8859_1);
        }

        //设置响应头
        //兼容不同浏览器的中文乱码问题
        response.setCharacterEncoding("UTF-8");
        // 是否下载
        String downloadString;
        if (download) {
            downloadString = "attachment";
        } else {
            downloadString = "inline";
        }
        response.setHeader("Content-disposition", downloadString + ";filename=" + fileName);
        response.setContentType("application/pdf");
    }
}
