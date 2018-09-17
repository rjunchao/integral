<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Author(s): yangyong
  - Date: 2013-02-28 10:14:50
  - Description:
-->
<head>
<title>权限信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/coframe/tools/skins/common.jsp" %>
</head>
<body>
<div class="nui-fit" style="padding:5px;">
	 <table style="height:99%;">
	   <tr>
	    <td style="width:120px;height:100%;">
	    	<div id="listbox1" class="nui-listbox" style="width:250px;height:100%;"
	            textField="name" valueField="id" ajaxData="getAjaxData" multiSelect="true"
	           	dataField="authorizedRoleList" onload="onAuthorizedLoad" onvaluechanged="onListBoxValueChaged" showCheckBox="true">
	         	<div property="columns">
	               <div header="已授予角色" field="name" renderer="renderAuthorizedRoleList"></div>
	            </div>
	        </div>
	    </td>
	    <td style="width:120px;height:100%;text-align:center;">
	        <input class="nui-button" text="取消" onclick="remove()" style="width:65px;margin-top:5px;"/><br />
	        <input class="nui-button" text="全部取消" onclick="removeAll()" style="width:65px;margin-top:5px;"/><br />
	        <input class="nui-button" text="全部授权" onclick="addAll()" style="width:65px;margin-top:5px;"/><br />
	        <input class="nui-button" text="授权" onclick="add()" style="width:65px;margin-top:5px;"/><br />
	    </td>
	    <td style="width:120px;height:100%;">
		   <div id="listbox2" class="nui-listbox" style="width:250px;height:100%;"
			    textField="name" valueField="id" showCheckBox="true" ajaxData="getAjaxData" multiSelect="true"
			   dataField="unauthorizedRoleList">
				 <div property="columns">
				      <div header="未授予角色" field="name"></div>
				 </div>
			</div>
	    </td>
	    </tr>
	</table>
</div>
<div id="toolBar" class="nui-toolbar" style="text-align:center;padding-top:5px;padding-bottom:5px;visibility:hidden;" 
    borderStyle="border:0;">
    <a class="nui-button" id="cancelBtn_01" iconCls="icon-cancel" style="width:60px;" onclick="cancel">关闭</a>
