package com.platform.permissions.sysuserhost.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class SysUserHost implements Serializable {

	// id
	private Long  id;
	// 用户主机名称
	private String  name;
	// 用户id
	private Long  userid;
	// 主机id
	private Long  hostinfoid;
	// 分配的ip
	private String  userhostip;
	// 备注
	private String  remark;
	// 规模类型
	private String  type;
	// 创建时间
	private Timestamp  createtime;
	//状态：0未启动1启动
	private Integer status;
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
	 * 返回 用户主机名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
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
	public void setHostinfoid(Long hostinfoid) 
	{
		this.hostinfoid = hostinfoid;
	}
	/**
	 * 返回 主机id
	 * @return
	 */
	public Long getHostinfoid() 
	{
		return this.hostinfoid;
	}
	public void setUserhostip(String userhostip) 
	{
		this.userhostip = userhostip;
	}
	/**
	 * 返回 分配的ip
	 * @return
	 */
	public String getUserhostip() 
	{
		return this.userhostip;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemark() 
	{
		return this.remark;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 规模类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
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
	 * 返回用户主机状态：0未启动1启动
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}