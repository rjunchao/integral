<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2018-09-08 08:17:06
  - Description: 积分兑换
-->
<head>
<title>积分兑换</title>
</head>
<body>
	<form id="formGrid">
       <div style="width:800px;">
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
	                        <a class="nui-button" iconCls="icon-addnew" plain="true" onclick="addRow()">增加</a>
                        	<a class="nui-button" iconCls="icon-edit" onclick="editRow()" plain="true">编辑</a>
	                        <a class="nui-button" iconCls="icon-remove" plain="true" onclick="removeRow()">删除</a> 
	                        <span class="separator"></span>
	                        <a class="nui-button" iconCls="icon-edit" onclick="custSignFun()" plain="true">客户签名</a>
	                        <span class="separator"></span>
                        	<a class="nui-button" iconCls="icon-save" onclick="saveData()" plain="true" id="savedata">保存</a>      
	                    </td>
	                </tr>
	            </table>           
        	</div>
    	</div>
    	 <div id=datagrid class="nui-datagrid" style="width:800px;height:380px;" allowResize="true" 
          idField="pk_integral_detail" multiSelect="true"  allowCellSelect="true" showPageSize="false" showPager="false" >
       
      		<div property="columns">
            	<div type="checkcolumn"></div>
            	<!--  showNullItem="true" com.xbkj.gd.data_handle.cust.integralConfig.configQuery.biz.ext?integral_type=2
	                	url="com.xbkj.gd.data_handle.cust.integralConfig.configQuery.biz.ext?integral_type=2"
	                	url="com.xbkj.gd.data_handle.prod.applyProduct.querySubProd.biz.ext"
            	
            	-->
            	<div  name="temp"  field="temp" headerAlign="center" allowSort="true" width="180px" >请选择
	                <input property="editor" class="nui-combobox" valueField="id" textField="text"
	                	url="com.xbkj.gd.data_handle.prod.subBranch.giftSub.biz.ext"
	                	required="true"
	                		dataField="vos" style="width:100%;" minWidth="200" onvaluechanged="computeIntegral" />
	            </div>
	            <div name="def5" field="def5" width="50px" >兑换数量
	                <input property="editor" class="nui-textbox" vtype="int" style="width:100%;" required="true" onvaluechanged="computeIntegral" />
	            </div> 
	            <div name="conversion_detail" field="conversion_detail" width="60px" >备注
	                <input property="editor" class="nui-textbox" style="width:100%;" />
	            </div> 
            	<div  name="def1"  field="def1" headerAlign="center" allowSort="true" width="160px" >兑换商品
	            </div>
            	<div  name="def2"  field="def2" headerAlign="center" allowSort="true" width="70px" >兑换商品单价
	            </div>
	            <div field="customer_integral" width="60px">积分
	                <!-- <input property="editor" class="nui-textbox" style="width:100%;" vtype="float"  /> -->
	            </div> 
        </div>
    </div>
    </form>
