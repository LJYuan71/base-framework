<#import "function.ftl" as func>
package com.${system}.${modular}.${packagename}.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.${system}.${modular}.${packagename}.dao.${modelname}Dao;
import com.${system}.${modular}.${packagename}.model.${modelname};
@Service
public class ${modelname}Service {
	@Resource
	private ${modelname}Dao ${modelname?uncap_first}Dao;
	/**
	 * 添加
	 */
	public boolean add(${modelname} obj) {
		return ${modelname?uncap_first}Dao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(${modelname} obj) {
		return ${modelname?uncap_first}Dao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public ${modelname} getById(Long id) {
		return (${modelname}) ${modelname?uncap_first}Dao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return ${modelname?uncap_first}Dao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<${modelname}> queryAll(QueryFilter queryFilter) {
		return ${modelname?uncap_first}Dao.queryAll(queryFilter);
	}
}