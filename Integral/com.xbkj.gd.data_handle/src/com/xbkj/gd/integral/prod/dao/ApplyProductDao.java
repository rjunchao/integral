package com.xbkj.gd.integral.prod.dao;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.tools.LoggerFactory;
import com.eos.foundation.PageCond;
import com.eos.system.logging.Logger;
import com.pub.xbkj.common.MsgResponse;
import com.pub.xbkj.pubapp.pagequery.VOPageQuery;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.util.MapUtil;
import com.xbkj.gd.integral.prod.vos.ApplyProductVO;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-17
 *@version 1.0.0
 *@desc
 */
public class ApplyProductDao {
	private static final Logger log = LoggerFactory.getLogger(ApplyProductDao.class);
	private DBUtils dbUtils = new DBUtils();
	private GdDataHandlerUtils<ApplyProductVO> voUtils = new GdDataHandlerUtils<ApplyProductVO>(new ApplyProductVO());
	
	/**
	 * 兑换成功后更是数量
	 * @param params
	 * @return
	 * @throws DAOException 
	 */
	public void subProd(Map<String, String> params) throws DAOException{
		if(params.isEmpty()){
			throw new DAOException("数据为空");
		}
		String sql = null;
		for(Map.Entry<String, String> map : params.entrySet()){
			sql = "UPDATE GD_APPLY_PRODUCT SET AUDIT_PRODUCT_NUM=AUDIT_PRODUCT_NUM - " + map.getValue() + " WHERE PK_APPLY_PRODUCT='"+map.getKey()+"'";
			dbUtils.executeUpdateSQL(sql);
		}
	}
	
