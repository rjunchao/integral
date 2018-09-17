<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@include file="/coframe/tools/skins/common.jsp" %>
<html>
<!-- 
  - Author(s): liuzn (mailto:liuzn@primeton.com)
  - Date: 2013-03-07 17:04:43
  - Description:
-->
<%@page import="com.eos.foundation.eoscommon.ResourcesMessageUtil"%>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>
<body>
<div class="nui-fit" style="padding:10px;">
<div class="mini-splitter" style="width:100%;height:100%;" allowResize="false">
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
				<ul id="viewtree" class="nui-tree" url="org.gocom.components.coframe.resource.view.viewAuthList.getViewTreeList.biz.ext"
					style="width:200px; padding:5px;" dataField="viewTreeList"
					idField="index" textField="name" parentField="pid"
					resultAsTree="false" allowSelect="true" enableHotTrack="false" showTreeLines="true" expandOnNodeClick="true"
					onnodeclick="selectedNode" ondrawnode="setIcon">
				</ul>
			</div>
		</div>
    </div>
    <div showCollapseButton="false"  borderStyle="border-top:0; border-bottom:0;">
    	<div class="nui-toolbar" style="text-align:left;line-height:30px;padding:5px 0px 5px 10px;" borderStyle="border-left:0;border-top:0;border-right:0;border-bottom:0;">
			<a class="nui-button" iconCls="icon-save" onclick="saving();"> 保存</a>
		</div>
		<div class="mini-splitter" vertical="true" style="width:100%;height:90%;" allowResize="false" borderStyle="border-top:0;border-left:0;border-right:0;border-bottom:0;">
		    <div size="33%" showCollapseButton="true">
		    	<div id="viewFieldGrid" class="nui-datagrid" borderStyle="border-left:0;border-top:0;border-right:0;border-bottom:0;" style="height:100%;"
					showHeader="false" showPager="false" allowRowSelect="false" allowCellSelect="false" allowAlternating="true" allowCellEdit="true" showModified="false"
					oncellclick="">
					<div property="columns">
						<div field="name" width="160" >显示字段</div>
						<div type="checkboxcolumn" field="status" trueValue="0" falseValue="" width="80">不控制</div>
						<div type="checkboxcolumn" field="status" trueValue="2" falseValue="" width="80">显示</div>
						<div type="checkboxcolumn" field="status" trueValue="1" falseValue="" width="80">不显示</div>
					</div>
				</div>
		    </div>
		    <div showCollapseButton="true" borderStyle="border-right:0;border-bottom:0;">
		        <div class="mini-splitter" vertical="true" style="width:100%;height:100%;" borderStyle="border-top:0;border-left:0;border-right:0;border-bottom:0;" allowResize="false">
				    <div size="33%" showCollapseButton="true">
				        <div id="viewActionGrid" class="nui-datagrid" borderStyle="border-left:0;border-top:0;border-right:0;border-bottom:0;" style="height:100%;"
							showHeader="false" showFooter="false" showPager="false" allowRowSelect="false" allowCellSelect="false" allowAlternating="true" allowCellEdit="true" showModified="false"
							oncellclick="initActionCell">
							<div property="columns">
								<div field="name" width="160" >操作</div>
								<div type="checkboxcolumn" field="status" trueValue="2" falseValue="" width="80">可见</div>
								<div type="checkboxcolumn" field="status" trueValue="1" falseValue="" width="80">不可见</div>
							</div>
						</div>
				    </div>
				    <div showCollapseButton="true" size="33%">
				        <div id="viewFilterGrid" class="nui-datagrid" borderStyle="border-left:0;border-top:0;border-right:0;border-bottom:15px;" style="height:80%;"
							showHeader="false" showFooter="false"  showPager="false" allowRowSelect="false" allowCellSelect="false" allowAlternating="true" allowCellEdit="true" showModified="false"
							oncellclick="initFilterCell">
							<div property="columns">
								<div field="name" width="160" >过滤器</div>
								<div type="checkboxcolumn" field="status" trueValue="3" falseValue="" width="80">不生效</div>
								<div type="checkboxcolumn" field="status" trueValue="4" falseValue="" width="80">生效</div>
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
	var viewtree = nui.get("viewtree");
	var viewFieldGrid = nui.get("viewFieldGrid");
	var viewFilterGrid = nui.get("viewFilterGrid");
	var viewActionGrid = nui.get("viewActionGrid");

	function setIcon(e){
		if(e.node.type == "module"){
			e.iconCls = "icon-application";
		} else if(e.node.type == "CAPVIEW"){
			e.iconCls = "icon-application-home";
		}
	}

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
	
	function selectedNode(e){
		var currentNode = e.node;
		var moduleNode;
		viewtree.bubbleParent(
			currentNode,
			function(node){
				var nodeLevel = viewtree.getLevel(node);
				if(nodeLevel < 1){
					moduleNode = node;
					return false;
				}
			}
		);
		var sendData = {"roleId":"<%= request.getParameter("roleId") %>", "moduleId":moduleNode.id, "viewId":currentNode.id};
		if(viewtree.isLeaf(currentNode) && currentNode.type == "CAPVIEW"){
			$.ajax({
                url: "org.gocom.components.coframe.resource.view.viewAuthList.getViewAuthStatus.biz.ext",
                type: 'POST',
                data: nui.encode(sendData),
                cache: false,
                contentType:'text/json',
                success: function (text) {
                    var json = nui.decode(text);
                    var emptyData = new Array();
                    if(json.fields){
	                    for(var cursor = 0; cursor < json.fields.length; cursor++){
	                    	if(json.fields[cursor].status == ""){
	                    		json.fields[cursor].status = "0";
	                    	}
	                    }
	                    viewFieldGrid.loadData(json.fields);
                    }else{
                    	viewFieldGrid.loadData(emptyData);
                    }
                    
                    if(json.filters){
	                    for(var cursor = 0; cursor < json.filters.length; cursor++){
	                    	if(json.filters[cursor].status == ""){
	                    		json.filters[cursor].status = "3";
	                    	}
	                    }
	                    viewFilterGrid.loadData(json.filters);
                    }else{
                    	viewFilterGrid.loadData(emptyData);
                    }
                    
                    if(json.actions){
	                    for(var cursor = 0; cursor < json.actions.length; cursor++){
	                    	if(json.actions[cursor].status == ""){
	                    		json.actions[cursor].status = "2";
	                    	}
	                    }
	                    viewActionGrid.loadData(json.actions);
                    }else{
                    	viewActionGrid.loadData(emptyData);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });
		}else{
			return false;
		}
	}

	function initActionCell(e){
		if(e.record.status == false){
			e.record.status = "2";
			e.sender.updateRow(e.record, {});
		}
	}

	function initFilterCell(e){
		if(e.record.status == false){
			e.record.status = "3";
			e.sender.updateRow(e.record, {});
		}
	}

	function saving(){
		var authReObjs = [];
		var fieldRecords = viewFieldGrid.getData();
		for(var cursor = 0; cursor < fieldRecords.length; cursor++){
			record = {resourceID:fieldRecords[cursor].id, resourceType:fieldRecords[cursor].type, state:fieldRecords[cursor].status};
			authReObjs.push(record);
		}
		var filterRecords = viewFilterGrid.getData();
		for(var cursor = 0; cursor < filterRecords.length; cursor++){
			record = {resourceID:filterRecords[cursor].id, resourceType:filterRecords[cursor].type, state:filterRecords[cursor].status};
			authReObjs.push(record);
		}
		var actionRecords = viewActionGrid.getData();
		for(var cursor = 0; cursor < actionRecords.length; cursor++){
			record = {resourceID:actionRecords[cursor].id, resourceType:actionRecords[cursor].type, state:actionRecords[cursor].status};
			authReObjs.push(record);
		}
		var sendData = {roleId:"<%= request.getParameter("roleId") %>", authRes:authReObjs};
		$.ajax({
			url:"org.gocom.components.coframe.resource.view.viewAuthList.saveViewAuths.biz.ext",
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
