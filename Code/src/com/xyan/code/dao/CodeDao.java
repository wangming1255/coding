package com.xyan.code.dao;

public interface CodeDao {
	
	/**
	 * @Description:获取表信息的sql
	 * @param tableName 表名称
	 * @param schema 数据库名
	 * @return 
	 * @author:wangming
	 * @date:2016年2月3日 上午9:53:39
	 */
	public String getTable(String tableName,String schema);
	
	/**
	 * @Description:获取表的列信息的sql
	 * @param tableName 表名称
	 * @param schema 数据库名
	 * @return 
	 * @author:wangming
	 * @date:2016年2月3日 上午9:53:39
	 */
	public String getTableColumn(String tableName,String schema);
	
	/**
	 * @Description:获取表主键信息的sql
	 * @param tableName 表名称
	 * @param schema 数据库名
	 * @return 
	 * @author:wangming
	 * @date:2016年2月3日 上午9:53:39
	 */
	public String getTablePK(String tableName,String schema);
}
