package com.platform.permissions.sysuserrole.service;

import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.platform.common.utils.GenidUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysuserpos.model.SysUserPos;
import com.platform.permissions.sysuserrole.dao.SysUserRoleDao;
import com.platform.permissions.sysuserrole.model.SysUserRole;
@Service
public class SysUserRoleService {
	@Resource
	private SysUserRoleDao sysUserRoleDao;
	/**
	 * 根据userid查询用户-岗位关联数据
	 */
	public List<SysUserRole> getByUserid(long userid){
		return sysUserRoleDao.getByUserid(userid);
	}
	/**
	 * 向关联表中添加用户-角色数据
	 */
	public void addUserRole(long userid,Long[] roleids){
		List<SysUserRole> userRoleList = sysUserRoleDao.getByUserid(userid);
		//删除原表数据
		for (SysUserRole sysUserRole : userRoleList) {
			sysUserRoleDao.delById(sysUserRole.getId());
		}
		for(long roleid:roleids){
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setId(GenidUtil.genId());
			sysUserRole.setUserid(userid);
			sysUserRole.setRoleid(roleid);
			sysUserRoleDao.add(sysUserRole);
		}
	}
	/**
	 * 添加
	 */
	public boolean add(SysUserRole obj) {
		return sysUserRoleDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysUserRole obj) {
		return sysUserRoleDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysUserRole getById(Long id) {
		return (SysUserRole) sysUserRoleDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysUserRoleDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysUserRole> queryAll(QueryFilter queryFilter) {
		return sysUserRoleDao.queryAll(queryFilter);
	}
	
	/**
	 * 根据用户id删除
	 */	
	public boolean delByUserId(Long userid) {
		return sysUserRoleDao.delByUserId(userid);
	}
}