</div>
<script type="text/javascript">
	 nui.parse();
	
	 var listbox1 = nui.get("listbox1");
     var listbox2 = nui.get("listbox2");
     var url1="org.gocom.components.coframe.auth.partyauth.partyauth.getAuthorizedRoleList.biz.ext";
     var url2="org.gocom.components.coframe.auth.partyauth.partyauth.getUnauthorizedRoleList.biz.ext";
    
    $(function(){
		if(window.parent.getCurrentNode){ // 从Tab页进入时
			var node = window.parent.getCurrentNode();
			var parentNode = node;
			window['parentNode'] = parentNode;
			loadAuth();
		}
	});
	
	function loadAuth(){
		var party = getPartyByNode(window['parentNode']);
		if(party && party.id && party.partyTypeID) {
			listbox1.load(url1);
			listbox2.load(url2);
		}
	}
    
	function add() {
	    var items = convertToData(listbox2.getSelecteds());
	    if(items && items.length > 0){
		   	saveData(items, function(){
		   		listbox2.removeItems(items);
			   	listbox1.addItems(items);
			   	onAuthorizedLoad();
		   	});
	   	}else{
	    	nui.alert("至少选择一条记录");
	    }
	}
	
	function remove() {
	    var items = convertToData(listbox1.getSelecteds());
	    if(items && items.length > 0){
		    deleteData(items, function(){
		    	listbox1.removeItems(items);
		        listbox2.addItems(items);
		        onAuthorizedLoad();
		    });
	    }else{
	    	nui.alert("至少选择一条记录");
	    }
	}
	
	 function addAll() {
        var items = convertToData(listbox2.getData());
        if(items && items.length > 0){
	        saveData(items, function(){
		        listbox2.removeItems(items);
		        listbox1.addItems(items);
		        onAuthorizedLoad();
	        });
        }else{
	    	nui.alert("至少选择一条记录");
	    }
    }
    
    function removeAll() {
        var items = convertToData(listbox1.getData());
        if(items && items.length > 0){
	        deleteData(items, function(){
		        listbox1.removeItems(items);
		        listbox2.addItems(items);
		        onAuthorizedLoad();
	        });
        }else{
	    	nui.alert("至少选择一条记录");
	    }
    }
    
    function convertToData(items){
    	var datas = [];
    	if(!items || items.length == 0){
    		return datas;
    	}
    	for(var i=0,len=items.length;i<len;i++){
    		if(!noSelectStores[items[i].id]){
    			var item = items[i];
    			delete item.isManaged;
    			datas.push(item);
    		}
    	}
    	return datas;
    }
    
    function saveData(items, fn){
		var node = window.parentNode;
	    var json = nui.encode({
	    	party:getPartyByNode(node),
			roleList:items	    	
	    });
	    $.ajax({
	    	url: "org.gocom.components.coframe.auth.partyauth.partyauth.addPartyAuth.biz.ext",
	    	cache: false,
	    	data: json,
	    	type: 'POST',
	    	contentType:'text/json',
	    	success: function (text) {
	    		if(text.result){
		    		nui.alert("授权成功");
		    		fn();
	    		}
            },
            error: function () {
            }
	    });
    }
	
	function deleteData(items, fn){
		var node = window.parentNode;
	    var json = nui.encode({
	    	party:getPartyByNode(node),
	    	roleList:items
	    });
	    $.ajax({
	    	url: "org.gocom.components.coframe.auth.partyauth.partyauth.deletePartyAuth.biz.ext",
	    	cache: false,
	    	data: json,
	    	type: 'POST',
	    	contentType:'text/json',
	    	success: function (text) {
	    		if(text.result){
		    		nui.alert("取消授权成功");
		    		fn();
	    		}
            },
            error: function () {
            }
	    });
	}
	
	// 弹出窗口时调用
	function SetData(data){
		// 显示关闭按钮
		$("#toolBar").css("visibility", "visible");
	
		data = nui.clone(data);
		if(data && data.parentNode){
			window['parentNode'] = data.parentNode;
			loadAuth();
		}
	}
	
	function getPartyByNode(node){
		var party = {};
		if(!node) return party;
		if(node.nodeType=="OrgOrganization"){
	    	party = {
	    		id:node.orgid,
	    		partyTypeID:"org"
	    	}
	    }
	    if(node.nodeType=="OrgPosition"){
	    	party = {
	    		id:node.positionid,
	    		partyTypeID:"position"
	    	}
	    }
	    if(node.nodeType=="OrgEmployee"){
	    	party = {
	    		id:node.empid,
	    		partyTypeID:"emp"
	    	}
	    }
	    if(node.nodeType=="user"){
	        party = {
	            id:node.userId,
	            partyTypeID:"user"
	        }
	    }	
	    return party;	
	}
	
	function getAjaxData(){
		var node = window.parentNode;
		var party = getPartyByNode(node);
		var data = {
			partyId:party.id,
			partyType:party.partyTypeID
		};
		return data;
	}
	
	function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();            
    }
    
    function cancel() {
    	CloseWindow("cancel");
    }
    
    var noSelectStores = {};
    
    function renderAuthorizedRoleList(e) {
    	if(e.record.isManaged && e.record.isManaged != "true") {
    		noSelectStores[e.record.id] = e.record.id;
    		return '<span style="color:#CFCFCF">' + e.record.name + '</span>';
    	} else {
    		var item = e.item;
	    	delete item.isManaged;
	    	e.item = item;
    		return e.record.name;
    	}
    }
    
     function onListBoxValueChaged(e) {
        var listbox = e.sender;
        var items = listbox.getSelecteds();
        for(var i=0,len=items.length;i<len;i++){
        	if(noSelectStores[items[i].id]){
	       	 	listbox.deselect(items[i]);
       	 	}
        }
    }
    
    function onAuthorizedLoad(e){
    	var checkboxs = $('#listbox1 .mini-listbox-checkbox :input');
    	checkboxs.each(function(){
    		if(existsRole(this)){
	    		$(this).hide();
    		}
    	});
    }
    
    function existsRole(checkbox){
    	var td = $(checkbox).parent();
    	var nextTd = td.next();
    	var span = nextTd.find("span")[0];
    	if(span){
    		return true;
    	}
    	return false;
    }
    
</script>
</body>
</html>