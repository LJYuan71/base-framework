package com.platform.permissions.register.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.common.base.model.ValidateRetuen;
import com.platform.common.utils.EncryptUtil;
import com.platform.common.utils.GenidUtil;
import com.platform.log.syslog.model.SysLog;
import com.platform.log.syslog.service.SysLogService;
import com.platform.permissions.sysuser.model.SysUser;
import com.platform.permissions.sysuser.service.SysUserService;
import com.platform.permissions.sysuserrole.service.SysUserRoleService;

@Controller
@RequestMapping("*")
public class registerController {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysLogService sysLogService;
	@Resource
	private SysUserRoleService sysUserRoleService;
	/**
	 * 用户登陆首页
	 */
	 @RequestMapping(value="/register", method=RequestMethod.POST)  
	 @ResponseBody
	public String index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> ret=new HashMap<String, Object>();
		//获取HttpSession中的验证码  
        String verifyCode = (String)request.getSession().getAttribute("verifyCode");  
        //获取用户请求表单中输入的验证码  
        String submitCode = WebUtils.getCleanParam(request, "verifyCode");  
        if (StringUtils.isEmpty(submitCode) || !StringUtils.equals(verifyCode, submitCode.toLowerCase())){  
        	ret.put("message_register", "验证码不正确");  
        	return JSONObject.fromObject(ret).toString();
        }  
        SysUser sysUser = new SysUser();
        sysUser.setAccount(request.getParameter("account")); 
        sysUser.setPassword(EncryptUtil.encryptMd5(request.getParameter("password"))); 
        sysUser.setCompany(request.getParameter("company")); 
        sysUser.setFullname(request.getParameter("fullname")); 
        sysUser.setEmail(request.getParameter("email")); 
        sysUser.setMobile(request.getParameter("mobile")); 
        sysUser.setId(GenidUtil.genId());
        sysUser.setCreatedate(new Timestamp(System.currentTimeMillis()));
        sysUser.setStatus(1L);
        
        try {
        	//账户验证唯一性
        	ValidateRetuen validateRetuen = sysUserService.checkAccountRepeat(sysUser.getId(), sysUser.getAccount());
        	if("n".equalsIgnoreCase(validateRetuen.status)){
        		ret.put("message_register", "用户名已存在"); 
        		return JSONObject.fromObject(ret).toString();
        	}
        	sysUserService.add(sysUser);
			//新用户赋角色为普通用户
			Long roleid[] = {100000000180006L};
			sysUserRoleService.addUserRole(sysUser.getId(), roleid);
			//sysLogService.saveLog(request, "用户管理", "新增用户,id="+sysUser.getId()+"姓名="+sysUser.getFullname(), 1);
			SysLog log = new SysLog();
			log.setId(GenidUtil.genId());
			log.setUserid(sysUser.getId());
			log.setUsername(sysUser.getFullname());
			log.setCreatetime(new Timestamp(System.currentTimeMillis()));
			log.setModelname("用户管理");
			log.setDescription("新增用户,id="+sysUser.getId()+"姓名="+sysUser.getFullname());
			log.setState(1L);
			sysLogService.add(log);
			ret.put("message_register", "注册成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("message_register", "注册失败"); 
		}
        
		return JSONObject.fromObject(ret).toString();
	}

}
