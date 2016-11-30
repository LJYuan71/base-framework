package com.platform.permissions.sysres.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.log.syslog.service.SysLogService;
import com.platform.permissions.sysres.dao.SysResDao;
import com.platform.permissions.sysres.model.SysRes;
@Service
public class SysResService {
	@Resource
	private SysResDao sysResDao;
	@Resource
	private SysLogService sysLogService;
	/**
	 * 根据roleids查询用户拥有的一级菜单权限
	 * String roleids
	 */
	public List<SysRes> queryFirstRes(Map<String, Object> params){
		return sysResDao.queryFirstRes(params);
	}
	/**
	 * 根据parentid、roleids查询用户拥有的二级菜单权限
	 * Long parentid,String roleids
	 */
	public List<SysRes> querySecondRes(Map<String, Object> params){
		return sysResDao.querySecondRes(params);
	}
	/**
	 * 根据parentid查询sn最大值，赋予默认sn值
	 */
	public Long queryMaxSnByParentid(Long parentid){
		return sysResDao.queryMaxSnByParentid(parentid);
	}
	/**
	 * 添加
	 */
	public boolean add(SysRes obj) {
		return sysResDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysRes obj) {
		return sysResDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysRes getById(Long id) {
		return (SysRes) sysResDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysResDao.delById(id);
	}
	/**
	 * 根据ids查询是否所选节点存在子节点，存在不允许删除
	 */	
	public boolean hasChilds(Long[] ids) {
		boolean ret=false;
		for (Long id : ids) {
			List<SysRes> childsList=sysResDao.queryByParentid(id);
			if(childsList.size()>0){
				ret=true;
				break;
			}
		}
		return ret;
	}
	/**
	 * 根据ids批量删除
	 * 同时删除子节点
	 */	
	public boolean delById(Long[] ids) {
		boolean ret=false;
		for (Long id : ids) {
			SysRes sysRes=sysResDao.getById(id);
			if(sysRes!=null){
				sysResDao.delById(id);
			}
		}
		return ret;
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysRes> queryAll(QueryFilter queryFilter) {
		return sysResDao.queryAll(queryFilter);
	}
	/**
	 * 根据用户id获取用户拥有的资源信息
	 */
	public List<SysRes>  queryUsersAllResByUserid(Map<String, Object> filter){
		return sysResDao.queryUsersAllResByUserid(filter);
	}
}