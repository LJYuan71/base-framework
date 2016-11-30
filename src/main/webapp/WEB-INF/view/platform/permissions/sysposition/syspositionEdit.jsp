<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>岗位表编辑页面</title>
<script>
   	$(function(){
   		$("#sysPositionForm").Validform();
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
				Modal.timealert({msg: '编辑成功！',time: '2000'}).on( function () {
					//关闭编辑页面
				  	window.location.href="${ctx}/platform/permissions/sysposition/queryAll.do";
				});
			}else {
				//失败
				Modal.alert({msg: '提交失败，请联系管理员！'});
			}
		};
		
		$("#submit").click(function(){
			$("#sysPositionForm").ajaxForm(options);
		});
		$("#back").click(function(){
			window.location.href="${ctx}/platform/permissions/sysposition/queryAll.do";
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
						<form class="form-horizontal" method="post" id="sysPositionForm" action="${ctx }/platform/permissions/sysposition/save.do">
							<input class="form-control" type="hidden"  name="id" value="${sysPosition.id }">
							<div class="form-group"></div>
							<div class="form-group">
								<label class="col-sm-4 control-label">岗位名称:</label>
								<div class="col-sm-7">
									<input class="form-control" type="text" id="posname" name="posname" value="${sysPosition.posname }" datatype="*" nullmsg="岗位名称必填！">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">岗位描述:</label>
								<div class="col-sm-7">
									<input class="form-control" type="text"  name="posdesc" value="${sysPosition.posdesc }">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-8 col-sm-offset-4">
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
