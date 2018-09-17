<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.primeton.cap.AppUserManager"%>
<html>
<head>
<%@include file="/coframe/tools/skins/common.jsp" %>
<title>融和友信-管理系统</title>
<style type="text/css">
.dtHighLight{
	background:#F0F8FF !important;
}
</style>
</head>
<body>
<div id="wrapper" class="wrap">
	<!--侧边栏-->
	<div class="sidebar">
		<!--用户信息区-->
		<div class="user">
			<img class="head" src="<%=contextPath%>/coframe/tools/skins/skin1/images/user-head.gif" width="51" height="51" alt="" />
			<p class="tips">
				<span class="font-1"><strong>您好，<%=AppUserManager.getCurrentUserId() %></strong></span>
				<span><a class="set" href="#" openJsp="<%=contextPath%>/coframe/rights/user/update_password.jsp">修改密码1</a><a class="login-out" href="<%=contextPath%>/coframe/auth/login/logout.jsp" target="_top">注销</a></span>
			</p>
		</div>
		<!--左侧菜单-->
		<div class="menu-wrap">
			<ul class="menu">
				
			</ul>
		</div>
		<ul id="contextMenu" class="nui-contextmenu" >
				    <li>
						<span >操作</span>
						<ul>
						    <li onclick="onItemClick">新建</li>
				            <li class="separator"></li>
				            <li  onclick="onItemClick">增加</li>	
							<li  onclick="onItemClick">修改</li>
					        <li onclick="onItemClick">删除</li>	 	             
						</ul>
					</li>
				    <li class="separator"></li>
					<li  >打开</li>
					<li  >关闭</li>
				</ul>
	</div>
	<!--右侧主内容区-->
	<div class="main">
		<!--面包屑导航条-->
		<div class="positionbar">
			<ul class="position clearfix" id="positionbar">
				<li class="index">
					<a><span>首页</span></a><b class="arrow"></b>
				</li>
			</ul>
		</div>
		<!--主体内容显示区-->
		<div class="submain radius">
			<b class="b1"></b>
			<b class="b2"></b>
			<div class="fmain">
				<div class="nui-fit" style="padding:5px;">
				<iframe id="mainframe" src="<%=contextPath %>/coframe/auth/welcome/welcome.jsp" frameborder="0" name="main" style="width:100%;height:100%;" border="0"></iframe>
				</div>
			</div>
			<b class="b3"></b>
			<b class="b4"></b>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	nui.parse();
	
	var iframe = document.getElementById("mainframe");
	//iframe.src = "";
	
	getMenuData();
	
	function getMenuData(){
		$.ajax({
			url: "org.gocom.components.coframe.auth.LoginManager.getMenuData.biz.ext",
			type: "POST",
			success: function(text){
				var treeNodes = text.treeNodes;
				setMenuData(treeNodes);
			}
		});
	}
	
	function setMenuData(data){
		if(data){
			var menusHtml = "";
			var secondMenulast = "</dd>";
			for(var i = 0; i < data.length; i++){
				var menuName = data[i].menuName;
				var linkAction = data[i].linkAction ? data[i].linkAction: "";
				var menuPrimeKey = data[i].menuPrimeKey;
				var menuSeq = data[i].menuSeq;
				var dt = "<dt><a href='#' url='" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a></dt>";
				var secondChilds = "";
				if(data[i].childrenMenuTreeNodeList){
					var Lev2childrens = data[i].childrenMenuTreeNodeList;
					for(var j = 0; j < Lev2childrens.length; j++){
						var menuName = Lev2childrens[j].menuName;
						var linkAction = Lev2childrens[j].linkAction ? Lev2childrens[j].linkAction : "";
						var menuPrimeKey = Lev2childrens[j].menuPrimeKey;
						var menuSeq = Lev2childrens[j].menuSeq;
						secondChilds += "<dd><ul class=\"module_m\" onmouseover='addHighLight(this)'  onmouseout='removeHighLight(this)'><li><a href='#' url='" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "<i>>></i></a></li></ul>";
						if(Lev2childrens[j].childrenMenuTreeNodeList){
							secondChilds += "<div>";
							var Lev3childrens = Lev2childrens[j].childrenMenuTreeNodeList;
							for(var k = 0; k < Lev3childrens.length; k++){
								var menuName = Lev3childrens[k].menuName;
								var linkAction = Lev3childrens[k].linkAction ? Lev3childrens[k].linkAction : "";
								var menuPrimeKey = Lev3childrens[k].menuPrimeKey;
								var menuSeq = Lev3childrens[k].menuSeq;
								secondChilds += "<li id='lev3smenu'><a href='#' url='" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "<i>>></i></a></li>";
							}
							secondChilds += "</div>";
						}
					}
				}
				var secondMenu = secondChilds + secondMenulast ;
				if(secondChilds == ""){
					menusHtml += "<li><dl>" + dt + "</dl></li>";
				}else{
					menusHtml += "<li><dl>" + dt + secondMenu + "</dl></li>";
				}
			}
			insertMenuToHtml(menusHtml);
		}
	}
	
	function addHighLight(t){
		$(t).addClass("dtHighLight");
	}
	
	function removeHighLight(t){
		$(t).removeClass("dtHighLight");
	}
	
	function insertMenuToHtml(menusHtml){
		$(".menu").html(menusHtml);
		$("li#lev3smenu").hover(function(){
	    	$(this).addClass("dtHighLight");
	    },function(){
	    	$(this).removeClass("dtHighLight");
	    }
	    );
		$("dt").click(function(){
	  		var has = $(this).parent().hasClass("current");
	  		if(has){
	  			$(this).parent().removeClass("current");
	  		}else{
		    	$(this).parent().addClass("current");
	  		}
	    });
	    $('.module_m').live('click',function(){
	    	$(this).parent().toggleClass('ddcurrent');
	    });
	   
	    $("ul").click(function(){
	    	if(this.parentNode.tagName.toLowerCase() == "dd"){
	    		
		    	if(this.parentNode.children[1] && this.parentNode.children[1].tagName.toLowerCase() == "dc"){
			  		var has = $(this).parent().hasClass("ddcurrent");
			  		if(has){
			  			$(this).parent().removeClass("ddcurrent");
			  		}else{
				    	$(this).parent().addClass("ddcurrent");
			  		}
		    	}
	    	}
	    });
	    $("a").click(function(){
	    	if($(this).attr("url")){
		    	var url = $(this).attr("url") ? $(this).attr("url") : "";
		    	setIFrame(url);
		    	var menuSeq = $(this).attr("menuSeq").split(".");
		    	initialPositionbar();
		    	for(var i = 0; i <　menuSeq.length; i++){
		    		if(menuSeq[i] != ""){
		    			setPositionbar(document.getElementById(menuSeq[i]).firstChild.data, document.getElementById(menuSeq[i]).href);
		    		}
		    	}
	    	}
	    	var jspUrl = $(this).attr("openJsp");
	    	if(jspUrl){
	    		nui.open({
	    			url: jspUrl,
	    			title:"修改密码",
	    			width: "370px",
	    			height: "200px"
	    		});
	    	}
	    });
        
	}
	
	function setIFrame(url){
		var relative = url.substr(0,1);
		if(relative == "/"){
			url = "<%=contextPath%>" + url;
		}
		iframe.src = url;
	}
	
	function setPositionbar(name, url){
		var currentHtml = $("#positionbar").html();
		currentHtml += "<li><!--[if lt IE 8]><span class='left'></span><![endif]--><a>" + name + "</a><b class='arrow'></b></li>";
		$("#positionbar").html(currentHtml);
	}
	
	function initialPositionbar(){
		var currentHtml = $("#positionbar").html();
		var initialData = "<li class='index'><a><span>首页</span></a><b class='arrow'></b></li>";
		$("#positionbar").html(initialData);
		
	}
	
	
	
	
</script>
</html>
