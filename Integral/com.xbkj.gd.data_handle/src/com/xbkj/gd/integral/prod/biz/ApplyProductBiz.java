package com.xbkj.gd.integral.prod.biz;


import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.eos.system.logging.Logger;
import com.pub.xbkj.common.MsgResponse;
import com.xbkj.gd.integral.prod.dao.ApplyProductDao;
import com.xbkj.gd.integral.prod.vos.ApplyProductVO;
import com.xbkj.gd.integral.vos.ComboboxVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-17
 *@version 1.0.0
 *@desc 商品申请
 */
@Bizlet("")
public class ApplyProductBiz {
	private static final Logger log = LoggerFactory.getLogger(ApplyProductBiz.class);
	private ApplyProductDao dao = new ApplyProductDao();
//	private AuditProductDao auditDao = new AuditProductDao();
	/**
	 * 积分兑换的时候使用
	 * 	查询审批通过的数量
	 * 	下拉框
	 * @param params
	 * @return
	 */
	@Bizlet
	public ComboboxVO[] querySubProd(Map<String, String> params){
		List<ApplyProductVO> vos= dao.queryApplyDetail(params);
		if(vos != null && vos.size() > 0){
			int len = vos.size();
			ComboboxVO[] coms = new ComboboxVO[len];
			ComboboxVO com = null;
			ApplyProductVO prod = null;
			for(int i = 0; i < len; i++){
				prod = vos.get(i);
				com = new ComboboxVO();
				//code_名称_积分
				String data = prod.getApply_product_code() + "_" + prod.getApply_product_integral() + "_" + prod.getApply_product_name();
				com.setId(data);
				com.setText(data);
				coms[i] = com;
			}
			return coms;
		}
		return null;
	}
	/**
	 *查询未审核的记录
	 * 审批的时候调用
	 * @param params
	 * @return
	 */
	@Bizlet
	public List<ApplyProductVO> queryApplyDetailCombobox(Map<String, String> params){
		List<ApplyProductVO> vos = dao.queryApplyDetailCombobox(params);
		return vos;
	}
	/**
	 * 分页查询商品申请数据
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public ApplyProductVO[] queryApplyProductPage(Map<String,String> params,PageCond page){
		return dao.queryApplyProductPage(params, page);
	}
	
	

	/**
	 * 提交申请
	 * @param vos
	 * @return
	 * @throws Exception 
	 */
	@Bizlet
	public MsgResponse apply(ApplyProductVO[] vos) throws Exception{
		MsgResponse msg = new MsgResponse(false);
		try {
			/*if(vos != null && vos.length > 0){
				//申请审批表
				String  pk_audit_product = auditDao.createAudit();
				//申请明细表
				String name = null;
				String[] temps = null;
				for(ApplyProductVO vo : vos){
					name = vo.getApply_product_name();
					temps = name.split("_");
					//prod.getProduct_code() + "_" + prod.getProduct_name() + "_" + prod.getProduct_integral();
					vo.setApply_product_code(temps[0]);//
					vo.setApply_product_name(temps[1]);
					vo.setApply_product_integral(temps[2]);
					vo.setPk_apply_product(PrimaryKeyUtil.getPrimaryKey());
					vo.setPk_audit_product(pk_audit_product);//审批主键
					vo.setDr("0");
					vo.setApply_org(UserUtils.getUserOrg());
					vo.setAudit_status(IntegralConstant.PROD_APPLY_AUDIT_STATUS_PASS_NULL);//
				}
				//保存明细信息
				dao.saveArr(vos);
				msg.setFlag(true);
				msg.setMessage("申请成功");
				return msg;
			}
			msg.setMessage("数据为空");*/
			return msg;
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e);
		}
	}

	
}
