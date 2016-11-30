<#import "function.ftl" as func>
<#assign pk=func.getPk(model) >
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/tree.jsp"%>
<html lang="zh-CN">
<head>
<title>${model.tabComment}管理页面</title>
</head>
<script type="text/javascript">
	$(function () { 
	   	$("#add").click(function(){
			dialogModal.editPageDialog({
				url: "<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/edit.do?parentid="+$("#queryParentid").val(),
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
				url: "<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/edit.do?id="+ids,
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
						url : '<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/del.do?ids='+ids,
						success : function(res) {
							var json = eval("(" + res + ")");
							if(json=="success"){
								Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
									window.location.href="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/tree.do";
								});
							}else if(json=="haschilds"){
								Modal.timealert({msg: '删除失败，请选择末级节点进行删除！',time: '2000'}).on( function () {
									window.location.href="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/tree.do";
								});
							}else{
								Modal.timealert({msg: '删除失败，请联系管理员！',time: '2000'}).on( function () {
									window.location.href="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/tree.do";
								});
							}
						}
					}); 
			  	}
			 });
	   	});
	   	$("#refresh").click(function(){
	   		var zTree = $.fn.zTree.getZTreeObj("${modelname?uncap_first}Tree");
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
				onClick: ${modelname?uncap_first}TreeClick
			}
		};
		$.ajax({
            type: "post",
            url: "<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/queryTreeJson.do",
            async:false,
            success: function(data){
            	var ret=eval("("+data+")");
	         	$(document).ready(function(){
					$.fn.zTree.init($("#${modelname?uncap_first}Tree"), setting, ret);
				});
            }
        });
	};
	function ${modelname?uncap_first}TreeClick(e,treeId, treeNode) {
		//改成单击展开事件
		var zTree = $.fn.zTree.getZTreeObj("${modelname?uncap_first}Tree");
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
           	search: encodeURI(params.search==undefined?"":params.search),  //模糊查询
           	parentidQuery:encodeURI($("#queryParentid").val()),
        };
        return temp;
    }
	//进入页面加载树
	loadTree();
</script>
<body>
	<div class="col-sm-3" style="">
		<div id="${modelname?uncap_first}Tree" class="ztree"></div>
	</div>
	<input type="hidden" id="queryParentid" >
	<div class="col-sm-9" >
		<div id="toolbar" style="margin-left:10px;">
			<shiro:hasPermission name="${system}:${packagename}:add">
		   		<button type="button" class="btn btn-success" id="add" >新增${model.tabComment}</button>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="${system}:${packagename}:edit">
		   		<button type="button" class="btn btn-success" id="edit" >编辑${model.tabComment}</button>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="${system}:${packagename}:del">
		    	<button type="button" class="btn btn-success" id="del" >删除${model.tabComment}</button>
		    </shiro:hasPermission>
		    <button type="button" class="btn btn-success" id="refresh" >刷新</button>
		</div>
		<table id="table" data-toggle="table" data-url="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/queryAllJson.do"
			data-pagination="true" data-side-pagination="server"
			data-page-list="<#noparse>${</#noparse>PAGE_LIST }" data-search="false"
			data-click-to-select="true" data-show-columns="true"
			data-toolbar="#toolbar" data-sort-name="${pk}" data-sort-order="desc"
			data-query-params="queryParams">
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
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>
