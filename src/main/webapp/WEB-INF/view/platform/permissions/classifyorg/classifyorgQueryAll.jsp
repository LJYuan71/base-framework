<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/queryAll.jsp"%>
<html lang="zh-CN">
<head>
<title>SEC_CLASSIFY_ORG管理页面</title>
</head>
<script type="text/javascript">
	$(function () { 
	   	$("#add").click(function(){
	   		window.location.href="${ctx}/platform/permissions/classifyorg/edit.do";
	   	});
	   	$("#edit").click(function(){
			var $table = $('#table');
	   	 	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
            	return row.id;
            });
            if(ids.length != 1){
		    	Modal.alert(
			  	{
			    	msg: '请选择一项进行编辑！'
			  	});
				return false;
			}
			window.location.href="${ctx}/platform/permissions/classifyorg/edit.do?id="+ids;
	   	});
		$("#del").click(function(){
			var $table = $('#table');
	   	 	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
            	return row.id;
            });
            if(ids.length == 0){
		    	Modal.alert(
			  	{
			    	msg: '还没有选择,请至少选择一项进行删除！'
			  	});
				return false;
			}
			Modal.confirm({msg:"删除后数据不可恢复，是否确定删除？"}).on( function (e) {
			  	if(e){
			  		//继续删除
			  		$.ajax({
						type : "post",
						url : '${ctx}/platform/permissions/classifyorg/del.do?ids='+ids,
						success : function(res) {
							var json = eval("(" + res + ")");
							if(json=="success"){
								Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/permissions/classifyorg/queryAll.do";
								});
							}else{
								Modal.timealert({msg: '删除失败，请联系管理员！',time: '2000'}).on( function () {
					
								});
							}
						}
					}); 
			  	}
			 });
	   	});
	   	$("#find").click(function(){
	   		$("#table").bootstrapTable("refresh");
	   	});
	});
	//操作按钮
	function actionFormatter(value, row, index) {
		return [
				'<a class="get" href="javascript:void(0)" title="查询详情">详情</a>' 
		].join('');
	}
	window.actionEvents = {
		'click .get' : function(e, value, row, index) {
			window.location.href="${ctx}/platform/permissions/classifyorg/get.do?id="+row.id;
		}
	};
	//table设置查询参数
	function queryParams(params) {  //配置参数
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        	sort: params.sort,  //排序列名
           	order: params.order,//排位命令（desc，asc）
           	limit: params.limit,   //每页显示多少条记录
           	offset: params.offset,  //从多少调记录开始显示
           	search: params.search,  //模糊查询
        };
        return temp;
    }
</script>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="display: block;">
				<form class="form-horizontal">
					<div class="form-group">
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="toolbar" style="margin-left:10px;">
	    <button type="button" class="btn btn-success" id="add" >新增SEC_CLASSIFY_ORG</button>
	    <button type="button" class="btn btn-success" id="edit" >编辑SEC_CLASSIFY_ORG</button>
	    <button type="button" class="btn btn-success" id="del" >删除SEC_CLASSIFY_ORG</button>
	     <button type="button" class="btn btn-success" id="find" >搜索</button>
	</div>
	<table id="table" data-toggle="table" data-url="${ctx }/platform/permissions/classifyorg/queryAllJson.do"
		data-pagination="true" data-side-pagination="server"
		data-page-list="${PAGE_LIST }" data-search="false"
		data-click-to-select="true" data-show-columns="true"
		data-toolbar="#toolbar" data-sort-name=""id"" data-sort-order="desc"
		data-query-params="queryParams">
		<thead>
			<tr>
				<th  data-checkbox="true" ></th>
				<th data-formatter="indexFormatter">序号</th>
				<th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
			</tr>
		</thead>
	</table>
</body>
</html>
