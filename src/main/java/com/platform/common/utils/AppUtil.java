package com.platform.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



/**
 * 获取ApplicationContext， 调用方法 SpringContextUtil.getContext();<br>
 * 在spring文件中配置方法：<br>
 * <!-- spring上下文,可以获取 上下文的Context -->
    <bean id="appUtil" class="casec.system.common.utils.AppUtil" />
 * @author hotent
 *
 */
public class AppUtil implements ApplicationContextAware {

	private static  ApplicationContext applicationContext;
	
	private static ServletContext servletContext;
	
	
	/**
	 * 
	 * @param servletContext
	 */
	public static void init(ServletContext _servletContext)
	{
		servletContext=_servletContext;
	}

	/**
	 * spring启动时注入context
	 */
	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		applicationContext=contex;
	}

	/**
	 * 获取spring的上下文。
	 * @return
	 */
	public static ApplicationContext getContext(){
		return applicationContext;
	}
	
	/**
	 * 获取web应用的ServletContext对象。
	 * @return
	 * @throws Exception
	 */
	public static ServletContext getServletContext() throws Exception{
		return servletContext;
	}

	
	/**
	 * 根据类从spring上下文获取bean。
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  static Object getBean(Class cls){
		return applicationContext.getBean(cls);
	}
	
	/**
	 * 根据类名从spring上下文获取bean。
	 * @param cls
	 * @return
	 */
	public static Object getBean(String  beanId){
		return applicationContext.getBean(beanId);
	}

	/**
	 * 取得应用程序的绝对路径
	 * @return
	 */
	public static String getAppAbsolutePath(){
		return servletContext.getRealPath("/");
	}
	
	/**
	 * 在web环境中根据web页面的路径获取对应页面的绝对路径。
	 * @param path
	 * @return
	 */
	public static String getRealPath(String path){
		return servletContext.getRealPath(path);
	}
	
	
	/**
	 * 获取Classpath物理路径
	 * @return
	 */
	public static String getClasspath(){
		 String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		 String rootPath  = "";
		  //windows下
		  if("\\".equals(File.separator)){   
		   rootPath  = classPath.substring(1);
		   rootPath = rootPath.replace("/", "\\");
		  }
		  //linux下
		  if("/".equals(File.separator)){   
		   rootPath  = classPath.substring(1);
		   rootPath = rootPath.replace("\\", "/");
		  }
		  return rootPath;
	}
	
	/**
	 * 根据键在属性文件中获取数据。
	 * @param fileName 属性文件名称。
	 * @param key 属性的键值。
	 * @return
	 */
	public static String readFromProperties(String fileName, String key) {
		String value = "";
		InputStream stream = null;
		try {
			stream = new BufferedInputStream(new FileInputStream(fileName));
			Properties prop = new Properties();
			prop.load(stream);
			value = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	public static void main(String[]args){
	}


}
