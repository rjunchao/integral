<%@page import="com.primeton.cap.AppUserManager"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-14 20:00:26
  - Description: 礼品管理
-->
<head>
	<title>礼品管理</title>
	<script src="<%=request.getContextPath() %>/gd/data_handle/integralManage/swfupload/swfupload.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath() %>/gd/data_handle/integralManage/common/common.js" type="text/javascript"></script>
	
</head>
<body>
	<div id="form1" style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table>
	            <tr>
		            <td>
		                	名称：<input id="product_name" class="nui-textbox" name="product_name" 
		                		emptyText="请输入..." style="width:150px;"/>
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
	<div style="padding:0px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table style="width:100%;">
	            <tr>
		            <td style="width:100%;">
		                <a class="nui-button" iconCls="icon-addnew" onclick="addRow" plain="true">增加</a>
		                <a class="nui-button" iconCls="icon-save" onclick="saveData()" plain="true" id="save">保存</a>
		            	<!-- <a class="nui-button" iconCls="icon-edit" onclick="edit()" id="edit_btn"  plain="true">编辑</a> -->
		            	<a class="nui-button" iconCls="icon-remove" onclick="stop()" plain="true">停用</a> 
		            	<a class="nui-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
		            	<span class="separator"></span>
		            	
		            	<%-- <input id="fileupload1" class="nui-fileupload"  style="width:180px;height:24px;"
						    flashUrl="<%=request.getContextPath() %>/gd/data_handle/integralManage/swfupload/swfupload.swf"
						 	uploadUrl="<%=request.getContextPath() %>/gd/data_handle/integralManage/prod/importProductInfo.jsp"
							onuploadsuccess="onUploadSuccess"
							onuploaderror="onUploadError" /> 
				 		<a class="nui-button" iconCls="icon-upload" plain="true" onclick="startUpload()">导入</a> 
		            	 --%>
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
   
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.prod.product.pageProduct.biz.ext"  
		    	idField="pk_product" dataField="vos" allowResize="false" multiSelect="true" 
		    	allowCellSelect="false">
		    <input class="nui-hidden" name="pk_product"/>
		    <div property="columns">
		        <div type="checkcolumn"></div>
		       <!--  <div field="product_code" width="150px" headerAlign="center" allowSort="false">礼品编号
		        	 <input property="editor" class="nui-textbox" style="width:100%;"  allowInput="true" required="true" />
		        </div> -->
		        <div field="product_name" width="120px" headerAlign="center" allowSort="false">礼品名称
		        	 <input property="editor" class="nui-textbox" style="width:100%;"  allowInput="true" required="true" />
		        </div> 
		        <div field="product_integral" width="90px" headerAlign="center" >礼品单位积分
		        	 <input property="editor" class="nui-textbox" style="width:100%;"  allowInput="true" required="true" />
		        </div> 
		        <!-- <div field="product_num" width="90px" headerAlign="center" >礼品数量
		        	 <input property="editor" class="nui-textbox" style="width:100%;"  allowInput="true" required="true" />
		        </div>  -->
		        <div field="dr" type="comboboxcolumn" renderer="statusRenderer" width="80px" headerAlign="center" allowSort="false" >状态</div>
		        
		        <div field="remark" width=120px" headerAlign="center" allowSort="false">备注
		        	 <input property="editor" class="nui-textbox" style="width:100%;"  allowInput="true"/>
		        </div>
		       	<!-- <div field="empname" width=120px" headerAlign="center" allowSort="false">录入人</div> 
		        <div field="ts" width=120px" headerAlign="center" allowSort="false">录入时间</div> -->
		    </div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		
		nui.parse();
		var grid = nui.get("grid");
		var form = new nui.Form("#form1");
		var userId="<%=AppUserManager.getCurrentUserId()%>";
		
		function statusRenderer(e){
      		var val = e.value;
        	for(let i = 0; i < drStatus.length; i++){
        		let inn = drStatus[i];
        		if(inn.id == val){return inn.text;}
        	}
      	}
		
		function addRow(){
			//增行
      		let newRow = {pk_product:"-1"};
		  	grid.addRow(newRow, 0);
		    grid.beginEditRow(0);
		    
      	}
      	function stop() {
        	let rows = grid.getSelecteds();
        	if(rows == null || rows.length <= 0){
        		nui.alert("请选择");
        		return ;
        	}
          	let json = nui.encode({vos:rows});
            //nui.alert(json);
            nui.ajax({
                url: "com.xbkj.gd.data_handle.prod.product.stops.biz.ext",
                type: "post",
                data: json,
                cache: false,
                contentType: 'text/json',
                success: function (text) {
               	 	debugger;
                	if(!text){
                		console.log("返回信息为空");
                		return;
                	}
                	nui.alert(text.msg.message);
					if(text.msg.flag){
						grid.reload();
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    nui.alert(jqXHR.responseText);
                }
            });
        }
      	function saveData() {
        	var from = new nui.Form("#grid");
            from.validate();
	        if (from.isValid() == false) {
	            nui.alert("请输入完整信息");
	            return;
	        }
            grid.validate();
	        if (grid.isValid() == false) {
                var error = grid.getCellErrors()[0];
                nui.alert(error.errorText);
                grid.beginEditRow(error.record);
                grid.beginEditCell(error.record, error.column);
                return;
            } 
            grid.commitEdit();
          
            let size = grid.getData().length; 
            let rows = grid.getChanges();
        
          	let json = nui.encode({vos:rows});
            //nui.alert(json);
            nui.ajax({
                url: "com.xbkj.gd.data_handle.prod.product.adds.biz.ext",
                type: "post",
                data: json,
                cache: false,
                contentType: 'text/json',
                success: function (text) {
               	 	debugger;
                	if(!text){
                		console.log("返回信息为空");
                		return;
                	}
                	nui.alert(text.msg.message);
					if(!text.msg.flag){
                		var sr = grid.getSelected();
        				grid.beginEditRow(sr);
                		return;
                	}else{
                		grid.reload();
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    nui.alert(jqXHR.responseText);
                }
            });
        }
		 /**
        	文件导入
        */
        function startUpload(e){
        	let file = nui.get("fileupload1");
			let name = file.getText();
			if(name == null || name == ""){
				nui.alert("请选择需要导入的文件");
				return;
			}else{
				file.startUpload();
				return;
			}
        }
        
        function onUploadError(e){
        	nui.alert("客户信息导入失败" + e);
        }
        
        function onUploadSuccess(e){
        	let data = e.serverData;
        	nui.alert(data);
        }
        
        function onFileSelect(e){
        	nui.alert(e);
        }
		
		/**	
			修改客户信息
		*/
		function integralDetail(){
			nui.open({
				url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/editCustomer.jsp",
				title:"修改客户信息",
				width:600,
				height:220,
				onload:function(){
					var iframe = this.getIFrameEl();
					//nui.alert(nui.encode(data[0]));
					iframe.contentWindow.SetData(data[0]);
				},
				ondestroy:function(action){
					if(action == "ok"){
						search();
					}
				}
			});
		}
		
		//编辑
		function edit(){
			let data = grid.getSelecteds();
			let len = data.length;
			if(len > 0 ){
				let creater = data[0].create_user;
				//编辑
				nui.open({
					url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/editCustomer.jsp",
					title:"修改客户信息",
					width:600,
					height:220,
					onload:function(){
						let iframe = this.getIFrameEl();
						//nui.alert(nui.encode(data[0]));
						iframe.contentWindow.SetData(data[0]);
					},
					ondestroy:function(action){
						if(action == "ok"){
							search();
						}
					}
				});
			}else{
				nui.alert("请选择要编辑的行");
				return;
			}
		}	
		function search(){
			//查询
			let params = form.getData();
			grid.load({params:params});
		}
			
	</script>
</body>
</html>