<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@include file="/coframe/tools/skins/common.jsp" %>
<!-- 
  - Author(s): fangwl (mailto:fangwl@primeton.com)
  - Date: 2013-03-06 14:34:21
  - Description:
-->
<head>
</head>
<body>
	<div class="nui-toolbar" style="text-align:center;line-height:30px;"
		borderStyle="border-left:0;border-top:0;border-right:0;">
		<label>名称：</label>
		<input id="key" class="nui-textbox" style="width:100px;" onenter="onKeyEnter" />
		<a class="nui-button" style="width:60px;" iconCls="icon-search" onclick="search()">查询</a>
		<a class="nui-button" iconCls="icon-expand" onclick="expandAll()" title="全部展开"></a>
		<a class="nui-button" iconCls="icon-collapse" onclick="collapseAll()" title="全部折叠"></a>
	</div>
	<div style="padding:10px;">
		<ul id="viewtree" class="nui-tree" url="org.gocom.components.coframe.resource.view.viewAuthList.getViewTreeList.biz.ext"
			style="width:200px; padding:5px;" dataField="viewTreeList"
			idField="index" textField="name" parentField="pid"
			resultAsTree="false" allowSelect="true" enableHotTrack="false" showTreeLines="true" expandOnNodeClick="true" onnodedblclick="onnodedblclick">
		</ul>
	</div>
</body>
</html>
<script type="text/javascript">
	nui.parse();
	var viewtree = nui.get("viewtree");
 	viewtree.expandAll();
	function search(){
		var filtedNodes = new Array();
		var key = nui.get("key").getValue();
		if(key == ""){
			viewtree.clearFilter();
		}else{
			var rootNode = viewtree.getRootNode();
			viewtree.cascadeChild(
				rootNode,
				function(node){
					var pNode = viewtree.getParentNode(node);
					var nofind = true;
					for(i = 0; i < filtedNodes.length; i++){
						if(filtedNodes[i] == pNode.id){
							filtedNodes[filtedNodes.length] = node.id;
							nofind = false;
						}
					}
					if(nofind){
						var text = node.name ? node.name.toLowerCase() : "";
						if(text.indexOf(key) != -1){
							filtedNodes[filtedNodes.length] = node.id;
						}
					}
				}
			);
			viewtree.filter(function(node){
				for(i = 0; i < filtedNodes.length; i++){
					if(filtedNodes[i] == node.id){
						return true;
					}
				}
			});
		}
	}

	function expandAll(){
		viewtree.expandAll();
	}

	function collapseAll(){
		viewtree.collapseAll();
	}
	
	function getSelectedData() {
        var node = viewtree.getSelectedNode();
        if(node.type!="CAPVIEW") return;
        var id = node.id;
        var ids = id.split(".");
        
        var resPara = "moduleId="+ids[1]+"&viewId="+ids[2];
        var resUrl = "com.primeton.cap.view.runtime.flow?moduleId="+ids[1]+"&viewId="+ids[2];
        var resType = "view"
        var resdata = nui.decode({resUrl:resUrl,resPara:resPara,resType:resType});
        return resdata;
    }
	function onnodedblclick(){
		parent.onOk();
	}
</script>
