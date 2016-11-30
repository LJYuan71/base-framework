<#import "function.ftl" as func>
<#assign pk=func.getPk(model) >
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/queryAll.jsp"%>
<html lang="zh-CN">
<head>
<title>${model.tabComment}管理页面</title>
</head>
<script type="text/javascript">
	$(function () { 
	   	$("#add").click(function(){
	   		window.location.href="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/edit.do";
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
			window.location.href="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/edit.do?id="+ids;
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
						url : '<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/del.do?ids='+ids,
						success : function(res) {
							var json = eval("(" + res + ")");
							if(json=="success"){
								Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
									window.location.href="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/queryAll.do";
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
			window.location.href="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/get.do?id="+row.id;
		}
	};
	//table设置查询参数
	function queryParams(params) {  //配置参数
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        	sort: params.sort,  //排序列名
           	order: params.order,//排位命令（desc，asc）
           	limit: params.limit,   //每页显示多少条记录
           	offset: params.offset,  //从多少调记录开始显示
           	search: encodeURI(params.search==undefined?"":params.search)  //模糊查询
           	/**
           	<#list model.columnList as col>
           	<#if (col.colType=="java.util.Date")>
           	begin${func.convertUnderLine(col.columnName)}_s:encodeURI(<#noparse>$(</#noparse>"#begin${func.convertUnderLine(col.columnName)}_s").val()),
           	end${func.convertUnderLine(col.columnName)}_e:encodeURI(<#noparse>$(</#noparse>"#end${func.convertUnderLine(col.columnName)}_e").val())<#if col_has_next>,</#if>
           	<#else>
           	${func.convertUnderLine(col.columnName)}:encodeURI(<#noparse>$(</#noparse>"#${func.convertUnderLine(col.columnName)}").val())<#if col_has_next>,</#if>
           	</#if>
           	</#list>*/
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
					<#list model.columnList as col>
					<#if 0<col_index  && (col_index % 3)==0>
					</div>
					<div class="form-group">
					</#if>
						<#if (col.colType=="java.util.Date")>
						<label class="col-sm-1 control-label">启始${col.comment}</label>
						<div class="col-sm-1">
							<input class="form-control" type="text"   id="begin${func.convertUnderLine(col.columnName)}_s" onclick="time()" readonly="readonly"  style="width: 80px;">
						</div>
						<label class="col-sm-1 control-label">结束${col.comment}</label>
						<div class="col-sm-1">
							<input class="form-control" type="text"   id="end${func.convertUnderLine(col.columnName)}_e" onclick="time()" readonly="readonly"  style="width: 80px;">
						</div>
						<#else>
						<label class="col-sm-1 control-label">${col.comment}</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="${func.convertUnderLine(col.columnName)}" >
						</div>
						</#if>
					</#list>
					</div>
				</form>
			</div>
		</div>
	</div>
	-->
	<div id="toolbar" style="margin-left:10px;">
		<shiro:hasPermission name="${system}:${packagename}:add">
	   		<button type="button" class="btn btn-success" id="add" >新增</button>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="${system}:${packagename}:edit">
	   		<button type="button" class="btn btn-success" id="edit" >编辑</button>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="${system}:${packagename}:del">
	    	<button type="button" class="btn btn-success" id="del" >删除</button>
	    </shiro:hasPermission>
	     <button type="button" class="btn btn-success" style="display: none;" id="find" >搜索</button>
	</div>
	<table id="table" data-toggle="table" data-url="<#noparse>${</#noparse>ctx }/${system}/${modular}/${packagename}/queryAllJson.do"
		data-pagination="true" data-side-pagination="server"
		data-page-list="<#noparse>${</#noparse>PAGE_LIST }" data-search="true"
		data-click-to-select="true" data-show-columns="true"
		data-toolbar="#toolbar" data-sort-name="${pk}" data-sort-order="desc"
		data-query-params="queryParams" data-height="100%">
		<thead>
			<tr>
				<th  data-checkbox="true" ></th>
				<th data-formatter="indexFormatter">序号</th>
				<#list model.columnList as col>
				<#if (col.colType=="java.util.Date")>
				<th data-field="${func.convertUnderLine(col.columnName)}" data-sortable="true" data-formatter="dateFormatter">${col.comment}</th>
				<#else>
				<th data-field="${func.convertUnderLine(col.columnName)}" data-sortable="true">${col.comment}</th>
				</#if>
				</#list>
				<th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
			</tr>
		</thead>
	</table>
</body>
</html>
