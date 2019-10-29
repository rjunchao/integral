package com.xbkj.gd.integral.prod.dao;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.foundation.PageCond;
import com.eos.system.logging.Logger;
import com.primeton.cap.AppUserManager;
import com.pub.xbkj.pubapp.pagequery.VOPageQuery;
import com.sun.star.uno.RuntimeException;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.util.MapUtil;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.gd.integral.prod.vos.AuditProductVO;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-17
 *@version 1.0.0
 *@desc
 */
public class AuditProductDao{
	private static final Logger log = LoggerFactory.getLogger(AuditProductDao.class);
	public DBUtils dbUtils = new DBUtils();
	/**
	 * 更新审批信息
	 * @return
	 * @throws DAOException 
	 */
	public int updateProductAuditInfo(SQLParameter parameter) throws DAOException{
		String sql = "UPDATE GD_AUDIT_PRODUCT SET AUDIT_USER=?, AUDIT_DATE=?, AUDIT_STATUS=? WHERE PK_AUDIT_PRODUCT=?";
		try {
			return dbUtils.executeUpdateSQL(sql, parameter);
		} catch (DAOException e) {
			log.error(e);
			throw new DAOException(e);
		}
	}
	
	public String createAudit() throws Exception {
		try {
			AuditProductVO audit = new AuditProductVO();
			String userId = AppUserManager.getCurrentUserId();
			String pk = PrimaryKeyUtil.getPrimaryKey();
			audit.setPk_audit_product(pk);
			audit.setApply_user(userId);//申请人id
			audit.setApply_org(UserUtils.getUserOrg());//申请人机构
			audit.setApply_date(DateUtils.getFormatDate(DateUtils.PATTERN_19));//申请时间
			audit.setRemark("商品申请");
			audit.setDr("0");
//			audit.setAudit_status(IntegralConstant.PROD_APPLY_AUDIT_STATUS_PASS_NULL);
			GdDataHandlerUtils<AuditProductVO> voUtils = new GdDataHandlerUtils<AuditProductVO>(new AuditProductVO());
			voUtils.save(audit);
			return pk;
		} catch (RuntimeException e) {
			log.error(e);
			throw new Exception("商品添加失败" + e.getMessage());
		}
	}
	
	/**
	 * 分页查询审批表
	 * @param params
	 * @param page
	 * @return
	 */
	public AuditProductVO[] queryAuditProductPage(Map<String,String> params,PageCond page){
		//判断是不是有参数
 		String where = whereSql(params);
		//查询数据实体
		VOPageQuery<AuditProductVO> query = new VOPageQuery<AuditProductVO>(AuditProductVO.class);
		//查询的总记录
		String queryCountSql = "SELECT  COUNT(*)" +
				"FROM GD_AUDIT_PRODUCT P WHERE P.DR = 0 " + where;
	    //查询
		String querySql = "SELECT P.PK_AUDIT_PRODUCT, P.APPLY_USER, P.APPLY_DATE, P.APPLY_ORG, " +
				"P.AUDIT_USER, P.AUDIT_DATE, P.AUDIT_STATUS, P.REMARK," +
				"P.DEF1, P.DEF2, P.DEF3, P.DEF4, P.DEF5, P.DEF6, P.TS " +
				",  O.ORGNAME " +
				"FROM GD_AUDIT_PRODUCT P  " +
//				"LEFT JOIN ORG_EMPLOYEE E ON P.AUDIT_USER = E.EMPCODE " +
				"LEFT JOIN ORG_ORGANIZATION O ON P.APPLY_ORG = O.ORGID " +
				" WHERE P.DR = 0 " + where;
		AuditProductVO[] vos = query.query(querySql, queryCountSql, page);
		return vos;
	}
	private String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		
		if(!MapUtil.isEmpty(params)){
			//身份证、名字 {customer_idcard=null, customer_name=阮}
			String pk_audit_product = params.get("pk_audit_product");
			if(StringUtils.isNotEmpty(pk_audit_product)){
				sb.append(" AND P.PK_AUDIT_PRODUCT LIKE '%"+pk_audit_product.trim()+"%'");
			}
			String apply_user = params.get("apply_user");
			if(StringUtils.isNotEmpty(apply_user)){
				sb.append(" AND P.APPLY_USER LIKE '%"+apply_user+"%'");
			}
			String audit_user = params.get("audit_user");
			if(StringUtils.isNotEmpty(audit_user)){
				sb.append(" AND P.AUDIT_USER LIKE '%"+audit_user+"%'");
			}
		}
		return sb.toString();
	}
}
