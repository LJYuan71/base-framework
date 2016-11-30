package com.platform.permissions.sysuserpos.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysuserpos.mapper.SysUserPosMapper;
import com.platform.permissions.sysuserpos.model.SysUserPos;
@Repository
public class SysUserPosDao{
	@Resource
	private SysUserPosMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 根据userid查询
	 */
	public List<SysUserPos> getByUserid(long userid){
		QueryFilter filter = new QueryFilter();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		filter.setFilters(map);
		Pager<SysUserPos> userPosPager = this.queryAll(filter);
		List<SysUserPos> userPosList = userPosPager.getDataList();
		return userPosList;
	}
	/**
	 * 添加
	 */
	public boolean add(SysUserPos obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(SysUserPos obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysUserPos getById(Long id) {
		return (SysUserPos) mapper.getById(id);
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
	public Pager<SysUserPos> queryAll(QueryFilter queryFilter) {
		Pager<SysUserPos> returnPager=new Pager<SysUserPos>();
		List<SysUserPos> list=null;
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
