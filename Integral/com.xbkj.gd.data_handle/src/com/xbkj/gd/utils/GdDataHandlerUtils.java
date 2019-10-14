/**
 * 
 */
package com.xbkj.gd.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.eos.foundation.PageCond;
import com.pub.xbkj.common.MsgResponse;
import com.pub.xbkj.pubapp.SqlBuilder;
import com.pub.xbkj.pubapp.exception.ExceptionUtils;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.bs.dao.BaseDAO;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.processor.BeanListProcessor;
import com.xbkj.common.util.StringUtil;
import com.xbkj.gd.base.GdSuperVO;


/**
 * @author rjc
 * @date 2017-07-16 15:30:37
 *
 */
public class GdDataHandlerUtils<T extends GdSuperVO> {
	private T t;
	public GdDataHandlerUtils(){
	}
	public GdDataHandlerUtils(T t){
		this.t = t;
	}
	/**
	 * 根据序号进行查询
	 * 序号也是唯一
	 * @param number
	 * @return
	 */
	public T query(String sql){
		BaseDAO dao = new BaseDAO();
		List<T> vos = null;
		try {
			vos = (List<T>) dao.executeQuery(sql, new BeanListProcessor(t.getClass()));
		} catch (DAOException e) {
			e.printStackTrace();
		}
		if(vos != null && vos.size() > 0){
			return vos.get(0);
		}else{
			return null;
		}
	}
	public List<T> query(String querySql ,String queryCountSql,PageCond page) {

		  
		  int count =0;
		  try {
			count = new DBUtils().getCountNumber(queryCountSql);
		} catch (DAOException e1) {
			e1.printStackTrace();
			count = -1;
		}
		     
	  if(count < 1){
			return null;
		}
		//每页显示条数，默认20条
		int length = page.getLength();
		if(length == 0){
			length = 20;
		}
		page.setCount(count);
		page.setTotalPage(count/length);
		int begin = page.getBegin();
		int end = begin + length;
		//从下一条开始查询
		if(begin >  0 ){
			begin += 1;
		}
		//构造分页查询SQL
		querySql = SqlBuilder.constructPageSql(querySql, begin, end);
		BaseDAO dao = new BaseDAO();
		try{
			List<T> obj = (List<T>) dao.executeQuery(querySql, new BeanListProcessor(t.getClass()));
			return obj;
		} catch (DAOException e) {
			e.printStackTrace();
			ExceptionUtils.wrappBusinessException("VOPageQuery ： 数据查询失败 ~ "+ querySql);		
		}
		return null;
 }
	
	/**
	 * 保存
	 * @param vo
	 * @return
	 */
	public MsgResponse save(T vo){
		//保存
		BaseDAO dao = new BaseDAO();
		try {
			vo.setCreate_user(UserUtils.getUser());
			vo.setCreate_user_org(UserUtils.getUserOrg());
			String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
			vo.setTs(ts);
		//	dao.insertVO(vo);
			dao.insertVOArrayWithPK(new SuperVO[]{vo});
			return new MsgResponse("保存成功", true);
		} catch (DAOException e) {
			e.printStackTrace();
			return new MsgResponse("保存失败" + e.getMessage(), false);
		} 
	}
	/**
	 * 保存
	 * @param vo
	 * @return
	 */
	public MsgResponse saveArr(T[] vos){
		//保存
		BaseDAO dao = new BaseDAO();
		try {
			//	dao.insertVO(vo);
			dao.insertVOArrayWithPK(vos);
			return new MsgResponse("保存成功", true);
		} catch (DAOException e) {
			e.printStackTrace();
			return new MsgResponse("保存失败" + e.getMessage(), false);
		} 
	}
	
	/**
	 * 根据主键
	 * @param number
	 * @return
	 */
	public MsgResponse del(String sql){
		if(StringUtil.isEmpty(sql)){
			return new MsgResponse("删除失败，sql为空！", false);
		}
		BaseDAO dao = new BaseDAO();
		try {
			int count = dao.executeUpdate(sql);
			if(count > 0){
				return new MsgResponse("删除成功", true);
			}
			return new MsgResponse("删除失败，没有删除数据的行", false);
		} catch (DAOException e) {
			e.printStackTrace();
			return new MsgResponse("删除失败" + e.getMessage(), false);
		}
	}
}