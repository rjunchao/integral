<%@page import="com.primeton.cap.AppUserManager"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-14 20:00:26
  - Description: 礼品管理申请
-->
<head>
	<title>礼品管理申请</title>
	<script src="<%=request.getContextPath() %>/gd/data_handle/integralManage/swfupload/swfupload.js" type="text/javascript"></script>
</head>
<body>
	<div id="form1" style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table>
	            <tr>
		            <!-- <td>
		                	编号：<input id="product_code"class="nui-textbox" name="product_code" emptyText="请输入..." style="width:140px;" />
		            </td> -->
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
		                <a class="nui-button" iconCls="icon-upload" onclick="apply()" plain="true">礼品申请</a>
		            	<a class="nui-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
   
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.prod.product.pageProduct.biz.ext"  
		    	idField="pk_product" dataField="vos" allowResize="false" multiSelect="false" 
		    	allowCellSelect="false">
		    <input class="nui-hidden" name="pk_product"/>
		    <div property="columns">
		        <div type="checkcolumn"></div>
		       <!--  <div field="product_code" width="150px" headerAlign="center" allowSort="false">礼品编号</div> -->
		        <div field="product_name" width="120px" headerAlign="center" allowSort="false">礼品名称</div> 
		       <!--  <div field="product_num" width="90px" headerAlign="center" >礼品数量</div>  -->
		        <div field="product_integral" width="90px" headerAlign="center" >礼品积分</div> 
		        <div field="remark" width=120px" headerAlign="center" allowSort="false">备注</div>
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
		
		
		function apply(){
			//申请申请
		    nui.open({
					url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/prod/ref/applyProds.jsp",
					title:"礼品申请",
					width:600,
					height:320,
					onload:function(){
						var iframe = this.getIFrameEl();
						//nui.alert(nui.encode(data[0]));
						iframe.contentWindow.SetData();
					},
					ondestroy:function(action){
						if(action == "ok"){
							search();
						}
					}
				});
      	}
		function search(){
			//查询
			let params = form.getData();
			params.product_num=1;//查询库存大于0的记录
			params.dr=0;//查询库存大于0的记录
			grid.load({params:params});
		}
			
	</script>
</body>
</html>