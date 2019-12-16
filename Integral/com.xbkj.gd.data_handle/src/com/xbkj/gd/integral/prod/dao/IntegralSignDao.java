package com.xbkj.gd.integral.prod.dao;

import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.gd.utils.DBUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-30
 *@version 1.0.0
 *@desc
 */
public class IntegralSignDao {

//	private GdDataHandlerUtils<IntegralSignVO> utils = new GdDataHandlerUtils<IntegralSignVO>();
	private DBUtils utils = new DBUtils();
	
	/*public MsgResponse save(IntegralSignVO vo){
		return utils.save(vo);
	}*/
	//更新积分明细签名id
	public void updateDetailSign(SQLParameter parameter){
		String sql = "UPDATE GD_SUB_INTEGRAL_DETAIL SET DEF6=? WHERE PK_INTEGRAL_DETAIL=?";
		try {
			utils.executeUpdateSQL(sql , parameter);
		} catch (DAOException e) {
			throw new RuntimeException(e);
		}
	}
}
