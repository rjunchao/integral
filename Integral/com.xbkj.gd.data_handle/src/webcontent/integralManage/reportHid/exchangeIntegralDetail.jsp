<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2018-09-08 20:32:23
  - Description:积分兑换详情
-->
<head>
	<title>积分兑换详情</title>
	<script src="<%=request.getContextPath() %>/gd/data_handle/integralManage/swfupload/swfupload.js" type="text/javascript"></script>
</head>
<body>
	<div id="form1" style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table>
	            <tr>
		            <td>
		                	身份证号：<input id="customer_idcard" class="nui-textbox" name="customer_idcard" 
		                		 required="true" emptyText="请选择..." style="width:150px;" />
		            </td>
		            <td>
		                	开始时间：<input id="start_date"class="nui-datepicker" name="start_date" emptyText="请选择..." allowInput="true"style="width:100px;" />
		            </td>
		            <td>
		                	结束时间：<input id="end_date" class="nui-datepicker" name="end_date" 
		                		emptyText="请选择..." style="width:100px;" allowInput="true"/>
		            </td>
		            <td>
		            	<a class="nui-button" iconCls="icon-search" onclick="search('2')" plain="true">查询</a>
		            	<!-- <a class="nui-button" iconCls="icon-edit" onclick="custSign()" plain="true">客户签名</a> -->
		            	<a class="nui-button" iconCls="icon-goto" onclick="viewSign()" plain="true">查看签名</a>
		            	<%--
		            	<a class="nui-button" iconCls="icon-download" onclick="downIntegral()" plain="true">导出客户积分明细</a>
		            	 <input id="fileupload1" class="nui-fileupload"  style="width:180px;height:24px;"
						    flashUrl="<%=request.getContextPath() %>/gd/data_handle/integralManage/swfupload/swfupload.swf"
						 	uploadUrl="<%=request.getContextPath() %>/gd/data_handle/integralManage/importHandller/importExchangeIntegralDetail.jsp"
							onuploadsuccess="onUploadSuccess"
							onuploaderror="onUploadError" /> 
				 		<a class="nui-button" iconCls="icon-upload" plain="true" onclick="startUpload()">导入积分兑换明细</a> --%>
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
		
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
		<input id="pk_customer_info" name="pk_integral_detail" class="nui-hidden" />
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.cust.integralManager.queryCustIntegal.biz.ext"  
		    	idField="pk_integral_detail" dataField="vos" allowResize="true" multiSelect="true" 
		    	allowCellSelect="false">
		    <input class="nui-hidden" name="pk_integral_detail"/>
		    <div property="columns">
		        <div type="checkcolumn"></div>
		         <div field="def3" width="60px" headerAlign="center" allowSort="false">年份</div>
		        <div field="def8" width="60px" headerAlign="center" allowSort="false">序号</div>
		        <div field="customer_name" width="60px" headerAlign="center" allowSort="false">客户名</div>
		        <div field="customer_idcard" width="150px" headerAlign="center" allowSort="false">客户身份证号</div>
		        <div field="customer_integral" width="60px" headerAlign="center" allowSort="false">积分</div>
		       <!--  
		       	<div field="customer_account" width="60px" headerAlign="center" allowSort="false">账号</div>
		        <div field="deposit_receipt_num" width="80px" headerAlign="center" allowSort="false">存单号号</div>  
		        <div field="deposit_receipt_amt" width="90px" align="center" headerAlign="center" allowSort="false">存单金额</div> 
		        <div field="conversion_detail" width="60px" headerAlign="center" allowSort="false">兑换明细</div>
		        -->
		        <div field="def2" width="120px" headerAlign="center" allowSort="false">兑换类型积分</div>
		        <div field="def5" width="80px" headerAlign="center" allowSort="false">兑换数量</div>
		        <div field="def1" width="90px" headerAlign="center" allowSort="false">兑换商品</div>
		        <div field="ts" width="130px" headerAlign="center" allowSort="false" dateFormat="yyyy-MM-dd hh:mm:ss">录入时间</div>
		        <div field="empname" width="120px" headerAlign="center" allowSort="false">录入人</div>
		        <div field="orgname" width="120px" headerAlign="center" allowSort="false">录入机构</div>
		       <!--  <div field="def6" width="60px" headerAlign="center" allowSort="false">签名</div> -->
		        <div field="conversion_detail" width="180px" headerAlign="center" allowSort="false">备注</div>
		    </div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		nui.parse();
		var integral_type = 2;
		var grid = nui.get("grid");
		var form = new nui.Form("#form1");
		function viewSign(){
		//查看签名
			var data = grid.getSelecteds();
			var len = data.length;
			if(data == null || len <= 0){
				nui.alert("请选择");
				return ;
			}
			if(data[0].def6 == null || data[0].def6 == ""){
				nui.alert("签名不存在");
				return;
			}
			if(len > 1){
				nui.alert("只能选择一条记录");
				return ;
			}
			nui.open({
				url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/prod/sign/signView.jsp",
				title:"查看客户签名",
				width:520,
				height:480,
				onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.SetData(data[0].def6);
				}
			});
		}
		
		function custSign(){
		//客户签名
			var data = grid.getSelecteds();
			var len = data.length;
			debugger;
			if(data == null || len <= 0){
				nui.alert("请选择");
				return ;
			}
			for(let i = 0; i < len; i++){
				if(data[0].def6 != null && data[0].def6 != ""){
					nui.alert("签名已存在");
					return ;
				}
			}
			let ids = "";
			for(let i = 0; i < len; i++){
				ids = ids + data[i].pk_integral_detail + ","
			}
			//编辑
			nui.open({
				url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/prod/sign/custSign/e560.jsp?ids=" + ids,
				title:"客户签名",
				width:900,
				height:520,
				onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.SetData(ids);
				}, 
				ondestroy:function(action){
					if(action == "ok"){
						search('2');
						nui.alert("签名成功");
					}
				}
			});
		}
		 /**
        	文件导入
        */
        function startUpload(e){
        	var file = nui.get("fileupload1");
			var name = file.getText();
			if(name == null || name == ""){
				nui.alert("请选择需要导入的文件");
				return;
			}else{
				file.startUpload();
				return;
			}
        }
        
        function onUploadError(e){
        	nui.alert("添加积分明细导入失败" + e);
        }
        
        function onUploadSuccess(e){
        	var data = e.serverData;
        	nui.alert(data);
        }
        
        function onFileSelect(e){
        }
		
		
		
		function customerRef(){
			var btnEdit = this;
			nui.open({
					url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/customerRef/customerRef.jsp",
					title:"选择客户",
					width:600,
					height:380,
					ondestroy:function(action){
						//判断是否成功与否
						if (action == "ok") {
				      		var iframe = this.getIFrameEl();
				      		var data = iframe.contentWindow.GetData();
				      		data = nui.clone(data);
				      		if (data) {
				      			var idcard = data.customer_idcard;
				      			var real_idcard = data.real_idcard;
				      			//nui.alert(idcard);
				       			nui.get("#customer_idcard").setValue(real_idcard);
				       			nui.get("#customer_idcard").setText(idcard);
				       			//$("#customer_idcard").val(idcard);
				       			//this.setValue(idcard);
				       		}
						}
					}
				});
		}
		function search(integral_type){
			//查询
			//查询
			if(form.validate() == false){
				nui.alert("身份证号必须输入");
				return;
			}
			var params = form.getData();
			params.hiddenFlag = 'Y';
			params.integral_type = integral_type;
			grid.load({params:params});
		}
		
		
			
	</script>
</body>
</html>