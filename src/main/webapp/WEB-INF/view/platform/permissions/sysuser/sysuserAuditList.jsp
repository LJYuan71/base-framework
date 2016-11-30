<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/queryAll.jsp"%>
<html lang="zh-CN">
<head>
<title>用户表管理页面</title>
</head>
<script type="text/javascript">
	//点击名字弹出明细页面
	function nameFormatter(value, row, index) {
		return [
			'<a class="name" href="javascript:void(0)" title="详细信息">'+value+'</a> ' 
		].join('');
	}
	window.nameEvents = {
		'click .name' : function(e, value, row, index) {
			dialogModal.editPageDialog({
				url: "${ctx}/platform/permissions/sysuser/get.do?id="+row.id,
				editLayout:"md"
			});
		}
	};
	//操作按钮
	function actionFormatter(value, row, index) {
		return [
				'<a class="adopt" href="javascript:void(0)" title="通过">通过</a> | <a class="notgo" href="javascript:void(0)" title="不通过">不通过</a>' 
		].join('');
	}
	window.actionEvents = {
		'click .adopt' : function(e, value, row, index) {
		Modal.confirm({msg:"确定通过审核？"}).on( function (e){
		if(e){
				$.ajax({
						type : "post",
						url : '${ctx}/platform/permissions/sysuser/audit.do?id='+row.id+'&status=1',
						success : function(res) {
				     	if(res=='"success"'){
				     		Modal.timealert({msg: '该用户通过审核！',time: '2000'}).on( function (){
							window.location.href="${ctx}/platform/permissions/sysuser/auditList.do";
							});
						}else{  
						Modal.timealert({msg: '审核失败，请联系管理员！',time: '2000'}).on( function () { });  }
						}
						});
				}
		});
		},
		'click .notgo' : function(e, value, row, index) {
		Modal.confirm({msg:"确定不予通过？"}).on( function (e) {
		if(e){
				$.ajax({
						type : "post",
						url : '${ctx}/platform/permissions/sysuser/audit.do?id='+row.id+'&status=-1',
						success : function(res) { 
						if(res=='"success"'){
						    Modal.timealert({msg: '已拒绝该用户通过！',time: '2000'}).on( function (){
							window.location.href="${ctx}/platform/permissions/sysuser/auditList.do";
							});
						}else{
							Modal.timealert({msg: '审核失败，请联系管理员！',time: '2000'}).on( function () { }); 
							}
						}
						});
			  	}
		});
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
           	/* fullname:encodeURI($("#fullname").val()),
           	account:encodeURI($("#account").val()) */
        };
        return temp;
    }
    //性别中文显示
    function sexFormatter(value) {
		if(value==1){
          return '<i class="">男</i> ';
    	}else if(value==0){
          return '<i class="">女</i> ';
        }else {
     	  return '<i class="">未知</i> ';
     	  }
	}
	//状态中文显示
	function statusFormatter(value){
		if(value==1){
			return '<i class="">通过</i> ';
		}else if(value==0){
			return '<i class="">未审核</i> ';
		}else if(value==-1){
			return '<i class="">不通过</i> ';
		}else{
			return '<i class="">未知</i> ';
		}
	}
</script>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="display: block;">
				<form class="form-horizontal">
				<div class="form-group">
					</div>
					<!-- <div class="form-group">
						<label class="col-sm-1 control-label">姓名</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"  name="fullname" id="fullname" >
						</div>
						<label class="col-sm-1 control-label">帐号</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"  name="account" id="account" >
						</div>
					</div> -->
				</form>
			</div>
		</div>
	</div>
	<!-- <div id="toolbar" style="margin-left:10px;">
	    <button type="button" class="btn btn-success" id="find" >搜索</button>
	</div> -->
	<table id="table" data-toggle="table" data-url="${ctx }/platform/permissions/sysuser/auditJson.do"
		data-pagination="true" data-side-pagination="server"
		data-page-list="${PAGE_LIST }" data-search="true"
		data-click-to-select="true" data-show-columns="true"
		data-toolbar="#toolbar" data-sort-name="ID" data-sort-order="desc"
		data-query-params="queryParams">
		<thead>
			<tr>
				<th data-formatter="indexFormatter">序号</th>
				<th data-field="fullname" data-sortable="true" data-events="nameEvents" data-formatter="nameFormatter">姓名</th>
				<th data-field="account" data-sortable="true">帐号</th>
				<th data-field="createdate" data-sortable="true" data-formatter="dateFormatter">创建时间</th>
				<th data-field="sex" data-formatter="sexFormatter" data-sortable="true">性别</th>
				<th data-field="status" data-formatter="statusFormatter" data-sortable="true">审核状态</th>
				<th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
			</tr>
		</thead>
	</table>
</body>
</html>
