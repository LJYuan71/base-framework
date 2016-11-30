<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>SYS_LOG明细页面</title>
<script>
   	$(function(){
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
						<form class="form-horizontal" >
							<div class="form-group">
								<label class="col-sm-2 control-label">ID</label>
								<div class="col-sm-4 form-control-static">
									${sysLog.id }
								</div>
								<label class="col-sm-2 control-label">用户id</label>
								<div class="col-sm-4 form-control-static">
									${sysLog.userid }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">用户名</label>
								<div class="col-sm-4 form-control-static">
									${sysLog.username }
								</div>
								<label class="col-sm-2 control-label">创建时间</label>
								<div class="col-sm-4 form-control-static">
									<fmt:formatDate value='${sysLog.createdate }' pattern='yyyy-MM-dd'/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">模块名</label>
								<div class="col-sm-4 form-control-static">
									${sysLog.modelname }
								</div>
								<label class="col-sm-2 control-label">描述</label>
								<div class="col-sm-4 form-control-static">
									${sysLog.description }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">状态  1=系统日志  2=业务日志</label>
								<div class="col-sm-4 form-control-static">
									${sysLog.state }
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
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
