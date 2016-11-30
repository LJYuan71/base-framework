<#import "function.ftl" as func>
package com.${system}.${modular}.${packagename}.service;

import java.util.List;
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
	 * 根据parentid查询sn最大值，赋予默认sn值
	 */
	public Long queryMaxSnByParentid(Long parentid){
		return ${modelname?uncap_first}Dao.queryMaxSnByParentid(parentid);
	}
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
	 * 根据ids查询是否所选节点存在子节点，存在不允许删除
	 */	
	public boolean hasChilds(Long[] ids) {
		boolean ret=false;
		for (Long id : ids) {
			List<${modelname}> childsList=${modelname?uncap_first}Dao.queryByParentid(id);
			if(childsList.size()>0){
				ret=true;
				break;
			}
		}
		return ret;
	}
	/**
	 * 根据ids批量删除
	 * 同时删除子节点
	 */	
	public boolean delById(Long[] ids) {
		boolean ret=false;
		for (Long id : ids) {
			${modelname} ${modelname?uncap_first}=${modelname?uncap_first}Dao.getById(id);
			if(${modelname?uncap_first}!=null){
				${modelname?uncap_first}Dao.delById(id);
			}
		}
		return ret;
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