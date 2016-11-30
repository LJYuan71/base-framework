package com.platform.log.syslog.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class SysLog implements Serializable {

	// ID
	private Long  id;
	// 用户id
	private Long  userid;
	// 用户名
	private String  username;
	// 创建时间
	private Timestamp  createtime;
	// 模块名
	private String  modelname;
	// 描述
	private String  description;
	// 状态  1=系统日志  2=业务日志
	private Long  state;
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
	public void setUsername(String username) 
	{
		this.username = username;
	}
	/**
	 * 返回 用户名
	 * @return
	 */
	public String getUsername() 
	{
		return this.username;
	}
	public void setModelname(String modelname) 
	{
		this.modelname = modelname;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	/**
	 * 返回 模块名
	 * @return
	 */
	public String getModelname() 
	{
		return this.modelname;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDescription() 
	{
		return this.description;
	}
	public void setState(Long state) 
	{
		this.state = state;
	}
	/**
	 * 返回 状态  1=系统日志  2=业务日志
	 * @return
	 */
	public Long getState() 
	{
		return this.state;
	}
}