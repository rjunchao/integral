package org.gocom.components.coframe.flowconfig.user;

import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.tools.IConstants;

import com.eos.data.datacontext.IUserObject;
import com.eos.system.log.LogFactory;
import com.eos.system.logging.Logger;
import com.eos.workflow.api.BPSServiceClientFactory;
import com.eos.workflow.api.IBPSServiceClient;
import com.eos.workflow.api.IWFProcessInstManager;
import com.eos.workflow.data.WFProcessInst;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;
import com.primeton.cap.spi.auth.rule.AbstractRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleContext;
import com.primeton.workflow.api.WFServiceException;

/**
 * 流程发起人所在部门条件匹配类
 * 
 * @author wangwx (mailto:wangwx@primeton.com)
 */

public class SelfOrgCondition extends AbstractRuleCondition {
	// BPS客户端
	public static IBPSServiceClient client = null;

	// 日志
	private static Logger logger = LogFactory.getLogger(SelfOrgCondition.class);

	private static final long serialVersionUID = -6937223323864057763L;

	/**
	 * 构造方法
	 * 
	 */
	public SelfOrgCondition() {
		try {
			client = BPSServiceClientFactory.getDefaultClient();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 判断当前的登录者是否属于流程发起人所在部门
	 * 
	 * @param context 当前流程上下文
	 */
	public boolean isMatch(IRuleContext context) {
		// 判断当前的登录者是否属于流程发起人所在部门
		if (context instanceof WorkflowRuleContext) {
			WorkflowRuleContext flowContext = (WorkflowRuleContext) context;
			long workItemId = flowContext.getWfWorkItemId();
			if (workItemId == 0) {// 代表是人工开始
				return false;
			}
			long processInstId = flowContext.getWfProcInstId();
			IWFProcessInstManager processInstanceManager = client.getProcessInstManager();
			WFProcessInst processInst = null;
			try {
				processInst = processInstanceManager.queryProcessInstDetail(processInstId);
			} catch (WFServiceException e) {
				logger.error(e);
				e.printStackTrace();
			}
			String creator = processInst.getCreator();
			IUserObject uObject = flowContext.getMuoContext().getUserObject();
			String userId = uObject.getUserId();
			// 获取所在的直属部门，这里工作流接口中没有只查找一级的方法，所以使用新party接口
			// IWFOMService omService = client.getOMService();
			Map<String, List<Party>> parentPartyMap = PartyRuntimeManager.getInstance().getDirectAssociateParentPartyList(creator,
					IConstants.EMP_PARTY_TYPE_ID, new String[] {
						IConstants.ORG_PARTY_TYPE_ID
					});
			List<Party> parentPartyList = parentPartyMap.get(IConstants.ORG_PARTY_TYPE_ID);
			for (Party party : parentPartyList) {
				Map<String, List<Party>> childPartyMap = PartyRuntimeManager.getInstance().getDirectAssociateChildPartyList(party.getId(),
						IConstants.ORG_PARTY_TYPE_ID, new String[] {
							IConstants.EMP_PARTY_TYPE_ID
						});
				List<Party> childPartyList = childPartyMap.get(IConstants.EMP_PARTY_TYPE_ID);
				for (Party childParty : childPartyList) {
					if (childParty.getId().equals(userId)) {
						return true;
					}
				}
			}

		}
		return false;
	}

}
