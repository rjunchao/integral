<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@include file="/vbm/vbmcommon_rights.jsp"%>
<!-- 
  - Author(s): WES
  - Date: 2015-03-31 11:28:29
  - Description:
-->
<link id="css_skin" rel="stylesheet" type="text/css"
	  href="<%=cp%>/coframe/tools/skins/skin1/style.css" />
<html>
<head>
    <title>人员分配操作机构</title>
	<style type="text/css">
    body{
        margin:0;padding:0;border:2;width:100%;height:100%;overflow:hidden;
    }    
    </style>  
    <script type="text/javascript" src="<%=cp%>/common/nui/locale/zh_CN.js"></script>
    <!--引入皮肤样式-->
</head>
<body>
 <div class="nui-splitter" style="width:100%;height:100%;">
    <div size="240" showCollapseButton="true">
        <div class="nui-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <span style="padding-left:5px;">名称：</span>
            <input id="key" class="nui-textbox" onenter="searchTree()" />
            <a class="nui-button" iconCls="icon-search" plain="true" onclick="searchTree()">查找</a>                  
        </div>
        <div class="nui-fit">
            <ul id="tree1" class="nui-tree" style="width:100%;" dataField="orgs" 
            url="com.vbm.mas.userpermissions.allorg.queryallorg.biz.ext"
                showTreeIcon="true" textField="orgname" idField="orgid" parentField="pid" 
                dataField="orgs" expandOnLoad="false" resultAsTree="false" 
            >        
            </ul>
        </div>
  </div>
  <div showCollapseButton="true" style="width:100%;height:100%;">
        <div class="nui-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <!-- <a class="nui-button" iconCls="icon-redo" id="allot" plain="true" onclick="allot()" enabled="false">分配操作机构</a> -->
            <a class="nui-button" iconCls="icon-redo" id="authOrgPer" plain="true" onclick="authOrg()" enabled="false">分配操作机构</a>
            <!--<a class="nui-button" iconCls="icon-edit" id="edit" plain="true" onclick="edit()">编辑</a>
            <span class="separator"></span> 
            <a class="nui-button" iconCls="icon-save" onclick="saveData()" plain="true" id="saveGrid" enabled="false">保存</a>                             
            <a class="nui-button" iconCls="icon-cancle" onclick="cancel()" plain="true" id="cancelGrid" enabled="false">取消</a>  -->
        </div>
        <div class="nui-fit" style="width:100%;height:100%;">
                
                <div id="grid1" class="nui-datagrid" style="width:100%;height:100%;" showPager="false"
     url="org.gocom.components.coframe.org.employee.queryEmpUsers.biz.ext" idField="id" dataField="treeNodes" onRowclick="rowchick();"
     allowCellValid="true" allowResize="true" pageSize="10" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true">
                <div property="columns">   
                <div type="checkcolumn" width="30">选择</div> 
                <div field="empcode" width="100" align="center" headerAlign="center" >人员代码</div>  
                <div field="empname" width="100" align="center" headerAlign="center" >人员姓名</div> 
                <div field="userid" width="100" align="center" headerAlign="center" >用户登录名</div>
                <div field="degree" displayField="emptypeName" width="100" align="center" headerAlign="center" >人员类型</div>
                <div field="positiontype" displayField="positiontypeName" width="100" align="center" headerAlign="center" >岗位类型</div>
<!--                 <div field="auth" allowResize="false" type="checkboxcolumn" trueValue="Y" falseValue="N" width="30" headerAlign="center">权限</div>    
 -->                </div>
            </div>
        </div>
    </div> 
