package com.platform.common.base.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.platform.common.utils.StringUtil;
import com.platform.core.query.ResultMessage;

public class CommonController {
	/**
	 * 通过Request的URL对应转成对应的JSP文件展示
	 */
	public ModelAndView getAutoView(HttpServletRequest request) throws Exception {
		//HttpServletRequest request = RequestUtil.getHttpServletRequest();
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();

		requestURI = requestURI.replace(".do", "");
		int cxtIndex = requestURI.indexOf(contextPath);
		if (cxtIndex != -1) {
			requestURI = requestURI.substring(cxtIndex + contextPath.length());
		}
		String[] paths = requestURI.split("[/]");
		if (paths != null && paths.length == 5) {
			String jspPath = "/" + paths[1] + "/" + paths[2] + "/" + paths[3]
					+ "/" + paths[3]+StringUtil.makeFirstLetterUpperCase(paths[4]);
			return new ModelAndView(jspPath);
		}else {
			return null;
		}
	}
	 /**
		 * 返回出错或成功的信息。
		 * @param writer
		 * @param resultMsg
		 * @param successFail
		 */
		protected void writeResultMessage(PrintWriter writer,String resultMsg,int successFail)
		{
			ResultMessage resultObj=new ResultMessage(successFail,resultMsg);
			writer.print(resultObj);
		}
		/**
		 * 返回出错或成功的信息。
		 * @param writer
		 * @param resultMessage
		 */
		protected void writeResultMessage(PrintWriter writer,ResultMessage resultMessage)
		{
			writer.print(resultMessage);
		}
}
