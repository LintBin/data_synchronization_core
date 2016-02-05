package com.lin.core.util;


/**
 * 
 * @author hongbin
 *
 */
public class SQLUtil {
	
	
	public static String addConstraint(String sqlbefore , String constraintSQL){
		
		String result = sqlbefore + " where " + constraintSQL;
		
		return result;
	}
	
	
}
