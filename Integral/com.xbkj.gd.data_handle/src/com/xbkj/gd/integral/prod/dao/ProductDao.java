package com.xbkj.gd.integral.prod.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.foundation.PageCond;
import com.eos.system.logging.Logger;
import com.pub.xbkj.pubapp.pagequery.VOPageQuery;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.util.MapUtil;
import com.xbkj.gd.integral.prod.vos.ProductVO;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-17
 *@version 1.0.0
 *@desc
 */
public class ProductDao {
	private static final Logger log = LoggerFactory.getLogger(ProductDao.class);
	private VOPageQuery<ProductVO> pageQuery = new VOPageQuery<ProductVO>(ProductVO.class);
	private GdDataHandlerUtils<ProductVO> utils = new GdDataHandlerUtils<ProductVO>(new ProductVO());
	private DBUtils dbUtils = new DBUtils();
	/**
	 * 更新商品信息
	 * @return
	 * @throws DAOException 
	 */
	public int updateProduct(SQLParameter parameter) throws DAOException{
		String sql = "UPDATE GD_PRODUCT T " +
				"	SET T.PRODUCT_CODE = ?, T.PRODUCT_NAME=?, T.PRODUCT_NUM=? " +
				"	WHERE T.PK_PRODUCT = ? ";try {
			return dbUtils.executeUpdateSQL(sql, parameter);
		} catch (DAOException e) {
			log.error(e);
			throw new DAOException(e);
		}
	}
	/**
	 * 更新商品数量
	 * @return
	 * @throws DAOException 
	 */
	public int updateProductNum(SQLParameter parameter) throws DAOException{
		String sql = "UPDATE GD_PRODUCT SET PRODUCT_NUM=? WHERE PK_PRODUCT=?";
		try {
			return dbUtils.executeUpdateSQL(sql, parameter);
		} catch (DAOException e) {
			log.error(e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * 查询可以申请的产品，
	 * @return
	 */
	public List<ProductVO> queryApplyProd(){
		String querySql = "	SELECT P.PRODUCT_CODE, P.PRODUCT_NAME,P.PRODUCT_INTEGRAL FROM GD_PRODUCT P " +
				"	WHERE P.DR = 0 AND P.PRODUCT_NUM > 0" ;
		return utils.queryList(querySql);
	}
	/**
	 * 根据产品编码查询
	 * @param productCode
	 * @return
	 */
	public ProductVO queryProductByCode(String productCode){
		 //查询
		String querySql = "	SELECT P.PK_PRODUCT, P.PRODUCT_CODE, P.PRODUCT_NAME,P.PRODUCT_INTEGRAL, P.PRODUCT_STATUS, " +
				"P.START_TIME,P.PRODUCT_NUM, P.REMARK, P.MODIFIEDTIME, P.MODIFIER,P.CREATETIME, " +
				"P.CREATE_USER, P.CREATE_USER_ORG, P.DEF1, P.DEF2, P.DEF3, P.DEF4, P.DEF5, P.DEF6, " +
				"P.DEF7, P.DEF8, P.DEF9, P.DEF10 FROM GD_PRODUCT P " +
							"	WHERE P.PRODUCT_CODE = '"+productCode+"' LIMIT 0, 1";
		return utils.query(querySql);
	}
	
	/**
	 * 根据产品编码查询
	 * @param productCode
	 * @return
	 */
	public ProductVO[] queryProductPage(Map<String,String> params,PageCond page){
		//判断是不是有参数
 		String where = whereSql(params);
		//查询数据实体
		
		//查询的总记录
		String queryCountSql = "	SELECT COUNT(*) FROM GD_PRODUCT P " +
								"	WHERE P.DR = 0 " + where;
	    //查询
		String querySql = "	SELECT P.PK_PRODUCT, P.PRODUCT_CODE, P.PRODUCT_NAME,P.PRODUCT_INTEGRAL, P.PRODUCT_STATUS, " +
				"P.START_TIME,P.PRODUCT_NUM, P.REMARK, P.MODIFIEDTIME, P.MODIFIER,P.CREATETIME, " +
				"P.CREATE_USER, P.CREATE_USER_ORG,P.DEF1, P.DEF2, P.DEF3, P.DEF4, P.DEF5, P.DEF6, " +
				"P.DEF7, P.DEF8, P.DEF9, P.DEF10,E.EMPNAME FROM GD_PRODUCT P " +
							"	LEFT JOIN ORG_EMPLOYEE E ON P.CREATE_USER = E.EMPCODE" +
							"	WHERE P.DR = 0 " + where;
		ProductVO[] vos = pageQuery.query(querySql, queryCountSql, page);
		return vos;
	}
	
	private String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		
		if(!MapUtil.isEmpty(params)){
			//身份证、名字 {customer_idcard=null, customer_name=阮}
			String product_code = params.get("product_code");
			if(StringUtils.isNotEmpty(product_code)){
				sb.append(" AND P.PRODUCT_CODE LIKE '%"+product_code.trim()+"%'");
			}
			
			String product_name = params.get("product_name");
			if(StringUtils.isNotEmpty(product_name)){
				sb.append(" AND P.PRODUCT_NAME LIKE '%"+product_name+"%'");
			}
			String product_num = params.get("product_num");
			if(StringUtils.isNotEmpty(product_num)){
				//查询库存大于0的记录
				sb.append(" AND P.PRODUCT_NUM > 0 ");
			}
		}
		return sb.toString();
	}
	
}
