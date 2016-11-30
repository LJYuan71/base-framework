package com.platform.log.syslog.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.platform.common.utils.GenidUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.log.syslog.dao.SysLogDao;
import com.platform.log.syslog.model.SysLog;
import com.platform.permissions.sysuser.model.SysUser;
@Service
public class SysLogService {
	@Resource
	private SysLogDao sysLogDao;

	
	
	//日志
	public void saveLog(HttpServletRequest request,String modelname,String description,long state) throws Exception{
		
		SysLog log = new SysLog();
		SysUser user = (SysUser) request.getSession().getAttribute("currentUser");
		log.setId(GenidUtil.genId());
		log.setUserid(user.getId());
		log.setUsername(user.getFullname());
		/*SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now= sdf.format(new Date());
		Date date = sdf.parse(now);*/
		log.setCreatetime(new Timestamp(System.currentTimeMillis()));
		log.setModelname(modelname);
		log.setDescription(description);
		log.setState(state);
		sysLogDao.add(log);
		
	}
	
	
	
	
	
	
	/**
	 * 添加
	 */
	public boolean add(SysLog obj) {
		return sysLogDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysLog obj) {
		return sysLogDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysLog getById(Long id) {
		return (SysLog) sysLogDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysLogDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysLog> queryAll(QueryFilter queryFilter) {
		return sysLogDao.queryAll(queryFilter);
	}
	
	
}