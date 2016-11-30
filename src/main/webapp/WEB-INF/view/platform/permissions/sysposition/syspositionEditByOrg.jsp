<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script>
   	$(function(){
   		$("#sysPositionForm").Validform();
   		var options = {
		   beforeSubmit: showRequest,  //提交前的回调函数
		   success: showResponse,      //提交后的回调函数
		   timeout: 3000               //限制请求的时间，当请求大于3秒后，跳出请求
		};
		function showRequest(formData, jqForm, options){
		   	return true;  //只要不返回false，表单都会提交,在这里可以对表单元素进行验证
		};
		function showResponse(responseText, statusText){
			var json = eval("(" + responseText + ")");
			if (json.result == 1) {
				Modal.timealert({msg: '编辑成功！',time: '2000'}).on( function () {
					//关闭编辑页面
					$('#editPagePopup').modal('hide');
					if (json.message == "添加成功") {
						var appendStr="<tr id='tr${sysPosition.id }'>"+
										  "<td><input type='checkbox' name='id' value='${sysPosition.id }'/></td>"+
										  "<td id='posname${sysPosition.id }'>"+$("#posname").val()+"</td>"+
										  "<td id='posdesc${sysPosition.id }'>"+$("#posdesc").val()+"</td>"+
										"</tr>";
						$("#positiontable").append(appendStr);
					}else if(json.message == "修改成功"){
						$("#posname${sysPosition.id }").empty();
						$("#posdesc${sysPosition.id }").empty();
						$("#posname${sysPosition.id }").html($("#posname").val());
						$("#posdesc${sysPosition.id }").html($("#posdesc").val());
					}
					
				});
			}else {
				//失败
				Modal.alert({msg: '提交失败，请联系管理员！'});
			}
		};
		
		$("#pouupsubmit").click(function(){
			$("#sysPositionForm").ajaxForm(options);
		});
		$("#pouupback").click(function(){
			$('#editPagePopup').modal('hide');
		});
   	});
   
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
						<form class="form-horizontal" method="post" id="sysPositionForm" action="${ctx }/platform/permissions/sysposition/saveByOrg.do">
							<input class="form-control" type="hidden"  name="id" value="${sysPosition.id }">
							<input class="form-control" type="hidden"  name="orgid" value="${orgid }">
							<div class="form-group">
								<label class="col-sm-4 control-label">岗位名称:</label>
								<div class="col-sm-7">
									<input class="form-control" type="text" id="posname"  name="posname" value="${sysPosition.posname }" datatype="*" nullmsg="必填！">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">岗位描述:</label>
								<div class="col-sm-7">
									<input class="form-control" type="text" id="posdesc"  name="posdesc" value="${sysPosition.posdesc }">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-8 col-sm-offset-5">
									<button class="btn btn-primary" id="pouupsubmit">保存内容</button>
									<input type="button" value="取消" id="pouupback"  class="btn btn-white"/>
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
