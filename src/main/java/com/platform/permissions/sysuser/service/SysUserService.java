package com.platform.permissions.sysuser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.platform.common.base.model.ValidateRetuen;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysorg.dao.SysOrgDao;
import com.platform.permissions.sysorg.service.SysOrgService;
import com.platform.permissions.sysposition.dao.SysPositionDao;
import com.platform.permissions.sysposition.service.SysPositionService;
import com.platform.permissions.sysrole.dao.SysRoleDao;
import com.platform.permissions.sysrole.service.SysRoleService;
import com.platform.permissions.sysuser.dao.SysUserDao;
import com.platform.permissions.sysuser.model.SysUser;
@Service
public class SysUserService {
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysPositionService sysPositionService;
	
	/**
	 *	判断用户帐号是否已存在
	 */
	public ValidateRetuen checkAccountRepeat(Long id,String account){
		ValidateRetuen validateRetuen=new ValidateRetuen();
		List<SysUser> sysUsers = sysUserDao.checkAccount(id, account);
		if(sysUsers!=null&&sysUsers.size()==0){
			validateRetuen.setInfo("验证通过！");
			validateRetuen.setStatus("y");
		}else {
			validateRetuen.setInfo("该用户名已存在！");
			validateRetuen.setStatus("n");
		}
		return validateRetuen;
	}
	/**
	 *	判断用户身份证号码是否已存在
	 */
	public ValidateRetuen checkIdcardRepeat(Long id,String idcard){
		ValidateRetuen validateRetuen=new ValidateRetuen();
		List<SysUser> sysUsers = sysUserDao.checkIdcard(id, idcard);
		if(sysUsers!=null&&sysUsers.size()==0){
			validateRetuen.setInfo("验证通过！");
			validateRetuen.setStatus("y");
		}else {
			validateRetuen.setInfo("该身份证号已存在！");
			validateRetuen.setStatus("n");
		}
		return validateRetuen;
	}
	/**
	 *逻辑删除
	 */
	public void del(Long id){
		SysUser sysUser = sysUserDao.getById(id);
		sysUser.setStatus(2l);
		sysUserDao.update(sysUser);
	}
	
	
	/**
	 * 根据帐号获取用户
	 */
	public SysUser getByAccount(String account){
		return sysUserDao.getByAccount(account);
	}
	/**
	 * 根据帐号获取用户、包含用户角色/岗位/部门
	 */
	public SysUser getUserPermissionByAccount(String account){
		SysUser sysUser=sysUserDao.getByAccount(account);
		if(sysUser!=null){
			sysUser.setOrgs(sysOrgService.queryOrgListByUserid(sysUser.getId()));
			sysUser.setPositions(sysPositionService.queryPositionListByUserid(sysUser.getId()));
			sysUser.setRoles(sysRoleService.queryRoleListByUserid(sysUser.getId()));
		}
		return sysUser;
	}
	/**
	 * 添加
	 */
	public boolean add(SysUser obj) {
		return sysUserDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysUser obj) {
		return sysUserDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysUser getById(Long id) {
		return (SysUser) sysUserDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysUserDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysUser> queryAll(QueryFilter queryFilter) {
		return sysUserDao.queryAll(queryFilter);
	}
}