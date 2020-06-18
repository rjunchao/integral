<%@page pageEncoding="UTF-8"%>
<%@page import="com.primeton.cap.AppUserManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Author(s): janno
  - Date: 2015-01-11 15:24:13
  - Description:
-->
<head>
<title>线下积分管理系统2.0-用户登录</title>
<%
   String contextPath=request.getContextPath();
 %>
<script type="text/javascript" src="<%=contextPath%>/common/nui/nui.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/coframe/auth/login/css/style.css" />
<script type="text/javascript">
    if(top!=window){
        top.location.href=top.location.href; 
        top.location.reload; 
    }
</script>
</head>
<%
	String original_url=null;
	Object objAttr=request.getAttribute("original_url");
	if(objAttr != null){
		original_url=(String)objAttr;
	}
	
	//获取cookie缓存:用户名自动填充
	String name=request.getParameter("userId");
	Cookie cookies[]=request.getCookies();
	if(name!=null){
		Cookie c = new Cookie("userId",name);
		c.setPath("/");
		c.setMaxAge(60*60*24*365);
		response.addCookie(c);
	}else if(cookies ==null){
		//System.out.println("cookies is null");
	}else if(cookies !=null){
		for(int i=0;i<cookies.length;i++){
			if(cookies[i].getName().equals("userId")){
				name=cookies[i].getValue();
				continue;
			}
		}
	}

 %>
<body class="login">
	<div id="warpper" class="wrap">
		<div class="main">
			<div id="form1" class="login-box">
				<h3 style="text-align:left;">线下积分管理系统2.0</h3>
				<p class="login-item">
				  <em>用户名：</em>
				  <input class="nui-textbox" id="userId" name="userId" style="width:237px;height:26px;"
				   onvalidation="onCheckUserId" value="<%if(name!=null) out.print(name); %>"/>
				</p>
				<p class="login-item">
				  <em>密　码：</em>
				  <input name="password" class="nui-password" vtype="minLength:6" minLengthErrorText="密码不能少于6个字符"
		                onenter="keyboardLogin" style="width:237px;height:26px;" onvalidation="onCheckPassword"/>
				</p>
				<p id="error" class="login-error" style="display:inline-block;height:20px;color:red;"></p>
				<div>
					<center><a href="https:www.baidu.com" target="_blank">最新操作演示</a> </center>
				</div>
				<p class="login-btn center">
					<input class="log" type="button" onclick="login();" value="登 录" />
				</p>
				
			</div>
		</div>
		<div class="foot">
			<p> Copyright © 北京融和友信有限公司版权所有 <span></span></p>
		</div>
	</div>
	
   <script type="text/javascript">
     nui.parse();
     var form = new nui.Form("#form1");
     
     nui.get("userId").focus();
     
     function login(){
       form.validate();
       if(form.isValid()==false) return;
       
       var data = form.getData();
       var json = nui.encode(data);
       nui.ajax({
         url:"org.gocom.components.coframe.auth.LoginManager.login.biz.ext",
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
     
     function reset(){
       form.reset();
     }
     
     function onCheckUserId(e){
       if (e.isValid) {
         if(e.value==""){
           e.errorText = "用户名不能为空";
           e.isValid = false;
         }
       }
     }
     
     function onCheckPassword(e){
       if (e.isValid) {
         if(e.value==""){
           e.errorText = "密码不能为空";
           e.isValid = false;
         }
       }
     }
    
     //获取键盘 Enter 键事件并响应登录
     function keyboardLogin(e){
       login();
     }
     
     
   </script>
 </body>
</html>
