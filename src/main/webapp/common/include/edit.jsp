<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- jquery -->
<script type="text/javascript" src="${ctx}/js/jquery-1.11.3.min.js"></script>
<!-- bootstrap -->
<link href="${ctx }/resource/bootstrap3.3.5/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/bootstrap3.3.5/js/bootstrap.min.js"></script>
<!-- bootstrap 按钮开关 -->
<link href="${ctx }/resource/bootstrap3.3.5/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/bootstrap3.3.5/js/bootstrap-switch.js"></script>
<!-- confirm、alert弹框js和页面 -->
<script type="text/javascript" src="${ctx}/js/popupwindow.js"></script>
<%@include file="/common/include/popupwindow.jsp"%>
<!-- 998元框架样式 
<link href="${ctx }/main/default_files/style.css" rel="stylesheet" type="text/css" />-->
<!-- jquery.form ajax异步提交表单-->
<script type="text/javascript" src="${ctx}/js/jquery.form.js"></script>
<!-- 表单验证js、css -->
<script type="text/javascript" src="${ctx}/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${ctx}/js/ValidformExtend.js"></script>
<script type="text/javascript" src="${ctx}/js/passwordStrength-min.js"></script>
<%-- <link href="${ctx }/css/validateform/demo.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/validateform/style.css" rel="stylesheet" type="text/css" />--%> 
<!-- 日历控件 -->
<script type="text/javascript" src="${ctx}/resource/My97DatePicker/WdatePicker.js"></script>
<!-- icheck bootstrap增强的单选复选框 -->
<link href="${ctx }/resource/icheck-1.x/skins/square/green.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/icheck-1.x/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<!-- chosen 下拉框 -->
<link href="${ctx }/resource/chosen_v1.5.1/chosen.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/chosen_v1.5.1/chosen.jquery.js"></script>
