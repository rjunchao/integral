<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-08-30 16:54:39
  - Description:对控制执行有效性：查询，新增，修改，删除的操作
-->
<head>
<title>控制执行有效性</title>
</head>
<body>
	<div class="nui-fit" style="height:90%;">
	  <div id="datagrid" class="nui-datagrid" style="width:100%;height:90%;"
			url="com.vbm.grc.basic.control.exec.ControlExec.controlExecList.biz.ext" 
			dataField="datas" onrowdblclick="onRowDblClick">
			<div property="columns">
				<div type="checkcolumn" width="10px">选择</div>
				<div field="level_code" allowResize="true" width="50px" headerAlign="center">控制执行有效性编码</div>
				<div field="level_name" allowResize="true" width="50px" headerAlign="center">控制执行有效性名称</div>
				<div field="describe" allowResize="true" width="160px" headerAlign="center">控制执行有效性说明</div>
			</div>
		</div>
	</div>
	<div class="nui-toolbar" style="text-align:center;height:10%;"borderStyle="border:0;">
		<a class="nui-button" iconCls="icon-save" onclick="ok">确定</a>&nbsp;&nbsp;
		<a class="nui-button" iconCls="icon-cancel" onclick="cancel">取消</a>
	</div>
</body>
     <script type="text/javascript">
       nui.parse();
       var grid = nui.get("#datagrid");
       grid.load();//加载数据
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
</html>