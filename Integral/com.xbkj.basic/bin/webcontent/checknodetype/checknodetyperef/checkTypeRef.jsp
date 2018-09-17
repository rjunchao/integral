<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): xjm
  - Date: 2016-09-27 09:34:40
  - Description:
-->
<head>
<title>检查点类型参照</title>
</head>
<body>
	<div id="form1" method="post"  class="nui-toolbar" style="text-align:center;height:8%;padding:2px;padding-top:6px;border-bottom:0;">
    	<label>查询条件：</label>
  		<input id="key" class="nui-textbox" emptyText="请输入编码或名称"  onenter="onKeyEnter"/>
  		<a class="nui-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div id="datagrid" class="nui-datagrid" style="height:79%;"
		url="com.vbm.grc.basic.checktype.checktype.queryCheckTypeList.biz.ext" 
		dataField="vos" allowCellValid="true" allowCellEdit="false"
		allowCellSelect="true" multiSelect="false" editNextOnEnterKey="true" onrowdblclick="onrowdblclick">
		
		<input id="pk_check_type" name="pk_check_type" class="nui-hidden" />
		<div property="columns">
			<div type="checkcolumn"></div>
			<div field="check_type_code" allowResize="true" width="70" headerAlign="center">检查类型编码</div>
			<div field="check_type_name" allowResize="true" width="70" headerAlign="center">检查类型名称</div>
			<div field="describe" allowResize="true" width="160" headerAlign="center">类型说明</div>
		</div>
	</div>                
    <div class="nui-toolbar" style="text-align:center;padding:8px;height:6%;">
        <a class="nui-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>

</body>

<script type="text/javascript"> 
    nui.parse();
 	var grid = nui.get("datagrid");
 	grid.load();
 	function GetData(){
 		var data = grid.getSelected();
 		return data;
 	}
 	
 	function search(){
 		var key=nui.get("key").getValue();
 		grid.load({paramMap:{key:key}});
 	}
 	
 	function onrowdblclick(){
 		CloseWindow("ok");
 	}
 	function onOk(){
 		var data = grid.getSelected();
		if(data != null){
	 		CloseWindow("ok");
		}else{
	 		CloseWindow("cancel");
		}
 	}
 	function onCancel(){
 		CloseWindow("cancel");
 	}
 	
 	 //关闭窗口
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
   		else window.close();
    }
   
</script>

</html>