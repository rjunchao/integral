<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lwl
  - Date: 2016-07-05 14:44:12
  - Description:
-->
<head>
<title>风险成因分类</title>
</head>
<body>
<style>
#tree1 .mini-grid-viewport{
	background-color:transparent !important;
}
#tree1 .mini-panel-viewport{
	background-color:transparent !important;
}
#menutabs .mini-tabs-bodys{
	padding:0px;
}
</style>
<div id="layout1" class="nui-layout" style="width:100%;height:100%;">
	<div id="region1" region="west" title="风险成因分类管理" showHeader="true" class="sub-sidebar" width="240" allowResize="false">
		<ul id="tree1" class="nui-tree" style="width: 100%; height: 99%;"
					url="com.vbm.grc.basic.risk.classify.riskclassify.riskClassifyTree.biz.ext"
					showTreeIcon="true" showTreeLines="true" textField="classify_name" idField="pk_risk_classify"	
					parentField="parent_code" dataField="lists" expandOnLoad="0" contextMenu="#menuTreeMenu"
					resultAsTree="false" onnodeclick="onNodeClick" onnodeclick="onNodeClick" onnodeclick="onNodeClick" onnodeclick="onNodeClick" onnodeclick="onNodeClick" onbeforeload="onBeforTreeLoad">
		</ul>
    	<ul id="menuTreeMenu" class="nui-contextmenu" onbeforeopen="onBeforeOpen"></ul>
    </div>
    <div title="center" region="center" style="border:0;padding-left:5px;padding-top:5px;">
    	<!--Tabs-->	
       <div id="risktabs" class="nui-tabs  bg-toolbar" activeIndex="0" style="width:100%;height:100%;">
		    <div name="tab_list" title="风险成因分类列表" url="<%=request.getContextPath() %>/grc/basic/riskclassify/riskClassifyList.jsp" visible="true" >
		    </div>
	   </div>
	</div>
</div>

<!-- js代码 -->
<script type="text/javascript">
	 nui.parse();
	 var tree = nui.get("tree1");
	 //tabs里的页面JSP
	 var risk_info = {title:'风险成因分类信息',path:'<%=request.getContextPath() %>/grc/basic/riskclassify/riskClassifyInfo.jsp'};
	 var risk_list = {title:'风险成因分类列表',path:'<%=request.getContextPath() %>/grc/basic/riskclassify/riskClassifyList.jsp'};
	 //根据节点的类型，tab所load的页面
	 var risktabs_map = {};
	 risktabs_map["root"] = [risk_list];//根节点
	 risktabs_map["riskgroup"] = [risk_info,risk_list];//内控元素下有子领域
	 risktabs_map["risk"] = [risk_info];//子领域
	 
	 //设置URL的参数
	 //url: grc/basic/riskclassify/riskclassifyInfo.jsp
	 //param: tree.getSelected();
	 var paramJson = {};
	 
	 function setUrlParam(url,params){
	 	if(!url){
	 		return url;
	 	}
	 	//参数名 = 参数值 的一个数组
	 	var paramsStr = [];
	 	//循环遍历
	 	for(var prop in params){
	 		// 参数名 = 参数值
	 		paramsStr.push(prop + "=" + params[prop]);
	 	}//设置URL后面的条件
	 	//判断有没有?号，
	 	if(url.indexOf("?") >= 0){
	 		//如果有，把参数数组用&连接起来
	 		return url + "&" + paramsStr.join("&");
	 	}else{
	 		//如果没有有，先加一个?号，在把参数数组用&连接起来
	 		var newUrl = url + "?" + paramsStr.join("&");;
			//nui.alert(newUrl);
			return newUrl;
	 	}
	 }
	//点击节点，右边的tab加载不同的页面
	function onNodeClick(e){
		//更新右边的tabs的内容
		var node = e.node;
		refreshTab(node);
	}
	
	//刷新右边表单
	function refreshTab(node){
		//刷新右边tab
		var tabs = nui.get("risktabs");
		//根据节点类型，填充tab页面
		var risktabs = risktabs_map[node.type];
		//设置tab
		for(var i = 0; i < risktabs.length; i++){
			var obj = risktabs[i];
			//把节点信息传到右侧进行显示
			obj.url = setUrlParam(obj.path,node);
		}
		//设置右侧显示的JSP页面
		tabs.setTabs(risktabs);
	}	
	function refresh(){
		//添加元素后刷新
		//刷新树
		var node = tree.getSelectedNode();
		if(!node){
			node = tree.getNode("root");
		}
		tree.load(node);
	}
	//修改子节点后，刷新节点
	function refreshNode(){
		var node = tree.getSelected();	
		var parentNode = tree.getParentNode(node);	
		//重新加载节点
		tree.load(paramJson);
		//选择节点
		tree.selectNode(node);
		//刷新
		refreshTab(node);
	}
	//设置参数
	function onBeforTreeLoad(e){
		var nodeId =  e.node.pk_risk_classify;
		if(!nodeId){
		   nodeId = "root";
		}
		e.params.nodeId = nodeId;
	}
</script>
</body>
</html>