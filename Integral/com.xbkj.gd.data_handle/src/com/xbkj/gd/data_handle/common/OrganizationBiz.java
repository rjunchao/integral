/**
 * 
 */
package com.xbkj.gd.data_handle.common;


import java.util.List;
import java.util.Map;

import com.eos.system.annotation.Bizlet;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.gd.utils.DBUtils;
import com.xbkj.gd.utils.UserUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-27
 *@version 1.0.0
 *@desc
 */
@Bizlet("")
public class OrganizationBiz {

	/**
	 * 查询当前用户的机构和所属机构
	 * @return
	 */
	@Bizlet("")
	public CommonTreeVO[] findOrgs(Map<String, String> params){
		List<String> ids = UserUtils.findDeptAndChildrenDept();
		/*String sql = "SELECT ORGID, ORGCODE, ORGNAME,  ORGLEVEL, ORGDEGREE, ORGSEQ, " +
				"ORGTYPE, ORGADDR, ZIPCODE, MANAPOSITION, MANAGERID, ORGMANAGER, LINKMAN, " +
				"LINKTEL, EMAIL, WEBURL, STARTDATE, ENDDATE, STATUS, AREA, CREATETIME, LASTUPDATE, " +
				"UPDATOR, SORTNO, ISLEAF, SUBCOUNT, " +
				"REMARK, TENANT_ID, APP_ID, PARENTORGID FROM org_organization";*/
		String sql = "SELECT ORGID as id, ORGNAME as text, PARENTORGID as parentId FROM org_organization";
		if(ids != null && ids.size() > 0){
			StringBuilder sb = new StringBuilder();
			sb.append(" WHERE ORGID IN (");
			for(String id : ids){
				sb.append(id +",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			sql = sql + sb.toString();
		}
		
		
		DBUtils utils = new DBUtils();
		try {
			List<Object[]> objs = utils.queryObjs(sql);
			if(objs != null && objs.size() > 0){
				int len = objs.size();
				CommonTreeVO[] vos = new CommonTreeVO[len];
				CommonTreeVO vo = null;
				Object[] obj  = null;
				for(int i = 0; i < len; i++){
					vo = new CommonTreeVO();
					obj = objs.get(i);
					vo.setId(obj[0].toString());
					vo.setText(obj[1].toString());
					if(obj[2] != null){
						vo.setParentId(obj[2].toString());
					}
					vos[i] = vo;
				}
				return vos;
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
