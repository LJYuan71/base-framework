<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- confirm、alert弹框js和页面 -->
<script type="text/javascript">
	$(function () { 
		$("#positiontable tr").click(function (){
			if($(this).find("input[type=checkbox]").attr("checked")=="checked"){
				$(this).find("input[type=checkbox]").removeAttr("checked");
			}else if($(this).find("input[type=checkbox]").attr("checked")==undefined){
				$(this).find("input[type=checkbox]").attr("checked","checked");
				$(this).find("input[type=checkbox]").prop("checked","checked");
			}
        });
	   	$("#add").click(function(){
			dialogPopupModal.editPagePopup({
				url: "${ctx}/platform/permissions/sysposition/editByOrg.do?orgid=${orgid}",
				editPopupLayout:"sm"
			})
	   	});
	   	$("#edit").click(function(){
			var selectIds="";
			$("#positiontable tr td input[type=checkbox]").each(function(){
			    if(this.checked){
			    	selectIds+=$(this).val()+",";
			    }
			});  
			selectIds = selectIds.substring(0, selectIds.length-1);
			if(selectIds==""){
				Modal.alert({msg: '请选择记录进行编辑'});
			}else if(selectIds.indexOf(",") > 0){
				Modal.alert({msg: '请只选择一条记录进行编辑'});
			}else{
				dialogPopupModal.editPagePopup({
					url: "${ctx}/platform/permissions/sysposition/editByOrg.do?orgid=${orgid}&&id="+selectIds,
					editPopupLayout:"sm"
				})
			}
	   	});
		$("#del").click(function(){
			var selectIds="";
			$("#positiontable tr td input[type=checkbox]").each(function(){
			    if(this.checked){
			    	selectIds+=$(this).val()+",";
			    }
			});  
			selectIds = selectIds.substring(0, selectIds.length-1);
			if(selectIds==""){
				Modal.alert({msg: '请选择记录进行删除'});
			}else{
	  			//继续删除
	  			$.ajax({
					type : "post",
					url : '${ctx}/platform/permissions/sysposition/delByOrg.do?orgid=${orgid}&&ids='+selectIds,
					success : function(res) {
						var json = eval("(" + res + ")");
						if(json=="success"){
							Modal.timealert({msg: '删除成功！',time: '2000'}).on( function () {
								var selectIdss=selectIds.split(",");
								$.each(selectIdss,function(index,obj){
									$("#tr"+obj).remove();
								});
							});
						}else{
							Modal.timealert({msg: '删除失败，请联系管理员！',time: '2000'}).on( function () {
							});
						}
					}
				}); 
			}
	   	});
	   	$("#back").click(function(){
			$('#editPage').modal('hide');
		});
	});
	function relationRole(positionid){
		dialogPopupModal.editPagePopup({
			url: "${ctx}/platform/permissions/sysposition/role.do?positionid="+positionid,
			editPopupLayout:"md"
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
								<button type="button" class="btn btn-success" id="add" >新增岗位</button>
								<button type="button" class="btn btn-success" id="edit" >编辑岗位</button>
								<button type="button" class="btn btn-success" id="del" >删除岗位</button>
								<table  class="table table-bordered table-hover" id="positiontable">
									<tr>
									  <th style="width: 35px;"></th>
									  <th style="width: 150px;">岗位名称</th>
									  <th>描述</th>
									  <th>操作</th>
									</tr>
									<c:forEach items="${positionList }" var="position" varStatus="s">
								    	<tr id="tr${position.id }">
										  <td><input type="checkbox" name="id" value="${position.id }"/></td>
										  <td id="posname${position.id }">${position.posname }</td>
										  <td id="posdesc${position.id }">${position.posdesc }</td>
										  <td width="100px;"><a href="javaScript:void(0);" onclick="relationRole(${position.id });">关联角色</a></td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<div class="col-sm-1"></div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-3 col-sm-offset-9">
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
