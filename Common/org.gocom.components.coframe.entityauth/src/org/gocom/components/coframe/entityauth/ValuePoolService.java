/*
 * Copyright 2013 Primeton.
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
package org.gocom.components.coframe.entityauth;
import java.util.ArrayList;
import java.util.List;

import org.gocom.components.coframe.entityauth.pojo.ConValue;
import org.gocom.components.coframe.entityauth.pojo.RightValue;

/**
 * 条件常量池服务
 * @author lijt (mailto:lijt@primeton.com)
 */
public class ValuePoolService {
	
	/**
	 * 所有条件
	 */
	private static List<ConValue> conValues = new ArrayList<ConValue>();
	/**
	 * 数字条件
	 */
	private static List<ConValue> dataTypeConValues = new ArrayList<ConValue>();
	/**
	 * 字符串条件
	 */
	private static List<ConValue> stringTypeConValues = new ArrayList<ConValue>();
	/**
	 * 日期条件
	 */
	private static List<ConValue> dateTypeConValues = new ArrayList<ConValue>();
	
	/**
	 * 可选范围值
	 */
	private static List<RightValue> rightValues = new ArrayList<RightValue>();
	
	/**
	 * 字符串值
	 */
	private static List<RightValue> stringTypeRightValues = new ArrayList<RightValue>();
	
	/**
	 * 数字值
	 */
	private static List<RightValue> dataTypeRightValues = new ArrayList<RightValue>();
	
	/**
	 * 日期值
	 */
	private static List<RightValue> dateTypeRightValues = new ArrayList<RightValue>();
	
