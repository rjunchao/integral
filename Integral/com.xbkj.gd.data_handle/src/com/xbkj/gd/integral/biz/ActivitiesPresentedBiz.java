/**
 * 
 */
package com.xbkj.gd.integral.biz;


import java.io.IOException;
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
import com.sun.star.uno.RuntimeException;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.gd.integral.vos.ActivitiesPresentedVO;
import com.xbkj.gd.integral.vos.CustomerVO;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.ExcelUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email rjunchao@126.com.cn
 *@date 2020-2-25
 *@version 1.0.0
 *@desc 活动积分赠送
 */

@Bizlet
public class ActivitiesPresentedBiz {

	/**
	 * 分页查询 积分信息
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public ActivitiesPresentedVO[] queryCustApIntegralDetail(Map<String,String> params,PageCond page){
		//判断是不是有参数
		String where = whereSql(params);
		//查询数据实体
		VOPageQuery<ActivitiesPresentedVO> query = new VOPageQuery<ActivitiesPresentedVO>(ActivitiesPresentedVO.class);
		//查询的总记录
		String queryCountSql = " SELECT COUNT(1) FROM GD_AP_INTEGRAL_DETAIL T" +
								"	LEFT JOIN ORG_EMPLOYEE E ON E.`USERID`=T.`CREATE_USER`" +
								" 	LEFT JOIN ORG_ORGANIZATION O ON O.`ORGID` = T.`CREATE_USER_ORG`" +
								" WHERE 1=1" + where;
	    //查询
		String querySql =" SELECT C.`CUSTOMER_NAME`, t.*," +
						"	E.`EMPNAME`, O.`ORGNAME` FROM GD_AP_INTEGRAL_DETAIL T" +
						
						"	LEFT JOIN ORG_EMPLOYEE E ON E.`USERID`=T.`CREATE_USER`" +
						" 	LEFT JOIN ORG_ORGANIZATION O ON O.`ORGID` = T.`CREATE_USER_ORG`" +
						" 	LEFT JOIN GD_CUSTOMER_INFO2 C ON T.`CUSTOMER_IDCARD` = C.`CUSTOMER_IDCARD` " +
						" WHERE 1=1" + where ;
		System.out.println("积分明细查询： " + querySql);
		ActivitiesPresentedVO[] vos = query.query(querySql, queryCountSql, page);
		 if(vos != null && vos.length > 0){
			 if("Y".equals(params.get("hiddenFlag"))){
				 for(ActivitiesPresentedVO vo : vos){
					 vo.setCustomer_idcard(idcardToX(vo.getCustomer_idcard()));
				 }
			 }
			 
		 }
		 return vos;
	}
	//身份证号只显示出生的月日
	public static String idcardToX(String idcard){
		if(idcard == null){
			return "";
		}
		if("".equals(idcard)){
			return "";
		}
		try {
			idcard = idcard.substring(6, 14);
			return "**"+ idcard+ "**";
		} catch (Exception e) {
			return "******";
		}
	}
	
	/**
	 * 导出积分信息
	 * @param request
	 * @param response
	 */
	public void exportIntegral(HttpServletRequest request, HttpServletResponse response){
		//导出积分明细信息
		//得到参数，根据参数查询得到数据
//		Map parameter = request.getParameterMap();
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("customer_idcard", request.getParameter("customer_idcard"));
		parameter.put("start_date", request.getParameter("start_date"));
		parameter.put("end_date", request.getParameter("end_date"));
		parameter.put("hiddenFlag", request.getParameter("hiddenFlag"));
		PageCondImpl page = new PageCondImpl();
		page.set("begin", 0);
		page.set("length", 65535);
		//如果查询为空导出一个模板
		
		
		ActivitiesPresentedVO[] integrals = this.queryCustApIntegralDetail(parameter, page);
		if(integrals == null){
			integrals = new ActivitiesPresentedVO[0];
		}
		//导出
	//	System.out.println("export vo:" + integrals);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("客户线下积分明细表");
		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell = row0.createCell(0);
		//第一行
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short)12);
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(style);
		
		cell.setCellValue("客户活动积分明细表");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		cell.setCellStyle(style);

		String[] titles = {"客户名称", "客户身份证号", "积分", "备注", "录入人", "录入机构", "录入时间"};
		//创建表头
		HSSFRow row = sheet.createRow(1);
		ExcelUtils.batchCreateCell(row, titles);
		if(integrals.length >= 0){
			String[] contents = null;
			ActivitiesPresentedVO vo = null;
			for(int i = 0, len = integrals.length; i < len; i++){
				vo = integrals[i];
				String customer_name = vo.getCustomer_name();
				String idcard = vo.getCustomer_idcard();
				double integral = vo.getAp_integral();
				String remark = vo.getRemark();
				String empname = vo.getEmpname();
				String orgname = vo.getOrgname();
				String ts = vo.getTs();
				
				contents = new String[]{customer_name, idcard, integral+"", remark, empname, orgname, ts};
				row = sheet.createRow((i+2));
				ExcelUtils.batchCreateCell(row, contents);
			}
		}
		
		
		response.setContentType("application/x-download");
		response.setCharacterEncoding("utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + 
						UserUtils.toUtf8String("客户活动积分明细表") + ".xls");
		try {
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		//类型
		
		String customer_idcard = params.get("customer_idcard");
		if(StringUtils.isNotEmpty(customer_idcard)){
			sb.append(" AND T.CUSTOMER_IDCARD = '"+customer_idcard+"'");
		}
		String start_date = params.get("start_date");
		if(StringUtils.isNotEmpty(start_date)){
			sb.append(" AND T.ts > '"+start_date+"'");
		}
		
		String end_date = params.get("end_date");
		if(StringUtils.isNotEmpty(end_date)){
			sb.append(" AND T.ts < '"+end_date+"'");
		}
		return sb.toString();
	}
	
	
	/**
	
	/**
	 * 添加积分
	 * @param vos
	 * @return
	 */
	@Bizlet
	public MsgResponse custActivitiesPresented(ActivitiesPresentedVO vo){
		MsgResponse vipIntegral = addIntegral(vo);
		System.out.println("活动积分赠送成功");
		return vipIntegral;
	}
	
	public MsgResponse addIntegral(ActivitiesPresentedVO vo){
		
		//校验
		MsgResponse msg = validateAddIntegral(vo);
		if(msg != null){
			return msg;
		}
		CustomerOptionBiz cust = new CustomerOptionBiz();
		
		String pk = vo.getCustomer_idcard();
		CustomerVO customerVO = cust.queryCustByPK(pk);
		
		//修改积分
		//更新客户积分
		String sql = "UPDATE gd_customer_info2 SET now_usable_integral = now_usable_integral + ? WHERE pk_customer_info=?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(vo.getAp_integral());
		parameter.addParam(customerVO.getPk_customer_info());//主键
		vo.setCustomer_idcard(customerVO.getCustomer_idcard());//身份证
		
		vo.setPk_ap_integral_detail(PrimaryKeyUtil.getPrimaryKey());//主键
		vo.setCreatetime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss"));//创建时间
		vo.setCreate_user(UserUtils.getUser());
		vo.setCreate_user_org(UserUtils.getUserOrg());
		vo.setTs(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss"));
		try {
			new DBUtils().executeUpdateSQL(sql, parameter);
			GdDataHandlerUtils<ActivitiesPresentedVO> utils = new GdDataHandlerUtils<ActivitiesPresentedVO>(new ActivitiesPresentedVO());
			//插入积分明细表
			msg = utils.save(vo);
			if(msg.isFlag()){
				msg.setMessage("积分添加成功");
			}else{
				msg.setMessage("积分添加失败");
			}
			return msg;
		} catch (DAOException e) {
			e.printStackTrace();
			System.out.println("积分添加失败" + e.getMessage());
			throw new RuntimeException("积分添加失败" + e.getMessage());
		}
//		return new MsgResponse();
	}

	private MsgResponse validateAddIntegral(ActivitiesPresentedVO vo) {
		return null;
	}
}
