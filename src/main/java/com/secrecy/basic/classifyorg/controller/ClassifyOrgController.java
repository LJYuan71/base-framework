package com.secrecy.basic.classifyorg.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.secrecy.basic.classifyorg.model.ClassifyOrg;
import com.secrecy.basic.classifyorg.service.ClassifyOrgService;

@Controller
@RequestMapping("/secrecy/basic/classifyorg")
public class ClassifyOrgController extends CommonController {
	@Resource
	private ClassifyOrgService classifyOrgService;
	/**
	 * 进入列表页面
	 */
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 分页查询数据
	 */
	@RequestMapping("/queryAllJson")
	@ResponseBody
	public Object queryAllJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		
		queryFilter.setFilters(filters);
		Pager<ClassifyOrg> classifyOrgPager = classifyOrgService.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", classifyOrgPager.getDataList()); //这里的 rows 和total 的key 是固定的 
		map.put("total", classifyOrgPager.getCount());
		return map;
	}
	/**
	 * 添加、修改
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("classifyOrg")  ClassifyOrg classifyOrg) throws Exception {
		String resultMsg=null;	
	 	ClassifyOrg flagObj=(ClassifyOrg) classifyOrgService.getById(classifyOrg.getId());
		try {
			if(flagObj==null){
				//添加
				classifyOrgService.add(classifyOrg);
				resultMsg="添加成功";
			}else {
				//修改
				classifyOrgService.update(classifyOrg);
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
	@RequestMapping("/del")
	@ResponseBody
	public String del(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			for (Long id : ids) {
				classifyOrgService.delById(id);
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
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String flag="";
		long id=RequestUtil.getLong(request, "id");
		ClassifyOrg classifyOrg=(ClassifyOrg) classifyOrgService.getById(id);
		if(classifyOrg==null){
			//添加页面
			flag="add";
			classifyOrg=new ClassifyOrg();
			classifyOrg.setId(GenidUtil.genId());
		}else {
			//修改页面
			flag="update";
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("classifyOrg", classifyOrg).addObject("flag", flag);
		return mv;
	}
	/**
	 * 进入明细页面
	 */
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		long id=RequestUtil.getLong(request, "id");
		ClassifyOrg classifyOrg=(ClassifyOrg) classifyOrgService.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("classifyOrg", classifyOrg);
		return mv;
	}
//-----------------------------------------------------------------------------------------------
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}