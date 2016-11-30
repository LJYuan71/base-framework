<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>sys_user_host编辑页面</title>
<script>
   	$(function(){
   		$("#sysUserHostForm").Validform();
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
				Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
					window.location.href="${ctx}/platform/permissions/sysuserhost/queryAll.do";
				});
			}else {
				//失败
				Modal.alert({msg: '提交失败，请联系管理员！'});
			}
		};
		
		$("#submit").click(function(){
			$("#sysUserHostForm").ajaxForm(options);
		});
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否返回列表页？"}).on( function (e) {
				  	if(e){
				  		//返回
				  		window.location.href="${ctx}/platform/permissions/sysuserhost/queryAll.do";
				  	}
			});
		});
   	})
</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-10">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="display: block;">
						<form class="form-horizontal" method="post" id="sysUserHostForm" action="${ctx }/platform/permissions/sysuserhost/save.do">
							<input class="form-control" type="text" style="display: none;" name="id" value="${sysUserHost.id }">
							<input class="form-control" type="text" style="display: none;" name="createtime" value="<fmt:formatDate value='${sysUserHost.createtime }' pattern='yyyy-MM-dd'/>" onclick="time();" readonly="readonly">
							<div class="form-group">
								<!-- 
								<label class="col-sm-2 control-label">id</label>
								<div class="col-sm-4">
								</div>
								 -->
								<label class="col-sm-2 control-label">用户主机名称:</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="name" value="${sysUserHost.name }">
								</div>
								<label class="col-sm-2 control-label">规模类型:</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="type" value="${sysUserHost.type }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">用户id:</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="userid" value="${sysUserHost.userid }" datatype="n" nullmsg="用户id必填！">
								</div>
								<label class="col-sm-2 control-label">主机id:</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="hostinfoid" value="${sysUserHost.hostinfoid }" datatype="n"  nullmsg="主机id必填！">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">分配的ip:</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="userhostip" value="${sysUserHost.userhostip }"  datatype="*"  nullmsg="分配ip必填！">
								</div>
								<label class="col-sm-2 control-label">备注:</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="remark" value="${sysUserHost.remark }">
								</div>
							</div>
							<div class="form-group">
								
								<!--  
								<label class="col-sm-2 control-label">创建时间</label>
								<div class="col-sm-4">
								</div>
								-->
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
