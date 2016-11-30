<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/queryAll.jsp"%>
<html lang="zh-CN">
<head>
<title>主机配置管理页面</title>
</head>
<script type="text/javascript">
	$(function () { 
	   	$("#add").click(function(){
	   		window.location.href="${ctx}/platform/permissions/syshostinfo/edit.do";
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
			window.location.href="${ctx}/platform/permissions/syshostinfo/edit.do?id="+ids;
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
						url : '${ctx}/platform/permissions/syshostinfo/del.do?ids='+ids,
						success : function(res) {
							var json = eval("(" + res + ")");
							if(json=="success"){
								Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/permissions/syshostinfo/queryAll.do";
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
			window.location.href="${ctx}/platform/permissions/syshostinfo/get.do?id="+row.id;
		}
	};
	//table设置查询参数
	function queryParams(params) {  //配置参数
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        	sort: params.sort,  //排序列名
           	order: params.order,//排位命令（desc，asc）
           	limit: params.limit,   //每页显示多少条记录
           	offset: params.offset,  //从多少调记录开始显示
           	//search: encodeURI(params.search==undefined?"":params.search)  //模糊查询
           	search:params.search==undefined?"":params.search
           	/*id:encodeURI($("#id").val()),
           	name:encodeURI($("#name").val()),
           	type:encodeURI($("#type").val()),
           	cpu:encodeURI($("#cpu").val()),
           	memory:encodeURI($("#memory").val()),
           	harddisk:encodeURI($("#harddisk").val()),
           	maxusers:encodeURI($("#maxusers").val()),
           	minusers:encodeURI($("#minusers").val()),
           	describes:encodeURI($("#describes").val()),
           	begincreatetime_s:encodeURI($("#begincreatetime_s").val()),
           	endcreatetime_e:encodeURI($("#endcreatetime_e").val())*/
        };
        return temp;
    }
</script>
<body>
<!-- 
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="display: block;">
				<form class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-1 control-label">主机id</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="id" >
						</div>
						<label class="col-sm-1 control-label">主机名称</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="name" >
						</div>
						<label class="col-sm-1 control-label">适用规模</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="type" >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">CPU核数</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="cpu" >
						</div>
						<label class="col-sm-1 control-label">内存</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="memory" >
						</div>
						<label class="col-sm-1 control-label">硬盘</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="harddisk" >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">最大用户数</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="maxusers" >
						</div>
						<label class="col-sm-1 control-label">最小用户数</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="minusers" >
						</div>
						<label class="col-sm-1 control-label">描述</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="describes" >
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
					</div>
				</form>
			</div>
		</div>
	</div>
	 -->
	<div id="toolbar" style="margin-left:10px;">
		<shiro:hasPermission name="platform:syshostinfo:add">
	   		<button type="button" class="btn btn-success" id="add" >新增</button>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="platform:syshostinfo:edit">
	   		<button type="button" class="btn btn-success" id="edit" >编辑</button>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="platform:syshostinfo:del">
	    	<button type="button" class="btn btn-success" id="del" >删除</button>
	    </shiro:hasPermission>
	     <button type="button" class="btn btn-success" style="display: none;" id="find" >搜索</button>
	</div>
	<table id="table" data-toggle="table" data-url="${ctx }/platform/permissions/syshostinfo/queryAllJson.do"
		data-pagination="true" data-side-pagination="server"
		data-page-list="${PAGE_LIST }" data-search="true"
		data-click-to-select="true" data-show-columns="true"
		data-toolbar="#toolbar" data-sort-order="desc"
		data-query-params="queryParams">
		<thead>
			<tr>
				<th  data-checkbox="true" ></th>
				<th class="table-field50" data-formatter="indexFormatter">序号</th>
				<th class="table-field150" data-field="id" data-sortable="true">主机id</th>
				<th data-field="name" data-sortable="true">主机名称</th>
				<th data-field="type" data-sortable="true">规模</th>
				<th data-field="cpu" data-sortable="true">CPU核数</th>
				<th data-field="memory" data-sortable="true">内存</th>
				<th data-field="harddisk" data-sortable="true">硬盘</th>
				<th data-field="maxusers" data-sortable="true">最大用户数</th>
				<th data-field="minusers" data-sortable="true">最小用户数</th>
				<th data-field="describes" data-sortable="true">描述</th>
				<th data-field="createtime" data-sortable="true" data-formatter="dateFormatter">创建时间</th>
				<th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
			</tr>
		</thead>
	</table>
</body>
</html>
