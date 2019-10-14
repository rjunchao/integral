package com.xbkj.gd.utils;

import java.util.List;

import com.xbkj.common.bs.dao.BaseDAO;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.jdbc.framework.processor.ArrayListProcessor;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2017-9-6
 *@version 1.0.0
 *@desc
 */
public class DBUtils {

	private BaseDAO dao = new BaseDAO();
	
	/**
	 * 执行更新
	 * @param sql
	 * @throws DAOException
	 */
	public int executeUpdateSQL(String sql) throws DAOException{
		return dao.executeUpdate(sql);
	}
	/**
	 * 执行更新
	 * @param sql
	 * @throws DAOException
	 */
	public int executeBatchUpdateSQL(List<String> sqls) throws DAOException{
		return dao.executeBatchSql(sqls);
	}
	/**
	 * 执行更新
	 * @param sql
	 * @throws DAOException
	 */
	public int executeUpdateSQL(String sql, SQLParameter parameter) throws DAOException{
		int count = dao.executeUpdate(sql, parameter);
		return count;
	}
	
	/**
	 * 统计条数
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public int getCountNumber(String sql) throws DAOException{
		String count = getOneValue(sql);
		try {
			return Integer.parseInt(count);
		} catch (Exception e) {
			return 0;
		}
	}
	/**
	 * 统计条数
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public int getCountNumber(String sql, SQLParameter param) throws DAOException{
		String count = getOneValue(sql, param);
		try {
			return Integer.parseInt(count);
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 查询一个值返回
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public String getOneValue(String sql) throws DAOException{
		List<Object[]> objs = queryObjs(sql);
		if(objs != null && objs.size() > 0){
			return objs.get(0)[0] == null ? "" : objs.get(0)[0].toString();
		}
		return "";
		
	}
	/**
	 * 查询一个值返回
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public String getOneValue(String sql, SQLParameter parameter) throws DAOException{
		List<Object[]> objs = queryObjs(sql);
		if(objs != null && objs.size() > 0){
			return objs.get(0)[0].toString() == null ? "" : objs.get(0)[0].toString();
		}
		return "";
		
	}
	/**
	 * 查询返回一个集合数组
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<Object[]> queryObjs(String sql) throws DAOException{
		return (List<Object[]>) dao.executeQuery(sql, new ArrayListProcessor());
	}
	/**
	 * 查询返回一个集合数组
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<Object[]> queryObjs(String sql, SQLParameter parameter) throws DAOException{
		return (List<Object[]>) dao.executeQuery(sql, parameter, new ArrayListProcessor());
	}
}
