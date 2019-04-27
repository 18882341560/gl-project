package com.greelee.tool.component.excel;

import lombok.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelWorkbook {
    /**
     * 表单列表
     */
    List<ExcelSheet> sheetList;
    /**
     * 字体列表
     */
    List<Font> fontList;
    /**
     * CellStyle列表
     */
    List<CellStyle> cellStyleList;
    /**
     * excel 类型
     */
    ExcelUtil.TYPE excelType;
    /**
     * Excel 工作簿
     */
    Workbook workbook;
}
