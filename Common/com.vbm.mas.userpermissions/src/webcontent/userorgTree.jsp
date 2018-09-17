<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@include file="/vbm/vbmcommon.jsp"%>
<html>
<head>
    <title>人员机构分配</title>
    <link href="/css/demo.css" rel="stylesheet" type="text/css" />
  	<style type="text/css">
    html,body
    {
        padding:0;
        margin:0;
        border:0;     
        width:100%;
        height:100%;
        overflow:hidden;   
    }
    </style>
</head>
<body>
    <div class="nui-toolbar" style="text-align:center;line-height:30px;" 
        borderStyle="border-left:0;border-top:0;border-right:0;">
          <label >名称：</label>
          <input id="key" class="nui-textbox" emptyText="请输入机构名称" style="width:150px;" onenter="onKeyEnter"/>
          <a class="nui-button" style="width:60px;" onclick="search()">查询</a>
    </div>
   <div class="nui-fit">
        
        <ul id="tree1" class="nui-tree" style="width:100%;height:100%;" idField="orgid" parentField="pid" 
       checkRecursive="true" showCheckBox="true" allowSelect="false" showFolderCheckBox="true"
            showTreeIcon="true" textField="orgname"
            expandOnLoad="0" onnodedblclick="onNodeDblClick" expandOnDblClick="false" onNodeselect="clearNodes"
            >
        </ul>
    
    </div>              
    <div class="nui-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" 
        borderStyle="border-left:0;border-bottom:0;border-right:0;">
        <a class="nui-button" iconCls="icon-save"  style="width:60px;" onclick="onOk()">保存</a>
         <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" iconCls="icon-cancel"  style="width:60px;" onclick="onCancel()">取消</a>
    </div>
 
</body>
</html>
<script type="text/javascript"> 
    nui.parse();
 
    var tree = nui.get("tree1");
   
    
       var orgid="",empid="";
    function queryOrg(){
           nui.ajax({
           		url:"com.vbm.mas.userpermissions.allorg.queryallorg.biz.ext",
            	type: 'POST',
            	async : false,
				contentType:"text/json",
				success: function (text) {
			        tree.loadList(text.orgs,"orgid","pid");	
			       	queryAllotedOrg();		
                }
               
        	 });
    }
    
    function queryAllotedOrg(){
        var json ={orgid:orgid,empid:empid};
        
        nui.ajax({
           		url:"com.vbm.mas.userpermissions.allorg.queryUserOrg.biz.ext",
           		data:json,
            	type: 'POST',
            	async : false,
				contentType:"text/json",
				success: function (text) {
			
			    	
			     var allotedNodes = text.orgs;
			      var treeNodes = tree.getList();
			      //nui.alert(nui.encode(allotedNodes));
			      for(var i = 0 , len = treeNodes.length; i < len ; i++){
			          var node = treeNodes[i];
			         for(var j = 0; j < allotedNodes.length; j++){
			             var tmpNode = allotedNodes[j];
			             if(node.orgid === parseInt(tmpNode.porgid)){		
			            //nui.alert(node.orgid +"  "+ tmpNode.porgid);  
			               tree.checkNode(node);
			              
			             }
			         }    		          
			      } 			
                }
               
        	 });
    }
   
	function SetData(data){		
		data = nui.clone(data);
		if(data){
			orgid=data.orgid;
			empid = data.empid;
			queryOrg();
		}
		
	}
	
	function GetData(){
		 var value = tree.getValue(true);
           nui
	     var json = {allotedOrgs: value};
	   
	   	 return json;
	}
  function clearNodes(e){
     var treenode=e.node;
     	tree.uncheckNodes((tree.getAllChildNodes(e.node)));
     	tree.bubbleParent (e.node,function (treenode){
     		treenode=tree.getParentNode(treenode);
     		tree.uncheckNode(treenode);
     	}, true );
		}
   
    function search() {
        var key = nui.get("key").getValue();
        if(key == ""){
            tree.clearFilter();
        }else{
            key = key.toLowerCase();
            tree.filter(function (node) {
                var text = node.orgname ? node.orgname.toLowerCase() : "";
                if (text.indexOf(key) != -1) {
                    return true;
                }
            });
        }
    }
    function onKeyEnter(e) {
        search();
    }
    function onNodeDblClick(e) {
        onOk();
    }
    //////////////////////////////////
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
 
    function onOk() {
        var node = tree.getSelectedNode();
        CloseWindow("ok");        
    }
    function onCancel() {
        CloseWindow("cancel");
    }
    
    
</script>
