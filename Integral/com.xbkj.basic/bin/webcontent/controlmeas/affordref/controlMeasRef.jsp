<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): ljb
  - Date: 2016-09-13 09:41:50
  - Description:
-->
<head>
<title>控制措施参照</title>
</head>
<body>
	<div class="nui-fit" style="height:90%;">
		<ul id="tree1" class="nui-tree" style="width:100%; height:100%;"
					url="com.vbm.grc.basic.control.meas.controlmeas.controlMeasTree.biz.ext"
					showTreeIcon="true" showTreeLines="true" textField="meas_name" idField="pk_control_meas"	
					parentField="parent_code" dataField="lists" expandOnLoad="0" contextMenu="#menuTreeMenu"
					resultAsTree="false" onnodedblclick="onNodedbClick" onbeforeload="onBeforTreeLoad">
		</ul>
   </div>
   <div class="nui-toolbar" style="text-align:center;padding:5px;height:10%;"borderStyle="border:0;">
        <a class="nui-button" style="width:60px;" onclick="onOk()">确定</a>
        &nbsp;&nbsp;
        <a class="nui-button" style="width:60px;" onclick="onCancel()">取消</a>
   </div>

</body>
	<script type="text/javascript">
		nui.parse();
		var tree = nui.get("#tree1");
		tree.load();
		//设置参数
		function onBeforTreeLoad(e)
		{
			var nodeId = e.node.pk_control_meas;
			if(!nodeId)
			{
				nodeId = "root";
			}
			e.params.nodeId = nodeId;
		}
		function onNodedbClick(e)
		{
			onOk();
		}
		//确定按钮的操作
		function onOk()
		{
			var node = tree.getSelectedNode();
			//不让选择root节点
			node = node.pk_control_meas;
			if(node == "root")
			{
				nui.alert("请选择控制措施");
			}
			else
			{
				CloseWindow("ok");
			}
		}
		//取消按钮操作
		function onCancel()
		{
			CloseWindow("cancel");
		}
		//关闭窗口
		function CloseWindow(action)
		{
			if(window.CloseOwnerWindow)
			{
				return window.CloseOwnerWindow(action);
			}
			else
			{
				window.close();
			}
		}
		//获取数据
		function GetData()
		{
			var node = tree.getSelectedNode();
			return node;
		}
	</script>
</html>