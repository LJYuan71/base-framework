<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>sys_user_host明细页面</title>
<script>
   	$(function(){
		$("#back").click(function(){
			window.location.href="${ctx}/platform/permissions/sysuserhost/queryAll.do";
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
								<label class="col-sm-2 control-label">id:</label>
								<div class="col-sm-4 form-control-static">
									${sysUserHost.id }
								</div>
								<label class="col-sm-2 control-label">用户主机名称:</label>
								<div class="col-sm-4 form-control-static">
									${sysUserHost.name }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">用户id:</label>
								<div class="col-sm-4 form-control-static">
									${sysUserHost.userid }
								</div>
								<label class="col-sm-2 control-label">主机id:</label>
								<div class="col-sm-4 form-control-static">
									${sysUserHost.hostinfoid }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">分配的ip:</label>
								<div class="col-sm-4 form-control-static">
									${sysUserHost.userhostip }
								</div>
								<label class="col-sm-2 control-label">备注:</label>
								<div class="col-sm-4 form-control-static">
									${sysUserHost.remark }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">规模类型:</label>
								<div class="col-sm-4 form-control-static">
									${sysUserHost.type }
								</div>
								<label class="col-sm-2 control-label">状态:</label>
								<div class="col-sm-4 form-control-static">
									<c:if test="${sysUserHost.status==0 }">关机</c:if>
									<c:if test="${sysUserHost.status==1 }">启动</c:if>
									<c:if test="${sysUserHost.status==2 }">重启</c:if>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">创建时间:</label>
								<div class="col-sm-4 form-control-static">
									<fmt:formatDate value='${sysUserHost.createtime }' pattern='yyyy-MM-dd'/>
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
