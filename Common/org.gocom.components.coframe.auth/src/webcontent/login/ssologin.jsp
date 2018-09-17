<%@page pageEncoding="UTF-8"%>
<%@include file="/coframe/tools/skins/common.jsp" %>
<%@include file="/common/skins/skin0/component.jsp" %>
<%
	String original_url=null;
	String usercode=request.getParameter("usercode");
	
	String password=request.getParameter("pwd");
	
	%>
<html>
<!-- 
  - Author(s): ljb
  - Date: 2016-08-02 23:09:51
  - Description:
-->
<head>
<title>单点登录</title>
</head>
<body>
 <script type="text/javascript">
     nui.parse();
     alert("------");
     login();
  function login(){
       
       
     //  var data = form.getData();
       var json = nui.encode({userId:'<%=usercode %>',password:'<%=password %>'});
       
       alert(json);
       nui.ajax({
         url:"org.gocom.components.coframe.auth.LoginManager.ssologin.biz.ext",
         type:'POST',
         data:json,
         success:function(text){
            var o = nui.decode(text);
            if(o.exception==null){
	           var ret = o.retCode;
	           if(ret==1){
	             location.href="<%=request.getContextPath() %>/coframe/auth/login/redirect.jsp?original_url=<%=original_url %>";
	           }else if(ret==0){
	             $("#error").html("输入密码错误");
	           }else if(ret==-2){
	           	 $("#error").html("用户无权限登录，请联系系统管理员");
	           }else{
	             $("#error").html("用户名不存在");
	           }
            }else{
               nui.alert("登录系统出错","系统提示");
            }
         }
       });
     }
</script>
</body>
</html>