<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/tree.jsp"%>
<html lang="zh-CN">
<head>
<title>组织机构管理页面</title>
</head>
<script type="text/javascript">
	$(function () { 
	   	$("#add").click(function(){
			dialogModal.editPageDialog({
				url: "${ctx}/platform/permissions/sysorg/edit.do?parentid="+$("#queryParentid").val(),
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
				url: "${ctx}/platform/permissions/sysorg/edit.do?id="+ids,
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
						url : '${ctx}/platform/permissions/sysorg/del.do?ids='+ids,
						success : function(res) {
							var json = eval("(" + res + ")");
							if(json=="success"){
								Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/permissions/sysorg/tree.do";
								});
							}else if(json=="haschilds"){
								Modal.timealert({msg: '删除失败，请选择末级节点进行删除！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/permissions/sysorg/tree.do";
								});
							}else{
								Modal.timealert({msg: '删除失败，请联系管理员！',time: '2000'}).on( function () {
									window.location.href="${ctx}/platform/permissions/sysorg/tree.do";
								});
							}
						}
					}); 
			  	}
			 });
	   	});
	   	$("#refresh").click(function(){
	   		var zTree = $.fn.zTree.getZTreeObj("sysOrgTree");
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
				onClick: sysOrgTreeClick
			}
		};
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
	};
	function sysOrgTreeClick(e,treeId, treeNode) {
		//改成单击展开事件
		var zTree = $.fn.zTree.getZTreeObj("sysOrgTree");
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
	
	//操作按钮
	function actionFormatter(value, row, index) {
		return [
				'<a class="classify" href="javascript:void(0)" title="设置流程分类">设置流程分类</a>/'+
				'<a class="position" href="javascript:void(0)" title="岗位列表">岗位列表</a>'
		].join('');
	}
	window.actionEvents = {
		'click .classify' : function(e, value, row, index) {
			loadClassifyTree(row.id);
			setSelectNode(row.id);
			$('#treeModal').modal('show');
		},
		'click .position' : function(e, value, row, index) {
			dialogModal.editPageDialog({
				url: "${ctx}/platform/permissions/sysposition/queryByOrg.do?orgid="+row.id,
				editLayout:"md"
			})
		}
	};
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
   function loadClassifyTree(orgid){
		treeModal.treeDialog({
			treeLayout:"md",
			isShow:"hide",
			treeTitle:"选择分类",
			treeId:"classifyInfoTreeCheckbox",
			treeHight:"300"
		}).on( function (e) {
		    if(e){
				getSelectCheckboxNodes(orgid);
		    }
		});
		$.ajax({   
           type: 'POST',   
           async:false,
           url: "${ctx}/secrecy/basic/classifyinfo/queryTreeJson.do",//请求的action路径    
           success:function(data){ //请求成功后处理函数。 
           		var ret=eval("("+data+")");
	         	$(document).ready(function(){
					$.fn.zTree.init($("#classifyInfoTreeCheckbox"), setting, ret);
				});
           }   
      });
   }
   function nodeClick(e,treeId, treeNode) {
		//改成单击展开事件
		var zTree = $.fn.zTree.getZTreeObj("classifyInfoTreeCheckbox");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		zTree.expandNode(treeNode);
	}
	//选中所选节点后保存
	function getSelectCheckboxNodes(orgid) {
	    var treeObj = $.fn.zTree.getZTreeObj("classifyInfoTreeCheckbox");
	    var nodes = treeObj.getCheckedNodes(true);
	    var selectNames = [];
	    var selectIds = [];
	    for (var i = 0; i < nodes.length; i ++){
	        var node = nodes[i];
	        selectNames.push(node.name);
	        selectIds.push(node.id);
	    }
	    var classifyids = selectIds.join(',');
	    $.ajax({
	        type:"POST",
	        async:false,
	        url: "${ctx}/secrecy/basic/classifyinfo/saveOrgClassify.do?orgid="+orgid+"&classifyids="+classifyids,    
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
    function setSelectNode(orgid){
    	$.ajax({   
           type: 'POST',   
           async:false,
           url: "${ctx}/secrecy/basic/classifyinfo/loadOrgClassify.do?id="+orgid,//请求的action路径    
           success:function(data){ //请求成功后处理函数。 
           		var ret=eval("("+data+")");
           		$.each(ret,function(index,obj){
           			var treeObj = $.fn.zTree.getZTreeObj("classifyInfoTreeCheckbox");
					var node = treeObj.getNodeByParam("id", obj.classifyid, null);
           		    treeObj.checkNode(node, true, false);
           		});
           }   
      });
  }
</script>
<body>
	<div class="col-sm-3" style="">
		<div id="sysOrgTree" class="ztree"></div>
	</div>
	<input type="hidden" id="queryParentid" >
	<div class="col-sm-9" >
		<div id="toolbar" style="margin-left:10px;">
		    <shiro:hasPermission name="platform:sysorg:add">
	    		<button type="button" class="btn btn-success" id="add" >新增组织机构</button>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="platform:sysorg:edit">
		    	<button type="button" class="btn btn-success" id="edit" >编辑组织机构</button>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="platform:sysorg:del">
		    	<button type="button" class="btn btn-success" id="del" >删除组织机构</button>
		    </shiro:hasPermission>
		    <button type="button" class="btn btn-success" id="refresh" >刷新</button>
		</div>
		<table id="table" data-toggle="table" data-url="${ctx}/platform/permissions/sysorg/queryAllJson.do"
			data-pagination="true" data-side-pagination="server"
			data-page-list="${PAGE_LIST }" data-search="true"
			data-click-to-select="true" data-show-columns="true"
			data-toolbar="#toolbar" data-sort-name="ID" data-sort-order="desc"
			data-query-params="queryParams">
			<thead>
				<tr>
					<th  data-checkbox="true" ></th>
					<th data-formatter="indexFormatter">序号</th>
					<th data-field="name" data-sortable="true">名称</th>
					<!-- <th data-field="orgdesc" data-sortable="true">描述</th> -->
					<th data-field="sn" data-sortable="true">同级排序</th>
					<th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>
