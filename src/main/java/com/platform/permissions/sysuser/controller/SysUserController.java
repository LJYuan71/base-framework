package com.platform.permissions.sysuser.controller;

import java.net.URLDecoder;
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
import com.platform.common.base.model.ValidateRetuen;
import com.platform.common.utils.EncryptUtil;
import com.platform.common.utils.GenidUtil;
import com.platform.common.utils.RequestUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.core.query.ResultMessage;
import com.platform.log.syslog.service.SysLogService;
import com.platform.permissions.sysorg.model.SysOrg;
import com.platform.permissions.sysorg.service.SysOrgService;
import com.platform.permissions.sysposition.model.SysPosition;
import com.platform.permissions.sysposition.service.SysPositionService;
import com.platform.permissions.sysrole.model.SysRole;
import com.platform.permissions.sysrole.service.SysRoleService;
import com.platform.permissions.sysuser.model.SysUser;
import com.platform.permissions.sysuser.service.SysUserService;
import com.platform.permissions.sysuserhost.service.SysUserHostService;
import com.platform.permissions.sysuserorg.model.SysUserOrg;
import com.platform.permissions.sysuserorg.service.SysUserOrgService;
import com.platform.permissions.sysuserrole.service.SysUserRoleService;

@Controller
@RequestMapping("/platform/permissions/sysuser")
public class SysUserController extends CommonController {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysLogService sysLogService;
	@Resource
	private SysUserOrgService sysUserOrgService;
	@Resource
	private SysPositionService sysPositionService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysUserRoleService sysUserRoleService;
	@Resource
	private SysUserHostService sysUserHostService;
	/**
	 * 判断用户帐号是否已存在
	 */
	@RequestMapping("/checkAccountRepeat")
	@ResponseBody
	public ValidateRetuen checkAccountRepeat(HttpServletRequest request,HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		String account = RequestUtil.getString(request, "param");
		account=URLDecoder.decode(account, "UTF-8");
		ValidateRetuen validateRetuen =sysUserService.checkAccountRepeat(id, account);
		return validateRetuen;
	}
	/**
	 * 判断身份证号码是否已存在
	 */
	@RequestMapping("/checkIdcardRepeat")
	@ResponseBody
	public ValidateRetuen checkIdcardRepeat(HttpServletRequest request,HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		String idcard = RequestUtil.getString(request, "param");
		idcard=URLDecoder.decode(idcard, "UTF-8");
		ValidateRetuen validateRetuen =sysUserService.checkIdcardRepeat(id, idcard);
		return validateRetuen;
	}
	/**
	 * 进入审核页面
	 */
	@RequestMapping("/auditList")
	public ModelAndView auditList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 用户审核页面分页数据
	 */
	@RequestMapping("/auditJson")
	@ResponseBody
	public Object shenHeAllJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		filters.put("status", "0");
		queryFilter.setFilters(filters);
		Pager<SysUser> sysUserPager = sysUserService.queryAll(queryFilter);
		List<SysUser> sysUserList = sysUserPager.getDataList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", sysUserList); //这里的 rows 和total 的key 是固定的 
		map.put("total", sysUserList.size());
		return map;
	}
	/**
	 * 审核方法
	 */
	@RequestMapping("/audit")
	@ResponseBody
	public String audit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			long id=RequestUtil.getLong(request, "id");
			long status=RequestUtil.getLong(request, "status");
			SysUser sysUser = sysUserService.getById(id);
			sysUser.setStatus(status);
			sysUserService.update(sysUser);
			sysLogService.saveLog(request, "审核管理", "更改审核状态,id="+sysUser.getId()+"状态="+sysUser.getStatus(), 1);
			flag="success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 对用户设置组织机构 向用户-组织机构表添加关联数据
	 */
	@RequestMapping("/saveUserOrg")
	@ResponseBody
	public String saveUserOrg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long userid = RequestUtil.getLong(request, "userid");//角色id
	    Long[] orgids = RequestUtil.getLongAryByStr(request, "orgids");//资源id
        String retsString = "fail";
        try {
			 sysUserOrgService.saveUserOrg(userid, orgids);
			 retsString = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
        return retsString;
	}
	/**
     * 将某用户已有组织机构加载
     */
	@RequestMapping("/loadUserOrg")
	@ResponseBody
	public List<SysUserOrg> loadUserOrg (HttpServletRequest request,HttpServletResponse response) throws Exception{
		long userid = RequestUtil.getLong(request, "userid");//角色id
		List<SysUserOrg> sysOrgPosList = sysUserOrgService.getByUserId(userid);
		return sysOrgPosList;
	}
	/**
	 * 进入用户岗位页面
	 */
	@RequestMapping("/pos")
	public ModelAndView pos(HttpServletRequest request,HttpServletResponse response) throws Exception {
		long userid = RequestUtil.getLong(request, "userid");
		ModelAndView mv = getAutoView(request);
		List<SysOrg> sysOrgs=sysOrgService.queryOrgListByUserid(userid);
		List<SysPosition> sysPositionList=sysPositionService.queryBySysorgList(sysOrgs);
		mv.addObject("sysPositionList", sysPositionList).addObject("userid", userid);
		return mv;
	}
	/**
	 * 进入用户角色页面
	 */
	@RequestMapping("/role")
	public ModelAndView role(HttpServletRequest request,HttpServletResponse response) throws Exception {
		long userid = RequestUtil.getLong(request, "userid");
		ModelAndView mv = getAutoView(request);
		Pager<SysRole> pager=sysRoleService.queryAll(new QueryFilter());
		List<SysRole> sysRoleList=pager.getDataList();
		mv.addObject("sysRoleList", sysRoleList).addObject("userid", userid);
		return mv;
	}
	/**
	 * 进入列表页面
	 */
	@RequiresPermissions("platform:sysuser:list")
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView(request);
		return mv;
	}
	/**
	 * 分页查询数据
	 */
	@RequiresPermissions("platform:sysuser:list")
	@RequestMapping("/queryAllJson")
	@ResponseBody
	public Object queryAllJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=new QueryFilter(request, true);
		Map<String, Object> filters = queryFilter.getFilters();
		filters.put("notstatus", "2");
		queryFilter.setFilters(filters);
		Pager<SysUser> sysUserPager = sysUserService.queryAll(queryFilter);
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysUser> userList = sysUserPager.getDataList();
		map.put("rows", userList); //这里的 rows 和total 的key 是固定的 
		map.put("total", userList.size());
		return map;
	}
	/**
	 * 添加、修改
	 */
	@RequiresPermissions(value={"platform:sysuser:add","platform:sysuser:edit"},logical=Logical.OR)
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("sysUser")  SysUser sysUser) throws Exception {
		String resultMsg=null;	
	 	SysUser flagObj=(SysUser) sysUserService.getById(sysUser.getId());
		try {
			if(flagObj==null){
				//添加
				sysUser.setStatus(0l);
				sysUser.setPassword(EncryptUtil.encryptMd5(sysUser.getPassword()));//密码加密
				if(sysUser.getCreatedate() == null){
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					sysUser.setCreatedate(timestamp);
				}
				sysUserService.add(sysUser);
				//新用户赋角色为普通用户
				Long roleid[] = {100000000180006L};
				sysUserRoleService.addUserRole(sysUser.getId(), roleid);
				sysLogService.saveLog(request, "用户管理", "新增用户,id="+sysUser.getId()+"姓名="+sysUser.getFullname(), 1);
				resultMsg="添加成功";
			}else {
				//修改
				sysUser.setPassword(EncryptUtil.encryptMd5(sysUser.getPassword()));//密码加密
				sysUserService.update(sysUser);
				sysLogService.saveLog(request, "用户管理", "编辑用户,id="+sysUser.getId()+"姓名="+sysUser.getFullname(), 1);
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
	@RequiresPermissions("platform:sysuser:del")
	@RequestMapping("/del")
	@ResponseBody
	public String del(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag="fail";
		try {
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			String strid="";
			for (Long id : ids) {
				strid+=id+",";
				sysUserService.del(id);
				sysUserRoleService.delByUserId(id);//删除对应用户的角色
				sysUserHostService.delByUserId(id);//删除对应用户的主机
			}
			sysLogService.saveLog(request, "用户管理", "删除用户,id="+strid, 1);
			flag="success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 进入添加、修改页面
	 */
	@RequiresPermissions(value={"platform:sysuser:add","platform:sysuser:edit"},logical=Logical.OR)
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String flag="";
		long id=RequestUtil.getLong(request, "id");
		SysUser sysUser=(SysUser) sysUserService.getById(id);
		if(sysUser==null){
			//添加页面
			flag="add";
			sysUser=new SysUser();
			sysUser.setId(GenidUtil.genId());
		}else {
			//修改页面
			flag="update";
		}
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysUser", sysUser).addObject("flag", flag);
		return mv;
	}
	/**
	 * 进入明细页面
	 */
	@RequiresPermissions("platform:sysuser:get")
	@RequestMapping("/get")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception {
		long id=RequestUtil.getLong(request, "id");
		SysUser sysUser=(SysUser) sysUserService.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysUser", sysUser);
		return mv;
	}
	/**
	 * 进入明细2页面
	 */
	@RequestMapping("/auditGet")
	public ModelAndView auditGet(HttpServletRequest request,HttpServletResponse response) throws Exception {
		long id=RequestUtil.getLong(request, "id");
		SysUser sysUser=(SysUser) sysUserService.getById(id);
		ModelAndView mv = getAutoView(request);
		mv.addObject("sysUser", sysUser);
		return mv;
	}
//-----------------------------------------------------------------------------------------------
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
}