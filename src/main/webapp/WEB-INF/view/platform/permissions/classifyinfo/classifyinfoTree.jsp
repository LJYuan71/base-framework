<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/tree.jsp"%>
<html lang="zh-CN">
<head>
<title>分类信息管理页面</title>
</head>
<script type="text/javascript">
	$(function () { 
	   	$("#add").click(function(){
			dialogModal.editPageDialog({
				url: "${ctx}/platform/permissions/classifyinfo/edit.do?parentid="+$("#queryParentid").val(),
				editLayout:"md"
			})
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
			dialogModal.editPageDialog({
				url: "${ctx}/platform/permissions/classifyinfo/edit.do?id="+ids,
				editLayout:"md"
			})
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
						url : '${ctx}/platform/permissions/classifyinfo/del.do?ids='+ids,
						success : function(res) {
							var json = eval("(" + res + ")");
							if(json=="success"){
								Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/permissions/classifyinfo/tree.do";
								});
							}else if(json=="haschilds"){
								Modal.timealert({msg: '删除失败，请选择末级节点进行删除！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/permissions/classifyinfo/tree.do";
								});
							}else{
								Modal.timealert({msg: '删除失败，请联系管理员！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/permissions/classifyinfo/tree.do";
								});
							}
						}
					}); 
			  	}
			 });
	   	});
	   	$("#refresh").click(function(){
	   		var zTree = $.fn.zTree.getZTreeObj("classifyInfoTree");
			zTree.refresh();
			//设置隐藏域parentid分页用
			$("#queryParentid").val("");
			//重新加载表格
			$("#table").bootstrapTable("refresh");
	   	})
	});
	
	//加载树      		
	function loadTree(){
		var setting = {
			view: {
				showIcon: false,//不限制前方图标
				dblClickExpand: false//屏蔽双击展开树事件
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: classifyInfoTreeClick
			}
		};
		$.ajax({
            type: "post",
            url: "${ctx}/platform/permissions/classifyinfo/queryTreeJson.do",
            async:false,
            success: function(data){
            	var ret=eval("("+data+")");
	         	$(document).ready(function(){
					$.fn.zTree.init($("#classifyInfoTree"), setting, ret);
				});
            }
        });
	};
	function classifyInfoTreeClick(e,treeId, treeNode) {
		//改成单击展开事件
		var zTree = $.fn.zTree.getZTreeObj("classifyInfoTree");
		zTree.expandNode(treeNode);
		//设置隐藏域parentid分页用
		$("#queryParentid").val(treeNode.id);
		//重新加载表格
		$("#table").bootstrapTable("refresh");
	}
	//table设置查询参数
	function queryParams(params) {  //配置参数
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        	sort: params.sort,  //排序列名
           	order: params.order,//排位命令（desc，asc）
           	limit: params.limit,   //每页显示多少条记录
           	offset: params.offset,  //从多少调记录开始显示
           	search: params.search,  //模糊查询
           	parentidQuery:encodeURI($("#queryParentid").val()),
        };
        return temp;
    }
	//进入页面加载树
	loadTree();
</script>
<body>
	<div class="col-sm-3" style="">
		<div id="classifyInfoTree" class="ztree"></div>
	</div>
	<input type="hidden" id="queryParentid" >
	<div class="col-sm-9" >
		<div id="toolbar" style="margin-left:10px;">
		    <button type="button" class="btn btn-success" id="add" >新增分类信息</button>
		    <button type="button" class="btn btn-success" id="edit" >编辑分类信息</button>
		    <button type="button" class="btn btn-success" id="del" >删除分类信息</button>
		    <button type="button" class="btn btn-success" id="refresh" >刷新</button>
		</div>
		<table id="table" data-toggle="table" data-url="${ctx}/platform/permissions/classifyinfo/queryAllJson.do"
			data-pagination="true" data-side-pagination="server"
			data-page-list="${PAGE_LIST }" data-search="true"
			data-click-to-select="true" data-show-columns="true"
			data-toolbar="#toolbar" data-sort-name="ID" data-sort-order="desc"
			data-query-params="queryParams">
			<thead>
				<tr>
					<th  data-checkbox="true" ></th>
					<th data-formatter="indexFormatter">序号</th>
					<th data-field="id" data-sortable="true">id</th>
					<th data-field="name" data-sortable="true">名称</th>
					<th data-field="parentid" data-sortable="true">父ID</th>
					<th data-field="sn" data-sortable="true">同级排序</th>
					<th data-field="path" data-sortable="true">PATH</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>
