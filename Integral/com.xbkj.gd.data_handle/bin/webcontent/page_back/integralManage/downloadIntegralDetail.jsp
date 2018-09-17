<%@page import="com.xbkj.gd.integral.biz.IntegralOptionBiz"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<html>
<!-- 
  - Author(s): rjc
  - Date: 2017-09-10 11:42:12
  - Description:
-->
	<head>
		<title>客户积分明细</title>
	</head>
	<body>
		<%
			out.clear();
			out = pageContext.pushBody();
			new IntegralOptionBiz().exportIntegral(request, response);
			response.flushBuffer();
				
		 %>
	</body>
</html>