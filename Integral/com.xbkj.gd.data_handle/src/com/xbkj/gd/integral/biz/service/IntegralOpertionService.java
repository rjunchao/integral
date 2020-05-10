package com.xbkj.gd.integral.biz.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gocom.components.coframe.tools.LoggerFactory;

import sun.misc.BASE64Decoder;

import com.eos.system.logging.Logger;
import com.pub.xbkj.common.MsgResponse;
import com.pub.xbkj.pubapp.query.VOQuery;
import com.sun.star.uno.RuntimeException;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.common.util.StringUtil;
import com.xbkj.gd.integral.biz.CustomerOptionBiz;
import com.xbkj.gd.integral.biz.IntegralOptionBiz;
import com.xbkj.gd.integral.prod.common.SignUtils;
import com.xbkj.gd.integral.prod.dao.OrgApplyProductDao;
import com.xbkj.gd.integral.prod.vos.OrgApplyProductVO;
import com.xbkj.gd.integral.vos.AddIntegralDetailVO;
import com.xbkj.gd.integral.vos.CustomerVO;
import com.xbkj.gd.integral.vos.IntegralDetailVO;
import com.xbkj.gd.integral.vos.LeadDrawIntegralVO;
import com.xbkj.gd.integral.vos.SubIntegralDetailVO;
import com.xbkj.gd.integral.vos.VipIntegralDetailVO;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.ExcelUtils;
import com.xbkj.gd.utils.FileImportUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2018-9-7
 *@version 1.0.0
 *@desc 积分新规则业务类
 */
public class IntegralOpertionService {
	
	private CustomerOptionBiz cust = new CustomerOptionBiz();
	
	private static Logger log = LoggerFactory.getLogger(IntegralOpertionService.class);
	
	
	/*private MsgResponse validateAccount(LeadDrawIntegralVO vo){
		String num = vo.getCustomer_account();
		if(num != null && !"".equals(num.trim())){
			if(!(num.length() == 16 || num.length() == 19)){
				//对的
				return new MsgResponse("账号填写错误，请重新填写", false);
			}
		}else{
			return new MsgResponse("账号为空，请重新填写", false);
		}
		return null;
	}*/
	
	/**
	 * 提前支取积分
	 * @return
	 */
	public MsgResponse leadDrawIntegral(LeadDrawIntegralVO vo){
		//扣除客户积分，可以为 负分
		//插入积分明细记录
		MsgResponse msg = validateLeadIntegral(vo);
		if(msg != null){
			return msg;
		}
		
		
		if(vo == null){
			return new MsgResponse("请填写支取项");
		}
		
		
		String pk = vo.getCustomer_idcard();
		CustomerVO customerVO = cust.queryCustByPK(pk);
		if(pk == null || "".equals(pk)){
			throw new RuntimeException("客户主键为空, 请在客户页面刷新查询并修改积分"); 
		}
		//查询批次号
		try {
			String sn = querySerialNumber(customerVO);
			//
			vo.setCustomer_idcard(customerVO.getCustomer_idcard());//身份证
			vo.setPk_integral_detail(PrimaryKeyUtil.getPrimaryKey());//主键
			vo.setCreatetime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss"));//创建时间
			vo.setCreate_user(UserUtils.getUser());
			vo.setCreate_user_org(UserUtils.getUserOrg());
			vo.setDef3(DateUtils.getFormatDate(DateUtils.PATTERN_YEAR));
//			vo.setTs(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss"));
			vo.setDef8(sn);//序号
			
			//不用校验负分的
			//更新客户积分
			String sql = "UPDATE gd_customer_info2 SET now_usable_integral = CONVERT(now_usable_integral - ?, DECIMAL) WHERE pk_customer_info=?";
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(vo.getCustomer_integral());
			parameter.addParam(customerVO.getPk_customer_info());
			new DBUtils().executeUpdateSQL(sql, parameter);
			GdDataHandlerUtils<IntegralDetailVO> utils = new GdDataHandlerUtils<IntegralDetailVO>(new IntegralDetailVO());
			msg = utils.save(vo);
			if(msg.isFlag()){
				msg.setMessage("积分兑换成功");
			}else{
				msg.setMessage("积分兑换失败");
			}
			return msg;
		} catch (DAOException e) {
			log.error("", e);
			throw new RuntimeException("vip积分赠送失败" + e.getMessage());
		}
	}
	
