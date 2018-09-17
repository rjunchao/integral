package com.vbm.grc.system;


import java.util.Map;

import com.eos.foundation.PageCond;
import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.annotation.Bizlet;
import com.eos.system.logging.Logger;
import com.pub.xbkj.common.MsgResponse;
import com.pub.xbkj.pubapp.JavaType;
import com.pub.xbkj.pubapp.delete.VODelete;
import com.pub.xbkj.pubapp.insert.VOInsert;
import com.pub.xbkj.pubapp.pagequery.VOPageQuery;
import com.pub.xbkj.pubapp.update.VOUpdate;
import com.vbm.grc.system.vo.RefRelationVO;
import com.xbkj.common.util.ArrayUtil;
import com.xbkj.common.util.MapUtil;
import com.xbkj.common.util.StringUtil;


/**
 *@author heFei
 *@date 
 *@version 1.0.0
 */

@Bizlet("表引用操作的类")
public class RefRelationService {
	//数据库操作的日志
	private static Logger logger = TraceLoggerFactory.getLogger(RefRelationService.class);
	/**
	 * 查询的操作：
	 *  查询用的是工具类：VOPageQuery这个类
	 */
	@Bizlet("查询")
	public RefRelationVO [] queryRefRelation(Map<String,String> params,PageCond page){
		RefRelationVO[] vos = null;
		String where = "";
		//判断是不是有参数
		if(!MapUtil.isEmpty(params)){
			//获取查询的key
			String key = params.get("key");
			if(!StringUtil.isEmpty(key)){
				key = key.trim();//对key修剪
				//被引用表名和引用字段
				where = " and (refed_table_name like '%" + key + "%' or refing_table_name like '%" + key + "%')";
			}
		}
		//查询数据实体
		VOPageQuery<RefRelationVO> query = new VOPageQuery<RefRelationVO>(RefRelationVO.class);
		//查询的总记录
		String queryCountSql = "select count(1) from grc_ref_relation where dr = 0 " +where;
	    //查询
		String querySql = "select t.pk_ref,t.refed_table_name,t.refed_table_key,t.refing_table_name,t.refing_table_colum,t.ts " +
				"from grc_ref_relation t where dr = 0 " + where;
		vos = query.query(querySql, queryCountSql, page);
		return vos;
	}
    /**
     * 1.在添加数据的时候，如果数据是空调用MsgResponse这个类，给去提示
     * 2.在添加的时候自动添加ts和dr
     * 
     * @param refs
     * @return 
     */
	
	@Bizlet("保存")
	public MsgResponse saveRefRelation(RefRelationVO [] refs){
		//保存时的信息提示
		MsgResponse msg = new MsgResponse();
		//对实体RefRelation添加的时候判断是不是空和null
		if(ArrayUtil.isEmpty(refs)){
			//保存出错时的提示信息
			msg.setMessage("保存的数据为空，请检查数据");
			msg.setFlag(false);
			return msg;
		}
		//自动添加ts和dr:默认0显示数据，1不显示数据
		VOInsert<RefRelationVO> insert = new VOInsert<RefRelationVO>(RefRelationVO.class);
		//添加的数据字段和表的字段名对应
		String[] fields = new String[]{RefRelationVO.PK_REF,RefRelationVO.REFED_TABLE_NAME,RefRelationVO.REFED_TABLE_KEY,RefRelationVO.REFING_TABLE_NAME,
		    RefRelationVO.REFING_TABLE_COLUM,RefRelationVO.TS};
		
		JavaType [] types = new JavaType[]{JavaType.String,JavaType.String,JavaType.String,JavaType.String,JavaType.String,JavaType.String};
		
		try {
			//将数据添加入数据库
			insert.insert(refs, fields, types, false);
			
		} catch (Exception e) {
			//添加数据时：出错的日志
			logger.error(e.getMessage(), e);
			msg.setMessage("保存数据失败，请检查...");
			msg.setFlag(false);
			return msg;
		}
		 msg.setMessage("保存数据成功，请继续...");
		 msg.setFlag(true);	
		 return msg;
	}
	
	//更新时忽略警告
	@Bizlet("更新")
	public MsgResponse updateRefRelation(RefRelationVO [] refs){
		MsgResponse msg = new MsgResponse();
		if(ArrayUtil.isEmpty(refs)){
			msg.setMessage("保存的数据是空的，请检查...");
			msg.setFlag(false);
			return msg;
		}
		String[] fields = new String[]{RefRelationVO.REFED_TABLE_NAME,RefRelationVO.REFED_TABLE_KEY,RefRelationVO.REFING_TABLE_NAME,
		    RefRelationVO.REFING_TABLE_COLUM};
		//利用VOUpdate这个类里面的update方法更新
		VOUpdate<RefRelationVO> update = new VOUpdate<RefRelationVO>(RefRelationVO.class);
		JavaType [] types = new JavaType[]{JavaType.String,JavaType.String,JavaType.String,JavaType.String};
		//这里是更新的操作
		try {
			update.update(refs, fields, types, true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg.setMessage("修改数据失败，请检查...");
			msg.setFlag(false);
			return msg;
		}
		msg.setMessage("修改数据成功，请继续！");
		msg.setFlag(true);
		return msg;
	}
	
	/**
	 * 在删除的时候调用VODelete这个类的delete方法对数据进行删除
	 * @param refs
	 * @return
	 */
	@Bizlet("删除")
	public MsgResponse deleteRefRelation(RefRelationVO [] refs){
		//删除成功的信息提示
		MsgResponse msg = new MsgResponse();
		VODelete<RefRelationVO> delete = new VODelete<RefRelationVO>();
		
		try {
				delete.delete(refs);//删除数据的操作
		} catch (Exception e) {
			logger.error(e.getMessage(),e);//错误日志
			msg.setMessage("删除数据失败，请检查！");
			msg.setFlag(false);
			return msg;
		}
		msg.setMessage("删除数据成功！");
		msg.setFlag(true);
		//System.out.println(msg);
		return msg;
	}
	
}
