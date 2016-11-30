package com.platform.permissions.sysrole.controller;

import java.text.SimpleDateFormat;
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
import com.platform.common.utils.GenidUtil;
import com.platform.common.utils.RequestUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.core.query.ResultMessage;
import com.platform.log.syslog.service.SysLogService;
import com.platform.permissions.sysrole.model.SysRole;
import com.platform.permissions.sysrole.service.SysRoleService;
import com.platform.permissions.sysroleres.model.SysRoleRes;
import com.platform.permissions.sysroleres.service.SysRoleResService;

@Controller
@RequestMapping("/platform/permissions/sysrole")
public class SysRoleController extends CommonController {
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysRoleResService sysRoleResService;
	@Resource
	private SysLogService sysLogService;
	/**
	 * 向角色-资源表添加关联数据
	 */
	@RequestMapping("/saveRoleRes")
	@ResponseBody
	public String saveRoleRes(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long roleid = RequestUtil.getLong(request, "roleid");//角色id
	    Long[] resids = RequestUtil.getLongAryByStr(request, "ids");//资源id
        String retsString = "";
        boolean  flag = sysRoleResService.saveRoleRes(roleid, resids);
    	if(flag){
		   retsString = "success";
	   }else{
		   retsString = "fail";
	   }
        return retsString;
	}
    /**
     * 将某角色已有资源加载
     */
	@RequestMapping("/loadRoleRes")
	@ResponseBody
	public List<SysRoleRes> loadRoleRes (HttpServletRequest request,HttpServletResponse response) throws Exception{
		long roleid = RequestUtil.getLong(request, "roleid");//角色id
		List<SysRoleRes> sysRoleRes = sysRoleResService.getListByRoleId(roleid);
		return sysRoleRes;
	}
	/**
	 * 进入列表页面
	 */
	@RequiresPermissions("platform:sysrole:list")
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 分页查询数据
	 */
	@RequiresPermissions("platform:sysrole:list")
	@RequestMapping("/queryAllJson")
	@ResponseBody
	public Object queryAllJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		
		queryFilter.setFilters(filters);
		Pager<SysRole> sysRolePager = sysRoleService.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", sysRolePager.getDataList()); //这里的 rows 和total 的key 是固定的 
		map.put("total", sysRolePager.getCount());
		return map;
	}
	/**
	 * 添加、修改
	 */
	@RequiresPermissions(value={"platform:sysrole:add","platform:sysrole:edit"},logical=Logical.OR)
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("sysRole")  SysRole sysRole) throws Exception {
		String resultMsg=null;	
	 	SysRole flagObj=(SysRole) sysRoleService.getById(sysRole.getId());
		try {
			if(flagObj==null){
				//添加
				sysRoleService.add(sysRole);
				sysLogService.saveLog(request, "角色管理", "新增系统角色,id="+sysRole.getId()+"角色="+sysRole.getRolename(), 1);
				resultMsg="添加成功";
			}else {
				//修改
				sysRoleService.update(sysRole);
				sysLogService.saveLog(request, "角色管理", "编辑系统角色,id="+sysRole.getId()+"角色="+sysRole.getRolename(), 1);
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
	@RequiresPermissions("platform:sysrole:del")
	@RequestMapping("/del")
	@ResponseBody
	public String del(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			String strid="";
			for (Long id : ids) {
				strid+=id+",";
				sysRoleService.delById(id);
			}
			sysLogService.saveLog(request, "角色管理", "删除系统角色,id="+strid, 1);
			flag="success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 进入添加、修改页面
	 */
	@RequiresPermissions(value={"platform:sysrole:add","platform:sysrole:edit"},logical=Logical.OR)
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String flag="";
		long id=RequestUtil.getLong(request, "id");
		SysRole sysRole=(SysRole) sysRoleService.getById(id);
		if(sysRole==null){
			//添加页面
			flag="add";
			sysRole=new SysRole();
			sysRole.setId(GenidUtil.genId());
		}else {
			//修改页面
			flag="update";
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysRole", sysRole).addObject("flag", flag);
		return mv;
	}
	/**
	 * 进入明细页面
	 */
	@RequiresPermissions("platform:sysrole:get")
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		long id=RequestUtil.getLong(request, "id");
		SysRole sysRole=(SysRole) sysRoleService.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysRole", sysRole);
		return mv;
	}
	//-----------------------------------------------------------------------------------------------
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}