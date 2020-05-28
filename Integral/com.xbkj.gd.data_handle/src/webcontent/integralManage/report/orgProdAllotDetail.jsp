<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2020-05-13 15:47:47
  - Description:
-->
<head>
<title>分理处礼品提取明细</title>
</head>
<body>
	<div id="form1" style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table>
	            <tr>
		            <td>
		                	分理处：<input id="allot_org" class="nui-textbox" name="allot_org" 
		                		 required="false" emptyText="请输入..." style="width:150px;" />
		            </td>
		            <td>
		                	礼品：<input id="allot_prod" class="nui-textbox" name="allot_prod" 
		                		 required="false" emptyText="请输入..." style="width:150px;" />
		            </td>
		            <td>
		                	开始时间：<input id="start_date"class="nui-datepicker" name="start_date" emptyText="请选择..." allowInput="true"style="width:100px;" />
		            </td>
		            <td>
		                	结束时间：<input id="end_date" class="nui-datepicker" name="end_date" 
		                		emptyText="请选择..." style="width:100px;" allowInput="true"/>
		            </td>
		            <td>
		            	<a class="nui-button" iconCls="icon-search" onclick="search('3')" plain="true">查询</a>
		            	<!-- <span class="separator"></span>
		            	<a class="nui-button" iconCls="icon-download" onclick="downIntegral()" plain="true">导出客户积分明细</a> -->
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
	
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.prod.OrgProdAllot.page.biz.ext"  
		    	idField="pk_org_prod_allot" dataField="vos" allowResize="false" multiSelect="false" 
		    	allowCellSelect="false">
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <div field="allot_org" width="60px" headerAlign="center" allowSort="false">分理处</div>
		        <div field="allot_prod" width="80px" headerAlign="center" allowSort="false">礼品</div>
		        <div field="allot_num" width="60px" headerAlign="center" allowSort="false">数量</div>
		        <div field="ts" width="120px" headerAlign="center" allowSort="false"dateFormat="yyyy-MM-dd hh:mm:ss">时间</div>
		        <div field="remark" width="120px" headerAlign="center" allowSort="false">备注</div>
		    </div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("grid");
		var form = new nui.Form("#form1");
		function search(){
			debugger;	
			//查询
			var params = form.getData();
		/* 	params.hiddenFlag = 'N';
			params.integral_type = integral_type; */
			grid.load({params:params});
		}
		
		
			
	</script>
</body>
</html>