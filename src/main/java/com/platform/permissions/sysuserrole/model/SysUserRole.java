package com.platform.permissions.sysuserrole.model;

import java.io.Serializable;
import java.util.List;

import com.platform.permissions.sysroleres.model.SysRoleRes;

public class SysUserRole implements Serializable {

	// Id
	private Long  id;
	// 角色ID
	private Long  roleid;
	// 用户ID
	private Long  userid;
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 Id
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setRoleid(Long roleid) 
	{
		this.roleid = roleid;
	}
	/**
	 * 返回 角色ID
	 * @return
	 */
	public Long getRoleid() 
	{
		return this.roleid;
	}
	public void setUserid(Long userid) 
	{
		this.userid = userid;
	}
	/**
	 * 返回 用户ID
	 * @return
	 */
	public Long getUserid() 
	{
		return this.userid;
	}
}