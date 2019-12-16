<%@page import="com.primeton.cap.AppUserManager"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-11-02 14:41:16
  - Description: office审批
-->
<head>
<title>office审批</title>
<script src="<%=request.getContextPath() %>/gd/data_handle/integralManage/swfupload/swfupload.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/gd/data_handle/integralManage/common/common.js" type="text/javascript"></script>
</head>
<body>
	<div id="form1" style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table>
	            <tr>
		           
		            <td>
		                	名称：<input id="apply_product_name" class="nui-textbox" name="apply_product_name" 
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
		            		<a class="nui-button" iconCls="icon-upload" onclick="orgAudit()" plain="true">同意</a>
		            	<a class="nui-button" iconCls="icon-cancel" onclick="orgAbort()" plain="true">拒绝</a>
		            	<a class="nui-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
   
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.prod.subBranch.sbPageQuery.biz.ext"  
		    	idField="pk_product" dataField="vos" allowResize="false"  multiSelect="true" 
		    	allowCellSelect="false">
		    <input class="nui-hidden" name="pk_org_apply_product"/>
		    <div property="columns">
		        <div type="checkcolumn"></div>
		       <!--  <div field="pk_audit_product" width="150px" headerAlign="center" allowSort="false">审批ID</div> -->
		        <div field="apply_product_name" width="120px" headerAlign="center" allowSort="false">礼品名称</div> 
		        <div field="apply_product_num" width="90px" headerAlign="center" >礼品数量</div> 
		        <div field="apply_product_integral" width="90px" headerAlign="center" >礼品积分</div> 
		        <!-- <div field="audit_product_num" width="90px" headerAlign="center" >审批通过数量</div>  -->
		        <div field="audit_status" type="comboboxcolumn" renderer="statusRenderer" width=120px" headerAlign="center" allowSort="false" >状态</div>
		        <div field="empname" width="120px" headerAlign="center" allowSort="false">申请人</div>
		        <div field="audit_user" width=120px" headerAlign="center" allowSort="false">审批人</div>
		        <div field="audit_date" width=120px" headerAlign="center" allowSort="false" dateFormat="yyyy-MM-dd hh:mm:ss">审批时间</div>
		       	<div field="remark" width=120px" headerAlign="center" allowSort="false">备注</div> 
		    </div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		
		
		nui.parse();
		var grid = nui.get("grid");
		var form = new nui.Form("#form1");
		function statusRenderer(e){
      		var val = e.value;
        	for(let i = 0; i < applyStatus.length; i++){
        		let inn = applyStatus[i];
        		if(inn.id == val){return inn.text;}
        	}
      	}
      	
      	function orgAbort(){
      		/**
      			拒绝
      		*/
      		var data = grid.getSelecteds();
			var len = data.length;
			if(data == null || len <= 0){
				nui.alert("请选择");
				return;
			}
			nui.open({
				url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/sb/ref/orgAbort.jsp",
				title:"礼品申请拒绝",
				width:260,
				height:180,
				onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.SetData(data);
				},
				ondestroy:function(action){
					if(action == "ok"){
						search();
						nui.alert("操作成功");
					}
				}
			});
      	}
      
      	function orgAudit(){
      		/**
      			
      		*/
      		let rows = grid.getSelecteds();
      		if(rows == null || rows.length <= 0){
      			nui.alert("请选择");
      			return ;
      		}
      		nui.confirm("确定同意礼品申请？","同意确认", function(action){
				if(action == "ok"){
					//
					var json = nui.encode({vos:rows});
					nui.ajax({
						url:"com.xbkj.gd.data_handle.prod.giftAudit.officeAudit.biz.ext",
						cache:false,
						async:true,
						data:json,
						type:"POST",
						contentType:"text/json",
						success:function(text){
							var msg = nui.decode(text);
							nui.alert(msg.msg.message);
							if(msg.msg.flag){
								search();
							}
						},
						error: function (jqXHR, textStatus, errorThrown) {
		                    alert(jqXHR.responseText);
		                }
					});
				}
			});
      	}
		function search(){
			//查询
			let params = form.getData();
			params.audit_status=4;//状态为4的
			grid.load({params:params});
		}
			
	</script>
</body>
</html>