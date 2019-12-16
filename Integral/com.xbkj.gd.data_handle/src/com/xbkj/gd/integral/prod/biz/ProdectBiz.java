/**
 * 
 */
package com.xbkj.gd.integral.prod.biz;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.eos.system.logging.Logger;
import com.pub.xbkj.common.MsgResponse;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.gd.integral.prod.dao.ProductDao;
import com.xbkj.gd.integral.prod.vos.ProductVO;
import com.xbkj.gd.integral.vos.ComboboxVO;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.FileImportUtils;
import com.xbkj.gd.utils.GdDataHandlerUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-14
 *@version 1.0.0
 *@desc
 */
@Bizlet("")
public class ProdectBiz {
	private static final Logger log = LoggerFactory.getLogger(ProdectBiz.class);
	
	//产品操作dao
	private ProductDao dao = new ProductDao();
	
	
	/**
	 * 下拉框数据
	 * @param params
	 * @return
	 */
	@Bizlet
	public ComboboxVO[] queryApplyProd(Map<String, String> params){
		
		List<ProductVO> vos = dao.queryApplyProd();
		if(vos != null && vos.size() > 0){
			int len = vos.size();
			ComboboxVO[] coms = new ComboboxVO[len];
			ComboboxVO com = null;
			ProductVO prod = null;
			for(int i = 0; i < len; i++){
				prod = vos.get(i);
				com = new ComboboxVO();
				//code_名称_积分
				String data = prod.getProduct_name() + "_" + prod.getProduct_integral();
				com.setId(data);
				com.setText(data);
				coms[i] = com;
			}
			return coms;
		}
		return null;
	}
	
