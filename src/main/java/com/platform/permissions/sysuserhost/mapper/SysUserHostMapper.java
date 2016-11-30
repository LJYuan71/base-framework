package com.platform.permissions.sysuserhost.mapper;

import com.platform.common.base.mapper.BaseMapper;
import com.platform.permissions.sysuserhost.model.SysUserHost;

import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;

public interface SysUserHostMapper extends BaseMapper
{
	/**
	 * 添加
	 */
	public boolean add(SysUserHost obj);
	/**
	 * 修改
	 */
	public boolean update(SysUserHost obj);
	/**
	 * 根据id获取
	 */
	public SysUserHost getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<SysUserHost> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
	
	/**
	 * 根据用户id删除
	 */
	public boolean delByUserId(Long userid);
	
}