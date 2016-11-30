package com.platform.permissions.sysroleres.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.platform.common.utils.GenidUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysroleres.dao.SysRoleResDao;
import com.platform.permissions.sysroleres.model.SysRoleRes;
@Service
public class SysRoleResService {
	@Resource
	private SysRoleResDao sysRoleResDao;
	
	/**
	 * 向角色-资源表添加关联数据
	 */
	public boolean saveRoleRes(long roleid,Long[] resids){
		boolean ret = false;
		List<SysRoleRes> sysRoleReslist = new ArrayList<SysRoleRes>();
		sysRoleReslist = sysRoleResDao.getByRoleId(roleid);
		for(SysRoleRes sysRoleRes:sysRoleReslist){
			sysRoleResDao.delById(sysRoleRes.getId());
		}
		for(Long resid:resids){
			SysRoleRes sysRoleRes = new SysRoleRes();
			sysRoleRes.setId(GenidUtil.genId());
			sysRoleRes.setRoleid(roleid);
			sysRoleRes.setResid(resid);
			sysRoleResDao.add(sysRoleRes);
		}
		ret = true;
	    return ret;
	}
	/**
	 * 根据roleid 查询该角色对应的资源
	 */
	public List<SysRoleRes> getListByRoleId(long roleid){
		List<SysRoleRes> sysRoleReslist = sysRoleResDao.getByRoleId(roleid);
		return sysRoleReslist;
	}
	/**
	 * 添加
	 */
	public boolean add(SysRoleRes obj) {
		return sysRoleResDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysRoleRes obj) {
		return sysRoleResDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysRoleRes getById(Long id) {
		return (SysRoleRes) sysRoleResDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysRoleResDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysRoleRes> queryAll(QueryFilter queryFilter) {
		return sysRoleResDao.queryAll(queryFilter);
	}
}