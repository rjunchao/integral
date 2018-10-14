package com.xbkj.gd.integral.biz;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.eos.foundation.PageCond;
import com.eos.foundation.impl.PageCondImpl;
import com.eos.system.annotation.Bizlet;
import com.pub.xbkj.common.MsgResponse;
import com.pub.xbkj.pubapp.pagequery.VOPageQuery;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.gd.integral.biz.service.IntegralOpertionService;
import com.xbkj.gd.integral.vos.AddIntegralDetailVO;
import com.xbkj.gd.integral.vos.CustomerVO;
import com.xbkj.gd.integral.vos.IntegralDetailVO;
import com.xbkj.gd.integral.vos.SubIntegralDetailVO;
import com.xbkj.gd.integral.vos.VipIntegralDetailVO;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.ExcelUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2017-9-5
 *@version 1.0.0
 *@desc:
 *	积分操作：
 *		积分修改：	
 *			积分增加：操作字段，积分、账号、存单号、存单金额
 *			积分减少：积分减少操作字段，积分、兑换明细
 *		积分导出：
 *			积分导出
 *			
 */
@Bizlet
public class IntegralOptionBiz {
	


	/**
	 * 特殊的账号和存单号可以重复保存
	 */
	private static String DEPOSIT_RECEIPT_NUM= "77777777 88888888 99999999";
	private static String CUSTOMER_ACCOUNT= "7777777777777777 8888888888888888 9999999999999999";
	
	private IntegralOpertionService service = new IntegralOpertionService();
	
	/**
	 * 导入历史积分明细
	 * @param request
	 * @param type 0:积分添加，1：积分兑换
	 */
	public String importHistoryIntegralDetail(HttpServletRequest request, int type){
		return service.importHistoryIntegralDetail(request, type);
	}
	/**
	 * 导出积分明细
	 * @param request
	 * @param response
	 */
	public void exportIntegralDetail(HttpServletRequest request, HttpServletResponse response){
		service.exportIntegralDetail(request, response);
	}
	
	/**
	 * 添加积分
	 * @param vos
	 * @return
	 */
	@Bizlet
	public MsgResponse addIntegral(AddIntegralDetailVO vo){
		MsgResponse vipIntegral = service.addIntegral(vo);
		System.out.println("积分添加成功");
		return vipIntegral;
	}
	
	/**
	 * 积分兑换
	 * @param vo
	 * @return
	 */
	@Bizlet
	public MsgResponse exchangeIntegral(SubIntegralDetailVO[] vos) throws RuntimeException{
		MsgResponse vipIntegral = service.exchangeIntegral(vos);
		return vipIntegral;
	}
	
	/**
	 * vip 积分赠送
	 * @param vo
	 * @return
	 */
	@Bizlet
	public MsgResponse vipIntegral(VipIntegralDetailVO vo) throws RuntimeException{
		MsgResponse vipIntegral = service.vipIntegral(vo);
		return vipIntegral;
	}

