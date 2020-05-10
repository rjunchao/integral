/**
 * 
 */
package com.xbkj.gd.integral.report;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.eos.system.logging.Logger;
import com.pub.xbkj.pubapp.pagequery.VOPageQuery;
import com.pub.xbkj.pubapp.query.VOQuery;
import com.xbkj.gd.integral.report.vos.IntegralDetailReportVO;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.ExcelUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2020-5-10
 *@version 1.0.0
 *@desc 积分明细报表查询
 *
 */

@Bizlet("")
public class IntegralDetailReportController {
	private static final Logger log = LoggerFactory.getLogger(IntegralDetailReportController.class);
	
	private final static String INTEGRAL_SUB_DETAIL="1";//正常积分兑换
	private final static String INTEGRAL_VIP_DETAIL="2";//vip积分赠送
	private final static String INTEGRAL_AP_DETAIL="3";//活动积分赠送
	
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 */
	@Bizlet("")
	public void export(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("report_type", request.getParameter("report_type"));
		parameter.put("orgs", request.getParameter("orgs"));
		parameter.put("start_date", request.getParameter("start_date"));
		parameter.put("end_date", request.getParameter("end_date"));
		String sql = buildQuerySql(parameter);
		String[] heads = {"机构", "时间", "积分"};
		Workbook wb = ExcelUtils.buildWorkBook("xlsx", "积分报表信息", heads);
		Sheet sheet = wb.getSheetAt(0);
		ExcelUtils.setCellWidth(sheet, 3, 50);
		VOQuery<IntegralDetailReportVO> voQuery = new VOQuery<IntegralDetailReportVO>(IntegralDetailReportVO.class);
		IntegralDetailReportVO[] vos = voQuery.query(sql);
		if(vos != null && vos.length > 0) {
			String[] contents = null;
			int size = vos.length;
			IntegralDetailReportVO vo = null;
			Row row = null;
			for(int i = 0; i < size; i++) {
				vo = vos[i];
				row = sheet.createRow(i+2);
				contents = new String[] {
						vo.getOrgname(),
						vo.getTran_date(),
						vo.getTran_integral()+""
				};
				ExcelUtils.batchCreateCell(row, contents);
			}
		}
		try {
			response.setContentType("application/x-download");
			response.setCharacterEncoding("utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + 
					UserUtils.toUtf8String("积分报表信息" + DateUtils.getFormatDate("yyyyMMddHHmmss")) + ".xlsx");
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String buildQuerySql(Map<String, String> params){
		String whereSql = whereSql(params);
		String report_type = params.get("report_type");
		String sql="";
		switch (report_type) {
			case INTEGRAL_SUB_DETAIL:
				sql = buildIntegralSub(whereSql);
				break;
			case INTEGRAL_VIP_DETAIL:
				sql = buildIntegralVip(whereSql);
				break;
			case INTEGRAL_AP_DETAIL:
				sql = buildIntegralAp(whereSql);
				break;
			default:
				break;
		}
		return sql;
	}

	@Bizlet("")
	public IntegralDetailReportVO[] report(Map<String, String> params, PageCond page){
		IntegralDetailReportVO[] vos = null;
		String sql = buildQuerySql(params);
		if(StringUtils.isEmpty(sql)){
			return null;
		}
		VOPageQuery<IntegralDetailReportVO> query = new VOPageQuery<IntegralDetailReportVO>(IntegralDetailReportVO.class);
		String queryCountSql = "SELECT COUNT(*) FROM ("+sql+") C";
		log.info("query sql: "+ sql);
		vos = query.query(sql, queryCountSql , page);
		return vos;
	}
	
	private String buildIntegralAp(String whereSql) {
		return buildIntegralDetailSql("GD_AP_INTEGRAL_DETAIL", "AP_INTEGRAL", whereSql);
	}
	private String buildIntegralVip(String whereSql) {
		return buildIntegralDetailSql("GD_VIP_INTEGRAL_DETAIL", "CUSTOMER_INTEGRAL", whereSql);
	}
	
	private String buildIntegralDetailSql(String tableName, String integralFiled, String whereSql){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT R.TS AS TRAN_DATE, R.CREATE_USER_ORG, ");
		sb.append("SUM(R.");
		sb.append(integralFiled);
		sb.append(") AS TRAN_INTEGRAL, O.ORGNAME FROM");
		sb.append("(");
			sb.append("SELECT "); 
			sb.append("DATE_FORMAT(T.TS, '%Y-%m-%d') AS TS,");
			sb.append("T.CREATE_USER_ORG,");
			sb.append("T.CREATE_USER,");
			sb.append("T. ");
			sb.append(integralFiled);
			sb.append(" FROM " + tableName + " T FORCE INDEX (ts) ");
			sb.append(" WHERE TS IS NOT NULL");
			sb.append(whereSql);//查询条件
		sb.append(") R ");
		sb.append("LEFT JOIN ORG_ORGANIZATION O ON O.ORGID = R.CREATE_USER_ORG");
		sb.append(" GROUP BY R.TS, R.CREATE_USER_ORG, O.ORGNAME");
		return sb.toString();
	}

	/**
	 * 构建积分兑换报表查询sql
	 * @param whereSql
	 * @return
	 */
	private String buildIntegralSub(String whereSql) {
		/*StringBuilder sb = new StringBuilder();
		sb.append("SELECT R.TS AS TRAN_DATE, R.CREATE_USER_ORG, ");
		sb.append("SUM(R.CUSTOMER_INTEGRAL) AS TRAN_INTEGRAL, O.ORGNAME FROM");
		sb.append("(");
			sb.append("SELECT"); 
			sb.append("DATE_FORMAT(T.TS, '%Y-%m-%d') AS TS,");
			sb.append("T.CREATE_USER_ORG,");
			sb.append("T.CREATE_USER,");
			sb.append("T.CUSTOMER_INTEGRAL ");
			sb.append(" FROM  T FORCE INDEX (ts) ");
			sb.append(" WHERE TS IS NOT NULL");
			sb.append(whereSql);//查询条件
		sb.append(") R ");
		sb.append("LEFT JOIN ORG_ORGANIZATION O ON O.ORGID = R.CREATE_USER_ORG");
		sb.append("R.TS, R.CREATE_USER_ORG, O.ORGNAME");*/
		return buildIntegralDetailSql("GD_SUB_INTEGRAL_DETAIL", "CUSTOMER_INTEGRAL", whereSql);
	}


	public static String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		//类型
		String orgs = params.get("orgs");
		if(StringUtils.isNotEmpty(orgs)){
			String orgArr = convertOrgToArray(orgs);
			if(StringUtils.isNotEmpty(orgArr)){
				sb.append(" AND T.CREATE_USER_ORG IN  "+orgArr);
			}
		}
		String start_date = params.get("start_date");
		if(StringUtils.isNotEmpty(start_date)){
			sb.append(" AND T.TS > '"+start_date+"'");
		}
		
		String end_date = params.get("end_date");
		if(StringUtils.isNotEmpty(end_date)){
			//AND T.ts < '2020-03-31 23:59:59' 
			end_date = end_date.substring(0,10) + " 23:59:59";
			sb.append(" AND T.TS < '"+end_date+"'");
		}
		return sb.toString();
	}


	private static String convertOrgToArray(String orgs) {
		String[] split = orgs.substring(1).split(",");
		if(split.length > 0){
			StringBuilder sb = new StringBuilder("(");
			for(String org: split){
				sb.append('\'');
				sb.append(org);
				sb.append('\'');
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")");
			return sb.toString();
		}
		return null;
	}
}
