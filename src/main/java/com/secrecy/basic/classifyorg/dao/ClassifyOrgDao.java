package com.secrecy.basic.classifyorg.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.secrecy.basic.classifyorg.mapper.ClassifyOrgMapper;
import com.secrecy.basic.classifyorg.model.ClassifyOrg;
@Repository
public class ClassifyOrgDao{
	@Resource
	private ClassifyOrgMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 根据组织结构id查询分类
	 */
	public List<ClassifyOrg> getByOrgIdList(long orgid){
		QueryFilter filter = new QueryFilter();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orgid", orgid);
		filter.setFilters(map);
		Pager<ClassifyOrg> pager = this.queryAll(filter);
		List<ClassifyOrg> classifyOrgs = pager.getDataList();
		return classifyOrgs;
	}
	/**
	 * 根据orgid删除
	 */
	public boolean delByOrgid(Long orgid){
		return mapper.delByOrgid(orgid);
	}
	/**
	 * 添加
	 */
	public boolean add(ClassifyOrg obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(ClassifyOrg obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public ClassifyOrg getById(Long id) {
		return (ClassifyOrg) mapper.getById(id);
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
	public Pager<ClassifyOrg> queryAll(QueryFilter queryFilter) {
		Pager<ClassifyOrg> returnPager=new Pager<ClassifyOrg>();
		List<ClassifyOrg> list=null;
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
