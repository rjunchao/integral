<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-11-02 15:46:39
  - Description: 审批明细
-->
<head>
<title>审批明细</title>
</head>
<body>
	<form id="formGrid">
    	 <div id="datagrid" class="nui-datagrid" style="width:100%;height:290px;" allowResize="true" 
          	 url="com.xbkj.gd.data_handle.prod.giftAudit.auditDetail.biz.ext"
         	 	allowCellSelect="true" showPageSize="false" showPager="false" dataField="vos">
      		<div property="columns">
		        <div field="audit_user" width="100px" headerAlign="center" allowSort="false">审批人</div>
		        <div field="audit_date" width="160px" headerAlign="center" allowSort="false" dateFormat="yyyy-MM-dd hh:mm:ss">审批时间</div>
		        <div field="audit_type" type="comboboxcolumn" renderer="statusRenderer" width="60px" headerAlign="center" allowSort="false" >状态</div>
		       	<div field="remark" width="120px" headerAlign="center" allowSort="false">备注</div>
        	</div>
    	</div>
    </form>
</body>
	<script type="text/javascript">
		var statusData =[{id:"1",text:"同意"},{id:"2", text:"拒绝"}];
		
		nui.parse();
		var grid = nui.get("datagrid");
		
		function statusRenderer(e){
      		var val = e.value;
      		
        	for(let i = 0; i < statusData.length; i++){
        		let inn = statusData[i];
        		if(inn.id == val){return inn.text;}
        	}
      	}
		
		function SetData(data){
			var pk_org_apply_product = nui.clone(data);
			if(pk_org_apply_product){
				let params = {"applyId":pk_org_apply_product};
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