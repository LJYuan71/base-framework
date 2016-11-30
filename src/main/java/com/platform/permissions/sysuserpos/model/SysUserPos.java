package com.platform.permissions.sysuserpos.model;

import java.io.Serializable;

public class SysUserPos implements Serializable {

	// ID
	private Long  id;
	// 岗位id
	private Long  posid;
	// 用户ID
	private Long  userid;
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 ID
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