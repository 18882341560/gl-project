package com.greelee.tool.component.excel;

import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: Excel 行对象
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelRow {
    /**
     * 所属单元格列表对象
     */
    private List<ExcelCell> cellList;

    /**
     * 行索引
     */
    private Integer index;
    /**
     * 行高
     */
    private Integer height;
    /**
     * Excel 行
     */
    private Row row;
}
