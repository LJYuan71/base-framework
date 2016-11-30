package com.platform.core.db.model;

public class Database {
	private String dbHelperClass;
	private String url;
	private String username;
	private String password;

	public Database(String dbHelperClass, String url, String username,
			String password) {
		this.dbHelperClass = dbHelperClass;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * 数据库实现类。<br>
	 * 目前可为三种： Oracle ：com.hotent.cgm.db.impl.OracleHelper
	 * MySql：com.hotent.cgm.db.impl.MySqlHelper
	 * Sql2005：com.hotent.cgm.db.impl.Sql2005Helper
	 * 
	 * @return
	 */
	public String getDbHelperClass() {
		return dbHelperClass;
	}

	public void setDbHelperClass(String dbHelperClass) {
		this.dbHelperClass = dbHelperClass;
	}

	/**
	 * 数据库的URL
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 连接数据库用户名
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 连接数据库密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
