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
								<%-- <label class="col-sm-2 control-label">id</label>
								<div class="col-sm-4 form-control-static">
									${sysPosition.id }
								</div> --%>
								<label class="col-sm-4 control-label">岗位名称:</label>
								<div class="col-sm-8 form-control-static">
									${sysPosition.posname }
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">岗位描述:</label>
								<div class="col-sm-8 form-control-static">
									${sysPosition.posdesc }
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
