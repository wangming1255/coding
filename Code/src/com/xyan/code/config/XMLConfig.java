package com.xyan.code.config;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xyan.code.entity.Template;

@SuppressWarnings("unchecked")
public class XMLConfig {
	
	public static  HashMap<String, String> jdbc=new HashMap<>();
	
	public static String path = Class.class.getClass().getResource("/").getPath();
	
	/**
	 * 表名
	 */
	public static List<String> tables=new LinkedList<>();
	/**
	 * 当前模块共同的包名
	 */
	public static String commonPackage;
	/**
	 * 输出的路径
	 */
	public static String outPath;
	
	/**
	 * 项目的共通包
	 */
	public static String projectPackage;
	/**
	 * 代码的作者
	 */
	public static String user;
	/**
	 * 代码生成的时间
	 */
	public static String date;
	
	/**
	 * 联系邮箱
	 */
	public static String email;
	/**
	 * 模板
	 */
	public static List<Template> templates=new LinkedList<>();
	static{
		SAXReader reader = new SAXReader();
		try {
			Document  document = reader.read(new File(path+"codeConfig.xml"));
			Element rootElement=document.getRootElement();
			
			/*
			 * 1、变量部分
			 */
			Element variableElement=rootElement.element("variable");
			/*
			 * 1.1、jdbc属性
			 */
			Element jdbcElement=variableElement.element("jdbc");
			Element driverElement=jdbcElement.element("driver");
			jdbc.put("driver", driverElement.getTextTrim());
			Element urlElement=jdbcElement.element("url");
			jdbc.put("url", urlElement.getTextTrim());
			Element schemaElement=jdbcElement.element("schema");
			jdbc.put("schema", schemaElement.getTextTrim());
			Element userElement=jdbcElement.element("user");
			jdbc.put("user", userElement.getTextTrim());
			Element passwordElement=jdbcElement.element("password");
			jdbc.put("password", passwordElement.getTextTrim());
			Element remarksReportingElement=jdbcElement.element("remarksReporting");
			jdbc.put("remarksReporting", remarksReportingElement.getTextTrim());
			/*
			 * 1.2、输出属性
			 */
			Element outElement=variableElement.element("out");
			Element commonPackageElement=outElement.element("commonPackage");
			commonPackage=commonPackageElement.getTextTrim();
			Element outPathElement=outElement.element("outPath");
			outPath=outPathElement.getTextTrim();
			Element tablesElement=outElement.element("tables");
			List<Element> tableList=tablesElement.elements("table");
			for(Element e:tableList){
				tables.add(e.getTextTrim());
			}
			
			
			/*
			 * 2、固定部分
			 */
			
			Element constantElement=rootElement.element("constant");
			Element projectPackageElement=constantElement.element("projectPackage");
			projectPackage=projectPackageElement.getTextTrim();
			
			Element noteUserElement=constantElement.element("user");
			user=noteUserElement.getTextTrim();
			
			Element emailElement=constantElement.element("email");
			email=emailElement.getTextTrim();
			
			
			Element templatesElement=constantElement.element("templates");
			List<Element> templateList=templatesElement.elements("template");
			for(Element el:templateList){
				Template template=new Template();
				template.setFile(el.attributeValue("file"));
				template.setPath(el.attributeValue("path"));
				template.setName(el.getTextTrim());
				templates.add(template);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}  
	}
	
	
	public static Properties getDatabaseProperty() {
		Properties databaseProperties = new Properties();
		databaseProperties.put("user", jdbc.get("user"));
		databaseProperties.put("password", jdbc.get("password"));
		databaseProperties.put("remarksReporting",jdbc.get("remarksReporting"));
		return databaseProperties;
	}
	
	public static void initVelocity() {
		Properties properties = new Properties();
		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
		properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		properties.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		properties.setProperty(Velocity.RESOURCE_LOADER, "file");
		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "true");
		Velocity.init(properties);
	}
}
