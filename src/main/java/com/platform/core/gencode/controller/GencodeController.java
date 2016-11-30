package com.platform.core.gencode.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.platform.common.base.controller.CommonController;
import com.platform.common.utils.AppUtil;
import com.platform.common.utils.FileUtil;
import com.platform.common.utils.Freemarker;
import com.platform.common.utils.RequestUtil;
import com.platform.common.utils.StringUtil;
import com.platform.core.db.exception.CodegenException;
import com.platform.core.db.helper.IDbHelper;
import com.platform.core.db.model.Database;
import com.platform.core.db.model.TableModel;
import com.platform.log.syslog.service.SysLogService;

/** 
 * 类名称：FreemarkerController
 * 创建人：FH 
 * 创建时间：2015年1月12日
 * @version
 */
@Controller
@RequestMapping(value="/platform/core/gencode")
public class GencodeController extends CommonController{
	@Resource
	private SysLogService sysLogService;
	
	private static JdbcTemplate jdbcTemplate;
	/**
	 * 代码生成数据库连接dbHelperClass
	 */
	private static String dbHelperClass="";
	/**
	 * 代码生成数据库连接url
	 */
	private static String url="";
	/**代码生成数据库连接username
	 * 
	 */
	private static String username="";
	/**
	 * 代码生成数据库连接password
	 */
	private static String password="";
	/**
	 * 项目路径	F\:\\workspacesJdk7\\casec_base\\
	 */
	private static String baseDir="";
	/**
	 * java文件基础路径，这个不改	src\\com\\
	 */
	private static String javaFilesPath="";
	/**
	 * 系统名称	platform代表基础平台项目	每个系统设置一个system	platform\\
	 */
	private static String system="";
	/**
	 * 模块名称	permissions代表系统权限管理模块	permissions\\
	 */
	private static String modular="";
	/**
	 * jsp文件基础路径，这个不改	web\\WEB-INF\\view\\
	 */
	private static String jspFilesPath="";
	
	private static void init(){
		jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		String path = AppUtil.getClasspath()+ "properties/genCode.properties".replace("/", File.separator);
		dbHelperClass=FileUtil.readFromProperties(path, "dbHelperClass");
		url=FileUtil.readFromProperties(path, "url");
		username=FileUtil.readFromProperties(path, "username");
		password=FileUtil.readFromProperties(path, "password");
		baseDir=FileUtil.readFromProperties(path, "baseDir");
		javaFilesPath=FileUtil.readFromProperties(path, "javaFilesPath");
		system=FileUtil.readFromProperties(path, "system");
		modular=FileUtil.readFromProperties(path, "modular");
		jspFilesPath=FileUtil.readFromProperties(path, "jspFilesPath");
	}
	/**
	 * 进入列表页面
	 */
	@RequestMapping("/forward")
	public ModelAndView forward(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 生成代码
	 */
	@RequestMapping(value="/gencode")
	@ResponseBody
	public String gencode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(jdbcTemplate==null){
			init();
		}
		String tablename=RequestUtil.getString(request, "tablename");//sys_role
		String modelname=RequestUtil.getString(request, "modelname");//SysRole
		String packagename=RequestUtil.getString(request, "packagename");//sysrole
		String createType=RequestUtil.getString(request, "createType");//common（正常代码生成）、tree（树形代码生成）
		//获取需要生成所有文件的路径
		String controllerPath=baseDir+javaFilesPath+system+"\\"+modular+"\\"+packagename+"\\controller\\"+modelname+"Controller.java";
		String daoPath=baseDir+javaFilesPath+system+"\\"+modular+"\\"+packagename+"\\dao\\"+modelname+"Dao.java";
		String mapperPath=baseDir+javaFilesPath+system+"\\"+modular+"\\"+packagename+"\\mapper\\"+modelname+"Mapper.java";
		String modelPath=baseDir+javaFilesPath+system+"\\"+modular+"\\"+packagename+"\\model\\"+modelname+".java";
		String xmlPath=baseDir+javaFilesPath+system+"\\"+modular+"\\"+packagename+"\\model\\"+modelname+"Mapper.xml";
		String servicePath=baseDir+javaFilesPath+system+"\\"+modular+"\\"+packagename+"\\service\\"+modelname+"Service.java";
		String jspEditPath=baseDir+jspFilesPath+system+"\\"+modular+"\\"+packagename+"\\"+packagename+"Edit.jsp";
		String jspQueryAllPath=baseDir+jspFilesPath+system+"\\"+modular+"\\"+packagename+"\\"+packagename+"QueryAll.jsp";
		String jspGetPath=baseDir+jspFilesPath+system+"\\"+modular+"\\"+packagename+"\\"+packagename+"Get.jsp";
		String jspTreePath=baseDir+jspFilesPath+system+"\\"+modular+"\\"+packagename+"\\"+packagename+"Tree.jsp";
		//获取DbHelper
		IDbHelper iDbHelper=getDbHelper(new Database(dbHelperClass, url, username, password));
		TableModel tableModel=iDbHelper.getByTable(tablename);
		Map<String,Object> data = new HashMap<String,Object>();		//创建数据模型
		data.put("model", tableModel);
		data.put("system",system);//系统名称
		data.put("modular",modular);//模块名称
		data.put("packagename",packagename);//包名称
		data.put("modelname",modelname);//实体名称
		data.put("tablename",tablename.toUpperCase());//表名
		//生成相应代码
		Freemarker.printFile("controller.ftl", data, controllerPath,createType);
		Freemarker.printFile("dao.ftl", data, daoPath,createType);
		Freemarker.printFile("mapper.ftl", data, mapperPath,createType);
		Freemarker.printFile("model.ftl", data, modelPath,createType);
		Freemarker.printFile("xml.ftl", data, xmlPath,createType);
		Freemarker.printFile("service.ftl", data, servicePath,createType);
		Freemarker.printFile("edit.ftl", data, jspEditPath,createType);
		Freemarker.printFile("queryAll.ftl", data, jspQueryAllPath,createType);
		Freemarker.printFile("get.ftl", data, jspGetPath,createType);
		if(StringUtil.isNotEmpty(createType)&&createType.equals("tree")){
			//树形结构代码多一个文件
			Freemarker.printFile("tree.ftl", data, jspTreePath,createType);
		}
		sysLogService.saveLog(request, "生成代码", "生成代码,表名="+tablename+"实体名="+modelname+"包名="+packagename, 1);
		
		return "success";
	}
	/**
	 * 获取DbHelper。
	 * 
	 * @param configModel
	 * @return
	 * @throws CodegenException
	 */
	private IDbHelper getDbHelper(Database database) throws CodegenException {
		IDbHelper dbHelper = null;
		String dbHelperClass = database.getDbHelperClass();

		try {
			dbHelper = (IDbHelper) Class.forName(dbHelperClass).newInstance();
		} catch (InstantiationException e) {
			throw new CodegenException("指定的dbHelperClass：" + dbHelperClass
					+ "无法实例化，可能该类是一个抽象类、接口、数组类、基本类型或 void, 或者该类没有默认构造方法!", e);
		} catch (IllegalAccessException e) {
			throw new CodegenException("指定的dbHelperClass： " + dbHelperClass + "没有默认构造方法或不可访问!", e);
		} catch (ClassNotFoundException e) {
			throw new CodegenException("找不到指定的dbHelperClass:" + dbHelperClass + "!", e);
		}
		dbHelper.setUrl(database.getUrl(), database.getUsername(), database.getPassword());
		return dbHelper;
	}
}
