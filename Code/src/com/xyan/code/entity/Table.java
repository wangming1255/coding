package com.xyan.code.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.xyan.code.util.NameParser;
import com.xyan.code.config.Config;

public class Table {
	private static final String TYPE_FLOAT = "java.math.BigDecimal";
	private static final String TYPE_INTEGER = "Integer";
	private static final String TYPE_LONG = "Long";
	private static final String TYPE_STRING = "String";

	private List<Column> cols = new ArrayList<Column>();

	private String tableDesc;
	private String tableName;
	private String primaryKeyField;
	private String primaryKeyColumn;
	private String domainClassName;
	private String domainPackageName;
	private String apiPackageName;
	private String servicePackageName;
	private String serviceClassName;
	private String mapperInterfacePackageName;
	private String mapperInterfaceClassName;
	private String controllerPackageName;
	private String controllerClassName;
	private String voPackageName;
	private String voClassName;
	private String l_domainClassName;//首字母小写的实体类名
	private String l_serviceClassName;//首字母小写的service类名
	
	
	public Table(String tableName, String tableDesc) {

		this.tableName = tableName;//表名
		this.tableDesc = tableDesc;//表注释
		this.domainClassName = NameParser.getDomainName(tableName);//实体类名称
		this.l_domainClassName=com.xyan.code.util.StringUtils.toLowerCaseFirstOne(domainClassName);
		this.domainPackageName = Config.getInstance().getProperty(
				"projectPackage")
				+ ".domain";

		this.serviceClassName = this.domainClassName + "Service";
		this.l_serviceClassName=this.l_domainClassName+"Service";
		this.servicePackageName = Config.getInstance().getProperty(
				"projectPackage")
				+ ".service";
		this.apiPackageName = Config.getInstance().getProperty(
				"projectPackage")
				+ ".api";
		this.mapperInterfaceClassName = this.domainClassName + "Mapper";
		this.mapperInterfacePackageName = Config.getInstance().getProperty(
				"projectPackage")
				+ ".persistence";

		this.controllerClassName = this.domainClassName + "Controller";
		this.controllerPackageName = Config.getInstance().getProperty(
				"projectPackage")
				+ ".controller";
		
		this.voClassName = this.domainClassName + "VO";
		this.voPackageName = Config.getInstance().getProperty(
				"projectPackage")
				+ ".vo";
	}

	/**
	 * @Description:计算包路径。如果不传则使用默认的表名称作为公共包
	 * @param common
	 * @author:wangming
	 * @date:2016年2月4日 下午1:59:23
	 */
	public void calPackageName(String common){
		if(StringUtils.isBlank(common)){
			common=com.xyan.code.util.StringUtils.toLowerCaseFirstOne(this.domainClassName);
		}
		this.domainPackageName=Config.getInstance().getProperty(
				"projectPackage")+"."+common+".domain";
		this.servicePackageName=Config.getInstance().getProperty(
				"projectPackage")+"."+common+".service";
		this.apiPackageName = Config.getInstance().getProperty(
				"projectPackage")+"."+common+".service.impl";
		this.mapperInterfacePackageName = Config.getInstance().getProperty(
				"projectPackage")+"."+common+".dao";
		this.controllerPackageName = Config.getInstance().getProperty(
				"projectPackage")+"."+common+".controller";
		this.voPackageName = Config.getInstance().getProperty(
				"projectPackage")+"."+common+".vo";
	}
	
	
	/**
	 * @Description:(描述该方法的作用)
	 * @param colName 列名称
	 * @param colType 列类型
	 * @param pec 精度
	 * @param desc 注释
	 * @param length 长度
	 * @param isPK 是否主键
	 * @author:wangming
	 * @date:2016年2月2日 下午4:05:58
	 */
	public void addCol(String colName, String colType, String pec, String desc,
			String length, boolean isPK) {
		Column col = new Column();

		col.setColName(colName);
		if (desc == null)
			desc = "";
		col.setColDesc(desc.trim());
		col.setLength(length);

		String ct = parseDataType(colType, pec);
		col.setColType(ct);
		col.setColDBType(colType);
		col.setPkFlag(isPK);
		if(isPK){
			this.primaryKeyField=NameParser.capitalize(colName, false);
		}
		cols.add(col);
	}
	
	

	private String parseDataType(String colType, String digits) {
		String ct;

		if ("VARCHAR".equalsIgnoreCase(colType)) {
			ct = TYPE_STRING;
		} else if ("NUMBER".equalsIgnoreCase(colType)) {
			if ("0".equals(digits)) {
				ct = TYPE_INTEGER;
			} else {
				ct = TYPE_FLOAT;
			}
		} else if ("BIGINT".equalsIgnoreCase(colType)) {
			ct = TYPE_LONG;
		} else if ("INTEGER".equalsIgnoreCase(colType) || "SMALLINT".equalsIgnoreCase(colType)) {
			ct = TYPE_INTEGER;
		} else if ("DATE".equalsIgnoreCase(colType) || "TIMESTAMP".equalsIgnoreCase(colType)) {
			ct = "java.util.Date";
		} else if ("BLOB".equalsIgnoreCase(colType)) {
			ct = "Object";

		} else {
			ct = "String";
		}
		return ct;
	}

	public String toString() {
		return "Dabase Table Name:" + this.tableName + "\n Table Columns:"
				+ this.cols.toString();

	}

	public List<Column> getCols() {
		return cols;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public String getTableName() {
		return tableName;
	}

	public String getDomainClassName() {
		return domainClassName;
	}

	public String getDomainPackageName() {
		return domainPackageName;
	}

	public String getServicePackageName() {
		return servicePackageName;
	}

	public String getServiceClassName() {
		return serviceClassName;
	}

	public String getMapperInterfacePackageName() {
		return mapperInterfacePackageName;
	}

	public String getMapperInterfaceClassName() {
		return mapperInterfaceClassName;
	}

	public String getControllerPackageName() {
		return controllerPackageName;
	}

	public String getControllerClassName() {
		return controllerClassName;
	}

	public String getVoPackageName() {
		return voPackageName;
	}

	public void setVoPackageName(String voPackageName) {
		this.voPackageName = voPackageName;
	}

	public String getVoClassName() {
		return voClassName;
	}

	public void setVoClassName(String voClassName) {
		this.voClassName = voClassName;
	}
	
	public String getApiPackageName() {
		return apiPackageName;
	}

	public void setApiPackageName(String apiPackageName) {
		this.apiPackageName = apiPackageName;
	}

	public String getL_domainClassName() {
		return l_domainClassName;
	}

	public void setL_domainClassName(String l_domainClassName) {
		this.l_domainClassName = l_domainClassName;
	}

	public String getL_serviceClassName() {
		return l_serviceClassName;
	}

	public void setL_serviceClassName(String l_serviceClassName) {
		this.l_serviceClassName = l_serviceClassName;
	}

	public String getPrimaryKeyField() {
		return primaryKeyField;
	}

	public void setPrimaryKeyField(String primaryKeyField) {
		this.primaryKeyField = primaryKeyField;
	}

	public String getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}

	public void setPrimaryKeyColumn(String primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}


}
