package com.xyan.code.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.xyan.code.config.XMLConfig;
import com.xyan.code.util.NameParser;

/**
 * @description 表信息，实际也是最主要的数据类
 * @author wangming
 * @date 2016年2月15日 上午9:40:18
 */
public class Table {
	private static final String TYPE_FLOAT = "BigDecimal";
	//private static final String TYPE_FLOAT = "java.math.BigDecimal";
	private static final String TYPE_INTEGER = "Integer";
	private static final String TYPE_LONG = "Long";
	private static final String TYPE_STRING = "String";

	private List<Column> cols = new ArrayList<Column>();
	
	/**表描述（备注）*/
	private String tableDesc;
	/**表名称*/
	private String tableName;
	/**共同的名称*/
	private String commonName;
	/**主键属性名*/
	private String primaryKeyField;
	/**主键列名*/
	private String primaryKeyColumn;
	/**实体类名称*/
	private String domainClassName;
	/**实体类包路径*/
	private String domainPackageName;
	/**service实现类包路径*/
	private String servicePackageName;
	/**service类名*/
	private String serviceClassName;
	/**mapper接口包路径（dao 包路径）*/
	private String mapperInterfacePackageName;
	/**mapper接口名称*/
	private String mapperInterfaceClassName;
	/**controller包路径*/
	private String controllerPackageName;
	/**controller类名*/
	private String controllerClassName;
	/**vo包路径*/
	private String voPackageName;
	/**vo类名*/
	private String voClassName;
	private String l_domainClassName;//首字母小写的实体类名
	private String l_serviceClassName;//首字母小写的service类名
	
	/**
	 * 注释的作者
	 */
	private String user;
	
	/**
	 * 联系邮箱
	 */
	private String email;
	/**
	 * 注释的时间
	 */
	private String date;
	
	public Table(String tableName, String tableDesc) {
		this.tableName = tableName;//表名
		this.tableDesc = tableDesc;//表注释
		
		this.domainClassName = NameParser.getDomainName(tableName);//实体类名称
		this.l_domainClassName=com.xyan.code.util.StringUtils.toLowerCaseFirstOne(domainClassName);//首字母小写的类名
		
		
		this.serviceClassName = this.domainClassName + "Service";
		this.l_serviceClassName=this.l_domainClassName+"Service";//首字母小写的service类名
		this.mapperInterfaceClassName = this.domainClassName + "Mapper";
		this.controllerClassName = this.domainClassName + "Controller";
		this.voClassName = this.domainClassName + "VO";
	}

	/**
	 * @Description:计算包路径。如果不传则使用默认的表名称作为公共包
	 * @param common
	 * @author:wangming
	 * @date:2016年2月4日 下午1:59:23
	 */
	public void calPackageName(String common){
		System.out.println("计算包路径");
		if(StringUtils.isBlank(common)){
			common=com.xyan.code.util.StringUtils.toLowerCaseFirstOne(this.domainClassName);
		}
		this.commonName=common;
		this.domainPackageName=XMLConfig.projectPackage+"."+common+".domain";
		this.servicePackageName=XMLConfig.projectPackage+"."+common+".service";
		this.mapperInterfacePackageName = XMLConfig.projectPackage+"."+common+".dao";
		this.controllerPackageName = XMLConfig.projectPackage+"."+common+".controller";
		this.voPackageName =XMLConfig.projectPackage+"."+common+".vo";
	}
	
	
	/**
	 * @Description:(描述该方法的作用)
	 * @param colName 列名称
	 * @param colType 列类型
	 * @param pec 精度，小数的长度
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
			this.primaryKeyColumn=colName;
		}
		cols.add(col);
	}
	
	

	/**
	 * @Description:列类型解析
	 * @param colType 数据库的列类别
	 * @param digits 精度
	 * @return
	 * @author:wangming
	 * @date:2016年3月30日 下午12:50:20
	 */
	private String parseDataType(String colType, String digits) {
		String ct;

		if ("VARCHAR".equalsIgnoreCase(colType)) {
			ct = TYPE_STRING;
		} else if ("NUMBER".equalsIgnoreCase(colType)) {
			if ("0".equals(digits)) {
				ct = TYPE_LONG;
			} else {
				ct = TYPE_FLOAT;
			}
		} else if ("BIGINT".equalsIgnoreCase(colType)) {
			ct = TYPE_LONG;
		} else if ("INTEGER".equalsIgnoreCase(colType) || "SMALLINT".equalsIgnoreCase(colType)) {
			ct = TYPE_INTEGER;
		} else if ("DATE".equalsIgnoreCase(colType) || "TIMESTAMP".equalsIgnoreCase(colType)) {
			ct = "Date";
		} else if ("BLOB".equalsIgnoreCase(colType)) {
			ct = "Object";
		} else {
			ct = "String";
		}
		return ct;
	}

	public String toString() {
		return "Dabase Table Name:" + this.tableName + "\n Table Columns:" + this.cols.toString();
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

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		if(StringUtils.isBlank(date)){
			this.date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
