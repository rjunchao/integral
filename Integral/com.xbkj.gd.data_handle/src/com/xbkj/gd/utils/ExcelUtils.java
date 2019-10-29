package com.xbkj.gd.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2017-7-13
 *@version 1.0.0
 *@desc
 */
public class ExcelUtils {

	/**
	 * 创建一个单元格，
	 */
	public static void createCellByStringValue(HSSFRow row, HSSFCellStyle style, int column, String value){
		HSSFCell cell = row.createCell(column);
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}
	
	public static void batchCreateCell(HSSFSheet sheet, String[] contents, int[] rowIndexs, HSSFCellStyle style){
		int len = rowIndexs.length;
		for(int i = 0; i < len; i++){
			HSSFCell cell = sheet.createRow(rowIndexs[i]).createCell(0);
			cell.setCellValue(contents[i]);
			cell.setCellStyle(style);
		}
	}
	public static void batchCreateCell(HSSFRow row, String[] contents){
		int len = contents.length;
		for(int i = 0; i < len; i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(contents[i]);
		}
	}
	public static void batchCreateCell(HSSFRow row, String[] contents, HSSFCellStyle style){
		int len = contents.length;
		for(int i = 0; i < len; i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(contents[i]);
			cell.setCellStyle(style);
		}
	}
	
	/**
	 * 合并单元格
	 * @param sheet 
	 * @param ranges firstRow, lastRow, firstCol, lastCol
	 */
	public static void CellRangeAddress(HSSFSheet sheet, int[] ranges){
		//firstRow, lastRow, firstCol, lastCol
		sheet.addMergedRegion(new CellRangeAddress(ranges[0], ranges[1], ranges[2], ranges[3]));
	}
	/**
	 * 合并单元格
	 * @param sheet
	 * @param ranges
	 */
	public static void setRowHeght(HSSFRow row, short rowHeight){
		row.setHeight(rowHeight);
	}
	
}
