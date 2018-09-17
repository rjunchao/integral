<%@page pageEncoding="UTF-8"%>
<%@include file="/vbm/vbmcommon.jsp" %>
<%@include file="/common/common.jsp"%>
<%@include file="/common/skins/skin0/component.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lwl
  - Date: 2016-07-21 15:03:43
  - Description:
-->
<head>
<title>内控要素</title>
</head>
<body>
	<div class="nui-fit" style="padding:0px;height:90%;">
		<ul id="tree1" class="nui-tree" style="width: 100%; height: 100%;"
			url="com.vbm.grc.basic.inner.element.innercontrol.innerconele.innerElementTree.biz.ext"
			showTreeIcon="true" textField="inter_cntr_name" idField="pk_inter_cntr" 
			parentField="parent_code" dataField="lists" expandOnLoad="0">
		</ul>
	</div>
	<div class="nui-toolbar" style="text-align:center;paddin:8px;">
        <a class="nui-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
	
</body>
</html>
<script type="text/javascript">
		nui.parse();
		var tree = nui.get("tree1");
		tree.load();
		
		function clearNodes(e){
	     	var treenode=e.node;
	     	tree.uncheckNodes((tree.getAllChildNodes(e.node)));
	     	tree.bubbleParent (e.node,function (treenode){
	     		treenode=tree.getParentNode(treenode);
	     		tree.uncheckNode(treenode);
	     	}, true );
		}
		
		//设置参数
	   function onBeforTreeLoad(e){
		var nodeId =  e.node.pk_inter_cntr;
		if(!nodeId){
		   nodeId = "root";
		}
		e.params.nodeId = nodeId;
	  }
		//获取数据
	    function GetData(){
			var node = tree.getSelectedNode();
       		return node;
		} 
		function onNodeDblClick(e) {
		  onOk();
    	}
		//确定
		function onOk() {
	        var node = tree.getSelectedNode();
	        node=node.pk_inter_cntr
	         if(node=="root"){
	        nui.alert("请选择业务领域");
	        }
	        else
       		CloseWindow("ok");         
	    }
	    //取消
		function onCancel() {
	        CloseWindow("cancel");
	    }
	    //关闭窗口
	    function CloseWindow(action) {
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
       		else window.close();
	    }
	</script>