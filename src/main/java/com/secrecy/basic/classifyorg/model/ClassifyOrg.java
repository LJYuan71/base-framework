package com.secrecy.basic.classifyorg.model;

import java.io.Serializable;

public class ClassifyOrg implements Serializable {

	// id
	private Long  id;
	// 分类id
	private Long  classifyid;
	// 部门id
	private Long  orgid;
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
	public void setClassifyid(Long classifyid) 
	{
		this.classifyid = classifyid;
	}
	/**
	 * 返回 分类id
	 * @return
	 */
	public Long getClassifyid() 
	{
		return this.classifyid;
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
}