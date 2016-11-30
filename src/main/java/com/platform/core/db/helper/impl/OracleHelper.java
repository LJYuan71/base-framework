package com.platform.core.db.helper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.platform.common.utils.StringUtil;
import com.platform.core.db.exception.CodegenException;
import com.platform.core.db.helper.DaoHelper;
import com.platform.core.db.helper.IDbHelper;
import com.platform.core.db.helper.MapCmd;
import com.platform.core.db.model.ColumnModel;
import com.platform.core.db.model.TableModel;

/**
 * 取得数据库表接口IDbHelper，ORACLE 的实现
 *
 */
public class OracleHelper implements IDbHelper {

	// 取得主键
	private String sqlPk = "select column_name from user_constraints c,user_cons_columns col where c.constraint_name=col.constraint_name and c.constraint_type='P' and c.table_name='%s'";

	// 取得注释
	private String sqlTableComment = "select * from user_tab_comments  where table_type='TABLE' AND table_name ='%s'";

	// 取得列表
	private String sqlColumn = "select    A.column_name NAME,A.data_type TYPENAME,A.data_length LENGTH,A.data_precision PRECISION,    A.Data_Scale SCALE,A.Data_default, A.NULLABLE, B.comments DESCRIPTION "
			+ " from  user_tab_columns A,user_col_comments B where a.COLUMN_NAME=b.column_name and    A.Table_Name = B.Table_Name and A.Table_Name='%s' order by A.column_id";

	/***********ljh 2013-7-22 扩展代码生成器，使之能够bpm_form_field表中获取相关的字段验证*********************/
	private String sqlValidRule = "select fed.fieldname,fed.validrule from bpm_form_table tab inner join bpm_form_field fed on tab.tableid = fed.tableid where tab.tablename = '%s'";
	/***********ljh 2013-7-22 End*********************/
	
	// 取得数据库所有表
	private String sqlAllTables = "select table_name from user_tables where status='VALID'";

	private String url;
	private String username;
	private String password;

	public OracleHelper() throws CodegenException {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new CodegenException("找不到oracle驱动!", e);
		}
	}

	public void setUrl(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	

	/**
	 * 取得表注释
	 * @param tableName
	 * @return
	 * @throws CodegenException
	 */
	private String getTableComment(String tableName) throws CodegenException {
		tableName=tableName.toUpperCase();
		
		String sql = String.format(sqlTableComment, tableName);
		DaoHelper<String> dao=new DaoHelper<String>(url, username, password);
		String comment= dao.queryForObject(sql,new MapCmd<String>() {
			public String getObjecFromRs(ResultSet rs) throws SQLException {
				return rs.getString("COMMENTS");
			}
		});
		if(comment==null) comment=tableName;
		String[] aryComment=comment.split("\n");
		return aryComment[0];

	}
	
	/**
	 * 取得表的主键
	 * @param tableName
	 * @return
	 * @throws CodegenException
	 */
	private String getPk(String tableName) throws CodegenException
	{
		tableName=tableName.toUpperCase();
		String sql = String.format(sqlPk, tableName);
		DaoHelper<String> dao=new DaoHelper<String>(url, username, password);
		String pk="";
		try {
			pk= dao.queryForObject(sql,new MapCmd<String>() {
				public String getObjecFromRs(ResultSet rs) throws SQLException{
					return rs.getString("COLUMN_NAME");
					
				}
			});
		} catch (Exception e) {
			throw new CodegenException("从表中取得主键出错,请检查表是否设置主键");
		}
		return pk;
		
	}

	/**
	 * 设置主键
	 * @param list
	 * @param pk
	 */
	private void setPk(List<ColumnModel> list ,String pk)
	{
		for(ColumnModel model :list){
			if(pk.toLowerCase().equals(model.getColumnName().toLowerCase()))
				model.setIsPK(true);
		}
	}
	
	/**
	 * 根据表名取得表对象
	 * @param tableName
	 */
	public TableModel getByTable(String tableName) throws CodegenException {
		//取得表的列表数据
		List<ColumnModel> list=getColumnsByTable(tableName);
				
		tableName=tableName.toUpperCase();
		//取得注释
		String tabComment=getTableComment(tableName);
		String pk=getPk(tableName);
		
		TableModel tableModel = new TableModel();
		tableModel.setTableName(tableName);
		tableModel.setTabComment(tabComment);
		
		//设置主键
		if(StringUtil.isNotEmpty(pk)){
			setPk(list,pk);
		}
		tableModel.setColumnList(list);
 
		return tableModel;
	}

	/**
	 * 取得数据库的所有表
	 */
	public List<String> getAllTable() throws CodegenException {

		DaoHelper<String> dao=new DaoHelper<String>(url, username, password);
		return dao.queryForList(sqlAllTables,new  MapCmd<String>() {
			public String getObjecFromRs(ResultSet rs) throws SQLException {
				return rs.getString("TABLE_NAME");
			}
		});
	}
	/**
	 * 根据表名取得列列表
	 * @param tableName
	 * @return
	 * @throws CodegenException
	 */
	private List<ColumnModel> getColumnsByTable(String tableName) throws CodegenException
	{
		DaoHelper<ColumnModel> dao=new DaoHelper<ColumnModel>(this.url, this.username, this.password);
		tableName=tableName.toUpperCase();
		String sql=String.format(sqlColumn,tableName);
		List<ColumnModel> list=dao.queryForList(sql, new OracleMapCmd());
		return list;
	}
	public static void main(String[] args) throws CodegenException
	{
		
		OracleHelper helper=new OracleHelper();
		helper.setUrl("jdbc:oracle:thin:@localhost:1521:orcl", "casec", "123456");
		TableModel t=helper.getByTable("sys_user");
		/*String pk= helper.getPk("sys_user");
*/
	}


}
