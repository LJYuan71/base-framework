<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>分类信息/部门关联关系编辑页面</title>
<script>
   	$(function(){
   		$("#classifyOrgForm").Validform();
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
				  		window.location.href="${ctx}/secrecy/basic/classifyorg/edit.do?id=${classifyOrg.id }";
				  	}else{
				  		//不继续编辑，返回列表页
				  		window.location.href="${ctx}/secrecy/basic/classifyorg/queryAll.do";
				  	}
				 });
			}else {
				//失败
				Modal.alert({msg: '提交失败，请联系管理员！'});
			}
		};
		
		$("#submit").click(function(){
			$("#classifyOrgForm").ajaxForm(options);
		});
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否返回列表页？"}).on( function (e) {
				  	if(e){
				  		//返回
				  		window.location.href="${ctx}/secrecy/basic/classifyorg/queryAll.do";
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
						<form class="form-horizontal" method="post" id="classifyOrgForm" action="${ctx }/secrecy/basic/classifyorg/save.do">
							<div class="form-group">
								<label class="col-sm-2 control-label">id</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="id" value="${classifyOrg.id }">
								</div>
								<label class="col-sm-2 control-label">分类id</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="classifyid" value="${classifyOrg.classifyid }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">部门id</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="orgid" value="${classifyOrg.orgid }">
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
