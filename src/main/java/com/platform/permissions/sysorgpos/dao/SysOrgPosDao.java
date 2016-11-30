package com.platform.permissions.sysorgpos.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysorgpos.mapper.SysOrgPosMapper;
import com.platform.permissions.sysorgpos.model.SysOrgPos;
@Repository
public class SysOrgPosDao{
	@Resource
	private SysOrgPosMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 根据部门id查询
	 */
	public List<SysOrgPos> getByOrgId(long orgid){
		QueryFilter filter = new QueryFilter();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgid", orgid);
		filter.setFilters(map);
		Pager<SysOrgPos> pager = this.queryAll(filter);
		List<SysOrgPos> sysOrgPosList = pager.getDataList();
		return sysOrgPosList;
	}
	/**
	 * 根据岗位id查询
	 */
	public List<SysOrgPos> getByPosId(long posid){
		QueryFilter filter = new QueryFilter();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("posid", posid);
		filter.setFilters(map);
		Pager<SysOrgPos> pager = this.queryAll(filter);
		List<SysOrgPos> sysOrgPosList = pager.getDataList();
		return sysOrgPosList;
	}
	/**
	 * 添加
	 */
	public boolean add(SysOrgPos obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(SysOrgPos obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysOrgPos getById(Long id) {
		return (SysOrgPos) mapper.getById(id);
	}
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id) {
		return mapper.delById(id);
	}
	/**
	 * 根据posid删除
	 */
	public boolean delByPosId(Long posid) {
		return mapper.delByPosId(posid);
	}
	/**
	 * 根据posid/orgid删除
	 */
	public boolean delByPosIdAndOrgId(Long posid,Long orgid) {
		Map<String, Object> filter =new HashMap<String, Object>();
		filter.put("posid", posid);
		filter.put("orgid", orgid);
		return mapper.delByPosIdAndOrgId(filter);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysOrgPos> queryAll(QueryFilter queryFilter) {
		Pager<SysOrgPos> returnPager=new Pager<SysOrgPos>();
		List<SysOrgPos> list=null;
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
