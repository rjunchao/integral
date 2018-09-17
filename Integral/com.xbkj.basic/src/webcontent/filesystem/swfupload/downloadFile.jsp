<%@page import="com.vbm.grc.regulation.inner.base.InnerreguBaseService"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//下载文件
	new InnerreguBaseService().download(request, response, out);
	out.clear();
	out = pageContext.pushBody();
%>
<html>
<!-- 
  - Author(s): lwl
  - Date: 2016-08-01 11:07:58
  - Description: 文件下载
-->
<head>
<title>文件下载</title>
<script type="text/javascript">
window.close();
</script>
</head>
<body>
</body>
</html>
