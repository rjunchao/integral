<%@page import="com.xbkj.gd.integral.biz.CustomerOptionBiz"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<html>
<!-- 
  - Author(s): rjc
  - Date: 2017-09-10 11:41:18
  - Description:
-->
	<head>
		<title>下载客户信息</title>
	</head>
	<body>
		<%
			out.clear();
			out = pageContext.pushBody();
			new CustomerOptionBiz().exportCustomerInfo(request, response);
			response.flushBuffer();
				
		 %>
	</body>
</html>