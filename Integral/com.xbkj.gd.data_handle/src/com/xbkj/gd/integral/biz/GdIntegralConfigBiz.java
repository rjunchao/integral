package com.xbkj.gd.integral.biz;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.eos.system.logging.Logger;
import com.pub.xbkj.common.MsgResponse;
import com.pub.xbkj.pubapp.pagequery.VOPageQuery;
import com.pub.xbkj.pubapp.query.VOQuery;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.util.MapUtil;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.gd.integral.biz.service.IntegralOpertionService;
import com.xbkj.gd.integral.vos.ComboboxVO;
import com.xbkj.gd.integral.vos.CustomerVO;
import com.xbkj.gd.integral.vos.IntegralConfigVO;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2018-9-4
 *@version 1.0.0
 *@desc 配置积分基础数据
 */
@Bizlet
public class GdIntegralConfigBiz {
	
	private static final Logger logger = LoggerFactory.getLogger(IntegralOpertionService.class);
	
	private GdDataHandlerUtils<IntegralConfigVO> voUtils = new GdDataHandlerUtils<IntegralConfigVO>(new IntegralConfigVO());
	
	/**
	 * 查询当前用户在这一年是否已经送了积分
	 * @param cust
	 * @return
	 */
	@Bizlet
	public int queryVipIntegralByYear(CustomerVO vo){
		String sql = "SELECT COUNT(*) FROM gd_integral_detail WHERE " +
				"def3= '"+DateUtils.getFormatDate(DateUtils.PATTERN_YEAR)+"' " +
				"AND def4=3 AND customer_idcard= '"+vo.getCustomer_idcard()+"'";
		try {
			int count = new DBUtils().getCountNumber(sql);
			return count;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 查询配置
	 * @param integral_type
	 * @return
	 */
	@Bizlet
	public ComboboxVO[] queryConfig(int integral_type){
		String sql = "SELECT integral_coefficient, integral_type_name FROM gd_integral_config WHERE dr = 0 AND integral_type=" + integral_type;
		VOQuery<IntegralConfigVO> voQuery = new VOQuery<IntegralConfigVO>(IntegralConfigVO.class);
		IntegralConfigVO[] vos = voQuery.query(sql);
		if(vos != null && vos.length > 0){
			int len = vos.length;
			ComboboxVO[] comVo = new ComboboxVO[len];
			ComboboxVO com = null;
			
			for(int i = 0; i < len; i++){
				com = new ComboboxVO();
				if(integral_type == 4){
					com.setId(vos[i].getIntegral_type_name());
				}else{
					com.setId(vos[i].getIntegral_coefficient()+"");
				}
				com.setText(vos[i].getIntegral_type_name());
				comVo[i] = com;
			}
			return comVo;
		}
		return null;
	}
	
	/**
	 * 分页查询
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public IntegralConfigVO[] queryCustomerPage(Map<String,String> params,PageCond page){
		//判断是不是有参数
 		String where = whereSql(params);
		//查询数据实体
		VOPageQuery<IntegralConfigVO> query = new VOPageQuery<IntegralConfigVO>(IntegralConfigVO.class);
		//查询的总记录
		String queryCountSql = "SELECT COUNT(*)" +
							"	FROM gd_integral_config T " +
							"	WHERE T.`DR` = 0 " + where;
	    //查询
		String querySql = "SELECT T.*" +
							"	FROM gd_integral_config T " +
							"	WHERE T.`DR` = 0 " + where;

		logger.info("query 积分配置 sql :" + querySql);
		IntegralConfigVO[] vos = query.query(querySql, queryCountSql, page);
		return vos;
	}
	
	private String whereSql(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		
		if(!MapUtil.isEmpty(params)){
			String integral_type = params.get("integral_type");
			sb.append(" AND T.integral_type = " + integral_type);//类型
			String config_name = params.get("config_name");
			if(StringUtils.isNotEmpty(config_name)){
				sb.append(" AND T.integral_type_name LIKE '%"+config_name+"%'");
			}
		}
		return sb.toString();
	}
	

	
	/**
	 * 删除配置
	 * @param vo
	 * @return
	 */
	@Bizlet
	public MsgResponse delConfig(IntegralConfigVO vo){
		String sql = "delete from gd_integral_config where pk_gd_integral_config = '"+vo.getPk_gd_integral_config()+"'";
		MsgResponse msg = voUtils.del(sql);
		return msg;
	}
	
	/**
	 * 修改配置数据
	 * @return
	 */
	@Bizlet
	public MsgResponse updateConfig(IntegralConfigVO vo){
		MsgResponse msg = vaildateVO(vo);
		if(msg != null){
			return msg;
		}
		StringBuilder sql = new StringBuilder("UPDATE gd_integral_config t ");
		 
		String integral_type = vo.getIntegral_type();
//		long integral = vo.getIntegral();//积分不能为空
		String integral_type_name = vo.getIntegral_type_name();//积分配置名称不能为空
		sql.append(" set ");
		////1：添加积分，2：减少积分, 3:vip赠送，4：资金来源
		sql.append("t.`integral_type_name`='"+integral_type_name+"'");//名称
		double integral_coefficient = vo.getIntegral_coefficient();
		/*if("1".equals(integral_type)){
			//添加积分
			if(integral_coefficient == 0){
				return new MsgResponse("积分比较大于0", false);
			}
			if(!StringUtils.isNotEmpty(integral_type_name)){
				return new MsgResponse("添加积分类型必须输入", false);
			}
			
		}else if("2".equals(integral_type) || "3".equals(integral_type)){
			//积分，积分兑换和vip赠送
			sql.append(", t.`integral_coefficient`=" + integral);
		}*/
		sql.append(", t.`integral_coefficient`=" + integral_coefficient);
		//WHERE T.integral_type=1 AND t.`pk_gd_integral_config` = '11' ";
		sql.append(" WHERE T.integral_type='"+integral_type+"'");//类型
		String pk = vo.getPk_gd_integral_config();
		sql.append(" AND t.`pk_gd_integral_config`='"+pk+"'");//主键
		try {
			int count = new DBUtils().executeUpdateSQL(sql.toString());
			if(count > 0){
				return new MsgResponse("更新成功", true);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			return new MsgResponse("更新失败，" + e.getMessage(), false);
		}
		return new MsgResponse("更新0行", false);
	}
	
	/**
	 * 添加积分
	 * @param vo
	 * @return
	 */
	@Bizlet
	public MsgResponse addConfig(IntegralConfigVO vo){
		MsgResponse msg = vaildateVO(vo);
		if(msg != null){
			return msg;
		}
		vo.setPk_gd_integral_config(PrimaryKeyUtil.getPrimaryKey());
		vo.setCreatetime(DateUtils.getFormatDate(DateUtils.PATTERN_19));
		vo.setDef1(UserUtils.getUserOrg());//建档机构
		String integral_type = vo.getIntegral_type();
		if("3".equals(integral_type)){
			vo.setIntegral_year(DateUtils.getFormatDate(DateUtils.PATTERN_YEAR));
		}
		vo.setDr("0");
		msg = voUtils.save(vo);
		return msg;
	}
	private MsgResponse vaildateVO(IntegralConfigVO vo) {
		String integral_type = vo.getIntegral_type();
//		long integral = vo.getIntegral();//积分不能为空
		String integral_type_name = vo.getIntegral_type_name();//积分配置名称不能为空
		if(!StringUtils.isNotEmpty(integral_type_name)){
			if("1".equals(integral_type)){
				return new MsgResponse("添加积分类型必须输入", false);
			}
			if("2".equals(integral_type)){
				return new MsgResponse("积分兑换礼品必须输入", false);
			}
			if("3".equals(integral_type)){
				return new MsgResponse("积分赠送条目", false);
			}
			if("4".equals(integral_type)){
				return new MsgResponse("资金来源不能为空", false);
			}
		}
		double integral_coefficient = vo.getIntegral_coefficient();
		if("1".equals(integral_type)){
			//添加积分
			if(integral_coefficient == 0){
				return new MsgResponse("积分计算系数不能为0", false);
			}
		}
		if("2".equals(integral_type) || "3".equals(integral_type)){
			//积分兑换
			if(integral_coefficient <= 0){
				return new MsgResponse("积分必须大于0", false);
			}
			
		}
		return null;
	}
	
}
