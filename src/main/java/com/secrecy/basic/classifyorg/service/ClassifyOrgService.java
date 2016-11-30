package com.secrecy.basic.classifyorg.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.platform.common.utils.GenidUtil;
import com.platform.common.utils.RequestUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysorgpos.model.SysOrgPos;
import com.platform.permissions.sysroleres.model.SysRoleRes;
import com.secrecy.basic.classifyorg.dao.ClassifyOrgDao;
import com.secrecy.basic.classifyorg.model.ClassifyOrg;
@Service
public class ClassifyOrgService {
	@Resource
	private ClassifyOrgDao classifyOrgDao;
	/**
	 * 向组织机构分类表中添加某一组织机构的分类 添加前先删除原有的
	 */
	public boolean addOrgClassify(long orgid,Long[] classifyids){
		boolean ret = false;
		List<ClassifyOrg> classifyOrgs = classifyOrgDao.getByOrgIdList(orgid);
		//删除原有分类
		for (ClassifyOrg classifyOrg : classifyOrgs) {
			classifyOrgDao.delById(classifyOrg.getId());
		}
		//添加新分类
		for (Long classifyid:classifyids){
			ClassifyOrg classifyOrg = new ClassifyOrg();
			classifyOrg.setId(GenidUtil.genId());
			classifyOrg.setOrgid(orgid);
			classifyOrg.setClassifyid(classifyid);
			classifyOrgDao.add(classifyOrg);
		}
		ret = true;
		return ret;
	}
	/**
	 * 根据roleid 查询该角色对应的资源
	 */
	public List<ClassifyOrg> getListByOrgId(long orgid){
		List<ClassifyOrg> classifyOrglist = classifyOrgDao.getByOrgIdList(orgid);
		return classifyOrglist;
	}
	/**
	 * 根据orgid删除
	 */
	public boolean delByOrgid(Long orgid){
		return classifyOrgDao.delByOrgid(orgid);
	}
	/**
	 * 添加
	 */
	public boolean add(ClassifyOrg obj) {
		return classifyOrgDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(ClassifyOrg obj) {
		return classifyOrgDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public ClassifyOrg getById(Long id) {
		return (ClassifyOrg) classifyOrgDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return classifyOrgDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<ClassifyOrg> queryAll(QueryFilter queryFilter) {
		return classifyOrgDao.queryAll(queryFilter);
	}
}