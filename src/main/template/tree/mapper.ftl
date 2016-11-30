<#import "function.ftl" as func>
package com.${system}.${modular}.${packagename}.mapper;

import com.platform.common.base.mapper.BaseMapper;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;
import com.${system}.${modular}.${packagename}.model.${modelname};

public interface ${modelname}Mapper extends BaseMapper
{
	/**
	 * 添加
	 */
	public boolean add(${modelname} obj);
	/**
	 * 修改
	 */
	public boolean update(${modelname} obj);
	/**
	 * 根据id获取
	 */
	public ${modelname} getById(Long id);
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id);
	/**
	 * 不分页查询 orderField 排序 id asc
	 */
	public List<${modelname}> queryAll(Map<String, Object> filter, RowBounds rb);
	/**
	 * 获取总记录条数
	 */
	public Integer queryTotal(Map<String, Object> filter);
}