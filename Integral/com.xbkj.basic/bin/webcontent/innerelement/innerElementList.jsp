<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-06-20 09:37:30
  - Description:
-->
<head>
<title>内控信息列表</title>
</head>
<body>
	<div style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table style="width:100%;">
	            <tr>
		            <td style="width:100%;">
		                <a class="nui-button" iconCls="icon-add" onclick="add()">增加</a>
		            	<a class="nui-button" iconCls="icon-edit" onclick="edit()" id="edit_btn">编辑</a>
		            	<a class="nui-button" iconCls="icon-remove" onclick="remove()" >删除</a>
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
		<input id="pid" name="pk_inter_cntr" class="nui-hidden" />
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
	    url="com.vbm.grc.basic.inner.element.innercontrol.innerconele.innerElementList.biz.ext"  
	    idField="pk_inter_cntr" dataField="datas" allowResize="false" multiSelect="true" 
	    onrowdblclick="edit()" onselectionchanged="selectionChanged" allowCellSelect="false">
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <div field="inter_cntr_code" width="80" headerAlign="center" allowSort="false">内控要素编码</div>
		        <div field="inter_cntr_name" width="80" headerAlign="center" allowSort="false">内控要素名称</div> 
		        <div field="inter_cntr_level" width="30" headerAlign="center" allowSort="false">层级</div> 
		        <div field="is_leaf" width="50" headerAlign="center" allowSort="false" renderer="onIsLeafRenderer">是否叶子菜单</div>
		        <div field="inter_cntr_explain" width="200" headerAlign="center" allowSort="false">内控要素说明</div>
		    </div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("grid");
		//获取请求参数
		var pid = "<%=request.getParameter("pk_inter_cntr") %>";
		var level = "<%=request.getParameter("inter_cntr_level") %>";
		//加载子内控要素
		grid.load({params:{parentCode:pid}});
		
		function edit(){
			var data = grid.getSelecteds();
			var len = data.length;
			if(len > 0 ){
				//编辑
				nui.open({
					url:"<%=request.getContextPath() %>/grc/basic/innerelement/innerElementEdit.jsp",
					title:"修改内控要素",
					width:600,
					height:200,
					onload:function(){
						var iframe = this.getIFrameEl();
						iframe.contentWindow.SetData(data[0]);
					},
					ondestroy:function(action){
						if(action == "ok"){
							nui.alert("修改成功");
							parent.refresh();//刷新树节点，调用innerElementMain.jsp的方法
							grid.reload();
						}
					}
				});
			}else{
				nui.alert("请选择要编辑的行");
				return;
			}
		}	
		
		
		function add(){
			//添加
			//添加元素
			nui.open({
				url:"<%=request.getContextPath() %>/grc/basic/innerelement/innerElementAdd.jsp",
				title:"添加内控要素",
				width:600,
				height:200,
				onload:function(){
					//把父节点传过去
					//var id = document.getElementById("pid").value;
					//nui.alert(pid);
					var rows = grid.getSelecteds();
					if(rows != null && rows.length > 0){
						level = rows[0].inter_cntr_level;
					}else{
						if(level == "null" || level == null || level == ""){
							level = 0;
						}
					}
					var data = {parentCode:pid,level:level};
					var iframe = this.getIFrameEl();
					iframe.contentWindow.SetData(data);//调用另外一个页面的方法
				},
				ondestroy:function(action){
					//判断是否成功与否
					if(action == "ok"){
						nui.alert("添加成功");
						parent.refresh();//刷新树节点，调用innerElementMain.jsp的方法
						grid.reload();
					}
				}
			});
		}
		
		function remove(){
			//删除
			var rows = grid.getSelecteds();
			if(rows.length > 0){
				/* var ids = [];
				//删除id数组
				for(var i = 0; i < rows.length; i++){
					ids.push(rows[i].pk_inter_cntr);
				} */
				nui.confirm("将一起删除该节点下的子节点，确认？","删除确认", function(action){
					if(action == "ok"){
						//具体的删除
						var json = nui.encode({vos:rows});
						nui.ajax({
							url:"com.vbm.grc.basic.inner.element.innercontrol.innerconele.delInnElems.biz.ext",
							cache:false,
							async:true,
							data:json,
							type:"POST",
							contentType:"text/json",
							success:function(msg){
								nui.alert(msg.msg.message);
								parent.refresh()
								grid.reload();
								return;
							},
							error: function (jqXHR, textStatus, errorThrown) {
			                    alert(jqXHR.responseText);
			                }
						});
					}
				});
			}else{
				nui.alert("请选择要删除内控要素");
			}
		}
		
		//是否是叶子菜单的显示
		function onIsLeafRenderer(e){
			if(e.value == "1") return "是";
			else return "否";
		}
		
		function selectionChanged(){
			//多选禁止编辑按钮
			var rows = grid.getSelecteds();
			if(rows.length > 1){
				nui.get("edit_btn").setEnabled(false);
			}else{
				nui.get("edit_btn").setEnabled(true);
			}
		}
			
	</script>
</body>
</html>