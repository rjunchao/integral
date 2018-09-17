<%@page import="com.xbkj.gd.integral.biz.IntegralOptionBiz"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<html>
<!-- 
  - Author(s): rjc
  - Date: 2018-09-08 21:30:54
  - Description:
-->
<head>
<title>导出积分明细</title>
</head>
<body>
	<%
			out.clear();
			out = pageContext.pushBody();
			new IntegralOptionBiz().exportIntegralDetail(request, response);
			response.flushBuffer();
	 %>
</body>
</html>