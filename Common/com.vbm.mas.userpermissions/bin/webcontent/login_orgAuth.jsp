<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <%@include file="/vbm/vbmcommon.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
    <title>机构</title>
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
        <ul id="tree1" class="nui-tree" style="width:100%;height:100%;" dataField="orgs" 
        	url="com.pam.coframe.org.newcomponent.organization.biz.ext"
            showTreeIcon="true" textField="orgname" idField="orgid" parentField="pid" resultAsTree="false"  
            expandOnLoad="0" expandOnDblClick="false" checkRecursive="false"
            showCheckBox="true"
			contextMenu="#treeMenu">
        </ul>
        <!--<ul id="treeMenu" class="nui-contextmenu" onbeforeopen="onBeforeOpen"></ul>-->
    </div>                
    <div class="nui-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" 
        borderStyle="border-left:0;border-bottom:0;border-right:0;">
        <a class="nui-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
 	<ul id="treeMenu" class="mini-contextmenu"  onbeforeopen="onBeforeOpen">        
		<li name="checkAll" iconCls="icon-edit" onclick="onCheckAll">全选</li>
		<li name="canlAll" iconCls="icon-remove" onclick="onCancelAll">取消全选</li>        
	</ul>
</body>
</html>
<script type="text/javascript"> 
    nui.parse();
 
    var tree = nui.get("tree1");
    tree.load({"pType":"checkAll"});
    
    var ckField=[];
  
	function SetData(data){		
		data = nui.clone(data);
		var parentNode = data;
		var json = nui.encode({orgid:data.orgid,empid:data.empid});
		if(data){
			nui.ajax({
			    url:"com.vbm.mas.userpermissions.allorg.queryUserOrg.biz.ext",
			    type: 'POST',
				data: json,
				async : false,
				contentType:"text/json",
				success: function (dataRs) {
					if(dataRs){
						ckField = dataRs.orgs;
						for(i=0;i<ckField.length;i++){
							tree.cascadeChild(tree.getRootNode(),function(node){
								if(node.orgid==ckField[i].porgid){
									tree.checkNode(node);
								}
							});
						}
					}
				}
			});
		}
	}
    function GetData() {
        var nodes = tree.getCheckedNodes();
        return nodes;
    }
	//全选当前节点下所有子节点
	function onCheckAll(e){
		var node = tree.getSelectedNode();
        var nodes = tree.getAllChildNodes(node);
        tree.checkNode(node);
        tree.checkNodes(nodes);
	}
	//取消全选当前节点下所有子节点
	function onCancelAll(e){
		var node = tree.getSelectedNode();
        var nodes = tree.getAllChildNodes(node);
        tree.uncheckNode(node);
        tree.uncheckNodes(nodes);
	}
	function onBeforeOpen(e) {
    	var menu = e.sender;
    	var tree1 = mini.get("tree1");

    	var node = tree1.getSelectedNode();
    	if (!node) {
       	 	e.cancel = true;
        	return;
    	}
    	/*
    	if (node && node.orgname.indexOf("0001")!=-1) {
        	e.cancel = true;
        	//阻止浏览器默认右键菜单
        	e.htmlEvent.preventDefault();
        	return;
     	}*/
    	
    	var editItem = mini.getbyName("checkAll", menu);
    	var removeItem = mini.getbyName("canlAll", menu);
    	editItem.show();
    	removeItem.enable();
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
    
    function SetTreeChecked(){
     // var tree = nui.get("tree1");
      
      tree.setAllowSelect("true");
      tree.setShowCheckBox("checked");
      
    }
    //设置teer不展开
     function SetTreeNoExpand(){
     // var tree = nui.get("tree1");
      tree.collapseAll();
    }
 
 function GetMoreData(){

  //var value = tree.getValue(false);
  //alert(value);
  var nodes = tree.getCheckedNodes();
           //alert(nui.encode(nodes));
            return nodes;
 }
    
</script>
