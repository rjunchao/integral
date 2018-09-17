<%@page pageEncoding="UTF-8"%>
<%@include file="/coframe/tools/skins/common.jsp" %>
<html>
<!-- 
  - Author(s): XJM
  - Date: 2016-08-30 10:32:20
  - Description:附件管理统一页面
-->
<head>
<title>附件管理窗口</title>
</head>
<body style="overflow-y:hidden">

		<div class="nui-toolbar" style="width:100%;padding:0px;" >
			<table >
				<tr>
					<td>
						<a class="nui-button" onclick="addFileAction" id="addBtn" iconCls="icon-add">添加文件</a>
						<a class="nui-button" onclick="addFolderAction" id="addBtn" iconCls="icon-addfolder">添加文件夹</a>
						<a class="nui-button" onclick="removeAction" id="removeBtn" iconCls="icon-remove">删除</a>
						<span class="separator"></span>
						<a class="nui-button" onclick="downloadFileAction" id="downLoadBtn" iconCls="icon-download" title="下载文档"></a>
						<span class="separator"></span>
						<a class="nui-button" onclick="refreshAction" id="reLoadBtn" iconCls="icon-reload" title="刷新"></a>
					</td>
				</tr>
			</table>
		</div>
		
		<ul id="tree1" class="nui-tree" 
				showTreeIcon="true" showTreeLines="true" textField="file_name" idField="pk_filesystem"	
				parentField="parent_file_id" dataField="fileList" expandOnLoad="0" resultAsTree="false" 
				contextMenu="#treeMenu" style="width:100%;height:90%" onbeforeload="onBeforTreeLoad"></ul>
				
		<ul id="treeMenu" class="nui-contextmenu" onbeforeopen="onBeforeOpen"></ul>
		
    </div>