	static {
		ConValue conValue1 = new ConValue();
		conValue1.setId("eq");
		conValue1.setName("=");
		conValue1.setValue("=");
		
		ConValue conValue2 = new ConValue();
		conValue2.setId("lt");
		conValue2.setName("<");
		conValue2.setValue("<");
		
		ConValue conValue3 = new ConValue();
		conValue3.setId("lq");
		conValue3.setName("<=");
		conValue3.setValue("<=");
		
		ConValue conValue4 = new ConValue();
		conValue4.setId("gt");
		conValue4.setName(">");
		conValue4.setValue(">");
		
		ConValue conValue5 = new ConValue();
		conValue5.setId("gq");
		conValue5.setName(">=");
		conValue5.setValue(">=");
		
		ConValue conValue6 = new ConValue();
		conValue6.setId("nq");
		conValue6.setName("<>");
		conValue6.setValue("<>");
		
		ConValue conValue7 = new ConValue();
		conValue7.setId("in");
		conValue7.setName("in");
		conValue7.setValue("in");
		
		ConValue conValue77 = new ConValue();
		conValue77.setId("notin");
		conValue77.setName("notin");
		conValue77.setValue("notin");
		
		ConValue conValue8 = new ConValue();
		conValue8.setId("null");
		conValue8.setName("null");
		conValue8.setValue("null");
		
		ConValue conValue9 = new ConValue();
		conValue9.setId("notnull");
		conValue9.setName("notnull");
		conValue9.setValue("notnull");
		
		ConValue conValue10 = new ConValue();
		conValue10.setId("like_end");
		conValue10.setName("like 后匹配");
		conValue10.setValue("like_end");
		
		ConValue conValue11 = new ConValue();
		conValue11.setId("like_start");
		conValue11.setName("like 前匹配");
		conValue11.setValue("like_start");
		
		ConValue conValue12 = new ConValue();
		conValue12.setId("like_none");
		conValue12.setName("like 不匹配");
		conValue12.setValue("like_none");
		
		ConValue conValue13 = new ConValue();
		conValue13.setId("like_all");
		conValue13.setName("like 中间匹配");
		conValue13.setValue("like_all");
		
		conValues.add(conValue1);
		conValues.add(conValue2);
		conValues.add(conValue3);
		conValues.add(conValue4);
		conValues.add(conValue5);
		conValues.add(conValue6);
		conValues.add(conValue7);
		conValues.add(conValue77);
		conValues.add(conValue8);
		conValues.add(conValue9);
		conValues.add(conValue10);
		conValues.add(conValue11);
		conValues.add(conValue12);
		conValues.add(conValue13);
		
		dataTypeConValues.add(conValue1);
		dataTypeConValues.add(conValue2);
		dataTypeConValues.add(conValue3);
		dataTypeConValues.add(conValue4);
		dataTypeConValues.add(conValue5);
		dataTypeConValues.add(conValue6);
		dataTypeConValues.add(conValue7);
		dataTypeConValues.add(conValue77);
		dataTypeConValues.add(conValue8);
		dataTypeConValues.add(conValue9);
		
		stringTypeConValues.add(conValue1);
		stringTypeConValues.add(conValue6);
		stringTypeConValues.add(conValue7);
		stringTypeConValues.add(conValue77);
		stringTypeConValues.add(conValue8);
		stringTypeConValues.add(conValue9);
		stringTypeConValues.add(conValue10);
		stringTypeConValues.add(conValue11);
		stringTypeConValues.add(conValue12);
		stringTypeConValues.add(conValue13);
		
		dateTypeConValues.add(conValue1);
		dateTypeConValues.add(conValue2);
		dateTypeConValues.add(conValue3);
		dateTypeConValues.add(conValue4);
		dateTypeConValues.add(conValue5);
		dateTypeConValues.add(conValue6);
		dateTypeConValues.add(conValue7);
		dateTypeConValues.add(conValue77);
		dateTypeConValues.add(conValue8);
		dateTypeConValues.add(conValue9);
		
		RightValue rightValue1 = new RightValue();
		rightValue1.setId("com.primeton.cap.defaultVaule.userName");
		rightValue1.setName("当前用户名");
		rightValue1.setExpression("${sessionScope.userObject.userName}");
		
		RightValue rightValue2 = new RightValue();
		rightValue2.setId("com.primeton.cap.defaultVaule.empId");
		rightValue2.setName("当前人员ID");
		rightValue2.setExpression("${sessionScope.userObject.userId}");
		
		RightValue rightValue3 = new RightValue();
		rightValue3.setId("com.primeton.cap.defaultVaule.userId");
		rightValue3.setName("当前用户ID");
		rightValue3.setExpression("${sessionScope.userObject.attributes.EXTEND_USER_ID}");
		
		RightValue rightValue4 = new RightValue();
		rightValue4.setId("com.primeton.cap.defaultVaule.userMail");
		rightValue4.setName("当前用户邮箱");
		rightValue4.setExpression("${sessionScope.userObject.userMail}");
		
		RightValue rightValue5 = new RightValue();
		rightValue5.setId("com.primeton.cap.defaultVaule.uniqueId");
		rightValue5.setName("当前用户对象的唯一标识");
		rightValue5.setExpression("${sessionScope.userObject.uniqueId}");
		
		RightValue rightValue6 = new RightValue();
		rightValue6.setId("com.primeton.cap.defaultVaule.userOrgId");
		rightValue6.setName("当前用户组织ID");
		rightValue6.setExpression("${sessionScope.userObject.userOrgId}");
		
		RightValue rightValue7 = new RightValue();
		rightValue7.setId("com.primeton.cap.defaultVaule.userOrgName");
		rightValue7.setName("当前用户组织名");
		rightValue7.setExpression("${sessionScope.userObject.userOrgName}");
		
		RightValue rightValue8 = new RightValue();
		rightValue8.setId("com.primeton.cap.defaultVaule.userRealName");
		rightValue8.setName("当前用户真正的名字");
		rightValue8.setExpression("${sessionScope.userObject.userRealName}");
		
		RightValue rightValue9 = new RightValue();
		rightValue9.setId("com.primeton.cap.defaultVaule.userRemoteIP");
		rightValue9.setName("当前用户的IP地址");
		rightValue9.setExpression("${sessionScope.userObject.userRemoteIP}");
		
		stringTypeRightValues.add(rightValue1);
		stringTypeRightValues.add(rightValue3);
		stringTypeRightValues.add(rightValue4);
		stringTypeRightValues.add(rightValue5);
		stringTypeRightValues.add(rightValue7);
		stringTypeRightValues.add(rightValue8);
		stringTypeRightValues.add(rightValue9);
		
		dataTypeRightValues.add(rightValue2);
		dataTypeRightValues.add(rightValue6);
		
		rightValues.add(rightValue1);
		rightValues.add(rightValue2);
		rightValues.add(rightValue3);
		rightValues.add(rightValue4);
		rightValues.add(rightValue5);
		rightValues.add(rightValue6);
		rightValues.add(rightValue7);
		rightValues.add(rightValue8);
		rightValues.add(rightValue9);
	}
	
	/**
	 * 获取所有条件判断列表
	 * @param type "String" "Data" "Date"
	 * @return List<ConValue>
	 */
	public static List<ConValue> getConValues(String type) {
		if("String".equals(type)) {
			return stringTypeConValues;
		} else if("Data".equals(type)){
			return dataTypeConValues;
		} else if("Date".equals(type)){
			return dateTypeConValues;
		}
		return conValues;
	}
	
	
	/**
	 * 获取可选值列表
	 * @return List<RightValue>
	 */
	public static List<RightValue> getRightValues(String type) {
		if("String".equals(type)) {
			return stringTypeRightValues;
		} else if("Data".equals(type)){
			return dataTypeRightValues;
		} else if("Date".equals(type)){
			return dateTypeRightValues;
		}
		return rightValues;
	}
	
	/**
	 * 判断是可选值还是输入值
	 * @param rightId 右值
	 * @return boolean
	 */
	public static boolean iExistRightValue(String rightId) {
		for( RightValue rightValue : rightValues) {
			if(rightId != null && rightId.equals(rightValue.getId())) {
				return true;
			}
		}
		return false;
	}
	
}
