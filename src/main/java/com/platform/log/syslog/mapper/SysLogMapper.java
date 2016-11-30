package com.platform.log.syslog.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.platform.log.syslog.model.SysLog;

public interface SysLogMapper extends BaseMapper
{
	/**
	 * 添加
	 */
	public boolean add(SysLog obj);
	/**
	 * 修改
	 */
	public boolean update(SysLog obj);
	/**
	 * 根据id获取
	 */
	public SysLog getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<SysLog> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
}