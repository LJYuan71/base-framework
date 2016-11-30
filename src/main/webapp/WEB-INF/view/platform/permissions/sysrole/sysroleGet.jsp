<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
   	$(function(){
		$("#back").click(function(){
			$('#editPage').modal('hide');
		});
   	});
</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="display: block;">
						<form class="form-horizontal" >
						<div class="form-group"></div>
							<div class="form-group">
								<label class="col-sm-4 control-label">角色名:</label>
								<div class="col-sm-8 form-control-static">
									${sysRole.rolename }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">角色别名:</label>
								<div class="col-sm-8 form-control-static">
									${sysRole.alias }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">备注:</label>
								<div class="col-sm-8 form-control-static">
									${sysRole.remark }
								</div>
							</div>
							<%-- <div class="form-group">
								<label class="col-sm-4 control-label">允许删除</label>
								<div class="col-sm-8 form-control-static">
									${sysRole.allowdel }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">允许编辑</label>
								<div class="col-sm-8 form-control-static">
									${sysRole.allowedit }
								</div>
							</div> --%>
							<div class="form-group">
								<label class="col-sm-4 control-label">是否启用:</label>
								<div class="col-sm-8 form-control-static">
								    <c:if test="${sysRole.enabled==0 }">禁止</c:if>
									<c:if test="${sysRole.enabled==1 }">启用</c:if>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">时间:</label>
								<div class="col-sm-8 form-control-static">
									<fmt:formatDate value='${sysRole.createdate }' pattern='yyyy-MM-dd'/>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-5">
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
