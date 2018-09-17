<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lwl
  - Date: 2016-06-29 09:08:05
  - Description:
-->
<head>
<title>业务领域列表</title>
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
		            <td style="white-space:nowrap;">
	        			<input id="key" class="nui-textbox" style="width:150px;" emptyText="请输入编码或名称"/>
						<a class="nui-button" onclick="search()">查询</a>
				 	</td>
	            </tr>
	        </table>
	    </div>
    </div>
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
		<input id="pid" name="pk_busi_area" class="nui-hidden" />
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.vbm.grc.basic.busiarea.busiarea.busiAreaList.biz.ext" 
		    dataField="datas" allowResize="false"
		    onrowdblclick="edit()" onselectionchanged="selectionChanged">
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <div field="busi_code" width="100" headerAlign="center" allowSort="false">业务领域编码</div>
		        <div field="busi_name" width="100" headerAlign="center" allowSort="false">业务领域名称</div> 
		        <div field="isLeaf" width="50" headerAlign="center" allowSort="false" renderer="onIsLeafRenderer">是否叶子节点</div>
		        <div field="busi_describe" width="150" headerAlign="center" allowSort="false">业务领域描述</div>
		    </div>
		</div>
	</div>
	
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("grid");
		//获取请求参数
		var pid = "<%=request.getParameter("pk_busi_area") %>";
		//加载子业务领域
		grid.load({params:{parentCode:pid}});
		
		//查询
		function search(){
			//获取请求参数
			var pid = "<%=request.getParameter("pk_busi_area") %>";
			var key = nui.get("key").getValue();
            key = trim(key);
	        grid.load({params:{key:key,parentCode:pid}});
		}
		function trim(str){
	    	var result = "";
	    	result = str.replace(/(^\s+)|(\s+$)/g,"");
	    	result = result.replace(/\s/g,"");
	    	return result;
	    }
	    //编辑
		function edit(){
			var data = grid.getSelecteds();
			var len = data.length;
			if(len > 0 ){
				nui.open({
					url:"<%=request.getContextPath() %>/grc/basic/busiarea/editBusiArea.jsp",
					title:"修改业务领域",
					width:600,
					height:200,
					onload:function(){
						var iframe = this.getIFrameEl();
						iframe.contentWindow.SetData(data[0]);
					},
					ondestroy:function(action){
						if(action == "ok"){
							nui.alert("修改成功");
							//刷新树节点，调用busiAreaMain.jsp的方法
							parent.refresh();
							grid.reload();
						}
					}
				});
			}else{
				nui.alert("请选择要编辑的行");
				return;
			}
		}	
		//新增	
		function add(){
			//添加
			//添加元素后刷新
			nui.open({
				url:"<%=request.getContextPath() %>/grc/basic/busiarea/addBusiArea.jsp",
				title:"新建业务领域",
				width:600,
				height:200,
				onload:function(){
					//把父节点传过去
					var data = {parentCode:pid};
					var iframe = this.getIFrameEl();
					//调用另外一个页面的方法
					iframe.contentWindow.SetData(data);
				},
				ondestroy:function(action){
					//判断是否成功与否
					if(action == "ok"){
						//刷新树节点
						parent.refresh();
						grid.reload();
					}
				}
			});
		}
		//删除
		function remove(){
			var rows = grid.getSelecteds();
			if(rows.length > 0){
				nui.confirm("将一起删除该节点下的子节点","是否删除", function(action){
					if(action == "ok"){
						var json = nui.encode({vos:rows});
						nui.ajax({
							url:"com.vbm.grc.basic.busiarea.busiarea.removeBusiArea.biz.ext",
							cache:false,
							async:true,
							data:json,
							type:"POST",
							contentType:"text/json",
							success: function (msg) {
			                	nui.alert(msg.msg.message);
								parent.refresh()
								grid.reload();
								return;
				                },				                
			                error: function (jqXHR, textStatus, errorThrown) {
			                    nui.alert(jqXHR.responseText);
			                }
						});
					}
				});
			}else{
				nui.alert("请选择要删除业务领域");
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