package com.xbkj.gd.integral.prod.biz;

import java.util.Map;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.xbkj.gd.integral.prod.dao.ReportDao;
import com.xbkj.gd.integral.prod.vos.ReportProductVO;

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
		return new ReportDao().countApplyProduct(params, page);
	}
}
