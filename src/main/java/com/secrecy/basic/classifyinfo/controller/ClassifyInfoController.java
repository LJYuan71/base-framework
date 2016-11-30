package com.secrecy.basic.classifyinfo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.platform.common.base.model.TreeData;
import com.platform.common.utils.GenidUtil;
import com.platform.common.utils.RequestUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.core.query.ResultMessage;
import com.platform.log.syslog.service.SysLogService;
import com.platform.permissions.sysorgpos.model.SysOrgPos;
import com.secrecy.basic.classifyinfo.model.ClassifyInfo;
import com.secrecy.basic.classifyinfo.service.ClassifyInfoService;
import com.secrecy.basic.classifyorg.model.ClassifyOrg;
import com.secrecy.basic.classifyorg.service.ClassifyOrgService;

@Controller
@RequestMapping("/secrecy/basic/classifyinfo")
public class ClassifyInfoController extends CommonController {
	@Resource
	private ClassifyInfoService classifyInfoService;
	@Resource
	private ClassifyOrgService classifyOrgService;
	@Resource
	private SysLogService sysLogService;
	
	/**
	 * 将所选分类添加到部门分类表
	 */
	@RequestMapping("/saveOrgClassify")
	@ResponseBody
	public String saveOrgClassify (HttpServletRequest request,HttpServletResponse response)throws Exception {
		String restString = "";
		long orgid = RequestUtil.getLong(request, "orgid");
		Long[] classifyids = RequestUtil.getLongAryByStr(request, "classifyids");
		boolean flag = classifyOrgService.addOrgClassify(orgid, classifyids);
		if (flag) {
			restString = "success";
		}else{
			restString = "fail";
		}
		return restString;
	}
	 /**
     * 将某组织机构已有资源加载
     */
	@RequestMapping("/loadOrgClassify")
	@ResponseBody
	public List<ClassifyOrg> loadOrgClassify(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long orgid = RequestUtil.getLong(request, "id");//角色id
		List<ClassifyOrg> classifyOrglist = classifyOrgService.getListByOrgId(orgid);
		return classifyOrglist;
	}
	/**
	 * 进入树形结构页面
	 */
	@RequiresPermissions("secrecy:classifyinfo:list")
	@RequestMapping("/tree")
	public ModelAndView tree(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 查询树形json
	 */
	@RequiresPermissions("secrecy:classifyinfo:list")
	@RequestMapping("/queryTreeJson")
	@ResponseBody
	public List<TreeData> queryTreeJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter();
		Pager<ClassifyInfo> classifyInfoPager = classifyInfoService.queryAll(queryFilter);
		List<ClassifyInfo> list=classifyInfoPager.getDataList();
		List<TreeData> retList=new ArrayList<>();
		for (ClassifyInfo classifyInfo : list) {
			TreeData treeData=new TreeData();
			treeData.setId(classifyInfo.getId().toString());
			treeData.setName(classifyInfo.getName());
			treeData.setpId(classifyInfo.getParentid().toString());
			retList.add(treeData);
		}
		return retList;
	}
	/**
	 * 进入列表页面
	 */
	@RequiresPermissions("secrecy:classifyinfo:list")
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 分页查询数据
	 */
	@RequiresPermissions("secrecy:classifyinfo:list")
	@RequestMapping("/queryAllJson")
	@ResponseBody
	public Object queryAllJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		
		queryFilter.setFilters(filters);
		Pager<ClassifyInfo> classifyInfoPager = classifyInfoService.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", classifyInfoPager.getDataList()); //这里的 rows 和total 的key 是固定的 
		map.put("total", classifyInfoPager.getCount());
		return map;
	}
	/**
	 * 添加、修改
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("classifyInfo")  ClassifyInfo classifyInfo) throws Exception {
		String resultMsg=null;	
	 	ClassifyInfo flagObj=(ClassifyInfo) classifyInfoService.getById(classifyInfo.getId());
		try {
			if(flagObj==null){
				//添加
				if(classifyInfo.getParentid()==0){
					classifyInfo.setPath("0");
				}else {
					ClassifyInfo parentRes=classifyInfoService.getById(classifyInfo.getParentid());
					classifyInfo.setPath(parentRes.getPath()+","+classifyInfo.getId());
				}
				classifyInfoService.add(classifyInfo);
				sysLogService.saveLog(request, "分类管理", "新增分类管理,id="+classifyInfo.getId()+"分类="+classifyInfo.getName(), 2);
				resultMsg="添加成功";
			}else {
				//修改
				classifyInfoService.update(classifyInfo);
				sysLogService.saveLog(request, "分类管理", "编辑分类管理,id="+classifyInfo.getId()+"分类="+classifyInfo.getName(), 2);
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
	@RequiresPermissions("secrecy:classifyinfo:del")
	@RequestMapping("/del")
	@ResponseBody
	public String del(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			String strid="";
			//检查所选节点是否含有子节点，含有子节点不进行删除，删除的节点必须无子节点
			boolean ret=classifyInfoService.hasChilds(ids);
			if(!ret){
				for(int i=0;i<ids.length;i++){
					strid+=ids[i]+",";
				}
				classifyInfoService.delById(ids);
				sysLogService.saveLog(request, "分类管理", "删除分类管理,id="+strid, 2);
				flag="success";
			}else {
				flag="haschilds";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 进入添加、修改页面
	 */
	@RequiresPermissions(value={"secrecy:classifyinfo:add","secrecy:classifyinfo:edit"},logical=Logical.OR)
	@RequestMapping("/queryMaxSnByParentid")
	@ResponseBody
	public String queryMaxSnByParentid(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long parentid=RequestUtil.getLong(request, "parentid");
		Long maxsn=classifyInfoService.queryMaxSnByParentid(parentid);
		return maxsn.toString();
	}
	/**
	 * 进入添加、修改页面
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long parentid=RequestUtil.getLong(request, "parentid");
		String flag="";
		long id=RequestUtil.getLong(request, "id");
		ClassifyInfo classifyInfo=(ClassifyInfo) classifyInfoService.getById(id);
		if(classifyInfo==null){
			//添加页面
			flag="add";
			classifyInfo=new ClassifyInfo();
			classifyInfo.setId(GenidUtil.genId());
			classifyInfo.setParentid(parentid);
			if(parentid==0){
				classifyInfo.setParentname("顶级节点");
			}else {
				ClassifyInfo parentRes=classifyInfoService.getById(parentid);
				classifyInfo.setParentname(parentRes.getName());
			}
			Long maxsn=classifyInfoService.queryMaxSnByParentid(parentid);
			classifyInfo.setSn(maxsn);
		}else {
			//修改页面
			flag="update";
			if(classifyInfo.getParentid()==0){
				classifyInfo.setParentname("顶级节点");
			}else {
				ClassifyInfo parentRes=classifyInfoService.getById(classifyInfo.getParentid());
				classifyInfo.setParentname(parentRes.getName());
			}
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("classifyInfo", classifyInfo).addObject("flag", flag);
		return mv;
	}
	/**
	 * 进入明细页面
	 */
	@RequiresPermissions("secrecy:classifyinfo:get")
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		long id=RequestUtil.getLong(request, "id");
		ClassifyInfo classifyInfo=(ClassifyInfo) classifyInfoService.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("classifyInfo", classifyInfo);
		return mv;
	}
//-----------------------------------------------------------------------------------------------
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}