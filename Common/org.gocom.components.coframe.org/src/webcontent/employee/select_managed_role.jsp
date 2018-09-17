<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Author(s): yangyong
  - Date: 2013-02-28 10:14:50
  - Description:
-->
<head>
<title>选择可管理角色</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/coframe/tools/skins/common.jsp" %>
</head>
<body>
<div class="nui-fit" style="padding:5px;">
	<div id="listbox1" class="nui-listbox" style="width:388px;height:100%;"
        textField="name" valueField="id" ajaxData="getAjaxData" multiSelect="true"
       	dataField="authorizedRoles" showCheckBox="true">
     	<div property="columns">
           <div header="可管理角色" field="name"></div>
        </div>
    </div>
</div>
<div id="toolBar" class="nui-toolbar" style="text-align:center;padding-top:5px;padding-bottom:5px;visibility:hidden;" 
    borderStyle="border:0;">
    <a class="nui-button" style="width:60px;" iconCls="icon-ok" onclick="ok">确定</a>
    <span style="display:inline-block;width:25px;"></span>
    <a class="nui-button" id="cancelBtn_01" iconCls="icon-cancel" style="width:60px;" onclick="cancel">关闭</a>
</div>
<script type="text/javascript">
	 nui.parse();
	
	 var listbox1 = nui.get("listbox1");
     var url1="org.gocom.components.coframe.org.employeeAuth.getEmpAuthorizedRoles.biz.ext";
    
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
		if(party && party.partyTypeID) {
			listbox1.load(url1);
		}
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
	
	// 弹出窗口时调用
	function SetData(data){
		// 显示关闭按钮
		$("#toolBar").css("visibility", "visible");
		data = nui.clone(data);
		if(data && data.parentNode){
			window['parentNode'] = data.parentNode;
			loadAuth();
		}
		var ids = data.ids.split(",");
        if(ids!=""){
	       for(var i=0;i<ids.length;i++){
	          var id = ids[i].split(":");
	          var listdata = listbox1.getData();
	          for(var j=0,len=listdata.length;j<len;j++){
	              if(listdata[j].id==id[0]){
	                  listbox1.select(listdata[j]);
	                  break;
	              }
	          }
	       }
        }
	}
	
	function GetData() {
		var items = listbox1.getSelecteds();
		var ids = [];
		var texts = [];
		for(var i=0;i<items.length;i++){
			ids.push(items[i].id+":"+items[i].name);
			texts.push(items[i].name);
		}
        return {id:ids.join(","),text:texts.join(",")};
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
    
    function ok(){
        CloseWindow("ok");
    }
    
    function cancel() {
    	CloseWindow("cancel");
    }
</script>
</body>
</html>