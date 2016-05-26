package com.xyan.code.dao.impl;

import com.xyan.code.dao.CodeDao;

public class OracleCodeDaoImpl implements CodeDao{

	@Override
	public String getTable(String tableName,String schema) {
		return "select t.table_name as \"tableName\",t.comments as \"tableComments\" from user_tab_comments t where t.table_name='"+tableName+"'";
	}

	@Override
	public String getTableColumn(String tableName,String schema) {
		return " select t.table_name as \"tableName\",t.COLUMN_NAME as \"columnName\",t.data_length as \"dataLength\",t.DATA_TYPE as \"dataType\",t.DATA_SCALE as \"dataPrecision\",cc.comments as \"columnComments\" from all_tab_columns  t "
				+" left join  user_col_comments cc on cc.table_name=t.TABLE_NAME and cc.column_name=t.COLUMN_NAME where t.Table_Name='"+tableName+"' AND t.owner='"+schema+"' ";
	}

	@Override
	public String getTablePK(String tableName,String schema) {
		return " select    t.column_name as \"columnName\"   from   user_cons_columns t  "
				+ " where   constraint_name   =   (select   constraint_name   from   user_constraints   "
				+" where   table_name   =   '"+tableName+"'  and   constraint_type   ='P') and owner= '"+schema+"'  ";
	}
	
	
}
