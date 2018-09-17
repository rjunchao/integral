<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html>
<!--
  - Author(s): liuzn (mailto:liuzn@primeton.com)
  - Date: 2013-03-01 13:51:37
  - Description: 表单树
-->
<head>
    <title>form tree</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>
<body>
<style>
#formtree .mini-grid-viewport{
	background-color:transparent !important;
}
#formtree .mini-panel-viewport{
	background-color:transparent !important;
}
</style>
<div class="nui-fit" style="padding:10px;">
<div class="nui-splitter" style="width:100%;height:100%;" allowResize="false">
    <div size="225" showCollapseButton="false">
        <div class="nui-fit">
			<div class="nui-toolbar" style="text-align:center;line-height:30px;"
				borderStyle="border-left:0;border-top:0;border-right:0;border-foot:0;">
				<input id="key" class="nui-textbox" style="width:70px;" onenter="onKeyEnter" />
				<a class="nui-button" iconCls="icon-search" onclick="search()"></a>
				<span class="separator"></span>
				<a class="nui-button" iconCls="icon-expand" onclick="expandAll()" title="全部展开"></a>
				<a class="nui-button" iconCls="icon-collapse" onclick="collapseAll()" title="全部折叠"></a>
			</div>
			<div class="nui-fit" style="padding:10px 0px 0px 20px;">
				<ul id="formtree" class="nui-tree" url="org.gocom.components.coframe.resource.formListService.getFormTree.biz.ext"
					style="width:200px; padding:5px;" dataField="formTreeList"
					idField="index" textField="name" parentField="pid"
					resultAsTree="false" allowSelect="true" enableHotTrack="false" showTreeLines="true" expandOnNodeClick="true"
					onnodeclick="getSelectNode" ondrawnode="setIcon">
				</ul>
			</div>
		</div>
    </div>
    <div showCollapseButton="false">
        <div class="nui-toolbar" style="text-align:left;line-height:30px;padding:5px 0px 5px 10px;"  borderStyle="border-left:0;border-top:0;border-right:0;border-foot:0;">
			<a class="nui-button" iconCls="icon-save" onclick="saving();"> 保存</a>
		</div>
	    <div class="nui-fit">
		 	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" allowResize="false"  borderStyle="border-left:0;border-top:0;border-right:0;border-bottom:0;">
			    <div showCollapseButton="false" size="60%">
			    	<div class="nui-fit">
			        <div id="formFieldAuthTree" class="nui-treegrid"  borderStyle="border-left:0;border-top:0;border-right:0;border-bottom:0;" style="width:100%; height:100%;"
						idField="id" treeColumn="name" nodesField="child" resultAsTree="true"
						showTreeIcon="false" allowResize="false" fitColumns="true" allowRowSelect="false" allowCellSelect="false" allowAlternating="true" allowCellEdit="true"
						expandOnLoad="true" showModified="false">
						<div property="columns">
							<div headerAlign="center" name="name" field="name">字段</div>
							<div type="checkboxcolumn" field="status" trueValue="0" falseValue="">未授权</div>
							<div type="checkboxcolumn" field="status" trueValue="1" falseValue="">不可编辑</div>
							<div type="checkboxcolumn" field="status" trueValue="2" falseValue="">不可见</div>
						</div>
					</div>
					</div>
			    </div>
				<div showCollapseButton="false" size="40%">
					<div class="nui-fit">
			        <div id="formActionAuthGrid" class="nui-datagrid"
						showHeader="false" style="width:100%;height:100%;" borderStyle="border-left:0;border-top:0;border-right:0;border-bottom:0;"
						allowRowSelect="false" allowCellSelect="false" allowAlternating="true" allowCellEdit="true" showModified="false" showPager="false"
						oncellclick="initCell">
						<div property="columns">
							<div headerAlign="center"  name="name" field="name" width="160" >操作</div>
							<div type="checkboxcolumn" field="status" trueValue="2" falseValue="" width="80">可见</div>
							<div type="checkboxcolumn" field="status" trueValue="1" falseValue="" width="80">不可见</div>
						</div>
					</div>
					</div>
			    </div>        
			</div>
		</div>
    </div>        
