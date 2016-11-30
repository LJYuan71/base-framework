package com.platform.permissions.sysorg.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.platform.permissions.sysorg.model.SysOrg;

public interface SysOrgMapper extends BaseMapper
{
	/**
	 * 添加
	 */
	public boolean add(SysOrg obj);
	/**
	 * 修改
	 */
	public boolean update(SysOrg obj);
	/**
	 * 根据id获取
	 */
	public SysOrg getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<SysOrg> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
}