	/**
	 * 导入历史积分明细
	 * @param request
	 * @param type 0:积分添加，1：积分兑换
	 */
	public String importHistoryIntegralDetail(HttpServletRequest request, int type){
		InputStream is = null;
		try {
			//解析数据
			is = FileImportUtils.getExcelIs(request);
			if(is == null){
				return "导入积分明细失败，文件输入为空";
			}
			//根据类型导入
			String msg = null;
			if(0 == type){
				msg = importHistoryAddIntegralDetail(is);
			}else if(1 == type){
				msg = importHistorySubIntegralDetail(is);
			}
			if(msg != null){
				return msg;
			}
		} catch (Exception e) {
			log.error("", e);
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
		return "导入积分明细失败";
	}

	/**
	 * 导入历史添加积分记录
	 * @param is
	 * @return
	 */
	private String importHistoryAddIntegralDetail(InputStream is) {

		try {
			GdDataHandlerUtils<IntegralDetailVO> voUtils = new GdDataHandlerUtils<IntegralDetailVO>();
			HSSFWorkbook wb = new HSSFWorkbook(is);
			//解析数据
			HSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			if(rows >= 3){
				HSSFRow row = null;
				HSSFCell cell = null;
				List<IntegralDetailVO> vos = new ArrayList<IntegralDetailVO>();
				IntegralDetailVO vo = null;
				for(int i = 2;i < rows; i++){
					row = sheet.getRow(i);
					if(row != null){
//						return new String[]{"客户名称", "客户身份证号", "积分", "账号", 
//								"存单号","存单金额","销售人", "资金", "添加积分类型", "积分计算系数", "录入人", "录入机构", "录入时间"};
//						contents = new String[]{customer_name, idcard, integral+"", 
//								account, num, amt + "", def7, def6, def1, def2,
//								ts, empname, orgname};
						vo = new IntegralDetailVO();
						cell = row.getCell(1);//身份证
						vo.setCustomer_idcard(cell.getStringCellValue());//身份证
						cell = row.getCell(2);//积分
						vo.setCustomer_integral(cell.getNumericCellValue());
						cell = row.getCell(3);//账号
						vo.setCustomer_account(cell.getStringCellValue());
						cell = row.getCell(4);//存单号
						vo.setDeposit_receipt_num(cell.getNumericCellValue()+"");
						cell = row.getCell(5);//存单金额
						vo.setDeposit_receipt_amt(cell.getNumericCellValue());
						cell = row.getCell(6);//销售人
						vo.setDef7(cell.getStringCellValue());
						cell = row.getCell(7);//资金来源
						vo.setDef6(cell.getStringCellValue());
						cell = row.getCell(8);//添加积分类型
						vo.setDef1(cell.getStringCellValue());
						cell = row.getCell(9);//积分计算系数
						vo.setDef2(cell.getStringCellValue());
						cell = row.getCell(10);//录入机构id
						vo.setCreate_user_org(cell.getNumericCellValue() + "");
						cell = row.getCell(12);//录入人
						vo.setCreate_user(cell.getStringCellValue());
						cell = row.getCell(13);//录入时间
						vo.setTs(cell.getStringCellValue());
						
						vo.setDef4("1");
						vo.setDr("0");
						vo.setPk_integral_detail(PrimaryKeyUtil.getPrimaryKey());//主键
						vos.add(vo);
					}
				}
				if(vos.size() > 0){
					voUtils.saveArr(vos.toArray(new IntegralDetailVO[vos.size()]));
					return "导入积分明细数据成功";
				}
			}else{
				return "导入数据化为空";
			}
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	/**
	 * 添加历史积分兑换记录
	 * @param is
	 * @return
	 */
	private String importHistorySubIntegralDetail(InputStream is) {
		try {
			GdDataHandlerUtils<IntegralDetailVO> voUtils = new GdDataHandlerUtils<IntegralDetailVO>();
			HSSFWorkbook wb = new HSSFWorkbook(is);
			//解析数据
			HSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			if(rows >= 3){
				HSSFRow row = null;
				HSSFCell cell = null;
				List<IntegralDetailVO> vos = new ArrayList<IntegralDetailVO>();
				IntegralDetailVO vo = null;
				for(int i = 2;i < rows; i++){
					row = sheet.getRow(i);
					if(row != null){
						vo = new IntegralDetailVO();
						cell = row.getCell(1);//身份证
						vo.setCustomer_idcard(cell.getStringCellValue());//身份证
						cell = row.getCell(2);//兑换积分
						vo.setCustomer_integral(cell.getNumericCellValue());
						cell = row.getCell(3);//兑换类型积分
						vo.setDef2(cell.getNumericCellValue()+"");
						cell = row.getCell(4);//兑换数量
						vo.setDef5(cell.getNumericCellValue()+"");
						cell = row.getCell(5);//兑换商品
						vo.setDef1(cell.getStringCellValue());
						
						cell = row.getCell(6);//录入时间
						vo.setTs(cell.getStringCellValue());
						cell = row.getCell(7);//录入人
						vo.setCreate_user(cell.getStringCellValue());
						cell = row.getCell(8);//录入机构id
						vo.setCreate_user_org(cell.getNumericCellValue() + "");
						
						vo.setDef4("2");
						vo.setDr("0");
						vo.setPk_integral_detail(PrimaryKeyUtil.getPrimaryKey());//主键
						vos.add(vo);
					}
				}
				if(vos.size() > 0){
					voUtils.saveArr(vos.toArray(new IntegralDetailVO[vos.size()]));
					return "导入积分明细数据成功";
				}
			}else{
				return "导入数据化为空";
			}
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	/**
	 * 导出积分明细
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void exportIntegralDetail(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("customer_idcard", request.getParameter("customer_idcard"));
		parameter.put("start_date", request.getParameter("start_date"));
		parameter.put("end_date", request.getParameter("end_date"));
		parameter.put("hiddenFlag", request.getParameter("hiddenFlag"));
		parameter.put("integral_type", request.getParameter("integral_type"));
		String integralTitle = "";
		String type = request.getParameter("integral_type").toString();
		if("1".equals(type)){
			integralTitle = "积分添加明细";
		}else if("2".equals(type)){
			integralTitle= "积分兑换明细";
		}else if("3".equals(type)){
			integralTitle= "积分赠送明细";
		}else if("4".equals(type)){
			integralTitle= "积分提前明细";
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(integralTitle );
		
		XSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(18);
		XSSFCell cell = row0.createCell(0);
		//第一行
		XSSFCellStyle style = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short)14);
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(style);
		
		cell.setCellValue(integralTitle);
		if("1".equals(type)){
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 23));
			setCellWidth(sheet, 1);
		}else if("2".equals(type)){
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
			setCellWidth(sheet, 2);
		}else if("3".equals(type)){
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
			setCellWidth(sheet, 3);
		}else if("4".equals(type)){
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
			setCellWidth(sheet, 3);
		}
		cell.setCellStyle(style);
		String[] titles = getTitleArr(type);
		
		//创建表头
		XSSFRow row = sheet.createRow(1);
		style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);//前景色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		font = wb.createFont();
		font.setFontHeightInPoints((short)12);//字体
		style.setFont(font);
		row.setHeightInPoints(16);
		ExcelUtils.batchCreateCell(row, titles, style);
		//统计
		IntegralDetailVO[] vos = queryAll(parameter);
		try {
			if(vos.length >= 0){
				String[] contents = null;
				IntegralDetailVO vo = null;
				for(int i = 0, len = vos.length; i < len; i++){
					vo = vos[i];
					String customer_name = vo.getCustomer_name();
					String idcard = vo.getCustomer_idcard();
					double integral = vo.getCustomer_integral();
					
					String account = vo.getCustomer_account();//账号
					double amt = vo.getDeposit_receipt_amt();//存单金额
					String num = vo.getDeposit_receipt_num();//存单号
					
					String def2 = vo.getDef2();//计算系数（添加）、积分单位（赠送和兑换）
					String def1 = vo.getDef1();//积分操作详情类型
					String def3 = vo.getDef3();//积分赠送年份
					String def5 = vo.getDef5();//积分兑换的数量
	//				String def6 = vo.getDef6();//资金来源
	//				String def7 = vo.getDef7();//销售人
					String def8 = vo.getDef8();//添加积分备注，积分兑换序号
					
					
					String empname = vo.getEmpname();
					String orgname = vo.getOrgname();
					String ts = vo.getTs();
					if("1".equals(type)){
						/*
						 * "客户名称", "客户身份证号", "积分", "账号", "存单号","存单金额",
						"营销人1", "营销人1金额", 
						"营销人2", "营销人2金额", 
						"营销人3", "营销人3金额", 
						"资金来源1", "资金来源金额1"
						"资金来源2", "资金来源金额2"
						"资金来源3", "资金来源金额3"
						"添加积分类型", "积分计算系数", "录入时间", "录入人", "录入机构", "备注"
						 */
						vo.getMarketing_people1();
						contents = new String[]{customer_name, idcard, integral+"", account, num, amt + "",
								
								vo.getMarketing_people1(), vo.getMarketing_people_amt1() + "", 
								vo.getMarketing_people2(), vo.getMarketing_people_amt2() + "", 
								vo.getMarketing_people3(), vo.getMarketing_people_amt3() + "", 
								vo.getCapital_source1(), vo.getCapital_source_amt1() + "",
								vo.getCapital_source2(), vo.getCapital_source_amt2() + "",
								vo.getCapital_source3(), vo.getCapital_source_amt3() + "",
								
								def1, def2, ts, empname, orgname, def8};
					}else if("2".equals(type)){
						contents = new String[]{
								def3, def8,
								customer_name, idcard, integral+"", 
								def2, def5, def1,vo.getConversion_detail(),
								ts, empname, orgname};
					}else if("3".equals(type)){
						contents = new String[]{customer_name, idcard, integral+"", 
								def2, def1, def3,vo.getDef8(),
								ts, empname, orgname};
					}else if("4".equals(type)){
						contents = new String[]{
								def3, def8,
								customer_name, idcard, integral+"", 
								def2, def5, def1,vo.getConversion_detail(),
								ts, empname, orgname};
					}
					row = sheet.createRow((i+2));
					ExcelUtils.batchCreateCell(row, contents);
					 if("2".equals(type)){
							//添加签名导出
						 String signPk = vo.getDef6();
						 if(StringUtil.isNotEmpty(signPk)){
							 String sign = SignUtils.getSign(signPk);
							 if(StringUtil.isNotEmpty(sign)){
	//							 byte[] decode = Base64.decode(sign);
								 BASE64Decoder decoder = new BASE64Decoder();
								 byte[] buf = decoder.decodeBuffer(sign);
								 for(int b = 0; b < buf.length; ++b) {
									 if (buf[b] < 0) {// 调整异常数据
										buf[b] += 256;
									 }
								}
								 //dx1, dy1, dx2, dy2, col1, row1, col2, row2
								 int rc = contents.length;
//								 int[] locations = new int[]{0, 0, 0, 0, rc , rc, rc, i + rc};
								 row.setHeight((short)1500);
								 sheet.setColumnWidth(rc, 50*200);
								 //                         dx1, dy1, dx2, dy2, col1, row1, col2, row2
								 //5,0,500,122,(short) 0, 5,(short)10,15 
								 int[] locations = new int[]{10, 10, 50, 50, rc, (i + 2), rc + 1, (i + 3)};
//								 int[] locations = new int[]{0, 0, 0, 0, rc, rc+i, rc, rc+1};
								 ExcelUtils.createPic(wb, sheet, row, locations, buf);
							 }
						 }
					}
				}
			}
			
			integralTitle = integralTitle + DateUtils.getFormatDate("yyyyMMddHHmmss");
			response.setContentType("application/x-download");
			response.setCharacterEncoding("utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + 
						UserUtils.toUtf8String(integralTitle) + ".xlsx");
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			log.error("", e);
		}
		
	}
	
