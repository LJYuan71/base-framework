package com.platform.permissions.sysrole.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.platform.permissions.sysrole.model.SysRole;

public interface SysRoleMapper extends BaseMapper
{
	/**
	 * 添加
	 */
	public boolean add(SysRole obj);
	/**
	 * 修改
	 */
	public boolean update(SysRole obj);
	/**
	 * 根据id获取
	 */
	public SysRole getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<SysRole> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
	/**
	 * 不分页查询 
	 * 根据用户id获取用户拥有的角色信息
	 */
	public List<SysRole> queryUsersAllRoleByUserid(Map<String, Object> filter);
}