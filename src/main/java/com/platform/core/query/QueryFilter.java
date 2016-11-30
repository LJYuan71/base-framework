package com.platform.core.query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.platform.common.utils.RequestUtil;
import com.platform.core.pager.Pager;

public class QueryFilter {
	private HttpServletRequest request;
	/**
	 * 过滤参数
	 */
	private Map<String, Object> filters = new HashMap<String, Object>();
	/**
	 * 分页参数
	 */
	private Pager pager;
	/**
	 * 
	 */
	private boolean needPage;
	
	public QueryFilter(HttpServletRequest request,boolean needPage) {
		this.request=request;
		this.needPage=needPage;
		try{
			if(needPage)
			{
				Integer offset = request.getParameter("offset") == null ? 0 :Integer.parseInt(request.getParameter("offset"));
				Integer limit = request.getParameter("limit") == null ? 10 :Integer.parseInt(request.getParameter("limit"));
				
				this.pager=new Pager(offset+1,limit+offset);
			}
			Map map = RequestUtil.getQueryMap(request) ;
			filters=map;
		 }catch(Exception ex){
		 }
	}
	public QueryFilter() {
		
	}
	// -------------------------------------------------------------------------------------
	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public boolean isNeedPage() {
		return needPage;
	}
	public void setNeedPage(boolean needPage) {
		this.needPage = needPage;
	}

}
