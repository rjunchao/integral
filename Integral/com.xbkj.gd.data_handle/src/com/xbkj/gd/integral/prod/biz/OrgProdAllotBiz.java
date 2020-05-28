/**
 * 
 */
package com.xbkj.gd.integral.prod.biz;


import java.util.Map;

import com.eos.foundation.PageCond;
import com.eos.system.annotation.Bizlet;
import com.xbkj.gd.integral.prod.dao.OrgProdAllotDao;
import com.xbkj.gd.integral.prod.vos.OrgProdAllotVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2020-5-13
 *@version 1.0.0
 *@desc 分理处礼品提取明细
 */

@Bizlet("")
public class OrgProdAllotBiz {
	
	@Bizlet("")
	public OrgProdAllotVO[] page(Map<String, String> params, PageCond page){
		OrgProdAllotDao dao = new OrgProdAllotDao();
		return dao.page(params, page, true);
	}
}
