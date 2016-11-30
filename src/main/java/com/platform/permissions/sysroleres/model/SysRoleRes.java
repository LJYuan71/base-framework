package com.platform.permissions.sysroleres.model;

import java.io.Serializable;
import java.util.List;

public class SysRoleRes implements Serializable {
    
	// Id
	private Long  id;
	// 角色id
	private Long  roleid;
	// 资源id
	private Long  resid;
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
	 * 返回 角色id
	 * @return
	 */
	public Long getRoleid() 
	{
		return this.roleid;
	}
	public void setResid(Long resid) 
	{
		this.resid = resid;
	}
	/**
	 * 返回 资源id
	 * @return
	 */
	public Long getResid() 
	{
		return this.resid;
	}
}