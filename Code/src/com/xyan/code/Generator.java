package com.xyan.code;

import com.xyan.code.service.CodeService;
import com.xyan.code.service.impl.CodeServiceImpl;

public class Generator {
	
	public static void main(String[] args) throws Exception {
		CodeService service=new CodeServiceImpl();
		service.generateCode();
	}
}
