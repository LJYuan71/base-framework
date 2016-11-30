package com.platform.permissions.sysroleres.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.platform.permissions.sysroleres.model.SysRoleRes;

public interface SysRoleResMapper extends BaseMapper
{
	/**
	 * 添加
	 */
	public boolean add(SysRoleRes obj);
	/**
	 * 修改
	 */
	public boolean update(SysRoleRes obj);
	/**
	 * 根据id获取
	 */
	public SysRoleRes getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 根据roleid删除
	 */
	public boolean delByRoleId(Long roleid);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<SysRoleRes> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
}