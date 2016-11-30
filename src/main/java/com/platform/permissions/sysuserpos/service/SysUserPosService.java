package com.platform.permissions.sysuserpos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.platform.common.utils.GenidUtil;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysuserpos.dao.SysUserPosDao;
import com.platform.permissions.sysuserpos.model.SysUserPos;
@Service
public class SysUserPosService {
	@Resource
	private SysUserPosDao sysUserPosDao;
	/**
	 * 根据userid查询用户-岗位关联数据
	 */
	public List<SysUserPos> getByUserid(long userid){
		return sysUserPosDao.getByUserid(userid);
	}
	/**
	 * 向关联表中添加用户-岗位数据
	 */
	public void addUserPos(long userid,Long[] posids){
		List<SysUserPos> userPosList = sysUserPosDao.getByUserid(userid);
		//删除原表数据
		for (SysUserPos sysUserPos : userPosList) {
			sysUserPosDao.delById(sysUserPos.getId());
		}
		for(long posid:posids){
			SysUserPos sysUserPos = new SysUserPos();
			sysUserPos.setId(GenidUtil.genId());
			sysUserPos.setUserid(userid);
			sysUserPos.setPosid(posid);
			sysUserPosDao.add(sysUserPos);
		}
	}
	/**
	 * 添加
	 */
	public boolean add(SysUserPos obj) {
		return sysUserPosDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysUserPos obj) {
		return sysUserPosDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysUserPos getById(Long id) {
		return (SysUserPos) sysUserPosDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysUserPosDao.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysUserPos> queryAll(QueryFilter queryFilter) {
		return sysUserPosDao.queryAll(queryFilter);
	}
}