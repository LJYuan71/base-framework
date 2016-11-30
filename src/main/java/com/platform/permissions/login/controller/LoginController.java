package com.platform.permissions.login.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.platform.common.base.controller.CommonController;
import com.platform.common.utils.EncryptUtil;
import com.platform.common.utils.VerifyCodeUtil;
import com.platform.core.pager.Pager;
import com.platform.permissions.sysres.model.SysRes;
import com.platform.permissions.sysres.service.SysResService;
import com.platform.permissions.sysroleres.service.SysRoleResService;
import com.platform.permissions.sysuser.model.SysUser;
import com.platform.permissions.sysuser.service.SysUserService;
import com.platform.permissions.sysuserrole.model.SysUserRole;
import com.platform.permissions.sysuserrole.service.SysUserRoleService;
@Controller
@RequestMapping("*")
public class LoginController extends CommonController {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUserRoleService sysUserRoleService;
	@Resource
	private SysRoleResService sysRoleResService;
	@Resource
	private SysResService sysResService;
	/**
	 * 用户登陆首页
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//接收用户
		SysUser user = (SysUser) request.getSession().getAttribute("currentUser");
		//获取用户角色
		List<SysUserRole> userRoleList = sysUserRoleService.getByUserid(user.getId());
		StringBuffer roleids=new StringBuffer("(0,");
		for (SysUserRole sysUserRole : userRoleList) {
			roleids.append(sysUserRole.getRoleid()+",");
		}
		if(roleids.length()>1){
			roleids.delete(roleids.length()-1,roleids.length());
		}
		roleids.append(")");
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("roleids", roleids.toString());
		List<SysRes> sysresList=sysResService.queryFirstRes(params);
		for (SysRes sysRes : sysresList) {
			params.clear();
			params.put("roleids", roleids.toString());
			params.put("parentid", sysRes.getId());
			List<SysRes> secondList=sysResService.querySecondRes(params);
			sysRes.setSecondList(secondList);
		}
		ModelAndView mv = new ModelAndView("/index");
		mv.addObject("sysresList", sysresList);
		return mv;
	}
	 /** 
     * 获取验证码图片和文本(验证码文本会保存在HttpSession中) 
     */  
    @RequestMapping("/getVerifyCodeImage")  
    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
        //设置页面不缓存  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);  
        //将验证码放到HttpSession里面  
        request.getSession().setAttribute("verifyCode", verifyCode);  
        System.out.println("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");  
        //设置输出的内容的类型为JPEG图像  
        response.setContentType("image/jpeg");  
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);  
        //写给浏览器  
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());  
    }  
       
       
    /** 
     * 用户登录 
     * @throws Exception 
     */  
    @RequestMapping(value="/login", method=RequestMethod.POST)  
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception{  
    	Map<String, Object> ret=new HashMap<String, Object>();
        String username = request.getParameter("username");  
        String password = request.getParameter("password");  
        //获取HttpSession中的验证码  
        /*String verifyCode = (String)request.getSession().getAttribute("verifyCode");  
        //获取用户请求表单中输入的验证码  
        String submitCode = WebUtils.getCleanParam(request, "verifyCode");  
        if (StringUtils.isEmpty(submitCode) || !StringUtils.equals(verifyCode, submitCode.toLowerCase())){  
        	ret.put("message_login", "验证码不正确");  
        	//return JSONObject.fromObject(ret).toString();
        }  */
        UsernamePasswordToken token = new UsernamePasswordToken(username,EncryptUtil.encryptMd5(password));  
        token.setRememberMe(true);  
        //获取当前的Subject  
        Subject currentUser = SecurityUtils.getSubject();  
        try {  
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查  
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应  
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法  
            currentUser.login(token);  
            ret.put("message_login", "登陆成功");
            
            /**
			 * shiro 获取登录之前的地址
			 */
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String url = null ;
			if(null != savedRequest){
				url = savedRequest.getRequestUrl();
			}
			/**
			 * 我们平常用的获取上一个请求的方式，在Session不一致的情况下是获取不到的
			 * String url = (String) request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
			 */
			//如果登录之前没有地址，那么就跳转到首页。
			if(StringUtils.isBlank(url)){
				url = request.getContextPath() + "/index.do";
			}
			ret.put("url", url);
        }catch(UnknownAccountException uae){  
        	ret.put("message_login", "未知账户");  
        }catch(IncorrectCredentialsException ice){  
        	ret.put("message_login", "用户名或密码不正确");  //密码不正确
        }catch(LockedAccountException lae){  
        	ret.put("message_login", "账户已锁定");  
        }catch(ExcessiveAttemptsException eae){  
        	ret.put("message_login", "用户名或密码错误次数过多");  
        }catch(AuthenticationException ae){  
            ret.put("message_login", "用户名或密码不正确");  
        }  
        //验证是否登录成功  
        if(currentUser.isAuthenticated()){  
        	//这里可以进行一些认证通过后的一些系统参数初始化操作
        	HttpSession session=request.getSession();
    		session.setAttribute("ctx", request.getContextPath());
    		session.setAttribute("PAGE_LIST", Pager.PAGE_LIST);
        }else{  
            token.clear();  
        }  
        return JSONObject.fromObject(ret).toString();
    }  
       
       
    /** 
     * 用户登出 
     */  
    @RequestMapping("/logout")  
    public String logout(HttpServletRequest request){  
         SecurityUtils.getSubject().logout();  
         return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";  
    }  
}