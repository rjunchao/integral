<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lqy
  - Date: 2016-07-26 10:01:43
  - Description:
-->
<head>
<title>业务领域</title>
</head>
<body>
	<div class="nui-fit" style="height:90%;">
		<ul id="tree1" class="nui-tree" style="width:100%; height:100%;"
			url="com.vbm.grc.basic.busiarea.busiarea.busiAreaTree.biz.ext"
			sshowTreeIcon="true" textField="busi_name" idField="pk_busi_area" checkRecursive="false"	
			parentField="parent_code" dataField="lists" expandOnLoad="0" 
			resultAsTree="false" onnodedblclick="onNodeDblClick" 
			expandOnDblClick="false" onbeforeload="onBeforTreeLoad">
		</ul>
	</div>
	<div class="nui-toolbar" style="text-align:center;padding:5px;height:10%;"borderStyle="border:0;">
        <a class="nui-button" style="width:60px;" onclick="onOk()">确定</a>
        &nbsp;&nbsp;
        <a class="nui-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
	
</body>
<script type="text/javascript">
	nui.parse();
	var tree = nui.get("tree1");
	tree.load();
		//设置参数
   	function onBeforTreeLoad(e){
		var nodeId =  e.node.pk_busi_area;
		if(!nodeId){
		   nodeId = "root";
		}
		e.params.nodeId = nodeId;
  	}
		
	//获取数据
    function GetData(){
		var node = tree.getSelectedNode();
   		return node;
	} 
	function onNodeDblClick(e) {
    	onOk();
	}
	//确定
	function onOk() {
        var node = tree.getSelectedNode();
        //不能让客户选择root节点，否则更新插入失败
        node =  node.pk_busi_area;
        if(node=="root"){
        nui.alert("请选择业务领域");
        }
        else
   		CloseWindow("ok");         
    }
    //取消
	function onCancel() {
        CloseWindow("cancel");
    }
    //关闭窗口
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
   		else window.close();
    }
</script>
</html>
