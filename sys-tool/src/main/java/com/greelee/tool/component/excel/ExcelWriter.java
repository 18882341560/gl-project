package com.greelee.tool.component.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * @author: gl
 * @Email: 110.com
 * @version: 1.0
 * @Date: 2019/4/21
 * @describe:  excel 写出类, 若从 excel 文件读后再写出,只有当没有合并单元格时才能正常工作
 */
public class ExcelWriter {

    /**
     * 将工作簿写出到文件
     */
    public void writeToFile(ExcelWorkbook excelWorkbook, File file) throws ExcelException, IOException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            ExcelUtil.TYPE type = ExcelUtil.getType(file);
            if (type == null) {
                throw new ExcelException("目标文件不是 Excel 文件类型");
            }
            write(excelWorkbook, type).write(outputStream);
        }
    }

    /**
     * 将 Excel 工作簿写到 Workbook 中
     */
    public Workbook write(ExcelWorkbook excelWorkbook) throws ExcelException {
        return write(excelWorkbook, excelWorkbook.getExcelType());
    }

    /**
     * 将 Excel 工作簿写到 Workbook 中
     */
    public Workbook write(ExcelWorkbook excelWorkBook, ExcelUtil.TYPE targetType) throws ExcelException {
        Workbook workbook = null;
        switch (targetType) {
            case XLS:
                workbook = new HSSFWorkbook();
                break;
            case XLSX:
                workbook = new XSSFWorkbook();
                break;
            default:
                break;
        }
        return generateWorkbook(workbook, excelWorkBook);
    }

    /**
     * 生成 Excel 工作簿
     */
    private Workbook generateWorkbook(Workbook workbook, ExcelWorkbook excelWorkBook) {

        /*
         * 设置字体
         */
        for (Font font : excelWorkBook.getFontList()) {
            Font workbookFont = workbook.createFont();
            setFont(workbookFont, font);
        }
        /*
         * 设置单元格样式
         */
        for (CellStyle cellStyle : excelWorkBook.getCellStyleList()) {
            CellStyle workbookCellStyle = workbook.createCellStyle();
            setCellStyle(workbookCellStyle, cellStyle, workbook);
        }
        /*
         * 设置表单数据
         */
        for (ExcelSheet excelSheet : excelWorkBook.getSheetList()) {
            Sheet sheet = workbook.createSheet(excelSheet.getName());
            // 已设定列宽的列索引列表
            Set<Integer> hadWidthColumnIndexSet = new HashSet<>();
            setSheet(sheet, excelSheet, workbook, hadWidthColumnIndexSet);
        }
        return workbook;
    }

    /**
     * 设置表单数据
     */
    private void setSheet(Sheet sheet, ExcelSheet excelSheet, Workbook workbook, Set<Integer> hadWidthColumnIndexSet) {
        for (ExcelRow excelRow : excelSheet.getRowList()) {
            Row row = sheet.createRow(excelRow.getIndex());
            setRow(row, excelRow, workbook, sheet, hadWidthColumnIndexSet);
        }
    }

    /**
     * 设置行数据
     */
    private void setRow(Row row, ExcelRow excelRow, Workbook workbook, Sheet sheet, Set<Integer> hadWidthColumnIndexSet) {
        // 行高
        row.setHeight(excelRow.getHeight().shortValue());
        // 行号
        row.setRowNum(excelRow.getIndex());
        for (ExcelCell excelCell : excelRow.getCellList()) {
            Cell cell = row.createCell(excelCell.getColumnIndex());
            setCell(cell, excelCell, workbook);
            /*
             * 第一次循环时设定单元格宽度
             */
            setWidth(sheet, excelCell, hadWidthColumnIndexSet);
        }
    }

    /**
     * 设置列宽
     */
    private void setWidth(Sheet sheet, ExcelCell excelCell, Set<Integer> hadWidthColumnIndexSet) {
        int columnIndex = excelCell.getColumnIndex();
        // 只有在第一次循环时会设置列宽
        if (!hadWidthColumnIndexSet.contains(columnIndex)) {
            hadWidthColumnIndexSet.add(columnIndex);
            sheet.setColumnWidth(columnIndex, excelCell.getWidth());
        }
    }


    /**
     * 设置单元格数据
     */
    private void setCell(Cell cell, ExcelCell excelCell, Workbook workbook) {
        // 值类型
        cell.setCellType(CellType.STRING);
        // 值
        cell.setCellValue(excelCell.getContent());
        // 单元格样式
        cell.setCellStyle(workbook.getCellStyleAt(excelCell.getStyleIndex()));
    }

    /**
     * 设置工作簿字体
     */
    private void setFont(Font to, Font from) {
        to.setBold(from.getBold());
        to.setColor(from.getColor());
        to.setFontHeightInPoints(from.getFontHeightInPoints());
        to.setFontHeight(from.getFontHeight());
        to.setFontName(from.getFontName());
        to.setCharSet(from.getCharSet());
        to.setItalic(from.getItalic());
        to.setStrikeout(from.getStrikeout());
        to.setTypeOffset(from.getTypeOffset());
        to.setUnderline(from.getUnderline());
    }

    /**
     * 设置工作簿单元格样式
     */
    private void setCellStyle(CellStyle to, CellStyle from, Workbook workbook) {
        int fontIndex = from.getFontIndexAsInt();
        to.setFont(workbook.getFontAt(fontIndex));
        to.setAlignment(from.getAlignment());
        to.setBorderTop(from.getBorderTop());
        to.setBorderBottom(from.getBorderBottom());
        to.setBorderLeft(from.getBorderLeft());
        to.setBorderRight(from.getBorderRight());
        to.setTopBorderColor(from.getTopBorderColor());
        to.setBottomBorderColor(from.getBottomBorderColor());
        to.setLeftBorderColor(from.getLeftBorderColor());
        to.setRightBorderColor(from.getRightBorderColor());
        to.setDataFormat(from.getDataFormat());
        to.setFillBackgroundColor(from.getFillBackgroundColor());
        to.setFillForegroundColor(from.getFillForegroundColor());
        to.setFillPattern(from.getFillPattern());
        to.setHidden(from.getHidden());
        to.setIndention(from.getIndention());
        to.setLocked(from.getLocked());
        to.setQuotePrefixed(from.getQuotePrefixed());
        to.setRotation(from.getRotation());
        to.setShrinkToFit(from.getShrinkToFit());
        to.setVerticalAlignment(from.getVerticalAlignment());
        to.setWrapText(from.getWrapText());
    }
}
