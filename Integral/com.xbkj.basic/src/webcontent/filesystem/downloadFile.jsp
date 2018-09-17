<%@page import="com.vbm.grc.common.FileManager"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String pk_filesystem = request.getParameter("pk_filesystem");
	String file_name = request.getParameter("file_name");
	//查询文件sql
	String sql = "select content_data from grc_filesystem gfs where gfs.dr = 0 and gfs.pk_filesystem = '"+pk_filesystem+"'";
	//下载文件
	new FileManager().download(sql, file_name, response, out);
	/*
	* 第二种调用方式
	*/
	//new ExtRegulationService().download(request, response, out);
	out.clear();
	out = pageContext.pushBody();
%>
<html>
<!-- 
  - Author(s): xjm
  - Date: 2016-09-06 10:20:58
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
