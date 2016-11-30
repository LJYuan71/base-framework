package com.platform.permissions.sysposrole.service;

import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.platform.common.utils.GenidUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysposrole.dao.SysPosRoleDao;
import com.platform.permissions.sysposrole.model.SysPosRole;
import com.platform.permissions.sysuserrole.model.SysUserRole;
@Service
public class SysPosRoleService {
	@Resource
	private SysPosRoleDao sysPosRoleDao;
	/**
	 * 根据posid查询岗位角色关联数据
	 */
	public List<SysPosRole> getByPosid(long posid){
		return sysPosRoleDao.getByPosid(posid);
	}
	/**
	 * 向关联表中添加岗位-角色数据
	 */
	public void addPosRole(long posid,Long[] roleids){
		List<SysPosRole> posRoleList = sysPosRoleDao.getByPosid(posid);
		//删除原表数据
		for (SysPosRole sysPosRole : posRoleList) {
			sysPosRoleDao.delById(sysPosRole.getId());
		}
		for(long roleid:roleids){
			SysPosRole sysPosRole = new SysPosRole();
			sysPosRole.setId(GenidUtil.genId());
			sysPosRole.setPosid(posid);
			sysPosRole.setRoleid(roleid);
			sysPosRoleDao.add(sysPosRole);
		}
	}
	/**
	 * 添加
	 */
	public boolean add(SysPosRole obj) {
		return sysPosRoleDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysPosRole obj) {
		return sysPosRoleDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysPosRole getById(Long id) {
		return (SysPosRole) sysPosRoleDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysPosRoleDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysPosRole> queryAll(QueryFilter queryFilter) {
		return sysPosRoleDao.queryAll(queryFilter);
	}
}