package com.platform.permissions.sysres.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysres.mapper.SysResMapper;
import com.platform.permissions.sysres.model.SysRes;
@Repository
public class SysResDao{
	@Resource
	private SysResMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 根据roleids查询用户拥有的一级菜单权限
	 * String roleids
	 */
	public List<SysRes> queryFirstRes(Map<String, Object> params){
		return mapper.queryFirstRes(params);
	}
	/**
	 * 根据parentid、roleids查询用户拥有的二级菜单权限
	 * Long parentid,String roleids
	 */
	public List<SysRes> querySecondRes(Map<String, Object> params){
		return mapper.querySecondRes(params);
	}
	/**
	 * 根据parentid获取列表
	 */
	public List<SysRes> queryByParentid(Long parentid){
		QueryFilter queryFilter=new QueryFilter();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("parentid", parentid);
		queryFilter.setFilters(map);
		Pager<SysRes> sysResPager=this.queryAll(queryFilter);
		List<SysRes> sysResList=sysResPager.getDataList();
		return sysResList;
	}
	/**
	 * 根据path获取列表，用户删除子节点
	 */
	public List<SysRes> queryByPath(String path){
		QueryFilter queryFilter=new QueryFilter();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("path", path);
		queryFilter.setFilters(map);
		Pager<SysRes> sysResPager=this.queryAll(queryFilter);
		List<SysRes> sysResList=sysResPager.getDataList();
		return sysResList;
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
		Pager<SysRes> sysResPager=this.queryAll(queryFilter);
		List<SysRes> sysResList=sysResPager.getDataList();
		if(sysResList.size()>0){
			maxSn=sysResList.get(0).getSn()+1;
		}
		return maxSn;
	}
	/**
	 * 添加
	 */
	public boolean add(SysRes obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(SysRes obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysRes getById(Long id) {
		return (SysRes) mapper.getById(id);
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
	public Pager<SysRes> queryAll(QueryFilter queryFilter) {
		Pager<SysRes> returnPager=new Pager<SysRes>();
		List<SysRes> list=null;
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
	 * 根据用户id获取用户拥有的资源信息
	 */
	public List<SysRes>  queryUsersAllResByUserid(Map<String, Object> filter){
		return mapper.queryUsersAllResByUserid(filter);
	}
}
