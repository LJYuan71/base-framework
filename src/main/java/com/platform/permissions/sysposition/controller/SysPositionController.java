package com.platform.permissions.sysposition.controller;

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
import com.platform.permissions.sysorgpos.model.SysOrgPos;
import com.platform.permissions.sysorgpos.service.SysOrgPosService;
import com.platform.permissions.sysposition.model.SysPosition;
import com.platform.permissions.sysposition.service.SysPositionService;
import com.platform.permissions.sysrole.model.SysRole;
import com.platform.permissions.sysrole.service.SysRoleService;

@Controller
@RequestMapping("/platform/permissions/sysposition")
public class SysPositionController extends CommonController {
	@Resource
	private SysPositionService sysPositionService;
	@Resource
	private SysOrgPosService sysOrgPosService;
	@Resource
	private SysLogService sysLogService;
	@Resource
	private SysRoleService sysRoleService;
	/**
	 * 进入用户角色页面
	 */
	@RequestMapping("/role")
	public ModelAndView role(HttpServletRequest request,HttpServletResponse response) throws Exception {
		long positionid = RequestUtil.getLong(request, "positionid");
		ModelAndView mv = getAutoView(request);
		Pager<SysRole> pager=sysRoleService.queryAll(new QueryFilter());
		List<SysRole> sysRoleList=pager.getDataList();
		mv.addObject("sysRoleList", sysRoleList).addObject("positionid", positionid);
		return mv;
	}
	/**
	 * 批量删除
	 */
	//@RequiresPermissions("platform:sysposition:del")
	@RequestMapping("/delByOrg")
	@ResponseBody
	public String delByOrg(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			Long orgid=RequestUtil.getLong(request, "orgid");
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			String strid="";
			if(ids.length>0){
				for(int i=0;i<ids.length;i++){
					strid+=ids[i]+",";
				}
			}
			sysPositionService.delByIdsAndOrgid(ids,orgid);
			sysLogService.saveLog(request, "岗位管理", "删除岗位,id="+strid, 1);
			flag="success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 从部门列表进入岗位  添加、修改
	 */
	@RequestMapping("/saveByOrg")
	public void saveByOrg(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("sysPosition")  SysPosition sysPosition) throws Exception {
		Long orgid=RequestUtil.getLong(request, "orgid");
		String resultMsg=null;	
	 	SysPosition flagObj=(SysPosition) sysPositionService.getById(sysPosition.getId());
		try {
			if(flagObj==null){
				//添加
				sysPositionService.addByOrgid(sysPosition, orgid);
				sysLogService.saveLog(request, "岗位管理", "新增岗位,id="+sysPosition.getId()+"岗位="+sysPosition.getPosname(), 1);
				resultMsg="添加成功";
			}else {
				//修改
				sysPositionService.update(sysPosition);
				sysLogService.saveLog(request, "岗位管理", "编辑岗位,id="+sysPosition.getId()+"岗位="+sysPosition.getPosname(), 1);
				resultMsg="修改成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(),"操作失败",ResultMessage.Fail);
		}
	}
	/**
	 * 从部门列表进入岗位的添加、修改页面
	 */
	//@RequiresPermissions(value={"platform:sysposition:add","platform:sysposition:edit"},logical=Logical.OR)
	@RequestMapping("/editByOrg")
	public ModelAndView editByOrg(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long orgid=RequestUtil.getLong(request, "orgid");
		long id=RequestUtil.getLong(request, "id");
		SysPosition sysPosition=(SysPosition) sysPositionService.getById(id);
		if(sysPosition==null){
			//添加页面
			sysPosition=new SysPosition();
			sysPosition.setId(GenidUtil.genId());
		}else {
			//修改页面
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysPosition", sysPosition).addObject("orgid", orgid);
		return mv;
	}
	/**
	 * 根据部门id获取岗位列表
	 */
	//@RequiresPermissions("platform:sysposition:queryByOrg")
	@RequestMapping("/queryByOrg")
	public ModelAndView queryByOrg(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long orgid=RequestUtil.getLong(request, "orgid");
		List<SysPosition> positionList=sysPositionService.queryPositionListByOrgid(orgid);
		ModelAndView mv = getAutoView(request);
		mv.addObject("positionList", positionList).addObject("orgid", orgid);
		return mv;
	}
	/**
	 * 查询所有岗位，不分页
	 */
	@RequestMapping("/getAllPos")
	@ResponseBody
	public Object getAllPos(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//long userid = RequestUtil.getLong(request, "userid");
		QueryFilter queryFilter=new QueryFilter(request, true);
	
		//Pager<SysUserPos> sysUserPosPager = sysUserPosService.queryAll(queryFilter);
		Pager<SysPosition> sysUserPosPager = sysPositionService.queryAll(queryFilter);
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("rows", sysUserPosPager.getDataList()); //这里的 rows 和total 的key 是固定的 
		pageMap.put("total", sysUserPosPager.getDataList().size());
		return pageMap;
	}
	/**
	 * 对所选岗位分配组织机构
	 */
	@RequestMapping("/saveDistributeOrg")
	@ResponseBody
	public String saveDistributeOrg(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String ret = "";
		long posid = RequestUtil.getLong(request, "posid");
		Long[] orgids = RequestUtil.getLongAryByStr(request, "orgids");
		boolean flag = sysOrgPosService.addOrgPos(posid, orgids);
		if (flag) {
			ret = "success";
		}else {
			ret = "fail";
		}
		return ret;
	}
	 /**
     * 将某岗位已有组织机构加载
     */
	@RequestMapping("/loadOrgPos")
	@ResponseBody
	public List<SysOrgPos> loadOrgPos (HttpServletRequest request,HttpServletResponse response) throws Exception{
		long posid = RequestUtil.getLong(request, "posid");//角色id
		List<SysOrgPos> sysOrgPosList = sysOrgPosService.getListByPosId(posid);
		return sysOrgPosList;
	}
	/**
	 * 进入列表页面
	 */
	@RequiresPermissions("platform:sysposition:list")
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 分页查询数据
	 */
	@RequiresPermissions("platform:sysposition:list")
	@RequestMapping("/queryAllJson")
	@ResponseBody
	public Object queryAllJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		
		queryFilter.setFilters(filters);
		Pager<SysPosition> sysPositionPager = sysPositionService.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", sysPositionPager.getDataList()); //这里的 rows 和total 的key 是固定的 
		map.put("total", sysPositionPager.getCount());
		return map;
	}
	/**
	 * 添加、修改
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("sysPosition")  SysPosition sysPosition) throws Exception {
		String resultMsg=null;	
	 	SysPosition flagObj=(SysPosition) sysPositionService.getById(sysPosition.getId());
		try {
			if(flagObj==null){
				//添加
				sysPositionService.add(sysPosition);
				sysLogService.saveLog(request, "岗位管理", "新增岗位,id="+sysPosition.getId()+"岗位="+sysPosition.getPosname(), 1);
				resultMsg="添加成功";
			}else {
				//修改
				sysPositionService.update(sysPosition);
				sysLogService.saveLog(request, "岗位管理", "编辑岗位,id="+sysPosition.getId()+"岗位="+sysPosition.getPosname(), 1);
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
	@RequiresPermissions("platform:sysposition:del")
	@RequestMapping("/del")
	@ResponseBody
	public String del(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			String strid="";
			if(ids.length>0){
				for(int i=0;i<ids.length;i++){
					strid+=ids[i]+",";
				}
			}
			sysPositionService.delByIds(ids);
			sysLogService.saveLog(request, "岗位管理", "新增岗位,id="+strid, 1);
			flag="success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 进入添加、修改页面
	 */
	@RequiresPermissions(value={"platform:sysposition:add","platform:sysposition:edit"},logical=Logical.OR)
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String flag="";
		long id=RequestUtil.getLong(request, "id");
		SysPosition sysPosition=(SysPosition) sysPositionService.getById(id);
		if(sysPosition==null){
			//添加页面
			flag="add";
			sysPosition=new SysPosition();
			sysPosition.setId(GenidUtil.genId());
		}else {
			//修改页面
			flag="update";
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysPosition", sysPosition).addObject("flag", flag);
		return mv;
	}
	/**
	 * 进入明细页面
	 */
	@RequiresPermissions("platform:sysposition:get")
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		long id=RequestUtil.getLong(request, "id");
		SysPosition sysPosition=(SysPosition) sysPositionService.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysPosition", sysPosition);
		return mv;
	}
//-----------------------------------------------------------------------------------------------
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}