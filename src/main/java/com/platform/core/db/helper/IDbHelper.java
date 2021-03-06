package com.platform.core.db.helper;

import java.util.List;

import com.platform.core.db.exception.CodegenException;
import com.platform.core.db.model.TableModel;

/**
 * 获取数据库所有表和表的TableMode接口类。
 * @author hotent
 *
 */
public interface IDbHelper {

	/**
	 * 设置URL,username,password
	 * 
	 * @param url
	 * @param username
	 * @param password
	 */
	void setUrl(String url, String username, String password);

	/**
	 * 根据表名取得TableModel
	 * 
	 * @param tableName
	 * @return
	 */
	TableModel getByTable(String tableName) throws CodegenException;

	/**
	 * 取得所有的表名
	 * 
	 * @return
	 */
	List<String> getAllTable() throws CodegenException;
}
