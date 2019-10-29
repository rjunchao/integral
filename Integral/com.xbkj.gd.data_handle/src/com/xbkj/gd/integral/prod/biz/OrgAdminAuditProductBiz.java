/**
 * 
 */
package com.xbkj.gd.integral.prod.biz;


import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.eos.system.logging.Logger;
import com.pub.xbkj.common.MsgResponse;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.util.StringUtil;
import com.xbkj.gd.integral.prod.common.IntegralConstant;
import com.xbkj.gd.integral.prod.dao.OrgApplyProductDao;
import com.xbkj.gd.integral.prod.dao.OrgAuditProductDao;
import com.xbkj.gd.integral.prod.dao.ProductDao;
import com.xbkj.gd.integral.prod.vos.ApplyProductVO;
import com.xbkj.gd.integral.prod.vos.AuditProductVO;
import com.xbkj.gd.integral.prod.vos.OrgApplyProductVO;
import com.xbkj.gd.integral.prod.vos.ProductVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-23
 *@version 1.0.0
 *@desc
 *	礼品分配 
 *		从其他分理处调拨
 *	礼品申请
 *		从总行申请
 *	分页查询
 *		
 *	
 */

@Bizlet("")
public class OrgAdminAuditProductBiz {
	private static final Logger log = LoggerFactory.getLogger(OrgAdminAuditProductBiz.class);
	private OrgAuditProductDao dao = new OrgAuditProductDao();
	private ProductDao prodDao = new ProductDao();
	private OrgApplyProductDao appDao = new OrgApplyProductDao();
	
	
	/**
	 * 从分理处调拨其他分理处的礼物
	 * @param vo 需要礼物的分理处
	 * @param subBran 提供礼物的分理处
	 * @return
	 */
	@Bizlet
	public MsgResponse subBranchesAllot(OrgApplyProductVO vo, OrgApplyProductVO subBran){
		MsgResponse msg = new MsgResponse(true);
		log.info("从" + subBran + "调拨" + vo);
		//校验
		MsgResponse temp = validate(vo, subBran);
		if(temp != null){
			return temp;
		}
		//校验分理处是否有那么多礼品
		try {
			OrgApplyProductVO target = appDao.querySubBranchProd(subBran.getPk_org_apply_product());
			if(target == null){return new MsgResponse("调拨失败，被调拨中心没有", false);}
			
			if(vo.getApply_product_num() > target.getApply_product_num()){
				return new MsgResponse("调拨分理处货存不足，失败", false);
			}
			//调拨
			//总的 - 调拨的
			subBran.setApply_product_num(target.getApply_product_num() - vo.getApply_product_num());
			subBran.setRemark("调拨减少");
			appDao.allotOrgApply(subBran);
			
			vo.setAudit_status(IntegralConstant.ALLOT_PASS);//调拨
			vo.setRemark("通过调拨的形式");
			appDao.auditOrgApply(vo);
			msg.setMessage("调拨成功");
			return msg;
		} catch (DAOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 支行审批通过
	 * @param vos
	 * @return
	 */
	@Bizlet
	public MsgResponse sbApproved(OrgApplyProductVO[] vos){
		if(vos == null || vos.length <= 0){
			return new MsgResponse("数据为空", false);
		}
		try {
			for(OrgApplyProductVO vo : vos){
				ProductVO prod = prodDao.queryProductByCode(vo.getApply_product_code());
				if(prod.getProduct_num() < vo.getApply_product_num()){
					throw new RuntimeException(vo.getApply_product_code() + "数量不够，请重新申请");
				}
				vo.setRemark("支行向合行发起申请成功");
				vo.setAudit_status(IntegralConstant.HEAD_ORG_AMDIN);
				appDao.auditOrgApply(vo);
			}
			return new MsgResponse("支行向合行发起申请成功", true);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * 合行管理员审批通过
	 * @param vos
	 * @return
	 */
	@Bizlet
	public MsgResponse orgApproved(OrgApplyProductVO[] vos){
		if(vos == null || vos.length <= 0){
			return new MsgResponse("数据为空", false);
		}
		try {
			for(OrgApplyProductVO vo : vos){
				ProductVO prod = prodDao.queryProductByCode(vo.getApply_product_code());
				if(prod.getProduct_num() < vo.getApply_product_num()){
					throw new RuntimeException(vo.getApply_product_code() + "数量不够，请重新申请");
				}
				vo.setRemark("合行管理员审批成功");
				vo.setAudit_status(IntegralConstant.HEAD_ORG_LEADER);
				appDao.auditOrgApply(vo);
			}
			return new MsgResponse("审批成功", true);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * 总行领导审核通过
	 * @param vos
	 * @return
	 */
	@Bizlet
	public MsgResponse leaderApproved(OrgApplyProductVO[] vos){
		if(vos == null || vos.length <= 0){
			return new MsgResponse("数据为空", false);
		}
		try {
			SQLParameter parameter = null;;
			for(OrgApplyProductVO vo : vos){
				ProductVO prod = prodDao.queryProductByCode(vo.getApply_product_code());
				if(prod.getProduct_num() < vo.getApply_product_num()){
					throw new RuntimeException(vo.getApply_product_code() + "数量不够，请重新申请");
				}
				vo.setRemark("总行领导审核通过");
				vo.setAudit_status(IntegralConstant.AUDIT_PASS);
				appDao.auditOrgApply(vo);
				//更新产品的数量
				parameter = new SQLParameter();
				parameter.addParam((prod.getProduct_num() - vo.getApply_product_num()));//总的-申请的
				parameter.addParam(prod.getPk_product());
				prodDao.updateProductNum(parameter);
				
			}
			return new MsgResponse("审批成功", true);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * 礼品拒绝
	 * @param vos
	 * @return
	 */
	@Bizlet
	public MsgResponse giftRefuse(OrgApplyProductVO[] vos, Map<String, String> params){
		if(vos == null || vos.length <= 0){
			return new MsgResponse("数据为空", false);
		}
		try {
			String remark = params.get("refuse_remark");
			for(OrgApplyProductVO vo : vos){
				vo.setAudit_status(IntegralConstant.AUDIT_ABORT);
				vo.setRemark(remark);//备注
				appDao.auditOrgApply(vo);
			}
			return new MsgResponse("拒绝成功", true);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	
	private MsgResponse validate(OrgApplyProductVO vo, OrgApplyProductVO subBran) {
		if(StringUtil.isEmpty(vo.getApply_product_code())){
			return new MsgResponse("申请礼品代码为空", false);
		}
		if(vo.getApply_product_num() <= 0){
			return new MsgResponse("申请礼品数量小于0", false);
		}
	/*	if(StringUtil.isEmpty(subBran.getApply_user())){
			return new MsgResponse("调拨分理处为空", false);
		}*/
		return null;
	}

	/**
	 * 查询提交明细
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public List<OrgApplyProductVO> queryDetail(Map<String,String> params){
		return appDao.queryOrgApplyByPkAudit(params);
	}
	/**
	 * 分页查询
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public AuditProductVO[] queryApplyProductPage(Map<String,String> params,PageCond page){
		return dao.queryAuditProductPage(params, page);
	}
	
	/**
	 * 审批申请的商品数
	 * @param applyProds prod.getPk_apply_product() + "_" + prod.getPk_audit_product() + "_" + prod.getApply_product_code() + "_" + prod.getApply_product_name() + "_" + prod.getApply_product_num()
	 * @param params 
	 * @return
	 * @throws Exception 
	 */
	@Bizlet
	public MsgResponse auditApplyProd(ApplyProductVO[] vos) throws Exception{
		MsgResponse msg = new MsgResponse(true);
		/*if(vos != null && vos.length > 0){
			SQLParameter parameter = null;
			int count = 0;
			try {
				ProdectBiz prodBiz = new ProdectBiz();
				String status = null;
				ProductVO tempVO = null;
				for(ApplyProductVO vo : vos){
					status = vo.getAudit_status();
					tempVO = prodBiz.queryProductByCode(vo.getApply_product_code());
					
					//判断审批通过的是否超过了库存
					if((tempVO.getProduct_num() < vo.getAudit_product_num()) 
							&& IntegralConstant.PROD_APPLY_AUDIT_STATUS_SUCCESS.equals(status)){
						count++;
						continue;
					}
					
					//更新审批审批表数据
					parameter = new SQLParameter();
					parameter.addParam(vo.getAudit_product_num());//审批通过的数量
					parameter.addParam(status);//状态
					parameter.addParam(vo.getRemark());//备注
					parameter.addParam(UserUtils.getUser());//审批人
					parameter.addParam(DateUtils.getFormatDate(DateUtils.PATTERN_19));//时间
					parameter.addParam(vo.getPk_apply_product());//主键
					appDao.updateAuditStatus(parameter);
					//更新商品表是数量，审批通过才更新，失败不更新
					if(IntegralConstant.PROD_APPLY_AUDIT_STATUS_SUCCESS.equals(status)){
						//商品数量减少
						//update gd_product xxxx
						parameter = new SQLParameter();
						parameter.addParam(tempVO.getProduct_num() - vo.getAudit_product_num());//总的数量-审批通过的数量
						parameter.addParam(tempVO.getPk_product());//状态
						prodDao.updateProductNum(parameter);
					}
				}
				//更新审批表
				parameter = new SQLParameter();
				parameter.addParam(UserUtils.getUser());//审批人
				parameter.addParam(DateUtils.getFormatDate(DateUtils.PATTERN_19));//时间
				if(count > 0){
					parameter.addParam(IntegralConstant.PROD_APPLY_AUDIT_STATUS_PASS_PORTION);//部分通过
				}else{
					parameter.addParam(IntegralConstant.PROD_APPLY_AUDIT_STATUS_PASS_ALL);//全部通过
				}
				parameter.addParam(vos[0].getPk_audit_product());//审批主键
				dao.updateProductAuditInfo(parameter);
			} catch (DAOException e) {
				e.printStackTrace();
				log.error(e);
				throw new Exception(e);
			}
			msg.setMessage("审批成功，全部审批通过！");
			if(count > 0){
				msg.setMessage("部分审批成功，存在货存不足！");
			}
			return msg;
		}
		msg.setFlag(false);
		msg.setMessage("审批失败，数据为空");*/
		return msg;
	}
}
