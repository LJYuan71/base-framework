<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>组织机构明细页面</title>
<script>
   	$(function(){
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否返回列表页？"}).on( function (e) {
				  	if(e){
				  		//返回
				  		window.location.href="${ctx}/platform/permissions/sysorg/queryAll.do";
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
								<label class="col-sm-2 control-label">ID:</label>
								<div class="col-sm-4 form-control-static">
									${sysOrg.id }
								</div>
								<label class="col-sm-2 control-label">名称:</label>
								<div class="col-sm-4 form-control-static">
									${sysOrg.name }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">描述:</label>
								<div class="col-sm-4 form-control-static">
									${sysOrg.orgdesc }
								</div>
								<label class="col-sm-2 control-label">上级:</label>
								<div class="col-sm-4 form-control-static">
									${sysOrg.parentid }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">路径:</label>
								<div class="col-sm-4 form-control-static">
									${sysOrg.path }
								</div>
								<label class="col-sm-2 control-label">同级排序:</label>
								<div class="col-sm-4 form-control-static">
									${sysOrg.sn }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">子系统ID:</label>
								<div class="col-sm-4 form-control-static">
									${sysOrg.systemid }
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
