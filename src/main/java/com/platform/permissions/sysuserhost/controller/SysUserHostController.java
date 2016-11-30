package com.platform.permissions.sysuserhost.controller;

import java.sql.Timestamp;
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
import com.platform.common.utils.ContextUtil;
import com.platform.common.utils.GenidUtil;
import com.platform.common.utils.RequestUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.core.query.ResultMessage;
import com.platform.permissions.syshostinfo.model.SysHostInfo;
import com.platform.permissions.syshostinfo.service.SysHostInfoService;
import com.platform.permissions.sysuserhost.model.SysUserHost;
import com.platform.permissions.sysuserhost.service.SysUserHostService;

@Controller
@RequestMapping("/platform/permissions/sysuserhost")
public class SysUserHostController extends CommonController {
	@Resource
	private SysUserHostService sysUserHostService;
	@Resource
	private SysHostInfoService sysHostInfoService;
	/**
	 * 进入列表页面
	 */
	@RequiresPermissions("platform:sysuserhost:list")
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 分页查询数据
	 */
	@RequiresPermissions("platform:sysuserhost:list")
	@RequestMapping("/queryAllJson")
	@ResponseBody
	public Object queryAllJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		
		queryFilter.setFilters(filters);
		Pager<SysUserHost> sysUserHostPager = sysUserHostService.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", sysUserHostPager.getDataList()); //这里的 rows 和total 的key 是固定的 
		map.put("total", sysUserHostPager.getCount());
		return map;
	}
	/**
	 * 添加、修改
	 */
	@RequiresPermissions(value={"platform:sysuserhost:add","platform:sysuserhost:edit"},logical=Logical.OR)
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("sysUserHost")  SysUserHost sysUserHost) throws Exception {
		String resultMsg=null;	
	 	SysUserHost flagObj=(SysUserHost) sysUserHostService.getById(sysUserHost.getId());
		try {
			if(flagObj==null){
				//添加
				sysUserHostService.add(sysUserHost);
				resultMsg="添加成功";
			}else {
				//修改
				sysUserHostService.update(sysUserHost);
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
	@RequiresPermissions("platform:sysuserhost:del")
	@RequestMapping("/del")
	@ResponseBody
	public String del(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			for (Long id : ids) {
				sysUserHostService.delById(id);
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
	@RequiresPermissions(value={"platform:sysuserhost:add","platform:sysuserhost:edit"},logical=Logical.OR)
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String flag="";
		long id=RequestUtil.getLong(request, "id");
		SysUserHost sysUserHost=(SysUserHost) sysUserHostService.getById(id);
		if(sysUserHost==null){
			//添加页面
			flag="add";
			sysUserHost=new SysUserHost();
			sysUserHost.setId(GenidUtil.genId());
		}else {
			//修改页面
			flag="update";
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysUserHost", sysUserHost).addObject("flag", flag);
		return mv;
	}
	/**
	 * 进入明细页面
	 */
	@RequiresPermissions("platform:sysuserhost:get")
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		long id=RequestUtil.getLong(request, "id");
		SysUserHost sysUserHost=(SysUserHost) sysUserHostService.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysUserHost", sysUserHost);
		return mv;
	}
	
	/**
	 * 用户进入建立主机页面
	 */
	@RequiresPermissions("platform:sysuserhost:build")
	@RequestMapping("/build")
	public ModelAndView build(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		QueryFilter queryFilter=new QueryFilter(request, false);
		Map<String, Object> filters = queryFilter.getFilters();
		queryFilter.setFilters(filters);
		List<SysHostInfo> sysHostInfos = sysHostInfoService.queryAll(queryFilter).getDataList();
		return mv.addObject("sysHostInfoList", sysHostInfos);
	}
	/**
	 * 创建用户主机
	 * @param 
	 */
	//@RequiresPermissions("platform:sysuserhost:add")
	@RequestMapping("/saveUserHost")
	@ResponseBody
	public String saveUserHost(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//获取参数值
		String flag="fail";
		Long userId = ContextUtil.getSysUserId();
		long hostinfoid=RequestUtil.getLong(request, "hostinfoid");
		String hostinfotype = RequestUtil.getString(request, "hostinfotype");
		String name = RequestUtil.getString(request, "name");
		String remark = RequestUtil.getString(request, "remark");
		//设置参数值
		SysUserHost	sysUserHost=new SysUserHost();
		sysUserHost.setId(GenidUtil.genId());
		sysUserHost.setCreatetime(new Timestamp(System.currentTimeMillis()));
		sysUserHost.setHostinfoid(hostinfoid);
		sysUserHost.setType(hostinfotype);
		sysUserHost.setName(name);
		sysUserHost.setRemark(remark);
		sysUserHost.setUserid(userId);
		//保存用户主机
		if(userId!=null){
			sysUserHostService.add(sysUserHost);
			flag="success";
		}
		return flag;
	}
	/**
	 * 进入下一个页面
	 */
	//@RequiresPermissions("platform:sysuserhost:list")
	@RequestMapping("/next")
	public ModelAndView next(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 进入用户主机页面
	 */
	@RequiresPermissions("platform:sysuserhost:list")
	@RequestMapping("/queryByUserId")
	public ModelAndView queryByUserId(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 分页查询用户数据
	 */
	@RequiresPermissions("platform:sysuserhost:list")
	@RequestMapping("/queryJsonByUserId")
	@ResponseBody
	public Object queryJsonByUserId(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		filters.put("userid", ContextUtil.getSysUserId());
		queryFilter.setFilters(filters);
		Pager<SysUserHost> sysUserHostPager = sysUserHostService.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", sysUserHostPager.getDataList()); //这里的 rows 和total 的key 是固定的 
		map.put("total", sysUserHostPager.getCount());
		return map;
	}
	/**
	 * 进入远程维护
	 */
	@RequiresPermissions("platform:sysuserhost:get")
	@RequestMapping("/RM")
	public ModelAndView RM(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
//-----------------------------------------------------------------------------------------------
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}