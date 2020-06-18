<%@page pageEncoding="UTF-8"%>
<%@page import="com.primeton.cap.AppUserManager"%>
<%@page import ="com.eos.data.datacontext.UserObject" %>
<%@page import ="java.util.*" %>
<%@page import ="java.text.DateFormat" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="x-ua-compatible" content="IE=8;" />
<html>
<head>
<%@include file="/coframe/tools/skins/common.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线下积分管理系统2.0</title>
<style type="text/css">
.dtHighLight{
	background:#F0F8FF !important;
 }
</style>

</head>
<%
	UserObject currUser =(UserObject)session.getAttribute("userObject");
	//当前用户所在机构
	String orgName = currUser.getUserOrgName();
	if(orgName == null || orgName.equals("")){
		orgName = "官渡农村合作银行";
	} 
	String currUserid = currUser.getUserId(); 
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");   
    String currDate = sdf.format(cal.getTime());
    
    String[] weekArr = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"}; 
    String weekName = weekArr[cal.get(Calendar.DAY_OF_WEEK)-1];
    
    //添加cookie缓存:用户名
    String name=request.getParameter("userId");
    Cookie cookies[]=request.getCookies();
	if(name!=null){
		Cookie c = new Cookie("userId",name);
		c.setPath("/");
		c.setMaxAge(60*60*24*365);
		response.addCookie(c);
	}else{
		Cookie c = new Cookie("userId",currUser.getUserName());
		c.setPath("/");
		c.setMaxAge(60*60*24*365);
		response.addCookie(c);
	}
    
 %>
<body class="index">
<div id="layout1" class="nui-layout" style="width:100%;height:100%;">

    <div class="header" region="north" height="60%" showSplit="false" showHeader="false">
		<div id="header">
			<div class="head-in clearfix" style="overflow:hidden">
				<div class="fl clearfix">
					<div class="logo"></div>
					<h2 style="margin-top:15px;font-size:200%">线下积分管理系统2.0</h2>
				</div>
				<div class="options fr">
					<div class="time font-5"> 
						<span id="currentData"><%=orgName %>|<%=currDate%>|<%=weekName %></span>
					</div>
				</div>
			</div>
		</div>
    </div>

    <div title="south" region="south" showSplit="false" showHeader="false" height="30" >
        <div style="line-height:28px;text-align:center;cursor:default"> Copyright © 官渡农村合作银行 </div>
    </div>

    <div  region="center" style="border:0;height:100%;" bodyStyle="overflow:hidden;height:100%;">
        <!--Splitter-->
        <div class="nui-splitter" style="width:100%;height:100%;" borderStyle="border:0;">

			<div size="201" class="sidebar" maxSize="550" minSize="200" showCollapseButton="true" style="border:1;">

					<!--用户信息区-->
					<div class="user">
						<img class="head" src="<%=contextPath%>/coframe/tools/skins/skin1/images/user-head.gif" width="51" height="61" alt="" />
						<p class="tips" style="width:128px">
							<span class="font-1"><strong>您好，<%=AppUserManager.getCurrentUserId() %></strong></span>
							<span><a class="set" href="#" openJsp="<%=contextPath%>/coframe/rights/user/update_password.jsp">修改密码</a>
								  <a class="login-out" onclick="quit()"  target="_top">注销</a></span>
						</p>
					</div>
				
					<div class="nui-fit">
						<div id="leftTree" class="nui-outlooktree"  
							style="width:100%;height:100%;background-color:#ffffff" 
							borderStyle="border-bottom:1px solid #999999;border-top:0px;border-left:1px;border-right:0px"
							textField="text" idField="id" parentField="pid" resultAsTree="false" onnodeclick="onNodeSelect"
							showTreeIcon="true" dataField="outlooktrees"
							allowResizeColumn="true"
							allowResize="true">
						</div>
					</div>
					

            </div>

            <div showCollapseButton="false" style="border:0;">
                <!--Tabs-->
                <div id="mainTabs" class="nui-tabs" activeIndex="0" style="width:100%;height:100%;"      
                     plain="false"  >
                    <div title="首页" url="<%=contextPath %>/coframe/auth/welcome/welcome.jsp" ></div>
                </div>
            </div> 
                   
        </div>
    </div>
</div>
</body>


