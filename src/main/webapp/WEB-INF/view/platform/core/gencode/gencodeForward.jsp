<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp"%>
<html lang="zh-CN">
<head>
<title>代码生成页面</title>
<script>
	$(function() {
		$("#gencode").click(function() {
			var createType = $("input[name='createType']:checked").val();
			var tablename = $("#tablename").val();
			var modelname = $("#modelname").val();
			var packagename = $("#packagename").val();
			if (tablename != "" && modelname != ""&& packagename != ""&&createType!=undefined) {
				$.ajax({
					type : "post",
					async : false,
					url : '${ctx}/platform/core/gencode/gencode.do?tablename='+ tablename
							+ '&modelname='+ modelname
							+ '&packagename='+ packagename
							+ '&createType='+createType,
					success : function(res) {
						
					}
				});
			} else {
				Modal.timealert({
					msg : '信息填写不完整，无法进行生成！',
					time : '2000'
				}).on(function() {

				});
			}
		});
		// 所有的单选框、复选框应用icheck的样式      页面需要引用ichek的相关js
		$('input').iCheck({
			checkboxClass : 'icheckbox_square-green',
			radioClass : 'iradio_square-green',
			increaseArea : '20%' // optional
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
						<div class="form-horizontal">
							<div class="form-group"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">表名</label>
								<div class="col-sm-4">
									<input class="form-control" type="text" name="tablename"
										id="tablename">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">实体名</label>
								<div class="col-sm-4">
									<input class="form-control" type="text" name="modelname"
										id="modelname">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">包名</label>
								<div class="col-sm-4">
									<input class="form-control" type="text" name="packagename"
										id="packagename">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">生成代码类型</label>
								<div class="col-sm-4">
									<input tabindex="2" type="radio" id="common" name="createType" value="common">
              						<label for="common">普通</label>
              						<input tabindex="2" type="radio" id="tree" name="createType" value="tree">
              						<label for="tree">树形</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">说明</label>
								<div class="col-sm-4">
									树形结构数据库必含字段(字段名称不能改)：<br>
										id	主键ID	number(18)<br>
										name	名称	varchar2(128)<br>
										parentid	上级id	number(18)<br>
										path	路径	varchar2(128)<br>
										sn	同级排序	number(18)<br>
										不能有parentname
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" id="gencode">代码生成</button>
									<button class="btn btn-white" id="back">取消</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
