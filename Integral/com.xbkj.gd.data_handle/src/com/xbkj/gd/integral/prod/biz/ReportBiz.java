package com.xbkj.gd.integral.prod.biz;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.xbkj.gd.integral.prod.dao.ReportDao;
import com.xbkj.gd.integral.prod.vos.ReportProductVO;
import com.xbkj.gd.utils.DateUtils;
import com.xbkj.gd.utils.ExcelUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-11-9
 *@version 1.0.0
 *@desc
 */
@Bizlet
public class ReportBiz {

	@Bizlet
	public ReportProductVO[] countApplyProduct(Map<String, String> params, PageCond page){
		return new ReportDao().countApplyProduct(params, page, true);
	}
	
	@Bizlet("")
	public void export(HttpServletRequest request, HttpServletResponse response){
		
		
		Map<String, String> parameter = new HashMap<String, String>();
		String type = request.getParameter("counttype");
		parameter.put("counttype", type);
		parameter.put("start_date", request.getParameter("start_date"));
		parameter.put("end_date", request.getParameter("end_date"));
		parameter.put("product_name", request.getParameter("product_name"));
		parameter.put("now_user", request.getParameter("now_user"));
		parameter.put("apply_org", request.getParameter("apply_org"));
		
		ReportProductVO[] vos = new ReportDao().countApplyProduct(parameter , null, false);
		String[] heads = null;
		/*
		 * counttype
		 * 	1：按分理处统计 {""}
		 * 	2：按机构分理处统计 {""}
		 * 	3：按机构统计 {""}
		 */
		String excelTitle ="";
		if("1".equals(type)){
			heads = new String[]{"机构", "分理处", "礼品", "申请礼品总数", "调换总数", "兑换总数", "剩余总数"};
			excelTitle = "分理处礼品统计";
		}else if("2".equals(type)){
			heads = new String[]{"机构", "分理处", "礼品", "申请礼品总数", "调换总数", "兑换总数", "剩余总数"};
			excelTitle = "机构分理处礼品统计";
		}else if("3".equals(type)){
			heads = new String[]{"机构", "礼品", "申请礼品总数", "兑换总数", "剩余总数"};
			excelTitle = "机构礼品统计";
		}
		Workbook wb = ExcelUtils.buildWorkBook("xlsx", "积分报表信息", heads);
		Sheet sheet = wb.getSheetAt(0);
		ExcelUtils.setCellWidth(sheet, heads.length, 50);
		if(vos != null && vos.length > 0) {
			String[] contents = null;
			int size = vos.length;
			ReportProductVO vo = null;
			Row row = null;
			for(int i = 0; i < size; i++) {
				vo = vos[i];
				row = sheet.createRow(i+2);
				if("1".equals(type) || "2".equals(type)){
					heads = new String[]{"机构", "分理处", "礼品", "申请礼品总数", "调换总数", "兑换总数", "剩余总数"};
					contents = new String[] {
							vo.getOrgname(),
							vo.getApply_user(),
							vo.getApply_product_name(),
							String.valueOf(vo.getApply_product_num()),
							String.valueOf(vo.getAllot_product_num()),
							String.valueOf(vo.getOrg_sub_num()),
							String.valueOf(vo.getResidue_class())
					};
				}else if("3".equals(type)){
					heads = new String[]{"机构", "礼品", "申请礼品总数", "兑换总数", "剩余总数"};
					contents = new String[] {
							vo.getOrgname(),
							vo.getApply_product_name(),
							String.valueOf(vo.getApply_product_num()),
							String.valueOf(vo.getOrg_sub_num()),
							String.valueOf(vo.getResidue_class()),
					};
				}
			
				ExcelUtils.batchCreateCell(row, contents);
			}
		}
		try {
			response.setContentType("application/x-download");
			response.setCharacterEncoding("utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + 
					UserUtils.toUtf8String(excelTitle + DateUtils.getFormatDate("yyyyMMddHHmmss")) + ".xlsx");
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
