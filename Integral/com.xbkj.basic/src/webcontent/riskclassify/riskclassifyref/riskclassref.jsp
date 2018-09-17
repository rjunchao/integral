<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-08-29 10:34:52
  - Description:
-->
<head>
<title>风险成因分类参照页面</title>
</head>
<body>
	<div class="nui-fit" style="height:90%;">
		<ul id="tree1" class="nui-tree" style="width: 100%; height: 100%;"
			url="com.vbm.grc.basic.risk.classify.riskclassify.riskClassifyTree.biz.ext"
			showTreeIcon="true" showTreeLines="true" textField="classify_name" idField="pk_risk_classify"	
			parentField="parent_code" dataField="lists" expandOnLoad="0" 
			resultAsTree="false" onnodedblclick="onNodedbClick">
		</ul>
	</div> 
	<div class="nui-toolbar" style="text-align:center;height:10%;"borderStyle="border:0;">
		<a class="nui-button" iconCls="icon-save" onclick="ok">确定</a>&nbsp;&nbsp;
		<a class="nui-button" iconCls="icon-cancel" onclick="cancel">取消</a>
	</div>
	
	<script type="text/javascript">
		nui.parse();
		
		var tree = nui.get("tree1");
		tree.load({nodeId:'root'});
		
		function GetData(){
			var data = tree.getSelected();
			return data;
		}
		
		function onNodedbClick(){
			var node = tree.getSelected();
			if(node==null){
				CloseWindow("cancel");	
			}
			if(node.type=="root"){
				nui.alert("请选择风险成因！");
				return;
			}
			CloseWindow("ok");
		}
		
		function cancel(){
			CloseWindow("cancel");
		}
		
		function ok(){      
			var node = tree.getSelected();
			if(node==null){
				CloseWindow("cancel");	
			}
			if(node.type=="root"){
				nui.alert("请选择风险成因！");
				return;
			}
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
</body>
</html>