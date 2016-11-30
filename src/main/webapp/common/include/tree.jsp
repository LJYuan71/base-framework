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
<!-- confirm、alert弹框js和页面 -->
<script type="text/javascript" src="${ctx}/js/popupwindow.js"></script>
<%@include file="/common/include/popupwindow.jsp"%>
<!-- bootstrap table -->
<link href="${ctx }/resource/bootstrap3.3.5/css/bootstrap-table.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/bootstrap3.3.5/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/bootstrap3.3.5/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/bootstrap3.3.5/js/bootstrap-table-export.js"></script>
<script type="text/javascript" src="${ctx}/resource/bootstrap3.3.5/js/tableExport.js"></script>
<!-- ztree树控件 -->
<%-- <link href="${ctx }/resource/zTree_v3/css/demo.css" rel="stylesheet" type="text/css" /> --%>
<link href="${ctx }/resource/zTree_v3/css/bootstrapTreeStyle/metro.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resource/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resource/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
<!-- 格式化 -->
<script type="text/javascript" src="${ctx}/js/formate.js"></script>
<!-- queryAll页面列表信息格式化 -->
<script type="text/javascript" src="${ctx}/js/queryAllFormate.js"></script>
<!-- 日历控件 -->
<script type="text/javascript" src="${ctx}/resource/My97DatePicker/WdatePicker.js"></script>
<!-- jquery.form ajax异步提交表单-->
<script type="text/javascript" src="${ctx}/js/jquery.form.js"></script>
<!-- 表单验证js、css -->
<script type="text/javascript" src="${ctx}/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${ctx}/js/ValidformExtend.js"></script>
<script type="text/javascript" src="${ctx}/js/passwordStrength-min.js"></script>
<!-- 下拉框 -->
<link href="${ctx }/resource/bootstrap3.3.5/css/bootstrap-select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/bootstrap3.3.5/js/bootstrap-select.js"></script>
<!-- chosen 下拉框 -->
<link href="${ctx }/resource/chosen_v1.5.1/chosen.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/chosen_v1.5.1/chosen.jquery.js"></script>
<!-- 日历控件 -->
<script type="text/javascript" src="${ctx}/resource/My97DatePicker/WdatePicker.js"></script>
<!-- 下拉收索框控件
<script type="text/javascript" src="${ctx}/resource/bootstrap-suggest-plugin-0.0.2/bootstrap-suggest.js"></script>
 -->
<!-- 加一行加一行表格控件 
<link href="${ctx }/resource/editTable-0.2.0/jquery.edittable.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/editTable-0.2.0/jquery.edittable.min.js"></script>
-->
<!-- 弹框控件-->
<script type="text/javascript" src="${ctx}/resource/layer-v2.2/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/layerUtil.js"></script>
<!-- icheck bootstrap增强的单选复选框 -->
<link href="${ctx }/resource/icheck-1.x/skins/square/blue.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/icheck-1.x/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<!-- 
<link href="${ctx }/resource/treeTable/themes/default/treeTable.min.css" rel="stylesheet" type="text/css" />
<script src="${ctx }/resource/treeTable/jquery.treeTable.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/workflow.js"></script>
 -->
<!-- 二维码 
<script type="text/javascript" src="${ctx}/resource/qrcode/jquery.qrcode.min.js"></script>
-->