<%@page pageEncoding="UTF-8"%>

<%
	String original_url=null;
	Object objAttr=request.getParameter("original_url");
	if(objAttr != null){
		original_url=(String)objAttr;
	}
	String contextPath = request.getContextPath();
%>

<script>
	//直接访问login.jsp之后的跳转
	//var originalUrl=$id("originalUrl").value;
	var originalUrl="<%=original_url %>";
	if(originalUrl != "null"){
		//有可能不存在或上来就访问了login.jsp
		if(originalUrl.indexOf("login.jsp")==-1){
			location.href="<%=contextPath %>" + originalUrl;
		}else{
			location.href="<%=contextPath %>" + "/coframe/auth/skin1/index.jsp";
		}
	}else{
		location.href="<%=contextPath %>" + "/coframe/auth/skin1/index.jsp";
	}
</script>	



