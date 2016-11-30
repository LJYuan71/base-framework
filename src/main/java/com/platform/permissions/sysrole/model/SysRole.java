package com.platform.permissions.sysrole.model;

import java.io.Serializable;
import java.util.List;

import com.platform.permissions.sysres.model.SysRes;

public class SysRole implements Serializable {

	// ID
	private Long id;
	// 系统ID
	private Long systemid;
	// 角色别名
	private String alias;
	// 角色名
	private String rolename;
	// 备注
	private String remark;
	// 允许删除
	private Long allowdel;
	// 允许编辑
	private Long allowedit;
	// "是否启用(0,禁止,1,启用)"
	private Long enabled;
	// 时间
	private java.util.Date createdate;

	// 角色含有哪些资源 不存数据库
	private List<SysRes> sysRess;

	public List<SysRes> getSysRess() {
		return sysRess;
	}

	public void setSysRess(List<SysRes> sysRess) {
		this.sysRess = sysRess;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 返回 ID
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	public void setSystemid(Long systemid) {
		this.systemid = systemid;
	}

	/**
	 * 返回 系统ID
	 * 
	 * @return
	 */
	public Long getSystemid() {
		return this.systemid;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 返回 角色别名
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	/**
	 * 返回 角色名
	 * 
	 * @return
	 */
	public String getRolename() {
		return this.rolename;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 返回 备注
	 * 
	 * @return
	 */
	public String getRemark() {
		return this.remark;
	}

	public void setAllowdel(Long allowdel) {
		this.allowdel = allowdel;
	}

	/**
	 * 返回 允许删除
	 * 
	 * @return
	 */
	public Long getAllowdel() {
		return this.allowdel;
	}

	public void setAllowedit(Long allowedit) {
		this.allowedit = allowedit;
	}

	/**
	 * 返回 允许编辑
	 * 
	 * @return
	 */
	public Long getAllowedit() {
		return this.allowedit;
	}

	public void setEnabled(Long enabled) {
		this.enabled = enabled;
	}

	/**
	 * 返回 "是否启用(0,禁止,1,启用)"
	 * 
	 * @return
	 */
	public Long getEnabled() {
		return this.enabled;
	}

	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}

	/**
	 * 返回 时间
	 * 
	 * @return
	 */
	public java.util.Date getCreatedate() {
		return this.createdate;
	}
}