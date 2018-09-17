<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Author(s): shitf
  - Date: 2013-03-03 16:20:11
  - Description:
-->
<head>
<%@include file="/coframe/tools/skins/common.jsp" %>
<title>用户列表</title>
</head>
<body style="width:100%;overflow:hidden;">
 <div class="search-condition">
   <div class="list">
	  <div id="form1">
	    <table class="table" style="width:100%;">
	      <tr>
	        <td class="tit">&nbsp;&nbsp;用户名称：</td>
	        <td>
	          <input class="nui-textbox" name="criteria._expr[1].userName"/>
	          <input class="nui-hidden" name="criteria._expr[1]._op" value="like"/>
	          <input class="nui-hidden" name="criteria._expr[1]._likeRule" value="all"/>
	        </td>
	        <td class="tit">&nbsp;&nbsp;用户状态：</td>
	        <td>
	          <input class="nui-dictcombobox" valueField="dictID" textField="dictName" emptyText="全部"
	           dictTypeId="COF_USERSTATUS" name="criteria._expr[2].status" showNullItem="true" nullItemText="全部"/>
	          <input class="nui-hidden" name="criteria._expr[2]._op" value="="/>
	        </td>
	        <td class="btn-wrap">
	          <a class="nui-button" iconCls="icon-search" onclick="search">查询</a>
	        </td>
	      </tr>
	    </table>
	  </div>
    </div>
  </div>
  
  <div style="margin:10px 0px 0px 0px;">
    <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
      <table style="width:100%;">
        <tr>
           <td style="width:100%;">
              <a class="nui-button" iconCls="icon-add" onclick="add">增加</a>
		     <a id="update" class="nui-button" iconCls="icon-edit" onclick="update">编辑</a>
		     <a class="nui-button" iconCls="icon-remove" onclick="remove">删除</a>
		     <a id="auth" class="nui-button" onclick="auth" iconCls="icon-authorization">权限配置</a>
		     <a id="calculate" class="nui-button" onclick="authCalculate" iconCls="icon-auth-calculate">权限计算</a>
		     <span class="separator"></span>  
		     <a class="nui-button" onclick="resetPassword" iconCls="icon-change-password">重置密码</a>
           </td>
        </tr>
      </table>
    </div>
  </div>
  
  <div class="nui-fit">
	 <div id="datagrid1" class="nui-datagrid" style="width:100%;height:100%;" idField="operatorId"
	  url="org.gocom.components.coframe.rights.UserManager.queryUser.biz.ext"
	  sizeList=[5,10,20,50,100] multiSelect="true" pageSize="10" onselectionchanged="selectionChanged">
	    <div property="columns" >
	      <div type="checkcolumn"></div>
	      <div field="userId" headerAlign="center">登录用户名</div>
	      <div field="userName" headerAlign="center">用户名称</div>
	      <div field="authmode" headerAlign="center" renderer="renderAuthmode">认证模式</div>
	      <div field="status" headerAlign="center" renderer="renderStatus">用户状态</div>
	      <div field="email" headerAlign="center">邮箱</div>
	      <div field="createuser" headerAlign="center">创建人</div>
	    </div>
	 </div>
  </div>
  
  <script type="text/javascript">
    nui.parse();
    var grid = nui.get("datagrid1");
    grid.load();
    
    function search(){
       var form = new nui.Form("#form1");
       var data = form.getData(true,true);
       grid.load(data);
    }
    
    function add(){
       nui.open({
          url:"<%=request.getContextPath() %>/coframe/rights/user/user_add.jsp",
          title:'新增用户',
          width:610,
          height:290,
          onload:function(){
          },
          ondestroy:function(action){
             if(action=="saveSuccess"){
	            grid.reload();
	         }
          }
       });
    }
    
    function update(){
       var row = grid.getSelected();
       if(row!=null){
	       nui.open({
	          url:"<%=request.getContextPath() %>/coframe/rights/user/user_update.jsp",
	          title:'编辑用户',
	          width:610,
	          height:290,
	          onload:function(){
	             var iframe = this.getIFrameEl();
	             var data = row;
	             iframe.contentWindow.SetData(data);
	          },
	          ondestroy:function(action){
	             if(action=="saveSuccess"){
	                grid.reload();
	             }
	          }
	       });
       }else{
           nui.alert("请选中一条记录！");
       }
    }
    
    function remove(){
      var rows = grid.getSelecteds();
      if(rows.length > 0){
         nui.confirm("确定删除选中记录？","系统提示",
           function(action){
             if(action=="ok"){
	            var json = nui.encode({users:rows});
	            grid.loading("正在删除中,请稍等...");
		        $.ajax({
		          url:"org.gocom.components.coframe.rights.UserManager.deleteUser.biz.ext",
		          type:'POST',
		          data:json,
		          cache: false,
		          contentType:'text/json',
		          success:function(text){
		            var returnJson = nui.decode(text);
					if(returnJson.exception == null){
						nui.alert("删除用户成功", "系统提示", function(action){
							grid.reload();
						});
					}else{
						nui.alert("删除用户失败", "系统提示");
						grid.unmask();
					}
					
		          }
		        });
		     }
		   });
      }else{
        nui.alert("请选中一条记录！");
      }
    }
    
    function auth(){
       var row = grid.getSelected();
       if(row!=null){
	       nui.open({
	          url:"<%=request.getContextPath() %>/coframe/rights/user/user_auth.jsp",
	          title:'用户授权',
	          width:600,
	          height:500,
	          onload:function(){
	         	var iframe = this.getIFrameEl();
                var data = {
                   nodeId:row.userId,
                   nodeType:"user"
                };
                iframe.contentWindow.SetData(data);
	          },
	          ondestroy:function(){
	          }
	       });
       }else{
           nui.alert("请选中一条记录！");
       }
    }
    
    function authCalculate(){
       var row = grid.getSelected();
       if(row!=null){
	       nui.open({
	          url:"<%=request.getContextPath() %>/coframe/rights/user/user_auth_graph.jsp",
	          title:'权限计算',
	          width:650,
	          height:500,
	          onload:function(){
	            var iframe = this.getIFrameEl();
                var data = {
                   nodeId:row.userId,
                   nodeType:"user"
                };
                iframe.contentWindow.SetData(data);
	          },
	          ondestroy:function(){
	          }
	       });
       }else{
           nui.alert("请选中一条记录！");
       }
    }
    
    function resetPassword(){
       var rows = grid.getSelecteds();
       if(rows.length>0){
	       nui.confirm("确定将密码重置为000000？","系统提示",
           function(action){
             if(action=="ok"){
	            var json = nui.encode({users:rows});
	            grid.loading("正在重置中,请稍等...");
		        $.ajax({
		          url:"org.gocom.components.coframe.rights.UserManager.updatePasswords.biz.ext",
		          type:'POST',
		          data:json,
		          cache: false,
		          contentType:'text/json',
		          success:function(text){
		            var returnJson = nui.decode(text);
					if(returnJson.exception == null){
						nui.alert("重置密码成功", "系统提示", function(action){
							grid.reload();
						});
					}else{
						nui.alert("重置密码失败", "系统提示");
						grid.unmask();
					}
		          }
		        });
		     }
		   });
       }else{
           nui.alert("请选中一条记录！");
       }
    }
    
    function renderAuthmode(e){
       return nui.getDictText("COF_AUTHMODE", e.row.authmode);
    }    
    
    function renderStatus(e){
       return nui.getDictText("COF_USERSTATUS",e.row.status);
    }
    
    function selectionChanged(){
       var rows = grid.getSelecteds();
       if(rows.length>1){
           nui.get("update").disable();
           nui.get("auth").disable();
           nui.get("calculate").disable();
       }else{
           nui.get("update").enable();
           nui.get("auth").enable();
           nui.get("calculate").enable();
       }
    }
  </script>
</body>
</html>