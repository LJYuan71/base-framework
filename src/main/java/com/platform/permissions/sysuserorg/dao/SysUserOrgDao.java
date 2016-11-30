package com.platform.permissions.sysuserorg.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.filter.Filter;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysuserorg.mapper.SysUserOrgMapper;
import com.platform.permissions.sysuserorg.model.SysUserOrg;
@Repository
public class SysUserOrgDao{
	@Resource
	private SysUserOrgMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 根据usrid获取 用户-组织机构 关联数据
	 */
	public List<SysUserOrg> getByUserId(long userid){
		QueryFilter filter = new QueryFilter();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		filter.setFilters(map);
		Pager<SysUserOrg> pager = this.queryAll(filter);
		List<SysUserOrg> userOrgs = pager.getDataList();
		return userOrgs;
	}
	/**
	 * 添加
	 */
	public boolean add(SysUserOrg obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(SysUserOrg obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysUserOrg getById(Long id) {
		return (SysUserOrg) mapper.getById(id);
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
	public Pager<SysUserOrg> queryAll(QueryFilter queryFilter) {
		Pager<SysUserOrg> returnPager=new Pager<SysUserOrg>();
		List<SysUserOrg> list=null;
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
