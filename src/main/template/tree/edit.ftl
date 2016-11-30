<#import "function.ftl" as func>
<#assign pk=func.getPk(model) >
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script>
   	$(function(){
   		$("#${modelname?uncap_first}Form").Validform();
   		var options = {
		   beforeSubmit: showRequest,  //提交前的回调函数
		   success: showResponse,      //提交后的回调函数
		   timeout: 3000               //限制请求的时间，当请求大于3秒后，跳出请求
		}
		function showRequest(formData, jqForm, options){
		   	return true;  //只要不返回false，表单都会提交,在这里可以对表单元素进行验证
		};
		function showResponse(responseText, statusText){
			var json = eval("(" + responseText + ")");
			if (json.result == 1) {
				Modal.timealert({msg: '编辑成功！',time: '2000'}).on( function () {
					//关闭编辑页面
				  	window.location.href="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/tree.do";
				});
			}else {
				//失败
				Modal.alert({msg: '提交失败，请联系管理员！'});
			}
		};
		
		$("#submit").click(function(){
			$("#${modelname?uncap_first}Form").ajaxForm(options);
		});
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否放弃编辑返回列表页？"}).on( function (e) {
				  	if(e){
				  		//关闭编辑页面
				  		$('#editPage').modal('hide');
				  	}
			});
		});
   	})
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
				chkStyle: "radio",
				radioType: "all"
			},
			callback: {
				onClick: nodeClick
			}
   };
   $(function(){
   		treeModal.treeDialog({
			treeLayout:"sm",
			isShow:"hide",
			treeTitle:"选择上级菜单",
			treeId:"${modelname?uncap_first}ParentTree",
			treeHight:"300"
		}).on( function (e) {
		    if(e){
				getSelectNodes();
		    }
		});
		$.ajax({   
           type: 'POST',   
           async:false,
           url: "<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/queryTreeJson.do",//请求的action路径    
           success:function(data){ //请求成功后处理函数。 
           		var ret=eval("("+data+")");
	         	$(document).ready(function(){
					$.fn.zTree.init($("#${modelname?uncap_first}ParentTree"), setting, ret);
				});
           }   
      });
   })
   function nodeClick(e,treeId, treeNode) {
		//改成单击展开事件
		var zTree = $.fn.zTree.getZTreeObj("${modelname?uncap_first}ParentTree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		zTree.expandNode(treeNode);
	}
   function getSelectNodes() {
	    var treeObj = $.fn.zTree.getZTreeObj("${modelname?uncap_first}ParentTree");
	    var nodes = treeObj.getCheckedNodes(true);
	    var selectNames = [];
	    var selectIds = [];
	    for (var i = 0; i < nodes.length; i ++){
	        var node = nodes[i];
	        selectNames.push(node.name);
	        selectIds.push(node.id);
	    }
	    //sn赋默认值
		$.ajax({   
        	type: 'POST',   
          	async:false,
           	url: "<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/queryMaxSnByParentid.do?parentid="+selectIds.join(","),//请求的action路径    
           	success:function(data){ //请求成功后处理函数。 
           		var ret=eval("("+data+")");
           		$("#sn").val(ret);
           	}   
      	});
	    $("#parentname").val(selectNames.join(","));
	    $("#parentid").val(selectIds.join(","));
	    $('#treeModal').modal('hide');
	}
	$(function(){
		$("#parentname").focus(function(){
			$('#treeModal').modal('show');
		});
		$("#topNode").click(function(){
			$("#parentname").val("顶级节点");
	   		$("#parentid").val("0");
		})
	})
</script>


<body class="gray-bg">
	<div class="modal-header">
     	<button type="button" class="close" data-dismiss="modal">
			<span aria-hidden="true">×</span><span class="sr-only">Close</span>
		</button>
		<h5 class="modal-title">
			<i class="fa fa-exclamation-circle"></i> 编辑
		</h5>
   	</div>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="display: block;">
						<div class="form-group"></div>
						<form class="form-horizontal" method="post" id="${modelname?uncap_first}Form" action="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/save.do">
							<input class="form-control" type="hidden"  name="id" value="<#noparse>${</#noparse>${modelname?uncap_first}.id }">
							<input class="form-control" type="hidden"  name="path" value="<#noparse>${</#noparse>${modelname?uncap_first}.path }">
							<div class="form-group">
								<label class="col-sm-4 control-label">上级节点</label>
								<div class="col-sm-5">
									<input class="form-control" type="text"  name="parentname" id="parentname" value="<#noparse>${</#noparse>${modelname?uncap_first}.parentname }" readonly="readonly">
									<input class="form-control" type="hidden"  name="parentid" id="parentid" value="<#noparse>${</#noparse>${modelname?uncap_first}.parentid }">
								</div>
								<div class="col-sm-3">
									<input type="button" value="顶级节点" id="topNode"  class="btn btn-info"/>
								</div>
							</div>
							<#list model.columnList as col>
							<#if (func.convertUnderLine(col.columnName)!="id")&&(func.convertUnderLine(col.columnName)!="path")&&(func.convertUnderLine(col.columnName)!="parentid")>
							<div class="form-group">
								<label class="col-sm-4 control-label">${col.comment}</label>
								<div class="col-sm-7">
									<input class="form-control" type="text" id="${func.convertUnderLine(col.columnName)}"   name="${func.convertUnderLine(col.columnName)}" value="<#noparse>${</#noparse>${modelname?uncap_first}.${func.convertUnderLine(col.columnName)} }">
								</div>
							</div>
							</#if>
							</#list>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" id="submit">保存内容</button>
									<input type="button" value="取消" id="back"  class="btn btn-white"/>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