	private void setCellWidth(XSSFSheet sheet, int type) {
		sheet.setColumnWidth(1, 60*100);
		if(1 == type){
			for(int i = 1; i < 23; i++){
				sheet.setColumnWidth(i, 53*100);
			}
		}if(2 == type){
			for(int i = 2; i < 11; i++){
				sheet.setColumnWidth(i, 35*100);
			}
			sheet.setColumnWidth(1, 35*100);
			sheet.setColumnWidth(3, 45*130);
			sheet.setColumnWidth(8, 55*100);
			sheet.setColumnWidth(10, 75*100);
		}else{
			for(int i = 2; i < 9; i++){
				sheet.setColumnWidth(i, 35*100);
			}
			sheet.setColumnWidth(6, 55*100);
			sheet.setColumnWidth(8, 55*100);
		}
		
	}

	public static String[] getTitleArr(String type){
//		String[] titles = {"客户名称", "客户身份证号", "积分", "账号", 
//				"存单金额", "存单号", "兑换明细", "备注", "录入人", "录入机构", "录入时间"};
		if("1".equals(type)){
			return new String[]{"客户名称", "客户身份证号", "积分", "账号", "存单号","存单金额",
					"营销人1", "营销人1金额", 
					"营销人2", "营销人2金额", 
					"营销人3", "营销人3金额", 
					"资金来源1", "资金来源金额1",
					"资金来源2", "资金来源金额2",
					"资金来源3", "资金来源金额3",
					"添加积分类型", "积分计算系数", "录入时间", "录入人", "录入机构", "备注"};
		}else if("2".equals(type)){
			return new String[]{"年份","序号","客户名称", "客户身份证号", "积分", "兑换类型积分", 
				"兑换数量", "兑换商品", "备注", "录入时间", "录入人", "录入机构","客户签名"};
/*			return new String[]{"年份","序号","客户名称", "客户身份证号", "积分", "兑换类型积分", 
					"兑换数量", "兑换商品", "备注", "录入时间", "录入人", "录入机构"};
*/		}else if("3".equals(type)){
			return new String[]{"客户名称", "客户身份证号", "积分", "vip赠送积分", 
					"vip积分赠送类型", "vip积分赠送年", "备注", "录入时间", "录入人", "录入机构"};
		}else if("4".equals(type)){
			return new String[]{"年份","序号","客户名称", "客户身份证号", "积分", "兑换类型积分", 
					"兑换数量", "兑换商品", "备注", "录入时间", "录入人", "录入机构"};
		}
		return null;
	}
	
	
	/**
	 * 添加积分
	 * @param vo
	 * @return
	 */
	public MsgResponse addIntegral(AddIntegralDetailVO vo){
		
		//校验
		MsgResponse msg = validateAddIntegral(vo);
		if(msg != null){
			return msg;
		}
		
		String pk = vo.getCustomer_idcard();
		CustomerVO customerVO = cust.queryCustByPK(pk);
		
		//修改积分
		//更新客户积分
		String sql = "UPDATE gd_customer_info2 SET now_usable_integral = now_usable_integral + ? WHERE pk_customer_info=?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(vo.getCustomer_integral());
		parameter.addParam(customerVO.getPk_customer_info());//主键
		
		vo.setCustomer_idcard(customerVO.getCustomer_idcard());//身份证
		vo.setPk_integral_detail(PrimaryKeyUtil.getPrimaryKey());//主键
		vo.setCreatetime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss"));//创建时间
		vo.setCreate_user(UserUtils.getUser());
		vo.setCreate_user_org(UserUtils.getUserOrg());
		vo.setTs(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss"));
		vo.setDef3(DateUtils.getFormatDate(DateUtils.PATTERN_YEAR));
		try {
			new DBUtils().executeUpdateSQL(sql, parameter);
			GdDataHandlerUtils<IntegralDetailVO> utils = new GdDataHandlerUtils<IntegralDetailVO>(new IntegralDetailVO());
			//插入积分明细表
			msg = utils.save(vo);
			if(msg.isFlag()){
				msg.setMessage("积分添加成功");
			}else{
				msg.setMessage("积分添加失败");
			}
			return msg;
		} catch (DAOException e) {
			log.error("积分添加失败" + e.getMessage());
			throw new RuntimeException("积分添加失败" + e.getMessage());
		}
//		return new MsgResponse();
	}
	
	/**
	 * 添加积分校验
	 * @param vo
	 * @return
	 */
	private MsgResponse validateAddIntegral(AddIntegralDetailVO vo) {
		String num = vo.getCustomer_account();
		if(num != null && !"".equals(num.trim())){
			if(!(num.length() == 16 || num.length() == 19)){
				//对的
				return new MsgResponse("账号填写错误，请重新填写", false);
			}
		}else{
			return new MsgResponse("账号为空，请重新填写", false);
		}
		//校验账号和存单号、存续期
		String deposit_receipt_num = vo.getDeposit_receipt_num();
		String def7 = vo.getDef7();//账户序号
		
		//根据账号和存单号进行查询存单号，根据存单号进行判断
		String sql = "SELECT deposit_receipt_num FROM gd_add_integral_detail WHERE " +
				"customer_account='"+num+"' AND def7='"+def7+"'";
		
		try {
			List<Object[]> objs = new DBUtils().queryObjs(sql);
			String duration = vo.getDuration();//是否存续期
			if(objs != null && objs.size() > 0){
				for(Object o:objs){
					//存单号相同
					if(deposit_receipt_num.equals(o.toString())){
						if("N".equals(duration)){
							return new MsgResponse("添加积分失败，账号、账户序号、存单号已经存在且不在存续期", false);
						}
					}else{
						//存单号不相同
						if("N".equals(duration)){
							return new MsgResponse("添加积分失败，账号、账户序号已经存在且不在存续期", false);
						}
					}
					
				}
			}
		} catch (DAOException e) {
			log.error("添加积分失败", e);
			return new MsgResponse("添加积分失败，" + e.getMessage(), false);
		}
		return null;
	}
	/**
	 * 添加积分校验
	 * @param vo
	 * @return
	 */
	private MsgResponse validateLeadIntegral(LeadDrawIntegralVO vo) {
		String num = vo.getCustomer_account();
		if(num != null && !"".equals(num.trim())){
			if(!(num.length() == 16 || num.length() == 19)){
				//对的
				return new MsgResponse("账号填写错误，请重新填写", false);
			}
		}else{
			return new MsgResponse("账号为空，请重新填写", false);
		}
		//校验账号和存单号、存续期
		String def7 = vo.getDef7();//账户序号
		
		//根据账号和存单号进行查询存单号，根据存单号进行判断
		String sql = "SELECT count(*) FROM gd_lead_integral_detail WHERE " +
				"customer_account='"+num+"' AND def7='"+def7+"'";
		
		try {
			int count = new DBUtils().getCountNumber(sql);
			if(count > 0){
				return new MsgResponse("对改账户和账户序号已经提前支取了", false);
			}
		} catch (DAOException e) {
			log.error("添加积分失败", e);
			return new MsgResponse("添加积分失败，" + e.getMessage(), false);
		}
		return null;
	}
	
	private OrgApplyProductDao prodDao = new OrgApplyProductDao();

	/**
	 * 积分兑换
	 * @param vo
	 * @return
	 */
	public MsgResponse exchangeIntegral(SubIntegralDetailVO[] vos){
		if(vos == null || vos.length <= 0){
			return new MsgResponse("请添加兑换项");
		}
		
		String pk = vos[0].getCustomer_idcard();
		CustomerVO customerVO = cust.queryCustByPK(pk);
		if(pk == null || "".equals(pk)){
			throw new RuntimeException("客户主键为空, 请在客户页面刷新查询并修改积分"); 
		}
		
		BigDecimal integralTotal = new BigDecimal("0.0");//总积分，更新到客户表，明细插入明细表
		BigDecimal customer_integral = null;
		Map<String, Integer> prods = new HashMap<String, Integer>();
		
		//查询批次号
		try {
			
			OrgApplyProductVO prod = null;
			String subNum = "0";
			String sn = querySerialNumber(customerVO);
			for(IntegralDetailVO vo : vos){
				//1、校验库存
				String giftId = vo.getTemp1();//申请的礼品主键
				prod = prodDao.get(giftId);
				subNum = vo.getDef5();//兑换数量
				
				if(Integer.parseInt(subNum) > (prod.getApply_product_num() - prod.getOrg_sub_num() - prod.getAllot_product_num())){//兑换的 >= 库存（总的-兑换的）
					//兑换的超过了审批过的数量
					return new MsgResponse(vo.getDef1() + "兑换数量超过了机构现有的数量,现有数量" + (prod.getApply_product_num() - prod.getOrg_sub_num() -  prod.getAllot_product_num()), false);
				}
				if(StringUtil.isEmpty(vo.getDef6())){//
					return new MsgResponse("请签名", false);
				}
				prods.put(prod.getPk_org_apply_product(), Integer.parseInt(subNum));//商品编码, 兑换数量
				customer_integral = new BigDecimal(vo.getDef2()).multiply(new BigDecimal(vo.getDef5())) ;
				integralTotal = integralTotal.add(customer_integral);
				//
				vo.setCustomer_idcard(customerVO.getCustomer_idcard());//身份证
				vo.setPk_integral_detail(PrimaryKeyUtil.getPrimaryKey());//主键
				vo.setCreatetime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss"));//创建时间
				vo.setCreate_user(UserUtils.getUser());
				vo.setCreate_user_org(UserUtils.getUserOrg());
				vo.setDef3(DateUtils.getFormatDate(DateUtils.PATTERN_YEAR));
				vo.setTs(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss"));
				vo.setDef8(sn);//序号
			}
			if(integralTotal.doubleValue() > customerVO.getNow_usable_integral()){
				return new MsgResponse("兑换积分超过了可用积分，修改兑换项或兑换数量", false);
			}
			//更新客户积分
			String sql = "UPDATE gd_customer_info2 SET now_usable_integral = now_usable_integral + ? WHERE pk_customer_info=?";
			SQLParameter parameter = new SQLParameter();
			integralTotal = integralTotal.multiply(new BigDecimal("-1"));
			parameter.addParam(integralTotal);
			parameter.addParam(customerVO.getPk_customer_info());
		
			new DBUtils().executeUpdateSQL(sql, parameter);
			GdDataHandlerUtils<IntegralDetailVO> utils = new GdDataHandlerUtils<IntegralDetailVO>(new IntegralDetailVO());
			MsgResponse msg = utils.saveArr(vos);
			if(msg.isFlag()){
				//2、兑换成功后需要－库存
				prodDao.updategiftNums(prods);
				msg.setMessage("积分兑换成功");
			}else{
				msg.setMessage("积分兑换失败");
			}
			return msg;
		} catch (DAOException e) {
			log.error("积分兑换失败", e);
			throw new RuntimeException("积分兑换失败, " + e.getMessage());
		}
	}

	/**
	 * 积分兑换查询序号
	 * @param customerVO
	 * @return
	 * @throws DAOException
	 */
	private String querySerialNumber(CustomerVO customerVO) throws DAOException {
		String snSQL = "SELECT MAX(CONVERT(def8,DECIMAL)) FROM gd_sub_integral_detail WHERE def4=2 " +
				"AND def3='"+DateUtils.getFormatDate(DateUtils.PATTERN_YEAR) + "' " + 
				"AND customer_idcard='"+customerVO.getCustomer_idcard()+"' ";
		int number = new DBUtils().getCountNumber(snSQL);
		return (number + 1) + "";
	}


	/**
	 * vip赠送积分
	 * @param vo
	 * @return
	 * @throws RuntimeException
	 */
	public MsgResponse vipIntegral(VipIntegralDetailVO vo) throws RuntimeException{
		
		String pk = vo.getCustomer_idcard();
		if(pk == null || "".equals(pk)){
			return new MsgResponse("客户主键为空, 请在客户页面刷新查询并修改积分", false);
		}
		CustomerVO customerVO = cust.queryCustByPK(pk);
		vo.setCustomer_idcard(customerVO.getCustomer_idcard());//身份证
		MsgResponse msg = validatedVip(vo);
		if(msg != null){
			return msg;
		}
		//更新客户积分
		double integral = Double.parseDouble(vo.getDef2());//积分
		String sql = "UPDATE gd_customer_info2 SET now_usable_integral = now_usable_integral + ? WHERE pk_customer_info=?";
		
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(integral);
		parameter.addParam(customerVO.getPk_customer_info());
		try {
			//插入积分明细表
			new DBUtils().executeUpdateSQL(sql, parameter);
			GdDataHandlerUtils<IntegralDetailVO> utils = new GdDataHandlerUtils<IntegralDetailVO>(new IntegralDetailVO());
			vo.setCustomer_integral(integral);//记录积分
			vo.setDef3(DateUtils.getFormatDate(DateUtils.PATTERN_YEAR));
//			vo.setDef4("3");//vip积分赠送
			vo.setCustomer_idcard(customerVO.getCustomer_idcard());//身份证
			vo.setPk_integral_detail(PrimaryKeyUtil.getPrimaryKey());//主键
			vo.setCreatetime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss"));//创建时间
			msg = utils.save(vo);
			if(msg.isFlag()){
				msg.setMessage("vip积分赠送成功");
			}else{
				msg.setMessage("vip积分赠送失败");
			}
			return msg;
		} catch (DAOException e) {
			log.error("vip赠送积分失败", e);
			throw new RuntimeException("vip积分赠送失败, " + e.getMessage());
		}
	}
	/**
	 * 校验VIP赠送次数
	 * @param vo
	 * @return
	 */
	private MsgResponse validatedVip(IntegralDetailVO vo) {
		String def1 = vo.getDef1();
		String def2 = vo.getDef2();
		vo.getCustomer_idcard();
		String vipNumberSql = "SELECT def2 FROM gd_integral_config " +
				"WHERE integral_type=3 AND " +//类型
				"integral_type_name='"+def1+"' " +//vip赠送名称
				"AND integral_coefficient=" + def2;//积分
		String countDetail = "SELECT COUNT(*) FROM gd_vip_integral_detail WHERE " +
				"customer_idcard= '" + vo.getCustomer_idcard()+ "' " +//身份证
				"AND DEF4=3 AND DEF3='" + DateUtils.getFormatDate(DateUtils.PATTERN_YEAR) + "'" +
				" AND def1='"+def1+"' " +//vip赠送名称
				"AND def2=" + def2;//积分
		DBUtils dbUtils = new DBUtils();
		try {
			int number = dbUtils.getCountNumber(vipNumberSql);
			int count = dbUtils.getCountNumber(countDetail);
			if(number > 0){
				if(count >= number){
					return new MsgResponse("积分赠送类型" + def1 + "已经用完，请选择其他条目", false);
				}
				return null;
			}else{
				return new MsgResponse("请先配置" + def1 + "一年可赠送次数", false);
			}
			
		} catch (DAOException e) {
			log.error("vip赠送积分失败", e);
			return new MsgResponse("vip赠送积分失败" + e.getMessage(), false);
		}
	}

	public int countExportSize(Map<String,String> params){
		//判断是不是有参数
		String where = IntegralOptionBiz.whereSql(params);
		//查询的总记录
		String queryCountSql = " SELECT COUNT(1)" +
								" FROM GD_INTEGRAL_DETAIL2 T" +
								"	LEFT JOIN ORG_EMPLOYEE E ON E.`USERID`=T.`CREATE_USER`" +
								" 	LEFT JOIN ORG_ORGANIZATION O ON O.`ORGID` = T.`CREATE_USER_ORG`" +
								" WHERE 1=1" + where;
		try {
			return new DBUtils().getCountNumber(queryCountSql);
		} catch (DAOException e) {
			log.error("", e);
		}
		return 0;
	}
	
	
	public IntegralDetailVO[] queryAll(Map<String,String> params){
		//判断是不是有参数
		String where = IntegralOptionBiz.whereSql(params);
		
		//类型
		String type = params.get("integral_type");
		String tableName = "";
		if("1".equals(type)){
			tableName = "gd_add_integral_detail";
		}else if("2".equals(type)){
			tableName = "gd_sub_integral_detail";
		}else if("3".equals(type)){
			tableName = "gd_vip_integral_detail";
		}else if("4".equals(type)){
			tableName = "gd_lead_integral_detail";
		}
		
		//查询数据实体
		VOQuery<IntegralDetailVO> voQuery = new VOQuery<IntegralDetailVO>(IntegralDetailVO.class);
	    //查询
		String querySql =" SELECT C.`CUSTOMER_NAME`, t.*," +
						"	E.`EMPNAME`, O.`ORGNAME` FROM " + tableName + " T" +
						"	LEFT JOIN ORG_EMPLOYEE E ON E.`USERID`=T.`CREATE_USER`" +
						" 	LEFT JOIN ORG_ORGANIZATION O ON O.`ORGID` = T.`CREATE_USER_ORG`" +
						" 	LEFT JOIN gd_customer_info2 C ON T.`CUSTOMER_IDCARD` = C.`CUSTOMER_IDCARD` " +
						" WHERE 1=1" + where +
						" ORDER BY T.`CUSTOMER_IDCARD`, T.CREATETIME" ;
		System.out.println("EXPORT query sql " + querySql);
		 IntegralDetailVO[] vos = voQuery.query(querySql);
		 
		 if(vos != null && vos.length > 0){
			 if("Y".equals(params.get("hiddenFlag"))){
				 for(IntegralDetailVO vo : vos){
					 vo.setCustomer_idcard(IntegralOptionBiz.idcardToX(vo.getCustomer_idcard()));
				 }
			 }
			 
		 }
		 return vos;
	}
	
}
