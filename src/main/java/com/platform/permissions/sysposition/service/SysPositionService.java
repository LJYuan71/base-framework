package com.platform.permissions.sysposition.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.platform.common.utils.GenidUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysorg.model.SysOrg;
import com.platform.permissions.sysorgpos.dao.SysOrgPosDao;
import com.platform.permissions.sysorgpos.model.SysOrgPos;
import com.platform.permissions.sysposition.dao.SysPositionDao;
import com.platform.permissions.sysposition.model.SysPosition;
import com.platform.permissions.sysuserorg.model.SysUserOrg;
import com.platform.permissions.sysuserpos.dao.SysUserPosDao;
import com.platform.permissions.sysuserpos.model.SysUserPos;
@Service
public class SysPositionService {
	@Resource
	private SysPositionDao sysPositionDao;
	@Resource
	private SysOrgPosDao sysOrgPosDao;
	@Resource
	private SysUserPosDao sysUserPosDao;
	/**
	 * 根据sysOrgs查询部门下面的岗位-不分页
	 */
	public List<SysPosition> queryBySysorgList(List<SysOrg> sysorgList){
		List<SysPosition> sysPositionList=new ArrayList<>();
		for (SysOrg sysOrg : sysorgList) {
			List<SysOrgPos> sysOrgPoss=sysOrgPosDao.getByOrgId(sysOrg.getId());
			for (SysOrgPos sysOrgPos : sysOrgPoss) {
				SysPosition sysPosition=sysPositionDao.getById(sysOrgPos.getPosid());
				sysPositionList.add(sysPosition);
			}
		}
		return sysPositionList;
	}
	/**
	 * 根据ids  orgid批量删除
	 */
	public void delByIdsAndOrgid(Long[] ids,Long orgid){
		//boolean ret=false;
		for (Long id : ids) {
			SysPosition sysPosition = sysPositionDao.getById(id);
			if (sysPosition!=null) {
				//根据posid删除关系表
				sysOrgPosDao.delByPosIdAndOrgId(id, orgid);
				sysPositionDao.delById(id);
			}
		}
		//return ret;
	}
	/**
	 * 添加 岗位  以及岗位与部门关系
	 */
	public void addByOrgid(SysPosition obj,Long orgid) {
		sysPositionDao.add(obj);
		SysOrgPos sysOrgPos=new SysOrgPos();
		sysOrgPos.setId(GenidUtil.genId());
		sysOrgPos.setOrgid(orgid);
		sysOrgPos.setPosid(obj.getId());
		sysOrgPosDao.add(sysOrgPos);
	}
	/**
	 * 根据部门orgid查询用户positionlist
	 */
	public List<SysPosition> queryPositionListByOrgid(Long orgid){
		List<SysPosition> sysPositionList=new ArrayList<SysPosition>();
		List<SysOrgPos> sysOrgPoss=sysOrgPosDao.getByOrgId(orgid);
		for (SysOrgPos sysOrgPos : sysOrgPoss) {
			SysPosition position=sysPositionDao.getById(sysOrgPos.getPosid());
			if(position!=null){
				sysPositionList.add(position);
			}
		}
		return sysPositionList;
	}
	/**
	 * 根据userid查询用户positionlist
	 */
	public List<SysPosition> queryPositionListByUserid(Long userid){
		List<SysPosition> sysPositionList=new ArrayList<SysPosition>();
		List<SysUserPos> SysUserPoss=sysUserPosDao.getByUserid(userid);
		for (SysUserPos sysUserPos : SysUserPoss) {
			SysPosition position=sysPositionDao.getById(sysUserPos.getPosid());
			if(position!=null){
				sysPositionList.add(position);
			}
		}
		return sysPositionList;
	}
	/**
	 * 添加
	 */
	public boolean add(SysPosition obj) {
		return sysPositionDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysPosition obj) {
		return sysPositionDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysPosition getById(Long id) {
		return (SysPosition) sysPositionDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysPositionDao.delById(id);
	}
	/**
	 * 根据ids批量删除
	 */
	public void delByIds(Long[] ids){
		//boolean ret=false;
		for (Long id : ids) {
			SysPosition sysPosition = sysPositionDao.getById(id);
			if (sysPosition!=null) {
				//根据posid删除关系表
				sysOrgPosDao.delByPosId(id);
				sysPositionDao.delById(id);
			}
		}
		//return ret;
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysPosition> queryAll(QueryFilter queryFilter) {
		return sysPositionDao.queryAll(queryFilter);
	}
}