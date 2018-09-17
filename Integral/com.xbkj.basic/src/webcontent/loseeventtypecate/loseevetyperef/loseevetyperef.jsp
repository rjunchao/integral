<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-08-03 10:08:32
  - Description:
-->
<head>
<title>损失事件类型参照页面</title>
</head>
<body>
	<div class="nui-fit" style="height:100%;">
		<ul id="tree1" class="nui-tree" style="width: 100%; height:100%;"
			url="com.vbm.grc.basic.loseevent.loseeventtype.loseeventtree.biz.ext"
			showTreeIcon="true" textField="eventtype_name" idField="pk_lose_eventtype"
			parentField="parent_code" dataField="lists" expandOnLoad="0"
			resultAsTree="false" onnodedblclick="onNodedbClick">
		</ul>
	</div>	
	<div class="nui-toolbar" style="text-align:center;padding:3px;">
        <a class="nui-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>    	
    <script type="text/javascript">
    	nui.parse();
    	var tree = nui.get("tree1");
    	tree.load();
    	
    	function onNodedbClick(e){
    		var node=tree.getSelected();
    		if(node.type=="root"){
    			nui.alert("请选择损失事件类型！");
    			return;
    		}
    		
    		CloseWindow("ok");
    	}
    	
    	function onOk(){
    		var node = tree.getSelected();
    		if(node==null){
    			CloseWindow("cancel");	
    		}
    		if(node.type=="root"){
    			nui.alert("请选择损失事件类型！");
    			return;
    		}
	    	CloseWindow("ok");
    		
    	}
    	
    	function onCancel(){
    		CloseWindow("cancel");
    	}
    	
    	//返回选中节点
    	function GetData(){//返回数据
    		var node = tree.getSelected();
    		return node;
    	}
    	//返回选择的节点和对应的父节点
    	function getNodeAndParentNode(){//返回数据
    		var node = tree.getSelected();
    		var parent = tree.getParentNode(node);
    		var data = nui.encode({node:node,parent:parent});
    		return data;
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