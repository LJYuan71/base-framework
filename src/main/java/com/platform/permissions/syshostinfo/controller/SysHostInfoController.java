package com.platform.permissions.syshostinfo.controller;

import java.sql.Timestamp;
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
import com.platform.permissions.syshostinfo.model.SysHostInfo;
import com.platform.permissions.syshostinfo.service.SysHostInfoService;

@Controller
@RequestMapping("/platform/permissions/syshostinfo")
public class SysHostInfoController extends CommonController {
	@Resource
	private SysHostInfoService sysHostInfoService;
	/**
	 * 进入列表页面
	 */
	@RequiresPermissions("platform:syshostinfo:list")
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 分页查询数据
	 */
	@RequiresPermissions("platform:syshostinfo:list")
	@RequestMapping("/queryAllJson")
	@ResponseBody
	public Object queryAllJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		queryFilter.setFilters(filters);
		Pager<SysHostInfo> sysHostInfoPager = sysHostInfoService.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", sysHostInfoPager.getDataList()); //这里的 rows 和total 的key 是固定的 
		map.put("total", sysHostInfoPager.getCount());
		return map;
	}
	/**
	 * 添加、修改
	 */
	@RequiresPermissions(value={"platform:syshostinfo:add","platform:syshostinfo:edit"},logical=Logical.OR)
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("sysHostInfo")  SysHostInfo sysHostInfo) throws Exception {
		String resultMsg=null;	
	 	SysHostInfo flagObj=(SysHostInfo) sysHostInfoService.getById(sysHostInfo.getId());
		try {
			if(flagObj==null){
				//添加
				sysHostInfo.setCreatetime(new Timestamp(System.currentTimeMillis()));
				sysHostInfoService.add(sysHostInfo);
				resultMsg="添加成功";
			}else {
				//修改
				sysHostInfoService.update(sysHostInfo);
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
	@RequiresPermissions("platform:syshostinfo:del")
	@RequestMapping("/del")
	@ResponseBody
	public String del(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			for (Long id : ids) {
				sysHostInfoService.delById(id);
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
	@RequiresPermissions(value={"platform:syshostinfo:add","platform:syshostinfo:edit"},logical=Logical.OR)
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String flag="";
		long id=RequestUtil.getLong(request, "id");
		SysHostInfo sysHostInfo=(SysHostInfo) sysHostInfoService.getById(id);
		if(sysHostInfo==null){
			//添加页面
			flag="add";
			sysHostInfo=new SysHostInfo();
			sysHostInfo.setId(GenidUtil.genId());
		}else {
			//修改页面
			flag="update";
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysHostInfo", sysHostInfo).addObject("flag", flag);
		return mv;
	}
	/**
	 * 进入明细页面
	 */
	@RequiresPermissions("platform:syshostinfo:get")
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		long id=RequestUtil.getLong(request, "id");
		SysHostInfo sysHostInfo=(SysHostInfo) sysHostInfoService.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysHostInfo", sysHostInfo);
		return mv;
	}
//-----------------------------------------------------------------------------------------------
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}