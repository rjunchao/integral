package org.gocom.components.coframe.flowconfig.user;

import java.util.ArrayList;
import java.util.List;

import org.gocom.components.coframe.tools.IConstants;

import com.eos.data.datacontext.IUserObject;
import com.eos.system.log.LogFactory;
import com.eos.system.logging.Logger;
import com.eos.system.utility.StringUtil;
import com.eos.workflow.api.BPSServiceClientFactory;
import com.eos.workflow.api.IBPSServiceClient;
import com.eos.workflow.omservice.IWFOMService;
import com.eos.workflow.omservice.WFParticipant;
import com.primeton.cap.spi.auth.rule.AbstractRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleContext;
import com.primeton.cap.spi.auth.rule.RuleConditionDefinition;
import com.primeton.cap.spi.auth.rule.RuleConditionDefinition.Property;

/**
 * 指定用户条件匹配类
 * 
 * @author wangwx (mailto:wangwx@primeton.com)
 */

public class GivenUserConditon extends AbstractRuleCondition {
	
	// BPS客户端
	public static IBPSServiceClient client = null;

	// 日志
	private static Logger logger = LogFactory.getLogger(GivenUserConditon.class);

	private static final long serialVersionUID = -6937223566864051163L;

	/**
	 * 构造方法
	 * 
	 */
	public GivenUserConditon() {
		try {
			client = BPSServiceClientFactory.getDefaultClient();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 获取当前用户的所有上级参与者并与右值比较其是否存在
	 * 
	 * @param context 规则上下文
	 */
	public boolean isMatch(IRuleContext context) {
		// 先获取当前用户的所有上级参与者（包括自己），然后和设置的右值集合进行比较
		if (context instanceof WorkflowRuleContext) {
			WorkflowRuleContext flowContext = (WorkflowRuleContext) context;
			RuleConditionDefinition ruleDef = this.getDefinition();
			List<Property> extensions = ruleDef.getAttachment();
			IUserObject uObject = flowContext.getMuoContext().getUserObject();
			String empId = uObject.getUserId();
			if (StringUtil.isNotNullAndBlank(empId)) {
				IWFOMService service = client.getOMService();
				List<WFParticipant> participantList = service.getAllParentParticipants(IConstants.EMP_PARTY_TYPE_ID, empId);
				if (participantList == null) {
					participantList = new ArrayList<WFParticipant>();
				}
				// 将自身给加进去
				participantList.add(service.findParticipantByID(IConstants.EMP_PARTY_TYPE_ID, empId));
				List<Property> propertyList = new ArrayList<Property>();
				for (WFParticipant participant : participantList) {
					// 前端界面上的存储方式（key=participantId，value=participantCode+"."+participantName）
					propertyList.add(new Property(participant.getId(), participant.getTypeCode() + "." + participant.getName()));
				}
				for (Property property : propertyList) {
					if (extensions.contains(property)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}