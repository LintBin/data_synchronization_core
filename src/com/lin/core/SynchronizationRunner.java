package com.lin.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lin.core.util.DbUtil;
import com.lin.core.util.MethodHelper;

public class SynchronizationRunner{
	
	private Scanner scanner = new Scanner();
	
	public List<?> run(Class<?> clazz) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		List<Object> objectList = new ArrayList<Object>();
		if(scanner.classIsExist(clazz)){
			
			//判断在指定的package里面是否有这个包
			if(!scanner.classIsExist(clazz)){
				System.out.println("clazz 不存在");
			}
			
			Map<Class<?>,Field[]> classMap = scanner.getClassMap();
			Field[] fieldArray = classMap.get(clazz);
			
			String selectSQL = scanner.getSelectSQL(fieldArray,clazz);
			
			Connection conn = DbUtil.getSourceConnection();
			
	        PreparedStatement pstmt = conn.prepareStatement(selectSQL + " limit 0,1");
			ResultSet rs = pstmt.executeQuery();
		
			while(rs.next()){
				Object obj = clazz.newInstance();
				
				for(int i=0;i<fieldArray.length;i++){

					Field field = fieldArray[i];
					Class<?> fieldType = field.getType();
					String setMethodName = MethodHelper.getSetMethodStr(field);
					Method method = null;
					
					try {
						method = clazz.getMethod(setMethodName,fieldType);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
					
					if(fieldType == Integer.class){
						Integer rsResult = rs.getInt(i+1);
						method.invoke(obj, rsResult);
					}
					
					if(fieldType == String.class){
						String rsResult = rs.getString(i+1);
						method.invoke(obj, rsResult);
					}
					
					if(fieldType == Boolean.class){
						Boolean rsResult = rs.getBoolean(i+1);
						method.invoke(obj, rsResult);
					}
					
					if(fieldType == Float.class){
						Float rsResult = rs.getFloat(i+1);
						method.invoke(obj, rsResult);
					}
				}
				objectList.add(obj);
			}
			
			
			
			
			/*for(int i=0;i<fieldArray.length;i++){

				Field field = fieldArray[i];
				String setMethodName = MethodHelper.getSetMethodStr(field);
				
				try {
					Method method = clazz.getMethod(setMethodName,field.getType());
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				
			}
			*/
			
			
			
			/*try {
				//运行方法的字符串要做个工具类
				Method method = clazz.getMethod("setUserId",Integer.class);
				method.invoke(obj,5);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			System.out.println(obj);*/
			
			/*List<Object> result = new ArrayList<Object>();
			Method method = clazz.getMethod("increase", int.class);*/
			//需要知道取出的属性的类型
			/*while(rs.next()){
				Object obj = clazz.newInstance();
				
				
			}*/
		}
		
      
		
		return objectList;
	}
	
}
