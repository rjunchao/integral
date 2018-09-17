<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.primeton.cap.AppUserManager"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/coframe/tools/skins/common.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>普元-用户机构及权限管理系统</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/coframe/tools/skins/skin2/css/style.css" />
<style type="text/css">

.nav_subcats_div {
    background-color: #EDEDED;
    border-left: 1px solid #DEDEDE;
    border-right: 2px solid #F7F7F7;
    height: 100%;
    left: 0;
    padding: 0 2px 0 0;
    position: absolute;
    width: 0;
}

.dlCls {
	border-top: 1px solid #D4D4D4;
	position:absolute;
	width:110px;
	margin-left:10px;
	bottom:0px;
	padding-left:10px;
    height: 40px;
    line-height: 32px;
}

.lev3 {
	padding-left:10px;
}

#levmenu{
	border-top:4px solid #3DA2E1;
	position:absolute;
	display: none;
	width:160px;
	min-height:400px;
	top:30px;
	left:2px;
	background:white;
	margin-left:10px;
	box-shadow: -10px 10px 10px #666666,10px 10px 10px #666666;
	filter : progid:DXImageTransform.Microsoft.Shadow(Strength=5, Direction=230, Color="#666666");
}

#lev2menu{
	border-top:4px solid #3DA2E1;
	position:absolute;
	display: none;
	min-width:250px;
	max-width:500px;
	min-height:400px;
	top:30px;
	left:172px;
	background:white;
	box-shadow: 10px 10px 10px #666666;
	filter : progid:DXImageTransform.Microsoft.Shadow(Strength=5, Direction=135, Color="#666666");
}

#levmenu dt{
    height: 32px;
    line-height: 32px;
    padding-left:10px;
	background:#fff url(<%=contextPath%>/coframe/tools/skins/skin2/images/menu-item-current-bg.gif) repeat-y;
}

#lev2menu dt{
    min-height:32px;
    padding-left:20px;
    padding-right:40px;
    line-height: 32px;
	background:#fff;
}

dl a{
	padding: 0 9px 0 8px;
	font-size:13px;
	cursor:pointer;
	height:32px;
}

dl ul.lev3 a{
	padding: 0 9px 0 8px;
	font-size:13px;
	height:32px;
}

dl ul.lev3 a.Nav{
	padding: 0 9px 0 8px;
	font-size:13px;
	height:32px;
	border-left: 1px solid #CCCCCC;
}

dl ul.lev3 a:hover{
	color: #FF7F24;
	font-weight:bold;
}

dl a.lev2 {
	font-size:15px;
	height:32px;
	color: #3A5FCD;
}

.dtSelected{
	color: #FF7F24 !important;
	font-weight:bold;
}

dl a.lev2 a:hover{
	color: #FF7F24;
	font-weight:bold;
}

.menuArrow{
	background:#F4F4F4 url(<%=contextPath%>/coframe/tools/skins/skin2/images/arrow-right2.png) no-repeat 97% 50% !important;
}

dl a:hover{
	color: #FF7F24;
	font-weight:bold;
}

.menuStart {
	background: url(<%=contextPath%>/coframe/tools/skins/skin2/images/menustart-normal.png) no-repeat !important;
	cursor:pointer;
	width:94px;
	height:42px;
	text-align:right;
	padding-top:10px;
	margin-top:-11px;
	margin-left:10px;
}

.menuStartHover{
	background: url(<%=contextPath%>/coframe/tools/skins/skin2/images/menustart-hover.png) no-repeat !important;
	width:94px;
	height:42px;
	text-align:right;
	padding-top:10px;
	margin-top:-11px;
}

.menuStart span {
	padding-right:15px;
	font-size:16px;
	color:#4169E1;
	font-weight:bold;
	margin-right:0;
}

</style>

