package com.platform.permissions.sysroleres.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysorg.model.SysOrg;
import com.platform.permissions.sysroleres.mapper.SysRoleResMapper;
import com.platform.permissions.sysroleres.model.SysRoleRes;
@Repository
public class SysRoleResDao{
	@Resource
	private SysRoleResMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	/**
	 * 根据roleid查询role-res数据  不分页
	 */
	public List<SysRoleRes> getByRoleId(long roleid){
		QueryFilter queryFilter=new QueryFilter();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("roleid", roleid);
		queryFilter.setFilters(map);	
		Pager<SysRoleRes> sysRoleRes = this.queryAll(queryFilter);
		List<SysRoleRes> sysRoleReslist = sysRoleRes.getDataList();
		return sysRoleReslist;
	}
	/**
	 * 添加
	 */
	public boolean add(SysRoleRes obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(SysRoleRes obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysRoleRes getById(Long id) {
		return (SysRoleRes) mapper.getById(id);
	}
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id) {
		return mapper.delById(id);
	}
	/**
	 * 根据roleid删除
	 */
	public boolean delByRoleId(Long roleid) {
		return mapper.delByRoleId(roleid);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysRoleRes> queryAll(QueryFilter queryFilter) {
		Pager<SysRoleRes> returnPager=new Pager<SysRoleRes>();
		List<SysRoleRes> list=null;
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
