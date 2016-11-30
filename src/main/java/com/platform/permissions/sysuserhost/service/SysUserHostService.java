package com.platform.permissions.sysuserhost.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysuserhost.dao.SysUserHostDao;
import com.platform.permissions.sysuserhost.model.SysUserHost;
@Service
public class SysUserHostService {
	@Resource
	private SysUserHostDao sysUserHostDao;
	/**
	 * 添加
	 */
	public boolean add(SysUserHost obj) {
		return sysUserHostDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysUserHost obj) {
		return sysUserHostDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysUserHost getById(Long id) {
		return (SysUserHost) sysUserHostDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysUserHostDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysUserHost> queryAll(QueryFilter queryFilter) {
		return sysUserHostDao.queryAll(queryFilter);
	}
	/**
	 * 根据用户id删除
	 */	
	public boolean delByUserId(Long userid) {
		return sysUserHostDao.delByUserId(userid);
	}
	
}