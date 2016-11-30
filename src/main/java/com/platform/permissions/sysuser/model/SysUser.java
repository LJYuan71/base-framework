package com.platform.permissions.sysuser.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.platform.permissions.sysorg.model.SysOrg;
import com.platform.permissions.sysposition.model.SysPosition;
import com.platform.permissions.sysrole.model.SysRole;

public class SysUser implements Serializable {

	// ID
	private Long id;
	// 姓名
	private String fullname;
	// 帐号
	private String account;
	// 密码
	private String password;
	// 是否过期
	private Long isexpired;
	// 是否锁定
	private Long islock;
	// 创建时间
	private Timestamp createdate;
	// "状态 1=通过 0=未审核 -1=未通过"
	private Long status;
	// 邮箱
	private String email;
	// 手机
	private String mobile;
	// 电话
	private String phone;
	// 性别"1=男 0=女"
	private String sex;
	// 照片
	private String picture;
	// 数据来源:0-系统添加；1-系统同步；其他-未知
	private String fromtype;
	// 身份证号
	private String idcard;
	//单位名称
	private String company;

	// 用户相关信息 关联查询出来 不存数据库
	private List<SysRole> roles;
	private List<SysOrg> orgs;
	private List<SysPosition> positions;

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public List<SysOrg> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<SysOrg> orgs) {
		this.orgs = orgs;
	}

	public List<SysPosition> getPositions() {
		return positions;
	}

	public void setPositions(List<SysPosition> positions) {
		this.positions = positions;
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

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * 返回 姓名
	 * 
	 * @return
	 */
	public String getFullname() {
		return this.fullname;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 返回 帐号
	 * 
	 * @return
	 */
	public String getAccount() {
		return this.account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回 密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}

	public void setIsexpired(Long isexpired) {
		this.isexpired = isexpired;
	}

	/**
	 * 返回 是否过期
	 * 
	 * @return
	 */
	public Long getIsexpired() {
		return this.isexpired;
	}

	public void setIslock(Long islock) {
		this.islock = islock;
	}

	/**
	 * 返回 是否锁定
	 * 
	 * @return
	 */
	public Long getIslock() {
		return this.islock;
	}

	/**
	 * 返回 创建时间
	 * 
	 * @return
	 */
	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * 返回 "状态 1=激活 0=禁用 -1=删除"
	 * 
	 * @return
	 */
	public Long getStatus() {
		return this.status;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 返回 邮箱
	 * 
	 * @return
	 */
	public String getEmail() {
		return this.email;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 返回 手机
	 * 
	 * @return
	 */
	public String getMobile() {
		return this.mobile;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 返回 电话
	 * 
	 * @return
	 */
	public String getPhone() {
		return this.phone;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 返回 性别"1=男 0=女"
	 * 
	 * @return
	 */
	public String getSex() {
		return this.sex;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	/**
	 * 返回 照片
	 * 
	 * @return
	 */
	public String getPicture() {
		return this.picture;
	}

	public void setFromtype(String fromtype) {
		this.fromtype = fromtype;
	}

	/**
	 * 返回 数据来源:0-系统添加；1-系统同步；其他-未知
	 * 
	 * @return
	 */
	public String getFromtype() {
		return this.fromtype;
	}

	public String getIdcard() {
		return idcard;
	}

	/**
	 * 返回身份证号
	 * 
	 * @param idcard
	 */
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	/**
	 * 获取单位名称
	 * @return
	 */
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
}