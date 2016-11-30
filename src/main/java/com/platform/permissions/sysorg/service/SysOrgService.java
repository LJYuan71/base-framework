package com.platform.permissions.sysorg.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysorg.dao.SysOrgDao;
import com.platform.permissions.sysorg.model.SysOrg;
import com.platform.permissions.sysuserorg.dao.SysUserOrgDao;
import com.platform.permissions.sysuserorg.model.SysUserOrg;
import com.secrecy.basic.classifyorg.dao.ClassifyOrgDao;
import com.secrecy.basic.classifyorg.model.ClassifyOrg;
@Service
public class SysOrgService {
	@Resource
	private SysOrgDao sysOrgDao;
	@Resource
	private ClassifyOrgDao classifyOrgDao;
	@Resource
	private SysUserOrgDao sysUserOrgDao;
	/**
	 * 根据userid查询用户orglist
	 */
	public List<SysOrg> queryOrgListByUserid(Long userid){
		List<SysOrg> sysorgList=new ArrayList<SysOrg>();
		List<SysUserOrg> sysUserOrgs=sysUserOrgDao.getByUserId(userid);
		for (SysUserOrg sysUserOrg : sysUserOrgs) {
			SysOrg sysOrg=sysOrgDao.getById(sysUserOrg.getOrgid());
			if(sysOrg!=null){
				sysorgList.add(sysOrg);
			}
		}
		return sysorgList;
	}
	/**
	 * 根据parentid查询sn最大值，赋予默认sn值
	 */
	public Long queryMaxSnByParentid(Long parentid){
		return sysOrgDao.queryMaxSnByParentid(parentid);
	}
	/**
	 * 添加
	 */
	public boolean add(SysOrg obj) {
		return sysOrgDao.add(obj);
	}
	/**
	 * 修改
	 */	
	public boolean update(SysOrg obj) {
		return sysOrgDao.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysOrg getById(Long id) {
		return (SysOrg) sysOrgDao.getById(id);
	}
	/**
	 * 根据id删除
	 */	
	public boolean delById(Long id) {
		return sysOrgDao.delById(id);
	}
	/**
	 * 根据ids查询是否所选节点存在子节点，存在不允许删除
	 */	
	public boolean hasChilds(Long[] ids) {
		boolean ret=false;
		for (Long id : ids) {
			List<SysOrg> childsList=sysOrgDao.queryByParentid(id);
			if(childsList.size()>0){
				ret=true;
				break;
			}
		}
		return ret;
	}
	/**
	 * 根据ids批量删除
	 */	
	public boolean delById(Long[] ids) {
		boolean ret=false;
		for (Long id : ids) {
			SysOrg sysOrg=sysOrgDao.getById(id);
			if(sysOrg!=null){
				//根据orgid删除关系表数据
				classifyOrgDao.delByOrgid(id);
				sysOrgDao.delById(id);
			}
		}
		return ret;
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysOrg> queryAll(QueryFilter queryFilter) {
		return sysOrgDao.queryAll(queryFilter);
	}
}