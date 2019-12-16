<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-19 15:51:01
  - Description: 礼品申请审批
-->
<head>
<title>礼品申请审批</title>
</head>
<body>
	<form id="formGrid">
       <div style="width:100%;">
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
	                    	<a class="nui-button" iconCls="icon-remove" plain="true" onclick="removeRow()">删除</a> 
	                    	<a class="nui-button" iconCls="icon-edit" plain="true" onclick="startAudit()">审批</a> 
                        	<a class="nui-button" iconCls="icon-save" onclick="saveData()" plain="true" id="savedata">保存</a>      
	                    </td>
	                </tr>
	            </table>           
        	</div>
    	</div>
    	<!--allowInput="false"  -->
    	 <div id=datagrid class="nui-datagrid" style="width:100%;height:260px;" 
    	 	allowResize="true" url="com.xbkj.gd.data_handle.prod.applyProduct.queryAuditProd.biz.ext"
          	idField="pk_integral_detail" multiSelect="true"  dataField="vos" 
          	allowCellSelect="true" showPageSize="false" showPager="false" >
       
      		<div property="columns">
      			<div type="checkcolumn"></div>
            	<div field="pk_audit_product" width="160px" headerAlign="center" allowSort="false">审批ID
            		<input property="editor" class="nui-textbox" style="width:100%;" allowInput="false"/>
            	</div>
		        <div field="apply_product_code" width="120px" headerAlign="center" allowSort="false">申请编号
		        	<input property="editor" class="nui-textbox" style="width:100%;" allowInput="false"/>
		        </div>
		        <div field="apply_product_name" width="80px" headerAlign="center" allowSort="false">申请名称
		        	<input property="editor" class="nui-textbox" style="width:100%;" allowInput="false"/>
		        </div> 
		        <div field="apply_product_num" width="70px" headerAlign="center" >申请数量
		        	<input property="editor" class="nui-textbox" style="width:100%;" allowInput="false"/>
		        </div> 
	            <div name="audit_product_num" field="audit_product_num" width="70px" >通过数量
	                <input property="editor" class="nui-textbox" vtype="int" style="width:100%;" required="true" />
	            </div> 
	            <div  name="audit_status"  field="audit_status" headerAlign="center" required="true" allowSort="true" width="60px" >状态
	                <input property="editor" class="nui-combobox" valueField="id" textField="text" data="applyData"
	                	required="true" dataField="vos" style="width:100%;"/>
	            </div>
	            <div name="remark" field="remark" width="100px" >备注
	                <input property="editor" class="nui-textbox" style="width:100%;" />
	            </div> 
        </div>
    </div>
    </form>
</body>
	<script type="text/javascript">
		var applyData =[{id:"2", text:"审批通过"},{id:"3", text:"审批拒绝"}];
		
		nui.parse();
		var grid = nui.get("datagrid");
		
		function startAudit(){
			debugger;
			grid.selectAll();
			editRow();
		}
		function SetData(data){
			debugger;
			var pk_audit_product = nui.clone(data);
			if(pk_audit_product){
				let params = {"params":{"pk_audit_product":pk_audit_product}};
				grid.load(params);
			}else{
				nui.alert("请选择");
			}
			/* let timeId = setTimeout(startAudit, 2000);//两秒后调用
			clearTimeout(timeId); */
			
		}
      	
      	/**
      		保存数据
      		com.xbkj.gd.data_handle.cust.integralNew.exchangeIntegral
      	*/
      	function saveData(){
      		var formGrid = new  nui.Form("#formGrid");
		    formGrid.validate();
			if(formGrid.isValid()==false ){
				nui.alert("请选择状态或输入数量");
				return;
			}
      		grid.commitEdit();
			var changes = grid.getData();
//			var changes = grid.getChanges();
			debugger;
			var count = 0;
			for(var i = 0; i < changes.length; i++){
				if(changes[i].audit_product_num <= 0){
					count = count + 1;
				}
			}
			
			if(count > 0){
				nui.alert("通过数量必须大于0");
				startAudit();
				return false;
			}
			debugger;
			nui.get("savedata").setEnabled(false);//防止反复提交
			var json = nui.encode({vos:changes});
			nui.ajax({
				url:"com.xbkj.gd.data_handle.prod.auditProduct.audit.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(text){
					var msg = nui.decode(text);
					debugger;
					nui.alert(msg.msg.message);
					if(msg.msg.flag){//添加成功关闭，添加不成功，不关闭窗口
						CloseWindow("ok");
						return;
					}
					nui.get("savedata").setEnabled(true)
					startAudit();
				},
				error: function (jqXHR, textStatus, errorThrown) {
                    nui.alert(jqXHR.responseText);
                    CloseWindow();
                }
			});
			nui.get("savedata").setEnabled(true)
      	}
      	function editRow(){
      		var rows = grid.getSelecteds();
	      	if (rows.length > 0) {
	          	for(var i = 0; i < rows.length; i++){
	          		grid.beginEditRow(rows[i]);
	          	}
	      	}else{
	    	  	nui.alert("数据有误");
	      	}
      	}
      
      	/**
      	*/
      	function removeRow(){
      		var rows = grid.getSelecteds();
	      	if (rows.length > 0) {
	          	grid.removeRows(rows, true);
	      	}else{
	    	  	nui.alert("请选择");
	      	}
      	}
      	function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if(window.CloseOwnerWindow){
				return window.CloseOwnerWindow(action);
			}else{
				window.close();
			}   
                   
        }
	</script>
</html>