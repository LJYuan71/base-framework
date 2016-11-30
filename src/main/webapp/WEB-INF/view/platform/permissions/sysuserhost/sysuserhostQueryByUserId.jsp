<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/queryAll.jsp"%>
<html lang="zh-CN">
<head>
<title>sys_user_host管理页面</title>
</head>
<script type="text/javascript">
	$(function () { 
	   	$("#find").click(function(){
	   		$("#table").bootstrapTable("refresh");
	   	});
	});
	//操作按钮
	function actionFormatter(value, row, index) {
		//alert(row.userhostip);
		var web="http://"+row.userhostip+"/asset/";
		return [
				'<a class="get" href="'+web+'" target="_blank" title="进入资产管理">进入资产管理</a>/'+
				'<a class="telemaintenance" href="javascript:void(0)" title="远程维护">远程维护</a>'
		].join('');
	}
	function statusFormater(value) {
		if(value=="0"){
	         	return '<i style="font-style: normal;">未启动</i> ';
	    	}else if(value=="1"){
	    		return '<i style="font-style: normal;">已启动</i> ';
	    	}else{
	    		return '<i style="font-style: normal;">-</i> ';
	    	}
	}
	/*
	window.actionEvents = {
		'click .get' : function(e, value, row, index) {
			window.location.href="${ctx}/platform/permissions/sysuserhost/get.do?id="+row.id;
		}
	};*/
	//远程维护Remote maintenance
	window.actionEvents = {
		'click .telemaintenance' : function(e, value, row, index) {
			parentOpenEdit("远程维护","${ctx}/platform/permissions/sysuserhost/RM.do?id="+row.id,"40%","80%");
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
        };
        return temp;
    }
</script>
<body>
	<div id="toolbar" style="margin-left:10px;">
		<!-- 
		<shiro:hasPermission name="platform:sysuserhost:add">
	   		<button type="button" class="btn btn-success" id="add" >新增用户主机</button>
	    </shiro:hasPermission>
	     
	    <shiro:hasPermission name="platform:sysuserhost:edit">
	   		<button type="button" class="btn btn-success" id="edit" >编辑</button>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="platform:sysuserhost:del">
	    	<button type="button" class="btn btn-success" id="del" >删除</button>
	    </shiro:hasPermission>
	    -->
	     <button type="button" class="btn btn-success" style="display: none;" id="find" >搜索</button>
	</div>
	<table id="table" data-toggle="table" data-url="${ctx }/platform/permissions/sysuserhost/queryJsonByUserId.do"
		data-pagination="true" data-side-pagination="server"
		data-page-list="${PAGE_LIST }" data-search="true"
		data-click-to-select="true" data-show-columns="true"
		data-toolbar="#toolbar"  data-sort-order="desc"
		data-query-params="queryParams">
		<thead>
			<tr>
				<th  data-checkbox="true" ></th>
				<th class="table-field50" data-formatter="indexFormatter">序号</th>
				<!-- 
				<th class="table-field150" data-field="id" data-sortable="true">id</th>
				 -->
				<th data-field="name" data-sortable="true">名称</th>
				<th class="table-field100" data-field="type" data-sortable="true">规模类型</th>
				<!-- 
				<th data-field="userid" data-sortable="true">用户id</th>
				<th data-field="hostinfoid" data-sortable="true">主机id</th>
				 -->
				<th class="table-field120" data-field="userhostip" data-sortable="true">分配的ip</th>
				<th class="table-field80" data-field="status" data-formatter="statusFormater" data-sortable="true">状态</th>
				<th data-field="remark" data-sortable="true">备注</th>
				<th class="table-field100" data-field="createtime" data-sortable="true" data-formatter="dateFormatter">创建时间</th>
				<th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">管理</th>
			</tr>
		</thead>
	</table>
</body>
</html>
