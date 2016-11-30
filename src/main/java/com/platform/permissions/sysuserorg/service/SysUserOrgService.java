package com.platform.permissions.sysuserorg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.platform.common.utils.GenidUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysroleres.model.SysRoleRes;
import com.platform.permissions.sysuserorg.dao.SysUserOrgDao;
import com.platform.permissions.sysuserorg.model.SysUserOrg;
@Service
public class SysUserOrgService {
	@Resource
	private SysUserOrgDao sysUserOrgDao;
	
	/**
	 * 向用户-组织机构表添加关联数据
	 */
	public void saveUserOrg(long userid,Long[] orgids){
		List<SysUserOrg> sysUserOrglist = sysUserOrgDao.getByUserId(userid);
		if (sysUserOrglist.size()>0) {
			for(SysUserOrg sysUserOrg:sysUserOrglist){
				sysUserOrgDao.delById(sysUserOrg.getId());
			}
		}
		for(Long orgid:orgids){
			SysUserOrg sysUserOrg = new SysUserOrg();
			sysUserOrg.setId(GenidUtil.genId());
			sysUserOrg.setUserid(userid);
			sysUserOrg.setOrgid(orgid);
			sysUserOrgDao.add(sysUserOrg);
		}
	}
	/**
	 * 根据userid 查询该用户对应的组织机构
	 */
	public List<SysUserOrg> getByUserId(long userid){
		List<SysUserOrg> sysUserOrgs = sysUserOrgDao.getByUserId(userid);
		return sysUserOrgs;
	}
	/**
	 * 添加
	 */
	public boolean add(SysUserOrg obj) {
		return sysUserOrgDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysUserOrg obj) {
		return sysUserOrgDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysUserOrg getById(Long id) {
		return (SysUserOrg) sysUserOrgDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysUserOrgDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysUserOrg> queryAll(QueryFilter queryFilter) {
		return sysUserOrgDao.queryAll(queryFilter);
	}
}