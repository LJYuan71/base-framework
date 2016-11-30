package com.platform.permissions.syshostinfo.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.syshostinfo.dao.SysHostInfoDao;
import com.platform.permissions.syshostinfo.model.SysHostInfo;
@Service
public class SysHostInfoService {
	@Resource
	private SysHostInfoDao sysHostInfoDao;
	/**
	 * 添加
	 */
	public boolean add(SysHostInfo obj) {
		return sysHostInfoDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysHostInfo obj) {
		return sysHostInfoDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysHostInfo getById(Long id) {
		return (SysHostInfo) sysHostInfoDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysHostInfoDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysHostInfo> queryAll(QueryFilter queryFilter) {
		return sysHostInfoDao.queryAll(queryFilter);
	}
}