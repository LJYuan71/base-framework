<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>

<script>
   	$(function(){
   		$("#sysRoleForm").Validform();
   		var options = {
		   beforeSubmit: showRequest,  //提交前的回调函数
		   success: showResponse,      //提交后的回调函数
		   timeout: 3000               //限制请求的时间，当请求大于3秒后，跳出请求
		};
		function showRequest(formData, jqForm, options){
		   	return true;  //只要不返回false，表单都会提交,在这里可以对表单元素进行验证
		};
		function showResponse(responseText, statusText){
			var json = eval("(" + responseText + ")");
			if (json.result == 1) {
			 	Modal.timealert({msg: '编辑成功！',time: '2000'}).on( function () {
					//关闭编辑页面
				  	window.location.href="${ctx}/platform/permissions/sysrole/queryAll.do";
				});
			}else {
				//失败
				Modal.alert({msg: '提交失败，请联系管理员！'});
			}
		};
		
		$("#submit").click(function(){
			$("#sysRoleForm").ajaxForm(options);
		});
		$("#back").click(function(){
			window.location.href="${ctx}/platform/permissions/sysrole/queryAll.do";
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
						<form class="form-horizontal" method="post" id="sysRoleForm" action="${ctx }/platform/permissions/sysrole/save.do">
							<input class="form-control" type="hidden"  name="id" value="${sysRole.id }">
							<input class="form-control" type="hidden"  name="systemid" value="${sysRole.systemid }">
							<input class="form-control" type="hidden"  name="allowdel" value="${sysRole.allowdel }">
							<input class="form-control" type="hidden"  name="allowedit" value="${sysRole.allowedit }">
          			  		<!-- 0禁止 1启用 -->
          					<input class="form-control" type="hidden"  name="enabled" value="${sysRole.enabled }">									
          					<input class="form-control" type="hidden"  name="createdate" value="<fmt:formatDate value='${sysRole.createdate }' pattern='yyyy-MM-dd'/>" onclick="time();" readonly="readonly">
			
							<div class="form-group"></div>
							<div class="form-group">
								<label class="col-sm-3 control-label">角色名:</label>
								<div class="col-sm-7">
									<input class="form-control" type="text" id="rolename" name="rolename" value="${sysRole.rolename }" datatype="*" nullmsg="角色名必填！">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">角色别名:</label>
								<div class="col-sm-7">
									<input class="form-control" type="text"  name="alias" value="${sysRole.alias }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">备注:</label>
								<div class="col-sm-7">
									<input class="form-control" type="text"  name="remark" value="${sysRole.remark }">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-12 col-sm-offset-4">
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