	/*--------------------------------------------------------------------------------*/
	/**
	 * 导出积分信息
	 * @param request
	 * @param response
	 */
	public void exportIntegral(HttpServletRequest request, HttpServletResponse response){
		//导出积分明细信息
		//得到参数，根据参数查询得到数据
//		Map parameter = request.getParameterMap();
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("customer_idcard", request.getParameter("customer_idcard"));
		parameter.put("start_date", request.getParameter("start_date"));
		parameter.put("end_date", request.getParameter("end_date"));
		parameter.put("hiddenFlag", request.getParameter("hiddenFlag"));
		PageCondImpl page = new PageCondImpl();
		page.set("begin", 0);
		page.set("length", 60000);
		//如果查询为空导出一个模板
		
		
		IntegralDetailVO[] integrals = this.queryCustIntegralDetail(parameter, page);
		if(integrals == null){
			integrals = new IntegralDetailVO[0];
		}
		//导出
	//	System.out.println("export vo:" + integrals);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("客户线下积分明细表");
		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell = row0.createCell(0);
		//第一行
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short)12);
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(style);
		
		cell.setCellValue("客户积分明细表");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		cell.setCellStyle(style);

		String[] titles = {"客户名称", "客户身份证号", "积分", "账号", 
					"存单金额", "存单号", "兑换明细", "备注", "录入人", "录入机构", "录入时间"};
		//创建表头
		HSSFRow row = sheet.createRow(1);
		ExcelUtils.batchCreateCell(row, titles);
		if(integrals.length >= 0){
			String[] contents = null;
			IntegralDetailVO vo = null;
			
			for(int i = 0, len = integrals.length; i < len; i++){
				vo = integrals[i];
				String customer_name = vo.getCustomer_name();
				String idcard = vo.getCustomer_idcard();
				double integral = vo.getCustomer_integral();
				String account = vo.getCustomer_account();
				double amt = vo.getDeposit_receipt_amt();
				String num = vo.getDeposit_receipt_num();
				String conversion_detail = vo.getConversion_detail();
				String def4 = vo.getDef4();
				String empname = vo.getEmpname();
				String orgname = vo.getOrgname();
				String ts = vo.getTs();
				
				contents = new String[]{customer_name, idcard, integral+"", account, amt + "", num, conversion_detail, def4, empname, orgname, ts};
				row = sheet.createRow((i+2));
				ExcelUtils.batchCreateCell(row, contents);
			}
		}
		
		
		response.setContentType("application/x-download");
		response.setCharacterEncoding("utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + 
						UserUtils.toUtf8String("客户积分明细表") + ".xls");
		try {
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 分页查询 积分信息
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public IntegralDetailVO[] queryCustIntegralDetail(Map<String,String> params,PageCond page){
		//判断是不是有参数
		String where = whereSql(params);
		
		//类型
		String type = params.get("integral_type");
		String tableName = "";
		if("1".equals(type)){
			tableName = "gd_add_integral_detail";
		}else if("2".equals(type)){
			tableName = "gd_sub_integral_detail";
		}else if("3".equals(type)){
			tableName = "gd_vip_integral_detail";
		}
		
		//查询数据实体
		VOPageQuery<IntegralDetailVO> query = new VOPageQuery<IntegralDetailVO>(IntegralDetailVO.class);
		//查询的总记录
		String queryCountSql = " SELECT COUNT(1) FROM " + tableName + " T" +
								"	LEFT JOIN ORG_EMPLOYEE E ON E.`USERID`=T.`CREATE_USER`" +
								" 	LEFT JOIN ORG_ORGANIZATION O ON O.`ORGID` = T.`CREATE_USER_ORG`" +
								" WHERE 1=1" + where;
	    //查询
		String querySql =" SELECT C.`CUSTOMER_NAME`, t.*," +
						"	E.`EMPNAME`, O.`ORGNAME` FROM " + tableName + " T" +
						
						"	LEFT JOIN ORG_EMPLOYEE E ON E.`USERID`=T.`CREATE_USER`" +
						" 	LEFT JOIN ORG_ORGANIZATION O ON O.`ORGID` = T.`CREATE_USER_ORG`" +
						" 	LEFT JOIN GD_CUSTOMER_INFO2 C ON T.`CUSTOMER_IDCARD` = C.`CUSTOMER_IDCARD` " +
						" WHERE 1=1" + where ;
		System.out.println("积分明细查询： " + querySql);
		 IntegralDetailVO[] vos = query.query(querySql, queryCountSql, page);
		 if(vos != null && vos.length > 0){
			 if("Y".equals(params.get("hiddenFlag"))){
				 for(IntegralDetailVO vo : vos){
					 vo.setCustomer_idcard(idcardToX(vo.getCustomer_idcard()));
				 }
			 }
			 
		 }
		 return vos;
	}
	//身份证号只显示出生的月日
	public static String idcardToX(String idcard){
		if(idcard == null){
			return "";
		}
		if("".equals(idcard)){
			return "";
		}
		try {
			idcard = idcard.substring(6, 14);
			return "**"+ idcard+ "**";
		} catch (Exception e) {
			return "******";
		}
	}
	
	public static String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		//类型
		String type = params.get("integral_type");
		if(StringUtils.isNotEmpty(type)){
			sb.append(" AND T.def4 ='"+type+"'");
		}
		String customer_idcard = params.get("customer_idcard");
		if(StringUtils.isNotEmpty(customer_idcard)){
			sb.append(" AND T.CUSTOMER_IDCARD = '"+customer_idcard+"'");
		}
		String start_date = params.get("start_date");
		if(StringUtils.isNotEmpty(start_date)){
			sb.append(" AND T.ts > '"+start_date+"'");
		}
		
		String end_date = params.get("end_date");
		if(StringUtils.isNotEmpty(end_date)){
			sb.append(" AND T.ts < '"+end_date+"'");
		}
		return sb.toString();
	}
	
	
	/**
	 * 修改客户积分
	 * 待定：需要添加账号校验：账号唯一，后台添加唯一索引
	 * 	Java添加唯一校验
	 * @param flag: 修改类型，0:添加积分，1：减少积分,
	 * 不用判断flag是否为空
	 * @param vo: 修改对象
	 * @return
	 * @throws DAOException 
	 */
	@Bizlet
	public MsgResponse modifyCustomerIntegral(String flag, IntegralDetailVO vo) throws DAOException{
		//IntegralDetailVO [pk_integral_detail=MA847931323338190848, customer_idcard=532123199206183313, customer_integral=233.0, customer_account=222222222222222, deposit_receipt_num=222, deposit_receipt_amt=2222.0, conversion_detail=null, input_date=null, option_org=null, def1=null, def2=null, def3=null, def4=null, customer_name=null]
				//根据主键查询得到客户
		/*
		 * 手工控制事务，失败就回滚
		 */
		MsgResponse msg = new MsgResponse();
		try {
			/*
			 * 需要添加账号校验：账号唯一和存单号
			 * 添加积分的时候
			 */
			/*if("0".equals(flag)){
				MsgResponse validateMsg = vaildateMsg(vo);
				if(validateMsg != null){
					return validateMsg;
				}
			}*/
			if(vo == null){
				return new MsgResponse("修改对象为空", false);
			}
			String pk = vo.getCustomer_idcard();
			if(pk == null || "".equals(pk)){
				return new MsgResponse("客户主键为空, 请在客户页面刷新查询并修改积分", false);
			}
			CustomerVO customerVO = new CustomerOptionBiz().queryCustByPK(pk);
			if("1".equals(flag.trim())){
				//积分减少，变为负数
				vo.setCustomer_integral(vo.getCustomer_integral()*(-1));
			}
			vo.setCustomer_idcard(customerVO.getCustomer_idcard());//
			if("0".equals(flag.trim())){
				String num = vo.getCustomer_account();
				if(num != null && !"".equals(num.trim())){
					if(!(num.length() == 16 || num.length() == 19)){
						//对的
						return new MsgResponse("账号填写错误，请重新填写", false);
					}
				}else{
					return new MsgResponse("账号为空，请重新填写", false);
				}
			}
			//不判断修改类型，直接保存VO
			vo.setPk_integral_detail(PrimaryKeyUtil.getPrimaryKey());
			vo.setCreatetime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss"));
			msg = new GdDataHandlerUtils<IntegralDetailVO>(new IntegralDetailVO()).save(vo);
			if(msg.isFlag()){
				//需要对客户的可用积分进行更新
				String idcard = vo.getCustomer_idcard();
				double integral = countCustomerIntegral(pk);//原积分
				//新积分
				integral = new BigDecimal(integral + vo.getCustomer_integral()).setScale(2, BigDecimal.ROUND_UP).doubleValue();
				String sql = "UPDATE GD_CUSTOMER_INFO C " +
						"	SET C.`NOW_USABLE_INTEGRAL` = ? WHERE C.`CUSTOMER_IDCARD`= ? ";
				SQLParameter parameter = new SQLParameter();
				parameter.addParam(integral);
				parameter.addParam(idcard);
				int result = new DBUtils().executeUpdateSQL(sql, parameter);
				if(result > 0){
					msg.setFlag(true);
					msg.setMessage("积分修改成功！");
					return msg;
				}
			}
			msg.setFlag(false);
			msg.setMessage("积分修改失败！");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setFlag(false);
			msg.setMessage("积分修改失败，" + e.getMessage());
			return msg;
		}
	}
	
	public MsgResponse vaildateMsg(IntegralDetailVO vo) {
		//客户账号必须要唯一
		String ca = vo.getCustomer_account();
		String sql = "SELECT COUNT(*) FROM GD_INTEGRAL_DETAIL2 WHERE CUSTOMER_ACCOUNT='"+ca+"'";
		DBUtils dbUtils = new DBUtils();
		try {
			if(!CUSTOMER_ACCOUNT.contains(ca)){
				int count = dbUtils.getCountNumber(sql);
				if(count >= 1){
					return new MsgResponse("添加积分失败，账号已经存在，如果是冲正，请联系管理员", false);
				}
			}
			String deposit_receipt_num2 = vo.getDeposit_receipt_num();
			if(!DEPOSIT_RECEIPT_NUM.contains(deposit_receipt_num2)){
				sql = "SELECT COUNT(*) FROM GD_INTEGRAL_DETAIL2 WHERE" +
						" DEPOSIT_RECEIPT_NUM='"+deposit_receipt_num2+"'";
				int count = dbUtils.getCountNumber(sql);
				if(count >= 1){
					return new MsgResponse("添加积分失败，存单号已经存在，如果是冲正，请联系管理员", false);
				}
			}
			return null;
		} catch (DAOException e) {
			e.printStackTrace();
			return new MsgResponse("添加积分失败，" + e.getMessage(), false);
		}
	}

	/**
	 * 根据身份证统计客户的总积分
	 * @param idcard
	 * @return
	 * @throws DAOException 
	 */
	@Bizlet
	public double countCustomerIntegral(String idcard){
		if(idcard == null){
			return 0;
		}
		if("".equals(idcard.trim())){
			return 0.0;
		}
		//根据主键查询
		String sql = "SELECT T.`NOW_USABLE_INTEGRAL` FROM GD_CUSTOMER_INFO2 T WHERE T.`PK_CUSTOMER_INFO` = '"+idcard+"'";
		String count;
		try {
			count = new DBUtils().getOneValue(sql);
			if("".equals(count)){
				return 0.0;
			}
			return Double.parseDouble(count);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return 0.0;
	}
	
}
