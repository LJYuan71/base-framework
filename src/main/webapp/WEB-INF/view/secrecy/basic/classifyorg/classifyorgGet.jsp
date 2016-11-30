<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>分类信息/部门关联关系明细页面</title>
<script>
   	$(function(){
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否返回列表页？"}).on( function (e) {
				  	if(e){
				  		//返回
				  		window.location.href="${ctx}/secrecy/basic/classifyorg/queryAll.do";
				  	}
			});
		});
   	})
</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="display: block;">
						<form class="form-horizontal" >
							<div class="form-group">
								<label class="col-sm-2 control-label">id</label>
								<div class="col-sm-4 form-control-static">
									${classifyOrg.id }
								</div>
								<label class="col-sm-2 control-label">分类id</label>
								<div class="col-sm-4 form-control-static">
									${classifyOrg.classifyid }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">部门id</label>
								<div class="col-sm-4 form-control-static">
									${classifyOrg.orgid }
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
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
