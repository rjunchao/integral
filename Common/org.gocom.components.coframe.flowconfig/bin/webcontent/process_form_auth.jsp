<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@include file="/coframe/tools/skins/common.jsp" %>
<!-- 
  - Author(s): wanghl
  - Date: 2013-03-05 20:08:33
  - Description:
-->
<head>
<title>Title</title>
</head>
<body>
<div class="nui-fit" style="padding:5px;">
	<div class="nui-splitter" style="width:100%;height:90%;" allowResize="true">
	    <div size="40%" showCollapseButton="false">
	        <div class="nui-fit" style="padding:2px;">
	        	<div id="tree1" class="nui-tree"
			        ajaxData="getTreeData" textField="name" idField="id" resultAsTree="true" 
			        dataField="conditions" nodesField="children" contextMenu="#treeMenu" allowDrop="true" ondrawnode="setIcon"
			        >
			    </div>
			    <ul id="treeMenu" class="nui-contextmenu"  onbeforeopen="onBeforeOpen"></ul>
			</div>
	    </div>
	    <div showCollapseButton="false">
		    <div class="nui-fit">
			 	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" allowResize="false"  borderStyle="border-left:0;border-top:0;border-right:0;border-bottom:0;">
				    <div showCollapseButton="false" size="60%">
				    	<div class="nui-fit">
				        <div id="formFieldAuthTree" class="nui-treegrid"  borderStyle="border-left:0;border-top:0;border-right-width:0px;;border-bottom:0;" style="width:100%; height:100%;"
							idField="id" treeColumn="name" nodesField="child" resultAsTree="true"
							showTreeIcon="false" allowResize="false" fitColumns="true" allowRowSelect="false" allowCellSelect="false" allowAlternating="true" allowCellEdit="true"
							expandOnLoad="true" showModified="false">
							<div property="columns">
								<div headerAlign="center" width="40" name="name" field="name">字段</div>
								<div type="checkboxcolumn" field="status" trueValue="0" width="20" falseValue="">未授权</div>
								<div type="checkboxcolumn" field="status" trueValue="1" width="20" falseValue="">不可编辑</div>
								<div type="checkboxcolumn" field="status" trueValue="2" width="20" falseValue="">不可见</div>
							</div>
						</div>
						</div>
				    </div>
					<div showCollapseButton="false" size="40%">
						<div class="nui-fit">
				        <div id="formActionAuthGrid" class="nui-datagrid"
							showHeader="false" style="width:100%;height:100%;" borderStyle="border-left:0;border-top:0;border-right:0;border-bottom:0;"
							allowRowSelect="false" allowCellSelect="false" allowAlternating="true" allowCellEdit="true" showModified="false" showPager="false">
							<div property="columns">
								<div headerAlign="center"  name="name" field="name" width="40" >操作</div>
								<div type="checkboxcolumn" field="status" trueValue="2" falseValue="" width="20">可见</div>
								<div type="checkboxcolumn" field="status" trueValue="1" falseValue="" width="20">不可见</div>
							</div>
						</div>
						</div>
				    </div>        
				</div>
			</div>
	    </div>        
	</div>
	<div style="width:100%;height:10%">
		<div class="nui-toolbar" style="width:99%;height:90%;text-align:center;" borderStyle="border:0;">
			<div style="padding-top:10px;">
		            <a class="nui-button" iconCls="icon-save" onclick="saveRule">保存</a>
		            <span style="display:inline-block;width:25px;"></span>
		            <a class="nui-button" iconCls="icon-cancel" onclick="onCancel">取消</a>
		    </div>
		</div>
	</div>
</div>
</body>

