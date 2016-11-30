<#import "function.ftl" as func>
package com.${system}.${modular}.${packagename}.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.platform.common.base.controller.CommonController;
import com.platform.common.utils.GenidUtil;
import com.platform.common.utils.RequestUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.core.query.ResultMessage;
import com.${system}.${modular}.${packagename}.model.${modelname};
import com.${system}.${modular}.${packagename}.service.${modelname}Service;

@Controller
@RequestMapping("/${system}/${modular}/${packagename}")
public class ${modelname}Controller extends CommonController {
	@Resource
	private ${modelname}Service ${modelname?uncap_first}Service;
	/**
	 * 进入列表页面
	 */
	@RequiresPermissions("${system}:${packagename}:list")
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 分页查询数据
	 */
	@RequiresPermissions("${system}:${packagename}:list")
	@RequestMapping("/queryAllJson")
	@ResponseBody
	public Object queryAllJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		
		queryFilter.setFilters(filters);
		Pager<${modelname}> ${modelname?uncap_first}Pager = ${modelname?uncap_first}Service.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", ${modelname?uncap_first}Pager.getDataList()); //这里的 rows 和total 的key 是固定的 
		map.put("total", ${modelname?uncap_first}Pager.getCount());
		return map;
	}
	/**
	 * 添加、修改
	 */
	@RequiresPermissions(value={"${system}:${packagename}:add","${system}:${packagename}:edit"},logical=Logical.OR)
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("${modelname?uncap_first}")  ${modelname} ${modelname?uncap_first}) throws Exception {
		String resultMsg=null;	
	 	${modelname} flagObj=(${modelname}) ${modelname?uncap_first}Service.getById(${modelname?uncap_first}.getId());
		try {
			if(flagObj==null){
				//添加
				${modelname?uncap_first}Service.add(${modelname?uncap_first});
				resultMsg="添加成功";
			}else {
				//修改
				${modelname?uncap_first}Service.update(${modelname?uncap_first});
				resultMsg="修改成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(),"操作失败",ResultMessage.Fail);
		}
	}
	/**
	 * 批量删除
	 */
	@RequiresPermissions("${system}:${packagename}:del")
	@RequestMapping("/del")
	@ResponseBody
	public String del(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			for (Long id : ids) {
				${modelname?uncap_first}Service.delById(id);
			}
			flag="success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 进入添加、修改页面
	 */
	@RequiresPermissions(value={"${system}:${packagename}:add","${system}:${packagename}:edit"},logical=Logical.OR)
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String flag="";
		long id=RequestUtil.getLong(request, "id");
		${modelname} ${modelname?uncap_first}=(${modelname}) ${modelname?uncap_first}Service.getById(id);
		if(${modelname?uncap_first}==null){
			//添加页面
			flag="add";
			${modelname?uncap_first}=new ${modelname}();
			${modelname?uncap_first}.setId(GenidUtil.genId());
		}else {
			//修改页面
			flag="update";
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("${modelname?uncap_first}", ${modelname?uncap_first}).addObject("flag", flag);
		return mv;
	}
	/**
	 * 进入明细页面
	 */
	@RequiresPermissions("${system}:${packagename}:get")
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		long id=RequestUtil.getLong(request, "id");
		${modelname} ${modelname?uncap_first}=(${modelname}) ${modelname?uncap_first}Service.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("${modelname?uncap_first}", ${modelname?uncap_first});
		return mv;
	}
//-----------------------------------------------------------------------------------------------
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}