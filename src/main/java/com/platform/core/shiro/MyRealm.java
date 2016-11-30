package com.platform.core.shiro;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import redis.clients.jedis.Jedis;

import com.platform.common.utils.RedisUtil;
import com.platform.common.utils.StringUtil;
import com.platform.permissions.sysres.model.SysRes;
import com.platform.permissions.sysres.service.SysResService;
import com.platform.permissions.sysrole.model.SysRole;
import com.platform.permissions.sysrole.service.SysRoleService;
import com.platform.permissions.sysuser.model.SysUser;
import com.platform.permissions.sysuser.service.SysUserService;
/** 
 * 自定义的指定Shiro验证用户登录的类 
 * @see 在本例中定义了2个用户:jadyer和玄玉,jadyer具有admin角色和admin:manage权限,玄玉不具有任何角色和权限 
 */  
public class MyRealm extends AuthorizingRealm{
	@Resource
	private SysUserService sysUserService;
	@Resource 
	private SysRoleService sysRoleService;
	@Resource
	private SysResService sysResService;
	@Resource
	private SessionDAO sessionDAO;
	/** 
     * 验证当前登录的Subject 
     * @see 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {  
    	Jedis jedis=RedisUtil.getJedis();
        //获取基于用户名和密码的令牌  
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的  
        //两个token的引用都是一样的,本例中是org.apache.shiro.authc.UsernamePasswordToken@33799a1e  
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;  
        SysUser user = sysUserService.getByAccount(token.getUsername());
        if(null != user){  
        	AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), this.getName());  
        	this.setSession("currentUser", user);  
        	//清空此用户之前缓存中的权限信息
			delRedis(jedis, user);
        	//清空Redis所有数据
        	//RedisUtil.clearDB(jedis);
        	//设置角色缓存
			setRoleRedis(user, jedis);
			//设置权限缓存
			setPermissionRedis(user, jedis);
			//关闭redis
			RedisUtil.returnResource(jedis);
			
        	return authcInfo;  
        }else{  
        	return null;  
        }  
    }  
    /** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            session.setTimeout(1800000);//设置session超时时间
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    } 
    //设置角色缓存
  	private void setRoleRedis(SysUser user,Jedis jedis){
  		Map<String, Object> filter=new HashMap<String, Object>();
  		filter.put("userid", user.getId());
  		List<SysRole> sysRoleList=sysRoleService.queryUsersAllRoleByUserid(filter);
  		for (SysRole sysRole : sysRoleList) {
  			//把用户角色放到缓存中
  			if(StringUtil.isNotEmpty(sysRole.getRolename())){
  				jedis.rpush("sysrole:"+user.getId(), sysRole.getRolename());
  			}
  		}   
  	}
  	//设置权限缓存
  	private void setPermissionRedis(SysUser user,Jedis jedis){
  		Map<String, Object> filter=new HashMap<String, Object>();
  		filter.put("userid", user.getId());
  		List<SysRes> sysResList=sysResService.queryUsersAllResByUserid(filter);
  		for (SysRes sysRes : sysResList) {
  			//把用户权限放到缓存中
  			if(StringUtil.isNotEmpty(sysRes.getPermission())&&
  					!"*".equals(sysRes.getPermission())&&
  					!"-".equals(sysRes.getPermission())){
  				jedis.rpush("permission:"+user.getId(),sysRes.getPermission());
  			}
  		}
  	}
  	//删除缓存
  	public static void delRedis(Jedis jedis,SysUser user){
  		jedis.del("sysrole:"+user.getId());
		jedis.del("permission:"+user.getId());
  	}
	 /** 
     * 为当前登录的Subject授予角色和权限 
     * @see 经测试:本例中该方法的调用时机为需授权资源被访问时 
     * @see 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache 
     * @see 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache 
     * @see 比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){  
    	Jedis jedis=RedisUtil.getJedis();
       //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next() 
    	String currentUsername = (String)super.getAvailablePrincipal(principals); 
    	//List<String> roleList = new ArrayList<String>();  
        //List<String> permissionList = new ArrayList<String>();  
        //从数据库中获取当前登录用户的详细信息  
        //SysUser user = sysUserService.getUserPermissionByAccount(currentUsername);
		SysUser user = sysUserService.getByAccount(currentUsername);
        List<String> roleList = jedis.lrange("sysrole:"+user.getId(), 0, -1);
        List<String> permissionList = jedis.lrange("permission:"+user.getId(), 0, -1);
        /*
        if(null != user){  
            //实体类User中包含有用户角色的实体类信息  
            if(null!=user.getRoles() && user.getRoles().size()>0){  
                //获取当前登录用户的角色  
                for(SysRole role : user.getRoles()){  
                    roleList.add(role.getRolename());  
                    //实体类Role中包含有角色权限的实体类信息  
                    if(null!=role.getSysRess() && role.getSysRess().size()>0){  
                        //获取权限  
                        for(SysRes res : role.getSysRess()){  
                            if(StringUtil.isNotEmpty(res.getPermission())){  
                                permissionList.add(res.getPermission());  
                            }  
                        }  
                    }  
                }  
            }  
        }else{  
            throw new AuthorizationException();  
        }  */
        //关闭redis
      	RedisUtil.returnResource(jedis);
        //为当前用户设置角色和权限  
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();  
        simpleAuthorInfo.addRoles(roleList);  
        simpleAuthorInfo.addStringPermissions(permissionList);  
        //SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();  
        //实际中可能会像上面注释的那样从数据库取得  
       /* if(null!=currentUsername && "abc".equals(currentUsername)){  
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色    
            simpleAuthorInfo.addRole("admin");  
            //添加权限  
            simpleAuthorInfo.addStringPermission("sys:user:list");  
            System.out.println("已为用户[abc]赋予了[admin]角色和[admin:manage]权限");  
            return simpleAuthorInfo;  
        }else if(null!=currentUsername && "lisi".equals(currentUsername)){  
            System.out.println("当前用户[玄玉]无授权");  
            return simpleAuthorInfo;  
        } */ 
        //若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址  
        //详见applicationContext.xml中的<bean id="shiroFilter">的配置  
        return simpleAuthorInfo;  
    }  
}
