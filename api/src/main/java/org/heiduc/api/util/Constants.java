package org.heiduc.api.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

public class Constants {
	
	public static String REALPATH = "";
	
	/**
	 * 数据库配置
	 */
	public static String DATABASE_NAME ="";
	@Deprecated
	public static int DATABASE_PORT =27017;
	@Deprecated
	public static String DATABASE_USER_NAME ="";
	@Deprecated
	public static String DATABASE_USER_PASSWORD ="";
	@Deprecated
	public static String DATABASE_HOST ="";
	public static String DATABASE_URI ="";
	
	/**
	 * task server 
	 */
	
	public static String TASKQUEUE_SERVER = "";
	
	static{
//		String webInfPath = getWebInfPath();
		Properties prorperties = new Properties();
		try {
			prorperties.load(Constants.class.getClassLoader().getResourceAsStream("config.properties"));
			DATABASE_NAME = (String) prorperties.get("heiduc.database.name");
			DATABASE_URI = (String) prorperties.get("heiduc.database.uri");
			TASKQUEUE_SERVER = (String) prorperties.get("heiduc.taskqueue.server");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getWebInfPath(){  
        URL url = Constants.class.getProtectionDomain().getCodeSource().getLocation();  
        String path = url.toString();  
        int index = path.indexOf("WEB-INF");  
          
        if(index == -1){  
            index = path.indexOf("classes");  
        }  
          
        if(index == -1){  
            index = path.indexOf("bin");  
        }  
          
        path = path.substring(0, index);  
          
        if(path.startsWith("zip")){//当class文件在war中时，此时返回zip:D:/...这样的路径  
            path = path.substring(4);  
        }else if(path.startsWith("file")){//当class文件在class文件中时，此时返回file:/D:/...这样的路径  
            path = path.substring(6);  
        }else if(path.startsWith("jar")){//当class文件在jar文件里面时，此时返回jar:file:/D:/...这样的路径  
            path = path.substring(10);   
        }  
        try {  
            path =  URLDecoder.decode(path, "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
          
        return path;  
    }
	

}
