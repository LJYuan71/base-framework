package com.platform.permissions.sysres.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.platform.permissions.sysres.model.SysRes;
import com.platform.permissions.sysres.service.SysResService;

@Controller
@RequestMapping("/platform/permissions/sysres")
public class SysResController extends CommonController {
	@Resource
	private SysResService sysResService;
	@Resource
	private SysLogService sysLogService;
	/**
	 * 进入树形结构页面
	 */
	@RequestMapping("/tree")
	public ModelAndView tree(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 查询树形json
	 */
	@RequestMapping("/queryTreeJson")
	@ResponseBody
	public List<TreeData> queryTreeJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter();
		Pager<SysRes> sysResPager = sysResService.queryAll(queryFilter);
		List<SysRes> list=sysResPager.getDataList();
		List<TreeData> retList=new ArrayList<>();
		for (SysRes sysRes : list) {
			TreeData treeData=new TreeData();
			treeData.setId(sysRes.getId().toString());
			treeData.setName(sysRes.getName());
			treeData.setpId(sysRes.getParentid().toString());
			retList.add(treeData);
		}
		return retList;
	}
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
		Pager<SysRes> sysResPager = sysResService.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", sysResPager.getDataList()); //这里的 rows 和total 的key 是固定的 
		map.put("total", sysResPager.getCount());
		return map;
	}
	/**
	 * 添加、修改
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("sysRes")  SysRes sysRes) throws Exception {
		String resultMsg=null;	
	 	SysRes flagObj=(SysRes) sysResService.getById(sysRes.getId());
		try {
			if(flagObj==null){
				//添加
				if(sysRes.getParentid()==0){
					sysRes.setPath("0");
				}else {
					SysRes parentRes=sysResService.getById(sysRes.getParentid());
					sysRes.setPath(parentRes.getPath()+","+sysRes.getParentid());
				}
				sysResService.add(sysRes);
				sysLogService.saveLog(request, "资源管理", "新增子系统资源,id="+sysRes.getId()+"name="+sysRes.getName(), 1);
				resultMsg="添加成功";
			}else {
				//修改
				sysResService.update(sysRes);
				sysLogService.saveLog(request, "资源管理", "编辑子系统资源,id="+sysRes.getId()+"name="+sysRes.getName(), 1);
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
			String strid="";
			//检查所选节点是否含有子节点，含有子节点不进行删除，删除的节点必须无子节点
			boolean ret=sysResService.hasChilds(ids);
			if(!ret){
				for(int i=0;i<ids.length;i++){
					strid+=ids[i]+",";
				}
				sysResService.delById(ids);
				sysLogService.saveLog(request, "资源管理", "删除子系统资源,id="+strid, 1);
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
		Long maxsn=sysResService.queryMaxSnByParentid(parentid);
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
		SysRes sysRes=(SysRes) sysResService.getById(id);
		if(sysRes==null){
			//添加页面
			flag="add";
			sysRes=new SysRes();
			sysRes.setId(GenidUtil.genId());
			sysRes.setParentid(parentid);
			if(parentid==0){
				sysRes.setParentname("顶级节点");
			}else {
				SysRes parentRes=sysResService.getById(parentid);
				sysRes.setParentname(parentRes.getName());
			}
			Long maxsn=sysResService.queryMaxSnByParentid(parentid);
			sysRes.setSn(maxsn);
		}else {
			//修改页面
			flag="update";
			if(sysRes.getParentid()==0){
				sysRes.setParentname("顶级节点");
			}else {
				SysRes parentRes=sysResService.getById(sysRes.getParentid());
				sysRes.setParentname(parentRes.getName());
			}
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysRes", sysRes).addObject("flag", flag);
		return mv;
	}
	/**
	 * 进入明细页面
	 */
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		long id=RequestUtil.getLong(request, "id");
		SysRes sysRes=(SysRes) sysResService.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysRes", sysRes);
		return mv;
	}
//-----------------------------------------------------------------------------------------------
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}