<script type="text/javascript">
	nui.parse();
	var tree = nui.get("tree1");
	var paramData;//保存参数 从调用附件管理的页面传入 paramData.belongTo:附件所属pk
	
	//设置值 
	function SetData(data){
		if(data)
			paramData = nui.clone(data);
		tree.load("com.vbm.grc.basic.filesystem.filemanage.queryFileByBelongToId.biz.ext");
	}
        
        
    /**
    * 添加新文件
    */
    function addFileAction(e){
    	var node=tree.getSelectedNode();// 获取当前选中的附件 即上级附件
    	if(!node){
    		nui.alert("请选择父文件夹！");
    		return;
    	}
    	if(node.type=="file"){
    		nui.alert("只可以在文件夹下添加！");
    		return;
    	}
    	//跳转到添加页面
    	nui.open({
            url: "<%=request.getContextPath() %>/grc/basic/filesystem/addFile.jsp",
            title: "新增文件", 
            width: 550, 
            height: 160,
            onload: function () {
            	var belongTo = paramData.belongTo;
            	var parentCode=node.pk_filesystem;
            	var data = {belongTo:belongTo,parentCode:parentCode};
            	var iframe = this.getIFrameEl();
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
	        	if (action == "ok") {
	        		var node = tree.getSelectedNode();
	            	tree.selectNode(node);
	            	refresh();
	            }
            }
        });
        	
    }
    /**
    * 编辑文件
    */
    function editFileAction(e){
    	//跳转到添加页面
    	nui.open({
            url: "<%=request.getContextPath() %>/grc/basic/filesystem/editFile.jsp",
            title: "编辑文件", 	
            width: 550, 
            height: 160,
            onload: function () {
            	var node = tree.getSelectedNode();// 获取当前选中的附件 即上级附件
            	
            	//编辑文件需要传到编辑页面的参数有 文件pk 文件名(直接进行显示 不用根据pk从数据库中获取)
            	var pk_filesystem=node.pk_filesystem;
            	var file_name=node.file_name;
            	
            	var data = {pk_filesystem:pk_filesystem,file_name:file_name};
            	var iframe = this.getIFrameEl();
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
	        	if (action == "ok") {
	        		var node = tree.getSelectedNode();
	            	node=tree.getParentNode(node); 
	            	tree.selectNode(node);
	            	refresh();
	            }
            }
        });
        	
    }
    /**
    * 添加新文件夹
    */
    function addFolderAction(e){
    	var node=tree.getSelectedNode();// 获取当前选中的附件 即上级附件
    	if(!node){
    		nui.alert("请选择父文件夹！");
    		return;
    	}
    	if(node.type=="file"){
    		nui.alert("只可以在文件夹下添加！");
    		return;
    	}
    
    	//跳转到添加页面
    	nui.open({
            url: "<%=request.getContextPath() %>/grc/basic/filesystem/addFolder.jsp",
            title: "新增文件夹", 
            width: 420, 
            height: 100,
            onload: function () {
            	var belongTo = paramData.belongTo;
            	var parentCode=node.pk_filesystem;
            	var data = {belongTo:belongTo,parentCode:parentCode};
            	var iframe = this.getIFrameEl();
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
	        	if (action == "ok") {
	        		var node = tree.getSelectedNode();
	            	tree.selectNode(node);
	            	refresh();
	            }
            }
        });
        	
    }
    
	//编辑文件夹  
	function editFolderAction(e){
	  	//跳转到添加页面
    	nui.open({
            url: "<%=request.getContextPath() %>/grc/basic/filesystem/editFolder.jsp",
            title: "编辑文件夹", 
            width: 420, 
            height: 100,
            onload: function () {
            	var node = tree.getSelectedNode();// 获取当前选中的附件 即上级附件
            	var pk_filesystem=node.pk_filesystem;
            	var file_name=node.file_name;
            	var belongTo = paramData.belongTo;
            	var parent_file_id=node.parent_file_id;
            	var data = {pk_filesystem:pk_filesystem,file_name:file_name,parent_file_id:parent_file_id,belongTo:belongTo};
            	var iframe = this.getIFrameEl();
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
	        	if (action == "ok") {
	        		var node = tree.getSelectedNode();
	        		node=tree.getParentNode(node); 
	            	tree.selectNode(node);
	            	refresh();
	            }
            }
        });
    }
    
    //下载文件
    function downloadFileAction(e){
    	var node=tree.getSelectedNode();
    	if(!node){
    		nui.alert("请选择一个文件！");
    		return;
    	}
    	if(node.type!="file"){
    		nui.alert("只可以下载文件！");
    		return;
    	}
    	var pk_filesystem=node.pk_filesystem;//文件主键
    	var file_name=node.file_name;//文件名
    	window.open("<%=request.getContextPath() %>/grc/basic/filesystem/downloadFile.jsp?pk_filesystem="+pk_filesystem+"&file_name="+file_name);
    }
    
  
        
    /**
    * 删除文件
    */
    function removeAction(e) {
        var node = tree.getSelectedNode();
        if(!node){
        	nui.alert("请选择要删除的文件或者文件夹！");
        	return;
        }
        if(node.type=="root"){
        	nui.alert("对不起，根节点不可以删除！");
        	return;
        }
        var json = nui.encode({fileId:node.pk_filesystem});
        
        if (node) {
            nui.confirm("确定要将该文件/文件夹及其下的所有文件？","删除确认",function(action){
	    	if(action!="ok") return;
        	$.ajax({
	            url: "com.vbm.grc.basic.filesystem.filemanage.deleteFileByID.biz.ext",
	            type: 'POST',
	            data: json,
	            cache: false,
	            contentType:'text/json',
	            success: function (text) {
	            	if(text.msg.flag){
	            		tree.removeNode(node);
	            	}
	            	nui.alert(text.msg.message);
	            },
	            error: function () {
	            	nui.alert("删除有误，请重试！");
	            }
	       	 });
        	});
        }
    }
    //结点刷新
    function refreshAction(e){
    	 var node = tree.getSelectedNode();
    	 if(!node){
    	 	tree.reload();
    	 	return;
    	 }
    	 tree.selectNode(node);
	     refresh();
    	 
    }
    
    //设置参数 在点击树节点前面的+之前的处理方法 
    function onBeforTreeLoad(e){
    	var nodeId =  e.node.pk_filesystem;
    	
    	e.params.nodeId = nodeId;//上级结点主键  首次加载为空
    	e.params.belongTo=paramData.belongTo;
    }
      
    //应用功能树右键菜单 在鼠标右击展开之前的响应事件 可以根据选中节点类型 相应地生成右击菜单
	function onBeforeOpen(e) { 
	    var menu = e.sender;
	    var tree = nui.get("tree1");
	    var node = tree.getSelectedNode();
	    
	    if (!node) {
	        e.cancel = true;
	        return;
	    }
	    //根据结点类型设置相应的右键菜单 类型有：root:根节点    folder：文件夹   file:文件
	    if (node.type == "root") {
	        var array = [{id: "addFile", text: "添加文件", iconCls:"icon-add", onclick:"addFileAction"},
						{id: "addFolder", text: "添加文件夹",iconCls:"icon-addfolder",onclick:"addFolderAction"},
						{id: "refreshNode", text: "刷新",iconCls:"icon-reload",onclick:"refreshAction"}];
			e.htmlEvent.preventDefault();
			menu.loadList(array);
	    }else if(node.type=="folder"){//文件夹
	    	var array = [{id: "addFile", text: "添加文件", iconCls:"icon-add", onclick:"addFileAction"},
						{id: "addFolder", text: "添加文件夹",iconCls:"icon-addfolder",onclick:"addFolderAction"},
						{id: "editFolder", text: "编辑文件夹",iconCls:"icon-edit",onclick:"editFolderAction"},
						{id: "deleteAction", text: "删除", iconCls:"icon-remove", onclick:"removeAction"},
						{id: "refreshNode", text: "刷新",iconCls:"icon-reload",onclick:"refreshAction"}
						];
			e.htmlEvent.preventDefault();
			menu.loadList(array);
	    }else if(node.type="file"){//文件
	    	var array = [{id: "editFile", text: "编辑文件", iconCls:"icon-edit", onclick:"editFileAction"},
	    					{id: "deleteAction", text: "删除", iconCls:"icon-remove", onclick:"removeAction"},
	    					{id: "downloadFileAction", text: "下载", iconCls:"icon-download", onclick:"downloadFileAction"},
	    					{id: "refreshNode", text: "刷新",iconCls:"icon-reload",onclick:"refreshAction"}	
	    				];
			e.htmlEvent.preventDefault();
			menu.loadList(array);
	    	
	    }
	
	   
	}
		
	function refresh(){
		var node = tree.getSelectedNode();
		if(!node){
			node = tree.getNode("root_root");
		}
		tree.loadNode(node);
	}	

</script>
</body>
</html>