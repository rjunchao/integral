<%@page import="com.xbkj.gd.integral.prod.biz.ReportBiz"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<html>
<!-- 
  - Author(s): rjc
  - Date: 2020-05-10 16:07:18
  - Description:
-->
<head>
<title>礼品统计导出</title>
</head>
<body>
	<%
		out.clear();
		out = pageContext.pushBody();
		new ReportBiz().export(request, response);
		response.flushBuffer();
	 %>
</body>
</html>