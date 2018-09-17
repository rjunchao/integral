<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): heFei
  - Date: 2016-07-04 14:50:13
  - Description:
-->
<head>
<title>产品目录主界面</title>
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
	<div id="region1" region="west" title="产品目录管理" 
		showHeader="true" class="sub-sidebar" width="250" allowResize="false">
		<div class="nui-toolbar" style="padding:2px; border-top:0; border-left:0; border-right:0;">
 			<input class="nui-textbox" emptyText="请输入产品编码或名称" id="key" onenter="search()"/>
 				<a class="nui-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
		</div>
		<ul id="tree1" class="nui-tree" style="width: 100%; height: 99%;"
			url="com.vbm.grc.basic.product.productcontrol.productcatalogue.productcataloguetree.biz.ext"
			sshowTreeIcon="true" textField="prod_name" idField="pk_product"
			parentField="parent_code" dataField="lists" expandOnLoad="0" contextMenu="#menuTreeMenu"
			resultAsTree="false" onnodeclick="onNodeClick">
		</ul>
    	<ul id="menuTreeMenu" class="nui-contextmenu" onbeforeopen="onBeforeOpen"></ul>
    </div>
    <div title="center" region="center" style="border:0;padding-left:5px;padding-top:5px;">
    	<!--Tabs显示表格-->
    	
       <div id="tabs" class="nui-tabs  bg-toolbar" activeIndex="0" style="width:100%;height:100%;">
		    <div name="tab_list" title="产品目录列表" url="<%=request.getContextPath() %>/grc/basic/productcatalogue/productCatalogueList.jsp" visible="true" >
		    </div>
		</div>
	</div>
</div>

