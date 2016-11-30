package com.platform.permissions.sysuserrole.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.platform.permissions.sysuserrole.model.SysUserRole;

public interface SysUserRoleMapper extends BaseMapper
{
	/**
	 * 添加
	 */
	public boolean add(SysUserRole obj);
	/**
	 * 修改
	 */
	public boolean update(SysUserRole obj);
	/**
	 * 根据id获取
	 */
	public SysUserRole getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<SysUserRole> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
	
	/**
	 * 根据用户id删除
	 */
	public boolean delByUserId(Long userid);
	
}