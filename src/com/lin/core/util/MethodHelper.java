package com.lin.core.util;

import java.lang.reflect.Field;

public class MethodHelper {
	
	
	public static String getSetMethodStr(Field field){
		
		String name = field.getName();
		
		String firstLetter = name.substring(0,1);
		String otherStr = name.substring(1);
		
		String fullMethodStr = "set" + firstLetter.toUpperCase() + otherStr;
		
		return fullMethodStr;
	}
	
	
}