<!-- 下面是JavaScript的代码 -->
<script type="text/javascript">
  nui.parse();
  var tree = nui.get("tree1");
  //nui.alert("产品目录树");
  //Tabs显示表格里的页面
   var productCatalogue_Info = {title:'产品目录信息',path:'<%=request.getContextPath() %>/grc/basic/productcatalogue/productCatalogueInfo.jsp'};
   var productCatalogue_list = {title:'产品目录列表',path:'<%=request.getContextPath() %>/grc/basic/productcatalogue/productCatalogueList.jsp'}; 
  
  //根据节点的类型，判断在Tabs显示表格
   var productCatalogue_map = {};
   productCatalogue_map["root"] = [productCatalogue_list];//根节点
  //产品目录下还有其子产品目录
   productCatalogue_map["productcataloguegrop"] = [productCatalogue_Info,productCatalogue_list];
   productCatalogue_map["productcatalogue"] = [productCatalogue_Info];//子产品目录
   
   		//点击节点在右边的表里显示不同的页面
     function onNodeClick(e){
        //nui.alert("hhh");
  		//更新右边表格里的内容
  		var node = e.node;
  		refreshTab(node); 
      }
   		
     //设置有关url:grc/basic/productcatalogue/productCatalogueInfo.jsp
      function setUrlParam(url, params){
  		if(!url){
  			return url;
  		}
  		//参数名=参数值的一个数组值
  		var paramsStr = [];
  		for(var pro in params){
  		   //在数组的末尾添加一个元素
  			paramsStr.push(pro + "=" + params[pro]);
  			
  		}
  		//设置URL后面的条件
  		if(url.indexOf("?") >= 0){
  			//判断有没有?如果有用&将数组连接起来
  			return url + "&" + paramsStr.join("&"); 
  		}else{
  		  //如果没有先用加一个？，再用&连接
  		  var newUrl = url + "?" + paramsStr.join("&");
  		  return newUrl;
  		}
     }
     
     //刷新右边表的内容
     function refreshTab(node){
     		var tabs = nui.get("tabs");
     		//根据节点类型，填充表格页面页面
     		var producttabs = productCatalogue_map[node.type];
     		//设置表
     		for(var i=0; i < producttabs.length; i++){
     			var tab = producttabs[i];
     			tab.url = setUrlParam(tab.path, node);
     		}
     		//设置表色jsp页面
     		tabs.setTabs(producttabs);
     } 
     //添加元素后刷新
     function refresh(){
            //获取树节点
     		var node = tree.getSelected();
     		if(!node){
     			node = tree.getRootNode();
     		}
     		tree.reload();//重新加载树
     		tree.selectNode(node);//选择节点
     		tree.load(node);//加载树
     }
     //修改节点后，刷新树节点
     function refreshNode(){
     	var node = tree.getSelected();//获取树节点
     	var parentNode = tree.getParentNode();//获取父节点
     	tree.load(node);//重新加载节点
     	tree.selectNode(node);//选择树节点
     	refreshTab(node);//刷新树节点
     }
   
     //新建产品目录的操作
     function add(){
     //alert("pp");
        nui.open({
        	url: "<%=request.getContextPath() %>/grc/basic/productcatalogue/productcatalogueAdd.jsp",
        	title: "添加产品",
        	width: 600,
        	height: 120,
        	onload:function(){//加载
        		//当前节点的pid是父节点的id
        		var node = tree.getSelectedNode();//获取树节点
        		//nui.alert(node); 
        		var data = {parentCode:node.pk_product,level:node.product_level};
        		var ifram = this.getIFrameEl();
    	  	 //父窗口向子窗口传值调用productcatalogueAdd.jsp页面SetData方法是productcatalogueAdd.jsp页面中的方法
    	  	 ifram.contentWindow.SetData(data);
        	},
        	ondestroy: function(action){//注销
        		//判断是否成功
        		if(action == "ok"){
        			nui.alert("添加成功");
        			var node = tree.getSelectedNode();//获取树节点
        			tree.reload();
        			refresh();//刷新树节点
        			refreshTab(node);//刷新右边表的内容
        		}
        	}
        });
     }
     //修改产品目录的操作
     function edit(){
     	//打开编辑的页面
     	nui.open({
     		url: "<%=request.getContextPath() %>/grc/basic/productcatalogue/productcatalogueEdit.jsp",
     		title: "修改产品目录",
     		width: 600,
     		height: 120,
     		onload:function(){
     			var node = tree.getSelectedNode();//获取选择的节点
     			var iframe = this.getIFrameEl();
     			iframe.contentWindow.SetData(node);
     		},
     		ondestroy: function(action){//注销的时候的操作
     		  if(action=="ok"){
     		    nui.alert("修改成功");
     		    var node = tree.getSelectedNode();//获取树节点
     		    tree.reload();
     		    refresh();//刷新树节点
     		    refreshTab(node);//刷新右边的表格
     		  }
     		}
     	});
     }
     //删除产品目录的操作：在删除节点的时候判断有没有子节点
     function remove(){
     	var node = tree.getSelectedNode();//获取操作的节点
     	if(node != null){
     		nui.confirm("将一起删除该节点下的子节点, 确定？","确认删除",function(action){
     			if(action=="ok"){
     			 var json = nui.encode({vos:{pk_product:node.pk_product}}); 
     			  nui.ajax({
     			    url: "com.vbm.grc.basic.product.productcontrol.productcatalogue.dellproductcatalogue.biz.ext",
     			    cache: false,
     			    async:true,//异步
     			    data: json,
     			    type: "POST",
     			    contentType: "text/json",
     			    success:function(msg){
     			       nui.alert(msg.msg.message);
     			       if(msg.msg.flag){
     			          var node = tree.getSelectedNode(node);//获取父节点
     			          tree.reload();//加载树节点的数据
     			          refresh();
     			          refreshTab(node);//刷新右边表格的内容
     			          return;
     			       }
     			    },
     			    error: function(jqXHR, textStatus, errorThrown){
     			     	 alert(jqXHR.responseText);
     			    }
     			  });
     			}
     		});
     	}	
     }
       //右键菜单
     function onBeforeOpen(e){
        //nui.alert("右键菜单");
     	var obj = e.sender;//获取树对象
     	//nui.alert(obj);
     	var node = tree.getSelectedNode();//获取节点
     	//nui.alert(node);
     	if(!node){
     	    //没有节点就注销
     	    //alert("gg");
     		e.cancel = true;
     		return;
     	}
     	//根据不同的节点，定义不同的右键菜单
     	//根节点
     	if(node.type=="root"){
     		//alert("根节点");
     		var array = [{id: "addproductCatalogue", text: "新建产品目录",iconCls:"icon-add",onclick:"add"},
     		             {id: "separator", text:"",cls:"mini-separator"},
     		             {id: "refresh", text: "刷新", iconCls:"icon-reload", onclick:"refresh"}];
     		e.htmlEvent.preventDefault();//阻止浏览器的默认行为
     		obj.loadList(array);             
     	}
     	//产品目录组
     	if(node.type=="productcataloguegrop"){
     		var array = [{id: "addproductcataloguegrop", text: "新建产品目录",iconCls:"icon-add",onclick:"add"},
     		            {id: "editproductcatalogueg", text: "修改产品目录", iconCls:"icon-edit", onclick:"edit"},
						{id: "removeproductcatalogueg", text: "删除产品目录", iconCls:"icon-remove", onclick:"remove"},
						{id: "separator", text: "", cls:"mini-separator"},
						{id: "refresh", text: "刷新", iconCls:"icon-reload", onclick:"refresh"}];
		    e.htmlEvent.preventDefault();//阻止浏览器的默认行为
     		obj.loadList(array); 
     	}
     }
     //查询的操作
     function search(){
     	var key = nui.get("key").getValue();
     	//nui.alert(key);
     	if(key == ""){
     		tree.clearFilter();//取消过滤
     	}else{
     		key = key.toLowerCase();//小写转化
     		//nui.alert(key);
     		tree.filter(function (node){//添加过滤
			var text1 = node.prod_code ? node.prod_code.toLowerCase() : "";
			var text2 = node.prod_name ? node.prod_name.toLowerCase() : "";	
			if(text1.indexOf(key) != -1 || text2.indexOf(key) != -1){//输入的字符和树的textField的匹配
					tree.expandPath(node);
					return true;
			           }
			          });
     		
     	}
     }
</script>
</body>
</html>