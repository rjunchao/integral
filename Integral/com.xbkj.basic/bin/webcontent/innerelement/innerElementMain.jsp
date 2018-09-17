<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-06-20 09:42:52
  - Description:
-->
<head>
<title>Title</title>
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
	<div id="region1" region="west" title="内控要素管理" showHeader="true" class="sub-sidebar" width="240" allowResize="false">
		<ul id="tree1" class="nui-tree" style="width: 100%; height: 99%;"
			url="com.vbm.grc.basic.inner.element.innercontrol.innerconele.innerElementTree.biz.ext"
			sshowTreeIcon="true" textField="inter_cntr_name" idField="pk_inter_cntr"
			parentField="parent_code" dataField="lists" expandOnLoad="0" contextMenu="#menuTreeMenu"
			resultAsTree="false" onnodeclick="onNodeClick">
		</ul>
    	
    	<ul id="menuTreeMenu" class="nui-contextmenu" onbeforeopen="onBeforeOpen"></ul>
    </div>
    <div title="center" region="center" style="border:0;padding-left:5px;padding-top:5px;">
    	<!--Tabs-->
    	
       <div id="tabs" class="nui-tabs  bg-toolbar" activeIndex="0" style="width:100%;height:100%;">
		    <div name="tab_list" title="内控要素列表" url="<%=request.getContextPath() %>/grc/basic/innerelement/innerElementList.jsp" visible="true" >
		    </div>
		</div>
	</div>
</div>


