package com.greelee.tool.component.excel;

import lombok.*;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: Excel 对象
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelSheet {
    /**
     * 内容行的列表
     */
    private List<ExcelRow> rowList;
    /**
     * 表单名
     */
    private String name;
    /**
     * Excel Sheet
     */
    private Sheet sheet;
}
