package com.xbkj.gd.integral.prod.biz;


import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.eos.system.logging.Logger;
import com.pub.xbkj.common.MsgResponse;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.gd.integral.prod.common.IntegralConstant;
import com.xbkj.gd.integral.prod.dao.AuditDetailDao;
import com.xbkj.gd.integral.prod.dao.OrgApplyProductDao;
import com.xbkj.gd.integral.prod.vos.AuditDetailVO;
import com.xbkj.gd.integral.prod.vos.OrgApplyProductVO;
import com.xbkj.gd.integral.vos.ComboboxVO;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-17
 *@version 1.0.0
 *@desc 支行业务员
 *	提交申请
 *	兑换
 *	
 */
@Bizlet("")
public class OrgApplyProductBiz {
	private static final Logger log = LoggerFactory.getLogger(OrgApplyProductBiz.class);
	private OrgApplyProductDao dao = new OrgApplyProductDao();
//	private OrgAuditProductDao auditDao = new OrgAuditProductDao();
	private AuditDetailDao adDao = new AuditDetailDao();
	
	/**
	 * 查询审批明细
	 * @return
	 */
	@Bizlet
	public List<AuditDetailVO> auditDetail(String applyId){
		List<AuditDetailVO> vos = adDao.findAuditDetailByApplyId(applyId);
		return vos;
	}
	/**
	 * 查询当前机构下的分理处人员
	 * @return
	 */
	@Bizlet
	public ComboboxVO[] getNowOrgUser(){
		List<Object[]> users = UserUtils.getUserList();
		if(users != null && users.size() > 0){
			int len = users.size();
			ComboboxVO[] coms = new ComboboxVO[len];
			ComboboxVO com = new ComboboxVO();
			Object[] objs = null;
			for(int i = 0; i < len; i++ ){
				com = new ComboboxVO();
				objs = users.get(i);
				String data = objs[0].toString() + "_" + objs[1].toString();
				com.setId(data);
				com.setText(data);
				coms[i] = com;
			}
			return coms;
		}
		return null;
	}
	/**
	 * 查询指定用户下富余的礼品
	 * @return
	 */
	@Bizlet
	public ComboboxVO[] queryProdByUser(String user){
		List<OrgApplyProductVO> vos = dao.queryProdByUser(user);
		if(vos != null && vos.size() > 0){
			int len = vos.size();
			ComboboxVO[] coms = new ComboboxVO[len];
			ComboboxVO com = new ComboboxVO();
			OrgApplyProductVO vo = null;
			for(int i = 0; i < len; i++ ){
				com = new ComboboxVO();
				vo = vos.get(i);
				String data = vo.getApply_product_name() + "_" + vo.getApply_product_num();//
				com.setId(vo.getPk_org_apply_product());
				com.setText(data);//编码_名称_数量
				coms[i] = com;
			}
			return coms;
		}
		return null;
	}
	
	
	@Bizlet
	public MsgResponse orgAudit(OrgApplyProductVO[] vos){
		try {
			MsgResponse msg = new MsgResponse(true);
			if(vos == null || vos.length <= 0){
				msg.setFlag(false);
				msg.setMessage("审核失败");
				log.error("审核失败");
				return msg;
			}
			String user = UserUtils.getUser();
			String formatDate = DateUtils.getFormatDate(DateUtils.PATTERN_19);
			for(OrgApplyProductVO vo : vos){
				vo.setAudit_status(IntegralConstant.HEAD_ORG_AMDIN);//合行管理
				if(!IntegralConstant.PROD_APPLY_AUDIT_STATUS_SUCCESS.equals(vo.getAudit_status())){//不同意
					vo.setAudit_status(IntegralConstant.AUDIT_ABORT);//拒绝
				}
				vo.setModifier(user);
				vo.setModifiedtime(formatDate);
				dao.auditOrgApply(vo);
			}
			return msg;
		} catch (Exception e) {
			log.error("审核失败", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 积分兑换的时候使用
	 * 	查询审批通过的数量
	 * 	下拉框
	 * @param params
	 * @return
	 */
	@Bizlet
	public ComboboxVO[] queryOrgSubGift(Map<String, String> params){
		List<OrgApplyProductVO> vos= dao.queryOrgApplyDetail(params);
		if(vos != null && vos.size() > 0){
			int len = vos.size();
			ComboboxVO[] coms = new ComboboxVO[len];
			ComboboxVO com = null;
			OrgApplyProductVO prod = null;
			for(int i = 0; i < len; i++){
				prod = vos.get(i);
				com = new ComboboxVO();
				//id_名称_积分 prod.getApply_product_code() + "_" + 
				String data = prod.getApply_product_name() + "_" + prod.getApply_product_integral();
				com.setId(prod.getPk_org_apply_product() + "_" + data);
				com.setText(data);
				coms[i] = com;
			}
			return coms;
		}
		return null;
	}
	/**
	 *	按条件查询
	 * @param params
	 * @return
	 */
	@Bizlet
	public List<OrgApplyProductVO> queryApplyDetailCombobox(Map<String, String> params){
		/*List<OrgApplyProductVO> vos = dao.queryOrgApply(params);
		return vos;*/
		return null;
	}
	/**
	 * 分页查询商品申请数据
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public OrgApplyProductVO[] queryApplyProductPage(Map<String,String> params,PageCond page){
		return dao.queryOrgApplyProductPage(params, page);
	}
	
	

	/**
	 * 提交申请
	 * 	需要四个
	 * 		支行管理员（调拨或向上继续提交）->合行管理员->领导
	 * 		
	 * @param vos
	 * @return
	 * @throws Exception 
	 */
	@Bizlet
	public MsgResponse apply(OrgApplyProductVO[] vos) throws Exception{
		MsgResponse msg = new MsgResponse(false);
		try {
			if(vos != null && vos.length > 0){
				//申请审批表
//				String  pk_audit_product = auditDao.createAudit();
				//申请明细表
				String name = null;
				String[] temps = null;
				String user = UserUtils.getUser();
				String userOrg = UserUtils.getUserOrg();
				String formatDate = DateUtils.getFormatDate(DateUtils.PATTERN_19);
				for(OrgApplyProductVO vo : vos){
					name = vo.getApply_product_name();
					temps = name.split("_");
					//prod.getProduct_name() + "_" + prod.getProduct_integral();
//					vo.setApply_product_code(temps[0]);//
					vo.setApply_product_name(temps[0]);
					vo.setApply_product_integral(temps[1]);
					vo.setPk_org_apply_product(PrimaryKeyUtil.getPrimaryKey());
//					vo.setPk_org_audit_product(pk_audit_product);//审批主键
					vo.setDr("0");
					vo.setTs(formatDate);
					vo.setApply_org(userOrg);
					vo.setApply_user(user);
					vo.setAudit_status(IntegralConstant.ORG_SUB_BRANCH_AMDIN);//
					vo.setModifier(user);
					vo.setModifiedtime(formatDate);
				}
				//保存明细信息
				dao.saveArr(vos);
				msg.setFlag(true);
				msg.setMessage("申请成功");
				return msg;
			}
			msg.setMessage("数据为空");
			return msg;
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e);
		}
	}

	
}