</div>
</div>
</body>
</html>
<script type="text/javascript">
	nui.parse();
	var formtree = nui.get("formtree");
	var formFieldAuthTree = nui.get("formFieldAuthTree");
	var formActionAuthGrid = nui.get("formActionAuthGrid");
	formFieldAuthTree.loadData([]);
	formActionAuthGrid.loadData([]);
	var i = 0;

	function setIcon(e){
		if(e.node.type == "CAPFORMSTATE"){
			e.iconCls = "icon-application-home";
		} else if(e.node.type == "module"){
			e.iconCls = "icon-application";
		} else if(e.node.type == null){
			e.iconCls = "icon-menu-root";
		}
	}

	function search(){
		var filtedNodes = [];
		var key = nui.get("key").getValue();
		if(key == ""){
			formtree.clearFilter();
		}else{
			var rootNode = formtree.getRootNode();
			formtree.cascadeChild(
				rootNode,
				function(node){
					var pNode = formtree.getParentNode(node);
					var nofind = true;
					for(i = 0; i < filtedNodes.length; i++){
						if(filtedNodes[i] == pNode.id){
							filtedNodes.push(node.id);
							nofind = false;
						}
					}
					if(nofind){
						var text = node.name ? node.name.toLowerCase() : "";
						if(text.indexOf(key) != -1){
							filtedNodes.push(node.id);
						}
					}
				}
			);
			formtree.filter(function(node){
				for(i = 0; i < filtedNodes.length; i++){
					if(filtedNodes[i] == node.id){
						return true;
					}
				}
			});
		}
	}

	function expandAll(){
		formtree.expandAll();
	}

	function collapseAll(){
		formtree.collapseAll();
	}

	function getSelectNode(e){
		var currentNode = e.node;
		var parentNode = formtree.getParentNode(currentNode);
		var moduleNode;
		formtree.bubbleParent(
			currentNode,
			function(node){
				var nodeLevel = formtree.getLevel(node);
				if(nodeLevel < 1){
					moduleNode = node;
					return false;
				}
			}
		);
		var sendData = {"roleId":"<%= request.getParameter("roleId") %>", "moduleId":moduleNode.id, "formId":parentNode.id, "formStatusId":currentNode.id};
		if(formtree.isLeaf(currentNode) && currentNode.type == "CAPFORMSTATE"){
			$.ajax({
                url: "org.gocom.components.coframe.resource.formAuth.getFormAuthStatus.biz.ext",
                type: 'POST',
                data: nui.encode(sendData),
                cache: false,
                contentType:'text/json',
                success: function (text) {
                    var json = nui.decode(text);
                    formFieldAuthTree.loadData(json.fields);
                    for(var cursor = 0; cursor < json.actions.length; cursor++){
                    	if(json.actions[cursor].status == ""){
                    		json.actions[cursor].status = "2";
                    	}
                    }
                    formActionAuthGrid.loadData(json.actions);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });
		}else{
			return false;
		}
	}

	function initCell(e){
		if(e.record.status == false){
			e.record.status = "2";
			e.sender.updateRow(e.record, {});
		}
	}

	function saving(){
		var fieldNodeArr = [];
		formFieldAuthTree.cascadeChild(formFieldAuthTree.getRootNode(), function(node){
			var nodeState = node.status;
			if(nodeState == ""){
				nodeState = "0";
			}
			fieldNode = {resourceID:node.id, state:nodeState, resourceType:node.type};
			fieldNodeArr.push(fieldNode);
		});
		var records = formActionAuthGrid.getData();
		for(var cursor = 0; cursor < records.length; cursor++){
			fieldNode = {resourceID:records[cursor].id, state:records[cursor].status, resourceType:records[cursor].type}; 
			fieldNodeArr.push(fieldNode);
		}
		var sendData = {roleId:"<%= request.getParameter("roleId") %>", authRes:fieldNodeArr};
		$.ajax({
			url:"org.gocom.components.coframe.resource.formAuth.saveFormAuths.biz.ext",
			type: "POST",
			data: nui.encode(sendData),
			cache: false,
			contentType: "text/json",
			success: function(text){
				var returnJson = nui.decode(text);
				if(returnJson.saveResult){
					nui.alert("权限设置成功");
				}else{
					nui.alert("权限设置失败");
				}
			},
			error: function(jqXHR, textStatus, errorThrown){}
		});
	}
</script>
