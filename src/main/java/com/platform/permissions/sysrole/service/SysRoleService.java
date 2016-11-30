package com.platform.permissions.sysrole.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.platform.common.utils.GenidUtil;
import com.platform.common.utils.RequestUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysposition.model.SysPosition;
import com.platform.permissions.sysres.dao.SysResDao;
import com.platform.permissions.sysres.model.SysRes;
import com.platform.permissions.sysrole.dao.SysRoleDao;
import com.platform.permissions.sysrole.model.SysRole;
import com.platform.permissions.sysroleres.dao.SysRoleResDao;
import com.platform.permissions.sysroleres.model.SysRoleRes;
import com.platform.permissions.sysuserpos.model.SysUserPos;
import com.platform.permissions.sysuserrole.dao.SysUserRoleDao;
import com.platform.permissions.sysuserrole.model.SysUserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class SysRoleService {
	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysRoleResDao sysRoleResDao;
	@Resource
	private SysUserRoleDao sysUserRoleDao;
	@Resource
	private SysResDao sysResDao;
	/**
	 * 根据userid查询用户rolelist  并查询角色下具有的资源
	 */
	public List<SysRole> queryRoleListByUserid(Long userid){
		List<SysRole> sysRoleList=new ArrayList<SysRole>();
		List<SysUserRole> sysUserRoles=sysUserRoleDao.getByUserid(userid);
		for (SysUserRole sysUserRole : sysUserRoles) {
			SysRole sysRole=sysRoleDao.getById(sysUserRole.getRoleid());
			if(sysRole!=null){
				List<SysRoleRes> sysRoleRess=sysRoleResDao.getByRoleId(sysRole.getId());
				List<SysRes> sysRess=new ArrayList<SysRes>();
				for (SysRoleRes sysRoleRes : sysRoleRess) {
					SysRes sysRes=sysResDao.getById(sysRoleRes.getResid());
					if(sysRes!=null){
						sysRess.add(sysRes);
					}
				}
				sysRole.setSysRess(sysRess);
				sysRoleList.add(sysRole);
			}
		}
		return sysRoleList;
	}
	/**
	 * 添加
	 */
	public boolean add(SysRole obj) {
		return sysRoleDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysRole obj) {
		return sysRoleDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysRole getById(Long id) {
		return (SysRole) sysRoleDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysRoleDao.delById(id);
	}
	/**
	 * 根据ids 角色ids删除
	 */	
	public void delByIds(Long[] ids) {
		for (Long id : ids) {
			SysRole sysRole = sysRoleDao.getById(id);
			if (sysRole != null) {
				sysRoleResDao.delByRoleId(id);
				sysRoleDao.delById(id);
			}
		}
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysRole> queryAll(QueryFilter queryFilter) {
		return sysRoleDao.queryAll(queryFilter);
	}
	/**
	 * 不分页查询 
	 * 根据用户id获取用户拥有的角色信息
	 */
	public List<SysRole> queryUsersAllRoleByUserid(Map<String, Object> filter){
		return sysRoleDao.queryUsersAllRoleByUserid(filter);
	}
}