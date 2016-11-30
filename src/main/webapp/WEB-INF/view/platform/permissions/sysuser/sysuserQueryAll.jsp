<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/queryAll.jsp"%>
<link href="${ctx }/resource/zTree_v3/css/bootstrapTreeStyle/metro.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resource/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resource/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resource/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
<html lang="zh-CN">
<head>
<title>用户表管理页面</title>
</head>
<script type="text/javascript">
	$(function () { 
	   	$("#add").click(function(){
	   		window.location.href="${ctx}/platform/permissions/sysuser/edit.do";
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
			window.location.href="${ctx}/platform/permissions/sysuser/edit.do?id="+ids;
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
						url : '${ctx}/platform/permissions/sysuser/del.do?ids='+ids,
						success : function(res) {
							var json = eval("(" + res + ")");
							if(json=="success"){
								Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/permissions/sysuser/queryAll.do";
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
				
				'<shiro:hasPermission name="platform:sysuser:get"><a class="get" href="javascript:void(0)" title="查询详情">详情</a>'+'/</shiro:hasPermission>'+
				'<a class="org" href="javascript:void(0)" title="查询详情">组织机构设置</a>'+'/'+ 
				'<a class="pos" href="javascript:void(0)" title="查询详情">岗位设置</a>'+'/'+
				'<a class="role" href="javascript:void(0)" title="查询详情">角色设置</a>'
		].join('');
	}
	window.actionEvents = {
		'click .get' : function(e, value, row, index) {
			dialogModal.editPageDialog({
				url: "${ctx}/platform/permissions/sysuser/get.do?id="+row.id,
				editLayout:"md"
			});
		},
		'click .org' : function(e, value, row, index) {
			loadOrgTree(row.id);
			setOrgSelectNode(row.id);
			$('#treeModal').modal('show');
		},
		'click .pos' : function(e, value, row, index) {
			dialogModal.editPageDialog({
				url: "${ctx}/platform/permissions/sysuser/pos.do?userid="+row.id,
				editLayout:"md"
			});
		},
		'click .role' : function(e, value, row, index) {
			dialogModal.editPageDialog({
				url: "${ctx}/platform/permissions/sysuser/role.do?userid="+row.id,
				editLayout:"md"
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
    //加载组织机构树
    function loadOrgTree(userid){
		treeModal.treeDialog({
			treeLayout:"md",
			isShow:"hide",
			treeTitle:"选择组织机构",
			treeId:"sysOrgTree",
			treeHight:"300"
		}).on( function (e) {
		    if(e){
				getSelectCheckboxNodes(userid);
		    }
		});
		$.ajax({
            type: "post",
            url: "${ctx}/platform/permissions/sysorg/queryTreeJson.do",
            async:false,
            success: function(data){
            	var ret=eval("("+data+")");
	         	$(document).ready(function(){
					$.fn.zTree.init($("#sysOrgTree"), setting, ret);
				});
            }
        });
   }
   function nodeClick(e,treeId, treeNode) {
		//改成单击展开事件
		var zTree = $.fn.zTree.getZTreeObj("sysOrgTree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		zTree.expandNode(treeNode);
	}
   //选中所选节点后保存
	function getSelectCheckboxNodes(userid) {
	    var treeObj = $.fn.zTree.getZTreeObj("sysOrgTree");
	    var nodes = treeObj.getCheckedNodes(true);
	    var selectNames = [];
	    var selectIds = [];
	    for (var i = 0; i < nodes.length; i ++){
	        var node = nodes[i];
	        selectNames.push(node.name);
	        selectIds.push(node.id);
	    }
	    var orgids = selectIds.join(',');
	    $.ajax({
	        type:"POST",
	        async:false,
	        url: "${ctx}/platform/permissions/sysuser/saveUserOrg.do?userid="+userid+"&orgids="+orgids,    
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
	//设置所选内容显示已选
    function setOrgSelectNode(userid){
    	 $.ajax({   
           type: 'POST',   
           async:false,
           url: "${ctx}/platform/permissions/sysuser/loadUserOrg.do?userid="+userid,//请求的action路径    
           success:function(data){ //请求成功后处理函数。 
           		var ret=eval("("+data+")");
           		$.each(ret,function(index,obj){
           			var treeObj = $.fn.zTree.getZTreeObj("sysOrgTree");
					var node = treeObj.getNodeByParam("id", obj.orgid, null);
           		    treeObj.checkNode(node, true, false);
           		});
           }   
      });
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
	//数据来源中文显示
	function fromtypeFormatter(value){
		if(value==1){
			return '<i class="">系统同步</i> ';
		}else if(value==0){
			return '<i class="">系统添加</i> ';
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
	<div id="toolbar" style="margin-left:10px;">
		<shiro:hasPermission name="platform:sysuser:add">
	    	<button type="button" class="btn btn-success" id="add" >新增用户表</button>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="platform:sysuser:edit">
	    	<button type="button" class="btn btn-success" id="edit" >编辑用户表</button>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="platform:sysuser:del">
	    	<button type="button" class="btn btn-success" id="del" >删除用户表</button>
	    </shiro:hasPermission>
	    <!-- <button type="button" class="btn btn-success" id="find" >搜索</button> -->
	</div>
	<table id="table" data-toggle="table" data-url="${ctx }/platform/permissions/sysuser/queryAllJson.do"
		data-pagination="true" data-side-pagination="server"
		data-page-list="${PAGE_LIST }" data-search="true"
		data-click-to-select="true" data-show-columns="true"
		data-toolbar="#toolbar" data-sort-name="ID" data-sort-order="desc"
		data-query-params="queryParams">
		<thead>
			<tr>
				<th  data-checkbox="true" ></th>
				<th class="table-field50"  data-formatter="indexFormatter">序号</th>
				<th class="table-field150" data-field="fullname" data-sortable="true">姓名</th>
				<th class="table-field150" data-field="account" data-sortable="true">帐号</th>
				<th class="table-field150" data-field="createdate" data-sortable="true" data-formatter="dateFormatter">创建时间</th>
				<!-- <th data-field="sex" data-formatter="sexFormatter" data-sortable="true">性别</th> -->
				<!-- 0-女；1-男  -->
				<th class="table-field150" data-field="company" data-sortable="true">单位名称</th>
				<th class="table-field150" data-field="status" data-formatter="statusFormatter" data-sortable="true">审核状态</th>
				<!-- 0-未审核；1-通过；-1-不通过 -->
				<!-- <th data-field="fromtype" data-formatter="fromtypeFormatter" data-sortable="true">数据来源</th> -->
			    <!-- 0-系统添加；1-系统同步；其他-未知 -->
				<th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
			</tr>
		</thead>
	</table>
</body>
</html>
