<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@include file="/coframe/tools/skins/common.jsp" %>
<!-- 
  - Author(s): wanghl
  - Date: 2013-03-13 10:51:45
  - Description:
-->
<head>
<title>Title</title>
</head>
<body>
<div align="center" style="padding-top: 15px;">
<table>
	<tr>
		<td >
			<div id="radioList" class="nui-radiobuttonlist" repeatItems="2" repeatLayout="table"
			    textField="text" valueField="id" value="and" >
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" class="nui-button" text="确定" id="buttonOk" onclick="onOk" />
			<input type="button" class="nui-button" text="取消" id="buttonCancel" onclick="onCancel" />
		</td>
	</tr>
</table>
</div>
</body>
<script type="text/javascript">
	nui.parse();
	
	var radioList = nui.get("radioList");

	var data = [{id:"and", text: "并且"},{id:"or", text: "或者"}];
	
	function setData(node){
		radioList.setData(data);
		if(node){
			radioList.setValue(node.operator);
		}
	}
	
	function getData(){
		var selectOper = radioList.getSelected();
		return selectOper;
	}
	
	function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }

    function onOk() {
        CloseWindow("ok");
    }
    function onCancel() {
        CloseWindow("cancel");
    }
	

</script>
</html>