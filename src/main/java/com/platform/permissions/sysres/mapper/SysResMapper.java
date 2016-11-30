package com.platform.permissions.sysres.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.platform.permissions.sysres.model.SysRes;

public interface SysResMapper extends BaseMapper
{
	/**
	 * 根据roleids查询用户拥有的一级菜单权限
	 * String roleids
	 */
	public List<SysRes> queryFirstRes(Map<String, Object> params);
	/**
	 * 根据parentid、roleids查询用户拥有的二级菜单权限
	 * Long parentid,String roleids
	 */
	public List<SysRes> querySecondRes(Map<String, Object> params);
	/**
	 * 添加
	 */
	public boolean add(SysRes obj);
	/**
	 * 修改
	 */
	public boolean update(SysRes obj);
	/**
	 * 根据id获取
	 */
	public SysRes getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<SysRes> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
	/**
	 * 不分页查询 
	 * 根据用户id获取用户拥有的资源信息
	 */
	public List<SysRes> queryUsersAllResByUserid(Map<String, Object> filter);
}