<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 分页 -->
<!-- <div class="pull-right pagination"> -->
<center>
	<ul class="pagination">
		 <li>
	    	<a>共<span style="color: #930000">${count}</span>条</a>
	    </li>
	    <li>
			<input id="toGoPage" type="number" placeholder="页码" style="width:70px;height:32px;text-align:center;float:left" value="">
		</li>
		<li>
			<a  onclick="tiaozhuan();" href="#"><span >跳转</span></a>
		</li>
		<pg:first>
			<li>
		    	<a href="<%=pageUrl%>" aria-label="Previous">
		        	<span aria-hidden="true">首页</span>
				</a>
			</li>
	    </pg:first>
	    <pg:prev>
	    	<li>
		    	<a href="<%=pageUrl%>" aria-label="Previous">
		        	<span aria-hidden="true">上一页</span>
				</a>
			</li>
	    </pg:prev>
	    <pg:pages>
	    	<li><a href="<%=pageUrl%>"><%=pageNumber%></a></li>
	    </pg:pages>
	    <pg:next>
	    	<li>
		      <a href="<%=pageUrl%>" aria-label="Next">
		        <span aria-hidden="true">下一页</span>
		      </a>
		    </li>
	    </pg:next>
	    <pg:last>
		    <li>
		      <a href="<%=pageUrl%>" aria-label="Next">
		        <span aria-hidden="true">尾页</span>
		      </a>
		    </li>
	    </pg:last>
	    <li>
	    	<a>共<pg:last><%=pageNumber%></pg:last>页</a>
	    </li>
	</ul>
</center>