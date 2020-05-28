<%@page import="com.xbkj.gd.integral.prod.biz.OrgApplyProductBiz"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<html>
<!-- 
  - Author(s): rjc
  - Date: 2020-05-10 17:05:22
  - Description:
-->
<head>
<title>导出采购清单</title>
</head>
<body>
	<%
		out.clear();
		out = pageContext.pushBody();
		new OrgApplyProductBiz().export(request, response);
		response.flushBuffer();
	 %>
</body>
</html>