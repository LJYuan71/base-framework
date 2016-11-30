<#import "function.ftl" as func>
<#assign pk=func.getPk(model) >
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/include/edit.jsp" %>
<html lang="zh-CN">
<head>
<title>${model.tabComment}明细页面</title>
<script>
   	$(function(){
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
						<form class="form-horizontal" >
							<div class="form-group">
							<#list model.columnList as col>
							<#if 0<col_index  && (col_index % 2)==0>
							</div>
							<div class="form-group">
							</#if>
								<label class="col-sm-2 control-label">${col.comment}:</label>
								<div class="col-sm-4 form-control-static">
									<#if (col.colType=="java.util.Date")>
									<fmt:formatDate value='<#noparse>${</#noparse>${modelname?uncap_first}.${func.convertUnderLine(col.columnName)} }' pattern='yyyy-MM-dd'/>
									<#else>
									<#noparse>${</#noparse>${modelname?uncap_first}.${func.convertUnderLine(col.columnName)} }
									</#if>
								</div>
							</#list>
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
