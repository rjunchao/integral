package com.xbkj.gd.integral.prod.biz;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.eos.system.logging.Logger;
import com.pub.xbkj.common.MsgResponse;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.gd.integral.prod.common.IntegralConstant;
import com.xbkj.gd.integral.prod.dao.AuditDetailDao;
import com.xbkj.gd.integral.prod.dao.OrgApplyProductDao;
import com.xbkj.gd.integral.prod.dao.OrgProdAllotDao;
import com.xbkj.gd.integral.prod.vos.AuditDetailVO;
import com.xbkj.gd.integral.prod.vos.OrgApplyProductVO;
import com.xbkj.gd.integral.prod.vos.OrgProdAllotVO;
import com.xbkj.gd.integral.vos.ComboboxVO;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.ExcelUtils;
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
	 * 
	 * 	
	 * 
	 * 礼品采购导出
	 * @param request
	 * @param response
	 */
	@Bizlet("")
	public void export(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("def1", request.getParameter("def1"));
		parameter.put("audit_status", "7");
		parameter.put("start_date", request.getParameter("start_date"));
		parameter.put("end_date", request.getParameter("end_date"));
		parameter.put("apply_product_name", request.getParameter("apply_product_name"));
		String[] heads = {"分理处", "礼品", "数量","积分", "状态", "备注"};
		Workbook wb = ExcelUtils.buildWorkBook("xlsx", "积分报表信息", heads);
		Sheet sheet = wb.getSheetAt(0);
		ExcelUtils.setCellWidth(sheet, 3, 50);
		OrgApplyProductVO[] vos = dao.queryOrgApplyProductPage(parameter, null, false);
		if(vos != null && vos.length > 0) {
			String[] contents = null;
			int size = vos.length;
			OrgApplyProductVO vo = null;
			Row row = null;
			for(int i = 0; i < size; i++) {
				vo = vos[i];
				row = sheet.createRow(i+2);
				contents = new String[] {
						vo.getEmpname(),
						vo.getApply_product_name(),
						String.valueOf(vo.getApply_product_num()),
						vo.getApply_product_integral(),
						convertStatus(vo.getDef1()),
						vo.getRemark()
				};
				ExcelUtils.batchCreateCell(row, contents);
			}
		}
		try {
			response.setContentType("application/x-download");
			response.setCharacterEncoding("utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + 
					UserUtils.toUtf8String("积分报表信息" + DateUtils.getFormatDate("yyyyMMddHHmmss")) + ".xlsx");
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String convertStatus(String def1) {
		if("1".equals(def1)){
			return "未采购";
		}
		if("2".equals(def1)){
			return "已采购";
		}
		if("3".equals(def1)){
			return "调拨通过";
		}
		return "未知状态";
	}

	/**
	 * 礼品采购
	 * @param vos
	 * @return
	 */
	@Bizlet
	public MsgResponse prodProcurement(Map<String, String> params){
		String ids = params.get("ids"); 
		if(StringUtils.isEmpty(ids)){
			return new MsgResponse("数据为空", false);
		}
		try {
			
			String remark = params.get("remark").toString();
			String opt_type = params.get("opt_type").toString();
			int count = dao.prodProcurement(ids, remark, opt_type);
			if(count>0){
				return new MsgResponse("成功", true);
			}
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		return new MsgResponse("采购失败", false);
	}
	
	
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
				String data = vo.getApply_product_name() + "_" + 
				//可以调度的=总的 - 调度的 - 兑换了的
				(vo.getApply_product_num() - vo.getAllot_product_num() - vo.getOrg_sub_num());
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
		return dao.queryOrgApplyProductPage(params, page, true);
	}
	
	/**
	 * 查询可以审批通过并且有富余的礼品
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public OrgApplyProductVO[] queryAuditPassProdPage(Map<String,String> params,PageCond page){
		return dao.queryAuditPassProdPage(params, page, true);
	}
	
	
	
	/**
	 * 提取支行的礼品，并插入礼品明细
	 * @param params
	 * @return
	 */
	@Bizlet
	public MsgResponse updateProdNumber(Map<String,String> params){
		
		try {
			MsgResponse msg = vaildate(params);
			if(msg != null){
				return msg;
			}
			OrgApplyProductVO oap = dao.get(params.get("pk_org_apply_product"));
			
			//(p.apply_product_num - p.allot_product_num - p.org_sub_num) > 0
			int now_num = (oap.getApply_product_num() - oap.getAllot_product_num() - oap.getOrg_sub_num());
			String num = params.get("org_sub_num");
			if(now_num <= 0){
				return new MsgResponse("库存不足", false);
			}
			if(now_num < Integer.parseInt(num)){
				return new MsgResponse("库存不足", false);
			}
			int count = dao.allotAuditProd(params);
			if(count > 0){
				//插入明细
				OrgProdAllotDao opaDao = new OrgProdAllotDao();
				OrgProdAllotVO opa =new OrgProdAllotVO(
						oap.getPk_org_apply_product(), 
						oap.getApply_product_name(), 
						params.get("allot_org"),
						Integer.parseInt(num),
						params.get("remark")
					);
				opa.setDef1(oap.getApply_user());
				opaDao.save(opa);
				return new MsgResponse("操作成功", true);
			}
			return new MsgResponse("操作失败", false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	

	private MsgResponse vaildate(Map<String, String> params) {
		if(StringUtils.isEmpty(params.get("pk_org_apply_product"))){
			return new MsgResponse("请选择", false);
		} 
		if(StringUtils.isEmpty(params.get("allot_prod"))){
			return new MsgResponse("请选择", false);
		} 
		if(StringUtils.isEmpty(params.get("allot_org"))){
			return new MsgResponse("请选择", false);
		} 
		if(StringUtils.isEmpty(params.get("org_sub_num"))){
			return new MsgResponse("请输入数量", false);
		} 
		if(StringUtils.isEmpty(params.get("remark"))){
			return new MsgResponse("请输入备注", false);
		}
		return null;
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
