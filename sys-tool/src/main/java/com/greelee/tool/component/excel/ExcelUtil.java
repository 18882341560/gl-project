package com.greelee.tool.component.excel;

import com.google.common.base.Charsets;
import com.greelee.tool.util.file.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Objects;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
public class ExcelUtil {
    private ExcelUtil() {
    }

    @Getter
    @AllArgsConstructor
    public enum TYPE {
        /**
         * xls 文件后缀
         */
        XLS("xls"),
        /**
         * xlsx 文件后缀
         */
        XLSX("xlsx");
        private String suffix;

    }

    /**
     * 获取 excel 文件类型
     */
    public static ExcelUtil.TYPE getType(File file) {
        if (Objects.equals(FileUtil.getSuffix(file), TYPE.XLS.getSuffix())) {
            return TYPE.XLS;
        }
        if (Objects.equals(FileUtil.getSuffix(file), TYPE.XLSX.getSuffix())) {
            return TYPE.XLSX;
        }
        return null;
    }

    /**
     * 设置 Excel 文件流响应属性
     *
     * @param response HTTP响应对象
     * @param fileName Excel 文件名
     */
    public static void setExcelResponse(HttpServletResponse response, String fileName) throws Exception {
        if (StringUtils.isNotBlank(fileName)) {
            // 中文解析
            fileName = new String(fileName.getBytes(), Charsets.ISO_8859_1);
        }
        //设置响应头
        //兼容不同浏览器的中文乱码问题
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setContentType("application/msexcel");
    }
}
