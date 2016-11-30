package com.platform.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysuser.model.SysUser;
import com.platform.permissions.sysuser.service.SysUserService;

/**
 * 取得当前用户登录时的上下文环境，一般用于获取当前登录的用户
 * @author csx
 *
 */
public class ContextUtil {
	
	/**
	 * 取得当前登录的用户。
	 */
	public static SysUser getSysUser(){
		SysUser sysUser=null;
		Subject account = SecurityUtils.getSubject();  
        if (account != null) {
        	QueryFilter queryFilter=new QueryFilter();
        	Map<String, Object> map=new HashMap<String, Object>();
        	map.put("accountEquals", account.getPrincipal());
        	queryFilter.setFilters(map);
        	SysUserService sysUserService=(SysUserService)AppUtil.getBean("sysUserService");
        	Pager<SysUser> sysuserPager=sysUserService.queryAll(queryFilter);
        	if(sysuserPager.getDataList().size()==1){
        		sysUser=sysuserPager.getDataList().get(0);
        	}
        }
        return sysUser;
	}
	/**
	 * 取得当前用户的ID
	 */
	public static Long getSysUserId(){
		Long userid=null;
		Subject account = SecurityUtils.getSubject();  
        if (account != null) {
        	QueryFilter queryFilter=new QueryFilter();
        	Map<String, Object> map=new HashMap<String, Object>();
        	map.put("accountEquals", account.getPrincipal());
        	queryFilter.setFilters(map);
        	SysUserService sysUserService=(SysUserService)AppUtil.getBean("sysUserService");
        	Pager<SysUser> sysuserPager=sysUserService.queryAll(queryFilter);
        	if(sysuserPager.getDataList().size()==1){
        		SysUser sysUser=sysuserPager.getDataList().get(0);
        		userid=sysUser.getId();
        	}
        }
        return userid;
	}
	
}
