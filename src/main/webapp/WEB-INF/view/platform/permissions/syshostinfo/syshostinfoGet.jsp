<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>主机配置明细页面</title>
<script>
   	$(function(){
		$("#back").click(function(){
			window.location.href="${ctx}/platform/permissions/syshostinfo/queryAll.do";
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
								<label class="col-sm-2 control-label">主机id:</label>
								<div class="col-sm-4 form-control-static">
									${sysHostInfo.id }
								</div>
								<label class="col-sm-2 control-label">主机名称:</label>
								<div class="col-sm-4 form-control-static">
									${sysHostInfo.name }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">规模:</label>
								<div class="col-sm-4 form-control-static">
									${sysHostInfo.type }
								</div>
								<label class="col-sm-2 control-label">CPU核数:</label>
								<div class="col-sm-4 form-control-static">
									${sysHostInfo.cpu }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">内存:</label>
								<div class="col-sm-4 form-control-static">
									${sysHostInfo.memory }
								</div>
								<label class="col-sm-2 control-label">硬盘:</label>
								<div class="col-sm-4 form-control-static">
									${sysHostInfo.harddisk }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">最大用户数:</label>
								<div class="col-sm-4 form-control-static">
									${sysHostInfo.maxusers }
								</div>
								<label class="col-sm-2 control-label">最小用户数:</label>
								<div class="col-sm-4 form-control-static">
									${sysHostInfo.minusers }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">描述:</label>
								<div class="col-sm-4 form-control-static">
									${sysHostInfo.describes }
								</div>
								<label class="col-sm-2 control-label">创建时间:</label>
								<div class="col-sm-4 form-control-static">
									<fmt:formatDate value='${sysHostInfo.createtime }' pattern='yyyy-MM-dd'/>
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
