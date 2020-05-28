package com.xbkj.gd.integral.prod.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.eos.foundation.PageCond;
import com.pub.xbkj.common.MsgResponse;
import com.pub.xbkj.pubapp.pagequery.VOPageQuery;
import com.pub.xbkj.pubapp.query.VOQuery;
import com.sun.star.uno.RuntimeException;
import com.xbkj.common.util.MapUtil;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.gd.integral.prod.vos.OrgProdAllotVO;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2020-5-13
 *@version 1.0.0
 *@desc
 */
public class OrgProdAllotDao {

	private GdDataHandlerUtils<OrgProdAllotVO> voUtils = new GdDataHandlerUtils<OrgProdAllotVO>(new OrgProdAllotVO());
	
	public MsgResponse save(OrgProdAllotVO vo){
		vo.setPk_org_prod_allot(PrimaryKeyUtil.getPrimaryKey());
		MsgResponse msg = voUtils.save(vo);
		if(!msg.isFlag()){
			throw new RuntimeException(msg.getMessage());
		}
		return msg;
	}
	
	public OrgProdAllotVO[] page(Map<String, String> params, PageCond page, boolean isPage){
		//判断是不是有参数
 		String where = whereSql(params);
		//查询数据实体
		VOPageQuery<OrgProdAllotVO> query = new VOPageQuery<OrgProdAllotVO>(OrgProdAllotVO.class);
		//查询的总记录
		String queryCountSql = "SELECT  COUNT(*)" +
				"FROM GD_ORG_PROD_ALLOT P WHERE PK_ORG_PROD_ALLOT IS NOT NULL " + where;
	    //查询
		String querySql = "SELECT P.PK_ORG_PROD_ALLOT, P.ALLOT_PROD, P.ALLOT_ORG, " +
				"P.ALLOT_NUM, P.REMARK, P.TS FROM GD_ORG_PROD_ALLOT P WHERE PK_ORG_PROD_ALLOT IS NOT NULL " + where;
		if(isPage){
			return query.query(querySql, queryCountSql, page);
		}
		VOQuery<OrgProdAllotVO> voQuery = new VOQuery<>(OrgProdAllotVO.class);
		return voQuery.query(querySql);
	}

	private String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		if(!MapUtil.isEmpty(params)){
			String org = params.get("org");
			if(StringUtils.isNotEmpty(org)){
				//org
				List<Object[]> users = UserUtils.getUserList();
				if(CollectionUtils.isNotEmpty(users)){
					StringBuilder usersb = new StringBuilder();
					usersb.append("AND P.ALLOT_ORG IN ");
					usersb.append("(");
					for(Object[] obj : users){
						usersb.append("'");
						usersb.append(obj[1].toString());
						usersb.append("'");
						usersb.append(",");
					}
					usersb.deleteCharAt((usersb.length()-1));
					usersb.append(")");
					sb.append(usersb.toString());
				}
				
			}
			String allot_prod = params.get("allot_prod");
			if(StringUtils.isNotEmpty(allot_prod)){
				sb.append(" AND P.ALLOT_PROD LIKE '%"+allot_prod.trim()+"%'");
			}
			String allot_org = params.get("allot_org");
			if(StringUtils.isNotEmpty(allot_org)){
				sb.append(" AND P.ALLOT_ORG LIKE '%"+allot_org+"%'");
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
		}
		return sb.toString();
	}
	
}
