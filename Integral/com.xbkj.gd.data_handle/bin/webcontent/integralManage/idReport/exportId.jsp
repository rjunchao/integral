<%@page import="com.xbkj.gd.integral.report.IntegralDetailReportController"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<html>
<!-- 
  - Author(s): rjc
  - Date: 2020-05-10 11:07:29
  - Description:
-->
<head>
<title>导出页面</title>
</head>
<body>
	<%
		out.clear();
		out = pageContext.pushBody();
		new IntegralDetailReportController().export(request, response);
		response.flushBuffer();
	 %>
</body>
</html>