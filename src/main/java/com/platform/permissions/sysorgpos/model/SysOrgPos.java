package com.platform.permissions.sysorgpos.model;

import java.io.Serializable;

public class SysOrgPos implements Serializable {

	// id
	private Long  id;
	// 部门id
	private Long  orgid;
	// 岗位id
	private Long  posid;
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
	public void setOrgid(Long orgid) 
	{
		this.orgid = orgid;
	}
	/**
	 * 返回 部门id
	 * @return
	 */
	public Long getOrgid() 
	{
		return this.orgid;
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
}