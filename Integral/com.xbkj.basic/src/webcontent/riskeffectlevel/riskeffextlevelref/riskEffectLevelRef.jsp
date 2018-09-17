<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-08-30 09:06:03
  - Description:
-->
<head>
<title>风险影响程度参照页面</title>
</head>
<body>
   <!-- 这个div里是增删改查的按钮 -->
  <div id="datagrid" class="nui-datagrid" style="width:100%;height:90%;"
		url="com.vbm.grc.basic.risk.effectlevel.riskeffectlevel.RiskEffectLevelList.biz.ext" 
		dataField="vos" onrowdblclick="onRowDblClick"
		allowCellSelect="true" multiSelect="false" >
		<input id="pk_risk_effect_level" name="pk_risk_effect_level" class="nui-hidden" />
		<div property="columns">
			<div type="checkcolumn"width="10px">选择</div>
			<div field="effect_level_code" align="center" width="24px" headerAlign="center">影响程度编码</div>
			<div field="effect_level_name" align="center" width="24px" headerAlign="center">影响程度名称</div>
			<div field="describe" width="160px" headerAlign="center">影响程度说明</div>
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