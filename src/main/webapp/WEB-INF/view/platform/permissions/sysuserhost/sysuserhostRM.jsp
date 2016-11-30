<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>建立主机下一步</title>
<script>
   	$(function(){
		$("#finish").click(function(){
			parent.location.reload();
			//window.location.href="${ctx}/platform/permissions/sysuserhost/queryByUserId.do";
		});
		$("#again").click(function(){
			window.location.href="${ctx}/platform/permissions/sysuserhost/build.do";
		})
		
		
   	})
</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div style="position: absolute;top:100px;left: 25%;">
				<div class="col-sm-12">
					<button id="startup" type="button" style="padding: 8px;margin: 10px;" class="btn btn-success pull-right">开机</button>
					<button id="shutdown" type="button" style="padding: 8px;margin: 10px;" class="btn ">关机</button>
					<button id="restart" type="button" style="padding: 8px;margin: 10px;" class="btn ">重启</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
