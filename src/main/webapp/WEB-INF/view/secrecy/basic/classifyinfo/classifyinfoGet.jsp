<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>分类信息明细页面</title>
<script>
   	$(function(){
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否返回列表页？"}).on( function (e) {
				  	if(e){
				  		//返回
				  		window.location.href="${ctx}/secrecy/basic/classifyinfo/queryAll.do";
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
									${classifyInfo.id }
								</div>
								<label class="col-sm-2 control-label">名称</label>
								<div class="col-sm-4 form-control-static">
									${classifyInfo.name }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">父ID</label>
								<div class="col-sm-4 form-control-static">
									${classifyInfo.parentid }
								</div>
								<label class="col-sm-2 control-label">同级排序</label>
								<div class="col-sm-4 form-control-static">
									${classifyInfo.sn }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">PATH</label>
								<div class="col-sm-4 form-control-static">
									${classifyInfo.path }
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
