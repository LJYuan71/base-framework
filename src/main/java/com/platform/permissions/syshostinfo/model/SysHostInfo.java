package com.platform.permissions.syshostinfo.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class SysHostInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 主机id
	private Long  id;
	// 主机名称
	private String  name;
	// 适用规模
	private String  type;
	// CPU核数
	private Long  cpu;
	// 内存
	private Long  memory;
	// 硬盘
	private Long  harddisk;
	// 最大用户数
	private Long  maxusers;
	// 最小用户数
	private Long  minusers;
	// 描述
	private String  describes;
	// 创建时间
	private Timestamp  createtime;
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 主机id
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
	 * 返回 主机名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 适用规模
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setCpu(Long cpu) 
	{
		this.cpu = cpu;
	}
	/**
	 * 返回 CPU核数
	 * @return
	 */
	public Long getCpu() 
	{
		return this.cpu;
	}
	public void setMemory(Long memory) 
	{
		this.memory = memory;
	}
	/**
	 * 返回 内存
	 * @return
	 */
	public Long getMemory() 
	{
		return this.memory;
	}
	public void setHarddisk(Long harddisk) 
	{
		this.harddisk = harddisk;
	}
	/**
	 * 返回 硬盘
	 * @return
	 */
	public Long getHarddisk() 
	{
		return this.harddisk;
	}
	public void setMaxusers(Long maxusers) 
	{
		this.maxusers = maxusers;
	}
	/**
	 * 返回 最大用户数
	 * @return
	 */
	public Long getMaxusers() 
	{
		return this.maxusers;
	}
	public void setMinusers(Long minusers) 
	{
		this.minusers = minusers;
	}
	/**
	 * 返回 最小用户数
	 * @return
	 */
	public Long getMinusers() 
	{
		return this.minusers;
	}
	public void setDescribes(String describes) 
	{
		this.describes = describes;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDescribes() 
	{
		return this.describes;
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
	
}