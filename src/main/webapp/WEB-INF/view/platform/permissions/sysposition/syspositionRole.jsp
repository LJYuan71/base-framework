<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function(){
	   setSelectNode();//设置某岗位已有角色
	   $("#roleable tr").click(function (){
			if($(this).find("input[type=checkbox]").attr("checked")=="checked"){
				$(this).find("input[type=checkbox]").removeAttr("checked");
			}else if($(this).find("input[type=checkbox]").attr("checked")==undefined){
				$(this).find("input[type=checkbox]").attr("checked","checked");
				$(this).find("input[type=checkbox]").prop("checked","checked");
			}
        });
		$("#posroleback").click(function(){
			$('#editPagePopup').modal('hide');
		});
		$("#posrolesubmit").click(function(){
			var selectIds="";
			$("#roleable tr td input[type=checkbox]").each(function(){
			    if(this.checked){
			    	selectIds+=$(this).val()+",";
			    }
			});  
			selectIds = selectIds.substring(0, selectIds.length-1);
		    $.ajax({
		        type:"POST",
		        async:false,
		        url: "${ctx}/platform/permissions/sysposrole/savePosRole.do?positionid="+'${positionid}'+"&ids="+selectIds,    
		        success:function(data){ //请求成功后处理函数。 
	           		var ret=eval("("+data+")");
		         	if(ret=='success'){
		         		Modal.alert({msg: '设置成功'});
		         	}else if(ret=='fail'){
		         		Modal.alert({msg: '设置失败，请联系管理员'});
		         	}
	           } 
		    });
	    	$('#editPagePopup').modal('hide');
		});
	});
	function setSelectNode(){
    	 $.ajax({   
           type: 'POST',   
           async:false,
           url: "${ctx}/platform/permissions/sysposrole/getByPosId.do?positionid="+'${positionid}',//请求的action路径    
           success:function(data){ //请求成功后处理函数。 
           		var ret=eval("("+data+")");
           		$.each(ret,function(index,obj){//后台返回的数据
		            $("#roleable tr td input[type=checkbox]").each(function(){//表格自身数据
				    	if(obj.roleid==$(this).val()){
				    		$(this).attr("checked","checked");
				    	}
					});  
           		}); 
           }   
      });
    }
</script>
<body class="gray-bg">
	<div class="modal-header">
     	<button type="button" class="close" data-dismiss="modal">
			<span aria-hidden="true">×</span><span class="sr-only">Close</span>
		</button>
		<h5 class="modal-title">
			<i class="fa fa-exclamation-circle"></i> 选择角色
		</h5>
   	</div>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="display: block;">
						<div class="form-group"></div>
						<div class="form-group">
							<div class="col-sm-10 col-sm-offset-1 table-responsive">
								<table  class="table table-bordered table-hover" id="roleable">
									<tr>
									  <th style="width: 35px;"></th>
									  <th style="width: 150px;">角色名称</th>
									  <th>描述</th>
									</tr>
									<c:forEach items="${sysRoleList }" var="sysRole" varStatus="s">
								    	<tr>
										  <td><input type="checkbox" name="id" value="${sysRole.id }"/></td>
										  <td>${sysRole.rolename }</td>
										  <td>${sysRole.remark }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<div class="col-sm-1"></div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-3 col-sm-offset-9">
								<button class="btn btn-primary" id="posrolesubmit">确定</button>
								<input type="button" value="取消" id="posroleback"  class="btn btn-white"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
