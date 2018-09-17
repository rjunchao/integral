<%@page pageEncoding="UTF-8"%>
<%@include file="/vbm/vbmcommon.jsp" %>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-08-30 09:44:06
  - Description: 风险发生概率参照页面
-->
<head>
<title>风险发生概率</title>
</head>
<body>
	<div style="width:100%;height:90%;"> 	
	    <div id="grid1" class="nui-datagrid" 
	    	url="com.vbm.grc.basic.risk.incidence.riskincidence.queryRiskIncidence.biz.ext" 
        	style="width:100%;height:100%;" onrowdblclick="onRowDblClick"
            dataField="datas" allowResize="true" multiSelect="false"
            >
        	<div property="columns">
	        	<div type="checkcolumn" width="10px">选择</div>
	        	<div field="incidence_code"  headerAlign="center" align="center" width="24px">概率等级编码</div>  
            	<div field="incidence_name"  headerAlign="center" align="center" width="24px">概率等级名称</div>
            	<div field="describe"  headerAlign="center" width="160px" allowSort="true">概率说明</div>
         	</div>    
        </div> 
    </div>
    <div class="nui-toolbar" style="text-align:center;height:10%;">
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