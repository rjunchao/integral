<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-08-03 10:29:46
  - Description:
-->
<head>
<title>损失形态参照页面</title>
</head>
<body>
 <!-- 这个div里是增删改查的按钮 -->
  <div class="nui-toolbar" style="border-bottom:0;padding:0px;height:10%;">
  		<table style="width:100%;">
  			<tr>
  				<td style="white-space:nowrap;" align="center">
  					<input id="key" class="nui-textbox" style="width:150px;" onkeyenter="search" emptyText="损失形态类型编码或名称"/>
					<a class="nui-button" onclick="search()">查询</a>
  				</td>
  			</tr>
  		</table>
  </div>
  	<div id="datagrid" class="nui-datagrid" style="width:100%;height:80%;"
		url="com.vbm.grc.basic.losetype.losetype.queryLoseType.biz.ext" 
		dataField="vos" allowCellSelect="true" multiSelect="false" onrowdblclick="rowDbClick">
		<input id="pk_loss_type" name="pk_loss_type" class="nui-hidden" />
		<div property="columns">
			<div type="indexcolumn" width="5px"></div>
			<div field="losstype_code" allowResize="true" width="10px" headerAlign="center">损失形态编码</div>
			<div field="lossname_name" allowResize="true" width="10px" headerAlign="center">影响程度名称</div>
		</div>
	</div>
	<div class="nui-toolbar" style="text-align: center;padding: 0px;height:10%;">             
		<a class="nui-button" onclick="ok" style="width:60px;">确定</a>  
		<a class="nui-button" onclick="cancel" style="width:60px;">取消</a>
	</div>
	
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("datagrid");
		grid.load();
		function search(){
			var key = nui.get("key").getValue();
			grid.load({params:{key:key}});
		}
		
		function ok(){
			var data = grid.getSelected();
			if(data != null){
				CloseWindow("ok");
			}else{
				CloseWindow("cancel");
			}
		}
		
		function rowDbClick(){
			CloseWindow("ok");
		}
		
		function GetData(){
			var data = grid.getSelected();
			return data;
		}
		
		function cancel(){
			CloseWindow("cancel");
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
</body>
</html>