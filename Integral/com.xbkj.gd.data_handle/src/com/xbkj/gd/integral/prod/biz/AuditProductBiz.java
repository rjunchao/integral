/**
 * 
 */
package com.xbkj.gd.integral.prod.biz;


import java.util.List;
import java.util.Map;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.pub.xbkj.common.MsgResponse;
import com.xbkj.gd.integral.prod.dao.ApplyProductDao;
import com.xbkj.gd.integral.prod.dao.AuditProductDao;
import com.xbkj.gd.integral.prod.vos.ApplyProductVO;
import com.xbkj.gd.integral.prod.vos.AuditProductVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-17
 *@version 1.0.0
 *@desc
 */
@Bizlet("")
public class AuditProductBiz {
	
//	private static final Logger log = LoggerFactory.getLogger(AuditProductBiz.class);
	private AuditProductDao dao = new AuditProductDao();
//	private ProductDao prodDao = new ProductDao();
	private ApplyProductDao appDao = new ApplyProductDao();
	

	/**
	 * 查询提交明细
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public List<ApplyProductVO> queryDetail(Map<String,String> params){
		return appDao.queryApplyByPkAudit(params);
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
