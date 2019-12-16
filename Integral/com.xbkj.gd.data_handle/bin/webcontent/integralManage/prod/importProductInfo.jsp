<%@page import="com.xbkj.gd.integral.prod.biz.ProdectBiz"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<html>
<!-- 
  - Author(s): rjc
  - Date: 2019-10-14 20:09:50
  - Description: 导入商品信息
-->
<head>
<title>Title</title>
</head>
<body>
	<%
		ProdectBiz handler = new ProdectBiz();
		String result = handler.importProducts(request);
		response.getWriter().write(result);
	 %>
</body>
</html>