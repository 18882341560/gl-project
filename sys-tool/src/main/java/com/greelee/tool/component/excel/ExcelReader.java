package com.greelee.tool.component.excel;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe: excel 读入类
 */
public class ExcelReader {
    /**
     * excel 数值处理
     */
    private ExcelNumericFormat excelNumericFormat;

    public ExcelReader(ExcelNumericFormat excelNumericFormat) {
        this.excelNumericFormat = excelNumericFormat;
    }

    /**
     * 读 Excel 文件
     */
    public ExcelWorkbook read(File file) throws ExcelException, IOException {
        /*
         * 判断 excel 文件是 xls 还是 xlsx
         */
        try (InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            /*
             * 获取 Excel 文件类型
             */
            ExcelUtil.TYPE type = getExcelType(file);
            /*
             * 获取 Excel 工作簿
             */
            Workbook workbook = getWorkBook(bufferedInputStream, type);
            /*
             * 获取 Excel 公式计算器
             */
            FormulaEvaluator evaluator = getFormulaEvaluator(workbook);
            /*
             * Excel 表单列表
             */
            List<ExcelSheet> excelSheetList = listExcelSheet(workbook, evaluator);
            /*
             * Excel 字体
             */
            List<Font> fontList = listWorkBookFont(workbook);
            /*
             * Excel 单元格样式
             */
            List<CellStyle> cellStyleList = listCellStyle(workbook);
            /*
             * 生成 Excel 工作簿
             */
            return ExcelWorkbook.builder()
                    .sheetList(excelSheetList)
                    .fontList(fontList)
                    .cellStyleList(cellStyleList)
                    .excelType(type)
                    .workbook(workbook)
                    .build();
        }
    }

    private ExcelUtil.TYPE getExcelType(File file) throws ExcelException {
        ExcelUtil.TYPE type = ExcelUtil.getType(file);
        if (null == type) {
            throw new ExcelException("不是 excel 文件");
        }
        return type;
    }

    /**
     * 根据 Excel 文件后缀名获取 Excel 工作簿
     */
    private Workbook getWorkBook(InputStream inputStream, ExcelUtil.TYPE excelType) throws IOException {
        Workbook workbook = null;
        switch (excelType) {
            case XLS:
                workbook = new HSSFWorkbook(inputStream);
                break;
            case XLSX:
                workbook = new XSSFWorkbook(inputStream);
                break;
            default:
                break;
        }
        return workbook;
    }

    /**
     * 获取工作簿的公式计算器
     */
    private FormulaEvaluator getFormulaEvaluator(Workbook workbook) throws ExcelException {
        FormulaEvaluator evaluator = null;
        if (workbook instanceof HSSFWorkbook) {
            evaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        }
        if (workbook instanceof XSSFWorkbook) {
            evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
        }
        if (null == evaluator) {
            throw new ExcelException("无法获取公式计算器");
        }
        return evaluator;
    }

    /**
     * 获取 Excel 表单列表
     */
    private List<ExcelSheet> listExcelSheet(Workbook workbook, FormulaEvaluator evaluator) {
        // 表单总数
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        List<ExcelSheet> excelSheetList = new ArrayList<>();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            /*
             * 获取 Excel 表单
             */
            ExcelSheet excelSheet = getExcelSheet(sheet, evaluator);
            excelSheetList.add(excelSheet);
        }
        return excelSheetList;
    }

    /**
     * 生成 Excel 表单对象
     */
    private ExcelSheet getExcelSheet(Sheet sheet, FormulaEvaluator evaluator) {
        // 表单名称
        String sheetName = sheet.getSheetName();
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<ExcelRow> excelRowList = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            /*
             * 获取 Excel 行
             */
            ExcelRow excelRow = getExcelRow(row, sheet, evaluator);
            excelRowList.add(excelRow);
        }
        /**
         * 生成 Excel 表单对象
         */
        return ExcelSheet.builder()
                .name(sheetName)
                .rowList(excelRowList)
                .sheet(sheet)
                .build();
    }

    /**
     * 生成 Excel 行对象
     */
    private ExcelRow getExcelRow(Row row, Sheet sheet, FormulaEvaluator evaluator) {
        // 行索引
        Integer rowIndex = row.getRowNum();
        // 行高
        Integer rowHeight = (int) row.getHeight();
        Iterator<Cell> cellIterator = row.cellIterator();
        List<ExcelCell> excelCellList = new ArrayList<>();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            /*
             * 获取 Excel 单元格
             */
            ExcelCell excelCell = getExcelCell(cell, sheet, evaluator, rowHeight, rowIndex);
            excelCellList.add(excelCell);
        }
        /*
         * 生成 Excel 行对象
         */
        return ExcelRow.builder()
                .index(rowIndex)
                .cellList(excelCellList)
                .height(rowHeight)
                .row(row)
                .build();
    }

    /**
     * 生成 Excel 单元格对象
     */
    private ExcelCell getExcelCell(Cell cell, Sheet sheet, FormulaEvaluator evaluator, int rowHeight, int rowIndex) {
        // 列索引
        Integer columnIndex = cell.getColumnIndex();
        // 单元格样式
        CellStyle cellStyle = cell.getCellStyle();
        // 单元格样式索引
        int cellStyleIndex = cellStyle.getIndex();
        // 列宽
        Integer columnWidth = sheet.getColumnWidth(columnIndex);
        // 列值
        String value = getCellValue(cell, evaluator);
        /*
         * 生成 Excel 单元格对象
         */
        return ExcelCell.builder()
                .content(value)
                .styleIndex(cellStyleIndex)
                .width(columnWidth)
                .height(rowHeight)
                .rowIndex(rowIndex)
                .columnIndex(columnIndex)
                .cell(cell)
                .build();
    }

    /**
     * 获取单元格字符串值
     */
    private String getCellValue(Cell cell, FormulaEvaluator evaluator) {
        String value = "";
        switch (cell.getCellType()) {
            case ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            case BLANK:
                break;
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                value = evaluator.evaluate(cell).formatAsString();
                break;
            case NUMERIC:
                value = excelNumericFormat.getNumericValue(cell);
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 获取工作簿字体列表
     */
    private List<Font> listWorkBookFont(Workbook workbook) {
        int fontCount = workbook.getNumberOfFontsAsInt();
        List<Font> fontList = new ArrayList<>();
        for (int i = 0; i < fontCount; i++) {
            Font font = workbook.getFontAt(i);
            fontList.add(font);
        }
        return fontList;
    }

    /**
     * 获取表格内单元格样式
     */
    private List<CellStyle> listCellStyle(Workbook workbook) {
        int cellStyleCount = workbook.getNumCellStyles();
        List<CellStyle> cellStyleList = new ArrayList<>();
        for (int i = 0; i < cellStyleCount; i++) {
            CellStyle cellStyle = workbook.getCellStyleAt(i);
            cellStyleList.add(cellStyle);
        }
        return cellStyleList;
    }

    public ExcelNumericFormat getExcelNumericFormat() {
        return excelNumericFormat;
    }
}
