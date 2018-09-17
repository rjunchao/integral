<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@include file="/vbm/vbmcommon.jsp" %>
<html>
<!-- 
  - Author(s): szb
  - Date: 2015-03-18 20:21:12
  - Description:
-->
<head>
<title>人员机构分配</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link id="css_skin" rel="stylesheet" type="text/css" href="/default/coframe/tools/skins/skin1/css/style.css"/>
<link id="css_icon" rel="stylesheet" type="text/css" href="/default/coframe/tools/icons/icon.css"/>
<script type="text/javascript" src="/default/common/nui/nui.js"></script>
<script>
	/* $(function(){
		nui.context='/default'
	});
	
	var data=[];
	
	nui.DataTree.prototype.dataField='data'; */
</script>
</head>
<body style="overflow:hidden;">
<div class="nui-toolbar" style="text-align:left;line-height:20px;color:blue;" >
   <table>
	<tr style="height:10px;width:100%;">
	   	<td style="width:55%;">
	   		<label>机构号：</label>
	   		<input id="org" class="nui-textbox"/>
	   		<a class="nui-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
	   	<td>
	   	<td style="width:45%;">
	   		<label>机构号：</label>
	   		<input id="orgUn" class="nui-textbox"/>
	   		<a class="nui-button" iconCls="icon-search" onclick="searchUn()" plain="true">查询</a>
	   	</td>
	</tr>
	</table>
</div>
<div id="fit1" class="nui-fit" style="margin:5px 5px 0px 5px;">
	 <table style="height:98%;border-bottom-style:none;">
	   <tr style="height:100%;">
	    <td style="width:300px;height:100%;">
	    	<div id="listbox1" class="nui-listbox" style="width:99%;height:100%;"
	              multiSelect="true" showCheckBox="true" onload="onAuthorizedLoad" dataField="orgs"> 
	         	<div property="columns"  style="width:1%;">
	         		<!-- <div header="已分配机构编码" field="orgcode"></div> -->
	               <div header="已分配机构" field="orgname"></div>
	            </div>
	        </div>
	    </td>
	    <td style="width:120px;height:100%;text-align:center;">
	    
	      <input class="nui-button" text="<" onclick="add()" style="width:65px;margin-top:5px;"/><br />
	       <input class="nui-button" text="<<" onclick="addAll()" style="width:65px;margin-top:5px;"/><br />
	        <input class="nui-button" text=">" onclick="remove()" style="width:65px;margin-top:5px;"/><br />
	        <input class="nui-button" text=">>" onclick="removeAll()" style="width:65px;margin-top:5px;"/><br />
	       
	      
	    </td>
	    <td style="width:300px;height:100%;">
		   <div id="listbox2" class="nui-listbox" style="width:99%;height:100%;"
			    textField="orgname" valueField="porgid" showCheckBox="true"  multiSelect="true"
			   dataField="orgs">
				 <div property="columns" style="width:1%;">
				      <div header="未分配机构" field="orgname"></div>
				 </div>
			</div>
	    </td>
	    </tr>
	</table>
</div>

    <table class="nui-toolbar" style="width:100%; border:0;">
       <tr>
           <td style="text-align:center;">
               <a class="nui-button" iconCls="icon-close" onclick="close">关闭</a>
               <a class="nui-button" iconCls="icon-close" onclick="onOk">确定</a>
           </td>
       </tr>
    </table>
    <script type="text/javascript">
	 nui.parse();	
	 var fit1 = nui.get("fit1");
	 var listbox1 = nui.get("listbox1");
     var listbox2 = nui.get("listbox2");
     var url1="com.vbm.mas.userpermissions.allorg.queryUserOrg.biz.ext";
     var url2="com.vbm.mas.userpermissions.allorg.queryUserOrgUn.biz.ext";
     var dataTemp;
     
	function SetData(data){		
		data = nui.clone(data);
		dataTemp = data;
		if(data){
			var parentNode = data;
			listbox1.load(url1+"?orgid="+data.orgid+"&empid="+data.empid);
			listbox2.load(url2+"?orgid="+data.orgid+"&empid="+data.empid);
		}
	}
	
	function search(){
		var org = nui.get("org").getValue();
		listbox1.load(url1+"?orgid="+dataTemp.orgid+"&empid="+dataTemp.empid+"&org="+org);
	}
	function searchUn(){
		var orgUn = nui.get("orgUn").getValue();
		listbox2.load(url2+"?orgid="+dataTemp.orgid+"&empid="+dataTemp.empid+"&orgUn="+orgUn);
	}
	
	function add() {
	    var items = listbox2.getSelecteds();
	    if(items && items.length > 0){
	 		   listbox2.removeItems(items);
			   	listbox1.addItems(items);
			   	onAuthorizedLoad();
	   	}else{
	    	nui.alert("至少选择一条记录");
	    }
	}
	
	function remove() {
	    var items = listbox1.getSelecteds();
	    if(items && items.length > 0){
	    		listbox1.removeItems(items);
		        listbox2.addItems(items);
		        onAuthorizedLoad();
	    }else{
	    	nui.alert("至少选择一条记录");
	    }
	}
	
	 function addAll() {
        var items = listbox2.getData();
        var arrs = $.grep(items, function(item){
             return item.isManaged != "false";
        });
        if(arrs && arrs.length > 0){
         		listbox2.removeItems(arrs);
		        listbox1.addItems(arrs);
		        onAuthorizedLoad();
        }else{
	    	nui.alert("至少选择一条记录");
	    }
    }
    
    function removeAll() {
        var items = listbox1.getData();
        var arrs = $.grep(items, function(item){
             return item.isManaged != "false";
        });
        if(arrs && arrs.length > 0){
        		listbox1.removeItems(arrs);
		        listbox2.addItems(arrs);
		        onAuthorizedLoad();
        }else{
	    	nui.alert("至少选择一条记录");
	    }
    }
	
	
	function close(){
	    CloseWindow("close");
	}
	
	
	function GetData(){
		var items = listbox1.getData();
		var items1 = listbox2.getData();
	     var json = {items:items,items1:items1};
	     //alert(nui.encode(json));
	    // alert(json);
	   	 return json;
	}
	
	function CloseWindow(action){
      if(window.CloseOwnerWindow) 
        return window.CloseOwnerWindow(action);
      else
        return window.close();
    }
    function onOk() {
    var items = listbox1.getData();
        	CloseWindow("ok");
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

