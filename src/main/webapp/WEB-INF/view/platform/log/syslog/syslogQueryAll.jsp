<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/queryAll.jsp"%>
<html lang="zh-CN">
<head>
<title>SYS_LOG管理页面</title>
</head>
<script type="text/javascript">
	$(function () { 
	   	$("#add").click(function(){
	   		window.location.href="${ctx}/platform/log/syslog/edit.do";
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
			window.location.href="${ctx}/platform/log/syslog/edit.do?id="+ids;
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
						url : '${ctx}/platform/log/syslog/del.do?ids='+ids,
						success : function(res) {
							var json = eval("(" + res + ")");
							if(json=="success"){
								Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/log/syslog/queryAll.do";
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
			window.location.href="${ctx}/platform/log/syslog/get.do?id="+row.id;
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
           	id:encodeURI($("#id").val()),
           	userid:encodeURI($("#userid").val()),
           	username:encodeURI($("#username").val()),
           	begincreatetime_s:encodeURI($("#begincreatetime_s").val()),
           	endcreatetime_e:encodeURI($("#endcreatetime_e").val()),
           	modelname:encodeURI($("#modelname").val()),
           	description:encodeURI($("#description").val()),
           	state:encodeURI($("#state").val())
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
						<label class="col-sm-1 control-label">ID</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="id" >
						</div>
						<label class="col-sm-1 control-label">用户id</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="userid" >
						</div>
						<label class="col-sm-1 control-label">用户名</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="username" >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">启始创建时间</label>
						<div class="col-sm-1">
							<input class="form-control" type="text"   id="begincreatetime_s" onclick="time()" readonly="readonly"  style="width: 80px;">
						</div>
						<label class="col-sm-1 control-label">结束创建时间</label>
						<div class="col-sm-1">
							<input class="form-control" type="text"   id="endcreatetime_e" onclick="time()" readonly="readonly"  style="width: 80px;">
						</div>
						<label class="col-sm-1 control-label">模块名</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="modelname" >
						</div>
						<label class="col-sm-1 control-label">描述</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="description" >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">状态  1=系统日志  2=业务日志</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="state" >
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="toolbar" style="margin-left:10px;">
	    <button type="button" class="btn btn-success" id="add" >新增SYS_LOG</button>
	    <button type="button" class="btn btn-success" id="edit" >编辑SYS_LOG</button>
	    <button type="button" class="btn btn-success" id="del" >删除SYS_LOG</button>
	     <button type="button" class="btn btn-success" id="find" >搜索</button>
	</div>
	<table id="table" data-toggle="table" data-url="${ctx }/platform/log/syslog/queryAllJson.do"
		data-pagination="true" data-side-pagination="server"
		data-page-list="${PAGE_LIST }" data-search="false"
		data-click-to-select="true" data-show-columns="true"
		data-toolbar="#toolbar" data-sort-name="ID" data-sort-order="desc"
		data-query-params="queryParams">
		<thead>
			<tr>
				<th  data-checkbox="true" ></th>
				<th data-formatter="indexFormatter">序号</th>
				<th data-field="id" data-sortable="true">ID</th>
				<th data-field="userid" data-sortable="true">用户id</th>
				<th data-field="username" data-sortable="true">用户名</th>
				<th data-field="createtime" data-sortable="true" data-formatter="dateFormatter">创建时间</th>
				<th data-field="modelname" data-sortable="true">模块名</th>
				<th data-field="description" data-sortable="true">描述</th>
				<th data-field="state" data-sortable="true">状态  1=系统日志  2=业务日志</th>
				<th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
			</tr>
		</thead>
	</table>
</body>
</html>
