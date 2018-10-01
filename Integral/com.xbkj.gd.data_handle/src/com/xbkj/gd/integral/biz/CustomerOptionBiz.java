package com.xbkj.gd.integral.biz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.eos.foundation.PageCond;
import com.eos.foundation.impl.PageCondImpl;
import com.eos.system.annotation.Bizlet;
import com.pub.xbkj.common.MsgResponse;
import com.pub.xbkj.pubapp.pagequery.VOPageQuery;
import com.pub.xbkj.pubapp.query.VOQuery;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.util.MapUtil;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.gd.integral.vos.CustomerVO;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.ExcelUtils;
import com.xbkj.gd.utils.FileImportUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2017-9-5
 *@version 1.0.0
 *@desc
 *	客户信息维护
 *		客户信息的CRUD、导出
 */
@Bizlet
public class CustomerOptionBiz {
	
	public String importCustomer(HttpServletRequest request){
		//解析请求得到文件输入流
		InputStream is = null;
		try {
			is = FileImportUtils.getExcelIs(request);
			if(is == null){
				return "导入失败，文件为空";
			}
			//解析流数据
			HSSFWorkbook wb = new HSSFWorkbook(is);
			CustomerVO[] vos = processData(wb);
			//写入数据库
			if(vos != null && vos.length > 0){
				GdDataHandlerUtils<CustomerVO> voUtils = new GdDataHandlerUtils<CustomerVO>(new CustomerVO());
				voUtils.saveArr(vos);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "导入客户信息失败，" + e.getMessage();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "客户信息导入成功";
	}
	
	private CustomerVO[] processData(HSSFWorkbook wb) {
		//key为身份证
		Map<String, CustomerVO> voMap = new HashMap<String, CustomerVO>();
		//解析
		HSSFSheet sheet = wb.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();
		if(rows > 0){
			CustomerVO vo = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			for(int i = 2; i < rows; i++){
				vo = new CustomerVO();
				row = sheet.getRow(i);
				if(row != null){
					//循环获取单元格的值
					cell = row.getCell(0);
					vo.setCustomer_name(cell.getStringCellValue());//客户名称
					cell = row.getCell(1);
					String idcard = cell.getStringCellValue();//身份证
					vo.setCustomer_idcard(idcard);
					cell = row.getCell(2);
					vo.setCustomer_phone(cell.getStringCellValue());//电话号码
					cell = row.getCell(3);
					vo.setNow_usable_integral(Double.parseDouble(cell.getStringCellValue()));//积分
					cell = row.getCell(4);
					vo.setDef1(cell.getStringCellValue());//vip 标识
					cell = row.getCell(5);
					vo.setCreate_user(cell.getStringCellValue());//录入人
					cell = row.getCell(6);
					vo.setInput_org((int)cell.getNumericCellValue()+"");//录入机构
					//备注
					cell = row.getCell(8);
					if(cell != null){
						vo.setRecommend_phone(cell.getStringCellValue());
					}
					vo.setPk_customer_info(PrimaryKeyUtil.getPrimaryKey());
					vo.setCreatetime(DateUtils.getFormatDate(DateUtils.PATTERN_19));
					vo.setDr("0");
					voMap.put(idcard, vo);//防止身份证号一样
				}
			}
		}
		Collection<CustomerVO> vos = voMap.values();
		if(vos != null && vos.size() > 0){
			return vos.toArray(new CustomerVO[vos.size()]);
		}
		return null;
	}

	public void exportCustomerInfo(HttpServletRequest request, HttpServletResponse response){
		//导出客户信息	Map<String, String> parameter = new HashMap<String, String>();
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("customer_idcard", request.getParameter("customer_idcard"));
		parameter.put("customer_name", request.getParameter("customer_name"));
		parameter.put("customer_phone", request.getParameter("customer_phone"));
		parameter.put("start_date", request.getParameter("start_date"));
		parameter.put("end_date", request.getParameter("end_date"));
		parameter.put("hiddenFlag", request.getParameter("hiddenFlag"));
		PageCondImpl page = new PageCondImpl();
		page.set("begin", 0);
		page.set("length", 60000);
		//如果查询为空导出一个模板
		CustomerVO[] customers = this.queryCustomerPage(parameter, page);
		if(customers == null){
			customers = new CustomerVO[0];
		}
		//导出
	//	System.out.println("export vo:" + integrals);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("客户信息表");
		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell = row0.createCell(0);
		//第一行
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short)12);
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(style);
		
		cell.setCellValue("客户积分明细表");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		cell.setCellStyle(style);
		/*
		 * T.`PK_CUSTOMER_INFO`, T.`CUSTOMER_NAME`,T.`CUSTOMER_IDCARD`, T.`CUSTOMER_PHONE`, " +
							"	T.`RECOMMEND_PHONE`,T.`NOW_USABLE_INTEGRAL`,T.`INPUT_ORG`, O.ORGNAME
		 */
		String[] titles = {"客户名称", "客户身份证号", "电话", 
					"当前可用积分", "VIP", "录入人", "录入机构" , "录入时间", "备注"};
		//创建表头
		HSSFRow row = sheet.createRow(1);
		ExcelUtils.batchCreateCell(row, titles);
		if(customers.length >= 0){
			String[] contents = null;
			CustomerVO vo = null;
			for(int i = 0, len = customers.length; i < len; i++){
				vo = customers[i];
				String customer_name = vo.getCustomer_name();
				String idcard = vo.getCustomer_idcard();
				String phone = vo.getCustomer_phone();
				String recommend_phone = vo.getRecommend_phone();
				double integral = vo.getNow_usable_integral();
				String vip = vo.getDef1();
				String orgname = vo.getOrgname();
				String empname = vo.getEmpname();
				String ts = vo.getTs();
				contents = new String[]{customer_name, idcard, phone, integral + "",vip, empname, orgname , ts, recommend_phone};
				row = sheet.createRow((i+2));
				ExcelUtils.batchCreateCell(row, contents);
			}
		}
		
		
		response.setContentType("application/x-download");
		response.setCharacterEncoding("utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + 
						UserUtils.toUtf8String("客户信息表") + ".xls");
		try {
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除客户信息
	 * 真删还是假删
	 * @param vo
	 * @return
	 */
	@Bizlet
	public MsgResponse delCustomer(CustomerVO vo){
		if(vo == null){
			return new MsgResponse("删除失败,对象为空！", false);
		}
		String pk = vo.getPk_customer_info();
		if(StringUtils.isNotEmpty(pk)){
			//根据主键进行删除或者更新
			//UPDATE gd_customer_info2 t SET t.`dr` = 1 WHERE t.`pk_customer_info`='';
			String sql = "DELETE FROM gd_customer_info2  WHERE `pk_customer_info`=? ";
			SQLParameter parameter = new SQLParameter();
			parameter.addParam(pk);
			//谁录入的谁删除
			try {
				new DBUtils().executeUpdateSQL(sql, parameter);
				return new MsgResponse("删除成功！", true);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return new MsgResponse("删除失败", false);
	}
	
	/**
	 * 分页查询
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public CustomerVO[] queryCustomerPage(Map<String,String> params,PageCond page){
		//判断是不是有参数
 		String where = whereSql(params);
		//查询数据实体
		VOPageQuery<CustomerVO> query = new VOPageQuery<CustomerVO>(CustomerVO.class);
		//查询的总记录
		String queryCountSql = "SELECT COUNT(*)" +
							"	FROM gd_customer_info2 T " +
							"	LEFT JOIN ORG_ORGANIZATION O ON O.ORGID = T.`INPUT_ORG` " +
							"	LEFT JOIN ORG_EMPLOYEE E ON E.`EMPCODE`= T.`CREATE_USER` " +
							"	WHERE T.`DR` = 0 " + where;
	    //查询
		String querySql = "SELECT T.*, E.EMPNAME, O.`ORGNAME`" +
							"	FROM gd_customer_info2 T " +
							"  LEFT JOIN ORG_ORGANIZATION O ON T.`INPUT_ORG`= O.`ORGID`" +
							"	LEFT JOIN ORG_EMPLOYEE E ON E.`EMPCODE`= T.`CREATE_USER` " +
							"	WHERE T.`DR` = 0 " + where + " ORDER BY TS DESC";
/*		String querySql = "SELECT T.`PK_CUSTOMER_INFO`, T.`CUSTOMER_NAME`,T.`CUSTOMER_IDCARD`, T.`CUSTOMER_PHONE`, " +
				"	T.`RECOMMEND_PHONE`,T.`NOW_USABLE_INTEGRAL`,T.`INPUT_ORG` " +
				"	, T.`TS`, T.`CREATE_USER`,  E.EMPNAME " +//O.`ORGNAME`,
				"	FROM gd_customer_info2 T " +
				//"	LEFT JOIN ORG_ORGANIZATION O ON O.ORGID = T.`INPUT_ORG` " +
				"	LEFT JOIN ORG_EMPLOYEE E ON E.`EMPCODE`= T.`CREATE_USER` " +
				"	WHERE T.`DR` = 0 " + where + " ORDER BY T.`PK_CUSTOMER_INFO`, T.`CREATETIME`";
*/		
		System.out.println("客户信息查询：query sql :" + querySql);
		
		CustomerVO[] vos = query.query(querySql, queryCountSql, page);
	//	List<CustomerVO> vos = new GdDataHandlerUtils<CustomerVO>(new CustomerVO()).query(querySql, queryCountSql, page);
		if(vos != null && vos.length > 0){
//			if(vos != null && vos.size() > 0){
			if("Y".equals(params.get("hiddenFlag"))){
				for(CustomerVO vo : vos){
					vo.setReal_idcard(vo.getCustomer_idcard());
					vo.setCustomer_idcard(idcardToX(vo.getCustomer_idcard()));
				}
			}
			return vos;
//			return vos.toArray(new CustomerVO[vos.size()]);
		}
		return null;
	}

	public CustomerVO queryCustByPK(String pk){
		String sql = "SELECT * FROM gd_customer_info2 T WHERE T.PK_CUSTOMER_INFO='"+pk+"'";
		VOQuery<CustomerVO> query = new VOQuery<CustomerVO>(CustomerVO.class);
		return query.query(sql)[0];
				
	}
	private String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		
		if(!MapUtil.isEmpty(params)){
			//身份证、名字 {customer_idcard=null, customer_name=阮}
			String customer_name = params.get("customer_name");
			if(StringUtils.isNotEmpty(customer_name)){
				sb.append(" AND T.CUSTOMER_NAME = '"+customer_name.trim()+"'");
			}
			
			String customer_idcard = params.get("customer_idcard");
			if(StringUtils.isNotEmpty(customer_idcard)){
				sb.append(" AND T.CUSTOMER_IDCARD = '"+customer_idcard+"'");
			}
			
			String customer_phone = params.get("customer_phone");
			if(StringUtils.isNotEmpty(customer_phone)){
				sb.append(" AND T.customer_phone = '"+customer_phone+"'");
			}
			String start_date = params.get("start_date");
			if(StringUtils.isNotEmpty(start_date)){
				sb.append(" AND T.createtime > '"+start_date+"'");
			}
			String end_date = params.get("end_date");
			if(StringUtils.isNotEmpty(end_date)){
				sb.append(" AND T.createtime < '"+end_date+"'");
			}
			
			String creater = params.get("create_user");
			if(StringUtils.isNotEmpty(creater)){
				String inExpress = "SELECT E.EMPCODE FROM ORG_EMPLOYEE E WHERE " +
						"E.USERID LIKE '%"+creater+"%' OR E.EMPCODE LIKE '%"+creater+"%' OR E.EMPNAME LIKE '%"+creater+"%'";
				sb.append(" AND T.create_user in ("+inExpress+")");
			}
			
		}
		return sb.toString();
	}
	
	/**
	 * 修改客户信息
	 * @param vo
	 * @return
	 */
	@Bizlet
	public MsgResponse updateCustomerInfo(CustomerVO vo){
		MsgResponse msg = vaildateVO(vo);
		if(msg != null){
			return msg;
		}
		/*
		 * 修改时判断身份证的方式需要改变 
		 * 先查询出原来的身份证号
		 * 和现在的进行比对，如果相同就是没有修改身份证号，如果不相同，在校验是否重复
		 * 需要注意*号，如果有*就是没有修改，就不改身份证号
		 * VIP标识
		 */
		String sql = "SELECT t.`customer_idcard` FROM gd_customer_info2 t " +
				"WHERE t.`dr` = 0 AND t.`pk_customer_info`='"+vo.getPk_customer_info()+"'";
		String old_idcard = "";
		try {
			old_idcard = new DBUtils().getOneValue(sql);//532123199206183314
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		String customer_idcard = vo.getCustomer_idcard();
		String tmpId = idcardToX(old_idcard);//加了*号的
		SQLParameter parameter = new SQLParameter();
		if(tmpId.equals(customer_idcard)){
			//没有修改身份证号
			vo.setCustomer_idcard(old_idcard);
		}
		//修改 手机号、姓名、推荐人手机号、修改时间、修改人
		sql = "UPDATE gd_customer_info2 T " +
				"	SET T.`CUSTOMER_PHONE` = ?, T.`CUSTOMER_NAME`=?, T.`RECOMMEND_PHONE`=?, " +
				"	T.`CUSTOMER_IDCARD` = ?, " +
				"	T.`MODIFIEDTIME`= ?, T.`MODIFIER` = ? , t.def1=?" +
				"	WHERE T.`PK_CUSTOMER_INFO`=? ";
		
		
		parameter.addParam(vo.getCustomer_phone());
		parameter.addParam(vo.getCustomer_name());
		parameter.addParam(vo.getRecommend_phone());
		//parameter.addParam(vo.getNow_usable_integral());
		parameter.addParam(vo.getCustomer_idcard());
		parameter.addParam(DateUtils.getFormatDate(DateUtils.PATTERN_19));
		parameter.addParam(UserUtils.getUser());
		parameter.addParam(vo.getDef1());
		parameter.addParam(vo.getPk_customer_info());
		try {
			new DBUtils().executeUpdateSQL(sql, parameter);
			return new MsgResponse("修改成功", true);
		} catch (DAOException e) {
			e.printStackTrace();
			return new MsgResponse("修改失败, " + e.getMessage(), false);
		}
	}
	
	//查询身份证是否重复
	@Bizlet
	public int queryIdCard(String idcard){
		
		String sql = "SELECT COUNT(*) FROM gd_customer_info2 t WHERE t.`dr` = 0 and t.`customer_idcard` = '"+idcard+"'";
		try {
			return new DBUtils().getCountNumber(sql);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	//查询身份证是否重复
		@Bizlet
		public int queryIdCard(String idcard, String pk){
			
			String sql = "SELECT COUNT(*) FROM gd_customer_info2 t WHERE t.`dr` = 0 " +
					"	and t.`customer_idcard` = '"+idcard+"' and t.`pk_customer_info` != '"+pk+"'";
			try {
				return new DBUtils().getCountNumber(sql);
			} catch (DAOException e) {
				e.printStackTrace();
			}
			return 0;
		}

	/**
	 * 保存
	 * 身份证不能为空，且唯一
	 * @param vo
	 * @return
	 */
	@Bizlet
	public MsgResponse addCustomer(CustomerVO vo){
		MsgResponse msg = vaildateVO(vo);
		if(msg != null){
			return msg;
		}
		GdDataHandlerUtils<CustomerVO> voUtils = new GdDataHandlerUtils<CustomerVO>(new CustomerVO());
		vo.setPk_customer_info(PrimaryKeyUtil.getPrimaryKey());
		vo.setCreatetime(DateUtils.getFormatDate(DateUtils.PATTERN_19));
		vo.setInput_org(UserUtils.getUserOrg());//建档机构
		vo.setDr("0");
		msg = voUtils.save(vo);
		return msg;
	}
	
	private MsgResponse vaildateVO(CustomerVO vo){
		MsgResponse msg = null;
		if(vo == null){
			return new MsgResponse("保存对象为空", false);
		}//在数据库了，加了身份证的唯一索引，不用校验
		
		String customer_idcard = vo.getCustomer_idcard();
		if(StringUtils.isEmpty(customer_idcard)){
			return new MsgResponse("身份证号为空，请填写！", false);
		}
		if(!customer_idcard.startsWith("**") && !customer_idcard.endsWith("**")){
			if(customer_idcard.length() != 18){
				return new MsgResponse("身份证号填写错误，请重新填写！", false);
			}
		}
		if(StringUtils.isNotEmpty(vo.getCustomer_phone()) && vo.getCustomer_phone().trim().length() != 11){
			return new MsgResponse("手机号填写错误，请重新填写！", false);
		}
		String pk = vo.getPk_customer_info();
		if(StringUtils.isNotEmpty(pk)){
			if(queryIdCard(customer_idcard, pk) > 0){
				return new MsgResponse("身份证号重复，请重新填写！", false);
			}
		}else{
			if(queryIdCard(customer_idcard) > 0){
				return new MsgResponse("身份证号重复，请重新填写！", false);
			}
		}
		
		
		return msg;
	}
	
	private String idcardToX(String idcard){
		if(idcard == null){
			return "";
		}
		if("".equals(idcard)){
			return "";
		}
		String tempidcard = idcard.substring(6, 14);
		return "**"+ tempidcard+ "**";
	}
	
	/*private String queryPermissions(){
		//查询权限，只能查询当前机构和下级机构
		StringBuilder sb = new StringBuilder();
		
		return sb.toString();
	}*/
}
