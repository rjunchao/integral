<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): fangwl (mailto:fangwl@primeton.com)
  - Date: 2013-02-28 10:38:33
  - Description:应用列表
-->
<head>
</head>
<body>	
		<div class="search-condition">
			<div class="list">
				<div id="form1">
					<table class="table">
						<tr>
							<td class="tit roleLabel" style="width:60px;">应用名称：</td>
							<td class="roleText" style="width:20%">
							  <input name="criteria/_expr[1]/appname" class="nui-textbox" style="width:90%;"/>
				              <input type="hidden" class="nui-hidden" name="criteria/_expr[1]/_op" value="like"/>
				              <input type="hidden" class="nui-hidden" name="criteria/_expr[1]/_likeRule" value="all"/>
					        </td>
							<td  class="tit" style="width:60px;">应用类别：</td>
							<td class="roleText" style="width:18%">
								<input class="nui-dictcombobox" name="criteria/_expr[2]/apptype" valueField="dictID" emptyText="全部" textField="dictName" showNullItem="true" style="width:88%;" nullItemText="全部" dictTypeId="COF_APPTYPE"/>
								<input type="hidden" class="nui-hidden" name="criteria/_expr[2]/_op" value="="/>
					        </td>
					        <td class="btn-wrap" style="width:60px;">
								<a class="nui-button" style="width:60px;" iconCls="icon-search" onclick="search()">查询</a>
							</td>
							<td></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div style="padding:10px 10px 0px 10px;">
		 <div class="nui-toolbar" style="border-bottom:0;">
	        <table style="width:100%;">
	            <tr>
		            <td style="width:100%;">
		                <a class="nui-button" iconCls="icon-add" onclick="add()">增加</a>
		            	<a class="nui-button" iconCls="icon-edit" onclick="edit()" id="edit_btn">编辑</a>
		            	<a class="nui-button" iconCls="icon-remove" onclick="remove()">删除</a>
		            </td>
	            </tr>
	        </table>
	    </div>
	    </div>
		<div class="nui-fit" style="padding:0px 10px 10px 10px;">
		    <div id="appgrid" class="nui-datagrid" style="width:100%;height:100%" 
		    url="org.gocom.components.coframe.framework.ApplicationManager.queryApplication.biz.ext" onselectionchanged="selectionChanged" idField="appid" allowResize="false"
		    sizeList="[10,20,50,100]" multiSelect="true" onrowdblclick="edit()" pageSize="15">
			    	<div property="columns">
			        <div type="checkcolumn" ></div>
			        <div field="appname" headerAlign="center" allowSort="false">应用名称</div>    
			        <div field="apptype" headerAlign="center" allowSort="false" renderer="onTypeRenderer">应用类型</div>
			        <div field="isopen" headerAlign="center" allowSort="false" renderer="onIsOpenRenderer">是否开通</div>
			        <div field="url" headerAlign="center" allowSort="false">应用上下文</div>
			        <div field="ipaddr" headerAlign="center" allowSort="false">应用IP</div>
			        <div field="ipport" headerAlign="center" allowSort="false">应用端口</div>
			        <div field="protocoltype" headerAlign="center" allowSort="false">协议类型</div>
			    </div>
			</div>
			
		</div>
	
    <script type="text/javascript">
	 	nui.parse();
		var grid = nui.get("appgrid");
	    grid.load();
	    var form = new nui.Form("#form1"); 
        function search() {
			var data = form.getData(false,false);      //获取表单多个控件的数据
            grid.load(data);
        }
        
        function add() {
            nui.open({
                url: "<%=request.getContextPath() %>/coframe/framework/application/application_add.jsp",
                title: "新增应用", 
                width: 550, 
            	height: 345,
                ondestroy: function (action) {
                    if(action=="ok"){
                    	parent.refresh();
                        grid.reload();
                    }
                }
            });
        }
        
        function edit() {
            var row = grid.getSelected();
            if (row) {
                nui.open({
                    url: "<%=request.getContextPath() %>/coframe/framework/application/application_edit.jsp",
                    title: "编辑应用", 
                    width: 550, 
           			height: 345,
                    allowResize:false,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = row;
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        if(action=="ok"){
	                    	parent.refresh();
	                        grid.reload();
                   	 	}
                    }
                });
                
            } else {
                alert("请选中一条记录");
            }
            
        }
        
        function remove() {
            var rows = grid.getSelecteds();
            
            var nodes = [];
            
            for(var i=0; i<rows.length;i++){
            	nodes.push({realId:rows[i].appid,type:"application"});
            }
            if (rows.length > 0) {
            	nui.confirm("应用下所有的功能信息及功能组信息也将被删除，确定？","删除确认",function(action){
	            	if(action!="ok") return;
	            	var json = nui.encode({nodes:nodes});
	                $.ajax({
	                     url: "org.gocom.components.coframe.framework.ApplicationManager.deleteApplications.biz.ext",
		                type: 'POST',
		                data: json,
		                cache: false,
		                contentType:'text/json',
	                    success: function (text) {
	                        grid.reload();
	                        parent.refresh();
	                    },
	                    error: function () {
	                    }
	                });
                }); 
            } else {
                alert("请选中一条记录");
            }
        }
        
        function resetForm(){
			form.reset();
			search();
		}
		
		function onTypeRenderer(e) {
            if (e.value == 1) return "远程";
            else return "本地";
        }
        
        function onIsOpenRenderer(e) {
            if (e.value == "1") return "是";
            else return "否";
        }
        
        function selectionChanged(){
			var rows = grid.getSelecteds();
			if(rows.length>1){
				//disable edit button
				nui.get("edit_btn").disable();
			}else{
				nui.get("edit_btn").enable();
			}
		}
	</script>
</body>
</html>