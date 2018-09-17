<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-07-06 09:02:52
  - Description: 岗位管理：新增岗位，删除岗位，查询岗位，编辑岗位
-->
<head>
<title>岗位列表</title>
</head>
<body>
	<div class="nui-toolbar">
		<table style="width:100%;">
			<tr>
				<td style="width:100%;">
					<a class="nui-button" iconCls="icon-add"  onclick="add" id="add_btn">新增</a>
					<a class="nui-button" iconCls="icon-edit" onclick="edit" id="edit_btn">修改</a>
					<a class="nui-button" iconCls="icon-remove" onclick="del" id="del_btn">删除</a>
				</td>
				
				<!-- white-space:nowrap 当外层容器设置了宽度，而又不希望内层内容换行 -->
				<td style="white-space:nowrap">
					<input id="key" class="nui-textbox" emptyText="输入岗位名称" style="width:150px;" onkeyenter="onKeyEnter">
					<a class="nui-button" plain="true" onclick="search">查询</a>
				</td>
			</tr>
			
		</table>
	</div>
	<div id="datagrid" class="nui-datagrid" style="width:100%;height:90%;"
		url="com.vbm.grc.basic.position.position.positionlist.biz.ext" 
		dataField="vos" allowCellValid="true" allowCellEdit="false" onselectionchanged="selectionChanged"
		allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true">
		
		<input id="pk_position" name="pk_position" class="nui-hidden" />
		<div property="columns">
			<div type="checkcolumn"></div>
			<div field="post_code" allowResize="true" width="70" headerAlign="center">岗位编码</div>
			<div field="post_name" allowResize="true" width="70" headerAlign="center">岗位名称</div>
			<div field="post_grade" allowResize="true" width="50" headerAlign="center">岗位级别</div>
			<div field="post_type" allowResize="true" width="50" headerAlign="center">岗位类型</div>
			<div field="post_duty" allowResize="true" width="130" headerAlign="center">岗位职责</div>
			<div field="describe" allowResize="true" width="160" headerAlign="center">岗位说明</div>
		</div>
	
	</div>
	
	
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("datagrid");
		grid.load();
		
		//添加岗位信息
		function add(){
			setDel_Edit(false);
			 //添加
			nui.open({
				url:"<%=request.getContextPath() %>/grc/basic/position/addposition.jsp",
				width:600,
				height:230,
				ondestroy:function(action){
					setDel_Edit(true);
					if(action == "ok"){
						grid.reload();
					}
				}
			});
		}
		
		//编辑岗位信息
		function edit(){
			 //编辑
			var row = grid.getSelected();
			if(row != null){
				setAdd_Del(false);
				nui.open({
				url:"<%=request.getContextPath() %>/grc/basic/position/addposition.jsp",
				width:600,
				height:230,
				onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.setData(row);
				},
				ondestroy:function(action){
					setAdd_Del(true);
					if(action == "ok"){
						nui.alert("修改成功");
						grid.reload();
					}
				}
			});
			}else{
				nui.alert("选择要编辑的岗位");
			} 
		}
		
		//删除岗位
		function del(){
			//设置按钮的有效性
			setAdd_Edit(false);
			//删除
			var rows = grid.getSelecteds();
			if(rows.length > 0){
				var json = nui.encode({vos:rows});
				nui.confirm("确定要删除岗位信息么？", "提示", function(action){
					if(action == "ok"){
						nui.ajax({
							url:"com.vbm.grc.basic.position.position.delposition.biz.ext",
							cache:false,
							data:json,
							type:"POST",
							contentType:"text/json",
							success:function(msg){
								nui.alert(msg.msg.message);
								setAdd_Edit(true);
								if(msg.msg.flag){
									grid.reload();
								}
							}
						});
					}
				});
			}else{
				nui.alert("请选择要删除的岗位");
			}
		}
		
		//查询
		function search(){
		 	var key = nui.get("key").getValue();
			grid.load({params:{key:key}});
		}
		
		function onKeyEnter(){
			 //查询
			search();
		}
		
		function selectionChanged(){
			//多选禁用编辑按钮
			var rows = grid.getSelecteds();
			if(rows.length > 1){
				nui.get("edit_btn").setEnabled(false);
			}else{
				nui.get("edit_btn").setEnabled(true);
			} 
		}
		
		//按钮的状态 添加和编辑按钮
		function setAdd_Edit(flag){
			nui.get("add_btn").setEnabled(flag);
			nui.get("edit_btn").setEnabled(flag);
		}
		//按钮的状态 删除和编辑按钮
		function setDel_Edit(flag){
			nui.get("del_btn").setEnabled(flag);
			nui.get("edit_btn").setEnabled(flag);
		}
		//按钮的状态 添加和删除
		function setAdd_Del(flag){
			nui.get("add_btn").setEnabled(flag);
			nui.get("del_btn").setEnabled(flag); 
		}
		
	</script>
</body>
</html>