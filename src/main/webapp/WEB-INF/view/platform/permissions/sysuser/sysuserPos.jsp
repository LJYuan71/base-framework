<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- confirm、alert弹框js和页面 -->
<script type="text/javascript">
	$(function(){
	    setSelectNode();//设置某用户已有的岗位显示
        $("#postable tr").click(function (){
			if($(this).find("input[type=checkbox]").attr("checked")=="checked"){
				$(this).find("input[type=checkbox]").removeAttr("checked");
			}else if($(this).find("input[type=checkbox]").attr("checked")==undefined){
				$(this).find("input[type=checkbox]").attr("checked","checked");
				$(this).find("input[type=checkbox]").prop("checked","checked");
			}
        });
		$("#back").click(function(){
			$('#editPage').modal('hide');
		});
		$("#submit").click(function(){
			var selectIds="";
			$("#postable tr td input[type=checkbox]").each(function(){
			    if(this.checked){
			    	selectIds+=$(this).val()+",";
			    }
			});  
			selectIds = selectIds.substring(0, selectIds.length-1);
		    $.ajax({
		        type:"POST",
		        async:false,
		        url: "${ctx}/platform/permissions/sysuserpos/saveUserPos.do?userid="+'${userid}'+"&ids="+selectIds,    
		        success:function(data){ //请求成功后处理函数。 
	           		var ret=eval("("+data+")");
		         	if(ret=='success'){
		         		Modal.alert({msg: '设置成功'});
		         	}else if(ret=='fail'){
		         		Modal.alert({msg: '设置失败，请联系管理员'});
		         	}
	           } 
	   		});
	    	$('#editPage').modal('hide');
		});
	});
	function setSelectNode(){
    	 $.ajax({   
           type: 'POST',   
           async:false,
           url: "${ctx}/platform/permissions/sysuserpos/getByUserId.do?userid="+'${userid}',//请求的action路径    
           success:function(data){ //请求成功后处理函数。 
           		var ret=eval("("+data+")");
           		$.each(ret,function(index,obj){//后台返回的数据
		            $("#postable tr td input[type=checkbox]").each(function(){//表格自身数据
				    	if(obj.posid==$(this).val()){
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
			<i class="fa fa-exclamation-circle"></i> 选择岗位
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
								<table  class="table table-bordered table-hover" id="postable">
									<tr>
									  <th style="width: 35px;"></th>
									  <th style="width: 150px;">岗位名称</th>
									  <th>描述</th>
									</tr>
									<c:forEach items="${sysPositionList }" var="sysPosition" varStatus="s">
								    	<tr>
										  <td><input type="checkbox" name="id" value="${sysPosition.id }" class="sysPositionId"/></td>
										  <td>${sysPosition.posname }</td>
										  <td>${sysPosition.posdesc }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<div class="col-sm-1"></div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-3 col-sm-offset-9">
								<button class="btn btn-primary" id="submit">确定</button>
								<input type="button" value="取消" id="back"  class="btn btn-white"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
