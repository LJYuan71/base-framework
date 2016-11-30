<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>用户表明细页面</title>
<script>
   	$(function(){
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否返回列表页？"}).on( function (e) {
				  	if(e){
				  		//返回
				  		window.location.href="${ctx}/platform/permissions/sysuser/auditList.do";
				  	}
			});
		});
   	});
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
								<label class="col-sm-2 control-label">ID:</label>
								<div class="col-sm-4">
									${sysUser.id }
								</div>
								<label class="col-sm-2 control-label">姓名:</label>
								<div class="col-sm-4">
									${sysUser.fullname }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">帐号:</label>
								<div class="col-sm-4">
									${sysUser.account }
								</div>
								<label class="col-sm-2 control-label">密码:</label>
								<div class="col-sm-4">
									${sysUser.password }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">是否过期:</label>
								<div class="col-sm-4">
									${sysUser.isexpired }
								</div>
								<label class="col-sm-2 control-label">是否锁定:</label>
								<div class="col-sm-4">
									${sysUser.islock }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">创建时间:</label>
								<div class="col-sm-4">
									<fmt:formatDate value='${sysUser.createdate }' pattern='yyyy-MM-dd'/>
								</div>
								<label class="col-sm-2 control-label">审核状态:</label>
								<div class="col-sm-4">
									<c:if test="${sysUser.status==0}">未审核</c:if>
									<c:if test="${sysUser.status==1}">通过</c:if>
									<c:if test="${sysUser.status==-1}">未通过</c:if>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">邮箱:</label>
								<div class="col-sm-4">
									${sysUser.email }
								</div>
								<label class="col-sm-2 control-label">手机:</label>
								<div class="col-sm-4">
									${sysUser.mobile }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">电话:</label>
								<div class="col-sm-4">
									${sysUser.phone }
								</div>
								<label class="col-sm-2 control-label">性别:</label>
								<div class="col-sm-4">
									<c:if test="${sysUser.sex==1}">男</c:if>
									<c:if test="${sysUser.sex==0}">女</c:if>
									<c:if test="${sysUser.sex!=0&&sysUser.sex!=1}">未知</c:if>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"> 照片:</label>
								<div class="col-sm-4">
									${sysUser.picture }
								</div>
								<label class="col-sm-2 control-label">数据来源:</label>
								<div class="col-sm-4">
									<c:if test="${sysUser.fromtype==0}">系统添加</c:if>
									<c:if test="${sysUser.fromtype==1}">系统同步</c:if>
									<c:if test="${sysUser.fromtype!=0&&sysUser.fromtype!=1}">未知</c:if>
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
