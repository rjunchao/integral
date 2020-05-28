<%@page import="com.primeton.cap.AppUserManager"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2020-05-10 10:31:47
  - Description: 按机构、时间对机构兑换的积分进行统计
-->
<head>
	<title>积分兑换统计</title>
</head>
<body>
	<div id="form1" style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table>
	            <tr>
		            <td>
		                	机构：<input class="nui-buttonedit" onbuttonclick="selectOrg" name="orgs" id="orgs" 
		                		showClose="true" oncloseclick="onCloseClick" style="width:150px;" allowInput="false"  emptyText="请选择..."/>
		            </td>
		             <td>
		                	开始时间：<input id="start_date" class="nui-datepicker" name="start_date" emptyText="请选择..." allowInput="true"style="width:100px;" />
		            </td>
		            <td>
		                	结束时间：<input id="end_date" class="nui-datepicker" name="end_date" 
		                		emptyText="请选择..." style="width:100px;" allowInput="true"/>
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
		            	<a class="nui-button" iconCls="icon-download" onclick="downReport()" plain="true">导出</a>
		            	
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.id_report.idReport.IntegralDetailReport.biz.ext"  
		    	idField="pk_product" dataField="vos" allowResize="false" multiSelect="false" 
		    	allowCellSelect="false">
		    <input class="nui-hidden" name="pk_apply_product"/>
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <div field="orgname" width="100px" headerAlign="center" >机构</div> 
		        <div field="tran_date" width="150px" headerAlign="center" allowSort="false">时间</div>
		        <div field="tran_integral" width="150px" headerAlign="center" allowSort="false">积分</div>
		    </div>
		</div>
	</div>
	
		<form id="excelForm"  action="<%=request.getContextPath() %>/gd/data_handle/integralManage/idReport/exportId.jsp" 
		 	method="post" target="excelIFrame">
		     <input type="hidden" name="report_type" value="2" />
		     <input type="hidden" name="start_date" id="start_date_form" />
	    	 <input type="hidden" name="end_date" id="end_date_form" />
	    	 <input type="hidden" name="orgs" id="orgs_form" />
		</form>
		<iframe id="excelIFrame" name="excelIFrame" style="display:none;"></iframe>
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("grid");
		var form = new nui.Form("#form1");
		function search(){
			//查询
			let params = form.getData();
			params.report_type = "2"
			grid.load({params:params});
		}
		
		function downReport(){
			let orgs = nui.get("orgs").getValue();
			let start_date = nui.get("start_date").getValue();
			let end_date = nui.get("end_date").getValue();
			document.getElementById("orgs_form").value=orgs;
			document.getElementById("start_date_form").value=start_date;
			document.getElementById("end_date_form").value=end_date;
			document.getElementById("excelForm").submit();
		}
		
		
		function selectOrg(){
      		var btnEdit = this;
	       	nui.open({
				url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/customerRef/orgsRefs.jsp",
			    showMaxButton: false,
			    title: "机构选择",
			    width: 420,
			    height: 460,
			    ondestroy: function (action) {             
			    	if (action == "ok") {
			        	var iframe = this.getIFrameEl();
			            var datas = iframe.contentWindow.GetData();
			            debugger;
	                    datas = nui.clone(datas);
			           	if (datas) {
			           		let ids = "";
			           		let text = "";
			           		let len = datas.length;
			           		for(let i = 0; i < len; i++){
		           				ids = ids + "," + datas[i].id;
		           				text = text + "," + datas[i].text;
			           		}
	                       	btnEdit.setText(text);
	                       	btnEdit.setValue(ids);
	                   }
			        }
			    }
			});
      	}
		function onCloseClick(e) {
            var obj = e.sender;
            obj.setText("");
            obj.setValue("");
        }
	</script>
</body>
</html>