<!-- js代码 -->
<script type="text/javascript">
	 nui.parse();
	 var tree = nui.get("tree1");
	 
	 //tabs里的页面JSP
	 var emlemnt_info = {title:'内控要素信息',path:'<%=request.getContextPath() %>/grc/basic/innerelement/innerElementInfo.jsp'};
	 var emlemnt_list = {title:'内控要素列表',path:'<%=request.getContextPath() %>/grc/basic/innerelement/innerElementList.jsp'};
	 //根据节点的类型，tab所load的页面
	 var element_map = {};
	 element_map["root"] = [emlemnt_list];//根节点
	 element_map["elementgroup"] = [emlemnt_info,emlemnt_list];//内控元素下有子要素
	 element_map["element"] = [emlemnt_info];//子要素
	 
	 //设置URL的参数
	 //url: grc/basic/interControl/interElementInfo.jsp
	 //param: tree.getSelected();
	function setUrlParam(url,params){
	 	if(!url){
	 		return url;
	 	}
	 	var paramsStr = [];//参数名 = 参数值 的一个数组
	 	for(var prop in params){//循环遍历
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
	
	//
	function refreshTab(node){
		//刷新右边tab
		var tabs = nui.get("tabs");
		var elementtabs = element_map[node.type];//根据节点类型，填充tab页面
		//设置tab
		for(var i = 0; i < elementtabs.length; i++){
			var obj = elementtabs[i];
			obj.url = setUrlParam(obj.path, node);//设置参数?x=xx&....
		}
		tabs.setTabs(elementtabs);//设置tab的JSP页面
	}
	
	

	function refresh(){
		//添加元素后刷新
		//刷新树
		var node = tree.getSelected();
		if(!node){
			node = tree.getRootNode();
		}
		tree.reload();//重新加载树
		//tree.expandNode(node);//展开节点
		tree.selectNode(node);//选择节点
		tree.load(node);
	}
	
	//修改子节点后，刷新节点
	function refreshNode(){
		var node = tree.getSelected();
		var parentNode = tree.getParentNode(node);
		tree.load(node);//重新加载节点
		tree.selectNode(node);//选择节点
		refreshTab(node);//刷新
	}
	
	
	//删除元素后刷新
	function remove(){
		//删除
		var node = tree.getSelectedNode();
		if(node != null){
			nui.confirm("将一起删除该节点下的子节点, 确定？","删除确认", function(action){
				if(action == "ok"){
					//删除的节点
					var json = nui.encode({vos:{pk_inter_cntr:node.pk_inter_cntr}});
					nui.ajax({
						url:"com.vbm.grc.basic.inner.element.innercontrol.innerconele.delInnElems.biz.ext",
						cache:false,
						async:true,
						data:json,
						type:"POST",
						contentType:"text/json",
						success:function(msg){
							nui.alert(msg.msg.message);
							var parent = tree.getParentNode(node);
							/* tree.loadNode(parentNode);//加载父节点 */
							tree.reload();//选择父节点
							refreshTab(parent);//刷新
							return;
						},
						error: function (jqXHR, textStatus, errorThrown) {
		                    alert(jqXHR.responseText);
		                }
					});
				}
			});
		}else{
			nui.alert("请选择要删除内控要素");
		}
	}
	
		//添加元素
	function add(){
		nui.open({
			url:"<%=request.getContextPath() %>/grc/basic/innerelement/innerElementAdd.jsp",
			title:"添加内控要素",
			width:600,
			height:200,
			onload:function(){
				//当前节点的id，是添加节点的父节点
				var node = tree.getSelectedNode();
				//id和层级
				var data = {parentCode:node.pk_inter_cntr,level:node.inter_cntr_level};
				var iframe = this.getIFrameEl();
				iframe.contentWindow.SetData(data);//调用另外一个页面的方法
			},
			ondestroy:function(action){
				//判断是否成功与否
				if(action == "ok"){
					nui.alert("添加成功");
					var node = tree.getSelectedNode();
					refresh();//刷新树节点，调用innerElementMain.jsp的方法
					refreshTab(node);
				}
			}
		});
	}
	
	//编辑元素
	function edit(){
		//编辑
		nui.open({
			url:"<%=request.getContextPath() %>/grc/basic/innerelement/innerElementEdit.jsp",
			title:"修改内控要素",
			width:600,
			height:200,
			onload:function(){
				var data = tree.getSelectedNode();
				var iframe = this.getIFrameEl();
				iframe.contentWindow.SetData(data);
			},
			ondestroy:function(action){
				if(action == "ok"){
					nui.alert("修改成功");
					refresh();//刷新树节点
					var node = tree.getSelectedNode();
					refreshTab(node);
				}
			}
		});
	}
	
	function onBeforeOpen(e){
		//右键菜单
		var obj = e.sender;
	    var node = tree.getSelectedNode();
	    if (!node) {
	        e.cancel = true;
	        return;
	    }
	    
	    //右键菜单，根据不同的节点定义不一样的右键菜单
	    //根节点
	    if(node.type=="root"){
	    	var array = [{id: "addelement", text: "新建要素", iconCls:"icon-add", onclick:"add"},
						{id: "separator", text: "", cls:"mini-separator"},
						{id: "refresh", text: "刷新", iconCls:"icon-reload", onclick:"refresh"}];
			e.htmlEvent.preventDefault();
			obj.loadList(array);
	    }
	    //内控要素组
	    if(node.type=="elementgroup"){
	    	var array = [{id: "addelementgroup", text: "新建要素", iconCls:"icon-add", onclick:"add"},
						{id: "editelement", text: "修改要素", iconCls:"icon-edit", onclick:"edit"},
						{id: "removeelement", text: "删除要素", iconCls:"icon-remove", onclick:"remove"},
						{id: "separator", text: "", cls:"mini-separator"},
						{id: "refresh", text: "刷新", iconCls:"icon-reload", onclick:"refresh"}];
			e.htmlEvent.preventDefault();
			obj.loadList(array);			
	    }
	    //内控要素叶子节点信息
	    if(node.type=="element"){
	   		var array = [{id: "editelement", text: "修改要素", iconCls:"icon-edit", onclick:"edit"},
						{id: "removeelement", text: "删除要素", iconCls:"icon-remove", onclick:"remove"}];
	    	e.htmlEvent.preventDefault();
			obj.loadList(array);
	    }
	}
	
</script>

</body>
</html>