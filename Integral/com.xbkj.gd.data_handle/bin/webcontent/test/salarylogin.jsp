<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.pub.xbkj.common.MsgResponse"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="com.primeton.cap.AppUserManager"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<html>
<!-- 
  - Author(s): rjc
  - Date: 2019-02-28 12:35:40
  - Description:
-->
<head>
<title>Title</title>
</head>
<body>
<%
	String username = AppUserManager.getCurrentUserName();
	HttpURLConnection conn = null;
		try {
			System.out.print(username);
			URL url = new URL("http://127.0.0.1:8013/integral/sys/single/login");
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("login_user", "admin");
			conn.setRequestMethod("POST");
			conn.connect();
			int code = conn.getResponseCode();
			if(200 == code){
				InputStream is = conn.getInputStream();
				byte[] buf = new byte[1024];
				int len = 0;
				StringBuilder sb = new StringBuilder();
				while((len = is.read()) != -1){
					sb.append(new String(buf, 0, len));
				}
				String openUrl = sb.toString();
				System.out.println(url);
				if(StringUtils.isNotEmpty(openUrl) && !openUrl.startsWith("false_")){
					response.sendRedirect(openUrl);
				}
				out.print(openUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(conn != null){
				conn.disconnect();
			}
		}
	
 %>
</body>
</html>