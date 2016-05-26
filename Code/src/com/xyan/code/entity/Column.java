package com.xyan.code.entity;

import com.xyan.code.util.NameParser;


/**
 * @description 列
 * @author wangming
 * @date 2016年2月3日 上午9:25:12
 */
public class Column{
	/**列名称*/
	private String colName;
	/**列描述*/
	private String colDesc;
	/**列类型*/
	private String colType;
	/**属性名*/
	private String fieldName;
	/**长度*/
	private String length;
	/**数据库列类型*/
	private String colDBType;
	/**set方法名称*/
	private String seOperName;
	/**get方法名称*/
	private String geOperName;
	/**是否主键*/
	private boolean pkFlag;

	public boolean isPkFlag() {
		return pkFlag;
	}

	public void setPkFlag(boolean pkFlag) {
		this.pkFlag = pkFlag;
	}

	public String getGeOperName() {
		return geOperName;
	}

	public void setGeOperName(String geOperName) {
		this.geOperName = geOperName;
	}

	public String getSeOperName() {
		return seOperName;
	}

	public void setSeOperName(String seOperName) {
		this.seOperName = seOperName;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;

		String fn = NameParser.capitalize(colName, false);
		String fnS = "set" + Character.toUpperCase(fn.charAt(0))
				+ fn.substring(1);
		String fnG = "get" + Character.toUpperCase(fn.charAt(0))
				+ fn.substring(1);
		this.setFieldName(fn);
		this.setSeOperName(fnS);
		this.setGeOperName(fnG);

	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {

		this.colType = colType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getColDesc() {
		return colDesc;
	}

	public void setColDesc(String colDesc) {
		this.colDesc = colDesc;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getColDBType() {
		return colDBType;
	}

	public void setColDBType(String colDBType) {
		this.colDBType = colDBType;
	}

	public String toString() {
		return this.getColName() + "<" + this.getColDesc() + ">";
	}
}
