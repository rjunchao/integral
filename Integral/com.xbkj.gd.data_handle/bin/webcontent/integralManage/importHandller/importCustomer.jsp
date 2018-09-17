<%@page import="com.xbkj.gd.integral.biz.CustomerOptionBiz"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<html>
<!-- 
  - Author(s): rjc
  - Date: 2018-09-10 20:29:52
  - Description:
-->
<head>
<title>客户信息导入</title>
</head>
<body>
	<%
		String result = new CustomerOptionBiz().importCustomer(request);
		response.getWriter().write(result);//返回消息
	%>
</body>
</html>