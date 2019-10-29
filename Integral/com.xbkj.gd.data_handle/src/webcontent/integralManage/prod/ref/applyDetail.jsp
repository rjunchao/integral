<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-19 15:51:01
  - Description: 申请明细
-->
<head>
<title>申请明细</title>
</head>
<body>
	<form id="formGrid">
    	 <div id=datagrid class="nui-datagrid" style="width:100%;height:260px;" allowResize="true" 
          		idField="pk_apply_product" url="com.xbkj.gd.data_handle.prod.auditProduct.applyDetail.biz.ext"
         	 allowCellSelect="true" showPageSize="false" showPager="false"  dataField="vos" >
      		<div property="columns">
		        <div field="pk_audit_product" width="160px" headerAlign="center" allowSort="false">审批ID</div>
		        <div field="apply_product_code" width="120px" headerAlign="center" allowSort="false">申请编号</div>
		        <div field="apply_product_name" width="80px" headerAlign="center" allowSort="false">申请名称</div> 
		        <div field="apply_product_num" width="70px" headerAlign="center" >申请数量</div> 
		        <div field="audit_status" type="comboboxcolumn" renderer="statusRenderer" width="60px" headerAlign="center" allowSort="false" >状态</div>
		       	<div field="remark" width=120px" headerAlign="center" allowSort="false">备注</div>
        </div>
    </div>
    </form>
</body>
	<script type="text/javascript">
		var applyData =[{id:"1",text:"审批中"},{id:"2", text:"审批通过"},{id:"3", text:"审批拒绝"}];
		
		nui.parse();
		var grid = nui.get("datagrid");
		
		function statusRenderer(e){
      		var val = e.value;
      		
        	for(let i = 0; i < applyData.length; i++){
        		let inn = applyData[i];
        		if(inn.id == val){return inn.text;}
        	}
      	}
		
		function SetData(data){
			var pk_audit_product = nui.clone(data);
			if(pk_audit_product){
				let params = {"params":{"pk_audit_product":pk_audit_product}};
				grid.load(params);
			}else{
				nui.alert("请选择");
			}
		}
      	function CloseWindow(action) {            
            if(window.CloseOwnerWindow){
				return window.CloseOwnerWindow(action);
			}else{
				window.close();
			}   
                   
        }
	</script>
</html>