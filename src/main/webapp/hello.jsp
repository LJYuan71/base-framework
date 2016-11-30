<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>cluster test - share session</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
</head>
<body>
	<%
		String sessionid = session.getId();
		System.out.println("当前sessionid = " + sessionid);
		// 如果有新的 Session 属性设置
		String dataName = request.getParameter("dataName");
		if (dataName != null && dataName.length() > 0) {
			String dataValue = request.getParameter("dataValue");
			session.setAttribute(dataName, dataValue);
		}
		out.println("当前sessionid = " + sessionid);
		out.println("<br><b>Session 列表</b><br>");
		System.out.println("============================");
		Enumeration e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = session.getAttribute(name).toString();
			out.println(name + " = " + value + "<br>");
			System.out.println(name + " = " + value);
		}
	%>
	<form action="hello.jsp" id="form_add" method="post">
		Key：<input id="dataName" name="dataName" type="text" /> Value：<input
			id="dataValue" name="dataValue" type="text" /> <input id="subBtn"
			name="subBtn" type="submit" value="提交" />
	</form>
</body>
</html>