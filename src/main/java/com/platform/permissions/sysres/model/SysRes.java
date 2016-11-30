package com.platform.permissions.sysres.model;

import java.io.Serializable;
import java.util.List;

public class SysRes implements Serializable {

	// ID
	private Long id;
	// 资源名称
	private String name;
	// 别名
	private String alias;
	// 同级排序
	private Long sn;
	// 图标
	private String icon;
	// 父ID
	private Long parentid;
	// 默认地址
	private String defaulturl;
	// 栏目
	private Long isfolder;
	// 显示到菜单
	private Long isdisplayinmenu;
	// 子系统ID
	private Long systemid;
	// PATH
	private String path;
	// 权限标识
	private String permission;

	// 不存数据库 父节点名称
	private String parentname;
	// 二级菜单list 不存数据库
	private List<SysRes> secondList;

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<SysRes> getSecondList() {
		return secondList;
	}

	public void setSecondList(List<SysRes> secondList) {
		this.secondList = secondList;
	}

	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
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

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 资源名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 返回 别名
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * 返回 同级排序
	 * 
	 * @return
	 */
	public Long getSn() {
		return this.sn;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 返回 图标
	 * 
	 * @return
	 */
	public String getIcon() {
		return this.icon;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	/**
	 * 返回 父ID
	 * 
	 * @return
	 */
	public Long getParentid() {
		return this.parentid;
	}

	public void setDefaulturl(String defaulturl) {
		this.defaulturl = defaulturl;
	}

	/**
	 * 返回 默认地址
	 * 
	 * @return
	 */
	public String getDefaulturl() {
		return this.defaulturl;
	}

	public void setIsfolder(Long isfolder) {
		this.isfolder = isfolder;
	}

	/**
	 * 返回 栏目
	 * 
	 * @return
	 */
	public Long getIsfolder() {
		return this.isfolder;
	}

	public void setIsdisplayinmenu(Long isdisplayinmenu) {
		this.isdisplayinmenu = isdisplayinmenu;
	}

	/**
	 * 返回 显示到菜单
	 * 
	 * @return
	 */
	public Long getIsdisplayinmenu() {
		return this.isdisplayinmenu;
	}

	public void setSystemid(Long systemid) {
		this.systemid = systemid;
	}

	/**
	 * 返回 子系统ID
	 * 
	 * @return
	 */
	public Long getSystemid() {
		return this.systemid;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 返回 PATH
	 * 
	 * @return
	 */
	public String getPath() {
		return this.path;
	}
}