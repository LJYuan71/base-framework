<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>登录页面</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- 引入样式文件 -->
<link rel="stylesheet" href="common/login/bootstrap.min.css" />
<link rel="stylesheet" href="common/login/css/camera.css" />
<link rel="stylesheet" href="common/login/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="common/login/matrix-login.css" />
<link href="common/login/font-awesome.css" rel="stylesheet" />
<script type="text/javascript" src="common/login/js/jquery-1.11.3.min.js"></script>
<script src="common/login/js/bootstrap.min.js"></script>
<script type="text/javascript" src="common/login/js/jquery-1.7.2.js"></script>
	<script src="common/login/js/jquery.easing.1.3.js"></script>
	<script src="common/login/js/jquery.mobile.customized.min.js"></script>
	<script src="common/login/js/camera.min.js"></script>
	<script src="common/login/js/templatemo_script.js"></script>
	<script type="text/javascript" src="common/login/js/jquery.tips.js"></script>
	<script type="text/javascript" src="common/login/js/jquery.cookie.js"></script>

</head>
<body>

	<div
		style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
		<div id="loginbox">
			<form action="" method="post" name="loginForm"
				id="loginForm">
				<div class="control-group normal_text">
					<h3>
						<img src="common/login/logo.png" alt="Logo" />
					</h3>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i><img height="37" src="common/login/user.png" /></i>
							</span><input type="text" name="username" id="username" value="" placeholder="请输入用户名" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
							<i><img height="37" src="common/login/suo.png" /></i>
							</span><input type="password" name="password" id="password" placeholder="请输入密码" value="" />
						</div>
					</div>
				</div>
				<!-- 
				<div style="float:right;padding-right:10%;">
					
					<div style="float: left;margin-top:3px;margin-right:2px;">
						<font color="white">记住密码</font>
					</div>
					
					<div style="float: left;">
						<input name="form-field-checkbox" id="saveid" type="checkbox"
							onclick="savePaw();" style="padding-top:0px;" />
					</div>
				</div>
				 -->
				<div class="form-actions">
					<div style="width:86%;padding-left:5%;">

						<div style="float: left;">
							<i><img src="common/login/yan.png" /></i>
						</div>
						<div style="float: left;" class="codediv">
							<input type="text" name="verifyCode" id="verifyCode" class="login_code"
								style="height:16px; padding-top:0px;" />
						</div>
						<div style="float: left;">
							<i><img style="height:22px;" id="codeImg" alt="点击更换"
								title="点击更换" src="" /></i>
						</div>
					</div>
					<div>
						<span class="pull-right" style="padding-right:3%;"><a
							href="javascript:quxiao();" class="btn btn-success">取消</a></span> 
						<span class="pull-right"><a onclick="severCheck();"
							class="flip-link btn btn-info" id="to-recover">登录</a></span>
					</div>
				</div>

			</form>


			<div class="controls">
				<div class="main_input_box">
					<font color="white"><span id="nameerr">中国有限公司</span></font>
				</div>
			</div>
		</div>
	</div>
	<div id="templatemo_banner_slide" class="container_wapper">
		<div class="camera_wrap camera_emboss" id="camera_slide">
			<div data-src="common/login/images/banner_slide_01.jpg"></div>
			<div data-src="common/login/images/banner_slide_02.jpg"></div>
			<div data-src="common/login/images/banner_slide_03.jpg"></div>
		</div>
		<!-- #camera_wrap_3 -->
	</div>

	<script type="text/javascript">
	
		//服务器校验
		function severCheck(){
			if(check()){
				
				var username = $("#username").val();
				var password = $("#password").val();
				var verifyCode = $("#verifyCode").val();
				$.ajax({
					type: "POST",
					url: '<%=request.getContextPath()%>/login.do',
			    	data: {
			    		"username" : username,
						"password" : password,
						"verifyCode" : verifyCode,
						"tm" : new Date().getTime()
					},
					dataType:'json',
					cache: false,
					success: function(data){
						var ret=eval("("+data+")");
						if("登陆成功" == ret.message_login){
							saveCookie();
							window.location.href = "<%=request.getContextPath()%>/index.do";
						}else if("验证码不正确" == ret.message_login){
							changeCode();
							$("#verifyCode").tips({
								side : 1,
								msg : ret.message_login,
								bg : '#FF5080',
								time : 2
							});
							$("#verifyCode").focus();
						}else{
							changeCode();
							$("#username").tips({
								side : 1,
								msg : ret.message_login,
								bg : '#FF5080',
								time : 2
							});
							$("#username").focus();
						}
					}
				});
			}
		}
		$(document).ready(function() {
			changeCode();
			$("#codeImg").bind("click", changeCode);
		});

		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		function changeCode() {
			$("#codeImg").attr("src", "<%=request.getContextPath()%>/getVerifyCodeImage.do?t=" + genTimestamp());
		}

		//客户端校验
		function check() {

			if ($("#username").val() == "") {

				$("#username").tips({
					side : 2,
					msg : '用户名不得为空',
					bg : '#AE81FF',
					time : 2
				});

				$("#username").focus();
				return false;
			} else {
				$("#username").val(jQuery.trim($('#username').val()));
			}

			if ($("#password").val() == "") {

				$("#password").tips({
					side : 2,
					msg : '密码不得为空',
					bg : '#AE81FF',
					time : 2
				});

				$("#password").focus();
				return false;
			}
			/* if ($("#verifyCode").val() == "") {

				$("#verifyCode").tips({
					side : 1,
					msg : '验证码不得为空',
					bg : '#AE81FF',
					time : 2
				});

				$("#verifyCode").focus();
				return false;
			} */

			$("#loginbox").tips({
				side : 1,
				msg : '正在登录 , 请稍后 ...',
				bg : '#68B500',
				time : 2
			});

			return true;
		}

		function savePaw() {
			if (!$("#saveid").attr("checked")) {
				$.cookie('username', '', {
					expires : -1
				});
				$.cookie('password', '', {
					expires : -1
				});
				$("#username").val('');
				$("#password").val('');
			}
		}

		function saveCookie() {
			if ($("#saveid").attr("checked")) {
				$.cookie('username', $("#username").val(), {
					expires : 7
				});
				$.cookie('password', $("#password").val(), {
					expires : 7
				});
			}
		}
		function quxiao() {
			$("#username").val('');
			$("#password").val('');
			$("#verifyCode").val('');
			changeCode();
		}
		
		jQuery(function() {
			var username = $.cookie('username');
			var password = $.cookie('password');
			if (typeof(username) != "undefined"
					&& typeof(password) != "undefined") {
				$("#username").val(username);
				$("#password").val(password);
				$("#saveid").attr("checked", true);
				$("#verifyCode").focus();
			}
		});
	</script>
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
	</script>

	
</body>

</html>