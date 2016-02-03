package com.lin.core;

import java.io.IOException;
import java.net.URL;

public class ConfigHelper {
	
	public static String getPath() throws IOException{
		
		Class<?> clazz = ConfigHelper.class;
		
		ClassLoader classLoader = clazz.getClassLoader();
		
		URL pathURL = classLoader.getResource("");
		String pathStr = pathURL.toString();
		if(pathStr.startsWith("file:/")){
			pathStr = pathStr.replace("file:/", "");
		}
		
		return pathStr;
	}
}
