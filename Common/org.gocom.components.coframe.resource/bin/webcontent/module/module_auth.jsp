<%@page pageEncoding="UTF-8"%>
<%@page import="com.eos.system.utility.StringUtil"%>
<%@page import="com.eos.foundation.eoscommon.ResourcesMessageUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Author(s): liuzn
  - Date: 2013-03-21 11:37:50
  - Description: 模块列表
-->
<head>
	<title>模块授权</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@include file="/coframe/tools/skins/common.jsp" %>
	<style type="text/css">
		#moduleToolBar{
			width: 100%;
			
			margin: 0px;
			border: 0px none transparent;
			border-collapse: collapse;
		}
		#moduleToolBar td{
			padding: 0px;
		}
	</style>
</head>
<body>
<div class="nui-fit" style="padding:10px;">
	<div id="moduleForm">
		<div class="nui-toolbar" style="text-align:left;line-height:30px;padding:5px 0px 5px 10px;" borderStyle="border-bottom:0;">
			<table id="moduleToolBar">
				<tr>
					<td style="width:1px;"></td>
					<td style="width:60px;"><a class="nui-button" iconCls="icon-save" onclick="saving();">保存</a></td>
					<td></td>
					<td style="width:10px;"></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="nui-fit">
	<div id="moduleGrid" class="nui-datagrid" url="org.gocom.components.coframe.resource.module.moduleAuth.getModuleList.biz.ext"
		showHeader="false" style="width:100%;height:100%;" 
		dataField="moduleList" idField="id" allowRowSelect="false" allowCellSelect="false" allowAlternating="true" allowCellEdit="true" showModified="false" showPager="false">
		<div property="columns">
			<div headerAlign="center" type="checkboxcolumn" name="status" field="status" trueValue="1" falseValue="2">授权</div>
			<div headerAlign="center" name="id" field="id">id</div>
			<div headerAlign="center" name="name" field="name">名称</div>
		</div>
	</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
	nui.parse();
	var moduleGrid = nui.get("moduleGrid");
	<%	if(StringUtil.isNotEmpty(request.getParameter("roleId"))){ %>
			var initData = {roleId:<%= request.getParameter("roleId") %>};
			moduleGrid.load(initData);
	<%	} %>

	function saving(){
		var moduleData = moduleGrid.getData();
		var fieldNodeArr = [];
		for(var i = 0; i < moduleData.length; i++){
			if(moduleData[i].status != ""){
				var fieldNode = {resourceID:moduleData[i].id, state:moduleData[i].status, resourceType:moduleData[i].type};
				fieldNodeArr.push(fieldNode);
			}
		}
		var sendData = {roleId:"<%= request.getParameter("roleId") %>", authRes:fieldNodeArr};
		$.ajax({
			url:"org.gocom.components.coframe.resource.module.moduleAuth.saveModuleAuth.biz.ext",
			type: "POST",
			data: nui.encode(sendData),
			cache: false,
			contentType: "text/json",
			success: function(text){
				var returnJson = nui.decode(text);
				if(returnJson.result == "true"){
					nui.alert("权限设置成功");
				}else{
					nui.alert("权限设置失败");
				}
			},
			error: function(jqXHR, textStatus, errorThrown){}
		});
	}
</script>
