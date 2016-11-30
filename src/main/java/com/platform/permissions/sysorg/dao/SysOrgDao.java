package com.platform.permissions.sysorg.dao;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysorg.mapper.SysOrgMapper;
import com.platform.permissions.sysorg.model.SysOrg;
@Repository
public class SysOrgDao{
	@Resource
	private SysOrgMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 根据parentid获取列表
	 */
	public List<SysOrg> queryByParentid(Long parentid){
		QueryFilter queryFilter=new QueryFilter();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("parentid", parentid);
		queryFilter.setFilters(map);
		Pager<SysOrg> sysOrgPager=this.queryAll(queryFilter);
		List<SysOrg> sysOrgList=sysOrgPager.getDataList();
		return sysOrgList;
	}
	/**
	 * 根据parentid查询sn最大值，赋予默认sn值
	 */
	public Long queryMaxSnByParentid(Long parentid){
		Long maxSn=1L;
		QueryFilter queryFilter=new QueryFilter();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("parentid", parentid);
		map.put("sort", "sn");
		map.put("order", "desc");
		queryFilter.setFilters(map);
		Pager<SysOrg> sysOrgPager=this.queryAll(queryFilter);
		List<SysOrg> sysOrgList=sysOrgPager.getDataList();
		if(sysOrgList.size()>0){
			maxSn=sysOrgList.get(0).getSn()+1;
		}
		return maxSn;
	}
	/**
	 * 添加
	 */
	public boolean add(SysOrg obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(SysOrg obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysOrg getById(Long id) {
		return (SysOrg) mapper.getById(id);
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
	public Pager<SysOrg> queryAll(QueryFilter queryFilter) {
		Pager<SysOrg> returnPager=new Pager<SysOrg>();
		List<SysOrg> list=null;
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