</head>
<body class="index">
<div id="wrapper" class="wrap">
	<div id="header">
		<div class="head-in clearfix">
			<div class="fl clearfix">
				<h1 class="logo"></h1>
				<h2 class="name"></h2>
			</div>
			<div class="options fr">
				<div class="time font-5"><span id="currentData"></span></div>
			</div>
		</div>
	</div>
	<div id="container">
		<div id="wrapper" class="wrap">
			<div class="h-menu-wrap">
				<ul class="h-menu">
					<dl id="hmenu" class="menuStart"><span>菜单</span></dl>
					<dl id="levmenu" ></dl>
					<dl id="lev2menu" ></dl>
				</ul>
				<ul class="user">
					<li class="name">您好，<%=AppUserManager.getCurrentUserId() %></li>
					<li class="hendle"> <span><a id="updatepassword" class="set" href="#" openJsp="<%=contextPath%>/coframe/rights/user/update_password.jsp">修改密码</a><a class="login-out" href="<%=contextPath%>/coframe/auth/login/logout.jsp" target="_top">注销</a></span> </li>
				</ul>
			</div>
			<div class="h-main" id="mainField" style="z-index:-1;positon:relative">
					<iframe id="mainframe" src="<%=contextPath %>/coframe/auth/welcome/welcome.jsp" frameborder="0" name="main" style="width:100%;height:100%;"></iframe>
			</div>
		</div>
	</div>
	<div id="footer">
		<p>(c) Copyright Primeton 2012. All Rights Reserved.</p>
	</div>
