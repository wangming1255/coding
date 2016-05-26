package com.xyan.code;

import com.xyan.code.service.CodeService;
import com.xyan.code.service.impl.CodeServiceImpl;

/**
 *入口，运行此方法即可生成代码
 * @author wangming
 */
public class Generator {
	
	public static void main(String[] args) throws Exception {
		CodeService service=new CodeServiceImpl();
		service.generateCode();
	}
}
