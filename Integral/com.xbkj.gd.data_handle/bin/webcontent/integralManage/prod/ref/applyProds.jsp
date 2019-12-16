<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-17 09:14:33
  - Description: 礼品申请
-->
<head>
<title>礼品申请</title>
</head>
<body>
	<form id="formGrid">
       <div style="width:100%;">
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
	                        <a class="nui-button" iconCls="icon-addnew" plain="true" onclick="addRow()">增加</a>
                        	<a class="nui-button" iconCls="icon-edit" onclick="editRow()" plain="true">编辑</a>
	                        <a class="nui-button" iconCls="icon-remove" plain="true" onclick="removeRow()">删除</a> 
	                        <span class="separator"></span>
                        	<a class="nui-button" iconCls="icon-save" onclick="saveData()" plain="true" id="savedata">保存</a>      
	                    </td>
	                </tr>
	            </table>           
        	</div>
    	</div>
    	 <div id=datagrid class="nui-datagrid" style="width:100%;height:260px;" allowResize="true" 
          idField="pk_integral_detail" multiSelect="true"  
          allowCellSelect="true" showPageSize="false" showPager="false" >
       
      		<div property="columns">
            	<div type="checkcolumn"></div>
            	<!--  showNullItem="true" -->
            	<div  name="apply_product_name"  field="apply_product_name" headerAlign="center" allowSort="true" width="120px" >请选择
	                <input property="editor" class="nui-combobox" valueField="id" textField="text"
	                	url="com.xbkj.gd.data_handle.prod.product.coms.biz.ext"
	                	required="true" dataField="vos" style="width:100%;" />
	            </div>
	            <div name="apply_product_num" field="apply_product_num" width="80px" >申请数量
	                <input property="editor" class="nui-textbox" vtype="int" style="width:100%;" required="true" />
	            </div> 
	            <div name="remark" field="remark" width="120px" >备注
	                <input property="editor" class="nui-textbox" style="width:100%;" />
	            </div> 
        </div>
    </div>
    </form>
</body>
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("datagrid");
		
		function SetData(data){
			
		}
      	
      	/**
      		保存数据
      		com.xbkj.gd.data_handle.cust.integralNew.exchangeIntegral
      	*/
      	function saveData(){
      		var formGrid = new  nui.Form("#formGrid");
		    formGrid.validate();
			if(formGrid.isValid()==false  ){
				nui.alert("请选择类型或输入数量");
				return;
			}
      		grid.commitEdit();
			var changes = grid.getChanges();
			debugger;
			var count = 0;
			for(var i = 0; i < changes.length; i++){
				if(changes[i].apply_product_num <= 0){
					count = count + 1;
				}
			}
			
			if(count > 0){
				nui.alert("申请数量必须大于0");
				grid.selectAll();
				editRow();
				return false;
			}
			debugger;
			nui.get("savedata").setEnabled(false);//防止反复提交
			var json = nui.encode({vos:changes});
			nui.ajax({
				url:"com.xbkj.gd.data_handle.prod.subBranch.sbApply.biz.ext",
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
						
					}
					nui.get("savedata").setEnabled(true)
					grid.selectAll();
					editRow();
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
	    	  	nui.alert("请选择");
	      	}
      	}
      	
      	/**
      		添加行
      		def4:2,customer_idcard:pk_customer_info
      	*/
      	function addRow(){
      		var newRow = {};
		  	grid.addRow(newRow, 0);
		    grid.beginEditRow(0);
		    
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