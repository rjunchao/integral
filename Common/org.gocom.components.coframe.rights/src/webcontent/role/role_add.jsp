<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Date: 2013-03-11 13:25:59
  - Description:
-->

<%@page import="com.eos.foundation.eoscommon.ResourcesMessageUtil"%>
<head>
	<title>添加角色</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@include file="/coframe/tools/skins/common.jsp" %>
	<style type="text/css">
		body{
			width:100%;
			height: 150px;
			padding: 0px;
		}
	</style>
</head>
<body>
	<div id="form1" style="margin-top:10px;">
		<input class="nui-hidden" name="capRole.tenantId" value="default" />
		<table style="width:100%;" class="nui-form-table">
			<tr>
				<td class="nui-form-label" style="width:90px;"><label for="capRole.roleCode$text">角色代码：</label></td>
				<td style="width:110px;"><input id="capRole.roleCode" class="nui-textbox" style="width:100px;" name="capRole.roleCode" required="true" onvalidation="checkRoleCode" /></td>
				<td class="nui-form-label" style="width:90px;"><label for="capRole.roleName$text">角色名称：</td>
				<td><input id="capRole.roleName" class="nui-textbox" style="width:100px;" name="capRole.roleName" required="true"/></td>
			</tr>
			<tr class="odd">
				<td class="nui-form-label"><label for="capRole.roleDesc$text">角色描述：</label></td>
				<td colspan="3"><textarea id="capRole.roleDesc" class="nui-textarea" style="width:348px; height:50px;" name="capRole.roleDesc"></textarea></td>
			</tr>
		</table>
		<div class="nui-toolbar" style="padding:0px;" borderStyle="border:0;">
			<table width="100%">
				<tr>
					<td style="text-align:center;">
						<a class="nui-button" iconCls="icon-save" onclick="formSaving">保存</a>
						<span style="display:inline-block;width:25px;"></span>
						<a class="nui-button" iconCls="icon-cancel" onclick="formCancel">取消</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	nui.parse();
	var form1 = new nui.Form("#form1");

	<%-- 验证角色是否存在 --%>
	function checkRoleExist(roleCode){
		var sendData = {"criteria":{"_expr":[{"roleCode":roleCode,"_op":"="}]}};
		var isExist;
		$.ajax({
			url: "org.gocom.components.coframe.rights.RoleManager.checkRoleExist.biz.ext",
			type: 'POST',
			data: nui.encode(sendData),
			cache: false,
			async: false,
			contentType: 'text/json',
			success: function(text){
				var returnJson = nui.decode(text);
				if(returnJson.isRoleExist == "true"){
					isExist = true;
				}else{
					isExist = false;
				}
			},
			error: function(jqXHR, textStatus, errorThrown){}
		});
		return isExist;
	}

	<%-- 关闭窗口 --%>
	function CloseWindow(action){
		if(action=="close" && form1.isChanged()){
			if(confirm("数据已改变,是否先保存?")){
				return false;
			}
		}else if(window.CloseOwnerWindow){
			return window.CloseOwnerWindow(action);
		}else{
			return window.close();
		}
	}

	function checkRoleCode(e){
		if(e.isValid){
			if(checkRoleExist(e.value) == true){
				e.errorText = "角色已存在";
				e.isValid = false;
			}
		}
	}

	function formSaving(){
		form1.validate();
		if(form1.isValid()==false) return;

		var form1Data = form1.getData(false,true);
		var sendData = nui.encode(form1Data);
		$.ajax({
			url:"org.gocom.components.coframe.rights.RoleManager.addRole.biz.ext",
			type:'POST',
			data:sendData,
			cache:false,
			contentType:'text/json',
			success:function(text){
				var returnJson = nui.decode(text);
				if(returnJson.exception == null){
					CloseWindow("saveSuccess");
				}else{
					nui.alert("添加角色失败", "系统提示", function(action){
						if(action == "ok" || action == "close"){
							CloseWindow("saveFailed");
						}
					});
				}
			}
		});
	}

	function formCancel(){
		CloseWindow("cancel");
	}
</script>
