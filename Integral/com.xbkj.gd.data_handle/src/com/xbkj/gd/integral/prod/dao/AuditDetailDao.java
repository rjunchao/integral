package com.xbkj.gd.integral.prod.dao;

import java.util.List;

import com.pub.xbkj.common.MsgResponse;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.gd.integral.prod.vos.AuditDetailVO;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-11-2
 *@version 1.0.0
 *@desc
 */
public class AuditDetailDao {

	GdDataHandlerUtils<AuditDetailVO> voUtils = new GdDataHandlerUtils<AuditDetailVO>(new AuditDetailVO());
	
	public MsgResponse save(String applyId, String type, String remark){
		AuditDetailVO vo = new AuditDetailVO();
		vo.setPk_audit_detail(PrimaryKeyUtil.getPrimaryKey());
		vo.setAudit_date(DateUtils.getFormatDate(DateUtils.PATTERN_19));
		vo.setAudit_user(UserUtils.getUser());
		vo.setPk_org_apply_product(applyId);
		vo.setAudit_type(type);
		vo.setRemark(remark);
		MsgResponse msg = voUtils.save(vo);
		return msg;
	}
	
	
	public List<AuditDetailVO> findAuditDetailByApplyId(String pk_org_apply_product){
		String sql = "SELECT AUDIT_DATE, AUDIT_USER,AUDIT_TYPE, REMARK FROM GD_AUDIT_DETAIL WHERE PK_ORG_APPLY_PRODUCT=? order by AUDIT_DATE";
		SQLParameter param = new SQLParameter();
		param.addParam(pk_org_apply_product);
		List<AuditDetailVO> vos = voUtils.queryList(sql, param);
		return vos;
	}
}
