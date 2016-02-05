package com.xyan.code.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.xyan.code.config.Config;
import com.xyan.code.dao.CodeDao;
import com.xyan.code.dao.impl.MySQLCodeDaoImpl;
import com.xyan.code.dao.impl.OracleCodeDaoImpl;
import com.xyan.code.entity.Table;
import com.xyan.code.service.CodeService;

public class CodeServiceImpl implements CodeService {
	
	private static CodeDao dao=null;
	private static String tableName="";
	private static String schema="";
	static{
		Config config=Config.getInstance();
		String type=config.getProperty("databaseType");
		tableName=config.getProperty("tableName");
		schema=config.getProperty("schema");
		//根据配置文件设置数据库类型，如果没有读取到则设为oracle
		if("oracle".equals(type)){
			dao=new OracleCodeDaoImpl();
		}if("mysql".equals(type)){
			dao=new MySQLCodeDaoImpl();
		}else{
			dao=new OracleCodeDaoImpl();
		}
	}
	
	@Override
	public List<Table> getTables() throws Exception {
		
		System.out.println("测试数据库连接....");
		
		Config config = Config.getInstance();
		
		Class.forName(config.getDatabaseDriver()).newInstance();
		Connection conn = DriverManager.getConnection(config.getDatabaseURL(),
				config.getDatabaseProperty());
		
		System.out.println(">>>>>>>>连接成功？？"+conn==null);
		String[] types = new String[1];
		types[0] = "TABLE";

		List<Table> tables = new ArrayList<Table>();
		System.out.println("================================");
		System.err.println("解析数据库中的表");
		PreparedStatement ps=conn.prepareStatement(dao.getTable(tableName,schema));
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String tbName = rs.getString("tableName");
			String tbDesc = rs.getString("tableComments");
			Table table = new Table(tbName,tbDesc);
			String pk ="";
			PreparedStatement ps2=conn.prepareStatement(dao.getTablePK(tableName,schema));
			ResultSet rsPK = ps2.executeQuery();
			if(rsPK.next()) {
				pk = rsPK.getString("COLUMNNAME");
			}
			System.out.println("表： " + tbName + "\t主键:" + pk);

			
			String colSQL=dao.getTableColumn(tableName,schema);
			PreparedStatement ps3=conn.prepareStatement(colSQL);
			ResultSet rsCol = ps3.executeQuery();
			while (rsCol.next()) {
				table.addCol(
						rsCol.getString("COLUMNNAME"),
						rsCol.getString("DATATYPE"),
						rsCol.getString("DATAPRECISION"),
						rsCol.getString("COLUMNCOMMENTS"),
						rsCol.getString("DATALENGTH"),
						pk.equalsIgnoreCase(rsCol.getString("COLUMNNAME")));

			}

			tables.add(table);
			System.out.println("\n===============\n表信息\n"+table.toString()+"===================================");
		}
		conn.close();
		System.err.println("解析完成");
		return tables;
	}
	
}