	/**
	 * 根据编码查询数量
	 * @param params
	 * @return
	 */
	public ApplyProductVO findApplyProdByCode(String code){
		String querySql = "SELECT P.PK_APPLY_PRODUCT, P.PK_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, P.APPLY_PRODUCT_NAME, " +
				"P.APPLY_PRODUCT_INTEGRAL, P.APPLY_PRODUCT_NUM, P.AUDIT_PRODUCT_NUM, P.AUDIT_STATUS, " +
				"P.AUDIT_USER, P.AUDIT_DATE, P.REMARK, P.DEF1, P.DEF2, P.DEF3, P.DEF4, P.DEF5, P.DEF6" +
				" FROM GD_APPLY_PRODUCT P WHERE P.APPLY_PRODUCT_CODE='"+code+"'";
		ApplyProductVO vo = voUtils.query(querySql);
		return vo;
	}
	/**
	 * 查询审批通过的记录
	 * @param params
	 * @return
	 */
	public MsgResponse saveArr(ApplyProductVO[] vos){
		/*for(ApplyProductVO vo: vos){
			voUtils.save(vo);
		}
		return new MsgResponse(true);
		*/
		return voUtils.saveArr(vos);
	}
	/**
	 * 查询审批通过的记录
	 * 	要不要缓存呢？
	 * @param params
	 * @return
	 */
	public List<ApplyProductVO> queryApplyDetail(Map<String, String> params){
//		String auditProduct = params.get("pk_audit_product");
		String sql = "SELECT P.PK_APPLY_PRODUCT,P.PK_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, " +
				"P.APPLY_PRODUCT_NAME, P.APPLY_PRODUCT_INTEGRAL, " +
				"P.APPLY_PRODUCT_NUM " +
				"FROM GD_APPLY_PRODUCT P WHERE P.AUDIT_STATUS= '2'";
		List<String> orgs = UserUtils.findDeptAndChildrenDept();
		if(orgs != null && orgs.size() > 0){
			StringBuilder sb = new StringBuilder();
			sb.append(sql);
			sb.append(" AND P.APPLY_ORG IN (");
			for(String org : orgs){
				sb.append(org + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(" )");
			return voUtils.queryList(sb.toString());
		}
		return voUtils.queryList(sql);
	}
	/**
	 * 根据审批id查询
	 * @param params
	 * @return
	 */
	public List<ApplyProductVO> queryApplyByPkAudit(Map<String, String> params){
		String auditProduct = params.get("pk_audit_product");
		String sql = "SELECT P.PK_APPLY_PRODUCT,P.PK_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, " +
				"P.APPLY_PRODUCT_NAME, P.APPLY_PRODUCT_INTEGRAL, " +
				"P.APPLY_PRODUCT_NUM,P.AUDIT_STATUS,P.REMARK " +
				"FROM GD_APPLY_PRODUCT P WHERE  P.PK_AUDIT_PRODUCT= ?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(auditProduct);
		return voUtils.queryList(sql, parameter);
	}
	
	/**
	 * 根据审批主键查询申请商品明细
	 * 审批的时候调用
	 * @param params
	 * @return
	 */
	public List<ApplyProductVO> queryApplyDetailCombobox(Map<String, String> params){
		String auditProduct = params.get("pk_audit_product");
		String sql = "SELECT P.PK_AUDIT_PRODUCT, P.PK_APPLY_PRODUCT, P.APPLY_PRODUCT_CODE, " +
				"P.APPLY_PRODUCT_NAME, P.APPLY_PRODUCT_INTEGRAL, " +
				"P.APPLY_PRODUCT_NUM, P.AUDIT_STATUS, P.REMARK " +
				"FROM GD_APPLY_PRODUCT P WHERE P.AUDIT_STATUS = '1' AND P.PK_AUDIT_PRODUCT= ? ";
		GdDataHandlerUtils<ApplyProductVO> query = new GdDataHandlerUtils<ApplyProductVO>(new ApplyProductVO());
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(auditProduct);
		List<ApplyProductVO> vos = query.queryList(sql, parameter);
		if(vos != null && vos.size() > 0){
			for (ApplyProductVO vo: vos) {
				vo.setAudit_user("");
				vo.setAudit_product_num(vo.getApply_product_num());//默认审批通过就是申请数量
			}
		}
		return vos;
	}
	
	/**
	 * 分页查询商品申请数据
	 * @param params
	 * @param page
	 * @return
	 */
	public ApplyProductVO[] queryApplyProductPage(Map<String,String> params,PageCond page){
		//判断是不是有参数
 		String where = whereSql(params);
		//查询数据实体
		VOPageQuery<ApplyProductVO> query = new VOPageQuery<ApplyProductVO>(ApplyProductVO.class);
		//查询的总记录
		String queryCountSql = "SELECT  COUNT(*)" +
				" FROM GD_APPLY_PRODUCT P WHERE P.DR = 0 " + where;
	    //查询
		String querySql = "SELECT P.PK_APPLY_PRODUCT, P.PK_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, P.APPLY_PRODUCT_NAME, " +
				"P.APPLY_PRODUCT_INTEGRAL, P.APPLY_PRODUCT_NUM, P.AUDIT_PRODUCT_NUM, P.AUDIT_STATUS, " +
				"P.AUDIT_USER, P.AUDIT_DATE, P.REMARK,E.EMPNAME, P.DEF1, P.DEF2, P.DEF3, P.DEF4, P.DEF5, P.DEF6" +
				" FROM GD_APPLY_PRODUCT P " +
				"LEFT JOIN ORG_EMPLOYEE E ON P.AUDIT_USER = E.EMPCODE WHERE P.DR = 0 " + where;
		log.info("query sql =================" + querySql);
		ApplyProductVO[] vos = query.query(querySql, queryCountSql, page);
		return vos;
	}
	private String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		
		if(!MapUtil.isEmpty(params)){
			//身份证、名字 {customer_idcard=null, customer_name=阮}
			String apply_product_code = params.get("apply_product_code");
			if(StringUtils.isNotEmpty(apply_product_code)){
				sb.append(" AND P.PRODUCT_CODE LIKE '%"+apply_product_code.trim()+"%'");
			}
			String apply_product_name = params.get("apply_product_name");
			if(StringUtils.isNotEmpty(apply_product_name)){
				sb.append(" AND P.PRODUCT_NAME LIKE '%"+apply_product_name+"%'");
			}
			String pk_audit_product = params.get("pk_audit_product");
			if(StringUtils.isNotEmpty(pk_audit_product)){
				sb.append(" AND P.PK_AUDIT_PRODUCT LIKE '%"+pk_audit_product+"%'");
			}
		}
		//添加机构权限
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
	
	/**
	 * 更新审批信息
	 * @param parameter
	 * @return
	 * @throws DAOException
	 */
	public int updateAuditStatus(SQLParameter parameter) throws DAOException{
		String sql = "UPDATE  GD_APPLY_PRODUCT SET " +
				"	AUDIT_PRODUCT_NUM=?, " +
				"	AUDIT_STATUS=?, " +
				"	REMARK=?,AUDIT_USER=?,AUDIT_DATE=? " +
				"WHERE " +
				"	PK_APPLY_PRODUCT=?";
		try {
			return dbUtils.executeUpdateSQL(sql, parameter);
		} catch (DAOException e) {
			log.error(e);
			throw new DAOException(e);
		}
	}
}
