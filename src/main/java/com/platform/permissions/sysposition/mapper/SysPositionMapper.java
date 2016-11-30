package com.platform.permissions.sysposition.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.platform.permissions.sysposition.model.SysPosition;

public interface SysPositionMapper extends BaseMapper
{
	/**
	 * 添加
	 */
	public boolean add(SysPosition obj);
	/**
	 * 修改
	 */
	public boolean update(SysPosition obj);
	/**
	 * 根据id获取
	 */
	public SysPosition getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<SysPosition> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
}