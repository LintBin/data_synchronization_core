package com.lin.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
	private static PropertiesHelper propertiesHelper = null;
	
	private PropertiesHelper(){
		String path ;
		try {
			path = ConfigHelper.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static synchronized  PropertiesHelper getInstance(){
		
		if(propertiesHelper == null){
			propertiesHelper = new PropertiesHelper();
		}
		return propertiesHelper;
	}
	
	public static Properties getProperties(){
	    String ROOT_DIR_FULL_PATH = null;
		//读取properties文件
		try {
			ROOT_DIR_FULL_PATH = "/" + ConfigHelper.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(ROOT_DIR_FULL_PATH + "/config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return prop;
	}
	
}
