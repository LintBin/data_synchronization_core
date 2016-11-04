package com.lin.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lin.core.annotation.Table;
import com.lin.core.annotation.TableField;
import com.lin.core.util.ConfigHelper;
import com.lin.core.util.PropertiesHelper;

public class Scanner {
    private static String PATH = null;
	 
	 //项目根目录的绝对路径
	private static String ROOT_DIR_FULL_PATH ;
	private Map<Class<?>,Field[]>  classMap = new HashMap<Class<?>, Field[]>();
	
	
	public Scanner(){
		//读取properties文件
		try {
			ROOT_DIR_FULL_PATH = "/" + ConfigHelper.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Properties prop = PropertiesHelper.getProperties();
		
		PATH = prop.getProperty("base-package");  
		
		
		List<Class<?>> classList = null;
		try {
			classList = this.getAnnotationClasses();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		for(Class<?> clazz : classList){
			
			Field[] Fields  = clazz.getDeclaredFields();
			
			classMap.put(clazz, Fields);
		}
		
		
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
    
	/**
	 * @deprecated 要解耦
	 * @param clazz
	 * @return
	 */
	public String getSelectSQL(Class<?> clazz){
		
		Table t = clazz.getAnnotation(Table.class);
		
		Field[] Fields  = clazz.getDeclaredFields();
		
		String tableName = t.tableName();
		String selectSQL = "select ";
		
		for(Field field : Fields){
			
			TableField tf = field.getAnnotation(TableField.class);
			String target = tf.target();
			
			selectSQL = selectSQL + target + " ,";
			
		}
		
		selectSQL = selectSQL.substring(0,selectSQL.length()-1);	
		
		selectSQL = selectSQL + " from " + tableName;
		
		
		return selectSQL;
	}
	
	/**
	 * 获取到查询的SQL,由Fields的顺序来决定查询的数据
	 * @param Fields
	 * @param clazz
	 * @return
	 */
	public String getSelectSQL(Field[] Fields ,Class<?> clazz){
		
		Table t = clazz.getAnnotation(Table.class);
		
		String selectSQL = "select ";
		String tableName = t.tableName();
		for(Field field : Fields){
			
			TableField tf = field.getAnnotation(TableField.class);
			
			if(tf == null){
				continue;
			}
			
			String target = tf.target();
			
			selectSQL = selectSQL + target + " ,";
			
		}
		
		selectSQL = selectSQL.substring(0,selectSQL.length()-1);	
		
		String result = selectSQL = selectSQL + " from " + tableName;
		
		return result;
	}
	



	public Map<Class<?>, Field[]> getClassMap() {
		return classMap;
	}
	
	public boolean classIsExist(Class<?> clazz){
		return classMap.containsKey(clazz);
	}
	
	
}
