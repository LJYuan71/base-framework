<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- <!-- jquery -->
<script type="text/javascript" src="${ctx}/js/jquery-1.11.3.min.js"></script>
<!-- bootstrap -->
<link href="${ctx }/resource/bootstrap3.3.5/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/bootstrap3.3.5/js/bootstrap.min.js"></script> --%>
<!-- 998元框架样式 -->
<!--link rel="shortcut icon" href="http://www.zi-han.net/theme/hplus/favicon.ico"-->
<link href="${ctx}/main/default_files/bootstrap.css" rel="stylesheet">
<link href="${ctx}/main/default_files/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/main/default_files/animate.css" rel="stylesheet">
<link href="${ctx}/main/default_files/style.css" rel="stylesheet">
<link style="" id="layui_layer_skinlayercss" href="${ctx}/main/default_files/layer.css" rel="stylesheet"><link style="" id="layui_layer_skinlayerextcss" href="${ctx}/main/default_files/layer_002.css" rel="stylesheet"><link style="" id="layui_layer_skinmoonstylecss" href="${ctx}/main/default_files/style_002.css" rel="stylesheet"></head>
<script src="${ctx}/main/default_files/jquery_002.js"></script>
<script src="${ctx}/main/default_files/bootstrap.js"></script>
<script src="${ctx}/main/default_files/jquery.js"></script>
<script src="${ctx}/main/default_files/jquery_003.js"></script>
<script src="${ctx}/main/default_files/layer.js"></script>
<script src="${ctx}/main/default_files/hplus.js"></script>
<script type="text/javascript" src="${ctx}/main/default_files/contabs.js"></script>
<script src="${ctx}/main/default_files/pace.js"></script>
<!-- confirm、alert弹框js和页面 -->
<script type="text/javascript" src="${ctx}/js/popupwindow.js"></script>
<%@include file="/common/include/popupwindow.jsp"%>

<!-- 弹框控件-->
<script type="text/javascript" src="${ctx}/resource/layer-v2.2/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/layerUtil.js"></script>