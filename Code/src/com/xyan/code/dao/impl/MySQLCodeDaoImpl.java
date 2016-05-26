package com.xyan.code.dao.impl;

import com.xyan.code.dao.CodeDao;

public class MySQLCodeDaoImpl implements CodeDao{

	@Override
	public String getTable(String tableName,String schema) {
		return "select t.TABLE_NAME as \"tableName\",t.TABLE_COMMENT as \"tableComments\" from information_schema.tables  t "
				+" where  table_type='base table' and TABLE_SCHEMA='"+schema+"' and TABLE_NAME='"+tableName+"'";
	}

	@Override
	public String getTableColumn(String tableName,String schema) {
		return " select t.table_name as \"tableName\" ,t.COLUMN_NAME as \"columnName\",t.CHARACTER_MAXIMUM_LENGTH as \"dataLength\" ,"
	+" t.DATA_TYPE as \"dataType\" ,t.NUMERIC_PRECISION as \"dataPrecision\" ,t.COLUMN_COMMENT as \"columnComments\" "
	+" from information_schema.columns t where   TABLE_SCHEMA='"+schema+"' and table_name='"+tableName+"'  ";
	}

	@Override
	public String getTablePK(String tableName,String schema) {
		return " select COLUMN_KEY, COLUMN_NAME as \"columnName\" from INFORMATION_SCHEMA.COLUMNS where table_name='"+tableName+"' AND COLUMN_KEY='PRI';  ";
	}
	
	
}
