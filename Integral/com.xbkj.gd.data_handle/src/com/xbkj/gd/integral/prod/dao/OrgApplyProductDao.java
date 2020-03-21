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
import com.xbkj.gd.integral.prod.vos.OrgApplyProductVO;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-23
 *@version 1.0.0
 *@desc
 */
public class OrgApplyProductDao {

	private static final Logger log = LoggerFactory.getLogger(OrgApplyProductDao.class);
	private DBUtils dbUtils = new DBUtils();
	private GdDataHandlerUtils<OrgApplyProductVO> voUtils = new GdDataHandlerUtils<OrgApplyProductVO>(new OrgApplyProductVO());
	
	/**
	 * 查询当前用户下审批通过的数量
	 * @param user
	 * @return
	 */
	public  List<OrgApplyProductVO> queryProdByUser(String user){
		String sql = "SELECT P.PK_ORG_APPLY_PRODUCT, P.PK_ORG_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, P.APPLY_PRODUCT_NAME, " +
				"P.APPLY_PRODUCT_INTEGRAL, P.APPLY_PRODUCT_NUM, P.ORG_SUB_NUM, P.AUDIT_PRODUCT_NUM, P.AUDIT_STATUS, " +
				"P.AUDIT_USER, P.AUDIT_DATE, P.REMARK, P.DEF1, P.DEF2, P.DEF3, P.DEF4, P.DEF5, P.DEF6" +
				" FROM GD_ORG_APPLY_PRODUCT P WHERE P.AUDIT_STATUS IN ('7','5') AND ORG_SUB_NUM < P.APPLY_PRODUCT_NUM AND P.APPLY_USER = ? ";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(user);
		List<OrgApplyProductVO> vos = voUtils.queryList(sql, parameter);
		return vos;
	}
	
	/**
	 * 更新商品数量
	 * @param code
	 * @param user
	 * @return
	 * @throws DAOException
	 */
	public int auditOrgApply(OrgApplyProductVO vo) throws DAOException{
		/*
		 * 更新，审批状态、审批人、审批时间、审批备注
		 */
		String sql = "UPDATE GD_ORG_APPLY_PRODUCT SET " +
				"AUDIT_STATUS=?, " +
				"AUDIT_USER=?, " +
				"AUDIT_DATE=?, " +
				"REMARK=?, " +
				"MODIFIER=?, " +
				"MODIFIEDTIME=? " +
//				"APPLY_PRODUCT_NUM= ? " + //不需要更新
				"WHERE PK_ORG_APPLY_PRODUCT=?";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(vo.getAudit_status());
		parameter.addParam(UserUtils.getUser());
		parameter.addParam(DateUtils.getFormatDate(DateUtils.PATTERN_19));
		parameter.addParam(vo.getRemark());
		parameter.addParam(vo.getModifier());
		parameter.addParam(vo.getModifiedtime());
//		parameter.addParam(vo.getApply_product_num());
		parameter.addParam(vo.getPk_org_apply_product());
		return dbUtils.executeUpdateSQL(sql, parameter);
	}
	/**
	 * 调拨后减少可兑换数量
	 * @param code
	 * @param user
	 * @return
	 * @throws DAOException
	 */
	public int allotOrgApply(OrgApplyProductVO vo) throws DAOException{
		/*
		 * 更新，审批状态、审批人、审批时间、审批备注、数量
		 */
		/*String sql = "UPDATE GD_ORG_APPLY_PRODUCT SET " +
				"modifier=?, " +
				"modifiedtime=?, " +
				"REMARK=?, " +
				"ORG_SUB_NUM= ? " +
				" WHERE PK_ORG_APPLY_PRODUCT=? ";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(UserUtils.getUser());
		parameter.addParam(DateUtils.getFormatDate(DateUtils.PATTERN_19));
		parameter.addParam(vo.getRemark());
		parameter.addParam(vo.getOrg_sub_num());
		parameter.addParam(vo.getPk_org_apply_product());
		return dbUtils.executeUpdateSQL(sql, parameter);*/
		
		String sql = "UPDATE GD_ORG_APPLY_PRODUCT SET " +
				"modifier='"+UserUtils.getUser()+"', " +
				"modifiedtime='"+DateUtils.getFormatDate(DateUtils.PATTERN_19)+"', " +
				"REMARK='"+vo.getRemark()+"', " +
				"allot_product_num= '"+vo.getAllot_product_num()+"'" +//调换数
//				"ORG_SUB_NUM= '"+vo.getOrg_sub_num()+"'" +
//				", apply_product_num='" + vo.getApply_product_num() + "' " +
				" WHERE PK_ORG_APPLY_PRODUCT='"+vo.getPk_org_apply_product()+"' ";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(UserUtils.getUser());
		parameter.addParam(DateUtils.getFormatDate(DateUtils.PATTERN_19));
		parameter.addParam(vo.getRemark());
		parameter.addParam(vo.getOrg_sub_num());
		parameter.addParam(vo.getPk_org_apply_product());
		return dbUtils.executeUpdateSQL(sql);
//		return dbUtils.executeUpdateSQL(sql, parameter);
		
	}
	
