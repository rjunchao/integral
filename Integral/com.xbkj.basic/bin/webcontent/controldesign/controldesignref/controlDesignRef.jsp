<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-08-30 17:16:52
  - Description: 控制执行有效性参照页面
-->
<head>
<title>控制设计有效性</title>
</head>
<body>
	<div class="nui-fit" style="height:90%;">
	    <div id="grid1" class="nui-datagrid" 
	    	url="com.vbm.grc.basic.control.design.controldesign.queryControlDesign.biz.ext" 
	    	style="width:100%;height:90%;"  
	        dataField="datas" allowResize="true"
	        idField="orgid" onrowdblclick="onRowDblClick">
	    	<div property="columns">
	        	<div type="checkcolumn" width="10px">选择</div>
	        	<div field="level_code"  headerAlign="center" align="center" width="50px">控制设计有效性编码</div>
	        	<div field="level_name"  headerAlign="center" width="50px" >控制设计有效性名称</div>  
	            <div field="describe"  headerAlign="center" width="160px">控制设计有效性说明</div>  
	     	</div>    
	    </div>
    </div>
    <div class="nui-toolbar" style="text-align:center;height:10%;"borderStyle="border:0;">
		<a class="nui-button" iconCls="icon-save" onclick="ok">确定</a>&nbsp;&nbsp;
		<a class="nui-button" iconCls="icon-cancel" onclick="cancel">取消</a>
	</div> 
</body>
</html>
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("grid1");
		grid.load();
		function onRowDblClick(e){
			CloseWindow("ok");
		}
		
		function GetData(){
			var data = grid.getSelected();
			return data;
		}
		function cancel(){
			CloseWindow("cancel");
		}
		
		function ok(){
			CloseWindow("ok");
		}
		//关闭窗口
		function CloseWindow(action) {            
	        if(window.CloseOwnerWindow){
				return window.CloseOwnerWindow(action);
			}else{
				window.close();
			}   
	    }	
	
		
	</script>