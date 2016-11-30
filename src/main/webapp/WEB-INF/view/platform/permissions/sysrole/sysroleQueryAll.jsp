<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/queryAll.jsp"%>
<!-- ztree树控件 -->
<%-- <link href="${ctx }/resource/zTree_v3/css/demo.css" rel="stylesheet" type="text/css" /> --%>
<link href="${ctx }/resource/zTree_v3/css/bootstrapTreeStyle/metro.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resource/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resource/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
<html lang="zh-CN">
<head>
<title>系统角色表管理页面</title>
</head>
<script type="text/javascript">
	$(function () { 
	   	$("#add").click(function(){
			dialogModal.editPageDialog({
				url: "${ctx}/platform/permissions/sysrole/edit.do",
				editLayout:"md"
			});
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
				url: "${ctx}/platform/permissions/sysrole/edit.do",
				editLayout:"md"
			});
			//window.location.href="${ctx}/platform/permissions/sysrole/edit.do?id="+ids;
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
						url : '${ctx}/platform/permissions/sysrole/del.do?ids='+ids,
						success : function(res) {
							var json = eval("(" + res + ")");
							if(json=="success"){
								Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/permissions/sysrole/queryAll.do";
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
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			callback: {
				onClick: nodeClick
			}
 	  };
   //加载资源复选树
    function loadResCheckboxTree(id){
	    treeModal.treeDialog({
				treeLayout:"sm",
				isShow:"hide",
				treeTitle:"选择资源",
				treeId:"sysResCheckboxTree",
				treeHight:"300"
			}).on( function (e) {
			    if(e){
					getSelectNodes(id);
			    }
			});
			$.ajax({   
	           type: 'POST',   
	           async:false,
	           url: "${ctx}/platform/permissions/sysres/queryTreeJson.do",//请求的action路径    
	           success:function(data){ //请求成功后处理函数。 
	           		var ret=eval("("+data+")");
		         	$(document).ready(function(){
						$.fn.zTree.init($("#sysResCheckboxTree"), setting, ret);
					});
	           }   
	      });
    }
    //设置所选内容显示已选
    function setSelectNode(id){
    	$.ajax({   
           type: 'POST',   
           async:false,
           url: "${ctx}/platform/permissions/sysrole/loadRoleRes.do?roleid="+id,//请求的action路径    
           success:function(data){ //请求成功后处理函数。 
           		var ret=eval("("+data+")");
           		$.each(ret,function(index,obj){
           			var treeObj = $.fn.zTree.getZTreeObj("sysResCheckboxTree");
					var node = treeObj.getNodeByParam("id", obj.resid, null);
           		    treeObj.checkNode(node, true, false);
           		});
           }   
      });
    }
    function getSelectNodes(id) {
	    var treeObj = $.fn.zTree.getZTreeObj("sysResCheckboxTree");
	    var nodes = treeObj.getCheckedNodes(true);
	    var selectNames = [];
	    var selectIds = [];
	    for (var i = 0; i < nodes.length; i ++){
	        var node = nodes[i];
	        selectNames.push(node.name);
	        selectIds.push(node.id);
	    }
	    var ids = selectIds.join(',');
	    $.ajax({
	        type:"POST",
	        async:false,
	        url: "${ctx}/platform/permissions/sysrole/saveRoleRes.do?roleid="+id+"&ids="+ids,    
	         success:function(data){ //请求成功后处理函数。 
           		var ret=eval("("+data+")");
	         	if(ret=='success'){
	         		Modal.alert({msg: '设置成功'});
	         	}else if(ret=='fail'){
	         		Modal.alert({msg: '设置失败，请联系管理员'});
	         	}
           }
	    });
    }
	function nodeClick(e,treeId, treeNode) {
		//改成单击展开事件
		var zTree = $.fn.zTree.getZTreeObj("sysResCheckboxTree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		zTree.expandNode(treeNode);
	}	//操作按钮
	function actionFormatter(value, row, index) {
		return[
				'<shiro:hasPermission name="platform:sysrole:get"><a class="get" href="javascript:void(0)" title="查询详情">详情</a>'+'/</shiro:hasPermission>'+
				'<a class="res" href="javascript:void(0)" title="资源设置">资源设置</a>'
		].join(''); 
		
	}
	window.actionEvents = {
		'click .get' : function(e, value, row, index) {
			dialogModal.editPageDialog({
				url: "${ctx}/platform/permissions/sysrole/get.do?id="+row.id,
				editLayout:"sm"
			});
		},
		'click .res' : function(e, value, row, index) {
			loadResCheckboxTree(row.id);
			setSelectNode(row.id);
			$('#treeModal').modal('show'); 
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
           	/* id:encodeURI($("#id").val()),
           	systemid:encodeURI($("#systemid").val()),
           	alias:encodeURI($("#alias").val()),
           	rolename:encodeURI($("#rolename").val()),
           	remark:encodeURI($("#remark").val()),
           	allowdel:encodeURI($("#allowdel").val()),
           	allowedit:encodeURI($("#allowedit").val()),
           	enabled:encodeURI($("#enabled").val()),
           	begincreatedate_s:encodeURI($("#begincreatedate_s").val()),
           	endcreatedate_e:encodeURI($("#endcreatedate_e").val()) */
        };
        return temp;
    }
    //是否启用中文显示
    function enabledFormatter(value) {
		if(value==1){
          return '<i class="">启用</i> ';
    	}else if(value==0){
          return '<i class="">禁止</i> ';
        }else {
     	  return '<i class="">未知</i> ';
     	  }
	}
</script>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="display: block;">
				<form class="form-horizontal">
				<div class="form-group"></div>
					<!-- <div class="form-group">
						<label class="col-sm-1 control-label">ID</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="id" >
						</div>
						<label class="col-sm-1 control-label">系统ID</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="systemid" >
						</div>
						<label class="col-sm-1 control-label">角色别名</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="alias" >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">角色名</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="rolename" >
						</div>
						<label class="col-sm-1 control-label">备注</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="remark" >
						</div>
						<label class="col-sm-1 control-label">允许删除</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="allowdel" >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">允许编辑</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="allowedit" >
						</div>
						<label class="col-sm-1 control-label">"是否启用(0,禁止,1,启用)"</label>
						<div class="col-sm-3">
							<input class="form-control" type="text"   id="enabled" >
						</div>
						<label class="col-sm-1 control-label">启始时间</label>
						<div class="col-sm-1">
							<input class="form-control" type="text"   id="begincreatedate_s" onclick="time()" readonly="readonly" style="width: 80px;">
						</div>
						<label class="col-sm-1 control-label">结束时间</label>
						<div class="col-sm-1">
							<input class="form-control" type="text"   id="endcreatedate_e" onclick="time()" readonly="readonly"  style="width: 80px;">
						</div>
					</div> -->
				</form>
			</div>
		</div>
	</div>
	<div id="toolbar" style="margin-left:10px;">
		<shiro:hasPermission name="platform:sysrole:add">
		    <button type="button" class="btn btn-success" id="add" >新增系统角色表</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="platform:sysrole:edit">
		    <button type="button" class="btn btn-success" id="edit" >编辑系统角色表</button>
	  	</shiro:hasPermission>
	  	<shiro:hasPermission name="platform:sysrole:del">
	    	<button type="button" class="btn btn-success" id="del" >删除系统角色表</button>
	    </shiro:hasPermission>
	    <!-- <button type="button" class="btn btn-success" id="find" >搜索</button> -->
	</div>
	<table id="table" data-toggle="table" data-url="${ctx }/platform/permissions/sysrole/queryAllJson.do"
		data-pagination="true" data-side-pagination="server"
		data-page-list="${PAGE_LIST }" data-search="true"
		data-click-to-select="true" data-show-columns="true"
		data-toolbar="#toolbar" data-sort-name="ID" data-sort-order="desc"
		data-query-params="queryParams">
		<thead>
			<tr>
				<th  data-checkbox="true" ></th>
				<th data-formatter="indexFormatter">序号</th>
				<!-- <th data-field="id" data-sortable="true">ID</th>
				<th data-field="systemid" data-sortable="true">系统ID</th> 
				<th data-field="alias" data-sortable="true">角色别名</th> -->
				<th data-field="rolename" data-sortable="true">角色名</th>
				<!-- <th data-field="remark" data-sortable="true">备注</th>
				<th data-field="allowdel" data-sortable="true">允许删除</th>
				<th data-field="allowedit" data-sortable="true">允许编辑</th>
				<th data-field="enabled" data-formatter="enabledFormatter" data-sortable="true">是否启用</th> -->
				<th data-field="createdate" data-sortable="true" data-formatter="dateFormatter">时间</th>
				<th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
			</tr>
		</thead>
	</table>
</body>
</html>
