package com.platform.permissions.sysorgpos.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.platform.permissions.sysorgpos.model.SysOrgPos;

public interface SysOrgPosMapper extends BaseMapper
{
	/**
	 * 添加
	 */
	public boolean add(SysOrgPos obj);
	/**
	 * 修改
	 */
	public boolean update(SysOrgPos obj);
	/**
	 * 根据id获取
	 */
	public SysOrgPos getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 根据posid删除
	 */
	public boolean delByPosId(Long posid);
	/**
	 * 根据posid/orgid删除
	 */
	public boolean delByPosIdAndOrgId(Map<String, Object> filter);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<SysOrgPos> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
}