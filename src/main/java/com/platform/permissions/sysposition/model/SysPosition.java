package com.platform.permissions.sysposition.model;

import java.io.Serializable;

public class SysPosition implements Serializable {

	// id
	private Long  id;
	// 岗位名称
	private String  posname;
	// 岗位描述
	private String  posdesc;
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
	public void setPosname(String posname) 
	{
		this.posname = posname;
	}
	/**
	 * 返回 岗位名称
	 * @return
	 */
	public String getPosname() 
	{
		return this.posname;
	}
	public void setPosdesc(String posdesc) 
	{
		this.posdesc = posdesc;
	}
	/**
	 * 返回 岗位描述
	 * @return
	 */
	public String getPosdesc() 
	{
		return this.posdesc;
	}
}