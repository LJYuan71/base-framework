package com.platform.permissions.login.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.platform.common.base.controller.CommonController;
import com.platform.common.utils.RequestUtil;
import com.platform.permissions.sysroleres.model.SysRoleRes;
import com.platform.permissions.sysroleres.service.SysRoleResService;
import com.platform.permissions.sysuser.model.SysUser;
import com.platform.permissions.sysuser.service.SysUserService;
import com.platform.permissions.sysuserrole.model.SysUserRole;
import com.platform.permissions.sysuserrole.service.SysUserRoleService;

@Controller
@RequestMapping("/main")
public class MainController extends CommonController {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUserRoleService sysUserRoleService;
	@Resource
	private SysRoleResService sysRoleResService;
	/**
	 * 用户登陆首页
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		session.setAttribute("contextPath", request.getContextPath());
		ModelAndView mv = new ModelAndView("/index");
		return mv;
	}
	/**
	 * 用户登陆首页
	 */
	@RequestMapping("/default")
	public ModelAndView default1(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/default");
		return mv;
	}
}