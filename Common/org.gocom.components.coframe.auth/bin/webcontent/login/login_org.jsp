<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
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
<div class="nui-fit">
    <div class="nui-toolbar" style="text-align:center;line-height:30px;height:10%;">
          <label >查询条件：</label>
          <input id="key" class="nui-textbox" emptyText="请输入机构编码或名称" style="width:150px;" onenter="onKeyEnter"/>
          <a class="nui-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="nui-fit" style="height:80%;">
        <ul id="tree1" class="nui-tree" style="width:100%;height:100%;" dataField="orgs" 
        url="com.pam.coframe.org.newcomponent.organization.biz.ext"
             resultAsTree="false"  checkRecursive="false"
            showTreeIcon="true" textField="orgname" idField="orgid" parentField="pid" 
            expandOnLoad="1" onnodedblclick="onNodeDblClick" expandOnDblClick="false" onNodeselect="clearNodes"
            >
        </ul>
    </div>                
    <div class="nui-toolbar" style="text-align:center;padding:3px;height:10%;">
        <a class="nui-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
</div>
</body>
</html>
<script type="text/javascript"> 
    nui.parse();
 
    var tree = nui.get("tree1");
 
    tree.load();
  function clearNodes(e){
     var treenode=e.node;
     	tree.uncheckNodes((tree.getAllChildNodes(e.node)));
     	tree.bubbleParent (e.node,function (treenode){
     		treenode=tree.getParentNode(treenode);
     		tree.uncheckNode(treenode);
     	}, true );
		}
    function GetData() {
        var node = tree.getSelectedNode();
        return node;
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
