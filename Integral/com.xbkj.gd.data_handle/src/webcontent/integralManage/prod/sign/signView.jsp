<%@page import="com.primeton.cap.AppUserManager"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-27 09:26:21
  - Description:
-->
<head>
	<title>签名显示</title>
	<style>
　　
　　</style>
</head>
<body>

	
	<div>
		<img id="sign" style="width:200px;height:200px;border:1px red solid;"></img>
	</div>
	
	<script type="text/javascript">
		nui.ajax({
				url:"com.xbkj.gd.data_handle.prod.sign.signQuery.biz.ext",
				cache:false,
				type:"POST",
				contentType:"text/json",
				success: function(text){
					var msg = nui.decode(text);
					debugger;
					$("#sign").attr("src", msg.sign);
				},
				error: function (jqXHR, textStatus, errorThrown) {
                    nui.alert(jqXHR.responseText);
                }
			});
			
	</script>
</body>
</html>