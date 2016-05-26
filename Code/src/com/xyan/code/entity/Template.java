package com.xyan.code.entity;

/**
 * @Description：模板
 * @author：wangming
 *
 */
public class Template {
	
	/**
	 * 模板生成文件的路径
	 */
	private String path;
	/**
	 * 模板生成的文件类型
	 */
	private String file;
	/**
	 * 模板名称
	 */
	private String name;
	
	public Template() {
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
