package com.pub.xbkj.common;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.system.logging.Logger;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.bs.dao.BaseDAO;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.mapping.MappingMeta;
import com.xbkj.common.jdbc.framework.processor.ArrayProcessor;
import com.xbkj.common.jdbc.framework.processor.ResultSetProcessor;

/**
 *@desc DAO扩展类
 *@author zhoujun
 *@date 2016-06-09
 *@version 1.0.0
 */
public class DaoManageUtil {
	private static final Logger log = LoggerFactory.getLogger(DaoManageUtil.class);
	private static BaseDAO bdo = null;
	
	/**
	 * 根据用户id返回机构编码
	 * @param userId 用户ID
	 * @return 机构CODE
	 */
	@SuppressWarnings("unchecked")
	public static String getOrgCodeByUserId(String userId) throws Exception{
		String result = null;
		bdo = new BaseDAO();
		Object[] res = null;
		//查询机构sql
		String sql = "select o.orgcode from Org_Employee e left join org_organization o on e.orgid=o.orgid where e.empid="+userId;
		try{
			res = (Object[])bdo.executeQuery(sql, new ArrayProcessor());
		}catch(DAOException e){
			log.error("查询数据库失败!!!" + sql );
			throw new DAOException(e.getMessage() + sql);
		}
		if(res != null || res.length>0){
			result = res[0].toString().trim();
		}
		bdo = null;
		return result;
	}
	
	/**
	 * @param sql sql语句
	 * @return 查询对象
	 */
	public static Object getOneValueBySql(String sql) throws Exception{
		Object result = null;
		if(sql==null || sql.isEmpty()) return null;
		bdo = new BaseDAO();
		Object[] res = null;
		try{
			res = (Object[])bdo.executeQuery(sql, new ArrayProcessor());
		}catch(DAOException e){
			log.error("查询数据库失败!!!" + sql );
			throw new DAOException(e.getMessage() + sql);
		}
		if(res != null || res.length>0){
			result = res[0];
		}
		bdo = null;
		return result;
	}
	
	/**
	 * 查询返回
	 * @param sql 查询sql
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> exeQueryBySql(String sql,ResultSetProcessor processor) throws Exception {
		ArrayList <Object> result = null;
		BaseDAO bdmo = new BaseDAO();
		bdmo.setMaxRows(-1);
		try{
			//result = (ArrayList <Object[]>) bdmo.executeQuery(sql, new ArrayListProcessor());
			result = (ArrayList <Object>) bdmo.executeQuery(sql, processor);
		}catch(DAOException e){
			log.error("查询数据库失败!!!" + sql );
			throw new Exception(e.getMessage()+sql);
		}
		return result;
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String insertVO(SuperVO vo)throws Exception{
		if(vo ==null) return null;

		bdo = new BaseDAO();
		return bdo.insertVO(vo);
	}
	
	/**
	 * 插入实体，返回主键
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String insertVOWithPK(SuperVO vo)throws Exception{
		if(vo ==null) return null;

		bdo = new BaseDAO();
		return bdo.insertVOWithPK(vo);
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static Integer updateVO(SuperVO vo)throws Exception{
		if(vo ==null) return null;

		bdo = new BaseDAO();
		return bdo.updateVO(vo);
	}
	
	/**
	 * 执行更新，返回更新的行数
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static Integer executeUpdate(String sql)throws Exception{
		if(sql ==null) return null;

		bdo = new BaseDAO();
		return bdo.executeUpdate(sql);
	}
	
	/**
	 * 设置MappingMeta内部字段与属性对照
	 * @param c
	 * @param meta
	 */
	public static void handelMappingMeta(Class c, MappingMeta meta){
		if(c==null || meta==null) return;
		Field[] fields = c.getDeclaredFields();//得到定义的字段
		if(fields.length > 0){
			for(Field fld:fields){
				meta.addMapping(fld.getName(), fld.getName());
			}
		}
	}
}
