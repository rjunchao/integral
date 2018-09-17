<%@page pageEncoding="UTF-8" import="org.gocom.components.coframe.entityauth.pojo.ConstantPool"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@include file="/coframe/tools/skins/common.jsp" %>
<!-- 
  - Author(s): lijt (mailto:lijt@primeton.com)
  - Date: 2013-04-16 09:10:36
  - Description:添加实体规则
-->
<head>
	<title>添加实体规则</title>
	<style type="text/css">
		#ruleInfoEditTable {table-layout:fixed;text-align: left;border:0px;height:auto;width:100%;margin:10px;}
		#ruleInfoEditTable tr {line-height:30px;height:30px;}
		#ruleInfoEditTable tr td {line-height:30px;height:30px;}
		#ruleInfoEditTable tr .label{width:80px;text-align: right;}
		
		#conditionEditTable{table-layout:fixed;text-align: left;border:0px;height:auto;width:100%;}
		#conditionEditTable tr {line-height:40px;height:40px;}
		#conditionEditTable tr td {line-height:40px;height:40px;}
		#conditionEditTable tr .label{width:150px;text-align: right;}
		.mini-splitter-pane2-vertical .mini-splitter-border{border-left:0px;border-right:0px;border-bottom:0px;}
	</style>
</head>
<body>
<div class="nui-fit" style="padding:5px;">
	<div class="nui-splitter" vertical="true" style="width:100%;height:90%;" allowResize="false">
		<div size="25%" showCollapseButton="false" id="form1">
			<table id="ruleInfoEditTable">
				<tr>
    				<td class="label">实体名称：</td><td colspan='3'><span id="qNameSpan"></span></td>
  				</tr>
  				<tr>
    				<td class="label" style="">规则名称：</td><td><input id="ruleName" name="ruleName" style="width:230px;" class="nui-textbox" required="true" maxLength="32"/></td><td class="label">规则类型：</td><td><%=ConstantPool.RULE_TYPE %></td>
  				</tr>
			</table>
		</div>
		<div size="75%" showCollapseButton="false" style="border:0px;">
			<div class="nui-splitter" style="width:100%;height:100%;" allowResize="true">
			    <div size="50%" showCollapseButton="false">
	        		<div class="nui-fit" style="padding:2px;">
	        			<div id="tree1" class="nui-tree"
			        		ajaxData="getTreeData" textField="name" idField="id" resultAsTree="true" 
			        		dataField="conditions" nodesField="children" contextMenu="#treeMenu" allowDrop="true" ondrawnode="setIcon" onnodeselect="nodeSelect"
			        		>
			    		</div>
			    		<ul id="treeMenu" class="nui-contextmenu"  onbeforeopen="onBeforeOpen"></ul>
					</div>
	    		</div>
	    		<div showCollapseButton="false">
		    		<div class="nui-fit">
						<div style="padding:5px">
							<div id="form2" style="display:none">
	        					<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table" >
	            					<tr>
	                					<td class="nui-form-label">左值：</td>
	                					<td>    
	                    					<input id="leftCombo" class="nui-combobox" textField="hierarchialName" valueField="hierarchialName" onvaluechanged="leftValueChange" required="true"/>
	                					</td>
	            					</tr>
	            					<tr>
	            						<td class="nui-form-label">条件：</td>
	                					<td>
	                						<input id="conCombo" class="nui-combobox" textField="name" valueField="value" onvaluechanged="conValueChange" required="true"/>   
	                					</td>
	            					</tr>
	            					<tr>
	                					<td class="nui-form-label">右值：</td>
	                					<td> 
	                						<input id="rightCombo" class="nui-combobox" textField="name" valueField="id" onvaluechanged="rightValueChange" allowInput="true" valueFromSelect="false" required="true"/> 
	                					</td>
	            					</tr>
	            					<tr>
	                					<td colspan="2" align="center">
	                						<div id="conditionTip">提示：左值为<span id="leftTypeId">X</span>类型，请选择或填写正确的右值</div>
	                					</td>
	            					</tr>
	        					</table>
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
		
		var loadParam = {};
		var ruleName = "";
		var partyId = "";
		var partyType = "";
		var qName = "";
		
		var leftCombo = "";
        var conCombo = "";
        var rightCombo = "";
        var leftComboData = null;
		
		var tree = nui.get("tree1");
		
		function setIcon(e){
			if(e.node.name == "并且"){
				e.iconCls = "icon-and";
			}else if(e.node.name == "或者"){
				e.iconCls = "icon-or";
			}else{
				e.iconCls = "icon-condition";
			}
		}
		
		function getPropertyType(propertyValue) {
			if(leftComboData != null) {
				var type = "";
				$.each(leftComboData,function(n, obj) { 
					if(obj.hierarchialName == propertyValue) {
						type = obj.typeName;
					}
	           	});
	           	return type;
			}
		}
		
		function changeTip(propertyType) {
			var tipString = "";
			if(leftCombo.getValue() == null) {
				$('#conditionTip').hide();
			} else {
				if(propertyType == "String" || propertyType == "ClobString") {
					tipString = "提示：左值为<span id='leftTypeId'>" + propertyType + "</span>类型，右值填写格式：zhangsan";
				} else if(propertyType == "Int" || propertyType == "BigInteger" || propertyType == "Long" || propertyType == "Short") {
					tipString = "提示：左值为<span id='leftTypeId'>" + propertyType + "</span>类型，右值填写格式：89757";
				} else if(propertyType == "Float" || propertyType == "Double" || propertyType == "Decimal"){
					tipString = "提示：左值为<span id='leftTypeId'>" + propertyType + "</span>类型，右值填写格式：1000或1000.0";
				} else if(propertyType == "Boolean") {
					tipString = "提示：左值为<span id='leftTypeId'>" + propertyType + "</span>类型，右值填写格式：1(true)/0(false)";
				} else if(propertyType == "Date") {
					tipString = "提示：左值为<span id='leftTypeId'>" + propertyType + "</span>类型，右值填写格式：yyyy-MM-dd";
				} else if(propertyType == "Time") {
					tipString = "提示：左值为<span id='leftTypeId'>" + propertyType + "</span>类型，右值填写格式：hh:mm:ss";
				} else if(propertyType == "TimeStamp") {
					tipString = "提示：左值为<span id='leftTypeId'>" + propertyType + "</span>类型，右值填写格式：yyyy-MM-dd hh:mm:ss";
				} else {
					tipString = "提示：左值为<span id='leftTypeId'>" + propertyType + "</span>类型，右值请填写正确格式";
				}
				if(conCombo.getValue() != null ) {
					if("in" == conCombo.getValue() || "notin" == conCombo.getValue()) {
						tipString += "，多个值用逗号分隔"; 
					}
				}
				$('#conditionTip').html(tipString);
				$('#conditionTip').show();
			}
		}
		
		$('#conditionTip').hide();
		
		function nodeSelect(e) {
			if(e.node.name == "并且" || e.node.name == "或者"){
				leftCombo.setValue(null);
				conCombo.setValue(null);
				rightCombo.setValue(null);
				$("#form2").hide();
			} else {
				$("#form2").show();
				var node = tree.getSelectedNode();
				var propertyType = null;
				leftCombo.setValue(node.leftId);
				if(node.leftId != null) {
         			setConCombo();
         			var propertyType = getPropertyType(node.leftId);
         			changeTip(propertyType);
         		} else {
         			$('#conditionTip').hide();
         		}
         		
				conCombo.setValue(node.condition);
				
				setRightCombo();
				rightCombo.setValue(node.rightId);
				var items = rightCombo.findItems(node.rightId);
	            if (!items || items.length == 0) {
	               if(propertyType == "Date") {
	            		rightCombo.setText(node.rightId);
	            	} else if(propertyType == "TimeStamp") {
	            		rightCombo.setText(node.rightId);
	            	} else if(propertyType == "Time") {
	            		rightCombo.setText(node.rightId);
	            	} else {
	            		rightCombo.setText(node.rightId);
	            	}
	            }
	            
				var form2 = new nui.Form("#form2");
            	form2.validate();
            	
            	if(node.condition == "null" || node.condition == "notnull") {
            		rightCombo.disable();
            		$('#conditionTip').hide();
            	} else {
            		rightCombo.enable();
            		var propertyType = getPropertyType(node.leftId);
         			changeTip(propertyType);
            	}
			}
		}
		
		//页面刚开始时加载树数据
		function loadData(data){
			if(data){
				loadParam = nui.clone(data);
				//ruleName = loadParam.ruleName;
				partyId = loadParam.resauth.partyId;
				partyType = loadParam.resauth.partyType;
				qName = loadParam.qName;
				$("#qNameSpan").text(qName);
				
				leftCombo = nui.get("leftCombo");
        		conCombo = nui.get("conCombo");
        		rightCombo = nui.get("rightCombo");
				
				var rootNode = [{id:null, name:"并且", operator:"and", leftId:null, condition:null, rightId:null, extension:[]}];
				tree.loadData(rootNode);
				
				setLeftCombo();
				setConCombo();
				setRightCombo();
			}
		}
		
		function setLeftCombo(){
			var lastPointIndex = qName.lastIndexOf(".");
			namespace = qName.substr(0, lastPointIndex);
			entityName = qName.substr(lastPointIndex+1, qName.length);
			var dataObj = { namespace : namespace, entityName : entityName };
        	$.ajax({
        		url: "org.gocom.components.coframe.entityauth.entity.getEntityInfoProperties.biz.ext",
        		type: "POST",
        		data: nui.encode(dataObj),
        		contentType: "text/json",
        		async:false,
        		success: function(text){
        			leftComboData = nui.decode(text).propertyInfos;
        			leftCombo.setData(leftComboData);
        		}
        	});
        }
        
        function setConCombo(){
        	var propertyName = leftCombo.getValue();
        	var sendData = {"namespace":namespace,"entityName":entityName,"propertyName":propertyName};
        	$.ajax({
        		url: "org.gocom.components.coframe.entityauth.rule.getConValues.biz.ext",
        		type: "POST",
        		data: nui.encode(sendData),
        		contentType: "text/json",
        		async:false,
        		success: function(text){
        			var comboData = nui.decode(text).conValues;
        			conCombo.setData(comboData);
        		}
        	});
        }
        
         function setRightCombo(){
         	var conText = conCombo.getText();
         	
     		var propertyName = leftCombo.getValue();
        	var sendData = {"namespace":namespace,"entityName":entityName,"propertyName":propertyName};
         	$.ajax({
        		url: "org.gocom.components.coframe.entityauth.rule.getRightValues.biz.ext",
        		type: "POST",
        		data: nui.encode(sendData),
        		contentType: "text/json",
        		async:false,
        		success: function(text){
        			comboData = nui.decode(text).rightValues;
        			rightCombo.setData(comboData);
        		}
        	});
        	
        	if(conText=='null' || conText=='notnull') {
         		rightCombo.setValue(conText);
         		rightCombo.setText(conText);
         	}
         }
         
        function leftValueChange() {
        	setConCombo();
        	setRightCombo();
         	var node = tree.getSelectedNode();
         	
         	var leftText = leftCombo.getText();
         	var conText = conCombo.getText();
         	var rightText = rightCombo.getText()
         	if(leftText == null || leftText == "") {
         		leftText = "左值";
         	}
         	if(conText == null || conText == "") {
         		conText = "条件";
         	}
         	if(rightText == null || rightText == "") {
         		rightText = "右值";
         	}
			node.name = leftText + "_" + conText + "_" + rightText;
         	node.leftId=leftCombo.getValue();
         	var propertyType = getPropertyType(node.leftId);
         	changeTip(propertyType);
         	tree.updateNode(node,{name:node.name,leftId:node.leftId});
        }
		
		function conValueChange() {
			var node = tree.getSelectedNode();
			var propertyType = getPropertyType(node.leftId);
         	var leftText = leftCombo.getText();
         	var conText = conCombo.getText();
         	var rightText = rightCombo.getText()
         	if(leftText == null || leftText == "") {
         		leftText = "左值";
         		rightCombo.enable();
         	}
         	if(conText == null || conText == "") {
         		conText = "条件";
         		changeTip(propertyType);
         	} else if(conText=="null") {
         		rightCombo.setValue("null");
         		rightCombo.setText("null");
         		rightText="null";
         		node.rightId=rightText;
         		rightCombo.disable();
         		$('#conditionTip').hide();
         	} else if(conText=="notnull") {
         		rightCombo.setValue("notnull");
         		rightCombo.setText("notnull");
         		rightText="notnull";
         		node.rightId=rightText;
         		rightCombo.disable();
         		$('#conditionTip').hide();
         	} else {
         		rightCombo.enable();
         		changeTip(propertyType);
         	}
         	if(rightText == null || rightText == "") {
         		rightText = "右值";
         	}
			node.name = leftText + "_" + conText + "_" + rightText;
         	node.condition=conCombo.getValue();
         	tree.updateNode(node,{name:node.name,condition:node.condition});
         	
			//name:'左值_条件_右值',extension:[],leftId:null,rightId:null,condition:null
		}
		
		function rightValueChange() {
			var node = tree.getSelectedNode();
         	
         	var leftText = leftCombo.getText();
         	var conText = conCombo.getText();
         	var rightText = rightCombo.getText()
         	if(leftText == null || leftText == "") {
         		leftText = "左值";
         	}
         	if(conText == null || conText == "") {
         		conText = "条件";
         	}
         	if(rightText == null || rightText == "") {
         		rightText = "右值";
         	}
			node.name = leftText + "_" + conText + "_" + rightText;
         	node.rightId=rightCombo.getValue();
         	tree.updateNode(node,{name:node.name,rightId:node.rightId});
		}
		
		
		//树加载时传入参数
		function getTreeData(){
			return {ruleId: ruleId};
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
			tree.selectNode(childNode);
		}
		
		function removeNode(){
			var node = tree.getSelectedNode();
			if (node) {
				
				nui.confirm("确定删除选中节点？", "提示",
            		function (action) {            
                		if (action == "ok") {
                    		 tree.removeNode(node);
                    		 leftCombo.setValue(null);
							 conCombo.setValue(null);
							 rightCombo.setValue(null);
							 $("#form2").hide();
                		}
            		}
        		);
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
			$("#form2").show();
			addNode({id:null,name:'左值_条件_右值',extension:[],leftId:null,rightId:null,condition:null,operator:null}, "add", node);
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
		        				{id: "delete", text:"删除", iconCls:"icon-remove", onclick: "removeNode"}
		        			];
		        menu.loadList(array);
		        return;
		    }
		    
		}
		
		function isInteger( str ) {
		    var regu = /^[-]{0,1}[0-9]{1,}$/;
		    return regu.test(str);
		};
		
		function isDecimal( str ) {
		    if (isInteger(str)) {
		        return true;
		    }
		    var re = /^[-]{0,1}(\d+)[\.]+(\d+)$/;
		    if (re.test(str)) {
		        if (RegExp.$1 == 0 && RegExp.$2 == 0) {
		            return false;
		        }
		        return true;
		    }
		    else {
		        return false;
		    }
		};
		
		function isChinaOrNumbOrLett( s ){
		    //判断是否是汉字、字母、数字组成
		    var regu = "^[0-9a-zA-Z_\u4e00-\u9fa5]+$";
		    var re = new RegExp(regu);
		    if (re.test(s)) {
		        return true;
		    }
		    else {
		        return false;
		    }
		};
		
		function isDate(value){
			var regu = /^(\d{4})-(\d{2})-(\d{2})$/
			if (!regu.test(value)) { 
				return false;
			} else {
				return true;
			} 
		}
		
		function isTime(value){
			var regu = /^(\d{2}):(\d{2}):(\d{2})$/
			if (!regu.test(value)) { 
				return false;
			} else {
				return true;
			} 
		}
		
		function isDateTime(value){
			var regu = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/
			if (!regu.test(value)) { 
				return false;
			} else {
				return true;
			} 
		}
		
		function findUnLegalNodes() {        
            var tree = nui.get("tree1");
            var nodes = tree.findNodes(function (node) {
            	
            	var constantArray = [
	            	'com.primeton.cap.defaultVaule.userName',
	            	'com.primeton.cap.defaultVaule.empId',
	            	'com.primeton.cap.defaultVaule.userId',
	            	'com.primeton.cap.defaultVaule.userMail',
	            	'com.primeton.cap.defaultVaule.uniqueId',
	            	'com.primeton.cap.defaultVaule.userOrgId',
	            	'com.primeton.cap.defaultVaule.userOrgName',
	            	'com.primeton.cap.defaultVaule.userRealName',
	            	'com.primeton.cap.defaultVaule.userRemoteIP'
            	];
            	
            	for(var i=0; i<constantArray.length; i++) {
            		if(constantArray[i] == node.rightId) {
            			return false;
            		}
            	}
            	
            	var conditionArray = [
            		'null',
            		'notnull'
            	];
            	
            	for(var i=0; i<conditionArray.length; i++) {
            		if(conditionArray[i] == node.condition) {
            			return false;
            		}
            	}
            	
            	if ((node.operator != "or" && node.operator != "and") && (node.leftId == null || node.rightId==null || node.condition==null)) {
            		return true;
            	} else if ((node.operator != "or" && node.operator != "and") && (node.leftId != null && node.rightId != null && node.condition != null)){
            		var propertyType = getPropertyType(node.leftId);
            		
            		if(propertyType == "String" || propertyType == "ClobString") {
            			if(node.condition == "in" || node.condition == "notin") {
            				var rightValues = node.rightId.split(",");
            				for(var i =0; i < rightValues.length; i++) {
            					if(!isChinaOrNumbOrLett(rightValues[i])) {
		            				return true;
		            			}
            				}
            			} else {
            				if(!isChinaOrNumbOrLett(node.rightId)) {
	            				return true;
	            			}
            			}
					} else if(propertyType == "Int" || propertyType == "BigInteger" || propertyType == "Long" || propertyType == "Short") {
						if(node.condition == "in" || node.condition == "notin") {
            				var rightValues = node.rightId.split(",");
            				for(var i =0; i < rightValues.length; i++) {
		            			if(!isInteger(rightValues[i])) {
									return true;
								}
            				}
            			} else {
	            			if(!isInteger(node.rightId)) {
								return true;
							}
            			}
					} else if(propertyType == "Float" || propertyType == "Double" || propertyType == "Decimal"){
						if(node.condition == "in" || node.condition == "notin") {
            				var rightValues = node.rightId.split(",");
            				for(var i =0; i < rightValues.length; i++) {
		            			if(!isDecimal(rightValues[i])) {
									return true;
								}
            				}
            			} else {
	            			if(!isDecimal(node.rightId)) {
								return true;
							}
            			}						
					} else if(propertyType == "Boolean") {
						if((''+node.rightId) != '1' && (''+node.rightId) != '0') {
							return true;
						}						
					} else if(propertyType == "Date") {
						if(node.condition == "in" || node.condition == "notin") {
            				var rightValues = node.rightId.split(",");
            				for(var i =0; i < rightValues.length; i++) {
		            			if(!isDate(rightValues[i])) {
									return true;
								}
            				}
            			} else {
	            			if(!isDate(node.rightId)) {
								return true;
							}
            			}
					} else if(propertyType == "Time") {
						if(node.condition == "in" || node.condition == "notin") {
            				var rightValues = node.rightId.split(",");
            				for(var i =0; i < rightValues.length; i++) {
		            			if(!isTime(rightValues[i])) {
									return true;
								}
            				}
            			} else {
	            			if(!isTime(node.rightId)) {
								return true;
							}
            			}
					} else if(propertyType == "TimeStamp") {
					
						if((typeof node.rightId=='object')&&node.rightId.constructor==Date) {
	            			node.rightId = date2str(node.rightId,'yyyy-MM-dd hh:mm:ss');
	            		}
						
						if(node.condition == "in" || node.condition == "notin") {
            				var rightValues = node.rightId.split(",");
            				for(var i =0; i < rightValues.length; i++) {
		            			if(!isDateTime(rightValues[i])) {
									return true;
								}
            				}
            			} else {
	            			if(!isDateTime(node.rightId)) {
								return true;
							}
            			}
					}
            	}
            });
            return nodes;
        }
		
		//保存
		function saveRule(){
			var form = new nui.Form("#form1");
            form.validate();
            if (form.isValid() == false) {
            	return;
            }
            
            var form2 = new nui.Form("#form2");
            form2.validate();
            if(form2.isValid() == false) {
            	return;
            }
            
            var unLegalnodes = findUnLegalNodes();
            if(unLegalnodes != null && unLegalnodes.length>0) {
            	nui.alert("请完善规则条件：" + unLegalnodes[0].name);
            	return;
            }
			
			var ruleName = nui.get("ruleName").getValue();
			var treeData = editTreeData();
			// alert(JSON.stringify(treeData));
			var saveData = {ruleExpression:treeData, ruleName: ruleName, namespace: qName, party:{partyTypeID:partyType,id: partyId}};
			var submitData = nui.encode(saveData);
			$.ajax({
				url: "org.gocom.components.coframe.entityauth.rule.saveRule.biz.ext",
				type: "POST",
				data: submitData,
				cache: false,
           		contentType: 'text/json',
				success: function(text){
					if(text.flag == "success"){
						window.CloseOwnerWindow("ok");
					}else if(text.flag == "exist"){
						nui.alert("规则名称已存在");
					} else {
						nui.alert("保存失败");
					}
				}
			});
		}
		
		function onCancel() {
	        window.CloseOwnerWindow("cancel");
	    }
		
</script>
    
</html>