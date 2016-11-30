package com.secrecy.basic.classifyinfo.model;

import java.io.Serializable;

public class ClassifyInfo implements Serializable {

	// id
	private Long  id;
	// 名称
	private String  name;
	// 父ID
	private Long  parentid;
	// 同级排序
	private Long  sn;
	// PATH
	private String  path;
	// 不存数据库  父节点名称
	private String parentname;
	
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
	public void setParentid(Long parentid) 
	{
		this.parentid = parentid;
	}
	/**
	 * 返回 父ID
	 * @return
	 */
	public Long getParentid() 
	{
		return this.parentid;
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
	public void setPath(String path) 
	{
		this.path = path;
	}
	/**
	 * 返回 PATH
	 * @return
	 */
	public String getPath() 
	{
		return this.path;
	}
	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
}