<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <%@include file="/vbm/vbmcommon.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">



<head>
    <title>机构名称</title>
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
          <input id="key" class="nui-textbox" emptyText="请输入机构名称" style="width:150px;"/>
          <a class="nui-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="nui-fit" style="height:80%;">
        <ul id="tree1" class="nui-tree" style="width:100%;height:100%;" dataField="orgs" 
        url="com.pam.coframe.org.newcomponent.organizationname.biz.ext"
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
<body>
	<script type="text/javascript">
		nui.parse();
		var tree = nui.get("tree1");
		tree.load();
		//取消的操作
		function onCancel()
		{
			CloseWindow("cancel");
		}
		//关闭窗口的操作
		function CloseWindow(action)
		{
			if(window.CloseOwnerWindow)
			{
				return window.CloseOwnerWindow(action);
			}
			else
			{
				window.close();//关闭弹出窗口
			}
		}
		//选中节点的操作
		function onOk()
		{
			var node = tree.getSelectedNode();
			CloseWindow("ok");
		}
		function onNodeDblClick(e)
		{
			onOk();
		}
		function onNodeDblClick(e)
		{
			onOk();
		}
		//查询的操作
		function search()
		{
			var key = nui.get("key").getValue();
			//nui.alert(key);
			if(key == "")
			{
				tree.clearFilter();
			}
			else
			{
				key = key.toLowerCase();
				//过滤树节点
				tree.filter(function(node){
					var text = node.orgname ? node.orgname.toLowerCase() : "";
					if(text.indexOf(key) != -1)
					{
						return true;
					}
				});
			}
		}
		function clearNodes(e)
		{
			var treenode = e.node;
			//nui.alert(nui.encode(treenode));
			tree.uncheckNodes(tree.getAllChildNodes(e.node));
			tree.bubbleParent (e.node,function (treenode){
     		treenode=tree.getParentNode(treenode);
     		tree.uncheckNode(treenode);
     	}, true );
		}
		function GetData() 
		 {
        	var node = tree.getSelectedNode();
        	return node;
    	 }
    	 //设置tree不展开
       function SetTreeNoExpand()
       {
		tree.collapseAll();
       }
	</script>
</body>
</html>