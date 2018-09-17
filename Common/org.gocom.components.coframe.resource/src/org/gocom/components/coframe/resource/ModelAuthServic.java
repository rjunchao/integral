
package org.gocom.components.coframe.resource;

import java.util.ArrayList;
import java.util.List;

import org.gocom.components.coframe.tools.IConstants;

import com.eos.foundation.database.DatabaseUtil;
import com.primeton.cap.GlobalSettings;
import com.primeton.cap.TenantManager;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
import commonj.sdo.DataObject;

/**
 * 
 * 模块授权服务类
 *
 * @author caozw (mailto:caozw@primeton.com)
 */
public class ModelAuthServic {
	private static final String MODULE_TYPE = "module";

	private static final String HAS_AUTH = "hasAuth";

	private static final String NULL_AUTH = "0";

	public static DataObject[] getModulesFromDb(String dsName, DataObject dataObject, String isAuth, String currentRoleID) {
		List<DataObject> returnList = new ArrayList<DataObject>();
		DataObject[] modules = DatabaseUtil.queryEntitiesByCriteriaEntity(dsName, dataObject);
		// 获得默认的模块权限
		String defaultAuth;
		if (GlobalSettings.INSTANCE.getValue(GlobalSettings.KEY_AUTH_DEFAULTMODEL) == null)
			defaultAuth = "1";
		else if (GlobalSettings.INSTANCE.getValue(GlobalSettings.KEY_AUTH_DEFAULTMODEL).equals("true"))
			defaultAuth = "1";
		else
			defaultAuth = "0";

		if (modules != null) {
			for (DataObject module : modules) {
				if (true) {
					String state = AuthRuntimeManager.getInstance().getAuthResourceState(
							new Party(IConstants.ROLE_PARTY_TYPE_ID, currentRoleID, null, null, TenantManager.getCurrentTenantID()),
							module.getString("moduleID"), MODULE_TYPE);
					// 如果states中包含1，则有权限
					if ("1".equals(isAuth)) {
						if ((state == null && "1".equals(defaultAuth)) || "1".equals(state)) {
							module.setString(HAS_AUTH, isAuth);
							returnList.add(module);
						}

					} else if ("0".equals(isAuth)) {
						if ((state == null && "0".equals(defaultAuth)) || "0".equals(state)) {
							module.setString(HAS_AUTH, isAuth);
							returnList.add(module);
						}
					} else {
						if (state == null) {
							module.setString(HAS_AUTH, defaultAuth);
						} else if ("1".equals(state)) {
							module.setString(HAS_AUTH, state);
						} else {
							module.setString(HAS_AUTH, NULL_AUTH);
						}
						returnList.add(module);
					}
				}
			}
		}
		return returnList.toArray(new DataObject[returnList.size()]);
	}

}