	/**
	 * 更新礼品数量
	 * @return
	 * @throws DAOException 
	 */
	public int updategiftNum(SQLParameter parameter) throws DAOException{
		String sql = "UPDATE GD_ORG_APPLY_PRODUCT SET ORG_SUB_NUM = ORG_SUB_NUM + ? WHERE PK_ORG_APPLY_PRODUCT=?";
		try {
			return dbUtils.executeUpdateSQL(sql, parameter);
		} catch (DAOException e) {
			log.error(e);
			throw new DAOException(e);
		}
	}
	/**
	 * 更新礼品数量
	 * @return
	 * @throws DAOException 
	 */
	public int updategiftNums(Map<String, Integer> gifts) throws DAOException{
		if(!gifts.isEmpty()){
			SQLParameter parameter = null;
			for(Map.Entry<String, Integer> map : gifts.entrySet()){
				parameter = new SQLParameter();
				parameter.addParam(map.getValue());//数量
				parameter.addParam(map.getKey());//主键
				updategiftNum(parameter);
			}
			return 1;
		}
		return -1;
	}
	
	/**
	 * 根据礼品和分理处兑换商品
	 * 数量大于0的
	 * @param code
	 * @param user
	 * @return
	 * @throws DAOException
	 */
	public OrgApplyProductVO querySubBranchProd(String pk) throws DAOException{
		String sql = "SELECT P.PK_ORG_APPLY_PRODUCT, P.PK_ORG_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, P.APPLY_PRODUCT_NAME, " +
				"P.APPLY_PRODUCT_INTEGRAL, P.APPLY_PRODUCT_NUM, P.AUDIT_PRODUCT_NUM, P.AUDIT_STATUS, " +
				"P.AUDIT_USER, P.AUDIT_DATE, P.REMARK, P.ORG_SUB_NUM, P.DEF1, P.DEF2, P.DEF3, P.DEF4, P.DEF5, P.DEF6" +
				" FROM GD_ORG_APPLY_PRODUCT P WHERE P.AUDIT_STATUS IN ('7','5') AND P.ORG_SUB_NUM < P.APPLY_PRODUCT_NUM AND P.PK_ORG_APPLY_PRODUCT= ? ";
		SQLParameter parameter = new SQLParameter();
		parameter.addParam(pk);
		return voUtils.query(sql, parameter);
		
	}
	/**
	 * 兑换成功后更是数量
	 * @param params
	 * @return
	 * @throws DAOException 
	 */
	/*public void subOrgProd(Map<String, String> params) throws DAOException{
		if(params.isEmpty()){
			throw new DAOException("数据为空");
		}
		String sql = null;
		for(Map.Entry<String, String> map : params.entrySet()){
			sql = "UPDATE GD_ORG_APPLY_PRODUCT SET AUDIT_PRODUCT_NUM=AUDIT_PRODUCT_NUM - " + map.getValue() + " WHERE PK_ORG_APPLY_PRODUCT='"+map.getKey()+"'";
			dbUtils.executeUpdateSQL(sql);
		}
	}*/
	
