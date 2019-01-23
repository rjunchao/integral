<%@page import="com.primeton.cap.AppUserManager"%>
<%@page import="com.xbkj.gd.utils.UserUtils"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2017-09-06 10:22:18
  - Description:
-->
<head>
<title>客户管理</title>
</head>
<body>
	<div id="form1" style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table>
	            <tr>
		            <td>
		                	姓名：<input id="customer_name"class="nui-textbox" name="customer_name" emptyText="请输入..." style="width:140px;" />
		            </td>
		            <td>
		                	身份证号：<input id="customer_idcard" class="nui-textbox" name="customer_idcard" 
		                		emptyText="请输入..." style="width:150px;" />
		            </td>
		            <td>
		                	手机号：<input id="customer_phone" class="nui-textbox" name="customer_phone" 
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
		                <a class="nui-button" iconCls="icon-addnew" onclick="add()" plain="true">增加</a>
		            	<!-- 
		            	<a class="nui-button" iconCls="icon-edit" onclick="edit()" id="edit_btn"  plain="true">编辑</a>
		            	<a class="nui-button" iconCls="icon-remove" onclick="remove()" plain="true" >删除</a> 
		            	-->
		            	<a class="nui-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
		            	<span class="separator"></span>
		            	<a class="nui-button" iconCls="icon-undo" onclick="addIntegral()" plain="true">添加积分</a>
		            	<a class="nui-button" onclick="vipIntegral()" plain="true">VIP积分赠送</a>
		            	<!-- 
		            	<a class="nui-button" iconCls="icon-undo" onclick="queryIntegral()" plain="true">查看积分</a>
		            	<a class="nui-button" iconCls="icon-undo" onclick="integralDetail()" plain="true">积分详细信息</a> 
		            	-->
		            	<a class="nui-button" iconCls="icon-redo" onclick="subIntegral()" plain="true">积分兑换</a>
		            	<a class="nui-button" iconCls="icon-downgrade" onclick="leadIntegral()" plain="true">积分提前支取</a>
		            	
		            	<!-- 
		            	<span class="separator"></span>
		            	<a class="nui-button" iconCls="icon-download" onclick="downCustomerInfo()" plain="true">导出客户信息</a>
		            	<a class="nui-button" iconCls="icon-upload" onclick="upCustomerInfo()" plain="true">导入客户信息</a> 
		            	<span class="separator"></span>
		            	<a class="nui-button" iconCls="icon-download" onclick="downIntegral()" plain="true">导出客户积分信息</a>
		            	-->
		            	
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
    <!-- 导出 -->
	 <form id="excelForm"  
	 	action="<%=request.getContextPath() %>/gd/data_handle/integralManage/downloadCustomer.jsp" 
	 	method="post" target="excelIFrame">
	    <input type="hidden" name="customer_idcard" id="customer_idcard_form" />
	    <input type="hidden" name="customer_name" id="customer_name_form" />
	    <input type="hidden" name="customer_phone" id="customer_phone_form" />
	    <input type="hidden" name="start_date" id="start_date_form" />
	    <input type="hidden" name="end_date" id="end_date_form" /> 
	     <input type="hidden" name="hiddenFlag" value="Y" />
	</form>
	<iframe id="excelIFrame" name="excelIFrame" style="display:none;"></iframe>
		
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
		<input id="pk_customer_info" name="pk_customer_info" class="nui-hidden" />
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.cust.CustomerManager.queryCustomer.biz.ext"  
		    	idField="pk_customer_info" dataField="vos" allowResize="false" multiSelect="false" 
		    	allowCellSelect="false">
		    <input class="nui-hidden" name="pk_customer_info"/>
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <div field="customer_name" width="60px" headerAlign="center" allowSort="false">姓名</div>
		        <div field="customer_idcard" width="150px" headerAlign="center" allowSort="false">身份证号</div> 
		        <div field="customer_phone" width="90px" align="center" headerAlign="center" allowSort="false">电话号码</div> 
		        <div field="now_usable_integral" width="60px" headerAlign="center" allowSort="false">当前可用积分</div>
		        <div field="def1" width="60px" headerAlign="center" allowSort="false">VIP</div>
		     	<div field="empname" width=120px" headerAlign="center" allowSort="false">录入人</div> 
		        <div field="orgname" width=120px" headerAlign="center" allowSort="false">建档机构</div>
		        <div field="recommend_phone" width=120px" headerAlign="center" allowSort="false">备注</div>
		    </div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("grid");
		var form = new nui.Form("#form1");
		var userId="<%=AppUserManager.getCurrentUserId()%>";
		/**
			查询客户积分
		*/
		function queryIntegral(){
			
		}
		/**
			vip积分赠送
		*/
		function vipIntegral(){
			var row = grid.getSelected();
			if(row){
				if(row.def1 != 'Y'){
					nui.alert("只能对vip客户进行赠送积分");
					return ;
				}
				row.customer_idcard=row.real_idcard;
				var json = nui.encode({vo:row});
					nui.ajax({
						url:"com.xbkj.gd.data_handle.cust.integralNew.vipIntegralQuery.biz.ext",
						cache:false,
						async:false,
						data:json,
						type:"POST",
						contentType:"text/json",
						success:function(text){
						debugger;
							if(text){
								if(text.count > 0){
									nui.alert("今年已经对该vip客户赠送过积分了");
									return;
								}else{
									nui.open({
										url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/integral/vipIntegral.jsp",
										title:"vip积分赠送",
										width:300,
										height:200,
										onload:function(){
											var iframe = this.getIFrameEl();
							        		var data = { pk_customer_info: row.pk_customer_info};
							        		iframe.contentWindow.SetData(data);
										},
										ondestroy:function(action){
											//判断是否成功与否
											if(action == "ok"){
												//	search();
												nui.alert("vip客户积分赠送成功");
												search();
											}
										}
									});
								}
							}
						},
						error: function (jqXHR, textStatus, errorThrown) {
		                    alert(jqXHR.responseText);
		                }
					});
			}else{
				nui.alert("请选择需要添加积分的客户！");
			}
		}
		/**
			积分提前支取
		*/
		function leadIntegral(){
			var row = grid.getSelected();
			if(row){
				nui.open({
					url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/integral/leadIntegral.jsp",
					title:"提前支取扣除",
					width:440,
					height:260,
					onload:function(){
						var iframe = this.getIFrameEl();
		        		var data = { pk_customer_info: row.pk_customer_info};
		        		iframe.contentWindow.SetData(data);
					},
					ondestroy:function(action){
						//判断是否成功与否
						if(action == "ok"){
							//	search();
							nui.alert("提前支取扣除成功");
							search();
						}
					}
				});
			}else{
				nui.alert("请选择需要提前支取积分的客户！");
			}
		}
		
		/**	
			查看积分明细
		*/
		function integralDetail(){
			nui.open({
					url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/editCustomer.jsp",
					title:"修改客户信息",
					width:600,
					height:180,
					onload:function(){
						var iframe = this.getIFrameEl();
						//nui.alert(nui.encode(data[0]));
						iframe.contentWindow.SetData(data[0]);
					},
					ondestroy:function(action){
						if(action == "ok"){
							search();
						}
					}
				});
		}
		
		//编辑
		function edit(){
			var data = grid.getSelecteds();
			var len = data.length;
			if(len > 0 ){
				var creater = data[0].create_user;
				/* nui.alert("userId: " +userId);
				nui.alert("creater: " +creater); */
				/* if(creater != userId){
					nui.alert("你没有修改权限");
					return;
				} */
			
				//编辑
				nui.open({
					url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/editCustomer.jsp",
					title:"修改客户信息",
					width:600,
					height:220,
					onload:function(){
						var iframe = this.getIFrameEl();
						//nui.alert(nui.encode(data[0]));
						iframe.contentWindow.SetData(data[0]);
					},
					ondestroy:function(action){
						if(action == "ok"){
							search();
						}
					}
				});
			}else{
				nui.alert("请选择要编辑的行");
				return;
			}
		}	
		
		
		function add(){
			 //添加
			//添加元素
			nui.open({
				url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/addCustomer.jsp",
				title:"添加客户信息",
				width:600,
				height:220,
				ondestroy:function(action){
					//判断是否成功与否
					if(action == "ok"){
						search();
					}
				}
			});
		}
		
		function remove(){
			//删除
			var row = grid.getSelected();
			if(row){
				var creater = row.create_user;
				if(creater != userId){
					nui.alert("你没有修改权限");
					return;
				}
			
				//删除id数组
				nui.confirm("确定要删除该客户么？","删除确认", function(action){
				if(action == "ok"){
					//具体的删除
					var json = nui.encode({vo:row});
					nui.ajax({
						url:"com.xbkj.gd.data_handle.cust.CustomerManager.delCustomer.biz.ext",
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
		}else{
			nui.alert("请选择要删除的客户");
		}
	}
		/**
			添加积分
		*/
		function addIntegral(){
			var row = grid.getSelected();
			if(row){
				nui.open({
					url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/integral/addCustIntegralNew.jsp",
					title:"添加客户积分",
					width:600,
					height:500,
					onload:function(){
						var iframe = this.getIFrameEl();
		        		var data = { pk_customer_info: row.pk_customer_info};
		        		iframe.contentWindow.SetData(data);
					},
					ondestroy:function(action){
						//判断是否成功与否
						if(action == "ok"){
							//	search();
							nui.alert("添加客户积分成功");
							search();
						}
					}
				});
			}else{
				nui.alert("请选择需要添加积分的客户！");
			}
		}
		
		/**
			减少积分
		*/
					<%-- url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/subCustIntegral.jsp", --%>
		function subIntegral(){
			var row = grid.getSelected();
			if(row){
				nui.open({
					url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/integral/exchangeIntegral.jsp",
					title:"兑换客户积分",
					width:680,
					height:440,
					onload:function(){
						var iframe = this.getIFrameEl();
						iframe.contentWindow.SetData({pk_customer_info:row.pk_customer_info});
					},
					ondestroy:function(action){
						//判断是否成功与否
						if(action == "ok"){
							//	search();
							nui.alert("客户兑换积分成功");
							search();
							grid.clearRows();
						}
					}
				});
			}else{
				nui.alert("请选择需要兑换积分的客户！");
			}
		}
		
		
		/**
			下载客户信息
		*/
		function downCustomerInfo(){
			var idcard = nui.get("customer_idcard").getValue();
			var cust_name = nui.get("customer_name").getValue();
			
			var customer_phone = nui.get("customer_phone").getValue();
			var start_date = nui.get("start_date").getValue();
			var end_date = nui.get("end_date").getValue(); 
			
			document.getElementById("customer_idcard_form").value=idcard;
			document.getElementById("customer_name_form").value=cust_name;
			
			document.getElementById("customer_phone_form").value=customer_phone;
			document.getElementById("start_date_form").value=start_date;
			document.getElementById("end_date_form").value=end_date; 
			
			document.getElementById("excelForm").submit();	
		}
		
		/**
			导入客户信息
		*/
		function upCustomerInfo(){
		
		}
		
		/**
			下载积分信息
		*/
		function downIntegral(){
			
		}
		
		
		//是否是叶子菜单的显示
		function onIsLeafRenderer(e){
			if(e.value == "1") return "是";
			else return "否";
		}
		
		
		function search(){
			//查询
			var params = form.getData();
			//	{"customer_name":"","customer_idcard":"","customer_phone":""}
			if(params.customer_name == "" && params.customer_idcard == "" && params.customer_phone == ""){
				nui.alert("请输入一个条件进行查询");
				return;
			}
			params.hiddenFlag = 'Y';
			grid.load({params:params});
		}
			
	</script>
</body>
</html>