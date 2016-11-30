package com.secrecy.basic.classifyorg.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.secrecy.basic.classifyorg.model.ClassifyOrg;

public interface ClassifyOrgMapper extends BaseMapper
{
	/**
	 * 根据orgid删除
	 */
	public boolean delByOrgid(Long orgid);
	/**
	 * 添加
	 */
	public boolean add(ClassifyOrg obj);
	/**
	 * 修改
	 */
	public boolean update(ClassifyOrg obj);
	/**
	 * 根据id获取
	 */
	public ClassifyOrg getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<ClassifyOrg> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
}