<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/index.jsp"%>
<html lang="zh-CN">
<head>
<title>首页</title>
</head>
<script type="text/javascript">
	$(function(){
		$("#rightLogout").click(function(){
			//成功
			// 如需增加回调函数，后面直接加 .on( function(e){} );
			// 点击“确定” e: true
			// 点击“取消” e: false
			Modal.confirm(
			{
				msg: "是否确定退出系统？"
			})
			.on( function (e) {
			  	if(e){
			  		//退出系统
			  		window.location.href="${ctx}/logout.do";
			  	}
			 });
		});
	});
	//模拟click事件--调用<a>管理主机的的方法
	function manageHost(){
		//jquery属性过滤选择器,触发被选元素的指定事件类型
		$(".navbar-static-side a[name='管理主机']").trigger("click");
		$(".J_menuTabs a[name='建立主机'] > i").trigger("click");
	}
	//登出
	function logout() {
		$("#rightLogout").trigger("click");
	} 
</script>
<body class="fixed-sidebar full-height-layout gray-bg  pace-done"
	style="overflow:hidden">
	<div class="pace  pace-inactive">
		<div data-progress="99" data-progress-text="100%" style="width: 100%;"
			class="pace-progress">
			<div class="pace-progress-inner"></div>
		</div>
		<div class="pace-activity"></div>
	</div>
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation" >
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>
			</div>
			<div style="position: relative; width: auto; height: 100%;"
				class="slimScrollDiv" >
				<div style="width: auto; height: 100%;overflow:auto;" class="sidebar-collapse" >
					<ul class="nav" id="side-menu" >
						<li class="nav-header">
							<div class="dropdown profile-element">
								<span><img alt="image" class="img-circle"
									src="${ctx}/main/default_files/profile_small.jpg"> </span> <a
									data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
									class="clear"> <span class="block m-t-xs"><strong
											class="font-bold">${currentUser.account }</strong> </span> <span
										class="text-muted text-xs block">${currentUser.fullname }<b class="caret"></b>
									</span> </span> </a>
								<ul class="dropdown-menu animated fadeInRight m-t-xs">
									<li><a data-index="0" class="J_menuItem"
										href="http://www.zi-han.net/theme/hplus/form_avatar.html">修改头像</a>
									</li>
									<li><a data-index="1" class="J_menuItem"
										href="http://www.zi-han.net/theme/hplus/profile.html">个人资料</a>
									</li>
									<!-- 
									<li><a data-index="2" class="J_menuItem"
										href="http://www.zi-han.net/theme/hplus/contacts.html">联系我们</a>
									</li>
									<li><a data-index="3" class="J_menuItem"
										href="http://www.zi-han.net/theme/hplus/mailbox.html">信箱</a>
									</li>
									<li class="divider"></li>
									 -->
									<li><a onclick="logout();" href="javascript:void(0);">安全退出</a>
									</li>
								</ul>
							</div>
							<div class="logo-element">H+</div>
						</li>
						<c:forEach items="${sysresList }" var="sysres" varStatus="s">
							<li>
								<a href="#"> 
									<i class="fa fa-cog"></i> 
									<span class="nav-label">${sysres.name }</span> 
									<span class="fa arrow"></span> 
								</a>
								
								<ul class="nav nav-second-level collapse">
									<c:forEach items="${sysres.secondList }" var="secondSysres" varStatus="ss">
										<li><a data-index="${ss.count }" name="${secondSysres.name}" id="${secondSysres.id}"  class="J_menuItem" href="${ctx }${secondSysres.defaulturl}">${secondSysres.name }</a></li>
									</c:forEach>
								</ul>
							</li>
						</c:forEach>
						
					</ul>
				</div>
				<div
					style="background: rgb(0, 0, 0) none repeat scroll 0% 0%; width: 4px; position: absolute; top: 0px; opacity: 0.4; display: block; border-radius: 7px; z-index: 99; right: 1px; height: 582.107px;"
					class="slimScrollBar"></div>
				<div
					style="width: 4px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: rgb(51, 51, 51) none repeat scroll 0% 0%; opacity: 0.9; z-index: 90; right: 1px;"
					class="slimScrollRail"></div>
			</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
					<div class="navbar-header">
						<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
							href="#"><i class="fa fa-bars"></i> </a>
						<!-- 
						<form role="search" class="navbar-form-custom" method="post"
							action="search_results.html">
							<div class="form-group">
								<input placeholder="请输入您需要查找的内容 …" class="form-control"
									name="top-search" id="top-search" type="text">
							</div>
						</form>
						-->
					</div>
					<ul class="nav navbar-top-links navbar-right">
						<li class="dropdown"><a class="dropdown-toggle count-info"
							data-toggle="dropdown" href="#"> <i class="fa fa-envelope"></i>
								<span class="label label-warning">16</span> </a>
							<ul class="dropdown-menu dropdown-messages">
								<!-- 未读消息开始 -->
								<li class="m-t-xs">
									<div class="dropdown-messages-box">
										<a href="http://www.zi-han.net/theme/hplus/profile.html"
											class="pull-left"> <img alt="image" class="img-circle"
											src="${ctx}/main/default_files/a7.jpg"> </a>
										<div class="media-body">
											<small class="pull-right">46小时前</small> <strong>小四</strong>
											这个在日本投降书上签字的军官，建国后一定是个不小的干部吧？ <br> <small
												class="text-muted">3天前 2014.11.8</small>
										</div>
									</div>
								</li>
								<li class="divider"></li>
								<!-- 未读消息结束 -->
								<li>
									<div class="text-center link-block">
										<a data-index="88" class="J_menuItem"
											href="http://www.zi-han.net/theme/hplus/mailbox.html"> <i
											class="fa fa-envelope"></i> <strong> 查看所有消息</strong> </a>
									</div>
								</li>
							</ul>
						</li>
						<li class="dropdown hidden-xs"><a
							class="right-sidebar-toggle" aria-expanded="false"> <i
								class="fa fa-tasks"></i> 主题 </a>
						</li>
					</ul>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
					<div class="page-tabs-content">
						<!-- 选项卡菜单第一个 -->
						<a href="javascript:;" class="active J_menuTab"
							data-id="index_v1.html">首页</a>
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>

					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a>
						</li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a>
						</li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a>
						</li>
					</ul>
				</div>
				<a href="#" id="rightLogout" class="roll-nav roll-right J_tabExit">
					<i class="fa fa fa-sign-out"></i>退出
				</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" src="${ctx}/hello.jsp" data-id="index_v1.html"
					seamless="" frameborder="0" height="100%" width="100%"></iframe>
			</div>
			<!-- 底部开始 
			<div class="footer">
				<div class="pull-right">
					© 中国有限公司 
					<a href="http://www.zi-han.net/" target="_blank">
					</a>
				</div>
			</div>
			-->
			<!-- 底部结束 -->
		</div>
		<!--右侧部分结束-->
		<!--右侧边栏开始-->
		<div id="right-sidebar">
			<div style="position: relative; width: auto; height: 100%;"
				class="slimScrollDiv">
				<div style="width: auto; height: 100%;" class="sidebar-container">


					<div class="tab-content">
						<div id="tab-1" class="tab-pane active">
							<div class="sidebar-title">
								<h3>
									<i class="fa fa-comments-o"></i> 主题设置
								</h3>
								<small><i class="fa fa-tim"></i>
									你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
							</div>
							<div class="skin-setttings">
								<div class="title">主题设置</div>
								<div class="setings-item">
									<span>收起左侧菜单</span>
									<div class="switch">
										<div class="onoffswitch">
											<input name="collapsemenu" class="onoffswitch-checkbox"
												id="collapsemenu" type="checkbox"> <label
												class="onoffswitch-label" for="collapsemenu"> <span
												class="onoffswitch-inner"></span> <span
												class="onoffswitch-switch"></span> </label>
										</div>
									</div>
								</div>
								<div class="setings-item">
									<span>固定顶部</span>

									<div class="switch">
										<div class="onoffswitch">
											<input name="fixednavbar" class="onoffswitch-checkbox"
												id="fixednavbar" type="checkbox"> <label
												class="onoffswitch-label" for="fixednavbar"> <span
												class="onoffswitch-inner"></span> <span
												class="onoffswitch-switch"></span> </label>
										</div>
									</div>
								</div>
								<div class="setings-item">
									<span> 固定宽度 </span>

									<div class="switch">
										<div class="onoffswitch">
											<input name="boxedlayout" class="onoffswitch-checkbox"
												id="boxedlayout" type="checkbox"> <label
												class="onoffswitch-label" for="boxedlayout"> <span
												class="onoffswitch-inner"></span> <span
												class="onoffswitch-switch"></span> </label>
										</div>
									</div>
								</div>
								<div class="title">皮肤选择</div>
								<div class="setings-item default-skin nb">
									<span class="skin-name "> <a href="#" class="s-skin-0">
											默认皮肤 </a> </span>
								</div>
								<div class="setings-item blue-skin nb">
									<span class="skin-name "> <a href="#" class="s-skin-1">
											蓝色主题 </a> </span>
								</div>
								<div class="setings-item yellow-skin nb">
									<span class="skin-name "> <a href="#" class="s-skin-3">
											黄色/紫色主题 </a> </span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div
					style="background: rgb(0, 0, 0) none repeat scroll 0% 0%; width: 4px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 570px;"
					class="slimScrollBar"></div>
				<div
					style="width: 4px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: rgb(51, 51, 51) none repeat scroll 0% 0%; opacity: 0.4; z-index: 90; right: 1px;"
					class="slimScrollRail"></div>
			</div>
		</div>
		<!--右侧边栏结束-->
	</div>
</body>
</html>
