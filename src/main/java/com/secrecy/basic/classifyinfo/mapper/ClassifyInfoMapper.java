package com.secrecy.basic.classifyinfo.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.secrecy.basic.classifyinfo.model.ClassifyInfo;

public interface ClassifyInfoMapper extends BaseMapper
{
	/**
	 * 添加
	 */
	public boolean add(ClassifyInfo obj);
	/**
	 * 修改
	 */
	public boolean update(ClassifyInfo obj);
	/**
	 * 根据id获取
	 */
	public ClassifyInfo getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<ClassifyInfo> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
}