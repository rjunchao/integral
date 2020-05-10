<%@page import="com.primeton.cap.AppUserManager"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2020-05-10 09:24:11
  - Description:
-->
<head>
<title>礼品采购</title>
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
		            <td>
		                	状态：<input id="def1" name="def1" class="nui-combobox" data = "procurementData" style="width:150px;" value="0" />
		            
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
		            	<a class="nui-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
		            	<a class="nui-button" iconCls="icon-edit" onclick="procurement()" plain="true">采购</a>
		            	<a class="nui-button" iconCls="icon-download" onclick="downReport()" plain="true">导出</a>
		            
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
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <div field="empname" width="120px" headerAlign="center" allowSort="false">分理处</div> 
		        <div field="apply_product_name" width="120px" headerAlign="center" allowSort="false">礼品名称</div> 
		        <div field="apply_product_num" width="90px" headerAlign="center" >礼品数量</div> 
		        <div field="apply_product_integral" width="90px" headerAlign="center" >礼品积分</div> 
		        <div field="def1" type="comboboxcolumn" renderer="statusRenderer" width=120px" headerAlign="center" allowSort="false" >状态</div>
		       	<div field="remark" width=120px" headerAlign="center" allowSort="false">备注</div> 
		    </div>
		</div>
	</div>
	
	<form id="excelForm" action="<%=request.getContextPath() %>/gd/data_handle/integralManage/sb/expProcurement.jsp" 
	 	method="post" target="excelIFrame">
    	 <input type="hidden" name="def1" id="def1_form" />
    	 <input type="hidden" name="apply_product_name" id="apply_product_name_form" />
	</form>
	<script type="text/javascript">
		var procurementData = [{id:"0", text:"全部"}, {id:"1", text:"未采购"}, {id:"2", text:"已采购"}];
		nui.parse();
		var grid = nui.get("grid");
		var form = new nui.Form("#form1");
		function statusRenderer(e){
      		var val = e.value;
        	for(let i = 0; i < procurementData.length; i++){
        		let inn = procurementData[i];
        		if(inn.id == val){return inn.text;}
        	}
      	}
      	
      	function downReport(){
			let def1 = nui.get("def1").getValue();
			let apply_product_name = nui.get("apply_product_name").getValue();
			
			document.getElementById("def1_form").value=def1;
			document.getElementById("apply_product_name_form").value=apply_product_name;
			document.getElementById("excelForm").submit();
		}
      	
      	function procurement(){
      		/**
      			采购
      		*/
      		var data = grid.getSelecteds();
			var len = data.length;
			if(data == null || len <= 0){
				nui.alert("请选择");
				return;
			}
			nui.open({
				url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/sb/ref/orgProcurement.jsp",
				title:"礼品采购",
				width:260,
				height:220,
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
		function search(){
			//查询
			let params = form.getData();
			params.audit_status=7;//办公室审批通过的
			grid.load({params:params});
		}
			
	</script>
</body>
</html>