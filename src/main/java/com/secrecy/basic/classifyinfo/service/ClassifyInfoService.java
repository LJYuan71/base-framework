package com.secrecy.basic.classifyinfo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.secrecy.basic.classifyinfo.dao.ClassifyInfoDao;
import com.secrecy.basic.classifyinfo.model.ClassifyInfo;
@Service
public class ClassifyInfoService {
	@Resource
	private ClassifyInfoDao classifyInfoDao;
	/**
	 * 根据parentid查询sn最大值，赋予默认sn值
	 */
	public Long queryMaxSnByParentid(Long parentid){
		return classifyInfoDao.queryMaxSnByParentid(parentid);
	}
	/**
	 * 添加
	 */
	public boolean add(ClassifyInfo obj) {
		return classifyInfoDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(ClassifyInfo obj) {
		return classifyInfoDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public ClassifyInfo getById(Long id) {
		return (ClassifyInfo) classifyInfoDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return classifyInfoDao.delById(id);
	}
	/**
	 * 根据ids查询是否所选节点存在子节点，存在不允许删除
	 */	
	public boolean hasChilds(Long[] ids) {
		boolean ret=false;
		for (Long id : ids) {
			List<ClassifyInfo> childsList=classifyInfoDao.queryByParentid(id);
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
			ClassifyInfo classifyInfo=classifyInfoDao.getById(id);
			if(classifyInfo!=null){
				classifyInfoDao.delById(id);
			}
		}
		return ret;
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<ClassifyInfo> queryAll(QueryFilter queryFilter) {
		return classifyInfoDao.queryAll(queryFilter);
	}
}