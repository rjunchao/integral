

package com.vbm.grc.system.impl.pub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;
import com.pub.xbkj.pubapp.query.VOQuery;
import com.vbm.grc.system.itf.pub.IRefRelationPub;
import com.vbm.grc.system.vo.RefRelationVO;
import com.xbkj.basic.vo.pub.BusinessException;
import com.xbkj.common.bs.dao.BaseDAO;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.processor.ArrayListProcessor;
import com.xbkj.common.util.ArrayUtil;
import com.xbkj.common.util.StringUtil;
/**
 * @author lijbe
 * 删除时判断删除的记录是否被引用了
 */
public class RefRelationPubImpl implements IRefRelationPub{
	//数据库操作的日志
    private static Logger logger = TraceLoggerFactory.getLogger(RefRelationPubImpl.class);
	
	private HashMap<String, List<RefRelationVO>> tableName_RefRelation_Map = null;

	private BaseDAO baseDAO = null;
	
	public boolean isReferenced(String tableName, String keyField,
			String keyValue) throws BusinessException {
		if(StringUtil.isEmpty(keyValue)){
			return false;
		}
		return this.doJudgment(tableName, keyField, new String[]{keyValue});
		
	}
	
	/**
	 * 执行判断
	 * 
	 * @param tableName
	 * @param keyField
	 * @param keys
	 * @return
	 * @throws BusinessException
	 */
	private boolean doJudgment(String tableName, String keyField,String[] keys)
			throws BusinessException {
		if (keys == null || keys.length == 0)
			return false;
		boolean refFlag = false;
		List<RefRelationVO> refList = getRefList(tableName,keyField);
		
		if (refList == null || refList.isEmpty())
			return false;

		int  size = refList.size();
		for (int i = 0; i < size; i++) {
			
			String	sqlQryWithDr = getInSqlQueryByRefRelationList(keys, refList.get(i), true);
			
			refFlag = executejudgeSQL(sqlQryWithDr) ;
			if (refFlag) {
				logger.error("sqlQryWithDr->" + sqlQryWithDr);
				break;
			}
		}
		return refFlag;
	}

	/**
	 * 查询
	 * @param tableName
	 * @param keyField
	 * @return
	 */
	private List<RefRelationVO> getRefList(String tableName,String keyField){
		
		List<RefRelationVO> resultList = getTableName_RefRelation_Map().get(tableName+keyField);
		if(resultList == null){
			String condSql = RefRelationVO.REFED_TABLE_NAME + "='"+tableName+"' and "+ RefRelationVO.REFED_TABLE_KEY +"= '"+keyField+"'";
			String [] fields  = new String[]{RefRelationVO.PK_REF,RefRelationVO.REFED_TABLE_KEY,RefRelationVO.REFED_TABLE_NAME,
					RefRelationVO.REFING_TABLE_COLUM,RefRelationVO.REFING_TABLE_NAME};
			
			VOQuery<RefRelationVO> query = new VOQuery<RefRelationVO>(RefRelationVO.class);
			RefRelationVO [] vos = query.query(fields, condSql, null);
			if(ArrayUtil.isNotEmpty(vos)){
				this.getTableName_RefRelation_Map().put(tableName+keyField, Arrays.asList(vos));
			}
		}
		return resultList;
	
	}
	
	
	
	private HashMap<String, List<RefRelationVO>> getTableName_RefRelation_Map() {
		if (tableName_RefRelation_Map == null) {
			tableName_RefRelation_Map = new HashMap<String, List<RefRelationVO>>();
		}
		return tableName_RefRelation_Map;
	}
	
	
	
	private String getInSqlQueryByRefRelationList(String[] keys, RefRelationVO refVO,boolean isWithDr) {
		
		//取得引用的表名和字段名
		String refingtablename = refVO.getRefing_table_name();
		String refingcloumnname = refVO.getRefing_table_colum();
		StringBuilder buf = new StringBuilder();
		buf.append("select count(*) ");
		buf.append(" from ");
		buf.append(refingtablename);
		buf.append(" where 1=1");
		
		
		String inSql = "";
		int length =  keys.length;
		for(int i = 0; i < length ; i++){
			if(i == length-1){
				inSql += "'"+keys[i]+"'";
			}else{
				inSql += "'"+keys[i]+"',";
			}
		}
		if(inSql.length() > 0 ){
			buf.append(" and ");
			buf.append(refingcloumnname );
			buf.append(" in (" );
			buf.append(inSql);
			buf.append(" )" );
		}

		if (isWithDr) {
			buf.append(" dr = 0");
		}
		String sql = buf.toString();
		return sql;
	}
	
	// 执行校验
	@SuppressWarnings("rawtypes")
	private boolean executejudgeSQL(String sql) throws BusinessException {
		
		try {

			ArrayList result = (ArrayList) getBaseDAO().executeQuery(sql,new ArrayListProcessor());
			if(result != null && result.size() > 0){
				return true;
			}
		} catch (DAOException e) {
			logger.error(e);
		}
		return false;
		
	}
	
	
	private BaseDAO getBaseDAO() {
		if (baseDAO == null) {
			baseDAO = new BaseDAO();
		}
		return baseDAO;
	}
}