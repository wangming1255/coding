package com.xyan.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.xyan.code.config.Config;
import com.xyan.code.entity.Table;
import com.xyan.code.service.CodeService;
import com.xyan.code.service.impl.CodeServiceImpl;

public class Main {
	public static void main(String[] args) throws Exception {
		String path = Class.class.getClass().getResource("/").getPath();
		System.out.println("去读配置文件: " + path);

		Config config = Config.getInstance();
		//Velocity的配置初始化      
		Properties p = new Properties();
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
		p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		p.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		p.setProperty(Velocity.RESOURCE_LOADER, "file");
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "true");
		Velocity.init(p);

		CodeService service=new CodeServiceImpl();
		List<Table> tables = service.getTables();

		String fileSeparator = System.getProperty("file.separator");

		String outputPath = config.getProperty("outPath");
		if (outputPath == null || outputPath.trim().equals("")) {
			outputPath = path + "output/";
		}
		System.out.println(">>>>>>>>>>>>>>输出路径\t"+outputPath);
		String projectPackage = config.getProperty("projectPackage");
		VelocityContext context = new VelocityContext();
		String[] templates = { "domain.vm", "mapper.vm", "mapperInterface.vm","service.vm","serviceImpl.vm","vo.vm","controller.vm","add.vm","list.vm" };
		String[] paths = { "domain", "dao.mapper", "dao","service","service.impl","vo","controller" ,"jsp","jsp"};
		String[] fileNames = { ".java", "Mapper.xml", "Mapper.java","Service.java","ServiceImpl.java","VO.java","Controller.java","Add.jsp","List.jsp" };
		System.out.println("开始生成文件");
		System.out.println("================================================================================");
		for (Table table : tables) {
			table.calPackageName("idxDef");
			context.put("meta", table);
			for (int i = 0; i < templates.length; i++) {
				String outPath = outputPath
						+ projectPackage.replace(".", fileSeparator) + "/"+"idxDef"/*StringUtils.toLowerCaseFirstOne(NameParser.getDomainName(config.getProperty("tableName")))*/+"/"
						+ paths[i].replace(".", fileSeparator) + "/";
				File file = new File(outPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				Template template = Velocity.getTemplate(templates[i]);
				template.setEncoding("utf-8");
				String fileName = outPath + table.getDomainClassName()
						+ fileNames[i];
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