	@Bizlet
	public ProductVO queryProductByCode(String productCode){
	    //查询
		return dao.queryProductByCode(productCode);
	}
	
	
	/**
	 * 分页查询商品数据
	 * @param params
	 * @param page
	 * @return
	 */
	@Bizlet
	public ProductVO[] queryProductPage(Map<String,String> params,PageCond page){
		return dao.queryProductPage(params, page);
	}
	
	
	/**
	 * 导入商品数据
	 * @param request
	 * @return
	 */
	public String importProducts(HttpServletRequest request){
		//解析请求得到文件输入流
		InputStream is = null;
		try {
			is = FileImportUtils.getExcelIs(request);
			if(is == null){
				return "导入失败，文件为空";
			}
			//解析流数据
			XSSFWorkbook wb = new XSSFWorkbook(is);
			ProductVO[] vos = processData(wb);
			//写入数据库
			if(vos != null && vos.length > 0){
				MsgResponse msg = addProducts(vos);
				return msg.getMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return "导入商品信息失败，" + e.getMessage();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
		return "商品信息导入成功";
	}
	
	/**
	 * 添加商品
	 * @param vo
	 * @return
	 */
	@Bizlet
	public MsgResponse addProducts(ProductVO[] vos){
		try {
			if(vos == null || vos.length <= 0){
				return new MsgResponse("数据为空", false);
			}
			GdDataHandlerUtils<ProductVO> voUtils = new GdDataHandlerUtils<ProductVO>(new ProductVO());
			MsgResponse msg = null;
			int count = 0;
			int result = 0;
			for(ProductVO vo : vos){
				msg = vaildateVO(vo);
				if(msg != null){
					count++;
				}
				vo.setDr("0");
				vo.setPk_product(PrimaryKeyUtil.getPrimaryKey());
				msg = voUtils.save(vo);
				if(!msg.isFlag()){
					result++;
				}
				
			}
			if(result == vos.length){
				msg.setFlag(false);
				log.info("保存失败，请检查编码是否重复！");
				msg.setMessage("保存失败，请检查编码是否重复！");
				return msg;
			}
			if(result > 0 && result < vos.length){
				msg.setFlag(false);
				log.info("部分保存失败，请检查名称是否重复！");
				msg.setMessage("部分保存失败，请检查名称是否重复！");
				return msg;
			}
			msg = new MsgResponse(true);
			msg.setMessage("保存成功！");
			if(count > 0){
				msg.setMessage("部分保存成功，失败的请重新保存！");
			}
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return new MsgResponse("添加失败" + e.getMessage(), false);
		}
	}

	private MsgResponse vaildateVO(ProductVO vo) {
		/*if(StringUtils.isBlank(vo.getProduct_code())){
			return new MsgResponse("编号不能为空", false);
		}*/
		if(StringUtils.isBlank(vo.getProduct_name())){
			return new MsgResponse("名称不能为空", false);
		}
		if(StringUtils.isBlank(vo.getProduct_integral())){
			return new MsgResponse("积分单位不能为空", false);
		}
		/*if(vo.getProduct_num() <= 0){
			return new MsgResponse("数量必须大于0", false);
		}*/
		return null;
	}
	
	/**
	 * 修改商品信息
	 * @param vo
	 * @return
	 */
	@Bizlet
	public MsgResponse updateCustomerInfo(ProductVO vo){
		MsgResponse msg = vaildateVO(vo);
		if(msg != null){
			return msg;
		}
		
		SQLParameter parameter = new SQLParameter();
		
		//修改 手机号、姓名、推荐人手机号、修改时间、修改人
		parameter.addParam(vo.getProduct_code());
		parameter.addParam(vo.getProduct_name());
		parameter.addParam(vo.getProduct_num());
		parameter.addParam(vo.getPk_product());
		try {
			dao.updateProduct(parameter);
			return new MsgResponse("修改成功", true);
		} catch (DAOException e) {
			e.printStackTrace();
			log.error(e);
			return new MsgResponse("修改失败, " + e.getMessage(), false);
		}
	}
	/**
	 * 修改商品信息
	 * @param vo
	 * @return
	 */
	@Bizlet
	public MsgResponse stopProd(ProductVO[] vos){
		MsgResponse msg = new MsgResponse(false);
		if(vos == null || vos.length <= 0){
			msg.setMessage("请选择");
			return msg;
		}
		List<String> sqls = new ArrayList<String>();
		for(ProductVO vo : vos){
			sqls.add("UPDATE GD_PRODUCT SET dr='1' WHERE PK_PRODUCT='"+vo.getPk_product()+"'");
		}
		try {
			int count = new DBUtils().executeBatchUpdateSQL(sqls);
			log.info("stopProd " + count);
			msg.setFlag(true);
			msg.setMessage("停用成功");
			return msg;
		} catch (DAOException e) {
			log.error(e);
			msg.setMessage("停用失败" + e.getMessage());
			return msg;
		}
		
	}
	/**
	 * 解析商品导入excel
	 * @param wb
	 * @return
	 */
	private ProductVO[] processData(XSSFWorkbook wb) {
		//key为身份证
		Map<String, ProductVO> voMap = new HashMap<String, ProductVO>();
		//解析
		XSSFSheet sheet = wb.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();
		if(rows > 0){
			ProductVO vo = null;
			XSSFRow row = null;
			XSSFCell cell = null;
			for(int i = 2; i < rows; i++){
				vo = new ProductVO();
				row = sheet.getRow(i);
				if(row != null){
					//循环获取单元格的值
					cell = row.getCell(0);
					String code = cell.getStringCellValue();
					vo.setProduct_code(code);//编号
					cell = row.getCell(1);
					vo.setProduct_name(cell.getStringCellValue());//名称
					cell = row.getCell(2);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);//设置类型为数字
					vo.setProduct_num(Integer.parseInt(cell.getStringCellValue()));//数量
					cell = row.getCell(3);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);//设置类型字符串
					vo.setProduct_integral(cell.getStringCellValue());//积分单位 单个商品的积分
					
					cell = row.getCell(4);
					vo.setRemark(cell.getStringCellValue());//备注
					
					vo.setCreatetime(DateUtils.getFormatDate(DateUtils.PATTERN_19));
					vo.setDr("0");
					vo.setInput_org(UserUtils.getUserOrg());//创建机构
					if(vaildateVO(vo) == null){
						vo.setPk_product(PrimaryKeyUtil.getPrimaryKey());
						voMap.put(code, vo);
					}
				}
			}
		}
	
		Collection<ProductVO> vos = voMap.values();
		if(rows == (vos.size() + 2)){
			//编码没有重复的
			if(vos != null && vos.size() > 0){
				return vos.toArray(new ProductVO[vos.size()]);
			}
		}
		return null;
	}
}
