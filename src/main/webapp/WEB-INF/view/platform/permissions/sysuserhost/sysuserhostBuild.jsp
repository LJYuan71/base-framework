<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>建立主机</title>
<script>
   	$(function(){
   		//$("#sysUserHostForm").Validform();
		$("#next").click(function(){
			var param ={
				hostinfoid:$('input:radio:checked').attr("id"),
				hostinfotype:$('input:radio:checked').val(),
				name:$("#name").val(),
				remark:$("#remark").val()
			};
			$.ajax({  
		     type:'post',      
		     url:'${ctx}/platform/permissions/sysuserhost/saveUserHost.do',  
		     data:param,  
		     dataType:'json', 
		     async:false,
		     success:function(data){
		    	if(data=="success"){
		    		window.location.href="${ctx}/platform/permissions/sysuserhost/next.do";
		    	}else{
		    		Modal.alert(
    			  	{
    			    	msg: '建立主机失败,请联系管理员！'
    			  	});
		    	}
		     }
		    });  
			
		});
		
		
   	})
</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-10">
			<div class="ibox float-e-margins">
				<div class="ibox-content" style="display: block;">
					<c:forEach items="${sysHostInfoList }" var="hostinfo" varStatus="status">
					<div class="col-sm-12" style="height: 30px"></div>
					<div class="col-sm-12" >
						<div class="col-sm-2" style="height: 63px">
							<label class="col-sm-text  control-label" style="line-height: 63px">${status.count}、</label>
						</div>
						<div class="col-sm-4" style="border:2px #ccc solid;margin-left: 10px;">
							<h4>${hostinfo.type}:(适合
							<c:if test="${hostinfo.minusers!=null && hostinfo.maxusers!=null}">${hostinfo.minusers}人~${hostinfo.maxusers}人</c:if>
							<c:if test="${hostinfo.maxusers!=null && hostinfo.minusers==null}">${hostinfo.maxusers}人以下</c:if>
							<c:if test="${hostinfo.maxusers==null && hostinfo.minusers!=null}">${hostinfo.minusers}人以上</c:if>
							)
							</h4>
							<span>
							CPU:${hostinfo.cpu}核  &nbsp; &nbsp;内存:${hostinfo.memory}G &nbsp; &nbsp;硬盘:${hostinfo.harddisk}G
							</span>
						</div>
                    	<div class="col-sm-2" style="height: 48px;text-align:center;margin-top:15px">
							<input type="radio" id="${hostinfo.id}" name="hostinfoid" value="${hostinfo.type} "/><label for="${hostinfo.id}">选择</label>
						</div>	
						</div>								
					</c:forEach>
					<div class="col-sm-12" style="height: 30px"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label">名称:</label>
						<div class="col-sm-4">
							<input class="form-control" type="text" id="name"  name="name" >
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">备注:</label>
						<div class="col-sm-4">
							<input class="form-control" type="text" id="remark" name="remark">
						</div>
					</div>
				</div>
				<div class="col-sm-12" style="height: 30px"></div>
				<div class="form-group">
					<div class="col-sm-12">
						<button id="next" class="btn pull-right" disabled>下一步</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('input').iCheck({ 
			  labelHover : false, 
			  cursor : true, 
			  checkboxClass : 'icheckbox_square-green', 
			  radioClass : 'iradio_square-green', 
			  increaseArea : '20%' 
			})
	$('input').on('ifChecked', function(event){
		$("#next").removeAttr("disabled").addClass("btn-success");
		});
	</script>
</body>
</html>