</div>
  
    <script type="text/javascript">
        nui.parse();
        var tree = nui.get("tree1");
        var grid = nui.get("grid1");
        var auth = "";
        
        function renderAuth(e){
			return nui.getDictText("auth",e.row.auth);
		}
   	    
		function searchTree() {
            var key = nui.get("key").getValue();
            key=key.trim();
            if (key == "") {
            	tree.collapseAll();
            	tree.expandLevel(Number(0));
                tree.clearFilter();
            } else {
                key = key;
                tree.filter(function (node) {
                    var text = node.orgname ? node.orgname: "";
                    if (text.indexOf(key) != -1 ) {
                   	 	tree.expandAll();
                        return true;
                    }
                });
            }
        }
        tree.on("nodeselect", function (e) {
        var node = tree.getSelectedNode();        		
			var auth = '<%=isinput %>';
			var orgid = '<%=currOrgid %>'
			var empid = '<%=currUserid %>'
			if(auth == 'Y'){
				grid.load({"criteria":{"_expr":[{},{"orgid":node.orgid||"","_op":"="},{"empid":empid||"","_op":"="}]}});
			}else{
				grid.load({"criteria":{"_expr":[{},{"orgid":node.orgid||"","_op":"="}]}});
			}
        });
        function rowchick(){
        	var rows = grid.getSelecteds();
        	if(rows.length == 1){
        		if(rows[0].userid==null||rows[0].userid==""){
        			nui.get("#authOrgPer").setEnabled(false);
        		}else{
        			nui.get("#authOrgPer").setEnabled(true);
        		}
        	}else{
        		nui.get("#authOrgPer").setEnabled(false);
        	}
        }
         function allot(){
         var rows = grid.getSelecteds();
         var node = tree.getSelectedNode();
         if (rows.length == 1) {
	         if(rows[0].userid==null||rows[0].userid==""){
	         	nui.alert("登陆用户名为空不能分配！","提示");
	         	return;
	         }
         //var row = grid.getSelected();
	      			nui.open({
						url:"<%=request.getContextPath() %>/mas/userpermissions/login_orgAuth.jsp",
					    title:'分配人员权限',
					    width:620,
					    height:400,
					     onload:function(){
					    	var iframe = this.getIFrameEl();
					        var data = {orgid:node.orgid,empid:rows[0].empid};
				            iframe.contentWindow.SetData(data);
					    }, 
					    ondestroy:function(action){
					    	 if(action =="ok"){
						    		var iframe = this.getIFrameEl();
			                        var data = iframe.contentWindow.GetData();
			                         data = nui.clone(data);
			                         if (data) {
			                         		 var left=[];
					                         var right=[];
					                         for(var i=0;i<data.items.length;i++){
					                         	left[i]=data.items[i].porgid;
					                         } 
					                          for(var i=0;i<data.items1.length;i++){
					                         	right[i]=data.items1[i].porgid;
					                         } 
				                            var json2 = nui.encode({orgid:node.orgid,empid:rows[0].empid,userid:rows[0].userid,left:left,right:right});
			                				 
					                        nui.ajax({
			               						 url:"com.vbm.mas.userpermissions.allorg.insetUserOrg.biz.ext",
			              					     type: 'POST',
								                 data: json2,
								                 async : false,
								                 contentType:"text/json",
								                 success: function (data2) {
								                 	if(data2.re=="1"){
								                 		nui.alert("成功！","提示");
								                 	}
								                 	else{
								                 		nui.alert("失败！","提示");
								                 	} 
								                 }
								                 });
		           
			                        } 
					        	
					        }else{
					         	return;
					        }
					    }
					}); 
		}else{
                nui.alert("请选中一条记录","提示");
            }
		}
		
		function authOrg(){
         var rows = grid.getSelecteds();
         var node = tree.getSelectedNode();
         if (rows.length == 1) {
	         if(rows[0].userid==null||rows[0].userid==""){
	         	nui.alert("登陆用户名为空不能分配！","提示");
	         	return;
	         }
	      		nui.open({
						url:"<%=request.getContextPath() %>/mas/userpermissions/login_orgAuth.jsp",
					    title:'分配人员权限',
					    width:400,
					    height:400,
					    onload:function(){
					    	var iframe = this.getIFrameEl();
					        var data = {orgid:node.orgid,empid:rows[0].empid};
				            iframe.contentWindow.SetData(data);
					    }, 
					    ondestroy:function(action){
					    	 if(action =="ok"){
						    		var iframe = this.getIFrameEl();
			                        var data = iframe.contentWindow.GetData();
			                         data = nui.clone(data);
			                         if (data) {
					                        var orgIds=[];
					                        for(var i=0;i<data.length;i++){
					                         	orgIds[i]=data[i].orgid;
					                        } 
				                            var orgjson = nui.encode({orgid:node.orgid,empid:rows[0].empid,userid:rows[0].userid,orgIds:orgIds,authType:"treeAuth"});
			                				 nui.ajax({
			               						 url:"com.vbm.mas.userpermissions.allorg.insetUserOrg.biz.ext",
			              					     type: 'POST',
								                 data: orgjson,
								                 async : false,
								                 contentType:"text/json",
								                 success: function (data2) {
								                 	if(data2.re=="1"){
								                 		nui.alert("成功！","提示");
								                 	}
								                 	else{
								                 		nui.alert("失败！","提示");
								                 	} 
								                 }
								                 });
		           
			                        } 
					        	
					        }else{
					         	return;
					        }
					    }
					}); 
		}else{
                nui.alert("请选中一条记录","提示");
            }
		}
		
		//编辑
		function edit() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
            setButtonEnabled(true);
            	for(var i=0,l=rows.length;i<l;i++){
            		var row=rows[i];
                	grid.beginEditRow(row);
              	}
           	 }else{
            	nui.alert("请选择一条记录","提示");
            }
            
        }
        //取消
        function cancel() {
           grid.reload();
           grid.cancelEdit();  
           setButtonEnabled(false);      
        }
        //置灰
        function setButtonEnabled(st){
		      nui.get("#saveGrid").setEnabled(st);
		      nui.get("#cancelGrid").setEnabled(st);
		}
		//保存
		function saveData() { 
			grid.commitEdit();
            grid.validate();
            if (grid.isValid() == false) {
                return;
            }  
            var data = grid.getChanges();
			
            var json = nui.encode({auths:data});
            grid.loading("保存中，请稍后......","提示");
            nui.ajax({
                url: "com.vbm.mas.userpermissions.allorg.updateAuth.biz.ext",
                type: 'POST',
                data: json,
                success: function (text) {
                    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
            setButtonEnabled(false);
        } 
    </script>

</body>
</html>