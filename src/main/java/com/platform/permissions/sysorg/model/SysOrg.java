package com.platform.permissions.sysorg.model;

import java.io.Serializable;

public class SysOrg implements Serializable {

	// ID
	private Long  id;
	// 名称
	private String  name;
	// 描述
	private String  orgdesc;
	// 上级
	private Long  parentid;
	// 路径
	private String  path;
	// 同级排序
	private Long  sn;
	// 子系统ID
	private Long  systemid;
	// 不存数据库  父节点名称
	private String parentname;
	
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
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setOrgdesc(String orgdesc) 
	{
		this.orgdesc = orgdesc;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getOrgdesc() 
	{
		return this.orgdesc;
	}
	public void setParentid(Long parentid) 
	{
		this.parentid = parentid;
	}
	/**
	 * 返回 上级
	 * @return
	 */
	public Long getParentid() 
	{
		return this.parentid;
	}
	public void setPath(String path) 
	{
		this.path = path;
	}
	/**
	 * 返回 路径
	 * @return
	 */
	public String getPath() 
	{
		return this.path;
	}
	public void setSn(Long sn) 
	{
		this.sn = sn;
	}
	/**
	 * 返回 同级排序
	 * @return
	 */
	public Long getSn() 
	{
		return this.sn;
	}
	public void setSystemid(Long systemid) 
	{
		this.systemid = systemid;
	}
	/**
	 * 返回 子系统ID
	 * @return
	 */
	public Long getSystemid() 
	{
		return this.systemid;
	}
	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
}