<%@page import="com.xbkj.gd.integral.biz.IntegralOptionBiz"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<html>
<!-- 
  - Author(s): rjc
  - Date: 2018-09-11 19:54:09
  - Description:
-->
<head>
<title>积分明细历史数据导入</title>
</head>
<body>
	<%
		String result = new IntegralOptionBiz().importHistoryIntegralDetail(request, 1);
		response.getWriter().write(result);//返回消息
	%>
</body>
</html>