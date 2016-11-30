<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>建立主机下一步</title>
<script>
   	$(function(){
		$("#finish").click(function(){
			//parent.location.reload();
			$("#again").trigger("click");
			parent.manageHost();
		});
		$("#again").click(function(){
			window.location.href="${ctx}/platform/permissions/sysuserhost/build.do";
		})
   	})
   	//调用父窗体的模拟鼠标click事件
   	function manageHost(){
   		$("#again").trigger("click");
   		parent.manageHost();
   	}
</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-12" style="height:100px;"></div>
		<div class="col-sm-10">
			<div style="text-align: center;">
				<h3>正在生成服务器,请稍后到<a href="javascript:void(0);" onclick="manageHost();">"管理主机"</a>中查看.</h3>
			</div>
		</div>
		<div class="col-sm-12" style="height: 30px"></div>
		<div style="position: absolute;top:200px;right: 35%;">
			<div class="col-sm-12">
				<button id="finish" type="button" style="padding: 8px;margin: 10px;" class="btn btn-success pull-right">完成</button>
				<button id="again" type="button" style="padding: 8px;margin: 10px;" class="btn ">再次添加</button>
			</div>
		</div>
	</div>
</body>
</html>
