package org.gocom.components.coframe.flowconfig.user;

import java.util.List;

import com.eos.data.datacontext.IUserObject;
import com.eos.system.log.LogFactory;
import com.eos.system.logging.Logger;
import com.eos.workflow.api.BPSServiceClientFactory;
import com.eos.workflow.api.IBPSServiceClient;
import com.eos.workflow.api.IWFWorkItemManager;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.primeton.cap.spi.auth.rule.AbstractRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleContext;

/**
 * 流程发起人自身条件匹配类
 * 
 * @author wangwx (mailto:wangwx@primeton.com)
 */

public class SelfUserCondition extends AbstractRuleCondition {
	// BPS客户端
	public static IBPSServiceClient client = null;

	// 工作项管理
	public static IWFWorkItemManager workItemMgr = null;

	// 日志
	private static Logger logger = LogFactory.getLogger(SelfUserCondition.class);

	private static final long serialVersionUID = -6937223566222057763L;

	/**
	 * 构造方法
	 * 
	 */
	public SelfUserCondition() {
		try {
			client = BPSServiceClientFactory.getDefaultClient();
			workItemMgr = client.getWorkItemManager();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 检查当前用户自身与右侧是否匹配
	 * 
	 * @param context 规则上下文
	 * 
	 */
	public boolean isMatch(IRuleContext context) {
		if (context instanceof WorkflowRuleContext) {
			WorkflowRuleContext flowContext = (WorkflowRuleContext) context;
			long workItemId = flowContext.getWfWorkItemId();
			if (workItemId == 0) {
				return true;
			}

			try {
				WFWorkItem workItem = workItemMgr.queryWorkItemDetail(workItemId);
				List<WFParticipant> participants = workItem.getParticipants();
				IUserObject uObject = flowContext.getMuoContext().getUserObject();
				for (WFParticipant par : participants) {
					if (par.getId().equals(uObject.getUserId())) {
						return true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return true;
	}

}
