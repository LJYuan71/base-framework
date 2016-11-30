<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>用户表编辑页面</title>
<script>
   	$(function(){
   		$("#sysUserForm").Validform();
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
				//成功
				// 如需增加回调函数，后面直接加 .on( function(e){} );
				// 点击“确定” e: true
				// 点击“取消” e: false
				/*Modal.confirm({msg: json.message+"，是否继续编辑？"}).on( function (e) {
				  	if(e){
				  		//继续编辑
				  		window.location.href="${ctx}/platform/permissions/sysuser/edit.do?id=${sysUser.id }";
				  	}else{
				  		//不继续编辑，返回列表页
				  		window.location.href="${ctx}/platform/permissions/sysuser/queryAll.do";
				  	}
				 });*/
				Modal.timealert({msg: '编辑成功！',time: '2000'}).on( function () {
					//关闭编辑页面
				  	window.location.href="${ctx}/platform/permissions/sysuser/queryAll.do";
				});
			}else {
				//失败
				Modal.alert({msg: '提交失败，请联系管理员！'});
			}
		};
		
		$("#submit").click(function(){
			$("#sysUserForm").ajaxForm(options);
		});
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否返回列表页？"}).on( function (e) {
				  	if(e){
				  		//返回
				  		$('#editPage').modal('hide');
				  		window.location.href="${ctx}/platform/permissions/sysuser/queryAll.do";
				  	}
			});
		});
   	});
</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-7">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="display: block;">
						<form class="form-horizontal" method="post" id="sysUserForm" action="${ctx }/platform/permissions/sysuser/save.do">
							
							<input class="form-control" type="hidden"  name="status" value="${sysUser.status }">
							<input class="form-control" type="hidden" id="id"  name="id" value="${sysUser.id }">
							<input class="form-control" type="hidden"  name="isexpired" value="${sysUser.isexpired }">
							<input class="form-control" type="hidden"  name="islock" value="${sysUser.islock }">
							<input class="form-control" type="hidden"  name="fromtype" value="${sysUser.fromtype }">				
							<input class="form-control" type="hidden"  name="picture" value="${sysUser.picture }">
							<!-- 创建时间 -->
							<input class="form-control" type="hidden"  name="createdate" value="<fmt:formatDate value='${sysUser.createdate }' pattern='yyyy-MM-dd'/>" onclick="time();" readonly="readonly">
							<div class="form-group"></div>
							<div class="form-group">
								<label class="col-sm-6 control-label"> 姓名:</label>
								<div class="col-sm-6">
									<input class="form-control" type="text"  id="fullname" name="fullname" value="${sysUser.fullname }" datatype="*" nullmsg="姓名必填！">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-6 control-label">帐号:</label>
								<div class="col-sm-6">
									<input class="form-control" type="text"  id="account" name="account" value="${sysUser.account }" 
									ajaxurl="${ctx}/platform/permissions/sysuser/checkAccountRepeat.do?id=${sysUser.id }" datatype="*" nullmsg="帐号必填！">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-6 control-label">密码:</label>
								<div class="col-sm-6">
									<input class="form-control" type="text" id="password" name="password" datatype="*6-16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间！" value="${sysUser.password }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-6 control-label">确认密码:</label>
								<div class="col-sm-6">
									<input class="form-control" type="text" id="passwordconf" name="passwordconf" datatype="*" recheck="password" nullmsg="请再输入一次密码！" errormsg="您两次输入的账号密码不一致！">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-6 control-label">单位名称:</label>
								<div class="col-sm-6">
									<input class="form-control" type="text"  name="company"  value="${sysUser.company }" datatype="*" nullmsg="单位名称必填！">
								</div>
							</div>
							<!-- 
							<div class="form-group">
								<label class="col-sm-6 control-label">身份证号:</label>
								<div class="col-sm-6">
									<input class="form-control" type="text"  id="idcard" name="idcard" value="${sysUser.idcard }" datatype="*15-19" errormsg="请填写正确身份证号！">
								</div>
							</div>
							 -->
							<div class="form-group">
								<label class="col-sm-6 control-label">邮箱:</label>
								<div class="col-sm-6">
									<input class="form-control" type="text"  name="email"  value="${sysUser.email }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-6 control-label">手机:</label>
								<div class="col-sm-6">
									<input class="form-control" type="text"  name="mobile" value="${sysUser.mobile }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-6 control-label">电话:</label>
								<div class="col-sm-6">
									<input class="form-control" type="text"  name="phone" value="${sysUser.phone }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-6 control-label">性别:</label>
								<div class="col-sm-6">
									
 									<input type="radio" name="sex" value="1"<c:if test="${sysUser.sex=='1' }">checked="checked"</c:if>>男
   									<input type="radio" name="sex" value="0"<c:if test="${sysUser.sex=='0' }">checked="checked"</c:if>>女
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-12 col-sm-offset-7">
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
