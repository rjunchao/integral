<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-18 17:03:35
  - Description: 审批申请的礼品
-->
<head>
	<title>审批申请的礼品</title>
	<script src="<%=request.getContextPath() %>/gd/data_handle/integralManage/swfupload/swfupload.js" type="text/javascript"></script>
</head>
<body>
	<div id="form1" style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table>
	            <tr>
		            <td>
		                	审批id：<input id="pk_audit_product"class="nui-textbox" name="pk_audit_product" emptyText="请输入..." style="width:140px;" />
		            </td>
		            <td>
		                	申请人：<input id="apply_user" class="nui-textbox" name="apply_user" 
		                		emptyText="请输入..." style="width:150px;"/>
		            </td>
		            <td>
		                	审批人：<input id="audit_user" class="nui-textbox" name="audit_user" 
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
		                <a class="nui-button" onclick="detail()" plain="true">查看明细</a>
		                <a class="nui-button"  onclick="audit()" plain="true">审批</a>
		            	<a class="nui-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
   
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.prod.auditProduct.page.biz.ext"  
		    	idField="pk_product" dataField="vos" allowResize="false" multiSelect="false" 
		    	allowCellSelect="false">
		    <input class="nui-hidden" name="pk_audit_product"/>
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <div field="pk_audit_product" width="180px" headerAlign="center" allowSort="false">审批ID</div>
		        <div field="apply_user" width="100px" headerAlign="center" allowSort="false">申请人</div> 
		        <div field="apply_date" width="180px" headerAlign="center" dateFormat="yyyy-MM-dd hh:mm:ss">申请时间</div> 
		        <div field="orgname" width=120px" headerAlign="center" allowSort="false">申请人所属机构</div>
		        <div field="audit_user" width=120px" headerAlign="center" allowSort="false">审批人</div>
		        <div field="audit_date" width=180px" headerAlign="center" allowSort="false" dateFormat="yyyy-MM-dd hh:mm:ss">审批时间</div>
		        <div field="audit_status" type="comboboxcolumn" renderer="statusRenderer" width="80px" headerAlign="center" >状态
				</div>
		        <div field="remark" width=120px" headerAlign="center" allowSort="false">备注</div>
		        <div field="ts" width="180px" headerAlign="center" allowSort="false" dateFormat="yyyy-MM-dd hh:mm:ss">提交时间</div>
		    </div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		var auditData =[{id:"1",text:"未审批"},{id:"2", text:"审批完毕"},{id:"3", text:"部分审批"}];
		nui.parse();
		var grid = nui.get("grid");
		var form = new nui.Form("#form1");
		
		
		function audit(){
			//审批
			var data = grid.getSelecteds();
			var len = data.length;
			if(len > 0 ){
				var pk_audit_product = data[0].pk_audit_product;
				nui.open({
					url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/prod/ref/applyAudit.jsp",
					title:"礼品申请审批 ",
					width:860,
					height:320,
					onload:function(){
						var iframe = this.getIFrameEl();
						iframe.contentWindow.SetData(pk_audit_product);
					},
					ondestroy:function(action){
						if(action == "ok"){
							search();
						}
					}
				});
			}else{
				nui.alert("请选择");
			}
		}
		
		function detail(){
			//查看明细
			var data = grid.getSelecteds();
			var len = data.length;
			if(len > 0 ){
				var pk_audit_product = data[0].pk_audit_product;
				nui.open({
					url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/prod/ref/applyDetail.jsp",
					title:"礼品申请",
					width:660,
					height:320,
					onload:function(){
						var iframe = this.getIFrameEl();
						iframe.contentWindow.SetData(pk_audit_product);
					},
					ondestroy:function(action){
						if(action == "ok"){
							search();
						}
					}
				});
			}else{
				nui.alert("请选择");
			}
		    
      	}
      	
      	function statusRenderer(e){
      		var val = e.value;
        	for(let i = 0; i < auditData.length; i++){
        		let inn = auditData[i];
        		if(inn.id == val){return inn.text;}
        	}
      	}
		function search(){
			//查询
			let params = form.getData();
			params.product_num=1;//查询库存大于0的记录
			grid.load({params:params});
		}
			
	</script>
</body>
</html>