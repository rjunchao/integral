package com.xbkj.gd.integral.prod.dao;

import com.pub.xbkj.common.MsgResponse;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.gd.integral.prod.vos.SignVO;
import com.xbkj.gd.utils.GdDataHandlerUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-30
 *@version 1.0.0
 *@desc
 */
public class SignDao {

	private static GdDataHandlerUtils<SignVO> utils = new GdDataHandlerUtils<SignVO>(new SignVO());
	
	public MsgResponse save(SignVO vo){
		return utils.save(vo);
	}
	public MsgResponse saves(SignVO[] vos){
		return  utils.saveArr(vos);
	}
	public static SignVO getByPkSub(String pkDetail){
		String sql = "SELECT T.PK_SIGN, T.SIGN_DATE, T.SIGN_FILE_NAME, T.SIGN_FILE_PATH " +
				"	FROM GD_SIGN T " +
				"	WHERE T.PK_SIGN=?";
		SQLParameter param = new SQLParameter();
		param.addParam(pkDetail);
		SignVO vo = utils.query(sql, param);
		return vo;
	}
}
