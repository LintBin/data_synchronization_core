package com.lin.core;

import java.util.List;

public class SynchronizationRunner{
	
	private Scanner scanner = new Scanner();
	
	public List<?> run(Class<?> clazz) throws ClassNotFoundException{
		
		if(scanner.classIsExist(clazz)){
			String selectSQL = scanner.getSelectSQL(clazz);
		}
		
		return null;
	}
	
}