</div>
</body>
<script type="text/javascript">
	nui.parse();
	
	var date = new Date();
	var currentDate = date.getFullYear() + "年" + (date.getMonth()+ 1) + "月" + date.getDate() + "日";
	$("#currentData").text(currentDate);
	
	//主内容区
	var iframe = document.getElementById("mainframe");
	
	//获取菜单数据
	getMenuData();
	
	//监听修改密码链接点击事件
	$("#updatepassword").click(function(){
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
	
	//监听鼠标点击事件（用来隐藏菜单）
	$(document).click(function(e){
		LeaveLev1Menu(e);
	});
	
	//监听菜单按钮鼠标移上移下事件
	$("#hmenu").hover(function(){
		if($("#levmenu").css("display") == "none"){
			$(this).addClass("menuStartHover");
			showLev1Menu();
		}
	},function(e){
		leaveHMenu(e);
	}
	);
	
	//监听一级菜单区域鼠标移上移下事件
	$("#levmenu").hover(function(){
	},function(e){
		LeaveLev1Menu(e);
	}
	);
	
	//监听二级菜单区域鼠标移上移下事件
	$("#lev2menu").hover(function(){
	},function(e){
		LeaveLev2Menu(e);
	}
	);

	//展现二级以及更多级菜单
	function showLev2Menu(position){
		document.getElementById("lev2menu").style.display = 'block';
		document.getElementById("mainField").style.zIndex = "-1";
		if(lev2menu){
			var lev2menuHtml = lev2menu[position];
			document.getElementById("lev2menu").innerHTML = "<div class='nav_subcats_div'></div>" + lev2menuHtml;
		}
		var lev1menuHeight = $("#levmenu").css("height");
		var lev2menuHeight = $("#lev2menu").css("height");
		if(lev2menuHeight > lev1menuHeight){
			$("#levmenu").css("min-height", lev2menuHeight);
		}else{
			$("#lev2menu").css("min-height", lev1menuHeight);
		}
		//监听<a>标签点击事件，如果有url，则在主内容区展现url,如果有jspUrl，则跳转到该url界面
		$("a").click(function(){
	    	if($(this).attr("url")){
		    	var url = $(this).attr("url") ? $(this).attr("url") : "";
		    	setIFrame(url);
		    	document.getElementById("levmenu").style.display = 'none';
	        	document.getElementById("lev2menu").style.display = 'none';
	        	document.getElementById("mainField").style.zIndex = "1";
	    	}
	    });
	    
	    //二级菜单监听鼠标事件，选中时，字体颜色，粗细有变化
	    $("#lev2menu dt").hover(function(){
	    	$(this).find("a:first").addClass("dtSelected");
	    },function(){
	    	$(this).find("a:first").removeClass("dtSelected");
	    });
	    //三级以及更多级菜单第一个左侧不展现boder
	    if($(".lev3").children()){
			$(".lev3").each(function(){
				$(this).find("a").first().removeClass("Nav");
			});
		}
	}
	
	//清除一级菜单的背景选中箭头
	function removeArrow(){
		$("#levmenu").children().each(function(){
			if($(this).hasClass("menuArrow")){
				$(this).removeClass("menuArrow");
			}
		});
	}
	
	//展现一级菜单，并且监听鼠标在一级菜单区域上的出入事件
	function showLev1Menu(){
		document.getElementById("levmenu").style.display = 'block';
		document.getElementById("mainField").style.zIndex = "-1";
		removeArrow();
		$("#levmenu dt").hover(function(){
				removeArrow();
				$(this).addClass("menuArrow");
				$(this).children().addClass("dtSelected");
			},function (e) {
				LeaveLev1Menu(e);
				$(this).children().removeClass("dtSelected");
			}
		);
		var lev1menuNum = $("#levmenu").children().length - 1;
		if((lev1menuNum * 32 + 40) > 100){
			$("#levmenu").css("height", lev1menuNum * 32 + 100);
		}
	}
	
	//鼠标离开菜单开始按钮时，判断位置，并作相应处理（如隐藏一级、二级菜单等）
	function leaveHMenu(e){
		var x = e.clientX;
		var y = e.clientY;
		var pmenuDiv = document.getElementById("levmenu");
		if(pmenuDiv){
			var divx1 = pmenuDiv.offsetLeft;  
	        var divy1 = pmenuDiv.offsetTop;  
	        var divx2 = pmenuDiv.offsetLeft + pmenuDiv.offsetWidth;  
	        var divy2 = pmenuDiv.offsetTop + pmenuDiv.offsetHeight;
	        if( x < divx1 || x > divx2 || y < divy1 || y > divy2){
	        	document.getElementById("levmenu").style.display = 'none';
	        	if(document.getElementById("lev2menu")){
	        		document.getElementById("lev2menu").style.display = 'none';
	        	}
	        	document.getElementById("mainField").style.zIndex = "1";
	        	$("#hmenu").removeClass("menuStartHover");
	        }
		}
	}
	
	//鼠标离开一级菜单区域时，判断位置，并作相应处理（如隐藏一级、二级菜单等）
	function LeaveLev1Menu(e){
		var x = e.clientX;
		var y = e.clientY;
		var pmenuDiv = document.getElementById("levmenu");
		if(pmenuDiv){
			var divx1 = pmenuDiv.offsetLeft;  
	        var divy1 = pmenuDiv.offsetTop;  
	        var divx2 = pmenuDiv.offsetLeft + pmenuDiv.offsetWidth;  
	        var divy2 = pmenuDiv.offsetTop + pmenuDiv.offsetHeight;
	        if( x < divx1 || x > divx2 || y < divy1 || y > divy2){
	        	pmenuDiv = document.getElementById("hmenu");
				if(pmenuDiv){
					var divx1 = pmenuDiv.offsetLeft;  
			        var divy1 = pmenuDiv.offsetTop;  
			        var divx2 = pmenuDiv.offsetLeft + pmenuDiv.offsetWidth;  
			        var divy2 = pmenuDiv.offsetTop + pmenuDiv.offsetHeight;
			        if( x < divx1 || x > divx2 || y < divy1 || y > divy2){
			        	pmenuDiv = document.getElementById("lev2menu");
						if(pmenuDiv){
							var divx1 = pmenuDiv.offsetLeft;  
					        var divy1 = pmenuDiv.offsetTop;  
					        var divx2 = pmenuDiv.offsetLeft + pmenuDiv.offsetWidth;  
					        var divy2 = pmenuDiv.offsetTop + pmenuDiv.offsetHeight;
					        if( x < divx1 || x > divx2 || y < divy1 || y > divy2){
					        	document.getElementById("levmenu").style.display = 'none';
					        	document.getElementById("lev2menu").style.display = 'none';
					        	document.getElementById("mainField").style.zIndex = "1";
					        	$("#hmenu").removeClass("menuStartHover");
					        }
						}
			        }
				}
	        }
		}
	}
	
	//鼠标离开二级菜单区域时，判断位置，并作相应处理（如隐藏一级、二级菜单等）
	function LeaveLev2Menu(e){
		var x = e.clientX;
		var y = e.clientY;
		var pmenuDiv = document.getElementById("lev2menu");
		if(pmenuDiv){
			var divx1 = pmenuDiv.offsetLeft;  
	        var divy1 = pmenuDiv.offsetTop;  
	        var divx2 = pmenuDiv.offsetLeft + pmenuDiv.offsetWidth;  
	        var divy2 = pmenuDiv.offsetTop + pmenuDiv.offsetHeight;
	        if( x < divx1 || x > divx2 || y < divy1 || y > divy2){
	        	pmenuDiv = document.getElementById("levmenu");
				if(pmenuDiv){
					var divx1 = pmenuDiv.offsetLeft;  
			        var divy1 = pmenuDiv.offsetTop;  
			        var divx2 = pmenuDiv.offsetLeft + pmenuDiv.offsetWidth;  
			        var divy2 = pmenuDiv.offsetTop + pmenuDiv.offsetHeight;
			        if( x < divx1 || x > divx2 || y < divy1 || y > divy2){
			        	document.getElementById("levmenu").style.display = 'none';
			        	document.getElementById("lev2menu").style.display = 'none';
			        	document.getElementById("mainField").style.zIndex = "1";
			        	$("#hmenu").removeClass("menuStartHover");
			        }
				}
	        }
		}
	}
	
	//获取菜单数据
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
	
	//二级菜单数组，和一级菜单相关联，鼠标移到一级菜单时，数组里取出相应二级菜单展现
	var lev2menu = [ ];
	//根据取出的菜单数据拼出html
	function setMenuData(data){
		if(data){
			var levmenu = "";
			for(var i = 0; i < data.length; i++){
				var menuName = data[i].menuName;
				var linkAction = data[i].linkAction ? data[i].linkAction: "";
				var menuPrimeKey = data[i].menuPrimeKey;
				var menuSeq = data[i].menuSeq;
				if(linkAction == ""){
					levmenu += "<dt onmouseover='showLev2Menu(" + i + ")'><a url='" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a></dt>";
				}else{
					levmenu += "<dt onmouseover='showLev2Menu(" + i + ")'><a href='#' url='" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a></dt>";
				}
				var secondChilds = "";
				if(data[i].childrenMenuTreeNodeList){
					var Lev2childrens = data[i].childrenMenuTreeNodeList;
					for(var j = 0; j < Lev2childrens.length; j++){
						var menuName = Lev2childrens[j].menuName;
						var linkAction = Lev2childrens[j].linkAction ? Lev2childrens[j].linkAction : "";
						var menuPrimeKey = Lev2childrens[j].menuPrimeKey;
						var menuSeq = Lev2childrens[j].menuSeq;
						var otherChildrens = getThirdOrMoreChild(Lev2childrens[j]);
						if(otherChildrens && (otherChildrens != "")){
							otherChildrens = "<ul class='lev3'>" + otherChildrens + "</ul>";
							secondChilds += "<dt><ul><a class='lev2' url='" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a></ul>" + otherChildrens + "</dt>";
						}else{
							secondChilds += "<dt><ul class='lev2'><a href='#' url='" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a></ul></dt>";
						}
					}
				}
				lev2menu[i] = secondChilds;
			}
			insertMenuToHtml(levmenu);
		}
	}
	
	//根据二级菜单获取其所有子孙数据并且拼成相应html
	function getThirdOrMoreChild(lev2Childrens){
		var results = "";
		if(lev2Childrens.childrenMenuTreeNodeList){
			var childrens = lev2Childrens.childrenMenuTreeNodeList;
			for(var i = 0; i < childrens.length; i++){
				var menuName = childrens[i].menuName;
				var linkAction = childrens[i].linkAction ? childrens[i].linkAction : "";
				var menuPrimeKey = childrens[i].menuPrimeKey;
				var menuSeq = childrens[i].menuSeq;
				results += "<a class='Nav' href='#' url='" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a>";
				var ret = getThirdOrMoreChild(childrens[i]);
				if(ret){
					results += ret;
				}
			}
			if(results){
				return results;
			}
		}
	}
	
	//将拼成的一级菜单html插入到页面中
	function insertMenuToHtml(levmenu){
		levmenu += "<dl class='dlCls'><a href='#' url='/coframe/auth/skin2/allmenu.jsp'>全部菜单</a></dl>"
		$("#levmenu").html(levmenu);
	}
	
	//在iframe中展现相应页面
	function setIFrame(url){
		var relative = url.substr(0,1);
		if(relative == "/"){
			url = "<%=contextPath%>" + url;
		}
		iframe.src = url;
	}
	
</script>
</html>
