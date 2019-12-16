package com.xbkj.gd.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


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
	public static void batchCreateCell(XSSFRow row, String[] contents){
		int len = contents.length;
		for(int i = 0; i < len; i++){
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(contents[i]);
		}
	}
	/**
	 * 图片单元格
	 * @param wb
	 * @param sheet
	 * @param row
	 * @param locations dx1, dy1, dx2, dy2, col1, row1, col2, row2
	 * @param image
	 */
	public static void createPic(XSSFWorkbook wb, XSSFSheet sheet, XSSFRow row, int[] locations, byte[] image){
		XSSFClientAnchor ca = new XSSFClientAnchor
				(locations[0], locations[1], locations[2], locations[3], 
						locations[4], locations[5], locations[6], locations[7]);
		XSSFDrawing draw = sheet.createDrawingPatriarch();
		draw.createPicture(ca, wb.addPicture(image, XSSFWorkbook.PICTURE_TYPE_JPEG));
//		ca.setAnchorType(AnchorType.MOVE_DONT_RESIZE);
	}
	public static void batchCreateCell(HSSFRow row, String[] contents, HSSFCellStyle style){
		int len = contents.length;
		for(int i = 0; i < len; i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(contents[i]);
			cell.setCellStyle(style);
		}
	}
	public static void batchCreateCell(XSSFRow row, String[] contents, XSSFCellStyle style){
		int len = contents.length;
		for(int i = 0; i < len; i++){
			XSSFCell cell = row.createCell(i);
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
