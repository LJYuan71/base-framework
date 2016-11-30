package com.platform.permissions.sysposrole.model;

import java.io.Serializable;

public class SysPosRole implements Serializable {

	// id
	private Long  id;
	// 岗位id
	private Long  posid;
	// 角色id
	private Long  roleid;
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 id
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setPosid(Long posid) 
	{
		this.posid = posid;
	}
	/**
	 * 返回 岗位id
	 * @return
	 */
	public Long getPosid() 
	{
		return this.posid;
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
}