</html>
<script type="text/javascript">
	nui.parse();
	
	var tree = nui.get("leftTree");
	var contextPath = "<%=contextPath%>";
	
	var iframe = document.getElementById("mainframe");
	//iframe.src = "";
	
	getMenuData();
	
		
	
	//重新加载Main区域的URL
	function reloadMain(url){
		var mainIframe=document.getElementById('mainframe');
		if(mainIframe){
			mainIframe.src=url;
		}
	}
	
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
			var rtstring = "";
			//var secondMenulast = "</dd>";
			for(var i = 0; i < data.length; i++){
				var menuName1 = data[i].menuName;//第一级的标题名
				//var linkAction = data[i].linkAction ? data[i].linkAction: "";//第一级的链接
				var menuPrimeKey1 = data[i].menuPrimeKey;
				//var Lev2childrens = null;
				var menuName2=null;
				var linkAction2=null;
				var menuPrimeKey2 = null;

				var menuName3=null;
				var menuPrimeKey3 = null;
				var linkAction3=null;
				
				var menuName4=null;
				var menuPrimeKey4 = null;
				var linkAction4=null;
				
				var menuName5=null;
				var menuPrimeKey5 = null;
				var linkAction5=null;
				
				var menuName6=null;
				var menuPrimeKey6 = null;
				var linkAction6=null;

				rtstring += "{id: \""+menuPrimeKey1+"\", text: \""+menuName1+"\"},";
				
				if(data[i].childrenMenuTreeNodeList){
					var Lev2childrens = data[i].childrenMenuTreeNodeList;
					for(var j = 0; j < Lev2childrens.length; j++){
						menuName2 = Lev2childrens[j].menuName;
						menuPrimeKey2 = Lev2childrens[j].menuPrimeKey;
						linkAction2 = Lev2childrens[j].linkAction? Lev2childrens[j].linkAction: "";
						//alert(linkAction2);

						rtstring  +="{id: \""+menuPrimeKey2+"\", text: \""+menuName2+"\", pid: \""+menuPrimeKey1+"\", url: \""+linkAction2+"\" },";
						// alert(nui.encode(Lev2childrens[j].childrenMenuTreeNodeList));

						if(Lev2childrens[j].childrenMenuTreeNodeList){
							var Lev3childrens = Lev2childrens[j].childrenMenuTreeNodeList;
							for (var z = 0;z < Lev3childrens.length ;z++ ){	
								menuName3 = Lev3childrens[z].menuName;
								menuPrimeKey3 = Lev3childrens[z].menuPrimeKey;
								linkAction3 = Lev3childrens[z].linkAction? Lev3childrens[z].linkAction: "";
								//alert(linkAction2);

								rtstring  += "{id: \""+menuPrimeKey3+"\", text: \""+menuName3+"\", pid: \""+menuPrimeKey2+"\", url: \""+linkAction3+"\" },";
								if(Lev3childrens[z].childrenMenuTreeNodeList){
									var Lev4childrens = Lev3childrens[z].childrenMenuTreeNodeList;
									for (var v = 0;v < Lev4childrens.length ;v++ ){	
										menuName4 = Lev4childrens[v].menuName;
										menuPrimeKey4 = Lev4childrens[v].menuPrimeKey;
										linkAction4 = Lev4childrens[v].linkAction? Lev4childrens[v].linkAction: "";
										rtstring  += "{id: \""+menuPrimeKey4+"\", text: \""+menuName4+"\", pid: \""+menuPrimeKey3+"\", url: \""+linkAction4+"\" },";
									
										if(Lev4childrens[v].childrenMenuTreeNodeList){
											
											var lev5childrens = Lev4childrens[v].childrenMenuTreeNodeList;
											for (var m = 0;m<lev5childrens.length ;m++ ){	
												menuName5 = lev5childrens[m].menuName;
												menuPrimeKey5 = lev5childrens[m].menuPrimeKey;
												linkAction5 = lev5childrens[m].linkAction? lev5childrens[m].linkAction: "";
												//alert(linkAction2);
												rtstring  += "{id: \""+menuPrimeKey5+"\", text: \""+menuName5+"\", pid: \""+menuPrimeKey4+"\", url: \""+linkAction5+"\" },";
											
											
												if(lev5childrens[m].childrenMenuTreeNodeList){
													var lev6childrens = lev5childrens[m].childrenMenuTreeNodeList;
													for (var k = 0;k<lev6childrens.length ;k++ ){	
														menuName6 = lev6childrens[k].menuName;
														menuPrimeKey6 = lev6childrens[k].menuPrimeKey;
														linkAction6 = lev6childrens[k].linkAction? lev6childrens[k].linkAction: "";
														//alert(linkAction2);
														rtstring  += "{id: \""+menuPrimeKey6+"\", text: \""+menuName6+"\", pid: \""+menuPrimeKey5+"\", url: \""+linkAction6+"\" },";
													
													}
												}
											
											
											}
										}
										
									}
								}
								
							}
						}
					}
				}
			}
			rtstring=rtstring.substring(0,rtstring.length-1);
			var menuStr = "{outlookmenus:["+rtstring+"]}";
			var menuJson = nui.decode(menuStr);
			tree.loadList(menuJson.outlookmenus);
		
		}
	}
	
	
	 $("a").click(function(){
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
	  
	  
	  
	  function quit() {
	    
	   // if(confirm("确定退出登录?")){
	    //   window.location.href="<%=contextPath%>/coframe/auth/login/logout.jsp";
	        //nui.alert(href);
	   // }
	    // else{
	   //   return;
	  //  }
	    nui.confirm("   确定退出登录?  ", "提示",function (action) {
					if(action === "ok"){
					 window.location.href="<%=contextPath%>/coframe/auth/login/logout.jsp";
				}      
			 });
	  }
	
	function showTab(node) {
            var tabs = nui.get("mainTabs");
            var id = "tab$" + node.id;
            var tab = tabs.getTab(id);
            if (!tab) {
                tab = {};
                tab.name = id;
                tab.title = node.text;
                tab.showCloseButton = true;
                //这里拼接了url，实际项目，应该从后台直接获得完整的url地址
                tab.url = "<%=contextPath%>" + node.url ;
                tabs.addTab(tab);
            }
            tabs.activeTab(tab);
        }

        function onNodeSelect(e) {
            var node = e.node;
            var isLeaf = e.isLeaf;
            if (isLeaf) {
                showTab(node);
            }
        }

        function onClick(e) {
            var text = this.getText();
            alert(text);
        }
	
</script>