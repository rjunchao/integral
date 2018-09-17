/*
 * Copyright 2013 Primeton Technologies Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gocom.components.coframe.flowconfig;

import java.util.List;

import com.primeton.cap.auth.extension.flow.AuthTabPage;
import commonj.sdo.DataObject;

/**
 * <pre>
 * Title: 流程配置的统一对外接口
 * Description: 程序功能的描述
 * </pre>
 * @author wanghl
 * @version 1.00.00
 * 
 */
public class ProcessConfigServiceBean {

	/**
	 * 
	 */
	public ProcessConfigServiceBean() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取授权Tab页
	 * @return Tab页列表
	 */
	public static List<AuthTabPage> getAuthTabPages(){
		return ProcessConfigService.getAuthTabPages();
	}
	
	/**
	 * 解析环节
	 * 
	 * @param proc 流程实体信息
	 */
	public static void parseForm(DataObject proc) {
		ProcessConfigService.parseForm(proc);
	}
	
	/**
	 * 是否子流程
	 * 
	 * @param proc 流程实体信息
	 * @return 1:是 0：否
	 */
	public static DataObject isSubForm(DataObject proc) {
		return ProcessConfigService.isSubForm(proc);
	}
	
	/**
	 * 获取随机的ruleId
	 * @return
	 */
	public static String getRandomRuleId() {
		return ProcessConfigService.getRandomRuleId();
	}

}
