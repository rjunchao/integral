package com.xbkj.gd.integral.prod.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.foundation.PageCond;
import com.eos.system.logging.Logger;
import com.pub.xbkj.pubapp.pagequery.VOPageQuery;
import com.xbkj.gd.integral.prod.vos.ReportProductVO;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-11-9
 *@version 1.0.0
 *@desc
 */
public class ReportDao {
	private static final Logger log = LoggerFactory.getLogger(ReportDao.class);
	
	private VOPageQuery<ReportProductVO> pageQuery = new VOPageQuery<ReportProductVO>(ReportProductVO.class);
	
	
	
	public ReportProductVO[] countApplyProduct(Map<String, String> params, PageCond page){
		
		/*
		 * counttype
		 * 	1：按分理处统计
		 * 	2：按机构分理处统计
		 * 	3：按机构统计
		 */
		
		/*String now_user = params.get("now_user");
		if(!StringUtils.isNotEmpty(now_user)){
			//按机构
			return countOrgApplyProduct(params, page);
		}*/
		
		//判断是不是有参数
 		String where = whereSql(params);
		//查询数据实体
		StringBuilder countSB = new StringBuilder();
		StringBuilder querySB = new StringBuilder();
		/*
		 * counttype
		 * 	1：按分理处统计
		 * 	2：按机构分理处统计汇总明细
		 * 	3：按机构统计-汇总
		 */
		
		String type = params.get("counttype");
		//查询的总记录
		countSB.append(" SELECT COUNT(*) FROM  (");
		countSB.append(" SELECT SUM(P.APPLY_PRODUCT_NUM) AS APPLY_PRODUCT_NUM,");//总数
		countSB.append(" SUM(P.ORG_SUB_NUM) AS ORG_SUB_NUM,");//兑换数
		countSB.append(" SUM(P.ALLOT_PRODUCT_NUM) AS ALLOT_PRODUCT_NUM,");//调换总数
//		countSB.append(" (T.APPLY_PRODUCT_NUM - T.ORG_SUB_NUM) AS RESIDUE_CLASS,");//剩余数
		countSB.append(" P.APPLY_PRODUCT_NAME");
		countSB.append(" ,O.ORGNAME");
		if("1".equals(type) || "2".equals(type)){
			countSB.append(" , P.APPLY_USER");
		}
		countSB.append(" , P.APPLY_ORG");
		/*if("3".equals(type)){
			countSB.append(", P.APPLY_ORG");
		}*/
		countSB.append(" FROM GD_ORG_APPLY_PRODUCT P");
		countSB.append(" LEFT JOIN ORG_ORGANIZATION O ON P.APPLY_ORG = O.ORGID");
		countSB.append(" WHERE P.AUDIT_STATUS IN('5', '7') ");
		countSB.append(where);
		countSB.append(" GROUP BY ");
		countSB.append(" P.APPLY_PRODUCT_NAME");
		if("1".equals(type) || "2".equals(type)){
			countSB.append(" , P.APPLY_USER");
		}
		countSB.append(" , P.APPLY_ORG");
		/*if("3".equals(type)){
			countSB.append(" , P.APPLY_ORG");
		}*/
		countSB.append(" ) T");
		
		
		
		
	    //查询
		querySB.append("SELECT");
		querySB.append(" T.APPLY_PRODUCT_NUM,");//总数
		querySB.append(" T.ORG_SUB_NUM,");//兑换数
		querySB.append(" T.ALLOT_PRODUCT_NUM,");//兑换数
		
		querySB.append(" (T.APPLY_PRODUCT_NUM - T.ORG_SUB_NUM - T.ALLOT_PRODUCT_NUM) AS RESIDUE_CLASS");//剩余数
		querySB.append(" , T.APPLY_PRODUCT_NAME");//礼品名称
		querySB.append(" , T.ORGNAME");//机构名称
		if("1".equals(type) || "2".equals(type)){
			querySB.append(" , T.APPLY_USER");
		}
		/*if("3".equals(type)){
			countSB.append(", P.APPLY_ORG");
		}*/
		querySB.append(" FROM  (");
		querySB.append(" SELECT SUM(P.APPLY_PRODUCT_NUM) AS APPLY_PRODUCT_NUM,");//商品统计总数
		querySB.append(" SUM(P.ORG_SUB_NUM) AS ORG_SUB_NUM,");//兑换数统计
		querySB.append(" SUM(P.ALLOT_PRODUCT_NUM) AS ALLOT_PRODUCT_NUM,");//调换总数
		querySB.append(" P.APPLY_PRODUCT_NAME");//名称
		querySB.append(" ,O.ORGNAME");//机构
		if("1".equals(type) || "2".equals(type)){
			querySB.append(", P.APPLY_USER");
		}
		/*if("3".equals(type)){
			querySB.append(", P.APPLY_ORG");
		}*/
		querySB.append(" FROM GD_ORG_APPLY_PRODUCT P");
		querySB.append(" LEFT JOIN ORG_ORGANIZATION O ON P.APPLY_ORG = O.ORGID");
		querySB.append(" WHERE P.AUDIT_STATUS IN('5', '7') ");
		querySB.append(where);
		querySB.append(" GROUP BY");
		querySB.append(" P.APPLY_PRODUCT_NAME");
		querySB.append(" , P.APPLY_ORG");
		if("1".equals(type) || "2".equals(type)){
			querySB.append(", P.APPLY_USER");
		}
		querySB.append(" ORDER BY");
		querySB.append(" P.APPLY_ORG,");
		if("1".equals(type) || "2".equals(type)){
			querySB.append("P.APPLY_USER,");
		}
		querySB.append(" P.APPLY_PRODUCT_NAME");
		/*if("3".equals(type)){
			querySB.append(", P.APPLY_ORG");
		}*/
		querySB.append(" ) T");
		log.info("report sql ===========" + querySB.toString());
		ReportProductVO[] vos = pageQuery.query(querySB.toString(), countSB.toString(), page);
		return vos;
	}
	public ReportProductVO[] countOrgApplyProduct(Map<String, String> params, PageCond page){
		//判断是不是有参数
		String where = whereSql(params);
		//查询数据实体
		
		//查询的总记录
		String queryCountSql = "SELECT COUNT(*) " +
				" FROM  (" +
				"	SELECT  " +
				"		SUM(P.APPLY_PRODUCT_NUM) AS APPLY_PRODUCT_NUM, " +
				"		SUM(P.ORG_SUB_NUM) AS ORG_SUB_NUM, " +
				"		P.APPLY_PRODUCT_NAME, " +
				"		P.APPLY_ORG, P.APPLY_USER, " +
				"		O.ORGNAME   " +
				"		FROM GD_ORG_APPLY_PRODUCT P " +
				"			LEFT JOIN ORG_ORGANIZATION O ON P.APPLY_ORG = O.ORGID" +
				"		WHERE 1 = 1   " + where + 
				"		GROUP BY " +
				"			P.APPLY_PRODUCT_NAME, " +
				"			P.APPLY_ORG, P.APPLY_USER " +
				"	) T ";
		//查询
		String querySql = "SELECT  " +
				"T.APPLY_PRODUCT_NUM, " +
				"T.ORG_SUB_NUM, " +
				"T.APPLY_PRODUCT_NAME, " +
				"T.APPLY_ORG, " +
				"T.ORGNAME," +
				"T.APPLY_USER," +
				"(T.APPLY_PRODUCT_NUM - T.ORG_SUB_NUM) AS RESIDUE_CLASS  " +
				"FROM  (" +
				"	SELECT  " +
				"		SUM(P.APPLY_PRODUCT_NUM) AS APPLY_PRODUCT_NUM, " +
				"		SUM(P.ORG_SUB_NUM) AS ORG_SUB_NUM, " +
				"		P.APPLY_PRODUCT_NAME, " +
				"		P.APPLY_ORG, P.APPLY_USER,  " +
				"		O.ORGNAME   " +
				"		FROM GD_ORG_APPLY_PRODUCT P " +
				"			LEFT JOIN ORG_ORGANIZATION O ON P.APPLY_ORG = O.ORGID" +
				"		WHERE 1 = 1   " + where + 
				"		GROUP BY " +
				"			P.APPLY_PRODUCT_NAME, " +
				"			P.APPLY_ORG, P.APPLY_USER" +
				"		ORDER BY " +
				"			P.APPLY_ORG, " +
				"			P.APPLY_USER, P.APPLY_PRODUCT_NAME" +
				"	) T ";
		log.info("report sql ===========" + querySql);
		ReportProductVO[] vos = pageQuery.query(querySql, queryCountSql, page);
		return vos;
	}


