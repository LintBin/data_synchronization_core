package com.lin.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.lin.core.annotation.Table;
import com.lin.core.annotation.TableField;

public class Scanner {
    private static String PATH = null;
	 
	 //项目根目录的绝对路径
	private static String ROOT_DIR_FULL_PATH ;
	
	public Scanner() throws IOException{
		//读取properties文件
		ROOT_DIR_FULL_PATH = "/" + this.getPath();
		Properties prop = new Properties();
		prop.load(new FileInputStream(ROOT_DIR_FULL_PATH + "/config.properties"));  
		 
		PATH = prop.getProperty("base-package");  
	}
	
	public List<Class<?>> getAnnotationClasses() throws ClassNotFoundException {


		String packageUrl = PATH.replace(".", "/");

		packageUrl = packageUrl.replace("/", "\\");

		String tempPathStr = ROOT_DIR_FULL_PATH + packageUrl + "";

		File dir = new File(tempPathStr);

		File[] files = dir.listFiles();

		List<Class<?>> classList = new ArrayList<Class<?>>();

		for(File file : files){
	
			String fileName = file.getName();
			
			fileName = fileName.replace(".class", ""); 
					
			Class<?> clazz = Class.forName(PATH + "." + fileName);
			classList.add(clazz);
	
	/*Table t = clazz.getAnnotation(Table.class);
	
	Field[] Fields  = clazz.getDeclaredFields();
	
	System.out.println(t.tableName());
	String tableName = t.tableName();
	String selectSQL = "select ";
	
	for(Field field : Fields){
		
		TableField tf = field.getAnnotation(TableField.class);
		System.out.println(tf.target());
		System.out.println(tf.source());
		
		String target = tf.target();
		
		selectSQL = selectSQL + target + " ,";
		
	}
	
	selectSQL = selectSQL.substring(0,selectSQL.length()-1);	
	
	selectSQL = selectSQL + " from " + tableName;
	
	System.out.println(selectSQL);*/
		
		
		}
		return classList;
	}
	
	
 
	public List<String> getSelectSQLList() throws ClassNotFoundException{
		List<Class<?>> classList = this.getAnnotationClasses();
		
		List<String> selectSQLList = new ArrayList<String>();
		
		for(Class<?> clazz : classList){
			String selectSQL = this.getSelectSQL(clazz);
			selectSQLList.add(selectSQL);
		}
		return selectSQLList;
	}
    
	
	
	public String getSelectSQL(Class<?> clazz){
		
		Table t = clazz.getAnnotation(Table.class);
		
		Field[] Fields  = clazz.getDeclaredFields();
		
		System.out.println(t.tableName());
		String tableName = t.tableName();
		String selectSQL = "select ";
		
		for(Field field : Fields){
			
			TableField tf = field.getAnnotation(TableField.class);
			System.out.println(tf.target());
			System.out.println(tf.source());
			
			String target = tf.target();
			
			selectSQL = selectSQL + target + " ,";
			
		}
		
		selectSQL = selectSQL.substring(0,selectSQL.length()-1);	
		
		selectSQL = selectSQL + " from " + tableName;
		
		
		return selectSQL;
	}


	public String getPath() throws IOException{

		URL pathURL = this.getClass().getClassLoader().getResource("");
		String pathStr = pathURL.toString();
		if(pathStr.startsWith("file:/")){
			pathStr = pathStr.replace("file:/", "");
		}

		return pathStr;
	
	}
}
