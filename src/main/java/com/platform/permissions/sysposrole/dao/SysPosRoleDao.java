package com.platform.permissions.sysposrole.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysposrole.mapper.SysPosRoleMapper;
import com.platform.permissions.sysposrole.model.SysPosRole;
import com.platform.permissions.sysuserrole.model.SysUserRole;
@Repository
public class SysPosRoleDao{
	@Resource
	private SysPosRoleMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 根据posid查询
	 */
	public List<SysPosRole> getByPosid(long posid){
		QueryFilter filter = new QueryFilter();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("posid", posid);
		filter.setFilters(map);
		Pager<SysPosRole> posrolePager = this.queryAll(filter);
		List<SysPosRole> posroleList = posrolePager.getDataList();
		return posroleList;
	}
	/**
	 * 添加
	 */
	public boolean add(SysPosRole obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(SysPosRole obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysPosRole getById(Long id) {
		return (SysPosRole) mapper.getById(id);
	}
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id) {
		return mapper.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysPosRole> queryAll(QueryFilter queryFilter) {
		Pager<SysPosRole> returnPager=new Pager<SysPosRole>();
		List<SysPosRole> list=null;
		if(queryFilter.isNeedPage()){
			//分页
			Pager pager=queryFilter.getPager();
			//获取分页数据
			list=mapper.queryAll(queryFilter.getFilters(),new RowBounds(pager.getBeginRum(),pager.getEndRum()));
			returnPager.setDataList(list);
			//获取总条数
			Integer count=mapper.queryTotal(queryFilter.getFilters());
			returnPager.setCount(count);
		}else {
			//不分页
			list=mapper.queryAll(queryFilter.getFilters(),new RowBounds());
			returnPager.setDataList(list);
		}
		return returnPager;
	}
}