	/**
	 * 根据编码查询数量
	 * @param params
	 * @return
	 */
	/*public  OrgApplyProductVO findOrgApplyProdByCode(String code){
		String querySql = "SELECT P.PK_ORG_APPLY_PRODUCT, P.PK_ORG_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, P.APPLY_PRODUCT_NAME, " +
				"P.APPLY_PRODUCT_INTEGRAL, P.APPLY_PRODUCT_NUM, P.AUDIT_PRODUCT_NUM, P.AUDIT_STATUS, " +
				"P.AUDIT_USER, P.AUDIT_DATE, P.REMARK, P.DEF1, P.DEF2, P.DEF3, P.DEF4, P.DEF5, P.DEF6" +
				" FROM GD_ORG_APPLY_PRODUCT P WHERE P.APPLY_PRODUCT_CODE='"+code+"'";
		 OrgApplyProductVO vo = voUtils.query(querySql);
		return vo;
	}*/
	/**
	 * 根据编码查询数量
	 * @param params
	 * @return
	 */
	public  OrgApplyProductVO get(String pk){
		String querySql = "SELECT P.PK_ORG_APPLY_PRODUCT, P.PK_ORG_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, P.APPLY_PRODUCT_NAME, " +
				"P.APPLY_PRODUCT_INTEGRAL, P.APPLY_PRODUCT_NUM, P.AUDIT_PRODUCT_NUM, P.ORG_SUB_NUM,P.AUDIT_STATUS, " +
				"P.AUDIT_USER, P.AUDIT_DATE, P.REMARK, P.DEF1, P.DEF2, P.DEF3, P.DEF4, P.DEF5, P.DEF6" +
				" FROM GD_ORG_APPLY_PRODUCT P WHERE P.PK_ORG_APPLY_PRODUCT='"+pk+"'";
		OrgApplyProductVO vo = voUtils.query(querySql);
		return vo;
	}
	/**
	 * 查询审批通过的记录
	 * @param params
	 * @return
	 */
	public MsgResponse saveArr( OrgApplyProductVO[] vos){
		/*for( OrgApplyProductVO vo: vos){
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
	public List< OrgApplyProductVO> queryOrgApplyDetail(Map<String, String> params){
//		String auditProduct = params.get("PK_ORG_AUDIT_PRODUCT");
		String sql = "SELECT P.PK_ORG_APPLY_PRODUCT,P.PK_ORG_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, " +
				"P.APPLY_PRODUCT_NAME, P.APPLY_PRODUCT_INTEGRAL, " +
				"P.APPLY_PRODUCT_NUM " +
				"FROM GD_ORG_APPLY_PRODUCT P WHERE P.AUDIT_STATUS IN('7', '5') AND P.ORG_SUB_NUM < P.APPLY_PRODUCT_NUM AND P.APPLY_USER='"+UserUtils.getUser()+"'";
		/*List<String> orgs = UserUtils.findDeptAndChildrenDept();
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
		}*/
		return voUtils.queryList(sql);
	}
	/**
	 * 根据审批id查询
	 * @param params
	 * @return
	 */
	public List< OrgApplyProductVO> queryOrgApplyByPkAudit(Map<String, String> params){
		String auditProduct = params.get("PK_ORG_AUDIT_PRODUCT");
		String sql = "SELECT P.PK_ORG_APPLY_PRODUCT,P.PK_ORG_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, " +
				"P.APPLY_PRODUCT_NAME, P.APPLY_PRODUCT_INTEGRAL, " +
				"P.APPLY_PRODUCT_NUM,P.AUDIT_STATUS,P.REMARK " +
				"FROM GD_ORG_APPLY_PRODUCT P WHERE  P.PK_ORG_AUDIT_PRODUCT= ?";
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
	public List<OrgApplyProductVO> queryOrgApply(Map<String, String> params){
		
		/*String where = listWhere(params);
		String sql = "SELECT P.PK_ORG_AUDIT_PRODUCT, P.PK_ORG_APPLY_PRODUCT, P.APPLY_PRODUCT_CODE, " +
				"P.APPLY_PRODUCT_NAME, P.APPLY_PRODUCT_INTEGRAL, " +
				"P.APPLY_PRODUCT_NUM, P.AUDIT_STATUS, P.REMARK " +
				"FROM GD_ORG_APPLY_PRODUCT P WHERE P.DR='0' " + where;
		GdDataHandlerUtils< OrgApplyProductVO> query = new GdDataHandlerUtils< OrgApplyProductVO>(new  OrgApplyProductVO());
		List< OrgApplyProductVO> vos = query.queryList(sql);
		if(vos != null && vos.size() > 0){
			for ( OrgApplyProductVO vo: vos) {
				vo.setAudit_user("");
				vo.setAudit_product_num(vo.getApply_product_num());//默认审批通过就是申请数量
			}
		}
		return vos;*/
		
		return null;
	}
	
	
	
	/*private String listWhere(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		String auditProduct = params.get("PK_ORG_AUDIT_PRODUCT");
		if(StringUtil.isNotEmpty(auditProduct)){
			sb.append(" AND P.PK_ORG_AUDIT_PRODUCT='"+auditProduct+"'");
		}
		String status = params.get("audit_status");
		if(StringUtil.isNotEmpty(status)){
			sb.append(" AND P.AUDIT_STATUS='"+status+"'");
		}
		String ids = params.get("ids");
		if(StringUtil.isNotEmpty(ids)){
			String[] idss = ids.split(",");
			if(idss != null && idss.length > 0){
				sb.append(" AND P.PK_ORG_AUDIT_PRODUCT IN (");
				for(String id : idss){
					sb.append("'"+id+"',");
				}
				sb.deleteCharAt(sb.length()-1);//
				sb.append(" )");
			}
		}
		
		return sb.toString();
	}*/
	/**
	 * 分页查询商品申请数据
	 * @param params
	 * @param page
	 * @return
	 */
	public  OrgApplyProductVO[] queryOrgApplyProductPage(Map<String,String> params,PageCond page){
		//判断是不是有参数
 		String where = whereSql(params);
		//查询数据实体
		VOPageQuery< OrgApplyProductVO> query = new VOPageQuery< OrgApplyProductVO>( OrgApplyProductVO.class);
		//查询的总记录
		String queryCountSql = "SELECT  COUNT(*)" +
				" FROM GD_ORG_APPLY_PRODUCT P WHERE P.DR = 0 " + where;
	    //查询
		String querySql = "SELECT P.PK_ORG_APPLY_PRODUCT, P.PK_ORG_AUDIT_PRODUCT, P.APPLY_PRODUCT_CODE, P.APPLY_PRODUCT_NAME, " +
				"P.APPLY_PRODUCT_INTEGRAL, P.APPLY_PRODUCT_NUM,P.ORG_SUB_NUM, P.AUDIT_PRODUCT_NUM, P.AUDIT_STATUS, " +
				"P.AUDIT_USER, P.AUDIT_DATE, P.REMARK,E.EMPNAME, P.DEF1, P.DEF2, P.DEF3, P.DEF4, P.DEF5, P.DEF6," +
				" P.TS FROM GD_ORG_APPLY_PRODUCT P " +
				"LEFT JOIN ORG_EMPLOYEE E ON P.APPLY_USER = E.EMPCODE WHERE P.DR = 0 " + where;
		log.info("query sql =================" + querySql);
		 OrgApplyProductVO[] vos = query.query(querySql, queryCountSql, page);
		return vos;
	}
	private String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		
		if(!MapUtil.isEmpty(params)){
			//身份证、名字 {customer_idcard=null, customer_name=阮}
			String apply_product_code = params.get("apply_product_code");
			if(StringUtils.isNotEmpty(apply_product_code)){
				sb.append(" AND P.APPLY_PRODUCT_CODE LIKE '%"+apply_product_code.trim()+"%'");
			}
			String apply_product_name = params.get("apply_product_name");
			if(StringUtils.isNotEmpty(apply_product_name)){
				sb.append(" AND P.APPLY_PRODUCT_NAME LIKE '%"+apply_product_name+"%'");
			}
			String pk_org_audit_product = params.get("pk_org_audit_product");
			if(StringUtils.isNotEmpty(pk_org_audit_product)){
				sb.append(" AND P.PK_ORG_AUDIT_PRODUCT LIKE '%"+pk_org_audit_product+"%'");
			}
			String audit_status = params.get("audit_status");
			if(StringUtils.isNotEmpty(audit_status)){
				sb.append(" AND P.AUDIT_STATUS = '"+audit_status+"'");
			}
			String apply_user = params.get("apply_user");
			if(StringUtils.isNotEmpty(apply_user)){
				sb.append(" AND P.APPLY_USER = '"+UserUtils.getUser()+"'");
			}
			String apply_usera = params.get("apply_usera");
			if(StringUtils.isNotEmpty(apply_usera)){
				sb.append(" AND P.APPLY_USER = '"+apply_usera+"'");
			}
			String apply_org = params.get("apply_org");
			if(StringUtils.isNotEmpty(apply_org)){
				sb.append(" AND P.APPLY_ORG = '"+apply_org+"'");
			}
			String end_date = params.get("end_date");
			if(StringUtils.isNotEmpty(end_date)){
				sb.append(" AND P.ts <= '"+end_date+"'");
			}
			String start_date = params.get("start_date");
			if(StringUtils.isNotEmpty(start_date)){
				sb.append(" AND P.ts >= '"+start_date+"'");
			}
		}
		//查询
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
	public int updateOrgAuditStatus(SQLParameter parameter) throws DAOException{
		String sql = "UPDATE  GD_ORG_APPLY_PRODUCT SET " +
				"	AUDIT_PRODUCT_NUM=?, " +
				"	AUDIT_STATUS=?, " +
				"	REMARK=?,AUDIT_USER=?,AUDIT_DATE=? " +
				"WHERE " +
				"	PK_ORG_APPLY_PRODUCT=?";
		try {
			
			return dbUtils.executeUpdateSQL(sql, parameter);
		} catch (DAOException e) {
			log.error(e);
			throw new DAOException(e);
		}
	}
}
