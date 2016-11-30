<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>SYS_LOG编辑页面</title>
<script>
   	$(function(){
   		$("#sysLogForm").Validform();
   		var options = {
		   beforeSubmit: showRequest,  //提交前的回调函数
		   success: showResponse,      //提交后的回调函数
		   timeout: 3000               //限制请求的时间，当请求大于3秒后，跳出请求
		}
		function showRequest(formData, jqForm, options){
		   	return true;  //只要不返回false，表单都会提交,在这里可以对表单元素进行验证
		};
		function showResponse(responseText, statusText){
			var json = eval("(" + responseText + ")");
			if (json.result == 1) {
				//成功
				// 如需增加回调函数，后面直接加 .on( function(e){} );
				// 点击“确定” e: true
				// 点击“取消” e: false
				Modal.confirm({msg: json.message+"，是否继续编辑？"}).on( function (e) {
				  	if(e){
				  		//继续编辑
				  		window.location.href="${ctx}/platform/log/syslog/edit.do?id=${sysLog.id }";
				  	}else{
				  		//不继续编辑，返回列表页
				  		window.location.href="${ctx}/platform/log/syslog/queryAll.do";
				  	}
				 });
			}else {
				//失败
				Modal.alert({msg: '提交失败，请联系管理员！'});
			}
		};
		
		$("#submit").click(function(){
			$("#sysLogForm").ajaxForm(options);
		});
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否返回列表页？"}).on( function (e) {
				  	if(e){
				  		//返回
				  		window.location.href="${ctx}/platform/log/syslog/queryAll.do";
				  	}
			});
		});
   	})
</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="display: block;">
						<form class="form-horizontal" method="post" id="sysLogForm" action="${ctx }/platform/log/syslog/save.do">
							<div class="form-group">
								<label class="col-sm-2 control-label">ID</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="id" value="${sysLog.id }">
								</div>
								<label class="col-sm-2 control-label">用户id</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="userid" value="${sysLog.userid }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">用户名</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="username" value="${sysLog.username }">
								</div>
								<label class="col-sm-2 control-label">创建时间</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="createtime" value="<fmt:formatDate value='${sysLog.createdate }' pattern='yyyy-MM-dd'/>" onclick="time();" readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">模块名</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="modelname" value="${sysLog.modelname }">
								</div>
								<label class="col-sm-2 control-label">描述</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="description" value="${sysLog.description }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">状态  1=系统日志  2=业务日志</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="state" value="${sysLog.state }">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" id="submit">保存内容</button>
									<input type="button" value="取消" id="back"  class="btn btn-white"/>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
