<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/queryAll.jsp"%>
<html lang="zh-CN">
<head>
<title>用户编辑页面</title>
</head>
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
				layer.alert('编辑成功！',{title:'提示'},function(index){
					parentEditCloseWin();
				});
			}else {
				//失败
				layer.alert('提交失败，请联系管理员！',{title:'提示'}); 
			}
		};
		
		$("#submit").click(function(){
			$("#sysUserForm").Validform();
			$("#sysUserForm").ajaxForm(options);
		});
   	});
</script>
</head>

<body>
	<form class="form-horizontal" method="post" id="sysUserForm" action="${ctx }/platform/permissions/sysuser/save.do">
		<input class="form-control" type="hidden"  name="status" value="${sysUser.status }">
		<input class="form-control" type="hidden" id="id"  name="id" value="${sysUser.id }">
		<input class="form-control" type="hidden"  name="isexpired" value="${sysUser.isexpired }">
		<input class="form-control" type="hidden"  name="islock" value="${sysUser.islock }">
		<input class="form-control" type="hidden"  name="fromtype" value="${sysUser.fromtype }">				
		<input class="form-control" type="hidden"  name="picture" value="${sysUser.picture }">
		<input class="form-control" type="hidden"  name="password" value="${sysUser.password }"> 
		<!-- 创建时间 -->
		<input class="form-control" type="hidden"  name="createdate" value="<fmt:formatDate value='${sysUser.createdate }' pattern='yyyy-MM-dd'/>" onclick="time();" readonly="readonly">
		<div class="col-xs-12">
		    <div class="form-group">
		        <label class="col-xs-3 control-label">帐号：</label>
		        <div class="col-xs-8">
					<input class="form-control" readonly="readonly" maxlength="30" type="text"  id="account" name="account" value="${sysUser.account }" >
		        </div>
		        <div class="col-xs-1"></div>
		    </div>
		    <div class="form-group">
		        <label class="col-xs-3 control-label">姓名：</label>
		        <div class="col-xs-8">
					<input class="form-control" maxlength="10" type="text"  id="fullname" name="fullname" value="${sysUser.fullname }" datatype="*" nullmsg="姓名必填！">
		        </div>
		        <div class="col-xs-1"></div>
		    </div>
		    <!-- 
		     <div class="form-group">
		        <label class="col-xs-3 control-label">密码：</label>
		        <div class="col-xs-8">
					<input class="form-control" maxlength="15" id="password" type="text" name="password" value="${sysUser.password }" datatype="*6-15" errormsg="密码范围在6~15位之间！">
		        </div>
		        <div class="col-xs-1"></div>
		    </div>
		    <div class="form-group">
		        <label class="col-xs-3 control-label">身份证号：</label>
		        <div class="col-xs-8">
					<input class="form-control" type="text"  id="idcard" name="idcard" value="${sysUser.idcard }" <%-- datatype="*15-19" errormsg="请填写正确身份证号！" ajaxurl="${ctx }/platform/permissions/sysuser/checkIdcardRepeat.do?id=${sysUser.id }" --%>>
		        </div>
		        <div class="col-xs-1"></div>
		    </div>
		     -->
		    <div class="form-group">
		        <label class="col-xs-3 control-label">手机：</label>
		        <div class="col-xs-8">
					<input class="form-control" type="text" id="mobile" ignore="ignore" name="mobile" value="${sysUser.mobile }" datatype="m ">
		        </div>
		        <div class="col-xs-1"></div>
		    </div>
		    <div class="form-group">
		        <label class="col-xs-3 control-label">电话：</label>
		        <div class="col-xs-8">
					<input class="form-control" type="text"  name="phone" value="${sysUser.phone }" >
		        </div>
		        <div class="col-xs-1"></div>
		    </div>
		    <div class="form-group">
		        <label class="col-xs-3 control-label">邮箱：</label>
		        <div class="col-xs-8">
					<input class="form-control" type="text" id="email" ignore="ignore" name="email"  value="${sysUser.email }" datatype="e" >
		        </div>
		        <div class="col-xs-1"></div>
		    </div>
		    <div class="form-group">
		        <label class="col-xs-3 control-label">性别：</label>
		        <div class="col-xs-8">
					<input type="radio" id="male" name="sex" value="1"<c:if test="${sysUser.sex=='1' }">checked="checked"</c:if>><label for="male">男</label>
					<input type="radio" id="famale" name="sex" value="0"<c:if test="${sysUser.sex=='0' }">checked="checked"</c:if>><label for="famale">女</label>
		        </div>
		        <div class="col-xs-1"></div>
		    </div>
		</div>
		<button class="btn btn-primary" id="submit" style="display: none;">保存</button>
	</form>
</body>
</html>
   