	private String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
	//按时间范围
		String end_date = params.get("end_date");
		if(StringUtils.isNotEmpty(end_date)){
			sb.append(" AND P.MODIFIEDTIME <= '"+end_date+"'");
		}
		String start_date = params.get("start_date");
		if(StringUtils.isNotEmpty(start_date)){
			sb.append(" AND P.MODIFIEDTIME >= '"+start_date+"'");
		}
		//按礼品统计
		String apply_product_name = params.get("product_name");
		if(StringUtils.isNotEmpty(apply_product_name)){
			sb.append(" AND P.APPLY_PRODUCT_NAME = '"+apply_product_name+"'");
		}
		//当前用户
		String now_user = params.get("now_user");
		if(StringUtils.isNotEmpty(now_user)){
			sb.append(" AND P.APPLY_USER = '"+UserUtils.getUser()+"'");//当前用户
		}
		//按制定机构
		String apply_org = params.get("apply_org");
		if(StringUtils.isNotEmpty(apply_org)){
			sb.append(" AND P.APPLY_ORG = '"+apply_org+"'");
		}
		//所有机构
		List<String> orgs = UserUtils.findDeptAndChildrenDept();
		if(orgs != null && orgs.size() > 0){
			sb.append(" AND P.APPLY_ORG IN (");
			for(String org : orgs){
				sb.append(org + ",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")");
		}
		return sb.toString();
	}

}