</body>
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("datagrid");
		var custSign = null;
		function custSignFun(){
		//客户签名
			//编辑
			nui.open({
				url:"<%=request.getContextPath() %>/gd/data_handle/integralManage/prod/sign/custSign/e560.jsp",
				title:"客户签名",
				width:900,
				height:520,
				onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.SetData();
				}, 
				ondestroy:function(action){
					if(action != "ok"){
						nui.alert("签名失败");
						return ;
					}
					var iframe = this.getIFrameEl();
			        var signData = iframe.contentWindow.GetData();
			        custSign = nui.clone(signData);
				}
			});
		}
		
      	var pk_customer_info = "";
		var countIntegral = 0;
		function SetData(data){
			var row = nui.clone(data);
			if(row){
				pk_customer_info = row.pk_customer_info;
			}else{
				nui.alert("身份证号为空");
			}
		}
		
	
		function computeIntegral(e){
			debugger;
			var def1 = null;
			let temp1 = null;
			
			var row = grid.getSelected();
			if(e.selected){
			
				def1 = e.selected.text;	
				row.def2 = e.value;	
				//id_名称_积分
				var vals = e.value.split("_");
				temp1 = vals[0];
				def1 = vals[1];//商品名称
				row.def2 = vals[2];//积分单位
			}else{
				def1= row.def1;
				temp1= row.temp1;
			}
			grid.commitEdit();
			
			var data = new Array();
			var def2 = row.def2;//积分
			var def5 = row.def5;//兑换数量
			data.def1 = def1;//中文名称
			data.def2 = def2;
			data.def5 = def5;
			data.temp1 = temp1;//兑换礼品主键
			data.conversion_detail = row.conversion_detail;
			if(def2 > 0 &&  def5 > 0){
				var integral = def2 * def5;
				data.customer_integral = Math.round(parseFloat(integral));//取整积分
			}else{
				data.customer_integral=0;
			}
			grid.updateRow(row,data);
		    grid.beginEditRow(row); 
		 //  	grid.selectAll();
		//	editRow();
		}
      	
      	/**
      		保存数据
      		com.xbkj.gd.data_handle.cust.integralNew.exchangeIntegral
      	*/
      	function saveData(){
      		var formGrid = new  nui.Form("#formGrid");
		    formGrid.validate();
			if(formGrid.isValid()==false  ){
				nui.alert("请选择类型或输入数量");
				return;
			}
			if(custSign == null || custSign == "" || custSign == undefined  ){
				nui.alert("请签名");
				return;
			}
      		grid.commitEdit();
			var changes = grid.getChanges();
			debugger;
			var vos = new Array();
			var vo = {};
			for(var i = 0; i < changes.length; i++){
				if(changes[i].def1 != undefined){
					vo.def1 = changes[i].def1;//积分中文
					if(changes[i].def2 != undefined){
						vo.def2 = changes[i].def2;//积分单位
						vo.def5 = changes[i].def5;//数量
						if(Number(changes[i].customer_integral) > 0){
							vo.customer_integral = changes[i].customer_integral;//积分
							vo.def4 = 2;//类型
							vo.temp1 = changes[i].temp1;//礼品id
							vo.customer_idcard = changes[i].customer_idcard;//客户主键
							vo.conversion_detail = changes[i].conversion_detail;//备注
							vo.def6=custSign;//签名
							vos.push(vo);
							vo= {};
						}
					}
				}
				
			}
			
			if(vos.length != changes.length){
				nui.alert("输入行中数据有错误");
				grid.selectAll();
				editRow();
				return false;
			}
			
			nui.get("savedata").setEnabled(false);//防止反复提交
			var json = nui.encode({vos:vos});
			nui.ajax({
				url:"com.xbkj.gd.data_handle.cust.integralNew.exchangeIntegral.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(text){
					var msg = nui.decode(text);
					nui.alert(msg.msg.message);
					if(msg.msg.flag){//添加成功关闭，添加不成功，不关闭窗口
						CloseWindow("ok");
					}
					nui.get("savedata").setEnabled(true)
					grid.selectAll();
					editRow();
				},
				error: function (jqXHR, textStatus, errorThrown) {
                    nui.alert(jqXHR.responseText);
                    CloseWindow();
                }
			});
			nui.get("savedata").setEnabled(true)
			
      	}
      	
      	function editRow(){
      		var rows = grid.getSelecteds();
	      	if (rows.length > 0) {
	          	for(var i = 0; i < rows.length; i++){
	          		grid.beginEditRow(rows[i]);
	          	}
	      	}else{
	    	  	nui.alert("请选择");
	      	}
      	}
      	
      	/**
      		添加行
      		def4:2,customer_idcard:pk_customer_info
      	*/
      	function addRow(){
      		var newRow = {def4:2,customer_idcard:pk_customer_info};
		  	grid.addRow(newRow, 0);
		    grid.beginEditRow(0);
		    
      	}
      	
      	/**
      	*/
      	function removeRow(){
      		var rows = grid.getSelecteds();
	      	if (rows.length > 0) {
	          	grid.removeRows(rows, true);
	      	}else{
	    	  	nui.alert("请选择");
	      	}
      	}
      	function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if(window.CloseOwnerWindow){
				return window.CloseOwnerWindow(action);
			}else{
				window.close();
			}   
                   
        }
	</script>
</html>