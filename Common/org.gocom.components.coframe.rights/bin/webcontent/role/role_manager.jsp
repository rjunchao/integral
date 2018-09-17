<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html>
<!-- 
  - Author(s): liuzn (mailto:liuzn@primeton.com)
  - Date: 2013-03-06 19:07:31
  - Description: 角色管理页面
-->
<%@page import="com.eos.foundation.eoscommon.ResourcesMessageUtil"%>
<head>
	<title>角色管理</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>
<body>
<style>
#table1 .tit{
	height: 10px;
    line-height: 10px;
}
#table1 td{
	height: 10px;
    line-height: 10px;
}
#table1 .roleLabel{
	text-align: right;
}
#table1 .roleText{
	padding-left: 5px;
}
</style>
	<div class="search-condition">
		<div class="list">
			<div id="form1">
				<table id="table1" class="table">
					<tr>
						<td class="tit roleLabel" style="width:60px;">角色代码：</td>
						<td class="roleText" style="width:35%">
							<input class="nui-textbox" name="criteria._expr[0].roleCode" style="width:90%;" />
							<input class="nui-hidden" name="criteria._expr[0]._op" value="like" />
							<input class="nui-hidden" name="criteria._expr[0]._likeRule" value="all" />
						</td>
						<td class="tit roleLabel" style="width:60px;">角色名称：</td>
						<td class="roleText" style="width:35%">
							<input class="nui-textbox" name="criteria._expr[1].roleName" style="width:90%;"/>
							<input class="nui-hidden" name="criteria._expr[1]._op" value="like" />
							<input class="nui-hidden" name="criteria._expr[1]._likeRule" value="all" />
						</td>
						<td></td>
						<td class="btn-wrap" style="width:60px;">
							<input class="nui-button" iconCls="icon-search" text="查询" onclick="search" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div style="padding:10px 5px 0px 5px;">
		 <div class="nui-toolbar" style="border-bottom:0;">
			<table style="width:100%">
				<tr>
					<td>
						<a class="nui-button" iconCls="icon-add" onclick="addRole">增加</a>
						<a class="nui-button" id="btnEdit" iconCls="icon-edit" onclick="updateRole">编辑</a>
						<a class="nui-button" iconCls="icon-remove" onclick="removeRole">删除</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="nui-fit" style="padding:0px 5px 5px 5px;">
		<div id="roleGrid" class="nui-datagrid" style="width:100%;height:99%;"
			url="org.gocom.components.coframe.rights.RoleManager.queryRole.biz.ext"
			idField="roleId" multiSelect="true" allowAlternating="true" showPager="true"
			sizeList="[15,20,30]" pageSize="20"
			onselectionchanged="selectedRoles">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="roleCode" width="31%">角色代码</div>
				<div field="roleName" width="31%">角色名称</div>
				<div field="roleDesc">角色描述</div>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	nui.parse();
	var form1 = new nui.Form("#form1");
	var roleGrid = nui.get("roleGrid");
	var selectedRowsNumber = 0;
	roleGrid.load();

	function selectedRoles(){
		var roles = roleGrid.getSelecteds();
		if(roles.length > 1){
			nui.get("btnEdit").disable();
		}else{
			nui.get("btnEdit").enable();
		}
	}

	function addRole(){
		nui.open({
			url:"<%= request.getContextPath() %>/coframe/rights/role/role_add.jsp",
			title:'新增角色',
			width:500,
			height:190,
			onload:function(){
			},
			ondestroy:function(action){
				if(action == "saveSuccess"){
					roleGrid.reload();
				}
			}
		});
	}

	function updateRole(){
		var rows = roleGrid.getSelecteds();
		var row = roleGrid.getSelected();
		if(rows == null || rows.length == 0){
			nui.alert("请选中一条记录！");
			return false;
		}else if(rows.length == 1){
			nui.open({
				url:"<%= request.getContextPath() %>/coframe/rights/role/role_update.jsp",
				title:'编辑用户',
				width:500,
				height:190,
				onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.setData(row);
				},
				ondestroy:function(action){
					if(action == "saveSuccess"){
						roleGrid.reload();
					}
				}
			});
		}else{
			nui.alert("只能编辑一条记录！");
			return false;
		}
	}

	function removeRole(){
		var rows = roleGrid.getSelecteds();
		if(rows == null || rows.length == 0){
			nui.alert("请至少选中一条记录！");
			return false;
		}else{
			nui.confirm("确定删除选中记录？", "系统提示", function(action){
				if(action=="ok"){
					var sendData = nui.encode({roles:rows});
					roleGrid.loading("正在删除中,请稍等...");
					$.ajax({
						url:"org.gocom.components.coframe.rights.RoleManager.removeRoles.biz.ext",
						type:'POST',
						data:sendData,
						cache: false,
						contentType:'text/json',
						success:function(text){
							var returnJson = nui.decode(text);
							if(returnJson.exception == null){
								nui.alert("删除角色成功", "系统提示", function(action){
									roleGrid.reload();
								});
							}else{
								nui.alert("删除角色失败", "系统提示");
								roleGrid.unmask();
							}
						}
					});
				}
			});
		}
	}

	function search(){
		var form1Data = form1.getData(false, true);
        roleGrid.load(form1Data);
	}
</script>
