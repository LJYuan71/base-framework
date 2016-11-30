package com.platform.permissions.sysorgpos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.alibaba.druid.sql.dialect.oracle.ast.clause.FlashbackQueryClause;
import com.platform.common.utils.GenidUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysorgpos.dao.SysOrgPosDao;
import com.platform.permissions.sysorgpos.model.SysOrgPos;
import com.platform.permissions.sysroleres.model.SysRoleRes;
@Service
public class SysOrgPosService {
	@Resource
	private SysOrgPosDao sysOrgPosDao;
	/**
	 * 向组织机构-岗位 关联表中添加数据 添加前先删除原有的
	 * @param posid 岗位id
	 * @param orgids 组织机构id
	 * @return
	 */
	public boolean addOrgPos(long posid,Long[] orgids){
		boolean flag = false;
		List<SysOrgPos> sysOrgPoslist = sysOrgPosDao.getByPosId(posid);
		for (SysOrgPos sysOrgPos : sysOrgPoslist) {
			sysOrgPosDao.delById(sysOrgPos.getId());
		}
		for (long orgid : orgids) {
			SysOrgPos sysOrgPos = new SysOrgPos();
			sysOrgPos.setId(GenidUtil.genId());
			sysOrgPos.setPosid(posid);
			sysOrgPos.setOrgid(orgid);
			sysOrgPosDao.add(sysOrgPos);
		}
		flag = true;
		return flag;
	}
	/**
	 * 根据posid 查询该岗位对应的组织结构
	 */
	public List<SysOrgPos> getListByPosId(long posid){
		List<SysOrgPos> sysOrgPosList = sysOrgPosDao.getByPosId(posid);
		return sysOrgPosList;
	}
	/**
	 * 添加
	 */
	public boolean add(SysOrgPos obj) {
		return sysOrgPosDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysOrgPos obj) {
		return sysOrgPosDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysOrgPos getById(Long id) {
		return (SysOrgPos) sysOrgPosDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysOrgPosDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysOrgPos> queryAll(QueryFilter queryFilter) {
		return sysOrgPosDao.queryAll(queryFilter);
	}
}