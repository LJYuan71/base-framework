package com.platform.permissions.sysuserorg.model;

import java.io.Serializable;

public class SysUserOrg implements Serializable {

	// id
	private Long  id;
	// 组织id
	private Long  orgid;
	// 用户id
	private Long  userid;
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
	 * 返回 组织id
	 * @return
	 */
	public Long getOrgid() 
	{
		return this.orgid;
	}
	public void setUserid(Long userid) 
	{
		this.userid = userid;
	}
	/**
	 * 返回 用户id
	 * @return
	 */
	public Long getUserid() 
	{
		return this.userid;
	}
}