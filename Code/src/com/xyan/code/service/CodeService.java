package com.xyan.code.service;

import java.util.List;

import com.xyan.code.entity.Table;

public interface CodeService {
	
	public List<Table> getTables() throws Exception;
}
