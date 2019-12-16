<%@page import="com.primeton.cap.AppUserManager"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-11 09:26:21
  - Description:
-->
<head>
	<title>签名显示</title>
	<style>
　　		
　　</style>
</head>
<body>
	<div style="margin: 0px auto;">
		<img id="sign" style="width:100%;height:100%;"></img>
	</div>
	<script type="text/javascript">
		function SetData(data){
			let id = nui.clone(data);
			let json = nui.encode({params:{pk:id}});
			nui.ajax({
				url:"com.xbkj.gd.data_handle.prod.sign.querySign.biz.ext",
				cache:false,
				type:"POST",
				data:json,
				contentType:"text/json",
				success: function(text){
					var msg = nui.decode(text);
					debugger;
					if(!msg.msg.flag){
						nui.alert(msg.msg.message);
						return;
					}
					$("#sign").attr("src", msg.msg.obj);
				},
				error: function (jqXHR, textStatus, errorThrown) {
                    nui.alert(jqXHR.responseText);
                }
			});
		}
			
	</script>
</body>
</html>