package com.platform.permissions.sysorg.controller;

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
import com.platform.permissions.sysorg.model.SysOrg;
import com.platform.permissions.sysorg.service.SysOrgService;

@Controller
@RequestMapping("/platform/permissions/sysorg")
public class SysOrgController extends CommonController {
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysLogService sysLogService;
	/**
	 * 进入树形结构页面
	 */
	@RequiresPermissions("platform:sysorg:list")
	@RequestMapping("/tree")
	public ModelAndView tree(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 查询树形json
	 */
	@RequiresPermissions("platform:sysorg:list")
	@RequestMapping("/queryTreeJson")
	@ResponseBody
	public List<TreeData> queryTreeJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter();
		Pager<SysOrg> sysOrgPager = sysOrgService.queryAll(queryFilter);
		List<SysOrg> list=sysOrgPager.getDataList();
		List<TreeData> retList=new ArrayList<>();
		for (SysOrg sysOrg : list) {
			TreeData treeData=new TreeData();
			treeData.setId(sysOrg.getId().toString());
			treeData.setName(sysOrg.getName());
			treeData.setpId(sysOrg.getParentid().toString());
			retList.add(treeData);
		}
		return retList;
	}
	/**
	 * 进入列表页面
	 */
	@RequiresPermissions("platform:sysorg:list")
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 分页查询数据
	 */
	@RequiresPermissions("platform:sysorg:list")
	@RequestMapping("/queryAllJson")
	@ResponseBody
	public Object queryAllJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		
		queryFilter.setFilters(filters);
		Pager<SysOrg> sysOrgPager = sysOrgService.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", sysOrgPager.getDataList()); //这里的 rows 和total 的key 是固定的 
		map.put("total", sysOrgPager.getCount());
		return map;
	}
	/**
	 * 添加、修改
	 */
	@RequiresPermissions(value={"platform:sysorg:add","platform:sysorg:edit"},logical=Logical.OR)
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("sysOrg")  SysOrg sysOrg) throws Exception {
		String resultMsg=null;	
	 	SysOrg flagObj=(SysOrg) sysOrgService.getById(sysOrg.getId());
		try {
			if(flagObj==null){
				//添加
				if(sysOrg.getParentid()==0){
					sysOrg.setPath("0");
				}else {
					SysOrg parentRes=sysOrgService.getById(sysOrg.getParentid());
					sysOrg.setPath(parentRes.getPath()+","+sysOrg.getId());
				}
				sysOrgService.add(sysOrg);
				sysLogService.saveLog(request, "组织机构", "新增组织机构,id="+sysOrg.getId()+"组织机构="+sysOrg.getName(), 1);
				resultMsg="添加成功";
			}else {
				//修改
				sysOrgService.update(sysOrg);
				sysLogService.saveLog(request, "组织机构", "编辑组织机构,id="+sysOrg.getId()+"组织机构="+sysOrg.getName(), 1);
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
	@RequiresPermissions("platform:sysorg:del")
	@RequestMapping("/del")
	@ResponseBody
	public String del(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			String strid="";
			//检查所选节点是否含有子节点，含有子节点不进行删除，删除的节点必须无子节点
			boolean ret=sysOrgService.hasChilds(ids);
			if(!ret){
				for(int i=0;i<ids.length;i++){
					strid+=ids[i]+",";
				}
				sysOrgService.delById(ids);
				sysLogService.saveLog(request, "组织机构", "删除组织机构,id="+strid, 1);
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
	@RequestMapping("/queryMaxSnByParentid")
	@ResponseBody
	public String queryMaxSnByParentid(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long parentid=RequestUtil.getLong(request, "parentid");
		Long maxsn=sysOrgService.queryMaxSnByParentid(parentid);
		return maxsn.toString();
	}
	/**
	 * 进入添加、修改页面
	 */
	@RequiresPermissions(value={"platform:sysorg:add","platform:sysorg:edit"},logical=Logical.OR)
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long parentid=RequestUtil.getLong(request, "parentid");
		String flag="";
		long id=RequestUtil.getLong(request, "id");
		SysOrg sysOrg=(SysOrg) sysOrgService.getById(id);
		if(sysOrg==null){
			//添加页面
			flag="add";
			sysOrg=new SysOrg();
			sysOrg.setId(GenidUtil.genId());
			sysOrg.setParentid(parentid);
			if(parentid==0){
				sysOrg.setParentname("顶级节点");
			}else {
				SysOrg parentRes=sysOrgService.getById(parentid);
				sysOrg.setParentname(parentRes.getName());
			}
			Long maxsn=sysOrgService.queryMaxSnByParentid(parentid);
			sysOrg.setSn(maxsn);
		}else {
			//修改页面
			flag="update";
			if(sysOrg.getParentid()==0){
				sysOrg.setParentname("顶级节点");
			}else {
				SysOrg parentRes=sysOrgService.getById(sysOrg.getParentid());
				sysOrg.setParentname(parentRes.getName());
			}
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysOrg", sysOrg).addObject("flag", flag);
		return mv;
	}
	/**
	 * 进入明细页面
	 */
	@RequiresPermissions("platform:sysorg:get")
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		long id=RequestUtil.getLong(request, "id");
		SysOrg sysOrg=(SysOrg) sysOrgService.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysOrg", sysOrg);
		return mv;
	}
//-----------------------------------------------------------------------------------------------
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}