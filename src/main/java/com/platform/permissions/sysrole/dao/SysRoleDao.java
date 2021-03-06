package com.platform.permissions.sysrole.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysrole.mapper.SysRoleMapper;
import com.platform.permissions.sysrole.model.SysRole;
@Repository
public class SysRoleDao{
	@Resource
	private SysRoleMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 添加
	 */
	public boolean add(SysRole obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(SysRole obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysRole getById(Long id) {
		return (SysRole) mapper.getById(id);
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
	public Pager<SysRole> queryAll(QueryFilter queryFilter) {
		Pager<SysRole> returnPager=new Pager<SysRole>();
		List<SysRole> list=null;
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
	/**
	 * 不分页查询 
	 * 根据用户id获取用户拥有的角色信息
	 */
	public List<SysRole> queryUsersAllRoleByUserid(Map<String, Object> filter){
		return mapper.queryUsersAllRoleByUserid(filter);
	}
	
}