<script type="text/javascript">
		nui.parse();
		
		var tree = nui.get("tree1");
		var formFieldAuthTree = nui.get("formFieldAuthTree");
		var formActionAuthGrid = nui.get("formActionAuthGrid");
		formFieldAuthTree.loadData([]);
		formActionAuthGrid.loadData([]);
		
		function setIcon(e){
			if(e.node.name == "并且"){
				e.iconCls = "icon-and";
			}else if(e.node.name == "或者"){
				e.iconCls = "icon-or";
			}else{
				e.iconCls = "icon-condition";
			}
		}
		
		
		//树加载时传入参数
		function getTreeData(){
			return {ruleId: ruleId};
		}
		
		var loadParam = {};
		var ruleId = "";
		var ruleName = "";
		var ruleType = "";
		var namespace = "";
		var createFlag = "";
		var proc = {};
		
		//页面刚开始时加载树数据
		function loadData(data){
			if(data){
				loadParam = nui.clone(data);
				proc = loadParam.proc;
				ruleName = loadParam.ruleName;
				createFlag = loadParam.createFlag;
				if(data.ruleId){
					ruleId = loadParam.ruleId;
					ruleName = loadParam.ruleName;
					namespace = loadParam.namespace;
					tree.load("org.gocom.components.coframe.flowconfig.processMgr.getRuleCondition.biz.ext");
				}else{
					var rootNode = [{id: "11", name: "并且", operator: "and"}];
					tree.loadData(rootNode);
				}
				getformAuthStatus();
			}
		}
		
		//添加或者节点
		function addOrOper(){
			var childNode = {};
			childNode.name = "或者";
			childNode.operator = "or";
			childNode.children = [ ];
			addNode(childNode);
		}
		
		//添加并且节点
		function addAndOper(){
			var childNode = {};
			childNode.name = "并且";
			childNode.operator = "and";
			addNode(childNode);
		}
		
		//添加节点
		function addNode(childNode){
			var node = tree.getSelectedNode();
			tree.addNode(childNode, "add", node);
			tree.expandNode(node);
		}
		
		function removeNode(){
			var node = tree.getSelectedNode();
			if (node) {
                if (confirm("确定删除选中节点?")) {
                    tree.removeNode(node);
                }
            }
		}
		
		//从树上加载数据
		function editTreeData(){
			var data = tree.getData();
			var sdata =  deleteExtraProperty(data);
			return sdata;
		}
		
		//删除tree.getData等多余的属性
		function deleteExtraProperty(data){
			for(var i = 0; i < data.length; i++){
				if(data[i].children && data[i].children.length != 0){
					deleteExtraProperty(data[i].children);
				}
				var retData = {id: "", name: "", operator: "", leftId: "", condition: "", rightId: "", extension: [], children: []};
				if(data[i].id){
					retData.id = data[i].id;
				}
				if(data[i].name){
					retData.name = data[i].name;
				}
				if(data[i].operator){
					retData.operator = data[i].operator;
				}
				if(data[i].leftId){
					retData.leftId = data[i].leftId;
				}
				if(data[i].condition){
					retData.condition = data[i].condition;
				}
				if(data[i].rightId){
					retData.rightId = data[i].rightId;
				}
				if(data[i].extension){
					retData.extension = data[i].extension;
				}
				if(data[i].children){
					retData.children = data[i].children;
				}
				data[i] = retData;
			}
			return data;
		}
		
		//新建条件
		var ConditionNode; //条件节点数据
		function addCondition(){
			var node = tree.getSelectedNode();
			nui.open({
				url: "<%=contextPath%>/coframe/flowconfig/edit_condition.jsp",
				title:"新建条件", width:"370px" , height:"470px",
				onload: function(){
	            	var iframe = this.getIFrameEl();
	                iframe.contentWindow.setData();
	            },
	            ondestroy: function(action){
		            if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var retData = iframe.contentWindow.getData();
	                        retData = nui.clone(retData);    //必须
	                        if(retData){
	                        	ConditionNode = retData;
	                        	addNode(ConditionNode, "add", node);
	                        }
	                }
	            }
			});
		}
		
		//编辑条件
		function editCondition(){
			var node = tree.getSelectedNode();
			nui.open({
				url: "<%=contextPath%>/coframe/flowconfig/edit_condition.jsp",
				title:"编辑条件", width:"370px" , height:"470px",
				allowResize:false,
				onload: function(){
	            	var iframe = this.getIFrameEl();
	                iframe.contentWindow.setData(node);
	            },
	            ondestroy: function(action){
		            if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var retData = iframe.contentWindow.getData();
	                        retData = nui.clone(retData);    //必须
	                        if(retData){
	                        	ConditionNode = retData;
	                        	tree.updateNode(node, ConditionNode);
	                        }
	                }
	            }
			});
		}
		
		//编辑运算逻辑
		function editOper(){
			var node = tree.getSelectedNode();
			var data = node.name;
			nui.open({
				url: "<%=contextPath%>/coframe/flowconfig/edit_oper.jsp",
				title:"编辑运算逻辑", width:"150px" , height:"130px",
				onload: function(){
	            	var iframe = this.getIFrameEl();
	                iframe.contentWindow.setData(node);
	            },
	            ondestroy: function(action){
		            if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var retData = iframe.contentWindow.getData();
	                        retData = nui.clone(retData);    //必须
	                        if(retData){
	                        	node.name = retData.text;
	                        	node.operator = retData.id;
	                        	tree.updateNode(node, node);
	                        }
	                }
	            }
			});
		}
		
		//将并且"关系转换为“或者”关系
		function ChangeAndToOr(){
			var node = tree.getSelectedNode();
			node.name = "或者";
			node.operator = "or";
			tree.updateNode(node, node);
		}
		
		//将“或者”关系转换为“并且”关系
		function ChangeOrToAnd(){
			var node = tree.getSelectedNode();
			node.name = "并且";
			node.operator = "and";
			tree.updateNode(node, node);
		}
		
		//菜单加载前操作
		function onBeforeOpen(e) {
		    var menu = e.sender;
		    
		    var node = tree.getSelectedNode();
		    
		    if (!node) {
		        e.cancel = true;
		        return;
		    }else if(node._level == 0){
		     	e.htmlEvent.preventDefault();
		     	var editTitle = "";
		     	var editEvent = "";
		     	var iconCls = "";
		     	if(node.name == "或者"){
		     		editTitle = "转换为并且关系";
		     		editEvent = "ChangeOrToAnd";
		     		editIconCls = "icon-and";
		     	}else{
		     		editTitle = "转换为或者关系";
		     		editEvent = "ChangeAndToOr";
		     		editIconCls = "icon-or";
		     	}
		    	var array = [
		        				{id: "createNewRelation", text: "新建并且关系", iconCls:"icon-and", onclick:"addAndOper"},
		        				{id: "createNewRelation", text: "新建或者关系", iconCls:"icon-or", onclick:"addOrOper"},
		        				{id: "createNewCondition", text: "新建条件", iconCls:"icon-condition", onclick:"addCondition"},
		        				{id: "test"  ,text: "", cls: "nui-separator"},
		        				{id: "eidt", text:editTitle, iconCls:editIconCls, onclick:editEvent}
		        			];
		        menu.loadList(array);
		    	return ;
		    }else if (node.name == "并且") {
		        //阻止浏览器默认右键菜单
		        e.htmlEvent.preventDefault();
		        var array = [
		        				{id: "createNewRelation", text: "新建并且关系", iconCls:"icon-and", onclick:"addAndOper"},
		        				{id: "createNewRelation", text: "新建或者关系", iconCls:"icon-or", onclick:"addOrOper"},
		        				{id: "createNewCondition", text: "新建条件", iconCls:"icon-condition", onclick:"addCondition"},
		        				{id: "test"  ,text: "", cls: "nui-separator"},
		        				{id: "eidt", text:"转换为或者关系", iconCls:"icon-or", onclick:"ChangeAndToOr"},
		        				{id: "delete", text:"删除", iconCls:"icon-remove", onclick: "removeNode"}
		        			];
		        menu.loadList(array);
		        return;
		    }else if (node.name == "或者") {
		        //阻止浏览器默认右键菜单
		        e.htmlEvent.preventDefault();
		        var array = [
		        				{id: "createNewRelation", text: "新建并且关系", iconCls:"icon-and", onclick:"addAndOper"},
		        				{id: "createNewRelation", text: "新建或者关系", iconCls:"icon-or", onclick:"addOrOper"},
		        				{id: "createNewCondition", text: "新建条件", iconCls:"icon-condition", onclick:"addCondition"},
		        				{id: "test"  ,text: "", cls: "nui-separator"},
		        				{id: "eidt", text:"转换为并且关系", iconCls:"icon-and", onclick:"ChangeOrToAnd"},
		        				{id: "delete", text:"删除", iconCls:"icon-remove", onclick: "removeNode"}
		        			];
		        menu.loadList(array);
		        return;
		    }else{
		        //阻止浏览器默认右键菜单
		        e.htmlEvent.preventDefault();
		        var array = [
		        				{id: "eidt", text:"编辑", iconCls:"icon-condition", onclick:"editCondition"},
		        				{id: "delete", text:"删除", iconCls:"icon-remove", onclick: "removeNode"}
		        			];
		        menu.loadList(array);
		        return;
		    }
		    
		}
		
		//获取权限资源数据
		function getAuthRes(){
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
			return fieldNodeArr;
		}
		
		
		//保存
		function saveRule(){
			
			var treeData = editTreeData();
			var authRes = getAuthRes();
			var saveData = {fc:treeData, ruleId: ruleId, ruleName: ruleName, namespace: namespace, authRes: authRes, createFlag: createFlag};
			var submitData = nui.encode(saveData);
			$.ajax({
				url: "org.gocom.components.coframe.flowconfig.processMgr.saveRule.biz.ext",
				type: "POST",
				data: submitData,
				cache: false,
           		contentType: 'text/json',
				success: function(text){
					if(text.ret == 0){
						alert("保存成功");
						window.CloseOwnerWindow("ok");
					}else{
						alert("保存失败");
					}
					
				}
			});
		}
		
		function onCancel() {
	        window.CloseOwnerWindow("cancel");
	    }
		
		//表单授权部分
		/////////////////////////////////////////////////////////////////////
		
		function getformAuthStatus(){
			var sendData = {"ruleId": ruleId, "proc": proc};
			$.ajax({
	            url: "org.gocom.components.coframe.flowconfig.processMgr.getFormAuthStatus.biz.ext",
	            type: 'POST',
	            data: nui.encode(sendData),
	            cache: false,
	            contentType:'text/json',
	            success: function (text) {
	                var json = nui.decode(text);
	                namespace = json.retProc.nameSpace;
	                ruleId = json.retRuleId;
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
		}
		
		
</script>
    
</html>