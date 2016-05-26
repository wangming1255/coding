package com.xyan.code.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.xyan.code.config.XMLConfig;
import com.xyan.code.dao.CodeDao;
import com.xyan.code.dao.impl.MySQLCodeDaoImpl;
import com.xyan.code.dao.impl.OracleCodeDaoImpl;
import com.xyan.code.entity.Table;
import com.xyan.code.service.CodeService;
import com.xyan.code.util.StringUtils;

public class CodeServiceImpl implements CodeService {
	
	private static CodeDao dao=null;
	private static List<String> tableNames;
	private static String schema="";
	
	static{
		String type=XMLConfig.jdbc.get("url");
		tableNames=XMLConfig.tables;
		schema=XMLConfig.jdbc.get("schema");
		//根据配置数据库url如果没有读取到则设为oracle
		if(StringUtils.containsIgnoreCase(type, "oracle")){
			dao=new OracleCodeDaoImpl();
		}if(StringUtils.containsIgnoreCase(type, "mysql")){
			dao=new MySQLCodeDaoImpl();
		}else{
			dao=new OracleCodeDaoImpl();
		}
	}
	
	public List<Table> getTables() throws Exception {
		
		System.out.println("测试数据库连接....");
		Class.forName(XMLConfig.jdbc.get("driver")).newInstance();
		Connection conn = DriverManager.getConnection(XMLConfig.jdbc.get("url"),XMLConfig.getDatabaseProperty());
		System.out.println(">>>>>>>>连接成功？？\t"+conn==null?"数据库连接失败":"数据库连接成功");
		String[] types = new String[1];
		types[0] = "TABLE";

		List<Table> tables = new ArrayList<Table>();
		System.out.println("================================");
		System.err.println("解析数据库中的表");
		for(String tableName:tableNames){
			PreparedStatement ps=conn.prepareStatement(dao.getTable(tableName,schema));//表信息
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String tbName = rs.getString("tableName");
				String tbDesc = rs.getString("tableComments");
				Table table = new Table(tbName,tbDesc);
				table.setUser(XMLConfig.user);
				table.setEmail(XMLConfig.email);
				String pk ="";
				PreparedStatement ps2=conn.prepareStatement(dao.getTablePK(tableName,schema));//主键信息
				ResultSet rsPK = ps2.executeQuery();
				if(rsPK.next()) {
					pk = rsPK.getString("COLUMNNAME");
				}
				System.out.println("表： " + tbName + "\t主键:" + pk);
				String colSQL=dao.getTableColumn(tableName,schema);
				PreparedStatement ps3=conn.prepareStatement(colSQL);//表的列信息
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
		}
		conn.close();
		System.err.println("解析完成");
		return tables;
	}

	@Override
	public void generateCode() throws Exception {
		System.out.println("去读配置文件: " + XMLConfig.path);
		XMLConfig.initVelocity();
		//获取数据库中的信息
		List<Table> tables = this.getTables();
		if(tables==null||tables.size()<1){
			System.out.println("数据库中不存在表："+XMLConfig.tables);
			return ;
		}
		String fileSeparator = System.getProperty("file.separator");

		//设置输出路径
		String outputPath = XMLConfig.outPath;
		if (outputPath == null || outputPath.trim().equals("")) {
			outputPath =  XMLConfig.path + "output/";
		}
		
		System.out.println(">>>>>>>>>>>>>>输出路径\t"+outputPath);
		
		VelocityContext context = new VelocityContext();
		System.out.println("开始生成文件");
		System.out.println("================================================================================");
		for (Table table : tables) {
			//计算真正的包名
			table.calPackageName(XMLConfig.commonPackage);
			context.put("meta", table);
			for (com.xyan.code.entity.Template tem:XMLConfig.templates) {
				String outPath = outputPath
						+ XMLConfig.projectPackage.replace(".", fileSeparator) + "/"+XMLConfig.commonPackage+"/" 
						+ tem.getPath().replace(".", fileSeparator) + "/";
				File file = new File(outPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				Template template = Velocity.getTemplate(tem.getName());
				template.setEncoding("utf-8");
				String fileName = outPath;
				if(tem.getFile().endsWith(".jsp")){
					fileName+=StringUtils.toLowerCaseFirstOne(table.getDomainClassName())
					+ tem.getFile();
				}else{
					fileName+=table.getDomainClassName()+ tem.getFile();
				}
				
				writeFiles(template, context, fileName);
				System.out.println("生成文件: " + fileName);
			}
		}
		System.out.println("===========================================================================");
		System.out.println("代码生成结束!");
	}

	private static void writeFiles(Template template, VelocityContext context,
			String fileName) throws IOException {
		FileWriter fileWriter = new FileWriter(fileName, false);
		template.merge(context, fileWriter);
		fileWriter.close();
	}
	
}
