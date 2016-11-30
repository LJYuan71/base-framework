<#import "function.ftl" as func>
<#assign pk=func.getPk(model) >
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>${model.tabComment}编辑页面</title>
<script>
   	$(function(){
   		$("#${modelname?uncap_first}Form").Validform();
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
				 Modal.timealert({msg: '编辑成功！',time: '2000'}).on( function () {
					//关闭编辑页面
				  	window.location.href="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/queryAll.do";
				});
				 
			}else {
				//失败
				Modal.alert({msg: '提交失败，请联系管理员！'});
			}
		};
		
		$("#submit").click(function(){
			$("#${modelname?uncap_first}Form").ajaxForm(options);
		});
		$("#back").click(function(){
			Modal.confirm(
				{msg: "是否返回列表页？"}).on( function (e) {
				  	if(e){
				  		//返回
				  		window.location.href="<#noparse>${</#noparse>ctx}/${system}/${modular}/${packagename}/queryAll.do";
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
						<form class="form-horizontal" method="post" id="${modelname?uncap_first}Form" action="<#noparse>${</#noparse>ctx }/${system}/${modular}/${packagename}/save.do">
							<div class="form-group">
							<#list model.columnList as col>
							<#if 0<col_index  && (col_index % 2)==0>
							</div>
							<div class="form-group">
							</#if>
								<label class="col-sm-2 control-label">${col.comment}:</label>
								<div class="col-sm-4">
									<#if (col.colType=="java.util.Date")>
									<input class="form-control" type="text"  name="${func.convertUnderLine(col.columnName)}" value="<fmt:formatDate value='<#noparse>${</#noparse>${modelname?uncap_first}.${func.convertUnderLine(col.columnName)} }' pattern='yyyy-MM-dd'/>" onclick="time();" readonly="readonly">
									<#else>
									<input class="form-control" type="text"  name="${func.convertUnderLine(col.columnName)}" value="<#noparse>${</#noparse>${modelname?uncap_first}.${func.convertUnderLine(col.columnName)} }">
									</#if>
								</div>
							</#list>
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
