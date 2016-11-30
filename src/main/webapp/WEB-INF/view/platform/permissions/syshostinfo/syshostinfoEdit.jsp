<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>sys_host_info编辑页面</title>
<script>
   	$(function(){
   		$("#sysHostInfoForm").Validform();
   		var options = {
		   beforeSubmit: showRequest,  //提交前的回调函数
		   success: showResponse,      //提交后的回调函数
		   timeout: 3000               //限制请求的时间，当请求大于3秒后，跳出请求
		}
		function showRequest(formData, jqForm, options){
		   	return true;  //只要不返回false，表单都会提交,在这里可以对表单元素进行验证
		};
		function showResponse(responseText, statusText){
			var json = eval("(" + responseText + ")");
			if (json.result == 1) {
				//成功
				// 如需增加回调函数，后面直接加 .on( function(e){} );
				// 点击“确定” e: true
				// 点击“取消” e: false
				Modal.timealert({msg: '编辑成功！',time: '2000'}).on( function () {
					//关闭编辑页面
				  	window.location.href="${ctx}/platform/permissions/syshostinfo/queryAll.do";
				});
			}else {
				//失败
				Modal.alert({msg: '提交失败，请联系管理员！'});
			}
		};
		
		$("#submit").click(function(){
			$("#sysHostInfoForm").ajaxForm(options);
		});
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否返回列表页？"}).on( function (e) {
				  	if(e){
				  		//返回
				  		window.location.href="${ctx}/platform/permissions/syshostinfo/queryAll.do";
				  	}
			});
		});
   	})
</script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-10">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="display: block;">
						<form class="form-horizontal" method="post" id="sysHostInfoForm" action="${ctx }/platform/permissions/syshostinfo/save.do">
							<input class="form-control" type="text" style="display: none;"  name="createtime" value="<fmt:formatDate value='${sysHostInfo.createtime }' pattern='yyyy-MM-dd'/>" onclick="time();" readonly="readonly">
							<input class="form-control" type="text" style="display: none;" name="id" value="${sysHostInfo.id }">
							<div class="form-group">
								<!-- 
								<label class="col-sm-2 control-label">主机id:</label>
								<div class="col-sm-4">
								</div>
								 -->
								<label class="col-sm-2 control-label">主机名称:</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="name" value="${sysHostInfo.name }">
								</div>
								<label class="col-sm-2 control-label">规模类型:</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="type" value="${sysHostInfo.type }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">内存:</label>
								<div class="col-sm-3">
									<input class="form-control" type="number"  name="memory" value="${sysHostInfo.memory }" min="0" datatype="n" nullmsg="内存必填！">
								</div>
								<div class="col-sm-1 form-control-static">
									G
								</div>
								<label class="col-sm-2 control-label">CPU核数:</label>
								<div class="col-sm-3">
									<input class="form-control" type="number"  name="cpu" value="${sysHostInfo.cpu }" min="0" datatype="n"  nullmsg="cpu必填！">
								</div>
								<div class="col-sm-1 form-control-static">
									核
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">硬盘:</label>
								<div class="col-sm-3">
									<input class="form-control" type="number"  name="harddisk" value="${sysHostInfo.harddisk }" min="0" datatype="n"  nullmsg="硬盘必填！">
								</div>
								<div class="col-sm-1 form-control-static">
									G
								</div>
								<label class="col-sm-2 control-label">描述:</label>
								<div class="col-sm-4">
									<input class="form-control" type="text"  name="describes" value="${sysHostInfo.describes }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">最小用户数:</label>
								<div class="col-sm-4">
									<input class="form-control" type="number"  name="minusers" value="${sysHostInfo.minusers }" min="0">
								</div>
								<label class="col-sm-2 control-label">最大用户数:</label>
								<div class="col-sm-4">
									<input class="form-control" type="number"  name="maxusers" value="${sysHostInfo.maxusers }" min="0">
								</div>
							</div>
							<div class="form-group">
								
								<!-- 
								<label class="col-sm-2 control-label">创建时间:</label>
								<div class="col-sm-4">
								</div>
								 -->
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" id="submit">保存内容</button>
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
